import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by papushin on 27.06.2017.
 */
public class DealManager {
    private static final String DATA_PATH = "C:/Users/papushin/IdeaProjects/Sandbox/DealManager/data/";
    protected static final String DEAL_LIST = DATA_PATH + "deals.txt";
    protected static final String SAVE = DATA_PATH + "save.txt";
    protected static final char SAVE_FILE_LINE_SEPARATOR = ',';

    protected static final int DEAL_SAVE_LINE_NUMBER = 0;

    protected static final int DEFAULT_DEAL_LIFETIME = 24;

    private static final String DATE_STRING_FORMAT = "dd.MM.yyyy HH:mm:ss";
    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_STRING_FORMAT);

    // Handle save file
    private Save save;

    // Deal instance. Handle current deal
    private Deal deal;

    public DealManager() {
        save = new Save();
        deal = new Deal(save);
    }

    public static void main(String[] args) throws FileNotFoundException {
        DealManager dealManager = new DealManager();
    }

    @Override
    public String toString() {
        return "DealManager{" +
                "save=" + save +
                ", dailyDeal=" + deal +
                '}';
    }
}
