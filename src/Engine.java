

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.Random;

import static java.lang.Math.abs;

public class Engine {
    int n, m, target, move, score, curI, curJ, prevI, prevJ, amove;
    JFrame frame = new JFrame("More or less, less is more");
    JButton[][] b;
    JLabel l = new JLabel();
    JLabel mo = new JLabel();
    int[][] ctr;
    String temp;
    Random r = new Random();
    Engine(int n, int m, int move, int target,int score, int amove){
        this.n = n;
        this.m = m;
        this.target = target;
        this.move = move;
        this.score = score;
        this.amove = amove;

        frame.setLocation(100,200);
        frame.setSize(400,500);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);
        l.setText("Score: " + score + ", Target value: " + target);
        frame.add(l,BorderLayout.NORTH);
        mo.setText("The number of moves: " + move);
        frame.add(mo,BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem rst = new JMenuItem("Restart");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        menu.add(rst);
        menu.add(save);
        menu.add(load);
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
                        writer.write("Score: " + score + " Moves: " + move + "\n");
                        for (int i = 0; i<n;i++) {
                            for (int j = 0; j < m; j++) {
                                if (j % m == 0){
                                    writer.write("\n");
                                }
                                writer.write(b[i][j].getText() + " ");
                            }
                        }
                        writer.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Game saved!", "Result", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] field = new int[n][m];
                JFileChooser lchooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                lchooser.setFileFilter(filter);
                lchooser.setCurrentDirectory(new File("."));
                lchooser.setDialogTitle("Load");
                int res = lchooser.showSaveDialog(null);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File file = lchooser.getSelectedFile();
                    String filePath = file.getAbsolutePath();
                    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                        String line;
                        int row = 0;
                        while ((line = br.readLine()) != null && row < m) {
                            String[] values = line.trim().split("\\s+");
                            for (int col = 0; col < n; col++) {
                                if (!values[col].isEmpty()) {
                                    field[row][col] = Integer.parseInt(values[col]);
                                }
                            }
                            row++;
                        }
                    } catch (IOException | NumberFormatException t) {
                        t.printStackTrace();
                    }
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < m; j++) {
                            b[i][j].setText(field[i][j] + "");
                            b[i][j].setBackground(new Color(0xFFFFFF));
                        }
                    }
                }
            }
        });

        JPanel panel = new JPanel();
        GridLayout gl = new GridLayout(n,m);
        panel.setLayout(gl);

        ctr = new int[n][m];
        b = new JButton[n][m];
        for (int i = 0; i<n;i++){
            for (int j=0; j<m;j++){
                ctr[i][j] = r.nextInt(1,10);
                b[i][j] = new JButton(ctr[i][j]+"");
                b[i][j].setBackground(new Color(0xFFFFFF));
                //suggestions
                if (move == move){
                    if (target >= 9 && Integer.parseInt(b[i][j].getText())==Math.max(1,9)){
                        b[i][j].setBackground(new Color(0x6EC510));
                    }
                    if (target == Integer.parseInt(b[i][j].getText())){
                        b[i][j].setBackground(new Color(0x6EC510));
                    }
                }
                Coloring(i,j,n,m);
                calc(i,j);
                panel.add(b[i][j]);
            }
        }
        frame.add(panel,BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
    }
    public void Coloring(int iC, int jC, int n, int m){
        Random r = new Random();
        b[iC][jC].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i<n;i++){
                    for (int j=0; j<m;j++){
                        //possible nums for choice
                        if (iC == i || jC == j){ //same column and same raw
                            b[i][j].setBackground(new Color(0x6BFD07));
                            b[i][j].setEnabled(true);
                        }else { //impossible nums for choice
                            b[i][j].setBackground(new Color(0xF5F5F5));
                            b[i][j].setEnabled(false);
                        }
                        //selected num
                        if (iC == i && jC == j){
                            curI = i; //will use for swapping
                            curJ = j; //will use for swapping
                            b[i][j].setBackground(new Color(0x78DA64));
                            b[i][j].setEnabled(false);
                        }
                        //omg swapping XD
                        if (move <= amove - 2){
                            temp = b[curI][curJ].getText();
                            System.out.println(temp);
                            b[curI][curJ].setText(b[prevI][prevJ].getText());
                            b[prevI][prevJ].setText(temp);
                            prevI = curI;
                            prevJ = curJ;
                        }
                    }
                }
            }
        });
    }
    public void calc(int i, int j){
        b[i][j].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move--;
                mo.setText("The number of moves: " + move);
                int temp = Integer.parseInt(b[i][j].getText());
                score = score + temp;
                l.setText("Score: "+ score + ",Target value: " + target);
                if (move == 0){
                    int rem = abs(target - score);
                    JOptionPane.showMessageDialog(null, "You lost !!!\nYou ran out of moves\nRemaining score: "+ rem, "GAME OVER!", JOptionPane.WARNING_MESSAGE);
                    frame.dispose();
                    Menu m = new Menu();
                }
                if (score > target){
                    JOptionPane.showMessageDialog(null, "You lost !!!\nYour score is bigger than the target","GAME OVER!", JOptionPane.WARNING_MESSAGE);
                    frame.dispose();
                    Menu m = new Menu();
                }
                if(score == target){
                    JOptionPane.showMessageDialog(null, "YOU WIN !!!\nCongratulations","GAME OVER!", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    Menu m = new Menu();
                }
            }
        });
    }
}
