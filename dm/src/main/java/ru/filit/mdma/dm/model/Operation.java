package ru.filit.mdma.dm.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * Банковские операции по счету
 */
@ApiModel(description = "Банковские операции по счету")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Operation implements Serializable {

  public enum TypeEnum {
    RECEIPT("RECEIPT"),
    
    EXPENSE("EXPENSE");

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

  @NotNull
  private String accountNumber;

  @NotNull
  private Long operDate;

  @NotNull
  private BigDecimal amount;

  private String description;

}

