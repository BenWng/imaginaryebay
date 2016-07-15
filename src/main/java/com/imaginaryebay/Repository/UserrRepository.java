package com.imaginaryebay.Repository;
import com.imaginaryebay.Models.Userr;
import org.springframework.stereotype.Component;

/**
 * Created by Ben_Big on 6/27/16.
 */
@Component
public interface UserrRepository {

    public void createNewUserr(Userr userr);

    public Userr getUserrByID(Long id);

    public Userr getUserrByEmail(String email);

}
