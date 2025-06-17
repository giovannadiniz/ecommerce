package com.gec.ecommerce.controller;

import com.gec.ecommerce.domain.Order;
import com.gec.ecommerce.dto.response.OrderResponse;
import com.gec.ecommerce.dto.request.CheckoutRequest;
import com.gec.ecommerce.dto.request.OrderRequest;
import com.gec.ecommerce.dto.response.OrderResponse;
import com.gec.ecommerce.dto.response.StatusResponse;
import com.gec.ecommerce.mapper.CartMapper;
import com.gec.ecommerce.mapper.OrderMapper;
import com.gec.ecommerce.service.CheckoutService;
import jakarta.validation.Valid;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping("/processar")
    public ResponseEntity<OrderResponse> processarCheckout(@Valid @RequestBody OrderRequest request) {
         Order order = checkoutService.processarCheckout(request);
         return ResponseEntity.ok().body(orderMapper.entityToResponse(order));
    }
}

//    @GetMapping("/status/{orderId}")
//    public ResponseEntity<?> verificarStatus(@PathVariable Long orderId) {
//        try {
//            CheckoutResponse response = checkoutService.verificarStatusPagamento(orderId);
//
//            StatusResponse statusResponse = new StatusResponse(
//                    response.getStatus(),
//                    getStatusMessage(response.getStatus())
//            );
//
//            return ResponseEntity.ok(statusResponse);
//
//        } catch (Exception e) {
//            System.err.println("Erro ao verificar status: " + e.getMessage());
//            return ResponseEntity.badRequest().body("Erro ao verificar status do pagamento");
//        }
//    }
//
//    @GetMapping("/detalhes/{orderId}")
//    public ResponseEntity<?> obterDetalhesPedido(@PathVariable Long orderId) {
//        try {
//            CheckoutResponse response = checkoutService.verificarStatusPagamento(orderId);
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            System.err.println("Erro ao obter detalhes do pedido: " + e.getMessage());
//            return ResponseEntity.badRequest().body("Erro ao obter detalhes do pedido");
//        }
//    }
//
//    private String getStatusMessage(String status) {
//        switch (status) {
//            case "PENDENTE":
//                return "Aguardando pagamento";
//            case "PAGO":
//                return "Pagamento confirmado";
//            case "CANCELADO":
//                return "Pagamento cancelado";
//            case "EXPIRADO":
//                return "QR Code expirado";
//            default:
//                return "Status desconhecido";
//        }
//    }
