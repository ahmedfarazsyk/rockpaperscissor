import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RockPaperScissorGUI {
    private static int maxRounds;
    private static int round = 0;
    private static int won = 0;
    private static int lost = 0;
    private static int draw = 0;
    private static JLabel resultLabel;
    private static JLabel countLabel;
    private static JTextArea historyTextArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rock Paper Scissors Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(22, 160, 133)); // Background color

        JPanel gameInfoPanel = new JPanel();
        gameInfoPanel.setLayout(new BorderLayout());

        JPanel topInfoPanel = new JPanel();
        topInfoPanel.setLayout(new BorderLayout());

        resultLabel = new JLabel("To select the difficulty level, click on Start.");
        resultLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        topInfoPanel.add(resultLabel, BorderLayout.CENTER);
        countLabel = new JLabel("");
        countLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        topInfoPanel.add(countLabel, BorderLayout.EAST);

        gameInfoPanel.add(topInfoPanel, BorderLayout.NORTH);

        historyTextArea = new JTextArea(10, 30);
        historyTextArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        historyTextArea.setEditable(false);
        historyTextArea.setForeground(new Color(22, 160, 133)); // Text color
        historyTextArea.setBackground(new Color(255, 255, 255)); // Text background color
        JScrollPane scrollPane = new JScrollPane(historyTextArea);
        gameInfoPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(1, 3));

        JButton rockButton = createGameButton("Rock", new Color(231, 76, 60));
        JButton paperButton = createGameButton("Paper", new Color(41, 128, 185));
        JButton scissorButton = createGameButton("Scissors", new Color(46, 204, 113));
        JButton startButton = createGameButton("Start", new Color(243, 156, 18));
        JButton restartButton = createGameButton("Restart", new Color(108, 122, 137));

        gamePanel.add(rockButton);
        gamePanel.add(paperButton);
        gamePanel.add(scissorButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);

        frame.add(gameInfoPanel, BorderLayout.NORTH);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        rockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playRound(1, "Rock");
            }
        });

        paperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playRound(2, "Paper");
            }
        });

        scissorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playRound(3, "Scissors");
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseDifficulty();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                round = 0;
                won = 0;
                lost = 0;
                draw = 0;
                countLabel.setText("");
                resultLabel.setText("To select the difficulty level, click on Start.");
                historyTextArea.setText("");
            }
        });

        frame.setVisible(true);
    }

    private static JButton createGameButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        return button;
    }

    private static void chooseDifficulty() {
        String[] options = {"Easy (3 Rounds)", "Medium (5 Rounds)", "Hard (7 Rounds)"};
        int choice = JOptionPane.showOptionDialog(null, "Select a Difficulty Level",
                "Difficulty Level", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                maxRounds = 3;
                break;
            case 1:
                maxRounds = 5;
                break;
            case 2:
                maxRounds = 7;
                break;
            default:
                maxRounds = 3; // Default to Easy
                break;
        }

        round = 0;
        won = 0;
        lost = 0;
        draw = 0;
        countLabel.setText("User: 0  Computer: 0  Rounds: " + round + "/" + maxRounds);
        resultLabel.setText("Game On! Make your choice. Rounds: " + maxRounds);
        historyTextArea.setText("");
    }

    private static void playRound(int you, String youChoice) {
        if (round < maxRounds) {
            Random rd = new Random();
            int computer = rd.nextInt(1, 4);
            String computerChoice = getChoiceName(computer);

            appendToHistory("You chose " + youChoice + ", Computer chose " + computerChoice);

            if (you == 1 && computer == 3 || you == 2 && computer == 1 || you == 3 && computer == 2) {
                appendToHistory("You Win this round!");
                won = won + 1;
            } else if (you == computer) {
                appendToHistory("It's a Draw this round.");
                draw = draw + 1;
            } else {
                appendToHistory("Computer Wins this round.");
                lost = lost + 1;
            }
            round = round + 1;

            if (round == maxRounds) {
                displayResults();
            } else {
                countLabel.setText("User: " + won + "  Computer: " + lost + "  Rounds: " + round + "/" + maxRounds);
            }
        }
    }

    private static void displayResults() {
        String resultText = "Game Over! Total Rounds: " + maxRounds + "\n";
        if (won > lost) {
            resultText += "Congratulations! You win the game!";
        } else if (won < lost) {
            resultText += "Computer wins the game. Better luck next time!";
        } else {
            resultText += "The game is a draw. Play again!";
        }
        countLabel.setText("");
        resultLabel.setText(resultText);
    }

    private static void appendToHistory(String message) {
        historyTextArea.append(message + "\n");
    }

    private static String getChoiceName(int choice) {
        switch (choice) {
            case 1:
                return "Rock";
            case 2:
                return "Paper";
            case 3:
                return "Scissors";
            default:
                return "";
        }
    }
}
