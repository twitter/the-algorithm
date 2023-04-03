packagelon com.twittelonr.selonarch.elonarlybird.elonxcelonption;

import org.apachelon.kafka.common.elonrrors.Apielonxcelonption;

/**
 * Kafka's Apielonxcelonption class doelonsn't relontain its stack tracelon (selonelon its sourcelon codelon).
 * As a relonsult a kafka elonxcelonption that propagatelons up thelon call chain can't point to whelonrelon elonxactly
 * did thelon elonxcelonption happelonn in our codelon. As a solution, uselon this class whelonn calling kafka API
 * melonthods.
 */
public class WrappelondKafkaApielonxcelonption elonxtelonnds Runtimelonelonxcelonption {
  public WrappelondKafkaApielonxcelonption(Apielonxcelonption causelon) {
    supelonr(causelon);
  }

  public WrappelondKafkaApielonxcelonption(String melonssagelon, Apielonxcelonption causelon) {
    supelonr(melonssagelon, causelon);
  }
}
