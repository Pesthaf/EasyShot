import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

import javax.imageio.ImageIO;

/**
 * Класс для прослушки порта
 * 
 * @author Taneong
 * 
 */
public class Reader {

	/**
	 * Серверный сокет
	 */
	private ServerSocket socket;

	/**
	 * Порт для прослушки входящих соединений
	 */
	private int port;

	/**
	 * Адрес для сохранения изображений
	 */
	private String imagePath;
	
	/**
	 * IP-адрес сервера
	 */
	private String ip;
	
	/**
	 * Конструктор
	 * 
	 * @param Порт для прослушки входящих соединений
	 */
	public Reader(int port, String imagePath, String ip) {
		this.port = port;
		this.imagePath = imagePath;
		this.ip = ip;
	}

	/**
	 * Запускает бесконечный цикл прослушки порта на наличие соединений
	 */
	public void start() {
		
		System.out.println("Начинаем слушать " + ip + ":" + port);
		try {
			// Создаем серверный сокет
			socket = new ServerSocket(port);
			// Запускаем бесконечный цикл
			while (true) {
				Socket client = socket.accept();
				// Уведомляем о новом подключении
				System.out.println("Подключился клиент");
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
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
