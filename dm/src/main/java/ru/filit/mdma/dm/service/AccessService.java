package ru.filit.mdma.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.exception.WrongDataException;
import ru.filit.mdma.dm.model.Access;
import ru.filit.mdma.dm.model.Role;
import ru.filit.mdma.dm.web.dto.AccessDto;
import ru.filit.mdma.dm.web.dto.AccessRequestDto;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class AccessService {

    @Autowired
    private YamlService yamlService;

    private final URL access2FileUrl;
    private final URL access3FileUrl;

    public AccessService(@Value("${datafiles.access2}") String access2FileName,@Value("${datafiles.access3}") String access3FileName){
        this.access2FileUrl = getClass().getClassLoader().getResource(access2FileName);
        this.access3FileUrl = getClass().getClassLoader().getResource(access3FileName);
    }


    public List<AccessDto> getAccess(AccessRequestDto accessRequestDto) throws IOException, WrongDataException {
        if(!accessRequestDto.getVersion().equals("2") && !accessRequestDto.getVersion().equals("3")) throw new WrongDataException("Wrong version");
        URL url;
        switch (accessRequestDto.getVersion()){
            case "2":
                url=access2FileUrl;
                break;
            case "3":
                url=access3FileUrl;
                break;
            default:
                url=null;
                break;
        }
        List<Access> accesses=yamlService.readYaml(url, Access.class);
        List<AccessDto> accessDtoList =new LinkedList<>();
        accesses.forEach(access -> {
            if(access.getRole().getValue().equals(accessRequestDto.getRole())){
                accessDtoList.add(new AccessDto(access.getEntity(), access.getProperty()));
            }
        });
        return  accessDtoList;
    }
}
