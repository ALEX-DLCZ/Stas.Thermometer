package stas.thermometer.presentations;

import stas.thermometer.domains.ThermometerRepositoryInterface;


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
        this.view.printString("update");

    }






    public void Start() {

        this.view.printString("Bienvenue dans le thermomètre");
        this.view.inputLoop();

    }



    public void processingUserInput(String userInput) {
        switch (userInput) {
            case "h": {
                this.view.printString("h: help");
                this.view.printString("P: quit");
                this.view.printString("h: help");
                this.view.printString("h: help");
                this.view.printString("h: help");
                this.view.printString("h: help");
                break;
            }
            case "q": {
                this.view.printString("Bye");
                break;
            }
            case "r": {
                this.view.printString("raise");
                break;
            }
            case "m": {
                this.view.printString("lower");
                break;
            }
            case "s": {
                this.view.printString("Changement de sonde");
                break;
            }
            default:
                this.view.printString("Commande non reconnue");
        }
    }


}
