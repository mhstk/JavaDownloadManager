import java.awt.event.MouseEvent;

public class CompletedPanel extends CenterPanel {


    CompletedPanel() {
        super();
        myMouseListener = new MyMouseListener();

    }

    void addDownload(DownloadItem downloadItem) {
        CompletedDP downloadPanel = new CompletedDP(downloadItem);
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
            System.out.println("opening");

        }

    }
}

