package com.tw ter.search.common.ut l.ml.models_manager;

 mport java. o.BufferedReader;
 mport java. o. OExcept on;
 mport java. o.Unc cked OExcept on;
 mport java.ut l.Collect ons;
 mport java.ut l.Date;
 mport java.ut l.HashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Opt onal;
 mport java.ut l.Set;
 mport java.ut l.concurrent.ConcurrentHashMap;
 mport java.ut l.concurrent.Executors;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.funct on.Funct on;
 mport java.ut l.funct on.Suppl er;
 mport java.ut l.stream.Collectors;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Str ngs;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.Sets;
 mport com.google.common.ut l.concurrent.ThreadFactoryBu lder;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;
 mport org.yaml.snakeyaml.Yaml;

 mport com.tw ter.search.common.f le.AbstractF le;
 mport com.tw ter.search.common.f le.F leUt ls;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;

/**
 * Loads models from HDFS and prov des an  nterface for reload ng t m per od cally.
 *
 * T re are 2 poss ble ways of detect ng t  act ve models:
 *
 * - D rectorySuppl er: Uses all t  subd rector es of a base path
 * - Conf gSuppl er: Gets t  l st from from a conf gurat on f le
 *
 * Models can be updated or added. Depend ng on t  selected  thod, ex st ng models can be removed
 *  f t y are no longer act ve.
 */
publ c abstract class BaseModelsManager<T>  mple nts Runnable {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(BaseModelsManager.class);

  protected f nal Map<Str ng, Long> lastMod f edMsByModel = new ConcurrentHashMap<>();
  protected f nal Map<Str ng, T> loadedModels = new ConcurrentHashMap<>();
  protected f nal Suppl er<Map<Str ng, AbstractF le>> act veModelsSuppl er;

  protected Map<Str ng, T> prevLoadedModels = new ConcurrentHashMap<>();

  // T  flag determ nes w t r models are unloaded  m d ately w n t y're removed from
  // act veModelsSuppl er.  f false, old models stay  n  mory unt l t  process  s restarted.
  // T  may be useful to safely change model conf gurat on w hout restart ng.
  protected f nal boolean shouldUnload nact veModels;

  protected f nal SearchLongGauge numModels;
  protected f nal SearchCounter numErrors;
  protected f nal SearchLongGauge lastLoadedMs;

  protected Suppl er<Boolean> shouldServeModels;
  protected Suppl er<Boolean> shouldLoadModels;

  publ c BaseModelsManager(
      Suppl er<Map<Str ng, AbstractF le>> act veModelsSuppl er,
      boolean shouldUnload nact veModels,
      Str ng statsPref x
  ) {
    t (
      act veModelsSuppl er,
      shouldUnload nact veModels,
      statsPref x,
      () -> true,
      () -> true
    );
  }

  publ c BaseModelsManager(
      Suppl er<Map<Str ng, AbstractF le>> act veModelsSuppl er,
      boolean shouldUnload nact veModels,
      Str ng statsPref x,
      Suppl er<Boolean> shouldServeModels,
      Suppl er<Boolean> shouldLoadModels
  ) {
    t .act veModelsSuppl er = act veModelsSuppl er;
    t .shouldUnload nact veModels = shouldUnload nact veModels;

    t .shouldServeModels = shouldServeModels;
    t .shouldLoadModels = shouldLoadModels;

    numModels = SearchLongGauge.export(
        Str ng.format("model_loader_%s_num_models", statsPref x));
    numErrors = SearchCounter.export(
        Str ng.format("model_loader_%s_num_errors", statsPref x));
    lastLoadedMs = SearchLongGauge.export(
        Str ng.format("model_loader_%s_last_loaded_t  stamp_ms", statsPref x));
  }

  /**
   *  Retr eves a part cular model.
   */
  publ c Opt onal<T> getModel(Str ng na ) {
     f (shouldServeModels.get()) {
      return Opt onal.ofNullable(loadedModels.get(na ));
    } else {
      return Opt onal.empty();
    }
  }

  /**
   * Reads a model  nstance from t  d rectory f le  nstance.
   *
   * @param modelBaseD r AbstractF le  nstance represent ng t  d rectory.
   * @return Model  nstance parsed from t  d rectory.
   */
  publ c abstract T readModelFromD rectory(AbstractF le modelBaseD r) throws Except on;

  /**
   * Cleans up any res ces used by t  model  nstance.
   * T   thod  s called after remov ng t  model from t   n- mory map.
   * Sub-classes can prov de custom overr dden  mple ntat on as requ red.
   *
   * @param unloadedModel Model  nstance that would be unloaded from t  manager.
   */
  protected vo d cleanUpUnloadedModel(T unloadedModel) { }

  @Overr de
  publ c vo d run() {
    // Get ava lable models, e  r from t  conf g f le or by l st ng t  base d rectory
    f nal Map<Str ng, AbstractF le> modelPathsFromConf g;
     f (!shouldLoadModels.get()) {
      LOG. nfo("Load ng models  s currently d sabled.");
      return;
    }

    modelPathsFromConf g = act veModelsSuppl er.get();
    for (Map.Entry<Str ng, AbstractF le> na AndPath : modelPathsFromConf g.entrySet()) {
      Str ng modelNa  = na AndPath.getKey();
      try {
        AbstractF le modelD rectory = na AndPath.getValue();
         f (!modelD rectory.ex sts() && loadedModels.conta nsKey(modelNa )) {
          LOG.warn("Loaded model '{}' no longer ex sts at HDFS path {}, keep ng loaded vers on; "
              + "replace d rectory  n HDFS to update model.", modelNa , modelD rectory);
          cont nue;
        }

        long prev ousMod f edT  stamp = lastMod f edMsByModel.getOrDefault(modelNa , 0L);
        long lastMod f edMs = modelD rectory.getLastMod f ed();
         f (prev ousMod f edT  stamp == lastMod f edMs) {
          cont nue;
        }

        LOG. nfo("Start ng to load model. na ={} path={}", modelNa , modelD rectory.getPath());
        T model = Precond  ons.c ckNotNull(readModelFromD rectory(modelD rectory));
        LOG. nfo("Model  n  al zed: {}. Last mod f ed: {} ({})",
                 modelNa , lastMod f edMs, new Date(lastMod f edMs));
        T prev ousModel = loadedModels.put(modelNa , model);
        lastMod f edMsByModel.put(modelNa , lastMod f edMs);

         f (prev ousModel != null) {
          cleanUpUnloadedModel(prev ousModel);
        }
      } catch (Except on e) {
        numErrors. ncre nt();
        LOG.error("Error  n  al z ng model: {}", modelNa , e);
      }
    }

    // Remove any currently loaded models not present  n t  latest l st
     f (shouldUnload nact veModels) {
      Set<Str ng>  nact veModels =
          Sets.d fference(loadedModels.keySet(), modelPathsFromConf g.keySet()). mmutableCopy();

      for (Str ng modelNa  :  nact veModels) {
        T modelToUnload = loadedModels.get(modelNa );
        loadedModels.remove(modelNa );

         f (modelToUnload != null) {
          //   could have an  nact ve model key w hout a model (value)  f t 
          //  n  al readModelFromD rectory fa led for t  model entry.
          // C ck ng for null to avo d except on.
          cleanUpUnloadedModel(modelToUnload);
        }
        LOG. nfo("Unloaded model that  s no longer act ve: {}", modelNa );
      }
    }

     f (!prevLoadedModels.keySet().equals(loadedModels.keySet())) {
      LOG. nfo("F n s d load ng models: {}", loadedModels.keySet());
    }
    prevLoadedModels = loadedModels;
    numModels.set(loadedModels.s ze());
    lastLoadedMs.set(System.currentT  M ll s());
  }

  /**
   * Sc dules t  loader to run per od cally.
   * @param per od Per od bet en execut ons
   * @param t  Un  T  t   un  t  per od para ter.
   */
  publ c f nal vo d sc duleAtF xedRate(
      long per od, T  Un  t  Un , Str ng bu lderThreadNa ) {
    Executors.newS ngleThreadSc duledExecutor(
        new ThreadFactoryBu lder()
            .setDaemon(true)
            .setNa Format(bu lderThreadNa )
            .bu ld())
        .sc duleAtF xedRate(t , 0, per od, t  Un );
  }

  /**
   * Gets t  act ve l st of models from t  subd rector es  n a base d rectory.
   *
   * Each model  s  dent f ed by t  na  of t  subd rectory.
   */
  @V s bleForTest ng
  publ c stat c class D rectorySuppl er  mple nts Suppl er<Map<Str ng, AbstractF le>> {
    pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(D rectorySuppl er.class);
    pr vate f nal AbstractF le baseD r;

    publ c D rectorySuppl er(AbstractF le baseD r) {
      t .baseD r = baseD r;
    }

    @Overr de
    publ c Map<Str ng, AbstractF le> get() {
      try {
        LOG. nfo("Load ng models from t  d rector es  n: {}", baseD r.getPath());
        L st<AbstractF le> modelD rs =
             mmutableL st.copyOf(baseD r.l stF les(AbstractF le. S_D RECTORY));
        LOG. nfo("Found {} model d rector es: {}", modelD rs.s ze(), modelD rs);
        return modelD rs.stream()
            .collect(Collectors.toMap(
                AbstractF le::getNa ,
                Funct on. dent y()
            ));
      } catch ( OExcept on e) {
        throw new Unc cked OExcept on(e);
      }
    }
  }

  /**
   * Gets t  act ve l st of models by read ng a YAML conf g f le.
   *
   * T  keys are t  model na s, t  values are d ct onar es w h a s ngle entry for t  path
   * of t  model  n HDFS (w hout t  HDFS na  node pref x). For example:
   *
   *    model_a:
   *        path: /path/to/model_a
   *    model_b:
   *        path: /path/to/model_b
   *
   */
  @V s bleForTest ng
  publ c stat c class Conf gSuppl er  mple nts Suppl er<Map<Str ng, AbstractF le>> {

    pr vate f nal AbstractF le conf gF le;

    publ c Conf gSuppl er(AbstractF le conf gF le) {
      t .conf gF le = conf gF le;
    }

    @SuppressWarn ngs("unc cked")
    @Overr de
    publ c Map<Str ng, AbstractF le> get() {
      try (BufferedReader conf gReader = conf gF le.getCharS ce().openBufferedStream()) {
        Yaml yamlParser = new Yaml();
        //no nspect on unc cked
        Map<Str ng, Map<Str ng, Str ng>> conf g =
            (Map<Str ng, Map<Str ng, Str ng>>) yamlParser.load(conf gReader);

         f (conf g == null || conf g. sEmpty()) {
          return Collect ons.emptyMap();
        }

        Map<Str ng, AbstractF le> modelPaths = new HashMap<>();
        for (Map.Entry<Str ng, Map<Str ng, Str ng>> na AndConf g : conf g.entrySet()) {
          Str ng path = Str ngs.emptyToNull(na AndConf g.getValue().get("path"));
          Precond  ons.c ckNotNull(path, "M ss ng path for model: %s", na AndConf g.getKey());
          modelPaths.put(na AndConf g.getKey(), F leUt ls.getHdfsF leHandle(path));
        }
        return modelPaths;
      } catch ( OExcept on e) {
        throw new Unc cked OExcept on(e);
      }
    }
  }
}
