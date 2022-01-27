package com.webAdmin.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.webAdmin.jwt.JwtAuthenticationFilter;



@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userService;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// Get AuthenticationManager bean
		return super.authenticationManagerBean();
	}

	@Bean
	public  PasswordEncoder passwordEncoder() {
		// Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService) // Cung cáp userservice cho spring security
				.passwordEncoder(passwordEncoder()); // cung cấp password encoder
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/admin/**")
				.permitAll()
				.antMatchers("/adminPage/home")
				.hasAnyAuthority("ROLE_ADMIN")
				.antMatchers("/adminPage/accounts/*", "/adminPage/role/*", "/adminPage/categories/*", "/adminPage/subcategories/*",
						"/admin/products/*")
				.hasAnyAuthority("ROLE_ADMIN")
				.anyRequest().authenticated().and().formLogin().loginPage("/login")
				.permitAll().defaultSuccessUrl("/adminHomePage").and().rememberMe()
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)).key("webadmin").and().logout()
				.clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID","remember-me").logoutSuccessUrl("/login").and()
				.exceptionHandling().accessDeniedPage("/403");

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
