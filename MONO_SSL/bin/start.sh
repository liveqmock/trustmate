#!/bin/sh

unset LANG

# LANG
##########################
LANG=ko_KR.utf8
export LANG

# SET LIBRARY
for i in ../lib/*.jar; do
    CP=$CP:$i
done
CP=`echo $CP | cut -c2-`


# JVM_ARGS for VM
##########################
JVM_ARGS="-Du=MONO_SSL -DTN_ENV=../conf -Dfile.encoding=utf-8"
JVM_ARGS="$JVM_ARGS -Xss512k -Xms128m -Xmx256m"
JVM_ARGS="$JVM_ARGS -cp $CP"


java $JVM_ARGS com.pgmate.server.Service &
echo $!>pwd.pid
