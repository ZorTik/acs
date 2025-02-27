package me.zort.acs.rights;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RightsEntityRepository extends JpaRepository<RightsEntity, RightsId> {

    @Query("SELECT COUNT(r) > 0 FROM RightsEntity r" +
            " WHERE r.actorId = :actorId" +
            " AND r.scopeId = :scopeId" +
            " AND r.resourceId = :resourceId" +
            " AND SUBSTRING(:inputNode, 1, LENGTH(r.node)) = r.node")
    boolean existsByActorIdAndScopeIdAndResourceIdAndNodeIsPrefixOf(
            @Param("actorId") String actorId,
            @Param("scopeId") String scopeId,
            @Param("resourceId") String resourceId,
            @Param("inputNode") String node);

    @Modifying
    @Query("DELETE FROM RightsEntity r" +
            " WHERE r.actorId = :actorId" +
            " AND r.scopeId = :scopeId" +
            " AND r.resourceId = :resourceId" +
            " AND SUBSTRING(:inputNode, 1, LENGTH(r.node)) = r.node")
    void deleteByActorIdAndScopeIdAndResourceIdAndNodeIsPrefixOf(
            @Param("actorId") String actorId,
            @Param("scopeId") String scopeId,
            @Param("resourceId") String resourceId,
            @Param("inputNode") String node);

    @Modifying
    @Transactional
    @Query("DELETE FROM RightsEntity r" +
            " WHERE r.actorId = :actorId" +
            " AND r.scopeId = :scopeId" +
            " AND r.resourceId = :resourceId" +
            " AND r.node LIKE CONCAT(:inputNode, '%')")
    void deleteAllWhereNodeStartsWith(
            @Param("actorId") String actorId,
            @Param("scopeId") String scopeId,
            @Param("resourceId") String resourceId,
            @Param("inputNode") String inputNode);
}
