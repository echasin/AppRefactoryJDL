echo 
cd D:/github/apprefactoryJDL/src/main/resources/config/liquibase/changelog
copy 00000000000000_initial_schema.xml 00000000000000_initial_schema.bak
del *.xml
copy 00000000000000_initial_schema.bak 00000000000000_initial_schema.xml
cd D:/github/apprefactoryJDL/src/main/resources/config/liquibase
copy master.xml master.bak
del master.xml
copy master.bak master.xml
cd /
cd D:/github/apprefactoryJDL
jhipster import-jdl apprefactory.jh  --force