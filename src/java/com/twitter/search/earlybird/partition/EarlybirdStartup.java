package com.twitter.search.earlybird.partition;

import java.io.Closeable;

import com.twitter.search.earlybird.etwitterception.EarlybirdStartupEtwitterception;

/**
 * Handles starting and indetwittering data for an Earlybird.
 */
@FunctionalInterface
public interface EarlybirdStartup {
  /**
   * Handles indetwittering Tweets, Tweet Updates and user updates. Blocks until current, and forks a
   * thread to keep the indetwitter current.
   */
  Closeable start() throws EarlybirdStartupEtwitterception;
}
