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
import java.time.LocalDate;
import mappers.EnvioMapper;
import dtos.RegistroEnvioDTO;
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
                    .append("fecha_envio", e.getFecha_envio().toString())
                    .append("estado", e.getEstado().name()) //Se guarda el Enum como texto
                    .append("nombre_destinatario", e.getNombre_destinatario())
                    .append("direccion_destino", e.getDireccion_destino())
                    .append("telefono_destinatario", e.getTelefono_destinatario())
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
            // Creamos el objeto del nuevo registro de historial
            Document nuevoRegistro = new Document("id_registro", new ObjectId().toString())
                    .append("fecha", java.time.LocalDateTime.now().toString())
                    .append("direccion", "Actualización de estado a: " + edo);

            //combine para mandar el set y el push juntos
            coleccionEnvios.updateOne(
                eq("_id", new ObjectId(id_Envio)), 
                combine(
                    set("estado", edo.toString()),          // Cambia el estado
                    push("historial_envio", nuevoRegistro)  // Agrega al historial
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
            Document doc = coleccionEnvios.find(eq("_id", new ObjectId(id_Envio))).first();
            if (doc == null) return null;

            Envio envio = new Envio();
            
            String fechaStr = doc.getString("fecha_envio");
            if (fechaStr != null && !fechaStr.isEmpty()) {
                envio.setFecha_envio(LocalDate.parse(fechaStr)); 
            }
            envio.setId_envio(doc.getObjectId("_id").toString());
            envio.setCodigo_rastreo(doc.getString("codigo_rastreo"));
            envio.setNombre_destinatario(doc.getString("nombre_destinatario"));
            envio.setDireccion_destino(doc.getString("direccion_destino"));
            envio.setTelefono_destinatario(doc.getString("telefono_destinatario"));
            envio.setEstado(EstadoEnvio.valueOf(doc.getString("estado")));

            //Reconstrucción de Paquete desde el Documento anidado
            List<Document> docsPaquetes = (List<Document>) doc.get("paquetes");
            List<Paquete> listaPaquetes = new ArrayList<>();
            if (docsPaquetes != null) {
                for (Document d : docsPaquetes) {
                    Paquete p = new Paquete();
                    p.setAlto(d.getDouble("alto"));
                    p.setAncho(d.getDouble("ancho"));
                    p.setLargo(d.getDouble("largo"));
                    p.setPeso(d.getDouble("peso"));
                    p.setDescripcion(d.getString("descripcion"));
                    listaPaquetes.add(p);
                }
            }
            envio.setPaquetes(listaPaquetes);
            return envio;
        } catch (Exception ex) {
            System.err.println("Error al recuperar detalles: " + ex.getMessage());
            return null;
        }
    }
    
    @Override
    public Envio rastrearPaquete(String codigo) {
        try {
            // Buscamos el documento donde el campo "codigo_rastreo" coincida
            Document doc = coleccionEnvios.find(eq("codigo_rastreo", codigo)).first();
            
            if (doc != null) {
                // Usamos el Mapper para convertir el Documento de Mongo a nuestra Entidad
                return EnvioMapper.fromDocumentToEntity(doc);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar envío por código: " + e.getMessage());
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
            // 1. Creamos el documento del histo
            // Convertimos los datos del DTO a un Documento de MongoDB
            Document docHito = new Document()
                    .append("fecha", new java.util.Date()) // Fecha actual
                    .append("direccion", movimiento.getDireccion())
                    .append("latitud", movimiento.getLatitud())
                    .append("longitud", movimiento.getLongitud());

            // 2. Usamos updateOne con $push para insertar en el arreglo "historial"
            // Buscamos por el ID interno de MongoDB
            coleccionEnvios.updateOne(
                eq("_id", new ObjectId(idEnvio)), 
                new Document("$push", new Document("historial", docHito))
            );

            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar historial en BD: " + e.getMessage());
            return false;
        }
    }
}
