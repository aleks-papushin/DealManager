import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by papushin on 29.06.2017.
 */
public class Deal {
    private String name = null;
    private Date creationDate;
    private int dealSaveLineNumber;
    private Save save;

    protected DialogManager dialog;

    protected Deal(Save save) {
        // Save file reference
        this.save = save;

        // Create DialogManager instance
        dialog = new DialogManager();

        // Creating save line number from constant
        setDealSaveLineNumber(DealManager.DEAL_SAVE_LINE_NUMBER);

        // Try to parse deal name from save file
        setName(save.parseDealName(this.getDealSaveLineNumber()));

        // If deal exists in the save file
        if (this.getName() != null) {
            this.setCreationDate(save.parseDealDate(this.getDealSaveLineNumber()));
            showCurrentDeal();
            processCurrentDeal();
        }

        // If was found out no deal name in save file
        else {
            mainProcessDialog();
        }
    }

    // TODO later implement deal lifetime tracking
    // Tracks if current deal is done and ask user to add another one
    private void processCurrentDeal() {
        dialog.processCurrentDeal();

        if (dialog.isUserSaidYes()) {
            deleteCurrentDeal();
            mainProcessDialog();
        }
        else {
            // TODO terminate program here
        }
    }

    private void deleteCurrentDeal() {
        setName(null);
        setCreationDate(null);
        save.removeSaveEntry(dealSaveLineNumber);
    }

    private void showCurrentDeal() {
        dialog.showCurrentDeal(this.getName(), this.creationDateToString());
    }

    public int getDealSaveLineNumber() {
        return dealSaveLineNumber;
    }

    public void setDealSaveLineNumber(int dealSaveLineNumber) {
        this.dealSaveLineNumber = dealSaveLineNumber;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String creationDateToString() {
        String dateString = DealManager.DATE_FORMAT.format(creationDate);
        return dateString;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected void createDeal(String deal) {
        setName(deal);
        createDealDate();
        save.addSaveLine(this.getName(), this.getCreationDate(), this.getDealSaveLineNumber());
    }

    private void createDealDate() {
        this.setCreationDate(new Date());
    }

    private void requestDealFromUser() {
        dialog.requestDealFromUser();
        Scanner scanner = new Scanner(System.in);
        String deal = scanner.nextLine();
        createDeal(deal);
    }

    private void mainProcessDialog() {
        dialog.showRequestOptions();
        int userChoose = listenAndValidateInput();

        if (userChoose == 1) {
            requestDealFromUser();
        }
        else if (userChoose == 2) {
            String randomDeal = saveRandomDeal();
            createDeal(randomDeal);
            showCurrentDeal();
        }
        else if (userChoose == 3) {
            // TODO implement function to program termination
        }
        else {
            mainProcessDialog();
        }
    }

    // TODO implement exception handling
    private int listenAndValidateInput() {
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        if (input > 0 && input < 4) {
            return input;
        }
        else {
            return 0;
        }
    }

    // TODO handle empty deals file and empty lines in file
    private String saveRandomDeal() {
        String filename = DealManager.DEAL_LIST;
        String result = "";
        int lines = 0;
        try {
            lines = countLines(filename);
            Random rand = new Random();
            result = Files.readAllLines(Paths.get(filename)).get(rand.nextInt(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private int countLines(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int counter = 0;
        while (scanner.hasNextLine()) {
            counter++;
            scanner.nextLine();
        }
        return counter;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "name='" + name + '\'' +
                ", creationDate=" + DealManager.DATE_FORMAT.format(creationDate) +
                '}';
    }
}
