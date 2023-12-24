package stas.thermometer.infrastructures.database;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuerryBuilderTest {


    private final Mesure mesure = new Mesure("thermometerName", LocalDateTime.now(), "type", "format", 1.0);
    private final Alert alert = new Alert(1.0, 1);

    @Test
    public void Should_Return_String_Insert_Query_When_Build_Insert_Query_isCall_With_mesure() {
        QueryBuilder<Mesure> queryBuilder = new QueryBuilder<>();
        String query = queryBuilder.buildInsertQuery(mesure, "Mesure");
        assertEquals("INSERT INTO Mesure (thermometerName, datetime, type, format, value) VALUES (?, ?, ?, ?, ?)", query);
    }

    @Test
    public void Should_Return_String_Insert_Query_When_Build_Insert_Query_isCall_With_alert() {
        QueryBuilder<Alert> queryBuilder = new QueryBuilder<>();
        String query = queryBuilder.buildInsertQuery(alert, "Alert");
        assertEquals("INSERT INTO Alert (expectedValue, idMesure) VALUES (?, ?)", query);
    }

}
