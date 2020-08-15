package ie.cct.ger_garage.model;

public class Stock {

    private int idProd;
    private String product;
    private Double price;
    private Integer quantity;

    public Stock(int idProd, String product, Double price, Integer quantity) {
        super();
        this.idProd = idProd;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public Stock() {
        super();
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Stock [idProd=" + idProd + ", product=" + product + ", price=" + price + "]";
    }


}
