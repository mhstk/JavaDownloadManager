import javax.swing.*;

public class Main {

        public static void main(String[] args) {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                System.err.println(e);
            }
            MainFrame mainFrame = MainFrame.getInstance();





//        JFrame jFrame = new JFrame();
//        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jFrame.setSize(20,20);
//        jFrame.setLocation(130,70);
//        jFrame.setLayout(new BorderLayout());
//        JPanel jPanel1 = new JPanel();
//        jPanel1.setLayout(new BorderLayout());
//        JPanel processPanel = new JPanel();
//        JPanel keyPanel = new JPanel();
//
//        SpringLayout processLayout = new SpringLayout();
//        processPanel.setLayout(processLayout);
//
//        Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\setak\\IdeaProjects\\HM\\HM7\\src\\backLeft.png");
//        BackgroundPanel backgroundPanel = new BackgroundPanel(image,0);
//        JButton jButton = new JButton();
//        JLabel jLabel = new JLabel("NEW LABEL");
//        processPanel.add(jButton);
//        processPanel.add(jLabel);
//
//        backgroundPanel.add(processPanel);
//        jPanel1.add(backgroundPanel);
//        jFrame.add(jPanel1,BorderLayout.WEST);
//        jFrame.setVisible(true);
//        jFrame.setSize(960,660);


        }

    }


