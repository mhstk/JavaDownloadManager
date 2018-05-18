import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsFrame extends JFrame {
    private JComponent mainPanel;
    private SpringLayout layout;
    private JCheckBox limitCHB;
    private JSpinner limitS;
    private JLabel limitL;
    private JLabel directoryAddressL;
    private JTextField directoryAddressT;
    private JButton directoryB;
    private JButton ok;
    private JButton cancelB;
    private JLabel lookAndFeelL;
    private JRadioButton windowsLF ;
    private JRadioButton nimbusLF ;
    private JRadioButton windowsClassicLF ;
    private JRadioButton metalLF ;
    private JRadioButton motifLF ;
    private ButtonGroup buttonGroup;

    SettingsFrame() {
        setLocation(300, 100);
        setPreferredSize(new Dimension(460, 500));
        setSize(460, 500);
        setTitle("Settings");
        setIconImage(MainFrame.getInstance().icon);

        mainPanel = new JPanel();
        MainFrame.setComponentSize(mainPanel, getSize());
        layout = new SpringLayout();
        limitCHB = new JCheckBox("limit download at the same time");
        limitCHB.setFont(new Font("Tahoma",Font.PLAIN,15));
        limitCHB.setSelected(false);
        limitL = new JLabel("Download at the same time :  ");
        limitL.setFont(new Font("Tahoma",Font.PLAIN,14));
        limitL.setEnabled(false);

        limitS = new JSpinner(new SpinnerNumberModel(1,1, Manager.getInstance().getMaxDownload(), 1));
        limitS.setEnabled(false);
        limitS.setFont(new Font("Tahoma",Font.PLAIN,14));
        if (Manager.getInstance().getLimitDownload()<Manager.getInstance().getMaxDownload()){
            limitL.setEnabled(true);
            limitS.setEnabled(true);
            limitS = new JSpinner(new SpinnerNumberModel(Manager.getInstance().getLimitDownload(),1, Manager.getInstance().getMaxDownload(), 1));
            limitCHB.setSelected(true);

        }
        directoryAddressL = new JLabel("Choose default directory :  ");
        directoryAddressL.setFont(new Font("Tahoma",Font.PLAIN,15));
        directoryAddressT = new JTextField(Manager.getInstance().getDirectory());
        directoryAddressT.setFont(new Font("Tahoma",Font.PLAIN,14));
        MainFrame.setComponentSize(directoryAddressT,new Dimension(300,30));
        directoryB = new JButton("Open");
        directoryB.setFont(new Font("Tahoma",Font.PLAIN,13));
        MainFrame.setComponentSize(directoryB,new Dimension(100,30));
        directoryB.addActionListener(new ButtonHandler(this));
        ok = new JButton("OK");
        ok.setFont(new Font("Tahoma",Font.PLAIN,15));
        MainFrame.setComponentSize(ok,new Dimension(80,30));
        ok.addActionListener(new ButtonHandler(this));
        cancelB = new JButton("Cancel");
        cancelB.setFont(new Font("Tahoma",Font.PLAIN,15));
        MainFrame.setComponentSize(cancelB,new Dimension(100,30));
        cancelB.addActionListener(new ButtonHandler(this));
        windowsLF = new JRadioButton("Windows");
        nimbusLF = new JRadioButton("Nimbus");
        windowsClassicLF = new JRadioButton("Classic Windows");
        metalLF = new JRadioButton("Metal");
        motifLF = new JRadioButton("Motif");
        lookAndFeelL = new JLabel("Choose look and feel: ");
        lookAndFeelL.setFont(new Font("Tahoma",Font.PLAIN,15));
        buttonGroup = new ButtonGroup();
        buttonGroup.add(windowsLF);buttonGroup.add(nimbusLF);buttonGroup.add(windowsClassicLF);
        buttonGroup.add(metalLF);buttonGroup.add(motifLF);

        limitCHB.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (limitCHB.isSelected()) {
                    limitL.setEnabled(true);
                    limitS.setEnabled(true);
                }else {
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
        mainPanel.add(ok);
        mainPanel.add(cancelB);
        mainPanel.add(windowsLF);
        mainPanel.add(nimbusLF);
        mainPanel.add(windowsClassicLF);
        mainPanel.add(metalLF);
        mainPanel.add(motifLF);
        mainPanel.add(lookAndFeelL);

        if (Manager.getInstance().getLookAndFeelS().equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")){
            windowsLF.setSelected(true);
        }
        if (Manager.getInstance().getLookAndFeelS().equals("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel")){
            windowsClassicLF.setSelected(true);
        }
        if (Manager.getInstance().getLookAndFeelS().equals("javax.swing.plaf.nimbus.NimbusLookAndFeel")){
            nimbusLF.setSelected(true);
        }
        if (Manager.getInstance().getLookAndFeelS().equals("javax.swing.plaf.metal.MetalLookAndFeel")){
            metalLF.setSelected(true);
        }
        if (Manager.getInstance().getLookAndFeelS().equals("com.sun.java.swing.plaf.motif.MotifLookAndFeel")) {
            motifLF.setSelected(true);
        }

        layout.putConstraint(SpringLayout.WEST,limitCHB,30,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,limitCHB,50,SpringLayout.NORTH,mainPanel);
        layout.putConstraint(SpringLayout.WEST,limitL,50,SpringLayout.WEST,limitCHB);
        layout.putConstraint(SpringLayout.NORTH,limitL,20,SpringLayout.SOUTH,limitCHB);
        layout.putConstraint(SpringLayout.WEST,limitS,40,SpringLayout.EAST,limitL);
        layout.putConstraint(SpringLayout.NORTH,limitS,-2,SpringLayout.NORTH,limitL);
        layout.putConstraint(SpringLayout.WEST,directoryAddressL,0,SpringLayout.WEST,limitCHB);
        layout.putConstraint(SpringLayout.NORTH,directoryAddressL,40,SpringLayout.SOUTH,limitL);
        layout.putConstraint(SpringLayout.WEST,directoryAddressT,0,SpringLayout.WEST,directoryAddressL);
        layout.putConstraint(SpringLayout.NORTH,directoryAddressT,10,SpringLayout.SOUTH,directoryAddressL);
        layout.putConstraint(SpringLayout.WEST,directoryB,8,SpringLayout.EAST,directoryAddressT);
        layout.putConstraint(SpringLayout.NORTH,directoryB,0,SpringLayout.NORTH,directoryAddressT);
        layout.putConstraint(SpringLayout.WEST,lookAndFeelL,0,SpringLayout.WEST,directoryAddressT);
        layout.putConstraint(SpringLayout.NORTH,lookAndFeelL,40,SpringLayout.SOUTH,directoryAddressT);
        layout.putConstraint(SpringLayout.WEST,windowsLF,30,SpringLayout.WEST,lookAndFeelL);
        layout.putConstraint(SpringLayout.NORTH,windowsLF,20,SpringLayout.SOUTH,lookAndFeelL);
        layout.putConstraint(SpringLayout.WEST,windowsClassicLF,50,SpringLayout.EAST,windowsLF);
        layout.putConstraint(SpringLayout.NORTH,windowsClassicLF,0,SpringLayout.NORTH,windowsLF);
        layout.putConstraint(SpringLayout.WEST,nimbusLF,0,SpringLayout.WEST,windowsLF);
        layout.putConstraint(SpringLayout.NORTH,nimbusLF,10,SpringLayout.SOUTH,windowsLF);
        layout.putConstraint(SpringLayout.WEST,metalLF,0,SpringLayout.WEST,windowsClassicLF);
        layout.putConstraint(SpringLayout.NORTH,metalLF,10,SpringLayout.SOUTH,windowsClassicLF);
        layout.putConstraint(SpringLayout.WEST,motifLF,0,SpringLayout.WEST,nimbusLF);
        layout.putConstraint(SpringLayout.NORTH,motifLF,10,SpringLayout.SOUTH,nimbusLF);
        layout.putConstraint(SpringLayout.EAST,ok,-50,SpringLayout.EAST,mainPanel);
        layout.putConstraint(SpringLayout.SOUTH,ok,-30,SpringLayout.SOUTH,mainPanel);
        layout.putConstraint(SpringLayout.WEST,cancelB,50,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.SOUTH,cancelB,-30,SpringLayout.SOUTH,mainPanel);


        getRootPane().setDefaultButton(ok);
        setResizable(false);
        mainPanel.setLayout(layout);
        setContentPane(mainPanel);
        MainFrame.getInstance().setEnabled(false);
        setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }

    public void close() {
        MainFrame.getInstance().setEnabled(true);
        this.dispose();
    }
    class ButtonHandler implements ActionListener{

        private SettingsFrame settingsFrame;

        public ButtonHandler(SettingsFrame settingsFrame) {
            this.settingsFrame = settingsFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ok){
                if (directoryAddressT.getText().equals("")){
                    JOptionPane.showMessageDialog(settingsFrame, "Information is not valid","Error",JOptionPane.ERROR_MESSAGE);
                }else {
                    Manager manager = Manager.getInstance();
                    manager.setDirectory(directoryAddressT.getText());
                    if (limitCHB.isSelected()) {
                        manager.setLimitDownload((int) limitS.getValue());
                    }else {
                        manager.setLimitDownload( manager.getMaxDownload());
                    }
                    if (windowsLF.isSelected()){
                        manager.setLookAndFeelS("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    }
                    if (windowsClassicLF.isSelected()){
                        manager.setLookAndFeelS("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
                    }
                    if (nimbusLF.isSelected()){
                        manager.setLookAndFeelS("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    }
                    if (metalLF.isSelected()){
                        manager.setLookAndFeelS("javax.swing.plaf.metal.MetalLookAndFeel");
                    }
                    if (motifLF.isSelected()){
                        manager.setLookAndFeelS("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    }
                    manager.setMainFrameUI();
                    close();
                }
            }
            if (e.getSource() == cancelB){
                close();
            }
            if (e.getSource() == directoryB){
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File(directoryAddressT.getText()));
                chooser.setDialogTitle("Choose a Folder");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                    directoryAddressT.setText(chooser.getSelectedFile()+"");
                } else {
                    System.out.println("No Selection ");
                }
            }
        }
    }
}

