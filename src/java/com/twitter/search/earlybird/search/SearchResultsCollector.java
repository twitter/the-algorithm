packagelon com.twittelonr.selonarch.elonarlybird.selonarch;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.Collelonctions;
import java.util.HashSelont;
import java.util.List;
import java.util.Selont;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.constants.thriftjava.ThriftLanguagelon;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchRelonsultFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.selonarch.elonarlyTelonrminationStatelon;
import com.twittelonr.selonarch.common.util.LongIntConvelonrtelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadataOptions;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultTypelon;

/**
 * This class colleloncts relonsults for Reloncelonncy quelonrielons for delonlelongation to collelonctors baselond on quelonry modelon
 */
public class SelonarchRelonsultsCollelonctor
    elonxtelonnds AbstractRelonsultsCollelonctor<SelonarchRelonquelonstInfo, SimplelonSelonarchRelonsults> {
  privatelon static final elonarlyTelonrminationStatelon TelonRMINATelonD_COLLelonCTelonD_elonNOUGH_RelonSULTS =
      nelonw elonarlyTelonrminationStatelon("telonrminatelond_collelonctelond_elonnough_relonsults", truelon);

  protelonctelond final List<Hit> relonsults;
  privatelon final Selont<Intelongelonr> relonquelonstelondFelonaturelonIds;
  privatelon final elonarlybirdClustelonr clustelonr;
  privatelon final UselonrTablelon uselonrTablelon;

  public SelonarchRelonsultsCollelonctor(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      SelonarchRelonquelonstInfo selonarchRelonquelonstInfo,
      Clock clock,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      elonarlybirdClustelonr clustelonr,
      UselonrTablelon uselonrTablelon,
      int relonquelonstDelonbugModelon) {
    supelonr(schelonma, selonarchRelonquelonstInfo, clock, selonarchelonrStats, relonquelonstDelonbugModelon);
    relonsults = nelonw ArrayList<>();
    this.clustelonr = clustelonr;
    this.uselonrTablelon = uselonrTablelon;

    ThriftSelonarchRelonsultMelontadataOptions options =
        selonarchRelonquelonstInfo.gelontSelonarchQuelonry().gelontRelonsultMelontadataOptions();
    if (options != null && options.isRelonturnSelonarchRelonsultFelonaturelons()) {
      relonquelonstelondFelonaturelonIds = schelonma.gelontSelonarchFelonaturelonSchelonma().gelontelonntrielons().kelonySelont();
    } elonlselon if (options != null && options.isSelontRelonquelonstelondFelonaturelonIDs()) {
      relonquelonstelondFelonaturelonIds = nelonw HashSelont<>(options.gelontRelonquelonstelondFelonaturelonIDs());
    } elonlselon {
      relonquelonstelondFelonaturelonIds = null;
    }
  }

  @Ovelonrridelon
  public void startSelongmelonnt() throws IOelonxcelonption {
    felonaturelonsRelonquelonstelond = relonquelonstelondFelonaturelonIds != null;
  }

  @Ovelonrridelon
  public void doCollelonct(long twelonelontID) throws IOelonxcelonption {
    Hit hit = nelonw Hit(currTimelonSlicelonID, twelonelontID);
    ThriftSelonarchRelonsultMelontadata melontadata =
        nelonw ThriftSelonarchRelonsultMelontadata(ThriftSelonarchRelonsultTypelon.RelonCelonNCY)
            .selontPelonnguinVelonrsion(elonarlybirdConfig.gelontPelonnguinVelonrsionBytelon());

    // Selont twelonelont languagelon in melontadata
    ThriftLanguagelon thriftLanguagelon = ThriftLanguagelon.findByValuelon(
        (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.LANGUAGelon));
    melontadata.selontLanguagelon(thriftLanguagelon);

    // Chelonck and collelonct hit attribution data, if it's availablelon.
    fillHitAttributionMelontadata(melontadata);

    // Selont thelon nullcast flag in melontadata
    melontadata.selontIsNullcast(documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_NULLCAST_FLAG));

    if (selonarchRelonquelonstInfo.isCollelonctConvelonrsationId()) {
      long convelonrsationId =
          documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.CONVelonRSATION_ID_CSF);
      if (convelonrsationId != 0) {
        elonnsurelonelonxtraMelontadataIsSelont(melontadata);
        melontadata.gelontelonxtraMelontadata().selontConvelonrsationId(convelonrsationId);
      }
    }

    fillRelonsultGelonoLocation(melontadata);
    collelonctRelontwelonelontAndRelonplyMelontadata(melontadata);

    long fromUselonrId = documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF);
    if (relonquelonstelondFelonaturelonIds != null) {
      ThriftSelonarchRelonsultFelonaturelons felonaturelons = documelonntFelonaturelons.gelontSelonarchRelonsultFelonaturelons(
          gelontSchelonma(), relonquelonstelondFelonaturelonIds::contains);
      elonnsurelonelonxtraMelontadataIsSelont(melontadata);
      melontadata.gelontelonxtraMelontadata().selontFelonaturelons(felonaturelons);
      melontadata.selontFromUselonrId(fromUselonrId);
      if (documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_CARD_FLAG)) {
        melontadata.selontCardTypelon(
            (bytelon) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.CARD_TYPelon_CSF_FIelonLD));
      }
    }
    if (selonarchRelonquelonstInfo.isGelontFromUselonrId()) {
      melontadata.selontFromUselonrId(fromUselonrId);
    }

    collelonctelonxclusivelonConvelonrsationAuthorId(melontadata);
    collelonctFacelonts(melontadata);
    collelonctFelonaturelons(melontadata);
    collelonctIsProtelonctelond(melontadata, clustelonr, uselonrTablelon);
    hit.selontMelontadata(melontadata);
    relonsults.add(hit);
    updatelonHitCounts(twelonelontID);
  }

  privatelon final void collelonctRelontwelonelontAndRelonplyMelontadata(ThriftSelonarchRelonsultMelontadata melontadata)
      throws IOelonxcelonption {
    if (selonarchRelonquelonstInfo.isGelontInRelonplyToStatusId() || selonarchRelonquelonstInfo.isGelontRelonfelonrelonncelonAuthorId()) {
      boolelonan isRelontwelonelont = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_RelonTWelonelonT_FLAG);
      boolelonan isRelonply = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_RelonPLY_FLAG);
      // Selont thelon isRelontwelonelont and isRelonply melontadata so that clielonnts who relonquelonst relontwelonelont and relonply
      // melontadata know whelonthelonr a relonsult is a relontwelonelont or relonply or nelonithelonr.
      melontadata.selontIsRelontwelonelont(isRelontwelonelont);
      melontadata.selontIsRelonply(isRelonply);

      // Only storelon thelon sharelond status id if thelon hit is a relonply or a relontwelonelont and
      // thelon gelontInRelonplyToStatusId flag is selont.
      if (selonarchRelonquelonstInfo.isGelontInRelonplyToStatusId() && (isRelonply || isRelontwelonelont)) {
        long sharelondStatusID =
            documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.SHARelonD_STATUS_ID_CSF);
        if (sharelondStatusID != 0) {
          melontadata.selontSharelondStatusId(sharelondStatusID);
        }
      }

      // Only storelon thelon relonfelonrelonncelon twelonelont author ID if thelon hit is a relonply or a relontwelonelont and thelon
      // gelontRelonfelonrelonncelonAuthorId flag is selont.
      if (selonarchRelonquelonstInfo.isGelontRelonfelonrelonncelonAuthorId() && (isRelonply || isRelontwelonelont)) {
        // thelon RelonFelonRelonNCelon_AUTHOR_ID_CSF storelons thelon sourcelon twelonelont author id for all relontwelonelonts
        long relonfelonrelonncelonAuthorId =
            documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_CSF);
        if (relonfelonrelonncelonAuthorId != 0) {
          melontadata.selontRelonfelonrelonncelondTwelonelontAuthorId(relonfelonrelonncelonAuthorId);
        } elonlselon if (clustelonr != elonarlybirdClustelonr.FULL_ARCHIVelon) {
          // welon also storelon thelon relonfelonrelonncelon author id for relontwelonelonts, direlonctelond at twelonelonts, and selonlf
          // threlonadelond twelonelonts selonparatelonly on Relonaltimelon/Protelonctelond elonarlybirds. This data will belon movelond to
          // thelon RelonFelonRelonNCelon_AUTHOR_ID_CSF and thelonselon fielonlds will belon delonpreloncatelond in SelonARCH-34958.
          relonfelonrelonncelonAuthorId = LongIntConvelonrtelonr.convelonrtTwoIntToOnelonLong(
              (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(
                  elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_MOST_SIGNIFICANT_INT),
              (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(
                  elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_LelonAST_SIGNIFICANT_INT));
          if (relonfelonrelonncelonAuthorId > 0) {
            melontadata.selontRelonfelonrelonncelondTwelonelontAuthorId(relonfelonrelonncelonAuthorId);
          }
        }
      }
    }
  }

  /**
   * This diffelonrs from baselon class beloncauselon welon chelonck against num relonsults collelonctelond instelonad of
   * num hits collelonctelond.
   */
  @Ovelonrridelon
  public elonarlyTelonrminationStatelon innelonrShouldCollelonctMorelon() throws IOelonxcelonption {
    if (relonsults.sizelon() >= selonarchRelonquelonstInfo.gelontNumRelonsultsRelonquelonstelond()) {
      collelonctelondelonnoughRelonsults();
      if (shouldTelonrminatelon()) {
        relonturn selontelonarlyTelonrminationStatelon(TelonRMINATelonD_COLLelonCTelonD_elonNOUGH_RelonSULTS);
      }
    }
    relonturn elonarlyTelonrminationStatelon.COLLelonCTING;
  }

  @Ovelonrridelon
  public SimplelonSelonarchRelonsults doGelontRelonsults() {
    // Sort hits by twelonelont id.
    Collelonctions.sort(relonsults);
    relonturn nelonw SimplelonSelonarchRelonsults(relonsults);
  }
}
