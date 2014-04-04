#!/bin/sh
mysql --user=root mysql -e " create user 'ubuntu'@'localhost' identified by 'Vq9NvDXS'; grant all on *.* to 'ubuntu'@'localhost' ; " 

mysql -u ubuntu -pVq9NvDXS -e 'drop database if exists speech_dev ; create database speech_dev ;'

