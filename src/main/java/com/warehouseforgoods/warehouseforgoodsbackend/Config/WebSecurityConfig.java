package com.warehouseforgoods.warehouseforgoodsbackend.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final UserDetailsService userDetailsService;

   @Autowired
   public WebSecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
      this.userDetailsService = userDetailsService;
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception{
       http.csrf().disable()
               .authorizeRequests()
               .antMatchers("/user/list","/registration").permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic()
               .and()
               .formLogin().loginPage("/login").permitAll()
               .and()
               .logout()
               .logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))
               .invalidateHttpSession(true)
               .clearAuthentication(true)
               .deleteCookies("JSESSIONID");
   }
   @Override
   protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder){
      authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
   }

   @Bean
   protected DaoAuthenticationProvider daoAuthenticationProvider(){
      DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
      daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
      daoAuthenticationProvider.setUserDetailsService(userDetailsService);

      return daoAuthenticationProvider;
   }

   @Bean
   protected PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder(12);
   }


}
