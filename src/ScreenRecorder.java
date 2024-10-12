import org.jcodec.api.SequenceEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScreenRecorder extends Thread{
    public AtomicBoolean isInitiated;
    public AtomicBoolean isTerminated;
    int FRAME_RATE = 30;
    AWTSequenceEncoder enc;
    Robot robot;
    String format = "jpg";
    Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    String name;
    void Initiate() throws IOException, AWTException {

        name = new SimpleDateFormat("MMddyyyyhhmmss").format(new Date());
        enc = new AWTSequenceEncoder(new File(new StringBuilder().append("").append(name).append(".mp4").toString()));
        robot = new Robot();
        isInitiated =new AtomicBoolean(true);
        isTerminated =new AtomicBoolean(false);
    }
    void Terminate(){
        isInitiated.set(false);
        isTerminated.set(true);
      try {
          enc.finish();
      } catch (IOException e) {
          e.printStackTrace();
      }
      System.out.println("file created");

    }
      @Override
    public void run(){
        while (isInitiated.get() && !isTerminated.get()) {
            try {
                String filename = "Screenshot" + "." + format;
            BufferedImage img = robot.createScreenCapture(screenSize);
                enc.encodeImage(img);

                TimeUnit.SECONDS.sleep(1000/FRAME_RATE);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

    }
}


