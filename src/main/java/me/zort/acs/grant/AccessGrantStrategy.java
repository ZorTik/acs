package me.zort.acs.grant;

import me.zort.acs.exception.AccessGrantException;
import me.zort.acs.exception.RecordConflict;

public interface AccessGrantStrategy {

    void grantAccess(Grant grant) throws AccessGrantException, RecordConflict;

    void revokeAccess(Grant grant) throws AccessGrantException, RecordConflict;

    boolean checkAccess(Grant grant) throws AccessGrantException;
}
