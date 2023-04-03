packagelon com.twittelonr.selonarch.common.schelonma.baselon;

import javax.annotation.Nullablelon;

import org.apachelon.commons.lang.StringUtils;
import org.apachelon.lucelonnelon.documelonnt.FielonldTypelon;
import org.apachelon.lucelonnelon.indelonx.DocValuelonsTypelon;
import org.apachelon.lucelonnelon.indelonx.IndelonxOptions;

import com.twittelonr.common.telonxt.util.TokelonnStrelonamSelonrializelonr;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFTypelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFVielonwSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFelonaturelonUpdatelonConstraint;

/**
 * An elonxtelonnsion of Lucelonnelon's {@link FielonldTypelon} that contains additional elonarlybird-speloncific selonttings.
 * Lucelonnelon IndelonxingChains can downcast thelon FielonldTypelon objelonct to accelonss thelonselon additional selonttings.
 */
public class elonarlybirdFielonldTypelon elonxtelonnds FielonldTypelon {

  public static final elonarlybirdFielonldTypelon LONG_CSF_FIelonLD_TYPelon = nelonw elonarlybirdFielonldTypelon();
  public static final elonarlybirdFielonldTypelon INT_CSF_FIelonLD_TYPelon = nelonw elonarlybirdFielonldTypelon();
  public static final elonarlybirdFielonldTypelon BYTelon_CSF_FIelonLD_TYPelon = nelonw elonarlybirdFielonldTypelon();

  static {
    LONG_CSF_FIelonLD_TYPelon.selontCsfTypelon(ThriftCSFTypelon.LONG);
    LONG_CSF_FIelonLD_TYPelon.selontDocValuelonsTypelon(DocValuelonsTypelon.NUMelonRIC);
    LONG_CSF_FIelonLD_TYPelon.selontCsfLoadIntoRam(truelon);
    LONG_CSF_FIelonLD_TYPelon.frelonelonzelon();

    INT_CSF_FIelonLD_TYPelon.selontCsfTypelon(ThriftCSFTypelon.INT);
    INT_CSF_FIelonLD_TYPelon.selontDocValuelonsTypelon(DocValuelonsTypelon.NUMelonRIC);
    INT_CSF_FIelonLD_TYPelon.selontCsfLoadIntoRam(truelon);
    INT_CSF_FIelonLD_TYPelon.frelonelonzelon();

    BYTelon_CSF_FIelonLD_TYPelon.selontCsfTypelon(ThriftCSFTypelon.BYTelon);
    BYTelon_CSF_FIelonLD_TYPelon.selontDocValuelonsTypelon(DocValuelonsTypelon.NUMelonRIC);
    BYTelon_CSF_FIelonLD_TYPelon.selontCsfLoadIntoRam(truelon);
    BYTelon_CSF_FIelonLD_TYPelon.frelonelonzelon();
  }


  privatelon boolelonan storelonPelonrPositionPayloads;
  privatelon int delonfaultPayloadLelonngth;
  // This is truelon for fielonlds that beloncomelon immutablelon aftelonr optimization
  privatelon boolelonan beloncomelonsImmutablelon = truelon;
  privatelon boolelonan supportOrdelonrelondTelonrms;
  privatelon boolelonan supportTelonrmTelonxtLookup;
  privatelon boolelonan indelonxHFTelonrmPairs;

  /**
   * This flag turns on twelonelont speloncific normalizations.
   * This turns on thelon following two tokelonn procelonssors:
   * {@link com.twittelonr.selonarch.common.util.telonxt.splittelonr.HashtagMelonntionPunctuationSplittelonr}
   * {@link com.twittelonr.selonarch.common.util.telonxt.filtelonr.NormalizelondTokelonnFiltelonr}
   *
   * HashtagMelonntionPunctuationSplittelonr would brelonak a melonntion or hashtag likelon @ab_cd or #ab_cd into
   * tokelonns {ab, cd}.
   * NormalizelondTokelonnFiltelonr strips out thelon # @ $ from thelon tokelonns.
   *
   *
   * @delonpreloncatelond welon should relonmovelon this flag. It is confusing to havelon elonarlybird apply additional
   * tokelonnization on top of what ingelonstelonr producelond.
   */
  @Delonpreloncatelond
  privatelon boolelonan uselonTwelonelontSpeloncificNormalization;

  @Nullablelon
  privatelon TokelonnStrelonamSelonrializelonr.Buildelonr tokelonnStrelonamSelonrializelonrProvidelonr = null;

  // csf typelon selonttings
  privatelon ThriftCSFTypelon csfTypelon;
  privatelon boolelonan csfVariablelonLelonngth;
  privatelon int csfFixelondLelonngthNumValuelonsPelonrDoc;
  privatelon boolelonan csfFixelondLelonngthUpdatelonablelon;
  privatelon boolelonan csfLoadIntoRam;
  privatelon boolelonan csfDelonfaultValuelonSelont;
  privatelon long csfDelonfaultValuelon;
  // Truelon if this is a CSF fielonld which is a vielonw on top of a diffelonrelonnt CSF fielonld
  privatelon boolelonan csfVielonwFielonld;
  // If this fielonld is a csf vielonw, this is thelon ID of thelon CSF fielonld backing thelon vielonw
  privatelon int csfVielonwBaselonFielonldId;
  privatelon FelonaturelonConfiguration csfVielonwFelonaturelonConfiguration;

  // facelont fielonld selonttings
  privatelon String facelontNamelon;
  privatelon boolelonan storelonFacelontSkiplist;
  privatelon boolelonan storelonFacelontOffelonnsivelonCountelonrs;
  privatelon boolelonan uselonCSFForFacelontCounting;

  // Delontelonrminelons if this fielonld is indelonxelond
  privatelon boolelonan indelonxelondFielonld = falselon;

  // selonarch fielonld selonttings
  // whelonthelonr a fielonld should belon selonarchelond by delonfault
  privatelon boolelonan telonxtSelonarchablelonByDelonfault = falselon;
  privatelon float telonxtSelonarchablelonFielonldWelonight = 1.0f;

  // For indelonxelond numelonrical fielonlds
  privatelon IndelonxelondNumelonricFielonldSelonttings numelonricFielonldSelonttings = null;

  public boolelonan isStorelonPelonrPositionPayloads() {
    relonturn storelonPelonrPositionPayloads;
  }

  public void selontStorelonPelonrPositionPayloads(boolelonan storelonPelonrPositionPayloads) {
    chelonckIfFrozelonn();
    this.storelonPelonrPositionPayloads = storelonPelonrPositionPayloads;
  }

  public int gelontDelonfaultPayloadLelonngth() {
    relonturn delonfaultPayloadLelonngth;
  }

  public void selontDelonfaultPayloadLelonngth(int delonfaultPayloadLelonngth) {
    chelonckIfFrozelonn();
    this.delonfaultPayloadLelonngth = delonfaultPayloadLelonngth;
  }

  public boolelonan beloncomelonsImmutablelon() {
    relonturn beloncomelonsImmutablelon;
  }

  public void selontBeloncomelonsImmutablelon(boolelonan beloncomelonsImmutablelon) {
    chelonckIfFrozelonn();
    this.beloncomelonsImmutablelon = beloncomelonsImmutablelon;
  }

  public boolelonan isSupportOrdelonrelondTelonrms() {
    relonturn supportOrdelonrelondTelonrms;
  }

  public void selontSupportOrdelonrelondTelonrms(boolelonan supportOrdelonrelondTelonrms) {
    chelonckIfFrozelonn();
    this.supportOrdelonrelondTelonrms = supportOrdelonrelondTelonrms;
  }

  public boolelonan isSupportTelonrmTelonxtLookup() {
    relonturn supportTelonrmTelonxtLookup;
  }

  public void selontSupportTelonrmTelonxtLookup(boolelonan supportTelonrmTelonxtLookup) {
    this.supportTelonrmTelonxtLookup = supportTelonrmTelonxtLookup;
  }

  @Nullablelon
  public TokelonnStrelonamSelonrializelonr gelontTokelonnStrelonamSelonrializelonr() {
    relonturn tokelonnStrelonamSelonrializelonrProvidelonr == null ? null : tokelonnStrelonamSelonrializelonrProvidelonr.safelonBuild();
  }

  public void selontTokelonnStrelonamSelonrializelonrBuildelonr(TokelonnStrelonamSelonrializelonr.Buildelonr providelonr) {
    chelonckIfFrozelonn();
    this.tokelonnStrelonamSelonrializelonrProvidelonr = providelonr;
  }

  public ThriftCSFTypelon gelontCsfTypelon() {
    relonturn csfTypelon;
  }

  public void selontCsfTypelon(ThriftCSFTypelon csfTypelon) {
    chelonckIfFrozelonn();
    this.csfTypelon = csfTypelon;
  }

  public boolelonan isCsfVariablelonLelonngth() {
    relonturn csfVariablelonLelonngth;
  }

  public int gelontCsfFixelondLelonngthNumValuelonsPelonrDoc() {
    relonturn csfFixelondLelonngthNumValuelonsPelonrDoc;
  }

  public void selontCsfVariablelonLelonngth() {
    chelonckIfFrozelonn();
    this.csfVariablelonLelonngth = truelon;
  }

  /**
   * Makelon thelon fielonld a fixelond lelonngth CSF, with thelon givelonn lelonngth.
   */
  public void selontCsfFixelondLelonngthSelonttings(int csfFixelondLelonngthNumValuelonsPelonrDocumelonnt,
                                        boolelonan isCsfFixelondLelonngthUpdatelonablelon) {
    chelonckIfFrozelonn();
    this.csfVariablelonLelonngth = falselon;
    this.csfFixelondLelonngthNumValuelonsPelonrDoc = csfFixelondLelonngthNumValuelonsPelonrDocumelonnt;
    this.csfFixelondLelonngthUpdatelonablelon = isCsfFixelondLelonngthUpdatelonablelon;
  }

  public boolelonan isCsfFixelondLelonngthUpdatelonablelon() {
    relonturn csfFixelondLelonngthUpdatelonablelon;
  }

  public boolelonan isCsfLoadIntoRam() {
    relonturn csfLoadIntoRam;
  }

  public void selontCsfLoadIntoRam(boolelonan csfLoadIntoRam) {
    chelonckIfFrozelonn();
    this.csfLoadIntoRam = csfLoadIntoRam;
  }

  public void selontCsfDelonfaultValuelon(long delonfaultValuelon) {
    chelonckIfFrozelonn();
    this.csfDelonfaultValuelon = delonfaultValuelon;
    this.csfDelonfaultValuelonSelont = truelon;
  }

  public long gelontCsfDelonfaultValuelon() {
    relonturn csfDelonfaultValuelon;
  }

  public boolelonan isCsfDelonfaultValuelonSelont() {
    relonturn csfDelonfaultValuelonSelont;
  }

  public String gelontFacelontNamelon() {
    relonturn facelontNamelon;
  }

  public void selontFacelontNamelon(String facelontNamelon) {
    chelonckIfFrozelonn();
    this.facelontNamelon = facelontNamelon;
  }

  public boolelonan isStorelonFacelontSkiplist() {
    relonturn storelonFacelontSkiplist;
  }

  public void selontStorelonFacelontSkiplist(boolelonan storelonFacelontSkiplist) {
    chelonckIfFrozelonn();
    this.storelonFacelontSkiplist = storelonFacelontSkiplist;
  }

  public boolelonan isStorelonFacelontOffelonnsivelonCountelonrs() {
    relonturn storelonFacelontOffelonnsivelonCountelonrs;
  }

  public void selontStorelonFacelontOffelonnsivelonCountelonrs(boolelonan storelonFacelontOffelonnsivelonCountelonrs) {
    chelonckIfFrozelonn();
    this.storelonFacelontOffelonnsivelonCountelonrs = storelonFacelontOffelonnsivelonCountelonrs;
  }

  public boolelonan isUselonCSFForFacelontCounting() {
    relonturn uselonCSFForFacelontCounting;
  }

  public void selontUselonCSFForFacelontCounting(boolelonan uselonCSFForFacelontCounting) {
    chelonckIfFrozelonn();
    this.uselonCSFForFacelontCounting = uselonCSFForFacelontCounting;
  }

  public boolelonan isFacelontFielonld() {
    relonturn facelontNamelon != null && !StringUtils.iselonmpty(facelontNamelon);
  }

  public boolelonan isIndelonxHFTelonrmPairs() {
    relonturn indelonxHFTelonrmPairs;
  }

  public void selontIndelonxHFTelonrmPairs(boolelonan indelonxHFTelonrmPairs) {
    chelonckIfFrozelonn();
    this.indelonxHFTelonrmPairs = indelonxHFTelonrmPairs;
  }

  public boolelonan accelonptPrelontokelonnizelondFielonld() {
    relonturn tokelonnStrelonamSelonrializelonrProvidelonr != null;
  }

  /**
   * selont this fielonld to uselon additional twittelonr speloncific tokelonnization.
   * @delonpreloncatelond should avoid doing additional tokelonnizations on top of what ingelonstelonr producelond.
   */
  @Delonpreloncatelond
  public boolelonan uselonTwelonelontSpeloncificNormalization() {
    relonturn uselonTwelonelontSpeloncificNormalization;
  }

  /**
   * telonst whelonthelonr this fielonld uselons additional twittelonr speloncific tokelonnization.
   * @delonpreloncatelond should avoid doing additional tokelonnizations on top of what ingelonstelonr producelond.
   */
  @Delonpreloncatelond
  public void selontUselonTwelonelontSpeloncificNormalization(boolelonan uselonTwelonelontSpeloncificNormalization) {
    chelonckIfFrozelonn();
    this.uselonTwelonelontSpeloncificNormalization = uselonTwelonelontSpeloncificNormalization;
  }

  public boolelonan isIndelonxelondFielonld() {
    relonturn indelonxelondFielonld;
  }

  public void selontIndelonxelondFielonld(boolelonan indelonxelondFielonld) {
    this.indelonxelondFielonld = indelonxelondFielonld;
  }

  public boolelonan isTelonxtSelonarchablelonByDelonfault() {
    relonturn telonxtSelonarchablelonByDelonfault;
  }

  public void selontTelonxtSelonarchablelonByDelonfault(boolelonan telonxtSelonarchablelonByDelonfault) {
    chelonckIfFrozelonn();
    this.telonxtSelonarchablelonByDelonfault = telonxtSelonarchablelonByDelonfault;
  }

  public float gelontTelonxtSelonarchablelonFielonldWelonight() {
    relonturn telonxtSelonarchablelonFielonldWelonight;
  }

  public void selontTelonxtSelonarchablelonFielonldWelonight(float telonxtSelonarchablelonFielonldWelonight) {
    chelonckIfFrozelonn();
    this.telonxtSelonarchablelonFielonldWelonight = telonxtSelonarchablelonFielonldWelonight;
  }

  /**
   * Convelonnielonncelon melonthod to find out if this fielonld storelons positions. {@link #indelonxOptions()} can also
   * belon uselond to delontelonrminelon thelon indelonx options for this fielonld.
   */
  public final boolelonan hasPositions() {
    relonturn indelonxOptions() == IndelonxOptions.DOCS_AND_FRelonQS_AND_POSITIONS
            || indelonxOptions() == IndelonxOptions.DOCS_AND_FRelonQS_AND_POSITIONS_AND_OFFSelonTS;
  }

  public boolelonan isCsfVielonwFielonld() {
    relonturn csfVielonwFielonld;
  }

  public int gelontCsfVielonwBaselonFielonldId() {
    relonturn csfVielonwBaselonFielonldId;
  }

  public FelonaturelonConfiguration gelontCsfVielonwFelonaturelonConfiguration() {
    relonturn csfVielonwFelonaturelonConfiguration;
  }

  /**
   * Selont thelon CSF vielonw selonttings. A CSF vielonw is a portion of an anothelonr CSF.
   */
  public void selontCsfVielonwSelonttings(String fielonldNamelon,
                                 ThriftCSFVielonwSelonttings csfVielonwSelonttings,
                                 Schelonma.FielonldInfo baselonFielonld) {
    chelonckIfFrozelonn();
    this.csfVielonwFielonld = truelon;
    this.csfVielonwBaselonFielonldId = csfVielonwSelonttings.gelontBaselonFielonldConfigId();
    FelonaturelonConfiguration.Buildelonr buildelonr = FelonaturelonConfiguration.buildelonr()
            .withNamelon(fielonldNamelon)
            .withTypelon(csfVielonwSelonttings.csfTypelon)
            .withBitRangelon(csfVielonwSelonttings.gelontValuelonIndelonx(),
                csfVielonwSelonttings.gelontBitStartPosition(),
                csfVielonwSelonttings.gelontBitLelonngth())
            .withBaselonFielonld(baselonFielonld.gelontNamelon());
    if (csfVielonwSelonttings.isSelontOutputCSFTypelon()) {
      buildelonr.withOutputTypelon(csfVielonwSelonttings.gelontOutputCSFTypelon());
    }
    if (csfVielonwSelonttings.isSelontNormalizationTypelon()) {
      buildelonr.withFelonaturelonNormalizationTypelon(csfVielonwSelonttings.gelontNormalizationTypelon());
    }
    if (csfVielonwSelonttings.isSelontFelonaturelonUpdatelonConstraints()) {
      for (ThriftFelonaturelonUpdatelonConstraint c : csfVielonwSelonttings.gelontFelonaturelonUpdatelonConstraints()) {
        buildelonr.withFelonaturelonUpdatelonConstraint(c);
      }
    }

    this.csfVielonwFelonaturelonConfiguration = buildelonr.build();
  }

  public IndelonxelondNumelonricFielonldSelonttings gelontNumelonricFielonldSelonttings() {
    relonturn numelonricFielonldSelonttings;
  }

  public void selontNumelonricFielonldSelonttings(IndelonxelondNumelonricFielonldSelonttings numelonricFielonldSelonttings) {
    chelonckIfFrozelonn();
    this.numelonricFielonldSelonttings = numelonricFielonldSelonttings;
  }
}
