package org.springframework.samples.volleymate.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","/oups").permitAll()
				.antMatchers("/users/new").permitAll()
				.antMatchers("/session/**").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("admin")
				.antMatchers("/partidos/**").authenticated()
				.antMatchers("/jugadores/new").permitAll()
				.antMatchers("/chat/**").authenticated()
				.antMatchers("/jugadores/**").authenticated()
				.antMatchers("/valoraciones/**").authenticated()
				.antMatchers("/listaJugadores/**").authenticated()
				.antMatchers("/logout/**").authenticated()
				.antMatchers("/tienda/**").authenticated()
				.antMatchers("/pagos/**").authenticated()
				.antMatchers("/centros/**").authenticated()
        .antMatchers("/logro/**").authenticated()
				.antMatchers("/misAspectos/**").authenticated()
				.antMatchers("/terminos/**").permitAll()

				.anyRequest().denyAll()
				.and()
				 	.formLogin()
				 	/*.loginPage("/login")*/
				 	.failureUrl("/login-error")
					.successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                            response.sendRedirect("https://volleymate-wpl.ew.r.appspot.com");
                        }
                    })
				.and()
					.logout()
						.logoutSuccessUrl("https://volleymate-wpl.ew.r.appspot.com"); 
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select username,password,enabled "
	        + "from users "
	        + "where username = ?")
	      .authoritiesByUsernameQuery(
	       "select username, authority "
	        + "from authorities "
	        + "where username = ?")	      	      
	      .passwordEncoder(passwordEncoder());	
	}


	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}
	
}


