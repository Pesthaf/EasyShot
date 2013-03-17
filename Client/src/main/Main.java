package main;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;

import tray.Tray;
import area.Area;

/**
 * Основной класс клиента EasyShot. 
 * На данный момент консольный. TODO: -
 * 
 * @author Taneong
 * 
 */

public class Main {

	/**
	 * Класс-обвертка, берущий на себя всю работу с треем
	 */
	private static Tray tray;
	
	/**
	 * Принимает значение true при загрузке скриншота
	 */
	private static boolean uploading = false;
	
	/**
	 * Адрес для загрузки скриншота
	 */
	private static String url;
	
	/**
	 * @param args Аргументы при запуске
	 */
	public static void main(String[] args) {
		// "Пролезаем" в трей
		tray = new Tray();
	}

	/**
	 * Метод для заполнения буфера обмена
	 * @param str Строка, которую нужно сохранить в буфер обмена
	 */
	public static void setClipboard(String str) {
		StringSelection ss = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}

	/**
	 * Метод создает скриншот, отправляет его и возвращает адрес пользователю
	 * @param fullScreen True - скриншот всего экрана, false - области
	 */
	public static void createScreenshot(final boolean fullScreen) {
		if (uploading) // Если загрузка уже идет, то не реагируем
			return;
		else
			uploading = true; // Меняем значение флага, чтобы пользователь не мог повторно отправить скриншот
		// Создаем поток отдельный от GUI
		new Thread(new Runnable() {
			public void run() {
				// Чтение настроек
				Settings settings = new Settings();
				// Получение скриншота
				ScreenShot screenShot = new ScreenShot(); // Создаем скриншот всего экрана
				BufferedImage image;
				if (fullScreen) { // Всего экрана
					image = screenShot.getScreenShot();
				} else { // Области
					Area area = new Area(screenShot.getScreenShot()); // Представляем пользователю выбор части скриншота
					image = area.getCutImage();
				}
				// Проверяем, не отменил ли пользователь операцию
				if (image == null) {
					tray.message("Отменено");
					uploading = false; // Позволяем пользователю снова выгружать скриншоты
					return;
				}
				// Отправка скриншота
				Sender sender = new Sender(settings.getIP(), settings.getPort(), image);
				tray.message("Загружаем скриншот...");
				url = sender.Send();
				// Заполняем буфер обмена и выводим уведомление
				tray.startTimer(); // Меняем действие при нажатии на уведомление: теперь откроется ссылка, а не загрузится скриншот
				if (!url.equals("-1")) { // Если загрузка прошла успешно
					setClipboard(url);
					tray.message("Скриншот успешно загружен! Ссылка скопирована в буфер обмена.");
				} else { // Иначе
					tray.message("Ошибка загрузки! Проверьте соединение с интернетом.");
				}
				// Позволяем пользователю снова выгружать скриншоты
				uploading = false;
			}
		}).start();
	}
	
	/**
	 * Метод для получения адреса изображения
	 * @return URL-адрес скриншота в String
	 */
	public static String getURL() {
		return url;
	}
}
