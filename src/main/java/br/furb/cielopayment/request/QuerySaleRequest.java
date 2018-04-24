package br.furb.cielopayment.request;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import br.furb.cielopayment.Environment;
import br.furb.cielopayment.Merchant;
import br.furb.cielopayment.ecommerce.Sale;

/**
 * Query a Sale by it's paymentId
 */
public class QuerySaleRequest extends AbstractSaleRequest<String, Sale> {
	public QuerySaleRequest(Merchant merchant, Environment environment) {
		super(merchant, environment);
	}

	@Override
	public Sale execute(String paymentId) throws IOException, CieloRequestException {
		String url = environment.getApiQueryURL() + "1/sales/" + paymentId;

		HttpGet request = new HttpGet(url);
		HttpResponse response = sendRequest(request);

		return readResponse(response, Sale.class);
	}
}
