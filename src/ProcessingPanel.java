import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class ProcessingPanel extends JPanel {

    SpringLayout layout;
    Image background;
    ArrayList<DownloadItem> processing;
    ArrayList<DownloadPanel> processingPanels;
    ArrayList<DownloadPanel> selected;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }

    ProcessingPanel() {
        processingPanels = new ArrayList<>();
        processing = new ArrayList<>();
        selected = new ArrayList<>();

        ImageIcon bg = new ImageIcon("UIPic\\center.png");
        background = bg.getImage();
        layout = new SpringLayout();
        setLayout(layout);

    }

    public void setPlace() {
        removeAll();
        if (processingPanels.size() >= 6) {
            MainFrame.setComponentSize(this, new Dimension(getWidth(), (660 / 6) * processingPanels.size()));
        }
        for (int i = 0; i < processingPanels.size(); i++) {
            MainFrame.setComponentSize(processingPanels.get(i), new Dimension(getWidth(), 660 / 6));
            processingPanels.get(i).setPlace();
            add(processingPanels.get(i));
            layout.putConstraint(SpringLayout.WEST, processingPanels.get(i), 0, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, processingPanels.get(i), i * processingPanels.get(i).getHeight() + i, SpringLayout.NORTH, this);
        }
        revalidate();
        updateUI();
    }

    public void addADownload(DownloadItem downloadItem) {
        DownloadPanel downloadPanel = new DownloadPanel(downloadItem);
        processing.add(downloadItem);
        processingPanels.add(downloadPanel);
        downloadPanel.numberL.setText(processingPanels.size() + ".");
        MainFrame.setComponentSize(downloadPanel, new Dimension(getWidth(), getHeight() / 6));
        downloadPanel.addMouseListener(new MyMouseListener());
    }

    public void updateLabels(){
        for (int i=0 ; i<processingPanels.size();i++){
            processingPanels.get(i).updateInfo();
            processingPanels.get(i).numberL.setText((i+1)+".");
        }
    }

    class MyMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            DownloadPanel downloadPanel = (DownloadPanel) e.getSource();
            if (e.isMetaDown()) {
                System.out.println("rightclick!");
            } else {
                if (selected.contains(downloadPanel)) {
                    selected.remove(downloadPanel);
                    downloadPanel.setOpaque(false);
                    MainFrame.getInstance().update();

                } else {
                    selected.add(downloadPanel);
                    downloadPanel.setOpaque(true);
                    MainFrame.getInstance().update();
                }
            }
            if (selected.isEmpty()) {
                Manager.getInstance().downloadUnSelected();
            } else {
                Manager.getInstance().downloadSelected(selected);
            }

        }
    }
}


class DownloadPanel extends JPanel {

    private DownloadItem downloadItem;
    private JProgressBar jProgressBar;
    JLabel nameL;
    JLabel percentL;
    JLabel speedL;
    JLabel sizeL;
    JLabel numberL;
    JLabel startedTimeL;
    SpringLayout processLayout;

    DownloadPanel(DownloadItem downloadItem) {
        this.downloadItem = downloadItem;
        setOpaque(false);
        setBackground(Color.DARK_GRAY);
        jProgressBar = new JProgressBar(0, 10000);

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
        nameL = new JLabel();
        numberL = new JLabel();
        percentL = new JLabel();
        speedL = new JLabel();
        sizeL = new JLabel();
        startedTimeL = new JLabel();
        processLayout = new SpringLayout();
        setLayout(processLayout);
        nameL.setFont(new Font("Arial", Font.BOLD, 20));
        nameL.setForeground(Color.WHITE);
        percentL.setFont(new Font("Arial", Font.PLAIN, 16));
        percentL.setForeground(Color.WHITE);
        sizeL.setFont(new Font("Arial", Font.PLAIN, 16));
        sizeL.setForeground(Color.WHITE);
        speedL.setFont(new Font("Arial", Font.PLAIN, 16));
        speedL.setForeground(Color.WHITE);
        numberL.setFont(new Font("Arial", Font.PLAIN, 26));
        numberL.setForeground(Color.lightGray);
        startedTimeL.setFont(new Font("Arial", Font.PLAIN, 14));
        startedTimeL.setForeground(Color.WHITE);


        nameL.setText(downloadItem.getName());
        percentL.setText(String.format("%.2f%%", downloadItem.getPercent()));
        speedL.setText(downloadItem.getSpeedDownload() + " (mb/s)");
        sizeL.setText(downloadItem.getDownloadedSize() + "/" + downloadItem.getSize() + " (mb)");
        jProgressBar.setValue((int) downloadItem.getPercent() * 100);
        startedTimeL.setText("Started Time : " + downloadItem.startedTime.getHour() + ":" + downloadItem.startedTime.getMin() + " (" + downloadItem.startedTime.getMonth() + "/" + downloadItem.startedTime.getDay() + "/" + downloadItem.startedTime.getYear() + ")");




        add(jProgressBar);
        add(nameL);
        add(percentL);
        add(speedL);
        add(sizeL);
        add(numberL);
        setPlace();
    }

    public void updateInfo(){
        percentL.setText(String.format("%.2f%%", downloadItem.getPercent()));
        speedL.setText(downloadItem.getSpeedDownload() + " (mb/s)");
        jProgressBar.setValue((int)(downloadItem.getPercent()*100));
        sizeL.setText(downloadItem.getDownloadedSize() + "/" + downloadItem.getSize() + " (mb)");
        System.out.println(downloadItem.getDownloadedSize());
        revalidate();
        updateUI();

    }

    public void setPlace() {

        processLayout.putConstraint(SpringLayout.WEST, nameL, getWidth() / 10, SpringLayout.WEST, this);
        processLayout.putConstraint(SpringLayout.NORTH, nameL, 10, SpringLayout.NORTH, this);
        processLayout.putConstraint(SpringLayout.EAST, percentL, -getWidth() / 10, SpringLayout.EAST, this);
        processLayout.putConstraint(SpringLayout.NORTH, percentL, 10, SpringLayout.NORTH, this);
        processLayout.putConstraint(SpringLayout.WEST, speedL, getWidth() / 10, SpringLayout.WEST, this);
        processLayout.putConstraint(SpringLayout.SOUTH, speedL, -20, SpringLayout.SOUTH, this);
        processLayout.putConstraint(SpringLayout.EAST, sizeL, -getWidth() / 10, SpringLayout.EAST, this);
        processLayout.putConstraint(SpringLayout.SOUTH, sizeL, -20, SpringLayout.SOUTH, this);
        processLayout.putConstraint(SpringLayout.WEST, numberL, getWidth() / 50, SpringLayout.WEST, this);
        processLayout.putConstraint(SpringLayout.NORTH, numberL, 34, SpringLayout.NORTH, this);

        MainFrame.setComponentSize(jProgressBar, new Dimension(getWidth() * 79 / 100, 17));
        processLayout.putConstraint(SpringLayout.WEST, jProgressBar, getWidth() / 10, SpringLayout.WEST, this);
        processLayout.putConstraint(SpringLayout.NORTH, jProgressBar, 38, SpringLayout.NORTH, this);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }

    public DownloadItem getDownloadItem() {
        return downloadItem;
    }
}