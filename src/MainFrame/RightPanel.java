package MainFrame;

import Download.DownloadItem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RightPanel extends JPanel {
    JPanel upPanel;
    public CenterPanel centerPanel;
    JScrollPane scrollPane ;
    SpringLayout layout;
    Image upBackground;


    public void setupPanel() {
        Dimension d = getSize();
        MainFrame.setComponentSize(upPanel, new Dimension(d.width, d.height * 3 / 40));
        MainFrame.setComponentSize(scrollPane, new Dimension(d.width-8, (d.height*37/40)-50));
        MainFrame.setComponentSize(centerPanel, new Dimension(d.width, (d.height*37/40)));
        layout.putConstraint(SpringLayout.NORTH, upPanel, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, upPanel, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, d.height * 3 / 40 , SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST,scrollPane,0,SpringLayout.WEST,this);

    }




    RightPanel() {
        MainFrame.setComponentSize(this,new Dimension(960*7/10,660));

        scrollPane = new JScrollPane();
        centerPanel = new CenterPanel();

        ImageIcon upIcon = new ImageIcon("C:\\Users\\setak\\IdeaProjects\\HM\\JDM\\UIPic\\up.png");
        upBackground = upIcon.getImage();
        upPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Dimension d = getSize();
                g.drawImage(upBackground, 0, 0, d.width, d.height, null);
            }
        };
        MainFrame.setComponentSize(upPanel,new Dimension(960*7/10,660*3/20));
        MainFrame.setComponentSize(centerPanel,new Dimension(960*7/10,660*17/20));



        JButton newB = new JButton();
        newB.setPreferredSize(new Dimension(40, 40));
        newB.setSize(40, 40);
        newB.setIcon(getScaledImage("C:\\Users\\setak\\IdeaProjects\\HM\\JDM\\UIPic\\newIcon.png", newB));
        newB.setBackground(Color.CYAN);

        JButton resumeB = new JButton();
        resumeB.setPreferredSize(new Dimension(40, 40));
        resumeB.setSize(40, 40);
        resumeB.setIcon(getScaledImage("C:\\Users\\setak\\IdeaProjects\\HM\\JDM\\UIPic\\resumeIcon.png", newB));
        resumeB.setBackground(Color.CYAN);
        resumeB.setEnabled(false);

        JButton pauseB = new JButton();
        pauseB.setPreferredSize(new Dimension(40, 40));
        pauseB.setSize(40, 40);
        pauseB.setIcon(getScaledImage("C:\\Users\\setak\\IdeaProjects\\HM\\JDM\\UIPic\\pauseIcon.png", newB));
        pauseB.setBackground(Color.CYAN);
        pauseB.setEnabled(false);

        JButton cancelB = new JButton();
        cancelB.setPreferredSize(new Dimension(40, 40));
        cancelB.setSize(40, 40);
        cancelB.setIcon(getScaledImage("C:\\Users\\setak\\IdeaProjects\\HM\\JDM\\UIPic\\cancelIcon.png", newB));
        cancelB.setBackground(Color.CYAN);
        cancelB.setEnabled(false);

        JButton removeB = new JButton();
        removeB.setPreferredSize(new Dimension(40, 40));
        removeB.setSize(40, 40);
        removeB.setIcon(getScaledImage("C:\\Users\\setak\\IdeaProjects\\HM\\JDM\\UIPic\\removeIcon.png", newB));
        removeB.setBackground(Color.CYAN);
        removeB.setEnabled(false);


        upPanel.add(newB);
        upPanel.add(resumeB);
        upPanel.add(pauseB);
        upPanel.add(cancelB);
        upPanel.add(removeB);
        SpringLayout upPanelLayout = new SpringLayout();
        upPanel.setLayout(upPanelLayout);
        upPanelLayout.putConstraint(SpringLayout.WEST,newB,10,SpringLayout.WEST,upPanel);
        upPanelLayout.putConstraint(SpringLayout.NORTH,newB,5,SpringLayout.NORTH,upPanel);
        upPanelLayout.putConstraint(SpringLayout.WEST,resumeB,60,SpringLayout.WEST,upPanel);
        upPanelLayout.putConstraint(SpringLayout.NORTH,resumeB,5,SpringLayout.NORTH,upPanel);
        upPanelLayout.putConstraint(SpringLayout.WEST,pauseB,110,SpringLayout.WEST,upPanel);
        upPanelLayout.putConstraint(SpringLayout.NORTH,pauseB,5,SpringLayout.NORTH,upPanel);
        upPanelLayout.putConstraint(SpringLayout.WEST,cancelB,160,SpringLayout.WEST,upPanel);
        upPanelLayout.putConstraint(SpringLayout.NORTH,cancelB,5,SpringLayout.NORTH,upPanel);
        upPanelLayout.putConstraint(SpringLayout.WEST,removeB,210,SpringLayout.WEST,upPanel);
        upPanelLayout.putConstraint(SpringLayout.NORTH,removeB,5,SpringLayout.NORTH,upPanel);

        layout = new SpringLayout();


        centerPanel.addADownload(centerPanel.processing,"DVD.exe",200,1.2,100,12,34,8,2,2018);
        centerPanel.addADownload(centerPanel.processing,"pic.png",2,0.3,1.1,11,18,4,5,2018);
        centerPanel.addADownload(centerPanel.processing,"wed.jpeg",200,1.2,100,12,34,8,2,2018);
        centerPanel.addADownload(centerPanel.processing,"pic1.png",2,0.3,1.1,11,18,4,5,2018);
        centerPanel.addADownload(centerPanel.processing,"DVD234.exe",200,1.2,100,12,34,8,2,2018);
        centerPanel.addADownload(centerPanel.processing,"pic45.png",2,0.3,1.1,11,18,4,5,2018);
        centerPanel.addADownload(centerPanel.processing,"DVD.exe",200,1.2,100,12,34,8,2,2018);
        centerPanel.addADownload(centerPanel.processing,"pic.png",2,0.3,1.1,11,18,4,5,2018);
        centerPanel.addADownload(centerPanel.processing,"wed.jpeg",200,1.2,100,12,34,8,2,2018);
        centerPanel.addADownload(centerPanel.processing,"pic1.png",2,0.3,1.1,11,18,4,5,2018);
        centerPanel.addADownload(centerPanel.processing,"DVD234.exe",200,1.2,100,12,34,8,2,2018);
        centerPanel.addADownload(centerPanel.processing,"pic45.png",2,0.3,1.1,11,18,4,5,2018);
        centerPanel.addADownload(centerPanel.processing,"DVD234.exe",200,1.2,100,12,34,8,2,2018);
        centerPanel.addADownload(centerPanel.processing,"pic45.png",2,0.3,1.1,11,18,4,5,2018);
        centerPanel.addADownload(centerPanel.completed,"JDM.exe",250,0.6,250,16,23,3,3,2018);

        scrollPane.setViewportView(centerPanel);
        centerPanel.setCenterDownloads(centerPanel.processing);

        centerPanel.changeBack();

        centerPanel.setBackground(Color.GREEN);
        upPanel.setBackground(Color.BLUE);
        setLayout(layout);
        scrollPane.getHorizontalScrollBar().disable();
        add(upPanel);
        add(scrollPane);
        setupPanel();

    }

    public void setCenterPanel(JPanel newDownPanel) {
//        centerPanel.setVisible(false);
//        removeAll();
//        centerPanel = (newDownPanel);
//        centerPanel.setVisible(true);
//        scrollPane.setViewportView(centerPanel);
//        add(upPanel);
//        add(scrollPane);
//        setupPanel();
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

}
