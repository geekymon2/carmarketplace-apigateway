FROM azul/zulu-openjdk:17-latest
LABEL maintainer="geekymon2@gmail.com"
ARG ARTIFACT_NAME
ARG IMAGE_VERSION
EXPOSE 8080
ADD target/${ARTIFACT_NAME}*.jar ${ARTIFACT_NAME}.jar
RUN printf "IMAGE_VERSION=${IMAGE_VERSION}" > version.properties
COPY entrypoint.sh ./entrypoint.sh
RUN chmod +x ./entrypoint.sh
ENTRYPOINT ["/bin/bash", "./entrypoint.sh"]