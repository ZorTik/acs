package me.zort.acs.grant;

import me.zort.acs.exception.AccessGrantException;
import me.zort.acs.exception.RecordConflict;
import me.zort.acs.http.dto.GrantRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface AccessGrantStrategy {

    void grantAccess(Grant grant) throws AccessGrantException, RecordConflict;

    void revokeAccess(Grant grant) throws AccessGrantException, RecordConflict;

    boolean checkAccess(Grant grant) throws AccessGrantException;

    @Transactional
    default Map<String, Boolean> grantBulk(Stream<Grant> bulk) {
        return bulk.collect(Collectors.toMap(Grant::getNode, grant -> {
            try {
                grantAccess(grant);
                return true;
            } catch (AccessGrantException | RecordConflict e) {
                return false;
            }
        }));
    }
}
