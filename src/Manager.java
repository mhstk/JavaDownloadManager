import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

public class Manager {
    private MainFrame mainFrame;
    private static Manager manager;
    public ArrayList<DownloadPanel> selected;
    private String directory;
    private int limitDownload;
    private int maxDownload;
    private String lookAndFeelS;
    private String fileProcessingAddress;
    private String fileCompletedAddress;
    private String fileQueueAddress;
    private String fileSettingsAddress;
    private String fileRemovedAddress;
    private ArrayList<String> filter;
    private HashMap<DownloadItem, HttpDownloadUtility> downloading;
    private String center;
    private String language;
    private ArrayList<String> words ;

    private Manager(String lookAndFeelS, int limitDownload, String directory, ArrayList<String> filter) {

        this.directory = directory;
        maxDownload = 100;
        this.limitDownload = limitDownload;
        this.lookAndFeelS = lookAndFeelS;
        this.filter = filter;
        fileProcessingAddress = "files\\processing.jdm";
        fileCompletedAddress = "files\\completed.jdm";
        fileQueueAddress = "files\\queue.jdm";
        fileSettingsAddress = "files\\settings.jdm";
        fileRemovedAddress = "files\\removed.jdm";
        selected = new ArrayList<>();
        downloading = new HashMap<>();
        center = "Processing";
        language = "English";
        words = FileUtils.readLanguage(language);

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
        SwingUtilities.updateComponentTreeUI(mainFrame);
        mainFrame.revalidate();
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
            manager = new Manager(FileUtils.readLF(), FileUtils.readlimit(), FileUtils.readDirectory(), FileUtils.readFiltered());
        }
        return manager;
    }

    public void start() {

        ArrayList<DownloadItem> downloadItems = FileUtils.read(fileProcessingAddress);
        for (int i = 0; i < downloadItems.size(); i++) {
            mainFrame.getProcessingPanel().addDownload(downloadItems.get(i));
            ProcessingDP processingDP = (ProcessingDP) mainFrame.getProcessingPanel().downloadPanels.get(i);
            processingDP.pauseIcon();
        }
        mainFrame.getProcessingPanel().revalidate();
        downloadItems.clear();


        downloadItems = FileUtils.read(fileCompletedAddress);
        for (int i = 0; i < downloadItems.size(); i++) {
            mainFrame.getCompletedPanel().addDownload(downloadItems.get(i));
        }
        mainFrame.getCompletedPanel().revalidate();
        downloadItems.clear();


        downloadItems = FileUtils.read(fileQueueAddress);
        for (int i = 0; i < downloadItems.size(); i++) {
            mainFrame.getQueuePanel().addDownload(downloadItems.get(i));
            ProcessingDP processingDP = (ProcessingDP) mainFrame.getQueuePanel().downloadPanels.get(i);
            processingDP.pauseIcon();
        }
        mainFrame.getQueuePanel().revalidate();

        Sort.sortByNameAndSize(1);


    }

    public void newDownload() {
        new NewDownFrame();
    }

    public void addNewDownload(NewDownFrame newDownFrame, boolean toQueue) {
        selected.clear();
        downloadUnSelected();

        DownloadItem downloadItem = new DownloadItem(newDownFrame.getDownloadName());
        if (toQueue) {
            downloadItem.inQueue = true;
        }
        downloadItem.setInfo(newDownFrame.getDownloadSize(), 0.0, 0, newDownFrame.getServerAddress(), newDownFrame.getSavedAddress());
        downloadItem.setStartedTime(new Date());
        mainFrame.getProcessingPanel().addDownload(downloadItem);

        if (toQueue) {
            mainFrame.getQueuePanel().addDownload(downloadItem);
            FileUtils.write(fileQueueAddress, downloadItem);
            ProcessingDP processingDP = (ProcessingDP) mainFrame.getProcessingPanel().downloadPanels.get(mainFrame.getProcessingPanel().downloadPanels.size() - 1);
            processingDP.pauseIcon();
            processingDP = (ProcessingDP) mainFrame.getQueuePanel().downloadPanels.get(mainFrame.getQueuePanel().downloadPanels.size() - 1);
            processingDP.pauseIcon();
        }
        FileUtils.write(fileProcessingAddress, downloadItem);

        if (!toQueue) {
            if (downloading.keySet().size() + 1 <= limitDownload) {
                HttpDownloadUtility httpDownloadUtility = new HttpDownloadUtility();
                httpDownloadUtility.downloadFile(mainFrame.getProcessingPanel().downloadPanels.get(mainFrame.getProcessingPanel().downloadPanels.size() - 1), newDownFrame.getServerAddress(), newDownFrame.getSavedAddress(), (long) (newDownFrame.getDownloadSize() * 1000000.0));
                httpDownloadUtility.execute();
                downloading.put(downloadItem, httpDownloadUtility);
            } else {
                ProcessingDP processingDP = (ProcessingDP) mainFrame.getProcessingPanel().downloadPanels.get(mainFrame.getProcessingPanel().downloadPanels.size() - 1);
                processingDP.pauseIcon();
            }
        }
        mainFrame.update();
    }

    public void exit() {
        mainFrame.setVisible(false);
        FileUtils.clear(fileProcessingAddress);
        for (DownloadPanel downloadPanel : mainFrame.getProcessingPanel().downloadPanels) {
            FileUtils.write(fileProcessingAddress, downloadPanel.getDownloadItem());
        }
        FileUtils.clear(fileQueueAddress);
        for (DownloadPanel downloadPanel : mainFrame.getQueuePanel().downloadPanels) {
            FileUtils.write(fileQueueAddress, downloadPanel.getDownloadItem());
        }
        FileUtils.clear(fileCompletedAddress);
        for (DownloadPanel downloadPanel : mainFrame.getCompletedPanel().downloadPanels) {
            FileUtils.write(fileCompletedAddress, downloadPanel.getDownloadItem());
        }
    }

    public void settingFrame() {
        new SettingsFrame();

    }

    public void downloadSelected(ArrayList<DownloadPanel> selected) {
        int i = 0;
        for (DownloadPanel downloadPanel : selected) {
            if (downloadPanel.getDownloadItem().inQueue) {
                i++;
            }
        }
        if (i == selected.size()) {
            downloadQueuePSelected(selected);
        } else {
            mainFrame.getToolBar().downloadSelected();
            mainFrame.getFrameMenuBar().downloadSelected();
            this.selected = selected;
        }
    }

    public void downloadCompletedPSelected(ArrayList<DownloadPanel> selected) {
        mainFrame.getToolBar().downloadCompletedPSelected();
        mainFrame.getFrameMenuBar().downloadCompletedPSelected();
        this.selected = selected;
    }

    public void downloadQueuePSelected(ArrayList<DownloadPanel> selected) {
        mainFrame.getToolBar().downloadQueuePSelected();
        mainFrame.getFrameMenuBar().downloadQueuePSelected();
        this.selected = selected;
    }

    public void downloadUnSelected() {
        for (DownloadPanel downloadPanel : selected) {
            downloadPanel.setOpaque(false);
        }
        selected.clear();
        if (center.equals("Queue")) {
            mainFrame.getToolBar().downloadUnSelectedInQ();
        } else {
            mainFrame.getToolBar().downloadUnSelected();
        }
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
            FileUtils.removeAnObject(fileQueueAddress, downloadPanel.getDownloadItem());

            iterator = MainFrame.getInstance().getProcessingPanel().downloadPanels.iterator();
            while (iterator.hasNext()) {
                DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
                if (downloadPanel.getDownloadItem().equals(downloadPanel1.getDownloadItem())) {
                    MainFrame.getInstance().getProcessingPanel().remove(downloadPanel1);
                    iterator.remove();
                    MainFrame.getInstance().getProcessingPanel().downloadItems.remove(downloadPanel1.getDownloadItem());
                    FileUtils.writeRemoved(fileRemovedAddress, downloadPanel1.getDownloadItem());
                }
            }
            FileUtils.removeAnObject(fileProcessingAddress, downloadPanel.getDownloadItem());


            iterator = MainFrame.getInstance().getCompletedPanel().downloadPanels.iterator();
            while (iterator.hasNext()) {
                DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
                if (downloadPanel.getDownloadItem().equals(downloadPanel1.getDownloadItem())) {
                    MainFrame.getInstance().getCompletedPanel().remove(downloadPanel1);
                    iterator.remove();
                    MainFrame.getInstance().getCompletedPanel().downloadItems.remove(downloadPanel1.getDownloadItem());
                    FileUtils.writeRemoved(fileRemovedAddress, downloadPanel1.getDownloadItem());
                }
            }
            FileUtils.removeAnObject(fileCompletedAddress, downloadPanel.getDownloadItem());
            if (downloading.keySet().contains(downloadPanel.getDownloadItem())) {
                downloading.get(downloadPanel.getDownloadItem()).pause();
                downloading.remove(downloadPanel.getDownloadItem());
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        new File(directory + File.separator + downloadPanel.getDownloadItem().getName()).delete();
                    }
                };
                thread.start();
            } else {
                new File(directory + File.separator + downloadPanel.getDownloadItem().getName()).delete();
            }

            it.remove();

        }

//        mainFrame.getProcessingPanel().updateLabels();
//        mainFrame.getQueuePanel().updateLabels();
        mainFrame.getProcessingPanel().reSet();
        mainFrame.getCompletedPanel().reSet();
        mainFrame.getQueuePanel().reSet();
        selected.clear();
        downloadUnSelected();
    }

    public void cancel() {
        for (DownloadPanel downloadPanel : selected) {

            if (downloading.keySet().contains(downloadPanel.getDownloadItem())) {
                downloading.get(downloadPanel.getDownloadItem()).pause();
                downloading.remove(downloadPanel.getDownloadItem());
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Iterator iterator = MainFrame.getInstance().getQueuePanel().downloadPanels.iterator();
                        while (iterator.hasNext()) {
                            DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
                            if (downloadPanel.getDownloadItem().equals(downloadPanel1.getDownloadItem())) {
                                downloadPanel1.getDownloadItem().setDownloadedSize(0);
                                ProcessingDP processingDP = (ProcessingDP) downloadPanel1;
                                processingDP.cancelIcon();
                                downloadPanel1.updateInfo();
                            }
                        }
                        FileUtils.removeAnObject(fileQueueAddress, downloadPanel.getDownloadItem());

                        iterator = MainFrame.getInstance().getProcessingPanel().downloadPanels.iterator();
                        while (iterator.hasNext()) {
                            DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
                            if (downloadPanel.getDownloadItem().equals(downloadPanel1.getDownloadItem())) {
                                downloadPanel1.getDownloadItem().setDownloadedSize(0);
                                ProcessingDP processingDP = (ProcessingDP) downloadPanel1;
                                processingDP.cancelIcon();
                                downloadPanel1.updateInfo();
                            }
                        }
                        FileUtils.removeAnObject(fileProcessingAddress, downloadPanel.getDownloadItem());
                        new File(directory + File.separator + downloadPanel.getDownloadItem().getName()).delete();
                    }
                };
                thread.start();
            } else {
                Iterator iterator = MainFrame.getInstance().getQueuePanel().downloadPanels.iterator();
                while (iterator.hasNext()) {
                    DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
                    if (downloadPanel.getDownloadItem().equals(downloadPanel1.getDownloadItem())) {
                        downloadPanel1.getDownloadItem().setDownloadedSize(0);
                        ProcessingDP processingDP = (ProcessingDP) downloadPanel1;
                        processingDP.cancelIcon();
                        downloadPanel1.updateInfo();
                    }
                }
                FileUtils.removeAnObject(fileQueueAddress, downloadPanel.getDownloadItem());

                iterator = MainFrame.getInstance().getProcessingPanel().downloadPanels.iterator();
                while (iterator.hasNext()) {
                    DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
                    if (downloadPanel.getDownloadItem().equals(downloadPanel1.getDownloadItem())) {
                        downloadPanel1.getDownloadItem().setDownloadedSize(0);
                        ProcessingDP processingDP = (ProcessingDP) downloadPanel1;
                        processingDP.cancelIcon();
                        downloadPanel1.updateInfo();
                    }
                }
                FileUtils.removeAnObject(fileProcessingAddress, downloadPanel.getDownloadItem());
                new File(directory + File.separator + downloadPanel.getDownloadItem().getName()).delete();
            }


        }
        FileUtils.writeAll(fileProcessingAddress, mainFrame.getProcessingPanel().downloadPanels);
        FileUtils.writeAll(fileQueueAddress, mainFrame.getQueuePanel().downloadPanels);
        mainFrame.getProcessingPanel().reSet();
        mainFrame.getQueuePanel().reSet();
        mainFrame.getCompletedPanel().reSet();
//        selected.clear();
//        downloadUnSelected();
    }

    public void pause() {
        if (selected.isEmpty()) {
            if (!mainFrame.getQueuePanel().downloadPanels.isEmpty()) {
                ProcessingDP processingDP = (ProcessingDP) mainFrame.getQueuePanel().downloadPanels.get(0);
                DownloadPanel downloadPanel = null;
                for (DownloadPanel downloadPanel1 : mainFrame.getProcessingPanel().downloadPanels) {
                    if (processingDP.getDownloadItem().equals(downloadPanel1.getDownloadItem())) {
                        downloadPanel = downloadPanel1;
                    }
                }
                if (downloading.keySet().contains(downloadPanel.getDownloadItem())) {
                    downloading.get(downloadPanel.getDownloadItem()).pause();
                    downloading.remove(downloadPanel.getDownloadItem());
                    processingDP.pauseIcon();
                    ProcessingDP processingDP1 = (ProcessingDP) downloadPanel;
                    processingDP1.pauseIcon();
                }
            }
        } else {
            for (DownloadPanel downloadPanel : selected) {
                if (downloadPanel.getDownloadItem().inQueue) {
                    JOptionPane.showMessageDialog(MainFrame.getInstance(), "One or some of download you selected is(are) in queue.\nIf you want to stop it(them) you should stop the queue.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            for (DownloadPanel downloadPanel : selected) {
                ProcessingDP processingDP = ((ProcessingDP) downloadPanel);

                if (downloading.keySet().contains(downloadPanel.getDownloadItem())) {
                    downloading.get(downloadPanel.getDownloadItem()).pause();
                    downloading.remove(downloadPanel.getDownloadItem());
                }

                for (DownloadPanel downloadPanel1 : mainFrame.getProcessingPanel().downloadPanels) {
                    ProcessingDP processingDP1 = (ProcessingDP) downloadPanel1;
                    if (processingDP.getDownloadItem().equals(processingDP1.getDownloadItem())) {
                        processingDP1.pauseIcon();

                    }
                }

                for (DownloadPanel downloadPanel1 : mainFrame.getQueuePanel().downloadPanels) {
                    ProcessingDP processingDP1 = (ProcessingDP) downloadPanel1;
                    if (processingDP.getDownloadItem().equals(processingDP1.getDownloadItem())) {
                        processingDP1.pauseIcon();
                    }
                }
            }
        }
        FileUtils.writeAll(fileProcessingAddress, mainFrame.getProcessingPanel().downloadPanels);
        FileUtils.writeAll(fileQueueAddress, mainFrame.getQueuePanel().downloadPanels);


    }

    public void completed(DownloadItem downloadItem) {
        Iterator iterator = MainFrame.getInstance().getQueuePanel().downloadPanels.iterator();
        while (iterator.hasNext()) {
            DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
            if (downloadItem.equals(downloadPanel1.getDownloadItem())) {
                MainFrame.getInstance().getQueuePanel().remove(downloadPanel1);
                iterator.remove();
                MainFrame.getInstance().getQueuePanel().downloadItems.remove(downloadPanel1.getDownloadItem());
            }
        }
        FileUtils.removeAnObject(fileQueueAddress, downloadItem);

        iterator = MainFrame.getInstance().getProcessingPanel().downloadPanels.iterator();
        while (iterator.hasNext()) {
            DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
            if (downloadItem.equals(downloadPanel1.getDownloadItem())) {
                MainFrame.getInstance().getProcessingPanel().remove(downloadPanel1);
                iterator.remove();
                MainFrame.getInstance().getProcessingPanel().downloadItems.remove(downloadPanel1.getDownloadItem());

            }
        }
        downloading.remove(downloadItem);
        FileUtils.removeAnObject(fileProcessingAddress, downloadItem);

        iterator = selected.iterator();
        while (iterator.hasNext()){
            DownloadPanel downloadPanel = (DownloadPanel) iterator.next();
            if (downloadPanel.getDownloadItem().equals(downloadItem)){
                iterator.remove();
            }
        }

//        iterator = MainFrame.getInstance().getCompletedPanel().downloadPanels.iterator();
//        while (iterator.hasNext()) {
//            DownloadPanel downloadPanel1 = (DownloadPanel) iterator.next();
//            if (downloadItem.equals(downloadPanel1.getDownloadItem())) {
//                MainFrame.getInstance().getCompletedPanel().remove(downloadPanel1);
//                iterator.remove();
//                MainFrame.getInstance().getCompletedPanel().downloadItems.remove(downloadPanel1.getDownloadItem());
//            }
//        }
//        FileUtils.removeAnObject(fileCompletedAddress, downloadItem);


        mainFrame.getCompletedPanel().addDownload(downloadItem);
        FileUtils.write(fileCompletedAddress, downloadItem);


        mainFrame.getToolBar().sort((String) mainFrame.getToolBar().getSortMode().getSelectedItem(), mainFrame.getToolBar().getCounter());

        if (downloadItem.inQueue) {
            resumeInQueue();
        }
        try {

            mainFrame.getProcessingPanel().updateLabels();
            mainFrame.getQueuePanel().updateLabels();
            mainFrame.getProcessingPanel().reSet();
            mainFrame.getQueuePanel().reSet();
            mainFrame.getCompletedPanel().reSet();
        } catch (Exception e) {

        }
    }

    public ArrayList<String> getFilter() {
        return filter;
    }

    public void setFilter(ArrayList<String> filter) {
        this.filter = filter;
    }

    public void resumeInQueue() {
        if (!mainFrame.getQueuePanel().downloadPanels.isEmpty()) {
            ProcessingDP processingDP = (ProcessingDP) mainFrame.getQueuePanel().downloadPanels.get(0);
            DownloadPanel downloadPanel = null;
            for (DownloadPanel downloadPanel1 : mainFrame.getProcessingPanel().downloadPanels) {
                if (processingDP.getDownloadItem().equals(downloadPanel1.getDownloadItem())) {
                    downloadPanel = downloadPanel1;
                }
            }
            if (downloading.keySet().size() + 1 <= limitDownload) {
                HttpDownloadUtility httpDownloadUtility = new HttpDownloadUtility();
                httpDownloadUtility.downloadFile(downloadPanel, downloadPanel.getDownloadItem().serverAddress, downloadPanel.getDownloadItem().savedAddress, (long) (downloadPanel.getDownloadItem().size * 1000000.0));
                httpDownloadUtility.execute();
                downloading.put(downloadPanel.getDownloadItem(), httpDownloadUtility);
                processingDP.resumeIcon();
                ProcessingDP processingDP1 = (ProcessingDP) downloadPanel;
                processingDP1.resumeIcon();
            } else {
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "Can't download more than " + limitDownload + " file at the same time.\nYou can change limit size in settings.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public void resumeDownload() {
        if (selected.isEmpty()) {
            resumeInQueue();
        } else {
            for (DownloadPanel downloadPanel : selected) {
                if (downloadPanel.getDownloadItem().inQueue) {
                    JOptionPane.showMessageDialog(MainFrame.getInstance(), "One or some of download you selected is(are) in queue.\nIf you want to download it(them) you should start the queue.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (downloading.keySet().size() + selected.size() <= limitDownload) {
                Iterator<DownloadPanel> it = selected.iterator();
                while (it.hasNext()) {
                    DownloadPanel downloadPanel = it.next();
                    HttpDownloadUtility httpDownloadUtility = new HttpDownloadUtility();
                    httpDownloadUtility.downloadFile(downloadPanel, downloadPanel.getDownloadItem().serverAddress, downloadPanel.getDownloadItem().savedAddress, (long) (downloadPanel.getDownloadItem().size * 1000000.0));
                    httpDownloadUtility.execute();
                    downloading.put(downloadPanel.getDownloadItem(), httpDownloadUtility);

                    ProcessingDP processingDP = ((ProcessingDP) downloadPanel);

                    for (DownloadPanel downloadPanel1 : mainFrame.getProcessingPanel().downloadPanels) {
                        ProcessingDP processingDP1 = (ProcessingDP) downloadPanel1;
                        if (processingDP.getDownloadItem().equals(processingDP1.getDownloadItem())) {
                            processingDP1.resumeIcon();

                        }
                    }
                    for (DownloadPanel downloadPanel1 : mainFrame.getQueuePanel().downloadPanels) {
                        ProcessingDP processingDP1 = (ProcessingDP) downloadPanel1;
                        if (processingDP.getDownloadItem().equals(processingDP1.getDownloadItem())) {
                            processingDP1.resumeIcon();
                        }
                    }


                }
                mainFrame.getProcessingPanel().reSet();
                mainFrame.getQueuePanel().reSet();
                mainFrame.getCompletedPanel().reSet();
//            mainFrame.update();
//            selected.clear();
//            downloadUnSelected();
            } else {
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "Can't download more than " + limitDownload + " file at the same time.\nYou can change limit size in settings.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }


    }

    public void moveUpInQueue(){
        Sort.sortSelectedQueue(selected);
        if (!(selected.get(0)== mainFrame.getQueuePanel().downloadPanels.get(0))){
            for (int i=0 ; i<selected.size() ; i++){
                int j = mainFrame.getQueuePanel().downloadPanels.indexOf(selected.get(i));
                Collections.swap(mainFrame.getQueuePanel().downloadPanels,j,j-1);
            }
        }
        mainFrame.getQueuePanel().reSet();
    }
    public void moveDownInQueue(){
        Sort.sortSelectedQueue(selected);
        if (!(selected.get(selected.size()-1) == mainFrame.getQueuePanel().downloadPanels.get(mainFrame.getQueuePanel().downloadPanels.size()-1))){
            for (int i=selected.size()-1 ; i>=0 ; i--){
                int j = mainFrame.getQueuePanel().downloadPanels.indexOf(selected.get(i));
                Collections.swap(mainFrame.getQueuePanel().downloadPanels,j,j+1);
            }
        }
        mainFrame.getQueuePanel().reSet();
    }

    public void changeLangage(){
        words = FileUtils.readLanguage(language);
        mainFrame.getMenu().changeLanguage();
        mainFrame.getFrameMenuBar().changeLanguage();

    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }
}
