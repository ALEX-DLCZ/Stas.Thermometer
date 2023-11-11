package stas.thermometer.presentations;

import stas.thermometer.domains.ThermometerRepositoryInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainPresenter {

    private final MainViewInterface view;
    private ThermometerRepositoryInterface repository;

    // TODO: 10/11/2023 possède mes agrégateur ou thermometre a voir mais il le récupère grace a thermometreRepository

    public MainPresenter(MainViewInterface view, ThermometerRepositoryInterface repository) {
        this.view = view;
        this.view.setPresenter(this);
        this.repository = repository;
    }

    public void onUpdate() {

    }

    public void Start() {

        this.view.setPresenter(this);
        this.view.inputLoop();

    }

    public void processingUserInput(String userInput) {
        switch (userInput) {
            case "h": {
                System.out.println("h: help");
                break;
            }
            case "q": {
                System.out.println("q: quit");
                break;
            }
            case "r": {
                System.out.println("r: run");
                break;
            }
            case "m": {
                System.out.println("m: stop");
                break;
            }
            default:
                System.out.println("Erreur de lecture de la commande");
                //throw new IllegalStateException("Unexpected value: " + userInput.strip());
        }
    }


}
