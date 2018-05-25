import javax.swing.*;
import java.awt.*;

public class DownloadPanel extends JPanel {
    protected DownloadItem downloadItem;
    protected JProgressBar jProgressBar;
    CenterPanel outPanel;
    JLabel nameL;
    JLabel percentL;
    JLabel speedL;
    JLabel sizeL;
    JLabel numberL;
    JLabel startedTimeL;
    SpringLayout layout;

    DownloadPanel(DownloadItem downloadItem) {
        this.downloadItem = downloadItem;
        setOutPanel();
        setOpaque(false);

        jProgressBar = new JProgressBar(0, 10000);

        nameL = new JLabel();
        numberL = new JLabel();
        percentL = new JLabel();
        speedL = new JLabel();
        sizeL = new JLabel();
        startedTimeL = new JLabel();
        layout = new SpringLayout();
        setLayout(layout);
        nameL.setFont(new Font("Arial", Font.BOLD, 20));
        nameL.setForeground(Color.WHITE);
        percentL.setFont(new Font("Arial", Font.PLAIN, 16));
        percentL.setForeground(Color.WHITE);
        sizeL.setFont(new Font("Arial", Font.PLAIN, 16));
        sizeL.setForeground(Color.WHITE);
        speedL.setFont(new Font("Arial", Font.PLAIN, 16));
        speedL.setForeground(Color.WHITE);
        numberL.setFont(new Font("Arial", Font.PLAIN, 26));
        numberL.setForeground(Color.lightGray);
        startedTimeL.setFont(new Font("Arial", Font.PLAIN, 14));
        startedTimeL.setForeground(Color.WHITE);


        nameL.setText(downloadItem.getName());
        percentL.setText(String.format("%.2f%%", downloadItem.getPercent()));
        speedL.setText(downloadItem.getSpeedDownload() + " (mb/s)");
        sizeL.setText(downloadItem.getDownloadedSize() + "/" + downloadItem.getSize() + " (mb)");
        startedTimeL.setText("Started Time : " +downloadItem.startedTime.toString());


        add(nameL);
        add(sizeL);
        add(numberL);
        setPlace();

    }

    public void updateInfo() {
        percentL.setText(String.format("%.2f%%", downloadItem.getPercent()));
        speedL.setText(downloadItem.getSpeedDownload() + " (mb/s)");
        sizeL.setText(downloadItem.getDownloadedSize() + "/" + downloadItem.getSize() + " (mb)");
        System.out.println(downloadItem.getDownloadedSize());
        revalidate();
        updateUI();

    }

    public void setPlace() {

//        layout.putConstraint(SpringLayout.WEST, nameL, getWidth() / 10, SpringLayout.WEST, this);
//        layout.putConstraint(SpringLayout.NORTH, nameL, 10, SpringLayout.NORTH, this);
//        layout.putConstraint(SpringLayout.EAST, percentL, -getWidth() / 10, SpringLayout.EAST, this);
//        layout.putConstraint(SpringLayout.NORTH, percentL, 10, SpringLayout.NORTH, this);
//        layout.putConstraint(SpringLayout.WEST, speedL, getWidth() / 10, SpringLayout.WEST, this);
//        layout.putConstraint(SpringLayout.SOUTH, speedL, -20, SpringLayout.SOUTH, this);
//        layout.putConstraint(SpringLayout.EAST, sizeL, -getWidth() / 10, SpringLayout.EAST, this);
//        layout.putConstraint(SpringLayout.SOUTH, sizeL, -20, SpringLayout.SOUTH, this);
//        layout.putConstraint(SpringLayout.WEST, numberL, getWidth() / 50, SpringLayout.WEST, this);
//        layout.putConstraint(SpringLayout.NORTH, numberL, 34, SpringLayout.NORTH, this);
//
//        MainFrame.setComponentSize(jProgressBar, new Dimension(getWidth() * 79 / 100, 17));
//        layout.putConstraint(SpringLayout.WEST, jProgressBar, getWidth() / 10, SpringLayout.WEST, this);
//        layout.putConstraint(SpringLayout.NORTH, jProgressBar, 38, SpringLayout.NORTH, this);
//        setBorder(BorderFactory.createLineBorder(Color.BLACK));


    }

    public CenterPanel getOutPanel() {
        return outPanel;
    }

    public void setOutPanel() {

    }

    public DownloadItem getDownloadItem() {
        return downloadItem;
    }

    public JProgressBar getjProgressBar() {
        return jProgressBar;
    }

}
