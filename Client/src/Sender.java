import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Класс отправляет скриншот серверу.
 * 
 * @author Taneong
 *
 */

public class Sender {

	/**
	 * Сокет для соединения с сервером
	 */
	private Socket socket;
	
	/**
	 * IP-адрес сервера
	 */
	private String ip;
	
	/**
	 * Порт для соединения с сервером
	 */
	private int port;
	
	/**
	 * Изображение для отправки на сервер
	 */
	private ScreenShot image;
	
	/**
	 * Конструктор класса
	 * @param ip IP-адрес сервера
	 * @param port Порт для соединения с сервером
	 * @param image Изображение для отправки на сервер
	 */
	public Sender(String ip, int port, ScreenShot image) {
		this.ip = ip;
		this.port = port;
		this.image = image;
	}
	
	/**
	 * Метод для отправки скриншота на сервер
	 * @return Возвращает true при успешном выполнении
	 */
	public boolean Send() {
		try {
			// Открываем сокет
			socket = new Socket(ip, port);
			// Получаем потоки
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			// Создаем потоки для обмена объектами
			ObjectInputStream ois = new ObjectInputStream(is);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			// Отправляем скриншот
			oos.writeObject(image);
			oos.flush();
			// Закрываем сокет
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
