import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CenterPanel extends JPanel {
    SpringLayout layout;
    Image background;
    ArrayList<DownloadItem> downloadItems;
    ArrayList<DownloadPanel> downloadPanels;
    ArrayList<DownloadPanel> selected;
    MyMouseListener myMouseListener;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }

    CenterPanel() {
        downloadPanels = new ArrayList<>();
        downloadItems = new ArrayList<>();
        selected = new ArrayList<>();

        ImageIcon bg = new ImageIcon("UIPic\\center.png");
        background = bg.getImage();
        layout = new SpringLayout();
        setLayout(layout);
    }

    public void setPanelSize() {
        if (downloadPanels.size() >= 6) {
            MainFrame.setComponentSize(this, new Dimension(getWidth(), (660 / 6) * downloadPanels.size()));
        }else {
            MainFrame.setComponentSize(this,new Dimension(getWidth(),660));
        }
        revalidate();
        updateUI();
    }

    public void reSize(){
        for (int i=0 ; i<downloadPanels.size();i++){
            MainFrame.setComponentSize(downloadPanels.get(i),new Dimension(getWidth(),660/6));
            layout.putConstraint(SpringLayout.WEST, downloadPanels.get(i), 0, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, downloadPanels.get(i), i * downloadPanels.get(i).getHeight() + i, SpringLayout.NORTH, this);
            downloadPanels.get(i).setPlace();
        }
        revalidate();
        updateUI();
    }


    public void addDownload(DownloadPanel downloadPanel) {
        downloadItems.add(downloadPanel.getDownloadItem());
        this.downloadPanels.add(downloadPanel);
        downloadPanel.numberL.setText(this.downloadPanels.size() + ".");
        MainFrame.setComponentSize(downloadPanel, new Dimension(getWidth(), 660 / 6));
        downloadPanel.addMouseListener(myMouseListener);

        int i = downloadPanels.indexOf(downloadPanel);
        downloadPanels.get(i).setPlace();
        System.out.println(i);
        add(downloadPanels.get(i));
        layout.putConstraint(SpringLayout.WEST, downloadPanels.get(i), 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, downloadPanels.get(i), i * downloadPanels.get(i).getHeight() + i, SpringLayout.NORTH, this);

    }

    public void reSet(){
        for (int i=0 ; i<downloadPanels.size();i++){
            downloadPanels.get(i).setPlace();
            layout.putConstraint(SpringLayout.WEST, downloadPanels.get(i), 0, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, downloadPanels.get(i), i * downloadPanels.get(i).getHeight() + i, SpringLayout.NORTH, this);

        }
        revalidate();
        updateUI();
    }


    public void updateLabels() {
        for (int i = 0; i < downloadPanels.size(); i++) {
            downloadPanels.get(i).updateInfo();
            downloadPanels.get(i).numberL.setText((i + 1) + ".");
        }
    }

    class MyMouseListener extends MouseAdapter {

        public void mouseEntered(MouseEvent e) {
            DownloadPanel downloadPanel = (DownloadPanel) e.getSource();
            if (!selected.contains(downloadPanel)){
                downloadPanel.setBackground(new Color(7,38,51));
                downloadPanel.setOpaque(true);
                updateUI();
                revalidate();
                updateUI();
                //MainFrame.getInstance().update();
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            DownloadPanel downloadPanel = (DownloadPanel) e.getSource();
            if (!selected.contains(downloadPanel)){
                downloadPanel.setOpaque(false);
                revalidate();
                updateUI();
                //MainFrame.getInstance().update();
            }
        }

        public void rightClicked(MouseEvent e){
            DownloadPanel downloadPanel = (DownloadPanel) e.getSource();
            InfoFrame infoFrame = new InfoFrame();
            infoFrame.setDownloadItem(downloadPanel);
            infoFrame.action();
        }

        public void leftOneClicked(MouseEvent e){
            DownloadPanel downloadPanel = (DownloadPanel) e.getSource();
            if (selected.contains(downloadPanel)) {
                selected.remove(downloadPanel);
                downloadPanel.setBackground(new Color(7,38,51));
                downloadPanel.setOpaque(true);
                revalidate();
                updateUI();
                // MainFrame.getInstance().update();
            } else {
                selected.add(downloadPanel);
                System.out.println("hi");
                downloadPanel.setBackground(new Color(94, 138, 158));
                downloadPanel.setOpaque(true);
                revalidate();
                updateUI();
                //MainFrame.getInstance().update();
            }
        }

        public void leftDoubleClicked(MouseEvent e){

        }

        public void mouseClicked(MouseEvent e) {
            if (e.isMetaDown()) {
                rightClicked(e);
            } else {
                if (e.getClickCount()>=2){
                    leftDoubleClicked(e);
                }
                    leftOneClicked(e);


            }

            if (selected.isEmpty()) {
                unSelected();
            } else {
                selected();
            }
        }

    }
    public void selected(){
        Manager.getInstance().downloadSelected(selected);
    }

    public void unSelected(){
        Manager.getInstance().downloadUnSelected();
    }
}
