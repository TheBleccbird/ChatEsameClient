import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class StandardWindow extends JFrame
{
    public StandardWindow(String title, int larghezza, int altezza)
    {
        super(title);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(size.width/3, size.height/3);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(larghezza, altezza));
        setMaximumSize(new Dimension(larghezza, altezza));
        setMinimumSize(new Dimension(larghezza, altezza));

        //setLayout(new BorderLayout());
        setVisible(true);
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }
    }

}
