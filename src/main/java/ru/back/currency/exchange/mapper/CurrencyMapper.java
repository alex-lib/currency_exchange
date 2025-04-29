package ru.back.currency.exchange.mapper;
import org.mapstruct.Mapper;
import ru.back.currency.exchange.dto.CurrencyDto;
import ru.back.currency.exchange.entity.Currency;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyDto convertToDto(Currency currency);
    Currency convertToEntity(CurrencyDto currencyDto);
}