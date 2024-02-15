package com.services;

import com.analyzer.CaesarCipher;
import com.exeption.EncoderExeption;
import com.exeption.FileExeption;
import com.files.WorkWithFile;
import com.main.enums.Menu;

import java.util.Scanner;

import static com.constants.ConsoleMenuConsts.CIPH_EN;
import static com.constants.ConsoleMenuConsts.CIPH_RU;
import static com.constants.ConsoleMenuConsts.FORMAT_FILE;
import static com.constants.ConsoleMenuConsts.ORIGINAL_EN;
import static com.constants.ConsoleMenuConsts.ORIGINAL_RU;
import static com.constants.ConsoleMenuConsts.RU;
import static com.constants.ConsoleMenuConsts.EN;


public class Service {

    public void runService() throws EncoderExeption, FileExeption {
        System.out.println("Choose a language / Выберите язык" + "\n" + EN+"/"+ RU);
        String language = Menu.printChooseMenu(scanString());
        int chooseMenu = Menu.chooseOption(language, new Scanner(System.in).nextInt());
        while(chooseMenu==0){
            chooseMenu = Menu.chooseOption(language, new Scanner(System.in).nextInt());
        }
        CaesarCipher caesarCipher = new CaesarCipher();
        WorkWithFile workWithFile = new WorkWithFile("docs\\", "docs\\");
        switch (chooseMenu) {

            case 0 -> {
                if (translator(language)) {
                    System.out.println("Выберите команду соответсвующей цифре в данном списке");
                    repeatAction(language);
                } else {
                    System.out.println("Choose a command  corresponds to the number in the list");
                    repeatAction(language);
                }
            }
            case 1 -> funcCase(language, caesarCipher, workWithFile, 1);
            case 2 -> funcCase(language, caesarCipher, workWithFile, 2);
            case 3 -> miniCase(language, caesarCipher, workWithFile, 3);
            case 4 -> miniCase(language, caesarCipher, workWithFile, 4);
            case 5 -> funcCase(language, caesarCipher, workWithFile, 5);



        }
    }

    private void repeatAction(String language) throws EncoderExeption, FileExeption {
        Service service = new Service();
        if (translator(language)) {
            System.out.println("Желаете выбрать другую команду? Да/...");
            String answer = scanString();
            if (answer.equalsIgnoreCase("да")) {
                service.runService();
            }
        } else {
            System.out.println("Do you want to choose any command? Yes/...");
            String answer = scanString();
            if (answer.equalsIgnoreCase("yes")) {
                service.runService();
            }

        }
    }

    private void printKey(String language) {
        if (translator(language)) {
            System.out.print("ключ = ");
        } else {
            System.out.print("key = ");
        }

    }

    private void enterKey(String language) {
        if (translator(language)) {
            System.out.print("Введите ключ =");
        } else {
            System.out.print("Enter the key =");
        }
        System.out.println();
    }

    private void nameOfFileLoad(String language, String ru, String en) {
        if (translator(language)) {

            System.out.print("Введите название " + ru + " ТЕКСТОВОГО  файла для загрузки ");
        } else {
            System.out.print("Enter name of " + en + " TEXT file to load");
        }
        System.out.println();
    }

    private void nameOfFileSave(String language) {
        if (translator(language)) {
            System.out.print("Введите название ТЕКСТОВОГО  файла для сохранения ");
        } else {
            System.out.print("Enter name of TEXT file to save");
        }
        System.out.println();
    }

    private boolean translator(String tongue) {
        return tongue.equalsIgnoreCase(RU);
    }

    private void funcCase(String language, CaesarCipher caesarCipher, WorkWithFile workWithFile, int cases) throws EncoderExeption, FileExeption {
        nameOfFileLoad(language, ORIGINAL_RU, ORIGINAL_EN);
        String loadFile = scanString();
        if (cases == 1) {
            case1(language, caesarCipher, workWithFile, loadFile);
        } else if (cases == 2) {
            case2(language, caesarCipher, workWithFile, loadFile);
        } else {
            nameOfFileLoad(language, CIPH_RU, CIPH_EN);
            String chipFile = scanString();
            printKey(language);
            System.out.println(caesarCipher.getKeyFromEncryptText(workWithFile.loadTextOfFile(loadFile + FORMAT_FILE), workWithFile.loadTextOfFile(chipFile + FORMAT_FILE)));
        }
        repeatAction(language);
    }

    private void case2(String language, CaesarCipher caesarCipher, WorkWithFile workWithFile, String loadFile) throws FileExeption {
        StringBuilder stringBuilder;
        enterKey(language);
        int key =scanInt();
        stringBuilder = caesarCipher.toDeCrypt(workWithFile.loadTextOfFile(loadFile + FORMAT_FILE), key);
        nameOfFileSave(language);
        String saveFile =scanString();
        workWithFile.createFileWriteToText(stringBuilder, (saveFile + FORMAT_FILE));
    }

    private void case1(String language, CaesarCipher caesarCipher, WorkWithFile workWithFile, String loadFile) throws EncoderExeption, FileExeption {
        StringBuilder stringBuilder;
        enterKey(language);
        int key =scanInt();
        stringBuilder = caesarCipher.toEncrypt(workWithFile.loadTextOfFile(loadFile + FORMAT_FILE), key);
        nameOfFileSave(language);
        String saveFile =scanString();
        workWithFile.createFileWriteToText(stringBuilder, (saveFile + FORMAT_FILE));
    }

    private void miniCase(String language, CaesarCipher caesarCipher, WorkWithFile workWithFile, int cases) throws EncoderExeption, FileExeption {
        nameOfFileLoad(language, CIPH_RU, CIPH_EN);
        if (cases == 3) {
            System.out.println(caesarCipher.toDeCryptWithComma(workWithFile.loadTextOfFile(new Scanner(System.in).nextLine() + FORMAT_FILE)));
        } else {
            System.out.println(caesarCipher.statisticDeCryptWithSpace(workWithFile.loadTextOfFile(new Scanner(System.in).nextLine() + FORMAT_FILE)));
        }

        repeatAction(language);
    }
    private String scanString(){
        return new Scanner(System.in).nextLine();
    }
    private int scanInt(){
        return new Scanner(System.in).nextInt();
    }
    


}
