package com.challenge.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer Id;
    private String OrderReference ;
    private boolean despatched;
    private Integer NumberOfBricks;
}

