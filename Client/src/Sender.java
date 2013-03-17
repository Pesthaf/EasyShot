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
	public boolean Send() {
		try {
			// Открываем сокет
			socket = new Socket(ip, port);
			// Получаем потоки
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			// Отправляем скриншот
			ImageIO.write(image.getScreenShot(), "png", os);
			// Ждем адрес
			byte buffer[] = new byte[4096];
			while (is.read(buffer) != -1) {
				url += new String(buffer);
			}
			// Закрываем сокет
			is.close();
			os.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (SocketException e) {
			System.out.println("Адрес для загруки: " + url);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
