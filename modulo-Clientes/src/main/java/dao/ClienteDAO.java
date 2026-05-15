/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


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
        MongoDatabase db = ConexionMongoDB.getInstance().getDatabase();
        this.coleccionClientes = db.getCollection("clientes");
    }
 
    @Override
    public boolean agregarCliente(Cliente c) {
        try {
            // El telefono es el _id: evita duplicados automaticamente
            Document doc = new Document("_id", c.getTelefono())
                    .append("nombre", c.getNombre())
                    .append("telefono", c.getTelefono())
                    .append("direccion", c.getDireccion())
                    .append("rfc", c.getRfc());
 
            coleccionClientes.insertOne(doc);
            return true;
        } catch (com.mongodb.MongoWriteException e) {
            // Clave duplicada: ya existe un cliente con ese telefono
            System.err.println("Ya existe un cliente con ese telefono: " + c.getTelefono());
            return false;
        } catch (Exception e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }
 
    @Override
    public Cliente buscarCliente(String telefono) {
        try {
            // Ahora _id ES el telefono, sin conversion a ObjectId
            Document doc = coleccionClientes.find(eq("_id", telefono)).first();
            if (doc == null) return null;
            return documentToEntity(doc);
        } catch (Exception e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
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
                eq("_id", c.getId_cliente()), // id_cliente ahora es el telefono
                new Document("$set", new Document("nombre", c.getNombre())
                        .append("direccion", c.getDireccion())
                        .append("rfc", c.getRfc()))
            );
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }
 
    @Override
    public boolean eliminarCliente(String telefono) {
        try {
            return coleccionClientes.deleteOne(eq("_id", telefono)).getDeletedCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }
 
    private Cliente documentToEntity(Document doc) {
        Cliente c = new Cliente();
        // _id es String (telefono)
        c.setId_cliente(doc.getString("_id"));
        c.setNombre(doc.getString("nombre"));
        c.setTelefono(doc.getString("telefono"));
        c.setDireccion(doc.getString("direccion"));
        c.setRfc(doc.getString("rfc"));
        return c;
    }
}
