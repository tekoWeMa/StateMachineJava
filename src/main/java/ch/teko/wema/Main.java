package ch.teko.wema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//TODO: finishing implementing what is needed
//TODO: research if it is possible to have a general search for a word (eingabe in konsole, dann wird wort gesucht.)
public class Main {

    enum State {
        START, L, LO, LOR, LORD, G, GO, GOD, MATCHED_LORD, MATCHED_GOD, WHITESPACE
    }

    private static final String SPECIAL_CHARS = " \t\n.,;:!?()[]{}-–—\"'";

    public static void main(String[] args) {

        String fileName = "C:\\Dev\\PrefixTrie\\src\\main\\resources\\resources\\kjv.txt";

        int countLord = 0;
        int countGod = 0;
        State state = State.START;

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
                        if (isSpecialChar(ch) || isWhitespace(ch)) {
                            countGod++;
                        }
                        state = transitionFromWhitespace(ch);
                        break;
                    case MATCHED_LORD:
                        if (isSpecialChar(ch) || isWhitespace(ch)) {
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
    }

    private static boolean isWhitespace(char ch) {
        return Character.isWhitespace(ch);
    }

    private static boolean isSpecialChar(char ch) {
        return SPECIAL_CHARS.indexOf(ch) >= 0;
    }

    private static State transitionFromStart(char ch) {
        if (Character.isWhitespace(ch)) {
            return State.WHITESPACE;
        }
        if (ch == 'g') {
            return State.G;
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
        char[] specialChars = {' ', '.', ',', ';', ':', '!', '?', '-', '_', '/', '\\', '<', '>', '(', ')', '[', ']', '{', '}', '\"', '\'', '\n', '\r', '\t'};
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