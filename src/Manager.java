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

    private Manager() {
        this.mainFrame = MainFrame.getInstance();
        directory = "C:\\Users\\setak\\Desktop";
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
        Iterator<DownloadPanel> it = selected.iterator();
        while (it.hasNext()) {
            DownloadPanel downloadPanel = it.next();
            //downloadPanel.getDownloadItem().resumeDownload();
            for (int i = (int) downloadPanel.getDownloadItem().downloadedSize; i <= downloadPanel.getDownloadItem().size; i ++) {

                downloadPanel.getDownloadItem().updateInfo(0, i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i==downloadPanel.getDownloadItem().size){
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
        }
    }
}
