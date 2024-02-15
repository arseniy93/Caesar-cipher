package com.main;


import com.exeption.EncoderExeption;
import com.exeption.FileExeption;
import com.services.Service;


public class Application {
    public static void main(String[] args) throws EncoderExeption, FileExeption {

        new Service().runService();

    }
}
