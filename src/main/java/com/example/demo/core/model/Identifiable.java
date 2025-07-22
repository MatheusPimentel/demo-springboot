package com.example.demo.core.model;

import java.io.Serializable;

/**
 * Contrato para entidades e DTOs que possuem um identificador.
 * @param <ID> o tipo do identificador.
 */
public interface Identifiable<ID extends Serializable> {
    ID id();
}