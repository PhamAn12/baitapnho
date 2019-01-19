package khung;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MyFrame extends JFrame {
    static final int W_FRAME = 800;
    static final  int H_FRAME = 600;

    public MyFrame() throws HeadlessException {
        setTitle("Zombie");
        setSize(W_FRAME,H_FRAME);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(listener);
        MyPanel myPanel = new MyPanel();
        add(myPanel);
    }

   WindowListener listener = new WindowAdapter() {
       @Override
       public void windowClosing(WindowEvent e) {
           super.windowClosing(e);
           int result = JOptionPane.showConfirmDialog(
                   null,
                   "Do you want to exit",
                   "Zombie",
                   JOptionPane.YES_NO_OPTION,
                   JOptionPane.QUESTION_MESSAGE

           );
           if (result == JOptionPane.YES_OPTION) System.exit(0);
       }
   };

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();
        myFrame.setVisible(true);



    }

}
