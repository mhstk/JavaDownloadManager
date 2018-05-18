import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InfoFrame extends JFrame {

    private DownloadItem downloadItem;
    private JComponent mainPanel;
    private SpringLayout layout;
    private JButton directoryB;
    private JButton ok;
    private JLabel nameL;
    private JLabel linkAddressL;
    private JLabel directoryAddressL;
    private JLabel sizeL;
    private JLabel downloadedSizeL;
    private JLabel startedTimeL;

    public void setDownloadItem(DownloadPanel downloadPanel){
        this.downloadItem = downloadPanel.getDownloadItem();
    }

    public void setDownloadItem(DownloadCompletedPanel downloadPanel){
        this.downloadItem = downloadPanel.getDownloadItem();
    }

    InfoFrame(){
        setLocation(300, 100);
        setPreferredSize(new Dimension(580, 330));
        setSize(580, 330);
        setIconImage(MainFrame.getInstance().icon);

        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                close();
            }
        });

    }

    public void action(){
        setTitle(" <<"+downloadItem.getName() + ">>'s info");
        mainPanel = new JPanel();
        MainFrame.setComponentSize(mainPanel,getSize());
        layout = new SpringLayout();
        directoryAddressL = new JLabel("Directory :  " + downloadItem.getSavedAddress());
        directoryAddressL.setFont(new Font("Tahoma",Font.LAYOUT_LEFT_TO_RIGHT,14));
        linkAddressL = new JLabel("URL :  "+downloadItem.getServerAddress());
        linkAddressL.setFont(new Font("Tahoma",Font.LAYOUT_LEFT_TO_RIGHT,14));
        nameL = new JLabel(downloadItem.getName());
        nameL.setFont(new Font("Courier New",Font.BOLD,35));
        sizeL = new JLabel("Size :  "+downloadItem.getSize());
        sizeL.setFont(new Font("Tahoma",Font.LAYOUT_LEFT_TO_RIGHT,14));
        downloadedSizeL = new JLabel("Downloaded :  " + downloadItem.getDownloadedSize());
        downloadedSizeL.setFont(new Font("Tahoma",Font.LAYOUT_LEFT_TO_RIGHT,14));
        startedTimeL = new JLabel("Started Time : "+downloadItem.startedTime.getHour()+":"+downloadItem.startedTime.getMin() + " ("+downloadItem.startedTime.getMonth()+"/"+downloadItem.startedTime.getDay()+"/"+downloadItem.startedTime.getYear()+")");
        startedTimeL.setFont(new Font("Tahoma",Font.LAYOUT_LEFT_TO_RIGHT,13));
        directoryB = new JButton("Open Folder") ;
        MainFrame.setComponentSize(directoryB,new Dimension(100,30));
        directoryB.addActionListener(new ButtonHandler());
        ok = new JButton("OK");
        MainFrame.setComponentSize(ok,new Dimension(70,30));
        ok.addActionListener(new ButtonHandler());


        mainPanel.add(directoryAddressL);
        mainPanel.add(nameL);
        mainPanel.add(linkAddressL);
        mainPanel.add(sizeL);
        mainPanel.add(startedTimeL);
        mainPanel.add(downloadedSizeL);
        mainPanel.add(ok);
        mainPanel.add(directoryB);

        layout.putConstraint(SpringLayout.WEST,nameL,55,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,nameL,40,SpringLayout.NORTH,mainPanel);
        layout.putConstraint(SpringLayout.WEST,directoryAddressL,55,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,directoryAddressL,30,SpringLayout.SOUTH,nameL);
        layout.putConstraint(SpringLayout.WEST,linkAddressL,55,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,linkAddressL,8,SpringLayout.SOUTH,directoryAddressL);
        layout.putConstraint(SpringLayout.WEST,sizeL,55,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,sizeL,8,SpringLayout.SOUTH,linkAddressL);
        layout.putConstraint(SpringLayout.WEST,downloadedSizeL,55,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,downloadedSizeL,8,SpringLayout.SOUTH,sizeL);
        layout.putConstraint(SpringLayout.WEST,startedTimeL,55,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,startedTimeL,8,SpringLayout.SOUTH,downloadedSizeL);
        layout.putConstraint(SpringLayout.WEST,ok,260,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,ok,250,SpringLayout.NORTH,mainPanel);
        layout.putConstraint(SpringLayout.WEST,directoryB,430,SpringLayout.WEST,mainPanel);
        layout.putConstraint(SpringLayout.NORTH,directoryB,20,SpringLayout.SOUTH,nameL);


        getRootPane().setDefaultButton(ok);
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

    class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ok){
                close();
            }
            if (e.getSource() == directoryB){

            }
        }
    }

}
