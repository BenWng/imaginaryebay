package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Item;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ben_Big on 7/24/16.
 */

@Component
public class BiddingDAOImpl implements  BiddingDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void persist (Bidding bidding){
        entityManager.persist(bidding);
    }

    @Override
    public Bidding getBiddingByID(Long id){
        Bidding bidding=entityManager.find(Bidding.class,id);
        return bidding;
    }

    @Override
    public List<Bidding> getBiddingByUserrID (Long id){
        //Return all the Biddings made by one user
        String queryString= "SELECT b from Bidding b join b.userr u where u.id= :I";
        Query query=entityManager.createQuery(queryString);
        query.setParameter("I",id);
        return query.getResultList();
    }

    @Override
    public List<Bidding> getBiddingByItem (Item item){
        //Return all the Biddings on this item
        String queryString="SELECT b from Bidding b join b.item it where it= :IT";
        Query query=entityManager.createQuery(queryString);
        query.setParameter("IT",item);
        return query.getResultList();

    }

    @Override
    public List<Bidding> getBiddingByItemID (Long id){
        Query query = entityManager.createQuery("select b from Bidding b where b.item.id = ?1");
        Query query2 = query.setParameter(1,id);
        return query2.getResultList();
    }


    @Override
    public Bidding getHighestBiddingForItem (Long id){
        /*Item item = entityManager.find(Item.class, id);
        Query query = entityManager.createQuery("select b from Bidding b where b.price = ?1");
        Query query2 = query.setParameter(1, item.getPrice());
        List<Bidding> itemList= query2.getResultList();

        Iterator<Bidding> itr=itemList.iterator();
        while(itr.hasNext()){
            return itr.next();
        }
        return null;*/

        String queryString=
                "SELECT b from Bidding b join b.item it where it.id= :IT and b.price=(SELECT Max(b2.price) from Bidding b2 join b2.item it2 where it2.id= :IT2)";
        Query query=entityManager.createQuery(queryString);
        query.setParameter("IT",id);
        query.setParameter("IT2",id);
        List<Bidding> itemList= query.getResultList();

        Iterator<Bidding> itr=itemList.iterator();
        while(itr.hasNext()){
            return itr.next();
        }
        return null;
    }

    @Override
    public List<Item> getActiveBidItemsByBidder(Long bidderId) {
        return byActiveness(bidderId).getResultList();

    }

    @Override
    public List<Item> getSuccessfulBidItemsByBidder(Long bidderId) {
        return bySuccess(bidderId).getResultList();

    }

    @Override
    public List<Item> getActiveBidItemsByBidderByPage(Long bidderId, int pageNum, int pageSize) {
        return paginationHelper(byActiveness(bidderId), pageNum, pageSize);

    }

    @Override
    public List<Item> getSuccessfulBidItemsByBidderByPage(Long bidderId, int pageNum, int pageSize) {
        return paginationHelper(bySuccess(bidderId), pageNum, pageSize);

    }

    @Override
    public Integer getActiveBidItemsByBidderCount(Long bidderId) {
        return getActiveBidItemsByBidder(bidderId).size();

    }

    @Override
    public Integer getSuccessfulBidItemsByBidderCount(Long bidderId) {
        return getSuccessfulBidItemsByBidder(bidderId).size();

    }

    private Query bySuccess(Long bidderId){
        Query query = entityManager.createQuery("select b.item from Bidding b where b.item.endtime < ?1 and b.userr.id = ?2 and b.item.highestBid = b.price order by b.item.endtime desc");
        Timestamp currentTime = new Timestamp((new java.util.Date()).getTime());
        Query query2 = query.setParameter(1, currentTime);
        query2.setParameter(2, bidderId);
        return query2;
    }

    private Query byActiveness(Long bidderId){
        Query query = entityManager.createQuery("select distinct b.item from Bidding b where b.item.endtime > ?1 and b.userr.id = ?2 order by b.item.endtime desc");
        Timestamp currentTime = new Timestamp((new java.util.Date()).getTime());
        Query query2 = query.setParameter(1, currentTime);
        query2.setParameter(2, bidderId);
        return query2;
    }

    private List<Item> paginationHelper(Query query, int pageNum, int pageSize){
        query.setFirstResult((pageNum-1)*pageSize);
        query.setMaxResults(pageSize);
        List<Item> itemList=query.getResultList();
        return itemList;
    }



}