package com.twitter.search.earlybird.partition;

import java.io.Closeable;

import com.twitter.search.earlybird.exception.EarlybirdStartupException;

/**
 * Handles starting and indexing data for an Earlybird.
 */
@FunctionalInterface
public interface EarlybirdStartup {
  /**
   * Handles indexing Tweets, Tweet Updates and user updates. Blocks until current, and forks a
   * thread to keep the index current.
   */
  Closeable start() throws EarlybirdStartupException;
}
