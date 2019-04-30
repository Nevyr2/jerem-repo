import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.util.Random; 
import java.util.ArrayList;
import java.awt.Toolkit; 
import javax.swing.*;

public class Ball extends JPanel implements Runnable 
{
    public int x = 50;
    public int y = 650;
    public int width = 50;
    boolean on_jump = false;
    boolean on_ground = true;
    int ground =  650;
    int jump_ground = 0;
    boolean alive = true;
    boolean quit = false;
    float speed = 1;
    int score = 0;
    int lvl = 1;
    int next_lvl = 5;
    obstacle on_obstacle = null;
    ArrayList<obstacle> list_obstacle = new ArrayList<obstacle>();
    JPanel panel = new JPanel();

    public Ball(JPanel panel)
    {
        this.panel = panel;
    }
    @Override
    protected void paintComponent(Graphics g) 
    {

        super.paintComponent(g);
        
        //clear everything
        g.clearRect(0,0, 2000, 2000);
    
        // print ground and line
        g.setColor(Color.BLACK);
        g.drawLine(0,700,2500,700);
        g.drawLine(0,50,2500,50);
        g.drawLine(0,165,2500,165);
        
        //print Score
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString("SCORE: " + score,200,125);

        //print next_lvl
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString("Next_lvl: " + (score + next_lvl),600,150);

        //print level
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString("LEVEL : " + lvl,600,100);

        //print DEAD if you are
        if (!alive)
        {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial Black", Font.BOLD, 50));
            g.drawString("YOU'RE DEAD",300,400);

            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial Black", Font.BOLD, 20));
            g.drawString("Press Space to Try Again",50,750);
        }

        // print new position
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, width);
        for (int i = 0; i < list_obstacle.size(); i++)
        {
            g.setColor(Color.RED);
            obstacle o = list_obstacle.get(i);
            g.fillRect(o.posx, o.posy, o.width, o.width);
        }
    }

    public void update_score_lvl()
    {
        if (!alive)
        {
            score = 0;
            lvl = 1;
            next_lvl = 5;
        }

        if (next_lvl == 0)
        {
            lvl += 1;
            next_lvl = 5;
        }
    }

    public void move_player()
    {
        //on obstacle
        if (on_obstacle != null)
        {
            if ( (on_obstacle.posx + width) < x)
            {
                on_ground = false;
                ground += on_obstacle.width;
                on_obstacle = null;
            }
        }

        // if ball is jumping, go upward
        if (on_jump)
            y -= 2*speed;

        // if ball finish jump, have to go downward until touch ground
        if (!on_ground && !on_jump)
            y += 2*speed;

        // touch ground
        if (y >= ground)
            on_ground = true;
        // finish jump
        else if (y < jump_ground - 180)
            on_jump = false;

    }

    public void move_all_obstacles()
    {
        //check if ball jump one of the obstacle
        for (int i = 0; i < list_obstacle.size(); i++)
        {
            obstacle o = list_obstacle.get(i);

            if ( Math.abs(y + width - o.posy) <= 1
                    && o.posx + o.width - x <= width + o.width
                    && !on_ground && !on_jump)
            {
                on_ground = true;
                on_jump = false;
                ground -= o.width;
                on_obstacle = o;
            }
            else
            {
                //check if alive
                o.touch_obstacle(this);
            }
            //move obstacle
            o.move(speed);
        }
    }
    public void maj_obstacle()
    {
        //delete obstacle passed
        for (int i = 0; i < list_obstacle.size(); i++)
        {
            obstacle o = list_obstacle.get(i);
            if (o.posx < -50)
            {
                list_obstacle.remove(i);
                score += 1;
                next_lvl -= 1;
            }
        }

        //delete obstacle when too close
        for (int i = 0; i < list_obstacle.size(); i++)
        {
            obstacle o1 = list_obstacle.get(i);
            for (int j = 0; j < list_obstacle.size(); j++)
            {
                obstacle o2 = list_obstacle.get(j);

                if (i == j)
                    continue;

                if (Math.abs(o1.posx - o2.posx) > 20
                        && Math.abs(o1.posx - o2.posx) < 60
                        && o1.posy == o2.posy)
                    list_obstacle.remove(j);
            }
        }
        //add obstacle
        while (list_obstacle.size() < (5 + lvl))
        {
            int width = (int)(Math.random() * 20) + 10;
            obstacle new_o = new obstacle(1000 + (int)(Math.random() * 3000),
                    700 - width - ((int)(Math.random() * 2) * 60), width);
            list_obstacle.add(new_o);
        }

    }
    public void run() 
    {
        System.out.println("Début de la méthode run");
        while (!quit) 
        {
            Toolkit.getDefaultToolkit().sync();
            if (alive)
            {
                move_player();

                move_all_obstacles();

                maj_obstacle();

                update_score_lvl();

                // call paintComponent to print everything
                this.repaint();

                try 
                {
                    // sleep
                    Thread.sleep(3);
                } 
                catch (InterruptedException e) 
                {
                    e.printStackTrace();
                }
            }

        }
    }
}

