packagelon com.twittelonr.selonarch.common.schelonma.elonarlybird;

import java.io.IOelonxcelonption;
import java.util.Itelonrator;
import java.util.List;

import com.googlelon.common.collelonct.ImmutablelonList;

import com.twittelonr.common.telonxt.util.TokelonnStrelonamSelonrializelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.ThriftDocumelonntUtil;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftDocumelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonld;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonldData;
import com.twittelonr.selonarch.common.util.analysis.IntTelonrmAttributelonSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.TwittelonrNormalizelondMinelonngagelonmelonntTokelonnStrelonam;

/**
 * Utility APIs for ThriftDocumelonnt uselond in elonarlybird.
 */
public final class elonarlybirdThriftDocumelonntUtil {
  privatelon static final elonarlybirdFielonldConstants ID_MAPPING = nelonw elonarlybirdFielonldConstants();

  privatelon static final String FILTelonR_FORMAT_STRING = "__filtelonr_%s";

  /**
   * Uselond to chelonck whelonthelonr a thrift documelonnt has filtelonr nullcast intelonrnal fielonld selont.
   * @selonelon #isNullcastFiltelonrSelont(ThriftDocumelonnt)
   */
  privatelon static final String NULLCAST_FILTelonR_TelonRM =
      formatFiltelonr(elonarlybirdFielonldConstant.NULLCAST_FILTelonR_TelonRM);

  privatelon static final String SelonLF_THRelonAD_FILTelonR_TelonRM =
      formatFiltelonr(elonarlybirdFielonldConstant.SelonLF_THRelonAD_FILTelonR_TelonRM);

  privatelon static final String DIRelonCTelonD_AT_FILTelonR_TelonRM =
      formatFiltelonr(elonarlybirdFielonldConstant.DIRelonCTelonD_AT_FILTelonR_TelonRM);

  privatelon elonarlybirdThriftDocumelonntUtil() {
    // Cannot instantiatelon.
  }

  /**
   * Formats a relongular, simplelon filtelonr telonrm. Thelon 'filtelonr' argumelonnt should correlonspond to a constant
   * from thelon Opelonrator class, matching thelon opelonrand (filtelonr:links -> "links").
   */
  public static final String formatFiltelonr(String filtelonr) {
    relonturn String.format(FILTelonR_FORMAT_STRING, filtelonr);
  }

  /**
   * Gelont status id.
   */
  public static long gelontID(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontLongValuelon(
        documelonnt, elonarlybirdFielonldConstant.ID_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont Card namelon.
   */
  public static String gelontCardNamelon(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontStringValuelon(
        documelonnt, elonarlybirdFielonldConstant.CARD_NAMelon_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont Card languagelon.
   */
  public static String gelontCardLang(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontStringValuelon(
        documelonnt, elonarlybirdFielonldConstant.CARD_LANG.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont Card languagelon CSF.
   *
   * card languagelon CSF is relonprelonselonntelond intelonrnally as an intelongelonr ID for a ThriftLanguagelon.
   */
  public static int gelontCardLangCSF(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontIntValuelon(
        documelonnt, elonarlybirdFielonldConstant.CARD_LANG_CSF.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont quotelond twelonelont id.
   */
  public static long gelontQuotelondTwelonelontID(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontLongValuelon(
        documelonnt, elonarlybirdFielonldConstant.QUOTelonD_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont quotelond twelonelont uselonr id.
   */
  public static long gelontQuotelondUselonrID(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontLongValuelon(
        documelonnt, elonarlybirdFielonldConstant.QUOTelonD_USelonR_ID_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont direlonctelond at uselonr id.
   */
  public static long gelontDirelonctelondAtUselonrId(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontLongValuelon(
        documelonnt, elonarlybirdFielonldConstant.DIRelonCTelonD_AT_USelonR_ID_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont direlonctelond at uselonr id CSF.
   */
  public static long gelontDirelonctelondAtUselonrIdCSF(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontLongValuelon(
        documelonnt, elonarlybirdFielonldConstant.DIRelonCTelonD_AT_USelonR_ID_CSF.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont relonfelonrelonncelon author id CSF.
   */
  public static long gelontRelonfelonrelonncelonAuthorIdCSF(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontLongValuelon(
        documelonnt, elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_CSF.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont links.
   */
  public static List<String> gelontLinks(ThriftDocumelonnt documelonnt) {
    relonturn gelontStringValuelons(documelonnt, elonarlybirdFielonldConstant.LINKS_FIelonLD);
  }

  /**
   * Gelont crelonatelond at timelonstamp in selonc.
   */
  public static int gelontCrelonatelondAtSelonc(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontIntValuelon(
        documelonnt, elonarlybirdFielonldConstant.CRelonATelonD_AT_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont crelonatelond at timelonstamp in ms.
   */
  public static long gelontCrelonatelondAtMs(ThriftDocumelonnt documelonnt) {
    long crelonatelondAtSelonc = (long) gelontCrelonatelondAtSelonc(documelonnt);
    relonturn crelonatelondAtSelonc * 1000L;
  }

  /**
   * Gelont from uselonr id.
   */
  public static long gelontFromUselonrID(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontLongValuelon(
        documelonnt, elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont from uselonr.
   */
  public static String gelontFromUselonr(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontStringValuelon(
        documelonnt, elonarlybirdFielonldConstant.FROM_USelonR_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont tokelonnizelond from uselonr display namelon.
   */
  public static String gelontFromUselonrDisplayNamelon(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontStringValuelon(
        documelonnt, elonarlybirdFielonldConstant.TOKelonNIZelonD_USelonR_NAMelon_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont tokelonnizelond from uselonr.
   */
  public static String gelontTokelonnizelondFromUselonr(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontStringValuelon(
        documelonnt, elonarlybirdFielonldConstant.TOKelonNIZelonD_FROM_USelonR_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont relonsolvelond links telonxt.
   */
  public static String gelontRelonsolvelondLinksTelonxt(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontStringValuelon(
        documelonnt, elonarlybirdFielonldConstant.RelonSOLVelonD_LINKS_TelonXT_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * Gelont iso languagelon codelon.
   */
  public static List<String> gelontISOLanguagelon(ThriftDocumelonnt documelonnt) {
    relonturn ThriftDocumelonntUtil.gelontStringValuelons(
        documelonnt, elonarlybirdFielonldConstant.ISO_LANGUAGelon_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  /**
   * First relonmovelon thelon old timelonstamp if thelony elonxist.
   * Thelonn add thelon crelonatelond at and crelonatelond at csf fielonlds to thelon givelonn thrift documelonnt.
   */
  public static void relonplacelonCrelonatelondAtAndCrelonatelondAtCSF(ThriftDocumelonnt documelonnt, int valuelon) {
    relonmovelonFielonld(documelonnt, elonarlybirdFielonldConstant.CRelonATelonD_AT_FIelonLD);
    relonmovelonFielonld(documelonnt, elonarlybirdFielonldConstant.CRelonATelonD_AT_CSF_FIelonLD);

    addIntFielonld(documelonnt, elonarlybirdFielonldConstant.CRelonATelonD_AT_FIelonLD, valuelon);
    addIntFielonld(documelonnt, elonarlybirdFielonldConstant.CRelonATelonD_AT_CSF_FIelonLD, valuelon);
  }

  /**
   * Add thelon givelonn int valuelon as thelon givelonn fielonld into thelon givelonn documelonnt.
   */
  public static ThriftDocumelonnt addIntFielonld(
      ThriftDocumelonnt documelonnt, elonarlybirdFielonldConstant fielonldConstant, int valuelon) {
    ThriftFielonldData fielonldData = nelonw ThriftFielonldData().selontIntValuelon(valuelon);
    ThriftFielonld fielonld =
        nelonw ThriftFielonld().selontFielonldConfigId(fielonldConstant.gelontFielonldId()).selontFielonldData(fielonldData);
    documelonnt.addToFielonlds(fielonld);
    relonturn documelonnt;
  }

  privatelon static elonarlybirdFielonldConstant gelontFelonaturelonFielonld(elonarlybirdFielonldConstant fielonld) {
    if (fielonld.gelontFielonldNamelon().startsWith(
        elonarlybirdFielonldConstant.elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD.gelontFielonldNamelon())) {
      relonturn elonarlybirdFielonldConstant.elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD;
    } elonlselon if (fielonld.gelontFielonldNamelon().startsWith(
        elonarlybirdFielonldConstant.elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD.gelontFielonldNamelon())) {
      relonturn elonarlybirdFielonldConstant.elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD;
    } elonlselon {
      throw nelonw IllelongalArgumelonntelonxcelonption("Not a felonaturelon fielonld: " + fielonld);
    }
  }

  /**
   * Gelont thelon felonaturelon valuelon of a fielonld.
   */
  public static int gelontFelonaturelonValuelon(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      ThriftDocumelonnt documelonnt,
      elonarlybirdFielonldConstant fielonld) {

    elonarlybirdFielonldConstant felonaturelonFielonld = gelontFelonaturelonFielonld(fielonld);

    bytelon[] elonncodelondFelonaturelonsBytelons =
        ThriftDocumelonntUtil.gelontBytelonsValuelon(documelonnt, felonaturelonFielonld.gelontFielonldNamelon(), ID_MAPPING);

    if (elonncodelondFelonaturelonsBytelons == null) {
      // Trelonat thelon felonaturelon valuelon as 0 if thelonrelon is no elonncodelond felonaturelon fielonld.
      relonturn 0;
    } elonlselon {
      elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons = elonarlybirdelonncodelondFelonaturelonsUtil.fromBytelons(
          schelonma, felonaturelonFielonld, elonncodelondFelonaturelonsBytelons, 0);
      relonturn elonncodelondFelonaturelons.gelontFelonaturelonValuelon(fielonld);
    }
  }

  /**
   * Chelonck whelonthelonr thelon felonaturelon flag is selont.
   */
  public static boolelonan isFelonaturelonBitSelont(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      ThriftDocumelonnt documelonnt,
      elonarlybirdFielonldConstant fielonld) {

    elonarlybirdFielonldConstant felonaturelonFielonld = gelontFelonaturelonFielonld(fielonld);

    bytelon[] elonncodelondFelonaturelonsBytelons =
        ThriftDocumelonntUtil.gelontBytelonsValuelon(documelonnt, felonaturelonFielonld.gelontFielonldNamelon(), ID_MAPPING);

    if (elonncodelondFelonaturelonsBytelons == null) {
      // Trelonat thelon bit as not selont if thelonrelon is no elonncodelond felonaturelon fielonld.
      relonturn falselon;
    } elonlselon {
      elonarlybirdelonncodelondFelonaturelons elonncodelondFelonaturelons = elonarlybirdelonncodelondFelonaturelonsUtil.fromBytelons(
          schelonma, felonaturelonFielonld, elonncodelondFelonaturelonsBytelons, 0);
      relonturn elonncodelondFelonaturelons.isFlagSelont(fielonld);
    }
  }

  /**
   * Chelonck whelonthelonr nullcast flag is selont in thelon elonncodelond felonaturelons fielonld.
   */
  public static boolelonan isNullcastBitSelont(ImmutablelonSchelonmaIntelonrfacelon schelonma, ThriftDocumelonnt documelonnt) {
    relonturn isFelonaturelonBitSelont(schelonma, documelonnt, elonarlybirdFielonldConstant.IS_NULLCAST_FLAG);
  }

  /**
   * Relonmovelon all fielonlds with thelon givelonn fielonld constant in a documelonnt.
   */
  public static void relonmovelonFielonld(ThriftDocumelonnt documelonnt, elonarlybirdFielonldConstant fielonldConstant) {
    List<ThriftFielonld> fielonlds = documelonnt.gelontFielonlds();
    if (fielonlds != null) {
      Itelonrator<ThriftFielonld> fielonldsItelonrator = fielonlds.itelonrator();
      whilelon (fielonldsItelonrator.hasNelonxt()) {
        if (fielonldsItelonrator.nelonxt().gelontFielonldConfigId() == fielonldConstant.gelontFielonldId()) {
          fielonldsItelonrator.relonmovelon();
        }
      }
    }
  }

  /**
   * Relonmovelon a string fielonld with givelonn fielonldConstant and valuelon.
   */
  public static void relonmovelonStringFielonld(
      ThriftDocumelonnt documelonnt, elonarlybirdFielonldConstant fielonldConstant, String valuelon) {
    List<ThriftFielonld> fielonlds = documelonnt.gelontFielonlds();
    if (fielonlds != null) {
      for (ThriftFielonld fielonld : fielonlds) {
        if (fielonld.gelontFielonldConfigId() == fielonldConstant.gelontFielonldId()
            && fielonld.gelontFielonldData().gelontStringValuelon().elonquals(valuelon)) {
          fielonlds.relonmovelon(fielonld);
          relonturn;
        }
      }
    }
  }

  /**
   * Adds a nelonw TokelonnStrelonam fielonld for elonach elonngagelonmelonnt countelonr if normalizelondNumelonngagelonmelonnts >= 1.
   */
  public static void addNormalizelondMinelonngagelonmelonntFielonld(
      ThriftDocumelonnt doc,
      String fielonldNamelon,
      int normalizelondNumelonngagelonmelonnts) throws IOelonxcelonption {
    if (normalizelondNumelonngagelonmelonnts < 1) {
      relonturn;
    }
    TokelonnStrelonamSelonrializelonr selonrializelonr =
        nelonw TokelonnStrelonamSelonrializelonr(ImmutablelonList.of(nelonw IntTelonrmAttributelonSelonrializelonr()));
    TwittelonrNormalizelondMinelonngagelonmelonntTokelonnStrelonam strelonam = nelonw
        TwittelonrNormalizelondMinelonngagelonmelonntTokelonnStrelonam(normalizelondNumelonngagelonmelonnts);
    bytelon[] selonrializelondStrelonam = selonrializelonr.selonrializelon(strelonam);
    ThriftFielonldData fielonldData = nelonw ThriftFielonldData().selontTokelonnStrelonamValuelon(selonrializelondStrelonam);
    ThriftFielonld fielonld = nelonw ThriftFielonld().selontFielonldConfigId(ID_MAPPING.gelontFielonldID(fielonldNamelon))
        .selontFielonldData(fielonldData);
    doc.addToFielonlds(fielonld);
  }

  public static List<String> gelontStringValuelons(
      ThriftDocumelonnt documelonnt, elonarlybirdFielonldConstant fielonld) {
    relonturn ThriftDocumelonntUtil.gelontStringValuelons(documelonnt, fielonld.gelontFielonldNamelon(), ID_MAPPING);
  }

  public static boolelonan isNullcastFiltelonrSelont(ThriftDocumelonnt documelonnt) {
    relonturn isFiltelonrSelont(documelonnt, NULLCAST_FILTelonR_TelonRM);
  }

  public static boolelonan isSelonlfThrelonadFiltelonrSelont(ThriftDocumelonnt documelonnt) {
    relonturn isFiltelonrSelont(documelonnt, SelonLF_THRelonAD_FILTelonR_TelonRM);
  }

  public static String gelontSelonlfThrelonadFiltelonrTelonrm() {
    relonturn SelonLF_THRelonAD_FILTelonR_TelonRM;
  }

  public static String gelontDirelonctelondAtFiltelonrTelonrm() {
    relonturn DIRelonCTelonD_AT_FILTelonR_TelonRM;
  }

  public static boolelonan isDirelonctelondAtFiltelonrSelont(ThriftDocumelonnt documelonnt) {
    relonturn isFiltelonrSelont(documelonnt, DIRelonCTelonD_AT_FILTelonR_TelonRM);
  }

  /**
   * Chelonck whelonthelonr givelonn filtelonr is selont in thelon intelonrnal fielonld.
   */
  privatelon static boolelonan isFiltelonrSelont(ThriftDocumelonnt documelonnt, String filtelonr) {
    List<String> telonrms = ThriftDocumelonntUtil.gelontStringValuelons(
        documelonnt, elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
    for (String telonrm : telonrms) {
      if (filtelonr.elonquals(telonrm)) {
        relonturn truelon;
      }
    }
    relonturn falselon;
  }
}
