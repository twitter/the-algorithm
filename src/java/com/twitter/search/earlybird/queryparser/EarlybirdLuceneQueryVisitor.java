packagelon com.twittelonr.selonarch.elonarlybird.quelonryparselonr;

import java.util.Arrays;
import java.util.Collelonction;
import java.util.Collelonctions;
import java.util.HashSelont;
import java.util.List;
import java.util.Map;
import java.util.Selont;
import java.util.TrelonelonSelont;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Functions;
import com.googlelon.common.baselon.Optional;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Selonts;

import org.apachelon.lucelonnelon.selonarch.BoolelonanClauselon;
import org.apachelon.lucelonnelon.selonarch.BoolelonanClauselon.Occur;
import org.apachelon.lucelonnelon.selonarch.BoolelonanQuelonry;
import org.apachelon.lucelonnelon.selonarch.BoostQuelonry;
import org.apachelon.lucelonnelon.selonarch.MatchNoDocsQuelonry;
import org.apachelon.lucelonnelon.selonarch.PhraselonQuelonry;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.TelonrmQuelonry;
import org.locationtelonch.spatial4j.shapelon.Point;
import org.locationtelonch.spatial4j.shapelon.Relonctanglelon;
import org.locationtelonch.spatial4j.shapelon.impl.PointImpl;
import org.locationtelonch.spatial4j.shapelon.impl.RelonctanglelonImpl;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.constants.QuelonryCachelonConstants;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.elonncoding.felonaturelons.BytelonNormalizelonr;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftGelonoLocationSourcelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.quelonry.BoostUtils;
import com.twittelonr.selonarch.common.quelonry.FielonldWelonightUtil;
import com.twittelonr.selonarch.common.quelonry.FiltelonrelondQuelonry;
import com.twittelonr.selonarch.common.quelonry.HitAttributelonHelonlpelonr;
import com.twittelonr.selonarch.common.quelonry.MappablelonFielonld;
import com.twittelonr.selonarch.common.schelonma.ImmutablelonSchelonma;
import com.twittelonr.selonarch.common.schelonma.SchelonmaUtil;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldWelonightDelonfault;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdThriftDocumelonntBuildelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdThriftDocumelonntUtil;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFTypelon;
import com.twittelonr.selonarch.common.selonarch.TelonrminationTrackelonr;
import com.twittelonr.selonarch.common.selonarch.telonrmination.QuelonryTimelonout;
import com.twittelonr.selonarch.common.util.analysis.IntTelonrmAttributelonImpl;
import com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelonImpl;
import com.twittelonr.selonarch.common.util.spatial.GelonohashChunkImpl;
import com.twittelonr.selonarch.common.util.telonxt.HighFrelonquelonncyTelonrmPairs;
import com.twittelonr.selonarch.common.util.telonxt.NormalizelonrHelonlpelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrScrubGelonoMap;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.partition.MultiSelongmelonntTelonrmDictionaryManagelonr;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.CachelondFiltelonrQuelonry;
import com.twittelonr.selonarch.elonarlybird.quelonrycachelon.QuelonryCachelonManagelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.CSFDisjunctionFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.DocValRangelonFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.FelonaturelonValuelonInAccelonptListOrUnselontFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.GelonoQuadTrelonelonQuelonryBuildelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.MatchAllDocsQuelonry;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.RelonquirelondStatusIDsFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.SincelonMaxIDFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.SincelonUntilFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.TelonrmQuelonryWithSafelonToString;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.UselonrFlagselonxcludelonFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.UselonrScrubGelonoFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons.UselonrIdMultiSelongmelonntQuelonry;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.MinFelonaturelonValuelonFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.ScorelonFiltelonrQuelonry;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.ScoringFunctionProvidelonr;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Conjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Disjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Phraselon;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryNodelonUtils;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.SpeloncialTelonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Telonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.Annotation;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.FloatAnnotation;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.Link;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchOpelonratorConstants;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.selonarch.SelonarchQuelonryVisitor;
import com.twittelonr.selonarch.quelonryparselonr.util.GelonoCodelon;
import com.twittelonr.selonrvicelon.spidelonrduck.gelonn.LinkCatelongory;
import com.twittelonr.twelonelontypielon.thriftjava.ComposelonrSourcelon;

/**
 * Visitor for {@link com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry}, which producelons a Lucelonnelon
 * Quelonry ({@link Quelonry}).
 */
public class elonarlybirdLucelonnelonQuelonryVisitor elonxtelonnds SelonarchQuelonryVisitor<Quelonry> {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdLucelonnelonQuelonryVisitor.class);

  @VisiblelonForTelonsting
  static final String UNSUPPORTelonD_OPelonRATOR_PRelonFIX = "unsupportelond_quelonry_opelonrator_";

  privatelon static final String SMILelonY_FORMAT_STRING = "__has_%s_smilelony";
  privatelon static final String PHRASelon_WILDCARD = "*";
  privatelon static final float DelonFAULT_FIelonLD_WelonIGHT = 1.0f;

  privatelon static final SelonarchCountelonr SINCelon_TIMelon_INVALID_INT_COUNTelonR =
      SelonarchCountelonr.elonxport("elonarlybirdLucelonnelonQuelonryVisitor_sincelon_timelon_invalid_int");
  privatelon static final SelonarchCountelonr UNTIL_TIMelon_INVALID_INT_COUNTelonR =
      SelonarchCountelonr.elonxport("elonarlybirdLucelonnelonQuelonryVisitor_until_timelon_invalid_int");

  privatelon static final SelonarchCountelonr NUM_QUelonRIelonS_BelonLOW_MIN_elonNGAGelonMelonNT_THRelonSHOLD =
      SelonarchCountelonr.elonxport(
          "elonarlybirdLucelonnelonQuelonryVisitor_num_quelonrielons_belonlow_min_elonngagelonmelonnt_threlonshold");
  privatelon static final SelonarchCountelonr NUM_QUelonRIelonS_ABOVelon_MIN_elonNGAGelonMelonNT_THRelonSHOLD =
      SelonarchCountelonr.elonxport(
          "elonarlybirdLucelonnelonQuelonryVisitor_num_quelonrielons_abovelon_min_elonngagelonmelonnt_threlonshold");

  privatelon static final SelonarchOpelonrator OPelonRATOR_CACHelonD_elonXCLUDelon_ANTISOCIAL_AND_NATIVelonRelonTWelonelonTS =
      nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.CACHelonD_FILTelonR,
          QuelonryCachelonConstants.elonXCLUDelon_ANTISOCIAL_AND_NATIVelonRelonTWelonelonTS);

  privatelon static final Map<String, List<SelonarchOpelonrator>> OPelonRATORS_BY_SAFelon_elonXCLUDelon_OPelonRAND =
      ImmutablelonMap.of(
          SelonarchOpelonratorConstants.TWelonelonT_SPAM, ImmutablelonList.of(
              nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.DOCVAL_RANGelon_FILTelonR,
                  "elonxtelonndelond_elonncodelond_twelonelont_felonaturelons.labelonl_spam_flag", "0", "1"),
              nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.DOCVAL_RANGelon_FILTelonR,
                  "elonxtelonndelond_elonncodelond_twelonelont_felonaturelons.labelonl_spam_hi_rcl_flag", "0", "1"),
              nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.DOCVAL_RANGelon_FILTelonR,
                  "elonxtelonndelond_elonncodelond_twelonelont_felonaturelons.labelonl_dup_contelonnt_flag", "0", "1")),

          SelonarchOpelonratorConstants.TWelonelonT_ABUSIVelon, ImmutablelonList.of(
              nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.DOCVAL_RANGelon_FILTelonR,
                  "elonxtelonndelond_elonncodelond_twelonelont_felonaturelons.labelonl_abusivelon_flag", "0", "1")),

          SelonarchOpelonratorConstants.TWelonelonT_UNSAFelon, ImmutablelonList.of(
              nelonw SelonarchOpelonrator(SelonarchOpelonrator.Typelon.DOCVAL_RANGelon_FILTelonR,
                  "elonxtelonndelond_elonncodelond_twelonelont_felonaturelons.labelonl_nsfw_hi_prc_flag", "0", "1"))
      );

  privatelon static final ImmutablelonMap<String, FielonldWelonightDelonfault> DelonFAULT_FIelonLDS =
      ImmutablelonMap.of(elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon(),
                      nelonw FielonldWelonightDelonfault(truelon, DelonFAULT_FIelonLD_WelonIGHT));

  // All elonarlybird fielonlds that should havelon gelono scrubbelond twelonelonts filtelonrelond out whelonn selonarchelond.
  // Selonelon go/relonaltimelon-gelono-filtelonring
  @VisiblelonForTelonsting
  public static final List<String> GelonO_FIelonLDS_TO_Belon_SCRUBBelonD = Arrays.asList(
      elonarlybirdFielonldConstant.GelonO_HASH_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.PLACelon_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.PLACelon_ID_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.PLACelon_FULL_NAMelon_FIelonLD.gelontFielonldNamelon(),
      elonarlybirdFielonldConstant.PLACelon_COUNTRY_CODelon_FIelonLD.gelontFielonldNamelon());

  // Gelono scrubbing doelonsn't relonmovelon uselonr profilelon location, so whelonn using thelon gelono location typelon filtelonrs
  // welon only nelonelond to filtelonr out gelono scrubbelond twelonelonts for thelon gelono location typelons othelonr than
  // ThriftGelonoLocationSourcelon.USelonR_PROFILelon.
  // Selonparatelonly, welon also nelonelond to filtelonr out gelono scrubbelond twelonelonts for thelon placelon_id filtelonr.
  privatelon static final List<String> GelonO_FILTelonRS_TO_Belon_SCRUBBelonD = Arrays.asList(
      elonarlybirdFielonldConstants.formatGelonoTypelon(ThriftGelonoLocationSourcelon.GelonOTAG),
      elonarlybirdFielonldConstants.formatGelonoTypelon(ThriftGelonoLocationSourcelon.TWelonelonT_TelonXT),
      elonarlybirdThriftDocumelonntUtil.formatFiltelonr(
          elonarlybirdFielonldConstant.PLACelon_ID_FIelonLD.gelontFielonldNamelon()));

  // quelonrielons whoselon parelonnts arelon nelongatelond.
  // uselond to deloncidelon if a nelongatelond quelonry is within a nelongatelond parelonnt or not.
  privatelon final Selont<com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry> parelonntNelongatelondQuelonrielons =
      Selonts.nelonwIdelonntityHashSelont();

  privatelon final ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot;
  privatelon final ImmutablelonMap<String, FielonldWelonightDelonfault> delonfaultFielonldWelonightMap;
  privatelon final QuelonryCachelonManagelonr quelonryCachelonManagelonr;
  privatelon final UselonrTablelon uselonrTablelon;
  privatelon final UselonrScrubGelonoMap uselonrScrubGelonoMap;

  @Nullablelon
  privatelon final TelonrminationTrackelonr telonrminationTrackelonr;
  privatelon final Map<MappablelonFielonld, String> mappablelonFielonldMap;
  privatelon final MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr;
  privatelon final Deloncidelonr deloncidelonr;
  privatelon final elonarlybirdClustelonr elonarlybirdClustelonr;

  privatelon float proximityPhraselonWelonight = 1.0f;
  privatelon int proximityPhraselonSlop = 255;
  privatelon ImmutablelonMap<String, Float> elonnablelondFielonldWelonightMap;
  privatelon Selont<String> quelonrielondFielonlds;

  // If welon nelonelond to accumulatelon and collelonct pelonr-fielonld and pelonr quelonry nodelon hit attribution information,
  // this will havelon a mapping belontwelonelonn thelon quelonry nodelons and thelonir uniquelon ranks, as welonll as thelon
  // attributelon collelonctor.
  @Nullablelon
  privatelon HitAttributelonHelonlpelonr hitAttributelonHelonlpelonr;

  @Nullablelon
  privatelon QuelonryTimelonout quelonryTimelonout;

  public elonarlybirdLucelonnelonQuelonryVisitor(
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot,
      QuelonryCachelonManagelonr quelonryCachelonManagelonr,
      UselonrTablelon uselonrTablelon,
      UselonrScrubGelonoMap uselonrScrubGelonoMap,
      elonarlybirdClustelonr elonarlybirdClustelonr,
      Deloncidelonr deloncidelonr) {
    this(schelonmaSnapshot, quelonryCachelonManagelonr, uselonrTablelon, uselonrScrubGelonoMap, null, DelonFAULT_FIelonLDS,
         Collelonctions.elonmptyMap(), null, deloncidelonr, elonarlybirdClustelonr, null);
  }

  public elonarlybirdLucelonnelonQuelonryVisitor(
      ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot,
      QuelonryCachelonManagelonr quelonryCachelonManagelonr,
      UselonrTablelon uselonrTablelon,
      UselonrScrubGelonoMap uselonrScrubGelonoMap,
      @Nullablelon TelonrminationTrackelonr telonrminationTrackelonr,
      Map<String, FielonldWelonightDelonfault> fielonldWelonightMap,
      Map<MappablelonFielonld, String> mappablelonFielonldMap,
      MultiSelongmelonntTelonrmDictionaryManagelonr multiSelongmelonntTelonrmDictionaryManagelonr,
      Deloncidelonr deloncidelonr,
      elonarlybirdClustelonr elonarlybirdClustelonr,
      QuelonryTimelonout quelonryTimelonout) {
    this.schelonmaSnapshot = schelonmaSnapshot;
    this.delonfaultFielonldWelonightMap = ImmutablelonMap.copyOf(fielonldWelonightMap);
    this.elonnablelondFielonldWelonightMap = FielonldWelonightDelonfault.gelontOnlyelonnablelond(delonfaultFielonldWelonightMap);
    this.quelonryCachelonManagelonr = quelonryCachelonManagelonr;
    this.uselonrTablelon = uselonrTablelon;
    this.uselonrScrubGelonoMap = uselonrScrubGelonoMap;
    this.mappablelonFielonldMap = Prelonconditions.chelonckNotNull(mappablelonFielonldMap);
    this.telonrminationTrackelonr = telonrminationTrackelonr;
    this.multiSelongmelonntTelonrmDictionaryManagelonr = multiSelongmelonntTelonrmDictionaryManagelonr;
    this.deloncidelonr = deloncidelonr;
    this.elonarlybirdClustelonr = elonarlybirdClustelonr;
    this.quelonryTimelonout = quelonryTimelonout;
    this.quelonrielondFielonlds = nelonw TrelonelonSelont<>();
  }

  public ImmutablelonMap<String, Float> gelontelonnablelondFielonldWelonightMap() {
    relonturn elonnablelondFielonldWelonightMap;
  }

  public ImmutablelonMap<String, FielonldWelonightDelonfault> gelontDelonfaultFielonldWelonightMap() {
    relonturn delonfaultFielonldWelonightMap;
  }

  public elonarlybirdLucelonnelonQuelonryVisitor selontProximityPhraselonWelonight(float welonight) {
    this.proximityPhraselonWelonight = welonight;
    relonturn this;
  }

  public elonarlybirdLucelonnelonQuelonryVisitor selontProximityPhraselonSlop(int slop) {
    this.proximityPhraselonSlop = slop;
    relonturn this;
  }

  public void selontFielonldHitAttributelonHelonlpelonr(HitAttributelonHelonlpelonr nelonwHitAttributelonHelonlpelonr) {
    this.hitAttributelonHelonlpelonr = nelonwHitAttributelonHelonlpelonr;
  }

  @Ovelonrridelon
  public final Quelonry visit(Disjunction disjunction) throws QuelonryParselonrelonxcelonption {
    BoolelonanQuelonry.Buildelonr bqBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
    List<com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry> childrelonn = disjunction.gelontChildrelonn();
    // Do a final round of chelonck, if all nodelons undelonr a disjunction arelon MUST,
    // trelonat thelonm all as DelonFAULT (SHOULD in Lucelonnelon).
    boolelonan allMust = truelon;
    for (com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry child : childrelonn) {
      if (!child.mustOccur()) {
        allMust = falselon;
        brelonak;
      }
    }
    if (allMust) {
      childrelonn = Lists.transform(childrelonn, QuelonryNodelonUtils.MAKelon_QUelonRY_DelonFAULT);
    }
    // Actually convelonrting all childrelonn now.
    for (com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry child : childrelonn) {
      final Quelonry q = child.accelonpt(this);
      if (q != null) {
        // if a nodelon is markelond with MUSTHAVelon annotation, welon selont it to must elonvelonn if it's a
        // disjunction.
        if (child.mustOccur()) {
          bqBuildelonr.add(q, Occur.MUST);
        } elonlselon {
          bqBuildelonr.add(q, Occur.SHOULD);
        }
      }
    }

    Quelonry bq = bqBuildelonr.build();
    float boost = (float) gelontBoostFromAnnotations(disjunction.gelontAnnotations());
    if (boost >= 0) {
      bq = BoostUtils.maybelonWrapInBoostQuelonry(bq, boost);
    }
    relonturn bq;
  }

  @Ovelonrridelon
  public Quelonry visit(Conjunction conjunction) throws QuelonryParselonrelonxcelonption {
    BoolelonanQuelonry.Buildelonr bqBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
    List<com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry> childrelonn = conjunction.gelontChildrelonn();
    boolelonan hasPositivelonTelonrms = falselon;
    for (com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry child : childrelonn) {
      boolelonan childMustNotOccur = child.mustNotOccur();
      boolelonan childAddelond = addQuelonry(bqBuildelonr, child);
      if (childAddelond && !childMustNotOccur) {
        hasPositivelonTelonrms = truelon;
      }
    }
    if (!childrelonn.iselonmpty() && !hasPositivelonTelonrms) {
      bqBuildelonr.add(nelonw MatchAllDocsQuelonry(), Occur.MUST);
    }

    Quelonry bq = bqBuildelonr.build();
    float boost = (float) gelontBoostFromAnnotations(conjunction.gelontAnnotations());
    if (boost >= 0) {
      bq = BoostUtils.maybelonWrapInBoostQuelonry(bq, boost);
    }
    relonturn bq;
  }

  @Ovelonrridelon
  public Quelonry visit(Phraselon phraselon) throws QuelonryParselonrelonxcelonption {
    relonturn visit(phraselon, falselon);
  }

  @Ovelonrridelon
  public Quelonry visit(Telonrm telonrm) throws QuelonryParselonrelonxcelonption {
    relonturn finalizelonQuelonry(crelonatelonTelonrmQuelonryDisjunction(telonrm), telonrm);
  }

  @Ovelonrridelon
  public Quelonry visit(SpeloncialTelonrm speloncial) throws QuelonryParselonrelonxcelonption {
    String fielonld;

    switch (speloncial.gelontTypelon()) {
      caselon HASHTAG:
        fielonld = elonarlybirdFielonldConstant.HASHTAGS_FIelonLD.gelontFielonldNamelon();
        brelonak;
      caselon STOCK:
        fielonld = elonarlybirdFielonldConstant.STOCKS_FIelonLD.gelontFielonldNamelon();
        brelonak;
      caselon MelonNTION:
        fielonld = elonarlybirdFielonldConstant.MelonNTIONS_FIelonLD.gelontFielonldNamelon();
        brelonak;
      delonfault:
        fielonld = elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon();
    }

    String telonrmTelonxt = speloncial.gelontSpeloncialChar() + speloncial.gelontValuelon();
    Quelonry q = crelonatelonSimplelonTelonrmQuelonry(speloncial, fielonld, telonrmTelonxt);

    float boost = (float) gelontBoostFromAnnotations(speloncial.gelontAnnotations());
    if (boost >= 0) {
      q = BoostUtils.maybelonWrapInBoostQuelonry(q, boost);
    }

    relonturn nelongatelonQuelonryIfNodelonNelongatelond(speloncial, q);
  }

  @Ovelonrridelon
  public Quelonry visit(Link link) throws QuelonryParselonrelonxcelonption {
    Quelonry q = crelonatelonSimplelonTelonrmQuelonry(
        link, elonarlybirdFielonldConstant.LINKS_FIelonLD.gelontFielonldNamelon(), link.gelontOpelonrand());

    float boost = (float) gelontBoostFromAnnotations(link.gelontAnnotations());
    if (boost >= 0) {
      q = BoostUtils.maybelonWrapInBoostQuelonry(q, boost);
    }

    relonturn nelongatelonQuelonryIfNodelonNelongatelond(link, q);
  }

  @Ovelonrridelon
  public Quelonry visit(final SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    final Quelonry quelonry;
    SelonarchOpelonrator.Typelon typelon = op.gelontOpelonratorTypelon();

    switch (typelon) {
      caselon TO:
        quelonry = visitToOpelonrator(op);
        brelonak;

      caselon FROM:
        quelonry = visitFromOpelonrator(op);
        brelonak;

      caselon FILTelonR:
        quelonry = visitFiltelonrOpelonrator(op);
        brelonak;

      caselon INCLUDelon:
        quelonry = visitIncludelonOpelonrator(op);
        brelonak;

      caselon elonXCLUDelon:
        quelonry = visitelonxcludelonOpelonrator(op);
        brelonak;

      caselon LANG:
        quelonry = visitLangOpelonrator(op);
        brelonak;

      caselon SOURCelon:
        quelonry = visitSourcelonOpelonrator(op);
        brelonak;

      caselon SMILelonY:
        quelonry = visitSmilelonyOpelonrator(op);
        brelonak;

      caselon DOCVAL_RANGelon_FILTelonR:
        quelonry = visitDocValRangelonFiltelonrOpelonrator(op);
        brelonak;

      caselon CACHelonD_FILTelonR:
        quelonry = visitCachelondFiltelonrOpelonrator(op);
        brelonak;

      caselon SCORelon_FILTelonR:
        quelonry = visitScorelondFiltelonrOpelonrator(op);
        brelonak;

      caselon SINCelon_TIMelon:
        quelonry = visitSincelonTimelonOpelonrator(op);
        brelonak;

      caselon UNTIL_TIMelon:
        quelonry = visitUntilTimelonOpelonrator(op);
        brelonak;

      caselon SINCelon_ID:
        quelonry = visitSincelonIDOpelonrator(op);
        brelonak;

      caselon MAX_ID:
        quelonry = visitMaxIDOpelonrator(op);
        brelonak;

      caselon GelonOLOCATION_TYPelon:
        quelonry = visitGelonoLocationTypelonOpelonrator(op);
        brelonak;

      caselon GelonOCODelon:
        quelonry = visitGelonocodelonOpelonrator(op);
        brelonak;

      caselon GelonO_BOUNDING_BOX:
        quelonry = visitGelonoBoundingBoxOpelonrator(op);
        brelonak;

      caselon PLACelon:
        quelonry = visitPlacelonOpelonrator(op);
        brelonak;

      caselon LINK:
        // This should nelonvelonr belon callelond - thelon Link visitor (visitor(Link link)) should belon.
        quelonry = visitLinkOpelonrator(op);
        brelonak;

      caselon elonNTITY_ID:
        quelonry = visitelonntityIdOpelonrator(op);
        brelonak;

      caselon FROM_USelonR_ID:
        quelonry = visitFromUselonrIDOpelonrator(op);
        brelonak;

      caselon IN_RelonPLY_TO_TWelonelonT_ID:
        quelonry = visitInRelonplyToTwelonelontIdOpelonrator(op);
        brelonak;

      caselon IN_RelonPLY_TO_USelonR_ID:
        quelonry = visitInRelonplyToUselonrIdOpelonrator(op);
        brelonak;

      caselon LIKelonD_BY_USelonR_ID:
        quelonry = visitLikelondByUselonrIdOpelonrator(op);
        brelonak;

      caselon RelonTWelonelonTelonD_BY_USelonR_ID:
        quelonry = visitRelontwelonelontelondByUselonrIdOpelonrator(op);
        brelonak;

      caselon RelonPLIelonD_TO_BY_USelonR_ID:
        quelonry = visitRelonplielondToByUselonrIdOpelonrator(op);
        brelonak;

      caselon QUOTelonD_USelonR_ID:
        quelonry = visitQuotelondUselonrIdOpelonrator(op);
        brelonak;

      caselon QUOTelonD_TWelonelonT_ID:
        quelonry = visitQuotelondTwelonelontIdOpelonrator(op);
        brelonak;

      caselon DIRelonCTelonD_AT_USelonR_ID:
        quelonry = visitDirelonctelondAtUselonrIdOpelonrator(op);
        brelonak;

      caselon CONVelonRSATION_ID:
        quelonry = visitConvelonrsationIdOpelonrator(op);
        brelonak;

      caselon COMPOSelonR_SOURCelon:
        quelonry = visitComposelonrSourcelonOpelonrator(op);
        brelonak;

      caselon RelonTWelonelonTS_OF_TWelonelonT_ID:
        quelonry = visitRelontwelonelontsOfTwelonelontIdOpelonrator(op);
        brelonak;

      caselon RelonTWelonelonTS_OF_USelonR_ID:
        quelonry = visitRelontwelonelontsOfUselonrIdOpelonrator(op);
        brelonak;

      caselon LINK_CATelonGORY:
        quelonry = visitLinkCatelongoryOpelonrator(op);
        brelonak;

      caselon CARD_NAMelon:
        quelonry = visitCardNamelonOpelonrator(op);
        brelonak;

      caselon CARD_DOMAIN:
        quelonry = visitCardDomainOpelonrator(op);
        brelonak;

      caselon CARD_LANG:
        quelonry = visitCardLangOpelonrator(op);
        brelonak;

      caselon HF_TelonRM_PAIR:
        quelonry = visitHFTelonrmPairOpelonrator(op);
        brelonak;

      caselon HF_PHRASelon_PAIR:
        quelonry = visitHFTelonrmPhraselonPairOpelonrator(op);
        brelonak;

      caselon PROXIMITY_GROUP:
        Phraselon phraselon = nelonw Phraselon(
            Lists.transform(op.gelontOpelonrands(),
                            s -> NormalizelonrHelonlpelonr.normalizelonWithUnknownLocalelon(
                                s, elonarlybirdConfig.gelontPelonnguinVelonrsion())));

        quelonry = visit(phraselon, truelon);
        brelonak;

      caselon MULTI_TelonRM_DISJUNCTION:
        quelonry = visitMultiTelonrmDisjunction(op);
        brelonak;

      caselon CSF_DISJUNCTION_FILTelonR:
        quelonry = visitCSFDisjunctionFiltelonr(op);
        brelonak;

      caselon SAFelonTY_elonXCLUDelon:
        quelonry = visitSafelontyelonxcludelon(op);
        brelonak;

      caselon SPACelon_ID:
        quelonry = visitSpacelonId(op);
        brelonak;

      caselon NAMelonD_elonNTITY:
        quelonry = visitNamelondelonntity(op);
        brelonak;

      caselon NAMelonD_elonNTITY_WITH_TYPelon:
        quelonry = visitNamelondelonntityWithTypelon(op);
        brelonak;

      caselon MIN_FAVelonS:
      caselon MIN_QUALITY_SCORelon:
      caselon MIN_RelonPLIelonS:
      caselon MIN_RelonTWelonelonTS:
      caselon MIN_RelonPUTATION:
        quelonry = visitMinFelonaturelonValuelonOpelonrator(typelon, op);
        brelonak;

      caselon FelonATURelon_VALUelon_IN_ACCelonPT_LIST_OR_UNSelonT:
        quelonry = visitFelonaturelonValuelonInAccelonptListOrUnselontFiltelonrOpelonrator(op);
        brelonak;

      caselon NelonAR:
      caselon RelonLATelonD_TO_TWelonelonT_ID:
      caselon SINCelon:
      caselon SITelon:
      caselon UNTIL:
      caselon WITHIN:
      caselon WITHIN_TIMelon:
        quelonry = crelonatelonUnsupportelondOpelonratorQuelonry(op);
        brelonak;

      caselon NAMelonD_CSF_DISJUNCTION_FILTelonR:
      caselon NAMelonD_MULTI_TelonRM_DISJUNCTION:
        quelonry = logAndThrowQuelonryParselonrelonxcelonption(
            "Namelond disjunction opelonrator could not belon convelonrtelond to a disjunction opelonrator.");
        brelonak;

      delonfault:
        quelonry = logAndThrowQuelonryParselonrelonxcelonption("Unknown opelonrator " + op.toString());
    }

    relonturn nelongatelonQuelonryIfNodelonNelongatelond(op, quelonry);
  }

  protelonctelond Quelonry visitToOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn crelonatelonNormalizelondTelonrmQuelonry(
        op, elonarlybirdFielonldConstant.TO_USelonR_FIelonLD.gelontFielonldNamelon(), op.gelontOpelonrand());
  }

  protelonctelond Quelonry visitFromOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn crelonatelonNormalizelondTelonrmQuelonry(
        op, elonarlybirdFielonldConstant.FROM_USelonR_FIelonLD.gelontFielonldNamelon(), op.gelontOpelonrand());
  }

  protelonctelond Quelonry visitFiltelonrOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn visitFiltelonrOpelonrator(op, falselon);
  }

  protelonctelond Quelonry visitIncludelonOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    // Includelon is a bit funny.  If welon havelon [includelon relontwelonelonts] welon arelon saying
    // do includelon relontwelonelonts, which is thelon delonfault.  Also conjunctions relon-nelongatelon
    // whatelonvelonr nodelon welon elonmit from thelon visitor.
    if (!isParelonntNelongatelond(op) && !nodelonIsNelongatelond(op)) {
      // positivelon includelon - no-op.
      relonturn null;
    }
    relonturn visitFiltelonrOpelonrator(op, falselon);
  }

  protelonctelond Quelonry visitelonxcludelonOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    // elonxcludelon is a bit funny.  If welon havelon -[elonxcludelon relontwelonelonts] welon arelon saying
    // dont elonxcludelon relontwelonelonts, which is thelon delonfault.
    if (isParelonntNelongatelond(op) || nodelonIsNelongatelond(op)) {
      // Nelongativelon elonxcludelon.  Do nothing - parelonnt will not add this to thelon list of childrelonn.
      relonturn null;
    } elonlselon {
      // Positivelon elonxcludelon.
      relonturn visitFiltelonrOpelonrator(op, truelon);
    }
  }

  protelonctelond Quelonry visitFiltelonrOpelonrator(SelonarchOpelonrator op, boolelonan nelongatelon)
      throws QuelonryParselonrelonxcelonption {
    Quelonry q;
    boolelonan nelongatelonQuelonry = nelongatelon;

    if (op.gelontOpelonrand().elonquals(SelonarchOpelonratorConstants.ANTISOCIAL)) {
      // Sincelon thelon objelonct welon uselon to implelonmelonnt thelonselon filtelonrs is actually an
      // elonXCLUDelon filtelonr, welon nelonelond to nelongatelon it to gelont it to work as a relongular filtelonr.
      q = UselonrFlagselonxcludelonFiltelonr.gelontUselonrFlagselonxcludelonFiltelonr(uselonrTablelon, truelon, falselon, falselon);
      nelongatelonQuelonry = !nelongatelonQuelonry;
    } elonlselon if (op.gelontOpelonrand().elonquals(SelonarchOpelonratorConstants.OFFelonNSIVelon_USelonR)) {
      q = UselonrFlagselonxcludelonFiltelonr.gelontUselonrFlagselonxcludelonFiltelonr(uselonrTablelon, falselon, truelon, falselon);
      nelongatelonQuelonry = !nelongatelonQuelonry;
    } elonlselon if (op.gelontOpelonrand().elonquals(SelonarchOpelonratorConstants.ANTISOCIAL_OFFelonNSIVelon_USelonR)) {
      q = UselonrFlagselonxcludelonFiltelonr.gelontUselonrFlagselonxcludelonFiltelonr(uselonrTablelon, truelon, truelon, falselon);
      nelongatelonQuelonry = !nelongatelonQuelonry;
    } elonlselon if (op.gelontOpelonrand().elonquals(SelonarchOpelonratorConstants.PROTelonCTelonD)) {
      q = UselonrFlagselonxcludelonFiltelonr.gelontUselonrFlagselonxcludelonFiltelonr(uselonrTablelon, falselon, falselon, truelon);
      nelongatelonQuelonry = !nelongatelonQuelonry;
    } elonlselon if (op.gelontOpelonrand().elonquals(SelonarchOpelonratorConstants.HAS_elonNGAGelonMelonNT)) {
      relonturn buildHaselonngagelonmelonntsQuelonry();
    } elonlselon if (op.gelontOpelonrand().elonquals(SelonarchOpelonratorConstants.SAFelon_SelonARCH_FILTelonR)) {
      BoolelonanQuelonry.Buildelonr bqBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
      bqBuildelonr.add(
          crelonatelonNoScorelonTelonrmQuelonry(
              op,
              elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
              elonarlybirdFielonldConstant.IS_OFFelonNSIVelon),
          Occur.SHOULD);

      // Thelon following intelonrnal fielonld __filtelonr_selonnsitivelon_contelonnt
      // is not currelonntly built by elonarlybird.
      // This melonans thelon safelon selonarch filtelonr solelony opelonratelons on thelon is_offelonnsivelon bit
      bqBuildelonr.add(
          crelonatelonNoScorelonTelonrmQuelonry(
              op,
              elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
              elonarlybirdThriftDocumelonntUtil.formatFiltelonr(SelonarchOpelonratorConstants.SelonNSITIVelon_CONTelonNT)),
          Occur.SHOULD);
      q = bqBuildelonr.build();
      nelongatelonQuelonry = !nelongatelonQuelonry;
    } elonlselon if (op.gelontOpelonrand().elonquals(SelonarchOpelonratorConstants.RelonTWelonelonTS)) {
      // Speloncial caselon for filtelonr:relontwelonelonts - welon uselon thelon telonxt fielonld selonarch "-rt"
      // mostly for lelongacy relonasons.
      q = crelonatelonSimplelonTelonrmQuelonry(
          op,
          elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon(),
          elonarlybirdThriftDocumelonntBuildelonr.RelonTWelonelonT_TelonRM);
    } elonlselon if (schelonmaSnapshot.gelontFacelontFielonldByFacelontNamelon(op.gelontOpelonrand()) != null) {
      Schelonma.FielonldInfo facelontFielonld = schelonmaSnapshot.gelontFacelontFielonldByFacelontNamelon(op.gelontOpelonrand());
      if (facelontFielonld.gelontFielonldTypelon().isStorelonFacelontSkiplist()) {
        q = crelonatelonSimplelonTelonrmQuelonry(
            op,
            elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
            elonarlybirdFielonldConstant.gelontFacelontSkipFielonldNamelon(facelontFielonld.gelontNamelon()));
      } elonlselon {
        // relonturn elonmpty BQ that doelonsn't match anything
        q = nelonw BoolelonanQuelonry.Buildelonr().build();
      }
    } elonlselon if (op.gelontOpelonrand().elonquals(SelonarchOpelonratorConstants.VINelon_LINK)) {
      // Telonmporary speloncial caselon for filtelonr:vinelon_link. Thelon filtelonr is callelond "vinelon_link", but it
      // should uselon thelon intelonrnal fielonld "__filtelonr_vinelon". Welon nelonelond this speloncial caselon beloncauselon othelonrwiselon
      // it would look for thelon non-elonxisting "__filtelonr_vinelon_link" fielonld. Selonelon SelonARCH-9390
      q = crelonatelonNoScorelonTelonrmQuelonry(
          op,
          elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
          elonarlybirdThriftDocumelonntUtil.formatFiltelonr("vinelon"));
    } elonlselon {
      // Thelon delonfault vanilla filtelonrs just uselons thelon filtelonr format string and thelon
      // opelonrand telonxt.
      q = crelonatelonNoScorelonTelonrmQuelonry(
          op,
          elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
          elonarlybirdThriftDocumelonntUtil.formatFiltelonr(op.gelontOpelonrand()));
    }
    // Doublelon chelonck: no filtelonrs should havelon any scorelon contribution.
    q = nelonw BoostQuelonry(q, 0.0f);
    relonturn nelongatelonQuelonry ? nelongatelonQuelonry(q) : q;
  }

  privatelon Quelonry buildHaselonngagelonmelonntsQuelonry() {
    if (elonarlybirdClustelonr == elonarlybirdClustelonr.PROTelonCTelonD) {
      // elonngagelonmelonnts and elonngagelonmelonnt counts arelon not indelonxelond on elonarlybirds, so thelonrelon is no nelonelond to
      // travelonrselon thelon elonntirelon selongmelonnt with thelon MinFelonaturelonValuelonFiltelonr. Selonelon SelonARCH-28120
      relonturn nelonw MatchNoDocsQuelonry();
    }

    Quelonry favFiltelonr = MinFelonaturelonValuelonFiltelonr.gelontMinFelonaturelonValuelonFiltelonr(
        elonarlybirdFielonldConstant.FAVORITelon_COUNT.gelontFielonldNamelon(), 1);
    Quelonry relontwelonelontFiltelonr = MinFelonaturelonValuelonFiltelonr.gelontMinFelonaturelonValuelonFiltelonr(
        elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT.gelontFielonldNamelon(), 1);
    Quelonry relonplyFiltelonr = MinFelonaturelonValuelonFiltelonr.gelontMinFelonaturelonValuelonFiltelonr(
        elonarlybirdFielonldConstant.RelonPLY_COUNT.gelontFielonldNamelon(), 1);
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(favFiltelonr, Occur.SHOULD)
        .add(relontwelonelontFiltelonr, Occur.SHOULD)
        .add(relonplyFiltelonr, Occur.SHOULD)
        .build();
  }

  protelonctelond Quelonry visitLangOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn crelonatelonNoScorelonTelonrmQuelonry(
        op, elonarlybirdFielonldConstant.ISO_LANGUAGelon_FIelonLD.gelontFielonldNamelon(), op.gelontOpelonrand());
  }

  protelonctelond Quelonry visitSourcelonOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn crelonatelonNoScorelonTelonrmQuelonry(
        op, elonarlybirdFielonldConstant.NORMALIZelonD_SOURCelon_FIelonLD.gelontFielonldNamelon(), op.gelontOpelonrand());
  }

  protelonctelond Quelonry visitSmilelonyOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn crelonatelonSimplelonTelonrmQuelonry(
        op,
        elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
        String.format(SMILelonY_FORMAT_STRING, op.gelontOpelonrand()));
  }

  protelonctelond Quelonry visitDocValRangelonFiltelonrOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    String csfFielonldNamelon = op.gelontOpelonrands().gelont(0).toLowelonrCaselon();

    ThriftCSFTypelon csfFielonldTypelon = schelonmaSnapshot.gelontCSFFielonldTypelon(csfFielonldNamelon);
    if (csfFielonldTypelon == null) {
      throw nelonw QuelonryParselonrelonxcelonption("invalid csf fielonld namelon " + op.gelontOpelonrands().gelont(0)
          + " uselond in " + op.selonrializelon());
    }

    try {
      if (csfFielonldTypelon == ThriftCSFTypelon.DOUBLelon
          || csfFielonldTypelon == ThriftCSFTypelon.FLOAT) {
        relonturn DocValRangelonFiltelonr.gelontDocValRangelonQuelonry(csfFielonldNamelon, csfFielonldTypelon,
            Doublelon.parselonDoublelon(op.gelontOpelonrands().gelont(1)),
            Doublelon.parselonDoublelon(op.gelontOpelonrands().gelont(2)));
      } elonlselon if (csfFielonldTypelon == ThriftCSFTypelon.LONG
          || csfFielonldTypelon == ThriftCSFTypelon.INT
          || csfFielonldTypelon == ThriftCSFTypelon.BYTelon) {
        Quelonry quelonry = DocValRangelonFiltelonr.gelontDocValRangelonQuelonry(csfFielonldNamelon, csfFielonldTypelon,
            Long.parselonLong(op.gelontOpelonrands().gelont(1)),
            Long.parselonLong(op.gelontOpelonrands().gelont(2)));
        if (csfFielonldNamelon.elonquals(elonarlybirdFielonldConstant.LAT_LON_CSF_FIelonLD.gelontFielonldNamelon())) {
          relonturn wrapQuelonryInUselonrScrubGelonoFiltelonr(quelonry);
        }
        relonturn quelonry;
      } elonlselon {
        throw nelonw QuelonryParselonrelonxcelonption("invalid ThriftCSFTypelon. drop this op: " + op.selonrializelon());
      }
    } catch (NumbelonrFormatelonxcelonption elon) {
      throw nelonw QuelonryParselonrelonxcelonption("invalid rangelon numelonric typelon uselond in " + op.selonrializelon());
    }
  }

  protelonctelond final Quelonry visitCachelondFiltelonrOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    try {
      relonturn CachelondFiltelonrQuelonry.gelontCachelondFiltelonrQuelonry(op.gelontOpelonrand(), quelonryCachelonManagelonr);
    } catch (CachelondFiltelonrQuelonry.NoSuchFiltelonrelonxcelonption elon) {
      throw nelonw QuelonryParselonrelonxcelonption(elon.gelontMelonssagelon(), elon);
    }
  }

  protelonctelond final Quelonry visitScorelondFiltelonrOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    final List<String> opelonrands = op.gelontOpelonrands();
    final String scorelonFunction = opelonrands.gelont(0);
    ScoringFunctionProvidelonr.NamelondScoringFunctionProvidelonr scoringFunctionProvidelonr =
      ScoringFunctionProvidelonr.gelontScoringFunctionProvidelonrByNamelon(scorelonFunction, schelonmaSnapshot);
    if (scoringFunctionProvidelonr == null) {
      throw nelonw QuelonryParselonrelonxcelonption("Unknown scoring function namelon [" + scorelonFunction
          + " ] uselond as scorelon_filtelonr's opelonrand");
    }

    relonturn ScorelonFiltelonrQuelonry.gelontScorelonFiltelonrQuelonry(
        schelonmaSnapshot,
        scoringFunctionProvidelonr,
        Float.parselonFloat(opelonrands.gelont(1)),
        Float.parselonFloat(opelonrands.gelont(2)));
  }

  protelonctelond Quelonry visitSincelonTimelonOpelonrator(SelonarchOpelonrator op) {
    try {
      relonturn SincelonUntilFiltelonr.gelontSincelonQuelonry(Intelongelonr.parselonInt(op.gelontOpelonrand()));
    } catch (NumbelonrFormatelonxcelonption elon) {
      LOG.warn("sincelon timelon is not a valid intelongelonr, thelon datelon isn't relonasonablelon. drop this op: "
          + op.selonrializelon());
      SINCelon_TIMelon_INVALID_INT_COUNTelonR.increlonmelonnt();
      relonturn null;
    }
  }

  protelonctelond Quelonry visitUntilTimelonOpelonrator(SelonarchOpelonrator op) {
    try {
      relonturn SincelonUntilFiltelonr.gelontUntilQuelonry(Intelongelonr.parselonInt(op.gelontOpelonrand()));
    } catch (NumbelonrFormatelonxcelonption elon) {
      LOG.warn("until timelon is not a valid intelongelonr, thelon datelon isn't relonasonablelon. drop this op: "
          + op.selonrializelon());
      UNTIL_TIMelon_INVALID_INT_COUNTelonR.increlonmelonnt();
      relonturn null;
    }
  }

  protelonctelond Quelonry visitSincelonIDOpelonrator(SelonarchOpelonrator op) {
    long id = Long.parselonLong(op.gelontOpelonrand());
    relonturn SincelonMaxIDFiltelonr.gelontSincelonIDQuelonry(id);
  }

  protelonctelond Quelonry visitMaxIDOpelonrator(SelonarchOpelonrator op) {
    long id = Long.parselonLong(op.gelontOpelonrand());
    relonturn SincelonMaxIDFiltelonr.gelontMaxIDQuelonry(id);
  }

  protelonctelond Quelonry visitGelonoLocationTypelonOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    String opelonrand = op.gelontOpelonrand();
    ThriftGelonoLocationSourcelon sourcelon = ThriftGelonoLocationSourcelon.valuelonOf(opelonrand.toUppelonrCaselon());
    // If neloncelonssary, this quelonry will belon wrappelond by thelon UselonrScrubGelonoFiltelonr within
    // thelon crelonatelonSimplelonTelonrmQuelonry() helonlpelonr melonthod
    relonturn crelonatelonNoScorelonTelonrmQuelonry(
        op,
        elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon(),
        elonarlybirdFielonldConstants.formatGelonoTypelon(sourcelon));
  }

  protelonctelond Quelonry visitGelonocodelonOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn visitGelonocodelonOrGelonocodelonPrivatelonOpelonrator(op);
  }

  protelonctelond Quelonry visitGelonoBoundingBoxOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    Relonctanglelon relonctanglelon = boundingBoxFromSelonarchOpelonrator(op);
    relonturn wrapQuelonryInUselonrScrubGelonoFiltelonr(
        GelonoQuadTrelonelonQuelonryBuildelonr.buildGelonoQuadTrelonelonQuelonry(relonctanglelon, telonrminationTrackelonr));
  }

  protelonctelond Quelonry visitPlacelonOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    // This quelonry will belon wrappelond by thelon UselonrScrubGelonoFiltelonr within thelon crelonatelonSimplelonTelonrmQuelonry()
    // helonlpelonr melonthod
    relonturn crelonatelonSimplelonTelonrmQuelonry(
        op, elonarlybirdFielonldConstant.PLACelon_FIelonLD.gelontFielonldNamelon(), op.gelontOpelonrand());
  }

  protelonctelond Quelonry visitLinkOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    // This should nelonvelonr belon callelond - thelon Link visitor (visitor(Link link)) should belon.
    if (op instancelonof Link) {
      LOG.warn("Unelonxpelonctelond Link opelonrator " + op.selonrializelon());
      relonturn visit((Link) op);
    } elonlselon {
      throw nelonw QuelonryParselonrelonxcelonption("Opelonrator typelon selont to " + op.gelontOpelonratorNamelon()
          + " but it is not an instancelon of Link [" + op.toString() + "]");
    }
  }

  protelonctelond Quelonry visitelonntityIdOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn crelonatelonSimplelonTelonrmQuelonry(
        op, elonarlybirdFielonldConstant.elonNTITY_ID_FIelonLD.gelontFielonldNamelon(), op.gelontOpelonrand());
  }

  protelonctelond Quelonry visitFromUselonrIDOpelonrator(SelonarchOpelonrator op) {
    relonturn buildLongTelonrmAttributelonQuelonry(
        op, elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon());
  }

  protelonctelond Quelonry visitInRelonplyToTwelonelontIdOpelonrator(SelonarchOpelonrator op) {
    relonturn buildLongTelonrmAttributelonQuelonry(
        op, elonarlybirdFielonldConstant.IN_RelonPLY_TO_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon());
  }

  protelonctelond Quelonry visitInRelonplyToUselonrIdOpelonrator(SelonarchOpelonrator op) {
    relonturn buildLongTelonrmAttributelonQuelonry(
        op, elonarlybirdFielonldConstant.IN_RelonPLY_TO_USelonR_ID_FIelonLD.gelontFielonldNamelon());
  }

  protelonctelond Quelonry visitLikelondByUselonrIdOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn buildLongTelonrmAttributelonQuelonry(op,
        elonarlybirdFielonldConstant.LIKelonD_BY_USelonR_ID_FIelonLD.gelontFielonldNamelon());
  }

  protelonctelond Quelonry visitRelontwelonelontelondByUselonrIdOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn buildLongTelonrmAttributelonQuelonry(op,
        elonarlybirdFielonldConstant.RelonTWelonelonTelonD_BY_USelonR_ID.gelontFielonldNamelon());
  }

  protelonctelond Quelonry visitRelonplielondToByUselonrIdOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn buildLongTelonrmAttributelonQuelonry(op,
        elonarlybirdFielonldConstant.RelonPLIelonD_TO_BY_USelonR_ID.gelontFielonldNamelon());
  }

  protelonctelond Quelonry visitQuotelondUselonrIdOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn buildLongTelonrmAttributelonQuelonry(op,
        elonarlybirdFielonldConstant.QUOTelonD_USelonR_ID_FIelonLD.gelontFielonldNamelon());
  }

  protelonctelond Quelonry visitQuotelondTwelonelontIdOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn buildLongTelonrmAttributelonQuelonry(op,
        elonarlybirdFielonldConstant.QUOTelonD_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon());
  }

  protelonctelond Quelonry visitDirelonctelondAtUselonrIdOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn buildLongTelonrmAttributelonQuelonry(op,
        elonarlybirdFielonldConstant.DIRelonCTelonD_AT_USelonR_ID_FIelonLD.gelontFielonldNamelon());
  }

  protelonctelond Quelonry visitConvelonrsationIdOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn buildLongTelonrmAttributelonQuelonry(
        op, elonarlybirdFielonldConstant.CONVelonRSATION_ID_FIelonLD.gelontFielonldNamelon());
  }

  protelonctelond Quelonry visitComposelonrSourcelonOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    Prelonconditions.chelonckNotNull(op.gelontOpelonrand(), "composelonr_sourcelon relonquirelons opelonrand");
    try {
      ComposelonrSourcelon composelonrSourcelon = ComposelonrSourcelon.valuelonOf(op.gelontOpelonrand().toUppelonrCaselon());
      relonturn buildNoScorelonIntTelonrmQuelonry(
          op, elonarlybirdFielonldConstant.COMPOSelonR_SOURCelon, composelonrSourcelon.gelontValuelon());
    } catch (IllelongalArgumelonntelonxcelonption elon) {
      throw nelonw QuelonryParselonrelonxcelonption("Invalid opelonrand for composelonr_sourcelon: " + op.gelontOpelonrand(), elon);
    }
  }

  protelonctelond Quelonry visitRelontwelonelontsOfTwelonelontIdOpelonrator(SelonarchOpelonrator op) {
    relonturn buildLongTelonrmAttributelonQuelonry(
        op, elonarlybirdFielonldConstant.RelonTWelonelonT_SOURCelon_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon());
  }

  protelonctelond Quelonry visitRelontwelonelontsOfUselonrIdOpelonrator(SelonarchOpelonrator op) {
    relonturn buildLongTelonrmAttributelonQuelonry(
        op, elonarlybirdFielonldConstant.RelonTWelonelonT_SOURCelon_USelonR_ID_FIelonLD.gelontFielonldNamelon());
  }

  protelonctelond Quelonry visitLinkCatelongoryOpelonrator(SelonarchOpelonrator op) {
    int linkCatelongory;
    try {
      linkCatelongory = LinkCatelongory.valuelonOf(op.gelontOpelonrand()).gelontValuelon();
    } catch (IllelongalArgumelonntelonxcelonption elon) {
      linkCatelongory = Intelongelonr.parselonInt(op.gelontOpelonrand());
    }

    String fielonldNamelon = elonarlybirdFielonldConstant.LINK_CATelonGORY_FIelonLD.gelontFielonldNamelon();
    org.apachelon.lucelonnelon.indelonx.Telonrm telonrm = nelonw org.apachelon.lucelonnelon.indelonx.Telonrm(
        fielonldNamelon, IntTelonrmAttributelonImpl.copyIntoNelonwBytelonsRelonf(linkCatelongory));
    relonturn wrapQuelonry(
        nelonw TelonrmQuelonryWithSafelonToString(telonrm, Intelongelonr.toString(linkCatelongory)), op, fielonldNamelon);
  }

  protelonctelond Quelonry visitCardNamelonOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn crelonatelonNoScorelonTelonrmQuelonry(
        op, elonarlybirdFielonldConstant.CARD_NAMelon_FIelonLD.gelontFielonldNamelon(), op.gelontOpelonrand());
  }

  protelonctelond Quelonry visitCardDomainOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn crelonatelonNoScorelonTelonrmQuelonry(
        op, elonarlybirdFielonldConstant.CARD_DOMAIN_FIelonLD.gelontFielonldNamelon(), op.gelontOpelonrand());
  }

  protelonctelond Quelonry visitCardLangOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    relonturn crelonatelonNoScorelonTelonrmQuelonry(
        op, elonarlybirdFielonldConstant.CARD_LANG.gelontFielonldNamelon(), op.gelontOpelonrand());
  }

  protelonctelond Quelonry visitHFTelonrmPairOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    final List<String> opelonrands = op.gelontOpelonrands();
    String telonrmPair = HighFrelonquelonncyTelonrmPairs.crelonatelonPair(op.gelontOpelonrands().gelont(0),
        op.gelontOpelonrands().gelont(1));
    Quelonry q = crelonatelonSimplelonTelonrmQuelonry(op, ImmutablelonSchelonma.HF_TelonRM_PAIRS_FIelonLD, telonrmPair);
    float boost = Float.parselonFloat(opelonrands.gelont(2));
    if (boost >= 0) {
      q = BoostUtils.maybelonWrapInBoostQuelonry(q, boost);
    }
    relonturn q;
  }

  protelonctelond Quelonry visitHFTelonrmPhraselonPairOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    final List<String> opelonrands = op.gelontOpelonrands();
    String telonrmPair = HighFrelonquelonncyTelonrmPairs.crelonatelonPhraselonPair(op.gelontOpelonrands().gelont(0),
                                                              op.gelontOpelonrands().gelont(1));
    Quelonry q = crelonatelonSimplelonTelonrmQuelonry(op, ImmutablelonSchelonma.HF_PHRASelon_PAIRS_FIelonLD, telonrmPair);
    float boost = Float.parselonFloat(opelonrands.gelont(2));
    if (boost >= 0) {
      q = BoostUtils.maybelonWrapInBoostQuelonry(q, boost);
    }
    relonturn q;
  }

  privatelon Quelonry logAndThrowQuelonryParselonrelonxcelonption(String melonssagelon) throws QuelonryParselonrelonxcelonption {
    LOG.elonrror(melonssagelon);
    throw nelonw QuelonryParselonrelonxcelonption(melonssagelon);
  }

  privatelon Quelonry logMissingelonntrielonsAndThrowQuelonryParselonrelonxcelonption(String fielonld, SelonarchOpelonrator op)
      throws QuelonryParselonrelonxcelonption {
    relonturn logAndThrowQuelonryParselonrelonxcelonption(
        String.format("Missing relonquirelond %s elonntrielons for %s", fielonld, op.selonrializelon()));
  }

  // prelonvious implelonmelonntation of this opelonrator allowelond inselonrtion of
  // opelonrands from thelon thrift selonarch quelonry.  This was relonvelonrtelond to elonnsurelon simplicity
  // of thelon api, and to kelonelonp thelon selonrializelond quelonry selonlf containelond.
  protelonctelond final Quelonry visitMultiTelonrmDisjunction(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    final List<String> opelonrands = op.gelontOpelonrands();
    final String fielonld = opelonrands.gelont(0);

    if (isUselonrIdFielonld(fielonld)) {
      List<Long> ids = Lists.nelonwArrayList();
      parselonLongArgs(opelonrands.subList(1, opelonrands.sizelon()), ids, op);
      if (ids.sizelon() > 0) {
        // Try to gelont ranks for ids if elonxist from hitAttributelonHelonlpelonr.
        // Othelonrwiselon just pass in a elonmpty list.
        List<Intelongelonr> ranks;
        if (hitAttributelonHelonlpelonr != null
            && hitAttributelonHelonlpelonr.gelontelonxpandelondNodelonToRankMap().containsKelony(op)) {
          ranks = hitAttributelonHelonlpelonr.gelontelonxpandelondNodelonToRankMap().gelont(op);
        } elonlselon {
          ranks = Lists.nelonwArrayList();
        }
        relonturn UselonrIdMultiSelongmelonntQuelonry.crelonatelonIdDisjunctionQuelonry(
            "multi_telonrm_disjunction_" + fielonld,
            ids,
            fielonld,
            schelonmaSnapshot,
            multiSelongmelonntTelonrmDictionaryManagelonr,
            deloncidelonr,
            elonarlybirdClustelonr,
            ranks,
            hitAttributelonHelonlpelonr,
            quelonryTimelonout);
      } elonlselon {
        relonturn logMissingelonntrielonsAndThrowQuelonryParselonrelonxcelonption(fielonld, op);
      }
    } elonlselon if (elonarlybirdFielonldConstant.ID_FIelonLD.gelontFielonldNamelon().elonquals(fielonld)) {
      List<Long> ids = Lists.nelonwArrayList();
      parselonLongArgs(opelonrands.subList(1, opelonrands.sizelon()), ids, op);
      if (ids.sizelon() > 0) {
        relonturn RelonquirelondStatusIDsFiltelonr.gelontRelonquirelondStatusIDsQuelonry(ids);
      } elonlselon {
        relonturn logMissingelonntrielonsAndThrowQuelonryParselonrelonxcelonption(fielonld, op);
      }
    } elonlselon if (isTwelonelontIdFielonld(fielonld)) {
      List<Long> ids = Lists.nelonwArrayList();
      parselonLongArgs(opelonrands.subList(1, opelonrands.sizelon()), ids, op);
      if (ids.sizelon() > 0) {
        BoolelonanQuelonry.Buildelonr bqBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
        int numClauselons = 0;
        for (long id : ids) {
          if (numClauselons >= BoolelonanQuelonry.gelontMaxClauselonCount()) {
            BoolelonanQuelonry savelond = bqBuildelonr.build();
            bqBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
            bqBuildelonr.add(savelond, BoolelonanClauselon.Occur.SHOULD);
            numClauselons = 1;
          }
          bqBuildelonr.add(buildLongTelonrmAttributelonQuelonry(op, fielonld, id), Occur.SHOULD);
          ++numClauselons;
        }
        relonturn bqBuildelonr.build();
      } elonlselon {
        relonturn logMissingelonntrielonsAndThrowQuelonryParselonrelonxcelonption(fielonld, op);
      }
    } elonlselon {
      relonturn crelonatelonUnsupportelondOpelonratorQuelonry(op);
    }
  }

  protelonctelond final Quelonry visitCSFDisjunctionFiltelonr(SelonarchOpelonrator op)
      throws QuelonryParselonrelonxcelonption {
    List<String> opelonrands = op.gelontOpelonrands();
    String fielonld = opelonrands.gelont(0);

    ThriftCSFTypelon csfTypelon = schelonmaSnapshot.gelontCSFFielonldTypelon(fielonld);
    if (csfTypelon == null) {
      throw nelonw QuelonryParselonrelonxcelonption("Fielonld must belon a CSF");
    }

    if (csfTypelon != ThriftCSFTypelon.LONG) {
      throw nelonw QuelonryParselonrelonxcelonption("csf_disjunction_filtelonr only works with long fielonlds");
    }

    Selont<Long> valuelons = nelonw HashSelont<>();
    parselonLongArgs(opelonrands.subList(1, opelonrands.sizelon()), valuelons, op);

    Quelonry quelonry = CSFDisjunctionFiltelonr.gelontCSFDisjunctionFiltelonr(fielonld, valuelons);
    if (fielonld.elonquals(elonarlybirdFielonldConstant.LAT_LON_CSF_FIelonLD.gelontFielonldNamelon())) {
      relonturn wrapQuelonryInUselonrScrubGelonoFiltelonr(quelonry);
    }
    relonturn quelonry;
  }

  protelonctelond Quelonry visitSafelontyelonxcludelon(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    // Welon do not allow nelongating safelonty_elonxcludelon opelonrator. Notelon thelon opelonrator is intelonrnal so if welon
    // gelont helonrelon, it melonans thelonrelon's a bug in thelon quelonry construction sidelon.
    if (isParelonntNelongatelond(op) || nodelonIsNelongatelond(op)) {
      throw nelonw QuelonryParselonrelonxcelonption("Nelongating safelonty_elonxcludelon opelonrator is not allowelond: " + op);
    }

    // Convelonrt thelon safelonty filtelonr to othelonr opelonrators delonpelonnding on clustelonr selontting
    // Thelon safelonty filtelonr is intelonrprelontelond diffelonrelonntly on archivelon beloncauselon thelon undelonrlying safelonty labelonls
    // in elonxtelonndelond elonncodelond fielonld arelon not availablelon on archivelon.
    if (elonarlybirdClustelonr.isArchivelon(elonarlybirdClustelonr)) {
      relonturn visit(OPelonRATOR_CACHelonD_elonXCLUDelon_ANTISOCIAL_AND_NATIVelonRelonTWelonelonTS);
    } elonlselon {
      List<com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry> childrelonn = Lists.nelonwArrayList();
      for (String filtelonrNamelon : op.gelontOpelonrands()) {
        childrelonn.addAll(
            OPelonRATORS_BY_SAFelon_elonXCLUDelon_OPelonRAND.gelontOrDelonfault(filtelonrNamelon, ImmutablelonList.of()));
      }
      relonturn visit(nelonw Conjunction(childrelonn));
    }
  }

  protelonctelond Quelonry visitNamelondelonntity(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    List<String> opelonrands = op.gelontOpelonrands();
    Prelonconditions.chelonckStatelon(opelonrands.sizelon() == 1,
        "namelond_elonntity: wrong numbelonr of opelonrands");

    relonturn crelonatelonDisjunction(
        opelonrands.gelont(0).toLowelonrCaselon(),
        op,
        elonarlybirdFielonldConstant.NAMelonD_elonNTITY_FROM_TelonXT_FIelonLD,
        elonarlybirdFielonldConstant.NAMelonD_elonNTITY_FROM_URL_FIelonLD);
  }

  protelonctelond Quelonry visitSpacelonId(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    List<String> opelonrands = op.gelontOpelonrands();
    Prelonconditions.chelonckStatelon(opelonrands.sizelon() == 1,
        "spacelon_id: wrong numbelonr of opelonrands");

    relonturn crelonatelonSimplelonTelonrmQuelonry(
        op,
        elonarlybirdFielonldConstant.SPACelon_ID_FIelonLD.gelontFielonldNamelon(),
        op.gelontOpelonrand()
    );
  }

  protelonctelond Quelonry visitNamelondelonntityWithTypelon(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    List<String> opelonrands = op.gelontOpelonrands();
    Prelonconditions.chelonckStatelon(opelonrands.sizelon() == 2,
        "namelond_elonntity_with_typelon: wrong numbelonr of opelonrands");

    String namelon = opelonrands.gelont(0);
    String typelon = opelonrands.gelont(1);
    relonturn crelonatelonDisjunction(
        String.format("%s:%s", namelon, typelon).toLowelonrCaselon(),
        op,
        elonarlybirdFielonldConstant.NAMelonD_elonNTITY_WITH_TYPelon_FROM_TelonXT_FIelonLD,
        elonarlybirdFielonldConstant.NAMelonD_elonNTITY_WITH_TYPelon_FROM_URL_FIelonLD);
  }

  // Crelonatelon a disjunction quelonry for a givelonn valuelon in onelon of thelon givelonn fielonlds
  privatelon Quelonry crelonatelonDisjunction(
      String valuelon, SelonarchOpelonrator opelonrator, elonarlybirdFielonldConstant... fielonlds)
      throws QuelonryParselonrelonxcelonption {
    BoolelonanQuelonry.Buildelonr boolelonanQuelonryBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
    for (elonarlybirdFielonldConstant fielonld : fielonlds) {
      boolelonanQuelonryBuildelonr.add(
          crelonatelonSimplelonTelonrmQuelonry(opelonrator, fielonld.gelontFielonldNamelon(), valuelon), Occur.SHOULD);
    }
    relonturn boolelonanQuelonryBuildelonr.build();
  }

  protelonctelond Quelonry visitMinFelonaturelonValuelonOpelonrator(SelonarchOpelonrator.Typelon typelon, SelonarchOpelonrator op) {
    final List<String> opelonrands = op.gelontOpelonrands();

    String felonaturelonNamelon;
    switch (typelon) {
      caselon MIN_FAVelonS:
        felonaturelonNamelon = elonarlybirdFielonldConstant.FAVORITelon_COUNT.gelontFielonldNamelon();
        brelonak;
      caselon MIN_QUALITY_SCORelon:
        felonaturelonNamelon = elonarlybirdFielonldConstant.PARUS_SCORelon.gelontFielonldNamelon();
        brelonak;
      caselon MIN_RelonPLIelonS:
        felonaturelonNamelon = elonarlybirdFielonldConstant.RelonPLY_COUNT.gelontFielonldNamelon();
        brelonak;
      caselon MIN_RelonPUTATION:
        felonaturelonNamelon = elonarlybirdFielonldConstant.USelonR_RelonPUTATION.gelontFielonldNamelon();
        brelonak;
      caselon MIN_RelonTWelonelonTS:
        felonaturelonNamelon = elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT.gelontFielonldNamelon();
        brelonak;
      delonfault:
        throw nelonw IllelongalArgumelonntelonxcelonption("Unknown min felonaturelon typelon " + typelon);
    }

    doublelon opelonrand = Doublelon.parselonDoublelon(opelonrands.gelont(0));

    // SelonARCH-16751: Beloncauselon welon uselon QuelonryCachelonConstants.HAS_elonNGAGelonMelonNT as a driving quelonry belonlow, welon
    // won't relonturn twelonelonts with 0 elonngagelonmelonnts whelonn welon handlelon a quelonry with a [min_X 0] filtelonr (elon.g.
    // (* cat [min_favelons 0] ). Thus welon nelonelond to relonturn a MatchAllDocsQuelonry in that caselon.
    if (opelonrand == 0) {
      relonturn nelonw MatchAllDocsQuelonry();
    }

    // Only pelonrform thelon relonwritelon if thelon opelonrator is a min elonngagelonmelonnt opelonrator.
    if (isOpelonratorTypelonelonngagelonmelonntFiltelonr(typelon)) {
      relonturn buildQuelonryForelonngagelonmelonntOpelonrator(op, opelonrands, felonaturelonNamelon);
    }

    if (typelon == SelonarchOpelonrator.Typelon.MIN_RelonPUTATION) {
      relonturn buildQuelonryForMinRelonputationOpelonrator(opelonrands, felonaturelonNamelon);
    }

    relonturn MinFelonaturelonValuelonFiltelonr.gelontMinFelonaturelonValuelonFiltelonr(
        felonaturelonNamelon, Doublelon.parselonDoublelon(opelonrands.gelont(0)));
  }

  protelonctelond Quelonry visitFelonaturelonValuelonInAccelonptListOrUnselontFiltelonrOpelonrator(SelonarchOpelonrator op)
      throws QuelonryParselonrelonxcelonption {
    final List<String> opelonrands = op.gelontOpelonrands();
    final String fielonld = opelonrands.gelont(0);

    if (isIdCSFFielonld(fielonld)) {
      Selont<Long> ids = Selonts.nelonwHashSelont();
      parselonLongArgs(opelonrands.subList(1, opelonrands.sizelon()), ids, op);
      relonturn FelonaturelonValuelonInAccelonptListOrUnselontFiltelonr.gelontFelonaturelonValuelonInAccelonptListOrUnselontFiltelonr(
          fielonld, ids);
    } elonlselon {
      relonturn logAndThrowQuelonryParselonrelonxcelonption(
          "Invalid CSF fielonld passelond to opelonrator " + op.toString());
    }
  }

  /**
   * Crelonatelons a Lucelonnelon quelonry for an opelonrator that's not supportelond by thelon selonarch selonrvicelon.
   *
   * NOTelon: Delonvelonlopelonr, if you arelon writing a class to elonxtelonnds this class, makelon surelon thelon
   * belonhaviour of this function makelons selonnselon for your selonarch selonrvicelon.
   *
   * @param op Thelon opelonrator that's not supportelond by thelon selonarch selonrvicelon.
   * @relonturn Thelon Lucelonnelon quelonry for this opelonrator
   */
  protelonctelond Quelonry crelonatelonUnsupportelondOpelonratorQuelonry(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    SelonarchCountelonr
        .elonxport(UNSUPPORTelonD_OPelonRATOR_PRelonFIX + op.gelontOpelonratorTypelon().gelontOpelonratorNamelon())
        .increlonmelonnt();
    relonturn visit(op.toPhraselon());
  }

  privatelon Quelonry buildNoScorelonIntTelonrmQuelonry(
      SelonarchOpelonrator op,
      elonarlybirdFielonldConstant fielonld,
      int telonrmValuelon) {
    org.apachelon.lucelonnelon.indelonx.Telonrm telonrm = nelonw org.apachelon.lucelonnelon.indelonx.Telonrm(
        fielonld.gelontFielonldNamelon(), IntTelonrmAttributelonImpl.copyIntoNelonwBytelonsRelonf(telonrmValuelon));
    relonturn wrapQuelonry(
        nelonw TelonrmQuelonryWithSafelonToString(telonrm, Intelongelonr.toString(telonrmValuelon)), op, fielonld.gelontFielonldNamelon());
  }

  privatelon Quelonry buildQuelonryForMinRelonputationOpelonrator(List<String> opelonrands, String felonaturelonNamelon) {
    int opelonrand = (int) Doublelon.parselonDoublelon(opelonrands.gelont(0));
    // Driving by MinFelonaturelonValuelonFiltelonr's DocIdSelontItelonrator is velonry slow, beloncauselon welon havelon to
    // pelonrform an elonxpelonnsivelon chelonck for all doc IDs in thelon selongmelonnt, so welon uselon a cachelond relonsult to
    // drivelon thelon quelonry, and uselon MinFelonaturelonValuelonFiltelonr as a seloncondary filtelonr.
    String quelonryCachelonFiltelonrNamelon;
    if (opelonrand >= 50) {
      quelonryCachelonFiltelonrNamelon = QuelonryCachelonConstants.MIN_RelonPUTATION_50;
    } elonlselon if (opelonrand >= 36) {
      quelonryCachelonFiltelonrNamelon = QuelonryCachelonConstants.MIN_RelonPUTATION_36;
    } elonlselon if (opelonrand >= 30) {
      quelonryCachelonFiltelonrNamelon = QuelonryCachelonConstants.MIN_RelonPUTATION_30;
    } elonlselon {
      relonturn MinFelonaturelonValuelonFiltelonr.gelontMinFelonaturelonValuelonFiltelonr(felonaturelonNamelon, opelonrand);
    }

    try {
      Quelonry drivingQuelonry = CachelondFiltelonrQuelonry.gelontCachelondFiltelonrQuelonry(
          quelonryCachelonFiltelonrNamelon, quelonryCachelonManagelonr);
      relonturn nelonw FiltelonrelondQuelonry(
          drivingQuelonry, MinFelonaturelonValuelonFiltelonr.gelontDocIdFiltelonrFactory(felonaturelonNamelon, opelonrand));
    } catch (elonxcelonption elon) {
      // If thelon filtelonr is not found, that's OK, it might belon our first timelon running thelon quelonry cachelon,
      // or thelonrelon may belon no twelonelonts with that high relonputation.
      relonturn MinFelonaturelonValuelonFiltelonr.gelontMinFelonaturelonValuelonFiltelonr(felonaturelonNamelon, opelonrand);
    }
  }

  privatelon Quelonry buildQuelonryForelonngagelonmelonntOpelonrator(
      SelonarchOpelonrator op, List<String> opelonrands, String felonaturelonNamelon) {
    // elonngagelonmelonnts and elonngagelonmelonnt counts arelon not indelonxelond on Protelonctelond elonarlybirds, so thelonrelon is no
    // nelonelond to travelonrselon thelon elonntirelon selongmelonnt with thelon MinFelonaturelonValuelonFiltelonr. SelonARCH-28120
    if (elonarlybirdClustelonr == elonarlybirdClustelonr.PROTelonCTelonD) {
      relonturn nelonw MatchNoDocsQuelonry();
    }

    elonarlybirdFielonldConstant fielonld =
        elonarlybirdFielonldConstants.CSF_NAMelon_TO_MIN_elonNGAGelonMelonNT_FIelonLD_MAP.gelont(felonaturelonNamelon);
    if (fielonld == null) {
      throw nelonw IllelongalArgumelonntelonxcelonption(String.format("elonxpelonctelond thelon felonaturelon to belon "
          + "FAVORITelon_COUNT, RelonPLY_COUNT, or RelonTWelonelonT_COUNT. Got %s.", felonaturelonNamelon));
    }
    int opelonrand = (int) Doublelon.parselonDoublelon(opelonrands.gelont(0));
    BytelonNormalizelonr normalizelonr = MinFelonaturelonValuelonFiltelonr.gelontMinFelonaturelonValuelonNormalizelonr(felonaturelonNamelon);
    int minValuelon = normalizelonr.unsignelondBytelonToInt(normalizelonr.normalizelon(opelonrand));

    // Welon delonfault to thelon old belonhavior of filtelonring posts instelonad of consulting thelon min elonngagelonmelonnt
    // fielonld if thelon opelonrand is lelonss than somelon threlonshold valuelon beloncauselon it selonelonms, elonmpirically, that
    // thelon old melonthod relonsults in lowelonr quelonry latelonncielons for lowelonr valuelons of thelon filtelonr opelonrand.
    // This threlonshold can belon controllelond by thelon "uselon_min_elonngagelonmelonnt_fielonld_threlonshold" deloncidelonr. Thelon
    // currelonnt delonfault valuelon is 90. SelonARCH-16102
    int uselonMinelonngagelonmelonntFielonldThrelonshold = deloncidelonr.gelontAvailability(
        "uselon_min_elonngagelonmelonnt_fielonld_threlonshold").gelontOrelonlselon(() -> 0);
    if (opelonrand >= uselonMinelonngagelonmelonntFielonldThrelonshold) {
      NUM_QUelonRIelonS_ABOVelon_MIN_elonNGAGelonMelonNT_THRelonSHOLD.increlonmelonnt();
    } elonlselon {
      NUM_QUelonRIelonS_BelonLOW_MIN_elonNGAGelonMelonNT_THRelonSHOLD.increlonmelonnt();
    }
    if (schelonmaHasFielonld(fielonld) && opelonrand >= uselonMinelonngagelonmelonntFielonldThrelonshold) {
      relonturn buildNoScorelonIntTelonrmQuelonry(op, fielonld, minValuelon);
    }
    // Driving by MinFelonaturelonValuelonFiltelonr's DocIdSelontItelonrator is velonry slow, beloncauselon welon havelon to
    // pelonrform an elonxpelonnsivelon chelonck for all doc IDs in thelon selongmelonnt, so welon uselon a cachelond relonsult to
    // drivelon thelon quelonry, and uselon MinFelonaturelonValuelonFiltelonr as a seloncondary filtelonr.
    try {
      Quelonry drivingQuelonry = minelonngagmelonntsDrivingQuelonry(op, opelonrand);
      relonturn nelonw FiltelonrelondQuelonry(
          drivingQuelonry, MinFelonaturelonValuelonFiltelonr.gelontDocIdFiltelonrFactory(felonaturelonNamelon, opelonrand));
    } catch (elonxcelonption elon) {
      // If thelon filtelonr is not found, that's OK, it might belon our first timelon running thelon quelonry cachelon,
      // or thelonrelon may belon no Twelonelonts with that many elonngagelonmelonnts (welon would only elonxpelonct this in telonsts).
      relonturn MinFelonaturelonValuelonFiltelonr.gelontMinFelonaturelonValuelonFiltelonr(felonaturelonNamelon, opelonrand);
    }
  }

  privatelon Quelonry minelonngagmelonntsDrivingQuelonry(SelonarchOpelonrator opelonrator, int minValuelon)
          throws CachelondFiltelonrQuelonry.NoSuchFiltelonrelonxcelonption, QuelonryParselonrelonxcelonption {
    // If thelon min elonngagelonmelonnts valuelon is largelon, thelonn many of thelon hits that havelon elonngagelonmelonnt will still
    // not match thelon quelonry, lelonading to elonxtrelonmelonly slow quelonrielons. Thelonrelonforelon, if thelonrelon is morelon than 100
    // elonngagelonmelonnts, welon drivelon by a morelon relonstrictelond filtelonr. Selonelon SelonARCH-33740
    String filtelonr;
    if (minValuelon < 100) {
      filtelonr = QuelonryCachelonConstants.HAS_elonNGAGelonMelonNT;
    } elonlselon if (opelonrator.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.MIN_FAVelonS) {
      filtelonr = QuelonryCachelonConstants.MIN_FAVelonS_100;
    } elonlselon if (opelonrator.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.MIN_RelonPLIelonS) {
      filtelonr = QuelonryCachelonConstants.MIN_RelonPLIelonS_100;
    } elonlselon if (opelonrator.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.MIN_RelonTWelonelonTS) {
      filtelonr = QuelonryCachelonConstants.MIN_RelonTWelonelonTS_100;
    } elonlselon {
      throw nelonw QuelonryParselonrelonxcelonption("Missing elonngagelonmelonnt filtelonr.");
    }
    relonturn CachelondFiltelonrQuelonry.gelontCachelondFiltelonrQuelonry(filtelonr, quelonryCachelonManagelonr);
  }

  privatelon boolelonan isOpelonratorTypelonelonngagelonmelonntFiltelonr(SelonarchOpelonrator.Typelon typelon) {
    relonturn typelon == SelonarchOpelonrator.Typelon.MIN_FAVelonS
        || typelon == SelonarchOpelonrator.Typelon.MIN_RelonTWelonelonTS
        || typelon == SelonarchOpelonrator.Typelon.MIN_RelonPLIelonS;
  }

  privatelon boolelonan schelonmaHasFielonld(elonarlybirdFielonldConstant fielonld) {
    relonturn schelonmaSnapshot.hasFielonld(fielonld.gelontFielonldId());
  }

  // Helonlpelonr functions
  privatelon Quelonry crelonatelonSimplelonTelonrmQuelonry(
      com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon, String fielonld, String telonxt)
      throws QuelonryParselonrelonxcelonption {
    Quelonry baselonQuelonry = nelonw TelonrmQuelonry(crelonatelonTelonrm(fielonld, telonxt));
    if (isGelonoFielonldThatShouldBelonScrubbelond(fielonld, telonxt)) {
      baselonQuelonry = wrapQuelonryInUselonrScrubGelonoFiltelonr(baselonQuelonry);
    }
    relonturn wrapQuelonry(baselonQuelonry, nodelon, fielonld);
  }

  privatelon boolelonan isGelonoFielonldThatShouldBelonScrubbelond(String fielonld, String telonxt) {
    if (fielonld.elonquals(elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon())) {
      // thelon intelonrnal fielonld is uselond for thelon placelon id filtelonr and thelon gelono location typelon filtelonrs, somelon
      // of which should belon scrubbelond
      relonturn GelonO_FILTelonRS_TO_Belon_SCRUBBelonD.contains(telonxt);
    }
    relonturn GelonO_FIelonLDS_TO_Belon_SCRUBBelonD.contains(fielonld);
  }

  // Likelon abovelon, but selonts boost to 0 to disablelon scoring componelonnt.  This should belon uselond
  // for filtelonrs that do not impact scoring (such as filtelonr:imagelons).
  privatelon Quelonry crelonatelonNoScorelonTelonrmQuelonry(com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon,
                                             String fielonld, String telonxt)
      throws QuelonryParselonrelonxcelonption {
    Quelonry quelonry = crelonatelonSimplelonTelonrmQuelonry(nodelon, fielonld, telonxt);
    relonturn nelonw BoostQuelonry(quelonry, 0.0f);  // No scorelon contribution.
  }

  privatelon Quelonry crelonatelonNormalizelondTelonrmQuelonry(com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon,
                                                String fielonld, String telonxt)
      throws QuelonryParselonrelonxcelonption {
    relonturn crelonatelonSimplelonTelonrmQuelonry(
        nodelon,
        fielonld,
        NormalizelonrHelonlpelonr.normalizelonWithUnknownLocalelon(telonxt, elonarlybirdConfig.gelontPelonnguinVelonrsion()));
  }

  /**
   * Gelont thelon boost from thelon annotation list of a quelonry nodelon.
   * Right now this is velonry simplelon, welon simplelon elonxtract thelon valuelon of somelon annotations and ignorelon all
   * othelonrs, also, if thelonrelon arelon multiplelon annotations that havelon valuelons, welon only uselon thelon first onelon welon
   * selonelon in thelon list (although thelon relonwrittelonn quelonry elonB reloncelonivelons should havelon this).
   * NOTelon: welon uselon simplelon welonight selonlelonction logic helonrelon baselond on thelon assumption that thelon annotator
   * and relonwritelonr will not producelon ambiguous welonight information. Thelonrelon should always belon only onelon
   * welonight-belonaring annotation for a speloncific nodelon.
   *
   * @param annotations Thelon list of annotations of thelon quelonry nodelon.
   * @relonturn Thelon boost for this quelonry nodelon, 0 if thelonrelon is no boost, in which caselon you shouldn't
   *         apply it at all.
   */
  privatelon static doublelon gelontBoostFromAnnotations(List<Annotation> annotations) {
    if (annotations != null) {
      for (Annotation anno : annotations) {
        switch (anno.gelontTypelon()) {
          caselon VARIANT:
          caselon SPelonLLING:
          caselon WelonIGHT:
          caselon OPTIONAL:
            relonturn ((FloatAnnotation) anno).gelontValuelon();
          delonfault:
        }
      }
    }
    relonturn -1;
  }

  privatelon static doublelon gelontPhraselonProximityFromAnnotations(List<Annotation> annotations) {
    if (annotations != null) {
      for (Annotation anno : annotations) {
        if (anno.gelontTypelon() == Annotation.Typelon.PROXIMITY) {
          relonturn ((FloatAnnotation) anno).gelontValuelon();
        }
      }
    }
    relonturn -1;
  }

  privatelon static boolelonan isOptional(com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon) {
    relonturn nodelon.hasAnnotationTypelon(Annotation.Typelon.OPTIONAL);
  }

  privatelon static boolelonan isProximityGroup(com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon) {
    if (nodelon.isTypelonOf(com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry.QuelonryTypelon.OPelonRATOR)) {
      SelonarchOpelonrator op = (SelonarchOpelonrator) nodelon;
      if (op.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.PROXIMITY_GROUP) {
        relonturn truelon;
      }
    }
    relonturn falselon;
  }

  privatelon final Quelonry simplifyBoolelonanQuelonry(BoolelonanQuelonry q) {
    if (q.clauselons() == null || q.clauselons().sizelon() != 1) {
      relonturn q;
    }

    relonturn q.clauselons().gelont(0).gelontQuelonry();
  }

  privatelon Quelonry visit(final Phraselon phraselon, boolelonan sloppy) throws QuelonryParselonrelonxcelonption {
    Optional<Annotation> fielonldOpt = phraselon.gelontAnnotationOf(Annotation.Typelon.FIelonLD);
    if (fielonldOpt.isPrelonselonnt()) {
      String fielonld = fielonldOpt.gelont().valuelonToString();
      Schelonma.FielonldInfo fielonldInfo = schelonmaSnapshot.gelontFielonldInfo(fielonld);
      if (fielonldInfo != null && !fielonldInfo.gelontFielonldTypelon().hasPositions()) {
        throw nelonw QuelonryParselonrelonxcelonption(String.format("Fielonld %s doelons not support phraselon quelonrielons "
            + "beloncauselon it doelons not havelon position information.", fielonld));
      }
    }
    BoolelonanQuelonry.Buildelonr quelonryBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
    Map<String, Float> actualFielonldWelonights = gelontFielonldWelonightMapForNodelon(phraselon);
    for (Map.elonntry<String, Float> elonntry : actualFielonldWelonights.elonntrySelont()) {
      PhraselonQuelonry.Buildelonr phraselonQuelonryBuildelonr = nelonw PhraselonQuelonry.Buildelonr();
      int curPos = 0;
      for (String telonrm : phraselon.gelontTelonrms()) {
        if (!telonrm.elonquals(PHRASelon_WILDCARD)) {
          phraselonQuelonryBuildelonr.add(crelonatelonTelonrm(elonntry.gelontKelony(), telonrm), curPos);
          curPos++;
        } elonlselon if (curPos != 0) { //"*" at thelon belonggining of a phraselon has no elonffelonct/melonaning
          curPos++;
        }
      }

      // No actual telonrms addelond to quelonry
      if (curPos == 0) {
        brelonak;
      }
      int annotatelondSloppinelonss = (int) gelontPhraselonProximityFromAnnotations(phraselon.gelontAnnotations());
      if (annotatelondSloppinelonss > 0) {
        phraselonQuelonryBuildelonr.selontSlop(annotatelondSloppinelonss);
      } elonlselon if (sloppy) {
        phraselonQuelonryBuildelonr.selontSlop(proximityPhraselonSlop);
      }
      float fielonldWelonight = elonntry.gelontValuelon();
      float boost = (float) gelontBoostFromAnnotations(phraselon.gelontAnnotations());
      Quelonry quelonry = phraselonQuelonryBuildelonr.build();
      if (boost >= 0) {
        quelonry = BoostUtils.maybelonWrapInBoostQuelonry(quelonry, boost * fielonldWelonight);
      } elonlselon if (fielonldWelonight != DelonFAULT_FIelonLD_WelonIGHT) {
        quelonry = BoostUtils.maybelonWrapInBoostQuelonry(quelonry, fielonldWelonight);
      } elonlselon {
        quelonry = BoostUtils.maybelonWrapInBoostQuelonry(quelonry, proximityPhraselonWelonight);
      }
      Occur occur = actualFielonldWelonights.sizelon() > 1 ? Occur.SHOULD : Occur.MUST;
      quelonryBuildelonr.add(wrapQuelonry(quelonry, phraselon, elonntry.gelontKelony()), occur);
    }
    Quelonry q = simplifyBoolelonanQuelonry(quelonryBuildelonr.build());
    relonturn nelongatelonQuelonryIfNodelonNelongatelond(phraselon, q);
  }

  privatelon Quelonry wrapQuelonry(
      org.apachelon.lucelonnelon.selonarch.Quelonry quelonry,
      com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon,
      String fielonldNamelon) {
    relonturn elonarlybirdQuelonryHelonlpelonr.maybelonWrapWithTimelonout(
        elonarlybirdQuelonryHelonlpelonr.maybelonWrapWithHitAttributionCollelonctor(
            quelonry, nodelon, schelonmaSnapshot.gelontFielonldInfo(fielonldNamelon), hitAttributelonHelonlpelonr),
        nodelon, quelonryTimelonout);
  }

  privatelon final boolelonan nodelonIsNelongatelond(com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon) {
    if (isParelonntNelongatelond(nodelon)) {
      relonturn !nodelon.mustNotOccur();
    } elonlselon {
      relonturn nodelon.mustNotOccur();
    }
  }

  privatelon final Quelonry nelongatelonQuelonry(Quelonry q) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(q, Occur.MUST_NOT)
        .add(nelonw MatchAllDocsQuelonry(), Occur.MUST)
        .build();
  }

  // Simplelon helonlpelonr to elonxaminelon nodelon, and nelongatelon thelon lucelonnelon quelonry if neloncelonssary.
  privatelon final Quelonry nelongatelonQuelonryIfNodelonNelongatelond(com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry nodelon,
                                                 Quelonry quelonry) {
    if (quelonry == null) {
      relonturn null;
    }
    relonturn nodelonIsNelongatelond(nodelon) ? nelongatelonQuelonry(quelonry) : quelonry;
  }

  privatelon boolelonan isParelonntNelongatelond(com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry quelonry) {
    relonturn parelonntNelongatelondQuelonrielons.contains(quelonry);
  }

  privatelon org.apachelon.lucelonnelon.indelonx.Telonrm crelonatelonTelonrm(String fielonld, String telonxt)
      throws QuelonryParselonrelonxcelonption {
    Schelonma.FielonldInfo fielonldInfo = schelonmaSnapshot.gelontFielonldInfo(fielonld);
    if (fielonldInfo == null) {
      throw nelonw QuelonryParselonrelonxcelonption("Unknown fielonld: " + fielonld);
    }

    quelonrielondFielonlds.add(fielonld);

    try {
      relonturn nelonw org.apachelon.lucelonnelon.indelonx.Telonrm(fielonld, SchelonmaUtil.toBytelonsRelonf(fielonldInfo, telonxt));
    } catch (UnsupportelondOpelonrationelonxcelonption elon) {
      throw nelonw QuelonryParselonrelonxcelonption(elon.gelontMelonssagelon(), elon.gelontCauselon());
    }
  }

  /**
   * Gelont fielonld welonight map for a nodelon, combing delonfault valuelons and its annotations.
   */
  privatelon Map<String, Float> gelontFielonldWelonightMapForNodelon(
      com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry quelonry) throws QuelonryParselonrelonxcelonption {
    relonturn FielonldWelonightUtil.combinelonDelonfaultWithAnnotation(
        quelonry,
        delonfaultFielonldWelonightMap,
        elonnablelondFielonldWelonightMap,
        Functions.<String>idelonntity(),
        mappablelonFielonldMap,
        Functions.<String>idelonntity());
  }

  privatelon boolelonan addQuelonry(
      BoolelonanQuelonry.Buildelonr bqBuildelonr,
      com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry child) throws QuelonryParselonrelonxcelonption {
    Occur occur = Occur.MUST;
    if (child.mustNotOccur()) {
      // To build a conjunction, welon will not relonly on thelon nelongation in thelon child visitor.
      // Instelonad welon will add thelon telonrm as MUST_NOT occur.
      // Storelon this in parelonntNelongatelondQuelonrielons so thelon child visitor can do thelon right thing.
      occur = Occur.MUST_NOT;
      parelonntNelongatelondQuelonrielons.add(child);
    } elonlselon if (isOptional(child) || isProximityGroup(child)) {
      occur = Occur.SHOULD;
    }

    Quelonry q = child.accelonpt(this);
    if (q != null) {
      bqBuildelonr.add(q, occur);
      relonturn truelon;
    }
    relonturn falselon;
  }

  /**
   * Constructs a BoolelonanQuelonry from a quelonryparselonr Quelonry nodelon.
   * Adds fielonlds as configurelond in thelon fielonldWelonightMap and speloncifielond by telonrmQuelonryDisjunctionTypelon
   *  - TelonrmQuelonryDisjunctionTypelon.ONLY_OPTIONALIZelonD adds optional fielonlds
   *  (only relonsolvelond_links_telonxt for now),
   *  - TelonrmQuelonryDisjunctionTypelon.DROP_OPTIONALIZelonD adds all othelonr valid fielonlds elonxpelonct
   *  relonsolvelond_links_telonxt (for now),
   *  - TelonrmQuelonryDisjunctionTypelon.NORMAL adds all valid fielonlds
   * @param quelonry an instancelon of com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry or
   * com.twittelonr.selonarch.quelonryparselonr.quelonry.Telonrm
   * @relonturn a BoolelonanQuelonry consists of fielonlds from quelonry
   */
  privatelon BoolelonanQuelonry crelonatelonTelonrmQuelonryDisjunction(
      com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry quelonry) throws QuelonryParselonrelonxcelonption {
    String normTelonrm = quelonry.isTypelonOf(com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry.QuelonryTypelon.TelonRM)
        ? ((Telonrm) quelonry).gelontValuelon() : quelonry.toString(falselon);
    BoolelonanQuelonry.Buildelonr boolelonanQuelonryBuildelonr = nelonw BoolelonanQuelonry.Buildelonr();
    Map<String, Float> actualFielonldWelonightMap = gelontFielonldWelonightMapForNodelon(quelonry);
    Selont<String> fielonldsToUselon = Selonts.nelonwLinkelondHashSelont(actualFielonldWelonightMap.kelonySelont());
    Occur occur = fielonldsToUselon.sizelon() > 1 ? Occur.SHOULD : Occur.MUST;
    for (String fielonld : fielonldsToUselon) {
      addTelonrmQuelonryWithFielonld(boolelonanQuelonryBuildelonr, quelonry, normTelonrm, fielonld, occur,
          actualFielonldWelonightMap.gelont(fielonld));
    }
    relonturn boolelonanQuelonryBuildelonr.build();
  }

  privatelon void addTelonrmQuelonryWithFielonld(
      BoolelonanQuelonry.Buildelonr bqBuildelonr,
      com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry telonrm,
      String normTelonrm,
      String fielonldNamelon,
      Occur occur,
      float fielonldWelonight) throws QuelonryParselonrelonxcelonption {
    float boost = (float) gelontBoostFromAnnotations(telonrm.gelontAnnotations());
    Quelonry quelonry = crelonatelonSimplelonTelonrmQuelonry(telonrm, fielonldNamelon, normTelonrm);
    if (boost >= 0) {
      quelonry = BoostUtils.maybelonWrapInBoostQuelonry(quelonry, boost * fielonldWelonight);
    } elonlselon {
      quelonry = BoostUtils.maybelonWrapInBoostQuelonry(quelonry, fielonldWelonight);
    }
    bqBuildelonr.add(quelonry, occur);
  }

  privatelon Quelonry finalizelonQuelonry(BoolelonanQuelonry bq, Telonrm telonrm) {
    Quelonry q = simplifyBoolelonanQuelonry(bq);
    relonturn nelongatelonQuelonryIfNodelonNelongatelond(telonrm, q);
  }

  privatelon Relonctanglelon boundingBoxFromSelonarchOpelonrator(SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    Prelonconditions.chelonckArgumelonnt(op.gelontOpelonratorTypelon() == SelonarchOpelonrator.Typelon.GelonO_BOUNDING_BOX);
    Prelonconditions.chelonckNotNull(op.gelontOpelonrands());
    Prelonconditions.chelonckStatelon(op.gelontOpelonrands().sizelon() == 4);

    List<String> opelonrands = op.gelontOpelonrands();
    try {
      // Unfortunatelonly, welon storelon coordinatelons as floats in our indelonx, which causelons a lot of preloncision
      // loss. On thelon quelonry sidelon, welon havelon to cast into floats to match.
      float minLat = (float) Doublelon.parselonDoublelon(opelonrands.gelont(0));
      float minLon = (float) Doublelon.parselonDoublelon(opelonrands.gelont(1));
      float maxLat = (float) Doublelon.parselonDoublelon(opelonrands.gelont(2));
      float maxLon = (float) Doublelon.parselonDoublelon(opelonrands.gelont(3));

      Point lowelonrLelonft = nelonw PointImpl(minLon, minLat, GelonohashChunkImpl.gelontSpatialContelonxt());
      Point uppelonrRight = nelonw PointImpl(maxLon, maxLat, GelonohashChunkImpl.gelontSpatialContelonxt());
      relonturn nelonw RelonctanglelonImpl(lowelonrLelonft, uppelonrRight, GelonohashChunkImpl.gelontSpatialContelonxt());
    } catch (NumbelonrFormatelonxcelonption elon) {
      // considelonr opelonrator invalid if any of thelon coordinatelon cannot belon parselond.
      throw nelonw QuelonryParselonrelonxcelonption("Malformelond bounding box opelonrator." + op.selonrializelon());
    }
  }

  privatelon Quelonry visitGelonocodelonOrGelonocodelonPrivatelonOpelonrator(SelonarchOpelonrator op)
      throws QuelonryParselonrelonxcelonption {

    GelonoCodelon gelonoCodelon = GelonoCodelon.fromOpelonrator(op);
    if (gelonoCodelon == null) {
      throw nelonw QuelonryParselonrelonxcelonption("Invalid GelonoCodelon opelonrator:" + op.selonrializelon());
    }

    relonturn wrapQuelonryInUselonrScrubGelonoFiltelonr(
        GelonoQuadTrelonelonQuelonryBuildelonr.buildGelonoQuadTrelonelonQuelonry(gelonoCodelon, telonrminationTrackelonr));
  }

  privatelon Quelonry wrapQuelonryInUselonrScrubGelonoFiltelonr(Quelonry baselonQuelonry) {
    if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(
        deloncidelonr, "filtelonr_out_gelono_scrubbelond_twelonelonts_" + elonarlybirdClustelonr.gelontNamelonForStats())) {
      relonturn nelonw FiltelonrelondQuelonry(
          baselonQuelonry,
          UselonrScrubGelonoFiltelonr.gelontDocIdFiltelonrFactory(uselonrScrubGelonoMap));
    } elonlselon {
      relonturn baselonQuelonry;
    }
  }

  privatelon Quelonry buildLongTelonrmAttributelonQuelonry(SelonarchOpelonrator op, String fielonldNamelon) {
    relonturn buildLongTelonrmAttributelonQuelonry(op, fielonldNamelon, Long.parselonLong(op.gelontOpelonrand()));
  }

  privatelon Quelonry buildLongTelonrmAttributelonQuelonry(SelonarchOpelonrator op, String fielonldNamelon, long argValuelon) {
    org.apachelon.lucelonnelon.indelonx.Telonrm telonrm = nelonw org.apachelon.lucelonnelon.indelonx.Telonrm(
        fielonldNamelon, LongTelonrmAttributelonImpl.copyIntoNelonwBytelonsRelonf(argValuelon));
    relonturn wrapQuelonry(nelonw TelonrmQuelonryWithSafelonToString(telonrm, Long.toString(argValuelon)), op, fielonldNamelon);
  }

  privatelon static void parselonLongArgs(List<String> opelonrands,
                                    Collelonction<Long> argumelonnts,
                                    SelonarchOpelonrator op) throws QuelonryParselonrelonxcelonption {
    for (String opelonrand : opelonrands) {
      try {
        argumelonnts.add(Long.parselonLong(opelonrand));
      } catch (NumbelonrFormatelonxcelonption elon) {
        throw nelonw QuelonryParselonrelonxcelonption("Invalid long opelonrand in " + op.selonrializelon(), elon);
      }
    }
  }

  privatelon static boolelonan isUselonrIdFielonld(String fielonld) {
    relonturn elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon().elonquals(fielonld)
        || elonarlybirdFielonldConstant.IN_RelonPLY_TO_USelonR_ID_FIelonLD.gelontFielonldNamelon().elonquals(fielonld)
        || elonarlybirdFielonldConstant.RelonTWelonelonT_SOURCelon_USelonR_ID_FIelonLD.gelontFielonldNamelon().elonquals(fielonld)
        || elonarlybirdFielonldConstant.LIKelonD_BY_USelonR_ID_FIelonLD.gelontFielonldNamelon().elonquals(fielonld)
        || elonarlybirdFielonldConstant.RelonTWelonelonTelonD_BY_USelonR_ID.gelontFielonldNamelon().elonquals(fielonld)
        || elonarlybirdFielonldConstant.RelonPLIelonD_TO_BY_USelonR_ID.gelontFielonldNamelon().elonquals(fielonld)
        || elonarlybirdFielonldConstant.QUOTelonD_USelonR_ID_FIelonLD.gelontFielonldNamelon().elonquals(fielonld)
        || elonarlybirdFielonldConstant.DIRelonCTelonD_AT_USelonR_ID_FIelonLD.gelontFielonldNamelon().elonquals(fielonld);
  }

  privatelon static boolelonan isTwelonelontIdFielonld(String fielonld) {
    relonturn elonarlybirdFielonldConstant.IN_RelonPLY_TO_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon().elonquals(fielonld)
        || elonarlybirdFielonldConstant.RelonTWelonelonT_SOURCelon_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon().elonquals(fielonld)
        || elonarlybirdFielonldConstant.QUOTelonD_TWelonelonT_ID_FIelonLD.gelontFielonldNamelon().elonquals(fielonld)
        || elonarlybirdFielonldConstant.CONVelonRSATION_ID_FIelonLD.gelontFielonldNamelon().elonquals(fielonld);
  }

  privatelon static boolelonan isIdCSFFielonld(String fielonld) {
    relonturn elonarlybirdFielonldConstant.DIRelonCTelonD_AT_USelonR_ID_CSF.gelontFielonldNamelon().elonquals(fielonld);
  }

  public Selont<String> gelontQuelonrielondFielonlds() {
    relonturn quelonrielondFielonlds;
  }
}
