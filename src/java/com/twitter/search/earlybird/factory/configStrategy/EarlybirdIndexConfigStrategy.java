package com.twitter.search.earlybird.factory.configStrategy;

import com.twitter.search.earlybird.EarlybirdIndexConfig;
import com.twitter.search.earlybird.archive.ArchiveOnDiskEarlybirdIndexConfig;
import com.twitter.search.earlybird.exception.CriticalExceptionHandler;
import com.twitter.search.earlybird.partition.SearchIndexingMetricSet;

public interface EarlybirdIndexConfigStrategy {
    EarlybirdIndexConfig createEarlybirdIndexConfig(
            Decider decider, SearchIndexingMetricSet searchIndexingMetricSet,
            CriticalExceptionHandler criticalExceptionHandler);
}

public class ArchiveOnDiskEarlybirdIndexConfigStrategy implements EarlybirdIndexConfigStrategy {
    @Override
    public EarlybirdIndexConfig createEarlybirdIndexConfig(
            Decider decider, SearchIndexingMetricSet searchIndexingMetricSet,
            CriticalExceptionHandler criticalExceptionHandler) {
        return new ArchiveOnDiskEarlybirdIndexConfig(decider, searchIndexingMetricSet, criticalExceptionHandler);
    }
}