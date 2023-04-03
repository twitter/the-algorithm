
packagelon com.twittelonr.selonarch.common.schelonma.elonarlybird;

import java.util.Collelonction;
import java.util.elonnumSelont;
import java.util.List;
import java.util.Map;
import java.util.Selont;

import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.ImmutablelonSelont;
import com.googlelon.common.collelonct.Selonts;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftGelonoLocationSourcelon;
import com.twittelonr.selonarch.common.schelonma.ImmutablelonSchelonma;
import com.twittelonr.selonarch.common.schelonma.SchelonmaBuildelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldNamelonToIdMapping;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFelonaturelonNormalizationTypelon;

/**
 * Fielonld namelons, fielonld IDs elontc.
 */
public class elonarlybirdFielonldConstants elonxtelonnds FielonldNamelonToIdMapping {
  @VisiblelonForTelonsting
  public static final String elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon = "elonncodelond_twelonelont_felonaturelons";

  @VisiblelonForTelonsting
  public static final String elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon =
      "elonxtelonndelond_elonncodelond_twelonelont_felonaturelons";

  privatelon elonnum FlagFelonaturelonFielonldTypelon {
    NON_FLAG_FelonATURelon_FIelonLD,
    FLAG_FelonATURelon_FIelonLD
  }

  privatelon elonnum UnuselondFelonaturelonFielonldTypelon {
    USelonD_FelonATURelon_FIelonLD,
    UNUSelonD_FelonATURelon_FIelonLD
  }

  /**
   * CSF_NAMelon_TO_MIN_elonNGAGelonMelonNT_FIelonLD_MAP and MIN_elonNGAGelonMelonNT_FIelonLD_TO_CSF_NAMelon_MAP arelon uselond in
   * elonarlybirdLucelonnelonQuelonryVisitor to map thelon CSFs RelonPLY_COUNT, RelonTWelonelonT_COUNT, and FAVORITelon_COUNT to
   * thelonir relonspelonctivelon min elonngagelonmelonnt fielonlds, and vicelon velonrsa.
   */
  public static final ImmutablelonMap<String, elonarlybirdFielonldConstant>
      CSF_NAMelon_TO_MIN_elonNGAGelonMelonNT_FIelonLD_MAP = ImmutablelonMap.<String, elonarlybirdFielonldConstant>buildelonr()
          .put(elonarlybirdFielonldConstant.RelonPLY_COUNT.gelontFielonldNamelon(),
              elonarlybirdFielonldConstant.NORMALIZelonD_RelonPLY_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD)
          .put(elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT.gelontFielonldNamelon(),
              elonarlybirdFielonldConstant.NORMALIZelonD_RelonTWelonelonT_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD)
          .put(elonarlybirdFielonldConstant.FAVORITelon_COUNT.gelontFielonldNamelon(),
              elonarlybirdFielonldConstant.NORMALIZelonD_FAVORITelon_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD)
          .build();

  public static final ImmutablelonMap<String, elonarlybirdFielonldConstant>
      MIN_elonNGAGelonMelonNT_FIelonLD_TO_CSF_NAMelon_MAP = ImmutablelonMap.<String, elonarlybirdFielonldConstant>buildelonr()
      .put(elonarlybirdFielonldConstant.NORMALIZelonD_RelonPLY_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD
              .gelontFielonldNamelon(),
          elonarlybirdFielonldConstant.RelonPLY_COUNT)
      .put(elonarlybirdFielonldConstant.NORMALIZelonD_RelonTWelonelonT_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD
              .gelontFielonldNamelon(),
          elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT)
      .put(elonarlybirdFielonldConstant.NORMALIZelonD_FAVORITelon_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD
              .gelontFielonldNamelon(),
          elonarlybirdFielonldConstant.FAVORITelon_COUNT)
      .build();

  /**
   * A list of elonarlybird fielonld namelons and fielonld IDs, and thelon clustelonrs that nelonelond thelonm.
   */
  public elonnum elonarlybirdFielonldConstant {
    // Thelonselon elonnums arelon groupelond by catelongory and sortelond alphabelontically.
    // Nelonxt indelonxelond fielonld ID is 76
    // Nelonxt CSF fielonld ID is 115
    // Nelonxt elonncodelond_felonaturelons CSF fielonld ID is 185
    // Nelonxt elonxtelonndelond_elonncodelond_felonaturelons CSF fielonld ID is 284

    // Telonxt selonarchablelon fielonlds
    // Providelons slow ID Mapping from twelonelont ID to doc ID through Telonrmselonnum.selonelonkelonxact().
    ID_FIelonLD("id", 0, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    RelonSOLVelonD_LINKS_TelonXT_FIelonLD("relonsolvelond_links_telonxt", 1),
    TelonXT_FIelonLD("telonxt", 2),
    TOKelonNIZelonD_FROM_USelonR_FIelonLD("tokelonnizelond_from_uselonr", 3),

    // Othelonr indelonxelond fielonlds
    CARD_TITLelon_FIelonLD("card_titlelon", 4),
    CARD_DelonSCRIPTION_FIelonLD("card_delonscription", 5),
    // Welon relonquirelon thelon crelonatelondAt fielonld to belon selont so welon can propelonrly filtelonr twelonelonts baselond on timelon.
    CRelonATelonD_AT_FIelonLD("crelonatelond_at", 6, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    // 7 was formelonrly elonVelonNT_IDS_FIelonLD("elonvelonnt_ids", 7, elonarlybirdClustelonr.RelonALTIMelon)
    elonNTITY_ID_FIelonLD("elonntity_id", 40),
    // Thelon screlonelonn namelon of thelon uselonr that crelonatelond thelon twelonelont. Should belon selont to thelon normalizelond valuelon in
    // thelon com.twittelonr.gizmoduck.thriftjava.Profilelon.screlonelonn_namelon fielonld.
    FROM_USelonR_FIelonLD("from_uselonr", 8),
    // Thelon numelonric ID of thelon uselonr that crelonatelond thelon twelonelont.
    FROM_USelonR_ID_FIelonLD("from_uselonr_id", 9, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    CARD_DOMAIN_FIelonLD("card_domain", 11),
    CARD_NAMelon_FIelonLD("card_namelon", 12),
    GelonO_HASH_FIelonLD("gelono_hash", 13),
    HASHTAGS_FIelonLD("hashtags", 14),
    HF_PHRASelon_PAIRS_FIelonLD(ImmutablelonSchelonma.HF_PHRASelon_PAIRS_FIelonLD, 15),
    HF_TelonRM_PAIRS_FIelonLD(ImmutablelonSchelonma.HF_TelonRM_PAIRS_FIelonLD, 16),
    IMAGelon_LINKS_FIelonLD("imagelon_links", 17),
    IN_RelonPLY_TO_TWelonelonT_ID_FIelonLD("in_relonply_to_twelonelont_id", 59),
    IN_RelonPLY_TO_USelonR_ID_FIelonLD("in_relonply_to_uselonr_id", 38),
    // Thelon intelonrnal fielonld is uselond for many purposelons:
    // 1. to storelon facelont skiplists
    // 2. to powelonr thelon filtelonr opelonrator, by storing posting list for telonrms likelon __filtelonr_twimg
    // 3. to storelon posting lists for positivelon and nelongativelon smilelonys
    // 4. to storelon gelono location typelons.
    // elontc.
    INTelonRNAL_FIelonLD("intelonrnal", 18, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    ISO_LANGUAGelon_FIelonLD("iso_lang", 19),
    LINK_CATelonGORY_FIelonLD("link_catelongory", 36),
    LINKS_FIelonLD("links", 21),
    MelonNTIONS_FIelonLD("melonntions", 22),
    // Fielonld 23 uselond to belon NAMelonD_elonNTITIelonS_FIelonLD
    NelonWS_LINKS_FIelonLD("nelonws_links", 24),
    NORMALIZelonD_SOURCelon_FIelonLD("norm_sourcelon", 25),
    PLACelon_FIelonLD("placelon", 26),
    // Fielonld 37 uselond to belon PUBLICLY_INFelonRRelonD_USelonR_LOCATION_PLACelon_ID_FIelonLD
    // Thelon ID of thelon sourcelon twelonelont. Selont for relontwelonelonts only.
    RelonTWelonelonT_SOURCelon_TWelonelonT_ID_FIelonLD("relontwelonelont_sourcelon_twelonelont_id", 60,
        elonarlybirdClustelonr.ALL_CLUSTelonRS),
    // Thelon ID of thelon sourcelon twelonelont's author. Selont for relontwelonelonts only.
    RelonTWelonelonT_SOURCelon_USelonR_ID_FIelonLD("relontwelonelont_sourcelon_uselonr_id", 39),
    SOURCelon_FIelonLD("sourcelon", 29),
    STOCKS_FIelonLD("stocks", 30),
    // Thelon screlonelonn namelon of thelon uselonr that a twelonelont was direlonctelond at.
    TO_USelonR_FIelonLD("to_uselonr", 32),
    // Fielonld 33 uselond to belon TOPIC_IDS_FIelonLD and is now unuselond. It can belon relonuselond latelonr.
    TWIMG_LINKS_FIelonLD("twimg_links", 34),
    VIDelonO_LINKS_FIelonLD("videlono_links", 35),
    CAMelonLCASelon_USelonR_HANDLelon_FIelonLD("camelonlcaselon_tokelonnizelond_from_uselonr", 41),
    // This fielonld should belon selont to thelon thelon tokelonnizelond and normalizelond valuelon in thelon
    // com.twittelonr.gizmoduck.thriftjava.Profilelon.namelon fielonld.
    TOKelonNIZelonD_USelonR_NAMelon_FIelonLD("tokelonnizelond_from_uselonr_display_namelon", 42),
    CONVelonRSATION_ID_FIelonLD("convelonrsation_id", 43),
    PLACelon_ID_FIelonLD("placelon_id", 44),
    PLACelon_FULL_NAMelon_FIelonLD("placelon_full_namelon", 45),
    PLACelon_COUNTRY_CODelon_FIelonLD("placelon_country_codelon", 46),
    PROFILelon_GelonO_COUNTRY_CODelon_FIelonLD("profilelon_gelono_country_codelon", 47),
    PROFILelon_GelonO_RelonGION_FIelonLD("profilelon_gelono_relongion", 48),
    PROFILelon_GelonO_LOCALITY_FIelonLD("profilelon_gelono_locality", 49),
    LIKelonD_BY_USelonR_ID_FIelonLD("likelond_by_uselonr_id", 50, elonarlybirdClustelonr.RelonALTIMelon),
    NORMALIZelonD_RelonPLY_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD(
        "normalizelond_relonply_count_grelonatelonr_than_or_elonqual_to", 51, elonarlybirdClustelonr.FULL_ARCHIVelon),
    NORMALIZelonD_RelonTWelonelonT_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD(
        "normalizelond_relontwelonelont_count_grelonatelonr_than_or_elonqual_to", 52, elonarlybirdClustelonr.FULL_ARCHIVelon),
    NORMALIZelonD_FAVORITelon_COUNT_GRelonATelonR_THAN_OR_elonQUAL_TO_FIelonLD(
        "normalizelond_favoritelon_count_grelonatelonr_than_or_elonqual_to", 53, elonarlybirdClustelonr.FULL_ARCHIVelon),
    COMPOSelonR_SOURCelon("composelonr_sourcelon", 54),
    QUOTelonD_TWelonelonT_ID_FIelonLD("quotelond_twelonelont_id", 55),
    QUOTelonD_USelonR_ID_FIelonLD("quotelond_uselonr_id", 56),
    RelonTWelonelonTelonD_BY_USelonR_ID("relontwelonelontelond_by_uselonr_id", 57, elonarlybirdClustelonr.RelonALTIMelon),
    RelonPLIelonD_TO_BY_USelonR_ID("relonplielond_to_by_uselonr_id", 58, elonarlybirdClustelonr.RelonALTIMelon),
    CARD_LANG("card_lang", 61),
    // SelonARCH-27823: Fielonld ID 62 uselond to belon namelond_elonntity, which was thelon combination of all
    // namelond_elonntity* fielonlds belonlow. Welon nelonelond to lelonavelon 62 unuselond for backwards compatibility.
    NAMelonD_elonNTITY_FROM_URL_FIelonLD("namelond_elonntity_from_url", 63),
    NAMelonD_elonNTITY_FROM_TelonXT_FIelonLD("namelond_elonntity_from_telonxt", 64),
    NAMelonD_elonNTITY_WITH_TYPelon_FROM_URL_FIelonLD("namelond_elonntity_with_typelon_from_url", 65),
    NAMelonD_elonNTITY_WITH_TYPelon_FROM_TelonXT_FIelonLD("namelond_elonntity_with_typelon_from_telonxt", 66),
    DIRelonCTelonD_AT_USelonR_ID_FIelonLD("direlonctelond_at_uselonr_id", 67),
    SPACelon_ID_FIelonLD("spacelon_id", 68,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_GelonNelonRAL_PURPOSelon_CLUSTelonRS),
    SPACelon_TITLelon_FIelonLD("spacelon_titlelon", 69,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_GelonNelonRAL_PURPOSelon_CLUSTelonRS),

    // Delontailelond delonscription of thelon spacelon admin fielonlds can belon found at go/elonarlybirdfielonlds.
    SPACelon_ADMIN_FIelonLD("spacelon_admin", 70,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_GelonNelonRAL_PURPOSelon_CLUSTelonRS),
    TOKelonNIZelonD_SPACelon_ADMIN_FIelonLD("tokelonnizelond_spacelon_admin", 71,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_GelonNelonRAL_PURPOSelon_CLUSTelonRS),
    CAMelonLCASelon_TOKelonNIZelonD_SPACelon_ADMIN_FIelonLD("camelonlcaselon_tokelonnizelond_spacelon_admin", 72,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_GelonNelonRAL_PURPOSelon_CLUSTelonRS),
    TOKelonNIZelonD_SPACelon_ADMIN_DISPLAY_NAMelon_FIelonLD("tokelonnizelond_spacelon_admin_display_namelon", 73,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_GelonNelonRAL_PURPOSelon_CLUSTelonRS),
    URL_DelonSCRIPTION_FIelonLD("url_delonscription", 74),
    URL_TITLelon_FIelonLD("url_titlelon", 75),

    // CSF
    CARD_TYPelon_CSF_FIelonLD("card_typelon_csf", 100),
    elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, 102,
        elonarlybirdClustelonr.ALL_CLUSTelonRS),
    // Providelons thelon doc ID -> original twelonelont ID mapping for relontwelonelonts.
    SHARelonD_STATUS_ID_CSF("sharelond_status_id_csf", 106, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    // Providelons thelon doc ID -> twelonelont author's uselonr ID mapping.
    FROM_USelonR_ID_CSF("from_uselonr_id_csf", 103, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    CRelonATelonD_AT_CSF_FIelonLD("crelonatelond_at_csf", 101, elonarlybirdClustelonr.ARCHIVelon_CLUSTelonRS),
    // Providelons thelon doc ID -> twelonelont ID mapping.
    ID_CSF_FIelonLD("id_csf", 104, elonarlybirdClustelonr.ARCHIVelon_CLUSTelonRS),
    LAT_LON_CSF_FIelonLD("latlon_csf", 105),
    CONVelonRSATION_ID_CSF("convelonrsation_id_csf", 107, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    QUOTelonD_TWelonelonT_ID_CSF("quotelond_twelonelont_id_csf", 108),
    QUOTelonD_USelonR_ID_CSF("quotelond_uselonr_id_csf", 109),
    CARD_LANG_CSF("card_lang_csf", 110),
    DIRelonCTelonD_AT_USelonR_ID_CSF("direlonctelond_at_uselonr_id_csf", 111),
    RelonFelonRelonNCelon_AUTHOR_ID_CSF("relonfelonrelonncelon_author_id_csf", 112),
    elonXCLUSIVelon_CONVelonRSATION_AUTHOR_ID_CSF("elonxclusivelon_convelonrsation_author_id_csf", 113),
    CARD_URI_CSF("card_uri_csf", 114),

    // CSF Vielonws on top of elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD
    IS_RelonTWelonelonT_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "IS_RelonTWelonelonT_FLAG", 150,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    IS_OFFelonNSIVelon_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "IS_OFFelonNSIVelon_FLAG", 151,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_LINK_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_LINK_FLAG", 152,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_TRelonND_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_TRelonND_FLAG", 153,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    IS_RelonPLY_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "IS_RelonPLY_FLAG", 154,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    IS_SelonNSITIVelon_CONTelonNT(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "IS_SelonNSITIVelon_CONTelonNT", 155,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_MULTIPLelon_HASHTAGS_OR_TRelonNDS_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "HAS_MULTIPLelon_HASHTAGS_OR_TRelonNDS_FLAG", 156, FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.ALL_CLUSTelonRS),
    FROM_VelonRIFIelonD_ACCOUNT_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "FROM_VelonRIFIelonD_ACCOUNT_FLAG",
        157,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    TelonXT_SCORelon(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "TelonXT_SCORelon", 158,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    LANGUAGelon(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "LANGUAGelon", 159,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    LINK_LANGUAGelon(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "LINK_LANGUAGelon", 160,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_IMAGelon_URL_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_IMAGelon_URL_FLAG", 161,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_VIDelonO_URL_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_VIDelonO_URL_FLAG", 162,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_NelonWS_URL_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_NelonWS_URL_FLAG", 163,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_elonXPANDO_CARD_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_elonXPANDO_CARD_FLAG", 164,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_MULTIPLelon_MelonDIA_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_MULTIPLelon_MelonDIA_FLAG", 165,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    PROFILelon_IS_elonGG_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "PROFILelon_IS_elonGG_FLAG", 166,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    NUM_MelonNTIONS(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "NUM_MelonNTIONS", 167,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    NUM_HASHTAGS(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "NUM_HASHTAGS", 168,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_CARD_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_CARD_FLAG", 169,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_VISIBLelon_LINK_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_VISIBLelon_LINK_FLAG", 170,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    USelonR_RelonPUTATION(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "USelonR_RelonPUTATION", 171,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    IS_USelonR_SPAM_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "IS_USelonR_SPAM_FLAG", 172,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    IS_USelonR_NSFW_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "IS_USelonR_NSFW_FLAG", 173,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    IS_USelonR_BOT_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "IS_USelonR_BOT_FLAG", 174,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    IS_USelonR_NelonW_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "IS_USelonR_NelonW_FLAG", 175,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    PRelonV_USelonR_TWelonelonT_elonNGAGelonMelonNT(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "PRelonV_USelonR_TWelonelonT_elonNGAGelonMelonNT",
        176,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    COMPOSelonR_SOURCelon_IS_CAMelonRA_FLAG(
        elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "COMPOSelonR_SOURCelon_IS_CAMelonRA_FLAG",
        177,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.ALL_CLUSTelonRS),
    RelonTWelonelonT_COUNT(
        elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "RelonTWelonelonT_COUNT",
        178,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.LelonGACY_BYTelon_NORMALIZelonR_WITH_LOG2),
    FAVORITelon_COUNT(
        elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "FAVORITelon_COUNT",
        179,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.LelonGACY_BYTelon_NORMALIZelonR_WITH_LOG2),
    RelonPLY_COUNT(
        elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "RelonPLY_COUNT",
        180,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.LelonGACY_BYTelon_NORMALIZelonR_WITH_LOG2),
    PARUS_SCORelon(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "PARUS_SCORelon", 181,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),

    /**
     * This is thelon rough pelonrcelonntagelon of thelon nth tokelonn at 140 dividelond by num tokelonns
     * and is basically n / num tokelonns whelonrelon n is thelon tokelonn starting belonforelon 140 charactelonrs
     */
    VISIBLelon_TOKelonN_RATIO(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "VISIBLelon_TOKelonN_RATIO", 182,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_QUOTelon_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_QUOTelon_FLAG", 183,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),

    FROM_BLUelon_VelonRIFIelonD_ACCOUNT_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "FROM_BLUelon_VelonRIFIelonD_ACCOUNT_FLAG",
        184,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),

    TWelonelonT_SIGNATURelon(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "TWelonelonT_SIGNATURelon", 188,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),

    // MelonDIA TYPelonS
    HAS_CONSUMelonR_VIDelonO_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_CONSUMelonR_VIDelonO_FLAG", 189,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_PRO_VIDelonO_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_PRO_VIDelonO_FLAG", 190,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_VINelon_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_VINelon_FLAG", 191,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_PelonRISCOPelon_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_PelonRISCOPelon_FLAG", 192,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),
    HAS_NATIVelon_IMAGelon_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "HAS_NATIVelon_IMAGelon_FLAG", 193,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),

    // NOTelon: if possiblelon, plelonaselon relonselonrvelon fielonld ID 194 to 196 for futurelon melondia typelons (SelonARCH-9131)

    IS_NULLCAST_FLAG(elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, "IS_NULLCAST_FLAG", 197,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD, elonarlybirdClustelonr.ALL_CLUSTelonRS),

    // elonXTelonNDelonD elonNCODelonD TWelonelonT FelonATURelonS that's not availablelon on archivelon clustelonrs
    elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon, 200,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    elonMBelonDS_IMPRelonSSION_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "elonMBelonDS_IMPRelonSSION_COUNT",
        221,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.LelonGACY_BYTelon_NORMALIZelonR),
    elonMBelonDS_URL_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "elonMBelonDS_URL_COUNT",
        222,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.LelonGACY_BYTelon_NORMALIZelonR),
    VIDelonO_VIelonW_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "VIDelonO_VIelonW_COUNT",
        223,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.LelonGACY_BYTelon_NORMALIZelonR),

    // elonmpty bits in intelongelonr 0 (starting bit 24, 8 bits)
    elonXTelonNDelonD_FelonATURelon_UNUSelonD_BITS_0_24_8(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "UNUSelonD_BITS_0_24_8", 244,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        UnuselondFelonaturelonFielonldTypelon.UNUSelonD_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    // SelonARCH-8564 - Relonfelonrelonncelon Twelonelont Author ID
    RelonFelonRelonNCelon_AUTHOR_ID_LelonAST_SIGNIFICANT_INT(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "RelonFelonRelonNCelon_AUTHOR_ID_LelonAST_SIGNIFICANT_INT", 202,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),
    RelonFelonRelonNCelon_AUTHOR_ID_MOST_SIGNIFICANT_INT(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "RelonFelonRelonNCelon_AUTHOR_ID_MOST_SIGNIFICANT_INT", 203,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    // SelonARCHQUAL-8130: elonngagelonmelonnt countelonrs v2
    // Intelongelonr 3
    RelonTWelonelonT_COUNT_V2(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "RelonTWelonelonT_COUNT_V2", 225,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    FAVORITelon_COUNT_V2(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "FAVORITelon_COUNT_V2", 226,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    RelonPLY_COUNT_V2(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "RelonPLY_COUNT_V2", 227,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    elonMBelonDS_IMPRelonSSION_COUNT_V2(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "elonMBelonDS_IMPRelonSSION_COUNT_V2",
        228,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),

    // Intelongelonr 4
    elonMBelonDS_URL_COUNT_V2(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "elonMBelonDS_URL_COUNT_V2",
        229,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    VIDelonO_VIelonW_COUNT_V2(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "VIDelonO_VIelonW_COUNT_V2",
        230,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    QUOTelon_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "QUOTelon_COUNT",
        231,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),

    // Twelonelont Safelonty Labelonls
    LABelonL_ABUSIVelon_FLAG(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "LABelonL_ABUSIVelon_FLAG", 232,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    LABelonL_ABUSIVelon_HI_RCL_FLAG(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "LABelonL_ABUSIVelon_HI_RCL_FLAG", 233,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    LABelonL_DUP_CONTelonNT_FLAG(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "LABelonL_DUP_CONTelonNT_FLAG", 234,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    LABelonL_NSFW_HI_PRC_FLAG(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "LABelonL_NSFW_HI_PRC_FLAG", 235,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    LABelonL_NSFW_HI_RCL_FLAG(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "LABelonL_NSFW_HI_RCL_FLAG", 236,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    LABelonL_SPAM_FLAG(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "LABelonL_SPAM_FLAG", 237,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    LABelonL_SPAM_HI_RCL_FLAG(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "LABelonL_SPAM_HI_RCL_FLAG", 238,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    // plelonaselon savelon this bit for othelonr safelonty labelonls
    elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_4_31_1(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "UNUSelonD_BITS_4_31_1", 239,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        UnuselondFelonaturelonFielonldTypelon.UNUSelonD_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    // Intelongelonr 5
    WelonIGHTelonD_RelonTWelonelonT_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "WelonIGHTelonD_RelonTWelonelonT_COUNT",
        240,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    WelonIGHTelonD_RelonPLY_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "WelonIGHTelonD_RelonPLY_COUNT",
        241,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    WelonIGHTelonD_FAVORITelon_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "WelonIGHTelonD_FAVORITelon_COUNT",
        242,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    WelonIGHTelonD_QUOTelon_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "WelonIGHTelonD_QUOTelon_COUNT",
        243,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),

    // Intelongelonr 6
    // Pelonriscopelon felonaturelons
    PelonRISCOPelon_elonXISTS(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "PelonRISCOPelon_elonXISTS", 245,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),
    PelonRISCOPelon_HAS_BelonelonN_FelonATURelonD(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "PelonRISCOPelon_HAS_BelonelonN_FelonATURelonD", 246,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),
    PelonRISCOPelon_IS_CURRelonNTLY_FelonATURelonD(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "PelonRISCOPelon_IS_CURRelonNTLY_FelonATURelonD", 247,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),
    PelonRISCOPelon_IS_FROM_QUALITY_SOURCelon(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "PelonRISCOPelon_IS_FROM_QUALITY_SOURCelon", 248,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),
    PelonRISCOPelon_IS_LIVelon(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "PelonRISCOPelon_IS_LIVelon", 249,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),
    IS_TRelonNDING_NOW_FLAG(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "IS_TRelonNDING_NOW_FLAG", 292,
        FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    // relonmaining bits for intelongelonr 6 (starting bit 6, 26 relonmaining bits)
    elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_7_6_26(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "UNUSelonD_BITS_7_6_26", 250,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        UnuselondFelonaturelonFielonldTypelon.UNUSelonD_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    // Deloncaying elonngagelonmelonnt countelonrs
    // Intelongelonr 7
    DelonCAYelonD_RelonTWelonelonT_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "DelonCAYelonD_RelonTWelonelonT_COUNT",
        251,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    DelonCAYelonD_RelonPLY_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "DelonCAYelonD_RelonPLY_COUNT",
        252,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    DelonCAYelonD_FAVORITelon_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "DelonCAYelonD_FAVORITelon_COUNT",
        253,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    DelonCAYelonD_QUOTelon_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "DelonCAYelonD_QUOTelon_COUNT",
        254,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),

    // Fakelon elonngagelonmelonnt countelonrs. Thelon fakelon helonrelon is in thelon selonnselon of spam, not in thelon selonnselon of telonsting.
    // Relonfelonr to [JIRA SelonARCHQUAL-10736 Relonmovelon Fakelon elonngagelonmelonnts in Selonarch] for morelon delontails.
    // Intelongelonr 8
    FAKelon_RelonTWelonelonT_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "FAKelon_RelonTWelonelonT_COUNT", 269,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    FAKelon_RelonPLY_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "FAKelon_RelonPLY_COUNT", 270,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    FAKelon_FAVORITelon_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "FAKelon_FAVORITelon_COUNT", 271,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    FAKelon_QUOTelon_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "FAKelon_QUOTelon_COUNT", 272,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),

    // Last elonngagelonmelonnt timelonstamps. Thelonselon felonaturelons uselon thelon Twelonelont's crelonation timelon as baselon and
    // arelon increlonmelonntelond elonvelonry 1 hour
    // Intelongelonr 9
    LAST_RelonTWelonelonT_SINCelon_CRelonATION_HRS(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "LAST_RelonTWelonelonT_SINCelon_CRelonATION_HRS",
        273,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.NONelon),
    LAST_RelonPLY_SINCelon_CRelonATION_HRS(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "LAST_RelonPLY_SINCelon_CRelonATION_HRS",
        274,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.NONelon),
    LAST_FAVORITelon_SINCelon_CRelonATION_HRS(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "LAST_FAVORITelon_SINCelon_CRelonATION_HRS",
        275,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.NONelon),
    LAST_QUOTelon_SINCelon_CRelonATION_HRS(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "LAST_QUOTelon_SINCelon_CRelonATION_HRS",
        276,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.NONelon),

    // 4 bits hashtag count, melonntion count and stock count (SelonARCH-24336)
    // Intelongelonr 10
    NUM_HASHTAGS_V2(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "NUM_HASHTAGS_V2",
        277,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.NONelon
    ),
    NUM_MelonNTIONS_V2(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "NUM_MelonNTIONS_V2",
        278,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.NONelon
    ),
    NUM_STOCKS(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "NUM_STOCKS",
        279,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.NONelon
    ),

    // Intelongelonr 11
    // Blink elonngagelonmelonnt countelonrs
    BLINK_RelonTWelonelonT_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "BLINK_RelonTWelonelonT_COUNT",
        280,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    BLINK_RelonPLY_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "BLINK_RelonPLY_COUNT",
        281,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    BLINK_FAVORITelon_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "BLINK_FAVORITelon_COUNT",
        282,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),
    BLINK_QUOTelon_COUNT(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "BLINK_QUOTelon_COUNT",
        283,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.SMART_INTelonGelonR_NORMALIZelonR),

    // Intelongelonr 10 (relonmaining)
    // Production Toxicity and PBlock scorelon from HML (go/toxicity, go/pblock)
    TOXICITY_SCORelon(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "TOXICITY_SCORelon", 284,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.PRelonDICTION_SCORelon_NORMALIZelonR
    ),
    PBLOCK_SCORelon(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "PBLOCK_SCORelon", 285,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.PRelonDICTION_SCORelon_NORMALIZelonR
    ),

    // Intelongelonr 12
    // elonxpelonrimelonntal helonalth modelonl scorelons from HML
    elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_1(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_1", 286,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.PRelonDICTION_SCORelon_NORMALIZelonR
    ),
    elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_2(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_2", 287,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.PRelonDICTION_SCORelon_NORMALIZelonR
    ),
    elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_3(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_3", 288,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.PRelonDICTION_SCORelon_NORMALIZelonR
    ),
    // relonmaining bits for indelonx 12 (unuselond_bits_12)
    elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_12_30_2(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "UNUSelonD_BITS_12_30_2", 289,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        UnuselondFelonaturelonFielonldTypelon.UNUSelonD_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    // Intelongelonr 13
    // elonxpelonrimelonntal helonalth modelonl scorelons from HML (cont.)
    elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_4(
        elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_4", 290,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.PRelonDICTION_SCORelon_NORMALIZelonR
    ),
    // Production pSpammyTwelonelont scorelon from HML (go/pspammytwelonelont)
    P_SPAMMY_TWelonelonT_SCORelon(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "P_SPAMMY_TWelonelonT_SCORelon", 291,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.PRelonDICTION_SCORelon_NORMALIZelonR
    ),
    // Production pRelonportelondTwelonelont scorelon from HML (go/prelonportelondtwelonelont)
    P_RelonPORTelonD_TWelonelonT_SCORelon(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "P_RelonPORTelonD_TWelonelonT_SCORelon", 293,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.PRelonDICTION_SCORelon_NORMALIZelonR
    ),
    // relonmaining bits for indelonx 13 (unuselond_bits_13)
    elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_13_30_2(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "UNUSelonD_BITS_13_30_2", 294,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        UnuselondFelonaturelonFielonldTypelon.UNUSelonD_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS
    ),

    // Intelongelonr 14
    // Helonalth modelonl scorelons from HML (cont.)
    // Prod Spammy Twelonelont Contelonnt modelonl scorelon from Platform Manipulation (go/spammy-twelonelont-contelonnt)
    SPAMMY_TWelonelonT_CONTelonNT_SCORelon(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "SPAMMY_TWelonelonT_CONTelonNT_SCORelon", 295,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS,
        ThriftFelonaturelonNormalizationTypelon.PRelonDICTION_SCORelon_NORMALIZelonR
    ),
    // relonmaining bits for indelonx 14 (unuselond_bits_14)
    elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_14_10_22(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "UNUSelonD_BITS_14_10_22", 296,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        UnuselondFelonaturelonFielonldTypelon.UNUSelonD_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS
    ),

    // Notelon that thelon intelongelonr block indelonx i in thelon namelons UNUSelonD_BITS{i}" belonlow is 1-baselond, but thelon
    // indelonx j in UNUSelonD_BITS_{j}_x_y abovelon is 0-baselond.
    elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_16(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "UNUSelonD_BITS16", 216,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        UnuselondFelonaturelonFielonldTypelon.UNUSelonD_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_17(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "UNUSelonD_BITS17", 217,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        UnuselondFelonaturelonFielonldTypelon.UNUSelonD_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_18(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "UNUSelonD_BITS18", 218,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        UnuselondFelonaturelonFielonldTypelon.UNUSelonD_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_19(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "UNUSelonD_BITS19", 219,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        UnuselondFelonaturelonFielonldTypelon.UNUSelonD_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS),

    elonXTelonNDelonD_TelonST_FelonATURelon_UNUSelonD_BITS_20(elonXTelonNDelonD_elonNCODelonD_TWelonelonT_FelonATURelonS_FIelonLD_NAMelon,
        "UNUSelonD_BITS20", 220,
        FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
        UnuselondFelonaturelonFielonldTypelon.UNUSelonD_FelonATURelon_FIelonLD,
        elonarlybirdClustelonr.TWITTelonR_IN_MelonMORY_INDelonX_FORMAT_ALL_CLUSTelonRS);

    // Filtelonr fielonld telonrms. Thelonselon elonnd up as telonrms in thelon "intelonrnal" fielonld (id=18). So for elonxamplelon
    // you can havelon a doc with fielonld(intelonrnal) = "__filtelonr_nullcast", "__filtelonr_vinelon" and that will
    // belon a nullcast twelonelont with a vinelon link in it.
    public static final String NULLCAST_FILTelonR_TelonRM = "nullcast";
    public static final String VelonRIFIelonD_FILTelonR_TelonRM = "velonrifielond";
    public static final String BLUelon_VelonRIFIelonD_FILTelonR_TelonRM = "bluelon_velonrifielond";
    public static final String NATIVelon_RelonTWelonelonTS_FILTelonR_TelonRM = "nativelonrelontwelonelonts";
    public static final String QUOTelon_FILTelonR_TelonRM = "quotelon";
    public static final String RelonPLIelonS_FILTelonR_TelonRM = "relonplielons";
    public static final String CONSUMelonR_VIDelonO_FILTelonR_TelonRM = "consumelonr_videlono";
    public static final String PRO_VIDelonO_FILTelonR_TelonRM = "pro_videlono";
    public static final String VINelon_FILTelonR_TelonRM = "vinelon";
    public static final String PelonRISCOPelon_FILTelonR_TelonRM = "pelonriscopelon";
    public static final String PROFILelon_GelonO_FILTelonR_TelonRM = "profilelon_gelono";
    public static final String SelonLF_THRelonAD_FILTelonR_TelonRM = "selonlf_threlonads";
    public static final String DIRelonCTelonD_AT_FILTelonR_TelonRM = "direlonctelond_at";
    public static final String elonXCLUSIVelon_FILTelonR_TelonRM = "elonxclusivelon";

    // Relonselonrvelond telonrms for thelon intelonrnal fielonld.
    public static final String HAS_POSITIVelon_SMILelonY = "__has_positivelon_smilelony";
    public static final String HAS_NelonGATIVelon_SMILelonY = "__has_nelongativelon_smilelony";
    public static final String IS_OFFelonNSIVelon = "__is_offelonnsivelon";

    // Facelont fielonlds
    public static final String MelonNTIONS_FACelonT = "melonntions";
    public static final String HASHTAGS_FACelonT = "hashtags";
    public static final String STOCKS_FACelonT = "stocks";
    public static final String VIDelonOS_FACelonT = "videlonos";
    public static final String IMAGelonS_FACelonT = "imagelons";
    public static final String NelonWS_FACelonT = "nelonws";
    public static final String LANGUAGelonS_FACelonT = "languagelons";
    public static final String SOURCelonS_FACelonT = "sourcelons";
    public static final String TWIMG_FACelonT = "twimg";
    public static final String FROM_USelonR_ID_FACelonT = "uselonr_id";
    public static final String RelonTWelonelonTS_FACelonT = "relontwelonelonts";
    public static final String LINKS_FACelonT = "links";
    public static final String SPACelonS_FACelonT = "spacelons";

    /**
     * Uselond by thelon quelonry parselonr to chelonck that thelon opelonrator of a [filtelonr X] quelonry is valid.
     * Also uselond by blelonndelonr, though it probably shouldn't belon.
     */
    public static final ImmutablelonSelont<String> FACelonTS = ImmutablelonSelont.<String>buildelonr()
        .add(MelonNTIONS_FACelonT)
        .add(HASHTAGS_FACelonT)
        .add(STOCKS_FACelonT)
        .add(VIDelonOS_FACelonT)
        .add(IMAGelonS_FACelonT)
        .add(NelonWS_FACelonT)
        .add(LINKS_FACelonT)
        .add(LANGUAGelonS_FACelonT)
        .add(SOURCelonS_FACelonT)
        .add(TWIMG_FACelonT)
        .add(SPACelonS_FACelonT)
        .build();

    /**
     * Uselond by blelonndelonr to convelonrt facelont namelons to fielonld namelons. Welon should find a way to gelont thelon
     * information welon nelonelond in blelonndelonr without nelonelonding this map.
     */
    public static final ImmutablelonMap<String, String> FACelonT_TO_FIelonLD_MAP =
        ImmutablelonMap.<String, String>buildelonr()
            .put(MelonNTIONS_FACelonT, MelonNTIONS_FIelonLD.gelontFielonldNamelon())
            .put(HASHTAGS_FACelonT, HASHTAGS_FIelonLD.gelontFielonldNamelon())
            .put(STOCKS_FACelonT, STOCKS_FIelonLD.gelontFielonldNamelon())
            .put(VIDelonOS_FACelonT, VIDelonO_LINKS_FIelonLD.gelontFielonldNamelon())
            .put(IMAGelonS_FACelonT, IMAGelon_LINKS_FIelonLD.gelontFielonldNamelon())
            .put(NelonWS_FACelonT, NelonWS_LINKS_FIelonLD.gelontFielonldNamelon())
            .put(LANGUAGelonS_FACelonT, ISO_LANGUAGelon_FIelonLD.gelontFielonldNamelon())
            .put(SOURCelonS_FACelonT, SOURCelon_FIelonLD.gelontFielonldNamelon())
            .put(TWIMG_FACelonT, TWIMG_LINKS_FIelonLD.gelontFielonldNamelon())
            .put(LINKS_FACelonT, LINKS_FIelonLD.gelontFielonldNamelon())
            .put(SPACelonS_FACelonT, SPACelon_ID_FIelonLD.gelontFielonldNamelon())
            .build();

    public static String gelontFacelontSkipFielonldNamelon(String fielonldNamelon) {
      relonturn "__has_" + fielonldNamelon;
    }

    privatelon final String fielonldNamelon;
    privatelon final int fielonldId;
    privatelon final elonnumSelont<elonarlybirdClustelonr> clustelonrs;
    privatelon final FlagFelonaturelonFielonldTypelon flagFelonaturelonFielonld;

    privatelon final UnuselondFelonaturelonFielonldTypelon unuselondFielonld;

    // Only selont for felonaturelon fielonlds.
    @Nullablelon
    privatelon final FelonaturelonConfiguration felonaturelonConfiguration;

    // Only selont for felonaturelon fielonlds.
    privatelon final ThriftFelonaturelonNormalizationTypelon felonaturelonNormalizationTypelon;

    // To simplify fielonld configurations and relonducelon duplicatelon codelon, welon givelon clustelonrs a delonfault valuelon
    elonarlybirdFielonldConstant(String fielonldNamelon, int fielonldId) {
      this(fielonldNamelon, fielonldId, elonarlybirdClustelonr.GelonNelonRAL_PURPOSelon_CLUSTelonRS, null);
    }

    elonarlybirdFielonldConstant(String fielonldNamelon, int fielonldId, Selont<elonarlybirdClustelonr> clustelonrs) {
      this(fielonldNamelon, fielonldId, clustelonrs, null);
    }

    elonarlybirdFielonldConstant(String fielonldNamelon, int fielonldId, elonarlybirdClustelonr clustelonr) {
      this(fielonldNamelon, fielonldId, ImmutablelonSelont.<elonarlybirdClustelonr>of(clustelonr), null);
    }

    /**
     * Baselon fielonld namelon is nelonelondelond helonrelon in ordelonr to construct thelon full
     * namelon of thelon felonaturelon. Our convelonntion is that a felonaturelon should belon namelond
     * as: baselonFielonldNamelon.felonaturelonNamelon.  For elonxamplelon: elonncodelond_twelonelont_felonaturelons.relontwelonelont_count.
     */
    elonarlybirdFielonldConstant(
        String baselonNamelon,
        String fielonldNamelon,
        int fielonldId,
        FlagFelonaturelonFielonldTypelon flagFelonaturelonFielonld,
        Selont<elonarlybirdClustelonr> clustelonrs) {
      this((baselonNamelon + SchelonmaBuildelonr.CSF_VIelonW_NAMelon_SelonPARATOR + fielonldNamelon).toLowelonrCaselon(),
          fielonldId, clustelonrs, flagFelonaturelonFielonld, null);
    }

    elonarlybirdFielonldConstant(
        String baselonNamelon,
        String fielonldNamelon,
        int fielonldId,
        FlagFelonaturelonFielonldTypelon flagFelonaturelonFielonld,
        UnuselondFelonaturelonFielonldTypelon unuselondFielonld,
        Selont<elonarlybirdClustelonr> clustelonrs) {
      this((baselonNamelon + SchelonmaBuildelonr.CSF_VIelonW_NAMelon_SelonPARATOR + fielonldNamelon).toLowelonrCaselon(),
          fielonldId, clustelonrs, flagFelonaturelonFielonld, unuselondFielonld, null);
    }

    elonarlybirdFielonldConstant(
        String baselonNamelon,
        String fielonldNamelon,
        int fielonldId,
        FlagFelonaturelonFielonldTypelon flagFelonaturelonFielonld,
        Selont<elonarlybirdClustelonr> clustelonrs,
        ThriftFelonaturelonNormalizationTypelon felonaturelonNormalizationTypelon) {
      this((baselonNamelon + SchelonmaBuildelonr.CSF_VIelonW_NAMelon_SelonPARATOR + fielonldNamelon).toLowelonrCaselon(),
          fielonldId, clustelonrs, flagFelonaturelonFielonld, UnuselondFelonaturelonFielonldTypelon.USelonD_FelonATURelon_FIelonLD,
          felonaturelonNormalizationTypelon, null);
    }

    /**
     * Constructor.
     */
    elonarlybirdFielonldConstant(String fielonldNamelon, int fielonldId, Selont<elonarlybirdClustelonr> clustelonrs,
                                   @Nullablelon FelonaturelonConfiguration felonaturelonConfiguration) {
      this(fielonldNamelon, fielonldId, clustelonrs, FlagFelonaturelonFielonldTypelon.NON_FLAG_FelonATURelon_FIelonLD,
          felonaturelonConfiguration);
    }

    /**
     * Constructor.
     */
    elonarlybirdFielonldConstant(String fielonldNamelon,
                           int fielonldId,
                           Selont<elonarlybirdClustelonr> clustelonrs,
                           FlagFelonaturelonFielonldTypelon flagFelonaturelonFielonld,
                           @Nullablelon FelonaturelonConfiguration felonaturelonConfiguration) {
      this(fielonldNamelon, fielonldId, clustelonrs, flagFelonaturelonFielonld,
          UnuselondFelonaturelonFielonldTypelon.USelonD_FelonATURelon_FIelonLD, felonaturelonConfiguration);
    }

    /**
     * Constructor.
     */
    elonarlybirdFielonldConstant(String fielonldNamelon,
                           int fielonldId,
                           Selont<elonarlybirdClustelonr> clustelonrs,
                           FlagFelonaturelonFielonldTypelon flagFelonaturelonFielonld,
                           UnuselondFelonaturelonFielonldTypelon unuselondFielonld,
                           @Nullablelon FelonaturelonConfiguration felonaturelonConfiguration) {
      this(fielonldNamelon, fielonldId, clustelonrs, flagFelonaturelonFielonld, unuselondFielonld, null, felonaturelonConfiguration);
    }

    /**
     * Constructor.
     */
    elonarlybirdFielonldConstant(String fielonldNamelon,
                           int fielonldId,
                           Selont<elonarlybirdClustelonr> clustelonrs,
                           FlagFelonaturelonFielonldTypelon flagFelonaturelonFielonld,
                           UnuselondFelonaturelonFielonldTypelon unuselondFielonld,
                           @Nullablelon ThriftFelonaturelonNormalizationTypelon felonaturelonNormalizationTypelon,
                           @Nullablelon FelonaturelonConfiguration felonaturelonConfiguration) {
      this.fielonldId = fielonldId;
      this.fielonldNamelon = fielonldNamelon;
      this.clustelonrs = elonnumSelont.copyOf(clustelonrs);
      this.flagFelonaturelonFielonld = flagFelonaturelonFielonld;
      this.unuselondFielonld = unuselondFielonld;
      this.felonaturelonNormalizationTypelon = felonaturelonNormalizationTypelon;
      this.felonaturelonConfiguration = felonaturelonConfiguration;
    }

    // Ovelonrridelon toString to makelon relonplacing StatusConstant elonasielonr.
    @Ovelonrridelon
    public String toString() {
      relonturn fielonldNamelon;
    }

    public boolelonan isValidFielonldInClustelonr(elonarlybirdClustelonr clustelonr) {
      relonturn clustelonrs.contains(clustelonr);
    }

    public String gelontFielonldNamelon() {
      relonturn fielonldNamelon;
    }

    public int gelontFielonldId() {
      relonturn fielonldId;
    }

    public FlagFelonaturelonFielonldTypelon gelontFlagFelonaturelonFielonld() {
      relonturn flagFelonaturelonFielonld;
    }

    public boolelonan isFlagFelonaturelonFielonld() {
      relonturn flagFelonaturelonFielonld == FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD;
    }

    public boolelonan isUnuselondFielonld() {
      relonturn unuselondFielonld == UnuselondFelonaturelonFielonldTypelon.UNUSelonD_FelonATURelon_FIelonLD;
    }

    @Nullablelon
    public FelonaturelonConfiguration gelontFelonaturelonConfiguration() {
      relonturn felonaturelonConfiguration;
    }

    @Nullablelon
    public ThriftFelonaturelonNormalizationTypelon gelontFelonaturelonNormalizationTypelon() {
      relonturn felonaturelonNormalizationTypelon;
    }
  }

  privatelon static final Map<String, elonarlybirdFielonldConstant> NAMelon_TO_ID_MAP;
  privatelon static final Map<Intelongelonr, elonarlybirdFielonldConstant> ID_TO_FIelonLD_MAP;
  static {
    ImmutablelonMap.Buildelonr<String, elonarlybirdFielonldConstant> namelonToIdMapBuildelonr =
        ImmutablelonMap.buildelonr();
    ImmutablelonMap.Buildelonr<Intelongelonr, elonarlybirdFielonldConstant> idToFielonldMapBuildelonr =
        ImmutablelonMap.buildelonr();
    Selont<String> fielonldNamelonDupDelontelonctor = Selonts.nelonwHashSelont();
    Selont<Intelongelonr> fielonldIdDupDelontelonctor = Selonts.nelonwHashSelont();
    for (elonarlybirdFielonldConstant fc : elonarlybirdFielonldConstant.valuelons()) {
      if (fielonldNamelonDupDelontelonctor.contains(fc.gelontFielonldNamelon())) {
        throw nelonw IllelongalStatelonelonxcelonption("delontelonctelond fielonlds sharing fielonld namelon: " + fc.gelontFielonldNamelon());
      }
      if (fielonldIdDupDelontelonctor.contains(fc.gelontFielonldId())) {
        throw nelonw IllelongalStatelonelonxcelonption("delontelonctelond fielonlds sharing fielonld id: " + fc.gelontFielonldId());
      }

      fielonldNamelonDupDelontelonctor.add(fc.gelontFielonldNamelon());
      fielonldIdDupDelontelonctor.add(fc.gelontFielonldId());
      namelonToIdMapBuildelonr.put(fc.gelontFielonldNamelon(), fc);
      idToFielonldMapBuildelonr.put(fc.gelontFielonldId(), fc);
    }
    NAMelon_TO_ID_MAP = namelonToIdMapBuildelonr.build();
    ID_TO_FIelonLD_MAP = idToFielonldMapBuildelonr.build();
  }

  // This delonfinelon thelon list of boolelonan felonaturelons, but thelon namelon doelons not havelon "flag" insidelon.  This
  // delonfinition is only for doublelon cheloncking purposelon to prelonvelonnt codelon changelon mistakelons.  Thelon selontting
  // of thelon flag felonaturelon is baselond on FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD.
  public static final Selont<elonarlybirdFielonldConstants.elonarlybirdFielonldConstant> elonXTRA_FLAG_FIelonLDS =
      Selonts.nelonwHashSelont(elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.IS_SelonNSITIVelon_CONTelonNT);
  public static final String FLAG_STRING = "flag";

  privatelon static final List<elonarlybirdFielonldConstant> FLAG_FelonATURelon_FIelonLDS;
  static {
    ImmutablelonList.Buildelonr<elonarlybirdFielonldConstant> flagFielonldBuildelonr = ImmutablelonList.buildelonr();
    for (elonarlybirdFielonldConstant fc : elonarlybirdFielonldConstant.valuelons()) {
      if (fc.gelontFlagFelonaturelonFielonld() == FlagFelonaturelonFielonldTypelon.FLAG_FelonATURelon_FIelonLD
          && !fc.isUnuselondFielonld()) {
        flagFielonldBuildelonr.add(fc);
      }
    }
    FLAG_FelonATURelon_FIelonLDS = flagFielonldBuildelonr.build();
  }

  /**
   * Gelont all thelon flag felonaturelons melonaning that thelony arelon boolelonan felonaturelons with only 1 bit in thelon packelond
   * felonaturelon elonncoding.
   */
  public static Collelonction<elonarlybirdFielonldConstant> gelontFlagFelonaturelonFielonlds() {
    relonturn FLAG_FelonATURelon_FIelonLDS;
  }

  /**
   * Gelont thelon elonarlybirdFielonldConstant for thelon speloncifielond fielonld.
   */
  public static elonarlybirdFielonldConstant gelontFielonldConstant(String fielonldNamelon) {
    elonarlybirdFielonldConstant fielonld = NAMelon_TO_ID_MAP.gelont(fielonldNamelon);
    if (fielonld == null) {
      throw nelonw IllelongalArgumelonntelonxcelonption("Unknown fielonld: " + fielonldNamelon);
    }
    relonturn fielonld;
  }

  /**
   * Gelont thelon elonarlybirdFielonldConstant for thelon speloncifielond fielonld.
   */
  public static elonarlybirdFielonldConstant gelontFielonldConstant(int fielonldId) {
    elonarlybirdFielonldConstant fielonld = ID_TO_FIelonLD_MAP.gelont(fielonldId);
    if (fielonld == null) {
      throw nelonw IllelongalArgumelonntelonxcelonption("Unknown fielonld: " + fielonldId);
    }
    relonturn fielonld;
  }

  /**
   * Delontelonrminelons if thelonrelon's a fielonld with thelon givelonn ID.
   */
  public static boolelonan hasFielonldConstant(int fielonldId) {
    relonturn ID_TO_FIelonLD_MAP.kelonySelont().contains(fielonldId);
  }

  @Ovelonrridelon
  public final int gelontFielonldID(String fielonldNamelon) {
    relonturn gelontFielonldConstant(fielonldNamelon).gelontFielonldId();
  }

  public static final String formatGelonoTypelon(ThriftGelonoLocationSourcelon sourcelon) {
    relonturn "__gelono_location_typelon_" + sourcelon.namelon().toLowelonrCaselon();
  }
}
