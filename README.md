# Patrones Examen – Ferresin

Proyecto académico que implementa un sistema simple de descuentos para la ferretería Ferresin usando Java 17 y Spring Boot.  
Incluye descuentos por día/categoría, descuento para empleados (se aplica el mayor) y pruebas unitarias con JUnit 5 y Mockito.  
Se utilizaron los patrones Strategy (reglas de descuento) y Decorator (cálculo sin modificar la librería externa).

## Ejecutar
./gradlew bootRun  
http://localhost:8080/

Usuarios:  
- juan / 1234  
- andrea / 4321

## Pruebas
./gradlew test

