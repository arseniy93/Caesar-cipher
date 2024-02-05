package com.analyzer;



import com.exeption.AllExeptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CaesarCipher {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' ', '(', ')', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', '0', '/', '—', '-'};

    public StringBuilder statisticUncipherMethodSpace(String messageCiph) {
        StringBuilder unCipherMessage;
        Map<Character, Integer> checkFrequencies = new HashMap<>();
        for (char c : ALPHABET) {
            checkFrequencies.put(c, 0);
        }
        Map<Character, Integer> copyCheckFrequencies = new HashMap<>(checkFrequencies);
        char[] charsCiph = messageCiph.toCharArray();
        for (Map.Entry<Character, Integer> characterFloatEntry : copyCheckFrequencies.entrySet()) {
            int counter = 0;
            for (char c : charsCiph) {
                if (c == '\n' || c == '\r') {
                    continue;
                }
                if (characterFloatEntry.getKey() == c) {
                    counter++;
                }
                checkFrequencies.put(characterFloatEntry.getKey(), counter);
            }
        }
        int letterFrequencyMax = Collections.max(checkFrequencies.values(), (o1, o2) -> o1.compareTo(o2));
        char letterKey = 0;
        for (Map.Entry<Character, Integer> characterFloatEntry : checkFrequencies.entrySet()) {
            if (characterFloatEntry.getValue() == letterFrequencyMax) {
                letterKey = characterFloatEntry.getKey();
            }
        }
        int indexSpaceInOurAlphabet=getIndex(' ');
        int indexCipherSpace = getIndex(letterKey);
        int difference=indexSpaceInOurAlphabet- indexCipherSpace;
        unCipherMessage=toCipher(messageCiph,difference);


        return  unCipherMessage;
    }



    public StringBuilder toCipher(String message, int key) {
        key=checKey(key);
        if (key==0){
            try {
                throw new AllExeptions("You can't use key = 0 or key, which has size of your alphabet = " + ALPHABET.length);
            } catch (AllExeptions e) {
                throw new RuntimeException(e);
            }
        }

        char[] charsFromMessage = message.toLowerCase().toCharArray();
        StringBuilder messageCipher = new StringBuilder();
        for (int i = 0; i < charsFromMessage.length; i++) {
            if (charsFromMessage[i] == '\n') {
                messageCipher.append('\n');
            } else if (charsFromMessage[i] == '\r') {
                messageCipher.append('\r');
            } else if (checkLetter(charsFromMessage[i])) {

                try {
                    throw new AllExeptions("The letter/symbol: " + "'" + charsFromMessage[i] + "'" + ", number of this letter/symbol = "
                            + (i + 1) + " is not involved in ALPHABET array");
                } catch (AllExeptions e) {
                    throw new RuntimeException(e);
                }

            } else {
                messageCipher.append(ALPHABET[(getIndex(charsFromMessage[i]) + key) % ALPHABET.length]);
            }

        }
        return messageCipher;
    }

    public StringBuilder toUnCipher(String messageCipher, int key) {
        key=checKey(key);
        if (key==0){
            try {
                throw new AllExeptions("You can't use key = 0 or key = |n*"+ALPHABET.length+"/"+ALPHABET.length+"-n|, which has size of your alphabet = " + ALPHABET.length);
            } catch (AllExeptions e) {
                throw new RuntimeException(e);
            }
        }
        char[] charsFromMessage = messageCipher.toLowerCase().toCharArray();
        StringBuilder messageUnCipher = new StringBuilder();
        for (int i = 0; i < charsFromMessage.length; i++) {
            if (charsFromMessage[i] == '\n') {
                messageUnCipher.append('\n');
            } else if (charsFromMessage[i] == '\r') {
                messageUnCipher.append('\r');
            } else if (checkLetter(charsFromMessage[i])) {
                try {
                    throw new AllExeptions("The letter/symbol: " + "'" + charsFromMessage[i] + "'" + ", number of this letter/symbol = "
                            + (i + 1) + " is not involved in ALPHABET array");
                } catch (AllExeptions e) {
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
    public Map<Integer, StringBuilder> toUnCipherWithComma(String messageCipher) {
        char checkComma = ',';
        int counter = 0;
        int key = 0;
        for (int i = 1; i < ALPHABET.length - 1; i++) {
            char[] checkChars = toUnCipher(messageCipher, i).toString().toCharArray();
            int counter2 = 0;
            for (int i1 = 0; i1 < checkChars.length - 3; i1++) {
                boolean a = ((checkChars[i1] != ' ') && (checkChars[i1] != checkComma) && (checkChars[i1] != '.') && (checkChars[i1] != '!')
                        && (checkChars[i1] != '?') && (checkChars[i1] != ':') && (checkChars[i1] != ';') && (checkChars[i1] != '-')
                        && (checkChars[i1] != '—'));
                boolean b = (checkChars[i1 + 1] == checkComma);
                boolean c = (checkChars[i1 + 2] == ' ');
                if (a && b && c) {
                    counter2++;
                }
            }
            if (counter < counter2) {
                counter = counter2;
                key = i;
            }
        }
        Map<Integer, StringBuilder> unCiphMap = new HashMap<>();
        unCiphMap.put(key, toUnCipher(messageCipher, key));

        return unCiphMap;
    }

    public int getKeyFromCipherText(String text, String fileWithUnChiperText) {
        if (text.length() != fileWithUnChiperText.length()) {
            int quantityOfLetters = Math.max(text.length(), fileWithUnChiperText.length());
            String number = text.length() > fileWithUnChiperText.length() ? "first text" : "second text";
            try {
                throw new AllExeptions("The quantity of letters aren't coincidenced: " + number + " = "
                        + quantityOfLetters + " more than " + (Math.abs(text.length() - fileWithUnChiperText.length())));
            } catch (AllExeptions e) {
                throw new RuntimeException(e);
            }
        }
        int key = 0;
        StringBuilder textToUnCipher;
        for (int i = 1; i < ALPHABET.length; i++) {
            textToUnCipher = toUnCipher(fileWithUnChiperText, i);
            if (textToUnCipher.toString().equalsIgnoreCase(text)) {
                key = i;
                break;
            }
        }
        return key;
    }


    private int getIndex(char letter) {
        int indexLetter = 0;
        for (int i = 0; i < ALPHABET.length; i++) {
            if (letter == ALPHABET[i]) {
                indexLetter = i;
            }
        }
        return indexLetter;
    }

    private boolean checkLetter(char letter) {
        boolean law = false;
        for (char c : ALPHABET) {
            if (letter == c) {
                law = true;
                break;
            }
        }
        return !law;
    }
    private int checKey(int key){
        if(key==0 || key==ALPHABET.length ||key==-ALPHABET.length){
            return 0;
        }
        else if (key>0 ){
            if(key>ALPHABET.length){
                key%=ALPHABET.length;
            }
        } else {
            if((-key)>ALPHABET.length){
                key%=ALPHABET.length;
                if (key==0){
                    return 0;
                }
                key+=ALPHABET.length;

            } else if ((-key)<ALPHABET.length) {
                key+=ALPHABET.length;

            }

        }
        return  key;
    }
}
