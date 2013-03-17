import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, обеспечивающий логирование 
 * @author Антон
 *
 */

public class Logs {

	/**
	 * Имя файла для хранения логов
	 */
	private final static String FILELOG_NAME = "server.log";
	
	/**
	 * Основной метод класса, непосредственно осуществляет запись в файл лога и одновременный вывод сообщения в консоль
	 * @param msg Сообщение для лога
	 */
	public static void write(String msg) {
		try {
			// Открываем файл
			File log = new File(FILELOG_NAME);
			// Получаем поток
			FileWriter fw = new FileWriter(log, true);
			BufferedWriter bw = new BufferedWriter(fw);
			// Записываем
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			bw.write(dateFormat.format(new Date()) + " | " + msg + "\n");
			// Выводим в консоль
			System.out.println(dateFormat.format(new Date()) + " | " + msg);
			// Закрываем поток
			bw.close();
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
