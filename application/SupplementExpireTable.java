package application;

import java.sql.Date;

public class SupplementExpireTable {
    private int productId;
    private String serialNo;
    private String name;
    private Date expireDate;
    private String form;

    public SupplementExpireTable(int productId, String serialNo, String name, Date expireDate, String form) {
        this.productId = productId;
        this.serialNo = serialNo;
        this.name = name;
        this.expireDate = expireDate;
        this.form = form;
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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }
}
