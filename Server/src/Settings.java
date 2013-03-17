import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Класс для работы с настройками
 * @author Taneong
 *
 */

public class Settings {

	/**
	 * Наименование файла с настройками
	 */
	private final String FILE_NAME = "config.ini";
	
	/**
	 * Порт для прослушки
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
	 * Конструктор класса. Считывает содержимое файла конфигурации.
	 */
	public Settings() {
		try {
			// Создаем файл
			File file = new File(FILE_NAME);
			// Читаем
			Scanner scanner;
			scanner = new Scanner(file); // Вешаем сканер на файл конфигурации
			port = scanner.nextInt(); // Считываем порт
			scanner.nextLine(); // Костыль, почему-то возвращает пустую строку
			imagePath = scanner.nextLine(); // Считываем путь для сейва изображений
			ip = scanner.nextLine(); // Считываем IP-адрес сервера
		} catch (FileNotFoundException e) {
			System.err.println("Ошибка чтения файла конфигурации. Файл не найден");
		}
	}
	
	/**
	 * Метод для получения порта из настроек
	 * @return Возвращает порт в int
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Метод для получения пути сохранения изображений
	 * @return Возвращает абсолютный путь в String
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * Метод для получения IP-адреса сервера
	 * @return Возвращает IP-адрес в String
	 */
	public String getIP() {
		return ip;
	}
}
