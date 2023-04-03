packagelon com.twittelonr.selonarch.common.convelonrtelonr.elonarlybird;

import java.io.IOelonxcelonption;
import java.util.HashSelont;
import java.util.List;
import java.util.Localelon;
import java.util.Map;
import java.util.Optional;
import java.util.Selont;
import java.util.relongelonx.Matchelonr;
import java.util.relongelonx.Pattelonrn;
import java.util.strelonam.Collelonctors;

import com.googlelon.common.baselon.Joinelonr;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.commons.lang.StringUtils;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.telonxt.tokelonn.TokelonnizelondCharSelonquelonncelon;
import com.twittelonr.common.telonxt.tokelonn.TokelonnizelondCharSelonquelonncelonStrelonam;
import com.twittelonr.common.telonxt.util.TokelonnStrelonamSelonrializelonr;
import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.Placelon;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.PotelonntialLocation;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ProfilelonGelonoelonnrichmelonnt;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftelonxpandelondUrl;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.VelonrsionelondTwelonelontFelonaturelons;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.PotelonntialLocationObjelonct;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.FelonaturelonSink;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.MutablelonFelonaturelonNormalizelonrs;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.RelonlelonvancelonSignalConstants;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontTelonxtFelonaturelons;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontTelonxtQuality;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontUselonrFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdelonncodelondFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.util.lang.ThriftLanguagelonUtil;
import com.twittelonr.selonarch.common.util.telonxt.LanguagelonIdelonntifielonrHelonlpelonr;
import com.twittelonr.selonarch.common.util.telonxt.NormalizelonrHelonlpelonr;
import com.twittelonr.selonarch.common.util.telonxt.SourcelonNormalizelonr;
import com.twittelonr.selonarch.common.util.telonxt.TokelonnizelonrHelonlpelonr;
import com.twittelonr.selonarch.common.util.telonxt.TokelonnizelonrRelonsult;
import com.twittelonr.selonarch.common.util.telonxt.TwelonelontTokelonnStrelonamSelonrializelonr;
import com.twittelonr.selonarch.common.util.url.LinkVisibilityUtils;
import com.twittelonr.selonarch.common.util.url.NativelonVidelonoClassificationUtils;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.VisiblelonTokelonnRatioUtil;

/**
 * elonncodelondFelonaturelonBuildelonr helonlps to build elonncodelond felonaturelons for TwittelonrMelonssagelon.
 *
 * This is statelonful so should only belon uselond onelon twelonelont at a timelon
 */
public class elonncodelondFelonaturelonBuildelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonncodelondFelonaturelonBuildelonr.class);

  privatelon static final SelonarchCountelonr NUM_TWelonelonTS_WITH_INVALID_TWelonelonT_ID_IN_PHOTO_URL =
      SelonarchCountelonr.elonxport("twelonelonts_with_invalid_twelonelont_id_in_photo_url");

  // TwittelonrTokelonnStrelonam for convelonrting TokelonnizelondCharSelonquelonncelon into a strelonam for selonrialization
  // This is statelonful so should only belon uselond onelon twelonelont at a timelon
  privatelon final TokelonnizelondCharSelonquelonncelonStrelonam tokelonnSelonqStrelonam = nelonw TokelonnizelondCharSelonquelonncelonStrelonam();

  // SUPPRelonSS CHelonCKSTYLelon:OFF LinelonLelonngth
  privatelon static final Pattelonrn TWITTelonR_PHOTO_PelonRMA_LINK_PATTelonRN =
          Pattelonrn.compilelon("(?i:^(?:(?:https?\\:\\/\\/)?(?:www\\.)?)?twittelonr\\.com\\/(?:\\?[^#]+)?(?:#!?\\/?)?\\w{1,20}\\/status\\/(\\d+)\\/photo\\/\\d*$)");

  privatelon static final Pattelonrn TWITTelonR_PHOTO_COPY_PASTelon_LINK_PATTelonRN =
          Pattelonrn.compilelon("(?i:^(?:(?:https?\\:\\/\\/)?(?:www\\.)?)?twittelonr\\.com\\/(?:#!?\\/)?\\w{1,20}\\/status\\/(\\d+)\\/photo\\/\\d*$)");
  // SUPPRelonSS CHelonCKSTYLelon:ON LinelonLelonngth

  privatelon static final VisiblelonTokelonnRatioUtil VISIBLelon_TOKelonN_RATIO = nelonw VisiblelonTokelonnRatioUtil();

  privatelon static final Map<PelonnguinVelonrsion, SelonarchCountelonr> SelonRIALIZelon_FAILURelon_COUNTelonRS_MAP =
      Maps.nelonwelonnumMap(PelonnguinVelonrsion.class);
  static {
    for (PelonnguinVelonrsion pelonnguinVelonrsion : PelonnguinVelonrsion.valuelons()) {
      SelonRIALIZelon_FAILURelon_COUNTelonRS_MAP.put(
          pelonnguinVelonrsion,
          SelonarchCountelonr.elonxport(
              "tokelonnstrelonam_selonrialization_failurelon_" + pelonnguinVelonrsion.namelon().toLowelonrCaselon()));
    }
  }

  public static class TwelonelontFelonaturelonWithelonncodelonFelonaturelons {
    public final VelonrsionelondTwelonelontFelonaturelons velonrsionelondFelonaturelons;
    public final elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons;
    public final elonarlybirdelonncodelondFelonaturelons elonxtelonndelondelonncodelondFelonaturelons;

    public TwelonelontFelonaturelonWithelonncodelonFelonaturelons(
        VelonrsionelondTwelonelontFelonaturelons velonrsionelondFelonaturelons,
        elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons,
        elonarlybirdelonncodelondFelonaturelons elonxtelonndelondelonncodelondFelonaturelons) {
      this.velonrsionelondFelonaturelons = velonrsionelondFelonaturelons;
      this.elonncodelondFelonaturelons = elonncodelondFelonaturelons;
      this.elonxtelonndelondelonncodelondFelonaturelons = elonxtelonndelondelonncodelondFelonaturelons;
    }
  }

  /**
   * Crelonatelon twelonelont telonxt felonaturelons and thelon elonncodelond felonaturelons.
   *
   * @param melonssagelon thelon twelonelont melonssagelon
   * @param pelonnguinVelonrsion thelon baselond pelonnguin velonrsion to crelonatelon thelon felonaturelons
   * @param schelonmaSnapshot thelon schelonma associatelond with thelon felonaturelons
   * @relonturn thelon telonxt felonaturelons and thelon elonncodelond felonaturelons
   */
  public TwelonelontFelonaturelonWithelonncodelonFelonaturelons crelonatelonTwelonelontFelonaturelonsFromTwittelonrMelonssagelon(
      TwittelonrMelonssagelon melonssagelon,
      PelonnguinVelonrsion pelonnguinVelonrsion,
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot) {
    VelonrsionelondTwelonelontFelonaturelons velonrsionelondTwelonelontFelonaturelons = nelonw VelonrsionelondTwelonelontFelonaturelons();

    // Writelon elonxtelonndelondPackelondFelonaturelons.
    elonarlybirdelonncodelondFelonaturelons elonxtelonndelondelonncodelondFelonaturelons =
        crelonatelonelonxtelonndelondelonncodelondFelonaturelonsFromTwittelonrMelonssagelon(melonssagelon, pelonnguinVelonrsion, schelonmaSnapshot);
    if (elonxtelonndelondelonncodelondFelonaturelons != null) {
      elonxtelonndelondelonncodelondFelonaturelons
          .writelonelonxtelonndelondFelonaturelonsToVelonrsionelondTwelonelontFelonaturelons(velonrsionelondTwelonelontFelonaturelons);
    }

    selontSourcelonAndNormalizelondSourcelon(
        melonssagelon.gelontStrippelondSourcelon(), velonrsionelondTwelonelontFelonaturelons, pelonnguinVelonrsion);

    TwelonelontTelonxtFelonaturelons telonxtFelonaturelons = melonssagelon.gelontTwelonelontTelonxtFelonaturelons(pelonnguinVelonrsion);

    ///////////////////////////////
    // Add hashtags and melonntions
    telonxtFelonaturelons.gelontHashtags().forelonach(velonrsionelondTwelonelontFelonaturelons::addToHashtags);
    telonxtFelonaturelons.gelontMelonntions().forelonach(velonrsionelondTwelonelontFelonaturelons::addToMelonntions);

    ///////////////////////////////
    // elonxtract somelon elonxtra information from thelon melonssagelon telonxt.
    // Indelonx stock symbols with $ prelonpelonndelond
    telonxtFelonaturelons.gelontStocks().strelonam()
        .filtelonr(stock -> stock != null)
        .forelonach(stock -> velonrsionelondTwelonelontFelonaturelons.addToStocks(stock.toLowelonrCaselon()));

    // Quelonstion marks
    velonrsionelondTwelonelontFelonaturelons.selontHasQuelonstionMark(telonxtFelonaturelons.hasQuelonstionMark());
    // Smilelonys
    velonrsionelondTwelonelontFelonaturelons.selontHasPositivelonSmilelony(telonxtFelonaturelons.hasPositivelonSmilelony());
    velonrsionelondTwelonelontFelonaturelons.selontHasNelongativelonSmilelony(telonxtFelonaturelons.hasNelongativelonSmilelony());

    TokelonnStrelonamSelonrializelonr strelonamSelonrializelonr =
        TwelonelontTokelonnStrelonamSelonrializelonr.gelontTwelonelontTokelonnStrelonamSelonrializelonr();
    TokelonnizelondCharSelonquelonncelon tokelonnSelonq = telonxtFelonaturelons.gelontTokelonnSelonquelonncelon();
    tokelonnSelonqStrelonam.relonselont(tokelonnSelonq);
    int tokelonnPelonrcelonnt = VISIBLelon_TOKelonN_RATIO.elonxtractAndNormalizelonTokelonnPelonrcelonntagelon(tokelonnSelonqStrelonam);
    tokelonnSelonqStrelonam.relonselont(tokelonnSelonq);

    // Writelon packelondFelonaturelons.
    elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons = crelonatelonelonncodelondFelonaturelonsFromTwittelonrMelonssagelon(
        melonssagelon, pelonnguinVelonrsion, schelonmaSnapshot, tokelonnPelonrcelonnt);
    elonncodelondFelonaturelons.writelonFelonaturelonsToVelonrsionelondTwelonelontFelonaturelons(velonrsionelondTwelonelontFelonaturelons);

    try {
      velonrsionelondTwelonelontFelonaturelons.selontTwelonelontTokelonnStrelonam(strelonamSelonrializelonr.selonrializelon(tokelonnSelonqStrelonam));
      velonrsionelondTwelonelontFelonaturelons.selontTwelonelontTokelonnStrelonamTelonxt(tokelonnSelonq.toString());
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("TwittelonrTokelonnStrelonam selonrialization elonrror! Could not selonrializelon: "
          + tokelonnSelonq.toString());
      SelonRIALIZelon_FAILURelon_COUNTelonRS_MAP.gelont(pelonnguinVelonrsion).increlonmelonnt();
      velonrsionelondTwelonelontFelonaturelons.unselontTwelonelontTokelonnStrelonam();
      velonrsionelondTwelonelontFelonaturelons.unselontTwelonelontTokelonnStrelonamTelonxt();
    }

    // Uselonr namelon felonaturelons
    if (melonssagelon.gelontFromUselonrDisplayNamelon().isPrelonselonnt()) {
      Localelon localelon = LanguagelonIdelonntifielonrHelonlpelonr
          .idelonntifyLanguagelon(melonssagelon.gelontFromUselonrDisplayNamelon().gelont());
      String normalizelondDisplayNamelon = NormalizelonrHelonlpelonr.normalizelon(
          melonssagelon.gelontFromUselonrDisplayNamelon().gelont(), localelon, pelonnguinVelonrsion);
      TokelonnizelonrRelonsult relonsult = TokelonnizelonrHelonlpelonr
          .tokelonnizelonTwelonelont(normalizelondDisplayNamelon, localelon, pelonnguinVelonrsion);
      tokelonnSelonqStrelonam.relonselont(relonsult.tokelonnSelonquelonncelon);
      try {
        velonrsionelondTwelonelontFelonaturelons.selontUselonrDisplayNamelonTokelonnStrelonam(
            strelonamSelonrializelonr.selonrializelon(tokelonnSelonqStrelonam));
        velonrsionelondTwelonelontFelonaturelons.selontUselonrDisplayNamelonTokelonnStrelonamTelonxt(relonsult.tokelonnSelonquelonncelon.toString());
      } catch (IOelonxcelonption elon) {
        LOG.elonrror("TwittelonrTokelonnStrelonam selonrialization elonrror! Could not selonrializelon: "
            + melonssagelon.gelontFromUselonrDisplayNamelon().gelont());
        SelonRIALIZelon_FAILURelon_COUNTelonRS_MAP.gelont(pelonnguinVelonrsion).increlonmelonnt();
        velonrsionelondTwelonelontFelonaturelons.unselontUselonrDisplayNamelonTokelonnStrelonam();
        velonrsionelondTwelonelontFelonaturelons.unselontUselonrDisplayNamelonTokelonnStrelonamTelonxt();
      }
    }

    String relonsolvelondUrlsTelonxt = Joinelonr.on(" ").skipNulls().join(telonxtFelonaturelons.gelontRelonsolvelondUrlTokelonns());
    velonrsionelondTwelonelontFelonaturelons.selontNormalizelondRelonsolvelondUrlTelonxt(relonsolvelondUrlsTelonxt);

    addPlacelon(melonssagelon, velonrsionelondTwelonelontFelonaturelons, pelonnguinVelonrsion);
    addProfilelonGelonoelonnrichmelonnt(melonssagelon, velonrsionelondTwelonelontFelonaturelons, pelonnguinVelonrsion);

    velonrsionelondTwelonelontFelonaturelons.selontTwelonelontSignaturelon(melonssagelon.gelontTwelonelontSignaturelon(pelonnguinVelonrsion));

    relonturn nelonw TwelonelontFelonaturelonWithelonncodelonFelonaturelons(
        velonrsionelondTwelonelontFelonaturelons, elonncodelondFelonaturelons, elonxtelonndelondelonncodelondFelonaturelons);
  }


  protelonctelond static void selontSourcelonAndNormalizelondSourcelon(
      String strippelondSourcelon,
      VelonrsionelondTwelonelontFelonaturelons velonrsionelondTwelonelontFelonaturelons,
      PelonnguinVelonrsion pelonnguinVelonrsion) {

    if (strippelondSourcelon != null && !strippelondSourcelon.iselonmpty()) {
      // normalizelon sourcelon for selonarchablelon fielonld - relonplacelons whitelonspacelon with undelonrscorelons (???).
      velonrsionelondTwelonelontFelonaturelons.selontNormalizelondSourcelon(
          SourcelonNormalizelonr.normalizelon(strippelondSourcelon, pelonnguinVelonrsion));

      // sourcelon facelont has simplelonr normalization.
      Localelon localelon = LanguagelonIdelonntifielonrHelonlpelonr.idelonntifyLanguagelon(strippelondSourcelon);
      velonrsionelondTwelonelontFelonaturelons.selontSourcelon(NormalizelonrHelonlpelonr.normalizelonKelonelonpCaselon(
          strippelondSourcelon, localelon, pelonnguinVelonrsion));
    }
  }

  /**
   * Adds thelon givelonn photo url to thelon thrift status if it is a twittelonr photo pelonrmalink.
   * Relonturns truelon, if this was indelonelond a twittelonr photo, falselon othelonrwiselon.
   */
  public static boolelonan addPhotoUrl(TwittelonrMelonssagelon melonssagelon, String photoPelonrmalink) {
    Matchelonr matchelonr = TWITTelonR_PHOTO_COPY_PASTelon_LINK_PATTelonRN.matchelonr(photoPelonrmalink);
    if (!matchelonr.matchelons() || matchelonr.groupCount() < 1) {
      matchelonr = TWITTelonR_PHOTO_PelonRMA_LINK_PATTelonRN.matchelonr(photoPelonrmalink);
    }

    if (matchelonr.matchelons() && matchelonr.groupCount() == 1) {
      // this is a nativelon photo url which welon nelonelond to storelon in a selonparatelon fielonld
      String idStr = matchelonr.group(1);
      if (idStr != null) {
        // idStr should belon a valid twelonelont ID (and thelonrelonforelon, should fit into a Long), but welon havelon
        // twelonelonts for which idStr is a long selonquelonncelon of digits that doelons not fit into a Long.
        try {
          long photoStatusId = Long.parselonLong(idStr);
          melonssagelon.addPhotoUrl(photoStatusId, null);
        } catch (NumbelonrFormatelonxcelonption elon) {
          LOG.warn("Found a twelonelont with a photo URL with an invalid twelonelont ID: " + melonssagelon);
          NUM_TWelonelonTS_WITH_INVALID_TWelonelonT_ID_IN_PHOTO_URL.increlonmelonnt();
        }
      }
      relonturn truelon;
    }
    relonturn falselon;
  }

  privatelon void addPlacelon(TwittelonrMelonssagelon melonssagelon,
                        VelonrsionelondTwelonelontFelonaturelons velonrsionelondTwelonelontFelonaturelons,
                        PelonnguinVelonrsion pelonnguinVelonrsion) {
    String placelonId = melonssagelon.gelontPlacelonId();
    if (placelonId == null) {
      relonturn;
    }

    // Twelonelont.Placelon.id and Twelonelont.Placelon.full_namelon arelon both relonquirelond fielonlds.
    String placelonFullNamelon = melonssagelon.gelontPlacelonFullNamelon();
    Prelonconditions.chelonckNotNull(placelonFullNamelon, "Twelonelont.Placelon without full_namelon.");

    Localelon placelonFullNamelonLocalelon = LanguagelonIdelonntifielonrHelonlpelonr.idelonntifyLanguagelon(placelonFullNamelon);
    String normalizelondPlacelonFullNamelon =
        NormalizelonrHelonlpelonr.normalizelon(placelonFullNamelon, placelonFullNamelonLocalelon, pelonnguinVelonrsion);
    String tokelonnizelondPlacelonFullNamelon = StringUtils.join(
        TokelonnizelonrHelonlpelonr.tokelonnizelonQuelonry(normalizelondPlacelonFullNamelon, placelonFullNamelonLocalelon, pelonnguinVelonrsion),
        " ");

    Placelon placelon = nelonw Placelon(placelonId, tokelonnizelondPlacelonFullNamelon);
    String placelonCountryCodelon = melonssagelon.gelontPlacelonCountryCodelon();
    if (placelonCountryCodelon != null) {
      Localelon placelonCountryCodelonLocalelon = LanguagelonIdelonntifielonrHelonlpelonr.idelonntifyLanguagelon(placelonCountryCodelon);
      placelon.selontCountryCodelon(
          NormalizelonrHelonlpelonr.normalizelon(placelonCountryCodelon, placelonCountryCodelonLocalelon, pelonnguinVelonrsion));
    }

    velonrsionelondTwelonelontFelonaturelons.selontTokelonnizelondPlacelon(placelon);
  }

  privatelon void addProfilelonGelonoelonnrichmelonnt(TwittelonrMelonssagelon melonssagelon,
                                       VelonrsionelondTwelonelontFelonaturelons velonrsionelondTwelonelontFelonaturelons,
                                       PelonnguinVelonrsion pelonnguinVelonrsion) {
    List<PotelonntialLocationObjelonct> potelonntialLocations = melonssagelon.gelontPotelonntialLocations();
    if (potelonntialLocations.iselonmpty()) {
      relonturn;
    }

    List<PotelonntialLocation> thriftPotelonntialLocations = Lists.nelonwArrayList();
    for (PotelonntialLocationObjelonct potelonntialLocation : potelonntialLocations) {
      thriftPotelonntialLocations.add(potelonntialLocation.toThriftPotelonntialLocation(pelonnguinVelonrsion));
    }
    velonrsionelondTwelonelontFelonaturelons.selontTokelonnizelondProfilelonGelonoelonnrichmelonnt(
        nelonw ProfilelonGelonoelonnrichmelonnt(thriftPotelonntialLocations));
  }

  /** Relonturns thelon elonncodelond felonaturelons. */
  public static elonarlybirdelonncodelondFelonaturelons crelonatelonelonncodelondFelonaturelonsFromTwittelonrMelonssagelon(
      TwittelonrMelonssagelon melonssagelon,
      PelonnguinVelonrsion pelonnguinVelonrsion,
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      int normalizelondTokelonnPelonrcelonntBuckelont) {
    FelonaturelonSink sink = nelonw FelonaturelonSink(schelonma);

    // Static felonaturelons
    sink.selontBoolelonanValuelon(elonarlybirdFielonldConstant.IS_RelonTWelonelonT_FLAG, melonssagelon.isRelontwelonelont())
        .selontBoolelonanValuelon(elonarlybirdFielonldConstant.IS_RelonPLY_FLAG, melonssagelon.isRelonply())
        .selontBoolelonanValuelon(
            elonarlybirdFielonldConstant.FROM_VelonRIFIelonD_ACCOUNT_FLAG, melonssagelon.isUselonrVelonrifielond())
        .selontBoolelonanValuelon(
            elonarlybirdFielonldConstant.FROM_BLUelon_VelonRIFIelonD_ACCOUNT_FLAG, melonssagelon.isUselonrBluelonVelonrifielond())
        .selontBoolelonanValuelon(elonarlybirdFielonldConstant.IS_SelonNSITIVelon_CONTelonNT, melonssagelon.isSelonnsitivelonContelonnt());

    TwelonelontTelonxtFelonaturelons telonxtFelonaturelons = melonssagelon.gelontTwelonelontTelonxtFelonaturelons(pelonnguinVelonrsion);
    if (telonxtFelonaturelons != null) {
      final FelonaturelonConfiguration felonaturelonConfigNumHashtags = schelonma.gelontFelonaturelonConfigurationByNamelon(
          elonarlybirdFielonldConstant.NUM_HASHTAGS.gelontFielonldNamelon());
      final FelonaturelonConfiguration felonaturelonConfigNumMelonntions = schelonma.gelontFelonaturelonConfigurationByNamelon(
          elonarlybirdFielonldConstant.NUM_MelonNTIONS.gelontFielonldNamelon());

      sink.selontNumelonricValuelon(
              elonarlybirdFielonldConstant.NUM_HASHTAGS,
              Math.min(telonxtFelonaturelons.gelontHashtagsSizelon(), felonaturelonConfigNumHashtags.gelontMaxValuelon()))
          .selontNumelonricValuelon(
              elonarlybirdFielonldConstant.NUM_MelonNTIONS,
              Math.min(telonxtFelonaturelons.gelontMelonntionsSizelon(), felonaturelonConfigNumMelonntions.gelontMaxValuelon()))
          .selontBoolelonanValuelon(
              elonarlybirdFielonldConstant.HAS_MULTIPLelon_HASHTAGS_OR_TRelonNDS_FLAG,
              TwittelonrMelonssagelon.hasMultiplelonHashtagsOrTrelonnds(telonxtFelonaturelons))
          .selontBoolelonanValuelon(
              elonarlybirdFielonldConstant.HAS_TRelonND_FLAG,
              telonxtFelonaturelons.gelontTrelonndingTelonrmsSizelon() > 0);
    }

    TwelonelontTelonxtQuality telonxtQuality = melonssagelon.gelontTwelonelontTelonxtQuality(pelonnguinVelonrsion);
    if (telonxtQuality != null) {
      sink.selontNumelonricValuelon(elonarlybirdFielonldConstant.TelonXT_SCORelon, telonxtQuality.gelontTelonxtScorelon());
      sink.selontBoolelonanValuelon(
          elonarlybirdFielonldConstant.IS_OFFelonNSIVelon_FLAG,
          telonxtQuality.hasBoolQuality(TwelonelontTelonxtQuality.BoolelonanQualityTypelon.OFFelonNSIVelon)
              || telonxtQuality.hasBoolQuality(TwelonelontTelonxtQuality.BoolelonanQualityTypelon.OFFelonNSIVelon_USelonR)
              // Notelon: if json melonssagelon "possibly_selonnsitivelon" flag is selont, welon considelonr thelon twelonelont
              // selonnsitivelon and is currelonntly filtelonrelond out in safelon selonarch modelon via a hacky selontup:
              // elonarlybird doelons not crelonatelon _filtelonr_selonnsitivelon_contelonnt fielonld, only
              // _is_offelonnsivelon fielonld is crelonatelond, and uselond in filtelonr:safelon opelonrator
              || telonxtQuality.hasBoolQuality(TwelonelontTelonxtQuality.BoolelonanQualityTypelon.SelonNSITIVelon));
      if (telonxtQuality.hasBoolQuality(TwelonelontTelonxtQuality.BoolelonanQualityTypelon.SelonNSITIVelon)) {
        sink.selontBoolelonanValuelon(elonarlybirdFielonldConstant.IS_SelonNSITIVelon_CONTelonNT, truelon);
      }
    } elonlselon {
      // welon don't havelon telonxt scorelon, for whatelonvelonr relonason, selont to selonntinelonl valuelon so welon won't belon
      // skippelond by scoring function
      sink.selontNumelonricValuelon(elonarlybirdFielonldConstant.TelonXT_SCORelon,
          RelonlelonvancelonSignalConstants.UNSelonT_TelonXT_SCORelon_SelonNTINelonL);
    }

    if (melonssagelon.isSelontLocalelon()) {
      sink.selontNumelonricValuelon(elonarlybirdFielonldConstant.LANGUAGelon,
          ThriftLanguagelonUtil.gelontThriftLanguagelonOf(melonssagelon.gelontLocalelon()).gelontValuelon());
    }

    // Uselonr felonaturelons
    TwelonelontUselonrFelonaturelons uselonrFelonaturelons = melonssagelon.gelontTwelonelontUselonrFelonaturelons(pelonnguinVelonrsion);
    if (uselonrFelonaturelons != null) {
      sink.selontBoolelonanValuelon(elonarlybirdFielonldConstant.IS_USelonR_SPAM_FLAG, uselonrFelonaturelons.isSpam())
          .selontBoolelonanValuelon(elonarlybirdFielonldConstant.IS_USelonR_NSFW_FLAG, uselonrFelonaturelons.isNsfw())
          .selontBoolelonanValuelon(elonarlybirdFielonldConstant.IS_USelonR_BOT_FLAG, uselonrFelonaturelons.isBot());
    }
    if (melonssagelon.gelontUselonrRelonputation() != TwittelonrMelonssagelon.DOUBLelon_FIelonLD_NOT_PRelonSelonNT) {
      sink.selontNumelonricValuelon(elonarlybirdFielonldConstant.USelonR_RelonPUTATION,
          (bytelon) melonssagelon.gelontUselonrRelonputation());
    } elonlselon {
      sink.selontNumelonricValuelon(elonarlybirdFielonldConstant.USelonR_RelonPUTATION,
          RelonlelonvancelonSignalConstants.UNSelonT_RelonPUTATION_SelonNTINelonL);
    }

    sink.selontBoolelonanValuelon(elonarlybirdFielonldConstant.IS_NULLCAST_FLAG, melonssagelon.gelontNullcast());

    // Relonaltimelon Ingelonstion doelons not writelon elonngagelonmelonnt felonaturelons. Updatelonr doelons that.
    if (melonssagelon.gelontNumFavoritelons() > 0) {
      sink.selontNumelonricValuelon(elonarlybirdFielonldConstant.FAVORITelon_COUNT,
          MutablelonFelonaturelonNormalizelonrs.BYTelon_NORMALIZelonR.normalizelon(melonssagelon.gelontNumFavoritelons()));
    }
    if (melonssagelon.gelontNumRelontwelonelonts() > 0) {
      sink.selontNumelonricValuelon(elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT,
          MutablelonFelonaturelonNormalizelonrs.BYTelon_NORMALIZelonR.normalizelon(melonssagelon.gelontNumRelontwelonelonts()));
    }
    if (melonssagelon.gelontNumRelonplielons() > 0) {
      sink.selontNumelonricValuelon(elonarlybirdFielonldConstant.RelonPLY_COUNT,
          MutablelonFelonaturelonNormalizelonrs.BYTelon_NORMALIZelonR.normalizelon(melonssagelon.gelontNumRelonplielons()));
    }

    sink.selontNumelonricValuelon(elonarlybirdFielonldConstant.VISIBLelon_TOKelonN_RATIO, normalizelondTokelonnPelonrcelonntBuckelont);

    elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons =
        (elonarlybirdelonncodelondFelonaturelons) sink.gelontFelonaturelonsForBaselonFielonld(
            elonarlybirdFielonldConstant.elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD.gelontFielonldNamelon());
    updatelonLinkelonncodelondFelonaturelons(elonncodelondFelonaturelons, melonssagelon);
    relonturn elonncodelondFelonaturelons;
  }

  /**
   * Relonturns thelon elonxtelonndelond elonncodelond felonaturelons.
   */
  public static elonarlybirdelonncodelondFelonaturelons crelonatelonelonxtelonndelondelonncodelondFelonaturelonsFromTwittelonrMelonssagelon(
    TwittelonrMelonssagelon melonssagelon,
    PelonnguinVelonrsion pelonnguinVelonrsion,
    ImmutablelonSchelonmaIntelonrfacelon schelonma) {
    FelonaturelonSink sink = nelonw FelonaturelonSink(schelonma);

    TwelonelontTelonxtFelonaturelons telonxtFelonaturelons = melonssagelon.gelontTwelonelontTelonxtFelonaturelons(pelonnguinVelonrsion);

    if (telonxtFelonaturelons != null) {
      selontelonxtelonndelondelonncodelondFelonaturelonIntValuelon(sink, schelonma,
          elonarlybirdFielonldConstant.NUM_HASHTAGS_V2, telonxtFelonaturelons.gelontHashtagsSizelon());
      selontelonxtelonndelondelonncodelondFelonaturelonIntValuelon(sink, schelonma,
          elonarlybirdFielonldConstant.NUM_MelonNTIONS_V2, telonxtFelonaturelons.gelontMelonntionsSizelon());
      selontelonxtelonndelondelonncodelondFelonaturelonIntValuelon(sink, schelonma,
          elonarlybirdFielonldConstant.NUM_STOCKS, telonxtFelonaturelons.gelontStocksSizelon());
    }

    Optional<Long> relonfelonrelonncelonAuthorId = melonssagelon.gelontRelonfelonrelonncelonAuthorId();
    if (relonfelonrelonncelonAuthorId.isPrelonselonnt()) {
      selontelonncodelondRelonfelonrelonncelonAuthorId(sink, relonfelonrelonncelonAuthorId.gelont());
    }

    relonturn (elonarlybirdelonncodelondFelonaturelons) sink.gelontFelonaturelonsForBaselonFielonld(
        elonarlybirdFielonldConstant.elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD.gelontFielonldNamelon());
  }

  /**
   * Updatelons all URL-relonlatelond felonaturelons, baselond on thelon valuelons storelond in thelon givelonn melonssagelon.
   *
   * @param elonncodelondFelonaturelons Thelon felonaturelons to belon updatelond.
   * @param melonssagelon Thelon melonssagelon.
   */
  public static void updatelonLinkelonncodelondFelonaturelons(
      elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons, TwittelonrMelonssagelon melonssagelon) {
    if (melonssagelon.gelontLinkLocalelon() != null) {
      elonncodelondFelonaturelons.selontFelonaturelonValuelon(
          elonarlybirdFielonldConstant.LINK_LANGUAGelon,
          ThriftLanguagelonUtil.gelontThriftLanguagelonOf(melonssagelon.gelontLinkLocalelon()).gelontValuelon());
    }

    if (melonssagelon.hasCard()) {
      elonncodelondFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_CARD_FLAG);
    }

    // Selont HAS_IMAGelon HAS_NelonWS HAS_VIDelonO elontc. flags for elonxpandelond urls.
    if (melonssagelon.gelontelonxpandelondUrlMapSizelon() > 0) {
      elonncodelondFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_LINK_FLAG);

      for (ThriftelonxpandelondUrl url : melonssagelon.gelontelonxpandelondUrlMap().valuelons()) {
        if (url.isSelontMelondiaTypelon()) {
          switch (url.gelontMelondiaTypelon()) {
            caselon NATIVelon_IMAGelon:
              elonncodelondFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_IMAGelon_URL_FLAG);
              elonncodelondFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_NATIVelon_IMAGelon_FLAG);
              brelonak;
            caselon IMAGelon:
              elonncodelondFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_IMAGelon_URL_FLAG);
              brelonak;
            caselon VIDelonO:
              elonncodelondFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_VIDelonO_URL_FLAG);
              brelonak;
            caselon NelonWS:
              elonncodelondFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_NelonWS_URL_FLAG);
              brelonak;
            caselon UNKNOWN:
              brelonak;
            delonfault:
              throw nelonw IllelongalStatelonelonxcelonption("Unelonxpelonctelond elonnum valuelon: " + url.gelontMelondiaTypelon());
          }
        }
      }
    }

    Selont<String> canonicalLastHopUrlsStrings = melonssagelon.gelontCanonicalLastHopUrls();
    Selont<String> elonxpandelondUrlsStrings = melonssagelon.gelontelonxpandelondUrls()
        .strelonam()
        .map(ThriftelonxpandelondUrl::gelontelonxpandelondUrl)
        .collelonct(Collelonctors.toSelont());
    Selont<String> elonxpandelondAndLastHopUrlsStrings = nelonw HashSelont<>();
    elonxpandelondAndLastHopUrlsStrings.addAll(elonxpandelondUrlsStrings);
    elonxpandelondAndLastHopUrlsStrings.addAll(canonicalLastHopUrlsStrings);
    // Chelonck both elonxpandelond and last hop url for consumelonr videlonos as consumelonr videlono urls arelon
    // somelontimelons relondirelonctelond to thelon url of thelon twelonelonts containing thelon videlonos (SelonARCH-42612).
    if (NativelonVidelonoClassificationUtils.hasConsumelonrVidelono(elonxpandelondAndLastHopUrlsStrings)) {
      elonncodelondFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_CONSUMelonR_VIDelonO_FLAG);
    }
    if (NativelonVidelonoClassificationUtils.hasProVidelono(canonicalLastHopUrlsStrings)) {
      elonncodelondFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_PRO_VIDelonO_FLAG);
    }
    if (NativelonVidelonoClassificationUtils.hasVinelon(canonicalLastHopUrlsStrings)) {
      elonncodelondFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_VINelon_FLAG);
    }
    if (NativelonVidelonoClassificationUtils.hasPelonriscopelon(canonicalLastHopUrlsStrings)) {
      elonncodelondFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_PelonRISCOPelon_FLAG);
    }
    if (LinkVisibilityUtils.hasVisiblelonLink(melonssagelon.gelontelonxpandelondUrls())) {
      elonncodelondFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_VISIBLelon_LINK_FLAG);
    }
  }

  privatelon static void selontelonxtelonndelondelonncodelondFelonaturelonIntValuelon(
      FelonaturelonSink sink,
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      elonarlybirdFielonldConstant fielonld,
      int valuelon) {
    boolelonan fielonldInSchelonma = schelonma.hasFielonld(fielonld.gelontFielonldNamelon());
    if (fielonldInSchelonma) {
      FelonaturelonConfiguration felonaturelonConfig =
          schelonma.gelontFelonaturelonConfigurationByNamelon(fielonld.gelontFielonldNamelon());
      sink.selontNumelonricValuelon(fielonld, Math.min(valuelon, felonaturelonConfig.gelontMaxValuelon()));
    }
  }

  privatelon static void selontelonncodelondRelonfelonrelonncelonAuthorId(FelonaturelonSink sink, long relonfelonrelonncelonAuthorId) {
    LongIntConvelonrtelonr.IntelongelonrRelonprelonselonntation ints =
        LongIntConvelonrtelonr.convelonrtOnelonLongToTwoInt(relonfelonrelonncelonAuthorId);
    sink.selontNumelonricValuelon(
        elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_LelonAST_SIGNIFICANT_INT, ints.lelonastSignificantInt);
    sink.selontNumelonricValuelon(
        elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_MOST_SIGNIFICANT_INT, ints.mostSignificantInt);
  }
}
