package me.zort.acs.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectEntityRepository extends JpaRepository<SubjectEntity, SubjectId> {
}
