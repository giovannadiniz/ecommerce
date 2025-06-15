package com.gec.ecommerce.service;

import com.gec.ecommerce.domain.Cart;
import com.gec.ecommerce.domain.Order;
import com.gec.ecommerce.domain.Product;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.request.CheckoutRequest;
import com.gec.ecommerce.dto.request.OrderRequest;
import com.gec.ecommerce.dto.response.OrderResponse;
import com.gec.ecommerce.dto.response.PixResponse;
import com.gec.ecommerce.repository.CartRepository;
import com.gec.ecommerce.repository.OrderRepository;
import com.gec.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CheckoutService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PixService pixService;

    @Autowired
    private CartService cartService;

    @Transactional
    public Order processarCheckout(OrderRequest request) {
        try {
            // Buscar o usuário
            User user = userService.findById(request.userId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Product product = productService.findById(request.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

//            Order order = new Order();
//                    order.setUser(user);
//                    order.setProductId(product);
//                    order.setQuantity(request.quantity() > 0 ? request.quantity() : 1);
//                    order.setTotal(request.total());

            // Salvar pedido inicialmente
//            order = orderRepository.save(order);

            JSONObject chaveJson = pixService.pixCreateEVP();

//            Gerar cobrança PIX
//            PixResponse pixResponse = pixService.pixCreateCharge(chaveJson, request.total());
//
//
//           Atualizar pedido com dados do PIX
//            order.setTxid(pixResponse.getTxid());
//            order.setQrCode(pixResponse.getQrCode());
//            order.setQrCodeImage(pixResponse.getQrCodeImage());

            // Salvar pedido atualizado
//            order = orderRepository.save(order);

            // Limpar carrinho após criar pedido
             cartService.delete(request.idCart());

            return null;

//            return new CheckoutResponse(
//                    order.getId(),
//                    order.getQuantity(),
//                    order.getTotal(),
//                    order.getQrCode(),
//                    order.getQrCodeImage(),
//                    order.getStatus()
//            );

        } catch (Exception e) {
            System.err.println("Erro ao processar checkout: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao processar checkout: " + e.getMessage(), e);
        }
    }
}

//    public CheckoutResponse verificarStatusPagamento(Long orderId) {
//        try {
//            Optional<Order> orderOpt = orderRepository.findById(orderId);
//            if (!orderOpt.isPresent()) {
//                throw new RuntimeException("Pedido não encontrado");
//            }
//
//            Order order = orderOpt.get();
//
//            // Se já está pago, retorna sem verificar novamente
//            if ("PAGO".equals(order.getStatus())) {
//                return criarCheckoutResponse(order);
//            }

            // Verificar status no EFI
//            String statusEFI = pixService.verificarStatusPagamento(order.getTxid());

            // Atualizar status se necessário
//            if ("CONCLUIDA".equals(statusEFI) && !"PAGO".equals(order.getStatus())) {
//                order.setStatus("PAGO");
//                order.setPaymentDate(LocalDateTime.now());
//                orderRepository.save(order);
//            }
//
//            return criarCheckoutResponse(order);
//
//        } catch (Exception e) {
//            System.err.println("Erro ao verificar status do pagamento: " + e.getMessage());
//            throw new RuntimeException("Erro ao verificar status do pagamento", e);
//        }
//    }

//    private CheckoutResponse criarCheckoutResponse(Order order) {
//        return new CheckoutResponse(
//                order.getId(),
//                order.getProductName(),
//                order.getQuantity(),
//                order.getTotal(),
//                order.getQrCode(),
//                order.getQrCodeImage(),
//                order.getStatus()
//        );
//    }
//

