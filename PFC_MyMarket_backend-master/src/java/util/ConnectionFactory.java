package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

        public static Connection getConexao() {
            try {
                try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
                return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres","postgres");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
}
