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
	 * Путь для полученного изображения
	 */
	private String imagePath;
	
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
			imagePath = scanner.nextLine(); // Считываем путь для изображения
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
	 * Метод для получения пути изображения
	 * @return Возвращает путь для сохранения изображения в String
	 */
	public String getImagePath() {
		return imagePath;
	}
}
