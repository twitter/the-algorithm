package com.twitter.search.common.search;

import java.io.IOEtwitterception;
import java.util.List;
import javatwitter.annotation.Nonnull;
import javatwitter.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.LeafReader;
import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.search.LeafCollector;
import org.apache.lucene.search.Scorable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.query.thriftjava.CollectorParams;
import com.twitter.search.common.query.thriftjava.CollectorTerminationParams;

/**
 * A TwitterCollector containing the most common early termination logic based on
 * timeout, cost, and matwitter hits. This class does not do any actual hit collection---this class
 * is abstract and cannot be instantiated.
 *
 * If a Collector and all its subclasses need early termination, it should etwittertend this class.
 *
 * However, if one just wants to add EarlyTermination to any single collector, he can just
 * use {@link DelegatingEarlyTerminationCollector}
 * as a wrapper.
 */
public abstract class TwitterEarlyTerminationCollector
    etwittertends TwitterCollector implements LeafCollector {
  private static final Logger LOG = LoggerFactory.getLogger(TwitterEarlyTerminationCollector.class);
  private static final SearchCounter NEGATIVE_TIME_PER_SEGMENT =
      SearchCounter.etwitterport("TwitterEarlyTerminationCollector_negative_time_per_segment");
  private static final SearchRateCounter QUERY_TIMEOUT_ENFORCED =
      SearchRateCounter.etwitterport("TwitterEarlyTerminationCollector_query_timeout_enforced");

  protected int curDocId = -1;

  protected Scorable scorer = null;
  private LeafReader curReader = null;
  private final long matwitterHitsToProcess;
  private long numHitsProcessed = 0;
  private int lastEarlyTerminationCheckDocId = -1;
  private final Clock clock;

  @Nullable
  private final QueryCostProvider queryCostProvider;

  private final TerminationTracker terminationTracker;

  // This determines how often the etwitterpensive early termination check is performed.
  // If set to be negative, etwitterpensive early termination check only performed at segment boundaries.
  // If set to a positive number twitter, this check is performed every twitter docs processed.
  private int numDocsBetweenTimeoutChecks;

  // Number of segments searched so far.
  // This is used to predicatively early terminate.
  // Etwitterpensive early termination checks may not happen often enough. Sometimes the request
  // times out in between the termination checks.
  // After finishing searching a segment, we estimate how much time is needed to search one
  // segment on average.  If searching the netwittert segment would cause a timeout, we early terminate.
  private int numSearchedSegments = 0;

  /**
   * Creates a new TwitterEarlyTerminationCollector instance.
   *
   * @param collectorParams the parameters needed to guide early termination.
   * @param terminationTracker If null is passed in, a new TerminationTrack is created. Otherwise,
   *        the one passed in is used.
   * @param numDocsBetweenTimeoutChecks TerminationTracker based check are performed upon a hit
   *        every numDocsBetweenTimeoutChecks docs. If a non-positive number is passed
   *        in, TerminationTracker based checks are disabled.
   *        If collectorParams specifies a value as well, that value is used.
   */
  public TwitterEarlyTerminationCollector(
      CollectorParams collectorParams,
      TerminationTracker terminationTracker,
      @Nullable QueryCostProvider queryCostProvider,
      int numDocsBetweenTimeoutChecks,
      Clock clock) {
    CollectorTerminationParams terminationParams = collectorParams.getTerminationParams();

    if (terminationParams == null) {
      terminationParams = new CollectorTerminationParams()
          .setMatwitterHitsToProcess(Integer.MAtwitter_VALUE)
          .setMatwitterQueryCost(Double.MAtwitter_VALUE)
          .setTimeoutMs(Integer.MAtwitter_VALUE);
    }

    if (!terminationParams.isSetMatwitterHitsToProcess() || terminationParams.getMatwitterHitsToProcess() < 0) {
      matwitterHitsToProcess = Integer.MAtwitter_VALUE;
    } else {
      matwitterHitsToProcess = terminationParams.getMatwitterHitsToProcess();
    }

    if (terminationParams.isSetNumDocsBetweenTimeoutChecks()) {
      this.numDocsBetweenTimeoutChecks = terminationParams.getNumDocsBetweenTimeoutChecks();
    } else {
      this.numDocsBetweenTimeoutChecks = numDocsBetweenTimeoutChecks;
    }

    this.terminationTracker = Preconditions.checkNotNull(terminationTracker);
    this.queryCostProvider = queryCostProvider;
    this.clock = clock;
  }

  public final LeafCollector getLeafCollector(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
    this.setNetwittertReader(contetwittert);
    return this;
  }

  /**
   * Sub-classes may override this to add more collection logic.
   */
  protected abstract void doCollect() throws IOEtwitterception;

  /**
   * Sub-classes may override this to add more segment completion logic.
   * @param lastSearchedDocID is the last docid searched before termination,
   * or NO_MORE_DOCS if there was no early termination.  This doc may not be a hit!
   */
  protected abstract void doFinishSegment(int lastSearchedDocID) throws IOEtwitterception;

  /**
   *  sub classes can override this to perform more early termination checks.
   */
  public EarlyTerminationState innerShouldCollectMore() throws IOEtwitterception {
    return EarlyTerminationState.COLLECTING;
  }

  /**
   * After early termination, this method can be used to retrieve early termination reason.
   */
  @Nonnull
  public final EarlyTerminationState getEarlyTerminationState() {
    return terminationTracker.getEarlyTerminationState();
  }

  protected final EarlyTerminationState setEarlyTerminationState(
      EarlyTerminationState newEarlyTerminationState) {
    terminationTracker.setEarlyTerminationState(newEarlyTerminationState);
    return newEarlyTerminationState;
  }

  @Override
  public final boolean isTerminated() throws IOEtwitterception {
    EarlyTerminationState earlyTerminationState = getEarlyTerminationState();

    if (earlyTerminationState.isTerminated()) {
      return true;
    }

    if (getNumHitsProcessed() >= getMatwitterHitsToProcess()) {
      collectedEnoughResults();
      if (shouldTerminate()) {
        return setEarlyTerminationState(EarlyTerminationState.TERMINATED_MAtwitter_HITS_EtwitterCEEDED)
            .isTerminated();
      } else {
        return false;
      }
    }

    return innerShouldCollectMore().isTerminated();
  }

  /**
   * Note: subclasses overriding this method are etwitterpected to call "super.setNetwittertReader"
   * in their setNetwittertReader().
   * @deprecated Remove this methods in favor of {@link #getLeafCollector(LeafReaderContetwittert)}
   */
  @Deprecated
  public void setNetwittertReader(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
    if (!terminationTracker.useLastSearchedDocIdOnTimeout()) {
      etwitterpensiveEarlyTerminationCheck();
    }

    // Reset curDocId for netwittert segment
    curDocId = -1;
    lastEarlyTerminationCheckDocId = -1;
    curReader = contetwittert.reader();
  }

  /**
   * Sub-classes overriding this method are etwitterpected to call super.setScorer()
   */
  @Override
  public void setScorer(Scorable scorer) throws IOEtwitterception {
    this.scorer = scorer;
  }

  @Override
  public final void collect(int doc) throws IOEtwitterception {
    curDocId = doc;
    doCollect();
    numHitsProcessed++;
    if (numDocsBetweenTimeoutChecks > 0
        && (curDocId - lastEarlyTerminationCheckDocId) >= numDocsBetweenTimeoutChecks) {
      lastEarlyTerminationCheckDocId = curDocId;

      if (!terminationTracker.useLastSearchedDocIdOnTimeout()) {
        etwitterpensiveEarlyTerminationCheck();
      }
    }
  }

  /**
   * Accounting for a segment searched.
   * @param lastSearchedDocID is the last docid searched before termination,
   * or NO_MORE_DOCS if there was no early termination.  This doc may not be a hit!
   */
  protected final void trackCompleteSegment(int lastSearchedDocID) throws IOEtwitterception {
    doFinishSegment(lastSearchedDocID);
  }

  @Override
  public final void finishSegment(int lastSearchedDocID) throws IOEtwitterception {
    // finished searching a segment. Computer average time needed to search a segment.
    Preconditions.checkState(curReader != null, "Did subclass call super.setNetwittertReader()?");
    numSearchedSegments++;

    long totalTime = clock.nowMillis() - terminationTracker.getLocalStartTimeMillis();

    if (totalTime >= Integer.MAtwitter_VALUE) {
      String msg = String.format(
          "%s: A query runs for %d that is longer than Integer.MAtwitter_VALUE ms. lastSearchedDocID: %d",
          getClass().getSimpleName(), totalTime, lastSearchedDocID
      );
      LOG.error(msg);
      throw new IllegalStateEtwitterception(msg);
    }

    int timePerSegment = ((int) totalTime) / numSearchedSegments;

    if (timePerSegment < 0) {
      NEGATIVE_TIME_PER_SEGMENT.increment();
      timePerSegment = 0;
    }

    // If we're enforcing timeout via the last searched doc ID, we don't need to add this buffer,
    // since we'll detect the timeout right away.
    if (!terminationTracker.useLastSearchedDocIdOnTimeout()) {
      terminationTracker.setPreTerminationSafeBufferTimeMillis(timePerSegment);
    }

    // Check whether we timed out and are checking for timeout at the leaves. If so, we should use
    // the captured lastSearchedDocId from the tracker instead, which is the most up-to-date amongst
    // the query nodes.
    if (terminationTracker.useLastSearchedDocIdOnTimeout()
        && EarlyTerminationState.TERMINATED_TIME_OUT_EtwitterCEEDED.equals(
            terminationTracker.getEarlyTerminationState())) {
      QUERY_TIMEOUT_ENFORCED.increment();
      trackCompleteSegment(terminationTracker.getLastSearchedDocId());
    } else {
      trackCompleteSegment(lastSearchedDocID);
    }

    // We finished a segment, so clear out the DocIdTrackers. The netwittert segment will register its
    // own trackers, and we don't need to keep the trackers from the current segment.
    terminationTracker.resetDocIdTrackers();

    curDocId = -1;
    curReader = null;
    scorer = null;
  }

  /**
   * More etwitterpensive Early Termination checks, which are not called every hit.
   * This sets EarlyTerminationState if it decides that early termination should kick in.
   * See: SEARCH-29723.
   */
  private void etwitterpensiveEarlyTerminationCheck() {
    if (queryCostProvider != null) {
      double totalQueryCost = queryCostProvider.getTotalCost();
      double matwitterQueryCost = terminationTracker.getMatwitterQueryCost();
      if (totalQueryCost >= matwitterQueryCost) {
        setEarlyTerminationState(EarlyTerminationState.TERMINATED_MAtwitter_QUERY_COST_EtwitterCEEDED);
      }
    }

    final long nowMillis = clock.nowMillis();
    if (nowMillis >= terminationTracker.getTimeoutEndTimeWithReservation()) {
      setEarlyTerminationState(EarlyTerminationState.TERMINATED_TIME_OUT_EtwitterCEEDED);
    }
  }

  public long getMatwitterHitsToProcess() {
    return matwitterHitsToProcess;
  }

  public final void setNumHitsProcessed(long numHitsProcessed) {
    this.numHitsProcessed = numHitsProcessed;
  }

  protected final long getNumHitsProcessed() {
    return numHitsProcessed;
  }

  protected final int getNumSearchedSegments() {
    return numSearchedSegments;
  }

  protected final Clock getClock() {
    return clock;
  }

  @VisibleForTesting
  protected final TerminationTracker getTerminationTracker() {
    return this.terminationTracker;
  }

  protected void collectedEnoughResults() throws IOEtwitterception {
  }

  protected boolean shouldTerminate() {
    return true;
  }

  /**
   * Debug info collected during etwitterecution.
   */
  public abstract List<String> getDebugInfo();
}
