import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class FileUtils {

    public static void write(String fileName, Object object) {
        ArrayList<Object> objects = new ArrayList<>();
        try {

            ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(new File(fileName)));
            while (true){
                Object o = ois.readObject();
                if (o==null){
                    break;
                }else {
                    objects.add(o);
                }
            }
            ois.close();
            objects.add(object);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
            for (int i=0 ; i<objects.size(); i++) {
                oos.writeObject(objects.get(i));
            }
            oos.writeObject(null);

            oos.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeAll(String fileName , ArrayList<DownloadPanel> list){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
            for (int i=0 ; i<list.size(); i++) {
                DownloadItem downloadItem = list.get(i).getDownloadItem();
                oos.writeObject(downloadItem);
            }
            oos.writeObject(null);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList read(String fileName) {
        ArrayList<Object> objects = new ArrayList<>();
        ObjectInputStream ois ;
        try {

            ois = new ObjectInputStream(new FileInputStream(new File(fileName)));
            while (true){
                Object o = ois.readObject();
                if (o==null){
                    break;
                }else {
                    objects.add(o);
                }
            }

            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return objects;
        }
    }


    public static void removeAnObject(String fileName, Object object) {
        DownloadItem downloadItemO = (DownloadItem) object;
       // System.out.println("Object : "+ downloadItemO);
        ArrayList<Object> objects = new ArrayList<>();
        try {

            ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(new File(fileName)));
            while (true){
                Object o = ois.readObject();
                if (o==null){
                    break;
                }else {
                    objects.add(o);
                }
            }
            ois.close();


          //  System.out.println("size : "+objects.size());
            Iterator it = objects.iterator();
            while (it.hasNext()){
                DownloadItem downloadItem = (DownloadItem) it.next();
          //      System.out.println(downloadItem);
                if (downloadItem.equals(downloadItemO)){
            //        System.out.println("founded  :  "+downloadItem);
                    it.remove();
                }
            }

        //    System.out.println("size : "+objects.size());
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
            for (Object object1 : objects) {
                oos.writeObject(object1);
            }
            oos.writeObject(null);

            oos.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void write(String fileName , String text){
        try {
            PrintStream ps = new PrintStream( new FileOutputStream(fileName));
            ps.print(text);
            ps.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String readLF(){
        String lookAndFeel = null;
        String fileName = "files\\settings.jdm";
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            scanner.nextLine();
            scanner.nextLine();
            lookAndFeel =  scanner.nextLine();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lookAndFeel;
    }

    public static int readlimit(){
        String fileName = "files\\settings.jdm";
        int limit = 0;
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            limit = scanner.nextInt();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return limit;
    }

    public static String readDirectory(){
        String fileName = "files\\settings.jdm";
        String directory = null;
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            scanner.nextLine();
            directory = scanner.nextLine();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return directory;
    }

    public static ArrayList<String> readFiltered(){
        ArrayList<String> filter = new ArrayList<>();
        String fileName = "files\\filter.jdm";
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            String line ;
            while (scanner.hasNextLine()){
                line = scanner.nextLine();
//                System.out.println(line);
                filter.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filter;
    }

    public static void writeRemoved(String fileName,Object object){
        try {
            PrintStream ps = new PrintStream( new FileOutputStream(fileName,true));
            ps.print(object.toString()+"\n");
            ps.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void clear(String fileName){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
            oos.writeObject(null);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readLanguage(String language){
        String fileName = "" ;
        if (language.equals("English")){
             fileName= "files\\English.jdm";
        }
        if (language.equals("فارسی")){
            fileName = "files\\Persian.jdm";
        }
        ArrayList<String> words = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            String line ;
            while (scanner.hasNextLine()){
                line = scanner.nextLine();
//                System.out.println(line);
                words.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }


}
