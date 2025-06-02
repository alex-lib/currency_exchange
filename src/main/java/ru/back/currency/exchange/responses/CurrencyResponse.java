package ru.back.currency.exchange.responses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CurrencyResponse {
    private String name;
    private double value;
}