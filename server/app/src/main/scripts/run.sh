#!/bin/bash

TOP=`dirname $0`
cd $TOP
TOP=`pwd`

LOCAL_CLASSPATH=$CLASSPATH
for jar in $TOP/lib/*.jar; do
    LOCAL_CLASSPATH="$LOCAL_CLASSPATH:$jar"
done

java -cp $LOCAL_CLASSPATH uk.co.grahamcox.cpmmo.Application $@
