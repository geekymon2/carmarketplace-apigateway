FROM adoptopenjdk/openjdk11
LABEL maintainer="geekymon2@gmail.com"
ARG ARTIFACT_NAME
EXPOSE 8080
ADD target/$ARTIFACT_NAME*.jar $ARTIFACT_NAME.jar
ENTRYPOINT ["java","-jar","$ARTIFACT_NAME.jar"]