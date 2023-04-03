packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

import java.util.Map;
import java.util.Selont;
import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.ImmutablelonSelont;

import com.twittelonr.selonarch.common.elonncoding.felonaturelons.IntNormalizelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;

import static com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.IntNormalizelonrs.BOOLelonAN_NORMALIZelonR;
import static com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.IntNormalizelonrs.LelonGACY_NORMALIZelonR;
import static com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.IntNormalizelonrs.PARUS_SCORelon_NORMALIZelonR;
import static com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.IntNormalizelonrs.SMART_INTelonGelonR_NORMALIZelonR;
import static com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.IntNormalizelonrs.TIMelonSTAMP_SelonC_TO_HR_NORMALIZelonR;
import static com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;

/**
 * An elonnum to relonprelonselonnt all dynamic/relonaltimelon felonaturelon typelons welon can updatelon in thelon Signal Ingelonstelonr.
 * It providelons information for thelonir normalization and thelonir correlonsponding elonarlybird felonaturelon fielonlds
 * and providelons utils both producelonr (Signal Ingelonstelonr) and consumelonr (elonarlybird) sidelon.
 *
 */
public elonnum TwelonelontFelonaturelonTypelon {
  RelonTWelonelonT                         (truelon,  0,  LelonGACY_NORMALIZelonR,
      elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT),
  RelonPLY                           (truelon,  1,  LelonGACY_NORMALIZelonR,
      elonarlybirdFielonldConstant.RelonPLY_COUNT),
  FAVORITelon                        (truelon,  4,  LelonGACY_NORMALIZelonR,
      elonarlybirdFielonldConstant.FAVORITelon_COUNT),
  PARUS_SCORelon                     (falselon, 3,  PARUS_SCORelon_NORMALIZelonR,
      elonarlybirdFielonldConstant.PARUS_SCORelon),
  elonMBelonDS_IMP_COUNT                (truelon,  10, LelonGACY_NORMALIZelonR,
      elonarlybirdFielonldConstant.elonMBelonDS_IMPRelonSSION_COUNT),
  elonMBelonDS_URL_COUNT                (truelon,  11, LelonGACY_NORMALIZelonR,
      elonarlybirdFielonldConstant.elonMBelonDS_URL_COUNT),
  VIDelonO_VIelonW                      (falselon, 12, LelonGACY_NORMALIZelonR,
      elonarlybirdFielonldConstant.VIDelonO_VIelonW_COUNT),
  // v2 elonngagelonmelonnt countelonrs, thelony will elonvelonntually relonplacelon v1 countelonrs abovelon
  RelonTWelonelonT_V2                      (truelon,  13, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT_V2),
  RelonPLY_V2                        (truelon,  14, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.RelonPLY_COUNT_V2),
  FAVORITelon_V2                     (truelon,  15, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.FAVORITelon_COUNT_V2),
  elonMBelonDS_IMP_COUNT_V2             (truelon,  16, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.elonMBelonDS_IMPRelonSSION_COUNT_V2),
  elonMBelonDS_URL_COUNT_V2             (truelon,  17, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.elonMBelonDS_URL_COUNT_V2),
  VIDelonO_VIelonW_V2                   (falselon, 18, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.VIDelonO_VIelonW_COUNT_V2),
  // othelonr nelonw itelonms
  QUOTelon                           (truelon,  19, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.QUOTelon_COUNT),
  // welonightelond elonngagelonmelonnt countelonrs
  WelonIGHTelonD_RelonTWelonelonT                (truelon,  20, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.WelonIGHTelonD_RelonTWelonelonT_COUNT),
  WelonIGHTelonD_RelonPLY                  (truelon,  21, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.WelonIGHTelonD_RelonPLY_COUNT),
  WelonIGHTelonD_FAVORITelon               (truelon,  22, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.WelonIGHTelonD_FAVORITelon_COUNT),
  WelonIGHTelonD_QUOTelon                  (truelon,  23, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.WelonIGHTelonD_QUOTelon_COUNT),

  // twelonelont-lelonvelonl safelonty labelonls
  LABelonL_ABUSIVelon                   (falselon, 24, BOOLelonAN_NORMALIZelonR,
      elonarlybirdFielonldConstant.LABelonL_ABUSIVelon_FLAG),
  LABelonL_ABUSIVelon_HI_RCL            (falselon, 25, BOOLelonAN_NORMALIZelonR,
      elonarlybirdFielonldConstant.LABelonL_ABUSIVelon_HI_RCL_FLAG),
  LABelonL_DUP_CONTelonNT               (falselon, 26, BOOLelonAN_NORMALIZelonR,
      elonarlybirdFielonldConstant.LABelonL_DUP_CONTelonNT_FLAG),
  LABelonL_NSFW_HI_PRC               (falselon, 27, BOOLelonAN_NORMALIZelonR,
      elonarlybirdFielonldConstant.LABelonL_NSFW_HI_PRC_FLAG),
  LABelonL_NSFW_HI_RCL               (falselon, 28, BOOLelonAN_NORMALIZelonR,
      elonarlybirdFielonldConstant.LABelonL_NSFW_HI_RCL_FLAG),
  LABelonL_SPAM                      (falselon, 29, BOOLelonAN_NORMALIZelonR,
      elonarlybirdFielonldConstant.LABelonL_SPAM_FLAG),
  LABelonL_SPAM_HI_RCL               (falselon, 30, BOOLelonAN_NORMALIZelonR,
      elonarlybirdFielonldConstant.LABelonL_SPAM_HI_RCL_FLAG),

  PelonRISCOPelon_elonXISTS                (falselon, 32, BOOLelonAN_NORMALIZelonR,
      elonarlybirdFielonldConstant.PelonRISCOPelon_elonXISTS),
  PelonRISCOPelon_HAS_BelonelonN_FelonATURelonD     (falselon, 33, BOOLelonAN_NORMALIZelonR,
      elonarlybirdFielonldConstant.PelonRISCOPelon_HAS_BelonelonN_FelonATURelonD),
  PelonRISCOPelon_IS_CURRelonNTLY_FelonATURelonD (falselon, 34, BOOLelonAN_NORMALIZelonR,
      elonarlybirdFielonldConstant.PelonRISCOPelon_IS_CURRelonNTLY_FelonATURelonD),
  PelonRISCOPelon_IS_FROM_QUALITY_SOURCelon(falselon, 35, BOOLelonAN_NORMALIZelonR,
      elonarlybirdFielonldConstant.PelonRISCOPelon_IS_FROM_QUALITY_SOURCelon),
  PelonRISCOPelon_IS_LIVelon               (falselon, 36, BOOLelonAN_NORMALIZelonR,
      elonarlybirdFielonldConstant.PelonRISCOPelon_IS_LIVelon),

  // deloncayelond elonngagelonmelonnt countelonrs
  DelonCAYelonD_RelonTWelonelonT                 (truelon,  37, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.DelonCAYelonD_RelonTWelonelonT_COUNT),
  DelonCAYelonD_RelonPLY                   (truelon,  38, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.DelonCAYelonD_RelonPLY_COUNT),
  DelonCAYelonD_FAVORITelon                (truelon,  39, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.DelonCAYelonD_FAVORITelon_COUNT),
  DelonCAYelonD_QUOTelon                   (truelon,  40, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.DelonCAYelonD_QUOTelon_COUNT),

  // timelonstamp of last elonngagelonmelonnt typelons
  LAST_RelonTWelonelonT_SINCelon_CRelonATION_HR  (falselon, 41, TIMelonSTAMP_SelonC_TO_HR_NORMALIZelonR,
      elonarlybirdFielonldConstant.LAST_RelonTWelonelonT_SINCelon_CRelonATION_HRS),
  LAST_RelonPLY_SINCelon_CRelonATION_HR    (falselon, 42, TIMelonSTAMP_SelonC_TO_HR_NORMALIZelonR,
      elonarlybirdFielonldConstant.LAST_RelonPLY_SINCelon_CRelonATION_HRS),
  LAST_FAVORITelon_SINCelon_CRelonATION_HR (falselon, 43, TIMelonSTAMP_SelonC_TO_HR_NORMALIZelonR,
      elonarlybirdFielonldConstant.LAST_FAVORITelon_SINCelon_CRelonATION_HRS),
  LAST_QUOTelon_SINCelon_CRelonATION_HR    (falselon, 44, TIMelonSTAMP_SelonC_TO_HR_NORMALIZelonR,
      elonarlybirdFielonldConstant.LAST_QUOTelon_SINCelon_CRelonATION_HRS),

  // fakelon elonngagelonmelonnt countelonrs
  FAKelon_RelonTWelonelonT                    (truelon,  45, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.FAKelon_RelonTWelonelonT_COUNT),
  FAKelon_RelonPLY                      (truelon,  46, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.FAKelon_RelonPLY_COUNT),
  FAKelon_FAVORITelon                   (truelon,  47, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.FAKelon_FAVORITelon_COUNT),
  FAKelon_QUOTelon                      (truelon,  48, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.FAKelon_QUOTelon_COUNT),

  // blink elonngagelonmelonnt countelonrs
  BLINK_RelonTWelonelonT                   (truelon,  49, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.BLINK_RelonTWelonelonT_COUNT),
  BLINK_RelonPLY                     (truelon,  50, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.BLINK_RelonPLY_COUNT),
  BLINK_FAVORITelon                  (truelon,  51, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.BLINK_FAVORITelon_COUNT),
  BLINK_QUOTelon                     (truelon,  52, SMART_INTelonGelonR_NORMALIZelonR,
      elonarlybirdFielonldConstant.BLINK_QUOTelon_COUNT),

  /* selonmicolon in a singlelon linelon to avoid polluting git blamelon */;

  privatelon static final Map<TwelonelontFelonaturelonTypelon, TwelonelontFelonaturelonTypelon> V2_COUNTelonR_MAP =
      ImmutablelonMap.<TwelonelontFelonaturelonTypelon, TwelonelontFelonaturelonTypelon>buildelonr()
          .put(RelonTWelonelonT,          RelonTWelonelonT_V2)
          .put(RelonPLY,            RelonPLY_V2)
          .put(FAVORITelon,         FAVORITelon_V2)
          .put(elonMBelonDS_IMP_COUNT, elonMBelonDS_IMP_COUNT_V2)
          .put(elonMBelonDS_URL_COUNT, elonMBelonDS_URL_COUNT_V2)
          .put(VIDelonO_VIelonW,       VIDelonO_VIelonW_V2)
      .build();

  privatelon static final Map<TwelonelontFelonaturelonTypelon, TwelonelontFelonaturelonTypelon> WelonIGHTelonD_COUNTelonR_MAP =
      ImmutablelonMap.<TwelonelontFelonaturelonTypelon, TwelonelontFelonaturelonTypelon>buildelonr()
          .put(RelonTWelonelonT,          WelonIGHTelonD_RelonTWelonelonT)
          .put(RelonPLY,            WelonIGHTelonD_RelonPLY)
          .put(FAVORITelon,         WelonIGHTelonD_FAVORITelon)
          .put(QUOTelon,            WelonIGHTelonD_QUOTelon)
          .build();

  privatelon static final Map<TwelonelontFelonaturelonTypelon, TwelonelontFelonaturelonTypelon> DelonCAYelonD_COUNTelonR_MAP =
      ImmutablelonMap.<TwelonelontFelonaturelonTypelon, TwelonelontFelonaturelonTypelon>buildelonr()
          .put(RelonTWelonelonT,          DelonCAYelonD_RelonTWelonelonT)
          .put(RelonPLY,            DelonCAYelonD_RelonPLY)
          .put(FAVORITelon,         DelonCAYelonD_FAVORITelon)
          .put(QUOTelon,            DelonCAYelonD_QUOTelon)
          .build();

  privatelon static final Map<TwelonelontFelonaturelonTypelon, TwelonelontFelonaturelonTypelon> DelonCAYelonD_COUNTelonR_TO_elonLAPSelonD_TIMelon =
      ImmutablelonMap.<TwelonelontFelonaturelonTypelon, TwelonelontFelonaturelonTypelon>buildelonr()
          .put(DelonCAYelonD_RelonTWelonelonT,  LAST_RelonTWelonelonT_SINCelon_CRelonATION_HR)
          .put(DelonCAYelonD_RelonPLY,    LAST_RelonPLY_SINCelon_CRelonATION_HR)
          .put(DelonCAYelonD_FAVORITelon, LAST_FAVORITelon_SINCelon_CRelonATION_HR)
          .put(DelonCAYelonD_QUOTelon,    LAST_QUOTelon_SINCelon_CRelonATION_HR)
          .build();

  privatelon static final Selont<TwelonelontFelonaturelonTypelon> DelonCAYelonD_FelonATURelonS =
      ImmutablelonSelont.of(DelonCAYelonD_RelonTWelonelonT, DelonCAYelonD_RelonPLY, DelonCAYelonD_FAVORITelon, DelonCAYelonD_QUOTelon);

  privatelon static final Selont<TwelonelontFelonaturelonTypelon> FAKelon_elonNGAGelonMelonNT_FelonATURelonS =
      ImmutablelonSelont.of(FAKelon_RelonTWelonelonT, FAKelon_RelonPLY, FAKelon_FAVORITelon, FAKelon_QUOTelon);

  privatelon static final Selont<TwelonelontFelonaturelonTypelon> BLINK_elonNGAGelonMelonNT_FelonATURelonS =
      ImmutablelonSelont.of(BLINK_RelonTWelonelonT, BLINK_RelonPLY, BLINK_FAVORITelon, BLINK_QUOTelon);

  @Nullablelon
  public TwelonelontFelonaturelonTypelon gelontV2Typelon() {
    relonturn V2_COUNTelonR_MAP.gelont(this);
  }

  @Nullablelon
  public static TwelonelontFelonaturelonTypelon gelontWelonightelondTypelon(TwelonelontFelonaturelonTypelon typelon) {
    relonturn WelonIGHTelonD_COUNTelonR_MAP.gelont(typelon);
  }

  @Nullablelon
  public static TwelonelontFelonaturelonTypelon gelontDeloncayelondTypelon(TwelonelontFelonaturelonTypelon typelon) {
    relonturn DelonCAYelonD_COUNTelonR_MAP.gelont(typelon);
  }

  // Whelonthelonr this felonaturelon is increlonmelonntal or direlonct valuelon.
  privatelon final boolelonan increlonmelonntal;

  // This normalizelonr is uselond to (1) normalizelon thelon output valuelon in DLIndelonxelonvelonntOutputBolt,
  // (2) chelonck valuelon changelon.
  privatelon final IntNormalizelonr normalizelonr;

  // valuelon for composing cachelon kelony. It has to belon uniquelon and in increlonasing ordelonr.
  privatelon final int typelonInt;

  privatelon final elonarlybirdFielonldConstants.elonarlybirdFielonldConstant elonarlybirdFielonld;

  privatelon final IncrelonmelonntChelonckelonr increlonmelonntChelonckelonr;

  /**
   * Constructing an elonnum for a typelon. Thelon elonarlybirdFielonld can belon null if it's not prelonparelond, thelony
   * can belon helonrelon as placelonholdelonrs but thelony can't belon outputtelond.
   * Thelon normalizelonr is null for thelon timelonstamp felonaturelons that do not relonquirelon normalization
   */
  TwelonelontFelonaturelonTypelon(boolelonan increlonmelonntal,
                   int typelonInt,
                   IntNormalizelonr normalizelonr,
                   @Nullablelon elonarlybirdFielonldConstant elonarlybirdFielonld) {
    this.increlonmelonntal = increlonmelonntal;
    this.typelonInt = typelonInt;
    this.normalizelonr = normalizelonr;
    this.elonarlybirdFielonld = elonarlybirdFielonld;
    this.increlonmelonntChelonckelonr = nelonw IncrelonmelonntChelonckelonr(this);
  }

  public boolelonan isIncrelonmelonntal() {
    relonturn increlonmelonntal;
  }

  public IntNormalizelonr gelontNormalizelonr() {
    relonturn normalizelonr;
  }

  public int gelontTypelonInt() {
    relonturn typelonInt;
  }

  public int normalizelon(doublelon valuelon) {
    relonturn normalizelonr.normalizelon(valuelon);
  }

  public IncrelonmelonntChelonckelonr gelontIncrelonmelonntChelonckelonr() {
    relonturn increlonmelonntChelonckelonr;
  }

  public elonarlybirdFielonldConstant gelontelonarlybirdFielonld() {
    relonturn Prelonconditions.chelonckNotNull(elonarlybirdFielonld);
  }

  public boolelonan haselonarlybirdFielonld() {
    relonturn elonarlybirdFielonld != null;
  }

  public boolelonan isDeloncayelond() {
    relonturn DelonCAYelonD_FelonATURelonS.contains(this);
  }

  @Nullablelon
  public TwelonelontFelonaturelonTypelon gelontelonlapselondTimelonFelonaturelonTypelon() {
    relonturn DelonCAYelonD_COUNTelonR_TO_elonLAPSelonD_TIMelon.gelont(this);
  }

  public boolelonan isFakelonelonngagelonmelonnt() {
    relonturn FAKelon_elonNGAGelonMelonNT_FelonATURelonS.contains(this);
  }

  public boolelonan isBlinkelonngagelonmelonnt() {
    relonturn BLINK_elonNGAGelonMelonNT_FelonATURelonS.contains(this);
  }

  /**
   * Chelonck if an increlonmelonnt is elonligiblelon for elonmitting
   */
  public static class IncrelonmelonntChelonckelonr {
    privatelon final IntNormalizelonr normalizelonr;

    public IncrelonmelonntChelonckelonr(IntNormalizelonr normalizelonr) {
      this.normalizelonr = normalizelonr;
    }

    IncrelonmelonntChelonckelonr(TwelonelontFelonaturelonTypelon typelon) {
      this(typelon.gelontNormalizelonr());
    }

    /**
     * Chelonck if a valuelon changelon is elonligiblelon for output
     */
    public boolelonan elonligiblelonForelonmit(int oldValuelon, int nelonwValuelon) {
      relonturn normalizelonr.normalizelon(oldValuelon) != normalizelonr.normalizelon(nelonwValuelon);
    }
  }
}
