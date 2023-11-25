/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package stas.thermometer.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stas.thermometer.app.personalExceptions.fatalException;
import stas.thermometer.domains.AggregatorMain;
import stas.thermometer.infrastructures.database.MainDataBase;
import stas.thermometer.presentations.MainPresenter;
import stas.thermometer.views.MainView;

// Dépendances propres
import java.util.concurrent.*;

public class App {

    /**
     *
     * --NOTE--
     *
     * prévoir une évolution de maintenabilité du code
     * ex : pression atmosphérique, rayonnement thermique, vitesse du vent, Conductivité thermique.
     * (créer un probe "ProbeNom", créer un message "MsgNom" + imp MsgAlert, implémenter dans ValueType et c'est tout ... normalement)
     *
     *
     * gradlew run --args="--config-file config.ini"
     *
     *
     *
     *
     *-- Table 'Mesure'
     * CREATE TABLE Mesure (
     *                          id INT AUTO_INCREMENT PRIMARY KEY,
     *                          thermometer_name VARCHAR(255) NOT NULL,
     *                          datetime DATETIME NOT NULL,
     *                          type VARCHAR(50) NOT NULL,
     *                          format VARCHAR(50) NOT NULL,
     *                          value DOUBLE NOT NULL
     * );
     *
     * -- Table 'Alert'
     * CREATE TABLE Alert (
     *                         id INT AUTO_INCREMENT PRIMARY KEY,
     *                         expected_value DOUBLE NOT NULL,
     *                         id_mesure INT NOT NULL,
     *                         FOREIGN KEY (id_mesure) REFERENCES Mesure(id)
     * );
     *
     *
     *
     */


    private static final Logger LOG = LogManager.getLogger("stas");

    public static void main(String[] args) {


        var scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        try {
            ArgsExecutor argsExecutor = new ArgsExecutor(args);
            for (AggregatorMain aggregator : argsExecutor.getAggregators()) {
                argsExecutor.getThermometer().Subscribe(aggregator);
            }
            MainView mainView = new MainView();
            MainPresenter mainPresenter = new MainPresenter(mainView, argsExecutor.getThermometer(), argsExecutor.getFormatmap());

            new MainDataBase(argsExecutor.getConnectionString(),argsExecutor.getThermometer(),argsExecutor.getFormatmap());
            /*
            mainPresenter.setFormat();
            dbRepository.setFormat ();
            dbRepository.setDBConfiguration ();
             */


            var task = new RefreshProbeTask(mainPresenter);
            //Exécute la tache tout de suite et la répète toutes les 2 secondes
            scheduledExecutor.scheduleAtFixedRate(task, 0, 100, TimeUnit.MILLISECONDS);



            // TODO: 12/11/2023 askip c'est pas bien ici
            mainPresenter.Start();

        } catch ( fatalException e) {
            LOG.fatal(e.getMessage());
        }
        finally {
            scheduledExecutor.shutdown();
        }


    }

}

/**
 * Représente une boucle principale à demander un rafraichissement de la sonde.
 * */
class RefreshProbeTask implements Runnable {
    private final MainPresenter mainPresenter;
    public final Logger log = LogManager.getLogger("stas");
    public RefreshProbeTask(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;
    }
    @Override
    public void run() {
        //log.info("You should tell the probe to refresh");
        mainPresenter.onUpdate();

    }

}
