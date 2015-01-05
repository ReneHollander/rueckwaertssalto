package at.hackenbergerhollander.rueckwaertssalto.cli;

import at.hackenbergerhollander.rueckwaertssalto.error.InvalidOptionException;

public class Main {

    public static void main(String[] args) {

        try {
            new CLIParser().parse(args,"java -jar Rueckwaertssalto.jar [options]");
        } catch (InvalidOptionException ex) {
            System.out.println(ex.getMessage());
        }
    }

}