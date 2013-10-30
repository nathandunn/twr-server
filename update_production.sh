#!/bin/bash


grails clean 
grails war 

WEBAPPS=/usr/share/tomcat/webapps/
sudo service tomcat stop 
sudo rm -rf $WEBAPPS/twr* 
# have to be root . . .not just sudo service 
sudo cp target/twr-server-0.1.war /usr/share/tomcat/webapps/twr.war
sudo service tomcat start 


#sudo rm -rf $CA
#if [ -d "$WEBAPPS" ]; then
#    # Control will enter here if $DIRECTORY exists.
#    sudo cp target/nemo-0.1.war $WEBAPPS/staging.war
#fi





