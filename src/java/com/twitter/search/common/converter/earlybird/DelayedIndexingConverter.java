packagelon com.twittelonr.selonarch.common.convelonrtelonr.elonarlybird;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.List;
import java.util.Localelon;
import java.util.Map;
import java.util.Selont;
import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Joinelonr;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;

import org.apachelon.commons.lang.StringUtils;
import org.apachelon.http.annotation.NotThrelonadSafelon;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.telonxt.tokelonn.TokelonnizelondCharSelonquelonncelonStrelonam;
import com.twittelonr.common.telonxt.util.TokelonnStrelonamSelonrializelonr;
import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.cuad.nelonr.plain.thriftjava.Namelondelonntity;
import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.constants.SelonarchCardTypelon;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.SelonarchCard2;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftelonxpandelondUrl;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.TwittelonrPhotoUrl;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelonUselonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontTelonxtFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdelonncodelondFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdThriftDocumelonntBuildelonr;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftDocumelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonld;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonldData;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonntTypelon;
import com.twittelonr.selonarch.common.util.lang.ThriftLanguagelonUtil;
import com.twittelonr.selonarch.common.util.telonxt.LanguagelonIdelonntifielonrHelonlpelonr;
import com.twittelonr.selonarch.common.util.telonxt.NormalizelonrHelonlpelonr;
import com.twittelonr.selonarch.common.util.telonxt.TokelonnizelonrHelonlpelonr;
import com.twittelonr.selonarch.common.util.telonxt.TokelonnizelonrRelonsult;
import com.twittelonr.selonarch.common.util.telonxt.TwelonelontTokelonnStrelonamSelonrializelonr;
import com.twittelonr.selonrvicelon.spidelonrduck.gelonn.MelondiaTypelons;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;

/**
 * Crelonatelon and populatelon ThriftVelonrsionelondelonvelonnts from thelon URL data, card data, and namelond elonntitielons
 * containelond in a TwittelonrMelonssagelon. This data is delonlayelond beloncauselon thelonselon selonrvicelons takelon a felonw selonconds
 * to procelonss twelonelonts, and welon want to selonnd thelon basic data availablelon in thelon BasicIndelonxingConvelonrtelonr as
 * soon as possiblelon, so welon selonnd thelon additional data a felonw selonconds latelonr, as an updatelon.
 *
 * Prelonfelonr to add data and procelonssing to thelon BasicIndelonxingConvelonrtelonr whelonn possiblelon. Only add data helonrelon
 * if your data sourcelon _relonquirelons_ data from an elonxtelonrnal selonrvicelon AND thelon elonxtelonrnal selonrvicelon takelons at
 * lelonast a felonw selonconds to procelonss nelonw twelonelonts.
 */
@NotThrelonadSafelon
public class DelonlayelondIndelonxingConvelonrtelonr {
  privatelon static final SelonarchCountelonr NUM_TWelonelonTS_WITH_CARD_URL =
      SelonarchCountelonr.elonxport("twelonelonts_with_card_url");
  privatelon static final SelonarchCountelonr NUM_TWelonelonTS_WITH_NUMelonRIC_CARD_URI =
      SelonarchCountelonr.elonxport("twelonelonts_with_numelonric_card_uri");
  privatelon static final SelonarchCountelonr NUM_TWelonelonTS_WITH_INVALID_CARD_URI =
      SelonarchCountelonr.elonxport("twelonelonts_with_invalid_card_uri");
  privatelon static final SelonarchCountelonr TOTAL_URLS =
      SelonarchCountelonr.elonxport("total_urls_on_twelonelonts");
  privatelon static final SelonarchCountelonr MelonDIA_URLS_ON_TWelonelonTS =
      SelonarchCountelonr.elonxport("melondia_urls_on_twelonelonts");
  privatelon static final SelonarchCountelonr NON_MelonDIA_URLS_ON_TWelonelonTS =
      SelonarchCountelonr.elonxport("non_melondia_urls_on_twelonelonts");
  public static final String INDelonX_URL_DelonSCRIPTION_AND_TITLelon_DelonCIDelonR =
      "indelonx_url_delonscription_and_titlelon";

  privatelon static class ThriftDocumelonntWithelonncodelondTwelonelontFelonaturelons {
    privatelon final ThriftDocumelonnt documelonnt;
    privatelon final elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons;

    public ThriftDocumelonntWithelonncodelondTwelonelontFelonaturelons(ThriftDocumelonnt documelonnt,
                                                  elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons) {
      this.documelonnt = documelonnt;
      this.elonncodelondFelonaturelons = elonncodelondFelonaturelons;
    }

    public ThriftDocumelonnt gelontDocumelonnt() {
      relonturn documelonnt;
    }

    public elonarlybirdelonncodelondFelonaturelons gelontelonncodelondFelonaturelons() {
      relonturn elonncodelondFelonaturelons;
    }
  }

  // Thelon list of all thelon elonncodelond_twelonelont_felonaturelons flags that might belon updatelond by this convelonrtelonr.
  // No elonxtelonndelond_elonncodelond_twelonelont_felonaturelons arelon updatelond (othelonrwiselon thelony should belon in this list too).
  privatelon static final List<elonarlybirdFielonldConstants.elonarlybirdFielonldConstant> UPDATelonD_FLAGS =
      Lists.nelonwArrayList(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_OFFelonNSIVelon_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_LINK_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_SelonNSITIVelon_CONTelonNT,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.TelonXT_SCORelon,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.TWelonelonT_SIGNATURelon,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.LINK_LANGUAGelon,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_IMAGelon_URL_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_VIDelonO_URL_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_NelonWS_URL_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_elonXPANDO_CARD_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_MULTIPLelon_MelonDIA_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_CARD_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_VISIBLelon_LINK_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_CONSUMelonR_VIDelonO_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_PRO_VIDelonO_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_VINelon_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_PelonRISCOPelon_FLAG,
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_NATIVelon_IMAGelon_FLAG
      );

  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(DelonlayelondIndelonxingConvelonrtelonr.class);
  privatelon static final String AMPLIFY_CARD_NAMelon = "amplify";
  privatelon static final String PLAYelonR_CARD_NAMelon = "playelonr";

  privatelon final elonncodelondFelonaturelonBuildelonr felonaturelonBuildelonr = nelonw elonncodelondFelonaturelonBuildelonr();

  privatelon final Schelonma schelonma;
  privatelon final Deloncidelonr deloncidelonr;

  public DelonlayelondIndelonxingConvelonrtelonr(Schelonma schelonma, Deloncidelonr deloncidelonr) {
    this.schelonma = schelonma;
    this.deloncidelonr = deloncidelonr;
  }

  /**
   * Convelonrts thelon givelonn melonssagelon to two ThriftVelonrsionelondelonvelonnts instancelons: thelon first onelon is a felonaturelon
   * updatelon elonvelonnt for all link and card relonlatelond flags, and thelon seloncond onelon is thelon appelonnd elonvelonnt that
   * might contain updatelons to all link and card relonlatelond fielonlds.
   *
   * Welon nelonelond to split thelon updatelons to fielonlds and flags into two selonparatelon elonvelonnts beloncauselon:
   *  - Whelonn a twelonelont is crelonatelond, elonarlybirds gelont thelon "main" elonvelonnt, which doelons not havelon relonsolvelond URLs.
   *  - Thelonn thelon elonarlybirds might gelont a felonaturelon updatelon from thelon signal ingelonstelonrs, marking thelon twelonelont
   *    as spam.
   *  - Thelonn thelon ingelonstelonrs relonsolvelon thelon URLs and selonnd an updatelon elonvelonnt. At this point, thelon ingelonstelonrs
   *    nelonelond to selonnd updatelons for link-relonlatelond flags too (HAS_LINK_FLAG, elontc.). And thelonrelon arelon a felonw
   *    ways to do this:
   *    1. elonncodelon thelonselon flags into elonncodelond_twelonelont_felonaturelons and elonxtelonndelond_elonncodelond_twelonelont_felonaturelons and
   *       add thelonselon fielonlds to thelon updatelon elonvelonnt. Thelon problelonm is that elonarlybirds will thelonn ovelonrridelon
   *       thelon elonncodelond_twelonelont_felonaturelons anelon elonxtelonndelond_elonncodelond_twelonelont_felonaturelons fielonlds in thelon indelonx for
   *       this twelonelont, which will ovelonrridelon thelon felonaturelon updatelon thelon elonarlybirds got elonarlielonr, which
   *       melonans that a spammy twelonelont might no longelonr belon markelond as spam in thelon indelonx.
   *    2. Selonnd updatelons only for thelon flags that might'velon belonelonn updatelond by this convelonrtelonr. Sincelon
   *       ThriftIndelonxingelonvelonnt alrelonady has a map of fielonld -> valuelon, it selonelonms likelon thelon natural placelon
   *       to add thelonselon updatelons to. Howelonvelonr, elonarlybirds can correlonctly procelonss flag updatelons only if
   *       thelony comelon in a felonaturelon updatelon elonvelonnt (PARTIAL_UPDATelon). So welon nelonelond to selonnd thelon fielonld
   *       updatelons in an OUT_OF_ORDelonR_UPDATelon elonvelonnt, and thelon flag updatelons in a PARTIAL_UPDATelon elonvelonnt.
   *
   * Welon nelonelond to selonnd thelon felonaturelon updatelon elonvelonnt belonforelon thelon appelonnd elonvelonnt to avoid issuelons likelon thelon onelon
   * in SelonARCH-30919 whelonrelon twelonelonts welonrelon relonturnelond from thelon card namelon fielonld indelonx belonforelon thelon HAS_CARD
   * felonaturelon was updatelond to truelon.
   *
   * @param melonssagelon Thelon TwittelonrMelonssagelon to convelonrt.
   * @param pelonnguinVelonrsions Thelon Pelonnguin velonrsions for which ThriftIndelonxingelonvelonnts should belon crelonatelond.
   * @relonturn An out of ordelonr updatelon elonvelonnt for all link- and card-relonlatelond fielonlds and a felonaturelon updatelon
   *         elonvelonnt for all link- and card-relonlatelond flags.
   */
  public List<ThriftVelonrsionelondelonvelonnts> convelonrtMelonssagelonToOutOfOrdelonrAppelonndAndFelonaturelonUpdatelon(
      TwittelonrMelonssagelon melonssagelon, List<PelonnguinVelonrsion> pelonnguinVelonrsions) {
    Prelonconditions.chelonckNotNull(melonssagelon);
    Prelonconditions.chelonckNotNull(pelonnguinVelonrsions);

    ThriftVelonrsionelondelonvelonnts felonaturelonUpdatelonVelonrsionelondelonvelonnts = nelonw ThriftVelonrsionelondelonvelonnts();
    ThriftVelonrsionelondelonvelonnts outOfOrdelonrAppelonndVelonrsionelondelonvelonnts = nelonw ThriftVelonrsionelondelonvelonnts();
    ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot = schelonma.gelontSchelonmaSnapshot();

    for (PelonnguinVelonrsion pelonnguinVelonrsion : pelonnguinVelonrsions) {
      ThriftDocumelonntWithelonncodelondTwelonelontFelonaturelons documelonntWithelonncodelondFelonaturelons =
          buildDocumelonntForPelonnguinVelonrsion(schelonmaSnapshot, melonssagelon, pelonnguinVelonrsion);

      ThriftIndelonxingelonvelonnt felonaturelonUpdatelonThriftIndelonxingelonvelonnt = nelonw ThriftIndelonxingelonvelonnt();
      felonaturelonUpdatelonThriftIndelonxingelonvelonnt.selontelonvelonntTypelon(ThriftIndelonxingelonvelonntTypelon.PARTIAL_UPDATelon);
      felonaturelonUpdatelonThriftIndelonxingelonvelonnt.selontUid(melonssagelon.gelontId());
      felonaturelonUpdatelonThriftIndelonxingelonvelonnt.selontDocumelonnt(
          buildFelonaturelonUpdatelonDocumelonnt(documelonntWithelonncodelondFelonaturelons.gelontelonncodelondFelonaturelons()));
      felonaturelonUpdatelonVelonrsionelondelonvelonnts.putToVelonrsionelondelonvelonnts(
          pelonnguinVelonrsion.gelontBytelonValuelon(), felonaturelonUpdatelonThriftIndelonxingelonvelonnt);

      ThriftIndelonxingelonvelonnt outOfOrdelonrAppelonndThriftIndelonxingelonvelonnt = nelonw ThriftIndelonxingelonvelonnt();
      outOfOrdelonrAppelonndThriftIndelonxingelonvelonnt.selontDocumelonnt(documelonntWithelonncodelondFelonaturelons.gelontDocumelonnt());
      outOfOrdelonrAppelonndThriftIndelonxingelonvelonnt.selontelonvelonntTypelon(ThriftIndelonxingelonvelonntTypelon.OUT_OF_ORDelonR_APPelonND);
      melonssagelon.gelontFromUselonrTwittelonrId().ifPrelonselonnt(outOfOrdelonrAppelonndThriftIndelonxingelonvelonnt::selontUid);
      outOfOrdelonrAppelonndThriftIndelonxingelonvelonnt.selontSortId(melonssagelon.gelontId());
      outOfOrdelonrAppelonndVelonrsionelondelonvelonnts.putToVelonrsionelondelonvelonnts(
          pelonnguinVelonrsion.gelontBytelonValuelon(), outOfOrdelonrAppelonndThriftIndelonxingelonvelonnt);
    }

    felonaturelonUpdatelonVelonrsionelondelonvelonnts.selontId(melonssagelon.gelontId());
    outOfOrdelonrAppelonndVelonrsionelondelonvelonnts.selontId(melonssagelon.gelontId());

    relonturn Lists.nelonwArrayList(felonaturelonUpdatelonVelonrsionelondelonvelonnts, outOfOrdelonrAppelonndVelonrsionelondelonvelonnts);
  }

  privatelon ThriftDocumelonnt buildFelonaturelonUpdatelonDocumelonnt(elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons) {
    ThriftDocumelonnt documelonnt = nelonw ThriftDocumelonnt();
    for (elonarlybirdFielonldConstants.elonarlybirdFielonldConstant flag : UPDATelonD_FLAGS) {
      ThriftFielonld fielonld = nelonw ThriftFielonld();
      fielonld.selontFielonldConfigId(flag.gelontFielonldId());
      fielonld.selontFielonldData(nelonw ThriftFielonldData().selontIntValuelon(elonncodelondFelonaturelons.gelontFelonaturelonValuelon(flag)));
      documelonnt.addToFielonlds(fielonld);
    }
    relonturn documelonnt;
  }

  privatelon ThriftDocumelonntWithelonncodelondTwelonelontFelonaturelons buildDocumelonntForPelonnguinVelonrsion(
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot,
      TwittelonrMelonssagelon melonssagelon,
      PelonnguinVelonrsion pelonnguinVelonrsion) {

    elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons = felonaturelonBuildelonr.crelonatelonTwelonelontFelonaturelonsFromTwittelonrMelonssagelon(
        melonssagelon, pelonnguinVelonrsion, schelonmaSnapshot).elonncodelondFelonaturelons;

    elonarlybirdThriftDocumelonntBuildelonr buildelonr = nelonw elonarlybirdThriftDocumelonntBuildelonr(
        elonncodelondFelonaturelons,
        null,
        nelonw elonarlybirdFielonldConstants(),
        schelonmaSnapshot);

    buildelonr.selontAddLatLonCSF(falselon);
    buildelonr.withID(melonssagelon.gelontId());
    buildFielonldsFromUrlInfo(buildelonr, melonssagelon, pelonnguinVelonrsion, elonncodelondFelonaturelons);
    buildCardFielonlds(buildelonr, melonssagelon, pelonnguinVelonrsion);
    buildNamelondelonntityFielonlds(buildelonr, melonssagelon);
    buildelonr.withTwelonelontSignaturelon(melonssagelon.gelontTwelonelontSignaturelon(pelonnguinVelonrsion));

    buildSpacelonAdminAndTitlelonFielonlds(buildelonr, melonssagelon, pelonnguinVelonrsion);

    buildelonr.selontAddelonncodelondTwelonelontFelonaturelons(falselon);

    relonturn nelonw ThriftDocumelonntWithelonncodelondTwelonelontFelonaturelons(buildelonr.build(), elonncodelondFelonaturelons);
  }

  public static void buildNamelondelonntityFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr, TwittelonrMelonssagelon melonssagelon) {
    for (Namelondelonntity namelondelonntity : melonssagelon.gelontNamelondelonntitielons()) {
      buildelonr.withNamelondelonntity(namelondelonntity);
    }
  }

  privatelon void buildFielonldsFromUrlInfo(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      TwittelonrMelonssagelon melonssagelon,
      PelonnguinVelonrsion pelonnguinVelonrsion,
      elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons) {
    // Welon nelonelond to updatelon thelon RelonSOLVelonD_LINKS_TelonXT_FIelonLD, sincelon welon might havelon nelonw relonsolvelond URLs.
    // Uselon thelon samelon logic as in elonncodelondFelonaturelonBuildelonr.java.
    TwelonelontTelonxtFelonaturelons telonxtFelonaturelons = melonssagelon.gelontTwelonelontTelonxtFelonaturelons(pelonnguinVelonrsion);
    String relonsolvelondUrlsTelonxt = Joinelonr.on(" ").skipNulls().join(telonxtFelonaturelons.gelontRelonsolvelondUrlTokelonns());
    buildelonr.withRelonsolvelondLinksTelonxt(relonsolvelondUrlsTelonxt);

    buildURLFielonlds(buildelonr, melonssagelon, elonncodelondFelonaturelons);
    buildAnalyzelondURLFielonlds(buildelonr, melonssagelon, pelonnguinVelonrsion);
  }

  privatelon void buildAnalyzelondURLFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr, TwittelonrMelonssagelon melonssagelon, PelonnguinVelonrsion pelonnguinVelonrsion
  ) {
    TOTAL_URLS.add(melonssagelon.gelontelonxpandelondUrls().sizelon());
    if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(
        deloncidelonr,
        INDelonX_URL_DelonSCRIPTION_AND_TITLelon_DelonCIDelonR)) {
      for (ThriftelonxpandelondUrl elonxpandelondUrl : melonssagelon.gelontelonxpandelondUrls()) {
      /*
        Consumelonr Melondia URLs arelon addelond to thelon elonxpandelond URLs in
        TwelonelontelonvelonntParselonrHelonlpelonr.addMelondiaelonntitielonsToMelonssagelon. Thelonselon Twittelonr.com melondia URLs contain
        thelon twelonelont telonxt as thelon delonscription and thelon titlelon is "<Uselonr Namelon> on Twittelonr". This is
        relondundant information at belonst and mislelonading at worst. Welon will ignorelon thelonselon URLs to avoid
        polluting thelon url_delonscription and url_titlelon fielonld as welonll as saving spacelon.
       */
        if (!elonxpandelondUrl.isSelontConsumelonrMelondia() || !elonxpandelondUrl.isConsumelonrMelondia()) {
          NON_MelonDIA_URLS_ON_TWelonelonTS.increlonmelonnt();
          if (elonxpandelondUrl.isSelontDelonscription()) {
            buildTwelonelontTokelonnizelonrTokelonnizelondFielonld(buildelonr,
                elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.URL_DelonSCRIPTION_FIelonLD.gelontFielonldNamelon(),
                elonxpandelondUrl.gelontDelonscription(),
                pelonnguinVelonrsion);
          }
          if (elonxpandelondUrl.isSelontTitlelon()) {
            buildTwelonelontTokelonnizelonrTokelonnizelondFielonld(buildelonr,
                elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.URL_TITLelon_FIelonLD.gelontFielonldNamelon(),
                elonxpandelondUrl.gelontTitlelon(),
                pelonnguinVelonrsion);
          }
        } elonlselon {
          MelonDIA_URLS_ON_TWelonelonTS.increlonmelonnt();
        }
      }
    }
  }

  /**
   * Build thelon URL baselond fielonlds from a twelonelont.
   */
  public static void buildURLFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      TwittelonrMelonssagelon melonssagelon,
      elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons
  ) {
    Map<String, ThriftelonxpandelondUrl> elonxpandelondUrlMap = melonssagelon.gelontelonxpandelondUrlMap();

    for (ThriftelonxpandelondUrl elonxpandelondUrl : elonxpandelondUrlMap.valuelons()) {
      if (elonxpandelondUrl.gelontMelondiaTypelon() == MelondiaTypelons.NATIVelon_IMAGelon) {
        elonncodelondFelonaturelonBuildelonr.addPhotoUrl(melonssagelon, elonxpandelondUrl.gelontCanonicalLastHopUrl());
      }
    }

    // now add all twittelonr photos links that camelon with thelon twelonelont's payload
    Map<Long, String> photos = melonssagelon.gelontPhotoUrls();
    List<TwittelonrPhotoUrl> photoURLs = nelonw ArrayList<>();
    if (photos != null) {
      for (Map.elonntry<Long, String> elonntry : photos.elonntrySelont()) {
        TwittelonrPhotoUrl photo = nelonw TwittelonrPhotoUrl(elonntry.gelontKelony());
        String melondiaUrl = elonntry.gelontValuelon();
        if (melondiaUrl != null) {
          photo.selontMelondiaUrl(melondiaUrl);
        }
        photoURLs.add(photo);
      }
    }

    try {
      buildelonr
          .withURLs(Lists.nelonwArrayList(elonxpandelondUrlMap.valuelons()))
          .withTwimgURLs(photoURLs);
    } catch (IOelonxcelonption ioelon) {
      LOG.elonrror("URL fielonld crelonation threlonw an IOelonxcelonption", ioelon);
    }


    if (elonncodelondFelonaturelons.isFlagSelont(
        elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_OFFelonNSIVelon_FLAG)) {
      buildelonr.withOffelonnsivelonFlag();
    }
    if (elonncodelondFelonaturelons.isFlagSelont(
        elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_CONSUMelonR_VIDelonO_FLAG)) {
      buildelonr.addFiltelonrIntelonrnalFielonldTelonrm(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CONSUMelonR_VIDelonO_FILTelonR_TelonRM);
    }
    if (elonncodelondFelonaturelons.isFlagSelont(
        elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_PRO_VIDelonO_FLAG)) {
      buildelonr.addFiltelonrIntelonrnalFielonldTelonrm(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.PRO_VIDelonO_FILTelonR_TelonRM);
    }
    if (elonncodelondFelonaturelons.isFlagSelont(elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_VINelon_FLAG)) {
      buildelonr.addFiltelonrIntelonrnalFielonldTelonrm(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.VINelon_FILTelonR_TelonRM);
    }
    if (elonncodelondFelonaturelons.isFlagSelont(
        elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.HAS_PelonRISCOPelon_FLAG)) {
      buildelonr.addFiltelonrIntelonrnalFielonldTelonrm(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.PelonRISCOPelon_FILTelonR_TelonRM);
    }
  }

  /**
   * Build thelon card information insidelon ThriftIndelonxingelonvelonnt's fielonlds.
   */
  static void buildCardFielonlds(elonarlybirdThriftDocumelonntBuildelonr buildelonr,
                              TwittelonrMelonssagelon melonssagelon,
                              PelonnguinVelonrsion pelonnguinVelonrsion) {
    if (melonssagelon.hasCard()) {
      SelonarchCard2 card = buildSelonarchCardFromTwittelonrMelonssagelon(
          melonssagelon,
          TwelonelontTokelonnStrelonamSelonrializelonr.gelontTwelonelontTokelonnStrelonamSelonrializelonr(),
          pelonnguinVelonrsion);
      buildCardFelonaturelons(melonssagelon.gelontId(), buildelonr, card);
    }
  }

  privatelon static SelonarchCard2 buildSelonarchCardFromTwittelonrMelonssagelon(
      TwittelonrMelonssagelon melonssagelon,
      TokelonnStrelonamSelonrializelonr strelonamSelonrializelonr,
      PelonnguinVelonrsion pelonnguinVelonrsion) {
    SelonarchCard2 card = nelonw SelonarchCard2();
    card.selontCardNamelon(melonssagelon.gelontCardNamelon());
    if (melonssagelon.gelontCardDomain() != null) {
      card.selontCardDomain(melonssagelon.gelontCardDomain());
    }
    if (melonssagelon.gelontCardLang() != null) {
      card.selontCardLang(melonssagelon.gelontCardLang());
    }
    if (melonssagelon.gelontCardUrl() != null) {
      card.selontCardUrl(melonssagelon.gelontCardUrl());
    }

    if (melonssagelon.gelontCardTitlelon() != null && !melonssagelon.gelontCardTitlelon().iselonmpty()) {
      String normalizelondTitlelon = NormalizelonrHelonlpelonr.normalizelon(
          melonssagelon.gelontCardTitlelon(), melonssagelon.gelontLocalelon(), pelonnguinVelonrsion);
      TokelonnizelonrRelonsult relonsult = TokelonnizelonrHelonlpelonr.tokelonnizelonTwelonelont(
          normalizelondTitlelon, melonssagelon.gelontLocalelon(), pelonnguinVelonrsion);
      TokelonnizelondCharSelonquelonncelonStrelonam tokelonnSelonqStrelonam = nelonw TokelonnizelondCharSelonquelonncelonStrelonam();
      tokelonnSelonqStrelonam.relonselont(relonsult.tokelonnSelonquelonncelon);
      try {
        card.selontCardTitlelonTokelonnStrelonam(strelonamSelonrializelonr.selonrializelon(tokelonnSelonqStrelonam));
        card.selontCardTitlelonTokelonnStrelonamTelonxt(relonsult.tokelonnSelonquelonncelon.toString());
      } catch (IOelonxcelonption elon) {
        LOG.elonrror("TwittelonrTokelonnStrelonam selonrialization elonrror! Could not selonrializelon card titlelon: "
            + relonsult.tokelonnSelonquelonncelon);
        card.unselontCardTitlelonTokelonnStrelonam();
        card.unselontCardTitlelonTokelonnStrelonamTelonxt();
      }
    }
    if (melonssagelon.gelontCardDelonscription() != null && !melonssagelon.gelontCardDelonscription().iselonmpty()) {
      String normalizelondDelonsc = NormalizelonrHelonlpelonr.normalizelon(
          melonssagelon.gelontCardDelonscription(), melonssagelon.gelontLocalelon(), pelonnguinVelonrsion);
      TokelonnizelonrRelonsult relonsult = TokelonnizelonrHelonlpelonr.tokelonnizelonTwelonelont(
          normalizelondDelonsc, melonssagelon.gelontLocalelon(), pelonnguinVelonrsion);
      TokelonnizelondCharSelonquelonncelonStrelonam tokelonnSelonqStrelonam = nelonw TokelonnizelondCharSelonquelonncelonStrelonam();
      tokelonnSelonqStrelonam.relonselont(relonsult.tokelonnSelonquelonncelon);
      try {
        card.selontCardDelonscriptionTokelonnStrelonam(strelonamSelonrializelonr.selonrializelon(tokelonnSelonqStrelonam));
        card.selontCardDelonscriptionTokelonnStrelonamTelonxt(relonsult.tokelonnSelonquelonncelon.toString());
      } catch (IOelonxcelonption elon) {
        LOG.elonrror("TwittelonrTokelonnStrelonam selonrialization elonrror! Could not selonrializelon card delonscription: "
            + relonsult.tokelonnSelonquelonncelon);
        card.unselontCardDelonscriptionTokelonnStrelonam();
        card.unselontCardDelonscriptionTokelonnStrelonamTelonxt();
      }
    }

    relonturn card;
  }

  /**
   * Builds card felonaturelons.
   */
  privatelon static void buildCardFelonaturelons(
      long twelonelontId, elonarlybirdThriftDocumelonntBuildelonr buildelonr, SelonarchCard2 card) {
    if (card == null) {
      relonturn;
    }
    buildelonr
        .withTokelonnStrelonamFielonld(
            elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_TITLelon_FIelonLD.gelontFielonldNamelon(),
            card.gelontCardTitlelonTokelonnStrelonamTelonxt(),
            card.isSelontCardTitlelonTokelonnStrelonam() ? card.gelontCardTitlelonTokelonnStrelonam() : null)
        .withTokelonnStrelonamFielonld(
            elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_DelonSCRIPTION_FIelonLD.gelontFielonldNamelon(),
            card.gelontCardDelonscriptionTokelonnStrelonamTelonxt(),
            card.isSelontCardDelonscriptionTokelonnStrelonam() ? card.gelontCardDelonscriptionTokelonnStrelonam() : null)
        .withStringFielonld(
            elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_NAMelon_FIelonLD.gelontFielonldNamelon(),
            card.gelontCardNamelon())
        .withIntFielonld(
            elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_TYPelon_CSF_FIelonLD.gelontFielonldNamelon(),
            SelonarchCardTypelon.cardTypelonFromStringNamelon(card.gelontCardNamelon()).gelontBytelonValuelon());

    if (card.gelontCardLang() != null) {
      buildelonr.withStringFielonld(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_LANG.gelontFielonldNamelon(),
          card.gelontCardLang()).withIntFielonld(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_LANG_CSF.gelontFielonldNamelon(),
          ThriftLanguagelonUtil.gelontThriftLanguagelonOf(card.gelontCardLang()).gelontValuelon());
    }
    if (card.gelontCardDomain() != null) {
      buildelonr.withStringFielonld(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_DOMAIN_FIelonLD.gelontFielonldNamelon(),
          card.gelontCardDomain());
    }
    if (card.gelontCardUrl() != null) {
      NUM_TWelonelonTS_WITH_CARD_URL.increlonmelonnt();
      if (card.gelontCardUrl().startsWith("card://")) {
        String suffix = card.gelontCardUrl().relonplacelon("card://", "");
        if (StringUtils.isNumelonric(suffix)) {
          NUM_TWelonelonTS_WITH_NUMelonRIC_CARD_URI.increlonmelonnt();
          buildelonr.withLongFielonld(
              elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_URI_CSF.gelontFielonldNamelon(),
              Long.parselonLong(suffix));
          LOG.delonbug(String.format(
              "Good card URL for twelonelont %s: %s",
              twelonelontId,
              card.gelontCardUrl()));
        } elonlselon {
          NUM_TWelonelonTS_WITH_INVALID_CARD_URI.increlonmelonnt();
          LOG.delonbug(String.format(
              "Card URL starts with \"card://\" but followelond by non-numelonric for twelonelont %s: %s",
              twelonelontId,
              card.gelontCardUrl()));
        }
      }
    }
    if (isCardVidelono(card)) {
      // Add into "intelonrnal" fielonld so that this twelonelont is relonturnelond by filtelonr:videlonos.
      buildelonr.addFacelontSkipList(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.VIDelonO_LINKS_FIelonLD.gelontFielonldNamelon());
    }
  }

  /**
   * Delontelonrminelons if a card is a videlono.
   */
  privatelon static boolelonan isCardVidelono(@Nullablelon SelonarchCard2 card) {
    if (card == null) {
      relonturn falselon;
    }
    relonturn AMPLIFY_CARD_NAMelon.elonqualsIgnorelonCaselon(card.gelontCardNamelon())
        || PLAYelonR_CARD_NAMelon.elonqualsIgnorelonCaselon(card.gelontCardNamelon());
  }

  privatelon void buildSpacelonAdminAndTitlelonFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      TwittelonrMelonssagelon melonssagelon,
      PelonnguinVelonrsion pelonnguinVelonrsion) {

    buildSpacelonAdminFielonlds(buildelonr, melonssagelon.gelontSpacelonAdmins(), pelonnguinVelonrsion);

    // build thelon spacelon titlelon fielonld.
    buildTwelonelontTokelonnizelonrTokelonnizelondFielonld(
        buildelonr,
        elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.SPACelon_TITLelon_FIelonLD.gelontFielonldNamelon(),
        melonssagelon.gelontSpacelonTitlelon(),
        pelonnguinVelonrsion);
  }

  privatelon void buildSpacelonAdminFielonlds(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      Selont<TwittelonrMelonssagelonUselonr> spacelonAdmins,
      PelonnguinVelonrsion pelonnguinVelonrsion) {

    for (TwittelonrMelonssagelonUselonr spacelonAdmin : spacelonAdmins) {
      if (spacelonAdmin.gelontScrelonelonnNamelon().isPrelonselonnt()) {
        // build screlonelonn namelon (aka handlelon) fielonlds.
        String screlonelonnNamelon = spacelonAdmin.gelontScrelonelonnNamelon().gelont();
        String normalizelondScrelonelonnNamelon =
            NormalizelonrHelonlpelonr.normalizelonWithUnknownLocalelon(screlonelonnNamelon, pelonnguinVelonrsion);

        buildelonr.withStringFielonld(
            elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.SPACelon_ADMIN_FIelonLD.gelontFielonldNamelon(),
            normalizelondScrelonelonnNamelon);
        buildelonr.withWhitelonSpacelonTokelonnizelondScrelonelonnNamelonFielonld(
            elonarlybirdFielonldConstants
                .elonarlybirdFielonldConstant.TOKelonNIZelonD_SPACelon_ADMIN_FIelonLD.gelontFielonldNamelon(),
            normalizelondScrelonelonnNamelon);

        if (spacelonAdmin.gelontTokelonnizelondScrelonelonnNamelon().isPrelonselonnt()) {
          buildelonr.withCamelonlCaselonTokelonnizelondScrelonelonnNamelonFielonld(
              elonarlybirdFielonldConstants
                  .elonarlybirdFielonldConstant.CAMelonLCASelon_TOKelonNIZelonD_SPACelon_ADMIN_FIelonLD.gelontFielonldNamelon(),
              screlonelonnNamelon,
              normalizelondScrelonelonnNamelon,
              spacelonAdmin.gelontTokelonnizelondScrelonelonnNamelon().gelont());
        }
      }

      if (spacelonAdmin.gelontDisplayNamelon().isPrelonselonnt()) {
        buildTwelonelontTokelonnizelonrTokelonnizelondFielonld(
            buildelonr,
            elonarlybirdFielonldConstants
                .elonarlybirdFielonldConstant.TOKelonNIZelonD_SPACelon_ADMIN_DISPLAY_NAMelon_FIelonLD.gelontFielonldNamelon(),
            spacelonAdmin.gelontDisplayNamelon().gelont(),
            pelonnguinVelonrsion);
      }
    }
  }

  privatelon void buildTwelonelontTokelonnizelonrTokelonnizelondFielonld(
      elonarlybirdThriftDocumelonntBuildelonr buildelonr,
      String fielonldNamelon,
      String telonxt,
      PelonnguinVelonrsion pelonnguinVelonrsion) {

    if (StringUtils.isNotelonmpty(telonxt)) {
      Localelon localelon = LanguagelonIdelonntifielonrHelonlpelonr
          .idelonntifyLanguagelon(telonxt);
      String normalizelondTelonxt = NormalizelonrHelonlpelonr.normalizelon(
          telonxt, localelon, pelonnguinVelonrsion);
      TokelonnizelonrRelonsult relonsult = TokelonnizelonrHelonlpelonr
          .tokelonnizelonTwelonelont(normalizelondTelonxt, localelon, pelonnguinVelonrsion);
      TokelonnizelondCharSelonquelonncelonStrelonam tokelonnSelonqStrelonam = nelonw TokelonnizelondCharSelonquelonncelonStrelonam();
      tokelonnSelonqStrelonam.relonselont(relonsult.tokelonnSelonquelonncelon);
      TokelonnStrelonamSelonrializelonr strelonamSelonrializelonr =
          TwelonelontTokelonnStrelonamSelonrializelonr.gelontTwelonelontTokelonnStrelonamSelonrializelonr();
      try {
        buildelonr.withTokelonnStrelonamFielonld(
            fielonldNamelon,
            relonsult.tokelonnSelonquelonncelon.toString(),
            strelonamSelonrializelonr.selonrializelon(tokelonnSelonqStrelonam));
      } catch (IOelonxcelonption elon) {
        LOG.elonrror("TwittelonrTokelonnStrelonam selonrialization elonrror! Could not selonrializelon: " + telonxt);
      }
    }
  }
}
