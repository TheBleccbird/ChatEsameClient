import javax.swing.*;
import java.awt.*;

public class ChattingUser
{
    private String conversazione;
    private String nome;

    public ChattingUser(String nome) {
        this.nome = nome;
        conversazione = new String("");
    }

    public void appendText(String text)
    {
        if(!(conversazione.equals("")))
            conversazione = conversazione + "\n" + text;
        else
            conversazione = conversazione + text;
    }

    public String getConversazione() {
        return conversazione;
    }

    public void setConversazione(String conversazione) {
        this.conversazione = conversazione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
