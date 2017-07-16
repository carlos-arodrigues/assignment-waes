package com.waes.rodrigues.carlos.assignment.domain.model.diff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiffRepository extends JpaRepository<Diff, Long>{

}
