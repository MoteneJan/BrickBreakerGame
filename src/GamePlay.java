import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
   private boolean play = false;
   private int score = 0;
   private int totalBricks = 21;
   private Timer timer;
   private int delay = 0;
   private int playerX = 310;

   private int ballposX = 120;
   private int ballposY = 350;
   private int ballXdir = -1;
   private int ballYdir = -2;

   private MapGenerator map;
   public GamePlay() {
       map = new MapGenerator(3,7);
       addKeyListener(this);
       setFocusable(true);
       setFocusTraversalKeysEnabled(false);
       timer = new Timer(delay, this);
       timer.start();
   }

    public void startGame()
    {
        timer = new Timer(delay,this);
        timer.start();
    }
    public void paint(Graphics g)
    {
        //BACKGROUND
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //DRAWING MAP
        map.draw((Graphics2D)g);

        //BOARDERS
        g.setColor(Color.GREEN);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //PADDLE
        g.setColor(Color.RED);
        g.fillRect(playerX, 550,100,8);
        
        //SCORE
        g.setColor(Color.GREEN);
        g.setFont(new Font("serif", Font.BOLD,28));
        g.drawString("Score : " + score, 565,30);

        //BALL
        g.setColor(Color.GREEN);
        g.fillOval(ballposX, ballposY, 25, 25);

        if(totalBricks <= 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("You Won!: ",260,300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString("Press Enter To Restart!",230,350);
        }

        if(ballposY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Game Over : " + score + " Points" ,190,300 );

            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString("Press Enter To Restart!",230,350);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       timer.start();
       if(play){
           if(new Rectangle(ballposX, ballposY, 20,20).intersects(new Rectangle(playerX,550,100,8))){
               ballYdir = -ballYdir;
           }

           A: for(int x = 0; x < map.map.length; x++) {
               for (int y = 0; y < map.map[0].length; y++) {
                    if (map.map[x][y] > 0){
                        int brickX = y * map.brickWidth  + 80;
                        int brickY = x * map.brickHeight + 50;
                        int brickwidth = map.brickWidth;
                        int brickheight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
                        Rectangle ballRect = new Rectangle(ballposY, ballposX, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,x,y);
                            totalBricks--;
                            score += 5;

                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }
                            else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }

               }
           }

           ballposX += ballXdir;
           ballposY += ballYdir;
           if(ballposX < 0){
               ballXdir = -ballXdir;
           }
           if(ballposY < 0){
               ballYdir = -ballYdir;
           }
           if(ballposX > 670){
               ballXdir = -ballXdir;
           }
           repaint();
       }
       repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_RIGHT)
       {
           if(playerX >= 600)
           {
               playerX = 600;
           }
           else {
               moveRight();
           }
       }
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(playerX < 10)
            {
                playerX = 10;
            }
            else {
                moveLeft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }

    }

    private void moveLeft() {
        play = true;
        playerX -= 20;
    }

    private void moveRight() {
       play = true;
       playerX += 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
