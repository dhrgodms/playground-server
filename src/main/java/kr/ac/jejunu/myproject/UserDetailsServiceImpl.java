package kr.ac.jejunu.myproject;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Member member = memberDao.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(name));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (name.equals("admin")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        System.out.println("name: " + name + "grantedAuthorities" + grantedAuthorities);
        System.out.println("member.getName(): " + member.getName() + "member.getPassword(): " + member.getPassword());


        return new User(member.getName(), member.getPassword(), grantedAuthorities);
    }

}