package com.imaginaryebay.Repository;

import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import com.imaginaryebay.Models.S3FileUploader;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Chloe on 6/28/16.
 */
@Transactional
public class ItemRepositoryImpl implements ItemRepository {

    private static final String FAIL_STEM           = "Unable to upload.";
    private static final String FAIL_EMPTY_FILES    = "Unable to upload. File is empty.";
    private static final String COLON_SEP           = ": ";
    private static final String UNCAUGHT_EXCEPTION  = "An uncaught exception was raised during upload.";

    private ItemDAO itemDAO;

    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void save(Item item) {
        this.itemDAO.persist(item);
    }

    public void update(Item item) {
        if (this.itemDAO.find(item) == null) {
            throw new RestException("Item to be updated does not exist.",
                    "Item with id " + item.getId() + " was not found");
        } else {
            this.itemDAO.merge(item);
        }
    }


    public Item findByID(Long id) {
        Item toRet = this.itemDAO.findByID(id);
        if (toRet == null) {
            // what should the number be here?
            throw new RestException("Item not found.",
                    "Item with id " + id + " was not found");
        } else {
            return toRet;
        }
    }

    // What if item doesn't have a price? Is price required?
    public Double findPriceByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            Double price = itemDAO.findPriceByID(id);
            if (price != null) {
                return price;
            }
            //figure out code
            throw new RestException("Item does not have a price.",
                    "Item with id " + id + " does not have a price");
        }
        //figure out code
        throw new RestException("Item not found.",
                "Item with id " + id + " was not found");
    }

    // Same comment as for price, is category required?
    public Category findCategoryByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            Category cat = itemDAO.findCategoryByID(id);
            if (cat != null) {
                return cat;
            }
            throw new RestException("Item does not have a category.",
                    "Item with id " + id + " does not have a category");

        }
        throw new RestException("Item not found.",
                "Item with id " + id + " was not found");
    }

    public Timestamp findEndtimeByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            Timestamp time = itemDAO.findEndtimeByID(id);
            if (time != null) {
                return time;
            }
            throw new RestException("Item does not have an endtime.",
                    "Item with id " + id + " does not have a endtime");

        }
        throw new RestException("Item not found.",
                "Item with id " + id + " was not found");
    }

    public String findDescriptionByID(Long id) {
        Item item = this.itemDAO.findByID(id);
        if (item != null) {
            String description = itemDAO.findDescriptionByID(id);
            if (description != null) {
                return description;
            }
            throw new RestException("Item does not have a description.",
                    "Item with id " + id + " does not have a description");

        }
        throw new RestException("Item not found.",
                "Item with id " + id + " was not found");
    }

    public Item updateItemByID(Long id, Item item) {
        Item current = this.itemDAO.findByID(id);
        if (current != null) {
            //See price comment
            return itemDAO.updateItemByID(id, item);
        }
        throw new RestException("Item not found.",
                "Item with id " + id + " was not found");
    }

    public List<Item> findAllItemsByCategory(Category category) {
        List<Item> toRet = this.itemDAO.findAllItemsByCategory(category);
        if (!toRet.isEmpty()) {
            return toRet;
        }
        throw new RestException("No items of that category.",
                "Items of category " + category + " were not found");
    }

    public List<Item> findAllItems() {
        List<Item> toRet = this.itemDAO.findAllItems();
        if (!toRet.isEmpty()) {
            return toRet;
        }
        throw new RestException("No items available.",
                "There are not items available.");

    }

    /**
     * TODO: @Brian: I'm returning the ResponseEntities through the repository interface. TODO:
     * Do we want to manage this here or move to Controller?
     **/
    public List<ItemPicture> returnItemPicturesForItem(Long id, String urlOnly) {

        List<ItemPicture> itemPictures;

        if (urlOnly.equalsIgnoreCase("true")) {
            itemPictures = itemDAO.returnAllItemPictureURLsForItemID(id);
        } else if (urlOnly.equalsIgnoreCase("false")) {
            itemPictures = itemDAO.returnAllItemPicturesForItemID(id);
        } else {
            throw new RestException("Invalid request parameter.",
                    "The supplied request parameter is invalid for this URL.",
                    HttpStatus.BAD_REQUEST);
        }
        if (itemPictures.isEmpty()) {
            throw new RestException("Not available.",
                    "There are no entries for the requested resource.",
                    HttpStatus.OK);
        }
        return itemPictures;
    }

    public String createItemPicturesForItem(Long id, MultipartFile[] files){

        String fileName = null;
        String imageURL = "";
        Item item = this.findByID(id);

        if (files != null && files.length > 0) {
            for (MultipartFile mpFile : files) {
                if (!mpFile.isEmpty()) {
                    try {
                        fileName = mpFile.getOriginalFilename();
                        S3FileUploader s3FileUploader = new S3FileUploader();
                        imageURL = s3FileUploader.fileUploader(mpFile);

                        if (imageURL == null) {
                            throw new RestException(FAIL_STEM, UNCAUGHT_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
                        } else {
                            item.addItemPicture(new ItemPicture(imageURL));
                            this.update(item);
                        }

                    } catch (Exception e) {
                        String message = FAIL_STEM + fileName + COLON_SEP + e.getMessage();
                        throw new RestException(FAIL_STEM, message, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
            return imageURL;
        } else {
            throw new RestException(FAIL_STEM, FAIL_EMPTY_FILES, HttpStatus.BAD_REQUEST);
        }
    }
}
