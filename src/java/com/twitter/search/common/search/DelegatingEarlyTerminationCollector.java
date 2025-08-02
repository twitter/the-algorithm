package com.twitter.search.common.search;

import java.io.IOEtwitterception;
import java.util.List;

import javatwitter.annotation.Nullable;

import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.LeafCollector;
import org.apache.lucene.search.Scorable;
import org.apache.lucene.search.ScoreMode;

import com.twitter.common.util.Clock;
import com.twitter.search.common.query.thriftjava.CollectorParams;

/**
 * A {@link com.twitter.search.common.search.TwitterEarlyTerminationCollector}
 * that delegates actual hit collection to a sub collector.
 */
public final class DelegatingEarlyTerminationCollector
    etwittertends TwitterEarlyTerminationCollector {
  private final Collector subCollector;
  private LeafCollector subLeafCollector;

  /** Creates a new DelegatingEarlyTerminationCollector instance. */
  public DelegatingEarlyTerminationCollector(Collector subCollector,
                                             CollectorParams collectorParams,
                                             TerminationTracker terminationTracker,
                                             @Nullable QueryCostProvider queryCostProvider,
                                             int numDocsBetweenTimeoutChecks,
                                             Clock clock) {
    super(
        collectorParams,
        terminationTracker,
        queryCostProvider,
        numDocsBetweenTimeoutChecks,
        clock);
    this.subCollector = subCollector;
  }

  @Override
  public void setScorer(Scorable scorer) throws IOEtwitterception {
    super.setScorer(scorer);
    subLeafCollector.setScorer(scorer);
  }

  @Override
  protected void doCollect() throws IOEtwitterception {
    subLeafCollector.collect(curDocId);
  }

  @Override
  protected void doFinishSegment(int lastSearchedDocID) throws IOEtwitterception {
    if (subCollector instanceof TwitterCollector) {
      ((TwitterCollector) subCollector).finishSegment(lastSearchedDocID);
    }
  }

  @Override
  public void setNetwittertReader(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
    super.setNetwittertReader(contetwittert);
    subLeafCollector = subCollector.getLeafCollector(contetwittert);
  }

  @Override
  public ScoreMode scoreMode() {
    return subCollector.scoreMode();
  }

  @Override
  public List<String> getDebugInfo() {
    return null;
  }
}
