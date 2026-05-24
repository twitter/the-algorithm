package com.twitter.search.common.relevance.features;

import java.util.Set;

import com.google.common.collect.Sets;

public class TweetTextQuality {

  public static enum BooleanQualityType {
    OFFENSIVE,          // tweet text is offensive
    OFFENSIVE_USER,     // user name is offensive
    HASHTAG_NAME_MATCH,  // hashtag matches username
    SENSITIVE,           // tweet is marked as sensitive when it comes in
  }

  public static final double ENTROPY_NOT_SET = Double.MIN_VALUE;

  public static final byte UNSET_TEXT_SCORE = -128;

  private double readability;
  private double shout;
  private double entropy = ENTROPY_NOT_SET;
  private final Set<BooleanQualityType> boolQualities = Sets.newHashSet();
  private byte textScore = UNSET_TEXT_SCORE;

  public double getReadability() {
    return readability;
  }

  public void setReadability(double readability) {
    this.readability = readability;
  }

  public double getShout() {
    return shout;
  }

  public void setShout(double shout) {
    this.shout = shout;
  }

  public double getEntropy() {
    return entropy;
  }

  public void setEntropy(double entropy) {
    this.entropy = entropy;
  }

  public void addBoolQuality(BooleanQualityType type) {
    boolQualities.add(type);
  }

  public boolean hasBoolQuality(BooleanQualityType type) {
    return boolQualities.contains(type);
  }

  public Set<BooleanQualityType> getBoolQualities() {
    return boolQualities;
  }

  public byte getTextScore() {
    return textScore;
  }

  public void setTextScore(byte textScore) {
    this.textScore = textScore;
  }
}
