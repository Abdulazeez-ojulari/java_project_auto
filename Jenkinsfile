pipeline {
  agent any
  stages {
    stage("build") {
      steps {
        sh """
          docker build -t AfriPay-docker:latest .
        """
      }
    }
    stage("remove-old") {
      steps {
        script {
         if ("${FIRST_BUILD}" == "true") {
                echo 'Running first build ignoring removal'
            } else {
                sh 'docker rm -f AfriPay'
            }
        }
      }
    }
    stage("run") {
      steps {
        sh """
          docker run -d --restart always -p ${EXPOSE_PORT}:8080 \
          -e TZ=Africa/Lagos \
          -e SPRING_PROFILES_ACTIVE=${ACTIVE_PROFILE} \
          -e SPRING_CONFIG_IMPORT=${CONFIG_SERVER} \
          -e SPRING_APPLICATION_NAME=${APPLICATION_NAME} \
          --name AfriPay AfriPay-docker
        """
      }
    }
  }
}