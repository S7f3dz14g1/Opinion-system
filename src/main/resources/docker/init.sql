CREATE TABLE IF NOT EXISTS Product(
                                      id  NUMERIC(6) NOT NULL PRIMARY KEY,
                                      name VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL,
    price NUMERIC(6,2)NOT NULL
    );

CREATE TABLE  IF NOT EXISTS  Opinion(
                                        id NUMERIC(6) NOT NULL PRIMARY KEY,
                                        product_id NUMERIC(6) NOT NULL,
    author VARCHAR(50) NOT NULL,
    content VARCHAR(200) NOT NULL ,
    created TIMESTAMP  NOT NULL ,
    stars NUMERIC (1),
    CONSTRAINT fk_product
    FOREIGN KEY(product_id)
    REFERENCES Product(id)
    );

INSERT INTO Product values(1,'Bread','Right out of the oven',3.5);
INSERT INTO Product values(2,'Laciate','Milk 3.2%',2.6);
INSERT INTO Opinion values(1,1,'Antek','Good bread',NOW(),5);
INSERT INTO Opinion values(2,1,'Mietek','I do not recommend',NOW(),2);