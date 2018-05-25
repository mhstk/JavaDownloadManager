import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Manager {
    private MainFrame mainFrame;
    private static Manager manager;
    private ArrayList<DownloadPanel> selected;
    private String directory;
    private int limitDownload;
    private int maxDownload;
    private String lookAndFeelS;
    private String fileProcessingAddress ;
    private String fileCompletedAddress ;
    private String fileQueueAddress ;
    private String fileSettingsAddress ;
    private String fileRemovedAddress ;

    private Manager(String lookAndFeelS ,int limitDownload , String directory ) {

        this.directory = directory;
        maxDownload = 100;
        this.limitDownload = limitDownload;
        this.lookAndFeelS = lookAndFeelS;
        fileProcessingAddress = "files\\processing.jdm";
        fileCompletedAddress = "files\\completed.jdm";
        fileQueueAddress = "files\\queue.jdm";
        fileSettingsAddress = "files\\settings.jdm";
        fileRemovedAddress = "files\\removed.jdm";
        selected = new ArrayList<>();

    }

    public void run() {
        this.mainFrame = MainFrame.getInstance();
        start();
    }

    public void setMainFrameUI() {
        try {
            UIManager.setLookAndFeel(lookAndFeelS);
        } catch (Exception e) {
            System.err.println(e);
        }
        MainFrame.getInstance().update();

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
            manager = new Manager(FileUtils.readLF(),FileUtils.readlimit(),FileUtils.readDirectory());
        }
        return manager;
    }

    public void start(){

        ArrayList<DownloadItem> downloadItems = FileUtils.read(fileProcessingAddress);
        for (int i=0 ; i<downloadItems.size();i++) {
            mainFrame.getProcessingPanel().addDownload(downloadItems.get(i));
        }
        mainFrame.getProcessingPanel().revalidate();
        downloadItems.clear();


        downloadItems = FileUtils.read(fileCompletedAddress);
        for (int i=0 ; i<downloadItems.size();i++) {
            mainFrame.getCompletedPanel().addDownload(downloadItems.get(i));
        }
        mainFrame.getCompletedPanel().revalidate();
        downloadItems.clear();


        downloadItems = FileUtils.read(fileQueueAddress);
        for (int i=0 ; i<downloadItems.size();i++) {
            mainFrame.getQueuePanel().addDownload(downloadItems.get(i));
        }
        mainFrame.getQueuePanel().revalidate();


    }

    public void newDownload() {
        new NewDownFrame();
    }

    public void addNewDownload(NewDownFrame newDownFrame, boolean toQueue) {
        DownloadItem downloadItem = new DownloadItem(newDownFrame.getDownloadName());
        downloadItem.setInfo(newDownFrame.getDownloadSize(), 1.2, new Random().nextInt((int) newDownFrame.getDownloadSize()), newDownFrame.getStartedTime(), newDownFrame.getServerAddress(), newDownFrame.getSavedAddress());
        mainFrame.getProcessingPanel().addDownload(downloadItem);
        if (toQueue) {
            mainFrame.getQueuePanel().addDownload(downloadItem);
            FileUtils.write(fileQueueAddress,downloadItem);
        }
        mainFrame.update();
        FileUtils.write(fileProcessingAddress,downloadItem);
    }

    public void exit() {
        mainFrame.setVisible(false);
    }

    public void settingFrame() {
         new SettingsFrame();

    }

    public void downloadSelected(ArrayList<DownloadPanel> selected) {
        mainFrame.getToolBar().downloadSelected();
        mainFrame.getFrameMenuBar().downloadSelected();
        this.selected = selected;
    }

    public void downloadCompletedPSelected(ArrayList<DownloadPanel> selected) {
        mainFrame.getToolBar().downloadCompletedPSelected();
        mainFrame.getFrameMenuBar().downloadCompletedPSelected();
        this.selected = selected;
    }

    public void downloadUnSelected() {
        for (DownloadPanel downloadPanel : selected) {
            downloadPanel.setOpaque(false);
        }
        selected.clear();
        mainFrame.getToolBar().downloadUnSelected();
        mainFrame.getFrameMenuBar().downloadUnSelected();
        mainFrame.update();
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void remove() {
        Iterator<DownloadPanel> it = selected.iterator();
        while (it.hasNext()) {
            DownloadPanel downloadPanel = it.next();

            Iterator iterator = MainFrame.getInstance().getQueuePanel().downloadPanels.iterator();
            while (iterator.hasNext()) {
                DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
                if (downloadPanel.getDownloadItem().equals(downloadPanel1.getDownloadItem())) {
                    MainFrame.getInstance().getQueuePanel().remove(downloadPanel1);
                    iterator.remove();
                    MainFrame.getInstance().getQueuePanel().downloadItems.remove(downloadPanel1.getDownloadItem());
                }
            }
            FileUtils.removeAnObject(fileQueueAddress,downloadPanel.getDownloadItem());

            iterator = MainFrame.getInstance().getProcessingPanel().downloadPanels.iterator();
            while (iterator.hasNext()) {
                DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
                if (downloadPanel.getDownloadItem().equals(downloadPanel1.getDownloadItem())){
                    MainFrame.getInstance().getProcessingPanel().remove(downloadPanel1);
                    iterator.remove();
                    MainFrame.getInstance().getProcessingPanel().downloadItems.remove(downloadPanel1.getDownloadItem());
                    FileUtils.writeRemoved(fileRemovedAddress,downloadPanel1.getDownloadItem());
                }
            }
            FileUtils.removeAnObject(fileProcessingAddress,downloadPanel.getDownloadItem());



            iterator = MainFrame.getInstance().getCompletedPanel().downloadPanels.iterator();
            while (iterator.hasNext()) {
                DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
                if (downloadPanel.getDownloadItem().equals(downloadPanel1.getDownloadItem())){
                    MainFrame.getInstance().getCompletedPanel().remove(downloadPanel1);
                    iterator.remove();
                    MainFrame.getInstance().getCompletedPanel().downloadItems.remove(downloadPanel1.getDownloadItem());
                    FileUtils.writeRemoved(fileRemovedAddress,downloadPanel1.getDownloadItem());
                }
            }
            FileUtils.removeAnObject(fileCompletedAddress,downloadPanel.getDownloadItem());




            it.remove();

        }
        mainFrame.getProcessingPanel().updateLabels();
        mainFrame.getCompletedPanel().updateLabels();
        mainFrame.getQueuePanel().updateLabels();
        mainFrame.getProcessingPanel().reSet();
        mainFrame.getCompletedPanel().reSet();
        mainFrame.getQueuePanel().reSet();
        downloadUnSelected();
    }

    public void cancel() {
        for (DownloadPanel downloadPanel : selected) {
            downloadPanel.getDownloadItem().setDownloadedSize(0);
            downloadPanel.getDownloadItem().computePercent();
            downloadPanel.updateInfo();
        }
        downloadUnSelected();
    }

    public void pause() {
        System.out.println("pause");
    }

    public void resumeDownload() {
        if (selected.size() <= limitDownload) {
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

                        Iterator iterator = MainFrame.getInstance().getQueuePanel().downloadPanels.iterator();
                        while (iterator.hasNext()) {
                            DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
                            System.out.println("resume queue : "+downloadPanel1.getDownloadItem());
                            if (downloadPanel.getDownloadItem().equals(downloadPanel1.getDownloadItem())) {
                                System.out.println("inininininininininininin");
                                MainFrame.getInstance().getQueuePanel().remove(downloadPanel1);
                                iterator.remove();
                                MainFrame.getInstance().getQueuePanel().downloadItems.remove(downloadPanel1.getDownloadItem());
                            }
                        }
                        FileUtils.removeAnObject(fileQueueAddress,downloadPanel.getDownloadItem());

                        iterator = MainFrame.getInstance().getProcessingPanel().downloadPanels.iterator();
                        while (iterator.hasNext()) {
                            DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
                            System.out.println("resume processing : "+downloadPanel1.getDownloadItem());
                            if (downloadPanel.getDownloadItem().equals(downloadPanel1.getDownloadItem())){
                                MainFrame.getInstance().getProcessingPanel().remove(downloadPanel1);
                                iterator.remove();
                                MainFrame.getInstance().getProcessingPanel().downloadItems.remove(downloadPanel1.getDownloadItem());

                            }
                        }
                        FileUtils.removeAnObject(fileProcessingAddress,downloadPanel.getDownloadItem());

                        iterator = MainFrame.getInstance().getCompletedPanel().downloadPanels.iterator();
                        while (iterator.hasNext()) {
                            DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
                            System.out.println("resume completed: "+downloadPanel1.getDownloadItem());
                            if (downloadPanel.getDownloadItem().equals(downloadPanel1.getDownloadItem())){
                                MainFrame.getInstance().getCompletedPanel().remove(downloadPanel1);
                                iterator.remove();
                                MainFrame.getInstance().getCompletedPanel().downloadItems.remove(downloadPanel1.getDownloadItem());
                            }
                        }
                        FileUtils.removeAnObject(fileCompletedAddress,downloadPanel.getDownloadItem());

                        it.remove();

                        mainFrame.getCompletedPanel().addDownload(downloadPanel.getDownloadItem());
                        FileUtils.write(fileCompletedAddress,downloadPanel.getDownloadItem());
                    }
                }
            }
            mainFrame.getProcessingPanel().updateLabels();
            mainFrame.getQueuePanel().updateLabels();
            mainFrame.getCompletedPanel().updateLabels();
            mainFrame.getProcessingPanel().reSet();
            mainFrame.getQueuePanel().reSet();
            mainFrame.getCompletedPanel().reSet();
            mainFrame.update();
            if (selected.isEmpty()) {
                downloadUnSelected();
            } else {
                downloadSelected(selected);
            }
        } else {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Can't download more than " + limitDownload + " file at the same time.\nYou can change limit size in settings.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
