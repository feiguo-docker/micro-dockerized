node {
  def escapedBranchName = sh(script: "echo ${env.BRANCH_NAME} | tr [/] '-'", returnStdout: true).trim()
  def version = sh(script: "date -u +\"%Y%m%d.%H%M%S\"", returnStdout: true).trim()
  def appVersion = "${version}-${escapedBranchName}"

  stage('Checkout') {
    deleteDir()
    checkout scm
  }

  stage('Build') {
    sh "mvn -f application/addressbook/pom.xml versions:set -DnewVersion=${appVersion} -DgenerateBackupPoms=false"
    sh "make app-build"
  }

  stage('build docker') {
    sh "make EXECUTOR=${env.EXECUTOR_NUMBER} TAG=${appVersion} docker-build"
  }

  stage('setup db') {
    echo "done with flyway within the application"
  }

  stage('start application') {
    sh "docker ps -q --filter 'label=executor=${env.EXECUTOR_NUMBER}' | xargs -r docker stop"
    sh "make EXECUTOR=${env.EXECUTOR_NUMBER} TAG=${appVersion} docker-up docker-poll-app"
  }

  stage('integration-test') {
    sh "make EXECUTOR=${env.EXECUTOR_NUMBER} TAG=${appVersion} app-create-person app-read-person"
  }

  stage('stop application') {
    sh "make EXECUTOR=${env.EXECUTOR_NUMBER} TAG=${appVersion} docker-down"
  }
  stage('cleanup') {
    sh "docker ps -q --filter 'label=executor=${env.EXECUTOR_NUMBER}' | xargs -r docker stop | xargs -r docker rm --volumes"
  }
}