package ru.back.currency.exchange.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.back.currency.exchange.dto.CurrencyDto;
import ru.back.currency.exchange.responses.CurrencyResponse;
import ru.back.currency.exchange.services.CurrencyService;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/currency")
public class CurrencyController {
    private final CurrencyService service;

    @GetMapping(value = "/{id}")
    ResponseEntity<CurrencyDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping(value = "/convert")
    ResponseEntity<Double> convertValue(@RequestParam("value") Long value, @RequestParam("numCode") Long numCode) {
        return ResponseEntity.ok(service.convertValue(value, numCode));
    }

    @PostMapping("/create")
    ResponseEntity<CurrencyDto> create(@RequestBody CurrencyDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping(value = "/")
    ResponseEntity<Map<String, List<CurrencyResponse>>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
