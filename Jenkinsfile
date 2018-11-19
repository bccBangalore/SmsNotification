
pipeline {
  agent any
   tools {
          maven 'Maven 3.0.5'
      }
  stages {
    stage('Build'){
      steps {
        sh 'mvn package -Dmaven.test.skip=true'
      }
    }
     stage('Copy'){
          steps {
            sh 'scp -i /var/lib/jenkins/users/sms/ssh/NotificationServerKey.pem -o StrictHostKeyChecking=no target/smsNotification-1.0-SNAPSHOT.jar ec2-user@100.26.112.118:/home/ec2-user/smsNotification'
          }
        }
    stage('Deploy'){
      steps {
            sh 'sudo ssh -i /var/lib/jenkins/users/sms/ssh/NotificationServerKey.pem -o StrictHostKeyChecking=no ec2-user@100.26.112.118 "/home/ec2-user/smsNotification/smsNotification.sh"'
      }
    }
  }
}

