package com.vti.Final.Java.Advance.config.security;

import com.vti.Final.Java.Advance.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    IAccountService iAccountService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        //Mã hóa pass;
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(iAccountService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/css/**", "/assets/js/**", "/assets/images/**");
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http
                .authorizeRequests()
                    .antMatchers("/login", "/api/v1/accounts/create/**", "/api/v1/departments/name/**").permitAll()
                    .antMatchers("/admin").hasAnyAuthority(("ADMIN"))
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/hello", true)
                    .successHandler((request, response, authentication) -> {
                    for (GrantedAuthority authority : authentication.getAuthorities()){
                        if(authority.getAuthority().equals("ADMIN")){
                            response.sendRedirect("/admin");
                        }else {
                            response.sendRedirect("/hello");
                        }
                    }
                })
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll()
                .and()
                    .httpBasic()
                    .and()
                    .csrf().disable();
    }
}
