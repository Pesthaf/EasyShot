import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Класс создает скриншот при вызове конструктора класса.
 * Реализует интерфейс Serializable для поддержки передачи объектов данного класса через потоки.
 * 
 * @author Taneong
 *
 */

public class ScreenShot implements Serializable {

	/**
	 * Переменная для хранения непосредственного изображения с экрана
	 */
	private BufferedImage image;
	
	/**
	 * Метод для получения скриншота
	 * @return BufferedImage содержащий скриншот
	 */
	public BufferedImage getScreenShot() {
		return image;
	}
	
}