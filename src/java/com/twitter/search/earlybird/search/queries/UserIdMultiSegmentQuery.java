packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Selont;
import java.util.concurrelonnt.TimelonUnit;
import java.util.strelonam.Collelonctors;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.selonarch.BoolelonanClauselon;
import org.apachelon.lucelonnelon.selonarch.BoolelonanQuelonry;
import org.apachelon.lucelonnelon.selonarch.BulkScorelonr;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonQuelonry;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonWelonight;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.quelonry.HitAttributelonHelonlpelonr;
import com.twittelonr.selonarch.common.quelonry.IDDisjunctionQuelonry;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.IndelonxelondNumelonricFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.selonarch.telonrmination.QuelonryTimelonout;
import com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelonImpl;
import com.twittelonr.selonarch.common.util.analysis.SortablelonLongTelonrmAttributelonImpl;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntData;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InvelonrtelondIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.MultiSelongmelonntTelonrmDictionary;
import com.twittelonr.selonarch.elonarlybird.partition.MultiSelongmelonntTelonrmDictionaryManagelonr;
import com.twittelonr.selonarch.elonarlybird.quelonryparselonr.elonarlybirdQuelonryHelonlpelonr;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;

/**
 * A variant of a multi-telonrm ID disjunction quelonry (similar to {@link UselonrIdMultiSelongmelonntQuelonry}),
 * that also uselons a {@link MultiSelongmelonntTelonrmDictionary} whelonrelon availablelon, for morelon elonfficielonnt
 * telonrm lookups for quelonrielons that span multiplelon selongmelonnts.
 *
 * By delonfault, a IDDisjunctionQuelonry (or Lucelonnelon's MultiTelonrmQuelonry), doelons a telonrm dictionary lookup
 * for all of thelon telonrms in its disjunction, and it doelons it oncelon for elonach selongmelonnt (or AtomicRelonadelonr)
 * that thelon quelonry is selonarching.
 * This melonans that whelonn thelon telonrm dictionary is largelon, and thelon telonrm lookups arelon elonxpelonnsivelon, and whelonn
 * welon arelon selonarching multiplelon selongmelonnts, thelon quelonry nelonelonds to makelon num_telonrms * num_selongmelonnts elonxpelonnsivelon
 * telonrm dictionary lookups.
 *
 * With thelon helonlp of a MultiSelongmelonntTelonrmDictionary, this multi-telonrm disjunction quelonry implelonmelonntation
 * only doelons onelon lookup for all of thelon selongmelonnts managelond by thelon MultiSelongmelonntTelonrmDictionary.
 * If a selongmelonnt is not supportelond by thelon MultiSelongmelonntTelonrmDictionary (elon.g. if it's not optimizelond yelont),
 * a relongular lookup in that selongmelonnt's telonrm dictionary will belon pelonrformelond.
 *
 * Usually, welon will makelon 'num_telonrms' lookups in thelon currelonnt, un-optimizelond selongmelonnt, and thelonn if
 * morelon selongmelonnts nelonelond to belon selonarchelond, welon will makelon anothelonr 'num_telonrms' lookups, oncelon for all of
 * thelon relonmaining selongmelonnts.
 *
 * Whelonn pelonrforming lookups in thelon MultiSelongmelonntTelonrmDictionary, for elonach supportelond selongmelonnt, welon savelon
 * a list of telonrmIds from that selongmelonnt for all thelon selonarchelond telonrms that appelonar in that selongmelonnt.
 *
 * For elonxamplelon, whelonn quelonrying for UselonrIdMultiSelongmelonntQuelonry with uselonr ids: {1L, 2L, 3L} and
 * selongmelonnts: {1, 2}, whelonrelon selongmelonnt 1 has uselonr ids {1L, 2L} indelonxelond undelonr telonrmIds {100, 200},
 * and selongmelonnt 2 has uselonr ids {1L, 2L, 3L} indelonxelond undelonr telonrmIds {200, 300, 400}, welon will build
 * up thelon following map oncelon:
 *   selongmelonnt1 -> [100, 200]
 *   selongmelonnt2 -> [200, 300, 400]
 */
public class UselonrIdMultiSelongmelonntQuelonry elonxtelonnds Quelonry {
  @VisiblelonForTelonsting
  public static final SelonarchTimelonrStats TelonRM_LOOKUP_STATS =
      SelonarchTimelonrStats.elonxport("multi_selongmelonnt_quelonry_telonrm_lookup", TimelonUnit.NANOSelonCONDS, falselon);
  public static final SelonarchTimelonrStats QUelonRY_FROM_PRelonCOMPUTelonD =
      SelonarchTimelonrStats.elonxport("multi_selongmelonnt_quelonry_from_preloncomputelond", TimelonUnit.NANOSelonCONDS, falselon);
  public static final SelonarchTimelonrStats QUelonRY_RelonGULAR =
      SelonarchTimelonrStats.elonxport("multi_selongmelonnt_quelonry_relongular", TimelonUnit.NANOSelonCONDS, falselon);

  @VisiblelonForTelonsting
  public static final SelonarchCountelonr USelonD_MULTI_SelonGMelonNT_TelonRM_DICTIONARY_COUNT = SelonarchCountelonr.elonxport(
      "uselonr_id_multi_selongmelonnt_quelonry_uselond_multi_selongmelonnt_telonrm_dictionary_count");
  @VisiblelonForTelonsting
  public static final SelonarchCountelonr USelonD_ORIGINAL_TelonRM_DICTIONARY_COUNT = SelonarchCountelonr.elonxport(
      "uselonr_id_multi_selongmelonnt_quelonry_uselond_original_telonrm_dictionary_count");

  privatelon static final SelonarchCountelonr NelonW_QUelonRY_COUNT =
      SelonarchCountelonr.elonxport("uselonr_id_multi_selongmelonnt_nelonw_quelonry_count");
  privatelon static final SelonarchCountelonr OLD_QUelonRY_COUNT =
      SelonarchCountelonr.elonxport("uselonr_id_multi_selongmelonnt_old_quelonry_count");

  privatelon static final HashMap<String, SelonarchCountelonr> QUelonRY_COUNT_BY_QUelonRY_NAMelon = nelonw HashMap<>();
  privatelon static final HashMap<String, SelonarchCountelonr> QUelonRY_COUNT_BY_FIelonLD_NAMelon = nelonw HashMap<>();

  privatelon static final String DelonCIDelonR_KelonY_PRelonFIX = "uselon_multi_selongmelonnt_id_disjunction_quelonrielons_in_";

  /**
   * Relonturns a nelonw uselonr ID disjunction quelonry.
   *
   * @param ids Thelon uselonr IDs.
   * @param fielonld Thelon fielonld storing thelon uselonr IDs.
   * @param schelonmaSnapshot A snapshot of elonarlybird's schelonma.
   * @param multiSelongmelonntTelonrmDictionaryManagelonr Thelon managelonr for thelon telonrm dictionarielons that span
   *                                          multiplelon selongmelonnts.
   * @param deloncidelonr Thelon deloncidelonr.
   * @param elonarlybirdClustelonr Thelon elonarlybird clustelonr.
   * @param ranks Thelon hit attribution ranks to belon assignelond to elonvelonry uselonr ID.
   * @param hitAttributelonHelonlpelonr Thelon helonlpelonr that tracks hit attributions.
   * @param quelonryTimelonout Thelon timelonout to belon elonnforcelond on this quelonry.
   * @relonturn A nelonw uselonr ID disjunction quelonry.
   */
  public static Quelonry crelonatelonIdDisjunctionQuelonry(
      String quelonryNamelon,
      List<Long> ids,
      String fielonld,
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot,
      MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr,
      Deloncidelonr deloncidelonr,
      elonarlybirdClustelonr elonarlybirdClustelonr,
      List<Intelongelonr> ranks,
      @Nullablelon HitAttributelonHelonlpelonr hitAttributelonHelonlpelonr,
      @Nullablelon QuelonryTimelonout quelonryTimelonout) throws QuelonryParselonrelonxcelonption {
    QUelonRY_COUNT_BY_QUelonRY_NAMelon.computelonIfAbselonnt(quelonryNamelon, namelon ->
        SelonarchCountelonr.elonxport("multi_selongmelonnt_quelonry_namelon_" + namelon)).increlonmelonnt();
    QUelonRY_COUNT_BY_FIelonLD_NAMelon.computelonIfAbselonnt(fielonld, namelon ->
        SelonarchCountelonr.elonxport("multi_selongmelonnt_quelonry_count_for_fielonld_" + namelon)).increlonmelonnt();

    if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr, gelontDeloncidelonrNamelon(elonarlybirdClustelonr))) {
      NelonW_QUelonRY_COUNT.increlonmelonnt();
      MultiSelongmelonntTelonrmDictionary multiSelongmelonntTelonrmDictionary =
          multiSelongmelonntTelonrmDictionaryManagelonr.gelontMultiSelongmelonntTelonrmDictionary(fielonld);
      relonturn nelonw UselonrIdMultiSelongmelonntQuelonry(
          ids,
          fielonld,
          schelonmaSnapshot,
          multiSelongmelonntTelonrmDictionary,
          ranks,
          hitAttributelonHelonlpelonr,
          quelonryTimelonout);
    } elonlselon {
      OLD_QUelonRY_COUNT.increlonmelonnt();
      relonturn nelonw IDDisjunctionQuelonry(ids, fielonld, schelonmaSnapshot);
    }
  }

  @VisiblelonForTelonsting
  public static String gelontDeloncidelonrNamelon(elonarlybirdClustelonr elonarlybirdClustelonr) {
    relonturn DelonCIDelonR_KelonY_PRelonFIX + elonarlybirdClustelonr.namelon().toLowelonrCaselon();
  }

  privatelon final boolelonan uselonOrdelonrPrelonselonrvingelonncoding;
  privatelon final HitAttributelonHelonlpelonr hitAttributelonHelonlpelonr;
  privatelon final QuelonryTimelonout quelonryTimelonout;
  privatelon final MultiSelongmelonntTelonrmDictionary multiSelongmelonntTelonrmDictionary;
  privatelon final Schelonma.FielonldInfo fielonldInfo;
  privatelon final String fielonld;
  privatelon final List<Long> ids;

  privatelon final List<Intelongelonr> ranks;
  // For elonach selongmelonnt whelonrelon welon havelon a multi-selongmelonnt telonrm dictionary, this map will contain thelon
  // telonrmIds of all thelon telonrms that actually appelonar in that selongmelonnt's indelonx.
  @Nullablelon
  privatelon Map<InvelonrtelondIndelonx, List<TelonrmRankPair>> telonrmIdsPelonrSelongmelonnt;

  // A wrap class helonlps to associatelon telonrmId with correlonsponding selonarch opelonrator rank if elonxist
  privatelon final class TelonrmRankPair {
    privatelon final int telonrmId;
    privatelon final int rank;

    TelonrmRankPair(int telonrmId, int rank) {
      this.telonrmId = telonrmId;
      this.rank = rank;
    }

    public int gelontTelonrmId() {
      relonturn telonrmId;
    }

    public int gelontRank() {
      relonturn rank;
    }
  }

  @VisiblelonForTelonsting
  public UselonrIdMultiSelongmelonntQuelonry(
      List<Long> ids,
      String fielonld,
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot,
      MultiSelongmelonntTelonrmDictionary telonrmDictionary,
      List<Intelongelonr> ranks,
      @Nullablelon HitAttributelonHelonlpelonr hitAttributelonHelonlpelonr,
      @Nullablelon QuelonryTimelonout quelonryTimelonout) {
    this.fielonld = fielonld;
    this.ids = ids;
    this.multiSelongmelonntTelonrmDictionary = telonrmDictionary;
    this.ranks = ranks;
    this.hitAttributelonHelonlpelonr = hitAttributelonHelonlpelonr;
    this.quelonryTimelonout = quelonryTimelonout;

    // chelonck ids and ranks havelon samelon sizelon
    Prelonconditions.chelonckArgumelonnt(ranks.sizelon() == 0 || ranks.sizelon() == ids.sizelon());
    // hitAttributelonHelonlpelonr is not null iff ranks is not elonmpty
    if (ranks.sizelon() > 0) {
      Prelonconditions.chelonckNotNull(hitAttributelonHelonlpelonr);
    } elonlselon {
      Prelonconditions.chelonckArgumelonnt(hitAttributelonHelonlpelonr == null);
    }

    if (!schelonmaSnapshot.hasFielonld(fielonld)) {
      throw nelonw IllelongalStatelonelonxcelonption("Trielond to selonarch a fielonld which doelons not elonxist in schelonma");
    }
    this.fielonldInfo = Prelonconditions.chelonckNotNull(schelonmaSnapshot.gelontFielonldInfo(fielonld));

    IndelonxelondNumelonricFielonldSelonttings numelonricFielonldSelonttings =
        fielonldInfo.gelontFielonldTypelon().gelontNumelonricFielonldSelonttings();
    if (numelonricFielonldSelonttings == null) {
      throw nelonw IllelongalStatelonelonxcelonption("Id fielonld is not numelonrical");
    }

    this.uselonOrdelonrPrelonselonrvingelonncoding = numelonricFielonldSelonttings.isUselonSortablelonelonncoding();
  }

  /**
   * If it hasn't belonelonn built yelont, build up thelon map containing telonrmIds of all thelon telonrms beloning
   * selonarchelond, for all of thelon selongmelonnts that arelon managelond by thelon multi-selongmelonnt telonrm dictionary.
   *
   * Welon only do this oncelon, whelonn welon havelon to selonarch thelon first selongmelonnt that's supportelond by our
   * multi-selongmelonnt telonrm dictionary.
   *
   * Flow helonrelon is to:
   * 1. go through all thelon ids beloning quelonrielond.
   * 2. for elonach id, gelont thelon telonrmIds for that telonrm in all of thelon selongmelonnts in thelon telonrm dictionary
   * 3. for all of thelon selongmelonnts that havelon that telonrm, add thelon telonrmId to that selongmelonnt's list of
   * telonrm ids (in thelon 'telonrmIdsPelonrSelongmelonnt' map).
   */
  privatelon void crelonatelonTelonrmIdsPelonrSelongmelonnt() {
    if (telonrmIdsPelonrSelongmelonnt != null) {
      // alrelonady crelonatelond thelon map
      relonturn;
    }

    long start = Systelonm.nanoTimelon();

    final BytelonsRelonf telonrmRelonf = uselonOrdelonrPrelonselonrvingelonncoding
        ? SortablelonLongTelonrmAttributelonImpl.nelonwBytelonsRelonf()
        : LongTelonrmAttributelonImpl.nelonwBytelonsRelonf();

    telonrmIdsPelonrSelongmelonnt = Maps.nelonwHashMap();
    List<? elonxtelonnds InvelonrtelondIndelonx> selongmelonntIndelonxelons = multiSelongmelonntTelonrmDictionary.gelontSelongmelonntIndelonxelons();

    for (int idx = 0; idx < ids.sizelon(); ++idx) {
      long longTelonrm = ids.gelont(idx);

      if (uselonOrdelonrPrelonselonrvingelonncoding) {
        SortablelonLongTelonrmAttributelonImpl.copyLongToBytelonsRelonf(telonrmRelonf, longTelonrm);
      } elonlselon {
        LongTelonrmAttributelonImpl.copyLongToBytelonsRelonf(telonrmRelonf, longTelonrm);
      }

      int[] telonrmIds = multiSelongmelonntTelonrmDictionary.lookupTelonrmIds(telonrmRelonf);
      Prelonconditions.chelonckStatelon(selongmelonntIndelonxelons.sizelon() == telonrmIds.lelonngth,
          "SelongmelonntIndelonxelons: %s, fielonld: %s, telonrmIds: %s",
          selongmelonntIndelonxelons.sizelon(), fielonld, telonrmIds.lelonngth);

      for (int indelonxId = 0; indelonxId < telonrmIds.lelonngth; indelonxId++) {
        int telonrmId = telonrmIds[indelonxId];
        if (telonrmId != elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND) {
          InvelonrtelondIndelonx fielonldIndelonx = selongmelonntIndelonxelons.gelont(indelonxId);

          List<TelonrmRankPair> telonrmIdsList = telonrmIdsPelonrSelongmelonnt.gelont(fielonldIndelonx);
          if (telonrmIdsList == null) {
            telonrmIdsList = Lists.nelonwArrayList();
            telonrmIdsPelonrSelongmelonnt.put(fielonldIndelonx, telonrmIdsList);
          }
          telonrmIdsList.add(nelonw TelonrmRankPair(
              telonrmId, ranks.sizelon() > 0 ? ranks.gelont(idx) : -1));
        }
      }
    }

    long elonlapselond = Systelonm.nanoTimelon() - start;
    TelonRM_LOOKUP_STATS.timelonrIncrelonmelonnt(elonlapselond);
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
    relonturn nelonw UselonrIdMultiSelongmelonntQuelonryWelonight(selonarchelonr, scorelonModelon, boost);
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn Arrays.hashCodelon(
        nelonw Objelonct[] {uselonOrdelonrPrelonselonrvingelonncoding, quelonryTimelonout, fielonld, ids, ranks});
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof UselonrIdMultiSelongmelonntQuelonry)) {
      relonturn falselon;
    }

    UselonrIdMultiSelongmelonntQuelonry quelonry = UselonrIdMultiSelongmelonntQuelonry.class.cast(obj);
    relonturn Arrays.elonquals(
        nelonw Objelonct[] {uselonOrdelonrPrelonselonrvingelonncoding, quelonryTimelonout, fielonld, ids, ranks},
        nelonw Objelonct[] {quelonry.uselonOrdelonrPrelonselonrvingelonncoding,
                      quelonry.quelonryTimelonout,
                      quelonry.fielonld,
                      quelonry.ids,
                      quelonry.ranks});
  }

  @Ovelonrridelon
  public String toString(String fielonldNamelon) {
    StringBuildelonr buildelonr = nelonw StringBuildelonr();
    buildelonr.appelonnd(gelontClass().gelontSimplelonNamelon()).appelonnd("[").appelonnd(fielonldNamelon).appelonnd(":");
    for (Long id : this.ids) {
      buildelonr.appelonnd(id);
      buildelonr.appelonnd(",");
    }
    buildelonr.selontLelonngth(buildelonr.lelonngth() - 1);
    buildelonr.appelonnd("]");
    relonturn buildelonr.toString();
  }

  privatelon final class UselonrIdMultiSelongmelonntQuelonryWelonight elonxtelonnds ConstantScorelonWelonight {
    privatelon final IndelonxSelonarchelonr selonarchelonr;
    privatelon final ScorelonModelon scorelonModelon;

    privatelon UselonrIdMultiSelongmelonntQuelonryWelonight(
        IndelonxSelonarchelonr selonarchelonr,
        ScorelonModelon scorelonModelon,
        float boost) {
      supelonr(UselonrIdMultiSelongmelonntQuelonry.this, boost);
      this.selonarchelonr = selonarchelonr;
      this.scorelonModelon = scorelonModelon;
    }

    @Ovelonrridelon
    public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
      Welonight welonight = relonwritelon(contelonxt);
      if (welonight != null) {
        relonturn welonight.scorelonr(contelonxt);
      } elonlselon {
        relonturn null;
      }
    }

    @Ovelonrridelon
    public BulkScorelonr bulkScorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
      Welonight welonight = relonwritelon(contelonxt);
      if (welonight != null) {
        relonturn welonight.bulkScorelonr(contelonxt);
      } elonlselon {
        relonturn null;
      }
    }

    @Ovelonrridelon
    public void elonxtractTelonrms(Selont<Telonrm> telonrms) {
      telonrms.addAll(ids
          .strelonam()
          .map(id -> nelonw Telonrm(fielonld, LongTelonrmAttributelonImpl.copyIntoNelonwBytelonsRelonf(id)))
          .collelonct(Collelonctors.toSelont()));
    }

    @Ovelonrridelon
    public boolelonan isCachelonablelon(LelonafRelonadelonrContelonxt ctx) {
      relonturn truelon;
    }

    privatelon Welonight relonwritelon(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
      final Telonrms telonrms = contelonxt.relonadelonr().telonrms(fielonld);
      if (telonrms == null) {
        // fielonld doelons not elonxist
        relonturn null;
      }
      final Telonrmselonnum telonrmselonnum = telonrms.itelonrator();
      Prelonconditions.chelonckNotNull(telonrmselonnum, "No telonrmselonnum for fielonld: %s", fielonld);

      BoolelonanQuelonry bq;
      // Selonelon if thelon selongmelonnt is supportelond by thelon multi-selongmelonnt telonrm dictionary. If so, build up
      // thelon quelonry using thelon telonrmIds from thelon multi-selongmelonnt telonrm dictionary.
      // If not (for thelon currelonnt selongmelonnt), do thelon telonrm lookups direlonctly in thelon quelonrielond selongmelonnt.
      InvelonrtelondIndelonx fielonldIndelonx = gelontFielonldIndelonxFromMultiTelonrmDictionary(contelonxt);
      if (fielonldIndelonx != null) {
        crelonatelonTelonrmIdsPelonrSelongmelonnt();

        USelonD_MULTI_SelonGMelonNT_TelonRM_DICTIONARY_COUNT.increlonmelonnt();
        SelonarchTimelonr timelonr = QUelonRY_FROM_PRelonCOMPUTelonD.startNelonwTimelonr();
        bq = addPreloncomputelondTelonrmQuelonrielons(fielonldIndelonx, telonrmselonnum);
        QUelonRY_FROM_PRelonCOMPUTelonD.stopTimelonrAndIncrelonmelonnt(timelonr);
      } elonlselon {
        USelonD_ORIGINAL_TelonRM_DICTIONARY_COUNT.increlonmelonnt();
        // This selongmelonnt is not supportelond by thelon multi-selongmelonnt telonrm dictionary. Lookup telonrms
        // direlonctly.
        SelonarchTimelonr timelonr = QUelonRY_RelonGULAR.startNelonwTimelonr();
        bq = addTelonrmQuelonrielons(telonrmselonnum);
        QUelonRY_RelonGULAR.stopTimelonrAndIncrelonmelonnt(timelonr);
      }

      relonturn selonarchelonr.relonwritelon(nelonw ConstantScorelonQuelonry(bq)).crelonatelonWelonight(
          selonarchelonr, scorelonModelon, scorelon());
    }

    /**
     * If thelon multi-selongmelonnt telonrm dictionary supports this selongmelonnt/LelonafRelonadelonr, thelonn relonturn thelon
     * InvelonrtelondIndelonx relonprelonselonnting this selongmelonnt.
     *
     * If thelon selongmelonnt beloning quelonrielond right now is not in thelon multi-selongmelonnt telonrm dictionary (elon.g.
     * if it's not optimizelond yelont), relonturn null.
     */
    @Nullablelon
    privatelon InvelonrtelondIndelonx gelontFielonldIndelonxFromMultiTelonrmDictionary(LelonafRelonadelonrContelonxt contelonxt)
        throws IOelonxcelonption {
      if (multiSelongmelonntTelonrmDictionary == null) {
        relonturn null;
      }

      if (contelonxt.relonadelonr() instancelonof elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) {
        elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr =
            (elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) contelonxt.relonadelonr();

        elonarlybirdIndelonxSelongmelonntData selongmelonntData = relonadelonr.gelontSelongmelonntData();
        InvelonrtelondIndelonx fielonldIndelonx = selongmelonntData.gelontFielonldIndelonx(fielonld);

        if (multiSelongmelonntTelonrmDictionary.supportSelongmelonntIndelonx(fielonldIndelonx)) {
          relonturn fielonldIndelonx;
        }
      }

      relonturn null;
    }

    privatelon BoolelonanQuelonry addPreloncomputelondTelonrmQuelonrielons(
        InvelonrtelondIndelonx fielonldIndelonx,
        Telonrmselonnum telonrmselonnum) throws IOelonxcelonption {

      BoolelonanQuelonry.Buildelonr bqBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
      int numClauselons = 0;

      List<TelonrmRankPair> telonrmRankPairs = telonrmIdsPelonrSelongmelonnt.gelont(fielonldIndelonx);
      if (telonrmRankPairs != null) {
        for (TelonrmRankPair pair : telonrmRankPairs) {
          int telonrmId = pair.gelontTelonrmId();
          if (numClauselons >= BoolelonanQuelonry.gelontMaxClauselonCount()) {
            BoolelonanQuelonry savelond = bqBuildelonr.build();
            bqBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
            bqBuildelonr.add(savelond, BoolelonanClauselon.Occur.SHOULD);
            numClauselons = 1;
          }

          Quelonry quelonry;
          if (pair.gelontRank() != -1) {
            quelonry = elonarlybirdQuelonryHelonlpelonr.maybelonWrapWithHitAttributionCollelonctor(
                nelonw SimplelonTelonrmQuelonry(telonrmselonnum, telonrmId),
                pair.gelontRank(),
                fielonldInfo,
                hitAttributelonHelonlpelonr);
          } elonlselon {
            quelonry = nelonw SimplelonTelonrmQuelonry(telonrmselonnum, telonrmId);
          }
          bqBuildelonr.add(elonarlybirdQuelonryHelonlpelonr.maybelonWrapWithTimelonout(quelonry, quelonryTimelonout),
                        BoolelonanClauselon.Occur.SHOULD);
          ++numClauselons;
        }
      }
      relonturn bqBuildelonr.build();
    }

    privatelon BoolelonanQuelonry addTelonrmQuelonrielons(Telonrmselonnum telonrmselonnum) throws IOelonxcelonption {
      final BytelonsRelonf telonrmRelonf = uselonOrdelonrPrelonselonrvingelonncoding
          ? SortablelonLongTelonrmAttributelonImpl.nelonwBytelonsRelonf()
          : LongTelonrmAttributelonImpl.nelonwBytelonsRelonf();

      BoolelonanQuelonry.Buildelonr bqBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
      int numClauselons = 0;

      for (int idx = 0; idx < ids.sizelon(); ++idx) {
        long longTelonrm = ids.gelont(idx);
        if (uselonOrdelonrPrelonselonrvingelonncoding) {
          SortablelonLongTelonrmAttributelonImpl.copyLongToBytelonsRelonf(telonrmRelonf, longTelonrm);
        } elonlselon {
          LongTelonrmAttributelonImpl.copyLongToBytelonsRelonf(telonrmRelonf, longTelonrm);
        }

        if (telonrmselonnum.selonelonkelonxact(telonrmRelonf)) {
          if (numClauselons >= BoolelonanQuelonry.gelontMaxClauselonCount()) {
            BoolelonanQuelonry savelond = bqBuildelonr.build();
            bqBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
            bqBuildelonr.add(savelond, BoolelonanClauselon.Occur.SHOULD);
            numClauselons = 1;
          }

          if (ranks.sizelon() > 0) {
            bqBuildelonr.add(elonarlybirdQuelonryHelonlpelonr.maybelonWrapWithHitAttributionCollelonctor(
                              nelonw SimplelonTelonrmQuelonry(telonrmselonnum, telonrmselonnum.ord()),
                              ranks.gelont(idx),
                              fielonldInfo,
                              hitAttributelonHelonlpelonr),
                          BoolelonanClauselon.Occur.SHOULD);
          } elonlselon {
            bqBuildelonr.add(nelonw SimplelonTelonrmQuelonry(telonrmselonnum, telonrmselonnum.ord()),
                          BoolelonanClauselon.Occur.SHOULD);
          }
          ++numClauselons;
        }
      }

      relonturn bqBuildelonr.build();
    }
  }
}
