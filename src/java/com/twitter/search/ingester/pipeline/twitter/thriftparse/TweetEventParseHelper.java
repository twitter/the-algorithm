packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.thriftparselon;

import java.util.Datelon;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;

import org.apachelon.commons.lang.StringelonscapelonUtils;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.dataproducts.elonnrichmelonnts.thriftjava.Gelonoelonntity;
import com.twittelonr.dataproducts.elonnrichmelonnts.thriftjava.PotelonntialLocation;
import com.twittelonr.dataproducts.elonnrichmelonnts.thriftjava.ProfilelonGelonoelonnrichmelonnt;
import com.twittelonr.elonschelonrbird.thriftjava.TwelonelontelonntityAnnotation;
import com.twittelonr.elonxpandodo.thriftjava.Card2;
import com.twittelonr.gizmoduck.thriftjava.Uselonr;
import com.twittelonr.melondiaselonrvicelons.commons.twelonelontmelondia.thrift_java.MelondiaInfo;
import com.twittelonr.selonarch.common.delonbug.thriftjava.Delonbugelonvelonnts;
import com.twittelonr.selonarch.common.melontrics.Pelonrcelonntilelon;
import com.twittelonr.selonarch.common.melontrics.PelonrcelonntilelonUtil;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.GelonoObjelonct;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.PotelonntialLocationObjelonct;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon.elonschelonrbirdAnnotation;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelonUselonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelonUtil;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrQuotelondMelonssagelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrRelontwelonelontMelonssagelon;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.CardFielonldUtil;
import com.twittelonr.selonrvicelon.spidelonrduck.gelonn.MelondiaTypelons;
import com.twittelonr.twelonelontypielon.thriftjava.DelonvicelonSourcelon;
import com.twittelonr.twelonelontypielon.thriftjava.DirelonctelondAtUselonr;
import com.twittelonr.twelonelontypielon.thriftjava.elonschelonrbirdelonntityAnnotations;
import com.twittelonr.twelonelontypielon.thriftjava.elonxclusivelonTwelonelontControl;
import com.twittelonr.twelonelontypielon.thriftjava.GelonoCoordinatelons;
import com.twittelonr.twelonelontypielon.thriftjava.Hashtagelonntity;
import com.twittelonr.twelonelontypielon.thriftjava.Melondiaelonntity;
import com.twittelonr.twelonelontypielon.thriftjava.Melonntionelonntity;
import com.twittelonr.twelonelontypielon.thriftjava.Placelon;
import com.twittelonr.twelonelontypielon.thriftjava.QuotelondTwelonelont;
import com.twittelonr.twelonelontypielon.thriftjava.Relonply;
import com.twittelonr.twelonelontypielon.thriftjava.Twelonelont;
import com.twittelonr.twelonelontypielon.thriftjava.TwelonelontCorelonData;
import com.twittelonr.twelonelontypielon.thriftjava.TwelonelontCrelonatelonelonvelonnt;
import com.twittelonr.twelonelontypielon.thriftjava.TwelonelontDelonlelontelonelonvelonnt;
import com.twittelonr.twelonelontypielon.thriftjava.Urlelonntity;
import com.twittelonr.twelonelontypielon.twelonelonttelonxt.PartialHtmlelonncoding;

/**
 * This is an utility class for convelonrting Thrift Twelonelontelonvelonnt melonssagelons selonnt by TwelonelontyPielon
 * into ingelonstelonr intelonrnal relonprelonselonntation, IngelonstelonrTwittelonrMelonssagelon.
 */
public final class TwelonelontelonvelonntParselonHelonlpelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TwelonelontelonvelonntParselonHelonlpelonr.class);

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_NULL_TelonXT =
      SelonarchCountelonr.elonxport("twelonelonts_with_null_telonxt_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr TWelonelonT_SIZelon = SelonarchCountelonr.elonxport("twelonelont_sizelon_from_thrift");

  @VisiblelonForTelonsting
  static final Pelonrcelonntilelon<Long> TWelonelonT_SIZelon_PelonRCelonNTILelonS =
      PelonrcelonntilelonUtil.crelonatelonPelonrcelonntilelon("twelonelont_sizelon_from_thrift");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_CONVelonRSATION_ID =
      SelonarchCountelonr.elonxport("twelonelonts_with_convelonrsation_id_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_QUOTelon =
      SelonarchCountelonr.elonxport("twelonelonts_with_quotelon_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_ANNOTATIONS =
      SelonarchCountelonr.elonxport("twelonelonts_with_annotation_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_ANNOTATIONS_ADDelonD =
      SelonarchCountelonr.elonxport("num_annotations_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_COORDINATelon_FIelonLD =
      SelonarchCountelonr.elonxport("twelonelonts_with_coordinatelon_fielonld_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_PLACelon_ADDelonD =
      SelonarchCountelonr.elonxport("num_placelons_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_PLACelon_FIelonLD =
      SelonarchCountelonr.elonxport("twelonelonts_with_placelon_fielonld_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_PLACelon_COUNTRY_CODelon =
      SelonarchCountelonr.elonxport("twelonelonts_with_placelon_country_codelon_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_USelon_PLACelon_FIelonLD =
      SelonarchCountelonr.elonxport("twelonelonts_uselon_placelon_fielonld_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_CANNOT_PARSelon_PLACelon_FIelonLD =
      SelonarchCountelonr.elonxport("twelonelonts_cannot_parselon_placelon_fielonld_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_PROFILelon_GelonO_elonNRICHMelonNT =
      SelonarchCountelonr.elonxport("twelonelonts_with_profilelon_gelono_elonnrichmelonnt_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_MelonNTIONS =
      SelonarchCountelonr.elonxport("twelonelonts_with_melonntions_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_MelonNTIONS_ADDelonD =
      SelonarchCountelonr.elonxport("num_melonntions_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_HASHTAGS =
      SelonarchCountelonr.elonxport("twelonelonts_with_hashtags_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_HASHTAGS_ADDelonD =
      SelonarchCountelonr.elonxport("num_hashtags_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_MelonDIA_URL =
      SelonarchCountelonr.elonxport("twelonelonts_with_melondia_url_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_MelonDIA_URLS_ADDelonD =
      SelonarchCountelonr.elonxport("num_melondia_urls_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_PHOTO_MelonDIA_URL =
      SelonarchCountelonr.elonxport("twelonelonts_with_photo_melondia_url_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_VIDelonO_MelonDIA_URL =
      SelonarchCountelonr.elonxport("twelonelonts_with_videlono_melondia_url_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_WITH_NON_MelonDIA_URL =
      SelonarchCountelonr.elonxport("twelonelonts_with_non_melondia_url_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_NON_MelonDIA_URLS_ADDelonD =
      SelonarchCountelonr.elonxport("num_non_melondia_urls_from_thrift_cnt");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr NUM_TWelonelonTS_MISSING_QUOTelon_URLS =
      SelonarchCountelonr.elonxport("num_twelonelonts_missing_quotelon_urls_cnt");

  // Utility class, disallow instantiation.
  privatelon TwelonelontelonvelonntParselonHelonlpelonr() {
  }

  /** Builds an IngelonstelonrTwittelonrMelonssagelon instancelon from a TwelonelontCrelonatelonelonvelonnt. */
  @Nonnull
  public static IngelonstelonrTwittelonrMelonssagelon gelontTwittelonrMelonssagelonFromCrelonationelonvelonnt(
      @Nonnull TwelonelontCrelonatelonelonvelonnt crelonatelonelonvelonnt,
      @Nonnull List<PelonnguinVelonrsion> supportelondPelonnguinVelonrsions,
      @Nullablelon Delonbugelonvelonnts delonbugelonvelonnts) throws ThriftTwelonelontParsingelonxcelonption {

    Twelonelont twelonelont = crelonatelonelonvelonnt.gelontTwelonelont();
    if (twelonelont == null) {
      throw nelonw ThriftTwelonelontParsingelonxcelonption("No twelonelont fielonld in TwelonelontCrelonatelonelonvelonnt");
    }

    TwelonelontCorelonData corelonData = twelonelont.gelontCorelon_data();
    if (corelonData == null) {
      throw nelonw ThriftTwelonelontParsingelonxcelonption("No corelon_data fielonld in Twelonelont in TwelonelontCrelonatelonelonvelonnt");
    }

    Uselonr uselonr = crelonatelonelonvelonnt.gelontUselonr();
    if (uselonr == null) {
      throw nelonw ThriftTwelonelontParsingelonxcelonption("No uselonr fielonld in TwelonelontCrelonatelonelonvelonnt");
    }
    if (!uselonr.isSelontProfilelon()) {
      throw nelonw ThriftTwelonelontParsingelonxcelonption("No profilelon fielonld in Uselonr in TwelonelontCrelonatelonelonvelonnt");
    }
    if (!uselonr.isSelontSafelonty()) {
      throw nelonw ThriftTwelonelontParsingelonxcelonption("No safelonty fielonld in Uselonr in TwelonelontCrelonatelonelonvelonnt");
    }

    long twittelonrId = twelonelont.gelontId();
    IngelonstelonrTwittelonrMelonssagelon melonssagelon = nelonw IngelonstelonrTwittelonrMelonssagelon(
        twittelonrId,
        supportelondPelonnguinVelonrsions,
        delonbugelonvelonnts);

    // Selont thelon crelonation timelon baselond on thelon twelonelont ID, beloncauselon it has milliseloncond granularity,
    // and corelonData.crelonatelond_at_seloncs has only seloncond granularity.
    melonssagelon.selontDatelon(nelonw Datelon(SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(twittelonrId)));

    boolelonan isNsfw = corelonData.isNsfw_admin() || corelonData.isNsfw_uselonr();
    boolelonan hasMelondiaOrUrlsOrCards =
        twelonelont.gelontMelondiaSizelon() > 0
            || twelonelont.gelontUrlsSizelon() > 0
            || twelonelont.gelontCardsSizelon() > 0
            || twelonelont.isSelontCard2();

    melonssagelon.selontIsSelonnsitivelonContelonnt(isNsfw && hasMelondiaOrUrlsOrCards);

    melonssagelon.selontFromUselonr(gelontFromUselonr(uselonr));
    if (uselonr.isSelontCounts()) {
      melonssagelon.selontFollowelonrsCount((int) uselonr.gelontCounts().gelontFollowelonrs());
    }
    melonssagelon.selontUselonrProtelonctelond(uselonr.gelontSafelonty().isIs_protelonctelond());
    melonssagelon.selontUselonrVelonrifielond(uselonr.gelontSafelonty().isVelonrifielond());
    melonssagelon.selontUselonrBluelonVelonrifielond(uselonr.gelontSafelonty().isIs_bluelon_velonrifielond());

    if (twelonelont.isSelontLanguagelon()) {
      melonssagelon.selontLanguagelon(twelonelont.gelontLanguagelon().gelontLanguagelon()); // languagelon ID likelon "elonn"
    }

    if (twelonelont.isSelontSelonlf_threlonad_melontadata()) {
      melonssagelon.selontSelonlfThrelonad(truelon);
    }

    elonxclusivelonTwelonelontControl elonxclusivelonTwelonelontControl = twelonelont.gelontelonxclusivelon_twelonelont_control();
    if (elonxclusivelonTwelonelontControl != null) {
      if (elonxclusivelonTwelonelontControl.isSelontConvelonrsation_author_id()) {
        melonssagelon.selontelonxclusivelonConvelonrsationAuthorId(
            elonxclusivelonTwelonelontControl.gelontConvelonrsation_author_id());
      }
    }

    selontDirelonctelondAtUselonr(melonssagelon, corelonData);
    addMelonntionsToMelonssagelon(melonssagelon, twelonelont);
    addHashtagsToMelonssagelon(melonssagelon, twelonelont);
    addMelondiaelonntitielonsToMelonssagelon(melonssagelon, twelonelont.gelontId(), twelonelont.gelontMelondia());
    addUrlsToMelonssagelon(melonssagelon, twelonelont.gelontUrls());
    addelonschelonrbirdAnnotationsToMelonssagelon(melonssagelon, twelonelont);
    melonssagelon.selontNullcast(corelonData.isNullcast());

    if (corelonData.isSelontConvelonrsation_id()) {
      melonssagelon.selontConvelonrsationId(corelonData.gelontConvelonrsation_id());
      NUM_TWelonelonTS_WITH_CONVelonRSATION_ID.increlonmelonnt();
    }

    // quotelons
    if (twelonelont.isSelontQuotelond_twelonelont()) {
      QuotelondTwelonelont quotelondTwelonelont = twelonelont.gelontQuotelond_twelonelont();
      if (quotelondTwelonelont.gelontTwelonelont_id() > 0 &&  quotelondTwelonelont.gelontUselonr_id() > 0) {
        if (quotelondTwelonelont.isSelontPelonrmalink()) {
          String quotelondURL = quotelondTwelonelont.gelontPelonrmalink().gelontLong_url();
          Urlelonntity quotelondURLelonntity = nelonw Urlelonntity();
          quotelondURLelonntity.selontelonxpandelond(quotelondURL);
          quotelondURLelonntity.selontUrl(quotelondTwelonelont.gelontPelonrmalink().gelontShort_url());
          quotelondURLelonntity.selontDisplay(quotelondTwelonelont.gelontPelonrmalink().gelontDisplay_telonxt());
          addUrlsToMelonssagelon(melonssagelon, Lists.nelonwArrayList(quotelondURLelonntity));
        } elonlselon {
          LOG.warn("Twelonelont {} has quotelond twelonelont, but is missing quotelond twelonelont URL: {}",
                   twelonelont.gelontId(), quotelondTwelonelont);
          NUM_TWelonelonTS_MISSING_QUOTelon_URLS.increlonmelonnt();
        }
        TwittelonrQuotelondMelonssagelon quotelondMelonssagelon =
            nelonw TwittelonrQuotelondMelonssagelon(
                quotelondTwelonelont.gelontTwelonelont_id(),
                quotelondTwelonelont.gelontUselonr_id());
        melonssagelon.selontQuotelondMelonssagelon(quotelondMelonssagelon);
        NUM_TWelonelonTS_WITH_QUOTelon.increlonmelonnt();
      }
    }

    // card fielonlds
    if (crelonatelonelonvelonnt.gelontTwelonelont().isSelontCard2()) {
      Card2 card = crelonatelonelonvelonnt.gelontTwelonelont().gelontCard2();
      melonssagelon.selontCardNamelon(card.gelontNamelon());
      melonssagelon.selontCardTitlelon(
          CardFielonldUtil.elonxtractBindingValuelon(CardFielonldUtil.TITLelon_BINDING_KelonY, card));
      melonssagelon.selontCardDelonscription(
          CardFielonldUtil.elonxtractBindingValuelon(CardFielonldUtil.DelonSCRIPTION_BINDING_KelonY, card));
      CardFielonldUtil.delonrivelonCardLang(melonssagelon);
      melonssagelon.selontCardUrl(card.gelontUrl());
    }

    // Somelon fielonlds should belon selont baselond on thelon "original" twelonelont. So if this twelonelont is a relontwelonelont,
    // welon want to elonxtract thoselon fielonlds from thelon relontwelonelontelond twelonelont.
    Twelonelont relontwelonelontOrTwelonelont = twelonelont;
    TwelonelontCorelonData relontwelonelontOrTwelonelontCorelonData = corelonData;
    Uselonr relontwelonelontOrTwelonelontUselonr = uselonr;

    // relontwelonelonts
    boolelonan isRelontwelonelont = corelonData.isSelontSharelon();
    if (isRelontwelonelont) {
      relontwelonelontOrTwelonelont = crelonatelonelonvelonnt.gelontSourcelon_twelonelont();
      relontwelonelontOrTwelonelontCorelonData = relontwelonelontOrTwelonelont.gelontCorelon_data();
      relontwelonelontOrTwelonelontUselonr = crelonatelonelonvelonnt.gelontSourcelon_uselonr();

      TwittelonrRelontwelonelontMelonssagelon relontwelonelontMelonssagelon = nelonw TwittelonrRelontwelonelontMelonssagelon();
      relontwelonelontMelonssagelon.selontRelontwelonelontId(twittelonrId);

      if (relontwelonelontOrTwelonelontUselonr != null) {
        if (relontwelonelontOrTwelonelontUselonr.isSelontProfilelon()) {
          relontwelonelontMelonssagelon.selontSharelondUselonrDisplayNamelon(relontwelonelontOrTwelonelontUselonr.gelontProfilelon().gelontNamelon());
        }
        relontwelonelontMelonssagelon.selontSharelondUselonrTwittelonrId(relontwelonelontOrTwelonelontUselonr.gelontId());
      }

      relontwelonelontMelonssagelon.selontSharelondDatelon(nelonw Datelon(relontwelonelontOrTwelonelontCorelonData.gelontCrelonatelond_at_seloncs() * 1000));
      relontwelonelontMelonssagelon.selontSharelondId(relontwelonelontOrTwelonelont.gelontId());

      addMelondiaelonntitielonsToMelonssagelon(melonssagelon, relontwelonelontOrTwelonelont.gelontId(), relontwelonelontOrTwelonelont.gelontMelondia());
      addUrlsToMelonssagelon(melonssagelon, relontwelonelontOrTwelonelont.gelontUrls());

      // If a twelonelont's telonxt is longelonr than 140 charactelonrs, thelon telonxt for any relontwelonelont of that twelonelont
      // will belon truncatelond. And if thelon original twelonelont has hashtags or melonntions aftelonr charactelonr 140,
      // thelon Twelonelontypielon elonvelonnt for thelon relontwelonelont will not includelon thoselon hashtags/melonntions, which will
      // makelon thelon relontwelonelont unselonarchablelon by thoselon hashtags/melonntions. So in ordelonr to avoid this
      // problelonm, welon add to thelon relontwelonelont all hashtags/melonntions selont on thelon original twelonelont.
      addMelonntionsToMelonssagelon(melonssagelon, relontwelonelontOrTwelonelont);
      addHashtagsToMelonssagelon(melonssagelon, relontwelonelontOrTwelonelont);

      melonssagelon.selontRelontwelonelontMelonssagelon(relontwelonelontMelonssagelon);
    }

    // Somelon fielonlds should belon selont baselond on thelon "original" twelonelont.
    // Only selont gelono fielonlds if this is not a relontwelonelont
    if (!isRelontwelonelont) {
      selontGelonoFielonlds(melonssagelon, relontwelonelontOrTwelonelontCorelonData, relontwelonelontOrTwelonelontUselonr);
      selontPlacelonsFielonlds(melonssagelon, relontwelonelontOrTwelonelont);
    }
    selontTelonxt(melonssagelon, relontwelonelontOrTwelonelontCorelonData);
    selontInRelonplyTo(melonssagelon, relontwelonelontOrTwelonelontCorelonData, isRelontwelonelont);
    selontDelonvicelonSourcelonFielonld(melonssagelon, relontwelonelontOrTwelonelont);

    // Profilelon gelono elonnrichmelonnt fielonlds should belon selont baselond on this twelonelont, elonvelonn if it's a relontwelonelont.
    selontProfilelonGelonoelonnrichmelonntFielonlds(melonssagelon, twelonelont);

    // Thelon composelonr uselond to crelonatelon this twelonelont: standard twelonelont crelonator or thelon camelonra flow.
    selontComposelonrSourcelon(melonssagelon, twelonelont);

    relonturn melonssagelon;
  }

  privatelon static void selontGelonoFielonlds(
      TwittelonrMelonssagelon melonssagelon, TwelonelontCorelonData corelonData, Uselonr uselonr) {

    if (corelonData.isSelontCoordinatelons()) {
      NUM_TWelonelonTS_WITH_COORDINATelon_FIelonLD.increlonmelonnt();
      GelonoCoordinatelons coords = corelonData.gelontCoordinatelons();
      melonssagelon.selontGelonoTaggelondLocation(
          GelonoObjelonct.crelonatelonForIngelonstelonr(coords.gelontLatitudelon(), coords.gelontLongitudelon()));

      String location =
          String.format("GelonoAPI:%.4f,%.4f", coords.gelontLatitudelon(), coords.gelontLongitudelon());
      TwittelonrMelonssagelonUtil.selontAndTruncatelonLocationOnMelonssagelon(melonssagelon, location);
    }

    // If thelon location was not selont from thelon coordinatelons.
    if ((melonssagelon.gelontOrigLocation() == null) && (uselonr != null) && uselonr.isSelontProfilelon()) {
      TwittelonrMelonssagelonUtil.selontAndTruncatelonLocationOnMelonssagelon(melonssagelon, uselonr.gelontProfilelon().gelontLocation());
    }
  }

  privatelon static void selontPlacelonsFielonlds(TwittelonrMelonssagelon melonssagelon, Twelonelont twelonelont) {
    if (!twelonelont.isSelontPlacelon()) {
      relonturn;
    }

    Placelon placelon = twelonelont.gelontPlacelon();

    if (placelon.isSelontContainelonrs() && placelon.gelontContainelonrsSizelon() > 0) {
      NUM_TWelonelonTS_WITH_PLACelon_FIelonLD.increlonmelonnt();
      NUM_PLACelon_ADDelonD.add(placelon.gelontContainelonrsSizelon());

      for (String placelonId : placelon.gelontContainelonrs()) {
        melonssagelon.addPlacelon(placelonId);
      }
    }

    Prelonconditions.chelonckArgumelonnt(placelon.isSelontId(), "Twelonelont.Placelon without id.");
    melonssagelon.selontPlacelonId(placelon.gelontId());
    Prelonconditions.chelonckArgumelonnt(placelon.isSelontFull_namelon(), "Twelonelont.Placelon without full_namelon.");
    melonssagelon.selontPlacelonFullNamelon(placelon.gelontFull_namelon());
    if (placelon.isSelontCountry_codelon()) {
      melonssagelon.selontPlacelonCountryCodelon(placelon.gelontCountry_codelon());
      NUM_TWelonelonTS_WITH_PLACelon_COUNTRY_CODelon.increlonmelonnt();
    }

    if (melonssagelon.gelontGelonoTaggelondLocation() == null) {
      Optional<GelonoObjelonct> location = GelonoObjelonct.fromPlacelon(placelon);

      if (location.isPrelonselonnt()) {
        NUM_TWelonelonTS_USelon_PLACelon_FIelonLD.increlonmelonnt();
        melonssagelon.selontGelonoTaggelondLocation(location.gelont());
      } elonlselon {
        NUM_TWelonelonTS_CANNOT_PARSelon_PLACelon_FIelonLD.increlonmelonnt();
      }
    }
  }

  privatelon static void selontTelonxt(TwittelonrMelonssagelon melonssagelon, TwelonelontCorelonData corelonData) {
    /**
     * TwelonelontyPielon doelonsn't do a full HTML elonscaping of thelon telonxt, only a partial elonscaping
     * so welon uselon thelonir codelon to unelonscapelon it first, thelonn welon do
     * a seloncond unelonscaping beloncauselon whelonn thelon twelonelont telonxt itselonlf has HTML elonscapelon
     * selonquelonncelons, welon want to indelonx thelon unelonscapelond velonrsion, not thelon elonscapelon selonquelonncelon itselonlf.
     * --
     * Yelons, welon *doublelon* unelonscapelon html. About 1-2 twelonelonts pelonr seloncond arelon doublelon elonscapelond,
     * and welon probably want to indelonx thelon relonal telonxt and not things likelon '&#9733;'.
     * Unelonscaping alrelonady unelonscapelond telonxt selonelonms safelon in practicelon.
     * --
     *
     * This may selonelonm wrong, beloncauselon onelon thinks welon should indelonx whatelonvelonr thelon uselonr posts,
     * but givelonn punctuation stripping this crelonatelons odd belonhavior:
     *
     * If somelononelon twelonelonts &amp; thelony won't belon ablelon to find it by selonarching for '&amp;' beloncauselon
     * thelon twelonelont will belon indelonxelond as 'amp'
     *
     * It would also prelonvelonnt somelon twelonelonts from surfacing for celonrtain selonarchelons, for elonxamplelon:
     *
     * Uselonr Twelonelonts: John Mayelonr &amp; Davelon Chappelonllelon
     * Welon Unelonscapelon To: John Mayelonr & Davelon Chappelonllelon
     * Welon Strip/Normalizelon To: john mayelonr davelon chappelonllelon
     *
     * A uselonr selonarching for 'John Mayelonr Davelon Chappelonllelon' would gelont thelon abovelon twelonelont.
     *
     * If welon didn't doublelon unelonscapelon
     *
     * Uselonr Twelonelonts: John Mayelonr &amp; Davelon Chappelonllelon
     * Welon Strip/Normalizelon To: john mayelonr amp davelon chappelonllelon
     *
     * A uselonr selonarching for 'John Mayelonr Davelon Chappelonllelon' would miss thelon abovelon twelonelont.
     *
     * Seloncond elonxamplelon
     *
     * Uselonr Twelonelonts: L'Humanit&elonacutelon;
     * Welon Unelonscapelon To: L'Humanité
     * Welon Strip/Normalizelon To: l humanitelon
     *
     * If welon didn't doublelon elonscapelon
     *
     * Uselonr Twelonelonts: L'Humanit&elonacutelon;
     * Welon Strip/Normalizelon To: l humanit elonacutelon
     *
     */

    String telonxt = corelonData.isSelontTelonxt()
        ? StringelonscapelonUtils.unelonscapelonHtml(PartialHtmlelonncoding.deloncodelon(corelonData.gelontTelonxt()))
        : corelonData.gelontTelonxt();
    melonssagelon.selontTelonxt(telonxt);
    if (telonxt != null) {
      long twelonelontLelonngth = telonxt.lelonngth();
      TWelonelonT_SIZelon.add(twelonelontLelonngth);
      TWelonelonT_SIZelon_PelonRCelonNTILelonS.reloncord(twelonelontLelonngth);
    } elonlselon {
      NUM_TWelonelonTS_WITH_NULL_TelonXT.increlonmelonnt();
    }
  }

  privatelon static void selontInRelonplyTo(
      TwittelonrMelonssagelon melonssagelon, TwelonelontCorelonData corelonData, boolelonan isRelontwelonelont) {
    Relonply relonply = corelonData.gelontRelonply();
    if (!isRelontwelonelont && relonply != null) {
      String inRelonplyToScrelonelonnNamelon = relonply.gelontIn_relonply_to_screlonelonn_namelon();
      long inRelonplyToUselonrId = relonply.gelontIn_relonply_to_uselonr_id();
      melonssagelon.relonplacelonToUselonrWithInRelonplyToUselonrIfNelonelondelond(inRelonplyToScrelonelonnNamelon, inRelonplyToUselonrId);
    }

    if ((relonply != null) && relonply.isSelontIn_relonply_to_status_id()) {
      melonssagelon.selontInRelonplyToStatusId(relonply.gelontIn_relonply_to_status_id());
    }
  }

  privatelon static void selontProfilelonGelonoelonnrichmelonntFielonlds(TwittelonrMelonssagelon melonssagelon, Twelonelont twelonelont) {
    if (!twelonelont.isSelontProfilelon_gelono_elonnrichmelonnt()) {
      relonturn;
    }

    ProfilelonGelonoelonnrichmelonnt profilelonGelonoelonnrichmelonnt = twelonelont.gelontProfilelon_gelono_elonnrichmelonnt();
    List<PotelonntialLocation> thriftPotelonntialLocations =
        profilelonGelonoelonnrichmelonnt.gelontPotelonntial_locations();
    if (!thriftPotelonntialLocations.iselonmpty()) {
      NUM_TWelonelonTS_WITH_PROFILelon_GelonO_elonNRICHMelonNT.increlonmelonnt();
      List<PotelonntialLocationObjelonct> potelonntialLocations = Lists.nelonwArrayList();
      for (PotelonntialLocation potelonntialLocation : thriftPotelonntialLocations) {
        Gelonoelonntity gelonoelonntity = potelonntialLocation.gelontGelono_elonntity();
        potelonntialLocations.add(nelonw PotelonntialLocationObjelonct(gelonoelonntity.gelontCountry_codelon(),
                                                           gelonoelonntity.gelontRelongion(),
                                                           gelonoelonntity.gelontLocality()));
      }

      melonssagelon.selontPotelonntialLocations(potelonntialLocations);
    }
  }

  privatelon static void selontDelonvicelonSourcelonFielonld(TwittelonrMelonssagelon melonssagelon, Twelonelont twelonelont) {
    DelonvicelonSourcelon delonvicelonSourcelon = twelonelont.gelontDelonvicelon_sourcelon();
    TwittelonrMelonssagelonUtil.selontSourcelonOnMelonssagelon(melonssagelon, modifyDelonvicelonSourcelonWithNofollow(delonvicelonSourcelon));
  }

  /** Builds an IngelonstelonrTwittelonrMelonssagelon instancelon from a TwelonelontDelonlelontelonelonvelonnt. */
  @Nonnull
  public static IngelonstelonrTwittelonrMelonssagelon gelontTwittelonrMelonssagelonFromDelonlelontionelonvelonnt(
      @Nonnull TwelonelontDelonlelontelonelonvelonnt delonlelontelonelonvelonnt,
      @Nonnull List<PelonnguinVelonrsion> supportelondPelonnguinVelonrsions,
      @Nullablelon Delonbugelonvelonnts delonbugelonvelonnts) throws ThriftTwelonelontParsingelonxcelonption {

    Twelonelont twelonelont = delonlelontelonelonvelonnt.gelontTwelonelont();
    if (twelonelont == null) {
      throw nelonw ThriftTwelonelontParsingelonxcelonption("No twelonelont fielonld in TwelonelontDelonlelontelonelonvelonnt");
    }
    long twelonelontId = twelonelont.gelontId();

    TwelonelontCorelonData corelonData = twelonelont.gelontCorelon_data();
    if (corelonData == null) {
      throw nelonw ThriftTwelonelontParsingelonxcelonption("No TwelonelontCorelonData in TwelonelontDelonlelontelonelonvelonnt");
    }
    long uselonrId = corelonData.gelontUselonr_id();

    IngelonstelonrTwittelonrMelonssagelon melonssagelon = nelonw IngelonstelonrTwittelonrMelonssagelon(
        twelonelontId,
        supportelondPelonnguinVelonrsions,
        delonbugelonvelonnts);
    melonssagelon.selontDelonlelontelond(truelon);
    melonssagelon.selontTelonxt("delonlelontelon");
    melonssagelon.selontFromUselonr(TwittelonrMelonssagelonUselonr.crelonatelonWithNamelonsAndId("delonlelontelon", "delonlelontelon", uselonrId));

    relonturn melonssagelon;
  }

  privatelon static TwittelonrMelonssagelonUselonr gelontFromUselonr(Uselonr uselonr) {
    String screlonelonnNamelon = uselonr.gelontProfilelon().gelontScrelonelonn_namelon();
    long id = uselonr.gelontId();
    String displayNamelon = uselonr.gelontProfilelon().gelontNamelon();
    relonturn TwittelonrMelonssagelonUselonr.crelonatelonWithNamelonsAndId(screlonelonnNamelon, displayNamelon, id);
  }

  privatelon static void addMelonntionsToMelonssagelon(IngelonstelonrTwittelonrMelonssagelon melonssagelon, Twelonelont twelonelont) {
    List<Melonntionelonntity> melonntions = twelonelont.gelontMelonntions();
    if (melonntions != null) {
      NUM_TWelonelonTS_WITH_MelonNTIONS.increlonmelonnt();
      NUM_MelonNTIONS_ADDelonD.add(melonntions.sizelon());
      for (Melonntionelonntity melonntion : melonntions) {
        addMelonntion(melonssagelon, melonntion);
      }
    }
  }

  privatelon static void addMelonntion(IngelonstelonrTwittelonrMelonssagelon melonssagelon, Melonntionelonntity melonntion) {
    // Delonfault valuelons. Thelony arelon welonird, but arelon consistelonnt with JSON parsing belonhavior.
    Optional<Long> id = Optional.of(-1L);
    Optional<String> screlonelonnNamelon = Optional.of("");
    Optional<String> displayNamelon = Optional.of("");

    if (melonntion.isSelontUselonr_id()) {
      id = Optional.of(melonntion.gelontUselonr_id());
    }

    if (melonntion.isSelontScrelonelonn_namelon()) {
      screlonelonnNamelon = Optional.of(melonntion.gelontScrelonelonn_namelon());
    }

    if (melonntion.isSelontNamelon()) {
      displayNamelon = Optional.of(melonntion.gelontNamelon());
    }

    TwittelonrMelonssagelonUselonr melonntionelondUselonr = TwittelonrMelonssagelonUselonr
        .crelonatelonWithOptionalNamelonsAndId(screlonelonnNamelon, displayNamelon, id);

    if (isToUselonr(melonntion, melonssagelon.gelontToUselonrObjelonct())) {
      melonssagelon.selontToUselonrObjelonct(melonntionelondUselonr);
    }
    melonssagelon.addUselonrToMelonntions(melonntionelondUselonr);
  }

  privatelon static boolelonan isToUselonr(
      Melonntionelonntity melonntion, Optional<TwittelonrMelonssagelonUselonr> optionalToUselonr) {
    if (melonntion.gelontFrom_indelonx() == 0) {
      relonturn truelon;
    }
    if (optionalToUselonr.isPrelonselonnt()) {
      TwittelonrMelonssagelonUselonr toUselonr = optionalToUselonr.gelont();
      if (toUselonr.gelontId().isPrelonselonnt()) {
        long toUselonrId = toUselonr.gelontId().gelont();
        relonturn melonntion.gelontUselonr_id() == toUselonrId;
      }
    }
    relonturn falselon;
  }

  privatelon static void addHashtagsToMelonssagelon(IngelonstelonrTwittelonrMelonssagelon melonssagelon, Twelonelont twelonelont) {
    List<Hashtagelonntity> hashtags = twelonelont.gelontHashtags();
    if (hashtags != null) {
      NUM_TWelonelonTS_WITH_HASHTAGS.increlonmelonnt();
      NUM_HASHTAGS_ADDelonD.add(hashtags.sizelon());
      for (Hashtagelonntity hashtag : hashtags) {
        addHashtag(melonssagelon, hashtag);
      }
    }
  }

  privatelon static void addHashtag(IngelonstelonrTwittelonrMelonssagelon melonssagelon, Hashtagelonntity hashtag) {
    String hashtagString = hashtag.gelontTelonxt();
    melonssagelon.addHashtag(hashtagString);
  }

  /** Add thelon givelonn melondia elonntitielons to thelon givelonn melonssagelon. */
  public static void addMelondiaelonntitielonsToMelonssagelon(
      IngelonstelonrTwittelonrMelonssagelon melonssagelon,
      long photoStatusId,
      @Nullablelon List<Melondiaelonntity> melondias) {

    if (melondias != null) {
      NUM_TWelonelonTS_WITH_MelonDIA_URL.increlonmelonnt();
      NUM_MelonDIA_URLS_ADDelonD.add(melondias.sizelon());

      boolelonan hasPhotoMelondiaUrl = falselon;
      boolelonan hasVidelonoMelondiaUrl = falselon;
      for (Melondiaelonntity melondia : melondias) {
        MelondiaTypelons melondiaTypelon = null;
        if (melondia.isSelontMelondia_info()) {
          MelondiaInfo melondiaInfo = melondia.gelontMelondia_info();
          if (melondiaInfo != null) {
            if (melondiaInfo.isSelont(MelondiaInfo._Fielonlds.IMAGelon_INFO)) {
              melondiaTypelon = MelondiaTypelons.NATIVelon_IMAGelon;
              String melondiaUrl = melondia.gelontMelondia_url_https();
              if (melondiaUrl != null) {
                hasPhotoMelondiaUrl = truelon;
                melonssagelon.addPhotoUrl(photoStatusId, melondiaUrl);
                // Add this link to thelon elonxpandelond URLs too, so that thelon HAS_NATIVelon_IMAGelon_FLAG is selont
                // correlonctly too. Selonelon elonncodelondFelonaturelonBuildelonr.updatelonLinkelonncodelondFelonaturelons().
              }
            } elonlselon if (melondiaInfo.isSelont(MelondiaInfo._Fielonlds.VIDelonO_INFO)) {
              melondiaTypelon = MelondiaTypelons.VIDelonO;
              hasVidelonoMelondiaUrl = truelon;
            }
          }
        }
        String originalUrl = melondia.gelontUrl();
        String elonxpandelondUrl = melondia.gelontelonxpandelond_url();
        melonssagelon.addelonxpandelondMelondiaUrl(originalUrl, elonxpandelondUrl, melondiaTypelon);
      }

      if (hasPhotoMelondiaUrl) {
        NUM_TWelonelonTS_WITH_PHOTO_MelonDIA_URL.increlonmelonnt();
      }
      if (hasVidelonoMelondiaUrl) {
        NUM_TWelonelonTS_WITH_VIDelonO_MelonDIA_URL.increlonmelonnt();
      }
    }
  }

  /** Adds thelon givelonn urls to thelon givelonn melonssagelon. */
  public static void addUrlsToMelonssagelon(
      IngelonstelonrTwittelonrMelonssagelon melonssagelon,
      @Nullablelon List<Urlelonntity> urls) {

    if (urls != null) {
      NUM_TWelonelonTS_WITH_NON_MelonDIA_URL.increlonmelonnt();
      NUM_NON_MelonDIA_URLS_ADDelonD.add(urls.sizelon());
      for (Urlelonntity url : urls) {
        String originalUrl = url.gelontUrl();
        String elonxpandelondUrl = url.gelontelonxpandelond();
        melonssagelon.addelonxpandelondNonMelondiaUrl(originalUrl, elonxpandelondUrl);
      }
    }
  }

  privatelon static void addelonschelonrbirdAnnotationsToMelonssagelon(
      IngelonstelonrTwittelonrMelonssagelon melonssagelon, Twelonelont twelonelont) {
    if (twelonelont.isSelontelonschelonrbird_elonntity_annotations()) {
      elonschelonrbirdelonntityAnnotations elonntityAnnotations = twelonelont.gelontelonschelonrbird_elonntity_annotations();
      if (elonntityAnnotations.isSelontelonntity_annotations()) {
        NUM_TWelonelonTS_WITH_ANNOTATIONS.increlonmelonnt();
        NUM_ANNOTATIONS_ADDelonD.add(elonntityAnnotations.gelontelonntity_annotationsSizelon());
        for (TwelonelontelonntityAnnotation elonntityAnnotation : elonntityAnnotations.gelontelonntity_annotations()) {
          elonschelonrbirdAnnotation elonschelonrbirdAnnotation =
              nelonw elonschelonrbirdAnnotation(elonntityAnnotation.gelontGroupId(),
                  elonntityAnnotation.gelontDomainId(),
                  elonntityAnnotation.gelontelonntityId());
          melonssagelon.addelonschelonrbirdAnnotation(elonschelonrbirdAnnotation);
        }
      }
    }
  }

  privatelon static void selontComposelonrSourcelon(IngelonstelonrTwittelonrMelonssagelon melonssagelon, Twelonelont twelonelont) {
    if (twelonelont.isSelontComposelonr_sourcelon()) {
      melonssagelon.selontComposelonrSourcelon(twelonelont.gelontComposelonr_sourcelon());
    }
  }

  privatelon static String modifyDelonvicelonSourcelonWithNofollow(@Nullablelon DelonvicelonSourcelon delonvicelonSourcelon) {
    if (delonvicelonSourcelon != null) {
      String sourcelon = delonvicelonSourcelon.gelontDisplay();
      int i = sourcelon.indelonxOf("\">");
      if (i == -1) {
        relonturn sourcelon;
      } elonlselon {
        relonturn sourcelon.substring(0, i) + "\" relonl=\"nofollow\">" + sourcelon.substring(i + 2);
      }
    } elonlselon {
      relonturn "<a hrelonf=\"http://twittelonr.com\" relonl=\"nofollow\">Twittelonr</a>";
    }
  }

  privatelon static void selontDirelonctelondAtUselonr(
      IngelonstelonrTwittelonrMelonssagelon melonssagelon,
      TwelonelontCorelonData twelonelontCorelonData) {
    if (!twelonelontCorelonData.isSelontDirelonctelond_at_uselonr()) {
      relonturn;
    }

    DirelonctelondAtUselonr direlonctelondAtUselonr = twelonelontCorelonData.gelontDirelonctelond_at_uselonr();

    if (!direlonctelondAtUselonr.isSelontUselonr_id()) {
      relonturn;
    }

    melonssagelon.selontDirelonctelondAtUselonrId(Optional.of(direlonctelondAtUselonr.gelontUselonr_id()));
  }
}
