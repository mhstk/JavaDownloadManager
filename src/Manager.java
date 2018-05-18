import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Manager {
    private MainFrame mainFrame;
    private static Manager manager;
    private ArrayList<DownloadPanel> selected;
    private String directory ;
    private int limitDownload;
    private int maxDownload;
    private String lookAndFeelS;

    private Manager() {
        this.mainFrame = MainFrame.getInstance();
        directory = "C:\\Users\\setak\\Desktop";
        maxDownload = 100;
        limitDownload = maxDownload;
        lookAndFeelS = "javax.swing.plaf.nimbus.NimbusLookAndFeel" ;
    }

    public void run(){
        MainFrame mainFrame = MainFrame.getInstance();
    }

    public void setMainFrameUI(){
        try {
            UIManager.setLookAndFeel(lookAndFeelS);
        } catch (Exception e) {
            System.err.println(e);
        }
        MainFrame.getInstance().update();
        MainFrame.getInstance().pack();

    }


    public int getMaxDownload() {
        return maxDownload;
    }

    public String getLookAndFeelS() {
        return lookAndFeelS;
    }

    public void setLookAndFeelS(String lookAndFeelS) {
        this.lookAndFeelS = lookAndFeelS;
    }


    public int getLimitDownload() {
        return limitDownload;
    }

    public void setLimitDownload(int limitDownload) {
        this.limitDownload = limitDownload;
    }

    public static Manager getInstance() {
        if (manager == null) {
            manager = new Manager();
        }
        return manager;
    }

    public void newDownload() {
        NewDownFrame newDownFrame = new NewDownFrame();
    }

    public void addNewDownload(NewDownFrame newDownFrame){
        DownloadItem downloadItem = new DownloadItem(newDownFrame.getDownloadName());
        downloadItem.setInfo(newDownFrame.getDownloadSize(), 1.2, new Random().nextInt((int)newDownFrame.getDownloadSize()), newDownFrame.getStartedTime(),newDownFrame.getServerAddress(),newDownFrame.getSavedAddress());
        mainFrame.getProcessingPanel().addADownload(downloadItem);
        mainFrame.update();
    }

    public void exit() {
        mainFrame.setVisible(false);
    }

    public void settingFrame(){
        SettingsFrame settingsFrame = new SettingsFrame();
    }

    public void downloadSelected(ArrayList<DownloadPanel> selected) {
        mainFrame.getToolBar().downloadSelected();
        mainFrame.getFrameMenubar().downloadSelected();
        this.selected = selected;
    }

    public void downloadUnSelected() {
        mainFrame.getToolBar().downloadUnSelected();
        mainFrame.getFrameMenubar().downloadUnSelected();
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void resumeDownload() {
        if (selected.size()<= limitDownload) {
            Iterator<DownloadPanel> it = selected.iterator();
            while (it.hasNext()) {
                DownloadPanel downloadPanel = it.next();
                for (int i = (int) downloadPanel.getDownloadItem().downloadedSize; i <= downloadPanel.getDownloadItem().size; i++) {

                    downloadPanel.getDownloadItem().updateInfo(0, i);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i == downloadPanel.getDownloadItem().size) {
                        mainFrame.getProcessingPanel().processingPanels.remove(downloadPanel);
                        it.remove();
                        mainFrame.getProcessingPanel().processing.remove(downloadPanel.getDownloadItem());
                        mainFrame.getCompletedPanel().addADownload(downloadPanel.getDownloadItem());
                        mainFrame.update();
                    }
                }
            }
            mainFrame.getProcessingPanel().updateLabels();
            if (selected.isEmpty()) {
                downloadUnSelected();
            }else {
                downloadSelected(selected);
            }
        }else {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Can't download more than "+limitDownload+" file at the same time.\nYou can change limit size in settings.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
