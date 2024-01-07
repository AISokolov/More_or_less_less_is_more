import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.Random;

import static java.lang.Math.abs;

public class Engine implements ActionListener {
    int n, m, target, move, score, amove, previous, current;
    int MaxNumberOnTheField = 0;
    boolean load;
    JFrame frame = new JFrame("More or less, less is more");
    JButton[][] b;
    JLabel l = new JLabel();
    JLabel mo = new JLabel();
    Random r = new Random();

    Engine(int n, int m, int move, int target, int score, int amove, boolean load, String[][] field) {
        this.n = n;
        this.m = m;
        this.target = target;
        this.move = move;
        this.score = score;
        this.amove = amove;
        this.load = load;
        this.previous = 1;
        this.current = 1;

        frame.setLocation(100, 200);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);
        l.setText("Score: " + score + ", Target value: " + target);
        frame.add(l, BorderLayout.NORTH);
        mo.setText("The number of moves: " + move);
        frame.add(mo, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem rst = new JMenuItem("Restart");
        JMenuItem save = new JMenuItem("Save");
        menu.add(rst);
        menu.add(save);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        rst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Menu m = new Menu();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser schooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                schooser.setFileFilter(filter);
                schooser.setDialogTitle("Save");
                schooser.setCurrentDirectory(new File("."));
                int result = schooser.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = schooser.getSelectedFile();
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        writer.write(n + "," + m + "," + target + "," + amove + "\n");
                        for (int i = 0; i < n; i++) {
                            for (int j = 0; j < m; j++) {
                                writer.write(b[i][j].getText() + ",");
                            }
                            writer.write('\n');
                        }
                        writer.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Game saved!", "Result", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JPanel panel = new JPanel();
        GridLayout gl = new GridLayout(n, m);
        panel.setLayout(gl);

        int randomNumber = 1;
        b = new JButton[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                randomNumber = r.nextInt(9) + 1;

                if (load) b[i][j] = new JButton(field[i][j]);
                else b[i][j] = new JButton(randomNumber + "");

                if (target == Integer.parseInt(b[i][j].getText())) b[i][j].setBackground(new Color(0x6BFD07));
                else b[i][j].setBackground(new Color(0xFFFFFF));
                MaxNumberOnTheField = Math.max(MaxNumberOnTheField, Integer.parseInt(b[i][j].getText()));
                b[i][j].addActionListener(this);
                panel.add(b[i][j]);
            }
        }
        if (target > 9) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (MaxNumberOnTheField == Integer.parseInt(b[i][j].getText()))
                        b[i][j].setBackground(new Color(0x6BFD07));
                }
            }
        }
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
    }

    public void actionPerformed(ActionEvent e) {
        JButton o = (JButton) e.getSource();
        int value = Integer.parseInt(o.getText());
        calc(value);
        Coloring(value);
        // o.setText(r.nextInt(9) + 1 +"");
        o.setEnabled(false);

    }

    public void Coloring(int value) {

        previous = current;
        current = value;

        for (int row = 0; row < n; row++) {
            for (int column = 0; column < m; column++) {
                b[row][column].setBackground(new Color(0xF5F5F5));
                b[row][column].setEnabled(false);

                if (row % current == 0 && column % previous == 0) {
                    b[row][column].setBackground(new Color(0x6BFD07));
                    b[row][column].setEnabled(true);
                } else if (row % current == 0 || column % previous == 0)
                    b[row][column].setBackground(new Color(0x78DA64));
            }
        }
    }

    public void calc(int value) {
        move--;
        mo.setText("The number of moves: " + move);
        score += value;
        l.setText("Score: " + score + ",Target value: " + target);
        if (move == 0) {
            int rem = abs(target - score);
            JOptionPane.showMessageDialog(null, "You lost !!!\nYou ran out of moves\nRemaining score: " + rem, "GAME OVER!", JOptionPane.WARNING_MESSAGE);
            frame.dispose();
            Menu m = new Menu();
        }
        if (score > target) {
            JOptionPane.showMessageDialog(null, "You lost !!!\nYour score is bigger than the target", "GAME OVER!", JOptionPane.WARNING_MESSAGE);
            frame.dispose();
            Menu m = new Menu();
        }
        if (score == target) {
            JOptionPane.showMessageDialog(null, "YOU WIN !!!\nCongratulations", "GAME OVER!", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            Menu m = new Menu();
        }
    }
}
