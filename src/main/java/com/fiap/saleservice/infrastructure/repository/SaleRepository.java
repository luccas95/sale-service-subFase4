package com.fiap.saleservice.infrastructure.repository;

import com.fiap.saleservice.domain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findById(Long id);
}
