package com.uberClone.uberClone.repositories;

import com.uberClone.uberClone.entities.DriverView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverViewRepository extends JpaRepository<DriverView, Long> {
    List<DriverView> findAll();

    List<DriverView> findAllById(Iterable<Long> longs);
    List<DriverView> findAllByStatusContainingAndSocketIdNotNullOrderByIdAsc(String contains);

    List<DriverView> findAll(Sort sort);

    Page<DriverView> findAll(Pageable pageable);
}
