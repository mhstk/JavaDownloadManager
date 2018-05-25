import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(FileUtils.readLF());
        } catch (Exception e) {
            System.err.println(e);
        }

        Manager manager = Manager.getInstance();
        manager.run();

    }

}


