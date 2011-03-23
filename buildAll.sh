#!/bin/sh

mvn clean install -f Spring_Ext/pom.xml

mvn clean install -f Jersey_Ext/pom.xml

mvn clean install -f AppEngine_Common/pom.xml

mvn clean install -f AppEngine_DoWhatNow/pom.xml

mvn clean install -f AppEngine_Unifeed/pom.xml