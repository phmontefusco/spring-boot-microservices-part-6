package com.programmingtechie.orderservice.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent implements Serializable {
    private static final long serialVersionUID = 7387393848075097796L;

    private String orderNumber;
}