import javax.swing.*;
import java.awt.*;

public class CompletedDP extends DownloadPanel {
    ImageIcon doneIcon ;

    CompletedDP(DownloadItem downloadItem) {
        super(downloadItem);
        sizeL.setText(downloadItem.getSize() + " (mb)");

        MainFrame.setComponentSize(iconL,new Dimension(50,50));
        doneIcon = ToolBar.getScaledImage("UIPic\\done.png",iconL);
        iconL.setIcon(doneIcon);

        add(iconL);
        add(startedTimeL);

    }


//    private DownloadItem downloadItem;
//    JLabel nameL;
//    JLabel sizeL;
//    JLabel numberL;
//    JLabel startedTimeL;
//    SpringLayout layout;

//    DownloadCompletedPanel(DownloadItem downloadItem) {
//        this.downloadItem = downloadItem;
//        setOpaque(false);
//        setBackground(Color.DARK_GRAY);
//
//        nameL = new JLabel();
//        numberL = new JLabel();
//        sizeL = new JLabel();
//        startedTimeL = new JLabel();
//        layout = new SpringLayout();
//        setLayout(layout);
//        nameL.setFont(new Font("Arial", Font.BOLD, 20));
//        nameL.setForeground(Color.WHITE);
//        sizeL.setFont(new Font("Arial", Font.PLAIN, 16));
//        sizeL.setForeground(Color.WHITE);
//        numberL.setFont(new Font("Arial", Font.PLAIN, 26));
//        numberL.setForeground(Color.lightGray);
//        startedTimeL.setFont(new Font("Arial", Font.PLAIN, 14));
//        startedTimeL.setForeground(Color.WHITE);
//
//
//        nameL.setText(downloadItem.getName());
//        sizeL.setText(downloadItem.getSize() + " (mb)");
//        startedTimeL.setText("Started Time : " + downloadItem.startedTime.getHour() + ":" + downloadItem.startedTime.getMin() + " (" + downloadItem.startedTime.getMonth() + "/" + downloadItem.startedTime.getDay() + "/" + downloadItem.startedTime.getYear() + ")");
//
//
//        addDownload(nameL);
//        addDownload(sizeL);
//        addDownload(numberL);
//        setPanelSize();
//    }

    public void setPlace() {

        //add(numberL);
//        add(nameL);
//        add(sizeL);
//        add(startedTimeL);
//        layout.putConstraint(SpringLayout.WEST, numberL, getWidth() / 50, SpringLayout.WEST, this);
//        layout.putConstraint(SpringLayout.NORTH, numberL, 34, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.NORTH, iconL, 23, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, iconL,40 , SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.WEST, nameL, getWidth() * 9 / 50, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, nameL, 38, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, sizeL, -40, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, sizeL, 34, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, startedTimeL, -40, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, startedTimeL, 58, SpringLayout.NORTH, this);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));


    }

    public void setOutPanel() {
        outPanel = MainFrame.getInstance().getCompletedPanel();
    }



    public DownloadItem getDownloadItem() {
        return downloadItem;
    }

}
