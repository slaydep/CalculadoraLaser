package com.calculadoralaser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.calculadoralaser.auth.handler.LoginSuccessHandler;
import com.calculadoralaser.models.service.JpaUserDetailsService;

@SuppressWarnings("deprecation")
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", 
//				"/images/**", "/productos/listar", "/productos/ver/**","/vectores/listar","/vectores/ver/**","/uploads/**","/fotos/uploads/*",
//				"/vectores/uploads/*","/vectores/buscar/*","/productos/buscar/*","/productos/form/*").permitAll()
//		.antMatchers("/calculadora/**").hasAnyRole("ADMIN")
//		.antMatchers("/usuarios/**").hasAnyRole("ADMIN")
//		//.antMatchers("/productos/**").hasAnyRole("USER","ADMIN")
//		/*.antMatchers("/eliminar/**").hasAnyRole("ADMIN")*/
//		/*.antMatchers("/factura/**").hasAnyRole("ADMIN")*/
//		.anyRequest().authenticated()
//		.and()
//		    .formLogin()
//		        .successHandler(successHandler)
//		        .loginPage("/login")
//		    .permitAll()
//		.and()
//		.logout().permitAll()
//		.and()
//		.exceptionHandling().accessDeniedPage("/error_403");
		
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", 
				"/images/**", "/productos/listar", "/productos/ver/**","/uploads/**","/productos/buscar/*","/fotos/uploads/*").permitAll()
		.antMatchers("/calculadora/**").hasAnyRole("ADMIN")
		.antMatchers("/usuarios/**").hasAnyRole("ADMIN")
		.antMatchers("/vectores/**").hasAnyRole("USER","ADMIN")
		.antMatchers("/fotos/**").hasAnyRole("ADMIN")
		/*.antMatchers("/factura/**").hasAnyRole("ADMIN")*/
		.anyRequest().authenticated()
		.and()
		    .formLogin()
		        .successHandler(successHandler)
		        .loginPage("/login")
		    .permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");

	}
	
	
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
	{
		PasswordEncoder encoder=passwordEncoder();
		build.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
		
		UserBuilder users=User.builder().passwordEncoder(password -> {
			return encoder.encode(password);
		});
		//build.inMemoryAuthentication()
		//.withUser(users.username("admin").password("12345").roles("ADMIN","USER"))
		//.withUser(users.username("slaydep").password("voyadormir").roles("ADMIN","USER"))
		//.withUser(users.username("nuna").password("12345").roles("USER"));

	}
}
