package com.twitter.search.earlybird.partition;

/**
 * An exception thrown when the earlybird layout could not be loaded, or when a host cannot find
 * itself in the layout, and the layout has errors (which might be the reason why the host could not
 * find itself in the layout).
 */
public class PartitionConfigLoadingException extends Exception {
  public PartitionConfigLoadingException(String message) {
    super(message);
  }
}
