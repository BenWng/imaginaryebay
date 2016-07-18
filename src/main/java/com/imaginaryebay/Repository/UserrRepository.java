package com.imaginaryebay.Repository;
import com.imaginaryebay.Models.Userr;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Ben_Big on 6/27/16.
 */
public interface UserrRepository {

    public void createNewUserr(Userr userr);

    public Userr getUserrByID(Long id);

    public Userr getUserrByEmail(String email);

    public List<Userr> getAllUserrs();

    public List<Userr> getUserrByName(String name);

    public Userr updateUserrByID(long id, Userr u);

}
