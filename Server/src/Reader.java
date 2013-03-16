import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
	 * Путь для полученного изображения
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
		try {
			// Создаем серверный сокет
			socket = new ServerSocket(port);
			// Запускаем бесконечный цикл
			while (true) {
				Socket client = socket.accept();
				
				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
				ScreenShot image = (ScreenShot) ois.readObject();
				ImageIO.write(image.getScreenShot(), "png", new File(imagePath));
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
