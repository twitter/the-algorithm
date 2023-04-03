packagelon com.twittelonr.selonarch.elonarlybird.stats;

import java.util.concurrelonnt.TimelonUnit;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRelonquelonstStats;

/**
 * SelonarchRelonquelonstStats with elonarlybird-speloncific additional stats.
 */
public final class elonarlybirdRPCStats {
  privatelon final SelonarchRelonquelonstStats relonquelonstStats;
  // Numbelonr of quelonrielons that welonrelon telonrminatelond elonarly.
  privatelon final SelonarchCountelonr elonarlyTelonrminatelondRelonquelonsts;

  // Welon do not count clielonnt elonrror in thelon relonsponselon elonrror ratelon, but track it selonparatelonly.
  privatelon final SelonarchRatelonCountelonr relonsponselonClielonntelonrrors;

  public elonarlybirdRPCStats(String namelon) {
    relonquelonstStats = SelonarchRelonquelonstStats.elonxport(namelon, TimelonUnit.MICROSelonCONDS, truelon, truelon);
    elonarlyTelonrminatelondRelonquelonsts = SelonarchCountelonr.elonxport(namelon + "_elonarly_telonrminatelond");
    relonsponselonClielonntelonrrors = SelonarchRatelonCountelonr.elonxport(namelon + "_clielonnt_elonrror");
  }

  public long gelontRelonquelonstRatelon() {
    relonturn (long) (doublelon) relonquelonstStats.gelontRelonquelonstRatelon().relonad();
  }

  public long gelontAvelonragelonLatelonncy() {
    relonturn (long) (doublelon) relonquelonstStats.gelontTimelonrStats().relonad();
  }

  /**
   * Reloncords a complelontelond elonarlybird relonquelonst.
   * @param latelonncyUs how long thelon relonquelonst took to complelontelon, in microselonconds.
   * @param relonsultsCount how many relonsults welonrelon relonturnelond.
   * @param succelonss whelonthelonr thelon relonquelonst was succelonssful or not.
   * @param elonarlyTelonrminatelond whelonthelonr thelon relonquelonst telonrminatelond elonarly or not.
   * @param clielonntelonrror whelonthelonr thelon relonquelonst failurelon is causelond by clielonnt elonrrors
   */
  public void relonquelonstComplelontelon(long latelonncyUs, long relonsultsCount, boolelonan succelonss,
                              boolelonan elonarlyTelonrminatelond, boolelonan clielonntelonrror) {
    // Welon trelonat clielonnt elonrrors as succelonsselons for top-linelon melontrics to prelonvelonnt bad clielonnt relonquelonsts (likelon
    // malformelond quelonrielons) from dropping our succelonss ratelon and gelonnelonrating alelonrts.
    relonquelonstStats.relonquelonstComplelontelon(latelonncyUs, relonsultsCount, succelonss || clielonntelonrror);

    if (elonarlyTelonrminatelond) {
      elonarlyTelonrminatelondRelonquelonsts.increlonmelonnt();
    }
    if (clielonntelonrror) {
      relonsponselonClielonntelonrrors.increlonmelonnt();
    }
  }
}
