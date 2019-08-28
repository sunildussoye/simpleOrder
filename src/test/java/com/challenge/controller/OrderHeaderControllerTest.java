package com.challenge.controller;

import com.challenge.Dto.OrderHeaderDTO;
import com.challenge.service.OrderHeaderService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(MockitoJUnitRunner.class)
public class OrderHeaderControllerTest {

    @Mock
    private OrderHeaderService orderHeaderService;

    @InjectMocks
    private OrderHeaderController orderHeaderController;

    private MockMvc mockMvc;

    static List<OrderHeaderDTO> orderHeaderDTOS = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderHeaderController)
                .build();
    }

    @BeforeClass
    public static void DataSetup() {
        OrderHeaderDTO orderHeaderDTO = OrderHeaderDTO.builder()
                .Id(1)
                .despatched(false)
                .NumberOfBricks(23)
                .OrderReference("001")
                .build();
        orderHeaderDTOS.add(orderHeaderDTO);
        OrderHeaderDTO orderHeaderDTO1 = OrderHeaderDTO.builder()
                .Id(2)
                .despatched(false)
                .NumberOfBricks(123)
                .OrderReference("002")
                .build();
        orderHeaderDTOS.add(orderHeaderDTO1);
    }

    @Test
    public void getOrder() throws Exception {
        String orderRef = "001";

        when(orderHeaderService.getOrder(orderRef)).thenReturn(orderHeaderDTOS.get(0));

        mockMvc.perform(get("/getOrder/{orderRef}", orderRef))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.numberOfBricks").value(23));
    }

    @Test
    public void getOrders() throws Exception {

        when(orderHeaderService.getOrders()).thenReturn((List) orderHeaderDTOS);

        mockMvc.perform(get("/getOrders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        System.out.println("array size " + orderHeaderDTOS.size() + " id =" + orderHeaderDTOS.get(0).getId());
        assertEquals(orderHeaderDTOS.size(), 2);
        assertEquals("Reference is 001", "001", orderHeaderDTOS.get(0).getOrderReference());
    }
}

