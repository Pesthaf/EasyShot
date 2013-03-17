package tray;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

/**
 * Класс, ответственный за нахождение программы в трее.
 * Регистрирует себя в системе, создает меню и отлавливает события
 * 
 * @author Taneong
 */

public class Tray {

	/**
	 * Непосредственно иконка в трее
	 */
	private TrayIcon trayicon;
	
	/**
	 * Файл-изображение, которое принимает приложение в трее
	 */
	private File icon = new File("icon.gif");
	
	/**
	 * False - при нажатии по иконке начнется выгрузка, true - откроется ссылка
	 */
	private boolean openOrUpload = false;
	
	private static final long OPEN_DELAY = 10000;
	
	/**
	 * Конструктор, создает иконку, вешает обработчик и добавляет в трей.
	 */
	public Tray() {
		try {
			// Создаем иконку в трее
			trayicon = new TrayIcon(ImageIO.read(icon), "EasyShot", Menu.getMenu()); // Регистрируем место в трее
			trayicon.addActionListener(new ActionListener() { // Вешаем обработчик нажатий
				public void actionPerformed(ActionEvent arg0) { // Выполняется при нажатии по иконке в трее или по уведомлению
					if (!openOrUpload) Main.Main.createScreenshot();
					else
						try {
							Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + Main.Main.getURL()); // Открывает ссылку в браузере по-умолчанию
						} catch (IOException e) {}
				}
			});
			// Добавляем в трей
			SystemTray.getSystemTray().add(trayicon);
			// Выводим уведомление
			message("Приложение запущено. Нажмите по иконке, чтобы сделать скриншот");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Метод для вывода уведомления
	 * @param msg Сообщение для вывода
	 */
	public void message(String msg) {
		trayicon.displayMessage("EasyShot", msg, TrayIcon.MessageType.INFO);
	}
	
	public void startTimer() {
		// Меняем действие при нажатии
		openOrUpload = true;
		// Ждем OPEN_DELAY мс.
		Timer timer = new Timer(); // Создаем таймер
		TimerTask task = new TimerTask() { // Создаем задачу
			public void run() {
				openOrUpload = false; // Теперь при нажатии будет выгружаться скриншот
			}
		};
		timer.schedule(task, OPEN_DELAY); // Запускаем отчет
	}
}
