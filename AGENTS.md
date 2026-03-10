# Reglas para Agentes del Proyecto

## 1. Comandos de build y test
- Usar: ./mvnw clean install
- Ejecutar pruebas con: ./mvnw test
- No modificar el pom.xml sin justificación clara.

## 2. Convenciones de código
- Seguir principios SOLID.
- No mezclar lógica de negocio con infraestructura.
- Usar inyección de dependencias de Spring.
- Evitar lógica compleja en controladores o endpoints SOAP.

## 3. Patrones del proyecto
- Arquitectura en capas (controller/service/repository).
- Uso de interceptores para logging y auditoría.
- DTOs separados de entidades.
- Manejo de excepciones centralizado.

## 4. Restricciones importantes
- No crear nuevas clases sin necesidad real.
- No modificar endpoints existentes sin confirmación.
- Priorizar refactorización antes que reescritura.
- Mantener compatibilidad con SOAP existente.
