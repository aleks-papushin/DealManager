import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by papushin on 29.06.2017.
 */
public class Save {
    private ArrayList<String> saveList;
    private Date lastSaveDate;

    private String saveFilePath = DealManager.SAVE;

    private char separator = DealManager.SAVE_FILE_LINE_SEPARATOR;

    public Save() {
        saveList = new ArrayList<>();
        readSaveFile();
//        lastSaveDate = this.parseSaveDate();
    }

    private void readSaveFile() {
        Scanner s = null;
        File saveFile = new File(saveFilePath);
        // TODO check if doublecheck ("if" and "try/catch") below is required
        if (saveFile.exists() && !saveFile.isDirectory()) {
            try {
                s = new Scanner(saveFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while (s != null && s.hasNextLine()){
                saveList.add(s.nextLine());
            }
            s.close();
        }
        else {
            this.createSaveFile();
        }
    }

    private void createSaveFile() {
        File saveFile = new File(DealManager.SAVE);
        try {
            saveFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    private Date parseSaveDate() {
        String dateString = null;

        try {
            Date date = DealManager.DATE_FORMAT.parse(dateString);
            return date;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    protected String parseDealName(int saveStringNumber) {
        if (this.saveStringExists(saveStringNumber)) {
            String sourceString = saveList.get(saveStringNumber);
            String dealName;
            dealName = sourceString.substring(0, sourceString.lastIndexOf(separator));
            return dealName;
        }
        else {
            return null;
        }
    }

    private boolean saveStringExists(int saveStringNumber) {
        return saveList.size() > saveStringNumber;
    }

    @Nullable
    protected Date parseDealDate(int saveStringNumber) {
        String dateString = null;
        try {
            dateString = saveList.get(saveStringNumber);
            dateString = dateString.substring(dateString.lastIndexOf(separator) + 1);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Save file line number you're looking for is " + saveStringNumber +
                    ", but number of lines is " + saveList.size() + " at all.");
            e.printStackTrace();
        }

        try {
            Date date = DealManager.DATE_FORMAT.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Save{" +
                "saveData=" + saveList +
                '}';
    }

    public void addSaveLine(String name, Date creationDate, int saveLineNumber) {
        String saveString = name + DealManager.SAVE_FILE_LINE_SEPARATOR + this.dateToString(creationDate);
        saveList.add(saveLineNumber, saveString);
        saveToFile();
    }

    private String dateToString(Date date) {
        String dateString = DealManager.DATE_FORMAT.format(date);
        return  dateString;
    }

    public void saveToFile() {
        try {
            FileWriter writer = new FileWriter(DealManager.SAVE);
            for (String line : saveList) {
                writer.write(line);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeSaveEntry(int dealSaveLineNumber) {
        saveList.remove(dealSaveLineNumber);
        saveToFile();
    }
}