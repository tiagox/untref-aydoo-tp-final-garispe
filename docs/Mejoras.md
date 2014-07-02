# Análisis de _performance_.

## Comentarios generales sobre el código.

* La implementación tiene un limite y no procesa archivos mayores a ~60.000 registros.
    * El problema principal de la limitación de procesamiento, es que todos los registros se guardan en memoria, eso hace que la memoria que consume el programa sea casi igual que el peso de los archivos.
* Hay errores si no se indica un segundo argumento cuando se ejecuta por linea de comando.
* Las listas de registros se recorren muchas veces, al menos una por cada dato que se quiere calcular.
* Habría Mejorar el uso de convención de nombres en Java.
* Hay muchos métodos y datos que no son necesarios para poder calcular lo que se requiere.
* Se commitearon muchas cosas que no deberían estar en el repositorio, para eso se usa el archivo .gitignore.

## Pruebas sobre la aplicación.

Antes de hacer las pruebas y medir los tiempos, se limpió el repositorio y se corrigieron algunos errores como ser la recepción de argumentos por linea de comando. Además, se corrigió la tarea de Ant que sirve para empaquetar el proyecto en formato Jar.

Para la medición de los tiempos se utilizó el comando `time` de Unix.

### 1° Prueba.

#### Archivos de prueba:

* 1 archivo Zip que contiene 1 archivo CSV con 1.000.000 registros.

#### Resultado:

Durante la ejecución de esta prueba, luego de un breve periodo, aparece el siguiente error:

```text
java.lang.OutOfMemoryError: GC overhead limit exceeded
```

Este es un error esperable, ya que, esta limitación de memoria era conocida. Por eso, de ahora en más se asigna más memoria a la JVM para poder correr la aplicación.

Al correr nuevamente la aplicación:

```shell
$ time java -Xms1024m -Xmx1024m -XX:MaxPermSize=512m -jar build/dist/procesador.jar resources_test
```

Luego de 30 minutos de ejecución no se obtienen respuesta alguna por lo que, se aborta la ejecución para probar con archivos más chicos.

### 2° Prueba.

#### Archivos de prueba.

* 1 archivo Zip que contiene 4 archivos CSV con ~3.100 registros cada uno.

#### Resultado

```
$ time java -Xms1024m -Xmx1024m -XX:MaxPermSize=512m -jar build/dist/procesador.jar resources_test
*** [Extraccion de Archivos finalizada] ***
java -Xms1024m -Xmx1024m -XX:MaxPermSize=512m -jar build/dist/procesador.jar   4.37s user 0.12s system 96% cpu 4.673 total
```

Tiempo total: __4.6 segundos__.

El tiempo obtenido parecería estar bien, pero no es representativo ya que los datos de prueba son muy chicos.

### 3° Prueba.

#### Archivos de prueba.

* 1 archivo Zip que contiene 1 archivo CSV con ~740.000 registros.

#### Resultado

La ejecución de esta prueba nunca finalizó, llego a unos __30 minutos__ de ejecución.

---

## Mejoras sobre el código.

Para retomar las pruebas se realizaron algunas mejoras de _performance_ en el código para optimizar el manejo de memoria.

* Se eliminaron todos los datos que no eran necesarios para el calculo de los datos pedidos.
* Se reescribieron algunos métodos que recorría una lista, y por cada elemento, volvían a recorrer esa misma lista nuevamente.

Con estas dos mejoras, se repitieron las pruebas.

### Prueba.

#### Archivos de prueba.

* 1 archivo Zip que contiene 1 archivo CSV con ~740.000 registros.

#### Resultado

```
$ time java -Xms1024m -Xmx1024m -XX:MaxPermSize=512m -jar build/dist/procesador.jar resources_test
*** [Extraccion de Archivos finalizada] ***
java -Xms1024m -Xmx1024m -XX:MaxPermSize=512m -jar build/dist/procesador.jar   4.04s user 0.65s system 95% cpu 4.936 total
```

Tiempo total: __5 segundos__.

### Prueba extendida.

#### Archivos de prueba.

* 3 archivo Zip que contiene 7 archivo CSV, un total de ~145 MB.

#### Resultado

```
$ time java -Xms1024m -Xmx1024m -XX:MaxPermSize=512m -jar build/dist/procesador.jar resources_heavy
*** [Extraccion de Archivos finalizada] ***
*** [Extraccion de Archivos finalizada] ***
*** [Extraccion de Archivos finalizada] ***
java -Xms1024m -Xmx1024m -XX:MaxPermSize=512m -jar build/dist/procesador.jar   12.56s user 1.95s system 67% cpu 21.448 total
```

Tiempo total: __21,5 segundos__.

Un tiempo bastante respetable teniendo en cuenta que antes este proceso no llegaba a finalizar.

## Propuesta de mejoras.

Más allá de los cambios realizados sobre el código, se podría mejorar aún más la utilización de la memoria y la _performance_ de los algoritmos.

* Evitar el almacenamiento de todos los recorridos en los archivos, en lugar de guardar una lista de recorridos, sería mejor ir sumarizando los datos necesarios a medida que se parsear un registro del CSV.
* En lugar de recorrer las listas de recorridos para generar cada uno de los datos, se podría tener los totales pre-calculados y trabajar sobre estos.
* Leer los registros directamente desde el archivo Zip, sin descomprimir al disco los CSV.

