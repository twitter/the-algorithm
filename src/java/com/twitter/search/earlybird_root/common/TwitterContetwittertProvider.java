package com.twitter.search.earlybird_root.common;

import javatwitter.inject.Singleton;

import scala.Option;

import com.twitter.contetwittert.TwitterContetwittert;
import com.twitter.contetwittert.thriftscala.Viewer;
import com.twitter.search.TwitterContetwittertPermit;

/**
 * This class is needed to provide an easy way for unit tests to "inject"
 * a TwitterContetwittert Viewer
 */
@Singleton
public class TwitterContetwittertProvider {
  public Option<Viewer> get() {
    return TwitterContetwittert.acquire(TwitterContetwittertPermit.get()).apply();
  }
}
