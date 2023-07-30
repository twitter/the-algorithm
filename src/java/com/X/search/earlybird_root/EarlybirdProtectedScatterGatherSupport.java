package com.X.search.earlybird_root;

import javax.inject.Inject;

import com.X.search.common.partitioning.base.PartitionMappingManager;
import com.X.search.common.schema.earlybird.EarlybirdCluster;
import com.X.search.earlybird_root.common.EarlybirdFeatureSchemaMerger;

/**
 * The EarlybirdServiceScatterGatherSupport implementation used to fan out requests to the earlybird
 * partitions in the protected cluster.
 */
public class EarlybirdProtectedScatterGatherSupport extends EarlybirdServiceScatterGatherSupport {
  /**
   * Construct a EarlybirdProtectedScatterGatherSupport to do minUserFanOut,
   * used only by protected. The main difference from the base class is that
   * if the from user ID is not set, exception is thrown.
   */
  @Inject
  EarlybirdProtectedScatterGatherSupport(
      PartitionMappingManager partitionMappingManager,
      EarlybirdFeatureSchemaMerger featureSchemaMerger) {
    super(partitionMappingManager, EarlybirdCluster.PROTECTED, featureSchemaMerger);
  }
}
