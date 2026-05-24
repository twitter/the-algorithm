package com.twitter.search.common.relevance.features;

public class TweetFeatures {
  private final TweetTextQuality tweetTextQuality = new TweetTextQuality();
  private final TweetTextFeatures tweetTextFeatures = new TweetTextFeatures();
  private final TweetUserFeatures tweetUserFeatures = new TweetUserFeatures();

  public TweetTextFeatures getTweetTextFeatures() {
    return tweetTextFeatures;
  }

  public TweetTextQuality getTweetTextQuality() {
    return tweetTextQuality;
  }

  public TweetUserFeatures getTweetUserFeatures() {
    return tweetUserFeatures;
  }
}
