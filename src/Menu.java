import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {

    SpringLayout layout;
    private Image background;
    private JButton processingB;
    private JButton completedB;
    private JButton queueB;
    private JButton settingsB;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0,getWidth(),getHeight(), null);
    }

    Menu() {
        setBackground(Color.RED);
        MainFrame.setComponentSize(this, new Dimension(960 * 3 / 10, 660));
        layout = new SpringLayout();
        ImageIcon bg = new ImageIcon("C:\\Users\\setak\\IdeaProjects\\HM\\JDM\\UIPic\\left.png");
        background = bg.getImage();

        processingB = new JButton("Prossecing");
        MainFrame.setComponentSize(processingB, new Dimension(150, 40));
        processingB.addActionListener(new KeyListener());
        completedB = new JButton("Completed");
        MainFrame.setComponentSize(completedB, new Dimension(150, 40));
        completedB.addActionListener(new KeyListener());
        queueB = new JButton("Queue");
        MainFrame.setComponentSize(queueB, new Dimension(150, 40));
        queueB.addActionListener(new KeyListener());
        settingsB = new JButton("Settings");
        MainFrame.setComponentSize(settingsB, new Dimension(150, 40));
        settingsB.addActionListener(new KeyListener());

        add(processingB);
        add(completedB);
        add(queueB);
        add(settingsB);

        setLayout(layout);
        setPlace();


    }

    void setPlace() {
        Dimension d = getSize();
        System.out.println(d);

        layout.putConstraint(SpringLayout.NORTH, completedB, 20, SpringLayout.SOUTH, processingB);
        layout.putConstraint(SpringLayout.NORTH, queueB, 20, SpringLayout.SOUTH, completedB);
        layout.putConstraint(SpringLayout.NORTH, settingsB, 20, SpringLayout.SOUTH, queueB);
        layout.putConstraint(SpringLayout.NORTH, processingB, d.height* 7/ 20, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, processingB, d.width*57/200, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, completedB, 0, SpringLayout.WEST, processingB);
        layout.putConstraint(SpringLayout.WEST, queueB, 0, SpringLayout.WEST, processingB);
        layout.putConstraint(SpringLayout.WEST, settingsB, 0, SpringLayout.WEST, processingB);
    }

    class KeyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == processingB) {
                Manager.getInstance().downloadUnSelected();
                Manager.getInstance().selected.clear();
                Manager.getInstance().setCenter("Processing");
                MainFrame.getInstance().showProcessing();

            }
            if (e.getSource() == completedB) {
                Manager.getInstance().downloadUnSelected();
                Manager.getInstance().selected.clear();
                Manager.getInstance().setCenter("Completed");
                MainFrame.getInstance().showCompleted();

            }
            if (e.getSource() == queueB) {
                Manager.getInstance().downloadUnSelected();
                Manager.getInstance().selected.clear();
                Manager.getInstance().setCenter("Queue");
                MainFrame.getInstance().showQueue();
            }
            if (e.getSource() == settingsB) {
                Manager.getInstance().downloadUnSelected();
                Manager.getInstance().selected.clear();
                Manager.getInstance().settingFrame();
            }
        }
    }


}
