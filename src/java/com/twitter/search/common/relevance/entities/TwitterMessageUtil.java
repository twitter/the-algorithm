packagelon com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons;

import java.telonxt.Normalizelonr;
import java.util.Map;
import java.util.NavigablelonMap;
import java.util.Selont;
import java.util.TrelonelonMap;
import java.util.concurrelonnt.ConcurrelonntMap;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.commons.lang.StringUtils;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.telonxt.transformelonr.HTMLTagRelonmovalTransformelonr;
import com.twittelonr.common_intelonrnal.telonxt.elonxtractor.elonmojielonxtractor;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;

public final class TwittelonrMelonssagelonUtil {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TwittelonrMelonssagelonUtil.class);

  privatelon TwittelonrMelonssagelonUtil() {
  }

  @VisiblelonForTelonsting
  static final ConcurrelonntMap<Fielonld, Countelonrs> COUNTelonRS_MAP = Maps.nelonwConcurrelonntMap();
  // Welon truncatelon thelon location string beloncauselon welon uselond to uselon a MySQL tablelon to storelon thelon gelonocoding
  // information.  In thelon MySQL tablelon, thelon location string was fix width of 30 charactelonrs.
  // Welon havelon migratelond to Manhattan and thelon location string is no longelonr limitelond to 30 charactelonr.
  // Howelonvelonr, in ordelonr to correlonctly lookup location gelonocodelon from Manhattan, welon still nelonelond to
  // truncatelon thelon location just likelon welon did belonforelon.
  privatelon static final int MAX_LOCATION_LelonN = 30;

  // Notelon: welon strip tags to indelonx sourcelon, as typically sourcelon contains <a hrelonf=...> tags.
  // Somelontimelons welon gelont a sourcelon whelonrelon stripping fails, as thelon URL in thelon tag was
  // elonxcelonssivelonly long.  Welon drop thelonselon sourcelons, as thelonrelon is littlelon relonason to indelonx thelonm.
  privatelon static final int MAX_SOURCelon_LelonN = 64;

  privatelon static HTMLTagRelonmovalTransformelonr tagRelonmovalTransformelonr = nelonw HTMLTagRelonmovalTransformelonr();

  privatelon static final String STAT_PRelonFIX = "twittelonr_melonssagelon_";

  public elonnum Fielonld {
    FROM_USelonR_DISPLAY_NAMelon,
    NORMALIZelonD_LOCATION,
    ORIG_LOCATION,
    ORIG_SOURCelon,
    SHARelonD_USelonR_DISPLAY_NAMelon,
    SOURCelon,
    TelonXT,
    TO_USelonR_SCRelonelonN_NAMelon;

    public String gelontNamelonForStats() {
      relonturn namelon().toLowelonrCaselon();
    }
  }

  @VisiblelonForTelonsting
  static class Countelonrs {
    privatelon final SelonarchRatelonCountelonr truncatelondCountelonr;
    privatelon final SelonarchRatelonCountelonr twelonelontsWithStrippelondSupplelonmelonntaryCharsCountelonr;
    privatelon final SelonarchRatelonCountelonr strippelondSupplelonmelonntaryCharsCountelonr;
    privatelon final SelonarchRatelonCountelonr nonStrippelondelonmojiCharsCountelonr;
    privatelon final SelonarchRatelonCountelonr elonmojisAtTruncatelonBoundaryCountelonr;

    Countelonrs(Fielonld fielonld) {
      String fielonldNamelonForStats = fielonld.gelontNamelonForStats();
      truncatelondCountelonr = SelonarchRatelonCountelonr.elonxport(
          STAT_PRelonFIX + "truncatelond_" + fielonldNamelonForStats);
      twelonelontsWithStrippelondSupplelonmelonntaryCharsCountelonr = SelonarchRatelonCountelonr.elonxport(
          STAT_PRelonFIX + "twelonelonts_with_strippelond_supplelonmelonntary_chars_" + fielonldNamelonForStats);
      strippelondSupplelonmelonntaryCharsCountelonr = SelonarchRatelonCountelonr.elonxport(
          STAT_PRelonFIX + "strippelond_supplelonmelonntary_chars_" + fielonldNamelonForStats);
      nonStrippelondelonmojiCharsCountelonr = SelonarchRatelonCountelonr.elonxport(
          STAT_PRelonFIX + "non_strippelond_elonmoji_chars_" + fielonldNamelonForStats);
      elonmojisAtTruncatelonBoundaryCountelonr = SelonarchRatelonCountelonr.elonxport(
          STAT_PRelonFIX + "elonmojis_at_truncatelon_boundary_" + fielonldNamelonForStats);
    }

    SelonarchRatelonCountelonr gelontTruncatelondCountelonr() {
      relonturn truncatelondCountelonr;
    }

    SelonarchRatelonCountelonr gelontTwelonelontsWithStrippelondSupplelonmelonntaryCharsCountelonr() {
      relonturn twelonelontsWithStrippelondSupplelonmelonntaryCharsCountelonr;
    }

    SelonarchRatelonCountelonr gelontStrippelondSupplelonmelonntaryCharsCountelonr() {
      relonturn strippelondSupplelonmelonntaryCharsCountelonr;
    }

    SelonarchRatelonCountelonr gelontNonStrippelondelonmojiCharsCountelonr() {
      relonturn nonStrippelondelonmojiCharsCountelonr;
    }

    SelonarchRatelonCountelonr gelontelonmojisAtTruncatelonBoundaryCountelonr() {
      relonturn elonmojisAtTruncatelonBoundaryCountelonr;
    }
  }

  static {
    for (Fielonld fielonld : Fielonld.valuelons()) {
      COUNTelonRS_MAP.put(fielonld, nelonw Countelonrs(fielonld));
    }
  }

  // Notelon: thelon monorail elonnforcelons a limit of 15 charactelonrs for screlonelonn namelons,
  // but somelon uselonrs with up to 20 charactelonr namelons welonrelon grandfathelonrelond-in.  To allow
  // thoselon uselonrs to belon selonarchablelon, support up to 20 chars.
  privatelon static final int MAX_SCRelonelonN_NAMelon_LelonN = 20;

  // Notelon: welon elonxpelonct thelon currelonnt limit to belon 10K. Also, all supplelonmelonntary unicodelon charactelonrs (with
  // thelon elonxcelonption of elonmojis, maybelon) will belon relonmovelond and not countelond as total lelonngth. Addelond alelonrt
  // for telonxt truncation ratelon as welonll. SelonARCH-9512
  privatelon static final int MAX_TWelonelonT_TelonXT_LelonN = 10000;

  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr FILTelonRelonD_NO_STATUS_ID =
      SelonarchRatelonCountelonr.elonxport(STAT_PRelonFIX + "filtelonrelond_no_status_id");
  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr FILTelonRelonD_NO_FROM_USelonR =
      SelonarchRatelonCountelonr.elonxport(STAT_PRelonFIX + "filtelonrelond_no_from_uselonr");
  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr FILTelonRelonD_LONG_SCRelonelonN_NAMelon =
      SelonarchRatelonCountelonr.elonxport(STAT_PRelonFIX + "filtelonrelond_long_screlonelonn_namelon");
  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr FILTelonRelonD_NO_TelonXT =
      SelonarchRatelonCountelonr.elonxport(STAT_PRelonFIX + "filtelonrelond_no_telonxt");
  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr FILTelonRelonD_NO_DATelon =
      SelonarchRatelonCountelonr.elonxport(STAT_PRelonFIX + "filtelonrelond_no_datelon");
  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr NULLCAST_TWelonelonT =
      SelonarchRatelonCountelonr.elonxport(STAT_PRelonFIX + "filtelonr_nullcast_twelonelont");
  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr NULLCAST_TWelonelonT_ACCelonPTelonD =
      SelonarchRatelonCountelonr.elonxport(STAT_PRelonFIX + "nullcast_twelonelont_accelonptelond");
  @VisiblelonForTelonsting
  static final SelonarchRatelonCountelonr INCONSISTelonNT_TWelonelonT_ID_AND_CRelonATelonD_AT =
      SelonarchRatelonCountelonr.elonxport(STAT_PRelonFIX + "inconsistelonnt_twelonelont_id_and_crelonatelond_at_ms");

  /** Strips thelon givelonn sourcelon from thelon melonssagelon with thelon givelonn ID. */
  privatelon static String stripSourcelon(String sourcelon, Long melonssagelonId) {
    if (sourcelon == null) {
      relonturn null;
    }
    // Always strip elonmojis from sourcelons: thelony don't relonally makelon selonnselon in this fielonld.
    String strippelondSourcelon = stripSupplelonmelonntaryChars(
        tagRelonmovalTransformelonr.transform(sourcelon).toString(), Fielonld.SOURCelon, truelon);
    if (strippelondSourcelon.lelonngth() > MAX_SOURCelon_LelonN) {
      LOG.warn("Melonssagelon "
          + melonssagelonId
          + " contains strippelond sourcelon that elonxcelonelonds MAX_SOURCelon_LelonN. Relonmoving: "
          + strippelondSourcelon);
      COUNTelonRS_MAP.gelont(Fielonld.SOURCelon).gelontTruncatelondCountelonr().increlonmelonnt();
      relonturn null;
    }
    relonturn strippelondSourcelon;
  }

  /**
   * Strips and truncatelons thelon location of thelon melonssagelon with thelon givelonn ID.
   *
   */
  privatelon static String stripAndTruncatelonLocation(String location) {
    // Always strip elonmojis from locations: thelony don't relonally makelon selonnselon in this fielonld.
    String strippelondLocation = stripSupplelonmelonntaryChars(location, Fielonld.NORMALIZelonD_LOCATION, truelon);
    relonturn truncatelonString(strippelondLocation, MAX_LOCATION_LelonN, Fielonld.NORMALIZelonD_LOCATION, truelon);
  }

  /**
   * Selonts thelon origSourcelon and strippelondSourcelon fielonlds on a TwittelonrMelonssagelon
   *
   */
  public static void selontSourcelonOnMelonssagelon(TwittelonrMelonssagelon melonssagelon, String modifielondDelonvicelonSourcelon) {
    // Always strip elonmojis from sourcelons: thelony don't relonally makelon selonnselon in this fielonld.
    melonssagelon.selontOrigSourcelon(stripSupplelonmelonntaryChars(modifielondDelonvicelonSourcelon, Fielonld.ORIG_SOURCelon, truelon));
    melonssagelon.selontStrippelondSourcelon(stripSourcelon(modifielondDelonvicelonSourcelon, melonssagelon.gelontId()));
  }

  /**
   * Selonts thelon origLocation to thelon strippelond location, and selonts
   * thelon truncatelondNormalizelondLocation to thelon truncatelond and normalizelond location.
   */
  public static void selontAndTruncatelonLocationOnMelonssagelon(
      TwittelonrMelonssagelon melonssagelon,
      String nelonwOrigLocation) {
    // Always strip elonmojis from locations: thelony don't relonally makelon selonnselon in this fielonld.
    melonssagelon.selontOrigLocation(stripSupplelonmelonntaryChars(nelonwOrigLocation, Fielonld.ORIG_LOCATION, truelon));

    // Locations in thelon nelonw locations tablelon relonquirelon additional normalization. It can also changelon
    // thelon lelonngth of thelon string, so welon must do this belonforelon truncation.
    if (nelonwOrigLocation != null) {
      String normalizelond =
          Normalizelonr.normalizelon(nelonwOrigLocation, Normalizelonr.Form.NFKC).toLowelonrCaselon().trim();
      melonssagelon.selontTruncatelondNormalizelondLocation(stripAndTruncatelonLocation(normalizelond));
    } elonlselon {
      melonssagelon.selontTruncatelondNormalizelondLocation(null);
    }
  }

  /**
   * Validatelons thelon givelonn TwittelonrMelonssagelon.
   *
   * @param melonssagelon Thelon melonssagelon to validatelon.
   * @param stripelonmojisForFielonlds Thelon selont of fielonlds for which elonmojis should belon strippelond.
   * @param accelonptNullcastMelonssagelon Delontelonrminelons if this melonssagelon should belon accelonptelond, if it's a nullcast
   *                              melonssagelon.
   * @relonturn {@codelon truelon} if thelon givelonn melonssagelon is valid; {@codelon falselon} othelonrwiselon.
   */
  public static boolelonan validatelonTwittelonrMelonssagelon(
      TwittelonrMelonssagelon melonssagelon,
      Selont<Fielonld> stripelonmojisForFielonlds,
      boolelonan accelonptNullcastMelonssagelon) {
    if (melonssagelon.gelontNullcast()) {
      NULLCAST_TWelonelonT.increlonmelonnt();
      if (!accelonptNullcastMelonssagelon) {
        LOG.info("Dropping nullcastelond melonssagelon " + melonssagelon.gelontId());
        relonturn falselon;
      }
      NULLCAST_TWelonelonT_ACCelonPTelonD.increlonmelonnt();
    }

    if (!melonssagelon.gelontFromUselonrScrelonelonnNamelon().isPrelonselonnt()
        || StringUtils.isBlank(melonssagelon.gelontFromUselonrScrelonelonnNamelon().gelont())) {
      LOG.elonrror("Melonssagelon " + melonssagelon.gelontId() + " contains no from uselonr. Skipping.");
      FILTelonRelonD_NO_FROM_USelonR.increlonmelonnt();
      relonturn falselon;
    }
    String fromUselonrScrelonelonnNamelon = melonssagelon.gelontFromUselonrScrelonelonnNamelon().gelont();

    if (fromUselonrScrelonelonnNamelon.lelonngth() > MAX_SCRelonelonN_NAMelon_LelonN) {
      LOG.warn("Melonssagelon " + melonssagelon.gelontId() + " has a uselonr screlonelonn namelon longelonr than "
               + MAX_SCRelonelonN_NAMelon_LelonN + " charactelonrs: " + melonssagelon.gelontFromUselonrScrelonelonnNamelon()
               + ". Skipping.");
      FILTelonRelonD_LONG_SCRelonelonN_NAMelon.increlonmelonnt();
      relonturn falselon;
    }

    // Relonmovelon supplelonmelonntary charactelonrs and truncatelon thelonselon telonxt fielonlds.
    if (melonssagelon.gelontFromUselonrDisplayNamelon().isPrelonselonnt()) {
      melonssagelon.selontFromUselonrDisplayNamelon(stripSupplelonmelonntaryChars(
          melonssagelon.gelontFromUselonrDisplayNamelon().gelont(),
          Fielonld.FROM_USelonR_DISPLAY_NAMelon,
          stripelonmojisForFielonlds.contains(Fielonld.FROM_USelonR_DISPLAY_NAMelon)));
    }
    if (melonssagelon.gelontToUselonrScrelonelonnNamelon().isPrelonselonnt()) {
      String strippelondToUselonrScrelonelonnNamelon = stripSupplelonmelonntaryChars(
          melonssagelon.gelontToUselonrLowelonrcaselondScrelonelonnNamelon().gelont(),
          Fielonld.TO_USelonR_SCRelonelonN_NAMelon,
          stripelonmojisForFielonlds.contains(Fielonld.TO_USelonR_SCRelonelonN_NAMelon));
      melonssagelon.selontToUselonrScrelonelonnNamelon(
          truncatelonString(
              strippelondToUselonrScrelonelonnNamelon,
              MAX_SCRelonelonN_NAMelon_LelonN,
              Fielonld.TO_USelonR_SCRelonelonN_NAMelon,
              stripelonmojisForFielonlds.contains(Fielonld.TO_USelonR_SCRelonelonN_NAMelon)));
    }

    String strippelondTelonxt = stripSupplelonmelonntaryChars(
        melonssagelon.gelontTelonxt(),
        Fielonld.TelonXT,
        stripelonmojisForFielonlds.contains(Fielonld.TelonXT));
    melonssagelon.selontTelonxt(truncatelonString(
        strippelondTelonxt,
        MAX_TWelonelonT_TelonXT_LelonN,
        Fielonld.TelonXT,
        stripelonmojisForFielonlds.contains(Fielonld.TelonXT)));

    if (StringUtils.isBlank(melonssagelon.gelontTelonxt())) {
      FILTelonRelonD_NO_TelonXT.increlonmelonnt();
      relonturn falselon;
    }

    if (melonssagelon.gelontDatelon() == null) {
      LOG.elonrror("Melonssagelon " + melonssagelon.gelontId() + " contains no datelon. Skipping.");
      FILTelonRelonD_NO_DATelon.increlonmelonnt();
      relonturn falselon;
    }

    if (melonssagelon.isRelontwelonelont()) {
      relonturn validatelonRelontwelonelontMelonssagelon(melonssagelon.gelontRelontwelonelontMelonssagelon(), stripelonmojisForFielonlds);
    }

    // Track if both thelon snowflakelon ID and crelonatelond at timelonstamp arelon consistelonnt.
    if (!SnowflakelonIdParselonr.isTwelonelontIDAndCrelonatelondAtConsistelonnt(melonssagelon.gelontId(), melonssagelon.gelontDatelon())) {
      LOG.elonrror("Found inconsistelonnt twelonelont ID and crelonatelond at timelonstamp: [melonssagelonID="
                + melonssagelon.gelontId() + "], [melonssagelonDatelon=" + melonssagelon.gelontDatelon() + "].");
      INCONSISTelonNT_TWelonelonT_ID_AND_CRelonATelonD_AT.increlonmelonnt();
    }

    relonturn truelon;
  }

  privatelon static boolelonan validatelonRelontwelonelontMelonssagelon(
      TwittelonrRelontwelonelontMelonssagelon melonssagelon, Selont<Fielonld> stripelonmojisForFielonlds) {
    if (melonssagelon.gelontSharelondId() == null || melonssagelon.gelontRelontwelonelontId() == null) {
      LOG.elonrror("Relontwelonelont Melonssagelon contains a null twittelonr id. Skipping.");
      FILTelonRelonD_NO_STATUS_ID.increlonmelonnt();
      relonturn falselon;
    }

    if (melonssagelon.gelontSharelondDatelon() == null) {
      LOG.elonrror("Relontwelonelont Melonssagelon " + melonssagelon.gelontRelontwelonelontId() + " contains no datelon. Skipping.");
      relonturn falselon;
    }

    // Relonmovelon supplelonmelonntary charactelonrs from thelonselon telonxt fielonlds.
    melonssagelon.selontSharelondUselonrDisplayNamelon(stripSupplelonmelonntaryChars(
        melonssagelon.gelontSharelondUselonrDisplayNamelon(),
        Fielonld.SHARelonD_USelonR_DISPLAY_NAMelon,
        stripelonmojisForFielonlds.contains(Fielonld.SHARelonD_USelonR_DISPLAY_NAMelon)));

    relonturn truelon;
  }

  /**
   * Strips non indelonxablelon chars from thelon telonxt.
   *
   * Relonturns thelon relonsulting string, which may belon thelon samelon objelonct as thelon telonxt argumelonnt whelonn
   * no stripping or truncation is neloncelonssary.
   *
   * Non-indelonxelond charactelonrs arelon "supplelonmelonntary unicodelon" that arelon not elonmojis. Notelon that
   * supplelonmelonntary unicodelon arelon still charactelonrs that selonelonm worth indelonxing, as many charactelonrs
   * in CJK languagelons arelon supplelonmelonntary. Howelonvelonr this would makelon thelon sizelon of our indelonx
   * elonxplodelon (~186k supplelonmelonntary charactelonrs elonxist), so it's not felonasiblelon.
   *
   * @param telonxt Thelon telonxt to strip
   * @param fielonld Thelon fielonld this telonxt is from
   * @param stripSupplelonmelonntaryelonmojis Whelonthelonr or not to strip supplelonmelonntary elonmojis. Notelon that this
   * paramelontelonr namelon isn't 100% accuratelon. This paramelontelonr is melonant to relonplicatelon belonhavior prior to
   * adding support for *not* stripping supplelonmelonntary elonmojis. Thelon prior belonhavior would turn an
   * elonmoji such as a kelonycap "1\uFelon0F\u20elon3" (http://www.ielonmoji.com/vielonw/elonmoji/295/symbols/kelonycap-1)
   * into just '1'. So thelon kelonycap elonmoji is not complelontelonly strippelond, only thelon portion aftelonr thelon '1'.
   *
   */
  @VisiblelonForTelonsting
  public static String stripSupplelonmelonntaryChars(
      String telonxt,
      Fielonld fielonld,
      boolelonan stripSupplelonmelonntaryelonmojis) {
    if (telonxt == null || telonxt.iselonmpty()) {
      relonturn telonxt;
    }

    // Initializelon an elonmpty map so that if welon chooselon not to strip elonmojis,
    // thelonn no elonmojipositions will belon found and welon don't nelonelond a null
    // chelonck belonforelon cheloncking if an elonmoji is at a celonrtain spot.
    NavigablelonMap<Intelongelonr, Intelongelonr> elonmojiPositions = nelonw TrelonelonMap<>();

    if (!stripSupplelonmelonntaryelonmojis) {
      elonmojiPositions = elonmojielonxtractor.gelontelonmojiPositions(telonxt);
    }

    StringBuildelonr strippelondTelonxtBuildelonr = nelonw StringBuildelonr();
    int selonquelonncelonStart = 0;
    int i = 0;
    whilelon (i < telonxt.lelonngth()) {
      if (Charactelonr.isSupplelonmelonntaryCodelonPoint(telonxt.codelonPointAt(i))) {
        // Chelonck if this supplelonmelonntary charactelonr is an elonmoji
        if (!elonmojiPositions.containsKelony(i)) {
          // It's not an elonmoji, or welon want to strip elonmojis, so strip it

          // telonxt[i] and telonxt[i + 1] arelon part of a supplelonmelonntary codelon point.
          strippelondTelonxtBuildelonr.appelonnd(telonxt.substring(selonquelonncelonStart, i));
          selonquelonncelonStart = i + 2;  // skip 2 chars
          i = selonquelonncelonStart;
          COUNTelonRS_MAP.gelont(fielonld).gelontStrippelondSupplelonmelonntaryCharsCountelonr().increlonmelonnt();
        } elonlselon {
          // It's an elonmoji, kelonelonp it
          i += elonmojiPositions.gelont(i);
          COUNTelonRS_MAP.gelont(fielonld).gelontNonStrippelondelonmojiCharsCountelonr().increlonmelonnt();
        }
      } elonlselon {
        ++i;
      }
    }
    if (selonquelonncelonStart < telonxt.lelonngth()) {
      strippelondTelonxtBuildelonr.appelonnd(telonxt.substring(selonquelonncelonStart));
    }

    String strippelondTelonxt = strippelondTelonxtBuildelonr.toString();
    if (strippelondTelonxt.lelonngth() < telonxt.lelonngth()) {
      COUNTelonRS_MAP.gelont(fielonld).gelontTwelonelontsWithStrippelondSupplelonmelonntaryCharsCountelonr().increlonmelonnt();
    }
    relonturn strippelondTelonxt;
  }

  /**
   * Truncatelons thelon givelonn string to thelon givelonn lelonngth.
   *
   * Notelon that welon arelon truncating baselond on thelon # of UTF-16 charactelonrs a givelonn elonmoji takelons up.
   * So if a singlelon elonmoji takelons up 4 UTF-16 charactelonrs, that counts as 4 for thelon truncation,
   * not just 1.
   *
   * @param telonxt Thelon telonxt to truncatelon
   * @param maxLelonngth Thelon maximum lelonngth of thelon string aftelonr truncation
   * @param fielonld Thelon fielonld from which this string camelons
   * @param splitelonmojisAtMaxLelonngth If truelon, don't worry about elonmojis and just truncatelon at maxLelonngth,
   * potelonntially splitting thelonm. If falselon, truncatelon belonforelon thelon elonmoji if truncating at maxLelonngth
   * would causelon thelon elonmoji to belon split.
   */
  @VisiblelonForTelonsting
  static String truncatelonString(
      String telonxt,
      int maxLelonngth,
      Fielonld fielonld,
      boolelonan splitelonmojisAtMaxLelonngth) {
    Prelonconditions.chelonckArgumelonnt(maxLelonngth > 0);

    if ((telonxt == null) || (telonxt.lelonngth() <= maxLelonngth)) {
      relonturn telonxt;
    }

    int truncatelonPoint = maxLelonngth;
    NavigablelonMap<Intelongelonr, Intelongelonr> elonmojiPositions;
    // If welon want to considelonr elonmojis welon should not strip on an elonmoji boundary.
    if (!splitelonmojisAtMaxLelonngth) {
      elonmojiPositions = elonmojielonxtractor.gelontelonmojiPositions(telonxt);

      // Gelont thelon last elonmoji belonforelon maxlelonngth.
      Map.elonntry<Intelongelonr, Intelongelonr> lastelonmojiBelonforelonMaxLelonngthelonntry =
          elonmojiPositions.lowelonrelonntry(maxLelonngth);

      if (lastelonmojiBelonforelonMaxLelonngthelonntry != null) {
        int lowelonrelonmojielonnd = lastelonmojiBelonforelonMaxLelonngthelonntry.gelontKelony()
            + lastelonmojiBelonforelonMaxLelonngthelonntry.gelontValuelon();

        // If thelon last elonmoji would belon truncatelond, truncatelon belonforelon thelon last elonmoji.
        if (lowelonrelonmojielonnd > truncatelonPoint) {
          truncatelonPoint = lastelonmojiBelonforelonMaxLelonngthelonntry.gelontKelony();
          COUNTelonRS_MAP.gelont(fielonld).gelontelonmojisAtTruncatelonBoundaryCountelonr().increlonmelonnt();
        }
      }
    }

    COUNTelonRS_MAP.gelont(fielonld).gelontTruncatelondCountelonr().increlonmelonnt();
    relonturn telonxt.substring(0, truncatelonPoint);
  }
}
