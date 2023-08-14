package com.b.simple.design.business.customer;

import java.math.BigDecimal;
import java.util.List;

import com.b.simple.design.business.exception.DifferentCurrenciesException;
import com.b.simple.design.model.customer.Amount;
import com.b.simple.design.model.customer.AmountImpl;
import com.b.simple.design.model.customer.Currency;
import com.b.simple.design.model.customer.Product;

public class CustomerBOImpl implements CustomerBO {

    @Override
    public Amount getCustomerProductsSum(List<Product> products)
            throws DifferentCurrenciesException {

        if (products.size() == 0)
            return new AmountImpl(BigDecimal.ZERO, Currency.EURO);

        if (!doAllProductsHaveSameCurrency(products)) {
            throw new DifferentCurrenciesException();
        }

        return sumProductsAmount(products);
    }

    private AmountImpl sumProductsAmount(List<Product> products) {
        BigDecimal sum = products.stream()
                .map(product -> product.getAmount().getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Currency expectedCurrency = getExpectedCurrency(products);
        return new AmountImpl(sum, expectedCurrency);
    }

    private boolean doAllProductsHaveSameCurrency(List<Product> products) {
        Currency expectedCurrency = getExpectedCurrency(products);
        return products.stream().map(product -> product.getAmount().getCurrency())
                .allMatch(currency -> currency.equals(expectedCurrency));

    }

    private Currency getExpectedCurrency(List<Product> products) {
        return products.get(0).getAmount().getCurrency();
    }
}