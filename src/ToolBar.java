import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ToolBar extends JPanel {
    private Image background;
    private SpringLayout layout;
    private static ToolBar single_instance;
    private JButton newB;
    private JButton resumeB;
    private JButton pauseB;
    private JButton cancelB;
    private JButton removeB;
    private Color backButtonColor;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();
        g.drawImage(background, 0, 0, d.width, d.height, null);
    }

    private ToolBar() {
        ImageIcon bg = new ImageIcon("UIPic\\up.png");
        background = bg.getImage();
        layout = new SpringLayout();
        setLayout(layout);
        backButtonColor = new Color(13,92,106);


        newB = new JButton();
        MainFrame.setComponentSize(newB, new Dimension(40, 40));
        newB.setIcon(getScaledImage("UIPic\\newIcon.png", newB));
        newB.setToolTipText("New Download");
        newB.setBackground(backButtonColor);
        newB.addActionListener(new KeyListener());

        resumeB = new JButton();
        MainFrame.setComponentSize(resumeB, new Dimension(40, 40));
        resumeB.setIcon(getScaledImage("UIPic\\resumeIcon.png", newB));
        resumeB.setBackground(backButtonColor);
        resumeB.addActionListener(new KeyListener());
        resumeB.setToolTipText("Resume");

        pauseB = new JButton();
        MainFrame.setComponentSize(pauseB, new Dimension(40, 40));
        pauseB.setIcon(getScaledImage("UIPic\\pauseIcon.png", newB));
        pauseB.setBackground(backButtonColor);
        pauseB.addActionListener(new KeyListener());
        pauseB.setToolTipText("Pause");

        cancelB = new JButton();
        MainFrame.setComponentSize(cancelB, new Dimension(40, 40));
        cancelB.setIcon(getScaledImage("UIPic\\cancelIcon.png", newB));
        cancelB.setBackground(backButtonColor);
        cancelB.addActionListener(new KeyListener());
        cancelB.setToolTipText("Cancel");

        removeB = new JButton();
        MainFrame.setComponentSize(removeB, new Dimension(40, 40));
        removeB.setIcon(getScaledImage("UIPic\\removeIcon.png", newB));
        removeB.setBackground(backButtonColor);
        removeB.addActionListener(new KeyListener());
        removeB.setToolTipText("Remove");

        downloadUnSelected();

        add(newB);
        add(resumeB);
        add(pauseB);
        add(cancelB);
        add(removeB);


    }

    private class KeyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==newB){
                Manager.getInstance().newDownload();
            }
            if (e.getSource()==resumeB){
                Manager.getInstance().resumeDownload();
                System.out.println("2");
            }
        }
    }

    public static ToolBar getInstance() {
        if (single_instance == null) {
            single_instance = new ToolBar();

        }

        return single_instance;
    }

    public void downloadSelected(){
        resumeB.setEnabled(true);
        pauseB.setEnabled(true);
        removeB.setEnabled(true);
        cancelB.setEnabled(true);
    }

    public void downloadUnSelected(){
        resumeB.setEnabled(false);
        pauseB.setEnabled(false);
        removeB.setEnabled(false);
        cancelB.setEnabled(false);
    }

    public ImageIcon getScaledImage(String address, JButton button) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(address));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image icon = img.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(icon);

        return imageIcon;
    }

    public void setPlace() {
        int gapV = getHeight() / 5;
        int gapH = getWidth() / 80;

        MainFrame.setComponentSize(newB, new Dimension(getWidth()*6/100, getWidth()*6/100));
        MainFrame.setComponentSize(resumeB, new Dimension(getWidth()*6/100, getWidth()*6/100));
        MainFrame.setComponentSize(pauseB, new Dimension(getWidth()*6/100, getWidth()*6/100));
        MainFrame.setComponentSize(removeB, new Dimension(getWidth()*6/100, getWidth()*6/100));
        MainFrame.setComponentSize(cancelB, new Dimension(getWidth()*6/100, getWidth()*6/100));

        layout.putConstraint(SpringLayout.WEST, newB, gapH, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, newB, gapV, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, resumeB, getWidth()*6/100 + gapH * 2, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, resumeB, gapV, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, pauseB, getWidth()*2*6/100 + 3 * gapH, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, pauseB, gapV, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, cancelB, getWidth()*3*6/100 + 4 * gapH, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, cancelB, gapV, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, removeB, getWidth()*4*6/100 + 5 * gapH, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, removeB, gapV, SpringLayout.NORTH, this);
    }
}
