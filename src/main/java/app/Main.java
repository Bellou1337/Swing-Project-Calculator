package app;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {

    static Font font = new Font("Arial", Font.BOLD, 34);
    static Font fontBtn = new Font("Arial", Font.PLAIN, 20);
    static JFrame frame = new JFrame("Calculator");
    static JTextField textField = new JTextField(16);
    static ArrayList<JButton> buttonList = new ArrayList<>();
    static ArrayList<String> operations = new ArrayList<>(Arrays.asList("+", "-", "/", "C", "x", "="));
    static JPanel buttonsPanel = new JPanel();
    static JPanel mainPanel = new JPanel();
    static GridLayout gridLayout = new GridLayout(4, 4, 10, 10);
    static String left = "", right = "", result = " ";

    public static void main(String[] args) {

        URL imageURL;
        try {
            imageURL = new URL("https://cdn-icons-png.freepik.com/512/10310/10310245.png?ga=GA1.1.578290812.1720558970");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        ImageIcon icon = new ImageIcon(imageURL);

        CreateTextField();
        CreateButton();
        CreateWindow(icon);
        CreateMainPanel();
        CreateGridPos();

    }

    public static void CreateWindow(ImageIcon icon) {
        frame.setFont(font);
        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        frame.setBounds(screenSize.width / 2 - 250, screenSize.height / 2 - 250, 500, 500);
        frame.add(textField);
        frame.add(buttonsPanel);
        frame.add(mainPanel);
    }

    public static void CreateTextField() {
        textField.setText("0");
        textField.setEditable(false);
        textField.setFont(font);
        textField.setPreferredSize(new Dimension(100, 120));
        textField.setFocusable(false);
        textField.setBackground(new Color(32, 32, 32));
        textField.setForeground(Color.WHITE);
        textField.setBorder(null);
    }

    public static void CreateButton() {
        int countButtons = 10;
        for (int i = 0; i < countButtons; i++) {
            JButton btn = new JButton(Integer.toString(i));
            OnClicked(btn);
            btn.setBackground(new Color(49, 49, 49));
            btn.setForeground(Color.WHITE);
            btn.setFont(fontBtn);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);

            buttonList.add(btn);
        }
        buttonList.forEach(buttonsPanel::add);
        operations.forEach(it -> {
            boolean flag = false;
            if (it.equals("=")) {
                flag = true;
            }
            JButton btn = new JButton(it);
            if (flag == true) {
                btn.setBackground(new Color(118, 185, 237));
            } else {
                btn.setBackground(new Color(49, 49, 49));
            }
            OnClicked(btn);
            btn.setForeground(Color.WHITE);
            btn.setFont(fontBtn);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);

            buttonsPanel.add(btn);
        });

    }

    static void OnClicked(JButton btn) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                String now = e.getActionCommand();
                if (now.charAt(0) >= '0' && now.charAt(0) <= '9') {
                    if (result.equals(" ")) {
                        left = left + now;
                        if (left.charAt(0) == '0') left = left.substring(1);
                        if (left.length() == 0) left = "0";
                    } else {
                        right = right + now;
                        if (right.charAt(0) == '0') right = right.substring(1);
                        if (right.length() == 0) right = "0";
                    }
                    textField.setText(left + ' ' + result + ' ' + right);
                } else if (now.charAt(0) == '=') {
                    double firstNum, secondNum, answer;
                    String print;
                    switch (result) {
                        case "+":
                            if (left == "") left = "0";
                            if (right == "") right = "0";
                            firstNum = Double.parseDouble(left);
                            secondNum = Double.parseDouble(right);
                            answer = firstNum + secondNum;
                            if (IsNumOrDouble(answer)) {
                                Long printNum = (long) answer;
                                print = Long.toString(printNum);
                            } else {
                                print = Double.toString(answer);
                            }

                            textField.setText(print);

                            break;
                        case "-":
                            if (left == "") {
                                left = "0";
                            }
                            ;
                            if (right == "") {
                                right = "0";
                            }
                            ;
                            firstNum = Double.parseDouble(left);
                            secondNum = Double.parseDouble(right);
                            answer = firstNum - secondNum;
                            if (IsNumOrDouble(answer)) {
                                Long printNum = (long) answer;
                                print = Long.toString(printNum);
                            } else {
                                print = Double.toString(answer);
                            }
                            ;
                            textField.setText(print);

                            break;
                        case "x":
                            if (left == "") left = "0";
                            if (right == "") right = "0";
                            firstNum = Double.parseDouble(left);
                            secondNum = Double.parseDouble(right);
                            answer = firstNum * secondNum;
                            if (IsNumOrDouble(answer)) {
                                Long printNum = (long) answer;
                                print = Long.toString(printNum);
                            } else {
                                print = Double.toString(answer);
                            }

                            textField.setText(print);

                            break;
                        case "/":
                            if (left == "") left = "0";
                            if (right == "") right = "0";
                            firstNum = Double.parseDouble(left);
                            secondNum = Double.parseDouble(right);
                            double result;
                            try {
                                result = firstNum / secondNum;
                                if (IsNumOrDouble(result)) {
                                    Long printNum = (long) result;
                                    print = Long.toString(printNum);
                                } else {
                                    print = Double.toString(result);
                                }


                                textField.setText(print);

                            } catch (ArithmeticException exception) {
                                String errorString = "division by 0!";
                                textField.setText(errorString);
                            }

                            break;
                        default:
                            textField.setText(left);
                            break;
                    }
                    right = "";
                    result = " ";
                    left = textField.getText();
                } else if (now.charAt(0) == 'C') {
                    left = right = "";
                    result = " ";
                    textField.setText("0");
                } else {
                    if (left == "") left = "0";
                    result = now;
                    textField.setText(left + ' ' + result + ' ' + right);
                }

            }
        });
    }

    static boolean IsNumOrDouble(double num) {
        double checker = (long) num;
        if (num - checker != 0) {
            return false;
        }

        return true;
    }

    static void CreateMainPanel() {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(textField, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
    }

    static void CreateGridPos() {
        buttonsPanel.setLayout(gridLayout);
        buttonsPanel.setBackground(new Color(32, 32, 32));
    }
}