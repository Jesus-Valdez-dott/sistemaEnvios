/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.exception.StripeException;
/**
 *
 * @author Jesús
 */
public class StripeService {
    public StripeService() {
        // Esta es una llave de prueba universal de Stripe para desarrollo
        // En un proyecto real, esto iría en un archivo de configuración
        Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc"; 
    }

    public String procesarPagoSimulado(Double monto) {
        try {
            // Stripe maneja los montos en centavos (Long)
            // Multiplicamos por 100 para convertir pesos/dólares a centavos
            long montoEnCentavos = (long) (monto * 100);

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(montoEnCentavos)
                    .setCurrency("mxn") //Pesos mexicanos
                    .setPaymentMethod("pm_card_visa") //Simula una tarjeta Visa exitosa
                    .setConfirm(true)
                    .setReturnUrl("https://localhost/success")
                    .build();

            //Se hace una petición real a los servidores de Stripe
            PaymentIntent intent = PaymentIntent.create(params);

            System.out.println("Stripe API (Test Mode): Pago procesado con éxito.");
            return intent.getId(); // Devuelve el pi_XXXX real de la simulación

        } catch (StripeException e) {
        System.err.println("Error de Stripe: " + e.getMessage());
        return null;
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            return null;
        }
    }
}
