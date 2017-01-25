-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE CLIENT(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    address_id INT NOT NULL,
    FOREIGN KEY (address_id) REFERENCES ADDRESS(id),
    PRIMARY KEY (id)
);

CREATE TABLE ADDRESS(
    id INT NOT NULL AUTO_INCREMENT,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(2) NOT NULL,
    PRIMARY KEY (id)
);
