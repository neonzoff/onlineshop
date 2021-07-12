package com.neonzoff.onlineshop.config;

import com.neonzoff.onlineshop.model.PrivateAccount;
import com.neonzoff.onlineshop.model.Role;
import com.neonzoff.onlineshop.model.UserModel;
import com.neonzoff.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Tseplyaev Dmitry
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/registration").not().fullyAuthenticated()
//                .antMatchers("/admin/**").hasAuthority(Role.ADMIN.name())
//                .antMatchers("/").permitAll()
                .antMatchers("/images/**", "/css/**").permitAll()  //страницы с полным доступом неавторизованным пользователям
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login");

        if (userService.allUsers().isEmpty()) {
            addUserInDB("admin", "admin", Role.ADMIN,
                    "Админов", "Админ", "Админович",
                    "JVM", 1_000_000, "89992223344",
                    new PrivateAccount()
            );
            addUserInDB("manager", "manager", Role.MANAGER,
                    "Менеджеров", "Менеджер", "Менеджерович",
                    "JVM", 1_000_000, "89992223344",
                    new PrivateAccount()
            );
            addUserInDB("user", "user", Role.USER,
                    "Тестовый", "Михаил", "Поликарпович",
                    "JVM", 1_000_000, "89992223344",
                    new PrivateAccount()
            );
        }
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }


    private void addUserInDB(String username, String password, Role role,
                             String surname, String name, String middleName,
                             String address, int balance, String phoneNumber,
                             PrivateAccount privateAccount
    ) {
        UserModel user = new UserModel();
        privateAccount.setUser(user);
        privateAccount.setBalance(balance);
        user.setPrivateAccount(privateAccount);
        user.setAddress(address);
        user.setRole(role);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setSurname(surname);
        user.setName(name);
        user.setMiddleName(middleName);
        user.setUsername(username);
        userService.createUser(user);
    }

}
