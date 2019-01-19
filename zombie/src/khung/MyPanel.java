package khung;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyPanel extends JPanel {
    boolean[] flag = new boolean[256];// 256 phim va to hop phim
    boolean fire = false;
    GameManager gameManager = new GameManager();

    Image imageBack = new ImageIcon(
            getClass().getResource("/background.png")
    ).getImage();
    Image imageCursor = new ImageIcon(
            getClass().getResource("/crosshair.png")
    ).getImage();

    public MyPanel() {

        // Change default cursor to image cursor
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(imageCursor,new Point (0,0),"Cursor"));
        gameManager.InitGame();

        addMouseMotionListener(mouseMotionListener);
        setFocusable(true);
        addKeyListener(keyListener);
        addMouseListener(adapter);
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        super.paintComponent(graphics2D);
        graphics2D.drawImage(imageBack,0,0,MyFrame.W_FRAME,MyFrame.H_FRAME,null);

        gameManager.draw(graphics2D);


    }

    MouseMotionListener mouseMotionListener = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {
            mouseMoved(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            gameManager.player.ChangeAngle(e.getX(),e.getY());
            //repaint();
        }
    };

    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

//            gameManager.player.Move(e.getKeyCode());
//            repaint();
            flag[e.getKeyCode()] = true;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            flag[e.getKeyCode()] = false;
        }
    };
    MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if (e.getButton() == MouseEvent.BUTTON1) {
                //gameManager.player.fire(gameManager.arrBullet);
                fire = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            if (e.getButton() == MouseEvent.BUTTON1) {
                fire = false;
            }
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (flag[KeyEvent.VK_LEFT] == true) {
                    gameManager.player.Move(KeyEvent.VK_LEFT);
                }
                if (flag[KeyEvent.VK_RIGHT] == true) {
                    gameManager.player.Move(KeyEvent.VK_RIGHT);
                }
                if (flag[KeyEvent.VK_UP] == true) {
                    gameManager.player.Move(KeyEvent.VK_UP);
                }
                if (flag[KeyEvent.VK_DOWN] == true) {
                    gameManager.player.Move(KeyEvent.VK_DOWN);
                }

                if (fire == true) {
                    gameManager.player.fire(gameManager.arrBullet);
                }
                boolean isDie = gameManager.AI();
                if (isDie == true) {
                    int result = JOptionPane.showConfirmDialog(
                            null,
                            "Do you want to replay ?",
                            "Game Over",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (result == JOptionPane.YES_NO_OPTION) {
                        fire = false;
                        flag = new boolean[256];
                        gameManager.InitGame();
                    }
                    else System.exit(0);
                }
                repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
