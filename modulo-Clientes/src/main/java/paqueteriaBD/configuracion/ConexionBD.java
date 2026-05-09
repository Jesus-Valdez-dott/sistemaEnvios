/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteriaBD.configuracion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionBD {

    /*
     URI de conexión a MongoDB
     */

    private static final String URI =
            "mongodb://localhost:27017";

    /*
     Nombre de la base de datos
     */

    private static final String NOMBRE_BD =
            "enviosbd";

    /*
     Cliente Mongo
     */

    private static MongoClient mongoClient;

    /*
     Método para obtener la base de datos
     */

    public static MongoDatabase obtenerConexion() {

        try {

            /*
             Crear conexión
             */

            mongoClient = MongoClients.create(URI);

            /*
             Obtener base de datos
             */

            MongoDatabase database =
                    mongoClient.getDatabase(NOMBRE_BD);

            System.out.println(
                    "Conexión exitosa a MongoDB"
            );

            return database;

        } catch (Exception ex) {

            System.out.println(
                    "Error de conexión: "
                    + ex.getMessage()
            );
        }

        return null;
    }

    /*
     Cerrar conexión
     */

    public static void cerrarConexion() {

        if (mongoClient != null) {

            mongoClient.close();

            System.out.println(
                    "Conexión cerrada"
            );
        }
    }
}