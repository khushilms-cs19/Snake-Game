import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT= 600;
    static final int UNIT_SIZE=25;
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY= 100;
    final int x[]=new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];
    int bodyParts =6 ;
    int applesEaten=0;
    int applex;
    int appley;
    char direction='R';
    boolean running= false;
    Timer timer;
    Random random;

    GamePanel()
    {
        random =new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame()
    {
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        if(running)
        {
            // for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++)
            // {
            //     g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
            //     g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
            // }
            g.setColor(Color.RED);
            g.fillOval(applex,appley,UNIT_SIZE,UNIT_SIZE);
            for(int i=0;i<bodyParts;i++)
            {
                if(i == 0)
                {
                    g.setColor(new Color(29, 184, 99));
                    g.fillRoundRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE,10,10);
                }
                else{
                    g.setColor(new Color(66,245,147));
                    g.fillRoundRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE,10,10);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Calibri",Font.BOLD,20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Your Score: "+applesEaten,(SCREEN_WIDTH-metrics.stringWidth("Your Score: "+applesEaten))/2,g.getFont().getSize());
        }
        else
        {
            gameOver(g);
        }
        
    }
    public void newApple()
    {
        applex=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appley=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move()
    {
        for(int i=bodyParts;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction)
        {
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;            
        }
    }
    public void checkApple()
    {
        if((x[0]==applex)&&(y[0]==appley))
        {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions()
    {
        //Checks if the head collides with any of the body parts
        for(int i=bodyParts;i>0;i--)
        {
            if((x[0]==x[i]) && (y[0]==y[i]))
            {
                running=false;
            }
        }
        //Checks if the head collides with any of the edges or corners
        if(x[0]>SCREEN_WIDTH)//right
        {
            running=false;
        }
        if(x[0]<0)//left
        {
            running=false;
        }
        if(y[0]>SCREEN_HEIGHT)//bottom
        {
            running=false;
        }
        if(y[0]<0)//top
        {
            running=false;
        }
        if(!running)//stops after collision
        {
            timer.stop();
        }
    }
    public void gameOver(Graphics g)
    {
        g.setColor(Color.red);
        g.setFont(new Font("Calibri",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
        g.setColor(Color.red);
        g.setFont(new Font("Calibri",Font.BOLD,20));
        FontMetrics rmetrics = getFontMetrics(g.getFont());
        g.drawString("Your Score: "+applesEaten,(SCREEN_WIDTH-rmetrics.stringWidth("Your Score: "+applesEaten))/2,g.getFont().getSize());
    }
    public void actionPerformed(ActionEvent ae)
    {
        if(running)
        {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter
    {
        public void keyPressed(KeyEvent ke)
        {
            switch(ke.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R')
                    {
                        direction='L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if(direction!= 'L')
                    {
                        direction='R';
                    }
                    break;
                
                case KeyEvent.VK_UP:
                    if(direction!='D')
                    {
                        direction='U';
                    }    
                    break;
                
                case KeyEvent.VK_DOWN:
                    if(direction!='U')
                    {
                        direction='D';
                    }
                    break;        
            }
        }
    }
}
