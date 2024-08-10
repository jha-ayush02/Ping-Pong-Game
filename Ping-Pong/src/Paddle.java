import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Paddle extends Rectangle {

	int id;
	int yVelocity;
	int speed = 10;
	
	Paddle(int x, int y, int Paddle_Width, int Paddle_Height, int id){
		super(x,y,Paddle_Width,Paddle_Height);
		this.id= id;
	}
	
	public void KeyPressed(KeyEvent e) {
			switch (id) {
			case 1:
				if(e.getKeyCode()==KeyEvent.VK_W) {
					setYDirection(-speed);
					move();
				}
				if(e.getKeyCode() == KeyEvent.VK_S) {
					setYDirection(speed);
					move();
				}
				break;
			case 2:
				if(e.getKeyCode()==KeyEvent.VK_UP) {
					setYDirection(-speed);
					move();
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					setYDirection(speed);
					move();
				}
				break;
			}
	}
	
	public void KeyReleased(KeyEvent e) {
		switch (id) {
		case 1:
			if(e.getKeyCode()==KeyEvent.VK_W) {
				setYDirection(0);
				move();
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
				setYDirection(0);
				move();
			}
			break;
		case 2:
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				setYDirection(0);
				move();
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				setYDirection(0);
				move();
			}
			break;
		}
	}
	
	public void setYDirection(int yDirection) {
		yVelocity = yDirection;
	}
	
	public void move() {
		y = y + yVelocity;
	}
	
	public void draw(Graphics g) {
		if(id==1) 
			g.setColor(Color.yellow);
		else
			g.setColor(Color.green);
		g.fillRect(x, y, width, height);
	}
}
