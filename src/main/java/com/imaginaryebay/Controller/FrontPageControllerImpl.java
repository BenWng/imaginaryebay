package com.imaginaryebay.Controller;

/**
 * Created by Ben_Big on 7/1/16.
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;


public class FrontPageControllerImpl implements FrontPageController {

    @Override
    public ModelAndView getFirstPage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public ModelAndView getLoginPage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public ModelAndView getShopPage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public ModelAndView getSearchResult(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public ModelAndView getAdminPage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public ModelAndView getItemPage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public ModelAndView getItemCreationPage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public ModelAndView getItemUpdatePage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public ModelAndView getUserUpdatePage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public ModelAndView getProfilePage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public ModelAndView getContactPage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public String handleLogout(){
        return "{\"success\":1}";
    }


}
