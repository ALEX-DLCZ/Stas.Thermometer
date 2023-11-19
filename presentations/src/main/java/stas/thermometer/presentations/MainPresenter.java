package stas.thermometer.presentations;

import stas.thermometer.domains.AggregatorMain;
import stas.thermometer.domains.ThermometerRepositoryInterface;

import java.util.List;


public class MainPresenter {

    private final MainViewInterface view;
    private ThermometerRepositoryInterface repository;
    int currentProbe = 0;
    private List<AggregatorMain> aggregatorMains;

    // TODO: 10/11/2023 possède mes agrégateur ou thermometre a voir mais il le récupère grace a thermometreRepository

    public MainPresenter(MainViewInterface view, ThermometerRepositoryInterface repository) {
        this.view = view;
        this.view.setPresenter(this);
        this.repository = repository;
    }

    public void onUpdate() {
        //this.view.printString("update");
        this.repository.notifyUpdate();

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
                this.aggregatorMains.get(this.currentProbe).adjustDelta(0.1);
                break;
            }
            case "m": {
                this.view.printString("minimize");
                this.aggregatorMains.get(this.currentProbe).adjustDelta(-0.1);

                break;
            }
            case "s": {
                this.view.printString("Changement de sonde");
                this.currentProbe = (this.currentProbe + 1) % this.aggregatorMains.size();
                break;
            }
            default:
                this.view.printString("Commande non reconnue");
        }
    }


}
