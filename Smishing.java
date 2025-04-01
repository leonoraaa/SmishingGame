import java.util.ArrayList;  //List package 
import java.util.List;   //list package
import java.util.Random;   //random package
import java.util.Scanner;   //package allows for user input

class Message {   //define a real or smishing message   //Leonora
        private String text;   //message content is string   //Leonora
        private boolean Smishing;   //boolean value   //Leonora

    public Message (String text, boolean Smishing) {   //defining the Message instance in the messages list, consisting of string "text" and boolean value "Smishing"    //Leonora
        this.text = text;      //Leonora
        this.Smishing = Smishing;      //Leonora
    }

    public String getText() {   //Leonora
        return text;   //returns message   //Leonora
    }

    public boolean Smishing() {   //Leonora
        return Smishing;   //returns whether it is a smishing message   //Leonora
    }
}

public class Smishing {   
    public static List<Message> loadmessages() {   //allows messages to be loaded when called upon   //Leonora
    List<Message> messages = new ArrayList<>();   //list to contain the following messages   //Leonora
        messages.add(new Message("Your bank account has been compromised. Click the link to investigate http://.....", true));   //when smishing response is yes   //Leonora
        messages.add(new Message("Your electricity bill is due 30/4/2025. Please ensure payment is submitted by 29/4/2025 ", false));   //when smishing response is no   //Leonora
        messages.add(new Message("You've won a free iPhone. Click this link to claim http://.....", true));   //when smishing response is yes   //Leonora
        messages.add(new Message("Your package is out for delivery. Estimated arrival is 29/3/2025", false));   //when smishing response is no   //Leonora
        messages.add(new Message("Your paypal account has been locked. Click this link to verify http://....", true));   //when smishing response is yes   //Leonora
        messages.add(new Message("REMINDER: Your appointment is scheduled for tomorrow at Berwick Medical Clinic", false));   //when smishing response is no   //Leonora
    return messages;
    }

    public static void main(String[] args) {   //main that contains code for game execution    //Leonora
        Scanner scanner = new Scanner(System.in);  //create ability to read user input   //Leonora
        List<Message> messages = loadmessages();   //Messages list is accessed and loaded   //Leonora
        Random random = new Random();   //random object to randomly choose a message from messages list   //Leonora

        while (true) {   //continues loop   //Leonora
            Message message = messages.get(random.nextInt(messages.size()));   //random message chosen from its random index in the array list   //Leonora

            System.out.println("Message: " + message.getText());   //Message displayed to user   //Leonora
            System.out.print("Is this a smishing message or a real message? Yes or no. ");   //Leonora
            String userInput = scanner.next();   //read input from user   //Leonora
            
            boolean userresponse = userInput.equalsIgnoreCase("yes");   //yes response is recognised as a boolean value "true", case is ignored   //Leonora
            if (userresponse == message.Smishing()) {   //if the users input matches teh true smishing status of the message, then run the following line   //Leonora
                System.out.println("Correct");
            } else {   //if the users input does not match, run the following line   //Leonora
                System.out.println("Incorrect. Try again.");
            }

            System.out.print("Do you want to play again?");   //asking user to continue the questions or end the game   //Leonora
            if (!scanner.next().equalsIgnoreCase("yes")) {    //if answer is no, game is ended, if yes, the game stays on a loop   //Leonora
                System.out.println("Game ended.");
                break;   //exits loop, game ends   //Leonora
            }
        }
    }
}
