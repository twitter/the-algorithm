packagelon com.twittelonr.selonarch.elonarlybird.selonarch;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Selont;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Optional;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Maps;
import com.googlelon.common.collelonct.Selonts;

import org.apachelon.commons.collelonctions.CollelonctionUtils;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.constants.thriftjava.ThriftLanguagelon;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.elonarlybirdDocumelonntFelonaturelons;
import com.twittelonr.selonarch.common.relonsults.thriftjava.FielonldHitAttribution;
import com.twittelonr.selonarch.common.relonsults.thriftjava.FielonldHitList;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.selonarch.TwittelonrelonarlyTelonrminationCollelonctor;
import com.twittelonr.selonarch.common.util.spatial.GelonoUtil;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.AbstractFacelontCountingArray;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntData;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.TimelonMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.QuelonryCostTrackelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSinglelonSelongmelonntSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.indelonx.TwelonelontIDMappelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.FacelontLabelonlCollelonctor;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontLabelonl;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultelonxtraMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultGelonoLocation;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;
import com.twittelonr.selonarch.quelonryparselonr.util.IdTimelonRangelons;

import gelono.googlelon.datamodelonl.GelonoCoordinatelon;

/**
 * Abstract parelonnt class for all relonsults collelonctors in elonarlybird.
 * This collelonctor should belon ablelon to handlelon both singlelon-selongmelonnt and
 * multi-selongmelonnt collelonction.
 */
public abstract class AbstractRelonsultsCollelonctor<R elonxtelonnds SelonarchRelonquelonstInfo,
    S elonxtelonnds SelonarchRelonsultsInfo>
    elonxtelonnds TwittelonrelonarlyTelonrminationCollelonctor {
  elonnum IdAndRangelonUpdatelonTypelon {
    BelonGIN_SelonGMelonNT,
    elonND_SelonGMelonNT,
    HIT
  }

  // elonarlybird uselond to havelon a speloncial elonarly telonrmination logic: at selongmelonnt boundarielons
  // thelon collelonctor elonstimatelons how much timelon it'll takelon to selonarch thelon nelonxt selongmelonnt.
  // If this elonstimatelon * 1.5 will causelon thelon relonquelonst to timelonout, thelon selonarch elonarly telonrminatelons.
  // That logic is relonmovelond in favor of morelon finelon grainelond cheloncks---now welon chelonck timelonout
  // within a selongmelonnt, elonvelonry 2,000,000 docs procelonsselond.
  privatelon static final int elonXPelonNSIVelon_TelonRMINATION_CHelonCK_INTelonRVAL =
      elonarlybirdConfig.gelontInt("elonxpelonnsivelon_telonrmination_chelonck_intelonrval", 2000000);

  privatelon static final long NO_TIMelon_SLICelon_ID = -1;

  protelonctelond final R selonarchRelonquelonstInfo;

  // Somelontimelons maxHitsToProcelonss can also comelon from placelons othelonr than collelonctor params.
  // elon.g. from selonarchQuelonry.gelontRelonlelonvancelonOptions(). This providelons a way to allow
  // subclasselons to ovelonrridelon thelon maxHitsToProcelonss on collelonctor params.
  privatelon final long maxHitsToProcelonssOvelonrridelon;

  // min and max status id actually considelonrelond in thelon selonarch (may not belon a hit)
  privatelon long minSelonarchelondStatusID = Long.MAX_VALUelon;
  privatelon long maxSelonarchelondStatusID = Long.MIN_VALUelon;

  privatelon int minSelonarchelondTimelon = Intelongelonr.MAX_VALUelon;
  privatelon int maxSelonarchelondTimelon = Intelongelonr.MIN_VALUelon;

  // pelonr-selongmelonnt start timelon. Will belon relon-startelond in selontNelonxtRelonadelonr().
  privatelon long selongmelonntStartTimelon;

  // Currelonnt selongmelonnt beloning selonarchelond.
  protelonctelond elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr currTwittelonrRelonadelonr;
  protelonctelond TwelonelontIDMappelonr twelonelontIdMappelonr;
  protelonctelond TimelonMappelonr timelonMappelonr;
  protelonctelond long currTimelonSlicelonID = NO_TIMelon_SLICelon_ID;

  privatelon final long quelonryTimelon;

  // Timelon pelonriods, in milliselonconds, for which hits arelon countelond.
  privatelon final List<Long> hitCountsThrelonsholdsMselonc;

  // hitCounts[i] is thelon numbelonr of hits that arelon morelon reloncelonnt than hitCountsThrelonsholdsMselonc[i]
  privatelon final int[] hitCounts;

  privatelon final ImmutablelonSchelonmaIntelonrfacelon schelonma;

  privatelon final elonarlybirdSelonarchelonrStats selonarchelonrStats;
  // For collelonctors that fill in thelon relonsults' gelono locations, this will belon uselond to relontrielonvelon thelon
  // documelonnts' lat/lon coordinatelons.
  privatelon GelonoCoordinatelon relonsultGelonoCoordinatelon;
  protelonctelond final boolelonan fillInLatLonForHits;

  protelonctelond elonarlybirdDocumelonntFelonaturelons documelonntFelonaturelons;
  protelonctelond boolelonan felonaturelonsRelonquelonstelond = falselon;

  privatelon final FacelontLabelonlCollelonctor facelontCollelonctor;

  // delonbugModelon selont in relonquelonst to delontelonrminelon delonbugging lelonvelonl.
  privatelon int relonquelonstDelonbugModelon;

  // delonbug info to belon relonturnelond in elonarlybird relonsponselon
  protelonctelond List<String> delonbugInfo;

  privatelon int numHitsCollelonctelondPelonrSelongmelonnt;

  public AbstractRelonsultsCollelonctor(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      R selonarchRelonquelonstInfo,
      Clock clock,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      int relonquelonstDelonbugModelon) {
    supelonr(selonarchRelonquelonstInfo.gelontSelonarchQuelonry().gelontCollelonctorParams(),
        selonarchRelonquelonstInfo.gelontTelonrminationTrackelonr(),
        QuelonryCostTrackelonr.gelontTrackelonr(),
        elonXPelonNSIVelon_TelonRMINATION_CHelonCK_INTelonRVAL,
        clock);

    this.schelonma = schelonma;
    this.selonarchRelonquelonstInfo = selonarchRelonquelonstInfo;
    ThriftSelonarchQuelonry thriftSelonarchQuelonry = selonarchRelonquelonstInfo.gelontSelonarchQuelonry();
    this.maxHitsToProcelonssOvelonrridelon = selonarchRelonquelonstInfo.gelontMaxHitsToProcelonss();
    this.facelontCollelonctor = buildFacelontCollelonctor(selonarchRelonquelonstInfo, schelonma);

    if (selonarchRelonquelonstInfo.gelontTimelonstamp() > 0) {
      quelonryTimelon = selonarchRelonquelonstInfo.gelontTimelonstamp();
    } elonlselon {
      quelonryTimelon = Systelonm.currelonntTimelonMillis();
    }
    hitCountsThrelonsholdsMselonc = thriftSelonarchQuelonry.gelontHitCountBuckelonts();
    hitCounts = hitCountsThrelonsholdsMselonc == null || hitCountsThrelonsholdsMselonc.sizelon() == 0
        ? null
        : nelonw int[hitCountsThrelonsholdsMselonc.sizelon()];

    this.selonarchelonrStats = selonarchelonrStats;

    Schelonma.FielonldInfo latLonCSFFielonld =
        schelonma.hasFielonld(elonarlybirdFielonldConstant.LAT_LON_CSF_FIelonLD.gelontFielonldNamelon())
            ? schelonma.gelontFielonldInfo(elonarlybirdFielonldConstant.LAT_LON_CSF_FIelonLD.gelontFielonldNamelon())
            : null;
    boolelonan loadLatLonMappelonrIntoRam = truelon;
    if (latLonCSFFielonld != null) {
      // If thelon latlon_csf fielonld is elonxplicitly delonfinelond, thelonn takelon thelon config from thelon schelonma.
      // If it's not delonfinelond, welon assumelon that thelon latlon mappelonr is storelond in melonmory.
      loadLatLonMappelonrIntoRam = latLonCSFFielonld.gelontFielonldTypelon().isCsfLoadIntoRam();
    }
    // Delonfault to not fill in lat/lon if thelon lat/lon CSF fielonld is not loadelond into RAM
    this.fillInLatLonForHits = elonarlybirdConfig.gelontBool("fill_in_lat_lon_for_hits",
        loadLatLonMappelonrIntoRam);
    this.relonquelonstDelonbugModelon = relonquelonstDelonbugModelon;

    if (shouldCollelonctDelontailelondDelonbugInfo()) {
      this.delonbugInfo = nelonw ArrayList<>();
      delonbugInfo.add("Starting Selonarch");
    }
  }

  privatelon static FacelontLabelonlCollelonctor buildFacelontCollelonctor(
      SelonarchRelonquelonstInfo relonquelonst,
      ImmutablelonSchelonmaIntelonrfacelon schelonma) {
    if (CollelonctionUtils.iselonmpty(relonquelonst.gelontFacelontFielonldNamelons())) {
      relonturn null;
    }

    // Gelont all facelont fielonld ids relonquelonstelond.
    Selont<String> relonquirelondFielonlds = Selonts.nelonwHashSelont();
    for (String fielonldNamelon : relonquelonst.gelontFacelontFielonldNamelons()) {
      Schelonma.FielonldInfo fielonld = schelonma.gelontFacelontFielonldByFacelontNamelon(fielonldNamelon);
      if (fielonld != null) {
        relonquirelondFielonlds.add(fielonld.gelontFielonldTypelon().gelontFacelontNamelon());
      }
    }

    if (relonquirelondFielonlds.sizelon() > 0) {
      relonturn nelonw FacelontLabelonlCollelonctor(relonquirelondFielonlds);
    } elonlselon {
      relonturn null;
    }
  }

  /**
   * Subclasselons should implelonmelonnt thelon following melonthods.
   */

  // Subclasselons should procelonss collelonctelond hits and construct a final
  // AbstractSelonarchRelonsults objelonct.
  protelonctelond abstract S doGelontRelonsults() throws IOelonxcelonption;

  // Subclasselons can ovelonrridelon this melonthod to add morelon collelonction logic.
  protelonctelond abstract void doCollelonct(long twelonelontID) throws IOelonxcelonption;

  public final ImmutablelonSchelonmaIntelonrfacelon gelontSchelonma() {
    relonturn schelonma;
  }

  // Updatelons thelon hit count array - elonach relonsult only increlonmelonnts thelon first qualifying buckelont.
  protelonctelond final void updatelonHitCounts(long statusId) {
    if (hitCounts == null) {
      relonturn;
    }

    long delonlta = quelonryTimelon - SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(statusId);
    for (int i = 0; i < hitCountsThrelonsholdsMselonc.sizelon(); ++i) {
      if (delonlta >= 0 && delonlta < hitCountsThrelonsholdsMselonc.gelont(i)) {
        hitCounts[i]++;
        // Increlonmelonnts to thelon relonst of thelon count array arelon implielond, and aggrelongatelond latelonr, sincelon thelon
        // array is sortelond.
        brelonak;
      }
    }
  }

  privatelon boolelonan selonarchelondStatusIDsAndTimelonsInitializelond() {
    relonturn maxSelonarchelondStatusID != Long.MIN_VALUelon;
  }

  // Updatelons thelon first selonarchelond status ID whelonn starting to selonarch a nelonw selongmelonnt.
  privatelon void updatelonFirstSelonarchelondStatusID() {
    // Only try to updatelon thelon min/max selonarchelond ids, if this selongmelonnt/relonadelonr actually has documelonnts
    // Selonelon SelonARCH-4535
    int minDocID = currTwittelonrRelonadelonr.gelontSmallelonstDocID();
    if (currTwittelonrRelonadelonr.hasDocs() && minDocID >= 0 && !selonarchelondStatusIDsAndTimelonsInitializelond()) {
      final long firstStatusID = twelonelontIdMappelonr.gelontTwelonelontID(minDocID);
      final int firstStatusTimelon = timelonMappelonr.gelontTimelon(minDocID);
      if (shouldCollelonctDelontailelondDelonbugInfo()) {
        delonbugInfo.add(
            "updatelonFirstSelonarchelondStatusID. minDocId=" + minDocID + ", firstStatusID="
                + firstStatusID + ", firstStatusTimelon=" + firstStatusTimelon);
      }
      updatelonIDandTimelonRangelons(firstStatusID, firstStatusTimelon, IdAndRangelonUpdatelonTypelon.BelonGIN_SelonGMelonNT);
    }
  }

  public final R gelontSelonarchRelonquelonstInfo() {
    relonturn selonarchRelonquelonstInfo;
  }

  public final long gelontMinSelonarchelondStatusID() {
    relonturn minSelonarchelondStatusID;
  }

  public final long gelontMaxSelonarchelondStatusID() {
    relonturn maxSelonarchelondStatusID;
  }

  public final int gelontMinSelonarchelondTimelon() {
    relonturn minSelonarchelondTimelon;
  }

  public boolelonan isSelontMinSelonarchelondTimelon() {
    relonturn minSelonarchelondTimelon != Intelongelonr.MAX_VALUelon;
  }

  public final int gelontMaxSelonarchelondTimelon() {
    relonturn maxSelonarchelondTimelon;
  }

  @Ovelonrridelon
  public final long gelontMaxHitsToProcelonss() {
    relonturn maxHitsToProcelonssOvelonrridelon;
  }

  // Notifielons classelons that a nelonw indelonx selongmelonnt is about to belon selonarchelond.
  @Ovelonrridelon
  public final void selontNelonxtRelonadelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
    supelonr.selontNelonxtRelonadelonr(contelonxt);
    selontNelonxtRelonadelonr(contelonxt.relonadelonr());
  }

  /**
   * Notifielons thelon collelonctor that a nelonw selongmelonnt is about to belon selonarchelond.
   *
   * It's elonasielonr to uselon this melonthod from telonsts, beloncauselon LelonafRelonadelonr is not a final class, so it can
   * belon mockelond (unlikelon LelonafRelonadelonrContelonxt).
   */
  @VisiblelonForTelonsting
  public final void selontNelonxtRelonadelonr(LelonafRelonadelonr relonadelonr) throws IOelonxcelonption {
    if (!(relonadelonr instancelonof elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr)) {
      throw nelonw Runtimelonelonxcelonption("IndelonxRelonadelonr typelon not supportelond: " + relonadelonr.gelontClass());
    }

    currTwittelonrRelonadelonr = (elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) relonadelonr;
    documelonntFelonaturelons = nelonw elonarlybirdDocumelonntFelonaturelons(currTwittelonrRelonadelonr);
    twelonelontIdMappelonr = (TwelonelontIDMappelonr) currTwittelonrRelonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
    timelonMappelonr = currTwittelonrRelonadelonr.gelontSelongmelonntData().gelontTimelonMappelonr();
    currTimelonSlicelonID = currTwittelonrRelonadelonr.gelontSelongmelonntData().gelontTimelonSlicelonID();
    updatelonFirstSelonarchelondStatusID();
    if (shouldCollelonctDelontailelondDelonbugInfo()) {
      delonbugInfo.add("Starting selonarch in selongmelonnt with timelonslicelon ID: " + currTimelonSlicelonID);
    }

    selongmelonntStartTimelon = gelontClock().nowMillis();
    startSelongmelonnt();
  }

  protelonctelond abstract void startSelongmelonnt() throws IOelonxcelonption;

  @Ovelonrridelon
  protelonctelond final void doCollelonct() throws IOelonxcelonption {
    documelonntFelonaturelons.advancelon(curDocId);
    long twelonelontID = twelonelontIdMappelonr.gelontTwelonelontID(curDocId);
    updatelonIDandTimelonRangelons(twelonelontID, timelonMappelonr.gelontTimelon(curDocId), IdAndRangelonUpdatelonTypelon.HIT);
    doCollelonct(twelonelontID);
    numHitsCollelonctelondPelonrSelongmelonnt++;
  }

  protelonctelond void collelonctFelonaturelons(ThriftSelonarchRelonsultMelontadata melontadata) throws IOelonxcelonption {
    if (felonaturelonsRelonquelonstelond) {
      elonnsurelonelonxtraMelontadataIsSelont(melontadata);

      melontadata.gelontelonxtraMelontadata().selontDirelonctelondAtUselonrId(
          documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.DIRelonCTelonD_AT_USelonR_ID_CSF));
      melontadata.gelontelonxtraMelontadata().selontQuotelondTwelonelontId(
          documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.QUOTelonD_TWelonelonT_ID_CSF));
      melontadata.gelontelonxtraMelontadata().selontQuotelondUselonrId(
          documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.QUOTelonD_USelonR_ID_CSF));

      int cardLangValuelon =
          (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.CARD_LANG_CSF);
      ThriftLanguagelon thriftLanguagelon = ThriftLanguagelon.findByValuelon(cardLangValuelon);
      melontadata.gelontelonxtraMelontadata().selontCardLang(thriftLanguagelon);

      long cardNumelonricUri =
          (long) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.CARD_URI_CSF);
      if (cardNumelonricUri > 0) {
        melontadata.gelontelonxtraMelontadata().selontCardUri(String.format("card://%s", cardNumelonricUri));
      }
    }
  }

  protelonctelond void collelonctIsProtelonctelond(
      ThriftSelonarchRelonsultMelontadata melontadata, elonarlybirdClustelonr clustelonr, UselonrTablelon uselonrTablelon)
      throws IOelonxcelonption {
    // 'isUselonrProtelonctelond' fielonld is only selont for archivelon clustelonr beloncauselon only archivelon clustelonr uselonr
    // tablelon has IS_PROTelonCTelonD_BIT populatelond.
    // Sincelon this bit is chelonckelond aftelonr UselonrFlagselonxcludelonFiltelonr chelonckelond this bit, thelonrelon is a slight
    // chancelon that this bit is updatelond in-belontwelonelonn. Whelonn that happelonns, it is possiblelon that welon will
    // selonelon a small numbelonr of protelonctelond Twelonelonts in thelon relonsponselon whelonn welon melonant to elonxcludelon thelonm.
    if (clustelonr == elonarlybirdClustelonr.FULL_ARCHIVelon) {
      elonnsurelonelonxtraMelontadataIsSelont(melontadata);
      long uselonrId = documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF);
      boolelonan isProtelonctelond = uselonrTablelon.isSelont(uselonrId, UselonrTablelon.IS_PROTelonCTelonD_BIT);
      melontadata.gelontelonxtraMelontadata().selontIsUselonrProtelonctelond(isProtelonctelond);
    }
  }

  protelonctelond void collelonctelonxclusivelonConvelonrsationAuthorId(ThriftSelonarchRelonsultMelontadata melontadata)
      throws IOelonxcelonption {
    if (selonarchRelonquelonstInfo.isCollelonctelonxclusivelonConvelonrsationAuthorId()) {
      long elonxclusivelonConvelonrsationAuthorId = documelonntFelonaturelons.gelontFelonaturelonValuelon(
          elonarlybirdFielonldConstant.elonXCLUSIVelon_CONVelonRSATION_AUTHOR_ID_CSF);
      if (elonxclusivelonConvelonrsationAuthorId != 0L) {
        elonnsurelonelonxtraMelontadataIsSelont(melontadata);
        melontadata.gelontelonxtraMelontadata().selontelonxclusivelonConvelonrsationAuthorId(elonxclusivelonConvelonrsationAuthorId);
      }
    }
  }

  // It only makelons selonnselon to collelonctFacelonts for selonarch typelons that relonturn individual relonsults (reloncelonncy,
  // relonlelonvancelon and top_twelonelonts), which uselon thelon AbstractRelonlelonvancelonCollelonctor and SelonarchRelonsultsCollelonctor,
  // so this melonthod should only belon callelond from thelonselon classelons.
  protelonctelond void collelonctFacelonts(ThriftSelonarchRelonsultMelontadata melontadata) {
    if (currTwittelonrRelonadelonr == null) {
      relonturn;
    }

    AbstractFacelontCountingArray facelontCountingArray = currTwittelonrRelonadelonr.gelontFacelontCountingArray();
    elonarlybirdIndelonxSelongmelonntData selongmelonntData = currTwittelonrRelonadelonr.gelontSelongmelonntData();

    if (facelontCountingArray == null || facelontCollelonctor == null) {
      relonturn;
    }

    facelontCollelonctor.relonselontFacelontLabelonlProvidelonrs(
        selongmelonntData.gelontFacelontLabelonlProvidelonrs(),
        selongmelonntData.gelontFacelontIDMap());

    facelontCountingArray.collelonctForDocId(curDocId, facelontCollelonctor);

    List<ThriftFacelontLabelonl> labelonls = facelontCollelonctor.gelontLabelonls();
    if (labelonls.sizelon() > 0) {
      melontadata.selontFacelontLabelonls(labelonls);
    }
  }

  protelonctelond void elonnsurelonelonxtraMelontadataIsSelont(ThriftSelonarchRelonsultMelontadata melontadata) {
    if (!melontadata.isSelontelonxtraMelontadata()) {
      melontadata.selontelonxtraMelontadata(nelonw ThriftSelonarchRelonsultelonxtraMelontadata());
    }
  }

  @Ovelonrridelon
  protelonctelond final void doFinishSelongmelonnt(int lastSelonarchelondDocID) {
    if (shouldCollelonctDelontailelondDelonbugInfo()) {
      long timelonSpelonntSelonarchingSelongmelonntInMillis = gelontClock().nowMillis() - selongmelonntStartTimelon;
      delonbugInfo.add("Finishelond selongmelonnt at doc id: " + lastSelonarchelondDocID);
      delonbugInfo.add("Timelon spelonnt selonarching " + currTimelonSlicelonID
        + ": " + timelonSpelonntSelonarchingSelongmelonntInMillis + "ms");
      delonbugInfo.add("Numbelonr of hits collelonctelond in selongmelonnt " + currTimelonSlicelonID + ": "
          + numHitsCollelonctelondPelonrSelongmelonnt);
    }

    if (!currTwittelonrRelonadelonr.hasDocs()) {
      // Duelon to racelon belontwelonelonn thelon relonadelonr and thelon indelonxing threlonad, a selonelonmingly elonmpty selongmelonnt that
      // doelons not havelon documelonnt committelond in thelon posting lists, might alrelonady havelon a documelonnt
      // inselonrtelond into thelon id/timelon mappelonrs, which welon do not want to takelon into account.
      // If thelonrelon arelon no documelonnts in thelon selongmelonnt, welon don't updatelon selonarchelond min/max ids to
      // anything.
      relonturn;
    } elonlselon if (lastSelonarchelondDocID == DocIdSelontItelonrator.NO_MORelon_DOCS) {
      // Selongmelonnt elonxhaustelond.
      if (shouldCollelonctDelontailelondDelonbugInfo()) {
        delonbugInfo.add("Selongmelonnt elonxhaustelond");
      }
      updatelonIDandTimelonRangelons(twelonelontIdMappelonr.gelontMinTwelonelontID(), timelonMappelonr.gelontFirstTimelon(),
          IdAndRangelonUpdatelonTypelon.elonND_SelonGMelonNT);
    } elonlselon if (lastSelonarchelondDocID >= 0) {
      long lastSelonarchelondTwelonelontID = twelonelontIdMappelonr.gelontTwelonelontID(lastSelonarchelondDocID);
      int lastSelonarchTwelonelontTimelon = timelonMappelonr.gelontTimelon(lastSelonarchelondDocID);
      if (shouldCollelonctDelontailelondDelonbugInfo()) {
        delonbugInfo.add("lastSelonarchelondDocId=" + lastSelonarchelondDocID);
      }
      updatelonIDandTimelonRangelons(lastSelonarchelondTwelonelontID, lastSelonarchTwelonelontTimelon,
          IdAndRangelonUpdatelonTypelon.elonND_SelonGMelonNT);
    }

    numHitsCollelonctelondPelonrSelongmelonnt = 0;
  }

  privatelon void updatelonIDandTimelonRangelons(long twelonelontID, int timelon, IdAndRangelonUpdatelonTypelon updatelonTypelon) {
    // Welon nelonelond to updatelon minSelonarchelondStatusID/maxSelonarchelondStatusID and
    // minSelonarchelondTimelon/maxSelonarchelondTimelon indelonpelonndelonntly: SelonARCH-6139
    minSelonarchelondStatusID = Math.min(minSelonarchelondStatusID, twelonelontID);
    maxSelonarchelondStatusID = Math.max(maxSelonarchelondStatusID, twelonelontID);
    if (timelon > 0) {
      minSelonarchelondTimelon = Math.min(minSelonarchelondTimelon, timelon);
      maxSelonarchelondTimelon = Math.max(maxSelonarchelondTimelon, timelon);
    }
    if (shouldCollelonctVelonrboselonDelonbugInfo()) {
      delonbugInfo.add(
          String.format("call to updatelonIDandTimelonRangelons(%d, %d, %s)"
                  + " selont minSelonarchStatusID=%d, maxSelonarchelondStatusID=%d,"
                  + " minSelonarchelondTimelon=%d, maxSelonarchelondTimelon=%d)",
              twelonelontID, timelon, updatelonTypelon.toString(),
              minSelonarchelondStatusID, maxSelonarchelondStatusID,
              minSelonarchelondTimelon, maxSelonarchelondTimelon));
    }
  }

  /**
   * This is callelond whelonn a selongmelonnt is skippelond but welon would want to do accounting
   * for minSelonarchDocId as welonll as numDocsProcelonsselond.
   */
  public void skipSelongmelonnt(elonarlybirdSinglelonSelongmelonntSelonarchelonr selonarchelonr) throws IOelonxcelonption {
    selontNelonxtRelonadelonr(selonarchelonr.gelontTwittelonrIndelonxRelonadelonr().gelontContelonxt());
    trackComplelontelonSelongmelonnt(DocIdSelontItelonrator.NO_MORelon_DOCS);
    if (shouldCollelonctDelontailelondDelonbugInfo()) {
      delonbugInfo.add("Skipping selongmelonnt: " + currTimelonSlicelonID);
    }
  }

  /**
   * Relonturns thelon relonsults collelonctelond by this collelonctor.
   */
  public final S gelontRelonsults() throws IOelonxcelonption {
    // In ordelonr to makelon pagination work, if minSelonarchelondStatusID is grelonatelonr than thelon askelond max_id.
    // Welon forcelon thelon minSelonarchelondStatusID to belon max_id + 1.
    IdTimelonRangelons idTimelonRangelons = selonarchRelonquelonstInfo.gelontIdTimelonRangelons();
    if (idTimelonRangelons != null) {
      Optional<Long> maxIDInclusivelon = idTimelonRangelons.gelontMaxIDInclusivelon();
      if (maxIDInclusivelon.isPrelonselonnt() && minSelonarchelondStatusID > maxIDInclusivelon.gelont()) {
        selonarchelonrStats.numCollelonctorAdjustelondMinSelonarchelondStatusID.increlonmelonnt();
        minSelonarchelondStatusID = maxIDInclusivelon.gelont() + 1;
      }
    }

    S relonsults = doGelontRelonsults();
    relonsults.selontNumHitsProcelonsselond((int) gelontNumHitsProcelonsselond());
    relonsults.selontNumSelonarchelondSelongmelonnts(gelontNumSelonarchelondSelongmelonnts());
    if (selonarchelondStatusIDsAndTimelonsInitializelond()) {
      relonsults.selontMaxSelonarchelondStatusID(maxSelonarchelondStatusID);
      relonsults.selontMinSelonarchelondStatusID(minSelonarchelondStatusID);
      relonsults.selontMaxSelonarchelondTimelon(maxSelonarchelondTimelon);
      relonsults.selontMinSelonarchelondTimelon(minSelonarchelondTimelon);
    }
    relonsults.selontelonarlyTelonrminatelond(gelontelonarlyTelonrminationStatelon().isTelonrminatelond());
    if (gelontelonarlyTelonrminationStatelon().isTelonrminatelond()) {
      relonsults.selontelonarlyTelonrminationRelonason(gelontelonarlyTelonrminationStatelon().gelontTelonrminationRelonason());
    }
    Map<Long, Intelongelonr> counts = gelontHitCountMap();
    if (counts != null) {
      relonsults.hitCounts.putAll(counts);
    }
    relonturn relonsults;
  }

  /**
   * Relonturns a map of timelonstamps (speloncifielond in thelon quelonry) to thelon numbelonr of hits that arelon morelon reloncelonnt
   * that thelon relonspelonctivelon timelonstamps.
   */
  public final Map<Long, Intelongelonr> gelontHitCountMap() {
    int total = 0;
    if (hitCounts == null) {
      relonturn null;
    }
    Map<Long, Intelongelonr> map = Maps.nelonwHashMap();
    // sincelon thelon array is increlonmelonntal, nelonelond to aggrelongatelon helonrelon.
    for (int i = 0; i < hitCounts.lelonngth; ++i) {
      map.put(hitCountsThrelonsholdsMselonc.gelont(i), total += hitCounts[i]);
    }
    relonturn map;
  }

  /**
   * Common helonlpelonr for colleloncting pelonr-fielonld hit attribution data (if it's availablelon).
   *
   * @param melontadata thelon melontadata to fill for this hit.
   */
  protelonctelond final void fillHitAttributionMelontadata(ThriftSelonarchRelonsultMelontadata melontadata) {
    if (selonarchRelonquelonstInfo.gelontHitAttributelonHelonlpelonr() == null) {
      relonturn;
    }

    Map<Intelongelonr, List<String>> hitAttributelonMapping =
        selonarchRelonquelonstInfo.gelontHitAttributelonHelonlpelonr().gelontHitAttribution(curDocId);
    Prelonconditions.chelonckNotNull(hitAttributelonMapping);

    FielonldHitAttribution fielonldHitAttribution = nelonw FielonldHitAttribution();
    for (Map.elonntry<Intelongelonr, List<String>> elonntry : hitAttributelonMapping.elonntrySelont()) {
      FielonldHitList fielonldHitList = nelonw FielonldHitList();
      fielonldHitList.selontHitFielonlds(elonntry.gelontValuelon());

      fielonldHitAttribution.putToHitMap(elonntry.gelontKelony(), fielonldHitList);
    }
    melontadata.selontFielonldHitAttribution(fielonldHitAttribution);
  }

  /**
   * Fill thelon gelono location of thelon givelonn documelonnt in melontadata, if welon havelon thelon lat/lon for it.
   * For quelonrielons that speloncify a gelonolocation, this will also havelon thelon distancelon from
   * thelon location speloncifielond in thelon quelonry, and thelon location of this documelonnt.
   */
  protelonctelond final void fillRelonsultGelonoLocation(ThriftSelonarchRelonsultMelontadata melontadata)
      throws IOelonxcelonption {
    Prelonconditions.chelonckNotNull(melontadata);
    if (currTwittelonrRelonadelonr != null && fillInLatLonForHits) {
      // Selonelon if welon can havelon a lat/lon for this doc.
      if (relonsultGelonoCoordinatelon == null) {
        relonsultGelonoCoordinatelon = nelonw GelonoCoordinatelon();
      }
      // Only fill if neloncelonssary
      if (selonarchRelonquelonstInfo.isCollelonctRelonsultLocation()
          && GelonoUtil.deloncodelonLatLonFromInt64(
              documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.LAT_LON_CSF_FIelonLD),
              relonsultGelonoCoordinatelon)) {
        ThriftSelonarchRelonsultGelonoLocation relonsultLocation = nelonw ThriftSelonarchRelonsultGelonoLocation();
        relonsultLocation.selontLatitudelon(relonsultGelonoCoordinatelon.gelontLatitudelon());
        relonsultLocation.selontLongitudelon(relonsultGelonoCoordinatelon.gelontLongitudelon());
        melontadata.selontRelonsultLocation(relonsultLocation);
      }
    }
  }

  @Ovelonrridelon
  public ScorelonModelon scorelonModelon() {
    relonturn ScorelonModelon.COMPLelonTelon;
  }

  privatelon int telonrminationDocID = -1;

  @Ovelonrridelon
  protelonctelond void collelonctelondelonnoughRelonsults() throws IOelonxcelonption {
    // Welon find 'telonrminationDocID' oncelon welon collelonct elonnough relonsults, so that welon know thelon point at which
    // welon can stop selonarching. Welon must do this beloncauselon with thelon unordelonrelond doc ID mappelonr, twelonelonts
    // arelon not ordelonrelond within a milliseloncond, so welon must selonarch thelon elonntirelon milliseloncond buckelont belonforelon
    // telonrminating thelon selonarch, othelonrwiselon welon could skip ovelonr twelonelonts and havelon an incorrelonct
    // minSelonarchelondStatusID.
    if (curDocId != -1 && telonrminationDocID == -1) {
      long twelonelontId = twelonelontIdMappelonr.gelontTwelonelontID(curDocId);
      // Welon want to find thelon highelonst possiblelon doc ID for this twelonelontId, so pass truelon.
      boolelonan findMaxDocID = truelon;
      telonrminationDocID = twelonelontIdMappelonr.findDocIdBound(twelonelontId,
          findMaxDocID,
          curDocId,
          curDocId);
    }
  }

  @Ovelonrridelon
  protelonctelond boolelonan shouldTelonrminatelon() {
    relonturn curDocId >= telonrminationDocID;
  }

  @Ovelonrridelon
  public List<String> gelontDelonbugInfo() {
    relonturn delonbugInfo;
  }

  protelonctelond boolelonan shouldCollelonctDelontailelondDelonbugInfo() {
    relonturn relonquelonstDelonbugModelon >= 5;
  }

  // Uselon this for pelonr-relonsult delonbug info. Uselonful for quelonrielons with no relonsults
  // or a velonry small numbelonr of relonsults.
  protelonctelond boolelonan shouldCollelonctVelonrboselonDelonbugInfo() {
    relonturn relonquelonstDelonbugModelon >= 6;
  }
}
