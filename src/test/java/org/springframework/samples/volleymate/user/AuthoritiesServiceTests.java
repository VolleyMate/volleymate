package org.springframework.samples.volleymate.user;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
	"spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthoritiesServiceTests {

    @MockBean
    private JavaMailSender mailSender;
    
    @Autowired
    protected AuthoritiesService authoritiesService;

    protected UserService userService;


    @BeforeEach
    void setUp() {
        // ==== User1 ==== //
        User user1 = new User();
		user1.setUsername("Test1");
		user1.setPassword("123");
		user1.setEnabled(true);

        // ==== Authorities ==== //
		Authorities rol3 = new Authorities();
		rol3.setAuthority("jugador");
        userService.saveUser(user1);
        authoritiesService.saveAuthorities(user1.getUsername(), "jugador");
		user1.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol3)));
    }

    @Test
    public void shouldFindAuthoritiesByUser() {
        List<Authorities> lista = authoritiesService.findAuthoritiesByUser(userService.findUser("Test1").get());
        assertThat(lista).isNotNull();
        assertThat(lista.size()).isEqualTo(1);
    }

    @Test
    public void deleteAuthorities() {
        for( Authorities a : authoritiesService.findAuthoritiesByUser(userService.findUser("Test1").get())) {
            authoritiesService.deleteAuthorities(a);
        }
        List<Authorities> auth = authoritiesService.findAuthoritiesByUser(userService.findUser("Test1").get());
        assertThat(auth.size()).isEqualTo(0);
    }
}
