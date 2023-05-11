package org.springframework.samples.volleymate.user;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
	"spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
public class UserServiceTests {

    @Autowired
    protected UserService userService;

    @BeforeEach
	void setup() {
		

		// ==== Jugador2 ==== //

		User user2 = new User();
		user2.setUsername("Test2");
		user2.setPassword("123");
		user2.setEnabled(true);
		user2.setCorreo("test@test.com");

		Authorities rol2 = new Authorities();
		rol2.setAuthority("jugador");
        user2.setAuthorities(new HashSet<Authorities>(Arrays.asList(rol2)));

        userService.saveUser(user2);

    }

    @Test
    public void shouldFindUserById(){
        Optional<User> user = userService.findUser("Test2");
        User usuario = user.get();
        assertThat(user).isNotNull();
        assertEquals(usuario.getUsername(), "Test2");
    }
    
}*/
