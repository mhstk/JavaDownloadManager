import java.awt.*;
import java.awt.event.MouseEvent;

public class ProcessingPanel extends CenterPanel {


    ProcessingPanel() {
        super();
        myMouseListener = new MyMouseListener();
    }

    public void addDownload(DownloadItem downloadItem) {
        ProcessingDP downloadPanel = new ProcessingDP(downloadItem);
        super.addDownload(downloadPanel);
    }


    class MyMouseListener extends CenterPanel.MyMouseListener {

//        @Override
//        public void mouseEntered(MouseEvent e) {
//            DownloadPanel downloadPanel = (DownloadPanel) e.getSource();
//            if (!selected.contains(downloadPanel)){
//                downloadPanel.setBackground(new Color(7,38,51));
//                downloadPanel.setOpaque(true);
//                updateUI();
//                revalidate();
//                updateUI();
//                //MainFrame.getInstance().update();
//            }
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {
//            DownloadPanel downloadPanel = (DownloadPanel) e.getSource();
//            if (!selected.contains(downloadPanel)){
//                downloadPanel.setOpaque(false);
//                revalidate();
//                updateUI();
//                //MainFrame.getInstance().update();
//            }
//        }
//
//        public void mouseClicked(MouseEvent e) {
//            ProcessingDP downloadPanel = (ProcessingDP) e.getSource();
//            if (e.isMetaDown()) {
//                InfoFrame infoFrame = new InfoFrame();
//                infoFrame.setDownloadItem(downloadPanel);
//                infoFrame.action();
//            } else {
//
//                if (selected.contains(downloadPanel)) {
//                    selected.remove(downloadPanel);
//                    downloadPanel.setBackground(new Color(7,38,51));
//                    downloadPanel.setOpaque(true);
//                    revalidate();
//                    updateUI();
//                   // MainFrame.getInstance().update();
//                } else {
//                    selected.add(downloadPanel);
//                    System.out.println("hi");
//                    downloadPanel.setBackground(new Color(94, 138, 158));
//                    downloadPanel.setOpaque(true);
//                    revalidate();
//                    updateUI();
//                    //MainFrame.getInstance().update();
//                }
//
//            }
//
//            if (selected.isEmpty()) {
//                Manager.getInstance().downloadUnSelected();
//            } else {
//                Manager.getInstance().downloadSelected(selected);
//            }
//        }
    }


}