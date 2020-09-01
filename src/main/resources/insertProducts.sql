INSERT INTO productdb.Brand(id, code, name) values(1, '001', 'Apple');
INSERT INTO productdb.Brand(id, code, name) values(2, '002', 'Microsoft');
INSERT INTO productdb.Brand(id, code, name) values(3, '003', 'Samsung');

INSERT INTO productdb.Category(category_id, name) values(1, 'Clothes');
INSERT INTO productdb.Category(category_id, name) values(2, 'Music');
INSERT INTO productdb.Category(category_id, name) values(3, 'Electronics');

INSERT INTO productdb.product(product_id,name,description,image_link,price,category_id, brand_id, seller_id)
VALUES (1,'t-shirts','this is t-shirts', 'clothes1.jpg',10, 1, 1, 8);
INSERT INTO productdb.product(product_id,name,description,image_link,price,category_id, brand_id, seller_id)
VALUES (2,'hoodies','this is hoodies', 'clothes2.jpg',20, 1, 1, 8);
INSERT INTO productdb.product(product_id,name,description,image_link,price,category_id, brand_id, seller_id)
VALUES (3,'pants','this is pants', 'clothes3.jpg',30, 1, 1, 8);
INSERT INTO productdb.product(product_id,name,description,image_link,price,category_id, brand_id, seller_id)
VALUES (5,'Notebook','this is a Notebook', 'electronic1.jpg',10, 3, 2, 8);
INSERT INTO productdb.product(product_id,name,description,image_link,price,category_id, brand_id, seller_id)
VALUES (6,'Macbook','this is a Macbook', 'electronic2.jpg',20, 3, 2, 8);
INSERT INTO productdb.product(product_id,name,description,image_link,price,category_id, brand_id, seller_id)
VALUES (7,'IPhone','this is an IPhone', 'electronic3.jpg',30, 3, 2, 8);
INSERT INTO productdb.product(product_id,name,description,image_link,price,category_id, brand_id, seller_id)
VALUES (8,'music1','this is a music1', 'music1.jpg',10, 2, 3, 8);
INSERT INTO productdb.product(product_id,name,description,image_link,price,category_id, brand_id, seller_id)
VALUES (9,'music2','this is a music2', 'music2.jpg',20, 2, 3, 8);
INSERT INTO productdb.product(product_id,name,description,image_link,price,category_id, brand_id, seller_id)
VALUES (10,'music3','this is a music3', 'music3.jpg',30, 2, 3, 8);

INSERT INTO productdb.productstock(stockId, product_product_id, quantity )
VALUES (1, 1, 10 );
INSERT INTO productdb.productstock(stockId, product_product_id, quantity )
VALUES (2, 2, 5 );
INSERT INTO productdb.productstock(stockId, product_product_id, quantity )
VALUES (3, 3, 15 );
INSERT INTO productdb.productstock(stockId, product_product_id, quantity )
VALUES (5, 5, 20 );
INSERT INTO productdb.productstock(stockId, product_product_id, quantity )
VALUES (6, 6, 200 );
INSERT INTO productdb.productstock(stockId, product_product_id, quantity )
VALUES (7, 7, 2 );
INSERT INTO productdb.productstock(stockId, product_product_id, quantity )
VALUES (8, 8, 12 );
INSERT INTO productdb.productstock(stockId, product_product_id, quantity )
VALUES (9, 9, 120 );
INSERT INTO productdb.productstock(stockId, product_product_id, quantity )
VALUES (10, 10, 300 );

INSERT INTO productdb.productstocktxn(stockTxnId, product_product_id, quantity, sellerId, txnDate )
VALUES (1, 1, 10, 8, sysdate() );
INSERT INTO productdb.productstocktxn(stockTxnId, product_product_id, quantity, sellerId, txnDate )
VALUES (2, 2, 5, 8, sysdate() );
INSERT INTO productdb.productstocktxn(stockTxnId, product_product_id, quantity, sellerId, txnDate )
VALUES (3, 3, 15, 8, sysdate() );
INSERT INTO productdb.productstocktxn(stockTxnId, product_product_id, quantity, sellerId, txnDate )
VALUES (5, 5, 20, 8, sysdate() );
INSERT INTO productdb.productstocktxn(stockTxnId, product_product_id, quantity, sellerId, txnDate )
VALUES (6, 6, 200, 8, sysdate() );
INSERT INTO productdb.productstocktxn(stockTxnId, product_product_id, quantity, sellerId, txnDate )
VALUES (7, 7, 2, 8, sysdate() );
INSERT INTO productdb.productstocktxn(stockTxnId, product_product_id, quantity, sellerId, txnDate )
VALUES (8, 8, 12, 8, sysdate() );
INSERT INTO productdb.productstocktxn(stockTxnId, product_product_id, quantity, sellerId, txnDate )
VALUES (9, 9, 120, 8, sysdate() );
INSERT INTO productdb.productstocktxn(stockTxnId, product_product_id, quantity, sellerId, txnDate )
VALUES (10, 10, 300, 8, sysdate() );
