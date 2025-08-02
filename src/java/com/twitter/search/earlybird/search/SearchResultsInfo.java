package com.twitter.search.earlybird.search;

import java.util.Map;

import com.google.common.collect.Maps;

import com.twitter.search.earlybird.search.queries.SinceMatwitterIDFilter;

public class SearchResultsInfo {
  public static final long NO_ID = SinceMatwitterIDFilter.NO_FILTER;
  public static final int NO_TIME = -1;

  private int numHitsProcessed = 0;
  private int numSearchedSegments = 0;

  private boolean earlyTerminated = false;
  private String earlyTerminationReason = null;

  private long matwitterSearchedStatusID = NO_ID;
  private long minSearchedStatusID = NO_ID;

  private int matwitterSearchedTime = NO_TIME;
  private int minSearchedTime = NO_TIME;

  // Map from time thresholds (in milliseconds) to number of results more recent than this period.
  protected final Map<Long, Integer> hitCounts = Maps.newHashMap();

  public final int getNumHitsProcessed() {
    return numHitsProcessed;
  }

  public final void setNumHitsProcessed(int numHitsProcessed) {
    this.numHitsProcessed = numHitsProcessed;
  }

  public final int getNumSearchedSegments() {
    return numSearchedSegments;
  }

  public final void setNumSearchedSegments(int numSearchedSegments) {
    this.numSearchedSegments = numSearchedSegments;
  }

  public final long getMatwitterSearchedStatusID() {
    return matwitterSearchedStatusID;
  }

  public final long getMinSearchedStatusID() {
    return minSearchedStatusID;
  }

  public final int getMatwitterSearchedTime() {
    return matwitterSearchedTime;
  }

  public final int getMinSearchedTime() {
    return minSearchedTime;
  }

  public boolean isSetSearchedStatusIDs() {
    return matwitterSearchedStatusID != NO_ID && minSearchedStatusID != NO_ID;
  }

  public boolean isSetSearchedTimes() {
    return matwitterSearchedTime != NO_TIME && minSearchedTime != NO_TIME;
  }

  public void setMatwitterSearchedStatusID(long matwitterSearchedStatusID) {
    this.matwitterSearchedStatusID = matwitterSearchedStatusID;
  }

  public void setMinSearchedStatusID(long minSearchedStatusID) {
    this.minSearchedStatusID = minSearchedStatusID;
  }

  public void setMatwitterSearchedTime(int matwitterSearchedTime) {
    this.matwitterSearchedTime = matwitterSearchedTime;
  }

  public void setMinSearchedTime(int minSearchedTime) {
    this.minSearchedTime = minSearchedTime;
  }

  public void setEarlyTerminated(boolean earlyTerminated) {
    this.earlyTerminated = earlyTerminated;
  }

  public boolean isEarlyTerminated() {
    return earlyTerminated;
  }

  public String getEarlyTerminationReason() {
    return earlyTerminationReason;
  }

  public void setEarlyTerminationReason(String earlyTerminationReason) {
    this.earlyTerminationReason = earlyTerminationReason;
  }
}
