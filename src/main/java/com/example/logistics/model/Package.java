package com.example.logistics.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "package")
@ToString
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String packageName;
    private String origin;
    private String destination;
    private String optimalRoute;
    private BigDecimal cost;
}
