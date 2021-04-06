package Arkanoid.Logic.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public interface Savable<T extends Savable> {

    static int getLastId(File path) {
        path.mkdirs();
        return path.list().length;
    }
    static String loadSerializedString(File path) {
        String res = "";
        if (!path.exists())
            return null;
        try {
            Scanner scanner = new Scanner(path);
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNext())
                builder.append(scanner.next() + "\n");
            res = builder.toString();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("LOAD FAILED");
        }
        return res;
    }

    String serialize();
    void deserialize(Scanner serialized);

    default void extraSave(File path) {

    }
    default void extraLoad(File path) {

    }
    default void load(File path) {
        deserialize(new Scanner(loadSerializedString(path)));
        extraLoad(path.getParentFile());
    }
    default void save(File path) {
        extraSave(path.getParentFile());
        String obj = serialize();
        if (path.exists())
            path.delete();
        path.getParentFile().mkdirs();
        try {
            PrintStream printStream = new PrintStream(path);
            printStream.print(obj);
            printStream.flush();
            printStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("FAILED SAVING");
        }
    }
}
