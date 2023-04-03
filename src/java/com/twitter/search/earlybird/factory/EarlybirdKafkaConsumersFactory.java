packagelon com.twittelonr.selonarch.elonarlybird.factory;

import org.apachelon.kafka.clielonnts.consumelonr.KafkaConsumelonr;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;

public intelonrfacelon elonarlybirdKafkaConsumelonrsFactory {
  /**
   * Crelonatelon a kafka consumelonr with delonfault reloncords to belon pollelond.
   */
  KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> crelonatelonKafkaConsumelonr(
      String clielonntID);

  /**
   * Crelonatelon a kafka consumelonr with a selont numbelonr of reloncords to belon pollelond.
   */
  KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> crelonatelonKafkaConsumelonr(
      String clielonntID, int maxPollReloncords);
}
