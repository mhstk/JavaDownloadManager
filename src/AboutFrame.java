import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AboutFrame extends JFrame {
    private JComponent mainPanel;
    private SpringLayout layout;
    private JButton ok;
    private JLabel nameL;
    private JLabel programmerNameL;
    private JLabel programmerStudentNumL;
    private JLabel info;
    private JLabel finishedTimeL;
    private JLabel startedTimeL;

    public AboutFrame() {
        setLocation(300, 100);
        setPreferredSize(new Dimension(360, 460));
        setSize(360, 460);
        setTitle("About");
        setIconImage(MainFrame.getInstance().icon);

        mainPanel = new JPanel();
        MainFrame.setComponentSize(mainPanel, getSize());
        layout = new SpringLayout();

        ok = new JButton("OK");
        nameL = new JLabel("JDM 1.0");
        nameL.setFont(new Font("Courier New", Font.BOLD, 35));
        programmerNameL = new JLabel("Author :  Mohammad Hosein Setak");
        programmerNameL.setFont(new Font("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, 14));
        programmerStudentNumL = new JLabel("Student number : 9631811");
        programmerStudentNumL.setFont(new Font("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, 14));
        startedTimeL = new JLabel("Started :  5/11/2018");
        startedTimeL.setFont(new Font("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, 14));
        finishedTimeL = new JLabel("finished :  ...");
        finishedTimeL.setFont(new Font("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, 14));
        info = new JLabel();
        info.setText("<html>This app is for downloading programs, pictures,<br> movies, etc everything you want from internet.<br>It's written in java language and that is why it's<br>called JDM.<br>Enjoy using this app<br>and thank U for choosing us.</html>");
        info.setFont(new Font("Tahoma", Font.HANGING_BASELINE, 12));

        mainPanel.add(nameL);
        mainPanel.add(programmerNameL);
        mainPanel.add(programmerStudentNumL);
        mainPanel.add(info);
        mainPanel.add(startedTimeL);
        mainPanel.add(finishedTimeL);

        layout.putConstraint(SpringLayout.WEST, nameL, 35, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, nameL, 50, SpringLayout.NORTH, mainPanel);
        layout.putConstraint(SpringLayout.WEST, programmerNameL, 0, SpringLayout.WEST, nameL);
        layout.putConstraint(SpringLayout.NORTH, programmerNameL, 35, SpringLayout.SOUTH, nameL);
        layout.putConstraint(SpringLayout.WEST, programmerStudentNumL, 60, SpringLayout.WEST, nameL);
        layout.putConstraint(SpringLayout.NORTH, programmerStudentNumL, 5, SpringLayout.SOUTH, programmerNameL);
        layout.putConstraint(SpringLayout.WEST, startedTimeL, 0, SpringLayout.WEST, nameL);
        layout.putConstraint(SpringLayout.NORTH, startedTimeL, 5, SpringLayout.SOUTH, programmerStudentNumL);
        layout.putConstraint(SpringLayout.WEST, finishedTimeL, 0, SpringLayout.WEST, nameL);
        layout.putConstraint(SpringLayout.NORTH, finishedTimeL, 5, SpringLayout.SOUTH, startedTimeL);
        layout.putConstraint(SpringLayout.WEST, info, 0, SpringLayout.WEST, nameL);
        layout.putConstraint(SpringLayout.NORTH, info, 40, SpringLayout.NORTH, finishedTimeL);

        getRootPane().setDefaultButton(ok);
        setResizable(false);
        mainPanel.setLayout(layout);
        setContentPane(mainPanel);
        MainFrame.getInstance().setEnabled(false);
        setVisible(true);


        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }

        });
    }

    public void close() {
        MainFrame.getInstance().setEnabled(true);
        this.dispose();
    }
}