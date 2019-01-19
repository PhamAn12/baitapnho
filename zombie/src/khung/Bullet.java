package khung;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Bullet {
    double x,y;
    double angle;
    double rotationX,rotationY;
    Image img = new ImageIcon(getClass().getResource("/bullet.png")).getImage();

    public Bullet(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    void draw(Graphics2D graphics2D) {
        // trọng tâm của hình ảnh player
        rotationX = x + img.getWidth(null) / 2;
        rotationY = y + img.getHeight(null) / 2;

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(0, 0, 0);

        graphics2D.rotate(angle, rotationX, rotationY);
        graphics2D.drawImage(img, (int) x, (int) y, null);
        graphics2D.setTransform(affineTransform);

    }

    boolean move () {
        x = x - 20 * Math.cos(angle);
        y = y - 20 * Math.sin(angle);
        if (x<img.getWidth(null) || x>= MyFrame.W_FRAME ) {
            return false;
        }

        if (y<img.getHeight(null)|| y>= MyFrame.H_FRAME ) {
            return false;
        }
        return true;
    }
    Rectangle getRect() {
        Rectangle rect = new Rectangle((int)x,(int)y,
                img.getWidth(null),img.getHeight(null));

        return rect;
    }


}
