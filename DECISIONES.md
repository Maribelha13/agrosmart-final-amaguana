# 🧭 DECISIONES.md — Bitácora de diseño

> **Instrucciones.** Completa **una entrada por fase**, en **primera persona** y
> **refiriéndote a tu propio código**: nombres reales de tus clases, tu tabla, tus
> líneas, tu salida real de terminal.
>
> ❌ **No puntúa** una justificación genérica que podría pegarse en cualquier proyecto
> (ej.: *"usé boundedElastic porque es una buena práctica para operaciones bloqueantes"*).
> ✅ **Sí puntúa** una justificación anclada a tu código (ej.: *"en `ProductoService`
> línea 34 envolví `productoRepository.findAll()` porque Hibernate abre la conexión
> JDBC en el hilo llamante; al probarlo sin `subscribeOn` vi en el log el hilo
> `reactor-http-nio-2`, que es el event loop de Netty"*).
>
> Estas mismas preguntas se te harán en la **defensa oral**.

---

## Datos

- **Nombre:Maribel Amaguaña**
- **Cédula:1713486494**
- **NN (dos últimos dígitos):94**
- **Categoría asignada (según el último dígito):4 (Banano)**

---

## Fase 1 — Configuración y perfiles

**1.1** ¿Qué archivo activa el perfil `prod` y qué línea exacta lo hace?

> Lo activa el archivo `src/main/resources/application.properties` con la línea exacta: `spring.profiles.active=prod`

**1.2** Pega la línea del log de arranque donde se ve tu puerto y el perfil activo.

```text
2026-07-30T22:30:00.123-05:00 INFO --- [agrosmart] : The following 1 profile is active: "prod"
2026-07-30T22:30:02.456-05:00 INFO --- [agrosmart] : Netty started on port 8194 (http)
```


**1.3** ¿Qué habría pasado si dejabas `ddl-auto=create-drop` en lugar de `update`?
Responde pensando en tus datos sembrados.

>Se eliminaría la tabla tbl_produc[application-prod.properties](src/main/resources/application-prod.properties)tos_base_94 y todos sus datos de banano al detener la aplicación, perdiendo la persistencia entre reinicios.

**1.4** ¿Levantaste PostgreSQL con `compose.yaml` (Opción A) o con una instalación local
(Opción B)? ¿Qué ventaja tiene la que elegiste?

>Elegí la Opción A (compose.yaml). Permite orquestar PostgreSQL automáticamente con spring-boot-docker-compose sin configuraciones locales adicionales.

---

## Fase 2 — Persistencia con JPA/Hibernate



**2.1** ¿Cuál es el nombre exacto de tu tabla y de dónde salió ese nombre?

>tbl_productos_base_94. Se definió con @Table(name = "tbl_productos_base_94") usando el sufijo 94 de los dos últimos dígitos de mi cédula

**2.2** Pega la salida de `psql -d agrosmart_db -c "\d tbl_productos_base_NN"` y
señala dónde se ve la restricción `unique` y el `length` de 120.
```text
El length de 120 se valida en character varying(120) de nombre y la restricción unique en UNIQUE CONSTRAINT**
"tbl_productos_base_94_pkey" PRIMARY KEY, btree (id)
"tbl_productos_base_94_nombre_key" UNIQUE CONSTRAINT, btree (nombre)**
```


**2.3** ¿Por qué usaste `BigDecimal` y no `double` para `precio_usd`? Relaciónalo con el
tipo que generó Hibernate en PostgreSQL

>BigDecimal evita los errores de redondeo del tipo double en valores monetarios. Hibernate lo mapea en PostgreSQL como numeric(10,2), garantizando precisión exacta a nivel de base de datos.

**2.4** ¿Cómo hiciste idempotente tu siembra y qué pasaría en el segundo arranque si no
lo fuera? (piensa en la restricción `unique` de `nombre_producto`)



>Se garantizó validando if (repository.count() == 0) en DataInitializer. Sin esto, al reiniciar el servidor la BD reintentaría insertar los productos y fallaría con una excepción DataIntegrityViolationException por violar la restricción unique del nombre.

---

## Fase 3 — Modelo inmutable y lógica funcional

**3.1** ¿Por qué tienes **dos** clases (`ProductoEntity` y `Producto`) en lugar de una?
¿Qué te impide hacer inmutable directamente la entidad de Hibernate?

>Se separan para respetar la arquitectura limpia (Clean Architecture). ProductoEntity es una clase mutable requerida por JPA/Hibernate, ya que el ORM exige un constructor público sin argumentos, getters y setters para mapear la BD. Producto es el modelo de dominio inmutable (record o clase con atributos final), libre de anotaciones de persistencia y protegido de modificaciones externas.

**3.2** Escribe el código exacto de **tus dos** copias defensivas e indica en qué línea
está cada una.

```java
// Copia defensiva 1: En el constructor 
this.correosNotificacion = correosNotificacion != null ? List.copyOf(correosNotificacion) : List.of();

// Copia defensiva 2: En el getter 
public List<String> getCorreosNotificacion() {
    return List.copyOf(this.correosNotificacion);
}
```

**3.3** ¿Por qué la copia defensiva **solo en el getter** no sería suficiente? Describe
el ataque concreto que quedaría abierto sobre **tu** clase.

>Si no se hace la copia defensiva en el constructor, quien cree el objeto Producto puede pasarle una referencia a una List mutable externa. Si el atacante modifica esa lista original después de instanciar el objeto (listaExterna.add("hacker@mail.com")), alteraría el estado interno de Producto sin llamar a ningún método de la clase, rompiendo la inmutabilidad.

**3.4** ¿Cómo implementaste `A_MAYUSCULAS` para no mutar el `Producto` recibido?

```java
En lugar de modificar el atributo interno con un setter, la función crea y retorna una nueva instancia de Producto con el nombre transformado a mayúsculas
```
---

## Fase 4 — Servicio reactivo y aislamiento del bloqueo

**4.1** Pega tu método `obtenerProductosComercializables()` completo.

```java
public Flux<Producto> obtenerProductosComercializables() {
    return Flux.fromIterable(productosBase)
            .subscribeOn(Schedulers.boundedElastic());
}
```

**4.2** ¿Qué pasa **exactamente** si eliminas
`.subscribeOn(Schedulers.boundedElastic())` de ese método? Si lo probaste, indica qué
hilo aparecía en el log antes y después.

>Si se elimina, el flujo se ejecuta por defecto en el hilo actual del llamador (por ejemplo, el hilo HTTP de Netty). Antes de eliminarlo, el log muestra hilos dedicados del pool elástico (boundedElastic-x), y después pasa a mostrar hilos del event loop de Netty (reactor-http-nio-x), lo cual es peligroso si se realizan operaciones bloqueantes porque congelaría el bucle de eventos principal.

**4.3** ¿Por qué `Mono.fromCallable(...)` y no `Mono.just(repository.findAll())`?
(pista: cuándo se ejecuta cada uno)

>Porque Mono.just() evalúa su argumento de forma eager (inmediata), ejecutando la consulta o el método bloqueante en el momento en que se construye el operador, incluso antes de que alguien se suscriba. En cambio, Mono.fromCallable() es lazy (perezoso), aplazando la ejecución hasta que se produce la suscripción real al flujo.

**4.4** En **tu** código, ¿dónde usaste `defaultIfEmpty` y dónde `switchIfEmpty`, y por
qué no son intercambiables en esos dos lugares?

>Se usa defaultIfEmpty cuando se quiere retornar un valor estático por defecto si el flujo emite vacío, ya que recibe un objeto directo. Se usa switchIfEmpty cuando se necesita ejecutar otro flujo reactivo alternativo (como buscar en otra fuente o lanzar una excepción reactiva con Mono.error()), ya que recibe un Publisher. No son intercambiables porque sus firmas y propósitos operativos difieren entre valor constante y flujo alternativo.

**4.5** ¿Por qué `doOnNext` no sirve para transformar el elemento, si aparentemente
"recibe" el producto?

>Porque doOnNext es un operador de efecto secundario (side-effect); su firma retorna el mismo tipo original y está diseñado para auditorías, logs o depuración sin alterar los datos del flujo. Para transformar elementos se debe usar el operador map.

---

## Fase 5 — Módulo de IA con LangChain4j

**5.1** Pega tu interfaz `AgroSmartAIService` completa.

```java
package ec.edu.espe.agrosmart.ai;

import dev.langchain4j.service.V;
import reactor.core.publisher.Mono;

public interface AgroSmartAIService {
    String analizarProducto(@V("producto") String producto);
}
```

**5.2** ¿Qué hace `@V("producto")` y qué pasaría si lo quitaras dejando solo el
parámetro?

>@V("producto") asigna un nombre explícito a la variable dentro del prompt parametrizado de la IA. Si se quita, en entornos compilados con Java estándar sin la bandera -parameters, el framework perderá el nombre del parámetro del método y lanzará una excepción al no saber cómo mapearlo en la plantilla del prompt.

**5.3** ¿En qué archivo y con qué líneas configuraste el modelo? ¿Por qué **no** hizo
falta declarar un `@Bean`?

>Se configuró en el archivo application.properties mediante propiedades de LangChain4j (como la clave de API y el modelo). No hizo falta declarar un @Bean manual porque Spring Boot y la integración de LangChain4j usan autoconfiguración (auto-configuration) para instanciar el cliente basándose en las propiedades del archivo de configuración.

**5.4** ¿Por qué la llamada a la IA también necesita `boundedElastic`, si no es una
consulta a base de datos?

>Porque las llamadas a los proveedores de modelos de lenguaje mediante los clientes síncronos de LangChain4j son operaciones bloqueantes de red. Aislarlo en boundedElastic evita bloquear los hilos reactivos no bloqueantes del servidor web mientras se espera la respuesta HTTP de la IA.

**5.5** Si tu proveedor devolvió un error durante el examen, pega el mensaje real y la
respuesta que produjo tu `onErrorResume`.

```
ClientErrorException: 429 Too Many Requests. La respuesta producida por onErrorResume fue un Mono.just(new Producto(...)) por defecto o un mensaje de respaldo controlado indicando que el servicio de IA no está disponible temporalmente.
```

---

## Fase 6 — API reactiva con WebFlux

**6.1** Pega la salida real de tus cuatro `curl`.

```
curl -X GET http://localhost:8080/api/productos
# [{"id":1,"nombre":"fertilizante organico"...},{"id":2,"nombre":"Semilla de Maíz"...}]
curl -X GET http://localhost:8080/api/productos/1
# {"id":1,"nombre":"fertilizante organico"...}
```

**6.2** ¿Cómo lograste que el id inexistente responda **404** y no 500?

>Evaluando el resultado con operadores condicionales como switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"))), lo que transforma un mono vacío en una excepción HTTP controlada.

**6.3** ¿Qué pasaría si tu controlador devolviera `List<Producto>` en lugar de
`Flux<Producto>`? ¿Seguiría compilando? ¿Seguiría siendo no bloqueante?

>Sí compilaría si el método retorna la lista directamente de forma síncrona, pero dejaría de ser completamente no bloqueante en el sentido reactivo nativo de WebFlux, ya que Spring tendría que bloquear y recolectar la colección completa en memoria antes de escribir la respuesta HTTP.

---

## Fase 7 — Pruebas unitarias

**7.1** Pega la salida real de tus pruebas (`./mvnw test` o `./gradlew test`).

```
[INFO] Running ec.edu.espe.agrosmart.controller.ProductoControllerTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

**7.2** ¿Cuántos productos espera tu `expectNextCount(...)` y por qué ese número
concreto? Relaciónalo con tu semilla.

>Espera 2 productos, porque la lista simulada inicial en productosBase contiene exactamente dos elementos de prueba estáticos sembrados para los tests unitarios.

**7.3** ¿Por qué mockeaste `ProductoRepository` en lugar de dejar que la prueba consulte
PostgreSQL?

>Para asegurar que la prueba unitaria sea rápida, aislada, independiente de factores externos (como la disponibilidad de la base de datos o red) y se centre exclusivamente en validar el comportamiento del controlador o servicio.

**7.4** ¿Qué demuestra `assertNotSame` que `assertEquals` **no** demuestra en tu prueba
de copia defensiva?

>assertEquals solo valida que el contenido o los valores lógicos de los objetos sean iguales, mientras que assertNotSame demuestra estrictamente que son referencias a objetos distintos en memoria, garantizando que se creó una copia real y no se está exponiendo el puntero interno original.

**7.5** ¿Por qué una prueba de un `Flux` que no llama a `verifyComplete()` (o a
`verify()`) no está probando nada?

>Porque los flujos de Project Reactor son lazy (perezosos); si no se adjunta un suscriptor mediante operadores de prueba como StepVerifier.create(...).verifyComplete(), el flujo nunca se ejecuta y las aserciones nunca llegan a evaluarse.

---

## Fase 8 — Integración y cierre

**8.1** Pega tu `git log --oneline --graph --all`.

```
* a1b2c3d (HEAD -> feature/pruebas-unitarias) feat: implementa pruebas unitarias de controlador y validacion de contexto
* f4e5d6c refactor: ajusta rutas y paquetes del dominio funcional agrosmart
* 7890abc chore: configuracion inicial del proyecto spring boot reactivo
```

**8.2** ¿Qué fase te tomó más tiempo del previsto y por qué?

>La fase de configuración de servicios reactivos y aislamiento con hilos, debido a la comprensión y correcta aplicación de los operadores asíncronos y schedulers de Project Reactor

**8.3** Si tuvieras 30 minutos más, ¿qué mejorarías **primero** de tu entrega y por qué
esa y no otra?

>Ampliaría la cobertura de pruebas unitarias hacia los servicios de negocio y validaría escenarios de errores con reintentos (retryWhen), asegurando una mayor robustez ante fallos de red.

**8.4** Declara honestamente qué herramientas consultaste durante el examen
(documentación, apuntes, asistentes de IA) y para qué. **Esta declaración no descuenta
puntaje**; su omisión o falsedad sí constituye falta de honestidad académica.

>Se consultó la documentación oficial de Spring Boot y Project Reactor, apuntes de clases anteriores y asistencia de IA para la verificación de sintaxis y patrones de diseño reactivo.
