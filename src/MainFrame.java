import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainFrame extends JFrame {

    private JComponent mainPanel;
    private static MainFrame mainFrame;
    private SpringLayout layout;
    private CompletedPanel completedPanel;
    private ProcessingPanel processingPanel;
    private Menu menu;
    private ToolBar toolBar;
    private JScrollPane jScrollPane;
    Image icon = Toolkit.getDefaultToolkit().getImage("UIPic\\icon.png");
    MyMenu frameMenubar = new MyMenu(this);


    private void tray() {
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            Image iconImage = Toolkit.getDefaultToolkit().getImage("UIPic\\trayIcon.png");

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


    public static MainFrame getInstance() {
        if (mainFrame == null) {
            mainFrame = new MainFrame();
        }
        return mainFrame;
    }

    private MainFrame() {
        setLocation(200, 60);
        setPreferredSize(new Dimension(960, 660));
        setSize(960, 660);
        setTitle("Java Download Manager");
        setIconImage(icon);


        mainPanel = new JPanel();
        setComponentSize(mainPanel, getSize());
        mainPanel.setBackground(new Color(25, 42, 47));
        layout = new SpringLayout();
        mainPanel.setLayout(layout);



        menu = new Menu();

        processingPanel = new ProcessingPanel();

        completedPanel = new CompletedPanel();

        toolBar = ToolBar.getInstance();

        jScrollPane = new JScrollPane(processingPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setBorder(BorderFactory.createLineBorder(new Color(25, 42, 47)));

        mainPanel.add(jScrollPane);
        mainPanel.add(toolBar);
        mainPanel.add(menu);

        setPlace();
        toolBar.setPlace();
        processingPanel.setPlace();
        completedPanel.setPlace();



        setContentPane(mainPanel);
        setJMenuBar(frameMenubar.menuBar);
        setVisible(true);
        mainPanel.revalidate();
        mainPanel.updateUI();
        tray();

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension d = getSize();
                Dimension minD = new Dimension(960, 660);
                if (minD.height > d.height || minD.width > d.width) {
                    d = minD;
                }
                setSize(d);
                setComponentSize(menu, new Dimension(d.width * 3 / 10, d.height));
                setComponentSize(jScrollPane, new Dimension(d.width * 7 / 10, d.height * 16 / 20));
                setComponentSize(completedPanel, new Dimension(d.width * 7 / 10, d.height * 16 / 20));
                setComponentSize(processingPanel, new Dimension(d.width * 7 / 10, d.height * 16 / 20));
                setComponentSize(toolBar, new Dimension(d.width * 7 / 10, d.height * 3 / 20));

                update();
            }
        });

    }

    public void update(){
        revalidate();
        menu.setPlace();
        processingPanel.setPlace();
        completedPanel.setPlace();
        toolBar.setPlace();
        mainPanel.revalidate();
        mainPanel.updateUI();
    }


    public void setPlace(){
        layout.putConstraint(SpringLayout.WEST, menu, 0, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, menu, 0, SpringLayout.NORTH, mainPanel);
        layout.putConstraint(SpringLayout.EAST, toolBar, 1, SpringLayout.EAST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, toolBar, 0, SpringLayout.NORTH, mainPanel);
        layout.putConstraint(SpringLayout.EAST, jScrollPane, 0, SpringLayout.EAST, mainPanel);
        layout.putConstraint(SpringLayout.SOUTH, jScrollPane, 0, SpringLayout.SOUTH, mainPanel);
        setComponentSize(toolBar, new Dimension(getWidth() * 7 / 10, getHeight() * 3 / 20));
        setComponentSize(processingPanel, new Dimension(getWidth() * 7 / 10, getHeight() * 18 / 20));
        setComponentSize(completedPanel, new Dimension(getWidth() * 7 / 10, getHeight() * 18 / 20));
        setComponentSize(jScrollPane, new Dimension(getWidth() * 7 / 10, getHeight() * 16 / 20));
    }

    public CompletedPanel getCompletedPanel() {
        return completedPanel;
    }

    public ProcessingPanel getProcessingPanel() {
        return processingPanel;
    }

    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }

    public JComponent getMainPanel() {
        return mainPanel;
    }

    public Menu getMenu() {
        return menu;
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    public MyMenu getFrameMenubar() {
        return frameMenubar;
    }

    public static void setComponentSize(JComponent jComponent, Dimension d) {
        jComponent.setPreferredSize(d);
        jComponent.setMaximumSize(d);
        jComponent.setMinimumSize(d);
        jComponent.setSize(d);
    }

    class MyMenu {
        MainFrame mainFrame;
        JMenuBar menuBar;
        JMenu download;
        JMenuItem newDownload;
        JMenuItem pause;
        JMenuItem resume;
        JMenuItem cancel;
        JMenuItem remove;
        JMenuItem settings;

        JMenuItem exit;

        public MyMenu(MainFrame mainFrame) {
            this.mainFrame = mainFrame;
            menuBar = new JMenuBar();
            download = new JMenu("Download");
            newDownload = new JMenuItem("New Download");
            resume = new JMenuItem("Resume");
            pause = new JMenuItem("Pause");
            cancel = new JMenuItem("Cancel");
            remove = new JMenuItem("Remove");
            settings = new JMenuItem("Settings");
            exit = new JMenuItem("Exit");

            newDownload.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
            newDownload.setMnemonic('N');

            resume.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
            resume.setMnemonic('R');

            pause.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
            pause.setMnemonic('P');

            remove.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
            remove.setMnemonic('m');

            cancel.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
            cancel.setMnemonic('C');

            settings.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
            settings.setMnemonic('S');

            exit.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
            exit.setMnemonic('E');


            resume.setEnabled(false);
            remove.setEnabled(false);
            pause.setEnabled(false);
            cancel.setEnabled(false);


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
        public void downloadSelected(){
            resume.setEnabled(true);
            pause.setEnabled(true);
            remove.setEnabled(true);
            cancel.setEnabled(true);
        }

        public void downloadUnSelected(){
            resume.setEnabled(false);
            pause.setEnabled(false);
            remove.setEnabled(false);
            cancel.setEnabled(false);
        }
        class MenuHandler implements ActionListener {


            MenuHandler() {
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
                if (e.getSource() == newDownload) {
                    Manager.getInstance().newDownload();
                }
                if (e.getSource() == pause) {
                    System.out.println("Pause");
                }
                if (e.getSource() == resume) {
                    System.out.println("resume");
                }
                if (e.getSource() == remove) {
                    System.out.println("remove");
                }
                if (e.getSource() == cancel) {
                    System.out.println("cancel");
                }
                if (e.getSource() == settings) {
                    System.out.println("settings");
                }
                if (e.getSource() == exit) {
                    Manager.getInstance().exit();
                }
            }

        }
    }
}


