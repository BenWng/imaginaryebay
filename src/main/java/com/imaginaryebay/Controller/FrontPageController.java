package com.imaginaryebay.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ben_Big on 7/1/16.
 */

@Controller
@RequestMapping(value="/")

public interface FrontPageController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getFirstPage();

    @RequestMapping(value="/app/login" , method=RequestMethod.GET)
    public ModelAndView getLoginPage();

    @RequestMapping(value="/app/shop/{id}", method=RequestMethod.GET)
    public ModelAndView getShopPage();

    @RequestMapping(value="/app/item/{id}", method=RequestMethod.GET)
    public ModelAndView getItemPage();

    @RequestMapping(value = "/app/search", method=RequestMethod.GET)
    public ModelAndView getSearchResult();

    @RequestMapping(value = "/app/admin", method=RequestMethod.GET)
    public ModelAndView getAdminPage();

    @RequestMapping(value = "/app/item/create", method=RequestMethod.GET)
    public ModelAndView getItemCreationPage();

    @RequestMapping(value = "/app/item/{id}/update", method=RequestMethod.GET)
    public ModelAndView getItemUpdatePage();

    @RequestMapping(value = "/app/user/{id}/update", method=RequestMethod.GET)
    public ModelAndView getUserUpdatePage();

    @RequestMapping(value = "/app/profile/{userID}", method=RequestMethod.GET)
    public ModelAndView getProfilePage();

    @RequestMapping(value = "/app/contact", method=RequestMethod.GET)
    public ModelAndView getContactPage();

    @RequestMapping(value = "/login", method=RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public String handleLogout();
}