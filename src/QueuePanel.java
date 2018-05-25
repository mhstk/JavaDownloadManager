import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class QueuePanel extends CenterPanel {
    QueuePanel() {
        super();
        myMouseListener = new MyMouseListener();
    }

    public void addDownload(DownloadItem downloadItem) {
        ProcessingDP downloadPanel = new ProcessingDP(downloadItem);
        super.addDownload(downloadPanel);
    }


    class MyMouseListener extends CenterPanel.MyMouseListener {

    }
}
