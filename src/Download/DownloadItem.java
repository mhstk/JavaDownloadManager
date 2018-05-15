package Download;

import MainFrame.MainFrame;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class DownloadItem {
    String name;
    String serverAddress;
    String savedAddress;
    double speedDownload;
    double size;
    double downloadedSize;
    double percent;
    int number;
    public Time startedTime;

    public JPanel processPanel;
    public JPanel completePanel;
    JProgressBar jProgressBar;
    JLabel nameL;
    JLabel percentL;
    JLabel speedL;
    JLabel sizeL;
    JLabel numberL;
    JLabel startedTimeL;
    SpringLayout processLayout;
    SpringLayout completeLayout;


    public DownloadItem(String name) {

        this.name = name;
        processPanel = new JPanel();
        processPanel.setOpaque(false);
        completePanel = new JPanel();
        completePanel.setOpaque(false);
        processPanel.setBackground(Color.RED);
        jProgressBar  = new JProgressBar(0,10000);
        startedTime = new Time();

        jProgressBar.setUI(new BasicProgressBarUI(){
            @Override
            public void paintDeterminate(Graphics g, JComponent c) {
                if (!(g instanceof Graphics2D)) {
                    return;
                }
                Insets b = progressBar.getInsets(); // area for border
                int barRectWidth  = progressBar.getWidth()  - (b.right + b.left);
                int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);
                if (barRectWidth <= 0 || barRectHeight <= 0) {
                    return;
                }
                int cellLength = getCellLength();
                int cellSpacing = getCellSpacing();

                int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

                if(progressBar.getOrientation() == JProgressBar.HORIZONTAL) {

                    float x = amountFull / (float)barRectWidth;
                    g.setColor(Color.GREEN);
                    g.fillRect(b.left, b.top, amountFull, barRectHeight);

                } else { // VERTICAL

                }
                if(progressBar.isStringPainted()) {
                    paintString(g, b.left, b.top, barRectWidth, barRectHeight, amountFull, b);
                }
            }
        });
        jProgressBar.setBackground(Color.WHITE);
        nameL = new JLabel() ;
        numberL = new JLabel();
        percentL = new JLabel();
        speedL = new JLabel();
        sizeL = new JLabel();
        startedTimeL = new JLabel();
        processLayout = new SpringLayout();
        processPanel.setLayout(processLayout);
        completeLayout = new SpringLayout();
        completePanel.setLayout(completeLayout);
        nameL.setFont(new Font("Arial", Font.BOLD, 20));
        nameL.setForeground(Color.WHITE);
        percentL.setFont(new Font("Arial", Font.PLAIN, 16));
        percentL.setForeground(Color.WHITE);
        sizeL.setFont(new Font("Arial", Font.PLAIN, 16));
        sizeL.setForeground(Color.WHITE);
        speedL.setFont(new Font("Arial",Font.PLAIN, 16));
        speedL.setForeground(Color.WHITE);
        numberL.setFont(new Font("Arial",Font.PLAIN, 26));
        numberL.setForeground(Color.lightGray);
        startedTimeL.setFont(new Font("Arial",Font.PLAIN, 14));
        startedTimeL.setForeground(Color.WHITE);


    }

    public void setPlaceProcess(){
        setPanelInfo();
        processPanel.add(jProgressBar);
        processPanel.add(nameL);
        processPanel.add(percentL);
        processPanel.add(speedL);
        processPanel.add(sizeL);
        processPanel.add(numberL);
        processLayout.putConstraint(SpringLayout.WEST,nameL,70,SpringLayout.WEST, processPanel);
        processLayout.putConstraint(SpringLayout.NORTH,nameL,10,SpringLayout.NORTH, processPanel);
        processLayout.putConstraint(SpringLayout.EAST,percentL,-80,SpringLayout.EAST, processPanel);
        processLayout.putConstraint(SpringLayout.NORTH,percentL,10,SpringLayout.NORTH, processPanel);
        processLayout.putConstraint(SpringLayout.WEST,speedL,70,SpringLayout.WEST, processPanel);
        processLayout.putConstraint(SpringLayout.SOUTH,speedL,-10,SpringLayout.SOUTH, processPanel);
        processLayout.putConstraint(SpringLayout.EAST,sizeL,-80,SpringLayout.EAST, processPanel);
        processLayout.putConstraint(SpringLayout.SOUTH,sizeL,-10,SpringLayout.SOUTH, processPanel);
        processLayout.putConstraint(SpringLayout.WEST,numberL,15,SpringLayout.WEST, processPanel);
        processLayout.putConstraint(SpringLayout.NORTH,numberL,34,SpringLayout.NORTH, processPanel);

        MainFrame.setComponentSize(jProgressBar,new Dimension(520,20));
        processLayout.putConstraint(SpringLayout.WEST,jProgressBar,70,SpringLayout.WEST, processPanel);
        processLayout.putConstraint(SpringLayout.NORTH,jProgressBar,38,SpringLayout.NORTH, processPanel);
        processPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN));


    }

    public void setPlaceComplete(){
        completePanel.add(numberL);
        completePanel.add(nameL);
        sizeL.setText(size+" (mb)");
        completePanel.add(sizeL);
        completePanel.add(startedTimeL);
        completeLayout.putConstraint(SpringLayout.WEST,numberL,15,SpringLayout.WEST, completePanel);
        completeLayout.putConstraint(SpringLayout.NORTH,numberL,34,SpringLayout.NORTH, completePanel);
        completeLayout.putConstraint(SpringLayout.WEST,nameL,55,SpringLayout.WEST, completePanel);
        completeLayout.putConstraint(SpringLayout.NORTH,nameL,38,SpringLayout.NORTH, completePanel);
        completeLayout.putConstraint(SpringLayout.WEST,sizeL,350,SpringLayout.WEST, completePanel);
        completeLayout.putConstraint(SpringLayout.NORTH,sizeL,34,SpringLayout.NORTH, completePanel);
        completeLayout.putConstraint(SpringLayout.WEST,startedTimeL,350,SpringLayout.WEST, completePanel);
        completeLayout.putConstraint(SpringLayout.NORTH,startedTimeL,58,SpringLayout.NORTH, completePanel);
    }

    public void setPanelInfo(){
        nameL.setText(name);
        percentL.setText(String.format("%.2f%%" , percent));
        speedL.setText(speedDownload+" (mb/s)");
        sizeL.setText(downloadedSize+"/"+size+" (mb)");
        jProgressBar.setValue((int)percent*100);
        numberL.setText(number+".");
        startedTimeL.setText("Started Time : "+startedTime.hour+":"+startedTime.min + " ("+startedTime.month+"/"+startedTime.day+"/"+startedTime.year+")");
    }

    public void setPanelSize(JPanel outPanel){
        Dimension d = outPanel.getSize();
        MainFrame.setComponentSize(this.processPanel,new Dimension(d.width,d.height*7/40));
        MainFrame.setComponentSize(this.completePanel,new Dimension(d.width,d.height*7/40));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getSavedAddress() {
        return savedAddress;
    }

    public void setSavedAddress(String savedAddress) {
        this.savedAddress = savedAddress;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getSpeedDownload() {
        return speedDownload;
    }

    public void setSpeedDownload(double speedDownload) {
        this.speedDownload = speedDownload;
    }

    public double getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(double downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
