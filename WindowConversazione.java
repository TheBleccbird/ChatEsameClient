import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WindowConversazione extends StandardWindow
{
    static DefaultListModel<String> friendList;
    static JTextArea actualConversazione;
    static ArrayList<ChattingUser> conversazioni = new ArrayList<>();
    static String actualUser = null;
    private int c;
    private JPanel panel;
    private JPanel south;
    private int actualUserIndex;
    private JButton invia;
    private JTextArea testo;
    private JScrollPane scrollConversazione;
    private JScrollPane scrollFriends;
    private BorderLayout layout;
    private JScrollPane scrollTesto;
    private JList<String> onlineFriends;
    private JLabel connectedAs;


    public WindowConversazione(String title, int x, int y, int larghezza, int altezza)
    {
        super(title, larghezza, altezza);

        panel = new JPanel();
        layout = new BorderLayout();
        layout.setHgap(5);
        layout.setVgap(5);

        panel.setLayout(layout);
        setResizable(false);
        connectedAs = new JLabel("Connesso come " + WindowLogin.nomeUtente);
        connectedAs.setFont(new Font("Arial", Font.PLAIN, 11));
        //TextArea della conversazione corrente
        actualConversazione = new JTextArea();
        actualConversazione.setEditable(false);
        actualConversazione.setFont(new Font("Arial", Font.PLAIN, 15));
        actualConversazione.setWrapStyleWord(true);
        actualConversazione.setLineWrap(true);

        scrollConversazione = new JScrollPane(actualConversazione);
        scrollConversazione.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JScrollBar sb = new JScrollBar();
        sb = scrollConversazione.getVerticalScrollBar();
        sb.setValue(sb.getMaximum());
        scrollConversazione.setVerticalScrollBar(sb);
        //TextArea del messaggio che si sta scrivendo
        testo = new JTextArea(4, 53);
        testo.setWrapStyleWord(true);
        testo.setLineWrap(true);
        //testo.setBorder(new LineBorder(Color.BLACK, 1, true));
        testo.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    sendButton();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    testo.setText(null);
            }
        });
        scrollTesto = new JScrollPane(testo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Bottone di invio del messaggio
        invia = new JButton("Invia");
        invia.setPreferredSize(new Dimension(128, 76));

        invia.addActionListener(new ActionListener()
        {
            // L'invio dello 0 corrisponde ad inviare un messaggio a quell'utente
            public void actionPerformed(ActionEvent e)
            {
                sendButton();
            }
        });

        //Lista delle persone online
        friendList = new DefaultListModel<String>();

        Ricevi.list = new DefaultListModel<>();

        onlineFriends = new JList<>(Ricevi.list);
        scrollFriends = new JScrollPane(onlineFriends);
        scrollFriends.setPreferredSize(new Dimension(130, 480));
        onlineFriends.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me)
            {
                actualUser = onlineFriends.getSelectedValue();
                actualUserIndex = findUser(actualUser);
                actualConversazione.setText(conversazioni.get(actualUserIndex).getConversazione());
                setTitle("Chat con " + actualUser);
                connectedAs.setText("Connesso come " + WindowLogin.nomeUtente + "                               Chat con " + conversazioni.get(actualUserIndex).getNome());
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        //JPanel contenente il messaggio che si sta scrivendo e il bottone di invio
        south = new JPanel();
        south.setLayout(new FlowLayout());
        south.add(scrollTesto);
        south.add(invia);

        //Aggiungo tutti i componenti al panel principale
        panel.setBorder((BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        panel.add(connectedAs, BorderLayout.NORTH);
        panel.add(scrollConversazione, BorderLayout.CENTER);
        panel.add(scrollFriends, BorderLayout.WEST);
        panel.add(south, BorderLayout.SOUTH);

        add(panel);
        pack();

    }
    static int findUser(String user)
    {
        for (int c = 0; c < conversazioni.size(); c++)
        {
            if(conversazioni.get(c).getNome().equals(user))
                return c;
        }
        return -1;
    }
    static void addText(String text)
    {
        actualConversazione.append(text);
    }
    private void sendButton()
    {
        if(!(onlineFriends.isSelectionEmpty())){
            if(!(testo.getText().equals(""))) {
                conversazioni.get(actualUserIndex).appendText("Tu:" + testo.getText());
                if (!(actualConversazione.getText().equals("")))
                    actualConversazione.append("\nTu: " + testo.getText());
                else
                    actualConversazione.append("Tu: " + testo.getText());

                try {
                    ClientMain.invia("0" + "@#-@" + actualUser + "@#-@" + testo.getText());
                } catch (Exception ex) {
                }

                testo.setText(null);
            }
        }
    }
    public static void addUser(String newUser)
    {
        friendList.addElement(newUser);
    }

    public String getText()
    {
        return testo.getText();
    }
}