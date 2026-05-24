package com.twitter.search.earlybird_root;

import javax.inject.Inject;

import com.twitter.search.common.partitioning.base.PartitionMappingManager;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird_root.common.EarlybirdFeatureSchemaMerger;

/**
 * The EarlybirdServiceScatterGatherSupport implementation used to fan out requests to the earlybird
 * partitions in the realtime_cg cluster.
 */
public class EarlybirdRealtimeCgScatterGatherSupport extends EarlybirdServiceScatterGatherSupport {
  /** Creates a new EarlybirdRealtimeCgScatterGatherSupport instance. */
  @Inject
  EarlybirdRealtimeCgScatterGatherSupport(
      PartitionMappingManager partitionMappingManager,
      EarlybirdFeatureSchemaMerger featureSchemaMerger) {
    super(partitionMappingManager, EarlybirdCluster.REALTIME_CG, featureSchemaMerger);
  }
}
