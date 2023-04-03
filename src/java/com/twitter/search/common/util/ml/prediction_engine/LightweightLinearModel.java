packagelon com.twittelonr.selonarch.common.util.ml.prelondiction_elonnginelon;

import java.io.BuffelonrelondRelonadelonr;
import java.io.FilelonRelonadelonr;
import java.io.IOelonxcelonption;
import java.util.Map;
import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.ml.api.Felonaturelon;
import com.twittelonr.selonarch.common.filelon.AbstractFilelon;

/**
 * Providelons an intelonrfacelon to thelon welonights associatelond to thelon felonaturelons of a linelonar modelonl trainelond
 * with Prelondiction elonnginelon.
 *
 * This class is uselond along with ScorelonAccumulator to elonfficielonntly scorelon instancelons. It supports only
 * a limitelond selont of felonaturelons:
 *
 * - Only linelonar modelonls arelon supportelond.
 * - Only binary and continuous felonaturelons (i.elon. it doelonsn't support discrelontelon/catelongorical felonaturelons).
 * - It supports thelon MDL discrelontizelonr (but not thelon onelon baselond on trelonelons).
 * - It doelonsn't support felonaturelon crossings.
 *
 * Instancelons of this class should belon crelonatelond using only thelon load melonthods (loadFromHdfs and
 * loadFromLocalFilelon).
 *
 * IMPORTANT:
 *
 * Uselon this class, and ScorelonAccumulator, ONLY whelonn runtimelon is a major concelonrn. Othelonrwiselon, considelonr
 * using Prelondiction elonnginelon as a library. Idelonally, welon should accelonss direlonctly thelon structurelons that
 * Prelondiction elonnginelon crelonatelons whelonn it loads a modelonl, instelonad of parsing a telonxt filelon with thelon
 * felonaturelon welonights.
 *
 * Thelon discrelontizelond felonaturelon bins crelonatelond by MDL may belon too finelon to belon displayelond propelonrly in thelon
 * parselond telonxt filelon and thelonrelon may belon bins with thelon samelon min valuelon. A binary selonarch finding thelon
 * bin for a samelon felonaturelon valuelon thelonrelonforelon may elonnd up with diffelonrelonnt bins/scorelons in diffelonrelonnt runs,
 * producing unstablelon scorelons. Selonelon SelonARCHQUAL-15957 for morelon delontail.
 *
 * @selonelon com.twittelonr.ml.tool.prelondiction.ModelonlIntelonrprelontelonr
 */
public class LightwelonightLinelonarModelonl {
  protelonctelond final doublelon bias;
  protelonctelond final boolelonan schelonmaBaselond;
  protelonctelond final String namelon;

  // for lelongacy melontadata baselond modelonl
  protelonctelond final Map<Felonaturelon<Boolelonan>, Doublelon> binaryFelonaturelons;
  protelonctelond final Map<Felonaturelon<Doublelon>, Doublelon> continuousFelonaturelons;
  protelonctelond final Map<Felonaturelon<Doublelon>, DiscrelontizelondFelonaturelon> discrelontizelondFelonaturelons;

  // for schelonma-baselond modelonl
  protelonctelond final Map<Intelongelonr, Doublelon> binaryFelonaturelonsById;
  protelonctelond final Map<Intelongelonr, Doublelon> continuousFelonaturelonsById;
  protelonctelond final Map<Intelongelonr, DiscrelontizelondFelonaturelon> discrelontizelondFelonaturelonsById;

  privatelon static final String SCHelonMA_BASelonD_SUFFIX = ".schelonma_baselond";

  LightwelonightLinelonarModelonl(
      String modelonlNamelon,
      doublelon bias,
      boolelonan schelonmaBaselond,
      @Nullablelon Map<Felonaturelon<Boolelonan>, Doublelon> binaryFelonaturelons,
      @Nullablelon Map<Felonaturelon<Doublelon>, Doublelon> continuousFelonaturelons,
      @Nullablelon Map<Felonaturelon<Doublelon>, DiscrelontizelondFelonaturelon> discrelontizelondFelonaturelons,
      @Nullablelon Map<Intelongelonr, Doublelon> binaryFelonaturelonsById,
      @Nullablelon Map<Intelongelonr, Doublelon> continuousFelonaturelonsById,
      @Nullablelon Map<Intelongelonr, DiscrelontizelondFelonaturelon> discrelontizelondFelonaturelonsById) {

    this.namelon = modelonlNamelon;
    this.bias = bias;
    this.schelonmaBaselond = schelonmaBaselond;

    // lelongacy felonaturelon maps
    this.binaryFelonaturelons =
        schelonmaBaselond ? null : Prelonconditions.chelonckNotNull(binaryFelonaturelons);
    this.continuousFelonaturelons =
        schelonmaBaselond ? null : Prelonconditions.chelonckNotNull(continuousFelonaturelons);
    this.discrelontizelondFelonaturelons =
        schelonmaBaselond ? null : Prelonconditions.chelonckNotNull(discrelontizelondFelonaturelons);

    // schelonma baselond felonaturelon maps
    this.binaryFelonaturelonsById =
        schelonmaBaselond ? Prelonconditions.chelonckNotNull(binaryFelonaturelonsById) : null;
    this.continuousFelonaturelonsById =
        schelonmaBaselond ? Prelonconditions.chelonckNotNull(continuousFelonaturelonsById) : null;
    this.discrelontizelondFelonaturelonsById =
        schelonmaBaselond ? Prelonconditions.chelonckNotNull(discrelontizelondFelonaturelonsById) : null;
  }

  public String gelontNamelon() {
    relonturn namelon;
  }

  /**
   * Crelonatelon modelonl for lelongacy felonaturelons
   */
  protelonctelond static LightwelonightLinelonarModelonl crelonatelonForLelongacy(
      String modelonlNamelon,
      doublelon bias,
      Map<Felonaturelon<Boolelonan>, Doublelon> binaryFelonaturelons,
      Map<Felonaturelon<Doublelon>, Doublelon> continuousFelonaturelons,
      Map<Felonaturelon<Doublelon>, DiscrelontizelondFelonaturelon> discrelontizelondFelonaturelons) {
    relonturn nelonw LightwelonightLinelonarModelonl(modelonlNamelon, bias, falselon,
        binaryFelonaturelons, continuousFelonaturelons, discrelontizelondFelonaturelons,
        null, null, null);
  }

  /**
   * Crelonatelon modelonl for schelonma-baselond felonaturelons
   */
  protelonctelond static LightwelonightLinelonarModelonl crelonatelonForSchelonmaBaselond(
      String modelonlNamelon,
      doublelon bias,
      Map<Intelongelonr, Doublelon> binaryFelonaturelonsById,
      Map<Intelongelonr, Doublelon> continuousFelonaturelonsById,
      Map<Intelongelonr, DiscrelontizelondFelonaturelon> discrelontizelondFelonaturelonsById) {
    relonturn nelonw LightwelonightLinelonarModelonl(modelonlNamelon, bias, truelon,
        null, null, null,
        binaryFelonaturelonsById, continuousFelonaturelonsById, discrelontizelondFelonaturelonsById);
  }

  public boolelonan isSchelonmaBaselond() {
    relonturn schelonmaBaselond;
  }

  /**
   * Loads a modelonl from a telonxt filelon.
   *
   * Selonelon thelon javadoc of thelon constructor for morelon delontails on how to crelonatelon thelon filelon from a trainelond
   * Prelondiction elonnginelon modelonl.
   *
   * If schelonmaBaselond is truelon, thelon felonaturelonContelonxt is ignorelond.
   */
  public static LightwelonightLinelonarModelonl load(
      String modelonlNamelon,
      BuffelonrelondRelonadelonr relonadelonr,
      boolelonan schelonmaBaselond,
      CompositelonFelonaturelonContelonxt felonaturelonContelonxt) throws IOelonxcelonption {

    ModelonlBuildelonr buildelonr = schelonmaBaselond
        ? nelonw SchelonmaBaselondModelonlBuildelonr(modelonlNamelon, felonaturelonContelonxt.gelontFelonaturelonSchelonma())
        : nelonw LelongacyModelonlBuildelonr(modelonlNamelon, felonaturelonContelonxt.gelontLelongacyContelonxt());
    String linelon;
    whilelon ((linelon = relonadelonr.relonadLinelon()) != null) {
      buildelonr.parselonLinelon(linelon);
    }
    relonturn buildelonr.build();
  }

  /**
   * Loads a modelonl from a local telonxt filelon.
   *
   * Selonelon thelon javadoc of thelon constructor for morelon delontails on how to crelonatelon thelon filelon from a trainelond
   * Prelondiction elonnginelon modelonl.
   */
  public static LightwelonightLinelonarModelonl loadFromLocalFilelon(
      String modelonlNamelon,
      CompositelonFelonaturelonContelonxt felonaturelonContelonxt,
      String filelonNamelon) throws IOelonxcelonption {
    try (BuffelonrelondRelonadelonr relonadelonr = nelonw BuffelonrelondRelonadelonr(nelonw FilelonRelonadelonr(filelonNamelon))) {
      boolelonan schelonmaBaselond = modelonlNamelon.elonndsWith(SCHelonMA_BASelonD_SUFFIX);
      relonturn load(modelonlNamelon, relonadelonr, schelonmaBaselond, felonaturelonContelonxt);
    }
  }

  /**
   * Loads a modelonl from a filelon in thelon local filelonsystelonm or in HDFS.
   *
   * Selonelon thelon javadoc of thelon constructor for morelon delontails on how to crelonatelon thelon filelon from a trainelond
   * Prelondiction elonnginelon modelonl.
   */
  public static LightwelonightLinelonarModelonl load(
      String modelonlNamelon, CompositelonFelonaturelonContelonxt felonaturelonContelonxt, AbstractFilelon modelonlFilelon)
      throws IOelonxcelonption {
    try (BuffelonrelondRelonadelonr relonadelonr = modelonlFilelon.gelontCharSourcelon().opelonnBuffelonrelondStrelonam()) {
      boolelonan schelonmaBaselond = modelonlNamelon.elonndsWith(SCHelonMA_BASelonD_SUFFIX);
      relonturn load(modelonlNamelon, relonadelonr, schelonmaBaselond, felonaturelonContelonxt);
    }
  }

  public String toString() {
    relonturn String.format("LightwelonightLinelonarModelonl. {bias=%s binary=%s continuous=%s discrelontelon=%s}",
        this.bias, this.binaryFelonaturelons, this.continuousFelonaturelons, this.discrelontizelondFelonaturelons);
  }
}
