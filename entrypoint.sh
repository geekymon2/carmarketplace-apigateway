source version.properties
echo "This is the entrypoint script executing jar file: $ARTIFACT_NAME"
echo "Image version: $IMAGE_VERSION"
java -jar "$ARTIFACT_NAME.jar"