package Main;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Класс создает скриншот при вызове конструктора класса.
 * Реализует интерфейс Serializable для поддержки передачи объектов данного класса через потоки.
 * 
 * @author Taneong
 *
 */

public class ScreenShot {

	/**
	 * Переменная для хранения непосредственного изображения с экрана
	 */
	private BufferedImage image;
	
	/**
	 * 
	 * Конструктор класса. Создает скриншот.
	 * 
	 */
	public ScreenShot() {
		
		Robot robot;
		try {
			robot = new Robot(); // Создаем объект класса Robot
			Rectangle screenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); // Получаем разрешение экрана
			image = robot.createScreenCapture(screenRectangle); // Записываем в переменную image содержимое экрана
		} catch (AWTException e) {
			System.err.println("Ошибка получения содержимого экрана");
		} 
	}
	
	/**
	 * Метод для получения скриншота
	 * @return BufferedImage содержащий скриншот
	 */
	public BufferedImage getScreenShot() {
		return image;
	}
	
}
