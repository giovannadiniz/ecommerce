package com.gec.ecommerce.dto.response;

public class PixResponse {
    private String txid;
    private String qrCode;
    private String qrCodeImage;

    public PixResponse() {}

    public PixResponse(String txid, String qrCode, String qrCodeImage) {
        this.txid = txid;
        this.qrCode = qrCode;
        this.qrCodeImage = qrCodeImage;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCodeImage() {
        return qrCodeImage;
    }

    public void setQrCodeImage(String qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }
}
