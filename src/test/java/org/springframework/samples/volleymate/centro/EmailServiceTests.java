package org.springframework.samples.volleymate.centro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.volleymate.jugador.EmailService;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.junit.jupiter.api.Test;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class), properties = {
	"spring.jpa.database-platform: org.hibernate.dialect.H2Dialect" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EmailServiceTests {

	@Autowired
    private EmailService emailService;

    // @Test
    // public void testSendEmail(){
    //     assertTrue(this.emailService.aceptarSolicitudEmail("alejcn01@gmail.com"));        
    // }
    
    
}
