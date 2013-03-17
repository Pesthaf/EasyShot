package tray;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс, реализующий всплывающее меню при нажатии правой кнопкой мыши по иконке в трее.
 * Включает в себя обработку событий при нажатии на какой-либо пункт меню.
 * @author Taneong
 */

public class Menu {

	/**
	 * Непосредственно меню.
	 */
	private static PopupMenu popMenu = new PopupMenu();
	
	/**
	 * Статический метод, наполняет меню и вешает обработчики событий. Вызывается при запросе меню методом getMenu().
	 */
	private static void createMenu() {
		// Заполняем меню пунктами
		MenuItem item1 = new MenuItem("Сделать скриншот экрана");
		MenuItem item2 = new MenuItem("Сделать скриншот области");
		MenuItem item3 = new MenuItem("Выход");
		// Вешаем обработчики
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.Main.createScreenshot(true);
			}
		});
		// ----
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.Main.createScreenshot(false);
			}
		});
		// ----
		item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1); // Выходим
			}
		});
		// Добавляем пункты в меню
		popMenu.add(item1);
		popMenu.add(item2);
		popMenu.add(item3);
	}
	
	/**
	 * Метод возвращает меню, предварительно заполнив его.
	 * @return Возвращается объект класса PopupMenu, готовый к добавлению к иконке в трее.
	 */
	public static PopupMenu getMenu() {
		createMenu(); // Создаем меню
		return popMenu; // Возвращаем
	}
	
}
