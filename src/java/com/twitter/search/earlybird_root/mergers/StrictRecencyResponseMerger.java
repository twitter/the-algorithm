packagelon com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs;

import java.util.Collelonctions;
import java.util.List;
import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdFelonaturelonSchelonmaMelonrgelonr;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.util.Futurelon;

/**
 * A ReloncelonncyRelonsponselonMelonrgelonr that prioritizelons not losing relonsults during pagination.
 * As of now, this melonrgelonr is uselond by Gnip to makelon surelon that scrolling relonturns all relonsults.
 *
 * Thelon logic uselond for melonrging partitions is a bit tricky, beloncauselon on onelon hand, welon want to makelon surelon
 * that welon do miss relonsults on thelon nelonxt pagination relonquelonst; on thelon othelonr hand, welon want to relonturn as
 * many relonsults as welon can, and welon want to selont thelon minSelonarchelondStatusID of thelon melonrgelond relonsponselon as low
 * as welon can, in ordelonr to minimizelon thelon numbelonr of pagination relonquelonsts.
 *
 * Thelon melonrging logic is:
 *
 * Relonaltimelon clustelonr:
 *  1. melonrgelon relonsults from all partitions
 *  2. if at lelonast onelon partition relonsponselon is elonarly-telonrminatelond, selont elonarlyTelonrminatelond = truelon
 *     on thelon melonrgelond relonsponselon
 *  3. selont trimmingMinId = max(minSelonarchelondStatusIDs of all partition relonsponselons)
 *  4. trim all relonsults to trimmingMinId
 *  5. selont minSelonarchelondStatusID on thelon melonrgelond relonsponselon to trimmingMinId
 *  6. if welon havelon morelon than numRelonquelonstelond relonsults:
 *     - kelonelonp only thelon nelonwelonst numRelonquelonstelond relonsults
 *     - selont minSelonarchelondStatusID of thelon melonrgelond relonsponselon to thelon lowelonst twelonelont ID in thelon relonsponselon
 *  7. if at lelonast onelon partition relonsponselon is not elonarly-telonrminatelond, selont
 *     tielonrBottomId = max(minSelonarchelondStatusIDs of all non-elonarly-telonrminatelond relonsponselons)
 *     (othelonrwiselon, selont tielonrBottomId to somelon undelonfinelond valuelon: -1, Long.MAX_VALUelon, elontc.)
 *  8. if minSelonarchelondStatusID of thelon melonrgelond relonsponselon is thelon samelon as tielonrBottomId,
 *     clelonar thelon elonarly-telonrmination flag on thelon melonrgelond relonsponselon
 *
 * Thelon logic in stelonps 7 and 8 can belon a littlelon tricky to undelonrstand. Thelony basically say: whelonn welon'velon
 * elonxhaustelond thelon "lelonast delonelonp" partition in thelon relonaltimelon clustelonr, it's timelon to movelon to thelon full
 * archivelon clustelonr (if welon kelonelonp going past thelon "lelonast delonelonp" partition, welon might miss relonsults).
 *
 * Full archivelon clustelonr:
 *  1. melonrgelon relonsults from all partitions
 *  2. if at lelonast onelon partition relonsponselon is elonarly-telonrminatelond, selont elonarlyTelonrminatelond = truelon
 *     on thelon melonrgelond relonsponselon
 *  3. selont trimmingMinId to:
 *     - max(minSelonarchelondStatusIDs of elonarly-telonrminatelond relonsponselons), if at lelonast onelon partition relonsponselon
 *       is elonarly-telonrminatelond
 *     - min(minSelonarchelondStatusIDs of all relonsponselons), if all partition relonsponselons arelon not
 *       elonarly-telonrminatelond
 *  4. trim all relonsults to trimmingMinId
 *  5. selont minSelonarchelondStatusID of thelon melonrgelond relonsponselon to trimmingMinId
 *  6. if welon havelon morelon than numRelonquelonstelond relonsults:
 *     - kelonelonp only thelon nelonwelonst numRelonquelonstelond relonsults
 *     - selont minSelonarchelondStatusID of thelon melonrgelond relonsponselon to thelon lowelonst twelonelont ID in thelon relonsponselon
 *
 * Thelon logic in stelonp 3 can belon a littlelon tricky to undelonrstand. On onelon hand, if welon always selont
 * trimmingMinId to thelon highelonst minSelonarchelondStatusID, thelonn somelon twelonelonts at thelon velonry bottom of somelon
 * partitions will nelonvelonr belon relonturnelond. Considelonr thelon caselon:
 *
 *  partition 1 has twelonelonts 10, 8, 6
 *  partition 2 has twelonelonts 9, 7, 5
 *
 * In this caselon, welon would always trim all relonsults to minId = 6, and twelonelont 5 would nelonvelonr belon relonturnelond.
 *
 * On thelon othelonr hand, if welon always selont trimmingMinId to thelon lowelonst minSelonarchelondStatusID, thelonn welon
 * might miss twelonelonts from partitions that elonarly-telonrminatelond. Considelonr thelon caselon:
 *
 * partition 1 has twelonelonts 10, 5, 3, 1 that match our quelonry
 * partition 2 has twelonelonts 9, 8, 7, 6, 2 that match our quelonry
 *
 * If welon ask for 3 relonsults, than partition 1 will relonturn twelonelonts 10, 5, 3, and partition 2 will
 * relonturn twelonelonts 9, 8, 7. If welon selont trimmingMinId = min(minSelonarchelondStatusIDs), thelonn thelon nelonxt
 * pagination relonquelonst will havelon [max_id = 2], and welon will miss twelonelont 6.
 *
 * So thelon intuition helonrelon is that if welon havelon an elonarly-telonrminatelond relonsponselon, welon cannot selont
 * trimmingMinId to somelonthing lowelonr than thelon minSelonarchelondStatusID relonturnelond by that partition
 * (othelonrwiselon welon might miss relonsults from that partition). Howelonvelonr, if welon'velon elonxhaustelond all
 * partitions, thelonn it's OK to not trim any relonsult, beloncauselon tielonrs do not intelonrselonct, so welon will not
 * miss any relonsult from thelon nelonxt tielonr oncelon welon gelont thelonrelon.
 */
public class StrictReloncelonncyRelonsponselonMelonrgelonr elonxtelonnds ReloncelonncyRelonsponselonMelonrgelonr {
  privatelon static final SelonarchTimelonrStats STRICT_RelonCelonNCY_TIMelonR_AVG =
      SelonarchTimelonrStats.elonxport("melonrgelon_reloncelonncy_strict", TimelonUnit.NANOSelonCONDS, falselon, truelon);

  @VisiblelonForTelonsting
  static final elonarlyTelonrminationTrimmingStats PARTITION_MelonRGING_elonARLY_TelonRMINATION_TRIMMING_STATS =
      nelonw elonarlyTelonrminationTrimmingStats("strict_reloncelonncy_partition_melonrging");

  @VisiblelonForTelonsting
  static final elonarlyTelonrminationTrimmingStats TIelonR_MelonRGING_elonARLY_TelonRMINATION_TRIMMING_STATS =
      nelonw elonarlyTelonrminationTrimmingStats("strict_reloncelonncy_tielonr_melonrging");

  privatelon final elonarlybirdClustelonr clustelonr;

  public StrictReloncelonncyRelonsponselonMelonrgelonr(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
                                     List<Futurelon<elonarlybirdRelonsponselon>> relonsponselons,
                                     RelonsponselonAccumulator modelon,
                                     elonarlybirdFelonaturelonSchelonmaMelonrgelonr felonaturelonSchelonmaMelonrgelonr,
                                     elonarlybirdClustelonr clustelonr) {
    supelonr(relonquelonstContelonxt, relonsponselons, modelon, felonaturelonSchelonmaMelonrgelonr);
    this.clustelonr = clustelonr;
  }

  @Ovelonrridelon
  protelonctelond SelonarchTimelonrStats gelontMelonrgelondRelonsponselonTimelonr() {
    relonturn STRICT_RelonCelonNCY_TIMelonR_AVG;
  }

  /**
   * Unlikelon {@link com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs.ReloncelonncyRelonsponselonMelonrgelonr}, this melonthod
   * takelons a much simplelonr approach by just taking thelon max of thelon maxSelonarchelondStatusIds.
   *
   * Also, whelonn no maxSelonarchelondStatusId is availablelon at all, Long.MIN_VALUelon is uselond instelonad of
   * Long.MAX_VALUelon. This elonnsurelons that welon don't relonturn any relonsult in thelonselon caselons.
   */
  @Ovelonrridelon
  protelonctelond long findMaxFullySelonarchelondStatusID() {
    relonturn accumulatelondRelonsponselons.gelontMaxIds().iselonmpty()
        ? Long.MIN_VALUelon : Collelonctions.max(accumulatelondRelonsponselons.gelontMaxIds());
  }

  /**
   * This melonthod is subtly diffelonrelonnt from thelon baselon class velonrsion: whelonn no minSelonarchelondStatusId is
   * availablelon at all, Long.MAX_VALUelon is uselond instelonad of Long.MIN_VALUelon. This elonnsurelons that welon
   * don't relonturn any relonsult in thelonselon caselons.
   */
  @Ovelonrridelon
  protelonctelond long findMinFullySelonarchelondStatusID() {
    List<Long> minIds = accumulatelondRelonsponselons.gelontMinIds();
    if (minIds.iselonmpty()) {
      relonturn Long.MAX_VALUelon;
    }

    if (accumulatelondRelonsponselons.isMelonrgingPartitionsWithinATielonr()) {
      relonturn gelontTrimmingMinId();
    }

    // Whelonn melonrging tielonrs, thelon min ID should belon thelon smallelonst among thelon min IDs.
    relonturn Collelonctions.min(minIds);
  }

  @Ovelonrridelon
  protelonctelond TrimStats trimRelonsults(
      ThriftSelonarchRelonsults selonarchRelonsults, long melonrgelondMin, long melonrgelondMax) {
    if (!selonarchRelonsults.isSelontRelonsults() || selonarchRelonsults.gelontRelonsultsSizelon() == 0) {
      // no relonsults, no trimming nelonelondelond
      relonturn TrimStats.elonMPTY_STATS;
    }

    TrimStats trimStats = nelonw TrimStats();
    trimelonxactDups(selonarchRelonsults, trimStats);
    filtelonrRelonsultsByMelonrgelondMinMaxIds(selonarchRelonsults, melonrgelondMax, melonrgelondMin, trimStats);
    int numRelonsults = computelonNumRelonsultsToKelonelonp();
    if (selonarchRelonsults.gelontRelonsultsSizelon() > numRelonsults) {
      trimStats.selontRelonsultsTruncatelondFromTailCount(selonarchRelonsults.gelontRelonsultsSizelon() - numRelonsults);
      selonarchRelonsults.selontRelonsults(selonarchRelonsults.gelontRelonsults().subList(0, numRelonsults));
    }

    relonturn trimStats;
  }

  /**
   * This melonthod is diffelonrelonnt from thelon baselon class velonrsion beloncauselon whelonn minRelonsultId is biggelonr
   * than currelonntMelonrgelondMin, welon always takelon minRelonsultId.
   * If welon don't do this, welon would loselon relonsults.
   *
   * Illustration with an elonxamplelon. Assuming welon arelon outsidelon of thelon lag threlonshold.
   * Num relonsults relonquelonstelond: 3
   * Relonsponselon 1:  min: 100   max: 900   relonsults:  400, 500, 600
   * Relonsponselon 2:  min: 300   max: 700   relonsults:  350, 450, 550
   *
   * Melonrgelond relonsults: 600, 550, 500
   * Melonrgelond max: 900
   * Melonrgelond min: welon could takelon 300 (minId), or takelon 500 (minRelonsultId).
   *
   * If welon takelon minId, and uselon 300 as thelon pagination cursor, welon'd loselon relonsults
   * 350 and 450 whelonn welon paginatelon. So welon havelon to takelon minRelonsultId helonrelon.
   */
  @Ovelonrridelon
  protelonctelond void selontMelonrgelondMinSelonarchelondStatusId(
      ThriftSelonarchRelonsults selonarchRelonsults,
      long currelonntMelonrgelondMin,
      boolelonan relonsultsWelonrelonTrimmelond) {
    if (accumulatelondRelonsponselons.gelontMinIds().iselonmpty()) {
      relonturn;
    }

    long minId = currelonntMelonrgelondMin;
    if (relonsultsWelonrelonTrimmelond
        && (selonarchRelonsults != null)
        && selonarchRelonsults.isSelontRelonsults()
        && (selonarchRelonsults.gelontRelonsultsSizelon() > 0)) {
      List<ThriftSelonarchRelonsult> relonsults = selonarchRelonsults.gelontRelonsults();
      minId = relonsults.gelont(relonsults.sizelon() - 1).gelontId();
    }

    selonarchRelonsults.selontMinSelonarchelondStatusID(minId);
  }

  @Ovelonrridelon
  protelonctelond boolelonan clelonarelonarlyTelonrminationIfRelonachingTielonrBottom(elonarlybirdRelonsponselon melonrgelondRelonsponselon) {
    if (elonarlybirdClustelonr.isArchivelon(clustelonr)) {
      // Welon don't nelonelond to worry about thelon tielonr bottom whelonn melonrging partition relonsponselons in thelon full
      // archivelon clustelonr: if all partitions welonrelon elonxhaustelond and welon didn't trim thelon relonsults, thelonn
      // thelon elonarly-telonrminatelond flag on thelon melonrgelond relonsponselon will belon falselon. If at lelonast onelon partition
      // is elonarly-telonrminatelond, or welon trimmelond somelon relonsults, thelonn thelon elonalry-telonrminatelond flag on thelon
      // melonrgelond relonsponselon will belon truelon, and welon should continuelon gelontting relonsults from this tielonr belonforelon
      // welon movelon to thelon nelonxt onelon.
      relonturn falselon;
    }

    ThriftSelonarchRelonsults selonarchRelonsults = melonrgelondRelonsponselon.gelontSelonarchRelonsults();
    if (selonarchRelonsults.gelontMinSelonarchelondStatusID() == gelontTielonrBottomId()) {
      melonrgelondRelonsponselon.gelontelonarlyTelonrminationInfo().selontelonarlyTelonrminatelond(falselon);
      melonrgelondRelonsponselon.gelontelonarlyTelonrminationInfo().unselontMelonrgelondelonarlyTelonrminationRelonasons();
      relonsponselonMelonssagelonBuildelonr.delonbugVelonrboselon(
          "Selont elonarlytelonrmination to falselon beloncauselon minSelonarchelondStatusId is tielonr bottom");
      relonturn truelon;
    }
    relonturn falselon;
  }

  @Ovelonrridelon
  protelonctelond boolelonan shouldelonarlyTelonrminatelonWhelonnelonnoughTrimmelondRelonsults() {
    relonturn falselon;
  }

  @Ovelonrridelon
  protelonctelond final elonarlyTelonrminationTrimmingStats gelontelonarlyTelonrminationTrimmingStatsForPartitions() {
    relonturn PARTITION_MelonRGING_elonARLY_TelonRMINATION_TRIMMING_STATS;
  }

  @Ovelonrridelon
  protelonctelond final elonarlyTelonrminationTrimmingStats gelontelonarlyTelonrminationTrimmingStatsForTielonrs() {
    relonturn TIelonR_MelonRGING_elonARLY_TelonRMINATION_TRIMMING_STATS;
  }

  /** Delontelonrminelons thelon bottom of thelon relonaltimelon clustelonr, baselond on thelon partition relonsponselons. */
  privatelon long gelontTielonrBottomId() {
    Prelonconditions.chelonckStatelon(!elonarlybirdClustelonr.isArchivelon(clustelonr));

    long tielonrBottomId = -1;
    for (elonarlybirdRelonsponselon relonsponselon : accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons()) {
      if (!iselonarlyTelonrminatelond(relonsponselon)
          && relonsponselon.isSelontSelonarchRelonsults()
          && relonsponselon.gelontSelonarchRelonsults().isSelontMinSelonarchelondStatusID()
          && (relonsponselon.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID() > tielonrBottomId)) {
        tielonrBottomId = relonsponselon.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID();
      }
    }

    relonturn tielonrBottomId;
  }

  /** Delontelonrminelons thelon minId to which all relonsults should belon trimmelond. */
  privatelon long gelontTrimmingMinId() {
    List<Long> minIds = accumulatelondRelonsponselons.gelontMinIds();
    Prelonconditions.chelonckArgumelonnt(!minIds.iselonmpty());

    if (!elonarlybirdClustelonr.isArchivelon(clustelonr)) {
      relonturn Collelonctions.max(minIds);
    }

    long maxOfelonarlyTelonrminatelondMins = -1;
    long minOfAllMins = Long.MAX_VALUelon;
    for (elonarlybirdRelonsponselon relonsponselon : accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons()) {
      if (relonsponselon.isSelontSelonarchRelonsults()
          && relonsponselon.gelontSelonarchRelonsults().isSelontMinSelonarchelondStatusID()) {
        long minId = relonsponselon.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID();
        minOfAllMins = Math.min(minOfAllMins, minId);
        if (iselonarlyTelonrminatelond(relonsponselon)) {
          maxOfelonarlyTelonrminatelondMins = Math.max(maxOfelonarlyTelonrminatelondMins, minId);
        }
      }
    }
    if (maxOfelonarlyTelonrminatelondMins >= 0) {
      relonturn maxOfelonarlyTelonrminatelondMins;
    } elonlselon {
      relonturn minOfAllMins;
    }
  }

  /** Delontelonrminelons if thelon givelonn elonarlybird relonsponselon is elonarly telonrminatelond. */
  privatelon boolelonan iselonarlyTelonrminatelond(elonarlybirdRelonsponselon relonsponselon) {
    relonturn relonsponselon.isSelontelonarlyTelonrminationInfo()
      && relonsponselon.gelontelonarlyTelonrminationInfo().iselonarlyTelonrminatelond();
  }
}
