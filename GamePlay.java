package brickbreak;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePlay extends JPanel implements KeyListener,ActionListener{
	
		private boolean play=false;
		private int score=0;
		
		private int totalBricks=21;		
		private Timer time;
		private int delay=8;
		
		private int playerX=310;
		
		private int ballposX=120;
		private int ballposY=350;
		private int balldirX=-1;
		private int balldirY=-2;
		
		private MapGenerator map;
		
		public GamePlay() {
			map=new MapGenerator(3,7);
			addKeyListener(this);
			setFocusable(true);
			setFocusTraversalKeysEnabled(false);
			time=new Timer(delay,this);
			time.start();
		}
		public void paint(Graphics g) {
			//backgound
			g.setColor(Color.BLACK);
			g.fillRect(1, 1, 692, 592);
			
			//drawing map
			
			map.draw((Graphics2D)g);
			
			//borders
			g.setColor(Color.YELLOW);
			g.fillRect(0, 0, 3, 592);
			g.fillRect(0, 0, 692,3);
			g.fillRect(0, 0, 3, 592);
			
			//scores
			g.setColor(Color.white);
			g.setFont(new Font(Font.SERIF,Font.BOLD,25));
			g.drawString(""+score, 590, 30);
			
			//paddle
			g.setColor(Color.GREEN);
			g.fillRect(playerX, 550, 100, 8);
			
			//ball
			g.setColor(Color.YELLOW);
			g.fillOval(ballposX,ballposY,20,20);
			
			if(totalBricks<=0) {
				play=false;
				balldirX=0;
				balldirY=0;
				g.setColor(Color.RED);
				g.setFont(new Font(Font.SERIF,Font.BOLD,30));
				g.drawString("You Won!! ", 290, 300);
				
				g.setFont(new Font(Font.SERIF,Font.BOLD,25));
				g.drawString("Press Enter to restart", 210, 350);
				
				
			}
			
			if(ballposY>570) {
				play=false;
				balldirX=0;
				balldirY=0;
				g.setColor(Color.RED);
				g.setFont(new Font(Font.SERIF,Font.BOLD,30));
				g.drawString("Game Over, Score:"+score, 190, 300);
				
				g.setFont(new Font(Font.SERIF,Font.BOLD,25));
				g.drawString("Press Enter to restart", 210, 350);
				
				
			}
			
			g.dispose();
		}
						
	public void actionPerformed(ActionEvent arg0) {
		time.start();
		if(play==true) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
				balldirY=-balldirY;
			}
			
			A:for(int i=0;i<map.map.length;i++)
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX=j*map.brickwidth+80;
						int brickY=i*map.brickheight+50;
						int brickwidth=map.brickwidth;
						int brickheight=map.brickheight;
						
						Rectangle rect=new Rectangle(brickX,brickY,brickwidth,brickheight);
						Rectangle ballrect=new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickrect=rect;
						
						if(ballrect.intersects(brickrect)) {
							map.setBrickvalue(0,i, j);
							totalBricks--;
							score+=5;
							
							if(ballposX+19<=brickrect.x || ballposX+1>=brickrect.x+brickrect.width) 
								balldirX=-balldirX;
	
							
							else
								balldirY=-balldirY;
							break A;
							
							
						}		
						
					}
				}
			
			ballposX+=balldirX;
			ballposY+=balldirY;
			if(ballposX<0) {
				balldirX=-balldirX;
			}
			if(ballposY<0) {
				balldirY=-balldirY;
			}
			if(ballposX>670) {
				balldirX=-balldirX;
			}
		}
		repaint();
		
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!play)
				play=true;
			ballposX=120;
			ballposY=250;
			balldirX=-1;
			balldirY=-2;
			score=0;
			playerX=310;
			totalBricks=21;
			map=new MapGenerator(3, 7);
			repaint();
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerX>600) 
				playerX=600;
			
			else
				moveRight();
			
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(playerX<10)
				playerX=10;
			else
				moveLeft();
				
		}		
	}
	public void moveRight() {
		play=true;
		playerX+=20;
		
	}
		
	
	public void moveLeft() {
		play=true;
		playerX-=20;
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
}
		
		
	


