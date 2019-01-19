package khung;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class GameManager {
    static final int NUM_OF_ZOMBIE = 20;
    Player player;
    ArrayList<Zombie> arrZombie;
    ArrayList<Bullet> arrBullet;
    int score;
    void InitGame () {
        score = 0;
        arrBullet = new ArrayList<Bullet>();
        player = new Player(MyFrame.W_FRAME/2, MyFrame.H_FRAME/2);
        arrZombie = new ArrayList<>();
        InitZombies();
    }

    void draw (Graphics2D graphics2D) {
        for (Bullet b: arrBullet) {
            b.draw(graphics2D);
        }
        player.draw(graphics2D);
        for (Zombie z: arrZombie) {
            z.draw(graphics2D);

        }
        graphics2D.setFont(new Font(null, Font.BOLD,30));
        graphics2D.setColor(Color.RED);
        graphics2D.drawString(score + "",40,40);
    }

    void InitZombies () {
        for (int i=0; i<NUM_OF_ZOMBIE; i++) {
            arrZombie.add(new Zombie());
        }
    }

    boolean AI () {
        for (int i = arrZombie.size() - 1; i >= 0; i --) {
            arrZombie.get(i).move(player);
            boolean check = arrZombie.get(i).checkBullet(arrBullet);
            if (check == true) {
                arrZombie.remove(i);
                score += 10;
                if (arrZombie.size() == 0) {
                    InitZombies();
                }
            }


        }

        for (int i = arrBullet.size() - 1; i >= 0; i--) {
            boolean check = arrBullet.get(i).move();
            if (check == false) arrBullet.remove(i);
        }

        return player.checkDie(arrZombie);
    }

    static void playSound (String name) {
        try {
            Clip clip = AudioSystem.getClip();
            File file = new File("src/" + name);
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            clip.open(stream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
