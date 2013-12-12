#!/bin/sh
filename=dump_passage`date +"%Y-%m-%d"`.sql
echo $filename
mysqldump --user='ubuntu' speech_prod passage -pVq9NvDXS > $filename 
