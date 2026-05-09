/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moduloCliente.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import moduloCliente.dto.ClienteDTO;
import org.bson.Document;
import paqueteriaBD.configuracion.ConexionBD;

public class ClienteDAO implements IClienteDAO {

    private final MongoCollection<Document> coleccion;

    public ClienteDAO() {

        MongoDatabase database =
                ConexionBD.obtenerConexion();

        coleccion =
                database.getCollection("clientes");
    }

    @Override
    public void agregarCliente(ClienteDTO cliente) {

        Document documento = new Document();

        documento.append(
                "id_cliente",
                cliente.getIdCliente()
        );

        documento.append(
                "nombre",
                cliente.getNombre()
        );

        documento.append(
                "telefono",
                cliente.getTelefono()
        );

        documento.append(
                "direccion",
                cliente.getDireccion()
        );

        documento.append(
                "rfc",
                cliente.getRfc()
        );

        coleccion.insertOne(documento);

        System.out.println(
                "Cliente guardado en MongoDB"
        );
    }

    @Override
    public List<ClienteDTO> obtenerClientes() {

        List<ClienteDTO> lista =
                new ArrayList<>();

        for (Document doc : coleccion.find()) {

            ClienteDTO cliente =
                    new ClienteDTO();

            cliente.setIdCliente(
                    doc.getLong("id_cliente")
            );

            cliente.setNombre(
                    doc.getString("nombre")
            );

            cliente.setTelefono(
                    doc.getString("telefono")
            );

            cliente.setDireccion(
                    doc.getString("direccion")
            );

            cliente.setRfc(
                    doc.getString("rfc")
            );

            lista.add(cliente);
        }

        return lista;
    }

    @Override
    public ClienteDTO buscarCliente(long idCliente) {

        Document doc =
                coleccion.find(
                        eq("id_cliente", idCliente)
                ).first();

        if (doc != null) {

            ClienteDTO cliente =
                    new ClienteDTO();

            cliente.setIdCliente(
                    doc.getLong("id_cliente")
            );

            cliente.setNombre(
                    doc.getString("nombre")
            );

            cliente.setTelefono(
                    doc.getString("telefono")
            );

            cliente.setDireccion(
                    doc.getString("direccion")
            );

            cliente.setRfc(
                    doc.getString("rfc")
            );

            return cliente;
        }

        return null;
    }

    @Override
    public boolean eliminarCliente(long idCliente) {

        return coleccion.deleteOne(
                eq("id_cliente", idCliente)
        ).getDeletedCount() > 0;
    }
}