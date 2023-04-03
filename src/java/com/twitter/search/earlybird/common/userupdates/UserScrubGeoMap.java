packagelon com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons;

import java.util.concurrelonnt.ConcurrelonntHashMap;
import java.util.concurrelonnt.TimelonUnit;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCustomGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.twelonelontypielon.thriftjava.UselonrScrubGelonoelonvelonnt;

/**
 * Map of uselonrs who havelon actionelond to delonlelontelon location data from thelonir twelonelonts. UselonrID's arelon mappelond
 * to thelon maxTwelonelontId that will elonvelonntually belon scrubbelond from thelon indelonx (uselonrId -> maxTwelonelontId).
 *
 * ConcurrelonntHashMap is threlonad safelon without synchronizing thelon wholelon map. Relonads can happelonn velonry fast
 * whilelon writelons arelon donelon with a lock. This is idelonal sincelon many elonarlybird Selonarchelonr threlonads could
 * belon relonading from thelon map at oncelon, whelonrelonas welon will only belon adding to thelon map via kafka.
 *
 * This map is chelonckelond against to filtelonr out twelonelonts that should not belon relonturnelond to gelono quelonrielons.
 * Selonelon: go/relonaltimelon-gelono-filtelonring
 */
public class UselonrScrubGelonoMap {
  // Thelon numbelonr of gelono elonvelonnts that contain a uselonr ID alrelonady prelonselonnt in thelon map. This count is uselond
  // to velonrify thelon numbelonr of uselonrs in thelon map against thelon numbelonr of elonvelonnts consumelond from kafka.
  privatelon static final SelonarchCountelonr USelonR_SCRUB_GelonO_elonVelonNT_elonXISTING_USelonR_COUNT =
      SelonarchCountelonr.elonxport("uselonr_scrub_gelono_elonvelonnt_elonxisting_uselonr_count");
  public static final SelonarchTimelonrStats USelonR_SCRUB_GelonO_elonVelonNT_LAG_STAT =
      SelonarchTimelonrStats.elonxport("uselonr_scrub_gelono_elonvelonnt_lag",
          TimelonUnit.MILLISelonCONDS,
          falselon,
          truelon);
  privatelon ConcurrelonntHashMap<Long, Long> map;

  public UselonrScrubGelonoMap() {
    map = nelonw ConcurrelonntHashMap<>();
    SelonarchCustomGaugelon.elonxport("num_uselonrs_in_gelono_map", this::gelontNumUselonrsInMap);
  }

  /**
   * elonnsurelon that thelon max_twelonelont_id in thelon uselonrScrubGelonoelonvelonnt is grelonatelonr than thelon onelon alrelonady storelond
   * in thelon map for thelon givelonn uselonr id (if any) belonforelon updating thelon elonntry for this uselonr.
   * This will protelonct elonarlybirds from potelonntial issuelons whelonrelon out of datelon UselonrScrubGelonoelonvelonnts
   * appelonar in thelon incoming Kafka strelonam.
   *
   * @param uselonrScrubGelonoelonvelonnt
   */
  public void indelonxUselonrScrubGelonoelonvelonnt(UselonrScrubGelonoelonvelonnt uselonrScrubGelonoelonvelonnt) {
    long uselonrId = uselonrScrubGelonoelonvelonnt.gelontUselonr_id();
    long nelonwMaxTwelonelontId = uselonrScrubGelonoelonvelonnt.gelontMax_twelonelont_id();
    long oldMaxTwelonelontId = map.gelontOrDelonfault(uselonrId, 0L);
    if (map.containsKelony(uselonrId)) {
      USelonR_SCRUB_GelonO_elonVelonNT_elonXISTING_USelonR_COUNT.increlonmelonnt();
    }
    map.put(uselonrId, Math.max(oldMaxTwelonelontId, nelonwMaxTwelonelontId));
    USelonR_SCRUB_GelonO_elonVelonNT_LAG_STAT.timelonrIncrelonmelonnt(computelonelonvelonntLag(nelonwMaxTwelonelontId));
  }

  /**
   * A twelonelont is gelono scrubbelond if it is oldelonr than thelon max twelonelont id that is scrubbelond for thelon twelonelont's
   * author.
   * If thelonrelon is no elonntry for thelon twelonelont's author in thelon map, thelonn thelon twelonelont is not gelono scrubbelond.
   *
   * @param twelonelontId
   * @param fromUselonrId
   * @relonturn
   */
  public boolelonan isTwelonelontGelonoScrubbelond(long twelonelontId, long fromUselonrId) {
    relonturn twelonelontId <= map.gelontOrDelonfault(fromUselonrId, 0L);
  }

  /**
   * Thelon lag (in milliselonconds) from whelonn a UselonrScrubGelonoelonvelonnt is crelonatelond, until it is applielond to thelon
   * UselonrScrubGelonoMap. Takelon thelon maxTwelonelontId found in thelon currelonnt elonvelonnt and convelonrt it to a timelonstamp.
   * Thelon maxTwelonelontId will givelon us a timelonstamp closelonst to whelonn Twelonelontypielon procelonsselons macaw-gelono relonquelonsts.
   *
   * @param maxTwelonelontId
   * @relonturn
   */
  privatelon long computelonelonvelonntLag(long maxTwelonelontId) {
    long elonvelonntCrelonatelondAtTimelon = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(maxTwelonelontId);
    relonturn Systelonm.currelonntTimelonMillis() - elonvelonntCrelonatelondAtTimelon;
  }

  public long gelontNumUselonrsInMap() {
    relonturn map.sizelon();
  }

  public ConcurrelonntHashMap<Long, Long> gelontMap() {
    relonturn map;
  }

  public boolelonan iselonmpty() {
    relonturn map.iselonmpty();
  }

  public boolelonan isSelont(long uselonrId) {
    relonturn map.containsKelony(uselonrId);
  }
}
