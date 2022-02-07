package com.andres.insulinicpump.view.model;

import org.springframework.data.repository.CrudRepository;

public interface DataPointsRepository extends CrudRepository<DataPoint,Long> {
}
