/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import entidades.Paquete;
import mongoConnection.ConexionMongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author Jesús
 */
public class PaqueteDAO implements IPaqueteDAO {
    private final MongoCollection<Document> coleccionPaquetes;

    public PaqueteDAO() {
        MongoDatabase baseDatos = ConexionMongoDB.getInstance().getDatabase();
        // Si quieres guardar los paquetes en una colección separada en lugar de anidarlos
        this.coleccionPaquetes = baseDatos.getCollection("paquetes");
    }

    @Override
    public boolean insertarPaquete(Paquete p) {
        try {
            Document docPaquete = new Document("alto", p.getAlto())
                    .append("largo", p.getLargo())
                    .append("ancho", p.getAncho())
                    .append("peso", p.getPeso())
                    .append("descripcion", p.getDescripcion());
                    
            coleccionPaquetes.insertOne(docPaquete);
            return true;
        } catch (Exception ex) {
            System.err.println("Error al registrar paquete en BD: " + ex.getMessage());
            return false;
        }
    }
}
