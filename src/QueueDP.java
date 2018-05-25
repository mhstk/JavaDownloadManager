public class QueueDP extends ProcessingDP {
    private ProcessingDP processingDP;
    QueueDP(DownloadItem downloadItem) {
        super(downloadItem);
        inQueue = true ;
    }

    public ProcessingDP getProcessingDP() {
        return processingDP;
    }

    public void setProcessingDP(ProcessingDP processingDP) {
        this.processingDP = processingDP;
    }

    public void setOutPanel() {
        outPanel = MainFrame.getInstance().getQueuePanel();
    }
}
