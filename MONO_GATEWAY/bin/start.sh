#!/bin/sh

unset LANG

# LANG
##########################
LANG=ko_KR.utf8
export LANG

# JVM_ARGS for VM
##########################
JVM_ARGS="-Du=MONO_GATEWAY -DTN_ENV=../conf -Dfile.encoding=utf-8"
JVM_ARGS="$JVM_ARGS -Xss512k -Xms128m -Xmx256m"
JVM_ARGS="$JVM_ARGS -cp :../lib/log4j-1.2.15.jar:../lib/tn_common-1.0.jar:../lib/pwn-gateway-1.0.jar"


java $JVM_ARGS com.pgmate.gateway.server.Service &
echo $!>pwn.pid
