#!/bin/sh
mvn clean package && docker build -t org.tmplcl/stuffapp .
docker rm -f stuffapp || true && docker run -d -p 8080:8080 -p 4848:4848 --name stuffapp org.tmplcl/stuffapp 
