package stas.thermometer.presentations;

import stas.thermometer.domains.aggregator.handler.AggregatorAccessor;
import stas.thermometer.domains.ThermometerInterface;
import stas.thermometer.presentations.messages.MsgNotifOrganization;

import java.util.List;
import java.util.Map;


public class MainPresenter  {

    private final MainViewInterface view;
    private final ThermometerInterface thermometer;
    private int currentProbe = 0;
    private final List<AggregatorAccessor> aggregatorAccessors;
    private final MsgNotifOrganization msgNotifOrganization;


    public MainPresenter(MainViewInterface view, ThermometerInterface thermometer, Map<String, String> format) {
        this.view = view;
        this.view.setPresenter(this);
        this.thermometer = thermometer;
        this.aggregatorAccessors = this.thermometer.getAggregatorsAccessor();

        this.msgNotifOrganization = new MsgNotifOrganization(format);

        for (AggregatorAccessor aggregatorAccessor : aggregatorAccessors) {
            aggregatorAccessor.addSubscriber(this::updateAggregatorNotification);
        }
    }

    public void onUpdate() {
        //this.view.printString("update");
        this.thermometer.notifyUpdate();
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
                this.aggregatorAccessors.get(this.currentProbe).adjustDelta(true);
                break;
            }
            case "m": {
                this.view.printString("minimize");
                this.aggregatorAccessors.get(this.currentProbe).adjustDelta(false);

                break;
            }
            case "s": {
                this.view.printString("Changement de sonde");
                this.currentProbe = (this.currentProbe + 1) % this.aggregatorAccessors.size();
                this.view.printString("Sonde actuelle : " + this.aggregatorAccessors.get(this.currentProbe).getName());
                break;
            }
            default:
                this.view.printString("Commande non reconnue");
                break;
        }
    }



    public void updateAggregatorNotification(String aggregatorName) {
        AggregatorAccessor aggregatorAccessorCible = this.aggregatorAccessors.stream()
                .filter(aggregatorAccessor -> aggregatorAccessor.getName().equals(aggregatorName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("AggregatorAccessor not found"));

        //vérifie si le type d'allert est 0 ou autre
        if (aggregatorAccessorCible.getAlarmType() == 0) {
            this.view.printString(this.msgNotifOrganization.getMsgCurrent(aggregatorAccessorCible));
        } else {
            this.view.printString(this.msgNotifOrganization.getMsgAlert(aggregatorAccessorCible));
        }





//        this.aggregatorAccessors.stream()
//                .filter(aggregatorAccessor -> aggregatorAccessor.getName().equals(aggregatorName))
//                .forEach(aggregatorAccessor -> {
//                    this.view.printString("Update de la sonde " + aggregatorName);
//                    this.view.printString("Valeur actuelle : " + aggregatorAccessor.getmesurementMod().value());
//                    this.view.printString("Valeur simple : " + aggregatorAccessor.getmesurementSimple().value());
//                    this.view.printString("Alarme : " + aggregatorAccessor.getAlarmType());
//                    this.view.printString("DateTime : " + aggregatorAccessor.getmesurementMod().dateTime());
//                    this.view.printString(" ");
//                });



//        aggregatorAccessors.get(1).getName();
//
//        if (name.equals(this.aggregatorAccessors.get(this.currentProbe).getName())) {
//            this.view.printString("Update de la sonde " + name);
//            this.view.printString("Valeur actuelle : " + this.aggregatorAccessors.get(this.currentProbe).getmesurementMod().value());
//            this.view.printString("Valeur simple : " + this.aggregatorAccessors.get(this.currentProbe).getmesurementSimple().value());
//            this.view.printString("Alarme : " + this.aggregatorAccessors.get(this.currentProbe).getAlarmType());
//            this.view.printString(" ");
//        }
    }


}
