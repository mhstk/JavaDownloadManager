package MainFrame;

import Download.DownloadItem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;

public class CenterPanel extends JPanel {
    SpringLayout layout;
    Image background;
    ArrayList<DownloadItem> processing ;
    ArrayList<DownloadItem> completed ;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0,getWidth(),getHeight()*100,null);
    }

    CenterPanel(){
        ImageIcon bg = new ImageIcon("C:\\Users\\setak\\IdeaProjects\\HM\\JDM\\UIPic\\center.png");
        background = bg.getImage();
        completed = new ArrayList<>();
        processing = new ArrayList<>();
        layout = new SpringLayout();
        setLayout(layout);
    }

    public void changeBack(){
        Dimension d = new Dimension(getWidth(), getHeight() + (int) (processing.size() - 5.5) * getHeight() * 7 / 40);
        //MainFrame.setComponentSize(this,d);
    }

    public void addADownload(ArrayList<DownloadItem> downloadList , String name , int size , double speed , double downloaded , int hour , int min, int day , int month , int year){

        DownloadItem downloadItem = new DownloadItem(name);
        downloadItem.setSize(size);
        downloadItem.setSpeedDownload(speed);
        downloadItem.setDownloadedSize(downloaded);
        downloadItem.setPercent(downloaded*100.0/size);
        downloadItem.setNumber(downloadList.size()+1);
        downloadItem.startedTime.setTime(hour, min, day, month, year);
        downloadItem.setPanelSize(this);
        downloadItem.setPanelInfo();
        if (downloadList.equals( completed)){
            downloadItem.setPlaceComplete();
        }
        if (downloadList.equals(processing)){
            downloadItem.setPlaceProcess();
        }
        downloadList.add(downloadItem);
    }

    public void setCenterDownloads(ArrayList<DownloadItem> downloadList){
        removeAll();

        Dimension d ;
        if (downloadList.size()>= 6) {
            System.out.println(downloadList.size());
            d = new Dimension(getWidth(), getHeight() + (int) (downloadList.size() - 5.5) * getHeight() * 7 / 40);
            System.out.println(d);
        }else {
            d = getSize();
        }
        MainFrame.setComponentSize(this,d);
        if (downloadList.equals( completed)){
            for (int i = 0; i < downloadList.size(); i++) {
                this.add(downloadList.get(i).completePanel);
                layout.putConstraint(SpringLayout.WEST, downloadList.get(i).completePanel, 0, SpringLayout.WEST, this);
                layout.putConstraint(SpringLayout.NORTH, downloadList.get(i).completePanel, i * downloadList.get(0).completePanel.getHeight(), SpringLayout.NORTH, this);
            }
        }
        if (downloadList.equals( processing)) {
            for (int i = 0; i < downloadList.size(); i++) {
                this.add(downloadList.get(i).processPanel);
                layout.putConstraint(SpringLayout.WEST, downloadList.get(i).processPanel, 0, SpringLayout.WEST, this);
                layout.putConstraint(SpringLayout.NORTH, downloadList.get(i).processPanel, i * downloadList.get(0).processPanel.getHeight(), SpringLayout.NORTH, this);
            }
        }
    }

}
