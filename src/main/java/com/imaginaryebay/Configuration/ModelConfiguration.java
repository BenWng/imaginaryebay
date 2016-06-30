package com.imaginaryebay.Configuration;

import com.imaginaryebay.Controller.MessageRestController;
import com.imaginaryebay.Controller.ItemController;
import com.imaginaryebay.Controller.ItemControllerImpl;
import com.imaginaryebay.Controller.UserrRestController;
import com.imaginaryebay.DAO.MessageDao;
import com.imaginaryebay.DAO.MessageDaoImpl;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.DAO.ItemDAOImpl;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.DAO.UserrDaoImpl;
import com.imaginaryebay.Repository.MessageRepository;
import com.imaginaryebay.Repository.MessageRepositoryImpl;
import com.imaginaryebay.Repository.ItemRepository;
import com.imaginaryebay.Repository.ItemRepositoryImpl;
import com.imaginaryebay.Repository.UserrRepository;
import com.imaginaryebay.Repository.UserrRepositoryImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ben_Big on 6/27/16.
 */

@Configuration
public class ModelConfiguration {

    //All the bean dependencies use setter injection

    @Bean
    public UserrDao userrDao(){
        UserrDaoImpl bean = new UserrDaoImpl();
        return bean;
    }

    @Bean
    public UserrRepository userrRepository(){
        UserrRepositoryImpl bean=new UserrRepositoryImpl();
        bean.setUserrDao(userrDao());
        return bean;
    }

    @Bean
    public UserrRestController userrRestController(){
        UserrRestController bean=new UserrRestController();
        bean.setUserrRepository(userrRepository());
        return bean;
    }
    
    @Bean
    public MessageDao messageDao(){
        MessageDaoImpl bean = new MessageDaoImpl();
        return bean;
    }

    @Bean
    public MessageRepository messageRepository(){
        MessageRepositoryImpl bean=new MessageRepositoryImpl();
        bean.setMessageDao(messageDao());
        return bean;
    }

    public ItemDAO itemDAO() {
        ItemDAOImpl bean = new ItemDAOImpl();
        return bean;
    }

    @Bean
    public ItemRepository itemRepository(){
        ItemRepositoryImpl bean = new ItemRepositoryImpl();
        bean.setItemDAO(itemDAO());
        return bean;
    }

    @Bean
    public ItemController itemService() {
        ItemControllerImpl bean = new ItemControllerImpl();
        bean.setItemRepository(itemRepository());
        return bean;
    }

    @Bean
    public MessageRestController messageRestController(){
        MessageRestController bean=new MessageRestController();
        bean.setMessageRepository(messageRepository());
        return bean;
    }


}