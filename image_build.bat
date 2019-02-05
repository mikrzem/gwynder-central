@echo off
echo [INFO] Building docker image
docker build -t gwynder/gwynder-central:latest .
echo [INFO] Looking for current version
FOR /F "tokens=1,2 delims==" %%G IN (build/resources/main/META-INF/build-info.properties) DO (set %%G=%%H)
echo [INFO] Excepted version %build.version%
docker tag gwynder/gwynder-central:latest gwynder/gwynder-central:%build.version%
echo [SUCCESS] Finished build
