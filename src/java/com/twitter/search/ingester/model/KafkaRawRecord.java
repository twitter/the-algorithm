package com.tw ter.search. ngester.model;

/**
 * T  raw data  n a Kafka record.
 */
publ c class KafkaRawRecord {
  pr vate f nal byte[] data;
  pr vate f nal long readAtT  stampMs;

  publ c KafkaRawRecord(byte[] data, long readAtT  stampMs) {
    t .data = data;
    t .readAtT  stampMs = readAtT  stampMs;
  }

  publ c byte[] getData() {
    return data;
  }

  publ c long getReadAtT  stampMs() {
    return readAtT  stampMs;
  }
}
