import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class ProcessingDP extends DownloadPanel {

    boolean inQueue = false;
    ImageIcon resumeIcon;
    ImageIcon pauseIcon;
    ImageIcon cancelIcon;

    ProcessingDP(DownloadItem downloadItem) {
        super(downloadItem);




        jProgressBar.setUI(new BasicProgressBarUI() {
            @Override
            public void paintDeterminate(Graphics g, JComponent c) {
                if (!(g instanceof Graphics2D)) {
                    return;
                }
                Insets b = progressBar.getInsets(); // area for border
                int barRectWidth = progressBar.getWidth() - (b.right + b.left);
                int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);
                if (barRectWidth <= 0 || barRectHeight <= 0) {
                    return;
                }
                int cellLength = getCellLength();
                int cellSpacing = getCellSpacing();

                int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

                if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {

                    float x = amountFull / (float) barRectWidth;
                    g.setColor(Color.GREEN);
                    g.fillRect(b.left, b.top, amountFull, barRectHeight);

                } else { // VERTICAL

                }
                if (progressBar.isStringPainted()) {
                    paintString(g, b.left, b.top, barRectWidth, barRectHeight, amountFull, b);
                }
            }
        });
        jProgressBar.setBackground(Color.WHITE);
        System.out.println(downloadItem.downloadedSize);
        System.out.println(downloadItem.size);
        jProgressBar.setValue((int)(downloadItem.downloadedSize*10000/downloadItem.size));


        MainFrame.setComponentSize(iconL,new Dimension(50,50));
        resumeIcon = ToolBar.getScaledImage("UIPic\\play.png",iconL);
        pauseIcon = ToolBar.getScaledImage("UIPic\\pause.png",iconL);
        cancelIcon = ToolBar.getScaledImage("UIPic\\cancel-icon.png" , iconL);
        iconL.setIcon(resumeIcon);

        if (downloadItem.inQueue){
            queueL.setText("In Queue");
        }

        add(jProgressBar);
        add(percentL);
        add(speedL);
        add(iconL);
        add(queueL);

    }

    @Override
    public void updateInfo() {
        super.updateInfo();

    }


    public void setPlace() {


        layout.putConstraint(SpringLayout.NORTH, iconL, 23, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, iconL,40 , SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.WEST, nameL, 40, SpringLayout.EAST, iconL);
        layout.putConstraint(SpringLayout.NORTH, nameL, 10, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, queueL, 140, SpringLayout.EAST, speedL);
        layout.putConstraint(SpringLayout.SOUTH, queueL, -20, SpringLayout.SOUTH, this);

        MainFrame.setComponentSize(jProgressBar, new Dimension(getWidth() * 73 / 100, 17));
        layout.putConstraint(SpringLayout.WEST, jProgressBar, 0, SpringLayout.WEST, nameL);
        layout.putConstraint(SpringLayout.NORTH, jProgressBar, 38, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.EAST, percentL, 0, SpringLayout.EAST, jProgressBar);
        layout.putConstraint(SpringLayout.NORTH, percentL, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, speedL, 0, SpringLayout.WEST, nameL);
        layout.putConstraint(SpringLayout.SOUTH, speedL, -20, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, sizeL, 0, SpringLayout.EAST, jProgressBar);
        layout.putConstraint(SpringLayout.SOUTH, sizeL, -20, SpringLayout.SOUTH, this);

        setBorder(BorderFactory.createLineBorder(Color.BLACK));


    }

    public void pauseIcon(){
        iconL.setIcon(pauseIcon);
    }

    public void resumeIcon(){
        iconL.setIcon(resumeIcon);
    }

    public void cancelIcon(){
        iconL.setIcon(cancelIcon);
    }

    public boolean isInQueue() {
        return inQueue;
    }

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    public void setOutPanel() {
        outPanel = MainFrame.getInstance().getProcessingPanel();
    }

}
