package com.twitter.search.common.relevance.features;

public class TweetFeatures {
  private final TweetTetwittertQuality tweetTetwittertQuality = new TweetTetwittertQuality();
  private final TweetTetwittertFeatures tweetTetwittertFeatures = new TweetTetwittertFeatures();
  private final TweetUserFeatures tweetUserFeatures = new TweetUserFeatures();

  public TweetTetwittertFeatures getTweetTetwittertFeatures() {
    return tweetTetwittertFeatures;
  }

  public TweetTetwittertQuality getTweetTetwittertQuality() {
    return tweetTetwittertQuality;
  }

  public TweetUserFeatures getTweetUserFeatures() {
    return tweetUserFeatures;
  }
}
