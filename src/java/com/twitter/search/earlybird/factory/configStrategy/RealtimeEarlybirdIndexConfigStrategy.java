package com.twitter.search.earlybird.factory.configStrategy;

import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird.EarlybirdIndexConfig;
import com.twitter.search.earlybird.RealtimeEarlybirdIndexConfig;
import com.twitter.search.earlybird.exception.CriticalExceptionHandler;
import com.twitter.search.earlybird.partition.SearchIndexingMetricSet;

public class RealtimeEarlybirdIndexConfigStrategy implements EarlybirdIndexConfigStrategy {
    private EarlybirdCluster earlybirdCluster;

    public RealtimeEarlybirdIndexConfigStrategy(EarlybirdCluster earlybirdCluster) {
        this.earlybirdCluster = earlybirdCluster;
    }

    @Override
    public EarlybirdIndexConfig createEarlybirdIndexConfig(
        Decider decider, SearchIndexingMetricSet searchIndexingMetricSet,
        CriticalExceptionHandler criticalExceptionHandler) {
        return new RealtimeEarlybirdIndexConfig(
            earlybirdCluster, decider, searchIndexingMetricSet, criticalExceptionHandler);
    }
}