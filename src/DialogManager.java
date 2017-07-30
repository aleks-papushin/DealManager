// Handle all dialogs with user
// Deal class itself makes callings of DialogManager methods

import java.util.Scanner;

public class DialogManager {
    private boolean userSaidYes = true;

    private String currentMessage = "";

    public boolean isUserSaidYes() {
        return userSaidYes;
    }

    public void setUserSaidYes(boolean userSaidYes) {
        this.userSaidYes = userSaidYes;
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(String currentMessage) {
        this.currentMessage = currentMessage;
    }

    public void showCurrentDeal(String dealName, String creationDate) {
        setCurrentMessage("Your current deal is: " + dealName + "\n" +
                "was created on " + creationDate);
        System.out.println(getCurrentMessage());
    }

    public void showRequestOptions() {
        setCurrentMessage(
            "1. I want to type a new deal." + "\n" +
            "2. I want to choose random deal from my list (if present).");
        System.out.println(getCurrentMessage());
    }

    public void processCurrentDeal() {
        setCurrentMessage("Do you want to mark current deal as done?");
        System.out.println(getCurrentMessage());
        askUserToMakeChoice();
    }

    private void askUserToMakeChoice() {
        System.out.println("Y/N");
        Scanner scanner = new Scanner(System.in);
        readAndValidateUserChoice(scanner.nextLine());
    }

    private void readAndValidateUserChoice(String choiceString) {
        String s = choiceString.toLowerCase().trim();

        if (s.equals("y") || s.equals("yes")) setUserSaidYes(true);
        else if (s.equals("n") || s.equals("no")) setUserSaidYes(false);
        else {
            System.out.println("Sorry, your input is incorrect, please type either \"y\" or \"n\" and press \"Enter\"");
            askUserToMakeChoice();
        }
    }

    public void requestDealFromUser() {
        setCurrentMessage("Type your deal, please.");
        System.out.println(getCurrentMessage());
    }
}
