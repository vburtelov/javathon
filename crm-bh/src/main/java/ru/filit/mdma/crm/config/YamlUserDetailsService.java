package ru.filit.mdma.crm.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.filit.mdma.crm.model.User;
import ru.filit.mdma.crm.service.YamlService;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class YamlUserDetailsService implements UserDetailsService {

    private final URL usersFileUrl;

    private YamlService yamlService;

    public YamlUserDetailsService(){
        this.usersFileUrl = getClass().getClassLoader().getResource("datafiles/users.yml");
        this.yamlService=new YamlService();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users= null;
        try {
            users = yamlService.readYaml(usersFileUrl, User.class);
        } catch (IOException e) {
            throw new UsernameNotFoundException("Could not find user '"+username+"'");
        }
        User currentUser = users.stream().filter((user -> user.getUsername().equals(username))).findFirst()
                .orElseThrow(()->new UsernameNotFoundException("Could not find user '"+username+"'"));
        return MyUserDetails.from(currentUser);
    }
}
