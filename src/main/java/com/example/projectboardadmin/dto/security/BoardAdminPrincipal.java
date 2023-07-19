package com.example.projectboardadmin.dto.security;



import com.example.projectboardadmin.domain.constant.RoleType;
import com.example.projectboardadmin.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardAdminPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname,
        String memo,
        Map<String, Object> oAuth2Attributes
) implements UserDetails, OAuth2User {


    //일반 로그인 (카카오 로그인 x)
    public static BoardAdminPrincipal of(String username, String password, Set<RoleType> roleTypes, String email, String nickname, String memo) {
        return of(username, password, roleTypes, email, nickname, memo, Map.of());
                                                            // null 이랑은 잘 안맞는다고 생각해서 비어있는 Map 생성
    }
    // 카카오 로그인
    public static BoardAdminPrincipal of(String username, String password, Set<RoleType> roleTypes, String email, String nickname, String memo, Map<String, Object> oAuth2Attributes) {

        return new BoardAdminPrincipal(
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getRoleName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                email,
                nickname,
                memo,
                oAuth2Attributes
        );
    }

    public static BoardAdminPrincipal from(UserAccountDto dto) {
        return BoardAdminPrincipal.of(
                dto.userId(),
                dto.userPassword(),
                dto.roleTypes(),
                dto.email(),
                dto.nickname(),
                dto.memo()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                username,
                password,
                authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(RoleType::valueOf)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                email,
                nickname,
                memo
        );
    }


    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }


    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }


    // OAuth2User 를 사용하려면 구현 해야함
    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attributes;
    }
    @Override
    public String getName() {
        return username;
    }
    // ----------------------------------------------




}