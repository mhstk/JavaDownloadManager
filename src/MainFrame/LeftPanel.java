package MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeftPanel extends JPanel {

    SpringLayout layout;
    Image background;
    JButton prossecingB;
    JButton completedB;
    JButton queueB;
    JButton settingsB;
    RightPanel rightPanel;

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }

    LeftPanel(RightPanel rightPanel) {
        setBackground(Color.RED);
        this.rightPanel = rightPanel;
        MainFrame.setComponentSize(this, new Dimension(960 * 3 / 10, 660));
        layout = new SpringLayout();
        ImageIcon bg = new ImageIcon("C:\\Users\\setak\\IdeaProjects\\HM\\JDM\\UIPic\\left.png");
        background = bg.getImage();

        prossecingB = new JButton("Prossecing");
        MainFrame.setComponentSize(prossecingB,new Dimension(100,40));
        prossecingB.addActionListener(new KeyListenet());
        completedB = new JButton("Completed");
        MainFrame.setComponentSize(completedB,new Dimension(100,40));
        completedB.addActionListener(new KeyListenet());
        queueB = new JButton("Queue");
        MainFrame.setComponentSize(queueB,new Dimension(100,40));
        queueB.addActionListener(new KeyListenet());
        settingsB = new JButton("Settings");
        MainFrame.setComponentSize(settingsB,new Dimension(100,40));
        settingsB.addActionListener(new KeyListenet());

        add(prossecingB);
        add(completedB);
        add(queueB);
        add(settingsB);

        setLayout(layout);
        setupPanel();


    }

    public void setupPanel() {
        Dimension d = getSize();

        layout.putConstraint(SpringLayout.NORTH, completedB, 20, SpringLayout.SOUTH, prossecingB);
        layout.putConstraint(SpringLayout.NORTH, queueB, 20, SpringLayout.SOUTH, completedB);
        layout.putConstraint(SpringLayout.NORTH, settingsB, 20, SpringLayout.SOUTH, queueB);
        layout.putConstraint(SpringLayout.NORTH, prossecingB, d.height / 3, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, prossecingB, d.width / 3, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, completedB, d.width / 3, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, queueB, d.width / 3, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, settingsB, d.width / 3, SpringLayout.WEST, this);
    }

    class KeyListenet implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == prossecingB){
                rightPanel.centerPanel.changeBack();
            }
            if (e.getSource() == completedB){

            }
            if (e.getSource() == queueB){

            }
            if (e.getSource() == settingsB){

            }
        }
    }


}
