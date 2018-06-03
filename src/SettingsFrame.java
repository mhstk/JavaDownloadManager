import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class SettingsFrame extends JFrame {
    private JTabbedPane jTabbedPane;
    private JPanel filterPanel;
    private JPanel mainPanel;
    private JList<String> jList;
    private DefaultListModel<String> defaultListModel;
    private SpringLayout mainLayout;
    private SpringLayout filterLayout;
    private SpringLayout layout;
    private JCheckBox limitCHB;
    private JSpinner limitS;
    private JLabel limitL;
    private JLabel directoryAddressL;
    private JLabel languageL;
    private JComboBox<String> languageC;
    private JTextField directoryAddressT;
    private JButton directoryB;
    private JButton ok;
    private JButton cancelB;
    private JLabel lookAndFeelL;
    private JRadioButton windowsLF;
    private JRadioButton nimbusLF;
    private JRadioButton windowsClassicLF;
    private JRadioButton metalLF;
    private JRadioButton motifLF;
    private ButtonGroup buttonGroup;
    private JButton removeFilterB;
    private JButton addFilterB;
    private JTextField filterT;
    JLabel filterL;

    SettingsFrame() {
        setLocation(300, 100);
        setPreferredSize(new Dimension(470, 565));
        setSize(470, 550);
        setTitle("Settings");
        setBackground(new Color(43,49,52));
        setIconImage(MainFrame.getInstance().icon);


        jTabbedPane = new JTabbedPane();
        jTabbedPane.setBackground(new Color(43,49,52));
        MainFrame.setComponentSize(jTabbedPane, new Dimension(getWidth(),getHeight()-50));
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(43,49,52));
        MainFrame.setComponentSize(mainPanel, getSize());
        filterPanel = new JPanel();
        filterPanel.setBackground(new Color(43,49,52));
        MainFrame.setComponentSize(filterPanel, getSize());
        mainLayout = new SpringLayout();
        layout = new SpringLayout();
        filterLayout = new SpringLayout();
        limitCHB = new JCheckBox("limit download at the same time");
        limitCHB.setForeground(Color.WHITE);
        limitCHB.setFont(new Font("Tahoma", Font.PLAIN, 15));
        limitCHB.setSelected(false);
        limitL = new JLabel("Download at the same time :  ");
        limitL.setFont(new Font("Tahoma", Font.PLAIN, 14));
        limitL.setForeground(Color.WHITE);
        limitL.setEnabled(false);

        limitS = new JSpinner(new SpinnerNumberModel(1, 1, Manager.getInstance().getMaxDownload(), 1));
        limitS.setEnabled(false);
        limitS.setFont(new Font("Tahoma", Font.PLAIN, 14));
        if (Manager.getInstance().getLimitDownload() < Manager.getInstance().getMaxDownload()) {
            limitL.setEnabled(true);
            limitS.setEnabled(true);
            limitS.setValue(Manager.getInstance().getLimitDownload());
            limitCHB.setSelected(true);

        }
        directoryAddressL = new JLabel("Choose default directory :  ");
        directoryAddressL.setFont(new Font("Tahoma", Font.PLAIN, 15));
        languageL = new JLabel("Choose Language : ");
        languageL.setForeground(Color.WHITE);
        languageL.setFont(new Font("Tahoma", Font.PLAIN, 15));
        directoryAddressL.setForeground(Color.WHITE);
        directoryAddressT = new JTextField(Manager.getInstance().getDirectory());
        directoryAddressT.setFont(new Font("Tahoma", Font.PLAIN, 14));
        MainFrame.setComponentSize(directoryAddressT, new Dimension(300, 30));
        directoryB = new JButton("Open");
        directoryB.setFont(new Font("Tahoma", Font.PLAIN, 13));
        MainFrame.setComponentSize(directoryB, new Dimension(100, 30));
        directoryB.addActionListener(new ButtonHandler(this));
        ok = new JButton("OK");
        ok.setFont(new Font("Tahoma", Font.PLAIN, 15));
        MainFrame.setComponentSize(ok, new Dimension(80, 30));
        ok.addActionListener(new ButtonHandler(this));
        cancelB = new JButton("Cancel");
        cancelB.setFont(new Font("Tahoma", Font.PLAIN, 15));
        MainFrame.setComponentSize(cancelB, new Dimension(100, 30));
        cancelB.addActionListener(new ButtonHandler(this));
        windowsLF = new JRadioButton("Windows");
        windowsLF.setForeground(Color.WHITE);
        nimbusLF = new JRadioButton("Nimbus");
        nimbusLF.setForeground(Color.WHITE);
        windowsClassicLF = new JRadioButton("Classic Windows");
        windowsClassicLF.setForeground(Color.WHITE);
        metalLF = new JRadioButton("Metal");
        metalLF.setForeground(Color.WHITE);
        motifLF = new JRadioButton("Motif");
        motifLF.setForeground(Color.WHITE);
        lookAndFeelL = new JLabel("Choose look and feel: ");
        lookAndFeelL.setForeground(Color.WHITE);
        lookAndFeelL.setFont(new Font("Tahoma", Font.PLAIN, 15));
        buttonGroup = new ButtonGroup();
        buttonGroup.add(windowsLF);
        buttonGroup.add(nimbusLF);
        buttonGroup.add(windowsClassicLF);
        buttonGroup.add(metalLF);
        buttonGroup.add(motifLF);
        languageC = new JComboBox<>(new String[]{"English" , "فارسی"});
        languageC.setSelectedItem(Manager.getInstance().getLanguage());

        limitCHB.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (limitCHB.isSelected()) {
                    limitL.setEnabled(true);
                    limitS.setEnabled(true);
                } else {
                    limitL.setEnabled(false);
                    limitS.setEnabled(false);
                }
            }
        });


        mainPanel.add(limitCHB);
        mainPanel.add(limitL);
        mainPanel.add(limitS);
        mainPanel.add(directoryAddressL);
        mainPanel.add(directoryAddressT);
        mainPanel.add(directoryB);
        add(ok);
        add(cancelB);
        mainPanel.add(windowsLF);
        mainPanel.add(nimbusLF);
        mainPanel.add(windowsClassicLF);
        mainPanel.add(metalLF);
        mainPanel.add(motifLF);
        mainPanel.add(lookAndFeelL);
        mainPanel.add(languageL);
        mainPanel.add(languageC);

        if (Manager.getInstance().getLookAndFeelS().equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")) {
            windowsLF.setSelected(true);
        }
        if (Manager.getInstance().getLookAndFeelS().equals("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel")) {
            windowsClassicLF.setSelected(true);
        }
        if (Manager.getInstance().getLookAndFeelS().equals("javax.swing.plaf.nimbus.NimbusLookAndFeel")) {
            nimbusLF.setSelected(true);
        }
        if (Manager.getInstance().getLookAndFeelS().equals("javax.swing.plaf.metal.MetalLookAndFeel")) {
            metalLF.setSelected(true);
        }
        if (Manager.getInstance().getLookAndFeelS().equals("com.sun.java.swing.plaf.motif.MotifLookAndFeel")) {
            motifLF.setSelected(true);
        }

        mainLayout.putConstraint(SpringLayout.WEST, limitCHB, 30, SpringLayout.WEST, mainPanel);
        mainLayout.putConstraint(SpringLayout.NORTH, limitCHB, 30, SpringLayout.NORTH, mainPanel);
        mainLayout.putConstraint(SpringLayout.WEST, limitL, 50, SpringLayout.WEST, limitCHB);
        mainLayout.putConstraint(SpringLayout.NORTH, limitL, 20, SpringLayout.SOUTH, limitCHB);
        mainLayout.putConstraint(SpringLayout.WEST, limitS, 40, SpringLayout.EAST, limitL);
        mainLayout.putConstraint(SpringLayout.NORTH, limitS, -2, SpringLayout.NORTH, limitL);
        mainLayout.putConstraint(SpringLayout.WEST, directoryAddressL, 0, SpringLayout.WEST, limitCHB);
        mainLayout.putConstraint(SpringLayout.NORTH, directoryAddressL, 40, SpringLayout.SOUTH, limitL);
        mainLayout.putConstraint(SpringLayout.WEST, directoryAddressT, 0, SpringLayout.WEST, directoryAddressL);
        mainLayout.putConstraint(SpringLayout.NORTH, directoryAddressT, 10, SpringLayout.SOUTH, directoryAddressL);
        mainLayout.putConstraint(SpringLayout.WEST, directoryB, 8, SpringLayout.EAST, directoryAddressT);
        mainLayout.putConstraint(SpringLayout.NORTH, directoryB, 0, SpringLayout.NORTH, directoryAddressT);
        mainLayout.putConstraint(SpringLayout.WEST, lookAndFeelL, 0, SpringLayout.WEST, directoryAddressT);
        mainLayout.putConstraint(SpringLayout.NORTH, lookAndFeelL, 40, SpringLayout.SOUTH, directoryAddressT);
        mainLayout.putConstraint(SpringLayout.WEST, windowsLF, 30, SpringLayout.WEST, lookAndFeelL);
        mainLayout.putConstraint(SpringLayout.NORTH, windowsLF, 20, SpringLayout.SOUTH, lookAndFeelL);
        mainLayout.putConstraint(SpringLayout.WEST, windowsClassicLF, 50, SpringLayout.EAST, windowsLF);
        mainLayout.putConstraint(SpringLayout.NORTH, windowsClassicLF, 0, SpringLayout.NORTH, windowsLF);
        mainLayout.putConstraint(SpringLayout.WEST, nimbusLF, 0, SpringLayout.WEST, windowsLF);
        mainLayout.putConstraint(SpringLayout.NORTH, nimbusLF, 10, SpringLayout.SOUTH, windowsLF);
        mainLayout.putConstraint(SpringLayout.WEST, metalLF, 0, SpringLayout.WEST, windowsClassicLF);
        mainLayout.putConstraint(SpringLayout.NORTH, metalLF, 10, SpringLayout.SOUTH, windowsClassicLF);
        mainLayout.putConstraint(SpringLayout.WEST, motifLF, 0, SpringLayout.WEST, nimbusLF);
        mainLayout.putConstraint(SpringLayout.NORTH, motifLF, 10, SpringLayout.SOUTH, nimbusLF);
        mainLayout.putConstraint(SpringLayout.WEST, languageL, 0, SpringLayout.WEST, lookAndFeelL);
        mainLayout.putConstraint(SpringLayout.NORTH, languageL, 25, SpringLayout.SOUTH, motifLF);
        mainLayout.putConstraint(SpringLayout.WEST, languageC, 70, SpringLayout.EAST, languageL);
        mainLayout.putConstraint(SpringLayout.NORTH, languageC, 0, SpringLayout.NORTH, languageL);
        layout.putConstraint(SpringLayout.EAST, ok, -70, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, ok, -70, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, cancelB, 50, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, cancelB, -70, SpringLayout.SOUTH, this);


        addFilterB = new JButton("Add filter");
        MainFrame.setComponentSize(addFilterB,new Dimension(175,30));
        addFilterB.setFont(new Font("Tahoma",Font.PLAIN,13));
        addFilterB.addActionListener(new ButtonHandler(this));
        removeFilterB = new JButton("Remove filter");
        MainFrame.setComponentSize(removeFilterB,new Dimension(175,30));
        removeFilterB.setFont(new Font("Tahoma",Font.PLAIN,13));
        removeFilterB.addActionListener(new ButtonHandler(this));
        filterL = new JLabel("Enetr a site to filter: ");
        filterL.setForeground(Color.WHITE);
        filterL.setFont(new Font("Tahoma", Font.PLAIN, 14));
        filterT = new JTextField();
        filterT.setFont(new Font("Tahoma", Font.PLAIN, 14));
        MainFrame.setComponentSize(filterT, new Dimension(370, 30));
        defaultListModel = new DefaultListModel<>();
        for (String string : Manager.getInstance().getFilter()){
            defaultListModel .add(defaultListModel.size(),string);
        }
        jList = new JList<>(defaultListModel);
        jList.setFont(new Font("Tahoma", Font.PLAIN, 13));
        JScrollPane jScrollPane = new JScrollPane(jList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        MainFrame.setComponentSize(jScrollPane, new Dimension(370, 240));

        changeLanguage();
        filterPanel.add(jScrollPane);
        filterPanel.add(filterT);
        filterPanel.add(filterL);
        filterPanel.add(addFilterB);
        filterPanel.add(removeFilterB);

        filterLayout.putConstraint(SpringLayout.WEST, filterL, 40, SpringLayout.WEST, filterPanel);
        filterLayout.putConstraint(SpringLayout.NORTH, filterL, 20, SpringLayout.NORTH, filterPanel);
        filterLayout.putConstraint(SpringLayout.WEST, filterT, 0, SpringLayout.WEST, filterL);
        filterLayout.putConstraint(SpringLayout.NORTH, filterT, 10, SpringLayout.SOUTH, filterL);
        filterLayout.putConstraint(SpringLayout.WEST, addFilterB, 0, SpringLayout.WEST, filterL);
        filterLayout.putConstraint(SpringLayout.NORTH, addFilterB, 10, SpringLayout.SOUTH, filterT);
        filterLayout.putConstraint(SpringLayout.WEST, removeFilterB,20 , SpringLayout.EAST, addFilterB);
        filterLayout.putConstraint(SpringLayout.NORTH, removeFilterB, 10, SpringLayout.SOUTH, filterT);
        filterLayout.putConstraint(SpringLayout.WEST, jScrollPane, 0, SpringLayout.WEST, addFilterB);
        filterLayout.putConstraint(SpringLayout.NORTH, jScrollPane, 10, SpringLayout.SOUTH, addFilterB);
        filterLayout.putConstraint(SpringLayout.EAST, ok, -50, SpringLayout.EAST, filterPanel);
        filterLayout.putConstraint(SpringLayout.SOUTH, ok, -30, SpringLayout.SOUTH, filterPanel);
        filterLayout.putConstraint(SpringLayout.WEST, cancelB, 50, SpringLayout.WEST, filterPanel);
        filterLayout.putConstraint(SpringLayout.SOUTH, cancelB, -30, SpringLayout.SOUTH, filterPanel);


        setLayout(layout);
        getRootPane().setDefaultButton(ok);
        setResizable(false);
        mainPanel.setLayout(mainLayout);
        filterPanel.setLayout(filterLayout);
        jTabbedPane.add("General", mainPanel);
        jTabbedPane.add("Filter", filterPanel);
        add(jTabbedPane);
        MainFrame.getInstance().setEnabled(false);
        setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }

    public void changeLanguage(){
        setTitle(Manager.getInstance().getWords().get(4));
        limitCHB.setText(Manager.getInstance().getWords().get(5));
        limitL.setText(Manager.getInstance().getWords().get(6));
        directoryAddressL.setText(Manager.getInstance().getWords().get(7));
        languageL.setText(Manager.getInstance().getWords().get(8));
        lookAndFeelL.setText(Manager.getInstance().getWords().get(9));
        directoryB.setText(Manager.getInstance().getWords().get(10));
        ok.setText(Manager.getInstance().getWords().get(11));
        cancelB.setText(Manager.getInstance().getWords().get(12));
        addFilterB.setText(Manager.getInstance().getWords().get(13));
        removeFilterB.setText(Manager.getInstance().getWords().get(14));
        filterL.setText(Manager.getInstance().getWords().get(15));
    }

    public void close() {
        MainFrame.getInstance().setEnabled(true);
        this.dispose();
    }

    class ButtonHandler implements ActionListener {

        private SettingsFrame settingsFrame;

        public ButtonHandler(SettingsFrame settingsFrame) {
            this.settingsFrame = settingsFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ok) {
                if (directoryAddressT.getText().equals("")) {
                    JOptionPane.showMessageDialog(settingsFrame, "Information is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Manager manager = Manager.getInstance();
                    manager.setDirectory(directoryAddressT.getText());
                    if (limitCHB.isSelected()) {
                        manager.setLimitDownload((int) limitS.getValue());
                    } else {
                        manager.setLimitDownload(manager.getMaxDownload());
                    }
                    if (windowsLF.isSelected()) {
                        if (!manager.getLookAndFeelS().equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")) {
                            manager.setLookAndFeelS("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                            manager.setMainFrameUI();
                        }
                    }
                    if (windowsClassicLF.isSelected()) {
                        if (!manager.getLookAndFeelS().equals("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel")) {
                            manager.setLookAndFeelS("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
                            manager.setMainFrameUI();
                        }
                    }
                    if (nimbusLF.isSelected()) {
                        if (!manager.getLookAndFeelS().equals("javax.swing.plaf.nimbus.NimbusLookAndFeel")) {
                            manager.setLookAndFeelS("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                            manager.setMainFrameUI();
                        }
                    }
                    if (metalLF.isSelected()) {
                        if (!manager.getLookAndFeelS().equals("javax.swing.plaf.metal.MetalLookAndFeel")) {
                            manager.setLookAndFeelS("javax.swing.plaf.metal.MetalLookAndFeel");
                            manager.setMainFrameUI();
                        }
                    }
                    if (motifLF.isSelected()) {
                        if (!manager.getLookAndFeelS().equals("com.sun.java.swing.plaf.motif.MotifLookAndFeel")) {
                            manager.setLookAndFeelS("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                            manager.setMainFrameUI();
                        }
                    }
                    String filter = "";
                    manager.getFilter().clear();
                    for(int i = 0; i< jList.getModel().getSize();i++){
                        filter += (jList.getModel().getElementAt(i)) + "\n";
                        manager.getFilter().add(jList.getModel().getElementAt(i));
                    }
                    FileUtils.write("files\\filter.jdm",filter);
                    String settings = Manager.getInstance().getLimitDownload() + "\n" + Manager.getInstance().getDirectory() + "\n" + Manager.getInstance().getLookAndFeelS();
                    FileUtils.write("files\\settings.jdm", settings);
                    manager.setLanguage((String) languageC.getSelectedItem());
                    manager.changeLangage();
                    close();
                }
            }
            if (e.getSource() == cancelB) {
                close();
            }
            if (e.getSource() == directoryB) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File(directoryAddressT.getText()));
                chooser.setDialogTitle("Choose a Folder");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                    directoryAddressT.setText(chooser.getSelectedFile() + "");
                } else {
                    System.out.println("No Selection ");
                }
            }
            if (e.getSource() == addFilterB){
                if (!filterT.getText().equals("")) {
                    defaultListModel.add(defaultListModel.getSize(), filterT.getText());
                    filterT.setText("");
                }
            }
            if (e.getSource() == removeFilterB){
                int[] selectedIx = jList.getSelectedIndices();
                System.out.println(selectedIx.length);
                for (int i = selectedIx.length-1; i >= 0 ; i--) {
                    System.out.println(i);
                    defaultListModel.remove(selectedIx[i]);
                }
            }
        }
    }
}

