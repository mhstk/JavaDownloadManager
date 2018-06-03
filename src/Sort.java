import java.util.ArrayList;
import java.util.Collections;

public class Sort {

    private static Manager manager = Manager.getInstance();
    private static MainFrame mainFrame = MainFrame.getInstance();

    public static void sortByName(ArrayList<DownloadPanel> downloadPanels, int a) {
        for (int i = 0; i < downloadPanels.size(); i++) {
            for (int j = 0; j < downloadPanels.size() - 1; j++) {
                if (downloadPanels.get(j).getDownloadItem().getName().compareTo(downloadPanels.get(j + 1).getDownloadItem().getName()) * a > 0) {
                    Collections.swap(downloadPanels, j, j + 1);
                }
            }
        }
    }

    public static void sortBySize(ArrayList<DownloadPanel> downloadPanels, int a) {
        for (int i = 0; i < downloadPanels.size(); i++) {
            for (int j = 0; j < downloadPanels.size() - 1; j++) {
                if ((downloadPanels.get(j).getDownloadItem().getSize() - downloadPanels.get(j + 1).getDownloadItem().getSize()) * a > 0) {
                    Collections.swap(downloadPanels, j, j + 1);
                }
            }
        }
    }

    public static void sortByDate(ArrayList<DownloadPanel> downloadPanels, int a) {
        for (int i = 0; i < downloadPanels.size(); i++) {
            for (int j = 0; j < downloadPanels.size() - 1; j++) {
                if ((downloadPanels.get(j).getDownloadItem().getStartedTime().compareTo(downloadPanels.get(j + 1).getDownloadItem().getStartedTime())) * a > 0) {
                    Collections.swap(downloadPanels, j, j + 1);
                }
            }
        }
    }


    public static void sortByNameAndDate(ArrayList<DownloadPanel> downloadPanels, int a) {
        for (int i = 0; i < downloadPanels.size(); i++) {
            for (int j = 0; j < downloadPanels.size() - 1; j++) {
                if (downloadPanels.get(j).getDownloadItem().getName().compareTo(downloadPanels.get(j + 1).getDownloadItem().getName()) * a > 0) {
                    Collections.swap(downloadPanels, j, j + 1);
                } else if (downloadPanels.get(j).getDownloadItem().getName().compareTo(downloadPanels.get(j + 1).getDownloadItem().getName()) * a == 0) {
                    if ((downloadPanels.get(j).getDownloadItem().getStartedTime().compareTo(downloadPanels.get(j + 1).getDownloadItem().getStartedTime())) * a > 0) {
                        Collections.swap(downloadPanels, j, j + 1);
                    }
                }

            }
        }
    }

    public static void sortBySizeAndDate(ArrayList<DownloadPanel> downloadPanels, int a) {
        for (int i = 0; i < downloadPanels.size(); i++) {
            for (int j = 0; j < downloadPanels.size() - 1; j++) {
                if ((downloadPanels.get(j).getDownloadItem().getSize() - downloadPanels.get(j + 1).getDownloadItem().getSize()) * a > 0) {
                    Collections.swap(downloadPanels, j, j + 1);
                } else if ((downloadPanels.get(j).getDownloadItem().getSize() - downloadPanels.get(j + 1).getDownloadItem().getSize()) * a == 0) {
                    if ((downloadPanels.get(j).getDownloadItem().getStartedTime().compareTo(downloadPanels.get(j + 1).getDownloadItem().getStartedTime())) * a > 0) {
                        Collections.swap(downloadPanels, j, j + 1);
                    }
                }

            }
        }
    }

    public static void sortByNameAndSize(ArrayList<DownloadPanel> downloadPanels, int a) {
        for (int i = 0; i < downloadPanels.size(); i++) {
            for (int j = 0; j < downloadPanels.size() - 1; j++) {
                if (downloadPanels.get(j).getDownloadItem().getName().compareTo(downloadPanels.get(j + 1).getDownloadItem().getName()) * a > 0) {
                    Collections.swap(downloadPanels, j, j + 1);
                } else if (downloadPanels.get(j).getDownloadItem().getName().compareTo(downloadPanels.get(j + 1).getDownloadItem().getName()) * a == 0) {
                    if ((downloadPanels.get(j).getDownloadItem().getSize() - downloadPanels.get(j + 1).getDownloadItem().getSize()) * a > 0) {
                        Collections.swap(downloadPanels, j, j + 1);
                    }
                }

            }
        }
    }

    public static void sortByNameAndSizeAndDate(ArrayList<DownloadPanel> downloadPanels, int a) {
        for (int i = 0; i < downloadPanels.size(); i++) {
            for (int j = 0; j < downloadPanels.size() - 1; j++) {
                if (downloadPanels.get(j).getDownloadItem().getName().compareTo(downloadPanels.get(j + 1).getDownloadItem().getName()) * a > 0) {
                    Collections.swap(downloadPanels, j, j + 1);

                } else if (downloadPanels.get(j).getDownloadItem().getName().compareTo(downloadPanels.get(j + 1).getDownloadItem().getName()) * a == 0) {
                    if ((downloadPanels.get(j).getDownloadItem().getSize() - downloadPanels.get(j + 1).getDownloadItem().getSize()) * a > 0) {
                        Collections.swap(downloadPanels, j, j + 1);
                    } else if ((downloadPanels.get(j).getDownloadItem().getSize() - downloadPanels.get(j + 1).getDownloadItem().getSize()) * a == 0){
                        if ((downloadPanels.get(j).getDownloadItem().getStartedTime().compareTo(downloadPanels.get(j + 1).getDownloadItem().getStartedTime())) * a > 0) {
                            Collections.swap(downloadPanels, j, j + 1);
                        }
                    }
                }

            }
        }
    }


    public static void sortByNameAndSizeAndDate(int a) {
        sortByNameAndSizeAndDate(mainFrame.getProcessingPanel().downloadPanels, a);
        sortByNameAndSizeAndDate(mainFrame.getCompletedPanel().downloadPanels, a);
        sortByNameAndSizeAndDate(mainFrame.getQueuePanel().downloadPanels, a);
        mainFrame.getProcessingPanel().reSet();
        mainFrame.getCompletedPanel().reSet();
        mainFrame.getQueuePanel().reSet();
    }


    public static void sortByNameAndSize(int a) {
        sortByNameAndSize(mainFrame.getProcessingPanel().downloadPanels, a);
        sortByNameAndSize(mainFrame.getCompletedPanel().downloadPanels, a);
        mainFrame.getProcessingPanel().reSet();
        mainFrame.getCompletedPanel().reSet();
    }


    public static void sortBySizeAndDate(int a) {
        sortBySizeAndDate(mainFrame.getProcessingPanel().downloadPanels, a);
        sortBySizeAndDate(mainFrame.getCompletedPanel().downloadPanels, a);
        sortBySizeAndDate(mainFrame.getQueuePanel().downloadPanels, a);
        mainFrame.getProcessingPanel().reSet();
        mainFrame.getCompletedPanel().reSet();
        mainFrame.getQueuePanel().reSet();
    }


    public static void sortByNameAndDate(int a) {
        sortByNameAndDate(mainFrame.getProcessingPanel().downloadPanels, a);
        sortByNameAndDate(mainFrame.getCompletedPanel().downloadPanels, a);
        mainFrame.getProcessingPanel().reSet();
        mainFrame.getCompletedPanel().reSet();
    }


    public static void sortByName(int a) {
        sortByName(mainFrame.getProcessingPanel().downloadPanels, a);
        sortByName(mainFrame.getCompletedPanel().downloadPanels, a);
        mainFrame.getProcessingPanel().reSet();
        mainFrame.getCompletedPanel().reSet();
    }

    public static void sortBySize(int a) {
        sortBySize(mainFrame.getProcessingPanel().downloadPanels, a);
        sortBySize(mainFrame.getCompletedPanel().downloadPanels, a);
        mainFrame.getProcessingPanel().reSet();
        mainFrame.getCompletedPanel().reSet();

    }

    public static void sortByDate(int a) {
        sortByDate(mainFrame.getProcessingPanel().downloadPanels, a);
        sortByDate(mainFrame.getCompletedPanel().downloadPanels, a);
        mainFrame.getProcessingPanel().reSet();
        mainFrame.getCompletedPanel().reSet();

    }


    public static void sortSelectedQueue(ArrayList<DownloadPanel> downloadPanels) {
        for (int i = 0; i < downloadPanels.size(); i++) {
            for (int j = 0; j < downloadPanels.size() - 1; j++) {
                if (mainFrame.getQueuePanel().downloadPanels.indexOf(downloadPanels.get(j)) > mainFrame.getQueuePanel().downloadPanels.indexOf(downloadPanels.get(j+1))) {
                    Collections.swap(downloadPanels, j, j + 1);
                }
            }
        }
    }


}
