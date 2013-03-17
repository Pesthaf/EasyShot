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
	 * Конструктор
	 * 
	 * @param Порт для прослушки входящих соединений
	 */
	public Reader(int port) {
		this.port = port;
	}

	/**
	 * Запускает бесконечный цикл прослушки порта на наличие соединений
	 */
	public void start() {
		try {
			// Создаем серверный сокет
			socket = new ServerSocket(port);
			// Запускаем бесконечный цикл
			while (true) {
				Socket client = socket.accept();
				// Считываем изображение из потока
				BufferedImage image = ImageIO.read(client.getInputStream());
				// Сохраняем на диск
				UUID id = UUID.randomUUID();
				String imagePath = id.toString().replaceAll("-", "") + ".png";
				ImageIO.write(image, "png", new File(imagePath));
				// Закрываем сокет
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
