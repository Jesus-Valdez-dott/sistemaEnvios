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
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Updates.set;
import dtos.RegistroEnvioDTO;
import java.time.LocalDate;
import mappers.EnvioMapper;
import org.bson.Document;
import org.bson.types.ObjectId;
/**
 *
 * @author Jesús
 */
public class EnvioDAO implements IEnvioDAO {
 
    private final MongoCollection<Document> coleccionEnvios;
 
    public EnvioDAO() {
        MongoDatabase baseDatos = ConexionMongoDB.getInstance().getDatabase();
        this.coleccionEnvios = baseDatos.getCollection("envios");
    }
 
    @Override
    public boolean registrarEnvio(Envio e) {
        try {
            // Construimos los documentos de los paquetes embebidos
            List<Document> docsPaquetes = new ArrayList<>();
            if (e.getPaquetes() != null) {
                for (Paquete p : e.getPaquetes()) {
                    Document docPaquete = new Document("alto", p.getAlto())
                            .append("largo", p.getLargo())
                            .append("ancho", p.getAncho())
                            .append("peso", p.getPeso())
                            .append("descripcion", p.getDescripcion());
                    docsPaquetes.add(docPaquete);
                }
            }
 
            // Primer hito del historial: el envio acaba de registrarse
            Document primerHito = new Document("fecha", java.time.LocalDateTime.now().toString())
                    .append("direccion", "Sucursal origen")
                    .append("latitud", "")
                    .append("longitud", "");
            List<Document> historialInicial = new ArrayList<>();
            historialInicial.add(primerHito);
 
            // Documento principal del envio
            Document docEnvio = new Document("codigo_rastreo", e.getCodigo_rastreo())
                    .append("id_cliente", e.getId_cliente())
                    .append("fecha_envio", e.getFecha_envio().toString())
                    .append("estado", e.getEstado().name())
                    .append("nombre_destinatario", e.getNombre_destinatario())
                    .append("direccion_destino", e.getDireccion_destino())
                    .append("latitud_destino", e.getLatitud_destino())
                    .append("longitud_destino", e.getLongitud_destino())
                    .append("telefono_destinatario", e.getTelefono_destinatario())
                    .append("paquetes", docsPaquetes)
                    .append("historial_envio", historialInicial);
 
            coleccionEnvios.insertOne(docEnvio);
            return true;
 
        } catch (Exception ex) {
            System.err.println("Error al registrar envio en BD: " + ex.getMessage());
            return false;
        }
    }
 
    @Override
    public boolean actualizarEdo(String id_Envio, EstadoEnvio edo) {
        try {
            Document nuevoRegistro = new Document("fecha", java.time.LocalDateTime.now().toString())
                    .append("direccion", "Actualizacion de estado a: " + edo)
                    .append("latitud", "")
                    .append("longitud", "");
 
            coleccionEnvios.updateOne(
                eq("_id", new ObjectId(id_Envio)),
                combine(
                    set("estado", edo.toString()),
                    push("historial_envio", nuevoRegistro)
                )
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
            // Busca por _id de MongoDB (ObjectId)
            Document doc = coleccionEnvios.find(eq("_id", new ObjectId(id_Envio))).first();
            if (doc == null) return null;
            return EnvioMapper.fromDocumentToEntity(doc);
 
        } catch (Exception ex) {
            System.err.println("Error al recuperar detalles: " + ex.getMessage());
            return null;
        }
    }
 
    @Override
    public Envio rastrearPaquete(String codigo) {
        try {
            // Busca por codigo_rastreo (TRK-XXXX), NO por _id
            Document doc = coleccionEnvios.find(eq("codigo_rastreo", codigo)).first();
            if (doc != null) {
                return EnvioMapper.fromDocumentToEntity(doc);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar envio por codigo: " + e.getMessage());
        }
        return null;
    }
 
    @Override
    public List<Envio> obtenerHistCliente(String id_Cliente) {
        List<Envio> historial = new ArrayList<>();
        for (Document doc : coleccionEnvios.find(eq("id_cliente", id_Cliente))) {
            historial.add(EnvioMapper.fromDocumentToEntity(doc));
        }
        return historial;
    }
 
    @Override
    public boolean agregarHitoHistorial(String idEnvio, RegistroEnvioDTO movimiento) {
        try {
            Document docHito = new Document()
                    .append("fecha", java.time.LocalDateTime.now().toString())
                    .append("direccion", movimiento.getDireccion())
                    .append("latitud", movimiento.getLatitud())
                    .append("longitud", movimiento.getLongitud());
 
            // CORREGIDO: era "coleccion", debe ser "coleccionEnvios"
            coleccionEnvios.updateOne(
                eq("_id", new ObjectId(idEnvio)),
                new Document("$push", new Document("historial_envio", docHito))
            );
            return true;
 
        } catch (Exception e) {
            System.err.println("Error al actualizar historial en BD: " + e.getMessage());
            return false;
        }
    }
}