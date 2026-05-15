/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apiMapa;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
/**
 *
 * @author Jesús
 */
public class NominatimService {

    public double[] obtenerCoordenadas(String direccion) {
        try {
            //Se codifica la dirección para que sea válida en una URL
            String direccionEncoded = URLEncoder.encode(direccion, StandardCharsets.UTF_8);
            String urlStr = "https://nominatim.openstreetmap.org/search?format=json&q=" + direccionEncoded;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("SistemaPaqueteria", "SistemaPaqueteriaItson/1.0");

            if (conn.getResponseCode() == 200) {
                Scanner sc = new Scanner(url.openStream());
                StringBuilder sb = new StringBuilder();
                while (sc.hasNext()) sb.append(sc.next());
                sc.close();

                JSONArray jsonArray = new JSONArray(sb.toString());
                if (jsonArray.length() > 0) {
                    JSONObject firstResult = jsonArray.getJSONObject(0);
                    double lat = firstResult.getDouble("lat");
                    double lon = firstResult.getDouble("lon");
                    return new double[]{lat, lon};
                }
            }
        } catch (Exception e) {
            System.err.println("Error en Geocodificación OSM: " + e.getMessage());
        }
        return new double[]{0.0, 0.0};
    }
    
    public void abrirMapaEnNavegador(String latitud, String longitud) {
        try {
            // Creamos la URL de OpenStreetMap con las coordenadas
            String url = "https://www.openstreetmap.org/?mlat=" + latitud + "&mlon=" + longitud + "#map=18/" + latitud + "/" + longitud;

            // Abre el navegador predeterminado del sistema operativo
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
            }
        } catch (Exception e) {
            System.err.println("Error al abrir el navegador: " + e.getMessage());
        }
    }
}
