/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mongoConnection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Jesús
 */
public class ConexionMongoDB {
    private static final String url = "mongodb://localhost:27017";
    private static final String nombre_database = "EnviosBD";

    // Instancia estática para funcion singletone
    private static ConexionMongoDB instancia;

    private MongoClient mongoClient;
    private MongoDatabase database;

    // Constructor privado para evitar que otros módulos hagan uso de el
    private ConexionMongoDB() {
        try {
            mongoClient = MongoClients.create(url);
            database = mongoClient.getDatabase(nombre_database);
            System.out.println("Conexión exitosa a la base de datos: " + nombre_database);
        } catch (Exception e) {
            System.out.println("Error al conectar a MongoDB: " + e.getMessage());
        }
    }

    // Método estático para obtener la única instancia como punto de acceso global
    public static ConexionMongoDB getInstance() {
        if (instancia == null) {
            instancia = new ConexionMongoDB();
        }
        return instancia;
    }

    // Método para que los DAOs puedan obtener la base de datos
    public MongoDatabase getDatabase() {
        return database;
    }

    // Método para cerrar la conexión cuando se apague el sistema
    public void cerrar() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión cerrada");
        }
    }
}
