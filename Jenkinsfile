node {
  def escapedBranchName = sh(script: "echo ${env.BRANCH_NAME} | tr [/] '-'", returnStdout: true).trim()
  def version = sh(script: "date -u +\"%Y%m%d.%H%M%S\"", returnStdout: true).trim()
  def appVersion = "${version}-${escapedBranchName}"

  stage('Checkout') {
    deleteDir()
    checkout scm
  }

  stage('Build') {
    echo "do build"
    sh "make "
  }
}
