import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

//leonora
class Message {    //structure of smishing message
    private String text;
    private boolean Smishing;

    public Message(String text, boolean Smishing) {
        this.text = text;
        this.Smishing = Smishing;
    }

    public String getText() {
        return text;
    }

    public boolean isSmishing() {
        return Smishing;
    }
}

//mohit
public class RealVsSmishGame {
    private JFrame frame;
    private JTextArea message1, message2;
    private JLabel feedbackLabel;
    private JLabel progressLabel;   //leonora
    private JProgressBar progressBar;   //leonora
    private JButton nextButton;
    private int current = 0;
    private int score = 0;   //leonora
    private List<Message> realMessages;   //leonora
    private List<Message> fakeMessages;   //leonora
    private List<Message[]> questionPairs;   //leonora

    //mohit
    public RealVsSmishGame() {
        loadRealMessage();
        loadFakeMessage();
        questionPairs = new ArrayList<>();
        loadMessagePairs();   //leonora

        frame = new JFrame("Game 1: Real vs Smishing Game");    //title of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);   //window size
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Please select the Smishing Message from the below options:", SwingConstants.CENTER);      //Center text in the middle
        title.setFont(new Font("Arial", Font.BOLD, 22));    //attributes
        frame.add(title, BorderLayout.NORTH);    //add the title and place it north of the window

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));    //set up format for the messages
        message1 = createMessageBox(0);
        message2 = createMessageBox(1);
        panel.add(new JScrollPane(message1));
        panel.add(new JScrollPane(message2));
        frame.add(panel, BorderLayout.CENTER);

        //leonora start
        progressBar = new JProgressBar(0, questionPairs.size());
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setPreferredSize(new Dimension(500, 20));
        frame.add(progressBar, BorderLayout.NORTH);
        //leonora end

        feedbackLabel = new JLabel("Click the smishing message", SwingConstants.CENTER);

        progressLabel = new JLabel("", SwingConstants.CENTER);   //leonora
        progressLabel.setFont(new Font("Arial", Font.PLAIN, 12));   //leonora

        nextButton = new JButton("Next");
        nextButton.addActionListener(e -> nextQuestion());
        nextButton.setEnabled(false);   //leonora
        
        JButton restartButton;
        restartButton = new JButton("Restart");   //leonora
        restartButton.addActionListener(e -> restartGame());   //leonora

        JPanel top = new JPanel(new BorderLayout());
        top.add(feedbackLabel);
        frame.add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(progressLabel);   //leonora
        bottom.add(restartButton);
        bottom.add(nextButton);
        frame.add(bottom, BorderLayout.SOUTH);

        loadQuestion();
        frame.setVisible(true);
    }

    //leonora
    private void restartGame() {
        current = 0;
        score = 0;
        Collections.shuffle(questionPairs);

        loadQuestion();
        
        feedbackLabel.setText("Click the smishing message");
        nextButton.setEnabled(false);
        progressBar.setValue(0);
        progressLabel.setText("");
}

    //mohit
    private JTextArea createMessageBox(int index) {
        JTextArea area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setFont(new Font("Arial", Font.PLAIN, 14));
        area.setBackground(Color.WHITE);
        area.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        area.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                checkAnswer(index);
            }
        });
        return area;
    }

        //leonora
    private void loadRealMessage() {
        realMessages = new ArrayList<>();

        realMessages.add(new Message("Your electricity bill is due 30/4/2025. Please ensure payment is submitted by 29/4/2025 ", false));
        realMessages.add(new Message("Your package is out for delivery. Estimated arrival is 29/3/2025", false));
        realMessages.add(new Message("REMINDER: Your appointment is scheduled for tomorrow at Berwick Medical Clinic", false));
    }


    //leonora
    private void loadFakeMessage() {
        fakeMessages = new ArrayList<> ();

        fakeMessages.add(new Message("Your bank account has been compromised. Click the link to investigate http://bit.ly-21929749.com", true));
        fakeMessages.add(new Message("You've won a free iPhone. Click this link to claim http://free-iphone.com", true));
        fakeMessages.add(new Message("Your paypal account has been locked. To unlock your account, enter your credit card details at http://paypa1.com.au", true));

    }

    private void loadMessagePairs() {
        for (int i = 0; i < realMessages.size(); i++) {
            Message realMessage = realMessages.get(i);
            Message fakeMessage = fakeMessages.get(i);

            if (i % 2 == 0) {   //if element's index is even do following
                questionPairs.add(new Message[] {realMessage, fakeMessage});   //real message on right fake on left
            }
            else {   //if element's index is odd do following
                questionPairs.add(new Message[] {fakeMessage, realMessage});   //real message on left fake on right
            }
        }
    }
    
    //mohit
    private void loadQuestion() {
        message1.setBackground(Color.WHITE);
        message2.setBackground(Color.WHITE);

        Message[] pair = questionPairs.get(current);   //leonora
        message1.setText(pair[0].getText());   //shared
        message2.setText(pair[1].getText());   //shared

        feedbackLabel.setText("Click the smishing message");
    }

    //mohit
    private void checkAnswer(int selected) {
        Message[] pair = questionPairs.get(current);   //leonora
        boolean correct = pair[selected].isSmishing();   //leonora

        if (correct) {
            feedbackLabel.setText("Correct! That's the smishing message.");
            (selected == 0 ? message1 : message2).setBackground(new Color(200, 255, 200));
            score++;
        } else {
            feedbackLabel.setText("Nope! That's a legit message.");
            (selected == 0 ? message1 : message2).setBackground(new Color(255, 200, 200));
        }

        nextButton.setEnabled(true);   //leonora
    }

    //leonora
    private void endGame() {
        if (current >= questionPairs.size()){
            int answer = JOptionPane.showConfirmDialog(frame, "Game Over! Your score: " + score + " / " + questionPairs.size() + "Play again?", "Game Over",  JOptionPane.YES_NO_OPTION);
    
            if (answer == JOptionPane.YES_OPTION) {
                restartGame();   //reset progress
            } else {
                System.exit(0);
            }
            return;
        }
    }

    //mohit
    private void nextQuestion() {
        current++;
        progressBar.setValue(current);

        endGame();  //leonora, run method and check progress
        loadQuestion();

        nextButton.setEnabled(false);
        feedbackLabel.setText("Click the smishing message");
        }

    //mohit
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RealVsSmishGame::new);
    }
}
