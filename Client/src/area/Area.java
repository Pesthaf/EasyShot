package area;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * Класс для обрезания скриншота. Предлагает пользователю выделить область экрана, для последующей загрузки на сервер.
 * @author Taneong
 *
 */

public class Area extends Canvas {

	/**
	 * Полный скриншот экрана
	 */
	private final BufferedImage image;
	
	/**
	 * Обрезанный скриншот
	 */
	private BufferedImage cutImage = null;

	/**
	 * Флаг, определяющий, рисовать ли прямоугольник для выделения области. (Зависит от нажатия клавиши мыши)
	 */
	private boolean drawArea = false;
	
	/**
	 * Координаты прямоугольной области
	 */
	private int x1, y1, x2, y2;
	
	/**
	 * Окно для отображения скриншота
	 */
	private JFrame frame;
	
	/**
	 * Двойная буфферизация
	 */
	private BufferStrategy strategy;
	
	/**
	 * Флаг, определяющий работу основного цикла отрисовки/ожидания выделения области
	 */
	private boolean mainLoop = true;
	
	public Area(BufferedImage in) {
		this.image = in; // Присваиваем локальной переменной буффер экрана
	}
	
	private void create() {
		frame = new JFrame("EasyShot"); // Создаем фрейм
		frame.setUndecorated(true); // Убираем границы
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Получаем разрешение экрана
		frame.setSize(screenSize); // Устанавливаем размер фрейма аналогичный разрешению экрана
		this.setBounds(0, 0, screenSize.width, screenSize.height); // Задаем размеры Canvas
		frame.add(this); // Добавляем Canvas на фрейм
		frame.setVisible(true); // Делаем фрейм видимым
		
		this.setIgnoreRepaint(true);
		frame.pack();
		frame.setResizable(false); // Не позволяем изменять размер фрейма
		this.createBufferStrategy(2); // Включаем двойную буфферизацию
		strategy = this.getBufferStrategy();
		// Регистрируем обработчик
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {
				int button = arg0.getButton(); // Получаем нажатую клавишу
				if (button == 1) {
					drawArea = true;
					x1 = arg0.getX();
					y1 = arg0.getY();
					x2 = x1; // Устраняем "начало" из 0.0
					y2 = y1; // Устраняем "начало" из 0.0
				} else {
					mainLoop = false; // Выходим из режима выделения (Отменено пользователем)
					frame.dispose(); // Закрываем фрейм
				}
				
			}
			public void mouseReleased(MouseEvent arg0) {
				int button = arg0.getButton(); // Получаем нажатую клавишу
				if (button == 1) {
					drawArea = false;
					cutImage = cutArea(); // Вырезаем область
					mainLoop = false; // Останавливаем работу цикла
					frame.dispose(); // Закрываем фрейм
				} else {
					mainLoop = false; // Выходим из режима выделения (Отменено пользователем)
					frame.dispose(); // Закрываем фрейм
				}
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				x2 = e.getX();
				y2 = e.getY();
			}
			public void mouseMoved(MouseEvent e) {}
		});
		// Запускаем цикл
		while (mainLoop) {
			drawLoop();
			try {Thread.sleep(10);} catch (InterruptedException e1) {}
		}
	}

	private void drawLoop() {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.drawImage(image, 0, 0, null); // Зарисовываем буфер экрана на весь фрейм

		// Рисуем прямоугольник, определяющий область скриншшота
		if (drawArea) {
			for (int i = x1; i < x2; i+=4)
				g.drawLine(i, y1, i+2, y1);
			for (int i = x1; i > x2; i-=4)
				g.drawLine(i, y1, i-2, y1);
			
			for (int i = y1; i < y2; i+=4)
				g.drawLine(x2, i, x2, i+2);
			for (int i = y1; i > y2; i-=4)
				g.drawLine(x2, i, x2, i-2);
			
			for (int i = x2; i < x1; i+=4)
				g.drawLine(i, y2, i+2, y2);
			for (int i = x2; i > x1; i-=4)
				g.drawLine(i, y2, i-2, y2);

			for (int i = y1; i < y2; i+=4)
				g.drawLine(x1, i, x1, i+2);
			for (int i = y1; i > y2; i-=4)
				g.drawLine(x1, i, x1, i-2);
		}
		
		// Отрисовываем на Canvas'е
		g.dispose();
		strategy.show();
	}

	private BufferedImage cutArea() {
		// Получаем правильные координаты и размеры
		int nx, ny, nw, nh;
		if (x1 < x2) {
			nx = x1;
			nw = x2-x1;
		} else {
			nx = x2;
			nw = x1-x2;
		}
		if (y1 < y2) {
			ny = y1;
			nh = y2-y1;
		} else {
			ny = y2;
			nh = y1-y2;
		}
		// Вырезаем нужную часть изображения
		return image.getSubimage(nx, ny, nw, nh);
	}
	
	public BufferedImage getCutImage() {
		this.create();
		return cutImage;
	}
}
