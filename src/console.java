import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class console extends Thread{
    private JButton start;
    private JButton stop;
    private JButton pause;
    private JPanel panel;
    ScreenRecorder sc = new ScreenRecorder();
    Thread t = new Thread(sc);
    public console() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    sc.Initiate();
                    t.start();
                } catch(IOException ex) {
                    ex.printStackTrace();
                } catch(AWTException ex) {
                    ex.printStackTrace();
                }
            };

        });
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sc.isInitiated.get()==true)
                    sc.isInitiated.set(false);
                else if (sc.isInitiated.get()==false)
                    sc.isInitiated.set(true);
            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sc.isTerminated.get()==false){
                    sc.Terminate();
                    t.stop();
                }

            }
        });
    }
    public static void main(String args[]){
        JFrame frame = new JFrame();
        frame.setContentPane(new console().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
