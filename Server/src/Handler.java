import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
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

	/**
	 * Конструктор
	 * @param client Клиентский сокет
	 * @param imagePath Путь для сохранения изображения
	 * @param ip IP-Адрес сервера, для формирования адреса загрузки изображения
	 */
	Handler(Socket client, String imagePath, String ip) {
		this.client = client;
		this.imagePath = imagePath;
		this.ip = ip;
	}

	/**
	 * Метод, запускаемый при старте потока.
	 * Обрабатывает входящие соединение:
	 * 	- Получает файл
	 *  - Сохраняет на диск
	 *  - Возвращает адрес для загрузки
	 */
	public void run() {
		try {
			// Выводим информацию о подключении
			showInformation();
			// Считываем изображение из потока
			BufferedImage image = ImageIO.read(client.getInputStream());
			// Сохраняем на диск
			UUID id = UUID.randomUUID(); // Генерируем случайный ID
			String fileName = id.toString().replaceAll("-", "") + ".png"; // Формируем из него имя файла (в ID встречается символ '-', поэтому подчищаем)
			ImageIO.write(image, "png", new File(imagePath + fileName)); // Записываем изображение в файл
			String url = "http://" + ip + "/screenshots/" + fileName;
			// Уведомляем об успешном получении файла
			Logs.write("Скриншот успешно получен и сохранен по пути: " + imagePath + fileName);
			Logs.write("URL для загрузки: " + url);
			Logs.write("Размер скриншота: " + new File(imagePath + fileName).length()/1024 + "KB \n");
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
	
	private void showInformation() {
		InetAddress address = client.getInetAddress();
		Logs.write("Информация о подключении:");
		Logs.write("IP-Адрес: " + address.getHostAddress());
		Logs.write("Имя: " + address.getHostName());
		Logs.write("Дата/Время: " + new Date().toString());
	}

}
