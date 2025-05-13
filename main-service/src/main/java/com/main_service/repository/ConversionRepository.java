package com.main_service.repository;

import com.main_service.model.Conversion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//conversionRepository interface to interact with the database
@Repository
public interface ConversionRepository extends CrudRepository<Conversion, Long> {
    // methods to interact with the database
}
