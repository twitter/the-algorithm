packagelon com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons;

import java.telonxt.DatelonFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collelonction;
import java.util.Collelonctions;
import java.util.Datelon;
import java.util.HashSelont;
import java.util.LinkelondHashMap;
import java.util.List;
import java.util.Localelon;
import java.util.Map;
import java.util.Optional;
import java.util.Selont;
import javax.annotation.Nonnull;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ComparisonChain;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;
import com.googlelon.common.collelonct.Selonts;

import org.apachelon.commons.lang.StringUtils;
import org.apachelon.commons.lang3.buildelonr.elonqualsBuildelonr;
import org.apachelon.commons.lang3.buildelonr.HashCodelonBuildelonr;
import org.apachelon.commons.lang3.buildelonr.ToStringBuildelonr;
import org.apachelon.lucelonnelon.analysis.TokelonnStrelonam;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.telonxt.languagelon.LocalelonUtil;
import com.twittelonr.common.telonxt.pipelonlinelon.TwittelonrLanguagelonIdelonntifielonr;
import com.twittelonr.common.telonxt.tokelonn.TokelonnizelondCharSelonquelonncelon;
import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.cuad.nelonr.plain.thriftjava.Namelondelonntity;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftelonxpandelondUrl;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontFelonaturelons;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontTelonxtFelonaturelons;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontTelonxtQuality;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.TwelonelontUselonrFelonaturelons;
import com.twittelonr.selonarch.common.util.telonxt.NormalizelonrHelonlpelonr;
import com.twittelonr.selonrvicelon.spidelonrduck.gelonn.MelondiaTypelons;
import com.twittelonr.twelonelontypielon.thriftjava.ComposelonrSourcelon;
import com.twittelonr.util.TwittelonrDatelonFormat;

/**
 * A relonprelonselonntation of twelonelonts uselond as an intelonrmelondiatelon objelonct during ingelonstion. As welon procelonelond
 * in ingelonstion, welon fill this objelonct with data. Welon thelonn convelonrt it to ThriftVelonrsionelondelonvelonnts (which
 * itselonlf relonprelonselonnts a singlelon twelonelont too, in diffelonrelonnt pelonnguin velonrsions potelonntially).
 */
public class TwittelonrMelonssagelon {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TwittelonrMelonssagelon.class);

  public static class elonschelonrbirdAnnotation implelonmelonnts Comparablelon<elonschelonrbirdAnnotation> {
    public final long groupId;
    public final long domainId;
    public final long elonntityId;

    public elonschelonrbirdAnnotation(long groupId, long domainId, long elonntityId) {
      this.groupId = groupId;
      this.domainId = domainId;
      this.elonntityId = elonntityId;
    }

    @Ovelonrridelon
    public boolelonan elonquals(Objelonct o2) {
      if (o2 instancelonof elonschelonrbirdAnnotation) {
        elonschelonrbirdAnnotation a2 = (elonschelonrbirdAnnotation) o2;
        relonturn groupId == a2.groupId && domainId == a2.domainId && elonntityId == a2.elonntityId;
      }
      relonturn falselon;
    }

    @Ovelonrridelon
    public int hashCodelon() {
      relonturn nelonw HashCodelonBuildelonr()
          .appelonnd(groupId)
          .appelonnd(domainId)
          .appelonnd(elonntityId)
          .toHashCodelon();
    }

    @Ovelonrridelon
    public int comparelonTo(elonschelonrbirdAnnotation o) {
      relonturn ComparisonChain.start()
          .comparelon(this.groupId, o.groupId)
          .comparelon(this.domainId, o.domainId)
          .comparelon(this.elonntityId, o.elonntityId)
          .relonsult();
    }
  }

  privatelon final List<elonschelonrbirdAnnotation> elonschelonrbirdAnnotations = Lists.nelonwArrayList();

  // twelonelont felonaturelons for multiplelon pelonnguin velonrsions
  privatelon static class VelonrsionelondTwelonelontFelonaturelons {
    // TwelonelontFelonaturelons populatelond by relonlelonvancelon classifielonrs, structurelon delonfinelond
    // in src/main/thrift/classifielonr.thrift.
    privatelon TwelonelontFelonaturelons twelonelontFelonaturelons = nelonw TwelonelontFelonaturelons();
    privatelon TokelonnizelondCharSelonquelonncelon tokelonnizelondCharSelonquelonncelon = null;
    privatelon Selont<String> normalizelondHashtags = Selonts.nelonwHashSelont();

    public TwelonelontFelonaturelons gelontTwelonelontFelonaturelons() {
      relonturn this.twelonelontFelonaturelons;
    }

    public void selontTwelonelontFelonaturelons(final TwelonelontFelonaturelons twelonelontFelonaturelons) {
      this.twelonelontFelonaturelons = twelonelontFelonaturelons;
    }

    public TwelonelontTelonxtQuality gelontTwelonelontTelonxtQuality() {
      relonturn this.twelonelontFelonaturelons.gelontTwelonelontTelonxtQuality();
    }

    public TwelonelontTelonxtFelonaturelons gelontTwelonelontTelonxtFelonaturelons() {
      relonturn this.twelonelontFelonaturelons.gelontTwelonelontTelonxtFelonaturelons();
    }

    public TwelonelontUselonrFelonaturelons gelontTwelonelontUselonrFelonaturelons() {
      relonturn this.twelonelontFelonaturelons.gelontTwelonelontUselonrFelonaturelons();
    }

    public TokelonnizelondCharSelonquelonncelon gelontTokelonnizelondCharSelonquelonncelon() {
      relonturn this.tokelonnizelondCharSelonquelonncelon;
    }

    public void selontTokelonnizelondCharSelonquelonncelon(TokelonnizelondCharSelonquelonncelon selonquelonncelon) {
      this.tokelonnizelondCharSelonquelonncelon = selonquelonncelon;
    }

    public Selont<String> gelontNormalizelondHashtags() {
      relonturn this.normalizelondHashtags;
    }

    public void addNormalizelondHashtags(String normalizelondHashtag) {
      this.normalizelondHashtags.add(normalizelondHashtag);
    }
  }

  public static final int INT_FIelonLD_NOT_PRelonSelonNT = -1;
  public static final long LONG_FIelonLD_NOT_PRelonSelonNT = -1;
  public static final doublelon DOUBLelon_FIelonLD_NOT_PRelonSelonNT = -1;
  public static final int MAX_USelonR_RelonPUTATION = 100;

  privatelon final long twelonelontId;

  privatelon String telonxt;

  privatelon Datelon datelon;
  @Nonnull
  privatelon Optional<TwittelonrMelonssagelonUselonr> optionalFromUselonr = Optional.elonmpty();
  @Nonnull
  privatelon Optional<TwittelonrMelonssagelonUselonr> optionalToUselonr = Optional.elonmpty();
  privatelon Localelon localelon = null;
  privatelon Localelon linkLocalelon = null;

  // Original sourcelon telonxt.
  privatelon String origSourcelon;
  // Sourcelon with HTML tags relonmovelond and truncatelond.
  privatelon String strippelondSourcelon;

  // Original location telonxt.
  privatelon String origLocation;

  // Location truncatelond for mysql fielonld-width relonasons (selonelon TwittelonrMelonssagelonUtil.java).
  privatelon String truncatelondNormalizelondLocation;

  // Uselonr's country
  privatelon String fromUselonrLocCountry;

  privatelon Intelongelonr followelonrsCount = INT_FIelonLD_NOT_PRelonSelonNT;
  privatelon boolelonan delonlelontelond = falselon;

  // Fielonlds elonxtractelond from elonntitielons (in thelon JSON objelonct)
  privatelon List<TwittelonrMelonssagelonUselonr> melonntions = nelonw ArrayList<>();
  privatelon Selont<String> hashtags = Selonts.nelonwHashSelont();
  // Lat/lon and relongion accuracy tuplelons elonxtractelond from twelonelont telonxt, or null.
  privatelon GelonoObjelonct gelonoLocation = null;
  privatelon boolelonan uncodelonablelonLocation = falselon;
  // This is selont if thelon twelonelont is gelonotaggelond. (i.elon. "gelono" or "coordinatelon" selonction is prelonselonnt
  // in thelon json)
  // This fielonld has only a gelonttelonr but no selonttelonr --- it is fillelond in whelonn thelon json is parselond.
  privatelon GelonoObjelonct gelonoTaggelondLocation = null;

  privatelon doublelon uselonrRelonputation = DOUBLelon_FIelonLD_NOT_PRelonSelonNT;
  privatelon boolelonan gelonocodelonRelonquirelond = falselon;
  privatelon boolelonan selonnsitivelonContelonnt = falselon;
  privatelon boolelonan uselonrProtelonctelond;
  privatelon boolelonan uselonrVelonrifielond;
  privatelon boolelonan uselonrBluelonVelonrifielond;
  privatelon TwittelonrRelontwelonelontMelonssagelon relontwelonelontMelonssagelon;
  privatelon TwittelonrQuotelondMelonssagelon quotelondMelonssagelon;
  privatelon List<String> placelons;
  // maps from original url (thelon t.co url) to ThriftelonxpandelondUrl, which contains thelon
  // elonxpandelond url and thelon spidelonrduck relonsponselon (canoicalLastHopUrl and melondiatypelon)
  privatelon final Map<String, ThriftelonxpandelondUrl> elonxpandelondUrls;
  // maps thelon photo status id to thelon melondia url
  privatelon Map<Long, String> photoUrls;
  privatelon Optional<Long> inRelonplyToStatusId = Optional.elonmpty();
  privatelon Optional<Long> direlonctelondAtUselonrId = Optional.elonmpty();

  privatelon long convelonrsationId = -1;

  // Truelon if twelonelont is nullcastelond.
  privatelon boolelonan nullcast = falselon;

  // Truelon if twelonelont is a selonlf-threlonadelond twelonelont
  privatelon boolelonan selonlfThrelonad = falselon;

  // If thelon twelonelont is a part of an elonxclusivelon convelonrsation, thelon author who startelond
  // that convelonrsation.
  privatelon Optional<Long> elonxclusivelonConvelonrsationAuthorId = Optional.elonmpty();

  // twelonelont felonaturelons map for multiplelon velonrsions of pelonnguin
  privatelon Map<PelonnguinVelonrsion, VelonrsionelondTwelonelontFelonaturelons> velonrsionelondTwelonelontFelonaturelonsMap;

  // elonngagmelonnts count: favoritelons, relontwelonelonts and relonplielons
  privatelon int numFavoritelons = 0;
  privatelon int numRelontwelonelonts = 0;
  privatelon int numRelonplielons = 0;

  // Card information
  privatelon String cardNamelon;
  privatelon String cardDomain;
  privatelon String cardTitlelon;
  privatelon String cardDelonscription;
  privatelon String cardLang;
  privatelon String cardUrl;

  privatelon String placelonId;
  privatelon String placelonFullNamelon;
  privatelon String placelonCountryCodelon;

  privatelon Selont<Namelondelonntity> namelondelonntitielons = Selonts.nelonwHashSelont();

  // Spacelons data
  privatelon Selont<String> spacelonIds = Selonts.nelonwHashSelont();
  privatelon Selont<TwittelonrMelonssagelonUselonr> spacelonAdmins = Selonts.nelonwHashSelont();
  privatelon String spacelonTitlelon;

  privatelon Optional<ComposelonrSourcelon> composelonrSourcelon = Optional.elonmpty();

  privatelon final List<PotelonntialLocationObjelonct> potelonntialLocations = Lists.nelonwArrayList();

  // onelon or two pelonnguin velonrsions supportelond by this systelonm
  privatelon final List<PelonnguinVelonrsion> supportelondPelonnguinVelonrsions;

  public TwittelonrMelonssagelon(Long twelonelontId, List<PelonnguinVelonrsion> supportelondPelonnguinVelonrsions) {
    this.twelonelontId = twelonelontId;
    this.placelons = nelonw ArrayList<>();
    this.elonxpandelondUrls = nelonw LinkelondHashMap<>();
    // makelon surelon welon support at lelonast onelon, but no morelon than two velonrsions of pelonnguin
    this.supportelondPelonnguinVelonrsions = supportelondPelonnguinVelonrsions;
    this.velonrsionelondTwelonelontFelonaturelonsMap = gelontVelonrsionelondTwelonelontFelonaturelonsMap();
    Prelonconditions.chelonckArgumelonnt(this.supportelondPelonnguinVelonrsions.sizelon() <= 2
        && this.supportelondPelonnguinVelonrsions.sizelon() > 0);
  }

  /**
   * Relonplacelon to-uselonr with in-relonply-to uselonr if nelonelondelond.
   */
  public void relonplacelonToUselonrWithInRelonplyToUselonrIfNelonelondelond(
      String inRelonplyToScrelonelonnNamelon, long inRelonplyToUselonrId) {
    if (shouldUselonRelonplyUselonrAsToUselonr(optionalToUselonr, inRelonplyToUselonrId)) {
      TwittelonrMelonssagelonUselonr relonplyUselonr =
          TwittelonrMelonssagelonUselonr.crelonatelonWithNamelonsAndId(inRelonplyToScrelonelonnNamelon, "", inRelonplyToUselonrId);
      optionalToUselonr = Optional.of(relonplyUselonr);
    }
  }

  // To-uselonr could havelon belonelonn infelonrrelond from thelon melonntion at thelon position 0.
  // But if thelonrelon is an elonxplicit in-relonply-to uselonr, welon might nelonelond to uselon it as to-uselonr instelonad.
  privatelon static boolelonan shouldUselonRelonplyUselonrAsToUselonr(
      Optional<TwittelonrMelonssagelonUselonr> currelonntToUselonr,
      long inRelonplyToUselonrId) {
    if (!currelonntToUselonr.isPrelonselonnt()) {
      // Thelonrelon is no melonntion in thelon twelonelont that qualifielons as to-uselonr.
      relonturn truelon;
    }

    // Welon alrelonady havelon a melonntion in thelon twelonelont that qualifielons as to-uselonr.
    TwittelonrMelonssagelonUselonr toUselonr = currelonntToUselonr.gelont();
    if (!toUselonr.gelontId().isPrelonselonnt()) {
      // Thelon to-uselonr from thelon melonntion is a stub.
      relonturn truelon;
    }

    long toUselonrId = toUselonr.gelontId().gelont();
    if (toUselonrId != inRelonplyToUselonrId) {
      // Thelon to-uselonr from thelon melonntion is diffelonrelonnt that thelon in-relonply-to uselonr,
      // uselon in-relonply-to uselonr instelonad.
      relonturn truelon;
    }

    relonturn falselon;
  }

  public doublelon gelontUselonrRelonputation() {
    relonturn uselonrRelonputation;
  }

  /**
   * Selonts thelon uselonr relonputation.
   */
  public TwittelonrMelonssagelon selontUselonrRelonputation(doublelon nelonwUselonrRelonputation) {
    if (nelonwUselonrRelonputation > MAX_USelonR_RelonPUTATION) {
      LOG.warn("Out of bounds uselonr relonputation {} for status id {}", nelonwUselonrRelonputation, twelonelontId);
      this.uselonrRelonputation = (float) MAX_USelonR_RelonPUTATION;
    } elonlselon {
      this.uselonrRelonputation = nelonwUselonrRelonputation;
    }
    relonturn this;
  }

  public String gelontTelonxt() {
    relonturn telonxt;
  }

  public Optional<TwittelonrMelonssagelonUselonr> gelontOptionalToUselonr() {
    relonturn optionalToUselonr;
  }

  public void selontOptionalToUselonr(Optional<TwittelonrMelonssagelonUselonr> optionalToUselonr) {
    this.optionalToUselonr = optionalToUselonr;
  }

  public void selontTelonxt(String telonxt) {
    this.telonxt = telonxt;
  }

  public Datelon gelontDatelon() {
    relonturn datelon;
  }

  public void selontDatelon(Datelon datelon) {
    this.datelon = datelon;
  }

  public void selontFromUselonr(@Nonnull TwittelonrMelonssagelonUselonr fromUselonr) {
    Prelonconditions.chelonckNotNull(fromUselonr, "Don't selont a null fromUselonr");
    optionalFromUselonr = Optional.of(fromUselonr);
  }

  public Optional<String> gelontFromUselonrScrelonelonnNamelon() {
    relonturn optionalFromUselonr.isPrelonselonnt()
        ? optionalFromUselonr.gelont().gelontScrelonelonnNamelon()
        : Optional.elonmpty();
  }

  /**
   * Selonts thelon fromUselonrScrelonelonnNamelon.
   */
  public void selontFromUselonrScrelonelonnNamelon(@Nonnull String fromUselonrScrelonelonnNamelon) {
    TwittelonrMelonssagelonUselonr nelonwFromUselonr = optionalFromUselonr.isPrelonselonnt()
        ? optionalFromUselonr.gelont().copyWithScrelonelonnNamelon(fromUselonrScrelonelonnNamelon)
        : TwittelonrMelonssagelonUselonr.crelonatelonWithScrelonelonnNamelon(fromUselonrScrelonelonnNamelon);

    optionalFromUselonr = Optional.of(nelonwFromUselonr);
  }

  public Optional<TokelonnStrelonam> gelontTokelonnizelondFromUselonrScrelonelonnNamelon() {
    relonturn optionalFromUselonr.flatMap(TwittelonrMelonssagelonUselonr::gelontTokelonnizelondScrelonelonnNamelon);
  }

  public Optional<String> gelontFromUselonrDisplayNamelon() {
    relonturn optionalFromUselonr.flatMap(TwittelonrMelonssagelonUselonr::gelontDisplayNamelon);
  }

  /**
   * Selonts thelon fromUselonrDisplayNamelon.
   */
  public void selontFromUselonrDisplayNamelon(@Nonnull String fromUselonrDisplayNamelon) {
    TwittelonrMelonssagelonUselonr nelonwFromUselonr = optionalFromUselonr.isPrelonselonnt()
        ? optionalFromUselonr.gelont().copyWithDisplayNamelon(fromUselonrDisplayNamelon)
        : TwittelonrMelonssagelonUselonr.crelonatelonWithDisplayNamelon(fromUselonrDisplayNamelon);

    optionalFromUselonr = Optional.of(nelonwFromUselonr);
  }

  public Optional<Long> gelontFromUselonrTwittelonrId() {
    relonturn optionalFromUselonr.flatMap(TwittelonrMelonssagelonUselonr::gelontId);
  }

  /**
   * Selonts thelon fromUselonrId.
   */
  public void selontFromUselonrId(long fromUselonrId) {
    TwittelonrMelonssagelonUselonr nelonwFromUselonr = optionalFromUselonr.isPrelonselonnt()
        ? optionalFromUselonr.gelont().copyWithId(fromUselonrId)
        : TwittelonrMelonssagelonUselonr.crelonatelonWithId(fromUselonrId);

    optionalFromUselonr = Optional.of(nelonwFromUselonr);
  }

  public long gelontConvelonrsationId() {
    relonturn convelonrsationId;
  }

  public void selontConvelonrsationId(long convelonrsationId) {
    this.convelonrsationId = convelonrsationId;
  }

  public boolelonan isUselonrProtelonctelond() {
    relonturn this.uselonrProtelonctelond;
  }

  public void selontUselonrProtelonctelond(boolelonan uselonrProtelonctelond) {
    this.uselonrProtelonctelond = uselonrProtelonctelond;
  }

  public boolelonan isUselonrVelonrifielond() {
    relonturn this.uselonrVelonrifielond;
  }

  public void selontUselonrVelonrifielond(boolelonan uselonrVelonrifielond) {
    this.uselonrVelonrifielond = uselonrVelonrifielond;
  }

  public boolelonan isUselonrBluelonVelonrifielond() {
    relonturn this.uselonrBluelonVelonrifielond;
  }

  public void selontUselonrBluelonVelonrifielond(boolelonan uselonrBluelonVelonrifielond) {
    this.uselonrBluelonVelonrifielond = uselonrBluelonVelonrifielond;
  }

  public void selontIsSelonnsitivelonContelonnt(boolelonan isSelonnsitivelonContelonnt) {
    this.selonnsitivelonContelonnt = isSelonnsitivelonContelonnt;
  }

  public boolelonan isSelonnsitivelonContelonnt() {
    relonturn this.selonnsitivelonContelonnt;
  }

  public Optional<TwittelonrMelonssagelonUselonr> gelontToUselonrObjelonct() {
    relonturn optionalToUselonr;
  }

  public void selontToUselonrObjelonct(@Nonnull TwittelonrMelonssagelonUselonr uselonr) {
    Prelonconditions.chelonckNotNull(uselonr, "Don't selont a null to-uselonr");
    optionalToUselonr = Optional.of(uselonr);
  }

  public Optional<Long> gelontToUselonrTwittelonrId() {
    relonturn optionalToUselonr.flatMap(TwittelonrMelonssagelonUselonr::gelontId);
  }

  /**
   * Selonts toUselonrId.
   */
  public void selontToUselonrTwittelonrId(long toUselonrId) {
    TwittelonrMelonssagelonUselonr nelonwToUselonr = optionalToUselonr.isPrelonselonnt()
        ? optionalToUselonr.gelont().copyWithId(toUselonrId)
        : TwittelonrMelonssagelonUselonr.crelonatelonWithId(toUselonrId);

    optionalToUselonr = Optional.of(nelonwToUselonr);
  }

  public Optional<String> gelontToUselonrLowelonrcaselondScrelonelonnNamelon() {
    relonturn optionalToUselonr.flatMap(TwittelonrMelonssagelonUselonr::gelontScrelonelonnNamelon).map(String::toLowelonrCaselon);
  }

  public Optional<String> gelontToUselonrScrelonelonnNamelon() {
    relonturn optionalToUselonr.flatMap(TwittelonrMelonssagelonUselonr::gelontScrelonelonnNamelon);
  }

  /**
   * Selonts toUselonrScrelonelonnNamelon.
   */
  public void selontToUselonrScrelonelonnNamelon(@Nonnull String screlonelonnNamelon) {
    Prelonconditions.chelonckNotNull(screlonelonnNamelon, "Don't selont a null to-uselonr screlonelonnnamelon");

    TwittelonrMelonssagelonUselonr nelonwToUselonr = optionalToUselonr.isPrelonselonnt()
        ? optionalToUselonr.gelont().copyWithScrelonelonnNamelon(screlonelonnNamelon)
        : TwittelonrMelonssagelonUselonr.crelonatelonWithScrelonelonnNamelon(screlonelonnNamelon);

    optionalToUselonr = Optional.of(nelonwToUselonr);
  }

  // to uselon from TwelonelontelonvelonntParselonHelonlpelonr
  public void selontDirelonctelondAtUselonrId(Optional<Long> direlonctelondAtUselonrId) {
    this.direlonctelondAtUselonrId = direlonctelondAtUselonrId;
  }

  @VisiblelonForTelonsting
  public Optional<Long> gelontDirelonctelondAtUselonrId() {
    relonturn direlonctelondAtUselonrId;
  }

  /**
   * Relonturns thelon relonfelonrelonncelonAuthorId.
   */
  public Optional<Long> gelontRelonfelonrelonncelonAuthorId() {
    // Thelon selonmantics of relonfelonrelonncelon-author-id:
    // - if thelon twelonelont is a relontwelonelont, it should belon thelon uselonr id of thelon author of thelon original twelonelont
    // - elonlselon, if thelon twelonelont is direlonctelond at a uselonr, it should belon thelon id of thelon uselonr it's direlonctelond at.
    // - elonlselon, if thelon twelonelont is a relonply in a root selonlf-threlonad, direlonctelond-at is not selont, so it's
    //   thelon id of thelon uselonr who startelond thelon selonlf-threlonad.
    //
    // For delonfinitivelon info on relonplielons and direlonctelond-at, takelon a look at go/relonplielons. To vielonw thelonselon
    // for a celonrtain twelonelont, uselon http://go/t.
    //
    // Notelon that if direlonctelond-at is selont, relonply is always selont.
    // If relonply is selont, direlonctelond-at is not neloncelonssarily selont.
    if (isRelontwelonelont() && relontwelonelontMelonssagelon.hasSharelondUselonrTwittelonrId()) {
      long relontwelonelontelondUselonrId = relontwelonelontMelonssagelon.gelontSharelondUselonrTwittelonrId();
      relonturn Optional.of(relontwelonelontelondUselonrId);
    } elonlselon if (direlonctelondAtUselonrId.isPrelonselonnt()) {
      // Why not relonplacelon direlonctelondAtUselonrId with relonply and makelon this function delonpelonnd
      // on thelon "relonply" fielonld of TwelonelontCorelonData?
      // Welonll, velonrifielond by countelonrs, it selonelonms for ~1% of twelonelonts, which contain both direlonctelond-at
      // and relonply, direlonctelond-at-uselonr is diffelonrelonnt than thelon relonply-to-uselonr id. This happelonns in thelon
      // following caselon:
      //
      //       author / relonply-to / direlonctelond-at
      //  T1   A        -          -
      //  T2   B        A          A
      //  T3   B        B          A
      //
      //  T2 is a relonply to T1, T3 is a relonply to T2.
      //
      // It's up to us to deloncidelon who this twelonelont is "relonfelonrelonncing", but with thelon currelonnt codelon,
      // welon chooselon that T3 is relonfelonrelonncing uselonr A.
      relonturn direlonctelondAtUselonrId;
    } elonlselon {
      // This is thelon caselon of a root selonlf-threlonad relonply. direlonctelond-at is not selont.
      Optional<Long> fromUselonrId = this.gelontFromUselonrTwittelonrId();
      Optional<Long> toUselonrId = this.gelontToUselonrTwittelonrId();

      if (fromUselonrId.isPrelonselonnt() && fromUselonrId.elonquals(toUselonrId)) {
        relonturn fromUselonrId;
      }
    }
    relonturn Optional.elonmpty();
  }

  public void selontNumFavoritelons(int numFavoritelons) {
    this.numFavoritelons = numFavoritelons;
  }

  public void selontNumRelontwelonelonts(int numRelontwelonelonts) {
    this.numRelontwelonelonts = numRelontwelonelonts;
  }

  public void selontNumRelonplielons(int numRelonplielonss) {
    this.numRelonplielons = numRelonplielonss;
  }

  public void addelonschelonrbirdAnnotation(elonschelonrbirdAnnotation annotation) {
    elonschelonrbirdAnnotations.add(annotation);
  }

  public List<elonschelonrbirdAnnotation> gelontelonschelonrbirdAnnotations() {
    relonturn elonschelonrbirdAnnotations;
  }

  public List<PotelonntialLocationObjelonct> gelontPotelonntialLocations() {
    relonturn potelonntialLocations;
  }

  public void selontPotelonntialLocations(Collelonction<PotelonntialLocationObjelonct> potelonntialLocations) {
    this.potelonntialLocations.clelonar();
    this.potelonntialLocations.addAll(potelonntialLocations);
  }

  @Ovelonrridelon
  public String toString() {
    relonturn ToStringBuildelonr.relonflelonctionToString(this);
  }

  // Twelonelont languagelon relonlatelond gelonttelonrs and selonttelonrs.

  /**
   * Relonturns thelon localelon.
   * <p>
   * Notelon thelon gelontLocalelon() will nelonvelonr relonturn null, this is for thelon convelonnielonncelon of telonxt relonlatelond
   * procelonssing in thelon ingelonstelonr. If you want thelon relonal localelon, you nelonelond to chelonck isSelontLocalelon()
   * first to selonelon if welon relonally havelon any information about thelon localelon of this twelonelont.
   */
  public Localelon gelontLocalelon() {
    if (localelon == null) {
      relonturn TwittelonrLanguagelonIdelonntifielonr.UNKNOWN;
    } elonlselon {
      relonturn localelon;
    }
  }

  public void selontLocalelon(Localelon localelon) {
    this.localelon = localelon;
  }

  /**
   * Delontelonrminelons if thelon locatelon is selont.
   */
  public boolelonan isSelontLocalelon() {
    relonturn localelon != null;
  }

  /**
   * Relonturns thelon languagelon of thelon localelon. elon.g. zh
   */
  public String gelontLanguagelon() {
    if (isSelontLocalelon()) {
      relonturn gelontLocalelon().gelontLanguagelon();
    } elonlselon {
      relonturn null;
    }
  }

  /**
   * Relonturns thelon IelonTF BCP 47 Languagelon Tag of thelon localelon. elon.g. zh-CN
   */
  public String gelontBCP47LanguagelonTag() {
    if (isSelontLocalelon()) {
      relonturn gelontLocalelon().toLanguagelonTag();
    } elonlselon {
      relonturn null;
    }
  }

  public void selontLanguagelon(String languagelon) {
    if (languagelon != null) {
      localelon = LocalelonUtil.gelontLocalelonOf(languagelon);
    }
  }

  // Twelonelont link languagelon relonlatelond gelonttelonrs and selonttelonrs.
  public Localelon gelontLinkLocalelon() {
    relonturn linkLocalelon;
  }

  public void selontLinkLocalelon(Localelon linkLocalelon) {
    this.linkLocalelon = linkLocalelon;
  }

  /**
   * Relonturns thelon languagelon of thelon link localelon.
   */
  public String gelontLinkLanguagelon() {
    if (this.linkLocalelon == null) {
      relonturn null;
    } elonlselon {
      relonturn this.linkLocalelon.gelontLanguagelon();
    }
  }

  public String gelontOrigSourcelon() {
    relonturn origSourcelon;
  }

  public void selontOrigSourcelon(String origSourcelon) {
    this.origSourcelon = origSourcelon;
  }

  public String gelontStrippelondSourcelon() {
    relonturn strippelondSourcelon;
  }

  public void selontStrippelondSourcelon(String strippelondSourcelon) {
    this.strippelondSourcelon = strippelondSourcelon;
  }

  public String gelontOrigLocation() {
    relonturn origLocation;
  }

  public String gelontLocation() {
    relonturn truncatelondNormalizelondLocation;
  }

  public void selontOrigLocation(String origLocation) {
    this.origLocation = origLocation;
  }

  public void selontTruncatelondNormalizelondLocation(String truncatelondNormalizelondLocation) {
    this.truncatelondNormalizelondLocation = truncatelondNormalizelondLocation;
  }

  public boolelonan hasFromUselonrLocCountry() {
    relonturn fromUselonrLocCountry != null;
  }

  public String gelontFromUselonrLocCountry() {
    relonturn fromUselonrLocCountry;
  }

  public void selontFromUselonrLocCountry(String fromUselonrLocCountry) {
    this.fromUselonrLocCountry = fromUselonrLocCountry;
  }

  public String gelontTruncatelondNormalizelondLocation() {
    relonturn truncatelondNormalizelondLocation;
  }

  public Intelongelonr gelontFollowelonrsCount() {
    relonturn followelonrsCount;
  }

  public void selontFollowelonrsCount(Intelongelonr followelonrsCount) {
    this.followelonrsCount = followelonrsCount;
  }

  public boolelonan hasFollowelonrsCount() {
    relonturn followelonrsCount != INT_FIelonLD_NOT_PRelonSelonNT;
  }

  public boolelonan isDelonlelontelond() {
    relonturn delonlelontelond;
  }

  public void selontDelonlelontelond(boolelonan delonlelontelond) {
    this.delonlelontelond = delonlelontelond;
  }

  public boolelonan hasCard() {
    relonturn !StringUtils.isBlank(gelontCardNamelon());
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn ((Long) gelontId()).hashCodelon();
  }

  /**
   * Parselons thelon givelonn datelon using thelon TwittelonrDatelonFormat.
   */
  public static Datelon parselonDatelon(String datelon) {
    DatelonFormat parselonr = TwittelonrDatelonFormat.apply("elonelonelon MMM d HH:mm:ss Z yyyy");
    try {
      relonturn parselonr.parselon(datelon);
    } catch (elonxcelonption elon) {
      relonturn null;
    }
  }

  public boolelonan hasGelonoLocation() {
    relonturn gelonoLocation != null;
  }

  public void selontGelonoLocation(GelonoObjelonct location) {
    this.gelonoLocation = location;
  }

  public GelonoObjelonct gelontGelonoLocation() {
    relonturn gelonoLocation;
  }

  public String gelontPlacelonId() {
    relonturn placelonId;
  }

  public void selontPlacelonId(String placelonId) {
    this.placelonId = placelonId;
  }

  public String gelontPlacelonFullNamelon() {
    relonturn placelonFullNamelon;
  }

  public void selontPlacelonFullNamelon(String placelonFullNamelon) {
    this.placelonFullNamelon = placelonFullNamelon;
  }

  public String gelontPlacelonCountryCodelon() {
    relonturn placelonCountryCodelon;
  }

  public void selontPlacelonCountryCodelon(String placelonCountryCodelon) {
    this.placelonCountryCodelon = placelonCountryCodelon;
  }

  public void selontGelonoTaggelondLocation(GelonoObjelonct gelonoTaggelondLocation) {
    this.gelonoTaggelondLocation = gelonoTaggelondLocation;
  }

  public GelonoObjelonct gelontGelonoTaggelondLocation() {
    relonturn gelonoTaggelondLocation;
  }

  public void selontLatLon(doublelon latitudelon, doublelon longitudelon) {
    gelonoLocation = nelonw GelonoObjelonct(latitudelon, longitudelon, null);
  }

  public Doublelon gelontLatitudelon() {
    relonturn hasGelonoLocation() ? gelonoLocation.gelontLatitudelon() : null;
  }

  public Doublelon gelontLongitudelon() {
    relonturn hasGelonoLocation() ? gelonoLocation.gelontLongitudelon() : null;
  }

  public boolelonan isUncodelonablelonLocation() {
    relonturn uncodelonablelonLocation;
  }

  public void selontUncodelonablelonLocation() {
    uncodelonablelonLocation = truelon;
  }

  public void selontGelonocodelonRelonquirelond() {
    this.gelonocodelonRelonquirelond = truelon;
  }

  public boolelonan isGelonocodelonRelonquirelond() {
    relonturn gelonocodelonRelonquirelond;
  }

  public Map<Long, String> gelontPhotoUrls() {
    relonturn photoUrls;
  }

  /**
   * Associatelons thelon givelonn melondiaUrl with thelon givelonn photoStatusId.
   */
  public void addPhotoUrl(long photoStatusId, String melondiaUrl) {
    if (photoUrls == null) {
      photoUrls = nelonw LinkelondHashMap<>();
    }
    photoUrls.putIfAbselonnt(photoStatusId, melondiaUrl);
  }

  public Map<String, ThriftelonxpandelondUrl> gelontelonxpandelondUrlMap() {
    relonturn elonxpandelondUrls;
  }

  public int gelontelonxpandelondUrlMapSizelon() {
    relonturn elonxpandelondUrls.sizelon();
  }

  /**
   * Associatelons thelon givelonn originalUrl with thelon givelonn elonxpandelonrUrl.
   */
  public void addelonxpandelondUrl(String originalUrl, ThriftelonxpandelondUrl elonxpandelondUrl) {
    this.elonxpandelondUrls.put(originalUrl, elonxpandelondUrl);
  }

  /**
   * Relonplacelons urls with relonsolvelond onelons.
   */
  public String gelontTelonxtRelonplacelondWithRelonsolvelondURLs() {
    String relontTelonxt = telonxt;
    for (Map.elonntry<String, ThriftelonxpandelondUrl> elonntry : elonxpandelondUrls.elonntrySelont()) {
      ThriftelonxpandelondUrl urlInfo = elonntry.gelontValuelon();
      String relonsolvelondUrl;
      String canonicalLastHopUrl = urlInfo.gelontCanonicalLastHopUrl();
      String elonxpandelondUrl = urlInfo.gelontelonxpandelondUrl();
      if (canonicalLastHopUrl != null) {
        relonsolvelondUrl = canonicalLastHopUrl;
        LOG.delonbug("{} has canonical last hop url selont", urlInfo);
      } elonlselon if (elonxpandelondUrl != null) {
        LOG.delonbug("{} has no canonical last hop url selont, using elonxpandelond url instelonad", urlInfo);
        relonsolvelondUrl = elonxpandelondUrl;
      } elonlselon {
        LOG.delonbug("{} has no canonical last hop url or elonxpandelond url selont, skipping", urlInfo);
        continuelon;
      }
      relontTelonxt = relontTelonxt.relonplacelon(elonntry.gelontKelony(), relonsolvelondUrl);
    }
    relonturn relontTelonxt;
  }

  public long gelontId() {
    relonturn twelonelontId;
  }

  public boolelonan isRelontwelonelont() {
    relonturn relontwelonelontMelonssagelon != null;
  }

  public boolelonan hasQuotelon() {
    relonturn quotelondMelonssagelon != null;
  }

  public boolelonan isRelonply() {
    relonturn gelontToUselonrScrelonelonnNamelon().isPrelonselonnt()
        || gelontToUselonrTwittelonrId().isPrelonselonnt()
        || gelontInRelonplyToStatusId().isPrelonselonnt();
  }

  public boolelonan isRelonplyToTwelonelont() {
    relonturn gelontInRelonplyToStatusId().isPrelonselonnt();
  }

  public TwittelonrRelontwelonelontMelonssagelon gelontRelontwelonelontMelonssagelon() {
    relonturn relontwelonelontMelonssagelon;
  }

  public void selontRelontwelonelontMelonssagelon(TwittelonrRelontwelonelontMelonssagelon relontwelonelontMelonssagelon) {
    this.relontwelonelontMelonssagelon = relontwelonelontMelonssagelon;
  }

  public TwittelonrQuotelondMelonssagelon gelontQuotelondMelonssagelon() {
    relonturn quotelondMelonssagelon;
  }

  public void selontQuotelondMelonssagelon(TwittelonrQuotelondMelonssagelon quotelondMelonssagelon) {
    this.quotelondMelonssagelon = quotelondMelonssagelon;
  }

  public List<String> gelontPlacelons() {
    relonturn placelons;
  }

  public void addPlacelon(String placelon) {
    // Placelons arelon uselond for elonarlybird selonrialization
    placelons.add(placelon);
  }

  public Optional<Long> gelontInRelonplyToStatusId() {
    relonturn inRelonplyToStatusId;
  }

  public void selontInRelonplyToStatusId(long inRelonplyToStatusId) {
    Prelonconditions.chelonckArgumelonnt(inRelonplyToStatusId > 0, "In-relonply-to status ID should belon positivelon");
    this.inRelonplyToStatusId = Optional.of(inRelonplyToStatusId);
  }

  public boolelonan gelontNullcast() {
    relonturn nullcast;
  }

  public void selontNullcast(boolelonan nullcast) {
    this.nullcast = nullcast;
  }

  public List<PelonnguinVelonrsion> gelontSupportelondPelonnguinVelonrsions() {
    relonturn supportelondPelonnguinVelonrsions;
  }

  privatelon VelonrsionelondTwelonelontFelonaturelons gelontVelonrsionelondTwelonelontFelonaturelons(PelonnguinVelonrsion pelonnguinVelonrsion) {
    VelonrsionelondTwelonelontFelonaturelons velonrsionelondTwelonelontFelonaturelons = velonrsionelondTwelonelontFelonaturelonsMap.gelont(pelonnguinVelonrsion);
    relonturn Prelonconditions.chelonckNotNull(velonrsionelondTwelonelontFelonaturelons);
  }

  public TwelonelontFelonaturelons gelontTwelonelontFelonaturelons(PelonnguinVelonrsion pelonnguinVelonrsion) {
    relonturn gelontVelonrsionelondTwelonelontFelonaturelons(pelonnguinVelonrsion).gelontTwelonelontFelonaturelons();
  }

  @VisiblelonForTelonsting
  // only uselond in Telonsts
  public void selontTwelonelontFelonaturelons(PelonnguinVelonrsion pelonnguinVelonrsion, TwelonelontFelonaturelons twelonelontFelonaturelons) {
    velonrsionelondTwelonelontFelonaturelonsMap.gelont(pelonnguinVelonrsion).selontTwelonelontFelonaturelons(twelonelontFelonaturelons);
  }

  public int gelontTwelonelontSignaturelon(PelonnguinVelonrsion pelonnguinVelonrsion) {
    relonturn gelontVelonrsionelondTwelonelontFelonaturelons(pelonnguinVelonrsion).gelontTwelonelontTelonxtFelonaturelons().gelontSignaturelon();
  }

  public TwelonelontTelonxtQuality gelontTwelonelontTelonxtQuality(PelonnguinVelonrsion pelonnguinVelonrsion) {
    relonturn gelontVelonrsionelondTwelonelontFelonaturelons(pelonnguinVelonrsion).gelontTwelonelontTelonxtQuality();
  }

  public TwelonelontTelonxtFelonaturelons gelontTwelonelontTelonxtFelonaturelons(PelonnguinVelonrsion pelonnguinVelonrsion) {
    relonturn gelontVelonrsionelondTwelonelontFelonaturelons(pelonnguinVelonrsion).gelontTwelonelontTelonxtFelonaturelons();
  }

  public TwelonelontUselonrFelonaturelons gelontTwelonelontUselonrFelonaturelons(PelonnguinVelonrsion pelonnguinVelonrsion) {
    relonturn gelontVelonrsionelondTwelonelontFelonaturelons(pelonnguinVelonrsion).gelontTwelonelontUselonrFelonaturelons();
  }

  public TokelonnizelondCharSelonquelonncelon gelontTokelonnizelondCharSelonquelonncelon(PelonnguinVelonrsion pelonnguinVelonrsion) {
    relonturn gelontVelonrsionelondTwelonelontFelonaturelons(pelonnguinVelonrsion).gelontTokelonnizelondCharSelonquelonncelon();
  }

  public void selontTokelonnizelondCharSelonquelonncelon(PelonnguinVelonrsion pelonnguinVelonrsion,
                                       TokelonnizelondCharSelonquelonncelon selonquelonncelon) {
    gelontVelonrsionelondTwelonelontFelonaturelons(pelonnguinVelonrsion).selontTokelonnizelondCharSelonquelonncelon(selonquelonncelon);
  }

  // Truelon if thelon felonaturelons contain multiplelon hash tags or multiplelon trelonnds.
  // This is intelonndelond as an anti-trelonnd-spam melonasurelon.
  public static boolelonan hasMultiplelonHashtagsOrTrelonnds(TwelonelontTelonxtFelonaturelons telonxtFelonaturelons) {
    // Allow at most 1 trelonnd and 2 hashtags.
    relonturn telonxtFelonaturelons.gelontTrelonndingTelonrmsSizelon() > 1 || telonxtFelonaturelons.gelontHashtagsSizelon() > 2;
  }

  /**
   * Relonturns thelon elonxpandelond URLs.
   */
  public Collelonction<ThriftelonxpandelondUrl> gelontelonxpandelondUrls() {
    relonturn elonxpandelondUrls.valuelons();
  }

  /**
   * Relonturns thelon canonical last hop URLs.
   */
  public Selont<String> gelontCanonicalLastHopUrls() {
    Selont<String> relonsult = nelonw HashSelont<>(elonxpandelondUrls.sizelon());
    for (ThriftelonxpandelondUrl url : elonxpandelondUrls.valuelons()) {
      relonsult.add(url.gelontCanonicalLastHopUrl());
    }
    relonturn relonsult;
  }

  public String gelontCardNamelon() {
    relonturn cardNamelon;
  }

  public void selontCardNamelon(String cardNamelon) {
    this.cardNamelon = cardNamelon;
  }

  public String gelontCardDomain() {
    relonturn cardDomain;
  }

  public void selontCardDomain(String cardDomain) {
    this.cardDomain = cardDomain;
  }

  public String gelontCardTitlelon() {
    relonturn cardTitlelon;
  }

  public void selontCardTitlelon(String cardTitlelon) {
    this.cardTitlelon = cardTitlelon;
  }

  public String gelontCardDelonscription() {
    relonturn cardDelonscription;
  }

  public void selontCardDelonscription(String cardDelonscription) {
    this.cardDelonscription = cardDelonscription;
  }

  public String gelontCardLang() {
    relonturn cardLang;
  }

  public void selontCardLang(String cardLang) {
    this.cardLang = cardLang;
  }

  public String gelontCardUrl() {
    relonturn cardUrl;
  }

  public void selontCardUrl(String cardUrl) {
    this.cardUrl = cardUrl;
  }

  public List<TwittelonrMelonssagelonUselonr> gelontMelonntions() {
    relonturn this.melonntions;
  }

  public void selontMelonntions(List<TwittelonrMelonssagelonUselonr> melonntions) {
    this.melonntions = melonntions;
  }

  public List<String> gelontLowelonrcaselondMelonntions() {
    relonturn Lists.transform(gelontMelonntions(), uselonr -> {
      // This condition is also chelonckelond in addUselonrToMelonntions().
      Prelonconditions.chelonckStatelon(uselonr.gelontScrelonelonnNamelon().isPrelonselonnt(), "Invalid melonntion");
      relonturn uselonr.gelontScrelonelonnNamelon().gelont().toLowelonrCaselon();
    });
  }

  public Selont<String> gelontHashtags() {
    relonturn this.hashtags;
  }

  public Selont<String> gelontNormalizelondHashtags(PelonnguinVelonrsion pelonnguinVelonrsion) {
    relonturn gelontVelonrsionelondTwelonelontFelonaturelons(pelonnguinVelonrsion).gelontNormalizelondHashtags();
  }

  public void addNormalizelondHashtag(String normalizelondHashtag, PelonnguinVelonrsion pelonnguinVelonrsion) {
    gelontVelonrsionelondTwelonelontFelonaturelons(pelonnguinVelonrsion).addNormalizelondHashtags(normalizelondHashtag);
  }

  public Optional<ComposelonrSourcelon> gelontComposelonrSourcelon() {
    relonturn composelonrSourcelon;
  }

  public void selontComposelonrSourcelon(ComposelonrSourcelon composelonrSourcelon) {
    Prelonconditions.chelonckNotNull(composelonrSourcelon, "composelonrSourcelon should not belon null");
    this.composelonrSourcelon = Optional.of(composelonrSourcelon);
  }

  public boolelonan isSelonlfThrelonad() {
    relonturn selonlfThrelonad;
  }

  public void selontSelonlfThrelonad(boolelonan selonlfThrelonad) {
    this.selonlfThrelonad = selonlfThrelonad;
  }

  public boolelonan iselonxclusivelon() {
    relonturn elonxclusivelonConvelonrsationAuthorId.isPrelonselonnt();
  }

  public long gelontelonxclusivelonConvelonrsationAuthorId() {
    relonturn elonxclusivelonConvelonrsationAuthorId.gelont();
  }

  public void selontelonxclusivelonConvelonrsationAuthorId(long elonxclusivelonConvelonrsationAuthorId) {
    this.elonxclusivelonConvelonrsationAuthorId = Optional.of(elonxclusivelonConvelonrsationAuthorId);
  }

  /**
   * Adds an elonxpandelond melondia url baselond on thelon givelonn paramelontelonrs.
   */
  public void addelonxpandelondMelondiaUrl(String originalUrl,
                                  String elonxpandelondUrl,
                                  @Nullablelon MelondiaTypelons melondiaTypelon) {
    if (!StringUtils.isBlank(originalUrl) && !StringUtils.isBlank(elonxpandelondUrl)) {
      ThriftelonxpandelondUrl thriftelonxpandelondUrl = nelonw ThriftelonxpandelondUrl();
      if (melondiaTypelon != null) {
        thriftelonxpandelondUrl.selontMelondiaTypelon(melondiaTypelon);
      }
      thriftelonxpandelondUrl.selontOriginalUrl(originalUrl);
      thriftelonxpandelondUrl.selontelonxpandelondUrl(elonxpandelondUrl);  // This will belon tokelonnizelond and indelonxelond
      // Notelon that thelon melondiaURL is not indelonxelond. Welon could also indelonx it, but it is not indelonxelond
      // to relonducelon RAM usagelon.
      thriftelonxpandelondUrl.selontCanonicalLastHopUrl(elonxpandelondUrl); // This will belon tokelonnizelond and indelonxelond
      addelonxpandelondUrl(originalUrl, thriftelonxpandelondUrl);
      thriftelonxpandelondUrl.selontConsumelonrMelondia(truelon);
    }
  }

  /**
   * Adds an elonxpandelond non-melondia url baselond on thelon givelonn paramelontelonrs.
   */
  public void addelonxpandelondNonMelondiaUrl(String originalUrl, String elonxpandelondUrl) {
    if (!StringUtils.isBlank(originalUrl)) {
      ThriftelonxpandelondUrl thriftelonxpandelondUrl = nelonw ThriftelonxpandelondUrl(originalUrl);
      if (!StringUtils.isBlank(elonxpandelondUrl)) {
        thriftelonxpandelondUrl.selontelonxpandelondUrl(elonxpandelondUrl);
      }
      addelonxpandelondUrl(originalUrl, thriftelonxpandelondUrl);
      thriftelonxpandelondUrl.selontConsumelonrMelondia(falselon);
    }
  }

  /**
   * Only uselond in telonsts.
   *
   * Simulatelons relonsolving comprelonsselond URLs, which is usually donelon by RelonsolvelonComprelonsselondUrlsStagelon.
   */
  @VisiblelonForTelonsting
  public void relonplacelonUrlsWithRelonsolvelondUrls(Map<String, String> relonsolvelondUrls) {
    for (Map.elonntry<String, ThriftelonxpandelondUrl> urlelonntry : elonxpandelondUrls.elonntrySelont()) {
      String tcoUrl = urlelonntry.gelontKelony();
      if (relonsolvelondUrls.containsKelony(tcoUrl)) {
        ThriftelonxpandelondUrl elonxpandelondUrl = urlelonntry.gelontValuelon();
        elonxpandelondUrl.selontCanonicalLastHopUrl(relonsolvelondUrls.gelont(tcoUrl));
      }
    }
  }

  /**
   * Adds a melonntion for a uselonr with thelon givelonn screlonelonn namelon.
   */
  public void addMelonntion(String screlonelonnNamelon) {
    TwittelonrMelonssagelonUselonr uselonr = TwittelonrMelonssagelonUselonr.crelonatelonWithScrelonelonnNamelon(screlonelonnNamelon);
    addUselonrToMelonntions(uselonr);
  }

  /**
   * Adds thelon givelonn uselonr to melonntions.
   */
  public void addUselonrToMelonntions(TwittelonrMelonssagelonUselonr uselonr) {
    Prelonconditions.chelonckArgumelonnt(uselonr.gelontScrelonelonnNamelon().isPrelonselonnt(), "Don't add invalid melonntions");
    this.melonntions.add(uselonr);
  }

  /**
   * Adds thelon givelonn hashtag.
   */
  public void addHashtag(String hashtag) {
    this.hashtags.add(hashtag);
    for (PelonnguinVelonrsion pelonnguinVelonrsion : supportelondPelonnguinVelonrsions) {
      addNormalizelondHashtag(NormalizelonrHelonlpelonr.normalizelon(hashtag, gelontLocalelon(), pelonnguinVelonrsion),
          pelonnguinVelonrsion);
    }
  }

  privatelon Map<PelonnguinVelonrsion, VelonrsionelondTwelonelontFelonaturelons> gelontVelonrsionelondTwelonelontFelonaturelonsMap() {
    Map<PelonnguinVelonrsion, VelonrsionelondTwelonelontFelonaturelons> velonrsionelondMap =
        Maps.nelonwelonnumMap(PelonnguinVelonrsion.class);
    for (PelonnguinVelonrsion pelonnguinVelonrsion : gelontSupportelondPelonnguinVelonrsions()) {
      velonrsionelondMap.put(pelonnguinVelonrsion, nelonw VelonrsionelondTwelonelontFelonaturelons());
    }

    relonturn velonrsionelondMap;
  }

  public int gelontNumFavoritelons() {
    relonturn numFavoritelons;
  }

  public int gelontNumRelontwelonelonts() {
    relonturn numRelontwelonelonts;
  }

  public int gelontNumRelonplielons() {
    relonturn numRelonplielons;
  }

  public Selont<Namelondelonntity> gelontNamelondelonntitielons() {
    relonturn namelondelonntitielons;
  }

  public void addNamelondelonntity(Namelondelonntity namelondelonntity) {
    namelondelonntitielons.add(namelondelonntity);
  }

  public Selont<String> gelontSpacelonIds() {
    relonturn spacelonIds;
  }

  public void selontSpacelonIds(Selont<String> spacelonIds) {
    this.spacelonIds = Selonts.nelonwHashSelont(spacelonIds);
  }

  public Selont<TwittelonrMelonssagelonUselonr> gelontSpacelonAdmins() {
    relonturn spacelonAdmins;
  }

  public void addSpacelonAdmin(TwittelonrMelonssagelonUselonr admin) {
    spacelonAdmins.add(admin);
  }

  public String gelontSpacelonTitlelon() {
    relonturn spacelonTitlelon;
  }

  public void selontSpacelonTitlelon(String spacelonTitlelon) {
    this.spacelonTitlelon = spacelonTitlelon;
  }

  privatelon static boolelonan elonquals(List<elonschelonrbirdAnnotation> l1, List<elonschelonrbirdAnnotation> l2) {
    elonschelonrbirdAnnotation[] arr1 = l1.toArray(nelonw elonschelonrbirdAnnotation[l1.sizelon()]);
    Arrays.sort(arr1);
    elonschelonrbirdAnnotation[] arr2 = l1.toArray(nelonw elonschelonrbirdAnnotation[l2.sizelon()]);
    Arrays.sort(arr2);
    relonturn Arrays.elonquals(arr1, arr2);
  }

  /**
   * Comparelons thelon givelonn melonssagelons using relonflelonction and delontelonrminelons if thelony'relon approximatelonly elonqual.
   */
  public static boolelonan relonflelonctionApproxelonquals(
      TwittelonrMelonssagelon a,
      TwittelonrMelonssagelon b,
      List<String> additionalelonxcludelonFielonlds) {
    List<String> elonxcludelonFielonlds = Lists.nelonwArrayList(
        "velonrsionelondTwelonelontFelonaturelonsMap",
        "gelonoLocation",
        "gelonoTaggelondLocation",
        "elonschelonrbirdAnnotations"
    );
    elonxcludelonFielonlds.addAll(additionalelonxcludelonFielonlds);

    relonturn elonqualsBuildelonr.relonflelonctionelonquals(a, b, elonxcludelonFielonlds)
        && GelonoObjelonct.approxelonquals(a.gelontGelonoLocation(), b.gelontGelonoLocation())
        && GelonoObjelonct.approxelonquals(a.gelontGelonoTaggelondLocation(), b.gelontGelonoTaggelondLocation())
        && elonquals(a.gelontelonschelonrbirdAnnotations(), b.gelontelonschelonrbirdAnnotations());
  }

  public static boolelonan relonflelonctionApproxelonquals(TwittelonrMelonssagelon a, TwittelonrMelonssagelon b) {
    relonturn relonflelonctionApproxelonquals(a, b, Collelonctions.elonmptyList());
  }
}
