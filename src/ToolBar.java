import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ToolBar extends JPanel {
    private Image background;
    private SpringLayout layout;
    private static ToolBar single_instance;
    private JButton newB;
    private JButton resumeB;
    private JButton pauseB;
    private JButton cancelB;
    private JButton removeB;
    private JButton upB;
    private JButton downB;
    private JTextField searchT;
    private JLabel searchL;
    private Color backButtonColor;
    private JComboBox<String> sortMode ;
    private int counter = -1 ;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();
        g.drawImage(background, 0, 0, d.width, d.height, null);
    }

    private ToolBar() {
        ImageIcon bg = new ImageIcon("UIPic\\up.png");
        background = bg.getImage();
        layout = new SpringLayout();
        setLayout(layout);
        backButtonColor = new Color(13, 92, 106);


        newB = new JButton();
        MainFrame.setComponentSize(newB, new Dimension(40, 40));
        newB.setIcon(getScaledImage("UIPic\\newIcon.png", newB));
        newB.setToolTipText("New Download");
        newB.setBackground(backButtonColor);
        newB.addActionListener(new KeyListener());

        resumeB = new JButton();
        MainFrame.setComponentSize(resumeB, new Dimension(40, 40));
        resumeB.setIcon(getScaledImage("UIPic\\resumeIcon.png", newB));
        resumeB.setBackground(backButtonColor);
        resumeB.addActionListener(new KeyListener());
        resumeB.setToolTipText("Resume");

        pauseB = new JButton();
        MainFrame.setComponentSize(pauseB, new Dimension(40, 40));
        pauseB.setIcon(getScaledImage("UIPic\\pauseIcon.png", newB));
        pauseB.setBackground(backButtonColor);
        pauseB.addActionListener(new KeyListener());
        pauseB.setToolTipText("Pause");

        cancelB = new JButton();
        MainFrame.setComponentSize(cancelB, new Dimension(40, 40));
        cancelB.setIcon(getScaledImage("UIPic\\cancelIcon.png", newB));
        cancelB.setBackground(backButtonColor);
        cancelB.addActionListener(new KeyListener());
        cancelB.setToolTipText("Cancel");

        removeB = new JButton();
        MainFrame.setComponentSize(removeB, new Dimension(40, 40));
        removeB.setIcon(getScaledImage("UIPic\\removeIcon.png", newB));
        removeB.setBackground(backButtonColor);
        removeB.addActionListener(new KeyListener());
        removeB.setToolTipText("Remove");

        upB = new JButton();
        MainFrame.setComponentSize(upB, new Dimension(40, 40));
        upB.setIcon(getScaledImage("UIPic\\up-arrow.png", upB));
        upB.setToolTipText("Move up");
        upB.setBackground(backButtonColor);
        upB.addActionListener(new KeyListener());
        upB.setVisible(false);

        downB = new JButton();
        MainFrame.setComponentSize(downB, new Dimension(40, 40));
        downB.setIcon(getScaledImage("UIPic\\down-arrow.png", downB));
        downB.setToolTipText("Move down");
        downB.setBackground(backButtonColor);
        downB.addActionListener(new KeyListener());
        downB.setVisible(false);

        searchT = new JTextField();
        searchT.setFont(new Font("Tahoma", Font.PLAIN, 14));
        MainFrame.setComponentSize(searchT, new Dimension(200, 30));
        searchT.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changed();
            }

            public void changed() {
                MainFrame.getInstance().getProcessingPanel().search(searchT.getText().toLowerCase());
                MainFrame.getInstance().getCompletedPanel().search(searchT.getText().toLowerCase());
                MainFrame.getInstance().getQueuePanel().search(searchT.getText().toLowerCase());
            }
        });
        searchL = new JLabel("Search : ");
        searchL.setFont(new Font("Tahoma", Font.PLAIN, 14));
        searchL.setForeground(Color.WHITE);

        sortMode = new JComboBox<>(new String[]{"Name", "Size", "Date", "Name & Size", "Name & Date", "Size & Date", "Name &Size &Date"});
        sortMode.setSelectedItem("Date");
        MainFrame.setComponentSize(sortMode,new Dimension(120,28));
        sortMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choosed = sortMode.getItemAt(sortMode.getSelectedIndex());
                counter *= -1 ;
                System.out.println(choosed);
                sort(choosed,counter);
                System.out.println(counter);

            }
        });

        upB.setEnabled(false);
        downB.setEnabled(false);


        downloadUnSelected();

        add(newB);
        add(resumeB);
        add(pauseB);
        add(cancelB);
        add(removeB);
        add(upB);
        add(downB);
        add(searchL);
        add(searchT);
        add(sortMode);


    }

    public void sort(String choosed , int counter){
        switch (choosed){
            case "Name":
                Sort.sortByName(counter);
                break;
            case "Size":
                Sort.sortBySize(counter);
                break;
            case "Date":
                Sort.sortByDate(counter);
                break;
            case "Name & Size":
                Sort.sortByNameAndSize(counter);
                break;
            case "Name & Date":
                Sort.sortByNameAndDate(counter);
                break;
            case "Size & Date":
                Sort.sortBySizeAndDate(counter);
                break;
            case "Name &Size &Date":
                Sort.sortByNameAndSizeAndDate(counter);
                break;
        }
    }

    private class KeyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newB) {
                Manager.getInstance().newDownload();
            }
            if (e.getSource() == resumeB) {
                Manager.getInstance().resumeDownload();
            }
            if (e.getSource() == cancelB) {
                Manager.getInstance().cancel();
            }
            if (e.getSource() == removeB) {
                Manager.getInstance().remove();
            }
            if (e.getSource() == pauseB) {
                Manager.getInstance().pause();
            }
            if (e.getSource() == upB) {
                Manager.getInstance().moveUpInQueue();
            }
            if (e.getSource() == downB) {
                Manager.getInstance().moveDownInQueue();
            }
        }
    }

    public static ToolBar getInstance() {
        if (single_instance == null) {
            single_instance = new ToolBar();

        }

        return single_instance;
    }

    public void downloadSelected() {
        resumeB.setEnabled(true);
        pauseB.setEnabled(true);
        removeB.setEnabled(true);
        cancelB.setEnabled(true);
    }

    public void downloadCompletedPSelected() {
        removeB.setEnabled(true);
    }

    public void downloadQueuePSelected() {
        upB.setEnabled(true);
        downB.setEnabled(true);
        pauseB.setEnabled(false);
        resumeB.setEnabled(false);
        cancelB.setEnabled(true);
        removeB.setEnabled(true);
    }

    public void downloadUnSelected() {
        resumeB.setEnabled(false);
        pauseB.setEnabled(false);
        removeB.setEnabled(false);
        cancelB.setEnabled(false);

    }

    public void downloadUnSelectedInQ() {
        resumeB.setEnabled(true);
        pauseB.setEnabled(true);
        removeB.setEnabled(false);
        cancelB.setEnabled(false);
        upB.setEnabled(false);
        downB.setEnabled(false);

    }

    public void queueButtonsEnabale() {
        resumeB.setEnabled(true);
        pauseB.setEnabled(true);
        upB.setVisible(true);
        downB.setVisible(true);
    }

    public void queueButtonsDisable() {
        resumeB.setEnabled(false);
        pauseB.setEnabled(false);
        upB.setVisible(false);
        downB.setVisible(false);
    }

    public static ImageIcon getScaledImage(String address, JComponent component) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(address));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image icon = img.getScaledInstance(component.getWidth(), component.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(icon);

        return imageIcon;
    }

    public void setPlace() {
        int gapV = getHeight() / 5;
        int gapH = getWidth() / 80;

        MainFrame.setComponentSize(newB, new Dimension(getWidth() * 6 / 100, getWidth() * 6 / 100));
        MainFrame.setComponentSize(resumeB, new Dimension(getWidth() * 6 / 100, getWidth() * 6 / 100));
        MainFrame.setComponentSize(pauseB, new Dimension(getWidth() * 6 / 100, getWidth() * 6 / 100));
        MainFrame.setComponentSize(removeB, new Dimension(getWidth() * 6 / 100, getWidth() * 6 / 100));
        MainFrame.setComponentSize(cancelB, new Dimension(getWidth() * 6 / 100, getWidth() * 6 / 100));
        MainFrame.setComponentSize(upB, new Dimension(getWidth() * 6 / 100, getWidth() * 6 / 100));
        MainFrame.setComponentSize(downB, new Dimension(getWidth() * 6 / 100, getWidth() * 6 / 100));

        layout.putConstraint(SpringLayout.WEST, newB, gapH, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, newB, gapV, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, resumeB, gapH, SpringLayout.EAST, newB);
        layout.putConstraint(SpringLayout.NORTH, resumeB, 0, SpringLayout.NORTH, newB);
        layout.putConstraint(SpringLayout.WEST, pauseB, gapH, SpringLayout.EAST, resumeB);
        layout.putConstraint(SpringLayout.NORTH, pauseB, 0, SpringLayout.NORTH, resumeB);
        layout.putConstraint(SpringLayout.WEST, cancelB, gapH, SpringLayout.EAST, pauseB);
        layout.putConstraint(SpringLayout.NORTH, cancelB, 0, SpringLayout.NORTH, pauseB);
        layout.putConstraint(SpringLayout.WEST, removeB, gapH, SpringLayout.EAST, cancelB);
        layout.putConstraint(SpringLayout.NORTH, removeB, 0, SpringLayout.NORTH, cancelB);
        layout.putConstraint(SpringLayout.WEST, upB, gapH, SpringLayout.EAST, removeB);
        layout.putConstraint(SpringLayout.NORTH, upB, 0, SpringLayout.NORTH, removeB);
        layout.putConstraint(SpringLayout.WEST, downB, gapH, SpringLayout.EAST, upB);
        layout.putConstraint(SpringLayout.NORTH, downB, 0, SpringLayout.NORTH, upB);

        layout.putConstraint(SpringLayout.EAST, searchT, -30, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, searchT, gapH, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, searchL, -10, SpringLayout.WEST, searchT);
        layout.putConstraint(SpringLayout.NORTH, searchL, 2, SpringLayout.NORTH, searchT);
        layout.putConstraint(SpringLayout.EAST, sortMode, -30, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, sortMode, gapH, SpringLayout.SOUTH, searchT);

    }

    public JComboBox<String> getSortMode() {
        return sortMode;
    }


    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
