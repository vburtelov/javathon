package ru.filit.mdma.dm.model;

import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
  
  MANAGER("MANAGER"),
  
  SUPERVISOR("SUPERVISOR"),
  
  AUDITOR("AUDITOR");

  private String value;

  Role(String value) {
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
  public static Role fromValue(String value) {
    for (Role b : Role.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

