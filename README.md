Imaginary Ebay
===

Intellij Configuration
---------------------
1. DATABASE_URL    postgresql://postgres::@localhost:5432/test
2. AWS_SECRET       see below
3. AWS_PUBLIC       see below

The values of these two variables correspond to the credentials in Amazon Web Service, Security Credentials: IAM.
Please create a user in IAM and add the three variables in Intellij's Run/Debug Configuration under the
tab: Startup/Connection

Please also update the variable BUCKET in the file Models/S3FileUploader, the variable correspond to the BUCKET's name
you create in AWS S3.



To log in or log out:
----------
1. not store the cookies:

	curl -X POST -d username=butters@gmail.com -d password=Stotch http://localhost:8080/login 
	
2. store the cookies:
	
	curl -X POST -d username=butters@gmail.com -d password=Stotch -c ./cookies.txt http://localhost:8080/login

3. log out:

    curl -X GET -b cookies.txt http://localhost:8080/logout





User:
-----
#####REST Api:
1. create a new user: (no login is needed)

	curl -X POST -H "Content-Type:application/json" -d '{"password":"Testaburger", "name":"Wendy", "email":"wendy@gmail.com"}' -i  http://localhost:8080/user/new


2. update user with ID: (need to login first, and must be the user himself or an admin)

	curl -X PUT -H "Content-Type:application/json" -d '{"password":"Tucker", "name":"Craig", "email":"craig@gmail.com","address":"Bush"}' -i -b cookies.txt http://localhost:8080/user/2
	
	
3. return all user: (login is needed, and must be admin)

	curl -X GET -b cookies.txt http://localhost:8080/user
	
4. return user info based on name (login is needed, must be the user himself, or admin)

	curl -X GET -b cookies.txt http://localhost:8080/user/name/Wendy	


5. return user info based on email address (login is needed, must be the user himself, or admin)
	
	curl -X GET -b cookies.txt http://localhost:8080/user/email/eric@gmail.com/
	
	**The last / cannot be omitted

6. return all the items by a user with a specified ID

	curl -X GET http://localhost:8080/user/item/3
	

	
Bidding:
-----
#####REST Api:
1. create a new bidding:

	 curl -X POST http://localhost:8080/bidding/itemID/1/price/900
	 
	 (logged in user bids on item with id=2 with price:900)
	 (bid must be created before item's endtime, and price must be greater than highest bid. If
	 and item does not have a highest bid, the price must be greater than the item's original price.
	 User cannot bid on their own item. requires authentication.)

2. get bidding with bidding's ID:
	
	curl -X GET http://localhost:8080/bidding/3

3. get all the biddings by one user
	
	curl -X GET http://localhost:8080/bidding/userID/2

	(needs authentication - admin or bidder themselves)

4. get all the biddings on one item

	curl -X GET http://localhost:8080/bidding/itemID/1

5. get the highest bidding on one item

	curl -X GET http://localhost:8080/bidding/highest/itemID/1

6. get active auction items based on bidder

    curl -X GET http://localhost:8080/bidding/active/1

    (needs authentication - admin or bidder themselves)

7. get items from successful auctions based on bidder

    curl -X GET http://localhost:8080/bidding/successful/1

    (needs authentication - admin or bidder themselves)

8. the number of active auction items based on bidder

    curl -X GET http://localhost:8080/bidding/active/1/count

    (needs authentication - admin or bidder themselves)

9. the number of items from successful auctions based on bidder

    curl -X GET http://localhost:8080/bidding/successful/1/count

    (needs authentication - admin or bidder themselves)

10. get active auction items based on bidder with pagination (page number and page size)\

    curl -X GET http://localhost:8080/bidding/active/1/page/1/size/10

    (needs authentication - admin or bidder themselves)

    (would return the first 10 such items)

11. get items from successful auction based on bidder with pagination

    curl -X GET http://localhost:8080/bidding/successful/1/page/2/size/5

    (needs authentication - admin or bidder themselves)

    (would return items 6 to 10)


	
Message:
-----
#####REST Api:

1. get messages sent to user with user's ID (need to login first, and must be the user himself or an admin)
	
	curl -X GET -b cookies.txt http://localhost:8080/message/3

2. send an email to administrative account (do not need to be logged in user)
	
	curl -X POST -H "Content-Type: application/json" -d '{"emailAddress": "cooldude@gmail.com", "emailContent": "Yo dawg!", "name": "Peter"}' http://localhost:8080/contact


Item:
-----
#####Rest Api:

1. post a new item
   requires authentication, name, endtime and price are required, the endtime must be in the future and the price must be greater than 0)

    curl -X POST -H "Content-Type: application/json" -d '{ "name" : "watch", "endtime":"2017-04-04T00:00:00", "category": "Electronics", "price": "20.0"}' "http://localhost:8080/item"

2. get an item by its ID

    curl -X GET localhost:8080/item/1

3. get the categories of items the given seller sells, returns all categories if no seller ID is specified

    curl -X GET localhost:8080/item/sellercategories
    curl -X GET localhost:8080/item/sellercategories?sellerID=1

4. update an item by its ID,
   requires authentication the user must be the same as the item's owner, endtime and price must be valid (see requirements in posting an item)

    curl -X PUT -H "Content-Type: application/json" -d '{ "name" : "notwatch", "endtime":"2017-04-04T00:00:00", "category": "Electronics", "price": "20.0"}' "http://localhost:8080/item/1"

5. returns an HTTP response with the ItemPicture id, URL, and optionally the associated Item

    curl -X GET localhost:8080/item/1/picture

6. Returns an HTTP Response containing the status and string representing the uploaded image's URL.

    curl -X POST -F "file=@/path/to/image.jpg" localhost:8080/item/1/picture

7. returns the list of items that fits the input
    no parameters: returns all items
    cat parameter: returns all items of the given category (category must be a valid category name)
    keyword parameter: returns all items whose name matches or partially matches the given name
    sellerID parameter: returns all items sold by the user with the given id
    cat and keyword parameters: returns items that match both the cat and keyword parameters
    cat and sellerID parameters: returns items that match both the cat and sellerID parameters

      curl -X GET localhost:8080/item
      curl -X GET localhost:8080/item?cat=Clothes
      curl -X GET localhost:8080/item?keyword=card
      curl -X GET "localhost:8080/item?cat=Clothes&keyword=card"
      curl -X GET localhost:8080/item?sellerID=2
      curl -X GET "localhost:8080/item?cat=Clothes&sellerID=2"
      (with two parameters, make sure the  " " are there if you're using curl)

8. returns a list of items that fits the input using pagination
    (see description in 7. for parameter descriptions)

     curl -X GET localhost:8080/item/page/1/size/2

     (first 2 items that fit the input)

     curl -X GET localhost:8080/item/page/1/size/3?cat=Clothes

     (first 3 items that fit the input)

     curl -X GET localhost:8080/item/page/2/size/10?keyword=card

     (second page of size 10 that fit the input, so items 11 to 20)

     curl -X GET "localhost:8080/item/page/2/size/2?cat=Clothes&keyword=card"

     (second page of size 2 that fit the input, so items 3 to 4)

     curl -X GET localhost:8080/item/page/1/size/4?sellerID=2

     (first 4 items that fit the input)

     curl -X GET "localhost:8080/item/page/2/size/1?cat=Clothes&sellerID=2"

     (second item that fits the input)


9. returns the number of items that fits the input
    (see description in 7. for parameter descriptions)

     curl -X GET localhost:8080/item/count
     curl -X GET localhost:8080/item/count?cat=Clothes
     curl -X GET localhost:8080/item/count?keyword=card
     curl -X GET "localhost:8080/item/count?cat=Clothes&keyword=card"
     curl -X GET localhost:8080/item/count?sellerID=2
     curl -X GET "localhost:8080/item/count?cat=Clothes&sellerID=2"

10. return the highest bid for the item with the given ID

    curl -X GET localhost:8080/item/highestbid/1

11. get three random pics of items the given seller is selling

    curl -X GET localhost:8080/item/randompics/2

    (if the seller has fewer than 3 pictures of items, it returns whatever pictures the seller has)