import javax.swing.*;
import java.io.*;

public class Ricevi extends Thread
{

    static DefaultListModel<String> list;
    private BufferedReader inFromServer;
    private String[] data;
    private String text;
    private int c;
    private boolean flag = true;

    public Ricevi(BufferedReader inFromServer)
    {
        this.inFromServer = inFromServer;
    }

    public void run()
    {
        // 0 = Nuovo Utente nella lista
        // 1 = Messaggio ricevuto
        // 2 = Utente da Rimuovere dalla lista

        while(flag)
        {
            try
            {
                text = inFromServer.readLine();
            } catch (IOException ex) {}

            System.out.println(text);
            data = text.split("@#-@");
            if (data[0].equals("0"))
            {
                if(!(WindowLogin.nomeUtente.equals(data[1]))) {
                    list.addElement(data[1]);
                    WindowConversazione.conversazioni.add(new ChattingUser(data[1]));
                }
            }
            else if (data[0].equals("1"))
            {
                WindowConversazione.conversazioni.get(WindowConversazione.findUser(data[2])).appendText(data[2] + ": " + data[1]);
                if(data[2].equals(WindowConversazione.actualUser))
                    WindowConversazione.addText("\n" + data[2] + ": " + data[1]);
                //WindowConversazione.addText(data[2] + ": " + data[1]);
            }
            else if(data[0].equals("2"))
            {
                for (int c = 0; c < list.size(); c++)
                {
                    if(list.get(c).equals(data[1]))
                        list.remove(c);
                }
            }

        }

        this.interrupt();
    }

}
