package ALD_Actividad_4.auth;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {
	@Bean

	public UserDetailsManager usersCustom(DataSource dataSource) {

		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.setUsersByUsernameQuery("select username,password,enabled from Usuarios u where username=?");
		users.setAuthoritiesByUsernameQuery("select u.username,p.nombre from Usuario_Perfiles up " +
				"inner join usuarios u on u.username = up.username " +
				"inner join perfiles p on p.id_perfil = up.id_perfil " +
				"where u.username = ?");

		return users;

	}

	// @Bean
	// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	// http
	// .csrf(csrf -> csrf.disable())
	// .authorizeHttpRequests(authorize -> authorize
	// .requestMatchers("static/**").permitAll()
	// .requestMatchers("/registro", "/", "/home", "/login", "/logout",
	// "/public/**").permitAll()
	// .requestMatchers("/admin/**").hasAuthority("ROLE_ADMON")
	// .requestMatchers("/clientes/**").hasAuthority("ROLE_CLIENTE")
	// .anyRequest().authenticated())
	// .formLogin(form -> form
	// .loginPage("/login")
	// .successHandler((request, response, authentication) -> {
	// String rol = authentication.getAuthorities().toString();
	// if (rol.contains("ROLE_ADMON")) {
	// response.sendRedirect("/admin/menu");
	// } else if (rol.contains("ROLE_CLIENTE")) {
	// response.sendRedirect("/clientes");
	// } else {
	// response.sendRedirect("/");
	// }
	// })
	// .permitAll());
	// return http.build();
	// }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("static/**").permitAll()
						.requestMatchers("/registro", "/", "/home", "/login", "/logout", "/public/**", "/anonimo/**").permitAll()
						.requestMatchers("/admin/**").hasAuthority("ROLE_ADMON")
						.requestMatchers("/cliente/**").hasAuthority("ROLE_CLIENTE")
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/login")
						.successHandler((request, response, authentication) -> {
							String rol = authentication.getAuthorities().toString();
							if (rol.contains("ROLE_ADMON")) {
								response.sendRedirect("/admin/menu");
							} else if (rol.contains("ROLE_CLIENTE")) {
								response.sendRedirect("/cliente/menu");
							} else {
								response.sendRedirect("/");
							}
						})
						.permitAll());
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * @Bean
	 * public InMemoryUserDetailsManager usersdetails() throws Exception{
	 * List<UserDetails> users=List.of(
	 * User
	 * .withUsername("user1")
	 * //.password("$2a$12$YUq1fO2Vbz.ONbIo./xmBeGCYFr5m4OLNC8H9HFafn4fpcOnUbqda")
	 * .password("{noop}user1")
	 * .roles("USERS")
	 * .build(),
	 * User
	 * .withUsername("user2")
	 * .password("{noop}user2")
	 * .roles("OPERATOR")
	 * .build(),
	 * User
	 * .withUsername("admin")
	 * .password("{noop}admin")
	 * .roles("USERS","ADMIN")
	 * .build());
	 * return new InMemoryUserDetailsManager(users);
	 * }
	 */

}
