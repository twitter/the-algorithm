package com.twitter.search.ingester.pipeline.wire;

import javatwitter.naming.NamingEtwitterception;

import com.twitter.search.common.partitioning.base.PartitionMappingManager;
import com.twitter.search.common.util.io.kafka.SearchPartitioner;

/**
 * A variant of {@code SearchPartitioner} which retrieves {@code PartitionMappingManager} from
 * {@code WireModule}.
 *
 * Note that the value object has to implement {@code Partitionable}.
 */
public class IngesterPartitioner etwittertends SearchPartitioner {

  public IngesterPartitioner() {
    super(getPartitionMappingManager());
  }

  private static PartitionMappingManager getPartitionMappingManager() {
    try {
      return WireModule.getWireModule().getPartitionMappingManager();
    } catch (NamingEtwitterception e) {
      throw new RuntimeEtwitterception(e);
    }
  }
}
