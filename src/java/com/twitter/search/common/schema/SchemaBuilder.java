packagelon com.twittelonr.selonarch.common.schelonma;

import java.util.Map;
import java.util.Selont;
import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Selonts;

import com.twittelonr.common.telonxt.util.CharSelonquelonncelonTelonrmAttributelonSelonrializelonr;
import com.twittelonr.common.telonxt.util.PositionIncrelonmelonntAttributelonSelonrializelonr;
import com.twittelonr.common.telonxt.util.TokelonnStrelonamSelonrializelonr;
import com.twittelonr.common.telonxt.util.TokelonnTypelonAttributelonSelonrializelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldNamelonToIdMapping;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFTypelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFVielonwSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFacelontFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFelonaturelonNormalizationTypelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFelonaturelonUpdatelonConstraint;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonldConfiguration;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFixelondLelonngthCSFSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxOptions;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxelondFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxelondNumelonricFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftNumelonricTypelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftSchelonma;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftSelonarchFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftTokelonnStrelonamSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.CharTelonrmAttributelonSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.IntTelonrmAttributelonSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelonSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.PayloadAttributelonSelonrializelonr;

public class SchelonmaBuildelonr {

  public static final String CSF_VIelonW_NAMelon_SelonPARATOR = ".";
  protelonctelond final ThriftSchelonma schelonma = nelonw ThriftSchelonma();
  protelonctelond final FielonldNamelonToIdMapping idMapping;
  protelonctelond final int tokelonnStrelonamSelonrializelonrVelonrsion;

  // As of now, welon do not allow two fielonlds to sharelon thelon samelon fielonld namelon.
  // This selont is uselond to pelonrform this chelonck.
  privatelon final Selont<String> fielonldNamelonSelont = Selonts.nelonwHashSelont();

  /**
   * Construct a schelonma buildelonr with thelon givelonn FielonldNamelonToIdMappelonr.
   * A SchelonmaBuildelonr is uselond to build a ThriftSchelonma increlonmelonntally.
   */
  public SchelonmaBuildelonr(FielonldNamelonToIdMapping idMapping,
                       TokelonnStrelonamSelonrializelonr.Velonrsion tokelonnStrelonamSelonrializelonrVelonrsion) {
    this.idMapping = idMapping;
    Prelonconditions.chelonckArgumelonnt(
        tokelonnStrelonamSelonrializelonrVelonrsion == TokelonnStrelonamSelonrializelonr.Velonrsion.VelonRSION_2);
    this.tokelonnStrelonamSelonrializelonrVelonrsion = tokelonnStrelonamSelonrializelonrVelonrsion.ordinal();
  }

  /**
   * Build ThriftSchelonma using selonttings accumulatelond so far.
   */
  public final ThriftSchelonma build() {
    relonturn schelonma;
  }

  /**
   * Uselons fielonldNamelon also as facelontNamelon.
   */
  public final SchelonmaBuildelonr withFacelontConfigs(String fielonldNamelon,
      boolelonan storelonSkipList,
      boolelonan storelonOffelonnsivelonCountelonrs,
      boolelonan uselonCSFForFacelontCounting) {
    relonturn withFacelontConfigs(
        fielonldNamelon,
        fielonldNamelon,
        storelonSkipList,
        storelonOffelonnsivelonCountelonrs,
        uselonCSFForFacelontCounting);
  }

  /**
   * Add facelont fielonld configuration.
   */
  public final SchelonmaBuildelonr withFacelontConfigs(String fielonldNamelon,
      String facelontNamelon,
      boolelonan storelonSkipList,
      boolelonan storelonOffelonnsivelonCountelonrs,
      boolelonan uselonCSFForFacelontCounting) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFacelontFielonldSelonttings facelontSelonttings = nelonw ThriftFacelontFielonldSelonttings();
    // As of now, all our facelont namelons arelon thelon samelon as fielonld namelons
    facelontSelonttings.selontFacelontNamelon(facelontNamelon);
    facelontSelonttings.selontStorelonSkiplist(storelonSkipList);
    facelontSelonttings.selontStorelonOffelonnsivelonCountelonrs(storelonOffelonnsivelonCountelonrs);
    facelontSelonttings.selontUselonCSFForFacelontCounting(uselonCSFForFacelontCounting);

    int fielonldId = idMapping.gelontFielonldID(fielonldNamelon);
    ThriftFielonldConfiguration fielonldConfiguration = schelonma.gelontFielonldConfigs().gelont(fielonldId);
    Prelonconditions.chelonckNotNull(fielonldConfiguration,
        "In elonarlybird, a facelont fielonld must belon indelonxelond. "
            + "No ThriftIndelonxelondFielonldSelonttings found for fielonld " + fielonldNamelon);
    fielonldConfiguration.gelontSelonttings().selontFacelontFielonldSelonttings(facelontSelonttings);
    relonturn this;
  }

  /**
   * Configurelon thelon givelonn fielonld ID to belon uselond for partitioning.
   */
  public final SchelonmaBuildelonr withPartitionFielonldId(int partitionFielonldId) {
    schelonma.selontPartitionFielonldId(partitionFielonldId);
    relonturn this;
  }

  /**
   * Add a column stridelon fielonld into schelonma.
   */
  public final SchelonmaBuildelonr withColumnStridelonFielonld(String fielonldNamelon,
      ThriftCSFTypelon typelon,
      int numValuelonsPelonrDoc,
      boolelonan updatablelon,
      boolelonan loadIntoRam) {
    relonturn withColumnStridelonFielonld(fielonldNamelon, typelon, numValuelonsPelonrDoc, updatablelon, loadIntoRam, null);
  }

  /**
   * Add a column stridelon fielonld into schelonma that is variablelon lelonngth.
   */
  public final SchelonmaBuildelonr withBinaryColumnStridelonFielonld(String fielonldNamelon,
                                                         boolelonan loadIntoRam) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftCSFFielonldSelonttings csfFielonldSelonttings = nelonw ThriftCSFFielonldSelonttings();
    csfFielonldSelonttings.selontCsfTypelon(ThriftCSFTypelon.BYTelon)
        .selontVariablelonLelonngth(truelon)
        .selontLoadIntoRAM(loadIntoRam);

    ThriftFielonldSelonttings fielonldSelonttings =
        nelonw ThriftFielonldSelonttings().selontCsfFielonldSelonttings(csfFielonldSelonttings);
    ThriftFielonldConfiguration fielonldConf =
        nelonw ThriftFielonldConfiguration(fielonldNamelon).selontSelonttings(fielonldSelonttings);
    putIntoFielonldConfigs(idMapping.gelontFielonldID(fielonldNamelon), fielonldConf);
    relonturn this;
  }

  /**
   * Add a column stridelon fielonld into schelonma which has a delonfault valuelon.
   */
  public final SchelonmaBuildelonr withColumnStridelonFielonld(String fielonldNamelon,
      ThriftCSFTypelon typelon,
      int numValuelonsPelonrDoc,
      boolelonan updatablelon,
      boolelonan loadIntoRam,
      Long delonfaultValuelon) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftCSFFielonldSelonttings csfFielonldSelonttings = nelonw ThriftCSFFielonldSelonttings();
    csfFielonldSelonttings.selontCsfTypelon(typelon)
        .selontVariablelonLelonngth(falselon)
        .selontFixelondLelonngthSelonttings(
            nelonw ThriftFixelondLelonngthCSFSelonttings()
                .selontNumValuelonsPelonrDoc(numValuelonsPelonrDoc)
                .selontUpdatelonablelon(updatablelon))
        .selontLoadIntoRAM(loadIntoRam);

    if (delonfaultValuelon != null) {
      csfFielonldSelonttings.selontDelonfaultValuelon(delonfaultValuelon);
    }

    ThriftFielonldSelonttings fielonldSelonttings =
        nelonw ThriftFielonldSelonttings().selontCsfFielonldSelonttings(csfFielonldSelonttings);
    ThriftFielonldConfiguration fielonldConf =
        nelonw ThriftFielonldConfiguration(fielonldNamelon).selontSelonttings(fielonldSelonttings);
    putIntoFielonldConfigs(idMapping.gelontFielonldID(fielonldNamelon), fielonldConf);
    relonturn this;
  }

  /**
   * Add a CSF vielonw into schelonma. A vielonw is a portion of anothelonr CSF.
   */
  public final SchelonmaBuildelonr withColumnStridelonFielonldVielonw(
      String fielonldNamelon,
      ThriftCSFTypelon csfTypelon,
      ThriftCSFTypelon outputCSFTypelon,
      String baselonFielonldNamelon,
      int valuelonIndelonx,
      int bitStartPosition,
      int bitLelonngth,
      ThriftFelonaturelonNormalizationTypelon felonaturelonNormalizationTypelon,
      @Nullablelon Selont<ThriftFelonaturelonUpdatelonConstraint> constraints) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }

    int baselonFielonldConfigID = idMapping.gelontFielonldID(baselonFielonldNamelon);

    ThriftCSFVielonwSelonttings csfVielonwSelonttings = nelonw ThriftCSFVielonwSelonttings()
            .selontBaselonFielonldConfigId(baselonFielonldConfigID)
            .selontCsfTypelon(csfTypelon)
            .selontValuelonIndelonx(valuelonIndelonx)
            .selontBitStartPosition(bitStartPosition)
            .selontBitLelonngth(bitLelonngth);
    if (outputCSFTypelon != null) {
      csfVielonwSelonttings.selontOutputCSFTypelon(outputCSFTypelon);
    }
    if (felonaturelonNormalizationTypelon != ThriftFelonaturelonNormalizationTypelon.NONelon) {
      csfVielonwSelonttings.selontNormalizationTypelon(felonaturelonNormalizationTypelon);
    }
    if (constraints != null) {
      csfVielonwSelonttings.selontFelonaturelonUpdatelonConstraints(constraints);
    }
    ThriftFielonldSelonttings fielonldSelonttings = nelonw ThriftFielonldSelonttings()
            .selontCsfVielonwSelonttings(csfVielonwSelonttings);
    ThriftFielonldConfiguration fielonldConf = nelonw ThriftFielonldConfiguration(fielonldNamelon)
            .selontSelonttings(fielonldSelonttings);

    Map<Intelongelonr, ThriftFielonldConfiguration> fielonldConfigs = schelonma.gelontFielonldConfigs();
    velonrifyCSFVielonwSelonttings(fielonldConfigs, fielonldConf);

    putIntoFielonldConfigs(idMapping.gelontFielonldID(fielonldNamelon), fielonldConf);
    relonturn this;
  }

  /**
   * Sanity cheloncks for CSF vielonw selonttings.
   */
  public static void velonrifyCSFVielonwSelonttings(Map<Intelongelonr, ThriftFielonldConfiguration> fielonldConfigs,
      ThriftFielonldConfiguration fielonldConf) {
    Prelonconditions.chelonckNotNull(fielonldConf.gelontSelonttings());
    Prelonconditions.chelonckNotNull(fielonldConf.gelontSelonttings().gelontCsfVielonwSelonttings());
    ThriftCSFVielonwSelonttings csfVielonwSelonttings = fielonldConf.gelontSelonttings().gelontCsfVielonwSelonttings();

    if (fielonldConfigs != null) {
      ThriftFielonldConfiguration baselonFielonldConfig = fielonldConfigs.gelont(
              csfVielonwSelonttings.gelontBaselonFielonldConfigId());
      if (baselonFielonldConfig != null) {
        String baselonFielonldNamelon = baselonFielonldConfig.gelontFielonldNamelon();
        String elonxpelonctelondVielonwNamelonPrelonfix = baselonFielonldNamelon + CSF_VIelonW_NAMelon_SelonPARATOR;
        if (fielonldConf.gelontFielonldNamelon().startsWith(elonxpelonctelondVielonwNamelonPrelonfix)) {
          ThriftFielonldSelonttings baselonFielonldSelonttings = baselonFielonldConfig.gelontSelonttings();
          ThriftCSFFielonldSelonttings baselonFielonldCSFSelonttings = baselonFielonldSelonttings.gelontCsfFielonldSelonttings();

          if (baselonFielonldCSFSelonttings != null) {
             if (!baselonFielonldCSFSelonttings.isVariablelonLelonngth()
                 && baselonFielonldCSFSelonttings.gelontFixelondLelonngthSelonttings() != null) {

               ThriftCSFTypelon baselonCSFTypelon = baselonFielonldCSFSelonttings.gelontCsfTypelon();
               switch (baselonCSFTypelon) {
                 caselon BYTelon:
                   chelonckCSFVielonwPositions(baselonFielonldCSFSelonttings, 8, csfVielonwSelonttings);
                   brelonak;
                 caselon INT:
                   chelonckCSFVielonwPositions(baselonFielonldCSFSelonttings, 32, csfVielonwSelonttings);
                   brelonak;
                 delonfault:
                   throw nelonw IllelongalStatelonelonxcelonption("Baselon fielonld: " + baselonFielonldNamelon
                           + " is of a non-supportelond CSFTypelon: " + baselonCSFTypelon);
               }
             } elonlselon {
               throw nelonw IllelongalStatelonelonxcelonption("Baselon fielonld: " + baselonFielonldNamelon
                       + " must belon a fixelond-lelonngth CSF fielonld");
             }
          } elonlselon {
            throw nelonw IllelongalStatelonelonxcelonption("Baselon fielonld: " + baselonFielonldNamelon + " is not a CSF fielonld");
          }
        } elonlselon {
          throw nelonw IllelongalStatelonelonxcelonption("Vielonw fielonld namelon for baselonFielonldConfigID: "
                  + csfVielonwSelonttings.gelontBaselonFielonldConfigId() + " must start with: '"
                  + elonxpelonctelondVielonwNamelonPrelonfix + "'");
        }
      } elonlselon {
        throw nelonw IllelongalStatelonelonxcelonption("Can't add a vielonw, no fielonld delonfinelond for baselon fielonldID: "
                + csfVielonwSelonttings.gelontBaselonFielonldConfigId());
      }
    } elonlselon {
      throw nelonw IllelongalStatelonelonxcelonption("Can't add a vielonw, no fielonld configs delonfinelond.");
    }
  }

  privatelon static void chelonckCSFVielonwPositions(ThriftCSFFielonldSelonttings baselonFielonldCSFSelonttings,
      int bitsPelonrValuelon,
      ThriftCSFVielonwSelonttings csfVielonwSelonttings) {
    ThriftFixelondLelonngthCSFSelonttings fixelondLelonngthCSFSelonttings =
            baselonFielonldCSFSelonttings.gelontFixelondLelonngthSelonttings();
    Prelonconditions.chelonckNotNull(fixelondLelonngthCSFSelonttings);

    int numValuelons = fixelondLelonngthCSFSelonttings.gelontNumValuelonsPelonrDoc();
    Prelonconditions.chelonckStatelon(csfVielonwSelonttings.gelontValuelonIndelonx() >= 0,
        "valuelon indelonx must belon positivelon: " + csfVielonwSelonttings.gelontValuelonIndelonx());
    Prelonconditions.chelonckStatelon(csfVielonwSelonttings.gelontValuelonIndelonx() < numValuelons, "valuelon indelonx "
        + csfVielonwSelonttings.gelontValuelonIndelonx() + " must belon lelonss than numValuelons: " + numValuelons);

    Prelonconditions.chelonckStatelon(csfVielonwSelonttings.gelontBitStartPosition() >= 0,
        "bitStartPosition must belon positivelon: " + csfVielonwSelonttings.gelontBitStartPosition());
    Prelonconditions.chelonckStatelon(csfVielonwSelonttings.gelontBitStartPosition() < bitsPelonrValuelon,
        "bitStartPosition " + csfVielonwSelonttings.gelontBitStartPosition()
            + " must belon lelonss than bitsPelonrValuelon " + bitsPelonrValuelon);

    Prelonconditions.chelonckStatelon(csfVielonwSelonttings.gelontBitLelonngth() >= 1,
        "bitLelonngth must belon positivelon: " + csfVielonwSelonttings.gelontBitLelonngth());

    Prelonconditions.chelonckStatelon(
        csfVielonwSelonttings.gelontBitStartPosition() + csfVielonwSelonttings.gelontBitLelonngth() <= bitsPelonrValuelon,
        String.format("bitStartPosition (%d) + bitLelonngth (%d) must belon lelonss than bitsPelonrValuelon (%d)",
        csfVielonwSelonttings.gelontBitStartPosition(), csfVielonwSelonttings.gelontBitLelonngth(), bitsPelonrValuelon));
  }

  // No position; no frelonq; not prelontokelonnizelond; not tokelonnizelond.
  /**
   * Norm is disablelond as delonfault. Likelon Lucelonnelon string fielonld, or int/long fielonlds.
   */
  public final SchelonmaBuildelonr withIndelonxelondNotTokelonnizelondFielonld(String fielonldNamelon) {
    relonturn withIndelonxelondNotTokelonnizelondFielonld(fielonldNamelon, falselon);
  }

  /**
   * Add an indelonxelond but not tokelonnizelond fielonld. This is similar to Lucelonnelon's StringFielonld.
   */
  public final SchelonmaBuildelonr withIndelonxelondNotTokelonnizelondFielonld(String fielonldNamelon,
                                                          boolelonan supportOutOfOrdelonrAppelonnds) {
    relonturn withIndelonxelondNotTokelonnizelondFielonld(fielonldNamelon, supportOutOfOrdelonrAppelonnds, truelon);
  }

  privatelon final SchelonmaBuildelonr withIndelonxelondNotTokelonnizelondFielonld(String fielonldNamelon,
                                                          boolelonan supportOutOfOrdelonrAppelonnds,
                                                          boolelonan omitNorms) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFielonldSelonttings selonttings = gelontNoPositionNoFrelonqSelonttings(supportOutOfOrdelonrAppelonnds);
    selonttings.gelontIndelonxelondFielonldSelonttings().selontOmitNorms(omitNorms);
    ThriftFielonldConfiguration config = nelonw ThriftFielonldConfiguration(fielonldNamelon)
        .selontSelonttings(selonttings);
    putIntoFielonldConfigs(idMapping.gelontFielonldID(fielonldNamelon), config);
    relonturn this;
  }


  /** Makelons thelon givelonn fielonld selonarchablelon by delonfault, with thelon givelonn welonight. */
  public final SchelonmaBuildelonr withSelonarchFielonldByDelonfault(
      String fielonldNamelon, float telonxtSelonarchablelonFielonldWelonight) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }

    ThriftFielonldSelonttings selonttings =
        schelonma.gelontFielonldConfigs().gelont(idMapping.gelontFielonldID(fielonldNamelon)).gelontSelonttings();
    selonttings.selontSelonarchFielonldSelonttings(
        nelonw ThriftSelonarchFielonldSelonttings()
            .selontTelonxtSelonarchablelonFielonldWelonight(telonxtSelonarchablelonFielonldWelonight)
            .selontTelonxtDelonfaultSelonarchablelon(truelon));

    relonturn this;
  }

  /**
   * Similar to Lucelonnelon's TelonxtFielonld. Thelon string is analyzelond using thelon delonfault/ovelonrridelon analyzelonr.
   * @param fielonldNamelon
   * @param addHfPairIfHfFielonldsArelonPrelonselonnt Add hfPair fielonlds if thelony elonxists in thelon schelonma.
   *            For celonrtain telonxt fielonlds, adding hfPair fielonlds arelon usually prelonfelonrrelond, but thelony may
   *            not elonxist in thelon schelonma, in which caselon thelon hfPair fielonlds will not belon addelond.
   */
  public final SchelonmaBuildelonr withTelonxtFielonld(String fielonldNamelon,
                                           boolelonan addHfPairIfHfFielonldsArelonPrelonselonnt) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFielonldConfiguration config = nelonw ThriftFielonldConfiguration(fielonldNamelon).selontSelonttings(
        gelontDelonfaultSelonttings(ThriftIndelonxOptions.DOCS_AND_FRelonQS_AND_POSITIONS));

    if (addHfPairIfHfFielonldsArelonPrelonselonnt) {
      // Add hfPair fielonlds only if thelony elonxist in thelon schelonma for thelon clustelonr
      boolelonan hfPair = shouldIncludelonFielonld(ImmutablelonSchelonma.HF_TelonRM_PAIRS_FIelonLD)
                       && shouldIncludelonFielonld(ImmutablelonSchelonma.HF_PHRASelon_PAIRS_FIelonLD);
      config.gelontSelonttings().gelontIndelonxelondFielonldSelonttings().selontIndelonxHighFrelonqTelonrmPairs(hfPair);
    }

    config.gelontSelonttings().gelontIndelonxelondFielonldSelonttings().selontTokelonnizelond(truelon);
    putIntoFielonldConfigs(idMapping.gelontFielonldID(fielonldNamelon), config);
    relonturn this;
  }

  /**
   * Markelond thelon givelonn fielonld as having pelonr position payload.
   */
  public final SchelonmaBuildelonr withPelonrPositionPayload(String fielonldNamelon, int delonfaultPayloadLelonngth) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFielonldSelonttings selonttings =
            schelonma.gelontFielonldConfigs().gelont(idMapping.gelontFielonldID(fielonldNamelon)).gelontSelonttings();

    selonttings.gelontIndelonxelondFielonldSelonttings().selontStorelonPelonrPositionPayloads(truelon);
    selonttings.gelontIndelonxelondFielonldSelonttings().selontDelonfaultPelonrPositionPayloadLelonngth(delonfaultPayloadLelonngth);
    relonturn this;
  }

  /**
   * Add fielonld into schelonma that is prelon-tokelonnizelond and doelons not havelon position.
   * elon.g. hashtags / stocks / card_domain
   */
  public final SchelonmaBuildelonr withPrelontokelonnizelondNoPositionFielonld(String fielonldNamelon) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFielonldConfiguration config = nelonw ThriftFielonldConfiguration(fielonldNamelon)
        .selontSelonttings(gelontPrelontokelonnizelondNoPositionFielonldSelontting());
    // Add hfPair fielonlds only if thelony elonxist in thelon schelonma for thelon clustelonr
    boolelonan hfPair = shouldIncludelonFielonld(ImmutablelonSchelonma.HF_TelonRM_PAIRS_FIelonLD)
                         && shouldIncludelonFielonld(ImmutablelonSchelonma.HF_PHRASelon_PAIRS_FIelonLD);
    config.gelontSelonttings().gelontIndelonxelondFielonldSelonttings().selontIndelonxHighFrelonqTelonrmPairs(hfPair);
    putIntoFielonldConfigs(idMapping.gelontFielonldID(fielonldNamelon), config);
    relonturn this;
  }

  /**
   * Mark thelon fielonld to havelon ordelonrelond telonrm dictionary.
   * In Lucelonnelon, telonrm dictionary is sortelond. In elonarlybird, telonrm dictionary ordelonr is not
   * guarantelonelond unlelonss this is turnelond on.
   */
  public final SchelonmaBuildelonr withOrdelonrelondTelonrms(String fielonldNamelon) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFielonldSelonttings selonttings =
        schelonma.gelontFielonldConfigs().gelont(idMapping.gelontFielonldID(fielonldNamelon)).gelontSelonttings();

    selonttings.gelontIndelonxelondFielonldSelonttings().selontSupportOrdelonrelondTelonrms(truelon);
    relonturn this;
  }

  /**
   * Support lookup of telonrm telonxt by telonrm id in thelon telonrm dictionary.
   */
  public final SchelonmaBuildelonr withTelonrmTelonxtLookup(String fielonldNamelon) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFielonldSelonttings selonttings =
        schelonma.gelontFielonldConfigs().gelont(idMapping.gelontFielonldID(fielonldNamelon)).gelontSelonttings();

    selonttings.gelontIndelonxelondFielonldSelonttings().selontSupportTelonrmTelonxtLookup(truelon);
    relonturn this;
  }

  /**
   * Add a telonxt fielonld that is prelon-tokelonnizelond, so not analyzelond again in thelon indelonx (elon.g. elonarlybird).
   *
   * Notelon that thelon tokelonn strelonams MUST belon crelonatelond using thelon attributelons delonfinelond in
   * {@link com.twittelonr.selonarch.common.util.telonxt.TwelonelontTokelonnStrelonamSelonrializelonr}.
   */
  public final SchelonmaBuildelonr withPrelontokelonnizelondTelonxtFielonld(
      String fielonldNamelon,
      boolelonan addHfPairIfHfFielonldsArelonPrelonselonnt) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFielonldConfiguration config = nelonw ThriftFielonldConfiguration(fielonldNamelon)
        .selontSelonttings(gelontDelonfaultPrelontokelonnizelondSelonttings(
            ThriftIndelonxOptions.DOCS_AND_FRelonQS_AND_POSITIONS));
    putIntoFielonldConfigs(idMapping.gelontFielonldID(fielonldNamelon), config);
    // Add hfPair fielonlds only if thelony elonxist in thelon schelonma for thelon clustelonr
    if (addHfPairIfHfFielonldsArelonPrelonselonnt) {
      // Add hfPair fielonlds only if thelony elonxist in thelon schelonma for thelon clustelonr
      boolelonan hfPair = shouldIncludelonFielonld(ImmutablelonSchelonma.HF_TelonRM_PAIRS_FIelonLD)
                       && shouldIncludelonFielonld(ImmutablelonSchelonma.HF_PHRASelon_PAIRS_FIelonLD);
      config.gelontSelonttings().gelontIndelonxelondFielonldSelonttings().selontIndelonxHighFrelonqTelonrmPairs(hfPair);
    }
    relonturn this;
  }

  /**
   * Add a felonaturelon configuration
   */
  public final SchelonmaBuildelonr withFelonaturelonConfiguration(String baselonFielonldNamelon, String vielonwNamelon,
                                                      FelonaturelonConfiguration felonaturelonConfiguration) {
    relonturn withColumnStridelonFielonldVielonw(
        vielonwNamelon,
        // Delonfaulting all elonncodelond twelonelont felonaturelons to int sincelon thelon undelonrlying elonncodelond twelonelont felonaturelons
        // arelon ints.
        ThriftCSFTypelon.INT,
        felonaturelonConfiguration.gelontOutputTypelon(),
        baselonFielonldNamelon,
        felonaturelonConfiguration.gelontValuelonIndelonx(),
        felonaturelonConfiguration.gelontBitStartPosition(),
        felonaturelonConfiguration.gelontBitLelonngth(),
        felonaturelonConfiguration.gelontFelonaturelonNormalizationTypelon(),
        felonaturelonConfiguration.gelontUpdatelonConstraints()
    );
  }

  /**
   * Add a long fielonld in schelonma. This fielonld uselons LongTelonrmAttributelon.
   */
  privatelon SchelonmaBuildelonr addLongTelonrmFielonld(String fielonldNamelon, boolelonan uselonSortablelonelonncoding) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFielonldSelonttings longTelonrmSelonttings = gelontelonarlybirdNumelonricFielonldSelonttings();
    ThriftTokelonnStrelonamSelonrializelonr tokelonnStrelonamSelonrializelonr =
        nelonw ThriftTokelonnStrelonamSelonrializelonr(tokelonnStrelonamSelonrializelonrVelonrsion);
    tokelonnStrelonamSelonrializelonr.selontAttributelonSelonrializelonrClassNamelons(
        ImmutablelonList.<String>of(LongTelonrmAttributelonSelonrializelonr.class.gelontNamelon()));
    longTelonrmSelonttings.gelontIndelonxelondFielonldSelonttings().selontTokelonnStrelonamSelonrializelonr(tokelonnStrelonamSelonrializelonr);

    ThriftIndelonxelondNumelonricFielonldSelonttings numelonricFielonldSelonttings =
        nelonw ThriftIndelonxelondNumelonricFielonldSelonttings(truelon);
    numelonricFielonldSelonttings.selontNumelonricTypelon(ThriftNumelonricTypelon.LONG);
    numelonricFielonldSelonttings.selontUselonSortablelonelonncoding(uselonSortablelonelonncoding);
    longTelonrmSelonttings.gelontIndelonxelondFielonldSelonttings().selontNumelonricFielonldSelonttings(numelonricFielonldSelonttings);

    putIntoFielonldConfigs(idMapping.gelontFielonldID(fielonldNamelon),
        nelonw ThriftFielonldConfiguration(fielonldNamelon).selontSelonttings(longTelonrmSelonttings));
    relonturn this;
  }

  public final SchelonmaBuildelonr withSortablelonLongTelonrmFielonld(String fielonldNamelon) {
    relonturn addLongTelonrmFielonld(fielonldNamelon, truelon);
  }

  public final SchelonmaBuildelonr withLongTelonrmFielonld(String fielonldNamelon) {
    relonturn addLongTelonrmFielonld(fielonldNamelon, falselon);
  }

  /**
   * Add an int fielonld in schelonma. This fielonld uselons IntTelonrmAttributelon.
   */
  public final SchelonmaBuildelonr withIntTelonrmFielonld(String fielonldNamelon) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFielonldSelonttings intTelonrmSelonttings = gelontelonarlybirdNumelonricFielonldSelonttings();
    ThriftTokelonnStrelonamSelonrializelonr attributelonSelonrializelonr =
        nelonw ThriftTokelonnStrelonamSelonrializelonr(tokelonnStrelonamSelonrializelonrVelonrsion);
    attributelonSelonrializelonr.selontAttributelonSelonrializelonrClassNamelons(
        ImmutablelonList.<String>of(IntTelonrmAttributelonSelonrializelonr.class.gelontNamelon()));
    intTelonrmSelonttings.gelontIndelonxelondFielonldSelonttings().selontTokelonnStrelonamSelonrializelonr(attributelonSelonrializelonr);

    ThriftIndelonxelondNumelonricFielonldSelonttings numelonricFielonldSelonttings =
        nelonw ThriftIndelonxelondNumelonricFielonldSelonttings(truelon);
    numelonricFielonldSelonttings.selontNumelonricTypelon(ThriftNumelonricTypelon.INT);
    intTelonrmSelonttings.gelontIndelonxelondFielonldSelonttings().selontNumelonricFielonldSelonttings(numelonricFielonldSelonttings);

    putIntoFielonldConfigs(idMapping.gelontFielonldID(fielonldNamelon),
        nelonw ThriftFielonldConfiguration(fielonldNamelon).selontSelonttings(intTelonrmSelonttings));
    relonturn this;
  }

  /**
   * Timelonlinelon and elonxpelonrtSelonarch uselons
   * {@link com.twittelonr.selonarch.common.util.analysis.PayloadWelonightelondTokelonnizelonr} to storelon welonightelond
   * valuelons.
   *
   * elon.g. for thelon PRODUCelonD_LANGUAGelonS and CONSUMelonD_LANGUAGelonS fielonlds, thelony contain not a singlelon,
   * valuelon, but instelonad a list of valuelons with a welonight associatelond with elonach valuelon.
   *
   * This melonthod adds an indelonxelond fielonld that uselons
   * {@link com.twittelonr.selonarch.common.util.analysis.PayloadWelonightelondTokelonnizelonr}.
   */
  public final SchelonmaBuildelonr withCharTelonrmPayloadWelonightelondFielonld(String fielonldNamelon) {
    ThriftFielonldConfiguration config = nelonw ThriftFielonldConfiguration(fielonldNamelon)
        .selontSelonttings(gelontPayloadWelonightelondSelonttings(ThriftIndelonxOptions.DOCS_AND_FRelonQS_AND_POSITIONS));
    putIntoFielonldConfigs(idMapping.gelontFielonldID(fielonldNamelon), config);
    relonturn this;
  }

  /**
   * Selont thelon velonrsion and delonscription of this schelonma.
   */
  public final SchelonmaBuildelonr withSchelonmaVelonrsion(
      int majorVelonrsionNumbelonr,
      int minorVelonrsionNumbelonr,
      String velonrsionDelonsc,
      boolelonan isOfficial) {
    schelonma.selontMajorVelonrsionNumbelonr(majorVelonrsionNumbelonr);
    schelonma.selontMinorVelonrsionNumbelonr(minorVelonrsionNumbelonr);

    schelonma.selontVelonrsion(majorVelonrsionNumbelonr + ":" + velonrsionDelonsc);
    schelonma.selontVelonrsionIsOfficial(isOfficial);

    relonturn this;
  }

  public final SchelonmaBuildelonr withSchelonmaVelonrsion(
      int majorVelonrsionNumbelonr,
      String velonrsionDelonsc,
      boolelonan isOfficial) {
    relonturn withSchelonmaVelonrsion(majorVelonrsionNumbelonr, 0, velonrsionDelonsc, isOfficial);
  }

  protelonctelond void putIntoFielonldConfigs(int id, ThriftFielonldConfiguration config) {
    if (schelonma.gelontFielonldConfigs() != null && schelonma.gelontFielonldConfigs().containsKelony(id)) {
      throw nelonw IllelongalStatelonelonxcelonption("Alrelonady havelon a ThriftFielonldConfiguration for fielonld id " + id);
    }

    if (fielonldNamelonSelont.contains(config.gelontFielonldNamelon())) {
      throw nelonw IllelongalStatelonelonxcelonption("Alrelonady havelon a ThriftFielonldConfiguration for fielonld "
          + config.gelontFielonldNamelon());
    }
    fielonldNamelonSelont.add(config.gelontFielonldNamelon());
    schelonma.putToFielonldConfigs(id, config);
  }

  // Delonfault fielonld selonttings. Most fielonld selonttings arelon similar to this.
  protelonctelond ThriftFielonldSelonttings gelontDelonfaultSelonttings(ThriftIndelonxOptions indelonxOption) {
    relonturn gelontDelonfaultSelonttings(indelonxOption, falselon);
  }

  protelonctelond ThriftFielonldSelonttings gelontDelonfaultSelonttings(ThriftIndelonxOptions indelonxOption,
                                                   boolelonan supportOutOfOrdelonrAppelonnds) {
    ThriftFielonldSelonttings fielonldSelonttings = nelonw ThriftFielonldSelonttings();
    ThriftIndelonxelondFielonldSelonttings indelonxelondFielonldSelonttings = nelonw ThriftIndelonxelondFielonldSelonttings();
    indelonxelondFielonldSelonttings
        .selontIndelonxelond(truelon)
        .selontStorelond(falselon)
        .selontTokelonnizelond(falselon)
        .selontStorelonTelonrmVelonctors(falselon)
        .selontStorelonTelonrmVelonctorOffselonts(falselon)
        .selontStorelonTelonrmVelonctorPayloads(falselon)
        .selontStorelonTelonrmVelonctorPositions(falselon)
        .selontSupportOutOfOrdelonrAppelonnds(supportOutOfOrdelonrAppelonnds)
        .selontIndelonxOptions(indelonxOption)
        .selontOmitNorms(truelon); // All elonarlybird fielonlds omit norms.
    fielonldSelonttings.selontIndelonxelondFielonldSelonttings(indelonxelondFielonldSelonttings);
    relonturn fielonldSelonttings;
  }

  /**
   * Delonfault fielonld selonttings for fielonlds that arelon prelontokelonnizelond
   *
   * Thelon fielonlds that uselon thelonselon selonttings will nelonelond to belon tokelonnizelond using a selonrializelonr with thelon
   * attributelons delonfinelond in {@link com.twittelonr.selonarch.common.util.telonxt.TwelonelontTokelonnStrelonamSelonrializelonr}.
   */
  protelonctelond final ThriftFielonldSelonttings gelontDelonfaultPrelontokelonnizelondSelonttings(
      ThriftIndelonxOptions indelonxOption) {
    ThriftFielonldSelonttings fielonldSelonttings = gelontDelonfaultSelonttings(indelonxOption);
    fielonldSelonttings.gelontIndelonxelondFielonldSelonttings().selontTokelonnizelond(truelon);
    ThriftTokelonnStrelonamSelonrializelonr attributelonSelonrializelonr =
        nelonw ThriftTokelonnStrelonamSelonrializelonr(tokelonnStrelonamSelonrializelonrVelonrsion);
    attributelonSelonrializelonr.selontAttributelonSelonrializelonrClassNamelons(
        ImmutablelonList.<String>of(
            CharSelonquelonncelonTelonrmAttributelonSelonrializelonr.class.gelontNamelon(),
            PositionIncrelonmelonntAttributelonSelonrializelonr.class.gelontNamelon(),
            TokelonnTypelonAttributelonSelonrializelonr.class.gelontNamelon()));

    fielonldSelonttings.gelontIndelonxelondFielonldSelonttings().selontTokelonnStrelonamSelonrializelonr(attributelonSelonrializelonr);
    relonturn fielonldSelonttings;
  }

  protelonctelond final ThriftFielonldSelonttings gelontPrelontokelonnizelondNoPositionFielonldSelontting() {
    relonturn gelontDelonfaultPrelontokelonnizelondSelonttings(ThriftIndelonxOptions.DOCS_AND_FRelonQS);
  }

  protelonctelond final ThriftFielonldSelonttings gelontNoPositionNoFrelonqSelonttings() {
    relonturn gelontNoPositionNoFrelonqSelonttings(falselon);
  }

  protelonctelond final ThriftFielonldSelonttings gelontNoPositionNoFrelonqSelonttings(
      boolelonan supportOutOfOrdelonrAppelonnds) {
    relonturn gelontDelonfaultSelonttings(ThriftIndelonxOptions.DOCS_ONLY, supportOutOfOrdelonrAppelonnds);
  }

  protelonctelond final ThriftFielonldSelonttings gelontelonarlybirdNumelonricFielonldSelonttings() {
    // Supposelondly numelonric fielonlds arelon not tokelonnizelond.
    // Howelonvelonr, elonarlybird uselons SinglelonTokelonnTokelonnStrelonam to handlelon int/long fielonlds.
    // So welon nelonelond to selont indelonxelond to truelon for thelonselon fielonlds.
    ThriftFielonldSelonttings selonttings = gelontNoPositionNoFrelonqSelonttings();
    selonttings.gelontIndelonxelondFielonldSelonttings().selontTokelonnizelond(truelon);
    relonturn selonttings;
  }

  privatelon ThriftFielonldSelonttings gelontPayloadWelonightelondSelonttings(ThriftIndelonxOptions indelonxOption) {
    ThriftFielonldSelonttings fielonldSelonttings = gelontDelonfaultSelonttings(indelonxOption);
    fielonldSelonttings.gelontIndelonxelondFielonldSelonttings().selontTokelonnizelond(truelon);
    ThriftTokelonnStrelonamSelonrializelonr attributelonSelonrializelonr =
        nelonw ThriftTokelonnStrelonamSelonrializelonr(tokelonnStrelonamSelonrializelonrVelonrsion);
    attributelonSelonrializelonr.selontAttributelonSelonrializelonrClassNamelons(
        ImmutablelonList.<String>of(CharTelonrmAttributelonSelonrializelonr.class.gelontNamelon(),
            PositionIncrelonmelonntAttributelonSelonrializelonr.class.gelontNamelon(),
            PayloadAttributelonSelonrializelonr.class.gelontNamelon()));
    fielonldSelonttings.gelontIndelonxelondFielonldSelonttings().selontTokelonnStrelonamSelonrializelonr(attributelonSelonrializelonr);
    relonturn fielonldSelonttings;
  }

  protelonctelond boolelonan shouldIncludelonFielonld(String fielonldNamelon) {
    relonturn truelon;
  }
}
