package com.twitter.search.earlybird_root;

import javax.inject.Inject;

import com.twitter.search.common.partitioning.base.PartitionMappingManager;
import com.twitter.search.common.schema.earlybird.EarlybirdCluster;
import com.twitter.search.earlybird_root.common.EarlybirdFeatureSchemaMerger;

/**
 * The EarlybirdServiceScatterGatherSupport implementation used to fan out requests to the earlybird
 * partitions in the full archive tiers.
 */
public class EarlybirdFullArchiveScatterGatherSupport extends EarlybirdServiceScatterGatherSupport {
  /** Creates a new EarlybirdFullArchiveScatterGatherSupport instance. */
  @Inject
  EarlybirdFullArchiveScatterGatherSupport(
      PartitionMappingManager partitionMappingManager,
      EarlybirdFeatureSchemaMerger featureSchemaMerger) {
    super(partitionMappingManager, EarlybirdCluster.FULL_ARCHIVE, featureSchemaMerger);
  }
}
