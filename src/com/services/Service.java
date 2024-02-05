package com.services;

import com.analyzer.CaesarCipher;
import com.files.WorkWithFile;
import com.main.enums.Menu;
import java.util.Scanner;

import static com.constants.ConsoleMenuConsts.CIPH_EN;
import static com.constants.ConsoleMenuConsts.CIPH_RU;
import static com.constants.ConsoleMenuConsts.FORMAT_FILE;
import static com.constants.ConsoleMenuConsts.ORIGINAL_EN;
import static com.constants.ConsoleMenuConsts.ORIGINAL_RU;



public class Service {
    public void runService() {
        System.out.println("Choose a language / Выберите язык" + "\n" + "en/ru");
        String language = Menu.printChooseMenu(new Scanner(System.in).nextLine());
        int chooseMenu = Menu.chooseOption(language, new Scanner(System.in).nextInt());
        CaesarCipher caesarCipher = new CaesarCipher();
        WorkWithFile workWithFile = new WorkWithFile("docs\\", "docs\\");
        switch (chooseMenu) {
            case 0-> {
                if(translator(language)){
                    System.out.println("Выберите команду соответсвующей цифре");
                    repeatAction(language);
                }
                else{
                    System.out.println("Choose a command  corresponds to the number");
                    repeatAction(language);
                }

            }
            case 1 -> {

                nameOfFileLoad(language, ORIGINAL_RU,ORIGINAL_EN);
                String loadFile=new Scanner(System.in).nextLine();
                enterKey(language);
                int key = new Scanner(System.in).nextInt();
                StringBuilder stringBuilder = caesarCipher.toCipher(workWithFile.loadTextOfFile(loadFile+FORMAT_FILE), key);
                nameOfFileSave(language);
                workWithFile.createFileWriteToText(stringBuilder, (new Scanner(System.in).nextLine() + FORMAT_FILE));
                repeatAction(language);
            }
            case 2 -> {
                nameOfFileLoad(language,CIPH_RU, CIPH_EN);
                String name = new Scanner(System.in).nextLine();
                enterKey(language);
                int key = new Scanner(System.in).nextInt();
                StringBuilder stringBuilder = caesarCipher.toUnCipher(workWithFile.loadTextOfFile(name + FORMAT_FILE), key);
                nameOfFileSave(language);
                workWithFile.createFileWriteToText(stringBuilder, (new Scanner(System.in).nextLine() + FORMAT_FILE));
                repeatAction(language);
            }
            case 3 ->{
                nameOfFileLoad(language,CIPH_RU, CIPH_EN);
                //TODO language
                System.out.println(caesarCipher.toUnCipherWithComma(workWithFile.loadTextOfFile(new Scanner(System.in).nextLine()+FORMAT_FILE)));
                repeatAction(language);
            }
            case 4->{
                nameOfFileLoad(language,CIPH_RU, CIPH_EN);
                System.out.println(caesarCipher.statisticUncipherMethodSpace(workWithFile.loadTextOfFile(new Scanner(System.in).nextLine() + FORMAT_FILE)));
                repeatAction(language);
            }
            case 5->{
                nameOfFileLoad(language,ORIGINAL_RU,ORIGINAL_EN);
                String fileText=new Scanner(System.in).nextLine();
                nameOfFileLoad(language,CIPH_RU, CIPH_EN);
                String fileCiphText=new Scanner(System.in).nextLine();
                printKey(language);
                System.out.println(caesarCipher.getKeyFromCipherText(workWithFile.loadTextOfFile(fileText+FORMAT_FILE), workWithFile.loadTextOfFile(fileCiphText+FORMAT_FILE)));
                repeatAction(language);

            }

        }
    }
    private void repeatAction(String language) {
        if (translator(language)) {
            System.out.println("Желаете выбрать другую команду? Да/...");
            String answer = new Scanner(System.in).nextLine();
            if (answer.equalsIgnoreCase("да")) {
                new Service().runService();
            }
        } else {
            System.out.println("Do you want to choose any command? Yes/...");
            String answer = new Scanner(System.in).nextLine();
            if (answer.equalsIgnoreCase("yes")) {
                new Service().runService();
            }

        }
    }
    private  void printKey(String language){
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

    private void nameOfFileLoad(String language, String ru,String en) {
        if (translator(language)) {
            System.out.print("Введите название "+ru+" ТЕКСТОВОГО  файла для загрузки ");
        } else {
            System.out.print("Enter name of "+ en+" TEXT file to load");
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
        return tongue.equalsIgnoreCase("ru");

    }
}
