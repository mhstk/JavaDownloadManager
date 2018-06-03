import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpDownloadUtility extends SwingWorker {
    private static final int BUFFER_SIZE = 4096;
    private String fileURL;
    private String saveDir;
    private boolean pause;
    private long size;
    private DownloadPanel downloadPanel;
    private DownloadPanel QDownloadPanel = null;


    public void downloadFile(DownloadPanel downloadPanel, String fileURL, String saveDir, long size) {
        this.fileURL = fileURL;
        this.saveDir = saveDir;
        this.downloadPanel = downloadPanel;
        this.size = size;
        pause = false;
        System.out.println(size);
        for (DownloadPanel downloadPanel1 : MainFrame.getInstance().getQueuePanel().downloadPanels) {
            if (downloadPanel1.getDownloadItem().equals(downloadPanel.getDownloadItem())) {
                this.QDownloadPanel = downloadPanel1;
                break;
            }
        }


    }

    @Override
    protected Boolean doInBackground() throws Exception {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        String saveFilePath = saveDir + File.separator + downloadPanel.getDownloadItem().getName();
        File file = new File(saveFilePath);
        System.out.println(file.length());
        httpConn.setRequestProperty("Range", "bytes=" + file.length() + "-" + size);
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode / 100 == 2) {
            double percent;
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();


            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = downloadPanel.getDownloadItem().getName();
            }

            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("Size-Length = " + size);
            System.out.println("fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();


            // opens an output stream to save into file
            FileOutputStream outputStream;
            if (file.length() != 0) {
                outputStream = new FileOutputStream(saveFilePath, true);
            } else {
                outputStream = new FileOutputStream(saveFilePath);
            }

            double speed;
            long start;
            long end;
            long totalPerSecond = 0;
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            start = System.nanoTime();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                end = System.nanoTime();
                // System.out.println(end);
                if (end - start >= 100000000) {
                    totalPerSecond = file.length() - totalPerSecond;
                    speed = (double) (totalPerSecond) * 10.0 / 1000.0;
                    downloadPanel.speedL.setText(String.format("%.2f (kb/s)", speed));
                    if (QDownloadPanel != null) {
                        QDownloadPanel.speedL.setText(String.format("%.2f (kb/s)", speed));
                    }
                    totalPerSecond = file.length();
                    start = System.nanoTime();

                }
                percent = (double) (file.length()) * 100.0 / size;
                downloadPanel.getDownloadItem().setDownloadedSize(file.length());
                downloadPanel.percentL.setText(String.format("%.2f%%", percent));
                downloadPanel.sizeL.setText(String.format("%.2f", file.length() / 1000000.0) + " / " + String.format("%.2f", size / 1000000.0) + " (mb)");
                downloadPanel.jProgressBar.setValue((int) percent * 100);
                if (QDownloadPanel != null) {
                    QDownloadPanel.getDownloadItem().setDownloadedSize(file.length() / 1000000.0);
                    QDownloadPanel.percentL.setText(String.format("%.2f%%", percent));
                    QDownloadPanel.sizeL.setText(String.format("%.2f", file.length() / 1000000.0) + " / " + String.format("%.2f", size / 1000000.0) + " (mb)");
                    QDownloadPanel.jProgressBar.setValue((int) percent * 100);
                }

                outputStream.write(buffer, 0, bytesRead);
                if (pause) {
                    break;
                }
            }
            System.out.println("out");

            outputStream.close();
            inputStream.close();
            if (!pause) {
                Manager.getInstance().completed(downloadPanel.getDownloadItem());
                System.out.println("File downloaded");
            }
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
        return true;
    }

    public void pause() {
        pause = true;
    }


}