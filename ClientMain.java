import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;

class ClientMain
{
    //NON DIMENTICARE MAI PIU LO /N ALLA FINE DI UN INVIO GRAZIE PREGO
    static PrintWriter streamInvio;
    static BufferedReader streamRicezione;
    static DbConnection dbConnection;

    public static void main(String argv[]) throws Exception
    {
        Socket clientSocket;
        String ipServer = "82.48.166.148";
        //socket port = 2555
        //dbPort = 1556
        clientSocket = new Socket(ipServer, 2555);
        dbConnection = new DbConnection(ipServer, 1556);

        //RicezioneLista ricezioneLista = new RicezioneLista();

        streamInvio = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"), true);
        streamRicezione = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        Ricevi ricezione = new Ricevi(streamRicezione);

        WindowLogin login = new WindowLogin("Login", 450, 170);

        login.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    streamInvio.println("CLOSE@#-@bye");
                    clientSocket.close();
                    login.dispose();
                } catch (IOException e1) {
                    e1.printStackTrace();}
            }
        });
        //Ciao
        while(login.activeFlag)
        {
        }
        WindowConversazione conversazione = new WindowConversazione("Chat", 0, 0, 600, 650);
        conversazione.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    streamInvio.println("CLOSE@#-@bye");
                    ricezione.stop();
                    clientSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();}
            }
        });

        ricezione.start();

    }


    public static void invia(String sentence)
    {
        streamInvio.println(sentence);
    }

    public static String ricevi()
    {
        String sentence = null;
        try {
            sentence =  streamRicezione.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sentence;
    }
}