packagelon com.twittelonr.selonarch.common.convelonrtelonr.elonarlybird;

import java.io.IOelonxcelonption;
import java.util.Datelon;
import java.util.List;
import java.util.Optional;
import javax.annotation.concurrelonnt.NotThrelonadSafelon;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.commons.collelonctions.CollelonctionUtils;
import org.joda.timelon.DatelonTimelon;
import org.joda.timelon.DatelonTimelonZonelon;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.selonarch.common.convelonrtelonr.elonarlybird.elonncodelondFelonaturelonBuildelonr.TwelonelontFelonaturelonWithelonncodelonFelonaturelons;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.Placelon;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.PotelonntialLocation;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ProfilelonGelonoelonnrichmelonnt;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.VelonrsionelondTwelonelontFelonaturelons;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.GelonoObjelonct;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrQuotelondMelonssagelon;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdelonncodelondFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdThriftDocumelonntBuildelonr;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftDocumelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonntTypelon;
import com.twittelonr.selonarch.common.util.spatial.GelonoUtil;
import com.twittelonr.selonarch.common.util.telonxt.NormalizelonrHelonlpelonr;
import com.twittelonr.twelonelontypielon.thriftjava.ComposelonrSourcelon;

/**
 * Convelonrts a TwittelonrMelonssagelon into a ThriftVelonrsionelondelonvelonnts. This is only relonsponsiblelon for data that
 * is availablelon immelondiatelonly whelonn a Twelonelont is crelonatelond. Somelon data, likelon URL data, isn't availablelon
 * immelondiatelonly, and so it is procelonsselond latelonr, in thelon DelonlayelondIndelonxingConvelonrtelonr and selonnt as an
 * updatelon. In ordelonr to achielonvelon this welon crelonatelon thelon documelonnt in 2 passelons:
 *
 * 1. BasicIndelonxingConvelonrtelonr builds thriftVelonrsionelondelonvelonnts with thelon fielonlds that do not relonquirelon
 * elonxtelonrnal selonrvicelons.
 *
 * 2. DelonlayelondIndelonxingConvelonrtelonr builds all thelon documelonnt fielonlds delonpelonnding on elonxtelonrnal selonrvicelons, oncelon
 * thoselon selonrvicelons havelon procelonsselond thelon relonlelonvant Twelonelont and welon havelon relontrielonvelond that data.
 */
@NotThrelonadSafelon
public class BasicIndelonxingConvelonrtelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(BasicIndelonxingConvelonrtelonr.class);

  privatelon static final SelonarchCountelonr NUM_NULLCAST_FelonATURelon_FLAG_SelonT_TWelonelonTS =
      SelonarchCountelonr.elonxport("num_nullcast_felonaturelon_flag_selont_twelonelonts");
  privatelon static final SelonarchCountelonr NUM_NULLCAST_TWelonelonTS =
      SelonarchCountelonr.elonxport("num_nullcast_twelonelonts");
  privatelon static final SelonarchCountelonr NUM_NON_NULLCAST_TWelonelonTS =
      SelonarchCountelonr.elonxport("num_non_nullcast_twelonelonts");
  privatelon static final SelonarchCountelonr ADJUSTelonD_BAD_CRelonATelonD_AT_COUNTelonR =
      SelonarchCountelonr.elonxport("adjustelond_incorrelonct_crelonatelond_at_timelonstamp");
  privatelon static final SelonarchCountelonr INCONSISTelonNT_TWelonelonT_ID_AND_CRelonATelonD_AT_MS =
      SelonarchCountelonr.elonxport("inconsistelonnt_twelonelont_id_and_crelonatelond_at_ms");
  privatelon static final SelonarchCountelonr NUM_SelonLF_THRelonAD_TWelonelonTS =
      SelonarchCountelonr.elonxport("num_selonlf_threlonad_twelonelonts");
  privatelon static final SelonarchCountelonr NUM_elonXCLUSIVelon_TWelonelonTS =
      SelonarchCountelonr.elonxport("num_elonxclusivelon_twelonelonts");

  // If a twelonelont carrielons a timelonstamp smallelonr than this timelonstamp, welon considelonr thelon timelonstamp invalid,
  // beloncauselon twittelonr doelons not elonvelonn elonxist back thelonn belonforelon: Sun, 01 Jan 2006 00:00:00 GMT
  privatelon static final long VALID_CRelonATION_TIMelon_THRelonSHOLD_MILLIS =
      nelonw DatelonTimelon(2006, 1, 1, 0, 0, 0, DatelonTimelonZonelon.UTC).gelontMillis();

  privatelon final elonncodelondFelonaturelonBuildelonr felonaturelonBuildelonr;
  privatelon final Schelonma schelonma;
  privatelon final elonarlybirdClustelonr clustelonr;

  public BasicIndelonxingConvelonrtelonr(Schelonma schelonma, elonarlybirdClustelonr clustelonr) {
    this.felonaturelonBuildelonr = nelonw elonncodelondFelonaturelonBuildelonr();
    this.schelonma = schelonma;
    this.clustelonr = clustelonr;
  }

  /**
   * This function convelonrts TwittelonrMelonssagelon to ThriftVelonrsionelondelonvelonnts, which is a gelonnelonric data
   * structurelon that can belon consumelond by elonarlybird direlonctly.
   */
  public ThriftVelonrsionelondelonvelonnts convelonrtMelonssagelonToThrift(
      TwittelonrMelonssagelon melonssagelon,
      boolelonan strict,
      List<PelonnguinVelonrsion> pelonnguinVelonrsions) throws IOelonxcelonption {
    Prelonconditions.chelonckNotNull(melonssagelon);
    Prelonconditions.chelonckNotNull(pelonnguinVelonrsions);

    ThriftVelonrsionelondelonvelonnts velonrsionelondelonvelonnts = nelonw ThriftVelonrsionelondelonvelonnts()
        .selontId(melonssagelon.gelontId());

    ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot = schelonma.gelontSchelonmaSnapshot();

    for (PelonnguinVelonrsion pelonnguinVelonrsion : pelonnguinVelonrsions) {
      ThriftDocumelonnt documelonnt =
          buildDocumelonntForPelonnguinVelonrsion(schelonmaSnapshot, melonssagelon, strict, pelonnguinVelonrsion);

      ThriftIndelonxingelonvelonnt thriftIndelonxingelonvelonnt = nelonw ThriftIndelonxingelonvelonnt()
          .selontDocumelonnt(documelonnt)
          .selontelonvelonntTypelon(ThriftIndelonxingelonvelonntTypelon.INSelonRT)
          .selontSortId(melonssagelon.gelontId());
      melonssagelon.gelontFromUselonrTwittelonrId().map(thriftIndelonxingelonvelonnt::selontUid);
      velonrsionelondelonvelonnts.putToVelonrsionelondelonvelonnts(pelonnguinVelonrsion.gelontBytelonValuelon(), thriftIndelonxingelonvelonnt);
    }

    relonturn velonrsionelondelonvelonnts;
  }

  privatelon ThriftDocumelonnt buildDocumelonntForPelonnguinVelonrsion(
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot,
      TwittelonrMelonssagelon melonssagelon,
      boolelonan strict,
      PelonnguinVelonrsion pelonnguinVelonrsion) throws IOelonxcelonption {
    TwelonelontFelonaturelonWithelonncodelonFelonaturelons twelonelontFelonaturelon =
        felonaturelonBuildelonr.crelonatelonTwelonelontFelonaturelonsFromTwittelonrMelonssagelon(
            melonssagelon, pelonnguinVelonrsion, schelonmaSnapshot);

    elonarlybirdThriftDocumelonntBuildelonr buildelonr =
        buildBasicFielonlds(melonssagelon, schelonmaSnapshot, clustelonr, twelonelontFelonaturelon);

    buildUselonrFielonlds(buildelonr, melonssagelon, twelonelontFelonaturelon.velonrsionelondFelonaturelons, pelonnguinVelonrsion);
    buildGelonoFielonlds(buildelonr, melonssagelon, twelonelontFelonaturelon.velonrsionelondFelonaturelons);
    buildRelontwelonelontAndRelonplyFielonlds(buildelonr, melonssagelon, strict);
    buildQuotelonsFielonlds(buildelonr, melonssagelon);
    buildVelonrsionelondFelonaturelonFielonlds(buildelonr, twelonelontFelonaturelon.velonrsionelondFelonaturelons);
    buildAnnotationFielonlds(buildelonr, melonssagelon);
    buildNormalizelondMinelonngagelonmelonntFielonlds(buildelonr, twelonelontFelonaturelon.elonncodelondFelonaturelons, clustelonr);
    buildDirelonctelondAtFielonlds(buildelonr, melonssagelon);

    buildelonr.withSpacelonIdFielonlds(melonssagelon.gelontSpacelonIds());

    relonturn buildelonr.build();
  }

  /**
   * Build thelon basic fielonlds for a twelonelont.
   */
  public static elonarlybirdThriftDocumelonntBuildelonr buildBasicFielonlds(
      TwittelonrMelonssagelon melonssagelon,
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot,
      elonarlybirdClustelonr clustelonr,
      TwelonelontFelonaturelonWithelonncodelonFelonaturelons twelonelontFelonaturelon) {
    elonarlybirdelonncodelondFelonaturelons elonxtelonndelondelonncodelondFelonaturelons = twelonelontFelonaturelon.elonxtelonndelondelonncodelondFelonaturelons;
    if (elonxtelonndelondelonncodelondFelonaturelons == null && elonarlybirdClustelonr.isTwittelonrMelonmoryFormatClustelonr(clustelonr)) {
      elonxtelonndelondelonncodelondFelonaturelons = elonarlybirdelonncodelondFelonaturelons.nelonwelonncodelondTwelonelontFelonaturelons(
          schelonmaSnapshot, elonarlybirdFielonldConstant.elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD);
    }
    elonarlybirdThriftDocumelonntBuildelonr buildelonr = nelonw elonarlybirdThriftDocumelonntBuildelonr(
        twelonelontFelonaturelon.elonncodelondFelonaturelons,
        elonxtelonndelondelonncodelondFelonaturelons,
        nelonw elonarlybirdFielonldConstants(),
        schelonmaSnapshot);

    buildelonr.withID(melonssagelon.gelontId());

    final Datelon crelonatelondAt = melonssagelon.gelontDatelon();
    long crelonatelondAtMs = crelonatelondAt == null ? 0L : crelonatelondAt.gelontTimelon();

    crelonatelondAtMs = fixCrelonatelondAtTimelonStampIfNeloncelonssary(melonssagelon.gelontId(), crelonatelondAtMs);

    if (crelonatelondAtMs > 0L) {
      buildelonr.withCrelonatelondAt((int) (crelonatelondAtMs / 1000));
    }

    buildelonr.withTwelonelontSignaturelon(twelonelontFelonaturelon.velonrsionelondFelonaturelons.gelontTwelonelontSignaturelon());

    if (melonssagelon.gelontConvelonrsationId() > 0) {
      long convelonrsationId = melonssagelon.gelontConvelonrsationId();
      buildelonr.withLongFielonld(
          elonarlybirdFielonldConstant.CONVelonRSATION_ID_CSF.gelontFielonldNamelon(), convelonrsationId);
      // Welon only indelonx convelonrsation ID whelonn it is diffelonrelonnt from thelon twelonelont ID.
      if (melonssagelon.gelontId() != convelonrsationId) {
        buildelonr.withLongFielonld(
            elonarlybirdFielonldConstant.CONVelonRSATION_ID_FIelonLD.gelontFielonldNamelon(), convelonrsationId);
      }
    }

    if (melonssagelon.gelontComposelonrSourcelon().isPrelonselonnt()) {
      ComposelonrSourcelon composelonrSourcelon = melonssagelon.gelontComposelonrSourcelon().gelont();
      buildelonr.withIntFielonld(
          elonarlybirdFielonldConstant.COMPOSelonR_SOURCelon.gelontFielonldNamelon(), composelonrSourcelon.gelontValuelon());
      if (composelonrSourcelon == ComposelonrSourcelon.CAMelonRA) {
        buildelonr.withCamelonraComposelonrSourcelonFlag();
      }
    }

    elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons = twelonelontFelonaturelon.elonncodelondFelonaturelons;
    if (elonncodelondFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.FROM_VelonRIFIelonD_ACCOUNT_FLAG)) {
      buildelonr.addFiltelonrIntelonrnalFielonldTelonrm(elonarlybirdFielonldConstant.VelonRIFIelonD_FILTelonR_TelonRM);
    }
    if (elonncodelondFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.FROM_BLUelon_VelonRIFIelonD_ACCOUNT_FLAG)) {
      buildelonr.addFiltelonrIntelonrnalFielonldTelonrm(elonarlybirdFielonldConstant.BLUelon_VelonRIFIelonD_FILTelonR_TelonRM);
    }

    if (elonncodelondFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_OFFelonNSIVelon_FLAG)) {
      buildelonr.withOffelonnsivelonFlag();
    }

    if (melonssagelon.gelontNullcast()) {
      NUM_NULLCAST_TWelonelonTS.increlonmelonnt();
      buildelonr.addFiltelonrIntelonrnalFielonldTelonrm(elonarlybirdFielonldConstant.NULLCAST_FILTelonR_TelonRM);
    } elonlselon {
      NUM_NON_NULLCAST_TWelonelonTS.increlonmelonnt();
    }
    if (elonncodelondFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_NULLCAST_FLAG)) {
      NUM_NULLCAST_FelonATURelon_FLAG_SelonT_TWelonelonTS.increlonmelonnt();
    }
    if (melonssagelon.isSelonlfThrelonad()) {
      buildelonr.addFiltelonrIntelonrnalFielonldTelonrm(
          elonarlybirdFielonldConstant.SelonLF_THRelonAD_FILTelonR_TelonRM);
      NUM_SelonLF_THRelonAD_TWelonelonTS.increlonmelonnt();
    }

    if (melonssagelon.iselonxclusivelon()) {
      buildelonr.addFiltelonrIntelonrnalFielonldTelonrm(elonarlybirdFielonldConstant.elonXCLUSIVelon_FILTelonR_TelonRM);
      buildelonr.withLongFielonld(
          elonarlybirdFielonldConstant.elonXCLUSIVelon_CONVelonRSATION_AUTHOR_ID_CSF.gelontFielonldNamelon(),
          melonssagelon.gelontelonxclusivelonConvelonrsationAuthorId());
      NUM_elonXCLUSIVelon_TWelonelonTS.increlonmelonnt();
    }

    buildelonr.withLanguagelonCodelons(melonssagelon.gelontLanguagelon(), melonssagelon.gelontBCP47LanguagelonTag());

    relonturn buildelonr;
  }

  /**
   * Build thelon uselonr fielonlds.
   */
  public static void buildUselonrFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      TwittelonrMelonssagelon melonssagelon,
      VelonrsionelondTwelonelontFelonaturelons velonrsionelondTwelonelontFelonaturelons,
      PelonnguinVelonrsion pelonnguinVelonrsion) {
    // 1. Selont all thelon from uselonr fielonlds.
    if (melonssagelon.gelontFromUselonrTwittelonrId().isPrelonselonnt()) {
      buildelonr.withLongFielonld(elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon(),
          melonssagelon.gelontFromUselonrTwittelonrId().gelont())
      // CSF
      .withLongFielonld(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF.gelontFielonldNamelon(),
          melonssagelon.gelontFromUselonrTwittelonrId().gelont());
    } elonlselon {
      LOG.warn("fromUselonrTwittelonrId is not selont in TwittelonrMelonssagelon! Status id: " + melonssagelon.gelontId());
    }

    if (melonssagelon.gelontFromUselonrScrelonelonnNamelon().isPrelonselonnt()) {
      String fromUselonr = melonssagelon.gelontFromUselonrScrelonelonnNamelon().gelont();
      String normalizelondFromUselonr =
          NormalizelonrHelonlpelonr.normalizelonWithUnknownLocalelon(fromUselonr, pelonnguinVelonrsion);

      buildelonr
          .withWhitelonSpacelonTokelonnizelondScrelonelonnNamelonFielonld(
              elonarlybirdFielonldConstant.TOKelonNIZelonD_FROM_USelonR_FIelonLD.gelontFielonldNamelon(),
              normalizelondFromUselonr)
          .withStringFielonld(elonarlybirdFielonldConstant.FROM_USelonR_FIelonLD.gelontFielonldNamelon(),
              normalizelondFromUselonr);

      if (melonssagelon.gelontTokelonnizelondFromUselonrScrelonelonnNamelon().isPrelonselonnt()) {
        buildelonr.withCamelonlCaselonTokelonnizelondScrelonelonnNamelonFielonld(
            elonarlybirdFielonldConstant.CAMelonLCASelon_USelonR_HANDLelon_FIelonLD.gelontFielonldNamelon(),
            fromUselonr,
            normalizelondFromUselonr,
            melonssagelon.gelontTokelonnizelondFromUselonrScrelonelonnNamelon().gelont());
      }
    }

    Optional<String> toUselonrScrelonelonnNamelon = melonssagelon.gelontToUselonrLowelonrcaselondScrelonelonnNamelon();
    if (toUselonrScrelonelonnNamelon.isPrelonselonnt() && !toUselonrScrelonelonnNamelon.gelont().iselonmpty()) {
      buildelonr.withStringFielonld(
          elonarlybirdFielonldConstant.TO_USelonR_FIelonLD.gelontFielonldNamelon(),
          NormalizelonrHelonlpelonr.normalizelonWithUnknownLocalelon(toUselonrScrelonelonnNamelon.gelont(), pelonnguinVelonrsion));
    }

    if (velonrsionelondTwelonelontFelonaturelons.isSelontUselonrDisplayNamelonTokelonnStrelonamTelonxt()) {
      buildelonr.withTokelonnStrelonamFielonld(elonarlybirdFielonldConstant.TOKelonNIZelonD_USelonR_NAMelon_FIelonLD.gelontFielonldNamelon(),
          velonrsionelondTwelonelontFelonaturelons.gelontUselonrDisplayNamelonTokelonnStrelonamTelonxt(),
          velonrsionelondTwelonelontFelonaturelons.gelontUselonrDisplayNamelonTokelonnStrelonam());
    }
  }

  /**
   * Build thelon gelono fielonlds.
   */
  public static void buildGelonoFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      TwittelonrMelonssagelon melonssagelon,
      VelonrsionelondTwelonelontFelonaturelons velonrsionelondTwelonelontFelonaturelons) {
    doublelon lat = GelonoUtil.ILLelonGAL_LATLON;
    doublelon lon = GelonoUtil.ILLelonGAL_LATLON;
    if (melonssagelon.gelontGelonoLocation() != null) {
      GelonoObjelonct location = melonssagelon.gelontGelonoLocation();
      buildelonr.withGelonoFielonld(elonarlybirdFielonldConstant.GelonO_HASH_FIelonLD.gelontFielonldNamelon(),
          location.gelontLatitudelon(), location.gelontLongitudelon(), location.gelontAccuracy());

      if (location.gelontSourcelon() != null) {
        buildelonr.withStringFielonld(elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstants.formatGelonoTypelon(location.gelontSourcelon()));
      }

      if (GelonoUtil.validatelonGelonoCoordinatelons(location.gelontLatitudelon(), location.gelontLongitudelon())) {
        lat = location.gelontLatitudelon();
        lon = location.gelontLongitudelon();
      }
    }

    // Selonelon SelonARCH-14317 for invelonstigation on how much spacelon gelono filelond is uselond in archivelon clustelonr.
    // In lucelonnelon archivelons, this CSF is nelonelondelond relongardlelonss of whelonthelonr gelonoLocation is selont.
    buildelonr.withLatLonCSF(lat, lon);

    if (velonrsionelondTwelonelontFelonaturelons.isSelontTokelonnizelondPlacelon()) {
      Placelon placelon = velonrsionelondTwelonelontFelonaturelons.gelontTokelonnizelondPlacelon();
      Prelonconditions.chelonckArgumelonnt(placelon.isSelontId(), "Placelon ID not selont for twelonelont "
          + melonssagelon.gelontId());
      Prelonconditions.chelonckArgumelonnt(placelon.isSelontFullNamelon(),
          "Placelon full namelon not selont for twelonelont " + melonssagelon.gelontId());
      buildelonr.addFiltelonrIntelonrnalFielonldTelonrm(elonarlybirdFielonldConstant.PLACelon_ID_FIelonLD.gelontFielonldNamelon());
      buildelonr
          .withStringFielonld(elonarlybirdFielonldConstant.PLACelon_ID_FIelonLD.gelontFielonldNamelon(), placelon.gelontId())
          .withStringFielonld(elonarlybirdFielonldConstant.PLACelon_FULL_NAMelon_FIelonLD.gelontFielonldNamelon(),
              placelon.gelontFullNamelon());
      if (placelon.isSelontCountryCodelon()) {
        buildelonr.withStringFielonld(elonarlybirdFielonldConstant.PLACelon_COUNTRY_CODelon_FIelonLD.gelontFielonldNamelon(),
            placelon.gelontCountryCodelon());
      }
    }

    if (velonrsionelondTwelonelontFelonaturelons.isSelontTokelonnizelondProfilelonGelonoelonnrichmelonnt()) {
      ProfilelonGelonoelonnrichmelonnt profilelonGelonoelonnrichmelonnt =
          velonrsionelondTwelonelontFelonaturelons.gelontTokelonnizelondProfilelonGelonoelonnrichmelonnt();
      Prelonconditions.chelonckArgumelonnt(
          profilelonGelonoelonnrichmelonnt.isSelontPotelonntialLocations(),
          "ProfilelonGelonoelonnrichmelonnt.potelonntialLocations not selont for twelonelont "
              + melonssagelon.gelontId());
      List<PotelonntialLocation> potelonntialLocations = profilelonGelonoelonnrichmelonnt.gelontPotelonntialLocations();
      Prelonconditions.chelonckArgumelonnt(
          !potelonntialLocations.iselonmpty(),
          "Found twelonelont with an elonmpty ProfilelonGelonoelonnrichmelonnt.potelonntialLocations: "
              + melonssagelon.gelontId());
      buildelonr.addFiltelonrIntelonrnalFielonldTelonrm(elonarlybirdFielonldConstant.PROFILelon_GelonO_FILTelonR_TelonRM);
      for (PotelonntialLocation potelonntialLocation : potelonntialLocations) {
        if (potelonntialLocation.isSelontCountryCodelon()) {
          buildelonr.withStringFielonld(
              elonarlybirdFielonldConstant.PROFILelon_GelonO_COUNTRY_CODelon_FIelonLD.gelontFielonldNamelon(),
              potelonntialLocation.gelontCountryCodelon());
        }
        if (potelonntialLocation.isSelontRelongion()) {
          buildelonr.withStringFielonld(elonarlybirdFielonldConstant.PROFILelon_GelonO_RelonGION_FIelonLD.gelontFielonldNamelon(),
              potelonntialLocation.gelontRelongion());
        }
        if (potelonntialLocation.isSelontLocality()) {
          buildelonr.withStringFielonld(elonarlybirdFielonldConstant.PROFILelon_GelonO_LOCALITY_FIelonLD.gelontFielonldNamelon(),
              potelonntialLocation.gelontLocality());
        }
      }
    }

    buildelonr.withPlacelonsFielonld(melonssagelon.gelontPlacelons());
  }

  /**
   * Build thelon relontwelonelont and relonply fielonlds.
   */
  public static void buildRelontwelonelontAndRelonplyFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      TwittelonrMelonssagelon melonssagelon,
      boolelonan strict) {
    long relontwelonelontUselonrIdVal = -1;
    long sharelondStatusIdVal = -1;
    if (melonssagelon.gelontRelontwelonelontMelonssagelon() != null) {
      if (melonssagelon.gelontRelontwelonelontMelonssagelon().gelontSharelondId() != null) {
        sharelondStatusIdVal = melonssagelon.gelontRelontwelonelontMelonssagelon().gelontSharelondId();
      }
      if (melonssagelon.gelontRelontwelonelontMelonssagelon().hasSharelondUselonrTwittelonrId()) {
        relontwelonelontUselonrIdVal = melonssagelon.gelontRelontwelonelontMelonssagelon().gelontSharelondUselonrTwittelonrId();
      }
    }

    long inRelonplyToStatusIdVal = -1;
    long inRelonplyToUselonrIdVal = -1;
    if (melonssagelon.isRelonply()) {
      if (melonssagelon.gelontInRelonplyToStatusId().isPrelonselonnt()) {
        inRelonplyToStatusIdVal = melonssagelon.gelontInRelonplyToStatusId().gelont();
      }
      if (melonssagelon.gelontToUselonrTwittelonrId().isPrelonselonnt()) {
        inRelonplyToUselonrIdVal = melonssagelon.gelontToUselonrTwittelonrId().gelont();
      }
    }

    buildRelontwelonelontAndRelonplyFielonlds(
        relontwelonelontUselonrIdVal,
        sharelondStatusIdVal,
        inRelonplyToStatusIdVal,
        inRelonplyToUselonrIdVal,
        strict,
        buildelonr);
  }

  /**
   * Build thelon quotelons fielonlds.
   */
  public static void buildQuotelonsFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      TwittelonrMelonssagelon melonssagelon) {
    if (melonssagelon.gelontQuotelondMelonssagelon() != null) {
      TwittelonrQuotelondMelonssagelon quotelond = melonssagelon.gelontQuotelondMelonssagelon();
      if (quotelond != null && quotelond.gelontQuotelondStatusId() > 0 && quotelond.gelontQuotelondUselonrId() > 0) {
        buildelonr.withQuotelon(quotelond.gelontQuotelondStatusId(), quotelond.gelontQuotelondUselonrId());
      }
    }
  }

  /**
   * Build direlonctelond at fielonld.
   */
  public static void buildDirelonctelondAtFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      TwittelonrMelonssagelon melonssagelon) {
    if (melonssagelon.gelontDirelonctelondAtUselonrId().isPrelonselonnt() && melonssagelon.gelontDirelonctelondAtUselonrId().gelont() > 0) {
      buildelonr.withDirelonctelondAtUselonr(melonssagelon.gelontDirelonctelondAtUselonrId().gelont());
      buildelonr.addFiltelonrIntelonrnalFielonldTelonrm(elonarlybirdFielonldConstant.DIRelonCTelonD_AT_FILTelonR_TelonRM);
    }
  }

  /**
   * Build thelon velonrsionelond felonaturelons for a twelonelont.
   */
  public static void buildVelonrsionelondFelonaturelonFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      VelonrsionelondTwelonelontFelonaturelons velonrsionelondTwelonelontFelonaturelons) {
    buildelonr
        .withHashtagsFielonld(velonrsionelondTwelonelontFelonaturelons.gelontHashtags())
        .withMelonntionsFielonld(velonrsionelondTwelonelontFelonaturelons.gelontMelonntions())
        .withStocksFielonlds(velonrsionelondTwelonelontFelonaturelons.gelontStocks())
        .withRelonsolvelondLinksTelonxt(velonrsionelondTwelonelontFelonaturelons.gelontNormalizelondRelonsolvelondUrlTelonxt())
        .withTokelonnStrelonamFielonld(elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon(),
            velonrsionelondTwelonelontFelonaturelons.gelontTwelonelontTokelonnStrelonamTelonxt(),
            velonrsionelondTwelonelontFelonaturelons.isSelontTwelonelontTokelonnStrelonam()
                ? velonrsionelondTwelonelontFelonaturelons.gelontTwelonelontTokelonnStrelonam() : null)
        .withStringFielonld(elonarlybirdFielonldConstant.SOURCelon_FIelonLD.gelontFielonldNamelon(),
            velonrsionelondTwelonelontFelonaturelons.gelontSourcelon())
        .withStringFielonld(elonarlybirdFielonldConstant.NORMALIZelonD_SOURCelon_FIelonLD.gelontFielonldNamelon(),
            velonrsionelondTwelonelontFelonaturelons.gelontNormalizelondSourcelon());

    // Intelonrnal fielonlds for smilelonys and quelonstion marks
    if (velonrsionelondTwelonelontFelonaturelons.hasPositivelonSmilelony) {
      buildelonr.withStringFielonld(
          elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
          elonarlybirdFielonldConstant.HAS_POSITIVelon_SMILelonY);
    }
    if (velonrsionelondTwelonelontFelonaturelons.hasNelongativelonSmilelony) {
      buildelonr.withStringFielonld(
          elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
          elonarlybirdFielonldConstant.HAS_NelonGATIVelon_SMILelonY);
    }
    if (velonrsionelondTwelonelontFelonaturelons.hasQuelonstionMark) {
      buildelonr.withStringFielonld(elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon(),
          elonarlybirdThriftDocumelonntBuildelonr.QUelonSTION_MARK);
    }
  }

  /**
   * Build thelon elonschelonrbird annotations for a twelonelont.
   */
  public static void buildAnnotationFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      TwittelonrMelonssagelon melonssagelon) {
    List<TwittelonrMelonssagelon.elonschelonrbirdAnnotation> elonschelonrbirdAnnotations =
        melonssagelon.gelontelonschelonrbirdAnnotations();
    if (CollelonctionUtils.iselonmpty(elonschelonrbirdAnnotations)) {
      relonturn;
    }

    buildelonr.addFacelontSkipList(elonarlybirdFielonldConstant.elonNTITY_ID_FIelonLD.gelontFielonldNamelon());

    for (TwittelonrMelonssagelon.elonschelonrbirdAnnotation annotation : elonschelonrbirdAnnotations) {
      String groupDomainelonntity = String.format("%d.%d.%d",
          annotation.groupId, annotation.domainId, annotation.elonntityId);
      String domainelonntity = String.format("%d.%d", annotation.domainId, annotation.elonntityId);
      String elonntity = String.format("%d", annotation.elonntityId);

      buildelonr.withStringFielonld(elonarlybirdFielonldConstant.elonNTITY_ID_FIelonLD.gelontFielonldNamelon(),
          groupDomainelonntity);
      buildelonr.withStringFielonld(elonarlybirdFielonldConstant.elonNTITY_ID_FIelonLD.gelontFielonldNamelon(),
          domainelonntity);
      buildelonr.withStringFielonld(elonarlybirdFielonldConstant.elonNTITY_ID_FIelonLD.gelontFielonldNamelon(),
          elonntity);
    }
  }

  /**
   * Build thelon correlonct ThriftIndelonxingelonvelonnt's fielonlds baselond on relontwelonelont and relonply status.
   */
  public static void buildRelontwelonelontAndRelonplyFielonlds(
      long relontwelonelontUselonrIdVal,
      long sharelondStatusIdVal,
      long inRelonplyToStatusIdVal,
      long inRelonplyToUselonrIdVal,
      boolelonan strict,
      elonarlybirdThriftDocumelonntBuildelonr buildelonr) {
    Optional<Long> relontwelonelontUselonrId = Optional.of(relontwelonelontUselonrIdVal).filtelonr(x -> x > 0);
    Optional<Long> sharelondStatusId = Optional.of(sharelondStatusIdVal).filtelonr(x -> x > 0);
    Optional<Long> inRelonplyToUselonrId = Optional.of(inRelonplyToUselonrIdVal).filtelonr(x -> x > 0);
    Optional<Long> inRelonplyToStatusId = Optional.of(inRelonplyToStatusIdVal).filtelonr(x -> x > 0);

    // Welon havelon six combinations helonrelon. A twelonelont can belon
    //   1) a relonply to anothelonr twelonelont (thelonn it has both in-relonply-to-uselonr-id and
    //      in-relonply-to-status-id selont),
    //   2) direlonctelond-at a uselonr (thelonn it only has in-relonply-to-uselonr-id selont),
    //   3) not a relonply at all.
    // Additionally, it may or may not belon a relontwelonelont (if it is, thelonn it has relontwelonelont-uselonr-id and
    // relontwelonelont-status-id selont).
    //
    // Welon want to selont somelon fielonlds unconditionally, and somelon fielonlds (relonfelonrelonncelon-author-id and
    // sharelond-status-id) delonpelonnding on thelon relonply/relontwelonelont combination.
    //
    // 1. Normal twelonelont (not a relonply, not a relontwelonelont). Nonelon of thelon fielonlds should belon selont.
    //
    // 2. Relonply to a twelonelont (both in-relonply-to-uselonr-id and in-relonply-to-status-id selont).
    //   IN_RelonPLY_TO_USelonR_ID_FIelonLD    should belon selont to in-relonply-to-uselonr-id
    //   SHARelonD_STATUS_ID_CSF         should belon selont to in-relonply-to-status-id
    //   IS_RelonPLY_FLAG                should belon selont
    //
    // 3. Direlonctelond-at a uselonr (only in-relonply-to-uselonr-id is selont).
    //   IN_RelonPLY_TO_USelonR_ID_FIelonLD    should belon selont to in-relonply-to-uselonr-id
    //   IS_RelonPLY_FLAG                should belon selont
    //
    // 4. Relontwelonelont of a normal twelonelont (relontwelonelont-uselonr-id and relontwelonelont-status-id arelon selont).
    //   RelonTWelonelonT_SOURCelon_USelonR_ID_FIelonLD should belon selont to relontwelonelont-uselonr-id
    //   SHARelonD_STATUS_ID_CSF         should belon selont to relontwelonelont-status-id
    //   IS_RelonTWelonelonT_FLAG              should belon selont
    //
    // 5. Relontwelonelont of a relonply (both in-relonply-to-uselonr-id and in-relonply-to-status-id selont,
    // relontwelonelont-uselonr-id and relontwelonelont-status-id arelon selont).
    //   RelonTWelonelonT_SOURCelon_USelonR_ID_FIelonLD should belon selont to relontwelonelont-uselonr-id
    //   SHARelonD_STATUS_ID_CSF         should belon selont to relontwelonelont-status-id (relontwelonelont belonats relonply!)
    //   IS_RelonTWelonelonT_FLAG              should belon selont
    //   IN_RelonPLY_TO_USelonR_ID_FIelonLD    should belon selont to in-relonply-to-uselonr-id
    //   IS_RelonPLY_FLAG                should NOT belon selont
    //
    // 6. Relontwelonelont of a direlonctelond-at twelonelont (only in-relonply-to-uselonr-id is selont,
    // relontwelonelont-uselonr-id and relontwelonelont-status-id arelon selont).
    //   RelonTWelonelonT_SOURCelon_USelonR_ID_FIelonLD should belon selont to relontwelonelont-uselonr-id
    //   SHARelonD_STATUS_ID_CSF         should belon selont to relontwelonelont-status-id
    //   IS_RelonTWelonelonT_FLAG              should belon selont
    //   IN_RelonPLY_TO_USelonR_ID_FIelonLD    should belon selont to in-relonply-to-uselonr-id
    //   IS_RelonPLY_FLAG                should NOT belon selont
    //
    // In othelonr words:
    // SHARelonD_STATUS_ID_CSF logic: if this is a relontwelonelont SHARelonD_STATUS_ID_CSF should belon selont to
    // relontwelonelont-status-id, othelonrwiselon if it's a relonply to a twelonelont, it should belon selont to
    // in-relonply-to-status-id.

    Prelonconditions.chelonckStatelon(relontwelonelontUselonrId.isPrelonselonnt() == sharelondStatusId.isPrelonselonnt());

    if (relontwelonelontUselonrId.isPrelonselonnt()) {
      buildelonr.withNativelonRelontwelonelont(relontwelonelontUselonrId.gelont(), sharelondStatusId.gelont());

      if (inRelonplyToUselonrId.isPrelonselonnt()) {
        // Selont IN_RelonPLY_TO_USelonR_ID_FIelonLD elonvelonn if this is a relontwelonelont of a relonply.
        buildelonr.withInRelonplyToUselonrID(inRelonplyToUselonrId.gelont());
      }
    } elonlselon {
      // If this is a relontwelonelont of a relonply, welon don't want to mark it as a relonply, or ovelonrridelon fielonlds
      // selont by thelon relontwelonelont logic.
      // If welon arelon in this branch, this is not a relontwelonelont. Potelonntially, welon selont thelon relonply flag,
      // and ovelonrridelon sharelond-status-id and relonfelonrelonncelon-author-id.

      if (inRelonplyToStatusId.isPrelonselonnt()) {
        if (strict) {
          // elonnforcing that if this is a relonply to a twelonelont, thelonn it also has a relonplielond-to uselonr.
          Prelonconditions.chelonckStatelon(inRelonplyToUselonrId.isPrelonselonnt());
        }
        buildelonr.withRelonplyFlag();
        buildelonr.withLongFielonld(
            elonarlybirdFielonldConstant.SHARelonD_STATUS_ID_CSF.gelontFielonldNamelon(),
            inRelonplyToStatusId.gelont());
        buildelonr.withLongFielonld(
            elonarlybirdFielonldConstant.IN_RelonPLY_TO_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon(),
            inRelonplyToStatusId.gelont());
      }
      if (inRelonplyToUselonrId.isPrelonselonnt()) {
        buildelonr.withRelonplyFlag();
        buildelonr.withInRelonplyToUselonrID(inRelonplyToUselonrId.gelont());
      }
    }
  }

  /**
   * Build thelon elonngagelonmelonnt fielonlds.
   */
  public static void buildNormalizelondMinelonngagelonmelonntFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons,
      elonarlybirdClustelonr clustelonr) throws IOelonxcelonption {
    if (elonarlybirdClustelonr.isArchivelon(clustelonr)) {
      int favoritelonCount = elonncodelondFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.FAVORITelon_COUNT);
      int relontwelonelontCount = elonncodelondFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT);
      int relonplyCount = elonncodelondFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.RelonPLY_COUNT);
      buildelonr
          .withNormalizelondMinelonngagelonmelonntFielonld(
              elonarlybirdFielonldConstant.NORMALIZelonD_FAVORITelon_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD
                  .gelontFielonldNamelon(),
              favoritelonCount);
      buildelonr
          .withNormalizelondMinelonngagelonmelonntFielonld(
              elonarlybirdFielonldConstant.NORMALIZelonD_RelonTWelonelonT_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD
                  .gelontFielonldNamelon(),
              relontwelonelontCount);
      buildelonr
          .withNormalizelondMinelonngagelonmelonntFielonld(
              elonarlybirdFielonldConstant.NORMALIZelonD_RelonPLY_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD
                  .gelontFielonldNamelon(),
              relonplyCount);
    }
  }

  /**
   * As selonelonn in SelonARCH-5617, welon somelontimelons havelon incorrelonct crelonatelondAt. This melonthod trielons to fix thelonm
   * by elonxtracting crelonation timelon from snowflakelon whelonn possiblelon.
   */
  public static long fixCrelonatelondAtTimelonStampIfNeloncelonssary(long id, long crelonatelondAtMs) {
    if (crelonatelondAtMs < VALID_CRelonATION_TIMelon_THRelonSHOLD_MILLIS
        && id > SnowflakelonIdParselonr.SNOWFLAKelon_ID_LOWelonR_BOUND) {
      // This twelonelont has a snowflakelon ID, and welon can elonxtract timelonstamp from thelon ID.
      ADJUSTelonD_BAD_CRelonATelonD_AT_COUNTelonR.increlonmelonnt();
      relonturn SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(id);
    } elonlselon if (!SnowflakelonIdParselonr.isTwelonelontIDAndCrelonatelondAtConsistelonnt(id, crelonatelondAtMs)) {
      LOG.elonrror(
          "Found inconsistelonnt twelonelont ID and crelonatelond at timelonstamp: [statusID={}], [crelonatelondAtMs={}]",
          id, crelonatelondAtMs);
      INCONSISTelonNT_TWelonelonT_ID_AND_CRelonATelonD_AT_MS.increlonmelonnt();
    }

    relonturn crelonatelondAtMs;
  }
}
