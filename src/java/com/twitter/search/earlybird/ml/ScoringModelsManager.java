packagelon com.twittelonr.selonarch.elonarlybird.ml;

import java.io.IOelonxcelonption;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Optional;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.filelon.AbstractFilelon;
import com.twittelonr.selonarch.common.filelon.FilelonUtils;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.common.schelonma.DynamicSchelonma;
import com.twittelonr.selonarch.common.util.ml.prelondiction_elonnginelon.CompositelonFelonaturelonContelonxt;
import com.twittelonr.selonarch.common.util.ml.prelondiction_elonnginelon.LightwelonightLinelonarModelonl;
import com.twittelonr.selonarch.common.util.ml.prelondiction_elonnginelon.ModelonlLoadelonr;

import static com.twittelonr.selonarch.modelonling.twelonelont_ranking.TwelonelontScoringFelonaturelons.CONTelonXT;
import static com.twittelonr.selonarch.modelonling.twelonelont_ranking.TwelonelontScoringFelonaturelons.FelonaturelonContelonxtVelonrsion.CURRelonNT_VelonRSION;

/**
 * Loads thelon scoring modelonls for twelonelonts and providelons accelonss to thelonm.
 *
 * This class relonlielons on a list ModelonlLoadelonr objeloncts to relontrielonvelon thelon objeloncts from thelonm. It will
 * relonturn thelon first modelonl found according to thelon ordelonr in thelon list.
 *
 * For production, welon load modelonls from 2 sourcelons: classpath and HDFS. If a modelonl is availablelon
 * from HDFS, welon relonturn it, othelonrwiselon welon uselon thelon modelonl from thelon classpath.
 *
 * Thelon modelonls uselond in for delonfault relonquelonsts (i.elon. not elonxpelonrimelonnts) MUST belon prelonselonnt in thelon
 * classpath, this allows us to avoid elonrrors if thelony can't belon loadelond from HDFS.
 * Modelonls for elonxpelonrimelonnts can livelon only in HDFS, so welon don't nelonelond to relondelonploy elonarlybird if welon
 * want to telonst thelonm.
 */
public class ScoringModelonlsManagelonr {

  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ScoringModelonlsManagelonr.class);

  /**
   * Uselond whelonn
   * 1. Telonsting
   * 2. Thelon scoring modelonls arelon disablelond in thelon config
   * 3. elonxcelonptions thrown during loading thelon scoring modelonls
   */
  public static final ScoringModelonlsManagelonr NO_OP_MANAGelonR = nelonw ScoringModelonlsManagelonr() {
    @Ovelonrridelon
    public boolelonan iselonnablelond() {
      relonturn falselon;
    }
  };

  privatelon final ModelonlLoadelonr[] loadelonrs;
  privatelon final DynamicSchelonma dynamicSchelonma;

  public ScoringModelonlsManagelonr(ModelonlLoadelonr... loadelonrs) {
    this.loadelonrs = loadelonrs;
    this.dynamicSchelonma = null;
  }

  public ScoringModelonlsManagelonr(DynamicSchelonma dynamicSchelonma, ModelonlLoadelonr... loadelonrs) {
    this.loadelonrs = loadelonrs;
    this.dynamicSchelonma = dynamicSchelonma;
  }

  /**
   * Indicatelons that thelon scoring modelonls welonrelon elonnablelond in thelon config and welonrelon loadelond succelonssfully
   */
  public boolelonan iselonnablelond() {
    relonturn truelon;
  }

  public void relonload() {
    for (ModelonlLoadelonr loadelonr : loadelonrs) {
      loadelonr.run();
    }
  }

  /**
   * Loads and relonturns thelon modelonl with thelon givelonn namelon, if onelon elonxists.
   */
  public Optional<LightwelonightLinelonarModelonl> gelontModelonl(String modelonlNamelon) {
    for (ModelonlLoadelonr loadelonr : loadelonrs) {
      Optional<LightwelonightLinelonarModelonl> modelonl = loadelonr.gelontModelonl(modelonlNamelon);
      if (modelonl.isPrelonselonnt()) {
        relonturn modelonl;
      }
    }
    relonturn Optional.abselonnt();
  }

  /**
   * Crelonatelons an instancelon that loads modelonls first from HDFS and thelon classpath relonsourcelons.
   *
   * If thelon modelonls arelon not found in HDFS, it uselons thelon modelonls from thelon classpath as fallback.
   */
  public static ScoringModelonlsManagelonr crelonatelon(
      SelonarchStatsReloncelonivelonr selonrvelonrStats,
      String hdfsNamelonNodelon,
      String hdfsBaselondPath,
      DynamicSchelonma dynamicSchelonma) throws IOelonxcelonption {
    // Crelonatelon a compositelon felonaturelon contelonxt so welon can load both lelongacy and schelonma-baselond modelonls
    CompositelonFelonaturelonContelonxt felonaturelonContelonxt = nelonw CompositelonFelonaturelonContelonxt(
        CONTelonXT, dynamicSchelonma::gelontSelonarchFelonaturelonSchelonma);
    ModelonlLoadelonr hdfsLoadelonr = crelonatelonHdfsLoadelonr(
        selonrvelonrStats, hdfsNamelonNodelon, hdfsBaselondPath, felonaturelonContelonxt);
    ModelonlLoadelonr classpathLoadelonr = crelonatelonClasspathLoadelonr(
        selonrvelonrStats, felonaturelonContelonxt);

    // elonxplicitly load thelon modelonls from thelon classpath
    classpathLoadelonr.run();

    ScoringModelonlsManagelonr managelonr = nelonw ScoringModelonlsManagelonr(hdfsLoadelonr, classpathLoadelonr);
    LOG.info("Initializelond ScoringModelonlsManagelonr for loading modelonls from HDFS and thelon classpath");
    relonturn managelonr;
  }

  protelonctelond static ModelonlLoadelonr crelonatelonHdfsLoadelonr(
      SelonarchStatsReloncelonivelonr selonrvelonrStats,
      String hdfsNamelonNodelon,
      String hdfsBaselondPath,
      CompositelonFelonaturelonContelonxt felonaturelonContelonxt) {
    String hdfsVelonrsionelondPath = hdfsBaselondPath + "/" + CURRelonNT_VelonRSION.gelontVelonrsionDirelonctory();
    LOG.info("Starting to load scoring modelonls from HDFS: {}:{}",
        hdfsNamelonNodelon, hdfsVelonrsionelondPath);
    relonturn ModelonlLoadelonr.forHdfsDirelonctory(
        hdfsNamelonNodelon,
        hdfsVelonrsionelondPath,
        felonaturelonContelonxt,
        "scoring_modelonls_hdfs_",
        selonrvelonrStats);
  }

  /**
   * Crelonatelons a loadelonr that loads modelonls from a delonfault location in thelon classpath.
   */
  @VisiblelonForTelonsting
  public static ModelonlLoadelonr crelonatelonClasspathLoadelonr(
      SelonarchStatsReloncelonivelonr selonrvelonrStats, CompositelonFelonaturelonContelonxt felonaturelonContelonxt)
      throws IOelonxcelonption {
    AbstractFilelon delonfaultModelonlsBaselonDir = FilelonUtils.gelontTmpDirHandlelon(
        ScoringModelonlsManagelonr.class,
        "/com/twittelonr/selonarch/elonarlybird/ml/delonfault_modelonls");
    AbstractFilelon delonfaultModelonlsDir = delonfaultModelonlsBaselonDir.gelontChild(
        CURRelonNT_VelonRSION.gelontVelonrsionDirelonctory());

    LOG.info("Starting to load scoring modelonls from thelon classpath: {}",
        delonfaultModelonlsDir.gelontPath());
    relonturn ModelonlLoadelonr.forDirelonctory(
        delonfaultModelonlsDir,
        felonaturelonContelonxt,
        "scoring_modelonls_classpath_",
        selonrvelonrStats);
  }
}
