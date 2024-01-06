
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    Image pic;
    Menu() {
        JFrame frame = new JFrame("More or less, less is more");
        frame.setLocation(100,200);
        frame.setSize(150,350);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        JLabel gameName = new JLabel("More or less, less is more");
        gameName.setFont(new Font("",Font.ITALIC, 10));
        gameName.setBounds(5, 43, 150, 30);
        frame.add(gameName);

        JLabel size = new JLabel("Set field size:");
        size.setBounds(10, 80, 100, 20);
        frame.add(size);

        JTextField Input1 = new JTextField();
        Input1.setBounds(10, 100, 50, 30);
        frame.add(Input1);

        JTextField Input2 = new JTextField();
        Input2.setBounds(70, 100, 50, 30);
        frame.add(Input2);

        JLabel move = new JLabel("Set number of moves:");
        move.setBounds(10, 130, 250, 20);
        frame.add(move);

        JTextField num_mov = new JTextField();
        num_mov.setBounds(10, 150, 50, 30);
        frame.add(num_mov);

        JLabel target = new JLabel("Set target value:");
        target.setBounds(10, 180, 250, 20);
        frame.add(target);

        JTextField target_val = new JTextField();
        target_val.setBounds(10, 200, 50, 30);
        frame.add(target_val);

        JButton start = new JButton("Start");
        start.setBounds(20,240,100,30);
        start.setBackground(new Color(0xF5F5F5));
        frame.add(start);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = Integer.parseInt(Input1.getText());
                int m = Integer.parseInt(Input2.getText());
                int move = Integer.parseInt(num_mov.getText());
                int target = Integer.parseInt(target_val.getText());
                int score = 0;
                int amove = move;
                frame.dispose();
                Engine game = new Engine(n, m, move, target, score, amove);
            }
        });
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
