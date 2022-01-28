package ru.filit.mdma.crm.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.filit.mdma.crm.model.User;

import java.util.Collection;
import java.util.Collections;

public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean isActive;
    private Collection<? extends GrantedAuthority> grantedAuthorities;


    public static MyUserDetails from(User user){
        MyUserDetails userDetails =new MyUserDetails();
        userDetails.username= user.getUsername();
        userDetails.password=user.getPassword();
        userDetails.grantedAuthorities= Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getValue()));
        userDetails.isActive=true;
        return userDetails;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
