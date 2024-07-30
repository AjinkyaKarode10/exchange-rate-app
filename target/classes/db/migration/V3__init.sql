CREATE TABLE currency_exchange_rate (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  source_currency varchar(100) NOT NULL,
  target_currency varchar(50) NOT NULL,
  conversion_date date DEFAULT NULL,
  exchange_rate double,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;