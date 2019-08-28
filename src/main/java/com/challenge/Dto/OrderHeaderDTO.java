package com.challenge.Dto;

import lombok.*;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderHeaderDTO {
    private Integer Id;
    private String OrderReference ;
    private boolean despatched;
    private Integer NumberOfBricks;
}
