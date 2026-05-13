/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import entidades.Venta;
import java.time.LocalDateTime;
import mappers.VentaMapper;
import org.bson.Document;
import org.bson.types.ObjectId;
import mongoConnection.ConexionMongoDB;
/**
 *
 * @author Jesús
 */
public class VentaDAO implements IVentaDAO{
    private final MongoCollection<Document> coleccionVentas;

    public VentaDAO() {
        MongoDatabase db = ConexionMongoDB.getInstance().getDatabase();
        this.coleccionVentas = db.getCollection("ventas");
    }

    @Override
    public boolean guardarVenta(Venta entidad) {
        try {
            // Usamos el Mapper para convertir la Entidad a Documento de Mongo
            // Pasamos null como función de mapeo de envíos ya que para la DB 
            // solo nos interesan los datos de la venta y no el objeto envío completo
            Document doc = VentaMapper.toDocument(VentaMapper.toDTO(entidad));
            
            coleccionVentas.insertOne(doc);
            
            // Seteamos el ID generado por Mongo de vuelta a la entidad
            entidad.setId_venta(doc.getObjectId("_id").toString());
            return true;
        } catch (Exception e) {
            System.err.println("Error al insertar venta en MongoDB: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Venta buscarVentaPorId(String idVenta) {
        try {
            Document doc = coleccionVentas.find(eq("_id", new ObjectId(idVenta))).first();
            if (doc != null) {
                // Convertimos el documento a DTO y luego a Entidad (vía casteo)
                return VentaMapper.toEntity(VentaMapper.fromDocumentToDTO(doc));
            }
        } catch (Exception e) {
            System.err.println("Error al buscar venta: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Venta> listarVentasPorCliente(String idCliente) {
        List<Venta> lista = new ArrayList<>();
        try {
            //Buscamos todas las ventas que coincidan con el id_cliente
            for (Document doc : coleccionVentas.find(eq("id_cliente", idCliente))) {
                Venta entidad = VentaMapper.toEntity(VentaMapper.fromDocumentToDTO(doc));
                lista.add(entidad);
            }
        } catch (Exception e) {
            System.err.println("Error al listar ventas del cliente: " + e.getMessage());
        }
        return lista;
    }

    @Override
public List<Venta> obtenerVentasMesPasado(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
    List<Venta> reporte = new ArrayList<>();
    try {
        //Se formatean las fechas a String para la consulta en Mongo
        String inicio = fechaInicio.toString();
        String fin = fechaFin.toString();

        // Filtro: fecha >= inicio AND fecha <= fin
        Document filtro = new Document("fecha", new Document("$gte", inicio).append("$lte", fin));

        for (Document doc : coleccionVentas.find(filtro)) {
            Venta entidad = VentaMapper.toEntity(VentaMapper.fromDocumentToDTO(doc));
            reporte.add(entidad);
        }
    } catch (Exception e) {
        System.err.println("Error al generar reporte de ventas: " + e.getMessage());
    }
    return reporte;
}
}
