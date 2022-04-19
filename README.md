
# Solución Challenge Cupón de compra 

## Información Funcional
Mercado Libre está implementando un nuevo beneficio para los usuarios que más usan la
plataforma con un cupón de cierto monto gratis que les permitirá comprar tantos ítems
marcados como favoritos que no excedan el monto total. Para esto se está analizando
construir una API que dado una lista de item_id y el monto total pueda darle la lista de ítems
que maximice el total gastado.


## Stack de la aplicación

Esta es la lista de frameworks y tecnologías definidas en la aplicación:

- Java 11
- Spring boot 2.6.6
- Spring Data JPA - ORM provider Hibernate
- Postgres SQL como base de datos productiva
- H2 como base de datos en memoria embebida para los test
- Spring Cache con Guava
- Spring Actuator
- Spring Validation
- Maven 3.6.0 o superior
- Tomcat server (embebido)
- Springdoc OpenApi-UI
- Lombok
- SpringBootTest
- Docker

## Ejecución
### Local
Para ejecutar el proyecto en forma local basta con ejecutar el siguiente comando:

```bash
mvn spring-boot:run
```

### Dockerizado
El proyecto cuenta con la posibilidad de ser levantado en un entorno containerizado.

Como requisito previo se requiere la instalación de [docker](https://docs.docker.com/install/).

Luego se deberán ejecutar los siguientes comandos:

```bash
docker build -t ${nombre_imagen} .
docker run -dp 8080:8080 ${nombre_imagen}
```

## Swagger
Utiliza la especificación Swagger mediante la implementación de OpenApi.
Link de Acceso local [API](http://localhost:8080/swagger-ui/index.html)

## Forma de uso
Dada una lista de item_ids y un monto total, recupera una lista de ítems que maximiza lo gastado
```bash
POST → /coupon/
Body:
{
"item_ids": [
        "MLA1125798595",
        "MLA610309020",
        "MLA921300131",
        "MLA921149309",
        "MLA1104122892"],
"amount": 10000
}
Response:
{
"item_ids": [
        "MLA1104122892",
        "MLA1125798595"
    ],
"total": 9698
}
```

Retorna el top 5 de ítems mas favoriteados

```bash
GET /coupon/stats

Response:
[
    {
        "id": "MLA1125798595",
        "quantity": 23
    },
    {
        "id": "MLA610309020",
        "quantity": 21
    },
    {
        "id": "MLA921300131",
        "quantity": 20
    },
    {
        "id": "MLA863684498",
        "quantity": 18
    },
    {
        "id": "MLA909004508",
        "quantity": 17
    }
]
```
## Readiness && Liveness
Para comprobar cuando la apliaciónes esta lista y el estado de salud se exponen los siguientes endpoinds    
```bash
GET /actuator/health/liveness
{
Response:
"status": "UP"
}
```

```bash
GET /actuator/health/readiness
{
Response:
"status": "UP"
}
```

## Hosting 
Para realizar pruebas la aplicación se encuentra hosteada en un cluster de Kubernentes.
Hay 2 replicas de la aplicación corriendo y un balanceador de carga que a traves de un servicio cluster IP deriva en los pods que atienden las solicitudes.


Dentro del Directorio .\src\k8s se encuentra los manifiestos utilizados para crear el deployment, service y balanceador de carga

La dirección IP del balancador de carga es: 146.190.1.29 (no se genero un DNS)

Se puede probar a través del swagger http://146.190.1.29/swagger-ui/index.html o utilizar insomnia, postman etc

## Autor
[Gerardo Martin Gonzalez Tulian](gerardo.gonzaleztulian@gmail.com)
Fecha: 19-Abril-2022.

