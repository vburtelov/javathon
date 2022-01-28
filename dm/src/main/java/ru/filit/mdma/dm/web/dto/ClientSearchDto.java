package ru.filit.mdma.dm.web.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * Параметры поиска клиентов
 */
@Getter
@Setter
@Data
public class ClientSearchDto   {

  private String id;

  private String lastname;

  private String firstname;

  private String patronymic;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date birthDate;

  private String passport;

  private String inn;

}

