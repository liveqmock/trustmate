#!/bin/sh

unset LANG

# LANG
##########################
LANG=ko_KR.utf8
export LANG

# JVM_ARGS for VM
##########################
JVM_ARGS="-Du=SMS -DTN_ENV=../conf -Dfile.encoding=utf-8"
JVM_ARGS="$JVM_ARGS -Xss512k -Xms32m -Xmx64m"
JVM_ARGS="$JVM_ARGS -cp ../lib/jdbcpool-0.99.jar:../lib/log4j-1.2.14.jar:../lib/ojdbc14.jar:../lib/tn_common-1.0.jar:../lib/sms.jar"


java $JVM_ARGS com.pgmate.sms.server.SMSService &
echo $!>sms.pid
