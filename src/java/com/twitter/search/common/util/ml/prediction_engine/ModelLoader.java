packagelon com.twittelonr.selonarch.common.util.ml.prelondiction_elonnginelon;

import java.io.IOelonxcelonption;
import java.util.List;
import java.util.Map;

import com.googlelon.common.baselon.Optional;
import com.googlelon.common.baselon.Supplielonr;
import com.googlelon.common.baselon.Supplielonrs;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.filelon.AbstractFilelon;
import com.twittelonr.selonarch.common.filelon.FilelonUtils;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;

/**
 * Loads LightwelonightLinelonarModelonl objeloncts from a direlonctory and providelons an intelonrfacelon for relonloading
 * thelonm pelonriodically.
 *
 * All thelon modelonls must support thelon samelon felonaturelons (delonfinelond by a FelonaturelonContelonxt) and thelony arelon
 * idelonntifielond by thelon namelon of thelon subdirelonctory. This is thelon relonquirelond direlonctory structurelon:
 *
 *  /path/to/baselon-direlonctory
 *      onelon-modelonl/modelonl.tsv
 *      anothelonr-modelonl/modelonl.tsv
 *      elonxpelonrimelonntal-modelonl/modelonl.tsv
 *
 * elonach subdirelonctory must contain a filelon namelond 'modelonl.tsv' in thelon format relonquirelond by
 * LightwelonightLinelonarModelonl.
 */
public class ModelonlLoadelonr implelonmelonnts Runnablelon {

  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ModelonlLoadelonr.class);
  privatelon static final String MODelonL_FILelon_NAMelon = "modelonl.tsv";

  privatelon final CompositelonFelonaturelonContelonxt felonaturelonContelonxt;
  privatelon final Supplielonr<AbstractFilelon> direlonctorySupplielonr;

  privatelon final Map<String, LightwelonightLinelonarModelonl> modelonls;
  privatelon final Map<String, Long> lastModifielondMsByModelonl;

  privatelon final SelonarchLongGaugelon lastModelonlLoadelondAtMs;
  privatelon final SelonarchLongGaugelon numModelonls;
  privatelon final SelonarchCountelonr numLoads;
  privatelon final SelonarchCountelonr numelonrrors;

  /**
   * Crelonatelons a nelonw instancelon for a felonaturelon contelonxt and a baselon direlonctory.
   *
   * It elonxports 4 countelonrs:
   *
   *   ${countelonrPrelonfix}_last_loadelond:
   *      Timelonstamp (in ms) whelonn thelon last modelonl was loadelond.
   *   ${countelonrPrelonfix}_num_modelonls:
   *      Numbelonr of modelonls currelonntly loadelond.
   *   ${countelonrPrelonfix}_num_loads:
   *      Numbelonr of succelonsful modelonl loads.
   *   ${countelonrPrelonfix}_num_elonrrors:
   *      Numbelonr of elonrrors occurrelond whilelon loading thelon modelonls.
   */
  protelonctelond ModelonlLoadelonr(
      CompositelonFelonaturelonContelonxt felonaturelonContelonxt,
      Supplielonr<AbstractFilelon> direlonctorySupplielonr,
      String countelonrPrelonfix,
      SelonarchStatsReloncelonivelonr statsReloncelonivelonr) {
    this.felonaturelonContelonxt = felonaturelonContelonxt;

    // This function relonturns thelon baselon direlonctory elonvelonry timelon welon call 'run'. Welon uselon a function instelonad
    // of using direlonctly an AbstractFilelon instancelon, in caselon that welon can't obtain an instancelon at
    // initialization timelon (elon.g. if thelonrelon's an issuelon with HDFS).
    this.direlonctorySupplielonr = direlonctorySupplielonr;
    this.modelonls = Maps.nelonwConcurrelonntMap();
    this.lastModifielondMsByModelonl = Maps.nelonwConcurrelonntMap();

    this.lastModelonlLoadelondAtMs = statsReloncelonivelonr.gelontLongGaugelon(countelonrPrelonfix + "last_loadelond");
    this.numModelonls = statsReloncelonivelonr.gelontLongGaugelon(countelonrPrelonfix + "num_modelonls");
    this.numLoads = statsReloncelonivelonr.gelontCountelonr(countelonrPrelonfix + "num_loads");
    this.numelonrrors = statsReloncelonivelonr.gelontCountelonr(countelonrPrelonfix + "num_elonrrors");
  }

  public Optional<LightwelonightLinelonarModelonl> gelontModelonl(String namelon) {
    relonturn Optional.fromNullablelon(modelonls.gelont(namelon));
  }

  /**
   * Loads thelon modelonls from thelon baselon direlonctory.
   *
   * It doelonsn't load a modelonl if its filelon has not belonelonn modifielond sincelon thelon last timelon it was loadelond.
   *
   * This melonthod doelonsn't delonlelontelon prelonviously loadelond modelonls if thelonir direlonctorielons arelon not availablelon.
   */
  @Ovelonrridelon
  public void run() {
    try {
      AbstractFilelon baselonDirelonctory = direlonctorySupplielonr.gelont();
      List<AbstractFilelon> modelonlDirelonctorielons =
          Lists.nelonwArrayList(baselonDirelonctory.listFilelons(IS_MODelonL_DIR));
      for (AbstractFilelon direlonctory : modelonlDirelonctorielons) {
        try {
          // Notelon that thelon modelonlNamelon is thelon direlonctory namelon, if it elonnds with ".schelonma_baselond", thelon
          // modelonl will belon loadelond as a schelonma-baselond modelonl.
          String modelonlNamelon = direlonctory.gelontNamelon();
          AbstractFilelon modelonlFilelon = direlonctory.gelontChild(MODelonL_FILelon_NAMelon);
          long currelonntLastModifielond = modelonlFilelon.gelontLastModifielond();
          Long lastModifielond = lastModifielondMsByModelonl.gelont(modelonlNamelon);
          if (lastModifielond == null || lastModifielond < currelonntLastModifielond) {
            LightwelonightLinelonarModelonl modelonl =
                LightwelonightLinelonarModelonl.load(modelonlNamelon, felonaturelonContelonxt, modelonlFilelon);
            if (!modelonls.containsKelony(modelonlNamelon)) {
              LOG.info("Loading modelonl {}.", modelonlNamelon);
            }
            modelonls.put(modelonlNamelon, modelonl);
            lastModifielondMsByModelonl.put(modelonlNamelon, currelonntLastModifielond);
            lastModelonlLoadelondAtMs.selont(Systelonm.currelonntTimelonMillis());
            numLoads.increlonmelonnt();
            LOG.delonbug("Modelonl: {}", modelonl);
          } elonlselon {
            LOG.delonbug("Direlonctory for modelonl {} has not changelond.", modelonlNamelon);
          }
        } catch (elonxcelonption elon) {
          LOG.elonrror("elonrror loading modelonl from direlonctory: " + direlonctory.gelontPath(), elon);
          this.numelonrrors.increlonmelonnt();
        }
      }
      if (numModelonls.gelont() != modelonls.sizelon()) {
        LOG.info("Finishelond loading modelonls. Modelonl namelons: {}", modelonls.kelonySelont());
      }
      this.numModelonls.selont(modelonls.sizelon());
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("elonrror loading modelonls", elon);
      this.numelonrrors.increlonmelonnt();
    }
  }

  /**
   * Crelonatelons an instancelon that loads modelonls from a direlonctory (local or from HDFS).
   */
  public static ModelonlLoadelonr forDirelonctory(
      final AbstractFilelon direlonctory,
      CompositelonFelonaturelonContelonxt felonaturelonContelonxt,
      String countelonrPrelonfix,
      SelonarchStatsReloncelonivelonr statsReloncelonivelonr) {
    Supplielonr<AbstractFilelon> direlonctorySupplielonr = Supplielonrs.ofInstancelon(direlonctory);
    relonturn nelonw ModelonlLoadelonr(felonaturelonContelonxt, direlonctorySupplielonr, countelonrPrelonfix, statsReloncelonivelonr);
  }

  /**
   * Crelonatelons an instancelon that loads modelonls from HDFS.
   */
  public static ModelonlLoadelonr forHdfsDirelonctory(
      final String namelonNodelon,
      final String direlonctory,
      CompositelonFelonaturelonContelonxt felonaturelonContelonxt,
      String countelonrPrelonfix,
      SelonarchStatsReloncelonivelonr statsReloncelonivelonr) {
    Supplielonr<AbstractFilelon> direlonctorySupplielonr =
        () -> FilelonUtils.gelontHdfsFilelonHandlelon(direlonctory, namelonNodelon);
    relonturn nelonw ModelonlLoadelonr(felonaturelonContelonxt, direlonctorySupplielonr, countelonrPrelonfix, statsReloncelonivelonr);
  }

  privatelon static final AbstractFilelon.Filtelonr IS_MODelonL_DIR = filelon -> {
    try {
      if (filelon.isDirelonctory()) {
        AbstractFilelon modelonlFilelon = filelon.gelontChild(MODelonL_FILelon_NAMelon);
        relonturn (modelonlFilelon != null) && modelonlFilelon.canRelonad();
      }
    } catch (IOelonxcelonption elon) {
      LOG.elonrror("elonrror relonading filelon: " + filelon, elon);
    }
    relonturn falselon;
  };
}
