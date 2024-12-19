pipeline {
    agent any

    environment {
        // Define variables de entorno (modifica según tu configuración)
        MAVEN_HOME = 'C:\\Program Files\\apache-maven-3.8.7'
        SONAR_HOST_URL = 'http://localhost:9000'
       // SONAR_AUTH_TOKEN = credentials('sonar-auth-token') // Credenciales configuradas en Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                // Clona el código del repositorio
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                // Compila el proyecto Maven
                bat 'mvn clean compile'
            }
        }

        stage('Unit and Integration Tests') {
            steps {
                // Ejecuta pruebas unitarias y de integración
                bat "\"${MAVEN_HOME}\\bin\\mvn\" test verify"
            }
        }

        stage('Test Report') {
            steps {
                // Genera y publica informes de prueba
                junit '**/target/surefire-reports/*.xml'
            }
        }

        /*stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') { // Usa el nombre de tu configuración de SonarQube en Jenkins
                    bat "\"${MAVEN_HOME}\\bin\\mvn\" sonar:sonar -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_AUTH_TOKEN}"
                }
            }
        }*/

        stage('Package and Archive') {
            steps {
                // Empaqueta el proyecto y archiva el artefacto generado
                bat "\"${MAVEN_HOME}\\bin\\mvn\" package"
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
    }

   /* post {
        always {
            // Envío de correo con el resultado del pipeline
            mail to: 'lucasalso@hotmail.com',
                 subject: "Pipeline: ${currentBuild.fullDisplayName} - ${currentBuild.currentResult}",
                 body: """Pipeline result: ${currentBuild.currentResult}
                          Build URL: ${env.BUILD_URL}"""
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
        }
    }*/
}
