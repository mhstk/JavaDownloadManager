import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class NewDownFrame extends JFrame {

    private JComponent mainPanel;
    private SpringLayout layout;
    private JTextField linkAddressT;
    private JLabel linkAddressL;
    private JLabel directoryAddressL;
    private JTextField directoryAddressT;
    private JLabel nameL;
    private JLabel sizeL;
    private JTextField nameT;
    private JButton directoryB;
    private JButton addB;
    private JButton cancelB;
    private JButton addQB;
    private KeyHandler keyHandler;
    private String downloadName;
    private String serverAddress;
    private String savedAddress;
    private double downloadSize;


    NewDownFrame() {
        setLocation(300, 100);
        setPreferredSize(new Dimension(580, 380));
        setSize(580, 380);
        setTitle("New Download");
        setIconImage(MainFrame.getInstance().icon);


        savedAddress = Manager.getInstance().getDirectory();

        keyHandler = new KeyHandler(this);
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(43,49,52));
        MainFrame.setComponentSize(mainPanel, getSize());
        layout = new SpringLayout();
        linkAddressT = new JTextField();
        directoryAddressT = new JTextField(savedAddress);
        nameT = new JTextField();
        directoryAddressL = new JLabel("Directory : ");
        directoryAddressL.setFont(new Font("Arial", Font.PLAIN, 15));
        directoryAddressL.setForeground(Color.WHITE);
        nameL = new JLabel("Filename : ");
        nameL.setFont(new Font("Arial", Font.PLAIN, 15));
        nameL.setForeground(Color.WHITE);
        sizeL = new JLabel("");
        sizeL.setFont(new Font("Arial", Font.PLAIN, 13));
        sizeL.setForeground(Color.WHITE);
        linkAddressL = new JLabel("URL : ");
        linkAddressL.setFont(new Font("Arial", Font.PLAIN, 15));
        linkAddressL.setForeground(Color.WHITE);
        directoryB = new JButton("Open");
        MainFrame.setComponentSize(directoryB, new Dimension(60, 30));
        directoryB.addActionListener(keyHandler);
        addB = new JButton("Add");
        addB.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
        MainFrame.setComponentSize(addB, new Dimension(80, 40));
        addB.addActionListener(keyHandler);
        addQB = new JButton("Add to Queue");
        addQB.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
        MainFrame.setComponentSize(addQB, new Dimension(180, 40));
        addQB.addActionListener(keyHandler);
        cancelB = new JButton("Cancel");
        cancelB.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
        MainFrame.setComponentSize(cancelB, new Dimension(80, 40));

        MainFrame.setComponentSize(linkAddressT, new Dimension(400, 30));
        linkAddressT.getDocument().addDocumentListener(new DocumentListener() {
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
                sizeL.setText("");
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            URL url = new URL(linkAddressT.getText());
                            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                            int responseCode = httpConn.getResponseCode();
                            // always check HTTP response code first
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                sizeL.setText(String.format("File's size : %.2f",httpConn.getContentLength()/1000000.0)+" (mb)");
                            } else {
                                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
                            }
                        } catch (Exception e) {

                        }
                    }
                };
                thread.start();
            }
        });
        MainFrame.setComponentSize(directoryAddressT, new Dimension(340, 30));
        MainFrame.setComponentSize(nameT, new Dimension(400, 30));
        cancelB.addActionListener(keyHandler);
        mainPanel.add(directoryAddressL);
        mainPanel.add(directoryAddressT);
        mainPanel.add(linkAddressL);
        mainPanel.add(linkAddressT);
        mainPanel.add(nameT);
        mainPanel.add(nameL);
        mainPanel.add(sizeL);
        mainPanel.add(directoryB);
        mainPanel.add(addB);
        mainPanel.add(addQB);
        mainPanel.add(cancelB);

        layout.putConstraint(SpringLayout.NORTH, linkAddressL, 55, SpringLayout.NORTH, mainPanel);
        layout.putConstraint(SpringLayout.WEST, linkAddressL, 60, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, sizeL, 32, SpringLayout.SOUTH, linkAddressL);
        layout.putConstraint(SpringLayout.WEST, sizeL, 170, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, directoryAddressL, 40, SpringLayout.SOUTH, nameL);
        layout.putConstraint(SpringLayout.WEST, directoryAddressL, 30, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, nameL, 82, SpringLayout.SOUTH, linkAddressL);
        layout.putConstraint(SpringLayout.WEST, nameL, 30, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.WEST, linkAddressT, 105, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, linkAddressT, 50, SpringLayout.NORTH, mainPanel);
        layout.putConstraint(SpringLayout.WEST, directoryAddressT, 105, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, directoryAddressT, 30, SpringLayout.SOUTH, nameT);
        layout.putConstraint(SpringLayout.WEST, nameT, 105, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, nameT, 70, SpringLayout.SOUTH, linkAddressT);
        layout.putConstraint(SpringLayout.WEST, directoryB, 5, SpringLayout.EAST, directoryAddressT);
        layout.putConstraint(SpringLayout.NORTH, directoryB, 30, SpringLayout.SOUTH, nameT);
        layout.putConstraint(SpringLayout.SOUTH, cancelB, -20, SpringLayout.SOUTH, mainPanel);
        layout.putConstraint(SpringLayout.WEST, cancelB, 50, SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, addQB, 0, SpringLayout.NORTH, cancelB);
        layout.putConstraint(SpringLayout.WEST, addQB, 65, SpringLayout.EAST, cancelB);
        layout.putConstraint(SpringLayout.NORTH, addB, 0, SpringLayout.NORTH, addQB);
        layout.putConstraint(SpringLayout.WEST, addB, 65, SpringLayout.EAST, addQB);

        getRootPane().setDefaultButton(addB);
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

    public JTextField getNameT() {
        return nameT;
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

        public void getInfo() {

            String address = newDownFrame.getLinkAddressT().getText();
            String name;
            if (!newDownFrame.getNameT().getText().equals("")) {
                name = newDownFrame.getNameT().getText() + address.substring(address.lastIndexOf("."), address.length());
            } else {
                name = address.substring(address.lastIndexOf("/") + 1, address.length());
            }
            newDownFrame.setDownloadName(name);
            newDownFrame.setSavedAddress(newDownFrame.getDirectoryAddressT().getText());
            newDownFrame.setServerAddress(address);
            try {
                URL url = new URL(address);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                int responseCode = httpConn.getResponseCode();
                // always check HTTP response code first
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    newDownFrame.setDownloadSize(httpConn.getContentLength()/1000000.0);
                } else {
                    System.out.println("No file to download. Server replied HTTP code: " + responseCode);
                }
            } catch (Exception e) {

            }
            newDownFrame.close();
            MainFrame.getInstance().showProcessing();


        }

        public boolean checkFilter(){
            String link = newDownFrame.getLinkAddressT().getText();
            for (String filtered : Manager.getInstance().getFilter()){
                if (link.contains(filtered)){
                    return false;
                }
            }
            return true;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addB) {
                if (newDownFrame.getLinkAddressT().getText().equals("") || newDownFrame.getDirectoryAddressT().getText().equals("")) {
                    JOptionPane.showMessageDialog(newDownFrame, "Information is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!checkFilter()) {
                    JOptionPane.showMessageDialog(newDownFrame, "This site is filtered.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    getInfo();
                    Manager.getInstance().addNewDownload(newDownFrame, false);
                }

            }
            if (e.getSource() == cancelB) {
                newDownFrame.close();
            }
            if (e.getSource() == addQB) {
                if (newDownFrame.getLinkAddressT().getText().equals("") || newDownFrame.getDirectoryAddressT().getText().equals("")) {
                    JOptionPane.showMessageDialog(newDownFrame, "Information is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!checkFilter()) {
                    JOptionPane.showMessageDialog(newDownFrame, "This site is filtered.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    getInfo();
                    Manager.getInstance().addNewDownload(newDownFrame, true);
                }
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

        }
    }


}
