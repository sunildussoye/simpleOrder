package com.challenge.controller;

import com.challenge.Dto.OrderHeaderDTO;
import com.challenge.model.OrderHeader;
import com.challenge.service.OrderHeaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/")
public class OrderHeaderController {

    private OrderHeaderService orderHeaderService;

    @Autowired
    public OrderHeaderController(OrderHeaderService orderHeaderService) {
        this.orderHeaderService = orderHeaderService;
    }

    @GetMapping("getOrder/{orderRef}")
    public ResponseEntity<OrderHeaderDTO> getOrder(@PathVariable("orderRef") String orderRef) {
        log.info("Running API - get Order with Reference " + orderRef);
        OrderHeaderDTO orderHeaderDTO = orderHeaderService.getOrder(orderRef);

        try {
            if (!orderHeaderService.equals(null))
                return ResponseEntity.ok().body(orderHeaderDTO);
        } catch (Exception e) {
            log.info("Wrong OrderRef");
            return ResponseEntity.badRequest().build();
        }
        return null;
    }

    @GetMapping("getOrders")
    public ResponseEntity<List<OrderHeaderDTO>> getOrders() {
        log.info("Running API - get Orders ");
        List<OrderHeaderDTO> orderHeaderDTOS = orderHeaderService.getOrders();
        return ResponseEntity.ok().body(orderHeaderDTOS);
    }

    @GetMapping("fulfillOrder/{orderRef}")
    public ResponseEntity<OrderHeaderDTO> getFullFillOrder(@PathVariable("orderRef") String orderRef) {
        log.info("Running API - get Order with Reference " + orderRef);
        OrderHeaderDTO orderHeaderDTO = orderHeaderService.getOrder(orderRef);
        try {
            if (!orderHeaderDTO.equals(null)) {

                if (orderHeaderService.findById(orderHeaderDTO.getId()) != null) {
                    Optional<OrderHeader> orderHeader = orderHeaderService.findById(orderHeaderDTO.getId());
                    if (orderHeader.get().isDespatched() == true) {
                        log.info("Despatch = true");
                        return ResponseEntity.badRequest().build();
                    }
                }
                orderHeaderDTO.setDespatched(true);
                orderHeaderService.save(orderHeaderDTO);
                return ResponseEntity.ok().body(orderHeaderDTO);
            }
        } catch (Exception e) {
            log.info("Wrong OrderRef");
            return ResponseEntity.badRequest().build();
        }
        return null;
    }

    @PutMapping("updateOrder")
    public ResponseEntity updateOrder(@RequestBody OrderHeaderDTO orderHeaderDTO) {

        try {
            if (orderHeaderService.findById(orderHeaderDTO.getId()) != null) {
                Optional<OrderHeader> orderHeader = orderHeaderService.findById(orderHeaderDTO.getId());
                if (orderHeader.get().isDespatched() == true) {
                    log.info("Despatch = true");
                    return ResponseEntity.badRequest().build();
                }
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        log.info("Update Order");
        orderHeaderService.save(orderHeaderDTO);
        return ResponseEntity.ok().body(orderHeaderDTO);
    }

}
