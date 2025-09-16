package com.cinema;

import com.cinema.view.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Menu menuApp = new Menu();

        menuApp.menuApp(keyboard);
    }
}