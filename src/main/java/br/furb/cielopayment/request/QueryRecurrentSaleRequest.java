package br.furb.cielopayment.request;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import br.furb.cielopayment.Environment;
import br.furb.cielopayment.Merchant;
import br.furb.cielopayment.ecommerce.RecurrentSale;

public class QueryRecurrentSaleRequest extends AbstractSaleRequest<String, RecurrentSale> {
    public QueryRecurrentSaleRequest(Merchant merchant, Environment environment) {
        super(merchant, environment);
    }

    @Override
    public RecurrentSale execute(String recurrentPaymentId) throws IOException, CieloRequestException {
        String url = environment.getApiQueryURL() + "1/RecurrentPayment/" + recurrentPaymentId;

        HttpGet request = new HttpGet(url);
        HttpResponse response = sendRequest(request);

        return readResponse(response, RecurrentSale.class);
    }
}
