import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import javax.imageio.ImageIO;

/**
 * Класс для обработки подключений. 
 * Реализует многопоточность, чтобы не создавать очередь при нескольких подключениях
 * 
 * @author Taneong
 * 
 */

public class Handler extends Thread {

	/**
	 * Клиентский сокет
	 */
	private Socket client;

	/**
	 * Адрес для сохранения изображений
	 */
	private String imagePath;

	/**
	 * IP-адрес сервера
	 */
	private String ip;

	Handler(Socket client, String imagePath, String ip) {
		this.client = client;
		this.imagePath = imagePath;
		this.ip = ip;
	}

	public void run() {
		try {
			// Считываем изображение из потока
			BufferedImage image = ImageIO.read(client.getInputStream());
			// Сохраняем на диск
			UUID id = UUID.randomUUID();
			String fileName = id.toString().replaceAll("-", "") + ".png";
			ImageIO.write(image, "png", new File(imagePath + fileName));
			String url = "http://" + ip + "/screenshots/" + fileName;
			// Уведомляем об успешном получении файла
			System.out.println("Скриншот успешно получен и сохранен по пути: " + fileName + "\n");
			System.out.println("URL для загрузки: " + url);
			// Отправляем адрес клиенту
			BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
			bos.write(url.getBytes());
			// Закрываем сокет
			bos.flush();
			bos.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
