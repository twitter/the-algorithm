package com.twitter.search.common.relevance.features;

import java.util.Map;

public class TweetUserFeatures {
  private String lang;
  private double langConfidence;
  private int followers;
  private int following;
  private int reputation;
  private int tweets;
  private int retweets;
  private int retweeted;
  private Map<String, Double> knownForTopics;
  private boolean isSpam;
  private boolean isNsfw;
  private boolean isBot;

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public double getLangConfidence() {
    return langConfidence;
  }

  public void setLangConfidence(double langConfidence) {
    this.langConfidence = langConfidence;
  }

  public int getFollowers() {
    return followers;
  }

  public void setFollowers(int followers) {
    this.followers = followers;
  }

  public int getFollowing() {
    return following;
  }

  public void setFollowing(int following) {
    this.following = following;
  }

  public int getReputation() {
    return reputation;
  }

  public void setReputation(int reputation) {
    this.reputation = reputation;
  }

  public int getTweets() {
    return tweets;
  }

  public void setTweets(int tweets) {
    this.tweets = tweets;
  }

  public int getRetweets() {
    return retweets;
  }

  public void setRetweets(int retweets) {
    this.retweets = retweets;
  }

  public int getRetweeted() {
    return retweeted;
  }

  public void setRetweeted(int retweeted) {
    this.retweeted = retweeted;
  }

  public Map<String, Double> getKnownForTopics() {
    return knownForTopics;
  }

  public void setKnownForTopics(Map<String, Double> knownForTopics) {
    this.knownForTopics = knownForTopics;
  }

  public boolean isSpam() {
    return isSpam;
  }

  public void setSpam(boolean spam) {
    isSpam = spam;
  }

  public boolean isNsfw() {
    return isNsfw;
  }

  public void setNsfw(boolean nsfw) {
    isNsfw = nsfw;
  }

  public boolean isBot() {
    return isBot;
  }

  public void setBot(boolean bot) {
    isBot = bot;
  }
}
