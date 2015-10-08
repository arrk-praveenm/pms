package com.arrkgroup.apps.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.InetOrgPersonContextMapper;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlStrategy;

@Configuration
@EnableWebMvcSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Value("${ldap.url}")
	private String URL;
	
	@Value("${ldap.manger.dn}")
	private String LDAP_MANAGER_DN;
	
	@Value("${ldap.manager.password}")
	private String LDAP_MANAGER_PASSWORD;
	
	@Bean
	public DatabaseAuthoritiesPopulator authoritiesPopulator(){
		DatabaseAuthoritiesPopulator authoritiesPopulator = new DatabaseAuthoritiesPopulator(contextSource(),"");
		return authoritiesPopulator;
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
	}
    

   @Bean
    public InetOrgPersonContextMapper extendingContextOfLdap() {
        return new InetOrgPersonContextMapper();
    }

    @Override
	protected void configure(AuthenticationManagerBuilder auth)	throws Exception {
    	auth.authenticationProvider(ldapAuthenticationProvider());
    	
		log.info("In Spring configuration configure()..............");
	}

    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(URL);
        contextSource.setUserDn(LDAP_MANAGER_DN);
        contextSource.setPassword(LDAP_MANAGER_PASSWORD);
        return contextSource;
    }
    
    @Bean
    public FilterBasedLdapUserSearch userSearch(){
    	FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch("","(uid={0})",contextSource());
    	userSearch.setSearchSubtree(true);
    	return userSearch;
    }

    @Bean
    public AuthenticationProvider ldapAuthenticationProvider() {
        LdapAuthenticationProvider provider = 
            new LdapAuthenticationProvider(ldapAuthenticator(),authoritiesPopulator());
        provider.setUserDetailsContextMapper(extendingContextOfLdap() );
        return provider;
    }

    @Bean
    public LdapAuthenticator ldapAuthenticator() {
        BindAuthenticator authenticator = new BindAuthenticator(contextSource());
        
        //authenticator.setUserDnPatterns(new String[] {"uid={0}" });
        authenticator.setUserSearch(userSearch());
        return authenticator;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http
            .authorizeRequests()
            	.antMatchers("/admin/**").access("hasRole('ADMIN')")
            	.antMatchers("/ajax/**").access("hasRole('EMPLOYEE')")
            	.antMatchers("/hr/**").access("hasRole('HR')")
            	.antMatchers("/assessor/**").access("hasAnyRole('ASSESSOR','MANAGER','EMPLOYEE')")
                .antMatchers( "/favicon.ico", "/resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login?error=1")
                .loginProcessingUrl("/authenticate")
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutSuccessUrl("/login?logout")
                .and() .sessionManagement()
               .maximumSessions(1)
              .expiredUrl("/login?logout")
              .and().sessionAuthenticationStrategy(getConcurrentSessionControlStrategy())
              .and().sessionManagement().sessionFixation().none()
              .and().csrf().disable();

            //.rememberMe()
                //.rememberMeServices(rememberMeServices())
                //.key("remember-me-key");
        
        
        
        
    }
    
    
    @SuppressWarnings("deprecation")
	protected ConcurrentSessionControlStrategy getConcurrentSessionControlStrategy()
    {
    	
		ConcurrentSessionControlStrategy sessionStartegy= new ConcurrentSessionControlStrategy(getSessionRegistryImpl());
    	sessionStartegy.setMaximumSessions(1);
    	return sessionStartegy;
    }
    protected SessionRegistryImpl getSessionRegistryImpl()
    {
    	return new SessionRegistryImpl();
    }
}