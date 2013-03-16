/**
 * Основной класс клиента EasyShot.
 * На данный момент консольный.
 * TODO: Создание скриншота рабочего стола при запуске и незамедлительная отправка на сервер с дальнейшим получением адреса на файл.
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
		// Получение скриншота
		ScreenShot screenShot = new ScreenShot();
	}

}
