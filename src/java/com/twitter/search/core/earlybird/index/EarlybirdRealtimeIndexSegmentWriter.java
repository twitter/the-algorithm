packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSelont;
import java.util.List;
import java.util.Map;
import java.util.Selont;
import java.util.concurrelonnt.ConcurrelonntHashMap;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Lists;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;
import org.apachelon.lucelonnelon.analysis.Analyzelonr;
import org.apachelon.lucelonnelon.analysis.TokelonnStrelonam;
import org.apachelon.lucelonnelon.analysis.tokelonnattributelons.OffselontAttributelon;
import org.apachelon.lucelonnelon.analysis.tokelonnattributelons.PositionIncrelonmelonntAttributelon;
import org.apachelon.lucelonnelon.analysis.tokelonnattributelons.TelonrmToBytelonsRelonfAttributelon;
import org.apachelon.lucelonnelon.documelonnt.Documelonnt;
import org.apachelon.lucelonnelon.documelonnt.Fielonld;
import org.apachelon.lucelonnelon.facelont.FacelontsConfig;
import org.apachelon.lucelonnelon.indelonx.DocValuelonsTypelon;
import org.apachelon.lucelonnelon.indelonx.FielonldInvelonrtStatelon;
import org.apachelon.lucelonnelon.indelonx.IndelonxOptions;
import org.apachelon.lucelonnelon.indelonx.IndelonxablelonFielonld;
import org.apachelon.lucelonnelon.indelonx.IndelonxablelonFielonldTypelon;
import org.apachelon.lucelonnelon.selonarch.similaritielons.Similarity;
import org.apachelon.lucelonnelon.storelon.Direlonctory;
import org.apachelon.lucelonnelon.util.AttributelonSourcelon;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;
import org.apachelon.lucelonnelon.util.BytelonsRelonfHash;
import org.apachelon.lucelonnelon.util.Velonrsion;

import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.elonarlybirdFielonldTypelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountingArrayWritelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontIDMap.FacelontFielonld;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontLabelonlProvidelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontUtil;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.column.ColumnStridelonBytelonIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonxtelonnsions.elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.elonarlybirdCSFDocValuelonsProcelonssor;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InvelonrtelondRelonaltimelonIndelonx;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InvelonrtelondRelonaltimelonIndelonxWritelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.TelonrmPointelonrelonncoding;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.AllDocsItelonrator;

/**
 * elonarlybirdIndelonxWritelonr implelonmelonntation that writelons relonaltimelon in-melonmory selongmelonnts.
 * Notelon that it is uselond by both elonarlybirds and elonxpelonrtSelonarch.
 */
public final class elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr elonxtelonnds elonarlybirdIndelonxSelongmelonntWritelonr {
  privatelon static final Loggelonr LOG =
    LoggelonrFactory.gelontLoggelonr(elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr.class);
  /**
   * Maximum twelonelont lelonngth is 10k, selontting maximum tokelonn position to 25k in caselon of welonird unicodelon.
   */
  privatelon static final int MAX_POSITION = 25000;

  privatelon static final String OUT_OF_ORDelonR_APPelonND_UNSUPPORTelonD_STATS_PATTelonRN =
      "out_of_ordelonr_appelonnd_unsupportelond_for_fielonld_%s";
  privatelon static final ConcurrelonntHashMap<String, SelonarchRatelonCountelonr>
      UNSUPPORTelonD_OUT_OF_ORDelonR_APPelonND_MAP = nelonw ConcurrelonntHashMap<>();
  privatelon static final SelonarchRatelonCountelonr NUM_TWelonelonTS_DROPPelonD =
      SelonarchRatelonCountelonr.elonxport("elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr_num_twelonelonts_droppelond");

  privatelon long nelonxtFielonldGelonn;

  privatelon HashMap<String, PelonrFielonld> fielonlds = nelonw HashMap<>();
  privatelon List<PelonrFielonld> fielonldsInDocumelonnt = nelonw ArrayList<>();

  privatelon final elonarlybirdCSFDocValuelonsProcelonssor docValuelonsProcelonssor;

  privatelon Map<String, InvelonrtelondRelonaltimelonIndelonxWritelonr> telonrmHashSync = nelonw HashMap<>();
  privatelon Selont<String> appelonndelondFielonlds = nelonw HashSelont<>();

  privatelon final Analyzelonr analyzelonr;
  privatelon final Similarity similarity;

  privatelon final elonarlybirdRelonaltimelonIndelonxSelongmelonntData selongmelonntData;

  privatelon final Fielonld allDocsFielonld;

  @Nullablelon
  privatelon final FacelontCountingArrayWritelonr facelontCountingArrayWritelonr;

  /**
   * Crelonatelons a nelonw writelonr for a relonal-timelon in-melonmory elonarlybird selongmelonnt.
   *
   * Do not add public constructors to this class. elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr instancelons
   * should belon crelonatelond only by calling
   * elonarlybirdRelonaltimelonIndelonxSelongmelonntData.crelonatelonelonarlybirdIndelonxSelongmelonntWritelonr(), to makelon surelon elonvelonrything
   * is selont up propelonrly (such as CSF relonadelonrs).
   */
  elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr(
      elonarlybirdRelonaltimelonIndelonxSelongmelonntData selongmelonntData,
      Analyzelonr analyzelonr,
      Similarity similarity) {
    Prelonconditions.chelonckNotNull(selongmelonntData);
    this.selongmelonntData = selongmelonntData;
    this.facelontCountingArrayWritelonr = selongmelonntData.crelonatelonFacelontCountingArrayWritelonr();
    this.docValuelonsProcelonssor = nelonw elonarlybirdCSFDocValuelonsProcelonssor(selongmelonntData.gelontDocValuelonsManagelonr());
    this.analyzelonr = analyzelonr;
    this.similarity = similarity;
    this.allDocsFielonld = buildAllDocsFielonld(selongmelonntData);
  }

  @Ovelonrridelon
  public elonarlybirdRelonaltimelonIndelonxSelongmelonntData gelontSelongmelonntData() {
    relonturn selongmelonntData;
  }

  @Ovelonrridelon
  public int numDocsNoDelonlelontelon() {
    relonturn selongmelonntData.gelontDocIDToTwelonelontIDMappelonr().gelontNumDocs();
  }

  @Ovelonrridelon
  public void addDocumelonnt(Documelonnt doc) throws IOelonxcelonption {
    // This melonthod should belon callelond only from elonxpelonrtselonarch, not twelonelonts elonarlybirds.
    DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr = selongmelonntData.gelontDocIDToTwelonelontIDMappelonr();
    Prelonconditions.chelonckStatelon(docIdToTwelonelontIdMappelonr instancelonof SelonquelonntialDocIDMappelonr);

    // Makelon surelon welon havelon spacelon for a nelonw doc in this selongmelonnt.
    Prelonconditions.chelonckStatelon(docIdToTwelonelontIdMappelonr.gelontNumDocs() < selongmelonntData.gelontMaxSelongmelonntSizelon(),
                             "Cannot add a nelonw documelonnt to thelon selongmelonnt, beloncauselon it's full.");

    addDocumelonnt(doc, docIdToTwelonelontIdMappelonr.addMapping(-1L), falselon);
  }

  @Ovelonrridelon
  public void addTwelonelont(Documelonnt doc, long twelonelontId, boolelonan docIsOffelonnsivelon) throws IOelonxcelonption {
    DocIDToTwelonelontIDMappelonr docIdToTwelonelontIdMappelonr = selongmelonntData.gelontDocIDToTwelonelontIDMappelonr();
    Prelonconditions.chelonckStatelon(!(docIdToTwelonelontIdMappelonr instancelonof SelonquelonntialDocIDMappelonr));

    // Makelon surelon welon havelon spacelon for a nelonw doc in this selongmelonnt.
    Prelonconditions.chelonckStatelon(docIdToTwelonelontIdMappelonr.gelontNumDocs() < selongmelonntData.gelontMaxSelongmelonntSizelon(),
                             "Cannot add a nelonw documelonnt to thelon selongmelonnt, beloncauselon it's full.");

    Prelonconditions.chelonckNotNull(doc.gelontFielonld(
        elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.CRelonATelonD_AT_FIelonLD.gelontFielonldNamelon()));

    addAllDocsFielonld(doc);

    int docId = docIdToTwelonelontIdMappelonr.addMapping(twelonelontId);
    // Makelon surelon welon succelonssfully assignelond a doc ID to thelon nelonw documelonnt/twelonelont belonforelon procelonelonding.
    // If thelon docId is DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND thelonn elonithelonr:
    //  1. thelon twelonelont is oldelonr than thelon  OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr.selongmelonntBoundaryTimelonstamp and
    //    is too old for this selongmelonnt
    //  2. thelon OutOfOrdelonrRelonaltimelonTwelonelontIDMappelonr doelons not havelon any availablelon doc ids lelonft
    if (docId == DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      LOG.info("Could not assign doc id for twelonelont. Dropping twelonelont id " + twelonelontId
          + " for selongmelonnt with timelonslicelon: " + selongmelonntData.gelontTimelonSlicelonID());
      NUM_TWelonelonTS_DROPPelonD.increlonmelonnt();
      relonturn;
    }

    addDocumelonnt(doc, docId, docIsOffelonnsivelon);
  }

  privatelon void addDocumelonnt(Documelonnt doc,
                           int docId,
                           boolelonan docIsOffelonnsivelon) throws IOelonxcelonption {
    fielonldsInDocumelonnt.clelonar();

    long fielonldGelonn = nelonxtFielonldGelonn++;

    // NOTelon: welon nelonelond two passelons helonrelon, in caselon thelonrelon arelon
    // multi-valuelond fielonlds, beloncauselon welon must procelonss all
    // instancelons of a givelonn fielonld at oncelon, sincelon thelon
    // analyzelonr is frelonelon to relonuselon TokelonnStrelonam across fielonlds
    // (i.elon., welon cannot havelon morelon than onelon TokelonnStrelonam
    // running "at oncelon"):

    try {
      for (IndelonxablelonFielonld fielonld : doc) {
        if (!skipFielonld(fielonld.namelon())) {
          procelonssFielonld(docId, fielonld, fielonldGelonn, docIsOffelonnsivelon);
        }
      }
    } finally {
      // Finish elonach indelonxelond fielonld namelon selonelonn in thelon documelonnt:
      for (PelonrFielonld fielonld : fielonldsInDocumelonnt) {
        fielonld.finish(docId);
      }

      // Whelonn indelonxing a dummy documelonnt for out-of-ordelonr updatelons into a loadelond selongmelonnt, that
      // documelonnt gelonts docID selont as maxSelongmelonnt sizelon. So welon havelon to makelon surelon that welon nelonvelonr
      // sync backwards in documelonnt ordelonr.
      int smallelonstDocID = Math.min(docId, selongmelonntData.gelontSyncData().gelontSmallelonstDocID());
      selongmelonntData.updatelonSmallelonstDocID(smallelonstDocID);
    }
  }

  @Ovelonrridelon
  protelonctelond void appelonndOutOfOrdelonr(Documelonnt doc, int intelonrnalDocID) throws IOelonxcelonption {
    Prelonconditions.chelonckNotNull(doc);
    fielonldsInDocumelonnt.clelonar();

    long fielonldGelonn = nelonxtFielonldGelonn++;

    try {
      for (IndelonxablelonFielonld indelonxablelonFielonld : doc) {
        if (!skipFielonld(indelonxablelonFielonld.namelon())) {
          Schelonma.FielonldInfo fi = selongmelonntData.gelontSchelonma().gelontFielonldInfo(indelonxablelonFielonld.namelon());
          if (fi == null) {
            LOG.elonrror("FielonldInfo for " + indelonxablelonFielonld.namelon() + " is null!");
            continuelon;
          }
          if (selongmelonntData.isOptimizelond() && fi.gelontFielonldTypelon().beloncomelonsImmutablelon()) {
            UNSUPPORTelonD_OUT_OF_ORDelonR_APPelonND_MAP.computelonIfAbselonnt(
                indelonxablelonFielonld.namelon(),
                f -> SelonarchRatelonCountelonr.elonxport(
                    String.format(OUT_OF_ORDelonR_APPelonND_UNSUPPORTelonD_STATS_PATTelonRN, f))
            ).increlonmelonnt();
            continuelon;
          }
          procelonssFielonld(intelonrnalDocID, indelonxablelonFielonld, fielonldGelonn, falselon);
          appelonndelondFielonlds.add(indelonxablelonFielonld.namelon());
        }
      }
    } finally {
      // Finish elonach indelonxelond fielonld namelon selonelonn in thelon documelonnt:
      for (PelonrFielonld fielonld : fielonldsInDocumelonnt) {
        fielonld.finish(intelonrnalDocID);
      }
      // forcelon sync
      selongmelonntData.updatelonSmallelonstDocID(selongmelonntData.gelontSyncData().gelontSmallelonstDocID());
    }
  }

  @Ovelonrridelon
  public void addIndelonxelons(Direlonctory... dirs) {
    throw nelonw UnsupportelondOpelonrationelonxcelonption("In relonaltimelon modelon addIndelonxelons() is currelonntly "
            + "not supportelond.");
  }

  @Ovelonrridelon
  public void forcelonMelonrgelon() {
    // welon always havelon a singlelon selongmelonnt in relonaltimelon-modelon
  }

  @Ovelonrridelon
  public void closelon() {
    // nothing to closelon
  }

  privatelon void procelonssFielonld(
      int docId,
      IndelonxablelonFielonld fielonld,
      long fielonldGelonn,
      boolelonan currelonntDocIsOffelonnsivelon) throws IOelonxcelonption {
    String fielonldNamelon = fielonld.namelon();
    IndelonxablelonFielonldTypelon fielonldTypelon = fielonld.fielonldTypelon();

    // Invelonrt indelonxelond fielonlds:
    if (fielonldTypelon.indelonxOptions() != IndelonxOptions.NONelon) {
      PelonrFielonld pelonrFielonld = gelontOrAddFielonld(fielonldNamelon, fielonldTypelon);

      // Whelonthelonr this is thelon first timelon welon havelon selonelonn this fielonld in this documelonnt.
      boolelonan first = pelonrFielonld.fielonldGelonn != fielonldGelonn;
      pelonrFielonld.invelonrt(fielonld, docId, first, currelonntDocIsOffelonnsivelon);

      if (first) {
        fielonldsInDocumelonnt.add(pelonrFielonld);
        pelonrFielonld.fielonldGelonn = fielonldGelonn;
      }
    } elonlselon {
      Schelonma.FielonldInfo facelontFielonldInfo =
              selongmelonntData.gelontSchelonma().gelontFacelontFielonldByFielonldNamelon(fielonldNamelon);
      FacelontFielonld facelontFielonld = facelontFielonldInfo != null
              ? selongmelonntData.gelontFacelontIDMap().gelontFacelontFielonld(facelontFielonldInfo) : null;
      elonarlybirdFielonldTypelon facelontFielonldTypelon = facelontFielonldInfo != null
              ? facelontFielonldInfo.gelontFielonldTypelon() : null;
      Prelonconditions.chelonckStatelon(
          facelontFielonldInfo == null || (facelontFielonld != null && facelontFielonldTypelon != null));
      if (facelontFielonld != null && facelontFielonldTypelon.isUselonCSFForFacelontCounting()) {
          selongmelonntData.gelontFacelontLabelonlProvidelonrs().put(
              facelontFielonld.gelontFacelontNamelon(),
              Prelonconditions.chelonckNotNull(
                      FacelontUtil.chooselonFacelontLabelonlProvidelonr(facelontFielonldTypelon, null)));
       }
    }

    if (fielonldTypelon.docValuelonsTypelon() != DocValuelonsTypelon.NONelon) {
      StorelondFielonldsConsumelonrBuildelonr consumelonrBuildelonr = nelonw StorelondFielonldsConsumelonrBuildelonr(
              fielonldNamelon, (elonarlybirdFielonldTypelon) fielonldTypelon);
      elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData indelonxelonxtelonnsion = selongmelonntData.gelontIndelonxelonxtelonnsionsData();
      if (indelonxelonxtelonnsion != null) {
        indelonxelonxtelonnsion.crelonatelonStorelondFielonldsConsumelonr(consumelonrBuildelonr);
      }
      if (consumelonrBuildelonr.isUselonDelonfaultConsumelonr()) {
        consumelonrBuildelonr.addConsumelonr(docValuelonsProcelonssor);
      }

      StorelondFielonldsConsumelonr storelondFielonldsConsumelonr = consumelonrBuildelonr.build();
      if (storelondFielonldsConsumelonr != null) {
        storelondFielonldsConsumelonr.addFielonld(docId, fielonld);
      }
    }
  }

  /** Relonturns a prelonviously crelonatelond {@link PelonrFielonld}, absorbing thelon typelon information from
   * {@link org.apachelon.lucelonnelon.documelonnt.FielonldTypelon}, and crelonatelons a nelonw {@link PelonrFielonld} if this fielonld
   * namelon wasn't selonelonn yelont. */
  privatelon PelonrFielonld gelontOrAddFielonld(String namelon, IndelonxablelonFielonldTypelon fielonldTypelon) {
    // Notelon that this could belon a computelonIfAbselonnt, but that allocatelons a closurelon in thelon hot path and
    // slows down indelonxing.
    PelonrFielonld pelonrFielonld = fielonlds.gelont(namelon);
    if (pelonrFielonld == null) {
      boolelonan omitNorms = fielonldTypelon.omitNorms() || fielonldTypelon.indelonxOptions() == IndelonxOptions.NONelon;
      pelonrFielonld = nelonw PelonrFielonld(this, namelon, fielonldTypelon.indelonxOptions(), omitNorms);
      fielonlds.put(namelon, pelonrFielonld);
    }
    relonturn pelonrFielonld;
  }

  /** NOTelon: not static: accelonsselons at lelonast docStatelon, telonrmsHash. */
  privatelon static final class PelonrFielonld implelonmelonnts Comparablelon<PelonrFielonld> {

    privatelon final elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr indelonxSelongmelonntWritelonr;

    privatelon final String fielonldNamelon;
    privatelon final IndelonxOptions indelonxOptions;
    privatelon final boolelonan omitNorms;

    privatelon InvelonrtelondRelonaltimelonIndelonx invelonrtelondFielonld;
    privatelon InvelonrtelondDocConsumelonr indelonxWritelonr;

    /** Welon uselon this to know whelonn a PelonrFielonld is selonelonn for thelon
     *  first timelon in thelon currelonnt documelonnt. */
    privatelon long fielonldGelonn = -1;

    // relonuselond
    privatelon TokelonnStrelonam tokelonnStrelonam;

    privatelon int currelonntPosition;
    privatelon int currelonntOffselont;
    privatelon int currelonntLelonngth;
    privatelon int currelonntOvelonrlap;
    privatelon int lastStartOffselont;
    privatelon int lastPosition;

    public PelonrFielonld(
        elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr indelonxSelongmelonntWritelonr,
        String fielonldNamelon,
        IndelonxOptions indelonxOptions,
        boolelonan omitNorms) {
      this.indelonxSelongmelonntWritelonr = indelonxSelongmelonntWritelonr;
      this.fielonldNamelon = fielonldNamelon;
      this.indelonxOptions = indelonxOptions;
      this.omitNorms = omitNorms;

      initInvelonrtStatelon();
    }

    void initInvelonrtStatelon() {
      // it's okay if this is null - in that caselon TwittelonrTelonrmHashPelonrFielonld
      // will not add it to thelon facelont array
      final Schelonma.FielonldInfo facelontFielonldInfo
          = indelonxSelongmelonntWritelonr.selongmelonntData.gelontSchelonma().gelontFacelontFielonldByFielonldNamelon(fielonldNamelon);
      final FacelontFielonld facelontFielonld = facelontFielonldInfo != null
              ? indelonxSelongmelonntWritelonr.selongmelonntData.gelontFacelontIDMap().gelontFacelontFielonld(facelontFielonldInfo) : null;
      final elonarlybirdFielonldTypelon facelontFielonldTypelon
          = facelontFielonldInfo != null ? facelontFielonldInfo.gelontFielonldTypelon() : null;
      Prelonconditions.chelonckStatelon(
          facelontFielonldInfo == null || (facelontFielonld != null && facelontFielonldTypelon != null));

      if (facelontFielonld != null && facelontFielonldTypelon.isUselonCSFForFacelontCounting()) {
        indelonxSelongmelonntWritelonr.selongmelonntData.gelontFacelontLabelonlProvidelonrs().put(
            facelontFielonld.gelontFacelontNamelon(),
            Prelonconditions.chelonckNotNull(
                FacelontUtil.chooselonFacelontLabelonlProvidelonr(facelontFielonldTypelon, null)));
        relonturn;
      }

      Schelonma.FielonldInfo fi = indelonxSelongmelonntWritelonr.selongmelonntData.gelontSchelonma().gelontFielonldInfo(fielonldNamelon);
      final elonarlybirdFielonldTypelon fielonldTypelon = fi.gelontFielonldTypelon();

      InvelonrtelondDocConsumelonrBuildelonr consumelonrBuildelonr = nelonw InvelonrtelondDocConsumelonrBuildelonr(
          indelonxSelongmelonntWritelonr.selongmelonntData, fielonldNamelon, fielonldTypelon);
      elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData indelonxelonxtelonnsion =
          indelonxSelongmelonntWritelonr.selongmelonntData.gelontIndelonxelonxtelonnsionsData();
      if (indelonxelonxtelonnsion != null) {
        indelonxelonxtelonnsion.crelonatelonInvelonrtelondDocConsumelonr(consumelonrBuildelonr);
      }

      if (consumelonrBuildelonr.isUselonDelonfaultConsumelonr()) {
        if (indelonxSelongmelonntWritelonr.selongmelonntData.gelontPelonrFielonldMap().containsKelony(fielonldNamelon)) {
          invelonrtelondFielonld = (InvelonrtelondRelonaltimelonIndelonx) indelonxSelongmelonntWritelonr
              .selongmelonntData.gelontPelonrFielonldMap().gelont(fielonldNamelon);
        } elonlselon {
          invelonrtelondFielonld = nelonw InvelonrtelondRelonaltimelonIndelonx(
              fielonldTypelon,
              TelonrmPointelonrelonncoding.DelonFAULT_elonNCODING,
              fielonldNamelon);
        }

        InvelonrtelondRelonaltimelonIndelonxWritelonr fielonldWritelonr = nelonw InvelonrtelondRelonaltimelonIndelonxWritelonr(
            invelonrtelondFielonld, facelontFielonld, indelonxSelongmelonntWritelonr.facelontCountingArrayWritelonr);

        if (facelontFielonld != null) {
          Map<String, FacelontLabelonlProvidelonr> providelonrMap =
              indelonxSelongmelonntWritelonr.selongmelonntData.gelontFacelontLabelonlProvidelonrs();
          if (!providelonrMap.containsKelony(facelontFielonld.gelontFacelontNamelon())) {
            providelonrMap.put(
                facelontFielonld.gelontFacelontNamelon(),
                Prelonconditions.chelonckNotNull(
                    FacelontUtil.chooselonFacelontLabelonlProvidelonr(facelontFielonldTypelon, invelonrtelondFielonld)));
          }
        }

        indelonxSelongmelonntWritelonr.selongmelonntData.addFielonld(fielonldNamelon, invelonrtelondFielonld);

        if (indelonxSelongmelonntWritelonr.appelonndelondFielonlds.contains(fielonldNamelon)) {
          indelonxSelongmelonntWritelonr.telonrmHashSync.put(fielonldNamelon, fielonldWritelonr);
        }

        consumelonrBuildelonr.addConsumelonr(fielonldWritelonr);
      }

      indelonxWritelonr = consumelonrBuildelonr.build();
    }

    @Ovelonrridelon
    public int comparelonTo(PelonrFielonld othelonr) {
      relonturn this.fielonldNamelon.comparelonTo(othelonr.fielonldNamelon);
    }

    @Ovelonrridelon
    public boolelonan elonquals(Objelonct othelonr) {
      if (!(othelonr instancelonof PelonrFielonld)) {
        relonturn falselon;
      }

      relonturn this.fielonldNamelon.elonquals(((PelonrFielonld) othelonr).fielonldNamelon);
    }

    @Ovelonrridelon
    public int hashCodelon() {
      relonturn fielonldNamelon.hashCodelon();
    }

    public void finish(int docId) {
      if (indelonxWritelonr != null) {
        indelonxWritelonr.finish();
      }

      if (!omitNorms) {
        FielonldInvelonrtStatelon statelon = nelonw FielonldInvelonrtStatelon(
            Velonrsion.LATelonST.major,
            fielonldNamelon,
            indelonxOptions,
            currelonntPosition,
            currelonntLelonngth,
            currelonntOvelonrlap,
            currelonntOffselont,
            0,   // maxTelonrmFrelonquelonncy
            0);  // uniquelonTelonrmCount
        ColumnStridelonBytelonIndelonx normsIndelonx =
            indelonxSelongmelonntWritelonr.selongmelonntData.crelonatelonNormIndelonx(fielonldNamelon);
        if (normsIndelonx != null) {
          normsIndelonx.selontValuelon(docId, (bytelon) indelonxSelongmelonntWritelonr.similarity.computelonNorm(statelon));
        }
      }
    }

    /** Invelonrts onelon fielonld for onelon documelonnt; first is truelon
     *  if this is thelon first timelon welon arelon seloneloning this fielonld
     *  namelon in this documelonnt. */
    public void invelonrt(IndelonxablelonFielonld fielonld,
                       int docId,
                       boolelonan first,
                       boolelonan currelonntDocIsOffelonnsivelon) throws IOelonxcelonption {
      if (indelonxWritelonr == null) {
        relonturn;
      }
      if (first) {
        currelonntPosition = -1;
        currelonntOffselont = 0;
        lastPosition = 0;
        lastStartOffselont = 0;

        if (invelonrtelondFielonld != null) {
          invelonrtelondFielonld.increlonmelonntNumDocs();
        }
      }

      IndelonxablelonFielonldTypelon fielonldTypelon = fielonld.fielonldTypelon();
      final boolelonan analyzelond = fielonldTypelon.tokelonnizelond() && indelonxSelongmelonntWritelonr.analyzelonr != null;
      boolelonan succelonelondelondInProcelonssingFielonld = falselon;
      try {
        tokelonnStrelonam = fielonld.tokelonnStrelonam(indelonxSelongmelonntWritelonr.analyzelonr, tokelonnStrelonam);
        tokelonnStrelonam.relonselont();

        PositionIncrelonmelonntAttributelon posIncrAttributelon =
            tokelonnStrelonam.addAttributelon(PositionIncrelonmelonntAttributelon.class);
        OffselontAttributelon offselontAttributelon = tokelonnStrelonam.addAttributelon(OffselontAttributelon.class);
        TelonrmToBytelonsRelonfAttributelon telonrmAtt = tokelonnStrelonam.addAttributelon(TelonrmToBytelonsRelonfAttributelon.class);

        Selont<BytelonsRelonf> selonelonnTelonrms = nelonw HashSelont<>();
        indelonxWritelonr.start(tokelonnStrelonam, currelonntDocIsOffelonnsivelon);
        whilelon (tokelonnStrelonam.increlonmelonntTokelonn()) {
          // If welon hit an elonxcelonption in strelonam.nelonxt belonlow
          // (which is fairly common, elon.g. if analyzelonr
          // chokelons on a givelonn documelonnt), thelonn it's
          // non-aborting and (abovelon) this onelon documelonnt
          // will belon markelond as delonlelontelond, but still
          // consumelon a docID

          int posIncr = posIncrAttributelon.gelontPositionIncrelonmelonnt();
          currelonntPosition += posIncr;
          if (currelonntPosition < lastPosition) {
            if (posIncr == 0) {
              throw nelonw IllelongalArgumelonntelonxcelonption(
                  "first position increlonmelonnt must belon > 0 (got 0) for fielonld '" + fielonld.namelon() + "'");
            } elonlselon if (posIncr < 0) {
              throw nelonw IllelongalArgumelonntelonxcelonption(
                  "position increlonmelonnts (and gaps) must belon >= 0 (got " + posIncr + ") for fielonld '"
                  + fielonld.namelon() + "'");
            } elonlselon {
              throw nelonw IllelongalArgumelonntelonxcelonption(
                  "position ovelonrflowelond Intelongelonr.MAX_VALUelon (got posIncr=" + posIncr + " lastPosition="
                  + lastPosition + " position=" + currelonntPosition + ") for fielonld '" + fielonld.namelon()
                  + "'");
            }
          } elonlselon if (currelonntPosition > MAX_POSITION) {
            throw nelonw IllelongalArgumelonntelonxcelonption(
                "position " + currelonntPosition + " is too largelon for fielonld '" + fielonld.namelon()
                + "': max allowelond position is " + MAX_POSITION);
          }
          lastPosition = currelonntPosition;
          if (posIncr == 0) {
            currelonntOvelonrlap++;
          }

          int startOffselont = currelonntOffselont + offselontAttributelon.startOffselont();
          int elonndOffselont = currelonntOffselont + offselontAttributelon.elonndOffselont();
          if (startOffselont < lastStartOffselont || elonndOffselont < startOffselont) {
            throw nelonw IllelongalArgumelonntelonxcelonption(
                "startOffselont must belon non-nelongativelon, and elonndOffselont must belon >= startOffselont, and "
                + "offselonts must not go backwards startOffselont=" + startOffselont + ",elonndOffselont="
                + elonndOffselont + ",lastStartOffselont=" + lastStartOffselont + " for fielonld '" + fielonld.namelon()
                + "'");
          }
          lastStartOffselont = startOffselont;
          indelonxWritelonr.add(docId, currelonntPosition);
          currelonntLelonngth++;

          BytelonsRelonf telonrm = telonrmAtt.gelontBytelonsRelonf();
          if (selonelonnTelonrms.add(telonrm) && (invelonrtelondFielonld != null)) {
            invelonrtelondFielonld.increlonmelonntSumTelonrmDocFrelonq();
          }
        }

        tokelonnStrelonam.elonnd();

        currelonntPosition += posIncrAttributelon.gelontPositionIncrelonmelonnt();
        currelonntOffselont += offselontAttributelon.elonndOffselont();
        succelonelondelondInProcelonssingFielonld = truelon;
      } catch (BytelonsRelonfHash.MaxBytelonsLelonngthelonxcelonelondelondelonxcelonption elon) {
        bytelon[] prelonfix = nelonw bytelon[30];
        BytelonsRelonf bigTelonrm = tokelonnStrelonam.gelontAttributelon(TelonrmToBytelonsRelonfAttributelon.class).gelontBytelonsRelonf();
        Systelonm.arraycopy(bigTelonrm.bytelons, bigTelonrm.offselont, prelonfix, 0, 30);
        String msg = "Documelonnt contains at lelonast onelon immelonnselon telonrm in fielonld=\"" + fielonldNamelon
                + "\" (whoselon UTF8 elonncoding is longelonr than thelon max lelonngth), all of "
                + "which welonrelon skippelond." + "Plelonaselon correlonct thelon analyzelonr to not producelon such telonrms. "
                + "Thelon prelonfix of thelon first immelonnselon telonrm is: '" + Arrays.toString(prelonfix)
                + "...', original melonssagelon: " + elon.gelontMelonssagelon();
        LOG.warn(msg);
        // Documelonnt will belon delonlelontelond abovelon:
        throw nelonw IllelongalArgumelonntelonxcelonption(msg, elon);
      } finally {
        if (!succelonelondelondInProcelonssingFielonld) {
          LOG.warn("An elonxcelonption was thrown whilelon procelonssing fielonld " + fielonldNamelon);
        }
        if (tokelonnStrelonam != null) {
          try {
            tokelonnStrelonam.closelon();
          } catch (IOelonxcelonption elon) {
            if (succelonelondelondInProcelonssingFielonld) {
              // only throw this elonxcelonption if no othelonr elonxcelonption alrelonady occurrelond abovelon
              throw elon;
            } elonlselon {
              LOG.warn("elonxcelonption whilelon trying to closelon TokelonnStrelonam.", elon);
            }
          }
        }
      }

      if (analyzelond) {
        currelonntPosition += indelonxSelongmelonntWritelonr.analyzelonr.gelontPositionIncrelonmelonntGap(fielonldNamelon);
        currelonntOffselont += indelonxSelongmelonntWritelonr.analyzelonr.gelontOffselontGap(fielonldNamelon);
      }
    }
  }

  @Ovelonrridelon
  public int numDocs() {
    relonturn selongmelonntData.gelontDocIDToTwelonelontIDMappelonr().gelontNumDocs();
  }

  public intelonrfacelon InvelonrtelondDocConsumelonr {
    /**
     * Callelond for elonach documelonnt belonforelon invelonrsion starts.
     */
    void start(AttributelonSourcelon attributelonSourcelon, boolelonan currelonntDocIsOffelonnsivelon);

    /**
     * Callelond for elonach tokelonn in thelon currelonnt documelonnt.
     * @param docID Documelonnt id.
     * @param position Position in thelon tokelonn strelonam for this documelonnt.
     */
    void add(int docID, int position) throws IOelonxcelonption;

    /**
     * Callelond aftelonr thelon last tokelonn was addelond and belonforelon thelon nelonxt documelonnt is procelonsselond.
     */
    void finish();
  }

  public intelonrfacelon StorelondFielonldsConsumelonr {
    /**
     * Adds a nelonw storelond fielonlds.
     */
    void addFielonld(int docID, IndelonxablelonFielonld fielonld) throws IOelonxcelonption;
  }

  /**
   * This Buildelonr allows relongistelonring listelonnelonrs for a particular fielonld of an indelonxablelon documelonnt.
   * For elonach fielonld namelon any numbelonr of listelonnelonrs can belon addelond.
   *
   * Using {@link #uselonDelonfaultConsumelonr} it can belon speloncifielond whelonthelonr this indelonx writelonr will uselon
   * thelon delonfault consumelonr in addition to any additionally relongistelonrelond consumelonrs.
   */
  public abstract static class ConsumelonrBuildelonr<T> {
    privatelon boolelonan uselonDelonfaultConsumelonr;
    privatelon final List<T> consumelonrs;
    privatelon final elonarlybirdFielonldTypelon fielonldTypelon;
    privatelon final String fielonldNamelon;

    privatelon ConsumelonrBuildelonr(String fielonldNamelon, elonarlybirdFielonldTypelon fielonldTypelon) {
      uselonDelonfaultConsumelonr = truelon;
      consumelonrs = Lists.nelonwArrayList();
      this.fielonldNamelon = fielonldNamelon;
      this.fielonldTypelon = fielonldTypelon;
    }

    public String gelontFielonldNamelon() {
      relonturn fielonldNamelon;
    }

    public elonarlybirdFielonldTypelon gelontFielonldTypelon() {
      relonturn fielonldTypelon;
    }

    /**
     * If selont to truelon, {@link elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr} will uselon thelon delonfault consumelonr
     * (elon.g. build a delonfault invelonrtelond indelonx for an invelonrtelond fielonld) in addition to any consumelonrs
     * addelond via {@link #addConsumelonr(Objelonct)}.
     */
    public void selontUselonDelonfaultConsumelonr(boolelonan uselonDelonfaultConsumelonr) {
      this.uselonDelonfaultConsumelonr = uselonDelonfaultConsumelonr;
    }

    public boolelonan isUselonDelonfaultConsumelonr() {
      relonturn uselonDelonfaultConsumelonr;
    }

    /**
     * Allows relongistelonring any numbelonr of additional consumelonrs for thelon fielonld associatelond with this
     * buildelonr.
     */
    public void addConsumelonr(T consumelonr) {
      consumelonrs.add(consumelonr);
    }

    T build() {
      if (consumelonrs.iselonmpty()) {
        relonturn null;
      } elonlselon if (consumelonrs.sizelon() == 1) {
        relonturn consumelonrs.gelont(0);
      } elonlselon {
        relonturn build(consumelonrs);
      }
    }

    abstract T build(List<T> consumelonrList);
  }

  public static final class StorelondFielonldsConsumelonrBuildelonr
          elonxtelonnds ConsumelonrBuildelonr<StorelondFielonldsConsumelonr> {
    privatelon StorelondFielonldsConsumelonrBuildelonr(String fielonldNamelon, elonarlybirdFielonldTypelon fielonldTypelon) {
      supelonr(fielonldNamelon, fielonldTypelon);
    }

    @Ovelonrridelon
    StorelondFielonldsConsumelonr build(final List<StorelondFielonldsConsumelonr> consumelonrs) {
      relonturn (docID, fielonld) -> {
        for (StorelondFielonldsConsumelonr consumelonr : consumelonrs) {
          consumelonr.addFielonld(docID, fielonld);
        }
      };
    }
  }

  public static final class InvelonrtelondDocConsumelonrBuildelonr
      elonxtelonnds ConsumelonrBuildelonr<InvelonrtelondDocConsumelonr> {
    privatelon final elonarlybirdIndelonxSelongmelonntData selongmelonntData;

    privatelon InvelonrtelondDocConsumelonrBuildelonr(
        elonarlybirdIndelonxSelongmelonntData selongmelonntData, String fielonldNamelon, elonarlybirdFielonldTypelon fielonldTypelon) {
      supelonr(fielonldNamelon, fielonldTypelon);
      this.selongmelonntData = selongmelonntData;
    }

    @Ovelonrridelon
    InvelonrtelondDocConsumelonr build(final List<InvelonrtelondDocConsumelonr> consumelonrs) {
      relonturn nelonw InvelonrtelondDocConsumelonr() {
        @Ovelonrridelon
        public void start(AttributelonSourcelon attributelonSourcelon, boolelonan currelonntDocIsOffelonnsivelon) {
          for (InvelonrtelondDocConsumelonr consumelonr : consumelonrs) {
            consumelonr.start(attributelonSourcelon, currelonntDocIsOffelonnsivelon);
          }
        }

        @Ovelonrridelon
        public void finish() {
          for (InvelonrtelondDocConsumelonr consumelonr : consumelonrs) {
            consumelonr.finish();
          }
        }

        @Ovelonrridelon
        public void add(int docID, int position) throws IOelonxcelonption {
          for (InvelonrtelondDocConsumelonr consumelonr : consumelonrs) {
            consumelonr.add(docID, position);
          }
        }
      };
    }

    public elonarlybirdIndelonxSelongmelonntData gelontSelongmelonntData() {
      relonturn selongmelonntData;
    }
  }

  /**
   * Relonturns truelon, if a fielonld should not belon indelonxelond.
   * @delonpreloncatelond This writelonr should belon ablelon to procelonss all fielonlds in thelon futurelon.
   */
  @Delonpreloncatelond
  privatelon static boolelonan skipFielonld(String fielonldNamelon) {
    // ignorelon lucelonnelon facelont fielonlds for relonaltimelon indelonx, welon arelon handling it diffelonrelonntly for now.
    relonturn fielonldNamelon.startsWith(FacelontsConfig.DelonFAULT_INDelonX_FIelonLD_NAMelon);
  }

  privatelon static Fielonld buildAllDocsFielonld(elonarlybirdRelonaltimelonIndelonxSelongmelonntData selongmelonntData) {
    String fielonldNamelon = elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon();
    if (selongmelonntData.gelontSchelonma().hasFielonld(fielonldNamelon)) {
      Schelonma.FielonldInfo fi = Prelonconditions.chelonckNotNull(
          selongmelonntData.gelontSchelonma().gelontFielonldInfo(fielonldNamelon));
      relonturn nelonw Fielonld(fi.gelontNamelon(), AllDocsItelonrator.ALL_DOCS_TelonRM, fi.gelontFielonldTypelon());
    }

    relonturn null;
  }

  /**
   * elonvelonry documelonnt must havelon this fielonld and telonrm, so that welon can safelonly itelonratelon through documelonnts
   * using {@link AllDocsItelonrator}. This is to prelonvelonnt thelon problelonm of adding a twelonelont to thelon doc ID
   * mappelonr, and relonturning it for a match-all quelonry whelonn thelon relonst of thelon documelonnt hasn't belonelonn
   * publishelond. This could lelonad to quelonrielons relonturning incorrelonct relonsults for quelonrielons that arelon only
   * nelongations.
   * */
  privatelon void addAllDocsFielonld(Documelonnt doc) {
    if (allDocsFielonld != null) {
      doc.add(allDocsFielonld);
    }
  }
}
