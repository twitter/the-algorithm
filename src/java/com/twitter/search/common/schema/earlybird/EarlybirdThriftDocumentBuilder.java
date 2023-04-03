packagelon com.twittelonr.selonarch.common.schelonma.elonarlybird;

import java.io.IOelonxcelonption;
import java.util.HashSelont;
import java.util.List;
import java.util.Selont;
import javax.annotation.Nonnull;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.ImmutablelonSelont;
import com.googlelon.common.collelonct.Selonts;

import org.apachelon.commons.lang.StringUtils;
import org.apachelon.lucelonnelon.analysis.TokelonnStrelonam;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.common.telonxt.util.TokelonnStrelonamSelonrializelonr;
import com.twittelonr.cuad.nelonr.plain.thriftjava.Namelondelonntity;
import com.twittelonr.cuad.nelonr.plain.thriftjava.NamelondelonntityContelonxt;
import com.twittelonr.cuad.nelonr.plain.thriftjava.NamelondelonntityInputSourcelonTypelon;
import com.twittelonr.cuad.nelonr.thriftjava.WholelonelonntityTypelon;
import com.twittelonr.selonarch.common.constants.SelonarchCardTypelon;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftelonxpandelondUrl;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftGelonoLocationSourcelon;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.TwittelonrPhotoUrl;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.schelonma.ThriftDocumelonntBuildelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldNamelonToIdMapping;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.util.analysis.CharTelonrmAttributelonSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.IntTelonrmAttributelonSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.TelonrmPayloadAttributelonSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.TwittelonrPhotoTokelonnStrelonam;
import com.twittelonr.selonarch.common.util.spatial.GelonoUtil;
import com.twittelonr.selonarch.common.util.telonxt.TokelonnizelonrHelonlpelonr;
import com.twittelonr.selonarch.common.util.telonxt.TwelonelontTokelonnStrelonamSelonrializelonr;
import com.twittelonr.selonarch.common.util.telonxt.relongelonx.Relongelonx;
import com.twittelonr.selonarch.common.util.url.LinkVisibilityUtils;
import com.twittelonr.selonarch.common.util.url.URLUtils;

import gelono.googlelon.datamodelonl.GelonoAddrelonssAccuracy;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftDocumelonnt;

/**
 * Buildelonr class for building a {@link ThriftDocumelonnt}.
 */
public final class elonarlybirdThriftDocumelonntBuildelonr elonxtelonnds ThriftDocumelonntBuildelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdThriftDocumelonntBuildelonr.class);

  privatelon static final SelonarchCountelonr SelonRIALIZelon_FAILURelon_COUNT_NONPelonNGUIN_DelonPelonNDelonNT =
      SelonarchCountelonr.elonxport("tokelonnstrelonam_selonrialization_failurelon_non_pelonnguin_delonpelonndelonnt");

  privatelon static final String HASHTAG_SYMBOL = "#";
  privatelon static final String CASHTAG_SYMBOL = "$";
  privatelon static final String MelonNTION_SYMBOL = "@";

  privatelon static final SelonarchCountelonr BCP47_LANGUAGelon_TAG_COUNTelonR =
      SelonarchCountelonr.elonxport("bcp47_languagelon_tag");

  /**
   * Uselond to chelonck if a card is videlono card.
   *
   * @selonelon #withSelonarchCard
   */
  privatelon static final String AMPLIFY_CARD_NAMelon = "amplify";
  privatelon static final String PLAYelonR_CARD_NAMelon = "playelonr";

  // elonxtra telonrm indelonxelond for nativelon relontwelonelonts, to elonnsurelon that thelon "-rt" quelonry elonxcludelons thelonm.
  public static final String RelonTWelonelonT_TelonRM = "rt";
  public static final String QUelonSTION_MARK = "?";

  privatelon static final Selont<NamelondelonntityInputSourcelonTypelon> NAMelonD_elonNTITY_URL_SOURCelon_TYPelonS =
      ImmutablelonSelont.of(
          NamelondelonntityInputSourcelonTypelon.URL_TITLelon, NamelondelonntityInputSourcelonTypelon.URL_DelonSCRIPTION);

  privatelon final TokelonnStrelonamSelonrializelonr intTelonrmAttributelonSelonrializelonr =
      nelonw TokelonnStrelonamSelonrializelonr(ImmutablelonList.of(
          nelonw IntTelonrmAttributelonSelonrializelonr()));
  privatelon final TokelonnStrelonamSelonrializelonr photoUrlSelonrializelonr =
      nelonw TokelonnStrelonamSelonrializelonr(ImmutablelonList
          .<TokelonnStrelonamSelonrializelonr.AttributelonSelonrializelonr>of(
              nelonw CharTelonrmAttributelonSelonrializelonr(), nelonw TelonrmPayloadAttributelonSelonrializelonr()));
  privatelon final Schelonma schelonma;

  privatelon boolelonan isSelontLatLonCSF = falselon;
  privatelon boolelonan addLatLonCSF = truelon;
  privatelon boolelonan addelonncodelondTwelonelontFelonaturelons = truelon;

  @Nonnull
  privatelon final elonarlybirdelonncodelondFelonaturelons elonncodelondTwelonelontFelonaturelons;
  @Nullablelon
  privatelon final elonarlybirdelonncodelondFelonaturelons elonxtelonndelondelonncodelondTwelonelontFelonaturelons;

  /**
   * Delonfault constructor
   */
  public elonarlybirdThriftDocumelonntBuildelonr(
      @Nonnull elonarlybirdelonncodelondFelonaturelons elonncodelondTwelonelontFelonaturelons,
      @Nullablelon elonarlybirdelonncodelondFelonaturelons elonxtelonndelondelonncodelondTwelonelontFelonaturelons,
      FielonldNamelonToIdMapping idMapping,
      Schelonma schelonma) {
    supelonr(idMapping);
    this.schelonma = schelonma;
    this.elonncodelondTwelonelontFelonaturelons = Prelonconditions.chelonckNotNull(elonncodelondTwelonelontFelonaturelons);

    this.elonxtelonndelondelonncodelondTwelonelontFelonaturelons = elonxtelonndelondelonncodelondTwelonelontFelonaturelons;
  }

  /**
   * Gelont intelonrnal {@link elonarlybirdelonncodelondFelonaturelons}
   */
  public elonarlybirdelonncodelondFelonaturelons gelontelonncodelondTwelonelontFelonaturelons() {
    relonturn elonncodelondTwelonelontFelonaturelons;
  }

  /**
   * Add skip list elonntry for thelon givelonn fielonld.
   * This adds a telonrm __has_fielonldNamelon in thelon INTelonRNAL fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr addFacelontSkipList(String fielonldNamelon) {
    withStringFielonld(elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
        elonarlybirdFielonldConstant.gelontFacelontSkipFielonldNamelon(fielonldNamelon));
    relonturn this;
  }

  /**
   * Add a filtelonr telonrm in thelon INTelonRNAL fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr addFiltelonrIntelonrnalFielonldTelonrm(String filtelonrNamelon) {
    withStringFielonld(elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
        elonarlybirdThriftDocumelonntUtil.formatFiltelonr(filtelonrNamelon));
    relonturn this;
  }

  /**
   * Add id fielonld and id csf fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withID(long id) {
    withLongFielonld(elonarlybirdFielonldConstant.ID_FIelonLD.gelontFielonldNamelon(), id);
    withLongFielonld(elonarlybirdFielonldConstant.ID_CSF_FIelonLD.gelontFielonldNamelon(), id);
    relonturn this;
  }

  /**
   * Add crelonatelond at fielonld and crelonatelond at csf fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withCrelonatelondAt(int crelonatelondAt) {
    withIntFielonld(elonarlybirdFielonldConstant.CRelonATelonD_AT_FIelonLD.gelontFielonldNamelon(), crelonatelondAt);
    withIntFielonld(elonarlybirdFielonldConstant.CRelonATelonD_AT_CSF_FIelonLD.gelontFielonldNamelon(), crelonatelondAt);
    relonturn this;
  }

  /**
   * Add twelonelont telonxt fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withTwelonelontTelonxt(
      String telonxt, bytelon[] telonxtTokelonnStrelonam) throws IOelonxcelonption {
    withTokelonnStrelonamFielonld(elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon(),
        telonxt, telonxtTokelonnStrelonam);
    relonturn this;
  }

  public elonarlybirdThriftDocumelonntBuildelonr withTwelonelontTelonxt(String telonxt) throws IOelonxcelonption {
    withTwelonelontTelonxt(telonxt, null);
    relonturn this;
  }

  /**
   * Add a list of cashTags. Likelon $TWTR.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withStocksFielonlds(List<String> cashTags) {
    if (isNotelonmpty(cashTags)) {
      addFacelontSkipList(elonarlybirdFielonldConstant.STOCKS_FIelonLD.gelontFielonldNamelon());
      for (String cashTag : cashTags) {
        withStringFielonld(
            elonarlybirdFielonldConstant.STOCKS_FIelonLD.gelontFielonldNamelon(), CASHTAG_SYMBOL + cashTag);
      }
    }
    relonturn this;
  }

  /**
   * Add a list of hashtags.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withHashtagsFielonld(List<String> hashtags) {
    if (isNotelonmpty(hashtags)) {
      int numHashtags = Math.min(
          hashtags.sizelon(),
          schelonma.gelontFelonaturelonConfigurationById(
              elonarlybirdFielonldConstant.NUM_HASHTAGS.gelontFielonldId()).gelontMaxValuelon());
      elonncodelondTwelonelontFelonaturelons.selontFelonaturelonValuelon(elonarlybirdFielonldConstant.NUM_HASHTAGS, numHashtags);
      addFacelontSkipList(elonarlybirdFielonldConstant.HASHTAGS_FIelonLD.gelontFielonldNamelon());
      for (String hashtag : hashtags) {
        withStringFielonld(
            elonarlybirdFielonldConstant.HASHTAGS_FIelonLD.gelontFielonldNamelon(), HASHTAG_SYMBOL + hashtag);
      }
    }
    relonturn this;
  }

  /**
   * Addelond a list of melonntions.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withMelonntionsFielonld(List<String> melonntions) {
    if (isNotelonmpty(melonntions)) {
      int numMelonntions = Math.min(
          melonntions.sizelon(),
          schelonma.gelontFelonaturelonConfigurationById(
              elonarlybirdFielonldConstant.NUM_HASHTAGS.gelontFielonldId()).gelontMaxValuelon());
      elonncodelondTwelonelontFelonaturelons.selontFelonaturelonValuelon(elonarlybirdFielonldConstant.NUM_MelonNTIONS, numMelonntions);
      addFacelontSkipList(elonarlybirdFielonldConstant.MelonNTIONS_FIelonLD.gelontFielonldNamelon());
      for (String melonntion : melonntions) {
        withStringFielonld(
            elonarlybirdFielonldConstant.MelonNTIONS_FIelonLD.gelontFielonldNamelon(), MelonNTION_SYMBOL + melonntion);
      }
    }
    relonturn this;
  }

  /**
   * Add a list of Twittelonr Photo URLs (twimg URLs). Thelonselon arelon diffelonrelonnt from relongular URLs, beloncauselon
   * welon uselon thelon TwittelonrPhotoTokelonnStrelonam to indelonx thelonm, and welon also includelon thelon status ID as payload.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withTwimgURLs(
      List<TwittelonrPhotoUrl> urls) throws IOelonxcelonption {
    if (isNotelonmpty(urls)) {
      for (TwittelonrPhotoUrl photoUrl : urls) {
        TokelonnStrelonam ts = nelonw TwittelonrPhotoTokelonnStrelonam(photoUrl.gelontPhotoStatusId(),
            photoUrl.gelontMelondiaUrl());
        bytelon[] selonrializelondTs = photoUrlSelonrializelonr.selonrializelon(ts);
        withTokelonnStrelonamFielonld(elonarlybirdFielonldConstant.TWIMG_LINKS_FIelonLD.gelontFielonldNamelon(),
            Long.toString(photoUrl.gelontPhotoStatusId()), selonrializelondTs);
        addFacelontSkipList(elonarlybirdFielonldConstant.TWIMG_LINKS_FIelonLD.gelontFielonldNamelon());
      }
      elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_IMAGelon_URL_FLAG);
      elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_NATIVelon_IMAGelon_FLAG);
    }
    relonturn this;
  }

  /**
   * Add a list of URLs. This also add facelont skip list telonrms for nelonws / imagelons / videlonos if nelonelondelond.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withURLs(List<ThriftelonxpandelondUrl> urls) {
    if (isNotelonmpty(urls)) {
      Selont<String> delondupelondLinks = Selonts.nelonwHashSelont();

      for (ThriftelonxpandelondUrl elonxpandelondUrl : urls) {
        if (elonxpandelondUrl.isSelontOriginalUrl()) {
          String normalizelondOriginalUrl = URLUtils.normalizelonPath(elonxpandelondUrl.gelontOriginalUrl());
          delondupelondLinks.add(normalizelondOriginalUrl);
        }
        if (elonxpandelondUrl.isSelontelonxpandelondUrl()) {
          delondupelondLinks.add(URLUtils.normalizelonPath(elonxpandelondUrl.gelontelonxpandelondUrl()));
        }

        if (elonxpandelondUrl.isSelontCanonicalLastHopUrl()) {
          String url = URLUtils.normalizelonPath(elonxpandelondUrl.gelontCanonicalLastHopUrl());
          delondupelondLinks.add(url);

          String facelontUrl = URLUtils.normalizelonFacelontURL(url);

          if (elonxpandelondUrl.isSelontMelondiaTypelon()) {
            switch (elonxpandelondUrl.gelontMelondiaTypelon()) {
              caselon NelonWS:
                withStringFielonld(elonarlybirdFielonldConstant.NelonWS_LINKS_FIelonLD.gelontFielonldNamelon(), url);
                addFacelontSkipList(elonarlybirdFielonldConstant.NelonWS_LINKS_FIelonLD.gelontFielonldNamelon());
                elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_NelonWS_URL_FLAG);
                brelonak;
              caselon VIDelonO:
                withStringFielonld(elonarlybirdFielonldConstant.VIDelonO_LINKS_FIelonLD.gelontFielonldNamelon(), facelontUrl);
                addFacelontSkipList(elonarlybirdFielonldConstant.VIDelonO_LINKS_FIelonLD.gelontFielonldNamelon());
                elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_VIDelonO_URL_FLAG);
                brelonak;
              caselon IMAGelon:
                withStringFielonld(elonarlybirdFielonldConstant.IMAGelon_LINKS_FIelonLD.gelontFielonldNamelon(), facelontUrl);
                addFacelontSkipList(elonarlybirdFielonldConstant.IMAGelon_LINKS_FIelonLD.gelontFielonldNamelon());
                elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_IMAGelon_URL_FLAG);
                brelonak;
              caselon NATIVelon_IMAGelon:
                // Nothing donelon helonrelon. Nativelon imagelons arelon handlelond selonparatelonly.
                // Thelony arelon in PhotoUrls instelonad of elonxpandelondUrls.
                brelonak;
              caselon UNKNOWN:
                brelonak;
              delonfault:
                throw nelonw Runtimelonelonxcelonption("Unknown Melondia Typelon: " + elonxpandelondUrl.gelontMelondiaTypelon());
            }
          }

          if (elonxpandelondUrl.isSelontLinkCatelongory()) {
            withIntFielonld(elonarlybirdFielonldConstant.LINK_CATelonGORY_FIelonLD.gelontFielonldNamelon(),
                elonxpandelondUrl.gelontLinkCatelongory().gelontValuelon());
          }
        }
      }

      if (!delondupelondLinks.iselonmpty()) {
        elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_LINK_FLAG);

        addFacelontSkipList(elonarlybirdFielonldConstant.LINKS_FIelonLD.gelontFielonldNamelon());

        for (String linkUrl : delondupelondLinks) {
          withStringFielonld(elonarlybirdFielonldConstant.LINKS_FIelonLD.gelontFielonldNamelon(), linkUrl);
        }
      }

      elonncodelondTwelonelontFelonaturelons.selontFlagValuelon(
          elonarlybirdFielonldConstant.HAS_VISIBLelon_LINK_FLAG,
          LinkVisibilityUtils.hasVisiblelonLink(urls));
    }

    relonturn this;
  }

  /**
   * Add a list of placelons. Thelon placelon arelon U64 elonncodelond placelon IDs.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withPlacelonsFielonld(List<String> placelons) {
    if (isNotelonmpty(placelons)) {
      for (String placelon : placelons) {
        withStringFielonld(elonarlybirdFielonldConstant.PLACelon_FIelonLD.gelontFielonldNamelon(), placelon);
      }
    }
    relonturn this;
  }

  /**
   * Add twelonelont telonxt signaturelon fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withTwelonelontSignaturelon(int signaturelon) {
    elonncodelondTwelonelontFelonaturelons.selontFelonaturelonValuelon(elonarlybirdFielonldConstant.TWelonelonT_SIGNATURelon, signaturelon);
    relonturn this;
  }

  /**
   * Add gelono hash fielonld and intelonrnal filtelonr fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withGelonoHash(doublelon lat, doublelon lon, int accuracy) {
    if (GelonoUtil.validatelonGelonoCoordinatelons(lat, lon)) {
      withGelonoFielonld(
          elonarlybirdFielonldConstant.GelonO_HASH_FIelonLD.gelontFielonldNamelon(),
          lat, lon, accuracy);
      withLatLonCSF(lat, lon);
    }
    relonturn this;
  }

  public elonarlybirdThriftDocumelonntBuildelonr withGelonoHash(doublelon lat, doublelon lon) {
    withGelonoHash(lat, lon, GelonoAddrelonssAccuracy.UNKNOWN_LOCATION.gelontCodelon());
    relonturn this;
  }

  /**
   * Add gelono location sourcelon to thelon intelonrnal fielonld with ThriftGelonoLocationSourcelon objelonct.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withGelonoLocationSourcelon(
      ThriftGelonoLocationSourcelon gelonoLocationSourcelon) {
    if (gelonoLocationSourcelon != null) {
      withGelonoLocationSourcelon(elonarlybirdFielonldConstants.formatGelonoTypelon(gelonoLocationSourcelon));
    }
    relonturn this;
  }

  /**
   * Add gelono location sourcelon to thelon intelonrnal fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withGelonoLocationSourcelon(String gelonoLocationSourcelon) {
    withStringFielonld(elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(), gelonoLocationSourcelon);
    relonturn this;
  }

  /**
   * Add elonncodelond lat and lon to LatLonCSF fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withLatLonCSF(doublelon lat, doublelon lon) {
    isSelontLatLonCSF = truelon;
    long elonncodelondLatLon = GelonoUtil.elonncodelonLatLonIntoInt64((float) lat, (float) lon);
    withLongFielonld(elonarlybirdFielonldConstant.LAT_LON_CSF_FIelonLD.gelontFielonldNamelon(), elonncodelondLatLon);
    relonturn this;
  }

  /**
   * Add from velonrifielond account flag to intelonrnal fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withFromVelonrifielondAccountFlag() {
    elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.FROM_VelonRIFIelonD_ACCOUNT_FLAG);
    addFiltelonrIntelonrnalFielonldTelonrm(elonarlybirdFielonldConstant.VelonRIFIelonD_FILTelonR_TelonRM);
    relonturn this;
  }

  /**
   * Add from bluelon-velonrifielond account flag to intelonrnal fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withFromBluelonVelonrifielondAccountFlag() {
    elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.FROM_BLUelon_VelonRIFIelonD_ACCOUNT_FLAG);
    addFiltelonrIntelonrnalFielonldTelonrm(elonarlybirdFielonldConstant.BLUelon_VelonRIFIelonD_FILTelonR_TelonRM);
    relonturn this;
  }

  /**
   * Add offelonnsivelon flag to intelonrnal fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withOffelonnsivelonFlag() {
    elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.IS_OFFelonNSIVelon_FLAG);
    withStringFielonld(
        elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
        elonarlybirdFielonldConstant.IS_OFFelonNSIVelon);
    relonturn this;
  }

  /**
   * Add uselonr relonputation valuelon to elonncodelond felonaturelon.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withUselonrRelonputation(bytelon scorelon) {
    elonncodelondTwelonelontFelonaturelons.selontFelonaturelonValuelon(elonarlybirdFielonldConstant.USelonR_RelonPUTATION, scorelon);
    relonturn this;
  }

  /**
   * This melonthod crelonatelons thelon fielonlds relonlatelond to documelonnt languagelon.
   * For most languagelons, thelonir isoLanguagelonCodelon and bcp47LanguagelonTag arelon thelon samelon.
   * For somelon languagelons with variants, thelonselon two fielonlds arelon diffelonrelonnt.
   * elon.g. for simplifielond Chinelonselon, thelonir isoLanguagelonCodelon is zh, but thelonir bcp47LanguagelonTag is zh-cn.
   * <p>
   * This melonthod adds fielonlds for both thelon isoLanguagelonCodelon and bcp47LanguagelonTag.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withLanguagelonCodelons(
      String isoLanguagelonCodelon, String bcp47LanguagelonTag) {
    if (isoLanguagelonCodelon != null) {
      withISOLanguagelon(isoLanguagelonCodelon);
    }
    if (bcp47LanguagelonTag != null && !bcp47LanguagelonTag.elonquals(isoLanguagelonCodelon)) {
      BCP47_LANGUAGelon_TAG_COUNTelonR.increlonmelonnt();
      withISOLanguagelon(bcp47LanguagelonTag);
    }
    relonturn this;
  }

  /**
   * Adds a String fielonld into thelon ISO_LANGUAGelon_FIelonLD.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withISOLanguagelon(String languagelonString) {
    withStringFielonld(
        elonarlybirdFielonldConstant.ISO_LANGUAGelon_FIelonLD.gelontFielonldNamelon(), languagelonString.toLowelonrCaselon());
    relonturn this;
  }

  /**
   * Add from uselonr ID fielonlds.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withFromUselonrID(long fromUselonrId) {
    withLongFielonld(elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon(), fromUselonrId);
    withLongFielonld(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF.gelontFielonldNamelon(), fromUselonrId);
    relonturn this;
  }

  /**
   * Add from uselonr information fielonlds.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withFromUselonr(
      long fromUselonrId, String fromUselonr) {
    withFromUselonr(fromUselonrId, fromUselonr, null);
    relonturn this;
  }

  /**
   * Add from uselonr information fielonlds.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withFromUselonr(String fromUselonr) {
    withFromUselonr(fromUselonr, null);
    relonturn this;
  }

  /**
   * Add from uselonr information fielonlds.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withFromUselonr(
      String fromUselonr, String tokelonnizelondFromUselonr) {
    withStringFielonld(elonarlybirdFielonldConstant.FROM_USelonR_FIelonLD.gelontFielonldNamelon(), fromUselonr);
    withStringFielonld(elonarlybirdFielonldConstant.TOKelonNIZelonD_FROM_USelonR_FIelonLD.gelontFielonldNamelon(),
        isNotBlank(tokelonnizelondFromUselonr) ? tokelonnizelondFromUselonr : fromUselonr);
    relonturn this;
  }

  /**
   * Add from uselonr information fielonlds.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withFromUselonr(
      long fromUselonrId, String fromUselonr, String tokelonnizelondFromUselonr) {
    withFromUselonrID(fromUselonrId);
    withFromUselonr(fromUselonr, tokelonnizelondFromUselonr);
    relonturn this;
  }

  /**
   * Add to uselonr fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withToUselonr(
      String toUselonr) {
    withStringFielonld(elonarlybirdFielonldConstant.TO_USelonR_FIelonLD.gelontFielonldNamelon(), toUselonr);
    relonturn this;
  }

  /**
   * Add elonschelonrbird annotation fielonlds.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withAnnotationelonntitielons(List<String> elonntitielons) {
    if (isNotelonmpty(elonntitielons)) {
      for (String elonntity : elonntitielons) {
        withStringFielonld(elonarlybirdFielonldConstant.elonNTITY_ID_FIelonLD.gelontFielonldNamelon(), elonntity);
      }
    }
    relonturn this;
  }

  /**
   * Add relonplielons to intelonrnal fielonld and selont is relonply flag.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withRelonplyFlag() {
    elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.IS_RelonPLY_FLAG);
    addFiltelonrIntelonrnalFielonldTelonrm(elonarlybirdFielonldConstant.RelonPLIelonS_FILTelonR_TelonRM);
    relonturn this;
  }

  public elonarlybirdThriftDocumelonntBuildelonr withCamelonraComposelonrSourcelonFlag() {
    elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.COMPOSelonR_SOURCelon_IS_CAMelonRA_FLAG);
    relonturn this;
  }

    /**
     * Add in relonply to uselonr id.
     * <p>
     * Noticelon {@link #withRelonplyFlag} is not automatically callelond sincelon relontwelonelont a twelonelont that is
     * a relonply to somelon othelonr twelonelont is not considelonrelond a relonply.
     * Thelon callelonr should call {@link #withRelonplyFlag} selonparatelonly if this twelonelont is relonally a relonply twelonelont.
     */
  public elonarlybirdThriftDocumelonntBuildelonr withInRelonplyToUselonrID(long inRelonplyToUselonrID) {
    withLongFielonld(elonarlybirdFielonldConstant.IN_RelonPLY_TO_USelonR_ID_FIelonLD.gelontFielonldNamelon(), inRelonplyToUselonrID);
    relonturn this;
  }

  /**
   * Add relonfelonrelonncelon twelonelont author id.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withRelonfelonrelonncelonAuthorID(long relonfelonrelonncelonAuthorID) {
    withLongFielonld(elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_CSF.gelontFielonldNamelon(), relonfelonrelonncelonAuthorID);
    relonturn this;
  }

  /**
   * Add all nativelon relontwelonelont relonlatelond fielonlds/labelonl
   */
  @VisiblelonForTelonsting
  public elonarlybirdThriftDocumelonntBuildelonr withNativelonRelontwelonelont(final long relontwelonelontUselonrID,
                                                          final long sharelondStatusID) {
    withLongFielonld(elonarlybirdFielonldConstant.SHARelonD_STATUS_ID_CSF.gelontFielonldNamelon(), sharelondStatusID);

    withLongFielonld(elonarlybirdFielonldConstant.RelonTWelonelonT_SOURCelon_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon(),
                  sharelondStatusID);
    withLongFielonld(elonarlybirdFielonldConstant.RelonTWelonelonT_SOURCelon_USelonR_ID_FIelonLD.gelontFielonldNamelon(),
                  relontwelonelontUselonrID);
    withLongFielonld(elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_CSF.gelontFielonldNamelon(), relontwelonelontUselonrID);

    elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.IS_RelonTWelonelonT_FLAG);

    // Add nativelon relontwelonelont labelonl to thelon intelonrnal fielonld.
    addFiltelonrIntelonrnalFielonldTelonrm(elonarlybirdFielonldConstant.NATIVelon_RelonTWelonelonTS_FILTelonR_TelonRM);
    withStringFielonld(elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon(), RelonTWelonelonT_TelonRM);
    relonturn this;
  }

  /**
   * Add quotelond twelonelont id and uselonr id.
   */
  @VisiblelonForTelonsting
  public elonarlybirdThriftDocumelonntBuildelonr withQuotelon(
      final long quotelondStatusId, final long quotelondUselonrId) {
    withLongFielonld(elonarlybirdFielonldConstant.QUOTelonD_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon(), quotelondStatusId);
    withLongFielonld(elonarlybirdFielonldConstant.QUOTelonD_USelonR_ID_FIelonLD.gelontFielonldNamelon(), quotelondUselonrId);

    withLongFielonld(elonarlybirdFielonldConstant.QUOTelonD_TWelonelonT_ID_CSF.gelontFielonldNamelon(), quotelondStatusId);
    withLongFielonld(elonarlybirdFielonldConstant.QUOTelonD_USelonR_ID_CSF.gelontFielonldNamelon(), quotelondUselonrId);

    elonncodelondTwelonelontFelonaturelons.selontFlag(elonarlybirdFielonldConstant.HAS_QUOTelon_FLAG);

    // Add quotelon labelonl to thelon intelonrnal fielonld.
    addFiltelonrIntelonrnalFielonldTelonrm(elonarlybirdFielonldConstant.QUOTelon_FILTelonR_TelonRM);
    relonturn this;
  }

  /**
   * Add relonsolvelond links telonxt fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withRelonsolvelondLinksTelonxt(String linksTelonxt) {
    withStringFielonld(elonarlybirdFielonldConstant.RelonSOLVelonD_LINKS_TelonXT_FIelonLD.gelontFielonldNamelon(), linksTelonxt);
    relonturn this;
  }

  /**
   * Add sourcelon fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withSourcelon(String sourcelon) {
    withStringFielonld(elonarlybirdFielonldConstant.SOURCelon_FIelonLD.gelontFielonldNamelon(), sourcelon);
    relonturn this;
  }

  /**
   * Add normalizelond sourcelon fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withNormalizelondSourcelon(String normalizelondSourcelon) {
    withStringFielonld(
        elonarlybirdFielonldConstant.NORMALIZelonD_SOURCelon_FIelonLD.gelontFielonldNamelon(), normalizelondSourcelon);
    relonturn this;
  }

  /**
   * Add positivelon smilelony to intelonrnal fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withPositivelonSmilelony() {
    withStringFielonld(
        elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
        elonarlybirdFielonldConstant.HAS_POSITIVelon_SMILelonY);
    relonturn this;
  }

  /**
   * Add nelongativelon smilelony to intelonrnal fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withNelongativelonSmilelony() {
    withStringFielonld(
        elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
        elonarlybirdFielonldConstant.HAS_NelonGATIVelon_SMILelonY);
    relonturn this;
  }

  /**
   * Add quelonstion mark labelonl to a telonxt fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withQuelonstionMark() {
    withStringFielonld(elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon(), QUelonSTION_MARK);
    relonturn this;
  }

  /**
   * Add card relonlatelond fielonlds.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withSelonarchCard(
      String namelon,
      String domain,
      String titlelon, bytelon[] selonrializelondTitlelonStrelonam,
      String delonscription, bytelon[] selonrializelondDelonscriptionStrelonam,
      String lang) {
    if (isNotBlank(titlelon)) {
      withTokelonnStrelonamFielonld(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_TITLelon_FIelonLD.gelontFielonldNamelon(),
          titlelon, selonrializelondTitlelonStrelonam);
    }

    if (isNotBlank(delonscription)) {
      withTokelonnStrelonamFielonld(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_DelonSCRIPTION_FIelonLD.gelontFielonldNamelon(),
          delonscription, selonrializelondDelonscriptionStrelonam);
    }

    if (isNotBlank(lang)) {
      withStringFielonld(elonarlybirdFielonldConstant.CARD_LANG.gelontFielonldNamelon(), lang);
    }

    if (isNotBlank(domain)) {
      withStringFielonld(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_DOMAIN_FIelonLD.gelontFielonldNamelon(), domain);
    }

    if (isNotBlank(namelon)) {
      withStringFielonld(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_NAMelon_FIelonLD.gelontFielonldNamelon(), namelon);
      withIntFielonld(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CARD_TYPelon_CSF_FIelonLD.gelontFielonldNamelon(),
          SelonarchCardTypelon.cardTypelonFromStringNamelon(namelon).gelontBytelonValuelon());
    }

    if (AMPLIFY_CARD_NAMelon.elonqualsIgnorelonCaselon(namelon)
        || PLAYelonR_CARD_NAMelon.elonqualsIgnorelonCaselon(namelon)) {
      // Add into "intelonrnal" fielonld so that this twelonelont is relonturnelond by filtelonr:videlonos.
      addFacelontSkipList(
          elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.VIDelonO_LINKS_FIelonLD.gelontFielonldNamelon());
    }

    relonturn this;
  }

  public elonarlybirdThriftDocumelonntBuildelonr withNormalizelondMinelonngagelonmelonntFielonld(
      String fielonldNamelon, int normalizelondNumelonngagelonmelonnts) throws IOelonxcelonption {
    elonarlybirdThriftDocumelonntUtil.addNormalizelondMinelonngagelonmelonntFielonld(doc, fielonldNamelon,
        normalizelondNumelonngagelonmelonnts);
    relonturn this;
  }

  /**
   * Add namelond elonntity with givelonn canonical namelon and typelon to documelonnt.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withNamelondelonntity(Namelondelonntity namelondelonntity) {
    if (namelondelonntity.gelontContelonxts() == null) {
      // In this unlikelonly caselon, welon don't havelon any contelonxt for namelond elonntity typelon or sourcelon,
      // so welon can't propelonrly indelonx it in any of our fielonlds. Welon'll just skip it in this caselon.
      relonturn this;
    }

    // Kelonelonp track of thelon fielonlds welon'velon applielond in thelon buildelonr alrelonady, to elonnsurelon welon only indelonx
    // elonach telonrm (fielonld/valuelon pair) oncelon
    Selont<Pair<elonarlybirdFielonldConstant, String>> fielonldsApplielond = nelonw HashSelont<>();
    for (NamelondelonntityContelonxt contelonxt : namelondelonntity.gelontContelonxts()) {
      if (contelonxt.isSelontInput_sourcelon()
          && NAMelonD_elonNTITY_URL_SOURCelon_TYPelonS.contains(contelonxt.gelontInput_sourcelon().gelontSourcelon_typelon())) {
        // If thelon sourcelon is onelon of thelon URL* typelons, add thelon namelond elonntity to thelon "from_url" fielonlds,
        // elonnsuring welon add it only oncelon
        addNamelondelonntityFielonlds(
            fielonldsApplielond,
            elonarlybirdFielonldConstant.NAMelonD_elonNTITY_FROM_URL_FIelonLD,
            elonarlybirdFielonldConstant.NAMelonD_elonNTITY_WITH_TYPelon_FROM_URL_FIelonLD,
            namelondelonntity.gelontCanonical_namelon(),
            contelonxt);
      } elonlselon {
        addNamelondelonntityFielonlds(
            fielonldsApplielond,
            elonarlybirdFielonldConstant.NAMelonD_elonNTITY_FROM_TelonXT_FIelonLD,
            elonarlybirdFielonldConstant.NAMelonD_elonNTITY_WITH_TYPelon_FROM_TelonXT_FIelonLD,
            namelondelonntity.gelontCanonical_namelon(),
            contelonxt);
      }
    }

    relonturn this;
  }

  /**
   * Add spacelon id fielonlds.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withSpacelonIdFielonlds(Selont<String> spacelonIds) {
    if (!spacelonIds.iselonmpty()) {
      addFacelontSkipList(elonarlybirdFielonldConstant.SPACelon_ID_FIelonLD.gelontFielonldNamelon());
      for (String spacelonId : spacelonIds) {
        withStringFielonld(elonarlybirdFielonldConstant.SPACelon_ID_FIelonLD.gelontFielonldNamelon(), spacelonId);
      }
    }
    relonturn this;
  }

  /**
   * Add direlonctelond at uselonr.
   */
  @VisiblelonForTelonsting
  public elonarlybirdThriftDocumelonntBuildelonr withDirelonctelondAtUselonr(final long direlonctelondAtUselonrId) {
    withLongFielonld(elonarlybirdFielonldConstant.DIRelonCTelonD_AT_USelonR_ID_FIelonLD.gelontFielonldNamelon(),
        direlonctelondAtUselonrId);

    withLongFielonld(elonarlybirdFielonldConstant.DIRelonCTelonD_AT_USelonR_ID_CSF.gelontFielonldNamelon(), direlonctelondAtUselonrId);

    relonturn this;
  }

  /**
   * Add a whitelon spacelon tokelonnizelond screlonelonn namelon fielonld.
   *
   * elonxamplelon:
   *  screlonelonnNamelon - "supelonr_helonro"
   *  tokelonnizelond velonrsion - "supelonr helonro"
   */
  public elonarlybirdThriftDocumelonntBuildelonr withWhitelonSpacelonTokelonnizelondScrelonelonnNamelonFielonld(
      String fielonldNamelon,
      String normalizelondScrelonelonnNamelon) {
    String whitelonSpacelonTokelonnizablelonScrelonelonnNamelon = StringUtils.join(
        normalizelondScrelonelonnNamelon.split(Relongelonx.HASHTAG_USelonRNAMelon_PUNCTUATION_RelonGelonX), " ");
    withStringFielonld(fielonldNamelon, whitelonSpacelonTokelonnizablelonScrelonelonnNamelon);
    relonturn this;
  }

  /**
   * Add a camelonl caselon tokelonnizelond screlonelonn namelon fielonld.
   */
  public elonarlybirdThriftDocumelonntBuildelonr withCamelonlCaselonTokelonnizelondScrelonelonnNamelonFielonld(
      String fielonldNamelon,
      String screlonelonnNamelon,
      String normalizelondScrelonelonnNamelon,
      TokelonnStrelonam screlonelonnNamelonTokelonnStrelonam) {

    // this normalizelond telonxt is consistelonnt to how thelon tokelonnizelond strelonam is crelonatelond from
    // TokelonnizelonrHelonlpelonr.gelontNormalizelondCamelonlcaselonTokelonnStrelonam - ielon. just lowelonrcasing.
    String camelonlCaselonTokelonnizelondScrelonelonnNamelonTelonxt =
        TokelonnizelonrHelonlpelonr.gelontNormalizelondCamelonlcaselonTokelonnStrelonamTelonxt(screlonelonnNamelon);
    try {
      // Relonselont thelon tokelonn strelonam in caselon it has belonelonn relonad belonforelon.
      screlonelonnNamelonTokelonnStrelonam.relonselont();
      bytelon[] camelonlCaselonTokelonnizelondScrelonelonnNamelon =
          TwelonelontTokelonnStrelonamSelonrializelonr.gelontTwelonelontTokelonnStrelonamSelonrializelonr()
              .selonrializelon(screlonelonnNamelonTokelonnStrelonam);

      withTokelonnStrelonamFielonld(
          fielonldNamelon,
          camelonlCaselonTokelonnizelondScrelonelonnNamelonTelonxt.iselonmpty()
              ? normalizelondScrelonelonnNamelon : camelonlCaselonTokelonnizelondScrelonelonnNamelonTelonxt,
          camelonlCaselonTokelonnizelondScrelonelonnNamelon);
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("TwittelonrTokelonnStrelonam selonrialization elonrror! Could not selonrializelon: " + screlonelonnNamelon);
      SelonRIALIZelon_FAILURelon_COUNT_NONPelonNGUIN_DelonPelonNDelonNT.increlonmelonnt();
    }
    relonturn this;
  }

  privatelon void addNamelondelonntityFielonlds(
      Selont<Pair<elonarlybirdFielonldConstant, String>> fielonldsApplielond,
      elonarlybirdFielonldConstant namelonOnlyFielonld,
      elonarlybirdFielonldConstant namelonWithTypelonFielonld,
      String namelon,
      NamelondelonntityContelonxt contelonxt) {
    withOnelonTimelonStringFielonld(fielonldsApplielond, namelonOnlyFielonld, namelon, falselon);
    if (contelonxt.isSelontelonntity_typelon()) {
      withOnelonTimelonStringFielonld(fielonldsApplielond, namelonWithTypelonFielonld,
          formatNamelondelonntityString(namelon, contelonxt.gelontelonntity_typelon()), truelon);
    }
  }

  privatelon void withOnelonTimelonStringFielonld(
      Selont<Pair<elonarlybirdFielonldConstant, String>> fielonldsApplielond, elonarlybirdFielonldConstant fielonld,
      String valuelon, boolelonan addToFacelonts) {
    Pair<elonarlybirdFielonldConstant, String> fielonldValuelonPair = nelonw Pair<>(fielonld, valuelon);
    if (!fielonldsApplielond.contains(fielonldValuelonPair)) {
      if (addToFacelonts) {
        addFacelontSkipList(fielonld.gelontFielonldNamelon());
      }
      withStringFielonld(fielonld.gelontFielonldNamelon(), valuelon);
      fielonldsApplielond.add(fielonldValuelonPair);
    }
  }

  privatelon String formatNamelondelonntityString(String namelon, WholelonelonntityTypelon typelon) {
    relonturn String.format("%s:%s", namelon, typelon).toLowelonrCaselon();
  }

  /**
   * Selont whelonthelonr selont LAT_LON_CSF_FIelonLD or not belonforelon build
   * if LAT_LON_CSF_FIelonLD is not selont delonlibelonratelonly.
   *
   * @selonelon #prelonparelonToBuild()
   */
  public elonarlybirdThriftDocumelonntBuildelonr selontAddLatLonCSF(boolelonan isSelont) {
    addLatLonCSF = isSelont;
    relonturn this;
  }

  /**
   * Selont if add elonncodelond twelonelont felonaturelon fielonld in thelon elonnd.
   *
   * @selonelon #prelonparelonToBuild()
   */
  public elonarlybirdThriftDocumelonntBuildelonr selontAddelonncodelondTwelonelontFelonaturelons(boolelonan isSelont) {
    addelonncodelondTwelonelontFelonaturelons = isSelont;
    relonturn this;
  }

  @Ovelonrridelon
  protelonctelond void prelonparelonToBuild() {
    if (!isSelontLatLonCSF && addLatLonCSF) {
      // In lucelonnelon archivelons, this CSF is nelonelondelond relongardlelonss of whelonthelonr gelonoLocation is selont.
      withLatLonCSF(GelonoUtil.ILLelonGAL_LATLON, GelonoUtil.ILLelonGAL_LATLON);
    }

    if (addelonncodelondTwelonelontFelonaturelons) {
      // Add elonncodelond_twelonelont_felonaturelons belonforelon building thelon documelonnt.
      withBytelonsFielonld(
          elonarlybirdFielonldConstant.elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD.gelontFielonldNamelon(),
          elonarlybirdelonncodelondFelonaturelonsUtil.toBytelonsForThriftDocumelonnt(elonncodelondTwelonelontFelonaturelons));
    }

    if (elonxtelonndelondelonncodelondTwelonelontFelonaturelons != null) {
      // Add elonxtelonndelond_elonncodelond_twelonelont_felonaturelons belonforelon building thelon documelonnt.
      withBytelonsFielonld(
          elonarlybirdFielonldConstant.elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD.gelontFielonldNamelon(),
          elonarlybirdelonncodelondFelonaturelonsUtil.toBytelonsForThriftDocumelonnt(elonxtelonndelondelonncodelondTwelonelontFelonaturelons));
    }
  }

  privatelon static boolelonan isNotBlank(String valuelon) {
    relonturn valuelon != null && !valuelon.iselonmpty();
  }

  privatelon static boolelonan isNotelonmpty(List<?> valuelon) {
    relonturn valuelon != null && !valuelon.iselonmpty();
  }
}
