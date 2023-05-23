package org.springframework.samples.volleymate.user;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.jugador.JugadorService;
import org.springframework.samples.volleymate.jugador.Sexo;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
	"spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTests {
    
    @Autowired
    protected UserService userService;

    @Autowired
    protected JugadorService jugadorService;

    @Autowired
    protected AuthoritiesService authoritiesService;

    @BeforeEach
    void setUp() {
        // ==== User1 ==== //
        User user1 = new User();
		user1.setUsername("Test1");
		user1.setPassword("123");
		user1.setEnabled(true);

        // ==== Authorities ==== //
		Authorities rol1 = new Authorities();
		rol1.setAuthority("jugador");
		user1.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol1)));

        // ==== Jugador1 ==== //
		Jugador jugador1 = new Jugador();
		jugador1.setFirstName("Jugador1");
		jugador1.setLastName("Davis");
		jugador1.setUser(user1);
		jugador1.setVolleys(200);
		jugador1.setSexo(Sexo.MASCULINO);
		jugadorService.saveJugador(jugador1);
        userService.saveUser(user1);
        authoritiesService.saveAuthorities(user1.getUsername(), "jugador");
    }

    /*@Test
    public void shouldFindUser() {
        User user = userService.findUser("Test1").get();
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("Test1");
    }*/

    /*@Test
    public void shouldDeleteUser() {
        userService.deleteUser(userService.findUser("Test1").get());
        assertThat(userService.findUser("Test1")).isNull();
    }*/
}
