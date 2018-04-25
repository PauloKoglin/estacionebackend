/**
 * 
 */
package br.furb.persistence;

import java.io.IOException;
import java.text.NumberFormat;

import antlr.Parser;
import br.furb.cielopayment.Merchant;
import br.furb.cielopayment.ecommerce.CieloEcommerce;
import br.furb.cielopayment.ecommerce.Customer;
import br.furb.cielopayment.ecommerce.Environment;
import br.furb.cielopayment.ecommerce.Payment;
import br.furb.cielopayment.ecommerce.Sale;
import br.furb.cielopayment.request.CieloError;
import br.furb.cielopayment.request.CieloRequestException;
import br.furb.endpoints.estadia.EstadiaPojo;
import br.furb.endpoints.usuario.UsuarioPojo;

/**
 * @author PauloArnoldo
 *
 */
public class PagamentoDao {
	private final String MERCHANT_ID = "1198ba2c-3097-41bd-9205-44d8cc7488d2";
	private final String MERCHANT_KEY = "WMZZZOTCFFWYQYADNHSUBPFQJBOWOLDNJIFRWTZP";
	
	public String realizarPagamento(EstadiaPojo estadia) {
		System.out.println("Iniciando pagamento via cartão de crédito!");
		// Configure seu merchant
		Merchant merchant = new Merchant(this.MERCHANT_ID, this.MERCHANT_KEY);

		// Crie uma instância de Sale informando o ID do pagamento
		Sale sale = new Sale(estadia.getIdEstadia().toString());

		// Crie uma instância de Customer informando o nome do cliente
		//Customer customer = sale.customer(usuario.getNome());

		// Crie uma instância de Payment informando o valor do pagamento
		int valor = new Integer( Double.toString(Math.round(estadia.getPreco() * 100)).replace(".0", ""));
		Payment payment = sale.payment(valor);		

		// Crie  uma instância de Credit Card utilizando os dados de teste
		// esses dados estão disponíveis no manual de integração
		payment.creditCard("123", "Visa").setExpirationDate("12/2018")
		                                 .setCardNumber("0000000000000001")
		                                 .setHolder("Fulano de Tal");

		sale.setPayment(payment);
		
		// Crie o pagamento na Cielo
		try {
		    // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
		    sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);
		    System.out.println("Pagamento confirmado! Retorno : " + sale.getPayment().toString());
		    // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
		    // dados retornados pela Cielo
		    

		    // Com o ID do pagamento, podemos fazer sua captura, se ela não tiver sido capturada ainda
		    //sale = new CieloEcommerce(merchant, Environment.SANDBOX).captureSale(paymentId, 15700, 0);

		    // E também podemos fazer seu cancelamento, se for o caso
		    //sale = new CieloEcommerce(merchant, Environment.SANDBOX).cancelSale(paymentId, 15700);
		} catch (CieloRequestException e) {
		    // Em caso de erros de integração, podemos tratar o erro aqui.
		    // os códigos de erro estão todos disponíveis no manual de integração.
			
		    CieloError error = e.getError();
		    System.out.println("Deu ruim!"+ error.getMessage());
		} catch (IOException e) {
			System.out.println("Deu ruim!"+ e.getMessage());
			e.printStackTrace();
		}
		return sale.getPayment().getPaymentId();
	}
}
