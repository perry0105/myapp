node {
    stage('say hello') {
        echo 'Hello from myapp!'
        slackSend (color: '#FFFF00', message: "Jenkins build start...")
        slackSend (color: '#FFFF00', message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
    }
    
    stage('checkout') {
        checkout scm
    }

    // uncomment these 2 lines and edit the name 'node-4.6.0' according to what you choose in configuration
    // def nodeHome = tool name: 'node-4.6.0', type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation'
    // env.PATH = "${nodeHome}/bin:${env.PATH}"

    stage('check tools') {
        sh "node -v"
        sh "npm -v"
        sh "bower -v"
        sh "gulp -v"
    }

    stage('npm install') {
        sh "npm install"
    }

    stage('clean') {
        sh "./gradlew clean"
    }

    stage('backend tests') {
        try {
            sh "./gradlew test"
        } catch(err) {
            throw err
        } finally {
            step([$class: 'JUnitResultArchiver', testResults: '**/build/**/TEST-*.xml'])
        }
    }

    stage('frontend tests') {
        try {
            sh "gulp test"
        } catch(err) {
            throw err
        } finally {
            step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/karma/TESTS-*.xml'])
        }
    }

    stage('packaging') {
        sh "./gradlew bootRepackage -Pprod -x test"
    }

    stage('build result') {
        slackSend(message: "BUILD STATUS: ${buildStatus}")
    }
}
