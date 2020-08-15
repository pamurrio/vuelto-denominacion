package com.examen.denominacionesvuelto.dao;

import org.springframework.data.repository.CrudRepository;

import com.examen.denominacionesvuelto.entity.Currency;

public interface CurrencyDao extends CrudRepository<Currency, Long> {

}
