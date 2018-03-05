package pl.pwn.reaktor.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private BCryptPasswordEncoder bCryotPasswordEncoder;
	
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication()
		.usersByUsernameQuery("SELECT mail, password, active FROM user where mail=?")
		.authoritiesByUsernameQuery(
				"SELECT u.mail, r.role FROM user u " 
				+"inner join role r on r.id=u.role_id "
				+ "where u.mail=?"
				)
		.dataSource(dataSource)
		.passwordEncoder(bCryotPasswordEncoder);

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http.authorizeRequests()
			.antMatchers("/admin/*").hasAuthority("Admin")
			.antMatchers("/post/artice").hasAnyAuthority("User","Admin")
			.and()
			.formLogin().loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/")
			.usernameParameter("mail")
			.passwordParameter("password")
			.and()
			.logout().logoutUrl("/logout").logoutSuccessUrl("/");
	}
}
