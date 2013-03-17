import java.awt.image.BufferedImage;
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
	 * Конструктор
	 * 
	 * @param Порт для прослушки входящих соединений
	 */
	public Reader(int port, String imagePath) {
		this.port = port;
		this.imagePath = imagePath;
	}

	/**
	 * Запускает бесконечный цикл прослушки порта на наличие соединений
	 */
	public void start() {
		System.out.println("Начинаем слушать порт " + port + "...");
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
				String fileName = imagePath + id.toString().replaceAll("-", "") + ".png";
				ImageIO.write(image, "png", new File(fileName));
				// Уведомляем об успешном получении файла
				System.out.println("Скриншот успешно получен и сохранен по пути: " + fileName + "\n");
				// Закрываем сокет
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
