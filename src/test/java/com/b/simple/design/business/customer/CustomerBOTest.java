package com.b.simple.design.business.customer;

import com.b.simple.design.business.exception.DifferentCurrenciesException;
import com.b.simple.design.model.customer.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerBOTest {

	private CustomerBO customerBO = new CustomerBOImpl();

	@Test
	public void testCustomerProductSum_TwoProductsSameCurrencies()
			throws DifferentCurrenciesException {

		//given
		AmountImpl amount1 = new AmountImpl(new BigDecimal("5.0"), Currency.EURO);
		AmountImpl amount2 = new AmountImpl(new BigDecimal("6.0"), Currency.EURO);
		Amount[] amountsToSum = new Amount[] {amount1, amount2};
		List<Product> defaultProductsWithAmountsToSum = createDefaultProductsWithAmounts(amountsToSum);

		Amount expectedSum = new AmountImpl(new BigDecimal("11.0"), Currency.EURO);

		//when
		Amount actualSum = customerBO.getCustomerProductsSum(defaultProductsWithAmountsToSum);

		//then
		assertAmount(expectedSum, actualSum);
	}

	@Test
	public void testCustomerProductSum_TwoProductsDifferentCurrencies_throwsException() {

		//given
		AmountImpl amount1 = new AmountImpl(new BigDecimal("5.0"), Currency.INDIAN_RUPEE);
		AmountImpl amount2 = new AmountImpl(new BigDecimal("6.0"), Currency.EURO);
		Amount[] amounts = new Amount[] {amount1, amount2};

		List<Product> products = createDefaultProductsWithAmounts(amounts);

		//when & then
		Assertions.assertThrows(DifferentCurrenciesException.class,
				() -> customerBO.getCustomerProductsSum(products));
	}

	@Test
	public void testCustomerProductSum_noProducts() throws DifferentCurrenciesException{

		//given
		List<Product> emptyProducts = new ArrayList<Product>();
		Amount expectedSum = new AmountImpl(BigDecimal.ZERO, Currency.EURO);

		//when
		Amount actual = customerBO.getCustomerProductsSum(emptyProducts);

		//then
		assertAmount(expectedSum, actual);
	}

	private static void assertAmount(Amount expectedSum, Amount actualSum) {
		assertEquals(expectedSum.getCurrency(), actualSum.getCurrency());
		assertEquals(expectedSum.getValue(), actualSum.getValue());
	}

	private static List<Product> createDefaultProductsWithAmounts(Amount[] amounts) {
		return Arrays.stream(amounts)
				.map(amount ->
						new ProductImpl(1, "Product 1", ProductType.BANK_GUARANTEE,
								amount))
				.collect(Collectors.toList());
	}

}