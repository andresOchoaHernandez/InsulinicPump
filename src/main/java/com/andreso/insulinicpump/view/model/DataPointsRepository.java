package com.andreso.insulinicpump.view.model;

import org.springframework.data.repository.CrudRepository;

public interface DataPointsRepository extends CrudRepository<DataPoint,Long> {
}
