/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import entidades.Empleado;
import entidades.Sucursal;
import mappers.SucursalesMapper;
import mongoConnection.ConexionMongoDB;
import org.bson.Document;
import java.util.List;
import java.util.ArrayList;
import org.bson.types.ObjectId;

/**
 *
 * @author Jesús
 */
public class SucursalDAO implements ISucursalDAO{
    private final MongoCollection<Document> coleccion;

    public SucursalDAO() {
        MongoDatabase baseDatos = ConexionMongoDB.getInstance().getDatabase();
        
        this.coleccion = baseDatos.getCollection("sucursales");
    }

    @Override
    public boolean insertar(Sucursal sucursal) {
        try {
            Document doc = SucursalesMapper.toDocument(sucursal);
            coleccion.insertOne(doc);
            sucursal.setId_sucursal(doc.getObjectId("_id").toString());
            return true;
        } catch (Exception e) {
            System.err.println("Error al insertar sucursal: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Sucursal> listarTodas() {
        List<Sucursal> lista = new ArrayList<>();
        try {
            for (Document doc : coleccion.find()) {
                lista.add(SucursalesMapper.fromDocumentToEntity(doc));
            }
        } catch (Exception e) {
            System.err.println("Error al listar sucursales: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Sucursal buscarPorId(String id) {
        try {
            //Se busca el documento por su ObjectId de Mongo
            Document doc = coleccion.find(eq("_id", new ObjectId(id))).first();
            if (doc != null) {
                return SucursalesMapper.fromDocumentToEntity(doc);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar sucursal por ID: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public boolean agregarEmpleado(String idSucursal, Empleado empleado) {
        try {
            Document empDoc = new Document("nombre", empleado.getNombre_completo())
                                .append("rol", empleado.getRol());

            // Usamos $push para agregar el empleado al arreglo existente en la BD
            coleccion.updateOne(
                eq("_id", new ObjectId(idSucursal)), 
                new Document("$push", new Document("empleados", empDoc))
            );
            return true;
        } catch (Exception e) {
            System.err.println("Error al agregar empleado: " + e.getMessage());
            return false;
        }
    }
}
