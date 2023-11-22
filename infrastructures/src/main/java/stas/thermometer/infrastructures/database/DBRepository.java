package stas.thermometer.infrastructures.database;

public class DBRepository {
}



/*
-- Table 'Mesures'
CREATE TABLE Mesures (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         thermometer_name VARCHAR(255) NOT NULL,
                         datetime DATETIME NOT NULL,
                         type VARCHAR(50) NOT NULL,
                         format VARCHAR(50) NOT NULL,
                         value DOUBLE NOT NULL
);

-- Table 'Alerts'
CREATE TABLE Alerts (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        expected_value DOUBLE NOT NULL,
                        id_mesure INT NOT NULL,
                        FOREIGN KEY (id_mesure) REFERENCES Mesures(id)
);
 */