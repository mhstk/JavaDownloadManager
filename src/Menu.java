import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {

    SpringLayout layout;
    Image background;
    JButton processingB;
    JButton completedB;
    JButton queueB;
    JButton settingsB;

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
        MainFrame.setComponentSize(processingB, new Dimension(100, 40));
        processingB.addActionListener(new KeyListener());
        completedB = new JButton("Completed");
        MainFrame.setComponentSize(completedB, new Dimension(100, 40));
        completedB.addActionListener(new KeyListener());
        queueB = new JButton("Queue");
        MainFrame.setComponentSize(queueB, new Dimension(100, 40));
        queueB.addActionListener(new KeyListener());
        settingsB = new JButton("Settings");
        MainFrame.setComponentSize(settingsB, new Dimension(100, 40));
        settingsB.addActionListener(new KeyListener());

        add(processingB);
        add(completedB);
        add(queueB);
        add(settingsB);

        setLayout(layout);
        setPlace();


    }

    public void setPlace() {
        Dimension d = getSize();

        layout.putConstraint(SpringLayout.NORTH, completedB, 20, SpringLayout.SOUTH, processingB);
        layout.putConstraint(SpringLayout.NORTH, queueB, 20, SpringLayout.SOUTH, completedB);
        layout.putConstraint(SpringLayout.NORTH, settingsB, 20, SpringLayout.SOUTH, queueB);
        layout.putConstraint(SpringLayout.NORTH, processingB, d.height / 3, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, processingB, d.width / 3, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, completedB, d.width / 3, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, queueB, d.width / 3, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, settingsB, d.width / 3, SpringLayout.WEST, this);
        revalidate();
        updateUI();
    }

    class KeyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == processingB) {
                MainFrame.getInstance().getCompletedPanel().setVisible(false);
                MainFrame.getInstance().getProcessingPanel().setVisible(true);
                MainFrame.getInstance().getjScrollPane().setViewportView(MainFrame.getInstance().getProcessingPanel());
                MainFrame.getInstance().getProcessingPanel().setPlace();
            }
            if (e.getSource() == completedB) {
                MainFrame.getInstance().getCompletedPanel().setVisible(true);
                MainFrame.getInstance().getProcessingPanel().setVisible(false);
                MainFrame.getInstance().getjScrollPane().setViewportView(MainFrame.getInstance().getCompletedPanel());
                MainFrame.getInstance().getCompletedPanel().setPlace();
            }
            if (e.getSource() == queueB) {

            }
            if (e.getSource() == settingsB) {
                Manager.getInstance().settingFrame();
            }
        }
    }


}
