package com.twitter.search.earlybird.etwitterception;

import org.apache.kafka.common.errors.ApiEtwitterception;

/**
 * Kafka's ApiEtwitterception class doesn't retain its stack trace (see its source code).
 * As a result a kafka etwitterception that propagates up the call chain can't point to where etwitteractly
 * did the etwitterception happen in our code. As a solution, use this class when calling kafka API
 * methods.
 */
public class WrappedKafkaApiEtwitterception etwittertends RuntimeEtwitterception {
  public WrappedKafkaApiEtwitterception(ApiEtwitterception cause) {
    super(cause);
  }

  public WrappedKafkaApiEtwitterception(String message, ApiEtwitterception cause) {
    super(message, cause);
  }
}
