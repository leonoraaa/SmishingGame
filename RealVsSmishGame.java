import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;   //leonora
import java.util.ArrayList;   //leonora
import java.util.Collections;   //leonora


//leonora start
class Message {
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
//leonora end

//mohit start
public class RealVsSmishGame {
    private JFrame frame;
    private JTextArea message1, message2;
    private JLabel feedbackLabel;
    private JLabel progressLabel;   //leonora
    private JProgressBar progressBar;   //leonora
    private JButton nextButton;
    private int current = 0;
    private int score = 0;   //leonora
    private List<Message[]> questionPairs;   //leonora

//mohit start
    public RealVsSmishGame() {
        loadMessagePairs();   //leonora

        frame = new JFrame("Game 1: Real vs Smish");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Select the Smishing Message", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        message1 = createMessageBox(0);
        message2 = createMessageBox(1);
        panel.add(new JScrollPane(message1));
        panel.add(new JScrollPane(message2));
        frame.add(panel, BorderLayout.CENTER);

        //leonora start
        JProgressBar progressBar = new JProgressBar(0, questionPairs.size());
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

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(feedbackLabel, BorderLayout.CENTER);
        bottom.add(progressLabel);   //leonora
        bottom.add(restartButton);
        bottom.add(nextButton, BorderLayout.SOUTH);
        frame.add(bottom, BorderLayout.SOUTH);

        loadQuestion();
        frame.setVisible(true);
    }
//mohit end

//leonora start
private void restartGame() {
    current = 0;
    score = 0;
    Collections.shuffle(questionPairs);
    loadQuestion();
    feedbackLabel.setText("Click the smishing message");
    nextButton.setEnabled(false);
    progressLabel.setText("");
}
//leonora end

//mohit start
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
//mohit end

//leonora start
    private void loadMessagePairs() {
        List<Message> messages = new ArrayList<>();   //list to contain the following messages   //Leonora
        messages.add(new Message("Your bank account has been compromised. Click the link to investigate http://.....", true));
        messages.add(new Message("Your electricity bill is due 30/4/2025. Please ensure payment is submitted by 29/4/2025 ", false));
        messages.add(new Message("You've won a free iPhone. Click this link to claim http://.....", true));
        messages.add(new Message("Your package is out for delivery. Estimated arrival is 29/3/2025", false));
        messages.add(new Message("Your paypal account has been locked. Click this link to verify http://....", true));
        messages.add(new Message("REMINDER: Your appointment is scheduled for tomorrow at Berwick Medical Clinic", false));
        Collections.shuffle(messages);

        questionPairs = new ArrayList<>();
        for (int i = 0; i < messages.size() - 1; i += 2) {
            questionPairs.add(new Message[] {messages.get(i), messages.get(i + 1)});
        }
    }
    //leonora end
    
//mohit start
    private void loadQuestion() {
        message1.setBackground(Color.WHITE);
        message2.setBackground(Color.WHITE);
        feedbackLabel.setText("Click the smishing message");

        Message[] pair = questionPairs.get(current);   //leonora
        message1.setText(pair[0].getText());   //shared
        message2.setText(pair[1].getText());   //shared
    }
//mohit end

//mohit start
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

//mohit start
    private void nextQuestion() {
        current++;
//leonora start
        progressBar.setValue(current);

        if(current >= questionPairs.size()) {
            JOptionPane.showMessageDialog(frame,
                "Game Over! Your score: " + score + " / " + questionPairs.size(),
                "Finished", JOptionPane.INFORMATION_MESSAGE);
            nextButton.setEnabled(false);
            return;
        }

        loadQuestion();

        nextButton.setEnabled(false);
        feedbackLabel.setText("Click the smishing message");
        }
//leonora end

//mohit end

//mohit start
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RealVsSmishGame::new);
    }
}
//mohit end