#!/bin/sh

mvn clean install -f Submodules/Spring_Ext/pom.xml

mvn clean install -f Submodules/Jersey_Ext/pom.xml

mvn clean install -f Submodules/AppEngine_Common/pom.xml

mvn clean install -f AppEngine_DoWhatNow/pom.xml
