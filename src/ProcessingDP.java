import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class ProcessingDP extends DownloadPanel{

    boolean inQueue  = false ;

    ProcessingDP(DownloadItem downloadItem){
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
        jProgressBar.setValue((int) downloadItem.getPercent() * 100);

        add(jProgressBar);
        add(percentL);
        add(speedL);

    }

    @Override
    public void updateInfo() {
        super.updateInfo();
        jProgressBar.setValue((int) (downloadItem.getPercent() * 100));

    }

    public void setPlace() {

        layout.putConstraint(SpringLayout.WEST, nameL, getWidth() / 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, nameL, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, percentL, -getWidth() / 10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, percentL, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, speedL, getWidth() / 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, speedL, -20, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, sizeL, -getWidth() / 10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, sizeL, -20, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, numberL, getWidth() / 50, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, numberL, 34, SpringLayout.NORTH, this);

        MainFrame.setComponentSize(jProgressBar, new Dimension(getWidth() * 79 / 100, 17));
        layout.putConstraint(SpringLayout.WEST, jProgressBar, getWidth() / 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, jProgressBar, 38, SpringLayout.NORTH, this);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));


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
