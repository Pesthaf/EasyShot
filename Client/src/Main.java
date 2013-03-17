import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

/**
 * Основной класс клиента EasyShot. 
 * На данный момент консольный. TODO: -
 * 
 * @author Taneong
 * 
 */

public class Main {

	/**
	 * @param args Аргументы при запуске
	 */
	public static void main(String[] args) {
		// Приветствие
		System.out.println("EasyShot");
		// Чтение настроек
		Settings settings = new Settings();
		// Получение скриншота
		ScreenShot screenShot = new ScreenShot();
		// Отправка скриншота
		Sender sender = new Sender(settings.getIP(), settings.getPort(), screenShot);
		String url = sender.Send();
		// Заполняем буфер обмена и выводим адрес в консоль
		if (!url.equals("-1")) { // Если загрузка прошла успешно
			setClipboard(url);
			System.out.println("Адрес для загрузки: " + url);
		} else { // Иначе
			System.out.println("Ошибка загрузки.");
		}
	}

	/**
	 * Метод для заполнения буфера обмена
	 * @param str Строка, которую нужно сохранить в буфер обмена
	 */
	public static void setClipboard(String str) {
		StringSelection ss = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}

}
