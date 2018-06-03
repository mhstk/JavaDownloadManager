import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DownloadPanel extends JPanel {
    protected DownloadItem downloadItem;
    protected JProgressBar jProgressBar;
    CenterPanel outPanel;
    JLabel nameL;
    JLabel percentL;
    JLabel speedL;
    JLabel sizeL;
    JLabel numberL;
    JLabel iconL;
    JLabel queueL;
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
        iconL = new JLabel();
        queueL = new JLabel();
        queueL.setFont(new Font("Arial", Font.PLAIN, 16));
        queueL.setForeground(Color.WHITE);
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
//        percentL.setText(String.format("%.2f%%", downloadItem.getPercent()));
        percentL.setText(String.format("%.2f%%", (downloadItem.downloadedSize)*100.0/downloadItem.size));
        speedL.setText(downloadItem.getSpeedDownload() + " (mb/s)");
        sizeL.setText(String.format("%.2f",downloadItem.getDownloadedSize()) + " / " + String.format("%.2f",downloadItem.getSize()) + " (mb)");
        DateFormat df = new SimpleDateFormat("HH:mm:ss  (dd/MM/yy)");
        startedTimeL.setText("Started Time : " +df.format(downloadItem.getStartedTime()));



        add(nameL);
        add(sizeL);
        setPlace();

    }

    public void updateInfo() {
        speedL.setText(downloadItem.getSpeedDownload() + " (mb/s)");
        sizeL.setText(String.format("%.2f",downloadItem.getDownloadedSize()) + "/" +String.format("%.2f",downloadItem.getSize()) + " (mb)");
        percentL.setText(String.format("%.2f%%" , downloadItem.downloadedSize*100.0/downloadItem.size));
        jProgressBar.setValue((int)(downloadItem.downloadedSize*10000/downloadItem.size));
        revalidate();
        updateUI();

    }

    public void setPlace() {


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

    public String info() {
        return  downloadItem.name + "\n"+downloadItem.serverAddress+"\n"+downloadItem.savedAddress+"\n"+downloadItem.size+"\n"+downloadItem.getStartedTime();
    }

}
