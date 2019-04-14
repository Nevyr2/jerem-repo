import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener 
{
    private JPanel panel = new JPanel();
    private JPanel panelsud = new JPanel();
    Ball ball = new Ball();

    public MainFrame() 
    {
        this.setVisible(true);
        this.setTitle("Moving Balls");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(new BorderLayout());
        panelsud.setLayout(new BorderLayout());
        panel.add(panelsud, BorderLayout.SOUTH);
        panel.setBackground(Color.white);
        panelsud.setBackground(Color.white);
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }


            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == 32) {
                    panel.updateUI();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) 
            {
                int keyCode = e.getKeyCode();
                if (keyCode == 32) 
                {
                    if (ball.on_ground)
                    {
                        ball.on_jump = true;
                        ball.on_ground = false;
                        panel.updateUI();
                    }
                }
            }
        });

        this.setContentPane(panel);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);

        obstacle o1 = new obstacle(300,690,20);
        ball.list_obstacle.add(o1);
        obstacle o2 = new obstacle(500,670,40);
        ball.list_obstacle.add(o2);

        panel.add(ball, BorderLayout.CENTER);
        Thread thread = new Thread(ball);
        thread.start();
        panel.updateUI();

    }

    public void actionPerformed(ActionEvent arg0) 
    {
        panel.add(ball, BorderLayout.CENTER);
        Thread thread = new Thread(ball);
        thread.start();
        panel.updateUI();
    }

    public static void main(String[] args) 
    {
        MainFrame mainFrame = new MainFrame();
    }
}
