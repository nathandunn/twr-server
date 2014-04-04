#!/bin/sh
filename=dump_`date +"%Y-%m-%d"`.sql
echo $filename
mysqldump --user='ubuntu' speech_prod -pVq9NvDXS > $filename 
