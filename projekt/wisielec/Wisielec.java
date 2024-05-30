package projekt.wisielec;

import java.util.*;

public class Wisielec {
    private static final String[] EASY_WORDS = {"programista", "studia", "aplikacja", "komputer", "paczka"};
    private static final String[] MEDIUM_WORDS = {"repozytorium", "projekt zaliczeniowy", "konsola", "biblioteki"};
    private static final String[] HARD_WORDS = {"polimorfizm", "gałąź", "interfejs"};

    private static int wins = 0;
    private static int losses = 0;
    private static int totalAttempts = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=========================================");
            System.out.println("           Witaj w grze Wisielec!");
            System.out.println("=========================================");
            System.out.println("Wybierz poziom trudności: łatwy, średni, trudny");
            System.out.println("(Wpisz 'statystyki' aby zobaczyć statystyki, 'wyjście' aby zakończyć)");
            System.out.println("=========================================");
            String input = scanner.nextLine().toLowerCase().trim();

            if (input.equals("statystyki")) {
                displayStatistics();
                continue;
            } else if (input.equals("wyjście")) {
                break;
            }

            String wordToGuess = getWord(input);
            playGame(wordToGuess, scanner);
        }
        scanner.close();
    }

    private static String getWord(String difficulty) {
        Random random = new Random();
        switch (difficulty) {
            case "łatwy":
                return EASY_WORDS[random.nextInt(EASY_WORDS.length)];
            case "średni":
                return MEDIUM_WORDS[random.nextInt(MEDIUM_WORDS.length)];
            case "trudny":
                return HARD_WORDS[random.nextInt(HARD_WORDS.length)];
            default:
                throw new IllegalArgumentException("Nieznany poziom trudności: " + difficulty);
        }
    }

    private static void playGame(String wordToGuess, Scanner scanner) {
        int attempts = 0;
        int maxAttempts = 6;
        Set<Character> guessedLetters = new HashSet<>();
        Set<Character> wrongLetters = new HashSet<>();

        while (attempts < maxAttempts) {
            displayWord(wordToGuess, guessedLetters);
            displayHangman(attempts);
            System.out.println("Zgadnij literę:");
            String input = scanner.nextLine().toLowerCase().trim();
            if (input.length() != 1) {
                System.out.println("Proszę podać tylko jedną literę.");
                continue;
            }
            char guessedLetter = input.charAt(0);

            if (!Character.isLetter(guessedLetter)) {
                System.out.println("Proszę podać prawidłową literę.");
                continue;
            }

            if (guessedLetters.contains(guessedLetter) || wrongLetters.contains(guessedLetter)) {
                System.out.println("Ta litera była już zgadywana.");
                continue;
            }

            if (wordToGuess.indexOf(guessedLetter) >= 0) {
                guessedLetters.add(guessedLetter);
                if (isWordGuessed(wordToGuess, guessedLetters)) {
                    displayWord(wordToGuess, guessedLetters);
                    System.out.println("Gratulacje! Odgadłeś słowo: " + wordToGuess);
                    wins++;
                    totalAttempts += attempts + 1;
                    return;
                }
            } else {
                wrongLetters.add(guessedLetter);
                attempts++;
                System.out.println("Błędna litera. Pozostało prób: " + (maxAttempts - attempts));
            }
        }

        if (attempts == maxAttempts) {
            displayWord(wordToGuess, guessedLetters);
            displayHangman(attempts);
            System.out.println("Przegrałeś! Szukane słowo to: " + wordToGuess);
            losses++;
            totalAttempts += attempts;
        }
    }

    private static void displayWord(String wordToGuess, Set<Character> guessedLetters) {
        StringBuilder wordDisplay = new StringBuilder();
        for (char letter : wordToGuess.toCharArray()) {
            if (guessedLetters.contains(letter)) {
                wordDisplay.append(letter).append(" ");
            } else {
                wordDisplay.append("_ ");
            }
        }
        System.out.println(wordDisplay.toString());
    }

    private static boolean isWordGuessed(String wordToGuess, Set<Character> guessedLetters) {
        for (char letter : wordToGuess.toCharArray()) {
            if (!guessedLetters.contains(letter)) {
                return false;
            }
        }
        return true;
    }

    private static void displayHangman(int attempts) {
        String[] stages = {
            """
                  +---+
                  |   |
                      |
                      |
                      |
                      |
                =========
            """,
            """
                  +---+
                  |   |
                  O   |
                      |
                      |
                      |
                =========
            """,
            """
                  +---+
                  |   |
                  O   |
                  |   |
                      |
                      |
                =========
            """,
            """
                  +---+
                  |   |
                  O   |
                 /|   |
                      |
                      |
                =========
            """,
            """
                  +---+
                  |   |
                  O   |
                 /|\\  |
                      |
                      |
                =========
            """,
            """
                  +---+
                  |   |
                  O   |
                 /|\\  |
                 /    |
                      |
                =========
            """,
            """
                  +---+
                  |   |
                  O   |
                 /|\\  |
                 / \\  |
                      |
                =========
            """
        };
        System.out.println(stages[attempts]);
    }

    private static void displayStatistics() {
        System.out.println("=========================================");
        System.out.println("               Statystyki Gry");
        System.out.println("=========================================");
        System.out.println("Liczba zwycięstw: " + wins);
        System.out.println("Liczba porażek: " + losses);
        System.out.println("Średnia liczba prób na grę: " + (wins + losses == 0 ? 0 : (double) totalAttempts / (wins + losses)));
        System.out.println("=========================================");
    }
}