package khung;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

public class Zombie {
     double x;
     double y;
     double angle;
     Image [] images = {
             new ImageIcon(getClass().getResource("/SLOW_ZOMBIE.gif")).getImage(),
             new ImageIcon(getClass().getResource("/FAST_ZOMBIE.gif")).getImage(),
             new ImageIcon(getClass().getResource("/zombie.gif")).getImage(),
             new ImageIcon(getClass().getResource("/boss1.png")).getImage(),
             new ImageIcon(getClass().getResource("/boss1.png")).getImage()
     };
     Random random = new Random();
     int type; // Loai zombie
     double rotationX, rotationY;

    public Zombie () {
        int r = random.nextInt(4);
        switch (r) {
            case 0:
                x = -100;
                y = random.nextInt(MyFrame.H_FRAME);
                break;
            case 1:
                y = -100;
                x = random.nextInt(MyFrame.W_FRAME);
                break;
            case 2:
                x = MyFrame.W_FRAME;
                y = random.nextInt(MyFrame.H_FRAME);
                break;
            case 3:
                y = MyFrame.H_FRAME;
                x = random.nextInt(MyFrame.W_FRAME);
                break;
        }

        type = random.nextInt(5);
    }


    void draw(Graphics2D graphics2D) {
        // trọng tâm của hình ảnh player la rotationX va rotationY
        rotationX = x + images[type].getWidth(null)/2;
        rotationY = y +  images[type].getHeight(null)/2;

        AffineTransform affineTransform =  new AffineTransform();
        affineTransform.rotate(0,0,0);

        graphics2D.rotate(angle,rotationX,rotationY);
        graphics2D.drawImage( images[type],(int)x,(int)y,null);
        graphics2D.setTransform(affineTransform);

    }

    void move (Player player) {
        angle = Math.atan2(rotationY - player.rotationY, rotationX - player.rotationX );

        x = x - Math.cos(angle);
        y = y - Math.sin(angle);
    }

    Rectangle getRect() {
        Rectangle rect = new Rectangle((int)x,(int)y,
                images[type].getWidth(null),images[type].getHeight(null));

        return rect;
    }

    boolean checkBullet(ArrayList<Bullet> arr) {
        for (int i = 0; i < arr.size(); i++) {
            Rectangle rect = getRect().intersection(arr.get(i).getRect());
            if (rect.isEmpty() == false) {
                arr.remove(i);
                GameManager.playSound("zombieDeath.wav");
                return true;
            }
        }
        return false;
    }
}
