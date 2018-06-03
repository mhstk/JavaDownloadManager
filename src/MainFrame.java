import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class MainFrame extends JFrame {

    private JComponent mainPanel;
    private static MainFrame mainFrame;
    private SpringLayout layout;
    private CompletedPanel completedPanel;
    private ProcessingPanel processingPanel;
    private QueuePanel queuePanel;
    private Menu menu;
    private ToolBar toolBar;
    private JScrollPane jScrollPane;
    Image icon = Toolkit.getDefaultToolkit().getImage("UIPic\\icon.png");
    private MyMenu frameMenuBar = new MyMenu(this);


    private void tray() {
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            Image iconImage = Toolkit.getDefaultToolkit().getImage("UIPic\\trayIcon.png");

            //create popup menu
            PopupMenu menu = new PopupMenu();

            //create item
            MenuItem item1 = new MenuItem("Exit");
            MenuItem item2 = new MenuItem("Open");

            //addDownload item to menu
            menu.add(item2);
            menu.add(item1);

            //addDownload action listener to the item in the popup menu
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

            //addDownload the tray icon to the system tray.
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
        setLocation(170, 40);
        setPreferredSize(new Dimension(1040, 760));
        setSize(1040, 760);
        setTitle("Java Download Manager");
        setIconImage(icon);


        mainPanel = new JPanel();
        setComponentSize(mainPanel, getSize());
        mainPanel.setBackground(new Color(25, 42, 47));
        layout = new SpringLayout();
        mainPanel.setLayout(layout);


        menu = new Menu();

        processingPanel = new ProcessingPanel();
        queuePanel = new QueuePanel();

        completedPanel = new CompletedPanel();

        toolBar = ToolBar.getInstance();

        jScrollPane = new JScrollPane(processingPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setBorder(BorderFactory.createLineBorder(new Color(25, 42, 47)));

        mainPanel.add(jScrollPane);
        mainPanel.add(toolBar);
        mainPanel.add(menu);

        setSize();
        setPlace();
        toolBar.setPlace();
        processingPanel.setPanelSize();
        queuePanel.setPanelSize();
        completedPanel.setPanelSize();


        setContentPane(mainPanel);
        setJMenuBar(frameMenuBar.menuBar);
        setVisible(true);
        mainPanel.revalidate();
        mainPanel.updateUI();
        tray();
        Component thiss = this;
        update();

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension d = getSize();
                Dimension minD = new Dimension(1040, 760);
                if (minD.height > d.height || minD.width > d.width) {
                    d = minD;
                }
                setSize(d);
                setComponentSize(menu, new Dimension(d.width * 3 / 10, d.height));
                setComponentSize(jScrollPane, new Dimension(d.width * 7 / 10, d.height * 16 / 20));
                setComponentSize(completedPanel, new Dimension(d.width * 7 / 10, d.height * 16 / 20));
                setComponentSize(processingPanel, new Dimension(d.width * 7 / 10, d.height * 16 / 20));
                setComponentSize(queuePanel, new Dimension(d.width * 7 / 10, d.height * 16 / 20));
                setComponentSize(toolBar, new Dimension(d.width * 7 / 10, d.height * 3 / 20));

                update();
                mainPanel.revalidate();
        mainPanel.updateUI();
               // revalidate();
                //SwingUtilities.updateComponentTreeUI(thiss);
            }
        });


    }

    public void setSize(){
        Dimension d = getSize();
        setComponentSize(menu, new Dimension(d.width * 3 / 10, d.height));
        setComponentSize(jScrollPane, new Dimension(d.width * 7 / 10, d.height * 16 / 20));
        setComponentSize(completedPanel, new Dimension(d.width * 7 / 10, d.height * 16 / 20));
        setComponentSize(processingPanel, new Dimension(d.width * 7 / 10, d.height * 16 / 20));
        setComponentSize(queuePanel, new Dimension(d.width * 7 / 10, d.height * 16 / 20));
        setComponentSize(toolBar, new Dimension(d.width * 7 / 10, d.height * 3 / 20));
    }

    public void update() {
        menu.setPlace();
        processingPanel.setPanelSize();
        queuePanel.setPanelSize();
        completedPanel.setPanelSize();
        processingPanel.reSize();
        queuePanel.reSize();
        completedPanel.reSize();
        toolBar.setPlace();

        for (int i = 0; i < processingPanel.downloadPanels.size(); i++) {
            processingPanel.downloadPanels.get(i).getjProgressBar().setUI(new BasicProgressBarUI() {
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
        }

        for (int i = 0; i < queuePanel.downloadPanels.size(); i++) {
            queuePanel.downloadPanels.get(i).getjProgressBar().setUI(new BasicProgressBarUI() {
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
        }
//        revalidate();
    }


    public void showProcessing(){
        MainFrame.getInstance().getCompletedPanel().setVisible(false);
        MainFrame.getInstance().getQueuePanel().setVisible(false);
        MainFrame.getInstance().getProcessingPanel().setVisible(true);
        MainFrame.getInstance().getjScrollPane().setViewportView(MainFrame.getInstance().getProcessingPanel());
        MainFrame.getInstance().getProcessingPanel().setPanelSize();
        MainFrame.getInstance().getToolBar().queueButtonsDisable();
    }

    public void showCompleted(){
        MainFrame.getInstance().getCompletedPanel().setVisible(true);
        MainFrame.getInstance().getProcessingPanel().setVisible(false);
        MainFrame.getInstance().getQueuePanel().setVisible(false);
        MainFrame.getInstance().getjScrollPane().setViewportView(MainFrame.getInstance().getCompletedPanel());
        MainFrame.getInstance().getCompletedPanel().setPanelSize();
        MainFrame.getInstance().getToolBar().queueButtonsDisable();
    }

    public void showQueue(){
        MainFrame.getInstance().getCompletedPanel().setVisible(false);
        MainFrame.getInstance().getQueuePanel().setVisible(true);
        MainFrame.getInstance().getProcessingPanel().setVisible(false);
        MainFrame.getInstance().getjScrollPane().setViewportView(MainFrame.getInstance().getQueuePanel());
        MainFrame.getInstance().getProcessingPanel().setPanelSize();
        MainFrame.getInstance().getToolBar().queueButtonsEnabale();
    }

    public void setPlace() {
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

    public QueuePanel getQueuePanel() {
        return queuePanel;
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

    public MyMenu getFrameMenuBar() {
        return frameMenuBar;
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
        JMenu help;
        JMenuItem newDownload;
        JMenuItem pause;
        JMenuItem resume;
        JMenuItem cancel;
        JMenuItem remove;
        JMenuItem settings;
        JMenuItem export;
        JMenuItem about;

        JMenuItem exit;

        public MyMenu(MainFrame mainFrame) {
            this.mainFrame = mainFrame;
            menuBar = new JMenuBar();
            download = new JMenu("Download");
            help = new JMenu("Help");
            newDownload = new JMenuItem("New Download");
            resume = new JMenuItem("Resume");
            pause = new JMenuItem("Pause");
            cancel = new JMenuItem("Cancel");
            remove = new JMenuItem("Remove");
            settings = new JMenuItem("Settings");
            export = new JMenuItem("Export");
            exit = new JMenuItem("Exit");
            about = new JMenuItem("About");

            about.setAccelerator(KeyStroke.getKeyStroke('A', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            about.setMnemonic('A');

            newDownload.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            newDownload.setMnemonic('N');

            resume.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            resume.setMnemonic('R');

            pause.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            pause.setMnemonic('P');

            remove.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            remove.setMnemonic('m');

            cancel.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            cancel.setMnemonic('C');

            settings.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            settings.setMnemonic('S');

            exit.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
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
            download.add(export);
            download.add(settings);
            download.addSeparator();
            download.add(exit);
            help.add(about);
            menuBar.add(download);
            menuBar.add(help);
            MenuHandler menuHandler = new MenuHandler();

        }

        public void downloadSelected() {
            resume.setEnabled(true);
            pause.setEnabled(true);
            remove.setEnabled(true);
            cancel.setEnabled(true);
        }

        public void downloadCompletedPSelected(){
            remove.setEnabled(true);
        }

        public void downloadQueuePSelected(){
            resume.setEnabled(false);
            pause.setEnabled(false);
            remove.setEnabled(true);
            cancel.setEnabled(true);
        }

        public void downloadUnSelected() {
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
                about.addActionListener(this);
                export.addActionListener(this);

            }


            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == newDownload) {
                    Manager.getInstance().newDownload();
                }
                if (e.getSource() == pause) {
                    Manager.getInstance().pause();
                }
                if (e.getSource() == resume) {
                    Manager.getInstance().resumeDownload();
                }
                if (e.getSource() == remove) {
                    Manager.getInstance().remove();
                }
                if (e.getSource() == cancel) {
                    Manager.getInstance().cancel();
                }
                if (e.getSource() == settings) {
                    Manager.getInstance().settingFrame();
                }
                if (e.getSource() == export) {
                    File file = new File("File.zip");
                    if (file.exists()){
                        file.delete();
                    }
                    ZipUtils zipUtils = new ZipUtils("File.zip","files");
                    zipUtils.generateFileList(new File("files"));
                    zipUtils.zipIt("File.zip");
                }
                if (e.getSource() == exit) {
                    Manager.getInstance().exit();
                }
                if (e.getSource() == about) {
                    new AboutFrame();
                }
            }

        }
    }
}



