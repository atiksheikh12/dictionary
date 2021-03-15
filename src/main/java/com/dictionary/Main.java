package com.dictionary;

import com.dictionary.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DictionaryService<String, String> dictionary = new DictionaryService<>();

        System.out.println("Enter Command -> ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while (!"exit".equalsIgnoreCase(command)) {

            try {
                if (StringUtils.isEmpty(command)) {
                    throw new IllegalArgumentException("invalid command");
                }

                dictionary.multivalueDictionary(command, dictionary);
            } catch (IllegalArgumentException iae) {
                System.out.println("Invalid command, please re-enter!!");
            }
            System.out.print(" > ");
            command = scanner.nextLine();
        }
    }
}
