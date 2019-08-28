package com.challenge.repository;

import com.challenge.model.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Integer> {

    @Query(value ="select * from order_header where order_reference = ?1 " ,
            nativeQuery =true)
    Optional<OrderHeader> getOrder(String OrderReference);

    @Query(value ="select * from order_header " , nativeQuery =true)
    List<OrderHeader> getOrders();



}
