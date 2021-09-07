import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {
    private User user;
    private Computer computer;
    private int userScore;
    private int computerScore;
    private int numberOfGames;
    public static void main(String[] args) {
        RockPaperScissors game = new RockPaperScissors();
        game.startGame();
    }

    public void generateHMAC() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[32];
        random.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        System.out.println("HAMC:\n" + sb.toString());
    }

    private enum Move {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK, HELP;
        public int compareMoves(Move otherMove) {
            // Ничья
            if (this == otherMove)
                return 0;

            switch (this) {
                case ROCK:
                    return (otherMove == SCISSORS || otherMove == LIZARD ? 1 : -1);
                case PAPER:
                    return (otherMove == ROCK || otherMove == SPOCK ? 1 : -1);
                case SCISSORS:
                    return (otherMove == PAPER || otherMove == LIZARD ? 1 : -1);
                case LIZARD:
                    return (otherMove == PAPER || otherMove == SPOCK ? 1 : -1);
                case SPOCK:
                    return (otherMove == ROCK || otherMove == SCISSORS ? 1 : -1);

            }
            return 0;
        }
    }

    private class User {
        private Scanner inputScanner;

        public User() {
            inputScanner = new Scanner(System.in);
        }

        public Move getMove() {
            System.out.print("Choice ");

            String userInput = inputScanner.nextLine();
            userInput = userInput.toUpperCase();
            char firstLetter = userInput.charAt(0);
            if (firstLetter == '1' || firstLetter == '2' || firstLetter == '3' || firstLetter == '4' || firstLetter == '5'|| firstLetter == '0'|| firstLetter == '?') {
                // Ввод корректный
                switch (firstLetter) {
                    case '1':
                        return Move.ROCK;
                    case '2':
                        return Move.PAPER;
                    case '3':
                        return Move.SCISSORS;
                    case '4':
                        return Move.LIZARD;
                    case '5':
                        return Move.SPOCK;
                    case '0':
                        System.exit(0);
                    case '?':
                        printGameStats();
                }
            } else {
                startGame();
            }

            return getMove();
        }

        public boolean playAgain() {
            System.out.print("Do you want play again? (Y/N)");
            String userInput = inputScanner.nextLine();
            userInput = userInput.toUpperCase();
            if (userInput.charAt(0) == 'Y') {
                System.out.println("");
                return userInput.charAt(0) == 'Y';
            } else if (userInput.charAt(0) == 'N'){
                System.exit(0);
            }
                return playAgain();
        }
    }

    private class Computer {
        public Move getMove() {
            Move[] moves = Move.values();
            Random random = new Random();
            int index = random.nextInt(moves.length);
            return moves[index];
        }
    }

    public RockPaperScissors() {
        user = new User();
        computer = new Computer();
        userScore = 0;
        computerScore = 0;
        numberOfGames = 0;
    }

    public void startGame() {
        generateHMAC();
        System.out.println("Available moves:\n" +
                "1 - rock\n" +
                "2 - paper\n" +
                "3 - scissors\n" +
                "4 - lizard\n" +
                "5 - Spock\n" +
                "0 - exit\n" +
                "? - help");
        Move userMove = user.getMove();
        Move computerMove = computer.getMove();
        System.out.println("\nYou move " + userMove + ".");
        System.out.println("Computer move " + computerMove + ".\n");

        // Сравнение ходов и определение победителя
        int compareMoves = userMove.compareMoves(computerMove);
        switch (compareMoves) {
            case 0:
                System.out.println("Draw!");
                break;
            case 1:
                System.out.println(userMove + " beats " + computerMove + ". You win!");
                userScore++;
                break;
            case -1:
                System.out.println(computerMove + " beats " + userMove + ". You lose...");
                computerScore++;
                break;

        }
        generateHMAC();

        numberOfGames++;

        if (user.playAgain()) {
            System.out.println();
            startGame();
        } else {
            printGameStats();
        }
    }

    private void printGameStats() {
        System.out.format(" \\USER   |      |       |          |         |       \n");
        System.out.format("PC\\      | rock | paper | scissors | lizzard | spock \n");
        System.out.format("---------+------+-------+----------+---------+-------\n");
        System.out.format("rock     | draw |  win  |   lose   |   win   | lose  \n");
        System.out.format("paper    | lose |  draw |   win    |   lose  | win   \n");
        System.out.format("scissors | win  |  lose |   draw   |   win   | lose  \n");
        System.out.format("lizzard  | lose |  win  |   lose   |   draw  | win   \n");
        System.out.format("spock    | win  |  lose |   win    |   lose  | draw  \n");
    }

}