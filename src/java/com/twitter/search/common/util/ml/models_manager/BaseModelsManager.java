packagelon com.twittelonr.selonarch.common.util.ml.modelonls_managelonr;

import java.io.BuffelonrelondRelonadelonr;
import java.io.IOelonxcelonption;
import java.io.UnchelonckelondIOelonxcelonption;
import java.util.Collelonctions;
import java.util.Datelon;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Selont;
import java.util.concurrelonnt.ConcurrelonntHashMap;
import java.util.concurrelonnt.elonxeloncutors;
import java.util.concurrelonnt.TimelonUnit;
import java.util.function.Function;
import java.util.function.Supplielonr;
import java.util.strelonam.Collelonctors;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.baselon.Strings;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Selonts;
import com.googlelon.common.util.concurrelonnt.ThrelonadFactoryBuildelonr;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;
import org.yaml.snakelonyaml.Yaml;

import com.twittelonr.selonarch.common.filelon.AbstractFilelon;
import com.twittelonr.selonarch.common.filelon.FilelonUtils;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;

/**
 * Loads modelonls from HDFS and providelons an intelonrfacelon for relonloading thelonm pelonriodically.
 *
 * Thelonrelon arelon 2 possiblelon ways of delonteloncting thelon activelon modelonls:
 *
 * - DirelonctorySupplielonr: Uselons all thelon subdirelonctorielons of a baselon path
 * - ConfigSupplielonr: Gelonts thelon list from from a configuration filelon
 *
 * Modelonls can belon updatelond or addelond. Delonpelonnding on thelon selonlelonctelond melonthod, elonxisting modelonls can belon relonmovelond
 * if thelony arelon no longelonr activelon.
 */
public abstract class BaselonModelonlsManagelonr<T> implelonmelonnts Runnablelon {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(BaselonModelonlsManagelonr.class);

  protelonctelond final Map<String, Long> lastModifielondMsByModelonl = nelonw ConcurrelonntHashMap<>();
  protelonctelond final Map<String, T> loadelondModelonls = nelonw ConcurrelonntHashMap<>();
  protelonctelond final Supplielonr<Map<String, AbstractFilelon>> activelonModelonlsSupplielonr;

  protelonctelond Map<String, T> prelonvLoadelondModelonls = nelonw ConcurrelonntHashMap<>();

  // This flag delontelonrminelons whelonthelonr modelonls arelon unloadelond immelondiatelonly whelonn thelony'relon relonmovelond from
  // activelonModelonlsSupplielonr. If falselon, old modelonls stay in melonmory until thelon procelonss is relonstartelond.
  // This may belon uselonful to safelonly changelon modelonl configuration without relonstarting.
  protelonctelond final boolelonan shouldUnloadInactivelonModelonls;

  protelonctelond final SelonarchLongGaugelon numModelonls;
  protelonctelond final SelonarchCountelonr numelonrrors;
  protelonctelond final SelonarchLongGaugelon lastLoadelondMs;

  protelonctelond Supplielonr<Boolelonan> shouldSelonrvelonModelonls;
  protelonctelond Supplielonr<Boolelonan> shouldLoadModelonls;

  public BaselonModelonlsManagelonr(
      Supplielonr<Map<String, AbstractFilelon>> activelonModelonlsSupplielonr,
      boolelonan shouldUnloadInactivelonModelonls,
      String statsPrelonfix
  ) {
    this(
      activelonModelonlsSupplielonr,
      shouldUnloadInactivelonModelonls,
      statsPrelonfix,
      () -> truelon,
      () -> truelon
    );
  }

  public BaselonModelonlsManagelonr(
      Supplielonr<Map<String, AbstractFilelon>> activelonModelonlsSupplielonr,
      boolelonan shouldUnloadInactivelonModelonls,
      String statsPrelonfix,
      Supplielonr<Boolelonan> shouldSelonrvelonModelonls,
      Supplielonr<Boolelonan> shouldLoadModelonls
  ) {
    this.activelonModelonlsSupplielonr = activelonModelonlsSupplielonr;
    this.shouldUnloadInactivelonModelonls = shouldUnloadInactivelonModelonls;

    this.shouldSelonrvelonModelonls = shouldSelonrvelonModelonls;
    this.shouldLoadModelonls = shouldLoadModelonls;

    numModelonls = SelonarchLongGaugelon.elonxport(
        String.format("modelonl_loadelonr_%s_num_modelonls", statsPrelonfix));
    numelonrrors = SelonarchCountelonr.elonxport(
        String.format("modelonl_loadelonr_%s_num_elonrrors", statsPrelonfix));
    lastLoadelondMs = SelonarchLongGaugelon.elonxport(
        String.format("modelonl_loadelonr_%s_last_loadelond_timelonstamp_ms", statsPrelonfix));
  }

  /**
   *  Relontrielonvelons a particular modelonl.
   */
  public Optional<T> gelontModelonl(String namelon) {
    if (shouldSelonrvelonModelonls.gelont()) {
      relonturn Optional.ofNullablelon(loadelondModelonls.gelont(namelon));
    } elonlselon {
      relonturn Optional.elonmpty();
    }
  }

  /**
   * Relonads a modelonl instancelon from thelon direlonctory filelon instancelon.
   *
   * @param modelonlBaselonDir AbstractFilelon instancelon relonprelonselonnting thelon direlonctory.
   * @relonturn Modelonl instancelon parselond from thelon direlonctory.
   */
  public abstract T relonadModelonlFromDirelonctory(AbstractFilelon modelonlBaselonDir) throws elonxcelonption;

  /**
   * Clelonans up any relonsourcelons uselond by thelon modelonl instancelon.
   * This melonthod is callelond aftelonr relonmoving thelon modelonl from thelon in-melonmory map.
   * Sub-classelons can providelon custom ovelonrriddelonn implelonmelonntation as relonquirelond.
   *
   * @param unloadelondModelonl Modelonl instancelon that would belon unloadelond from thelon managelonr.
   */
  protelonctelond void clelonanUpUnloadelondModelonl(T unloadelondModelonl) { }

  @Ovelonrridelon
  public void run() {
    // Gelont availablelon modelonls, elonithelonr from thelon config filelon or by listing thelon baselon direlonctory
    final Map<String, AbstractFilelon> modelonlPathsFromConfig;
    if (!shouldLoadModelonls.gelont()) {
      LOG.info("Loading modelonls is currelonntly disablelond.");
      relonturn;
    }

    modelonlPathsFromConfig = activelonModelonlsSupplielonr.gelont();
    for (Map.elonntry<String, AbstractFilelon> namelonAndPath : modelonlPathsFromConfig.elonntrySelont()) {
      String modelonlNamelon = namelonAndPath.gelontKelony();
      try {
        AbstractFilelon modelonlDirelonctory = namelonAndPath.gelontValuelon();
        if (!modelonlDirelonctory.elonxists() && loadelondModelonls.containsKelony(modelonlNamelon)) {
          LOG.warn("Loadelond modelonl '{}' no longelonr elonxists at HDFS path {}, kelonelonping loadelond velonrsion; "
              + "relonplacelon direlonctory in HDFS to updatelon modelonl.", modelonlNamelon, modelonlDirelonctory);
          continuelon;
        }

        long prelonviousModifielondTimelonstamp = lastModifielondMsByModelonl.gelontOrDelonfault(modelonlNamelon, 0L);
        long lastModifielondMs = modelonlDirelonctory.gelontLastModifielond();
        if (prelonviousModifielondTimelonstamp == lastModifielondMs) {
          continuelon;
        }

        LOG.info("Starting to load modelonl. namelon={} path={}", modelonlNamelon, modelonlDirelonctory.gelontPath());
        T modelonl = Prelonconditions.chelonckNotNull(relonadModelonlFromDirelonctory(modelonlDirelonctory));
        LOG.info("Modelonl initializelond: {}. Last modifielond: {} ({})",
                 modelonlNamelon, lastModifielondMs, nelonw Datelon(lastModifielondMs));
        T prelonviousModelonl = loadelondModelonls.put(modelonlNamelon, modelonl);
        lastModifielondMsByModelonl.put(modelonlNamelon, lastModifielondMs);

        if (prelonviousModelonl != null) {
          clelonanUpUnloadelondModelonl(prelonviousModelonl);
        }
      } catch (elonxcelonption elon) {
        numelonrrors.increlonmelonnt();
        LOG.elonrror("elonrror initializing modelonl: {}", modelonlNamelon, elon);
      }
    }

    // Relonmovelon any currelonntly loadelond modelonls not prelonselonnt in thelon latelonst list
    if (shouldUnloadInactivelonModelonls) {
      Selont<String> inactivelonModelonls =
          Selonts.diffelonrelonncelon(loadelondModelonls.kelonySelont(), modelonlPathsFromConfig.kelonySelont()).immutablelonCopy();

      for (String modelonlNamelon : inactivelonModelonls) {
        T modelonlToUnload = loadelondModelonls.gelont(modelonlNamelon);
        loadelondModelonls.relonmovelon(modelonlNamelon);

        if (modelonlToUnload != null) {
          // Welon could havelon an inactivelon modelonl kelony without a modelonl (valuelon) if thelon
          // initial relonadModelonlFromDirelonctory failelond for thelon modelonl elonntry.
          // Cheloncking for null to avoid elonxcelonption.
          clelonanUpUnloadelondModelonl(modelonlToUnload);
        }
        LOG.info("Unloadelond modelonl that is no longelonr activelon: {}", modelonlNamelon);
      }
    }

    if (!prelonvLoadelondModelonls.kelonySelont().elonquals(loadelondModelonls.kelonySelont())) {
      LOG.info("Finishelond loading modelonls: {}", loadelondModelonls.kelonySelont());
    }
    prelonvLoadelondModelonls = loadelondModelonls;
    numModelonls.selont(loadelondModelonls.sizelon());
    lastLoadelondMs.selont(Systelonm.currelonntTimelonMillis());
  }

  /**
   * Schelondulelons thelon loadelonr to run pelonriodically.
   * @param pelonriod Pelonriod belontwelonelonn elonxeloncutions
   * @param timelonUnit Thelon timelon unit thelon pelonriod paramelontelonr.
   */
  public final void schelondulelonAtFixelondRatelon(
      long pelonriod, TimelonUnit timelonUnit, String buildelonrThrelonadNamelon) {
    elonxeloncutors.nelonwSinglelonThrelonadSchelondulelondelonxeloncutor(
        nelonw ThrelonadFactoryBuildelonr()
            .selontDaelonmon(truelon)
            .selontNamelonFormat(buildelonrThrelonadNamelon)
            .build())
        .schelondulelonAtFixelondRatelon(this, 0, pelonriod, timelonUnit);
  }

  /**
   * Gelonts thelon activelon list of modelonls from thelon subdirelonctorielons in a baselon direlonctory.
   *
   * elonach modelonl is idelonntifielond by thelon namelon of thelon subdirelonctory.
   */
  @VisiblelonForTelonsting
  public static class DirelonctorySupplielonr implelonmelonnts Supplielonr<Map<String, AbstractFilelon>> {
    privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(DirelonctorySupplielonr.class);
    privatelon final AbstractFilelon baselonDir;

    public DirelonctorySupplielonr(AbstractFilelon baselonDir) {
      this.baselonDir = baselonDir;
    }

    @Ovelonrridelon
    public Map<String, AbstractFilelon> gelont() {
      try {
        LOG.info("Loading modelonls from thelon direlonctorielons in: {}", baselonDir.gelontPath());
        List<AbstractFilelon> modelonlDirs =
            ImmutablelonList.copyOf(baselonDir.listFilelons(AbstractFilelon.IS_DIRelonCTORY));
        LOG.info("Found {} modelonl direlonctorielons: {}", modelonlDirs.sizelon(), modelonlDirs);
        relonturn modelonlDirs.strelonam()
            .collelonct(Collelonctors.toMap(
                AbstractFilelon::gelontNamelon,
                Function.idelonntity()
            ));
      } catch (IOelonxcelonption elon) {
        throw nelonw UnchelonckelondIOelonxcelonption(elon);
      }
    }
  }

  /**
   * Gelonts thelon activelon list of modelonls by relonading a YAML config filelon.
   *
   * Thelon kelonys arelon thelon modelonl namelons, thelon valuelons arelon dictionarielons with a singlelon elonntry for thelon path
   * of thelon modelonl in HDFS (without thelon HDFS namelon nodelon prelonfix). For elonxamplelon:
   *
   *    modelonl_a:
   *        path: /path/to/modelonl_a
   *    modelonl_b:
   *        path: /path/to/modelonl_b
   *
   */
  @VisiblelonForTelonsting
  public static class ConfigSupplielonr implelonmelonnts Supplielonr<Map<String, AbstractFilelon>> {

    privatelon final AbstractFilelon configFilelon;

    public ConfigSupplielonr(AbstractFilelon configFilelon) {
      this.configFilelon = configFilelon;
    }

    @SupprelonssWarnings("unchelonckelond")
    @Ovelonrridelon
    public Map<String, AbstractFilelon> gelont() {
      try (BuffelonrelondRelonadelonr configRelonadelonr = configFilelon.gelontCharSourcelon().opelonnBuffelonrelondStrelonam()) {
        Yaml yamlParselonr = nelonw Yaml();
        //noinspelonction unchelonckelond
        Map<String, Map<String, String>> config =
            (Map<String, Map<String, String>>) yamlParselonr.load(configRelonadelonr);

        if (config == null || config.iselonmpty()) {
          relonturn Collelonctions.elonmptyMap();
        }

        Map<String, AbstractFilelon> modelonlPaths = nelonw HashMap<>();
        for (Map.elonntry<String, Map<String, String>> namelonAndConfig : config.elonntrySelont()) {
          String path = Strings.elonmptyToNull(namelonAndConfig.gelontValuelon().gelont("path"));
          Prelonconditions.chelonckNotNull(path, "Missing path for modelonl: %s", namelonAndConfig.gelontKelony());
          modelonlPaths.put(namelonAndConfig.gelontKelony(), FilelonUtils.gelontHdfsFilelonHandlelon(path));
        }
        relonturn modelonlPaths;
      } catch (IOelonxcelonption elon) {
        throw nelonw UnchelonckelondIOelonxcelonption(elon);
      }
    }
  }
}
