package application;

public class SupplementOOSTable {
    private int productId;
    private String serialNo;
    private String name;
    private String manufacturer;

    public SupplementOOSTable(int productId, String serialNo, String name, String manufacturer) {
        this.productId = productId;
        this.serialNo = serialNo;
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
