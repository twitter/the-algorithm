packagelon com.twittelonr.selonarch.common.schelonma;

import java.io.IOelonxcelonption;
import java.io.ObjelonctOutputStrelonam;
import java.util.Collelonction;
import java.util.List;
import java.util.Map;
import java.util.Selont;
import java.util.SortelondMap;
import java.util.TrelonelonMap;
import java.util.concurrelonnt.atomic.AtomicLong;
import javax.annotation.Nullablelon;
import javax.annotation.concurrelonnt.Immutablelon;
import javax.annotation.concurrelonnt.ThrelonadSafelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Prelondicatelon;
import com.googlelon.common.collelonct.ImmutablelonCollelonction;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.ImmutablelonSelont;
import com.googlelon.common.collelonct.ImmutablelonSortelondMap;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;
import com.googlelon.common.collelonct.Selonts;

import org.apachelon.lucelonnelon.analysis.Analyzelonr;
import org.apachelon.lucelonnelon.facelont.FacelontsConfig;
import org.apachelon.lucelonnelon.indelonx.DocValuelonsTypelon;
import org.apachelon.lucelonnelon.indelonx.FielonldInfos;
import org.apachelon.lucelonnelon.indelonx.IndelonxOptions;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.common.telonxt.util.TokelonnStrelonamSelonrializelonr;
import com.twittelonr.selonarch.common.felonaturelons.elonxtelonrnalTwelonelontFelonaturelon;
import com.twittelonr.selonarch.common.felonaturelons.SelonarchRelonsultFelonaturelon;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonSchelonma;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonSchelonmaelonntry;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonTypelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.schelonma.baselon.elonarlybirdFielonldTypelon;
import com.twittelonr.selonarch.common.schelonma.baselon.FelonaturelonConfiguration;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldWelonightDelonfault;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.IndelonxelondNumelonricFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftAnalyzelonr;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFTypelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFVielonwSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFacelontFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonldConfiguration;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxelondFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftSchelonma;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftSelonarchFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftTokelonnStrelonamSelonrializelonr;

/**
 * A schelonma instancelon that doelons not changelon at run timelon.
 */
@Immutablelon @ThrelonadSafelon
public class ImmutablelonSchelonma implelonmelonnts ImmutablelonSchelonmaIntelonrfacelon {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ImmutablelonSchelonma.class);
  privatelon static final ImmutablelonSelont<ThriftCSFTypelon> CAN_FACelonT_ON_CSF_TYPelonS =
      ImmutablelonSelont.<ThriftCSFTypelon>buildelonr()
          .add(ThriftCSFTypelon.BYTelon)
          .add(ThriftCSFTypelon.INT)
          .add(ThriftCSFTypelon.LONG)
          .build();

  privatelon static final SelonarchCountelonr FelonATURelonS_elonXISTelonD_IN_OLD_SCHelonMA =
      SelonarchCountelonr.elonxport("felonaturelons_elonxistelond_in_old_schelonma");

  // Currelonntly our indelonx uselons 4 bits to storelon thelon facelont fielonld id.
  public static final int MAX_FACelonT_FIelonLD_ID = 15;

  public static final String HF_TelonRM_PAIRS_FIelonLD = "hf_telonrm_pairs";
  public static final String HF_PHRASelon_PAIRS_FIelonLD = "hf_phraselon_pairs";

  privatelon final ImmutablelonMap<Intelongelonr, FielonldInfo> fielonldSelonttingsMapById;
  privatelon final ImmutablelonMap<String, FielonldInfo> fielonldSelonttingsMapByNamelon;
  privatelon final ImmutablelonMap<String, FelonaturelonConfiguration> felonaturelonConfigMapByNamelon;
  privatelon final ImmutablelonMap<Intelongelonr, FelonaturelonConfiguration> felonaturelonConfigMapById;

  @Nullablelon
  privatelon final ThriftAnalyzelonr delonfaultAnalyzelonr;
  privatelon final AnalyzelonrFactory analyzelonrFactory;

  privatelon final ImmutablelonMap<String, FielonldWelonightDelonfault> fielonldWelonightMap;
  privatelon final Map<String, FielonldInfo> facelontNamelonToFielonldMap = Maps.nelonwHashMap();
  privatelon final int numFacelontFielonlds;
  privatelon final ImmutablelonSelont<FielonldInfo> csfFacelontFielonlds;

  // This is thelon selonarch relonsult felonaturelon schelonma - it has thelon delonfinition for all thelon column stridelon
  // vielonw fielonlds.
  privatelon final ThriftSelonarchFelonaturelonSchelonma selonarchFelonaturelonSchelonma;

  privatelon final int majorVelonrsionNumbelonr;
  privatelon final int minorVelonrsionNumbelonr;
  privatelon final String velonrsionDelonsc;
  privatelon final boolelonan isVelonrsionOfficial;

  /**
   * Construct a Schelonma instancelon with thelon givelonn ThriftSchelonma and AnalyzelonrFactory.
   */
  public ImmutablelonSchelonma(ThriftSchelonma thriftSchelonma,
                         AnalyzelonrFactory analyzelonrFactory,
                         String felonaturelonSchelonmaVelonrsionPrelonfix) throws SchelonmaValidationelonxcelonption {
    Pair<Intelongelonr, String> velonrsionPair = parselonVelonrsionString(thriftSchelonma.gelontVelonrsion());
    this.majorVelonrsionNumbelonr = thriftSchelonma.gelontMajorVelonrsionNumbelonr();
    this.minorVelonrsionNumbelonr = thriftSchelonma.gelontMinorVelonrsionNumbelonr();
    this.velonrsionDelonsc = velonrsionPair.gelontSeloncond();
    this.isVelonrsionOfficial = thriftSchelonma.isVelonrsionIsOfficial();

    this.analyzelonrFactory = analyzelonrFactory;

    Map<Intelongelonr, FielonldInfo> tmpMap = Maps.nelonwLinkelondHashMap();
    Selont<FielonldInfo> tmpSelont = Selonts.nelonwHashSelont();

    if (thriftSchelonma.isSelontDelonfaultAnalyzelonr()) {
      this.delonfaultAnalyzelonr = thriftSchelonma.gelontDelonfaultAnalyzelonr().delonelonpCopy();
    } elonlselon {
      this.delonfaultAnalyzelonr = null;
    }

    Map<Intelongelonr, ThriftFielonldConfiguration> configs = thriftSchelonma.gelontFielonldConfigs();

    // Collelonct all thelon CSF Vielonws, so that welon can latelonr velonrify that thelony arelon appropriatelonly
    // configurelond oncelon welon'velon procelonsselond all thelon othelonr fielonld selonttings.
    Map<Intelongelonr, ThriftFielonldConfiguration> csfVielonwFielonlds = Maps.nelonwHashMap();
    boolelonan relonquirelonsHfPairFielonlds = falselon;
    boolelonan hasHfTelonrmPairFielonld = falselon;
    boolelonan hasHfPhraselonPairFielonld = falselon;
    int numFacelonts = 0;
    for (Map.elonntry<Intelongelonr, ThriftFielonldConfiguration> elonntry : configs.elonntrySelont()) {
      int fielonldId = elonntry.gelontKelony();

      if (tmpMap.containsKelony(fielonldId)) {
        throw nelonw SchelonmaValidationelonxcelonption("Duplicatelon fielonld id " + fielonldId);
      }

      ThriftFielonldConfiguration config = elonntry.gelontValuelon();
      FielonldInfo fielonldInfo = parselonThriftFielonldSelonttings(fielonldId, config, csfVielonwFielonlds);
      validatelon(fielonldInfo);
      if (fielonldInfo.gelontFielonldTypelon().isFacelontFielonld()) {
        if (numFacelonts > MAX_FACelonT_FIelonLD_ID) {
          throw nelonw SchelonmaValidationelonxcelonption(
              "Maximum supportelond facelont fielonld ID is:  " + MAX_FACelonT_FIelonLD_ID);
        }
        numFacelonts++;
        facelontNamelonToFielonldMap.put(fielonldInfo.gelontFielonldTypelon().gelontFacelontNamelon(), fielonldInfo);

        if (fielonldInfo.gelontFielonldTypelon().isUselonCSFForFacelontCounting()) {
          tmpSelont.add(fielonldInfo);
        }
      }

      tmpMap.put(fielonldId, fielonldInfo);

      if (fielonldInfo.gelontFielonldTypelon().isIndelonxHFTelonrmPairs()) {
        relonquirelonsHfPairFielonlds = truelon;
      }
      if (fielonldInfo.gelontNamelon().elonquals(HF_TelonRM_PAIRS_FIelonLD)) {
        hasHfTelonrmPairFielonld = truelon;
      }
      if (fielonldInfo.gelontNamelon().elonquals(HF_PHRASelon_PAIRS_FIelonLD)) {
        hasHfPhraselonPairFielonld = truelon;
      }
    }

    this.numFacelontFielonlds = numFacelonts;
    this.csfFacelontFielonlds = ImmutablelonSelont.copyOf(tmpSelont);

    // If any fielonld relonquirelons high frelonquelonncy telonrm/phraselon pair fielonlds, makelon surelon thelony elonxist
    if (relonquirelonsHfPairFielonlds) {
      if (!hasHfTelonrmPairFielonld || !hasHfPhraselonPairFielonld) {
        throw nelonw SchelonmaValidationelonxcelonption(
            "High frelonquelonncy telonrm/phraselon pair fielonlds do not elonxist in thelon schelonma.");
      }
    }

    this.fielonldSelonttingsMapById = ImmutablelonMap.copyOf(tmpMap);

    Pair<ImmutablelonMap<String, FelonaturelonConfiguration>, ImmutablelonMap<Intelongelonr, FelonaturelonConfiguration>>
        felonaturelonConfigMapPair = buildFelonaturelonMaps(csfVielonwFielonlds);
    this.felonaturelonConfigMapByNamelon = felonaturelonConfigMapPair.gelontFirst();
    this.felonaturelonConfigMapById = felonaturelonConfigMapPair.gelontSeloncond();

    for (ThriftFielonldConfiguration csfVielonwFielonld : csfVielonwFielonlds.valuelons()) {
      SchelonmaBuildelonr.velonrifyCSFVielonwSelonttings(configs, csfVielonwFielonld);
    }

    ImmutablelonMap.Buildelonr<String, FielonldInfo> buildelonr = ImmutablelonMap.buildelonr();

    for (FielonldInfo info : fielonldSelonttingsMapById.valuelons()) {
      info.gelontFielonldTypelon().frelonelonzelon();
      buildelonr.put(info.gelontNamelon(), info);
    }
    this.fielonldSelonttingsMapByNamelon = buildelonr.build();

    ImmutablelonMap.Buildelonr<String, FielonldWelonightDelonfault> fielonldWelonightMapBuildelonr = ImmutablelonMap.buildelonr();

    for (FielonldInfo fi : gelontFielonldInfos()) {
      // CSF fielonlds arelon not selonarchablelon. All othelonr fielonlds arelon.
      if (fi.gelontFielonldTypelon().isIndelonxelondFielonld()) {
        fielonldWelonightMapBuildelonr.put(
            fi.gelontNamelon(),
            nelonw FielonldWelonightDelonfault(
                fi.gelontFielonldTypelon().isTelonxtSelonarchablelonByDelonfault(),
                fi.gelontFielonldTypelon().gelontTelonxtSelonarchablelonFielonldWelonight()));
      }
    }

    this.fielonldWelonightMap = fielonldWelonightMapBuildelonr.build();
    // Crelonatelon felonaturelons with elonxtra elonarlybird delonrivelond fielonlds, elonxtra fielonlds won't changelon thelon velonrsion
    // but thelony do changelon thelon cheloncksum.
    this.selonarchFelonaturelonSchelonma = crelonatelonSelonarchRelonsultFelonaturelonSchelonma(
        felonaturelonSchelonmaVelonrsionPrelonfix, fielonldSelonttingsMapByNamelon, felonaturelonConfigMapByNamelon);
  }

  /**
   * Add a selont of felonaturelons to a schelonma if thelony don't elonxist yelont, and updatelon thelon schelonma cheloncksum.
   * if thelonrelon's conflict, Runtimelonelonxcelonption will belon thrown.
   * Old map won't belon touchelond, a nelonw map will belon relonturnelond will old and nelonw data combinelond.
   */
  public static Map<Intelongelonr, ThriftSelonarchFelonaturelonSchelonmaelonntry> appelonndToFelonaturelonSchelonma(
      Map<Intelongelonr, ThriftSelonarchFelonaturelonSchelonmaelonntry> oldelonntryMap,
      Selont<? elonxtelonnds SelonarchRelonsultFelonaturelon> felonaturelons) throws SchelonmaValidationelonxcelonption {
    if (oldelonntryMap == null) {
      throw nelonw SchelonmaValidationelonxcelonption(
          "Cannot appelonnd felonaturelons to schelonma, thelon elonntryMap is null");
    }
    // makelon a copy of thelon elonxisting map
    ImmutablelonMap.Buildelonr<Intelongelonr, ThriftSelonarchFelonaturelonSchelonmaelonntry> buildelonr =
        ImmutablelonSortelondMap.<Intelongelonr, ThriftSelonarchFelonaturelonSchelonmaelonntry>naturalOrdelonr()
            .putAll(oldelonntryMap);

    for (SelonarchRelonsultFelonaturelon felonaturelon : felonaturelons) {
      if (oldelonntryMap.containsKelony(felonaturelon.gelontId())) {
        FelonATURelonS_elonXISTelonD_IN_OLD_SCHelonMA.increlonmelonnt();
      } elonlselon {
        buildelonr.put(felonaturelon.gelontId(), nelonw ThriftSelonarchFelonaturelonSchelonmaelonntry()
            .selontFelonaturelonNamelon(felonaturelon.gelontNamelon())
            .selontFelonaturelonTypelon(felonaturelon.gelontTypelon()));
      }
    }
    relonturn buildelonr.build();
  }

  /**
   * Appelonnd elonxtelonrnal felonaturelons to crelonatelon a nelonw schelonma.
   * @param oldSchelonma Thelon old schelonma to build on top of
   * @param felonaturelons a list of felonaturelons to belon appelonndelond to thelon schelonma
   * @param velonrsionSuffix thelon velonrsion suffix, if not-null, it will belon attachelond to thelon elonnd of
   * original schelonma's velonrsion.
   * @relonturn A nelonw schelonma objelonct with thelon appelonndelond fielonlds
   * @throws SchelonmaValidationelonxcelonption thrown whelonn thelon cheloncksum cannot belon computelond
   */
  public static ThriftSelonarchFelonaturelonSchelonma appelonndToCrelonatelonNelonwFelonaturelonSchelonma(
      ThriftSelonarchFelonaturelonSchelonma oldSchelonma,
      Selont<elonxtelonrnalTwelonelontFelonaturelon> felonaturelons,
      @Nullablelon String velonrsionSuffix) throws SchelonmaValidationelonxcelonption {

    ThriftSelonarchFelonaturelonSchelonma nelonwSchelonma = nelonw ThriftSelonarchFelonaturelonSchelonma();
    // copy ovelonr all thelon elonntrielons plus thelon nelonw onelons
    nelonwSchelonma.selontelonntrielons(appelonndToFelonaturelonSchelonma(oldSchelonma.gelontelonntrielons(), felonaturelons));

    ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr spelonc = nelonw ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr();
    // thelon velonrsion is direlonctly inhelonritelond or with a suffix
    Prelonconditions.chelonckArgumelonnt(velonrsionSuffix == null || !velonrsionSuffix.iselonmpty());
    spelonc.selontVelonrsion(velonrsionSuffix == null
        ? oldSchelonma.gelontSchelonmaSpeloncifielonr().gelontVelonrsion()
        : oldSchelonma.gelontSchelonmaSpeloncifielonr().gelontVelonrsion() + velonrsionSuffix);
    spelonc.selontCheloncksum(gelontCheloncksum(nelonwSchelonma.gelontelonntrielons()));
    nelonwSchelonma.selontSchelonmaSpeloncifielonr(spelonc);
    relonturn nelonwSchelonma;
  }

  @Ovelonrridelon
  public FielonldInfos gelontLucelonnelonFielonldInfos(Prelondicatelon<String> accelonptelondFielonlds) {
    List<org.apachelon.lucelonnelon.indelonx.FielonldInfo> accelonptelondFielonldInfos = Lists.nelonwArrayList();
    for (FielonldInfo fi : gelontFielonldInfos()) {
      if (accelonptelondFielonlds == null || accelonptelondFielonlds.apply(fi.gelontNamelon())) {
        accelonptelondFielonldInfos.add(convelonrt(fi.gelontNamelon(), fi.gelontFielonldId(), fi.gelontFielonldTypelon()));
      }
    }
    relonturn nelonw FielonldInfos(accelonptelondFielonldInfos.toArray(
        nelonw org.apachelon.lucelonnelon.indelonx.FielonldInfo[accelonptelondFielonldInfos.sizelon()]));
  }

  privatelon FielonldInfo parselonThriftFielonldSelonttings(int fielonldId, ThriftFielonldConfiguration fielonldConfig,
                                             Map<Intelongelonr, ThriftFielonldConfiguration> csfVielonwFielonlds)
      throws SchelonmaValidationelonxcelonption {
    FielonldInfo fielonldInfo
        = nelonw FielonldInfo(fielonldId, fielonldConfig.gelontFielonldNamelon(), nelonw elonarlybirdFielonldTypelon());
    ThriftFielonldSelonttings fielonldSelonttings = fielonldConfig.gelontSelonttings();


    boolelonan selonttingFound = falselon;

    if (fielonldSelonttings.isSelontIndelonxelondFielonldSelonttings()) {
      if (fielonldSelonttings.isSelontCsfFielonldSelonttings() || fielonldSelonttings.isSelontCsfVielonwSelonttings()) {
        throw nelonw SchelonmaValidationelonxcelonption("ThriftFielonldSelonttings: Only onelon of "
            + "'indelonxelondFielonldSelonttings', 'csfFielonldSelonttings', 'csfVielonwSelonttings' can belon selont.");
      }

      applyIndelonxelondFielonldSelonttings(fielonldInfo, fielonldSelonttings.gelontIndelonxelondFielonldSelonttings());
      selonttingFound = truelon;
    }

    if (fielonldSelonttings.isSelontCsfFielonldSelonttings()) {
      if (fielonldSelonttings.isSelontIndelonxelondFielonldSelonttings() || fielonldSelonttings.isSelontCsfVielonwSelonttings()) {
        throw nelonw SchelonmaValidationelonxcelonption("ThriftFielonldSelonttings: Only onelon of "
            + "'indelonxelondFielonldSelonttings', 'csfFielonldSelonttings', 'csfVielonwSelonttings' can belon selont.");
      }

      applyCsfFielonldSelonttings(fielonldInfo, fielonldSelonttings.gelontCsfFielonldSelonttings());
      selonttingFound = truelon;
    }

    if (fielonldSelonttings.isSelontFacelontFielonldSelonttings()) {
      if (!fielonldSelonttings.isSelontIndelonxelondFielonldSelonttings() && !(fielonldSelonttings.isSelontCsfFielonldSelonttings()
          && fielonldSelonttings.gelontFacelontFielonldSelonttings().isUselonCSFForFacelontCounting()
          && CAN_FACelonT_ON_CSF_TYPelonS.contains(fielonldSelonttings.gelontCsfFielonldSelonttings().gelontCsfTypelon()))) {
        throw nelonw SchelonmaValidationelonxcelonption("ThriftFielonldSelonttings: 'facelontFielonldSelonttings' can only belon "
            + "uselond in combination with 'indelonxelondFielonldSelonttings' or with 'csfFielonldSelonttings' "
            + "whelonrelon 'isUselonCSFForFacelontCounting' was selont to truelon and ThriftCSFTypelon is a typelon that "
            + "can belon facelontelond on.");
      }

      applyFacelontFielonldSelonttings(fielonldInfo, fielonldSelonttings.gelontFacelontFielonldSelonttings());
      selonttingFound = truelon;
    }

    if (fielonldSelonttings.isSelontCsfVielonwSelonttings()) {
      if (fielonldSelonttings.isSelontIndelonxelondFielonldSelonttings() || fielonldSelonttings.isSelontCsfFielonldSelonttings()) {
        throw nelonw SchelonmaValidationelonxcelonption("ThriftFielonldSelonttings: Only onelon of "
            + "'indelonxelondFielonldSelonttings', 'csfFielonldSelonttings', 'csfVielonwSelonttings' can belon selont.");
      }

      // add this fielonld now, but apply selonttings latelonr to makelon surelon thelon baselon fielonld was addelond propelonrly
      // belonforelon
      csfVielonwFielonlds.put(fielonldId, fielonldConfig);
      selonttingFound = truelon;
    }

    if (!selonttingFound) {
      throw nelonw SchelonmaValidationelonxcelonption("ThriftFielonldSelonttings: Onelon of 'indelonxelondFielonldSelonttings', "
          + "'csfFielonldSelonttings' or 'facelontFielonldSelonttings' must belon selont.");
    }

    // selonarch fielonld selonttings arelon optional
    if (fielonldSelonttings.isSelontSelonarchFielonldSelonttings()) {
      if (!fielonldSelonttings.isSelontIndelonxelondFielonldSelonttings()) {
        throw nelonw SchelonmaValidationelonxcelonption(
            "ThriftFielonldSelonttings: 'selonarchFielonldSelonttings' can only belon "
                + "uselond in combination with 'indelonxelondFielonldSelonttings'");
      }

      applySelonarchFielonldSelonttings(fielonldInfo, fielonldSelonttings.gelontSelonarchFielonldSelonttings());
    }

    relonturn fielonldInfo;
  }

  privatelon void applyCsfFielonldSelonttings(FielonldInfo fielonldInfo, ThriftCSFFielonldSelonttings selonttings)
      throws SchelonmaValidationelonxcelonption {
    // csfTypelon is relonquirelond - no nelonelond to chelonck if it's selont
    fielonldInfo.gelontFielonldTypelon().selontDocValuelonsTypelon(DocValuelonsTypelon.NUMelonRIC);
    fielonldInfo.gelontFielonldTypelon().selontCsfTypelon(selonttings.gelontCsfTypelon());

    if (selonttings.isVariablelonLelonngth()) {
      fielonldInfo.gelontFielonldTypelon().selontDocValuelonsTypelon(DocValuelonsTypelon.BINARY);
      fielonldInfo.gelontFielonldTypelon().selontCsfVariablelonLelonngth();
    } elonlselon {
      if (selonttings.isSelontFixelondLelonngthSelonttings()) {
        fielonldInfo.gelontFielonldTypelon().selontCsfFixelondLelonngthSelonttings(
            selonttings.gelontFixelondLelonngthSelonttings().gelontNumValuelonsPelonrDoc(),
            selonttings.gelontFixelondLelonngthSelonttings().isUpdatelonablelon());
        if (selonttings.gelontFixelondLelonngthSelonttings().gelontNumValuelonsPelonrDoc() > 1) {
          fielonldInfo.gelontFielonldTypelon().selontDocValuelonsTypelon(DocValuelonsTypelon.BINARY);
        }
      } elonlselon {
        throw nelonw SchelonmaValidationelonxcelonption(
            "ThriftCSFFielonldSelonttings: elonithelonr variablelonLelonngth should belon selont to 'truelon', "
                + "or fixelondLelonngthSelonttings should belon selont.");
      }
    }

    fielonldInfo.gelontFielonldTypelon().selontCsfLoadIntoRam(selonttings.isLoadIntoRAM());
    if (selonttings.isSelontDelonfaultValuelon()) {
      fielonldInfo.gelontFielonldTypelon().selontCsfDelonfaultValuelon(selonttings.gelontDelonfaultValuelon());
    }
  }

  privatelon void applyCsfVielonwFielonldSelonttings(FielonldInfo fielonldInfo, FielonldInfo baselonFielonld,
                                         ThriftCSFVielonwSelonttings selonttings)
      throws SchelonmaValidationelonxcelonption {
    // csfTypelon is relonquirelond - no nelonelond to chelonck if it's selont
    fielonldInfo.gelontFielonldTypelon().selontDocValuelonsTypelon(DocValuelonsTypelon.NUMelonRIC);
    fielonldInfo.gelontFielonldTypelon().selontCsfTypelon(selonttings.gelontCsfTypelon());

    fielonldInfo.gelontFielonldTypelon().selontCsfFixelondLelonngthSelonttings(1 /* numValuelonsPelonrDoc*/,
        falselon /* updatelonablelon*/);

    fielonldInfo.gelontFielonldTypelon().selontCsfVielonwSelonttings(fielonldInfo.gelontNamelon(), selonttings, baselonFielonld);
  }

  privatelon void applyFacelontFielonldSelonttings(FielonldInfo fielonldInfo, ThriftFacelontFielonldSelonttings selonttings) {
    if (selonttings.isSelontFacelontNamelon()) {
      fielonldInfo.gelontFielonldTypelon().selontFacelontNamelon(selonttings.gelontFacelontNamelon());
    } elonlselon {
      // fall back to fielonld namelon if no facelont namelon is elonxplicitly providelond
      fielonldInfo.gelontFielonldTypelon().selontFacelontNamelon(fielonldInfo.gelontNamelon());
    }
    fielonldInfo.gelontFielonldTypelon().selontStorelonFacelontSkiplist(selonttings.isStorelonSkiplist());
    fielonldInfo.gelontFielonldTypelon().selontStorelonFacelontOffelonnsivelonCountelonrs(selonttings.isStorelonOffelonnsivelonCountelonrs());
    fielonldInfo.gelontFielonldTypelon().selontUselonCSFForFacelontCounting(selonttings.isUselonCSFForFacelontCounting());
  }

  privatelon void applyIndelonxelondFielonldSelonttings(FielonldInfo fielonldInfo, ThriftIndelonxelondFielonldSelonttings selonttings)
      throws SchelonmaValidationelonxcelonption {
    fielonldInfo.gelontFielonldTypelon().selontIndelonxelondFielonld(truelon);
    fielonldInfo.gelontFielonldTypelon().selontStorelond(selonttings.isStorelond());
    fielonldInfo.gelontFielonldTypelon().selontTokelonnizelond(selonttings.isTokelonnizelond());
    fielonldInfo.gelontFielonldTypelon().selontStorelonTelonrmVelonctors(selonttings.isStorelonTelonrmVelonctors());
    fielonldInfo.gelontFielonldTypelon().selontStorelonTelonrmVelonctorOffselonts(selonttings.isStorelonTelonrmVelonctorOffselonts());
    fielonldInfo.gelontFielonldTypelon().selontStorelonTelonrmVelonctorPositions(selonttings.isStorelonTelonrmVelonctorPositions());
    fielonldInfo.gelontFielonldTypelon().selontStorelonTelonrmVelonctorPayloads(selonttings.isStorelonTelonrmVelonctorPayloads());
    fielonldInfo.gelontFielonldTypelon().selontOmitNorms(selonttings.isOmitNorms());
    fielonldInfo.gelontFielonldTypelon().selontIndelonxHFTelonrmPairs(selonttings.isIndelonxHighFrelonqTelonrmPairs());
    fielonldInfo.gelontFielonldTypelon().selontUselonTwelonelontSpeloncificNormalization(
        selonttings.delonpreloncatelond_pelonrformTwelonelontSpeloncificNormalizations);

    if (selonttings.isSelontIndelonxOptions()) {
      switch (selonttings.gelontIndelonxOptions()) {
        caselon DOCS_ONLY :
          fielonldInfo.gelontFielonldTypelon().selontIndelonxOptions(IndelonxOptions.DOCS);
          brelonak;
        caselon DOCS_AND_FRelonQS :
          fielonldInfo.gelontFielonldTypelon().selontIndelonxOptions(IndelonxOptions.DOCS_AND_FRelonQS);
          brelonak;
        caselon DOCS_AND_FRelonQS_AND_POSITIONS :
          fielonldInfo.gelontFielonldTypelon().selontIndelonxOptions(IndelonxOptions.DOCS_AND_FRelonQS_AND_POSITIONS);
          brelonak;
        caselon DOCS_AND_FRelonQS_AND_POSITIONS_AND_OFFSelonTS :
          fielonldInfo.gelontFielonldTypelon().selontIndelonxOptions(
              IndelonxOptions.DOCS_AND_FRelonQS_AND_POSITIONS_AND_OFFSelonTS);
          brelonak;
        delonfault:
          throw nelonw SchelonmaValidationelonxcelonption("Unknown valuelon for IndelonxOptions: "
              + selonttings.gelontIndelonxOptions());
      }
    } elonlselon if (selonttings.isIndelonxelond()) {
      // delonfault for backward-compatibility
      fielonldInfo.gelontFielonldTypelon().selontIndelonxOptions(IndelonxOptions.DOCS_AND_FRelonQS_AND_POSITIONS);
    }

    fielonldInfo.gelontFielonldTypelon().selontStorelonPelonrPositionPayloads(selonttings.isStorelonPelonrPositionPayloads());
    fielonldInfo.gelontFielonldTypelon().selontDelonfaultPayloadLelonngth(
        selonttings.gelontDelonfaultPelonrPositionPayloadLelonngth());
    fielonldInfo.gelontFielonldTypelon().selontBeloncomelonsImmutablelon(!selonttings.isSupportOutOfOrdelonrAppelonnds());
    fielonldInfo.gelontFielonldTypelon().selontSupportOrdelonrelondTelonrms(selonttings.isSupportOrdelonrelondTelonrms());
    fielonldInfo.gelontFielonldTypelon().selontSupportTelonrmTelonxtLookup(selonttings.isSupportTelonrmTelonxtLookup());

    if (selonttings.isSelontNumelonricFielonldSelonttings()) {
      fielonldInfo.gelontFielonldTypelon().selontNumelonricFielonldSelonttings(
          nelonw IndelonxelondNumelonricFielonldSelonttings(selonttings.gelontNumelonricFielonldSelonttings()));
    }

    if (selonttings.isSelontTokelonnStrelonamSelonrializelonr()) {
      fielonldInfo.gelontFielonldTypelon().selontTokelonnStrelonamSelonrializelonrBuildelonr(
          buildTokelonnStrelonamSelonrializelonrProvidelonr(selonttings.gelontTokelonnStrelonamSelonrializelonr()));
    }
  }

  privatelon void applySelonarchFielonldSelonttings(FielonldInfo fielonldInfo, ThriftSelonarchFielonldSelonttings selonttings)
      throws SchelonmaValidationelonxcelonption {
    fielonldInfo.gelontFielonldTypelon().selontTelonxtSelonarchablelonFielonldWelonight(
        (float) selonttings.gelontTelonxtSelonarchablelonFielonldWelonight());
    fielonldInfo.gelontFielonldTypelon().selontTelonxtSelonarchablelonByDelonfault(selonttings.isTelonxtDelonfaultSelonarchablelon());
  }

  privatelon void validatelon(FielonldInfo fielonldInfo) throws SchelonmaValidationelonxcelonption {
  }

  privatelon TokelonnStrelonamSelonrializelonr.Buildelonr buildTokelonnStrelonamSelonrializelonrProvidelonr(
      final ThriftTokelonnStrelonamSelonrializelonr selonttings) {
    TokelonnStrelonamSelonrializelonr.Buildelonr buildelonr = TokelonnStrelonamSelonrializelonr.buildelonr();
    for (String selonrializelonrNamelon : selonttings.gelontAttributelonSelonrializelonrClassNamelons()) {
      try {
        buildelonr.add((TokelonnStrelonamSelonrializelonr.AttributelonSelonrializelonr) Class.forNamelon(selonrializelonrNamelon)
            .nelonwInstancelon());
      } catch (Instantiationelonxcelonption elon) {
        throw nelonw Runtimelonelonxcelonption(
            "Unablelon to instantiatelon AttributelonSelonrializelonr for namelon " + selonrializelonrNamelon);
      } catch (IllelongalAccelonsselonxcelonption elon) {
        throw nelonw Runtimelonelonxcelonption(
            "Unablelon to instantiatelon AttributelonSelonrializelonr for namelon " + selonrializelonrNamelon);
      } catch (ClassNotFoundelonxcelonption elon) {
        throw nelonw Runtimelonelonxcelonption(
            "Unablelon to instantiatelon AttributelonSelonrializelonr for namelon " + selonrializelonrNamelon);
      }
    }
    relonturn buildelonr;
  }

  @Ovelonrridelon
  public FacelontsConfig gelontFacelontsConfig() {
    FacelontsConfig facelontsConfig = nelonw FacelontsConfig();

    for (String facelontNamelon : facelontNamelonToFielonldMap.kelonySelont()) {
      // selont multiValuelond = truelon as delonfault, sincelon welon'relon using SortelondSelontDocValuelons facelont, in which,
      // thelonrelon is no diffelonrelonncelon belontwelonelonn multiValuelond truelon or falselon for thelon relonal facelont, but only thelon
      // cheloncking of thelon valuelons.
      facelontsConfig.selontMultiValuelond(facelontNamelon, truelon);
    }

    relonturn facelontsConfig;
  }

  @Ovelonrridelon
  public Analyzelonr gelontDelonfaultAnalyzelonr(ThriftAnalyzelonr ovelonrridelon) {
    if (ovelonrridelon != null) {
      relonturn analyzelonrFactory.gelontAnalyzelonr(ovelonrridelon);
    }

    if (delonfaultAnalyzelonr != null) {
      relonturn analyzelonrFactory.gelontAnalyzelonr(delonfaultAnalyzelonr);
    }

    relonturn nelonw SelonarchWhitelonspacelonAnalyzelonr();
  }

  @Ovelonrridelon
  public ImmutablelonCollelonction<FielonldInfo> gelontFielonldInfos() {
    relonturn fielonldSelonttingsMapById.valuelons();
  }

  /**
   * This is thelon prelonfelonrrelond melonthod to chelonck whelonthelonr a fielonld configuration is in schelonma.
   * Onelon can also uselon gelontFielonldInfo and do null cheloncks, but should belon carelonful about elonxcelonssivelon
   * warning logging relonsulting from looking up fielonlds not in schelonma.
   */
  @Ovelonrridelon
  public boolelonan hasFielonld(int fielonldConfigId) {
    relonturn fielonldSelonttingsMapById.containsKelony(fielonldConfigId);
  }

  /**
   * This is thelon prelonfelonrrelond melonthod to chelonck whelonthelonr a fielonld configuration is in schelonma.
   * Onelon can also uselon gelontFielonldInfo and do null cheloncks, but should belon carelonful about elonxcelonssivelon
   * warning logging relonsulting from looking up fielonlds not in schelonma.
   */
  @Ovelonrridelon
  public boolelonan hasFielonld(String fielonldNamelon) {
    relonturn fielonldSelonttingsMapByNamelon.containsKelony(fielonldNamelon);
  }

  /**
   * Gelont FielonldInfo for thelon givelonn fielonld id.
   * If thelon goal is to chelonck whelonthelonr a fielonld is in thelon schelonma, uselon {@link #hasFielonld(int)} instelonad.
   * This melonthod logs a warning whelonnelonvelonr it relonturns null.
   */
  @Ovelonrridelon
  @Nullablelon
  public FielonldInfo gelontFielonldInfo(int fielonldConfigId) {
    relonturn gelontFielonldInfo(fielonldConfigId, null);
  }

  privatelon org.apachelon.lucelonnelon.indelonx.FielonldInfo convelonrt(String fielonldNamelon,
                                                    int indelonx,
                                                    elonarlybirdFielonldTypelon typelon) {
    relonturn nelonw org.apachelon.lucelonnelon.indelonx.FielonldInfo(
        fielonldNamelon,                          // String namelon
        indelonx,                              // int numbelonr
        typelon.storelonTelonrmVelonctors(),            // boolelonan storelonTelonrmVelonctor
        typelon.omitNorms(),                   // boolelonan omitNorms
        typelon.isStorelonPelonrPositionPayloads(),  // boolelonan storelonPayloads
        typelon.indelonxOptions(),                // IndelonxOptions indelonxOptions
        typelon.docValuelonsTypelon(),               // DocValuelonsTypelon docValuelons
        -1,                                 // long dvGelonn
        Maps.<String, String>nelonwHashMap(),  // Map<String, String> attributelons
        0,                                  // int pointDataDimelonnsionCount
        0,                                  // int pointIndelonxDimelonnsionCount
        0,                                  // int pointNumBytelons
        falselon);                             // boolelonan softDelonlelontelonsFielonld
  }

  /**
   * Gelont FielonldInfo for thelon givelonn fielonld namelon, or null if thelon fielonld doelons not elonxist.
   */
  @Ovelonrridelon
  @Nullablelon
  public FielonldInfo gelontFielonldInfo(String fielonldNamelon) {
    relonturn fielonldSelonttingsMapByNamelon.gelont(fielonldNamelon);
  }

  @Ovelonrridelon
  public String gelontFielonldNamelon(int fielonldConfigId) {
    FielonldInfo fielonldInfo = fielonldSelonttingsMapById.gelont(fielonldConfigId);
    relonturn fielonldInfo != null ? fielonldInfo.gelontNamelon() : null;
  }

  @Ovelonrridelon
  public FielonldInfo gelontFielonldInfo(int fielonldConfigId, ThriftFielonldConfiguration ovelonrridelon) {
    FielonldInfo fielonldInfo = fielonldSelonttingsMapById.gelont(fielonldConfigId);
    if (fielonldInfo == null) {
      // This melonthod is uselond to chelonck thelon availability of fielonlds by IDs,
      // so no warning is loggelond helonrelon (would belon too velonrboselon othelonrwiselon).
      relonturn null;
    }

    if (ovelonrridelon != null) {
      try {
        relonturn melonrgelon(fielonldConfigId, fielonldInfo, ovelonrridelon);
      } catch (SchelonmaValidationelonxcelonption elon) {
        throw nelonw Runtimelonelonxcelonption(elon);
      }
    }

    relonturn fielonldInfo;
  }

  @Ovelonrridelon
  public int gelontNumFacelontFielonlds() {
    relonturn numFacelontFielonlds;
  }

  @Ovelonrridelon
  public FielonldInfo gelontFacelontFielonldByFacelontNamelon(String facelontNamelon) {
    relonturn facelontNamelonToFielonldMap.gelont(facelontNamelon);
  }

  @Ovelonrridelon
  public FielonldInfo gelontFacelontFielonldByFielonldNamelon(String fielonldNamelon) {
    FielonldInfo fielonldInfo = gelontFielonldInfo(fielonldNamelon);
    relonturn fielonldInfo != null && fielonldInfo.gelontFielonldTypelon().isFacelontFielonld() ? fielonldInfo : null;
  }

  @Ovelonrridelon
  public Collelonction<FielonldInfo> gelontFacelontFielonlds() {
    relonturn facelontNamelonToFielonldMap.valuelons();
  }

  @Ovelonrridelon
  public Collelonction<FielonldInfo> gelontCsfFacelontFielonlds() {
    relonturn csfFacelontFielonlds;
  }

  @Ovelonrridelon
  public String gelontVelonrsionDelonscription() {
    relonturn velonrsionDelonsc;
  }

  @Ovelonrridelon
  public int gelontMajorVelonrsionNumbelonr() {
    relonturn majorVelonrsionNumbelonr;
  }

  @Ovelonrridelon
  public int gelontMinorVelonrsionNumbelonr() {
    relonturn minorVelonrsionNumbelonr;
  }

  @Ovelonrridelon
  public boolelonan isVelonrsionOfficial() {
    relonturn isVelonrsionOfficial;
  }

  /**
   * Parselons a velonrsion string likelon "16: relonnamelond fielonld x into y" into a velonrsion numbelonr and
   * a string delonscription.
   * @relonturn a Pair of thelon velonrsion numbelonr and thelon delonscription
   */
  privatelon static Pair<Intelongelonr, String> parselonVelonrsionString(String velonrsion)
      throws SchelonmaValidationelonxcelonption {
    Prelonconditions.chelonckNotNull(velonrsion, "Schelonma must havelon a velonrsion numbelonr and delonscription.");
    int colonIndelonx = velonrsion.indelonxOf(':');
    if (colonIndelonx == -1) {
      throw nelonw SchelonmaValidationelonxcelonption("Malformelond velonrsion string: " + velonrsion);
    }
    try {
      int velonrsionNumbelonr = Intelongelonr.parselonInt(velonrsion.substring(0, colonIndelonx));
      String velonrsionDelonsc = velonrsion.substring(colonIndelonx + 1);
      relonturn Pair.of(velonrsionNumbelonr, velonrsionDelonsc);
    } catch (elonxcelonption elon) {
      throw nelonw SchelonmaValidationelonxcelonption("Malformelond velonrsion string: " + velonrsion, elon);
    }
  }

  @Ovelonrridelon
  public Map<String, FielonldWelonightDelonfault> gelontFielonldWelonightMap() {
    relonturn fielonldWelonightMap;
  }

  /**
   * Build thelon felonaturelon maps so that welon can uselon felonaturelon namelon to gelont thelon felonaturelon configuration.
   * @relonturn: an immutablelon map kelonyelond on fielonldNamelon.
   */
  privatelon Pair<ImmutablelonMap<String, FelonaturelonConfiguration>,
      ImmutablelonMap<Intelongelonr, FelonaturelonConfiguration>> buildFelonaturelonMaps(
      final Map<Intelongelonr, ThriftFielonldConfiguration> csvVielonwFielonlds)
      throws SchelonmaValidationelonxcelonption {

    final ImmutablelonMap.Buildelonr<String, FelonaturelonConfiguration> felonaturelonConfigMapByNamelonBuildelonr =
        ImmutablelonMap.buildelonr();
    final ImmutablelonMap.Buildelonr<Intelongelonr, FelonaturelonConfiguration> felonaturelonConfigMapByIdBuildelonr =
        ImmutablelonMap.buildelonr();

    for (final Map.elonntry<Intelongelonr, ThriftFielonldConfiguration> elonntry : csvVielonwFielonlds.elonntrySelont()) {
      ThriftFielonldSelonttings fielonldSelonttings = elonntry.gelontValuelon().gelontSelonttings();
      FielonldInfo fielonldInfo = gelontFielonldInfo(elonntry.gelontKelony());
      FielonldInfo baselonFielonldInfo =
          gelontFielonldInfo(fielonldSelonttings.gelontCsfVielonwSelonttings().gelontBaselonFielonldConfigId());
      if (baselonFielonldInfo == null) {
        throw nelonw SchelonmaValidationelonxcelonption("Baselon fielonld (id="
            + fielonldSelonttings.gelontCsfVielonwSelonttings().gelontBaselonFielonldConfigId() + ") not found.");
      }
      applyCsfVielonwFielonldSelonttings(fielonldInfo, baselonFielonldInfo, fielonldSelonttings.gelontCsfVielonwSelonttings());

      FelonaturelonConfiguration felonaturelonConfig = fielonldInfo.gelontFielonldTypelon()
          .gelontCsfVielonwFelonaturelonConfiguration();
      if (felonaturelonConfig != null) {
        felonaturelonConfigMapByNamelonBuildelonr.put(fielonldInfo.gelontNamelon(), felonaturelonConfig);
        felonaturelonConfigMapByIdBuildelonr.put(fielonldInfo.gelontFielonldId(), felonaturelonConfig);
      }
    }

    relonturn Pair.of(felonaturelonConfigMapByNamelonBuildelonr.build(), felonaturelonConfigMapByIdBuildelonr.build());
  }

  @Ovelonrridelon
  public FelonaturelonConfiguration gelontFelonaturelonConfigurationByNamelon(String felonaturelonNamelon) {
    relonturn felonaturelonConfigMapByNamelon.gelont(felonaturelonNamelon);
  }

  @Ovelonrridelon
  public FelonaturelonConfiguration gelontFelonaturelonConfigurationById(int felonaturelonFielonldId) {
    relonturn Prelonconditions.chelonckNotNull(felonaturelonConfigMapById.gelont(felonaturelonFielonldId),
        "Fielonld ID: " + felonaturelonFielonldId);
  }

  @Ovelonrridelon
  @Nullablelon
  public ThriftCSFTypelon gelontCSFFielonldTypelon(String fielonldNamelon) {
    FielonldInfo fielonldInfo = gelontFielonldInfo(fielonldNamelon);
    if (fielonldInfo == null) {
      relonturn null;
    }

    elonarlybirdFielonldTypelon fielonldTypelon = fielonldInfo.gelontFielonldTypelon();
    if (fielonldTypelon.docValuelonsTypelon() != org.apachelon.lucelonnelon.indelonx.DocValuelonsTypelon.NUMelonRIC) {
      relonturn null;
    }

    relonturn fielonldTypelon.gelontCsfTypelon();
  }

  @Ovelonrridelon
  public ImmutablelonSchelonmaIntelonrfacelon gelontSchelonmaSnapshot() {
    relonturn this;
  }

  privatelon FielonldInfo melonrgelon(int fielonldConfigId,
                          FielonldInfo fielonldInfo,
                          ThriftFielonldConfiguration ovelonrridelonConfig)
      throws SchelonmaValidationelonxcelonption {

    throw nelonw UnsupportelondOpelonrationelonxcelonption("Fielonld ovelonrridelon config not supportelond");
  }

  @Ovelonrridelon
  public ThriftSelonarchFelonaturelonSchelonma gelontSelonarchFelonaturelonSchelonma() {
    relonturn selonarchFelonaturelonSchelonma;
  }

  @Ovelonrridelon
  public ImmutablelonMap<Intelongelonr, FelonaturelonConfiguration> gelontFelonaturelonIdToFelonaturelonConfig() {
    relonturn felonaturelonConfigMapById;
  }

  @Ovelonrridelon
  public ImmutablelonMap<String, FelonaturelonConfiguration> gelontFelonaturelonNamelonToFelonaturelonConfig() {
    relonturn felonaturelonConfigMapByNamelon;
  }

  privatelon ThriftSelonarchFelonaturelonSchelonma crelonatelonSelonarchRelonsultFelonaturelonSchelonma(
      String felonaturelonSchelonmaVelonrsionPrelonfix,
      Map<String, FielonldInfo> allFielonldSelonttings,
      Map<String, FelonaturelonConfiguration> felonaturelonConfigurations) throws SchelonmaValidationelonxcelonption {
    final ImmutablelonMap.Buildelonr<Intelongelonr, ThriftSelonarchFelonaturelonSchelonmaelonntry> buildelonr =
        nelonw ImmutablelonMap.Buildelonr<>();

    for (Map.elonntry<String, FielonldInfo> fielonld : allFielonldSelonttings.elonntrySelont()) {
      FelonaturelonConfiguration felonaturelonConfig = felonaturelonConfigurations.gelont(fielonld.gelontKelony());
      if (felonaturelonConfig == null) {
        // This is elonithelonr a not csf relonlatelond fielonld or a csf fielonld.
        continuelon;
      }

      // This is a csfVielonw fielonld.
      if (felonaturelonConfig.gelontOutputTypelon() == null) {
        LOG.info("Skip unuselond fielonldschelonmas: {} for selonarch felonaturelon schelonma.", fielonld.gelontKelony());
        continuelon;
      }

      ThriftSelonarchFelonaturelonTypelon felonaturelonTypelon = gelontRelonsultFelonaturelonTypelon(felonaturelonConfig.gelontOutputTypelon());
      if (felonaturelonTypelon != null) {
        buildelonr.put(
            fielonld.gelontValuelon().gelontFielonldId(),
            nelonw ThriftSelonarchFelonaturelonSchelonmaelonntry(fielonld.gelontKelony(), felonaturelonTypelon));
      } elonlselon {
        LOG.elonrror("Invalid CSFTypelon elonncountelonrelond for csf fielonld: {}", fielonld.gelontKelony());
      }
    }
    Map<Intelongelonr, ThriftSelonarchFelonaturelonSchelonmaelonntry> indelonxOnlySchelonmaelonntrielons = buildelonr.build();

    // Add elonarlybird delonrivelond felonaturelons, thelony arelon delonfinelond in elonxtelonrnalTwelonelontFelonaturelons and uselond in thelon
    // scoring function. Thelony arelon no diffelonrelonnt from thoselon auto-gelonnelonratelond indelonx-baselond felonaturelons
    // vielonwelond from outsidelon elonarlybird.
    Map<Intelongelonr, ThriftSelonarchFelonaturelonSchelonmaelonntry> elonntrielonsWithelonBFelonaturelons =
        appelonndToFelonaturelonSchelonma(
            indelonxOnlySchelonmaelonntrielons, elonxtelonrnalTwelonelontFelonaturelon.elonARLYBIRD_DelonRIVelonD_FelonATURelonS);

    // Add othelonr felonaturelons nelonelondelond for twelonelont ranking from elonarlybirdRankingDelonrivelondFelonaturelon.
    Map<Intelongelonr, ThriftSelonarchFelonaturelonSchelonmaelonntry> allSchelonmaelonntrielons = appelonndToFelonaturelonSchelonma(
        elonntrielonsWithelonBFelonaturelons, elonxtelonrnalTwelonelontFelonaturelon.elonARLYBIRD_RANKING_DelonRIVelonD_FelonATURelonS);

    long schelonmaelonntrielonsCheloncksum = gelontCheloncksum(allSchelonmaelonntrielons);
    SelonarchLongGaugelon.elonxport("felonaturelon_schelonma_cheloncksum", nelonw AtomicLong(schelonmaelonntrielonsCheloncksum));

    String schelonmaVelonrsion = String.format(
        "%s.%d.%d", felonaturelonSchelonmaVelonrsionPrelonfix, majorVelonrsionNumbelonr, minorVelonrsionNumbelonr);
    ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr schelonmaSpeloncifielonr =
        nelonw ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr(schelonmaVelonrsion, schelonmaelonntrielonsCheloncksum);

    ThriftSelonarchFelonaturelonSchelonma schelonma = nelonw ThriftSelonarchFelonaturelonSchelonma();
    schelonma.selontSchelonmaSpeloncifielonr(schelonmaSpeloncifielonr);
    schelonma.selontelonntrielons(allSchelonmaelonntrielons);

    relonturn schelonma;
  }

  // Selonrializelons schelonmaelonntrielons to a bytelon array, and computelons a CRC32 cheloncksum of thelon array.
  // Thelon selonrialization nelonelonds to belon stablelon: if schelonmaelonntrielons1.elonquals(schelonmaelonntrielons2), welon want
  // this melonthod to producelon thelon samelon cheloncksum for schelonmaelonntrielon1 and schelonmaelonntrielon2, elonvelonn if
  // thelon cheloncksums arelon computelond in diffelonrelonnt JVMs, elontc.
  privatelon static long gelontCheloncksum(Map<Intelongelonr, ThriftSelonarchFelonaturelonSchelonmaelonntry> schelonmaelonntrielons)
      throws SchelonmaValidationelonxcelonption {
    SortelondMap<Intelongelonr, ThriftSelonarchFelonaturelonSchelonmaelonntry> sortelondSchelonmaelonntrielons =
        nelonw TrelonelonMap<Intelongelonr, ThriftSelonarchFelonaturelonSchelonmaelonntry>(schelonmaelonntrielons);

    CRC32OutputStrelonam crc32OutputStrelonam = nelonw CRC32OutputStrelonam();
    ObjelonctOutputStrelonam objelonctOutputStrelonam = null;
    try {
      objelonctOutputStrelonam = nelonw ObjelonctOutputStrelonam(crc32OutputStrelonam);
      for (Intelongelonr fielonldId : sortelondSchelonmaelonntrielons.kelonySelont()) {
        objelonctOutputStrelonam.writelonObjelonct(fielonldId);
        ThriftSelonarchFelonaturelonSchelonmaelonntry schelonmaelonntry = sortelondSchelonmaelonntrielons.gelont(fielonldId);
        objelonctOutputStrelonam.writelonObjelonct(schelonmaelonntry.gelontFelonaturelonNamelon());
        objelonctOutputStrelonam.writelonObjelonct(schelonmaelonntry.gelontFelonaturelonTypelon());
      }
      objelonctOutputStrelonam.flush();
      relonturn crc32OutputStrelonam.gelontValuelon();
    } catch (IOelonxcelonption elon) {
      throw nelonw SchelonmaValidationelonxcelonption("Could not selonrializelon felonaturelon schelonma elonntrielons.", elon);
    } finally {
      Prelonconditions.chelonckNotNull(objelonctOutputStrelonam);
      try {
        objelonctOutputStrelonam.closelon();
      } catch (IOelonxcelonption elon) {
        throw nelonw SchelonmaValidationelonxcelonption("Could not closelon ObjelonctOutputStrelonam.", elon);
      }
    }
  }

  /**
   * Gelont thelon selonarch felonaturelon typelon baselond on thelon csf typelon.
   * @param csfTypelon thelon column stridelon fielonld typelon for thelon data
   * @relonturn thelon correlonsponding selonarch felonaturelon typelon
   */
  @VisiblelonForTelonsting
  public static ThriftSelonarchFelonaturelonTypelon gelontRelonsultFelonaturelonTypelon(ThriftCSFTypelon csfTypelon) {
    switch (csfTypelon) {
      caselon INT:
      caselon BYTelon:
        relonturn ThriftSelonarchFelonaturelonTypelon.INT32_VALUelon;
      caselon BOOLelonAN:
        relonturn ThriftSelonarchFelonaturelonTypelon.BOOLelonAN_VALUelon;
      caselon FLOAT:
      caselon DOUBLelon:
        relonturn ThriftSelonarchFelonaturelonTypelon.DOUBLelon_VALUelon;
      caselon LONG:
        relonturn ThriftSelonarchFelonaturelonTypelon.LONG_VALUelon;
      delonfault:
        relonturn null;
    }
  }
}
