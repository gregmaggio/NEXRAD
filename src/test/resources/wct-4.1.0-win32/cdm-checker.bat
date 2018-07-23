
@echo off
java -mx3500m -classpath dist\wct-4.1.0.jar gov.noaa.ncdc.wct.decoders.cdm.CheckCDMFeatureType %*
        