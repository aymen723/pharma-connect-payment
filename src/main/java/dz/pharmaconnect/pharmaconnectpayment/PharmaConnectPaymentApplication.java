package dz.pharmaconnect.pharmaconnectpayment;

import chargily.epay.java.ChargilyClient;
import chargily.epay.java.ChargilyResponse;
import chargily.epay.java.Invoice;
import chargily.epay.java.PaymentMethod;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PharmaConnectPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmaConnectPaymentApplication.class, args);

		// ChargilyClient client = new ChargilyClient(
		// "api_T3sOGKfkxKa6SCAgFlR9vWNf4kVKWMTv4RvopHjOXMqeNRWghDL6iNmQk1nkqjKY");
		// Invoice invoice = new Invoice(
		// "Chakhoum Ahmed",
		// "rainxh11@gmail.com",
		// 5.0,
		// "https://backend.com/webhook_endpoint",
		// "https://frontend.com",
		// PaymentMethod.EDAHABIA,
		// "5001",
		// 10000.0);
		// try {

		// ChargilyResponse response = client.submitInvoice(invoice);
		// if (response.isSuccess()) {
		// response.getStatusCode();
		// response.getCheckoutUrl();
		// System.out.println(response.getStatusCode());
		// System.out.println(response.getCheckoutUrl());
		// } else {
		// response.getStatusCode();
		// response.getErrorBody();
		// }

		// } catch (Exception e) {
		// System.out.println(e.getMessage());

		// }

	}

}
