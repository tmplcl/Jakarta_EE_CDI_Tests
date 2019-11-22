FROM airhacks/glassfish
COPY ./target/stuffapp.war ${DEPLOYMENT_DIR}
