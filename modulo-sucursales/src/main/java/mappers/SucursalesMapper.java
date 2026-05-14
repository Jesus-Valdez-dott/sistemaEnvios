/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import entidades.Sucursal;
import dto.SucursalDTO;
import entidades.Empleado;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
/**
 *
 * @author Jesús
 */
public class SucursalesMapper {
    public static Sucursal toEntity(SucursalDTO dto) {
        if (dto == null) return null;
        Sucursal entity = new Sucursal();
        if (dto.getId_sucursal()!= null) entity.setId_sucursal(dto.getId_sucursal());
        entity.setNombre(dto.getNombre());
        entity.setDireccion(dto.getDireccion());
        entity.setLatitud(dto.getLatitud());
        entity.setLongitud(dto.getLongitud());
        return entity;
    }

    public static SucursalDTO toDTO(Sucursal entity) {
        if (entity == null) return null;
        SucursalDTO dto = new SucursalDTO();
        dto.setId_sucursal(entity.getId_sucursal());
        dto.setNombre(entity.getNombre());
        dto.setDireccion(entity.getDireccion());
        dto.setLatitud(entity.getLatitud());
        dto.setLongitud(entity.getLongitud());
        return dto;
    }

    public static Document toDocument(Sucursal entity) {
        Document doc = new Document();
        if (entity.getId_sucursal()!= null) doc.append("_id", new org.bson.types.ObjectId(entity.getId_sucursal()));
        
        doc.append("nombre", entity.getNombre())
           .append("direccion", entity.getDireccion())
           .append("latitud", entity.getLatitud())
           .append("longitud", entity.getLongitud());

        List<Document> empleadosDoc = new ArrayList<>();
        for (Empleado emp : entity.getEmpleados()) {
            Document docEmp = new Document("nombre", emp.getNombre_completo());

            if (emp.getRol() != null) {
                docEmp.append("rol", emp.getRol().name());
            }

            empleadosDoc.add(docEmp);
        }
        doc.append("empleados", empleadosDoc);
        
        return doc;
    }

    public static Sucursal fromDocumentToEntity(Document doc) {
        Sucursal s = new Sucursal();
        s.setId_sucursal(doc.getObjectId("_id").toString());
        s.setNombre(doc.getString("nombre"));
        s.setDireccion(doc.getString("direccion"));
        s.setLatitud(doc.getDouble("latitud") != null ? doc.getDouble("latitud") : 0.0);
        s.setLongitud(doc.getDouble("longitud") != null ? doc.getDouble("longitud") : 0.0);

        List<Document> empsDoc = (List<Document>) doc.get("employees");
        if (empsDoc != null) {
            for (Document d : empsDoc) {
                Empleado e = new Empleado();
                e.setNombre_completo(d.getString("nombre"));

                String rolStr = d.getString("rol");
                if (rolStr != null) {
                    try {
                        e.setRol(Enums.EnumRol.valueOf(rolStr));
                    } catch (IllegalArgumentException ex) {
                        System.err.println("Rol no válido encontrado en la BD: " + rolStr);
                    }
                }
                s.getEmpleados().add(e);
            }
        }
        return s;
    }
}
