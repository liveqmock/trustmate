#!/bin/sh 
kill -9 `cat < pwn.pid`
rm pwn.pid
