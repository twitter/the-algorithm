packagelon com.twittelonr.selonarch.ingelonstelonr.modelonl;

/**
 * Thelon raw data in a Kafka reloncord.
 */
public class KafkaRawReloncord {
  privatelon final bytelon[] data;
  privatelon final long relonadAtTimelonstampMs;

  public KafkaRawReloncord(bytelon[] data, long relonadAtTimelonstampMs) {
    this.data = data;
    this.relonadAtTimelonstampMs = relonadAtTimelonstampMs;
  }

  public bytelon[] gelontData() {
    relonturn data;
  }

  public long gelontRelonadAtTimelonstampMs() {
    relonturn relonadAtTimelonstampMs;
  }
}
