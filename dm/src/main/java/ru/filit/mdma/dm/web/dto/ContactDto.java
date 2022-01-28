package ru.filit.mdma.dm.web.dto;



import lombok.*;
import ru.filit.mdma.dm.model.Contact;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.regex.*;


/**
 * Контактные данные клиента
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto   {

  private String id;

  @NotNull
  private String clientId;

  @NotNull
  private Contact.TypeEnum type;

  @NotNull
  @Setter(AccessLevel.NONE)
  private String value;

  @Setter(AccessLevel.NONE)
  private String shortcut;


  private void setShortcut(){
    if(type== Contact.TypeEnum.EMAIL){
      shortcut=value.substring(value.indexOf('@')-1);
    }else if (type== Contact.TypeEnum.PHONE){
      shortcut=value.substring(value.length()-4);
    }
  }

  //validate email or phone
  private boolean isValid(String value){
    Pattern p;
    Matcher m;
    if(type== Contact.TypeEnum.EMAIL){
      p = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
      m = p.matcher(value);
      return m.matches();
    }else if (type== Contact.TypeEnum.PHONE){
      p = Pattern.compile("^((\\+7|7|8)+([0-9]){10})$");
      m = p.matcher(value);
      return m.matches();
    }
    return false;
  }
  public void setValue(String value) throws IllegalArgumentException {
    if(isValid(value)){
      this.value= value;
    }else throw new IllegalArgumentException("Unexpected value '"+value+"'");
  }

  public static ContactDto fromEntity(Contact contact){
    ContactDto contactDto =new ContactDto();
    contactDto.setId(contact.getId());
    contactDto.setClientId(contact.getClientId());
    contactDto.setType(contact.getType());
    contactDto.value= contact.getValue();
    contactDto.setShortcut();
    return contactDto;
  }
}

