packagelon com.twittelonr.selonarch.elonarlybird.factory;

import org.apachelon.kafka.clielonnts.consumelonr.KafkaConsumelonr;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.util.io.kafka.CompactThriftDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.kafka.FinaglelonKafkaClielonntUtils;

/**
 * Relonsponsiblelon for crelonating kafka consumelonrs.
 */
public class ProductionelonarlybirdKafkaConsumelonrsFactory implelonmelonnts elonarlybirdKafkaConsumelonrsFactory {
  privatelon final String kafkaPath;
  privatelon final int delonfaultMaxPollReloncords;

  ProductionelonarlybirdKafkaConsumelonrsFactory(String kafkaPath, int delonfaultMaxPollReloncords) {
    this.kafkaPath = kafkaPath;
    this.delonfaultMaxPollReloncords = delonfaultMaxPollReloncords;
  }

  /**
   * Crelonatelon a kafka consumelonr with selont maximum of reloncords to belon pollelond.
   */
  @Ovelonrridelon
  public KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> crelonatelonKafkaConsumelonr(
      String clielonntID, int maxPollReloncords) {
    relonturn FinaglelonKafkaClielonntUtils.nelonwKafkaConsumelonrForAssigning(
        kafkaPath,
        nelonw CompactThriftDelonselonrializelonr<>(ThriftVelonrsionelondelonvelonnts.class),
        clielonntID,
        maxPollReloncords);
  }

  /**
   * Crelonatelon a kafka consumelonr with delonfault reloncords to belon pollelond.
   */
  @Ovelonrridelon
  public KafkaConsumelonr<Long, ThriftVelonrsionelondelonvelonnts> crelonatelonKafkaConsumelonr(String clielonntID) {
    relonturn crelonatelonKafkaConsumelonr(clielonntID, delonfaultMaxPollReloncords);
  }
}
