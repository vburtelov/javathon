package ru.filit.mdma.dm.model;

import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Уровни клиентов Банка
 */
public enum ClientLevel {
  
  Low("LOW"),
  
  Middle("MIDDLE"),
  
  Silver("SILVER"),
  
  Gold("GOLD");

  private String value;

  ClientLevel(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ClientLevel fromValue(String value) {
    for (ClientLevel b : ClientLevel.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

