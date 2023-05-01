package org.springframework.samples.petclinic.jugador;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.volleymate.jugador.EmailService;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail(){
        assertAll(() -> this.emailService.aceptarSolicitudEmail("volleymate2023@gmail.com"));        
    }
    
    
}
