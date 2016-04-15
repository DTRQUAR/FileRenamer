package Prog.Classes;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

/**
 * Created by Qouer on 15.04.2016.
 */
public class MainForm extends JFrame{
    private JPanel panel1;
    private JTextField dirField;
    private JTextField firstValueField;
    private JTextField secondValueField;
    private JButton applyButton;
    private JButton xButton;
    private JButton openButton;
    private JButton xButton2;
    private JButton xButton3;
    private JFrame frame;

    File directiory = null;
    String firstValue = "";
    String secondValue = "";
    int k = 0;

    public MainForm() {
        // default value JFrame.HIDE_ON_CLOSE]
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        this.setTitle("File Renamer");

        // Кнопка выбора директории
        openButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int returnVal = fc.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    directiory = fc.getSelectedFile();
                    if (directiory.isFile()){
                        directiory = directiory.getParentFile();
                    }
                    dirField.setText(directiory.toString());
                }
            }
        });

        //Кнопка запуска
        applyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                String dirText = dirField.getText();
                directiory = new File(dirText);
                firstValue = firstValueField.getText();
                secondValue = secondValueField.getText();
                firstValue.replaceAll(" ", "");
                secondValue.replaceAll(" ", "");

                if (dirText.equals("")) {
                    JOptionPane.showMessageDialog(frame,
                                        "Выберите директорию!",
                                        "Некорректные данные",
                                        JOptionPane.WARNING_MESSAGE);
                } else {
                    if (directiory.isDirectory()) {
                        File[] files = directiory.listFiles();
                        if (files.length != 0 && (!firstValue.equals("") || !secondValue.equals(""))) {
                            for (final File f : files) {
                                String fileName = f.getName();
                                int lastInd = fileName.lastIndexOf(".");
                                String format = "";
                                if (lastInd != -1) {format = fileName.substring(lastInd + 1, fileName.length());}

                                if (format.equals(firstValue) || firstValue.equals("")) {
                                    try {
                                        String fileNameSub = f.getName();
                                        if (lastInd != -1) {
                                            fileNameSub = f.getName().substring(0, lastInd);
                                        }

                                        File newfile = new File(directiory + "\\" + fileNameSub + "." + secondValue);
                                        if (f.renameTo(newfile)){
                                            k++;
                                        }
                                    } catch (Exception e1) {
                                        JOptionPane.showMessageDialog(frame,
                                                "Не удалось выполнить операцию",
                                                "Ошибка",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                            JOptionPane.showMessageDialog(frame,
                                    "Количество измнененных файлов: " + k,
                                    "Информация",
                                    JOptionPane.WARNING_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(frame,
                                    "Количество измнененных файлов: " + k,
                                    "Информация",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(frame,
                                "Директория указана не верно!",
                                "Ошибка",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        xButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dirField.setText("");
            }
        });
        xButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                firstValueField.setText("");
            }
        });
        xButton3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                secondValueField.setText("");
            }
        });
    }
}
