package snakegame;

import java.sql.*;

public class DBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DBConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://greeny.cs.tlu.ee/if17_kodakevi?useSSL=false", "if17", "if17");
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void getData() {
        try {
            String query = "SELECT * FROM snakescores";
            //st = con.createStatement();
            rs = st.executeQuery(query);
            System.out.println("Records from Databse");
            while (rs.next()) {
                String name = rs.getString("username");
                String score = rs.getString("score");
                System.out.println("Name: " + name + "   " + "Score: " + score);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }
    
    public void addData(String username, int score) {
        try {
            String query = "INSERT INTO snakescores (username, score) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            st.setInt(2, score);
            st.execute();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }
}
