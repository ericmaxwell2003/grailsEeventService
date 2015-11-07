# mysql start / stop => mysql.server start|stop
#
# mysql connectivity => mysql -uroot
#

drop database if exists demo_test;
drop user 'demo_test'@'localhost';
create database demo_test;
create user 'demo_test'@'localhost' identified by 'demo_test';
use demo_test;
GRANT ALL ON demo_test.* TO 'demo_test'@'localhost';

drop database if exists demo_dev;
drop user 'demo_dev'@'localhost';
create database demo_dev;
create user 'demo_dev'@'localhost' identified by 'demo_dev';
use demo_dev;
GRANT ALL ON demo_dev.* TO 'demo_dev'@'localhost';
