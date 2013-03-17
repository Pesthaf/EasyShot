package Main;
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
	 * IP-адрес сервера
	 */
	private String ip;
	
	/**
	 * Порт для соединения с сервером
	 */
	private int port;
	
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
			ip = scanner.nextLine(); // Считывем строку с IP
			port = scanner.nextInt(); // Считываем порт
		} catch (FileNotFoundException e) {
			System.err.println("Ошибка чтения файла конфигурации. Файл не найден");
		}
	}
	
	/**
	 * Метод для получения IP-адреса из настроек
	 * @return Возвращает IP адрес в String
	 */
	public String getIP() {
		return ip;
	}
	
	/**
	 * Метод для получения порта из настроек
	 * @return Возвращает порт в int
	 */
	public int getPort() {
		return port;
	}
}
