package com.arrkgroup.apps.account;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import com.arrkgroup.apps.config.WebSecurityConfigurationAware;

public class UserAuthenticationIntegrationTest extends WebSecurityConfigurationAware {

    private static String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

    @Test
    public void requiresAuthentication() throws Exception {
        mockMvc.perform(get("/account/current"))
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void userAuthenticates() throws Exception {
        final String username = "praveenm";
        ResultMatcher matcher = new ResultMatcher() {
            public void match(MvcResult mvcResult) throws Exception {
                HttpSession session = mvcResult.getRequest().getSession();
                SecurityContext securityContext = (SecurityContext) session.getAttribute(SEC_CONTEXT_ATTR);
                Assert.assertEquals(securityContext.getAuthentication().getName(), username);
            }
        };
        mockMvc.perform(post("/authenticate").param("username", username).param("password", "praveenm123"))
                .andExpect(redirectedUrl("/"))
                .andExpect(matcher);
    }

    @Test
    public void userAuthenticationFails() throws Exception {
        final String username = "praveenm";
        mockMvc.perform(post("/authenticate").param("username", username).param("password", "invalid"))
                .andExpect(redirectedUrl("/login?error=1"))
                .andExpect(new ResultMatcher() {
                    public void match(MvcResult mvcResult) throws Exception {
                        HttpSession session = mvcResult.getRequest().getSession();
                        SecurityContext securityContext = (SecurityContext) session.getAttribute(SEC_CONTEXT_ATTR);
                        Assert.assertNull(securityContext);
                    }
                });
    }
}
