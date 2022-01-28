package ru.filit.mdma.dm.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import ru.filit.mdma.dm.web.dto.ContactDto;

import javax.validation.constraints.*;

/**
 * Контактные данные клиента
 */
@Data
public class Contact  implements Serializable {

  @NotNull
  private String id;

  @NotNull
  private String clientId;

  @NotNull
  public enum TypeEnum {
    PHONE("PHONE"),
    
    EMAIL("EMAIL");

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
  private String value;

  public static Contact fromDto(ContactDto contactDto){
    Contact contact=new Contact();
    contact.clientId= contactDto.getClientId();
    contact.type=contactDto.getType();
    contact.value=contactDto.getValue();
    return contact;
  }

}

