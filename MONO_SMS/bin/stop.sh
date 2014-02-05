#!/bin/sh 
kill -9 `cat < sms.pid`
rm sms.pid
