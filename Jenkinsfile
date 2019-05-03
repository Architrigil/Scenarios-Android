pipeline {
  agent any
  options {
    buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '10', daysToKeepStr: '', numToKeepStr: '10')
  }
  stages {
    stage('Debug - Build') {
      steps {
        sh './gradlew clean assembleDebug'
      }
      post {
        success {
          archiveArtifacts(fingerprint: true, artifacts: 'app/build/outputs/apk/debug/*.apk')
          withCredentials([string(credentialsId: 'confluenceSean', variable: 'USER')]) {
            sh '''
              curl -D- \
                -u $USER \
                -X POST \
                -H "X-Atlassian-Token: nocheck" \
                -F "file=@$(ls app/build/outputs/apk/debug/*.apk)" \
                -F "comment=Automatically uploaded from Jenkins" \
                -F "minorEdit=true" \
                https://rigilcorp.atlassian.net/wiki/rest/api/content/715816986/child/attachment
            '''
          }
        }
      }
    }
  }
}