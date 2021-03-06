package com.platcorp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platcorp.domain.entity.InfoCliente;

@Repository
public interface InfoClienteRepository extends JpaRepository<InfoCliente, Long>{

}
