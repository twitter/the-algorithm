package com.twitter.search.ingester.pipeline.wire;

import javax.naming.NamingException;

import com.twitter.search.common.partitioning.base.PartitionMappingManager;
import com.twitter.search.common.util.io.kafka.SearchPartitioner;

/**
 * A variant of {@code SearchPartitioner} which retrieves {@code PartitionMappingManager} from
 * {@code WireModule}.
 *
 * Note that the value object has to implement {@code Partitionable}.
 */
public class IngesterPartitioner extends SearchPartitioner {

  public IngesterPartitioner() {
    super(getPartitionMappingManager());
  }

  private static PartitionMappingManager getPartitionMappingManager() {
    try {
      return WireModule.getWireModule().getPartitionMappingManager();
    } catch (NamingException e) {
      throw new RuntimeException(e);
    }
  }
}
