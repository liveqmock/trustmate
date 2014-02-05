#!/bin/sh

unset LANG

# LANG
##########################
LANG=ko_KR.utf8
export LANG

# JVM_ARGS for VM
##########################
JVM_ARGS="-Du=MONO_DAEMON -DTN_ENV=../conf -Dfile.encoding=utf-8"
JVM_ARGS="$JVM_ARGS -Xss512k -Xms128m -Xmx256m"
JVM_ARGS="$JVM_ARGS -cp ../lib/log4j-1.2.15.jar:../lib/tn_common-1.0.jar:../lib/jdbcpool-0.99.jar:../lib/ojdbc14.jar:../lib/pg-ddl-1.0.jar:../lib/mono-daemon-1.0.jar:../lib/activation.jar:../lib/mail.jar"


cd /home/trustmate/MONO_DAEMON/bin
/usr/local/jdk1.5.0_22/bin/java $JVM_ARGS com.pgmate.daemon.Daemon DEMOTRNSCTN
