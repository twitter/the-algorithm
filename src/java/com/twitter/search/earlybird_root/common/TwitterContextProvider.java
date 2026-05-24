package com.twitter.search.earlybird_root.common;

import javax.inject.Singleton;

import scala.Option;

import com.twitter.context.TwitterContext;
import com.twitter.context.thriftscala.Viewer;
import com.twitter.search.TwitterContextPermit;

/**
 * This class is needed to provide an easy way for unit tests to "inject"
 * a TwitterContext Viewer
 */
@Singleton
public class TwitterContextProvider {
  public Option<Viewer> get() {
    return TwitterContext.acquire(TwitterContextPermit.get()).apply();
  }
}
