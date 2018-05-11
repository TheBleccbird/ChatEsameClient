import java.sql.*;

public class DbConnection
{
    private Connection con;
    private Statement st;
    private ResultSet result;

    public DbConnection(String host, int port)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://" + host + ":"+port+"/chat?autoReconnect=true&useSSL=false", "root", "admin");
            st = con.createStatement();
        }catch(Exception ex)
        {
            System.out.println(ex.getStackTrace());
        }

    }

    //Registrazione nuovo utente
    //true registrazione effettuata
    //false non effettuata
        //EHYYYY

    public boolean addUser(String user, String password)
    {
        String insertQuery = "INSERT INTO utenti(Nickname, Password) values (\"" + user + "\", \"" + password + "\");" ;
        try {

             st.executeUpdate(insertQuery);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062);
                return false;
        }

        return true;
    }

    //0 = Accesso effettuato
    //1 = Utente non esistente
    //2 = Password sbagliata
    public byte loginUser(String user, String password)
    {
        String loginQuery = "SELECT Nickname, password FROM utenti WHERE Nickname = \"" + user + "\";";

        try {
            result = st.executeQuery(loginQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(!(result.next()))
            {
                //nome utente non esistente
                return 1;
            }
            else
            {
                if (result.getString("password").equals(password)) {
                    return 0; //login effettuato
                }
                else
                {
                    return 2; //password sbagliata
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }


    /*    public static void main(String args[]) throws SQLException {
        DbConnection con = new DbConnection();
       // con.addUser("peppo", "napoliNeccuore");
        System.out.println(con.loginUser("Adfdldo", "aaa"));

    }*/

}
