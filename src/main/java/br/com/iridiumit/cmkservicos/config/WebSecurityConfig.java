package br.com.iridiumit.cmkservicos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/administracao/**").hasAnyRole("CMK_ADMIN")
				.antMatchers("/chamados").hasAnyRole("CMK_ADMIN", "CMK_COORDENADOR","CMK_GESTOR","CMK_ANALISTA")
				.antMatchers("/chamados/**").hasAnyRole("CMK_ADMIN","CMK_COORDENADOR")
				.antMatchers("/relatorios/**").hasAnyRole("CMK_ADMIN","CMK_COORDENADOR","CMK_GESTOR")
				.antMatchers("/atendimentos").hasAnyRole("CMK_ADMIN", "CMK_ANALISTA","CMK_COORDENADOR","CMK_GESTOR")
				.antMatchers("/atendimentos/**").hasAnyRole("CMK_ADMIN","CMK_ANALISTA","CMK_COORDENADOR","CMK_GESTOR", "CMK_TECNICO")
				.antMatchers("/tecnico").hasAnyRole("CMK_ADMIN","CMK_GESTOR", "CMK_TECNICO")
				.antMatchers("/tecnico/**").hasAnyRole("CMK_ADMIN","CMK_GESTOR", "CMK_TECNICO")
				.anyRequest()				
				.authenticated()
				.and()
			.formLogin() // Para colocar o formulário de login quando usamos Spring Security
				.loginPage("/login") // URL para o formulário de login
				.permitAll() // permissão de acesso para todos ao formulário de login
				.defaultSuccessUrl("/inicio", true)
				.and()
			.sessionManagement() // Controla a sessão
				.maximumSessions(1) // O número máximo de sessões simultaneas para o mesmo usuário
				.expiredUrl("/login"); // Chama a página escolhida no caso de exceder o nr. de acessos ao mesmo tempo
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**")
				.antMatchers("/images/**")
				.antMatchers("/css/**")
				.antMatchers("/js/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
