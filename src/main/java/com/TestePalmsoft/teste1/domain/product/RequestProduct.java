package com.TestePalmsoft.teste1.domain.product;

public record RequestProduct(
        String id,
        String name,
        int price_in_cents) {

    public RequestProduct(Product product) {
        this(product.getId(), product.getName(), product.getPrice_in_cents());
    }
}
