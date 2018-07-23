
@echo off
set WCT_HOME=%~dp0
rem java -mx3500m -Djava.awt.headless=true -classpath "%WCT_HOME%dist\wct-4.1.0.jar" gov.noaa.ncdc.wct.export.WCTExportBatch %*
java -mx256m -Djava.awt.headless=true -classpath "%WCT_HOME%dist\wct-4.1.0.jar" gov.noaa.ncdc.wct.export.WCTExportBatch %*
