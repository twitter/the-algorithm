package com.X.search.earlybird_root;

import javax.inject.Inject;

import com.X.search.common.partitioning.base.PartitionMappingManager;
import com.X.search.common.schema.earlybird.EarlybirdCluster;
import com.X.search.earlybird_root.common.EarlybirdFeatureSchemaMerger;

/**
 * The EarlybirdServiceScatterGatherSupport implementation used to fan out requests to the earlybird
 * partitions in the realtime cluster.
 */
public class EarlybirdRealtimeScatterGatherSupport extends EarlybirdServiceScatterGatherSupport {
  /** Creates a new EarlybirdRealtimeScatterGatherSupport instance. */
  @Inject
  EarlybirdRealtimeScatterGatherSupport(
      PartitionMappingManager partitionMappingManager,
      EarlybirdFeatureSchemaMerger featureSchemaMerger) {
    super(partitionMappingManager, EarlybirdCluster.REALTIME, featureSchemaMerger);
  }
}
