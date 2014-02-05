#!/bin/sh

unset LANG

# LANG
##########################
LANG=ko_KR.utf8
export LANG

# JVM_ARGS for VM
##########################
JVM_ARGS="-Du=EPAYGEN_PAYMENT -DTN_ENV=../conf -Dfile.encoding=utf-8"
JVM_ARGS="$JVM_ARGS -Xss512k -Xms128m -Xmx256m"
JVM_ARGS="$JVM_ARGS -cp ../lib/log4j-1.2.15.jar:../lib/tn_common-1.0.jar:../lib/jdbcpool-0.99.jar:../lib/ojdbc14.jar:../lib/pg-ddl-1.0.jar:../lib/mono-payment-1.0.jar:../lib/bcprov-jdk14-134.jar:../lib/INICrypto_v3.1.7_signed.jar:../lib/INIpayJAVA.jar:../lib/xercesImpl.jar:/lib/sms.jar"


java $JVM_ARGS com.pgmate.payment.server.Service &
echo $!>pwd.pid
