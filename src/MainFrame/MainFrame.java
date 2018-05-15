package MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainFrame extends JFrame {

    private JComponent mainPanel;
    Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\setak\\IdeaProjects\\HM\\JDM\\UIPic\\icon.png");
    MyMenu frameMenubar = new MyMenu(this);


    private void tray() {
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            Image iconImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\setak\\IdeaProjects\\HM\\HM7\\UIPic\\trayIcon.png");

            //create popup menu
            PopupMenu menu = new PopupMenu();

            //create item
            MenuItem item1 = new MenuItem("Exit");
            MenuItem item2 = new MenuItem("Open");

            //add item to menu
            menu.add(item2);
            menu.add(item1);

            //add action listener to the item in the popup menu
            item1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            item2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(true);
                }
            });

            //create system tray icon.
            TrayIcon trayIcon = new TrayIcon(iconImage, "JDM.exe", menu);

            //add the tray icon to the system tray.
            try {
                systemTray.add(trayIcon);
                trayIcon.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(true);
                    }
                });
            } catch (AWTException e) {
                System.out.println(e.getMessage());
            }

        }
    }


    public MainFrame() {
        setLocation(200, 60);
        setPreferredSize(new Dimension(960, 660));
        setSize(960, 660);
        setTitle("Java Download Manager");
        setIconImage(icon);
        mainPanel = new JPanel();


        SpringLayout layout = new SpringLayout();
        mainPanel.setLayout(layout);


        RightPanel rightPanel = new RightPanel();
        LeftPanel leftPanel = new LeftPanel(rightPanel);


        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        layout.putConstraint(SpringLayout.WEST, leftPanel, 0, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, leftPanel, 0, SpringLayout.NORTH, mainPanel);
        layout.putConstraint(SpringLayout.WEST, rightPanel, 0, SpringLayout.EAST, leftPanel);



        setContentPane(mainPanel);
        setJMenuBar(frameMenubar.menuBar);
        setVisible(true);
        tray();

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension d = getSize();
                Dimension minD = new Dimension(960,660);
                if (minD.height>d.height || minD.width>d.width){
                    d = minD;
                }
                setSize(d);
                setComponentSize(leftPanel, new Dimension(d.width * 3 / 10, d.height));
                setComponentSize(rightPanel, new Dimension(d.width * 7 / 10, d.height));

                leftPanel.setupPanel();
                rightPanel.setupPanel();
            }
        });

    }

    class MyMenu{
        JFrame jFrame;
        JMenuBar menuBar;
        JMenu download;
        JMenuItem newDownload;
        JMenuItem pause;
        JMenuItem resume;
        JMenuItem cancel;
        JMenuItem remove;
        JMenuItem settings;
        JMenuItem exit;

        public MyMenu(JFrame jFrame) {
            this.jFrame = jFrame;
            menuBar = new JMenuBar();
            download = new JMenu("Download");
            newDownload = new JMenuItem("New Download");
            resume = new JMenuItem("Resume");
            pause = new JMenuItem("Pause");
            cancel = new JMenuItem("Cancel");
            remove = new JMenuItem("Remove");
            settings = new JMenuItem("Settings");
            exit = new JMenuItem("Exit");


            download.add(newDownload);
            download.add(resume);
            download.add(pause);
            download.add(cancel);
            download.add(remove);
            download.addSeparator();
            download.add(settings);
            download.addSeparator();
            download.add(exit);
            menuBar.add(download);
            MenuHandler menuHandler = new MenuHandler();
        }
        class MenuHandler implements ActionListener{

            MenuHandler(){
                newDownload.addActionListener(this);
                resume.addActionListener(this);
                pause.addActionListener(this);
                cancel.addActionListener(this);
                remove.addActionListener(this);
                settings.addActionListener(this);
                exit.addActionListener(this);

            }
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == newDownload){
                    System.out.println("new Download");
                }
                if (e.getSource() == pause){
                    System.out.println("Pause");
                }
                if (e.getSource() == resume){
                    System.out.println("resume");
                }
                if (e.getSource() == remove){
                    System.out.println("remove");
                }
                if (e.getSource() == cancel){
                    System.out.println("cancel");
                }
                if (e.getSource() == settings){
                    System.out.println("settings");
                }
                if (e.getSource() == exit){
                    jFrame.setVisible(false);
                }
            }
        }

    }

    public static void setComponentSize(JComponent jComponent, Dimension d) {
        jComponent.setPreferredSize(d);
        jComponent.setMaximumSize(d);
        jComponent.setMinimumSize(d);
        jComponent.setSize(d);
    }
}



