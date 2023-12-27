package stas.thermometer.presentations;

import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;
import stas.thermometer.domains.ThermometerInterface;
import stas.thermometer.presentations.messages.MsgNotifOrganization;

import java.util.List;
import java.util.Map;

public class MainPresenter {

    private final MainViewInterface view;
    private final ThermometerInterface thermometer;
    private int currentProbe = 0;
    private final List<AggregatorAccessor> aggregatorAccessors;
    private final MsgNotifOrganization msgNotifOrganization;

    // ----------------- Messages -----------------
    private static final String HELP_MESSAGE = "h: help\nP: quit\nr: raise\nm: minimize\ns: change probe";
    private static final String BYE_MESSAGE = "Bye";
    private static final String RAISE_MESSAGE = "raise";
    private static final String MINIMIZE_MESSAGE = "minimize";
    private static final String PROBE_CHANGE_MESSAGE = "Changement de sonde\nSonde actuelle : ";
    // --------------------------------------------

    public MainPresenter(MainViewInterface view, ThermometerInterface thermometer, Map<String, String> format) {
        this.view = view;
        this.view.setPresenter(this);
        this.thermometer = thermometer;
        this.aggregatorAccessors = this.thermometer.getAggregatorsAccessor();

        this.msgNotifOrganization = new MsgNotifOrganization(format);

        for ( AggregatorAccessor aggregatorAccessor : aggregatorAccessors ) {
            aggregatorAccessor.addSubscriber(this::updateAggregatorNotification);
        }
    }

    public void onUpdate() {
        this.thermometer.notifyUpdate();
    }

    public void Start() {
        this.view.printString("Bienvenue dans le thermomètre");
        this.view.inputLoop();
    }

    /**
     * @param userInput la commande entrée par l'utilisateur
     * @implNote cette méthode est appelée par la vue à chaque fois que l'utilisateur entre une commande
     * <p>
     * pmd NCSS: plusieurs versions de cette méthode ont été testées, pour rendre la méthode plus courte,
     * impossible de réduir d'avenatage la méthode dans une utilisations de switch
     * (DP chain of responsability a éventuellement mettre en place mais risque de complexifier le code inutilement)
     */
    public void processingUserInput(String userInput) {
        switch ( userInput ) {
            case "h":
                this.view.printString(HELP_MESSAGE);
                break;
            case "q":
                this.view.printString(BYE_MESSAGE);
                break;
            case "r":
                this.view.printString(RAISE_MESSAGE);
                adjustDelta(true);
                break;
            case "m":
                this.view.printString(MINIMIZE_MESSAGE);
                adjustDelta(false);
                break;
            case "s":
                changeProbe();
                break;
            default:
                this.view.printString("Commande inconnue");
                break;
        }
    }

    private void adjustDelta(boolean shouldRaise) {
        this.aggregatorAccessors.get(this.currentProbe).adjustDelta(shouldRaise);
    }

    private void changeProbe() {
        this.currentProbe = (this.currentProbe + 1) % this.aggregatorAccessors.size();
        this.view.printString(PROBE_CHANGE_MESSAGE + this.aggregatorAccessors.get(this.currentProbe).getName());
    }

    public void updateAggregatorNotification(String aggregatorName) {
        AggregatorAccessor aggregatorAccessorCible = this.aggregatorAccessors.stream().filter(aggregatorAccessor -> aggregatorAccessor.getName().equals(aggregatorName)).findFirst().orElseThrow(() -> new RuntimeException("AggregatorAccessor not found"));

        if ( aggregatorAccessorCible.getAlarmType() == 0 ) {
            this.view.printString(this.msgNotifOrganization.getMsgCurrent(aggregatorAccessorCible));
        }
        else {
            this.view.printString(this.msgNotifOrganization.getMsgAlert(aggregatorAccessorCible));
        }

/*
ancienne version de l'updater, stable mais pas bien
                this.aggregatorAccessors.stream()
                        .filter(aggregatorAccessor -> aggregatorAccessor.getName().equals(aggregatorName))
                        .forEach(aggregatorAccessor -> {
                            this.view.printString("Update de la sonde " + aggregatorName);
                            this.view.printString("Valeur actuelle : " + aggregatorAccessor.getmesurementMod().value());
                            this.view.printString("Valeur simple : " + aggregatorAccessor.getmesurementSimple().value());
                            this.view.printString("Alarme : " + aggregatorAccessor.getAlarmType());
                            this.view.printString("DateTime : " + aggregatorAccessor.getmesurementMod().dateTime());
                            this.view.printString(" ");
                        });


                aggregatorAccessors.get(1).getName();

                if (name.equals(this.aggregatorAccessors.get(this.currentProbe).getName())) {
                    this.view.printString("Update de la sonde " + name);
                    this.view.printString("Valeur actuelle : " + this.aggregatorAccessors.get(this.currentProbe).getmesurementMod().value());
                    this.view.printString("Valeur simple : " + this.aggregatorAccessors.get(this.currentProbe).getmesurementSimple().value());
                    this.view.printString("Alarme : " + this.aggregatorAccessors.get(this.currentProbe).getAlarmType());
                    this.view.printString(" ");
                }
                */
    }


}
