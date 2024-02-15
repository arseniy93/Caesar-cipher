package com.analyzer;


import static com.constants.ConsoleMenuConsts.SPACE;
import static com.constants.ConsoleMenuConsts.ZIRO;
import static com.constants.ConsoleMenuConsts.TAB_N;
import static com.constants.ConsoleMenuConsts.TAB_R;

import com.exeption.EncoderExeption;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CaesarCipher {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' ', '(', ')', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', '0', '/', '—', '-'};

    public StringBuilder statisticDeCryptWithSpace(String messageCiph) throws EncoderExeption {
        StringBuilder unCipherMessage;
        Map<Character, Integer> checkFrequencies = new HashMap<>();
        for (char chars : ALPHABET) {
            checkFrequencies.put(chars, ZIRO);
        }
        Map<Character, Integer> copyCheckFrequencies = new HashMap<>(checkFrequencies);
        char[] charsCiph = messageCiph.toCharArray();
        for (Map.Entry<Character, Integer> characterFloatEntry : copyCheckFrequencies.entrySet()) {
            int counter = ZIRO;
            for (char c : charsCiph) {
                if (c == TAB_N || c == TAB_R) {
                    continue;
                }
                if (characterFloatEntry.getKey() == c) {
                    counter++;
                }
                checkFrequencies.put(characterFloatEntry.getKey(), counter);
            }
        }
        int letterFrequencyMax = Collections.max(checkFrequencies.values(), (o1, o2) -> o1.compareTo(o2));
        char letterKey = ZIRO;
        for (Map.Entry<Character, Integer> characterFloatEntry : checkFrequencies.entrySet()) {
            if (characterFloatEntry.getValue() == letterFrequencyMax) {
                letterKey = characterFloatEntry.getKey();
            }
        }
        int indexSpaceInOurAlphabet = getIndex(SPACE);
        int indexCipherSpace = getIndex(letterKey);
        int difference = indexSpaceInOurAlphabet - indexCipherSpace;
        unCipherMessage = toEncrypt(messageCiph, difference);


        return unCipherMessage;
    }


    public StringBuilder toEncrypt(String message, int key) throws EncoderExeption {
        key = checKey(key);
        if (key == ZIRO) {
                throw new EncoderExeption("You can't use key = 0 or key, which has size of your alphabet = " + ALPHABET.length);
        }

        char[] charsFromMessage = message.toLowerCase().toCharArray();
        StringBuilder messageCipher = new StringBuilder();
        for (int i = ZIRO; i < charsFromMessage.length; i++) {
            if (charsFromMessage[i] == TAB_N) {
                messageCipher.append(TAB_N);
            } else if (charsFromMessage[i] == TAB_R) {
                messageCipher.append(TAB_R);
            } else if (checkLetter(charsFromMessage[i])) {

                try {
                    throw new EncoderExeption("The letter/symbol: " + "'" + charsFromMessage[i] + "'" + ", number of this letter/symbol = "
                            + (i + 1) + " is not involved in ALPHABET array");
                } catch (EncoderExeption e) {
                    throw new RuntimeException(e);
                }

            } else {
                messageCipher.append(ALPHABET[(getIndex(charsFromMessage[i]) + key) % ALPHABET.length]);
            }

        }
        return messageCipher;
    }

    public StringBuilder toDeCrypt(String messageCipher, int key) {
        key = checKey(key);
        if (key == ZIRO) {
            try {
                throw new EncoderExeption("You can't use key = 0 or key = |n*" + ALPHABET.length + "/" + ALPHABET.length + "-n|, which has size of your alphabet = " + ALPHABET.length);
            } catch (EncoderExeption e) {
                throw new RuntimeException(e);
            }
        }
        char[] charsFromMessage = messageCipher.toLowerCase().toCharArray();
        StringBuilder messageUnCipher = new StringBuilder();
        for (int i = ZIRO; i < charsFromMessage.length; i++) {
            if (charsFromMessage[i] == TAB_N) {
                messageUnCipher.append(TAB_N);
            } else if (charsFromMessage[i] == TAB_R) {
                messageUnCipher.append(TAB_R);
            } else if (checkLetter(charsFromMessage[i])) {
                try {
                    throw new EncoderExeption("The letter/symbol: " + "'" + charsFromMessage[i] + "'" + ", number of this letter/symbol = "
                            + (i + 1) + " is not involved in ALPHABET array");
                } catch (EncoderExeption e) {
                    throw new RuntimeException(e);
                }
            } else {
                if ((key > getIndex(charsFromMessage[i]))) {

                    messageUnCipher.append(ALPHABET[(ALPHABET.length - key + getIndex(charsFromMessage[i]))]);

                } else {

                    messageUnCipher.append((ALPHABET[(Math.abs(getIndex(charsFromMessage[i]) - key) + ALPHABET.length) % (ALPHABET.length)]));
                }

            }
        }
        return messageUnCipher;
    }

    public Map<Integer, StringBuilder> toDeCryptWithComma(String messageCipher) {
        char checkComma = ',';
        int counter = ZIRO;
        int key = ZIRO;
        for (int i = 1; i < ALPHABET.length - 1; i++) {
            char[] checkChars = toDeCrypt(messageCipher, i).toString().toCharArray();
            int counter2 = ZIRO;
            counter2 = conditionOfComma(checkChars, checkComma, counter2);
            if (counter < counter2) {
                counter = counter2;
                key = i;
            }
        }
        Map<Integer, StringBuilder> unCiphMap = new HashMap<>();
        unCiphMap.put(key, toDeCrypt(messageCipher, key));

        return unCiphMap;
    }


    public int getKeyFromEncryptText(String text, String fileWithUnChiperText) {
        if (text.length() != fileWithUnChiperText.length()) {
            int quantityOfLetters = Math.max(text.length(), fileWithUnChiperText.length());
            String number = text.length() > fileWithUnChiperText.length() ? "first text" : "second text";
            try {
                throw new EncoderExeption("The quantity of letters aren't coincidenced: " + number + " = "
                        + quantityOfLetters + " more than " + (Math.abs(text.length() - fileWithUnChiperText.length())));
            } catch (EncoderExeption e) {
                throw new RuntimeException(e);
            }
        }
        int key = ZIRO;
        StringBuilder textToUnCipher;
        for (int i = 1; i < ALPHABET.length; i++) {
            textToUnCipher = toDeCrypt(fileWithUnChiperText, i);
            if (textToUnCipher.toString().equalsIgnoreCase(text)) {
                key = i;
                break;
            }
        }
        return key;
    }


    private int getIndex(char letter) {
        int indexLetter = ZIRO;
        for (int i = ZIRO; i < ALPHABET.length; i++) {
            if (letter == ALPHABET[i]) {
                indexLetter = i;
            }
        }
        return indexLetter;
    }

    private boolean checkLetter(char letter) {
        boolean law = false;
        for (char chars : ALPHABET) {
            if (letter == chars) {
                law = true;
                break;
            }
        }
        return !law;
    }

    private int checKey(int key) {
        if (key == ZIRO || key == ALPHABET.length || key == -ALPHABET.length) {
            return ZIRO;
        } else if (key > ZIRO) {
            if (key > ALPHABET.length) {
                key %= ALPHABET.length;
            }
        } else {
            if ((-key) > ALPHABET.length) {
                key %= ALPHABET.length;
                if (key == ZIRO) {
                    return ZIRO;
                }
                key += ALPHABET.length;

            } else if ((-key) < ALPHABET.length) {
                key += ALPHABET.length;

            }

        }
        return key;
    }

    private static int conditionOfComma(char[] checkChars, char checkComma, int counter2) {
        for (int i1 = ZIRO; i1 < checkChars.length - 3; i1++) {
            boolean a = ((checkChars[i1] != SPACE) && (checkChars[i1] != checkComma) && (checkChars[i1] != '.') && (checkChars[i1] != '!')
                    && (checkChars[i1] != '?') && (checkChars[i1] != ':') && (checkChars[i1] != ';') && (checkChars[i1] != '-')
                    && (checkChars[i1] != '—'));
            boolean b = (checkChars[i1 + 1] == checkComma);
            boolean c = (checkChars[i1 + 2] == SPACE);
            if (a && b && c) {
                counter2++;
            }
        }
        return counter2;
    }
}
