package ru.filit.mdma.dm.web.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ru.filit.mdma.dm.model.Account;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Банковские счета клиента
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto   {

  @NotNull
  private String number;

  @NotNull
  private String clientId;
  @NotNull
  private Account.TypeEnum type;
  @NotNull
  private Account.CurrencyEnum currency;
  @NotNull
  private Account.StatusEnum status;
  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date openDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date closeDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Integer deferment;
  @NotNull
  @Setter(AccessLevel.NONE)
  private String shortcut;

  private void setShortcut(){
    this.shortcut=number.substring(number.length()-4);
  }

  public static AccountDto fromEntity(Account account){
    AccountDto accountDto=new AccountDto();
    accountDto.setNumber(account.getNumber());
    accountDto.setClientId(account.getClientId());
    accountDto.setType(account.getType());
    accountDto.setCurrency(account.getCurrency());
    accountDto.setStatus(account.getStatus());
    if(account.getCloseDate()!=null) {
      accountDto.setCloseDate(new Date(account.getCloseDate()*1000L));
    }
    accountDto.setOpenDate(new Date(account.getOpenDate()*1000L));
    accountDto.setDeferment(account.getDeferment());
    accountDto.setShortcut();
    return accountDto;
  }


}

