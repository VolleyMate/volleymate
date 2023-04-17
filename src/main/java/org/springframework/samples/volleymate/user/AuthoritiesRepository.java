package org.springframework.samples.volleymate.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{

    @Query("SELECT a FROM Authorities a WHERE a.user=:user")
    public List<Authorities> findAuthoritiesByUser(User user);
	
}
