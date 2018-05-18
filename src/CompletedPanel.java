import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CompletedPanel extends JPanel {
    SpringLayout layout;
    Image background;
    ArrayList<DownloadItem> completed;
    ArrayList<DownloadCompletedPanel> completedPanels;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }

    CompletedPanel() {
        completedPanels = new ArrayList<>();
        completed = new ArrayList<>();

        ImageIcon bg = new ImageIcon("UIPic\\center.png");
        background = bg.getImage();
        layout = new SpringLayout();
        setLayout(layout);

    }

    public void setPlace() {
        removeAll();
        if (completedPanels.size() >= 6) {
            MainFrame.setComponentSize(this, new Dimension(getWidth(), (660 / 6) * completedPanels.size()));
        }
        for (int i = 0; i < completedPanels.size(); i++) {
            MainFrame.setComponentSize(completedPanels.get(i), new Dimension(getWidth(), 660 / 6));
            completedPanels.get(i).setPlace();
            add(completedPanels.get(i));
            layout.putConstraint(SpringLayout.WEST, completedPanels.get(i), 0, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, completedPanels.get(i), i * completedPanels.get(i).getHeight() + i, SpringLayout.NORTH, this);
        }
        revalidate();
        updateUI();
    }

    public void addADownload(DownloadItem downloadItem) {
        DownloadCompletedPanel downloadPanel = new DownloadCompletedPanel(downloadItem);
        completed.add(downloadItem);
        completedPanels.add(downloadPanel);
        downloadPanel.numberL.setText(completedPanels.size() + ".");
        MainFrame.setComponentSize(downloadPanel, new Dimension(getWidth() - 15, getHeight() / 6));
        downloadPanel.addMouseListener(new MyMouseListener());
    }

    public ArrayList<DownloadItem> getCompleted() {
        return completed;
    }

    public void setCompleted(ArrayList<DownloadItem> completed) {
        this.completed = completed;
    }

    class MyMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            DownloadCompletedPanel downloadPanel = (DownloadCompletedPanel) e.getSource();
            if (e.isMetaDown()) {
                InfoFrame infoFrame = new InfoFrame();
                infoFrame.setDownloadItem(downloadPanel);
                infoFrame.action();
            } else {

            }
        }
    }
}

class DownloadCompletedPanel extends JPanel {

    private DownloadItem downloadItem;
    JLabel nameL;
    JLabel sizeL;
    JLabel numberL;
    JLabel startedTimeL;
    SpringLayout layout;

    DownloadCompletedPanel(DownloadItem downloadItem) {
        this.downloadItem = downloadItem;
        setOpaque(false);
        setBackground(Color.DARK_GRAY);

        nameL = new JLabel();
        numberL = new JLabel();
        sizeL = new JLabel();
        startedTimeL = new JLabel();
        layout = new SpringLayout();
        setLayout(layout);
        nameL.setFont(new Font("Arial", Font.BOLD, 20));
        nameL.setForeground(Color.WHITE);
        sizeL.setFont(new Font("Arial", Font.PLAIN, 16));
        sizeL.setForeground(Color.WHITE);
        numberL.setFont(new Font("Arial", Font.PLAIN, 26));
        numberL.setForeground(Color.lightGray);
        startedTimeL.setFont(new Font("Arial", Font.PLAIN, 14));
        startedTimeL.setForeground(Color.WHITE);


        nameL.setText(downloadItem.getName());
        sizeL.setText(downloadItem.getSize() + " (mb)");
        startedTimeL.setText("Started Time : " + downloadItem.startedTime.getHour() + ":" + downloadItem.startedTime.getMin() + " (" + downloadItem.startedTime.getMonth() + "/" + downloadItem.startedTime.getDay() + "/" + downloadItem.startedTime.getYear() + ")");


        add(nameL);
        add(sizeL);
        add(numberL);
        setPlace();
    }

    public void setPlace() {

        add(numberL);
        add(nameL);
        add(sizeL);
        add(startedTimeL);
        layout.putConstraint(SpringLayout.WEST, numberL, getWidth() / 50, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, numberL, 34, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, nameL, getWidth() * 4 / 50, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, nameL, 38, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, sizeL, getWidth() * 52 / 100, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, sizeL, 34, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, startedTimeL, getWidth() * 52 / 100, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, startedTimeL, 58, SpringLayout.NORTH, this);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));


    }

    public DownloadItem getDownloadItem() {
        return downloadItem;
    }
}
