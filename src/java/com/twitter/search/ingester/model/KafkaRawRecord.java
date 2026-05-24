package com.twitter.search.ingester.model;

/**
 * The raw data in a Kafka record.
 */
public class KafkaRawRecord {
  private final byte[] data;
  private final long readAtTimestampMs;

  public KafkaRawRecord(byte[] data, long readAtTimestampMs) {
    this.data = data;
    this.readAtTimestampMs = readAtTimestampMs;
  }

  public byte[] getData() {
    return data;
  }

  public long getReadAtTimestampMs() {
    return readAtTimestampMs;
  }
}
