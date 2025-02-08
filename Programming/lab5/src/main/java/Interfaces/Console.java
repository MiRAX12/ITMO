package Interfaces;

import java.util.Scanner;

public interface Console {
    void print(Object obj);
    void println(Object obj);
    void printError(Object obj);
    String readln();
    boolean isCanReadln();
    void printTable(Object obj1, Object obj2);
    void prompt();
    String getPrompt();
    void selectFileScanner(Scanner obj);
    void selectConsoleScanner();
}
