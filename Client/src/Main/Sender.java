package Main;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

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
	 * URL адрес для загрузки скриншота
	 */
	private String url = "";
	
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
	public String Send() {
		try {
			// Открываем сокет
			socket = new Socket(ip, port);
			// Получаем потоки
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			// Отправляем скриншот
			ImageIO.write(image.getScreenShot(), "png", os);
			// Ждем адрес
			byte buffer[] = new byte[4096]; // 4К байт - должно быть достаточно для адреса
			is.read(buffer); // Считываем из потока
			url = new String(buffer);
			// Закрываем сокет
			is.close();
			os.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
			return "-1";
		} catch (SocketException e) {
			System.err.println("Невозможно установить соединение. Возможно, сервер отключен.");
			return "-1";
		} catch (IOException e) {
			e.printStackTrace();
			return "-1";
		}
		return url;
	}
	
}
