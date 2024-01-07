
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class Menu {
    Image pic;

    Menu() {
        JFrame frame = new JFrame("More or less, less is more");
        frame.setLocation(100, 200);
        frame.setSize(150, 350);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        JLabel gameName = new JLabel("More or less, less is more");
        gameName.setFont(new Font("", Font.ITALIC, 10));
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
        start.setBounds(20, 240, 100, 30);
        start.setBackground(new Color(0xF5F5F5));
        frame.add(start);

        JButton load = new JButton("Load");
        load.setBounds(20, 275, 100, 30);
        load.setBackground(new Color(0xF5F5F5));
        frame.add(load);

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
                Engine game = new Engine(n, m, move, target, score, amove, false, new String[0][0]);
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] settings = new String[0];
                String[][] field = new String[0][0];
                JFileChooser lchooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                lchooser.setFileFilter(filter);
                lchooser.setCurrentDirectory(new File("."));
                lchooser.setDialogTitle("Load");
                int res = lchooser.showSaveDialog(null);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File file = lchooser.getSelectedFile();
                    String filePath = file.getAbsolutePath();
                    try {
                        File myObj = new File(filePath);
                        Scanner myReader = new Scanner(myObj);
                        settings = myReader.nextLine().split(",");
                        field = new String[Integer.parseInt(settings[0])][Integer.parseInt(settings[1])];
                        int i = 0;
                        while (myReader.hasNextLine()) {
                            field[i] = myReader.nextLine().split(",");
                            i++;
                        }
                        myReader.close();
                    } catch (FileNotFoundException ex) {
                        System.out.println("An error occurred.");
                        ex.printStackTrace();
                    }
                }


                int n = Integer.parseInt(settings[0]);
                int m = Integer.parseInt(settings[1]);
                int target = Integer.parseInt(settings[2]);
                int move = Integer.parseInt(settings[3]);
                int score = 0;
                int amove = move;
                frame.dispose();
                Engine game = new Engine(n, m, move, target, score, amove, true, field);
            }
        });


        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
