package com.X.search.earlybird.exception;

import com.X.common.zookeeper.ServerSet;

/**
 * Used when trying to join a server set when this earlybird is already in a server set.
 */
public class AlreadyInServerSetUpdateException extends ServerSet.UpdateException {
  public AlreadyInServerSetUpdateException(String message) {
    super(message);
  }
}
