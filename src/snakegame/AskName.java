package SnakeGame;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AskName extends JFrame {
    
    public ArrayList<String> names = new ArrayList<>();
    
    JTextField textField = new JTextField(20);
    JButton button = new JButton("OK");
    
    public AskName() {
        super("Insert username");
        setLayout(new FlowLayout());
        
        textField.addActionListener((ActionEvent event) -> {
            setVisible(false);
            dispose();
            String content = textField.getText();
            names.add(content);
            EventQueue.invokeLater(() -> {
                JFrame ex = new snakegame.Snake();
                ex.setVisible(true);
            });
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                String content = textField.getText();
                if (!content.equals("")) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
        });

        button.addActionListener((ActionEvent event) -> {
            setVisible(false);
            dispose();
            String content = textField.getText();
            names.add(content);
            EventQueue.invokeLater(() -> {
                JFrame ex = new snakegame.Snake();
                ex.setVisible(true);
            });
        });

        add(textField);
        add(button);

        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            new AskName();
        });
    }
}
