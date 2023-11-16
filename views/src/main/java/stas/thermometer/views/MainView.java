package stas.thermometer.views;

import stas.thermometer.presentations.FunctionalInput;
import stas.thermometer.presentations.MainPresenter;
import stas.thermometer.presentations.MainViewInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainView implements MainViewInterface {

    private MainPresenter presenter;

    public MainView() {

    }


//todo interface fonctionelle
    @Override
    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }





    @Override
    public void inputLoop() {
        try (var input = new BufferedReader(new InputStreamReader(System.in))) {
            boolean quitRequested = false;
            do {
                //System.out.print("> ");
                String cmd = input.readLine();

                if (cmd.strip().equals("q")) {
                    quitRequested = true;
                }
                this.presenter.processingUserInput(cmd.strip());

            } while (!quitRequested);
        } catch (IOException e) {
            //logger.warning("Erreur de lecture de la commande");
            System.out.println("FATAL : Erreur de lecture de la commande");
        }
    }



    @Override
    public void printString(String s) {
        System.out.println(s);
    }

}
