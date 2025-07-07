package dasturlashasil.uz.config;


import dasturlashasil.uz.Enums.ProfileRoleEnum;
import dasturlashasil.uz.Enums.ProfileStatusEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    private Integer id;
    private String username;
    private String password;//Profile ni umuman aralashtirmadik
    private ProfileStatusEnum statusEnum;
    private  List<SimpleGrantedAuthority> roles;

    public CustomUserDetails(Integer id, String username,
                             String password,
                             ProfileStatusEnum statusEnum,
                             List<ProfileRoleEnum> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.statusEnum = statusEnum;
        List<SimpleGrantedAuthority> roleList = new ArrayList<>();
        roles.forEach(role -> {
            roleList.add( new SimpleGrantedAuthority(role.name()));
        });
        this.roles = roleList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;//debugger da
    }


    @Override
    public String getPassword() {
        return password;//debugger da
    }

    @Override
    public String getUsername() {
        return username;//debugger da
    }


    @Override
    public boolean isAccountNonExpired() {
        return true; // UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return  statusEnum.equals(ProfileStatusEnum.ACTIVE); //UserDetails.super.isAccountNonLocked();
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return  true; //UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return  true; //UserDetails.super.isEnabled();
    }


    public Integer getId() {
        return id;
    }
}
