package com.stage.run.security;

import java.security.NoSuchAlgorithmException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.stage.run.repositories.UserRepository;

@Configuration
@EnableWebSecurity
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableGlobalMethodSecurity(securedEnabled = true,jsr250Enabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsServiceImpl userDetails;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        /*auth.inMemoryAuthentication()
        .passwordEncoder(passwordEncoder)
        .withUser("admin")
        .password(passwordEncoder.encode("123456"))
        .roles("ADMIN","SUPERADMIN");*/
		auth.userDetailsService(userDetails)
		.passwordEncoder(passwordEncoder());
		/*auth.jdbcAuthentication()
		.passwordEncoder(passwordEncoder)
		.dataSource(dataSource)
		.usersByUsernameQuery("select username as principal, password as credentials, id, description from user where username=?")
		//.authoritiesByUsernameQuery("select username as principal, role as role from users_role where username=?")
		.authoritiesByUsernameQuery("select u.username as principal, l.level as role from user u, level l, user_level lu where l.id=lu.level_id and u.id=lu.user_id and u.username=?")
		//.usersByUsernameQuery("select username , password , id , description from users where username=?")
		.rolePrefix("ROLE_");
		//.userDetailsService(userDetails);
		
		 */
	}
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.formLogin();
        http.authorizeRequests().antMatchers("/admin", "/admin/*").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/admin", "/admin/*").hasRole("SUPERADMIN");
        http.logout().logoutSuccessUrl("/").permitAll();
        http.exceptionHandling().accessDeniedPage("/403");
        http.csrf().disable();*/
    	http.exceptionHandling().accessDeniedPage("/403");
    	http.formLogin();
    	//http.authorizeRequests()
    	//.antMatchers("**/secured/**").authenticated().anyRequest().permitAll();
    	http.logout().logoutSuccessUrl("/");
    	//http.exceptionHandling().accessDeniedPage("/403");
    	http.csrf().disable();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new AppPasswordEncoder();
    }
}