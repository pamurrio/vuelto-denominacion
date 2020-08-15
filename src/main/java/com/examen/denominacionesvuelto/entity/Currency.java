package com.examen.denominacionesvuelto.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "monedas")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer value;
    private Integer cant;

    public Long getId() {
	return Id;
    }

    public Integer getValue() {
	return value;
    }

    public Integer getCant() {
	return cant;
    }

    public void setCant(Integer cant) {
	this.cant = cant;
    }
}
