import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NewDownFrame extends JFrame {

    private JComponent mainPanel;
    private SpringLayout layout;
    private JTextField linkAddressT;
    private JLabel linkAddressL;
    private JLabel directoryAddressL;
    private JTextField directoryAddressT;
    private JButton directoryB;
    private JButton addB;
    private JButton cancelB;
    private KeyHandler keyHandler;
    private String downloadName;
    private String serverAddress;
    private  String savedAddress;
    private  double downloadSize;
    private Time startedTime;



    NewDownFrame(){
        setLocation(300, 100);
        setPreferredSize(new Dimension(460, 500));
        setSize(460, 500);
        setTitle("New Download");
        setIconImage(MainFrame.getInstance().icon);


        savedAddress = Manager.getInstance().getDirectory();

        keyHandler = new KeyHandler(this);
        mainPanel = new JPanel();
        MainFrame.setComponentSize(mainPanel,getSize());
        layout = new SpringLayout();
        linkAddressT = new JTextField();
        directoryAddressT = new JTextField(savedAddress);
        directoryAddressL = new JLabel("Directory : ");
        directoryAddressL.setFont(new Font("Arial",Font.PLAIN,15));
        linkAddressL = new JLabel("URL : ");
        linkAddressL.setFont(new Font("Arial",Font.PLAIN,15));
        directoryB = new JButton("Open") ;
        MainFrame.setComponentSize(directoryB,new Dimension(60,30));
        directoryB.addActionListener(keyHandler);
        addB = new JButton("Add");
        addB.setFont(new Font("Arial",Font.CENTER_BASELINE,15));
        MainFrame.setComponentSize(addB,new Dimension(80,40));
        addB.addActionListener(keyHandler);
        cancelB = new JButton("Cancel");
        cancelB.setFont(new Font("Arial",Font.CENTER_BASELINE,15));
        MainFrame.setComponentSize(cancelB,new Dimension(80,40));
        cancelB.addActionListener(keyHandler);
        mainPanel.add(directoryAddressL);
        mainPanel.add(directoryAddressT);
        mainPanel.add(linkAddressL);
        mainPanel.add(linkAddressT);
        mainPanel.add(directoryAddressL);
        mainPanel.add(directoryB);
        mainPanel.add(addB);
        mainPanel.add(cancelB);

        MainFrame.setComponentSize(linkAddressT,new Dimension(310,30));
        MainFrame.setComponentSize(directoryAddressT,new Dimension(250,30));
        layout.putConstraint(SpringLayout.NORTH,linkAddressL,55,SpringLayout.NORTH,mainPanel);
        layout.putConstraint(SpringLayout.WEST,linkAddressL,60,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,directoryAddressL,40,SpringLayout.SOUTH,linkAddressL);
        layout.putConstraint(SpringLayout.WEST,directoryAddressL,30,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.WEST,linkAddressT,105,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,linkAddressT,50,SpringLayout.NORTH,mainPanel);
        layout.putConstraint(SpringLayout.WEST,directoryAddressT,105,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,directoryAddressT,30,SpringLayout.SOUTH,linkAddressT);
        layout.putConstraint(SpringLayout.WEST,directoryB,5,SpringLayout.EAST,directoryAddressT);
        layout.putConstraint(SpringLayout.NORTH,directoryB,30,SpringLayout.SOUTH,linkAddressT);
        layout.putConstraint(SpringLayout.NORTH,addB,400,SpringLayout.NORTH,mainPanel);
        layout.putConstraint(SpringLayout.WEST,addB,300,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,cancelB,400,SpringLayout.NORTH,mainPanel);
        layout.putConstraint(SpringLayout.WEST,cancelB,80,SpringLayout.WEST,mainPanel);

        getRootPane().setDefaultButton(addB);
        setResizable(false);
        mainPanel.setLayout(layout);
        setContentPane(mainPanel);
        MainFrame.getInstance().setEnabled(false);
        setVisible(true);

        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                close();
            }
        });
    }

    public void close(){
        MainFrame.getInstance().setEnabled(true);
        this.dispose();
    }


    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getSavedAddress() {
        return savedAddress;
    }

    public void setSavedAddress(String savedAddress) {
        this.savedAddress = savedAddress;
    }

    public double getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(double downloadSize) {
        this.downloadSize = downloadSize;
    }

    public Time getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Time startedTime) {
        this.startedTime = startedTime;
    }

    public JTextField getLinkAddressT() {
        return linkAddressT;
    }

    public JTextField getDirectoryAddressT() {
        return directoryAddressT;
    }

    class KeyHandler implements ActionListener {

        private NewDownFrame newDownFrame;

        public KeyHandler(NewDownFrame newDownFrame) {
            this.newDownFrame = newDownFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addB ){
                if (newDownFrame.getLinkAddressT().getText().equals("") || newDownFrame.getDirectoryAddressT().getText().equals("")){
                    JOptionPane.showMessageDialog(newDownFrame, "Information is not valid","Error",JOptionPane.ERROR_MESSAGE);
                }else {
                    String address = newDownFrame.getLinkAddressT().getText();
                    StringBuilder SBAddress = new StringBuilder(address);
                    String reverseAddress = SBAddress.reverse() + "";
                    String reverseName = reverseAddress;
                    if (reverseAddress.contains("\\")) {
                        reverseName = reverseAddress.substring(0, reverseAddress.indexOf('\\'));
                    }
                    StringBuilder SBName = new StringBuilder(reverseName);
                    String name = SBName.reverse() + "";
                    System.out.println(name);
                    newDownFrame.setDownloadName(name);
                    newDownFrame.setSavedAddress(newDownFrame.getDirectoryAddressT().getText());
                    newDownFrame.setStartedTime(new Time(12, 30, 2, 7, 2018));
                    newDownFrame.setServerAddress(address);
                    newDownFrame.setDownloadSize(200);
                    newDownFrame.close();
                    Manager.getInstance().addNewDownload(newDownFrame);

                }
            }
            if (e.getSource() == cancelB){
               newDownFrame.close();
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
