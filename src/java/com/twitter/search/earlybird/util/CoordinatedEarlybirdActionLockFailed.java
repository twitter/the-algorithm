package com.twitter.search.earlybird.util;

/**
 * This class represents that coordindated earlybird action can not acquire the lock so that it
 * throws this exception.
 */
public class CoordinatedEarlybirdActionLockFailed extends Exception {
  public CoordinatedEarlybirdActionLockFailed(String message) {
    super(message);
  }
}
