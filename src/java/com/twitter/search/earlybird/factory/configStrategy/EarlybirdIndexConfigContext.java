package com.twitter.search.earlybird.factory.configStrategy;

import com.twitter.search.earlybird.EarlybirdIndexConfig;
import com.twitter.search.earlybird.exception.CriticalExceptionHandler;
import com.twitter.search.earlybird.partition.SearchIndexingMetricSet;

public class EarlybirdIndexConfigContext {
    private EarlybirdIndexConfigStrategy strategy;

    public void setStrategy(EarlybirdIndexConfigStrategy strategy) {
        this.strategy = strategy;
    }

    public EarlybirdIndexConfig createEarlybirdIndexConfig(
        Decider decider, SearchIndexingMetricSet searchIndexingMetricSet,
        CriticalExceptionHandler criticalExceptionHandler) {
        return strategy.createEarlybirdIndexConfig(decider, searchIndexingMetricSet, criticalExceptionHandler);
    }
}