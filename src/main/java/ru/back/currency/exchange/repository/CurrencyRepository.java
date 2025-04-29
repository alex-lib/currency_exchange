package ru.back.currency.exchange.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.back.currency.exchange.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByIsoNumCode(Long isoNumCode);
}
