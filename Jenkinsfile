pipeline {
    agent any

    environment {
        // Define variables de entorno (modifica según tu configuración)
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-17'
        MAVEN_HOME = 'C:\\Program Files\\apache-maven-3.8.7'
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_AUTH_TOKEN = credentials('jenkins-sonar') // Credenciales configuradas en Jenkins
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
                bat 'mvn test verify'
            }
        }

        stage('Test Report') {
            steps {
                // Genera y publica informes de prueba
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv(installationName: 'Sonar') {
                    // Ejecuta el análisis de SonarQube después de la compilación y pruebas
                    bat "mvn sonar:sonar"
                }
            }
        }

        stage('Package and Archive') {
            steps {
                // Empaqueta el proyecto y archiva el artefacto generado
                bat 'mvn package'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
    }

   /* post {
        always {
            // Envío de correo con el resultado del pipeline
            mail to: 'XXXX@gmail.com',
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
