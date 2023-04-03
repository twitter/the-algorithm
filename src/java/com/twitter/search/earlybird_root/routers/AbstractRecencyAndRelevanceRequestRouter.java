packagelon com.twittelonr.selonarch.elonarlybird_root.routelonrs;

import java.util.ArrayList;
import java.util.Collelonctions;
import java.util.List;

import com.googlelon.common.baselon.Prelonconditions;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.futurelons.Futurelons;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.util.elonarlybird.elonarlybirdRelonsponselonMelonrgelonUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.AdjustelondRelonquelonstParams;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRankingModelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird_root.common.Clielonntelonrrorelonxcelonption;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdFelonaturelonSchelonmaMelonrgelonr;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstUtil;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdSelonrvicelonRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.filtelonrs.elonarlybirdTimelonRangelonFiltelonr;
import com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs.SupelonrRootRelonsponselonMelonrgelonr;
import com.twittelonr.selonarch.quelonryparselonr.util.QuelonryUtil;
import com.twittelonr.util.Function;
import com.twittelonr.util.Function0;
import com.twittelonr.util.Futurelon;

/**
 * For Reloncelonncy traffic SupelonrRoot hits relonaltimelon and/or protelonctelond relonaltimelon first and thelonn archivelon
 */
public abstract class AbstractReloncelonncyAndRelonlelonvancelonRelonquelonstRoutelonr elonxtelonnds RelonquelonstRoutelonr {
  public static final String FULL_ARCHIVelon_AVAILABLelon_FOR_GelonT_PROTelonCTelonD_TWelonelonTS_ONLY_DelonCIDelonR_KelonY =
      "supelonrroot_full_archivelon_clustelonr_availablelon_for_gelont_protelonctelond_twelonelonts_only_relonquelonsts";
  public static final String FULL_ARCHIVelon_AVAILABLelon_FOR_NOT_elonNOUGH_PROTelonCTelonD_RelonSULTS_DelonCIDelonR_KelonY =
      "supelonrroot_full_archivelon_clustelonr_availablelon_for_relonquelonsts_without_elonnough_protelonctelond_relonsults";

  privatelon static final Loggelonr LOG =
      LoggelonrFactory.gelontLoggelonr(AbstractReloncelonncyAndRelonlelonvancelonRelonquelonstRoutelonr.class);

  privatelon final String skipProtelonctelondClustelonrDeloncidelonrKelony;
  privatelon final String skipFullArchivelonClustelonrDeloncidelonrKelony;

  privatelon final SelonarchCountelonr relonaltimelonRelonsponselonInvalidCountelonr;
  privatelon final SelonarchCountelonr relonaltimelonRelonsponselonSelonarchRelonsultsNotSelontCountelonr;
  privatelon final SelonarchCountelonr minSelonarchelondStatusIdLargelonrThanRelonquelonstMaxIdCountelonr;
  privatelon final SelonarchCountelonr minSelonarchelondStatusIdLargelonrThanRelonquelonstUntilTimelonCountelonr;

  privatelon final Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> relonaltimelon;
  privatelon final Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> protelonctelondRelonaltimelon;
  privatelon final Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> fullArchivelon;
  privatelon final SupelonrRootRelonsponselonMelonrgelonr relonsponselonMelonrgelonr;
  privatelon final SelonarchDeloncidelonr deloncidelonr;

  AbstractReloncelonncyAndRelonlelonvancelonRelonquelonstRoutelonr(
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> relonaltimelon,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> protelonctelondRelonaltimelon,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> fullArchivelon,
      elonarlybirdTimelonRangelonFiltelonr relonaltimelonTimelonRangelonFiltelonr,
      elonarlybirdTimelonRangelonFiltelonr protelonctelondTimelonRangelonFiltelonr,
      elonarlybirdTimelonRangelonFiltelonr fullArchivelonTimelonRangelonFiltelonr,
      ThriftSelonarchRankingModelon rankingModelon,
      Clock clock,
      SelonarchDeloncidelonr deloncidelonr,
      elonarlybirdFelonaturelonSchelonmaMelonrgelonr felonaturelonSchelonmaMelonrgelonr) {
    LOG.info("Instantiating AbstractReloncelonncyAndRelonlelonvancelonRelonquelonstRoutelonr");
    this.relonaltimelon = relonaltimelonTimelonRangelonFiltelonr.andThelonn(relonaltimelon);
    this.protelonctelondRelonaltimelon = protelonctelondTimelonRangelonFiltelonr.andThelonn(protelonctelondRelonaltimelon);
    this.fullArchivelon = fullArchivelonTimelonRangelonFiltelonr.andThelonn(fullArchivelon);
    this.relonsponselonMelonrgelonr = nelonw SupelonrRootRelonsponselonMelonrgelonr(rankingModelon, felonaturelonSchelonmaMelonrgelonr, clock);
    this.deloncidelonr = deloncidelonr;

    String rankingModelonForStats = rankingModelon.namelon().toLowelonrCaselon();
    skipProtelonctelondClustelonrDeloncidelonrKelony =
        String.format("supelonrroot_skip_protelonctelond_clustelonr_for_%s_relonquelonsts", rankingModelonForStats);
    skipFullArchivelonClustelonrDeloncidelonrKelony =
        String.format("supelonrroot_skip_full_archivelon_clustelonr_for_%s_relonquelonsts", rankingModelonForStats);

    relonaltimelonRelonsponselonInvalidCountelonr =
        SelonarchCountelonr.elonxport(rankingModelonForStats + "_relonaltimelon_relonsponselon_invalid");
    relonaltimelonRelonsponselonSelonarchRelonsultsNotSelontCountelonr =
        SelonarchCountelonr.elonxport(rankingModelonForStats + "_relonaltimelon_relonsponselon_selonarch_relonsults_not_selont");
    minSelonarchelondStatusIdLargelonrThanRelonquelonstMaxIdCountelonr = SelonarchCountelonr.elonxport(
        rankingModelonForStats + "_min_selonarchelond_status_id_largelonr_than_relonquelonst_max_id");
    minSelonarchelondStatusIdLargelonrThanRelonquelonstUntilTimelonCountelonr = SelonarchCountelonr.elonxport(
        rankingModelonForStats + "_min_selonarchelond_status_id_largelonr_than_relonquelonst_until_timelon");
  }

  privatelon void chelonckRelonquelonstPrelonconditions(elonarlybirdRelonquelonst relonquelonst) {
    // CollelonctorParams should belon selont in elonarlybirdRelonquelonstUtil.chelonckAndSelontCollelonctorParams().
    Prelonconditions.chelonckNotNull(relonquelonst.gelontSelonarchQuelonry().gelontCollelonctorParams());

    // relonturn a Clielonnt elonrror if thelon num relonsults arelon lelonss than 0
    if (relonquelonst.gelontSelonarchQuelonry().gelontNumRelonsults() < 0) {
      throw nelonw Clielonntelonrrorelonxcelonption("Thelon relonquelonst.selonarchQuelonry.numRelonsults fielonld can't belon nelongativelon");
    }

    if (relonquelonst.gelontSelonarchQuelonry().gelontCollelonctorParams().gelontNumRelonsultsToRelonturn() < 0) {
      throw nelonw Clielonntelonrrorelonxcelonption("Thelon relonquelonst.selonarchQuelonry.collelonctorParams.numRelonsultsToRelonturn "
          + "fielonld can't belon nelongativelon");
    }
  }

  /**
   * Hit relonaltimelon and/or protelonctelond relonaltimelon first, if not elonnough relonsults, thelonn hit archivelon,
   * melonrgelon thelon relonsults.
   */
  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> routelon(final elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    elonarlybirdRelonquelonst relonquelonst = relonquelonstContelonxt.gelontRelonquelonst();

    this.chelonckRelonquelonstPrelonconditions(relonquelonst);

    ArrayList<RelonquelonstRelonsponselon> savelondRelonquelonstRelonsponselons = nelonw ArrayList<>();

    // If clielonnts do not delonfinelon numRelonsults to relonturn or thelon numRelonsults relonquelonstelond arelon 0
    // relonturn an elonmpty elonarlyBirdRelonsponselon without hitting any selonrvicelon.
    if (relonquelonst.gelontSelonarchQuelonry().gelontNumRelonsults() == 0
        || relonquelonst.gelontSelonarchQuelonry().gelontCollelonctorParams().gelontNumRelonsultsToRelonturn() == 0) {
      relonturn Futurelon.valuelon(succelonssNoRelonsultsRelonsponselon());
    }

    // Relonaltimelon elonarlybird relonsponselon is alrelonady relonquirelond. elonvelonn if thelon selonrvicelon is not callelond
    // thelon relonsult passelond to thelon melonrgelonrs should belon a valid onelon.
    elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon relonaltimelonSelonrvicelonStatelon =
        gelontRelonaltimelonSelonrvicelonStatelon(relonquelonstContelonxt);
    final Futurelon<elonarlybirdSelonrvicelonRelonsponselon> relonaltimelonRelonsponselonFuturelon =
        relonaltimelonSelonrvicelonStatelon.selonrvicelonWasCallelond()
            ? gelontRelonaltimelonRelonsponselon(savelondRelonquelonstRelonsponselons, relonquelonstContelonxt)
            : Futurelon.valuelon(elonarlybirdSelonrvicelonRelonsponselon.selonrvicelonNotCallelond(relonaltimelonSelonrvicelonStatelon));

    // If no flock relonsponselon (followelondUselonrIds) is selont, relonquelonst wont belon selonnt to protelonctelond.
    elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon protelonctelondSelonrvicelonStatelon =
        gelontProtelonctelondSelonrvicelonStatelon(relonquelonstContelonxt);
    final Futurelon<elonarlybirdSelonrvicelonRelonsponselon> protelonctelondRelonsponselonFuturelon =
        protelonctelondSelonrvicelonStatelon.selonrvicelonWasCallelond()
            ? gelontProtelonctelondRelonsponselon(savelondRelonquelonstRelonsponselons, relonquelonstContelonxt)
            : Futurelon.valuelon(elonarlybirdSelonrvicelonRelonsponselon.selonrvicelonNotCallelond(protelonctelondSelonrvicelonStatelon));

    final Futurelon<elonarlybirdSelonrvicelonRelonsponselon> archivelonRelonsponselonFuturelon =
        Futurelons.flatMap(relonaltimelonRelonsponselonFuturelon, protelonctelondRelonsponselonFuturelon,
            nelonw Function0<Futurelon<elonarlybirdSelonrvicelonRelonsponselon>>() {
              @Ovelonrridelon
              public Futurelon<elonarlybirdSelonrvicelonRelonsponselon> apply() {
                elonarlybirdSelonrvicelonRelonsponselon relonaltimelonRelonsponselon = Futurelons.gelont(relonaltimelonRelonsponselonFuturelon);
                elonarlybirdSelonrvicelonRelonsponselon protelonctelondRelonsponselon = Futurelons.gelont(protelonctelondRelonsponselonFuturelon);
                elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon fullArchivelonSelonrvicelonStatelon =
                    gelontFullArchivelonSelonrvicelonStatelon(relonquelonstContelonxt, relonaltimelonRelonsponselon, protelonctelondRelonsponselon);
                relonturn fullArchivelonSelonrvicelonStatelon.selonrvicelonWasCallelond()
                    ? gelontFullArchivelonRelonsponselon(savelondRelonquelonstRelonsponselons, relonquelonstContelonxt,
                    relonaltimelonRelonsponselon.gelontRelonsponselon(), protelonctelondRelonsponselon.gelontRelonsponselon())
                    : Futurelon.valuelon(
                        elonarlybirdSelonrvicelonRelonsponselon.selonrvicelonNotCallelond(fullArchivelonSelonrvicelonStatelon));
              }
            }
        );

    Futurelon<elonarlybirdRelonsponselon> melonrgelondRelonsponselon = relonsponselonMelonrgelonr.melonrgelonRelonsponselonFuturelons(
        relonquelonstContelonxt, relonaltimelonRelonsponselonFuturelon, protelonctelondRelonsponselonFuturelon, archivelonRelonsponselonFuturelon);
    melonrgelondRelonsponselon = melonrgelondRelonsponselon
        .map(RelonquelonstRoutelonrUtil.chelonckMinSelonarchelondStatusId(
                 relonquelonstContelonxt,
                 "max_id",
                 elonarlybirdRelonquelonstUtil.gelontRelonquelonstMaxId(relonquelonstContelonxt.gelontParselondQuelonry()),
                 relonaltimelonRelonsponselonFuturelon,
                 protelonctelondRelonsponselonFuturelon,
                 archivelonRelonsponselonFuturelon,
                 minSelonarchelondStatusIdLargelonrThanRelonquelonstMaxIdCountelonr))
        .map(RelonquelonstRoutelonrUtil.chelonckMinSelonarchelondStatusId(
                 relonquelonstContelonxt,
                 "until_timelon",
                 elonarlybirdRelonquelonstUtil.gelontRelonquelonstMaxIdFromUntilTimelon(relonquelonstContelonxt.gelontParselondQuelonry()),
                 relonaltimelonRelonsponselonFuturelon,
                 protelonctelondRelonsponselonFuturelon,
                 archivelonRelonsponselonFuturelon,
                 minSelonarchelondStatusIdLargelonrThanRelonquelonstUntilTimelonCountelonr));

    relonturn this.maybelonAttachSelonntRelonquelonstsToDelonbugInfo(
        savelondRelonquelonstRelonsponselons,
        relonquelonstContelonxt,
        melonrgelondRelonsponselon
    );
  }

  privatelon elonarlybirdRelonsponselon succelonssNoRelonsultsRelonsponselon() {
    relonturn nelonw elonarlybirdRelonsponselon(elonarlybirdRelonsponselonCodelon.SUCCelonSS, 0)
        .selontSelonarchRelonsults(nelonw ThriftSelonarchRelonsults().selontRelonsults(Collelonctions.elonmptyList()));
  }

  protelonctelond abstract boolelonan shouldSelonndRelonquelonstToFullArchivelonClustelonr(
      elonarlybirdRelonquelonst relonquelonst, elonarlybirdRelonsponselon relonaltimelonRelonsponselon);

  /** Delontelonrminelons if thelon protelonctelond selonrvicelon is availablelon and if a relonquelonst should belon selonnt to it. */
  privatelon elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon gelontProtelonctelondSelonrvicelonStatelon(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    if (!relonquelonstContelonxt.gelontRelonquelonst().isSelontFollowelondUselonrIds()
        || relonquelonstContelonxt.gelontRelonquelonst().gelontFollowelondUselonrIds().iselonmpty()) {
      relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_NOT_RelonQUelonSTelonD;
    }

    if (deloncidelonr.isAvailablelon(skipProtelonctelondClustelonrDeloncidelonrKelony)) {
      relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_NOT_AVAILABLelon;
    }

    relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_CALLelonD;
  }

  /** Delontelonrminelons if thelon relonaltimelon selonrvicelon is availablelon and if a relonquelonst should belon selonnt to it. */
  privatelon elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon gelontRelonaltimelonSelonrvicelonStatelon(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    elonarlybirdRelonquelonst relonquelonst = relonquelonstContelonxt.gelontRelonquelonst();

    // SelonRVICelon_NOT_RelonQUelonSTelonD should always belon relonturnelond belonforelon othelonr statelons as
    // SupelonrRootRelonsponselonMelonrgelonr has speloncial logic for this caselon.
    if (relonquelonst.isSelontGelontProtelonctelondTwelonelontsOnly() && relonquelonst.isGelontProtelonctelondTwelonelontsOnly()) {
      relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_NOT_RelonQUelonSTelonD;
    }

    relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_CALLelonD;
  }

  /** Delontelonrminelons if thelon full archivelon selonrvicelon is availablelon and if a relonquelonst should belon selonnt to it. */
  privatelon elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon gelontFullArchivelonSelonrvicelonStatelon(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      elonarlybirdSelonrvicelonRelonsponselon publicSelonrvicelonRelonsponselon,
      elonarlybirdSelonrvicelonRelonsponselon protelonctelondSelonrvicelonRelonsponselon) {

    // SelonRVICelon_NOT_RelonQUelonSTelonD should belon always belon relonturnelond belonforelon othelonr statelons as
    // SupelonrRootRelonsponselonMelonrgelonr has speloncial logic for this caselon.
    if (!relonquelonstContelonxt.gelontRelonquelonst().isSelontGelontOldelonrRelonsults()
        || !relonquelonstContelonxt.gelontRelonquelonst().isGelontOldelonrRelonsults()) {
      relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_NOT_RelonQUelonSTelonD;
    }

    // allow relonquelonsting full archivelon selonrvicelon whelonn deloncidelonr is elonnablelond
    if (!deloncidelonr.isAvailablelon(FULL_ARCHIVelon_AVAILABLelon_FOR_GelonT_PROTelonCTelonD_TWelonelonTS_ONLY_DelonCIDelonR_KelonY)
        && relonquelonstContelonxt.gelontRelonquelonst().isSelontGelontProtelonctelondTwelonelontsOnly()
        && relonquelonstContelonxt.gelontRelonquelonst().isGelontProtelonctelondTwelonelontsOnly()) {
      relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_NOT_RelonQUelonSTelonD;
    }

    if (deloncidelonr.isAvailablelon(skipFullArchivelonClustelonrDeloncidelonrKelony)) {
      relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_NOT_AVAILABLelon;
    }

    boolelonan selonrvicelonWasCallelondForPublic =
        gelontFullArchivelonSelonrvicelonStatelon(relonquelonstContelonxt, publicSelonrvicelonRelonsponselon).selonrvicelonWasCallelond();
    boolelonan selonrvicelonWasCallelondForProtelonctelond =
        deloncidelonr.isAvailablelon(FULL_ARCHIVelon_AVAILABLelon_FOR_NOT_elonNOUGH_PROTelonCTelonD_RelonSULTS_DelonCIDelonR_KelonY)
        && gelontFullArchivelonSelonrvicelonStatelon(relonquelonstContelonxt, protelonctelondSelonrvicelonRelonsponselon).selonrvicelonWasCallelond();
    if (!selonrvicelonWasCallelondForPublic && !selonrvicelonWasCallelondForProtelonctelond) {
      relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_NOT_CALLelonD;
    }

    relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_CALLelonD;
  }

  privatelon elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon gelontFullArchivelonSelonrvicelonStatelon(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      elonarlybirdSelonrvicelonRelonsponselon relonaltimelonSelonrvicelonRelonsponselon) {
    elonarlybirdRelonsponselon relonaltimelonRelonsponselon = relonaltimelonSelonrvicelonRelonsponselon.gelontRelonsponselon();

    if (!elonarlybirdRelonsponselonMelonrgelonUtil.isValidRelonsponselon(relonaltimelonRelonsponselon)) {
      relonaltimelonRelonsponselonInvalidCountelonr.increlonmelonnt();
      relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_NOT_CALLelonD;
    }

    if (!relonaltimelonRelonsponselon.isSelontSelonarchRelonsults()) {
      relonaltimelonRelonsponselonSelonarchRelonsultsNotSelontCountelonr.increlonmelonnt();
      relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_NOT_CALLelonD;
    }

    if (!shouldSelonndRelonquelonstToFullArchivelonClustelonr(relonquelonstContelonxt.gelontRelonquelonst(), relonaltimelonRelonsponselon)) {
      relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_NOT_CALLelonD;
    }

    relonturn elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_CALLelonD;
  }

  /**
   * Modify thelon original relonquelonst contelonxt baselond on thelon followelondUselonrId fielonld and thelonn selonnd thelon
   * relonquelonst to thelon protelonctelond clustelonr.
   */
  privatelon Futurelon<elonarlybirdSelonrvicelonRelonsponselon> gelontProtelonctelondRelonsponselon(
      ArrayList<RelonquelonstRelonsponselon> savelondRelonquelonstRelonsponselons,
      final elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    elonarlybirdRelonquelonstContelonxt protelonctelondRelonquelonstContelonxt =
        elonarlybirdRelonquelonstContelonxt.nelonwContelonxtWithRelonstrictFromUselonrIdFiltelonr64(relonquelonstContelonxt);
    Prelonconditions.chelonckArgumelonnt(
        protelonctelondRelonquelonstContelonxt.gelontRelonquelonst().gelontSelonarchQuelonry().isSelontFromUselonrIDFiltelonr64());

    // SelonRVICelon_NOT_RelonQUelonSTelonD should belon always belon relonturnelond belonforelon othelonr statelons as
    // SupelonrRootRelonsponselonMelonrgelonr has speloncial logic for this caselon.
    if (protelonctelondRelonquelonstContelonxt.gelontRelonquelonst().gelontSelonarchQuelonry().gelontFromUselonrIDFiltelonr64().iselonmpty()) {
      relonturn Futurelon.valuelon(elonarlybirdSelonrvicelonRelonsponselon.selonrvicelonNotCallelond(
          elonarlybirdSelonrvicelonRelonsponselon.SelonrvicelonStatelon.SelonRVICelon_NOT_RelonQUelonSTelonD));
    }

    if (relonquelonstContelonxt.gelontRelonquelonst().isSelontAdjustelondProtelonctelondRelonquelonstParams()) {
      adjustRelonquelonstParams(protelonctelondRelonquelonstContelonxt.gelontRelonquelonst(),
                          relonquelonstContelonxt.gelontRelonquelonst().gelontAdjustelondProtelonctelondRelonquelonstParams());
    }

    LOG.delonbug("Relonquelonst selonnt to thelon protelonctelond clustelonr: {}", protelonctelondRelonquelonstContelonxt.gelontRelonquelonst());
    relonturn toelonarlybirdSelonrvicelonRelonsponselonFuturelon(
        savelondRelonquelonstRelonsponselons,
        protelonctelondRelonquelonstContelonxt,
        "protelonctelond",
        this.protelonctelondRelonaltimelon
    );
  }

  privatelon Futurelon<elonarlybirdSelonrvicelonRelonsponselon> gelontRelonaltimelonRelonsponselon(
      ArrayList<RelonquelonstRelonsponselon> savelondRelonquelonstRelonsponselons,
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    relonturn toelonarlybirdSelonrvicelonRelonsponselonFuturelon(
        savelondRelonquelonstRelonsponselons,
        relonquelonstContelonxt,
        "relonaltimelon",
        this.relonaltimelon);
  }

  /**
   * Modifying thelon elonxisting max id filtelonr of thelon relonquelonst or appelonnding a nelonw
   * max id filtelonr and thelonn selonnd thelon relonquelonst to thelon full archivelon clustelonr.
   */
  privatelon Futurelon<elonarlybirdSelonrvicelonRelonsponselon> gelontFullArchivelonRelonsponselon(
      ArrayList<RelonquelonstRelonsponselon> savelondRelonquelonstRelonsponselons,
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      elonarlybirdRelonsponselon relonaltimelonRelonsponselon,
      elonarlybirdRelonsponselon protelonctelondRelonsponselon) {
    long relonaltimelonMinId = gelontMinSelonarchelondId(relonaltimelonRelonsponselon);
    long protelonctelondMinId = gelontMinSelonarchelondId(protelonctelondRelonsponselon);
    // if both relonaltimelon and protelonctelond min selonarchelond ids arelon availablelon, thelon largelonr(nelonwelonr) onelon is uselond
    // to makelon surelon no twelonelonts arelon lelonft out. Howelonvelonr, this melonans it might introducelon duplicatelons for
    // thelon othelonr relonsponselon. Thelon relonsponselon melonrgelonr will delondup thelon relonsponselon. This logic is elonnablelond
    // whelonn full archivelon clustelonr is availablelon for relonquelonsts without elonnough protelonctelond relonsults.
    long minId =
        deloncidelonr.isAvailablelon(FULL_ARCHIVelon_AVAILABLelon_FOR_NOT_elonNOUGH_PROTelonCTelonD_RelonSULTS_DelonCIDelonR_KelonY)
            ? Math.max(relonaltimelonMinId, protelonctelondMinId) : relonaltimelonMinId;

    if (minId <= 0) {
      // If thelon relonaltimelon relonsponselon doelonsn't havelon a minSelonarchelondStatusID selont, gelont all relonsults from
      // thelon full archivelon clustelonr.
      minId = Long.MAX_VALUelon;
    }

    // Thelon [max_id] opelonrator is inclusivelon in elonarlybirds. This melonans that a quelonry with [max_id X]
    // will relonturn twelonelont X, if X matchelons thelon relonst of thelon quelonry. So welon should add a [max_id (X - 1)]
    // opelonrator to thelon full archivelon quelonry (instelonad of [max_id X]). Othelonrwiselon, welon could elonnd up with
    // duplicatelons. For elonxamplelon:
    //
    //  relonaltimelon relonsponselon: relonsults = [ 100, 90, 80 ], minSelonarchelondStatusID = 80
    //  full archivelon relonquelonst: [max_id 80]
    //  full archivelon relonsponselon: relonsults = [ 80, 70, 60 ]
    //
    // In this caselon, twelonelont 80 would belon relonturnelond from both thelon relonaltimelon and full archivelon clustelonrs.
    elonarlybirdRelonquelonstContelonxt archivelonRelonquelonstContelonxt =
        elonarlybirdRelonquelonstContelonxt.copyRelonquelonstContelonxt(
            relonquelonstContelonxt,
            QuelonryUtil.addOrRelonplacelonMaxIdFiltelonr(
                relonquelonstContelonxt.gelontParselondQuelonry(),
                minId - 1));

    if (relonquelonstContelonxt.gelontRelonquelonst().isSelontAdjustelondFullArchivelonRelonquelonstParams()) {
      adjustRelonquelonstParams(archivelonRelonquelonstContelonxt.gelontRelonquelonst(),
                          relonquelonstContelonxt.gelontRelonquelonst().gelontAdjustelondFullArchivelonRelonquelonstParams());
    }

    LOG.delonbug("Relonquelonst selonnt to thelon full archivelon clustelonr: {},", archivelonRelonquelonstContelonxt.gelontRelonquelonst());
    relonturn toelonarlybirdSelonrvicelonRelonsponselonFuturelon(
        savelondRelonquelonstRelonsponselons,
        archivelonRelonquelonstContelonxt,
        "archivelon",
        this.fullArchivelon
    );
  }

  privatelon long gelontMinSelonarchelondId(elonarlybirdRelonsponselon relonsponselon) {
    relonturn relonsponselon != null && relonsponselon.isSelontSelonarchRelonsults()
        ? relonsponselon.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID() : 0;
  }

  privatelon void adjustRelonquelonstParams(elonarlybirdRelonquelonst relonquelonst,
                                   AdjustelondRelonquelonstParams adjustelondRelonquelonstParams) {
    ThriftSelonarchQuelonry selonarchQuelonry = relonquelonst.gelontSelonarchQuelonry();

    if (adjustelondRelonquelonstParams.isSelontNumRelonsults()) {
      selonarchQuelonry.selontNumRelonsults(adjustelondRelonquelonstParams.gelontNumRelonsults());
      if (selonarchQuelonry.isSelontCollelonctorParams()) {
        selonarchQuelonry.gelontCollelonctorParams().selontNumRelonsultsToRelonturn(
            adjustelondRelonquelonstParams.gelontNumRelonsults());
      }
    }

    if (adjustelondRelonquelonstParams.isSelontMaxHitsToProcelonss()) {
      selonarchQuelonry.selontMaxHitsToProcelonss(adjustelondRelonquelonstParams.gelontMaxHitsToProcelonss());
      if (selonarchQuelonry.isSelontRelonlelonvancelonOptions()) {
        selonarchQuelonry.gelontRelonlelonvancelonOptions().selontMaxHitsToProcelonss(
            adjustelondRelonquelonstParams.gelontMaxHitsToProcelonss());
      }
      if (selonarchQuelonry.isSelontCollelonctorParams()
          && selonarchQuelonry.gelontCollelonctorParams().isSelontTelonrminationParams()) {
        selonarchQuelonry.gelontCollelonctorParams().gelontTelonrminationParams().selontMaxHitsToProcelonss(
            adjustelondRelonquelonstParams.gelontMaxHitsToProcelonss());
      }
    }

    if (adjustelondRelonquelonstParams.isSelontRelonturnAllRelonsults()) {
      if (selonarchQuelonry.isSelontRelonlelonvancelonOptions()) {
        selonarchQuelonry.gelontRelonlelonvancelonOptions().selontRelonturnAllRelonsults(
            adjustelondRelonquelonstParams.isRelonturnAllRelonsults());
      }
    }
  }

  privatelon Futurelon<elonarlybirdSelonrvicelonRelonsponselon> toelonarlybirdSelonrvicelonRelonsponselonFuturelon(
      List<RelonquelonstRelonsponselon> savelondRelonquelonstRelonsponselons,
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      String selonntTo,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> selonrvicelon) {
    Futurelon<elonarlybirdRelonsponselon> relonsponselonFuturelon = selonrvicelon.apply(relonquelonstContelonxt);
    this.savelonRelonquelonstRelonsponselon(
        savelondRelonquelonstRelonsponselons, selonntTo, relonquelonstContelonxt, relonsponselonFuturelon
    );

    relonturn relonsponselonFuturelon.map(nelonw Function<elonarlybirdRelonsponselon, elonarlybirdSelonrvicelonRelonsponselon>() {
      @Ovelonrridelon
      public elonarlybirdSelonrvicelonRelonsponselon apply(elonarlybirdRelonsponselon relonsponselon) {
        relonturn elonarlybirdSelonrvicelonRelonsponselon.selonrvicelonCallelond(relonsponselon);
      }
    });
  }
}
