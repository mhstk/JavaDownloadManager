import javax.swing.*;
import java.io.Serializable;
import java.util.Date;

public class DownloadItem implements Serializable {
    String name;
    String serverAddress;
    String savedAddress;
    double speedDownload;
    double size;
    double downloadedSize;
    private Date startedTime;
    boolean inQueue;





    public DownloadItem(String name) {

        this.name = name;
    }



    public void setInfo(double size , double speed , double downloaded , String serverAddress,String savedAddress){
        setSize(size);
        setSpeedDownload(speed);
        setDownloadedSize(downloaded);
        setSavedAddress(savedAddress);
        setServerAddress(serverAddress);
    }


    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
    }

    public void resumeDownload(){

    }

    public void updateInfo(double speed , double downloaded ){
        this.downloadedSize = downloaded;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getSavedAddress() {
        return savedAddress;
    }

    public void setSavedAddress(String savedAddress) {
        this.savedAddress = savedAddress;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getSpeedDownload() {
        return speedDownload;
    }

    public Date getStartedTime() {
        return startedTime;
    }

    public void setSpeedDownload(double speedDownload) {
        this.speedDownload = speedDownload;
    }

    public double getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(double downloadedSize) {
        this.downloadedSize = downloadedSize;
    }



    @Override
    public String toString() {
        return "Name: "+ name + " *** Link: "+serverAddress+" *** Directory: "+savedAddress+" *** size: "+size+" *** start time: "+startedTime;
    }

    @Override
    public boolean equals(Object obj) {
        DownloadItem downloadItem = (DownloadItem) obj;
        if (downloadItem.toString().equals(toString()) ){
            return true;
        }else {
            return false;
        }
    }
}
