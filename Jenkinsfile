pipeline {
    agent any //include any jenkins  agent  to run the  project 

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "Maven"
    }

    stages {
        stage("Build Code") {
            steps{
            sh '''
            chmod +x build-microservices.sh
            ./build-microservices.sh
            '''
            }
        }   
   }
            
}