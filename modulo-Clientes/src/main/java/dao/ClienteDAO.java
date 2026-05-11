/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entidades.Cliente;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import entidades.Cliente;
import java.util.ArrayList;
import java.util.List;
import mongoConnection.ConexionMongoDB;
import org.bson.Document;
import org.bson.types.ObjectId;
/**
 *
 * @author Jesús
 */
public class ClienteDAO implements IClienteDAO {
    private final MongoCollection<Document> coleccionClientes;

    public ClienteDAO() {
        // Obtenemos la conexión desde tu Singleton
        MongoDatabase db = ConexionMongoDB.getInstance().getDatabase();
        this.coleccionClientes = db.getCollection("clientes");
    }

    @Override
    public boolean agregarCliente(Cliente c) {
        try {
            Document doc = new Document("nombre", c.getNombre())
                .append("telefono", c.getTelefono())
                .append("direccion", c.getDireccion())
                .append("rfc", c.getRfc());
            
            coleccionClientes.insertOne(doc);
            return true;
        } catch (Exception e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Cliente buscarCliente(String id) {
        try {
            // MongoDB usa ObjectId, lo convertimos desde el String que recibimos
            Document doc = coleccionClientes.find(eq("_id", new ObjectId(id))).first();
            
            if (doc == null) return null;
            
            return documentToEntity(doc);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        try {
            for (Document doc : coleccionClientes.find()) {
                lista.add(documentToEntity(doc));
            }
        } catch (Exception e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizarCliente(Cliente c) {
        try {
            coleccionClientes.updateOne(
                eq("_id", new ObjectId(c.getId_cliente())), 
                new Document("$set", new Document("nombre", c.getNombre())
                    .append("telefono", c.getTelefono())
                    .append("direccion", c.getDireccion())
                    .append("rfc", c.getRfc()))
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean eliminarCliente(String id) {
        try {
            return coleccionClientes.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método privado para no repetir código: convierte un Documento de Mongo a Entidad Cliente.
     */
    private Cliente documentToEntity(Document doc) {
        Cliente c = new Cliente();
        c.setId_cliente(doc.getObjectId("_id").toString());
        c.setNombre(doc.getString("nombre"));
        c.setTelefono(doc.getString("telefono"));
        c.setDireccion(doc.getString("direccion"));
        c.setRfc(doc.getString("rfc"));
        return c;
    }
}
