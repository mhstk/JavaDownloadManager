import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class CompletedPanel extends CenterPanel {


    CompletedPanel() {
        super();
        myMouseListener = new MyMouseListener();

    }

    void addDownload(DownloadItem downloadItem) {
        CompletedDP downloadPanel = new CompletedDP(downloadItem);
        downloadPanel.sizeL.setText(String.format("%.2f",downloadItem.getSize())+" (mb)");
        super.addDownload(downloadPanel);
    }

    @Override
    public void selected() {
        Manager.getInstance().downloadCompletedPSelected(selected);
    }


    class MyMouseListener extends CenterPanel.MyMouseListener {
        @Override
        public void leftDoubleClicked(MouseEvent e) {
            super.leftDoubleClicked(e);
            DownloadPanel downloadPanel = (DownloadPanel) e.getSource();
            File file = new File(downloadPanel.getDownloadItem().savedAddress+File.separator+downloadPanel.getDownloadItem().getName());
            if (file.exists()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }


        }

    }
}

