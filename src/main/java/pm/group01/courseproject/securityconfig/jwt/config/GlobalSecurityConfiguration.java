package pm.group01.courseproject.securityconfig.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import pm.group01.courseproject.securityconfig.jwt.filters.JwtRequestFilter;
import pm.group01.courseproject.securityconfig.jwt.userdeatils.service.UserDetailsServiceImp;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@ComponentScan("pm.group01.courseproject.securityconfig.jwt")
public class GlobalSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImp userDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        try {
            auth.userDetailsService(userDetailsService);
        } catch (Exception e) {
            System.out.println(e + "End User not found");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/enduserlogin", "/adminlogin", "/bologin", "/sellerlogin").permitAll()
                .antMatchers(HttpMethod.POST, "/getId").permitAll()
                .antMatchers(HttpMethod.GET, "/reports/**").permitAll()
                .antMatchers(HttpMethod.GET, "/products/**", "/categories/**").permitAll()
                .antMatchers(HttpMethod.POST, "/enduser/signup").permitAll()
                .antMatchers(HttpMethod.GET, "/payment/**").permitAll()
                .antMatchers(HttpMethod.POST, "/payment/**").permitAll()
                .antMatchers(HttpMethod.GET, "/enduser/create/**").permitAll()
                .antMatchers(HttpMethod.GET, "/brandowner/create/**").permitAll()
                .antMatchers(HttpMethod.GET, "/administrator/create/**").permitAll()
                .antMatchers(HttpMethod.GET, "/seller/create/**").permitAll()
                .antMatchers(HttpMethod.POST, "/order/checkout/**").permitAll()
                .antMatchers(HttpMethod.GET, "/order/create/**").permitAll()
                .antMatchers(HttpMethod.POST, "/products/api/v1/createProduct/**").permitAll()
                .antMatchers(HttpMethod.GET, "/cartLine/api/v1/**/clear/**").permitAll()
                .antMatchers(HttpMethod.GET, "/enduser/**").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        httpSecurity.cors().configurationSource(request -> config);
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

}