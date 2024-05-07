-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2024-05-02 14:32:34.467

-- tables
-- Table: category
CREATE TABLE category (
                          id serial  NOT NULL,
                          name varchar(255)  NOT NULL,
                          CONSTRAINT category_pk PRIMARY KEY (id)
);

-- Table: channel
CREATE TABLE channel (
                         id serial  NOT NULL,
                         title varchar(2559)  NOT NULL,
                         url varchar(2559)  NOT NULL,
                         CONSTRAINT channel_pk PRIMARY KEY (id)
);

-- Table: item
CREATE TABLE item (
                      id serial  NOT NULL,
                      channel_id serial  NOT NULL,
                      title varchar(2559)  NOT NULL,
                      link varchar(2559)  NOT NULL,
                      guid varchar(2559)  NULL,
                      pub_date timestamp  NOT NULL,
                      description varchar  NULL,
                      source varchar(2559)  NOT NULL,
                      author varchar(2559)  NOT NULL,
                      response varchar(2559)  NULL,
                      CONSTRAINT item_pk PRIMARY KEY (id)
);

-- Table: item_category
CREATE TABLE item_category (
                               id serial  NOT NULL,
                               item_id serial  NOT NULL,
                               category_id serial  NOT NULL,
                               CONSTRAINT item_category_pk PRIMARY KEY (id)
);

-- Table: media
CREATE TABLE media (
                       id serial  NOT NULL,
                       item_id serial  NOT NULL,
                       url varchar(2559)  NOT NULL,
                       medium varchar(255)  NULL,
                       type varchar(255)  NULL,
                       width int  NULL,
                       height int  NULL,
                       CONSTRAINT media_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: item_category_category (table: item_category)
ALTER TABLE item_category ADD CONSTRAINT item_category_category
    FOREIGN KEY (category_id)
        REFERENCES category (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: item_category_item (table: item_category)
ALTER TABLE item_category ADD CONSTRAINT item_category_item
    FOREIGN KEY (item_id)
        REFERENCES item (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: item_channel (table: item)
ALTER TABLE item ADD CONSTRAINT item_channel
    FOREIGN KEY (channel_id)
        REFERENCES channel (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: media_item (table: media)
ALTER TABLE media ADD CONSTRAINT media_item
    FOREIGN KEY (item_id)
        REFERENCES item (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- End of file.

