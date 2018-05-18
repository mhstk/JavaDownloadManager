public class DownloadItem {
    String name;
    String serverAddress;
    String savedAddress;
    double speedDownload;
    double size;
    double downloadedSize;
    double percent;
    public Time startedTime;




    public DownloadItem(String name) {

        this.name = name;
        startedTime = new Time();
    }



    public void setInfo(double size , double speed , double downloaded ,Time startedTime , String serverAddress,String savedAddress){
        setSize(size);
        setSpeedDownload(speed);
        setDownloadedSize(downloaded);
        setPercent(downloaded*100.0/size);
        this.startedTime = startedTime;
        setSavedAddress(savedAddress);
        setServerAddress(serverAddress);
    }

    public void resumeDownload(){

    }

    public void updateInfo(double speed , double downloaded ){
        this.downloadedSize = downloaded;
        setPercent(downloaded*100.0/size);
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

    public void setSpeedDownload(double speedDownload) {
        this.speedDownload = speedDownload;
    }

    public double getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(double downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }



}
