@echo off
echo [INFO] Looking for current version
FOR /F "tokens=1,2 delims==" %%G IN (build/resources/main/META-INF/build-info.properties) DO (set %%G=%%H)
echo [INFO] Pushing image to docker
docker push gwynder/gwynder-central:%build.version%
docker push gwynder/gwynder-central:latest
echo [SUCCESS] Pushed images to docker