package stas.thermometer.domains;

import java.time.LocalDateTime;

public record Measurement(double value, LocalDateTime dateTime) {}
