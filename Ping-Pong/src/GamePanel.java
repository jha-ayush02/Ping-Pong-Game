import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

	static final int Game_Width = 1000;
	static final int Game_Height = (int) (Game_Width * (0.5555));
	static final Dimension Screen_Size = new Dimension(Game_Width, Game_Height);
	static final int Ball_Diameter = 20;
	static final int Paddle_Width = 25;
	static final int Paddle_Height = 100;

	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;

	GamePanel() {
		newPaddle();
		newBall();
		score = new Score(Game_Width, Game_Height);
		this.setFocusable(true);
		this.addKeyListener(new ActionListner());
		this.requestFocus();
		this.setPreferredSize(Screen_Size);

		gameThread = new Thread(this);
		gameThread.start();

	}

	public void newBall() {
		random = new Random();
		ball = new Ball((Game_Width / 2) - (Ball_Diameter / 2), random.nextInt(Game_Height - Ball_Diameter),
				Ball_Diameter, Ball_Diameter);

	}

	public void newPaddle() {
		paddle1 = new Paddle(0, (Game_Height / 2) - (Paddle_Height / 2), Paddle_Width, Paddle_Height, 1);
		paddle2 = new Paddle((Game_Width - Paddle_Width), (Game_Height / 2) - (Paddle_Height / 2), Paddle_Width,
				Paddle_Height, 2);

	}

	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
	}

	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
	}

	public void move() {
		paddle1.move();
		paddle2.move();
		ball.move();
	}

	public void checkCollision() {

		// Bounce the ball off top & bottom window edge
		if (ball.y <= 0) {
			ball.SetYDirection(-ball.yVelocity);
		}
		if (ball.y >= Game_Height - Ball_Diameter) {
			ball.SetYDirection(-ball.yVelocity);
		}

		// bounce ball off paddle
		if (ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; // Increase the speed of ball after collision with paddle
			if (ball.yVelocity > 0)
				ball.yVelocity++;
			else
				ball.yVelocity--;

			ball.SetXDirection(ball.xVelocity);
			ball.SetYDirection(ball.yVelocity);
		}

		if (ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; // Increase the speed of ball after collision with paddle
			if (ball.yVelocity > 0)
				ball.yVelocity++;
			else
				ball.yVelocity--;

			ball.SetXDirection(-ball.xVelocity);
			ball.SetYDirection(ball.yVelocity);
		}

		// stop paddle at window edge
		if (paddle1.y <= 0)
			paddle1.y = 0;
		if (paddle1.y >= (Game_Height - Paddle_Height))
			paddle1.y = Game_Height - Paddle_Height;
		if (paddle2.y <= 0)
			paddle2.y = 0;
		if (paddle2.y >= (Game_Height - Paddle_Height))
			paddle2.y = Game_Height - Paddle_Height;

		// Give a player 1 point and creates new paddle & ball
		if (ball.x <= 0) {
			score.player2++;
			newPaddle();
			newBall();
			System.out.println("Player2: " + score.player2);
		}

		if (ball.x >= Game_Width - Ball_Diameter) {
			score.player1++;
			newPaddle();
			newBall();
			System.out.println("Player2: " + score.player1);
		}
	}

	public void run() {
		// game loop
		long lastTime = System.nanoTime();
		double amountofTicks = 60.0;
		double ns = 1000000000 / amountofTicks;
		double delta = 0;
		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--;
				// System.out.println("Test");
			}
		}
	}

	public class ActionListner extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			paddle1.KeyPressed(e);
			paddle2.KeyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			paddle1.KeyReleased(e);
			paddle2.KeyReleased(e);
		}
	}

}
