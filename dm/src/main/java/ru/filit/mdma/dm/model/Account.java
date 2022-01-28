package ru.filit.mdma.dm.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * Банковские счета клиента
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account  implements Serializable {

  @NotNull
  private String number;

  @NotNull
  private String clientId;


  public enum TypeEnum {
    PAYMENT("PAYMENT"),
    
    BUDGET("BUDGET"),
    
    TRANSIT("TRANSIT"),
    
    OVERDRAFT("OVERDRAFT");

    private String value;

    TypeEnum(String value) {
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
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @NotNull
  private TypeEnum type;

  public enum CurrencyEnum {
    RUR("RUR");

    private String value;

    CurrencyEnum(String value) {
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
    public static CurrencyEnum fromValue(String value) {
      for (CurrencyEnum b : CurrencyEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @NotNull
  private CurrencyEnum currency = CurrencyEnum.RUR;


  public enum StatusEnum {
    INACTIVE("INACTIVE"),
    
    ACTIVE("ACTIVE"),
    
    LOCKED("LOCKED"),
    
    CLOSED("CLOSED");

    private String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @NotNull
  private StatusEnum status;

  @NotNull
  private Long openDate;

  private Long closeDate;

  private Integer deferment;

}

