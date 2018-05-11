

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowLogin extends StandardWindow
{
    static String nomeUtente;
    static boolean activeFlag = true;
    private JPanel MainPanel, PswPanel, UsrPanel, buttonsPanel;
    private JTextField nomeUtenteArea;
    private JPasswordField passwordArea;
    private JLabel nomeUtenteLabel;
    private JLabel passwordLabel;
    private JButton accedi, registrati;
    private BorderLayout MainLayout;
    private JOptionPane error;
    public WindowLogin(String titolo, int larghezza, int altezza)
    {
        super(titolo, larghezza, altezza);
        setMaximumSize(new Dimension(larghezza, altezza));
        setMinimumSize(new Dimension(larghezza, altezza));
        setResizable(false);

        MainPanel = new JPanel();
        //MainLayout = new BoxLayout(MainPanel, BoxLayout.PAGE_AXIS);
        MainLayout = new BorderLayout();
        MainPanel.setPreferredSize(new Dimension(larghezza - 50, altezza -50));
        MainPanel.setLayout(MainLayout);

        UsrPanel = new JPanel();
        UsrPanel.setLayout(new FlowLayout());

        nomeUtenteLabel = new JLabel("Username");
        nomeUtenteLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nomeUtenteArea = new JTextField();
        nomeUtenteArea.setPreferredSize(new Dimension(250, 25));
        UsrPanel.add(nomeUtenteLabel);
        UsrPanel.add(nomeUtenteArea);

        passwordArea = new JPasswordField();
        passwordArea.setPreferredSize(new Dimension(250, 25));
        passwordArea.setEchoChar('*');

        PswPanel = new JPanel();
        PswPanel.setLayout(new FlowLayout());
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        PswPanel.add(passwordLabel);
        PswPanel.add(passwordArea);

        accedi = new JButton("Accedi");
        accedi.setPreferredSize(new Dimension(150, 45));
        accedi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loginButton();
            }
        });

        registrati = new JButton("Registrati");
        registrati.setPreferredSize(new Dimension(150, 45));
        registrati.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                registerButton();
            }
        });
        buttonsPanel = new JPanel();
        buttonsPanel.add(accedi);
        buttonsPanel.add(registrati);


        MainPanel.add(UsrPanel, BorderLayout.NORTH);
        MainPanel.add(PswPanel, BorderLayout.CENTER);
        MainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(MainPanel);
        pack();
    }

    private void loginButton()
    {
        nomeUtente = nomeUtenteArea.getText();
        if(!((nomeUtenteArea.getText().equals("")) || (new String(passwordArea.getPassword()).equals("")))) {

            byte loginResult = ClientMain.dbConnection.loginUser(nomeUtenteArea.getText(), new String(passwordArea.getPassword()));

            if (loginResult == 0) {
                ClientMain.invia(nomeUtenteArea.getText()); //Vedi se nome utente è disponibile e tutte cose qua la vit
                dispose();
                activeFlag = false;
            } else if (loginResult == 1) {
                JOptionPane.showMessageDialog(null, "Nome Utente non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
            } else if (loginResult == 2) {
                JOptionPane.showMessageDialog(null, "Password sbagliata!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Non sono permessi campi vuoti", "Errore", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void registerButton()
    {
        if(!((nomeUtenteArea.getText().equals("")) || (new String(passwordArea.getPassword()).equals("")))) {
            if (ClientMain.dbConnection.addUser(nomeUtenteArea.getText(), new String(passwordArea.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Registrazione effettuata!", "Registrazione", JOptionPane.INFORMATION_MESSAGE);
                loginButton();
            }
                else
                JOptionPane.showMessageDialog(null, "Utente già esistente, registrazione non effettuata!", "Errore", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Non sono permessi campi vuoti", "Errore", JOptionPane.WARNING_MESSAGE);
        }

    }
}