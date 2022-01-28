package ru.filit.mdma.crm.web.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;



/**
 * Параметры поиска клиентов
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientSearchDto {

  private String id;

  private String lastname;

  private String firstname;

  private String patronymic;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private String birthDate;

  private String passport;

  private String inn;

}

