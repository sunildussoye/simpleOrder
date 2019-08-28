package com.challenge.service;

import com.challenge.Dto.OrderHeaderDTO;
import com.challenge.model.OrderHeader;
import com.challenge.repository.OrderHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderHeaderServiceImpl implements OrderHeaderService {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Override
    public Optional<OrderHeader> findById(int id) {
        return orderHeaderRepository.findById(id);
    }

    @Override
    public OrderHeader save(OrderHeaderDTO orderHeaderDTO) {

        if (orderHeaderDTO.getOrderReference() == null) {
            orderHeaderDTO.setOrderReference(setReference(orderHeaderDTO));
        }
        return orderHeaderRepository.save(ConvertDTOToEntity(orderHeaderDTO));
    }

    private String setReference(OrderHeaderDTO orderHeaderDTO) {
        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
        return simpleDateformat.format(now) + orderHeaderDTO.getId();
    }

    @Override
    public OrderHeaderDTO getOrder(String OrderRef) {
        Optional<OrderHeader> orderHeader = orderHeaderRepository.getOrder(OrderRef);

        if (orderHeader.isPresent()) {
            log.info("Running API - get Customer with ID" + orderHeader.toString());
            return ConvertEntityToDTO(orderHeader.get());
        }
        return null;
    }

    @Override
    public List<OrderHeaderDTO> getOrders() {
        List<OrderHeader> orderHeader = orderHeaderRepository.getOrders();
        if (orderHeader != null) {
            return orderHeader
                    .stream()
                    .map(o -> ConvertEntityToDTO(o))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public OrderHeaderDTO ConvertEntityToDTO(OrderHeader orderHeader) {
        OrderHeaderDTO orderHeaderDTO = new OrderHeaderDTO();
        orderHeaderDTO.setOrderReference(orderHeader.getOrderReference());
        orderHeaderDTO.setNumberOfBricks(orderHeader.getNumberOfBricks());
        orderHeaderDTO.setDespatched(orderHeader.isDespatched());
        orderHeaderDTO.setId(orderHeader.getId());
        return orderHeaderDTO;
    }

    public OrderHeader ConvertDTOToEntity(OrderHeaderDTO orderHeaderDTO) {
        // log.info("ConvertDTOToEntity" + orderHeaderDTO.getId());
        log.info("ConvertDTOToEntity" + orderHeaderDTO.getOrderReference());
        OrderHeader orderHeaderTmp = new OrderHeader();

        if (orderHeaderDTO.getId().equals(null)) {
            OrderHeader orderHeader = new OrderHeader();
            orderHeaderTmp = orderHeader;
        } else {
            Optional<OrderHeader> orderHeader = orderHeaderRepository.findById(orderHeaderDTO.getId());
            if (orderHeader.isPresent()) {
                orderHeaderTmp = orderHeader.get();
            }
        }
        orderHeaderTmp.setOrderReference(orderHeaderDTO.getOrderReference());
        orderHeaderTmp.setNumberOfBricks(orderHeaderDTO.getNumberOfBricks());
        orderHeaderTmp.setDespatched(orderHeaderDTO.isDespatched());
        return orderHeaderTmp;
    }
}


