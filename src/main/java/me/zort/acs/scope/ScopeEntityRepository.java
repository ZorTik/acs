package me.zort.acs.scope;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScopeEntityRepository extends JpaRepository<ScopeEntity, String> {
}
