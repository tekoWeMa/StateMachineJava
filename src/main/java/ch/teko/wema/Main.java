package ch.teko.wema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

//TODO: finishing implementing what is needed
//TODO: research if it is possible to have a general search for a word (eingabe in konsole, dann wird wort gesucht.)
public class Main {

    enum State {
        START, L, LO, LOR, LORD, G, GO, GOD, MATCHED_LORD, MATCHED_GOD, WHITESPACE
    }

    public static void main(String[] args) {

        String fileName = "C:\\Dev\\StateMachineJava\\src\\main\\resources\\kjv.txt";

        int countLord = 0;
        int countGod = 0;
        State state = State.START;

        var counter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int c;
            while ((c = br.read()) != -1) {
                char ch = (char) c;
                ch = Character.toLowerCase(ch);

                switch (state) {
                    case START:
                        state = transitionFromStart(ch);
                        break;
                    case WHITESPACE:
                        state = transitionFromWhitespace(ch);
                        break;
                    case L:
                        state = transitionFromL(ch);
                        break;
                    case LO:
                        state = transitionFromLO(ch);
                        break;
                    case LOR:
                        state = transitionFromLOR(ch);
                        break;
                    case LORD:
                        state = transitionFromLord(ch);
                        break;
                    case G:
                        state = transitionFromG(ch);
                        break;
                    case GO:
                        state = transitionFromGO(ch);
                        break;
                    case GOD:
                        state = transitionFromGod(ch);
                        break;
                    case MATCHED_GOD:

                            countGod++;

                        state = transitionFromWhitespace(ch);
                        break;
                    case MATCHED_LORD:
                        if (isSpecialCharacter(ch) || isWhitespace(ch)) {
                            countLord++;
                        }
                        state = transitionFromWhitespace(ch);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("The word 'lord' occurs " + countLord + " times.");
        System.out.println("The word 'god' occurs " + countGod + " times.");

        System.out.println("The word 'god' occurs " + counter + " times.");
    }

    private static boolean isWhitespace(char ch) {
        return Character.isWhitespace(ch);
    }

    private static State transitionFromStart(char ch) {
        if (Character.isWhitespace(ch)) {
            return State.WHITESPACE;
        }
        return State.START;
    }

    private static State transitionFromWhitespace(char ch) {
        if (ch == 'l') {
            return State.L;
        }
        if (ch == 'g') {
            return State.G;
        }
        return State.START;
    }

    private static State transitionFromL(char ch) {
        if (ch == 'o') {
            return State.LO;
        }
        return State.START;
    }

    private static State transitionFromLO(char ch) {
        if (ch == 'r') {
            return State.LOR;
        }
        return State.START;
    }

    private static State transitionFromLOR(char ch) {
        if (ch == 'd') {
            return State.MATCHED_LORD;
        }
        return State.START;
    }

    private static State transitionFromG(char ch) {
        if (ch == 'o') {
            return State.GO;
        }
        return State.START;
    }

    private static State transitionFromGO(char ch) {
        if (ch == 'd') {
            return State.GOD;
        }
        return State.START;
    }

    private static State transitionFromGod(char ch) {
        if (Character.isWhitespace(ch) || isSpecialCharacter(ch)) {
            return State.MATCHED_GOD;
        }
        return State.START;
    }

    private static boolean isSpecialCharacter(char ch) {
        // define special characters that should be considered as end of a word
        //TODO research if there is a function that states of there is no buchstabe
        char[] specialChars = {'\'', ' ', '.', ',', ';', ':', '!', '?', '-', '_', '/', '\\', '<', '>', '(', ')', '[', ']', '{', '}', '\"', '\'', '\n', '\r', '\t'};
        for (char c : specialChars) {
            if (ch == c) {
                return true;
            }
        }
        return false;
    }

    private static State transitionFromLord(char ch) {
        if (Character.isWhitespace(ch) || isSpecialCharacter(ch)) {
            return State.MATCHED_LORD;
        }
        return State.START;
    }
}