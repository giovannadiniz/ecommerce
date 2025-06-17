package com.gec.ecommerce.service;
import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.gec.ecommerce.dto.request.PixChargeRequest;
import com.gec.ecommerce.dto.response.PixResponse;
import com.gec.ecommerce.pix.Credentials;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PixService {

    @Value("${efi.client.id}")
    private String clientId;

    @Value("${efi.client.secret}")
    private String clientSecret;

//    public JSONObject pixCreateEVP() {
//
//        JSONObject options = configuringJsonObject();
//
//        try {
//            EfiPay efi = new EfiPay(options);
//            JSONObject response = efi.call("pixCreateEvp", new HashMap<String,String>(), new JSONObject());
//            System.out.println(response.toString());
//            return response;
//        }catch (EfiPayException e){
//            System.out.println(e.getError());
//            System.out.println(e.getErrorDescription());
//        }
//        catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }

    public String listarEVPs() {
        JSONObject options = configuringJsonObject();

        try {
            EfiPay efi = new EfiPay(options);
            JSONObject response = efi.call("pixListEvp", new HashMap<>(), new JSONObject());

            System.out.println(response.toString());

            JSONArray chaves = response.getJSONArray("chaves");
            String primeiraChave = chaves.getString(0);

            return primeiraChave;
        } catch (EfiPayException e) {
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String pixCreateCharge(String chave, String valor){

        JSONObject options = configuringJsonObject();

        JSONObject body = new JSONObject();
        body.put("calendario", new JSONObject().put("expiracao", 3600));
        body.put("devedor", new JSONObject().put("cpf", "12345678909").put("nome", "Francisco da Silva"));
        body.put("valor", new JSONObject().put("original", valor));
        body.put("chave", chave);

//        JSONArray infoAdicionais = new JSONArray();
//        infoAdicionais.put(new JSONObject().put("nome", "Campo 1").put("valor", "Informação Adicional1 do PSP-Recebedor"));
//        infoAdicionais.put(new JSONObject().put("nome", "Campo 2").put("valor", "Informação Adicional2 do PSP-Recebedor"));
//        body.put("infoAdicionais", infoAdicionais);

        try {
            EfiPay efi = new EfiPay(options);
            JSONObject response = efi.call("pixCreateImmediateCharge", new HashMap<String,String>(), body);

//            int idFromJson= response.getJSONObject("loc").getInt("id");
//            pixGenerateQRCode(String.valueOf(idFromJson));

            return response.getString("pixCopiaECola");
        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    private void pixGenerateQRCode(String id){


        JSONObject options = configuringJsonObject();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);

        try {
            EfiPay efi= new EfiPay(options);
            Map<String, Object> response = efi.call("pixGenerateQRCode", params, new HashMap<String, Object>());

            System.out.println(response);

            File outputfile = new File("qrCodeImage.png");
            ImageIO.write(ImageIO.read(new ByteArrayInputStream(javax.xml.bind.DatatypeConverter.parseBase64Binary(((String) response.get("imagemQrcode")).split(",")[1]))), "png", outputfile);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(outputfile);

        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private JSONObject configuringJsonObject(){
        Credentials credentials = new Credentials();

        JSONObject options = new JSONObject();
        options.put("client_id", clientId);
        options.put("client_secret", clientSecret);
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.isSandbox());

        return options;
    }

}
