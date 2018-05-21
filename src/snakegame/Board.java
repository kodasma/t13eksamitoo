package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Board extends JPanel implements ActionListener {
    
    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;
    
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    private int dots;
    private int apple_x;
    private int apple_y;
    
    private boolean leftDirection = false;
    private boolean rightDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    
    public Board() {
        getConnection("INSERT INTO snakescores VALUES (asd, 15)");
        initBoard();
    }
    
    private void initBoard() {
        
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setDoubleBuffered(true);
        
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }
    
    private void loadImages() {
        
        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();
        
        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();
        
        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    }
    
    private void initGame() {
        
        dots = 3;
        
        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * 10;
            y[i] = 50;
        }
        
        locateApple();
        
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        
        if (inGame) {
            
            g.drawImage(apple, apple_x, apple_y, this);
            
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(ball, x[i], y[i], this);
                }
            }
            
            Toolkit.getDefaultToolkit().sync();
        } else {
            
            gameOver(g);
        }
    }
    
    private void gameOver(Graphics g) {
        String msg = "Game Over";
        String msg2 = "Score: " + (dots - 3);
        String msg3 = "Press Enter to Restart";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        Font small2 = new Font("Helvetica", Font.BOLD, 12);
        FontMetrics metr = getFontMetrics(small);
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2 - 15);
        
        g.setColor(Color.white);
        g.setFont(small2);
        g.drawString(msg2, (B_WIDTH - metr.stringWidth(msg2)) / 2, B_HEIGHT / 2);
        
        g.setColor(Color.white);
        g.setFont(small2);
        g.drawString(msg3, (B_WIDTH - metr.stringWidth(msg3)) / 2 + 15, B_HEIGHT / 2 + 15);
    }
    
    private void checkApple() {
        
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            
            dots++;
            locateApple();
        }
    }
    
    private void move() {
        
        for (int i = dots; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }
        
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }
        
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }
                
        if (upDirection) {
            y[0] -= DOT_SIZE;
        }
        
        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }
    
    private void checkCollision() {
        
        for (int i = dots; i > 0; i--) {
            
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        
        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }
        
        if (y[0] < 0) {
            inGame = false;
        }
        
        if (x[0] >= B_WIDTH) {
            inGame = false;
        }
        
        if (x[0] < 0) {
            inGame = false;
        }
        
        if (!inGame) {
            timer.stop();
        }
    }
    
    private void locateApple() {
        
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));
        
        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (inGame) {
            
            checkApple();
            checkCollision();
            move();
        }
        
        repaint();
    }
    
    private class TAdapter extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            
            int key = e.getKeyCode();
            
            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                inGame = true;
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                inGame = true;
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                inGame = true;
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            
            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                inGame = true;
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            
            if ((key == KeyEvent.VK_ENTER)) {
                if (!inGame) {
                    leftDirection = false;
                    rightDirection = false;
                    upDirection = false;
                    downDirection = false;
                    inGame = true;
                    initBoard();
                }
            }
        }
    }
    
    /*public void askData(String name) {
        try (Scanner reader = new Scanner(System.in)) {
            System.out.println("Enter your username: ");
            name = reader.next();
        }
    }
    
    public void insertData(String name, int score) throws SQLException {
        Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/if17_kodakevi?user=if17&password=if17");
        PreparedStatement st = cn.prepareStatement("INSERT INTO snakescores VALUES(?, ?)");
        st.setString(1, name);
        st.setDouble(2, score);
        st.executeUpdate();
    }
    
    public void showData(String username2, int score2) throws SQLException {
        Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/if17_kodakevi?user=if17&password=if17");
        PreparedStatement st = cn.prepareStatement("SELECT username, score FROM snakescores");
        st.setString(1, username2);
        st.setDouble(2, score2);
        ResultSet rs = st.executeQuery();
        String nametext = " ";
        int scoretext = 0;
        while (rs.next()) {
            nametext += rs.getString("username2");
            scoretext += rs.getDouble("score2");
        }
    }*/
    
    public Connection getConnection(String query) {
        
        Connection con = null;
        Statement st = null;
        
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/if17_kodakevi?user=if17&password=if17");
            st = con.createStatement();
            st.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Connected");
            return con;
        } catch(HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return null;
        }
    }
}
