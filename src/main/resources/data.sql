insert into customers (id, addition, city, country_code, date, house_nr, name, street, zip_code)
values
(1, 'abc', 'enschede', '12', '1888-10-10', '234fs', 'john', 'street', 'dweda'),
(2, 'abc', 'enschede', '12', '1888-10-10', '234fs', 'john', 'street', 'dweda'),
(3, 'abc', 'enschede', '12', '1888-10-10', '234fs', 'john', 'street', 'dweda'),
(4, 'abc', 'enschede', '12', '1888-10-10', '234fs', 'john', 'street', 'dweda'),
(5, 'abc', 'enschede', '12', '1888-10-10', '234fs', 'john', 'street', 'dweda'),
(6, 'abc', 'enschede', '12', '1888-10-10', '234fs', 'john', 'street', 'dweda'),
(7, 'abc', 'enschede', '12', '1888-10-10', '234fs', 'john', 'street', 'dweda');

insert into recipients (id, addition, city, country_code, date, house_nr, name, street, zip_code)
values
(1, 'abc', 'hengelo', '126', '1990-10-10', '234fs', 'michael', 'street', 'dweda'),
(2, 'abc', 'hengelo', '126', '1990-10-10', '234fs', 'michael', 'street', 'dweda'),
(3, 'abc', 'hengelo', '126', '1990-10-10', '234fs', 'michael', 'street', 'dweda'),
(4, 'abc', 'hengelo', '126', '1990-10-10', '234fs', 'michael', 'street', 'dweda'),
(5, 'abc', 'hengelo', '126', '1990-10-10', '234fs', 'michael', 'street', 'dweda'),
(6, 'abc', 'hengelo', '126', '1990-10-10', '234fs', 'michael', 'street', 'dweda'),
(7, 'abc', 'hengelo', '126', '1990-10-10', '234fs', 'michael', 'street', 'dweda');

insert into orders (id, artnr, date, description, nr_pallets, status, customer_id, recipient_id)
values
(1, 'ewws', '1999-10-10', 'description', 2, 'PENDING', 1, 2),
(2, 'ewws', '1999-10-10', 'description', 1, 'PENDING',  1, 2),
(3, 'ewws', '1999-10-10', 'description', 7, 'PENDING', 1, 2),
(4, 'ewws', '1999-10-10', 'description', 5, 'PENDING',  1, 2);
