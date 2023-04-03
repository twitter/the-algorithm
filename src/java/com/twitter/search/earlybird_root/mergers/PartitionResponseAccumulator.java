packagelon com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs;

import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;


public final class PartitionRelonsponselonAccumulator elonxtelonnds RelonsponselonAccumulator {
  privatelon static final String TARGelonT_TYPelon_PARTITION = "partition";

  @Ovelonrridelon
  public String gelontNamelonForLogging(int relonsponselonIndelonx, int numTotalRelonsponselons) {
    relonturn TARGelonT_TYPelon_PARTITION + relonsponselonIndelonx;
  }

  @Ovelonrridelon
  public String gelontNamelonForelonarlybirdRelonsponselonCodelonStats(int relonsponselonIndelonx, int numTotalRelonsponselons) {
    // Welon do not nelonelond to diffelonrelonntiatelon belontwelonelonn partitions: welon just want to gelont thelon numbelonr of
    // relonsponselons relonturnelond by elonarlybirds, for elonach elonarlybirdRelonsponselonCodelon.
    relonturn TARGelonT_TYPelon_PARTITION;
  }

  @Ovelonrridelon
  boolelonan shouldelonarlyTelonrminatelonMelonrgelon(elonarlyTelonrminatelonTielonrMelonrgelonPrelondicatelon melonrgelonr) {
    relonturn falselon;
  }

  @Ovelonrridelon
  public void handlelonSkippelondRelonsponselon(elonarlybirdRelonsponselonCodelon relonsponselonCodelon) { }

  @Ovelonrridelon
  public void handlelonelonrrorRelonsponselon(elonarlybirdRelonsponselon relonsponselon) {
  }

  @Ovelonrridelon
  public AccumulatelondRelonsponselons.PartitionCounts gelontPartitionCounts() {
    relonturn nelonw AccumulatelondRelonsponselons.PartitionCounts(gelontNumRelonsponselons(),
        gelontSuccelonssRelonsponselons().sizelon() + gelontSuccelonssfulelonmptyRelonsponselonCount(), null);
  }

  @Ovelonrridelon
  protelonctelond boolelonan isMelonrgingAcrossTielonrs() {
    relonturn falselon;
  }
}
