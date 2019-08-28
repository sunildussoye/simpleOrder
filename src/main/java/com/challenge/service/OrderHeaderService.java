package com.challenge.service;

import com.challenge.Dto.OrderHeaderDTO;
import com.challenge.model.OrderHeader;

import java.util.List;
import java.util.Optional;

public interface OrderHeaderService {
    OrderHeader save(OrderHeaderDTO orderHeaderDTO);
    Optional<OrderHeader> findById (int id);
    OrderHeaderDTO getOrder(String OrderRef);
    List<OrderHeaderDTO> getOrders();
}
