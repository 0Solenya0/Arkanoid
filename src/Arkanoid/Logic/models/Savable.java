package Arkanoid.Logic.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;

public interface Savable<T extends Savable> {

    static int getLastId(File path) {
        path.mkdirs();
        return path.list().length;
    }

    String serialize();
    //T deserialize(String serialized);

    default void extraSave(File path) {

    }
    default void extraLoad(File path) {

    }
    /*default T load(File path) {
        T obj = deserialize(loadSerializedString(path));
        obj.extraLoad(path);
        return obj;
    }*/
    default String loadSerializedString(File path) {
        String res = "";
        if (!path.exists())
            return null;
        try {
            Scanner scanner = new Scanner(path);
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNext())
                builder.append(scanner.next());
            res = builder.toString();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("LOAD FAILED");
        }
        return res;
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
