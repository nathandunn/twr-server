#filename=dump_`date +"%Y-%m-%d"`.sql
#!/bin/bash
mysql -u ubuntu -pVq9NvDXS -e 'drop database if exists speech_dev ; create database speech_dev ;'
#mysql -uubuntu -pVq9NvDXS speech_dev < dump_2014-04-17-postconvert.sql
#mysql -uubuntu -pVq9NvDXS speech_dev < dump_2014-05-05.sql
mysql -uubuntu -pVq9NvDXS speech_dev < dump_2014-05-07.sql
