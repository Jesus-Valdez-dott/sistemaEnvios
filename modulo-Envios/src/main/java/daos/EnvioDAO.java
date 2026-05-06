/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import Enums.EstadoEnvio;
import entidades.Paquete;
import entidades.Envio;
import mongoConnection.ConexionMongoDB;
import java.util.List;
import java.util.ArrayList;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import org.bson.Document;
import org.bson.types.ObjectId;
/**
 *
 * @author Jesús
 */
public class EnvioDAO implements IEnvioDAO{
    private final MongoCollection<Document> coleccionEnvios;

    public EnvioDAO() {
        //Se Obtiene la base de datos Singleton
        MongoDatabase baseDatos = ConexionMongoDB.getInstance().getDatabase();
        //Se establece la conexion a la colección Envios
        this.coleccionEnvios = baseDatos.getCollection("envios");
    }

    @Override
    public boolean registrarEnvio(Envio e) {
        try {
            //Se crea una lista de documentos para los paquetes anidados
            List<Document> docsPaquetes = new ArrayList<>();
            if (e.getPaquetes()!= null) {
                for (Paquete p : e.getPaquetes()) {
                    Document docPaquete = new Document("alto", p.getAlto())
                            .append("largo", p.getLargo())
                            .append("ancho", p.getAncho())
                            .append("peso", p.getPeso())
                            .append("descripcion", p.getDescripcion());
                    docsPaquetes.add(docPaquete);
                }
            }

            // Armamos el documento principal del envío
            Document docEnvio = new Document("codigo_rastreo", e.getCodigo_rastreo())
                    .append("fecha_envio", e.getFecha_envio())
                    .append("estado", e.getEstado().name()) //Se guarda el Enum como texto
                    .append("nombre_destinatario", e.getNombre_destinatario())
                    .append("direccion_destino", e.getDireccion_destino())
                    .append("paquetes", docsPaquetes); 

            //Se envia a la base de datos
            coleccionEnvios.insertOne(docEnvio);
            return true;
            
        } catch (Exception ex) {
            System.err.println("Error al registrar envío en BD: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizarEdo(String id_Envio, EstadoEnvio edo) {
        try {
            // Buscamos por ID convirtiendo el String a ObjectId
            ObjectId objectId = new ObjectId(id_Envio);
            
            //Se actualiza el campo estado
            coleccionEnvios.updateOne(
                eq("_id", objectId), 
                set("estado", edo.name())
            );
            return true;
        } catch (Exception ex) {
            System.err.println("Error al actualizar estado: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Envio obtenerDetalles(String id_Envio) {
        try {
            ObjectId objectId = new ObjectId(id_Envio);
            Document doc = coleccionEnvios.find(eq("_id", objectId)).first();
            
            if (doc != null) {
                Envio envio = new Envio();
                // Ojo: Esto es solo un ejemplo parcial. Tienes que extraer cada dato
                // del documento y hacerle set() a tu objeto Envio.
                envio.setCodigo_rastreo(doc.getString("codigo_rastreo"));
                envio.setNombre_destinatario(doc.getString("nombre_destinatario"));
                envio.setDireccion_destino(doc.getString("direccion_destino"));
                
                // Convertir el texto guardado de vuelta al Enum
                String estadoStr = doc.getString("estado");
                if(estadoStr != null) {
                    envio.setEstado(EstadoEnvio.valueOf(estadoStr));
                }
                return envio;
            }
            return null;
        } catch (Exception ex) {
            System.err.println("Error al buscar envío: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Envio> obtenerHistCliente(String id_Cliente) {
        // Aquí iría la lógica para buscar usando el filtro "eq("id_Cliente", id_Cliente)"
        // y recorrer el cursor de Mongo para armar tu lista de objetos Envio.
        return new ArrayList<>(); 
    }
}
