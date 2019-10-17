package br.com.iridiumit.cmkservicos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.com.iridiumit.cmkservicos.security.cmkUserDetailsService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private cmkUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests()
				.antMatchers(
					"/resources/**",
	                "/registration**",
	                "/js/**",
	                "/css/**",
	                "/images/**",
	                "/webjars/**",
	                "/error**").permitAll()
				.antMatchers("/").hasAnyRole("CMK_ADMIN", "CMK_COORDENADOR", "CMK_ANALISTA", "CMK_GESTOR")
				.antMatchers("/administracao/**").hasAnyRole("CMK_ADMIN")
				.antMatchers("/relatorio/**").hasAnyRole("CMK_ADMIN","CMK_COORDENADOR","CMK_GESTOR")
				.antMatchers("/atendimentos**").hasAnyRole("CMK_ADMIN","CMK_ANALISTA","CMK_COORDENADOR","CMK_GESTOR")
				.anyRequest()
				.authenticated()
			.and()
			.formLogin()
				.loginPage("/entrar")
				.permitAll()
			.and()
			.logout()
				.logoutSuccessUrl("/entrar?logout")
				.permitAll()
			.and()
			.rememberMe()
				.userDetailsService(userDetailsService)
			.and()
			.exceptionHandling().accessDeniedPage("/acessonegado");
	}

}
