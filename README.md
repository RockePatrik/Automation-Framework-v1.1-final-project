# Framework de automatización para Magento

Este es un proyecto de automatización web creado para el curso de Selenium.

El proyecto prueba los flujos principales de cliente de una tienda Magento que se ejecuta localmente mediante Docker.

## Tecnologías

- Java 21
- Selenium WebDriver 4
- TestNG
- Maven
- Allure Reports
- Docker
- Page Object Model (Modelo de Objetos de Página)

## Pruebas automatizadas

El proyecto incluye 15 casos de prueba:

- 6 pruebas de registro
- 5 pruebas de inicio de sesión
- 4 pruebas de pedidos

Las pruebas abarcan escenarios tanto exitosos como negativos, tales como campos obligatorios, credenciales no válidas, correo electrónico duplicado, recuperación de contraseña, carrito de compras y proceso de pago (checkout). ## Estructura del proyecto

```text
src/test/java/com/store/
├── listeners/    Listener de TestNG y capturas de pantalla
├── pages/        Clases de Page Object
├── reports/      Utilidades de Allure
├── tests/        Pruebas de registro, inicio de sesión y pedidos
└── utils/        BaseTest y Retry
```

## Requisitos

Antes de ejecutar las pruebas, instala:

- Java 21
- Maven
- Docker Desktop
- Google Chrome (segun el caso del Browser)
- Allure Commandline

Magento debe estar disponible en:

```text
http://localhost:8080
```

## Iniciar Magento

Abre Git Bash en el proyecto de Magento:

```bash
cd ~/magento-testing-playground
docker compose -f docker-compose.student.yml up -d
docker ps
```

## Ejecutar las pruebas

Abre Git Bash en el proyecto de automatización:

```bash
cd ~/IdeaProjects/Automation-Framework-v1.1
mvn clean test
```

Resultado esperado:

```text
Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Suites de TestNG

Ejecutar la suite completa:

```bash
mvn clean test
```

Ejecutar una suite por grupo:

```bash
mvn clean test -DsuiteXmlFile=testng_functional.xml
mvn clean test -DsuiteXmlFile=testng_regression.xml
mvn clean test -DsuiteXmlFile=testng_integration.xml
```

## Informe de Allure

Tras la ejecución de las pruebas, ejecuta:

```bash
allure serve target/allure-results
```

El informe muestra los casos de prueba ejecutados, los pasos, los reintentos y las capturas de pantalla cuando una prueba falla.

## Configuración importante

El CAPTCHA y el límite de solicitudes de restablecimiento de contraseña se desactivaron únicamente en el entorno de pruebas local. Esto permite que la prueba de recuperación de contraseña se ejecute automáticamente.

Estas configuraciones de seguridad no deben desactivarse en un entorno de producción.

## Autor

Rocke Patricio Hernández Núñez