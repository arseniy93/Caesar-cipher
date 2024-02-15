package com.main.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static com.constants.ConsoleMenuConsts.ORIGINAL_RU;
import static com.constants.ConsoleMenuConsts.RU;
import static com.constants.ConsoleMenuConsts.EN;

public enum Menu {
    START(0, "HELP" ),
    TOCIPHWITHKEY(1, "Ciph text with key" ),
    TOUNCIPHWITHKEY(2, "Unciph text with key" ),
    TOUNCIPHWITHCOMMA(3, "Unciph text using by method " + "\"" + "BRUTEFORCEWITHCOMMA" + "\"" ),
    TOUNCIPHWITHSPACE(4, "Unciph text using by method " + "\"" + "STATISTIC" + "\"" ),
    GETKEY(5, "Just get cipher key" ),

    STARTRU(0, "ПОМОЩЬ" ),
    TOCIPHWITHKEYRU(1, "Шифрование с помощью ключа" ),
    TOUNCIPHWITHKEYRU(2, "Расшифровка текста с помощью ключа" ),
    TOUNCIPHWITHCOMMARU(3, "Расшифровка текста, метод перебора (брут форсе по запятой)" ),
    TOUNCIPHWITHSPACRU(4, "Расшифровка текста статистическим методом (самый частый  символ - пробел)" ),
    GETKEYRU(5, "Получение ключа. Сравниваются 2 текста: оригнал, зашифрованный" );
    




    private final int number;
    private final String menuText;


    Menu(int number, String menuText) {
            this.number = number;
            this.menuText = menuText;

    }

    public static int chooseOption(String language,int number) {

        List<Menu> list = Arrays.asList(Menu.values());
        if (number<0 || number>5){
            if(language.equalsIgnoreCase(RU)){
                System.out.println("Не существует такой опции, повторите заново");
            }
            else {
                System.out.println("There isn't option, enter again");
            }

          return chooseOption(printChooseMenu(language),new Scanner(System.in).nextInt());
        }
        else{
             if(language.equalsIgnoreCase(EN) ){
                for (int i = 0; i < list.size()/2; i++) {
                    if(number==list.get(i).number){
                        return (list.get(i).number);
                    }
                }
            }
            else {
                for (int i = list.size() / 2; i < list.size()  ; i++) {
                    if(number==list.get(i).number){
                        return(list.get(i).number);
                    }
                }
            }
        }



        return 0;
    }

    public static  String printChooseMenu(String chooseLanguage) {
        List<Menu> list = Arrays.asList(Menu.values());
        if(chooseLanguage.equalsIgnoreCase(EN)){
            for (int i = 0; i < list.size()/2; i++) {
                System.out.println(list.get(i).number+" : "+list.get(i).menuText);
            }
        }
        else if(chooseLanguage.equalsIgnoreCase(RU)){
            for (int i = list.size() / 2; i < list.size()  ; i++) {
                    System.out.println(list.get(i).number+" : "+list.get(i).menuText);
            }
        }
        else{
            System.out.println("There isn't language in the app, please enter again"+"\n"+"Такого языка нету в данной аппе, пожалуйста введите еще раз");
           return printChooseMenu(new Scanner(System.in).nextLine());
        }
        return chooseLanguage;
    }
}
