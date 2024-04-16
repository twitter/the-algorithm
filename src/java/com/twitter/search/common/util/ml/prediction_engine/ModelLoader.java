package com.tw ter.search.common.ut l.ml.pred ct on_eng ne;

 mport java. o. OExcept on;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.base.Opt onal;
 mport com.google.common.base.Suppl er;
 mport com.google.common.base.Suppl ers;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.f le.AbstractF le;
 mport com.tw ter.search.common.f le.F leUt ls;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;

/**
 * Loads L ght  ghtL nearModel objects from a d rectory and prov des an  nterface for reload ng
 * t m per od cally.
 *
 * All t  models must support t  sa  features (def ned by a FeatureContext) and t y are
 *  dent f ed by t  na  of t  subd rectory. T   s t  requ red d rectory structure:
 *
 *  /path/to/base-d rectory
 *      one-model/model.tsv
 *      anot r-model/model.tsv
 *      exper  ntal-model/model.tsv
 *
 * Each subd rectory must conta n a f le na d 'model.tsv'  n t  format requ red by
 * L ght  ghtL nearModel.
 */
publ c class ModelLoader  mple nts Runnable {

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(ModelLoader.class);
  pr vate stat c f nal Str ng MODEL_F LE_NAME = "model.tsv";

  pr vate f nal Compos eFeatureContext featureContext;
  pr vate f nal Suppl er<AbstractF le> d rectorySuppl er;

  pr vate f nal Map<Str ng, L ght  ghtL nearModel> models;
  pr vate f nal Map<Str ng, Long> lastMod f edMsByModel;

  pr vate f nal SearchLongGauge lastModelLoadedAtMs;
  pr vate f nal SearchLongGauge numModels;
  pr vate f nal SearchCounter numLoads;
  pr vate f nal SearchCounter numErrors;

  /**
   * Creates a new  nstance for a feature context and a base d rectory.
   *
   *   exports 4 counters:
   *
   *   ${counterPref x}_last_loaded:
   *      T  stamp ( n ms) w n t  last model was loaded.
   *   ${counterPref x}_num_models:
   *      Number of models currently loaded.
   *   ${counterPref x}_num_loads:
   *      Number of succesful model loads.
   *   ${counterPref x}_num_errors:
   *      Number of errors occurred wh le load ng t  models.
   */
  protected ModelLoader(
      Compos eFeatureContext featureContext,
      Suppl er<AbstractF le> d rectorySuppl er,
      Str ng counterPref x,
      SearchStatsRece ver statsRece ver) {
    t .featureContext = featureContext;

    // T  funct on returns t  base d rectory every t     call 'run'.   use a funct on  nstead
    // of us ng d rectly an AbstractF le  nstance,  n case that   can't obta n an  nstance at
    //  n  al zat on t   (e.g.  f t re's an  ssue w h HDFS).
    t .d rectorySuppl er = d rectorySuppl er;
    t .models = Maps.newConcurrentMap();
    t .lastMod f edMsByModel = Maps.newConcurrentMap();

    t .lastModelLoadedAtMs = statsRece ver.getLongGauge(counterPref x + "last_loaded");
    t .numModels = statsRece ver.getLongGauge(counterPref x + "num_models");
    t .numLoads = statsRece ver.getCounter(counterPref x + "num_loads");
    t .numErrors = statsRece ver.getCounter(counterPref x + "num_errors");
  }

  publ c Opt onal<L ght  ghtL nearModel> getModel(Str ng na ) {
    return Opt onal.fromNullable(models.get(na ));
  }

  /**
   * Loads t  models from t  base d rectory.
   *
   *   doesn't load a model  f  s f le has not been mod f ed s nce t  last t     was loaded.
   *
   * T   thod doesn't delete prev ously loaded models  f t  r d rector es are not ava lable.
   */
  @Overr de
  publ c vo d run() {
    try {
      AbstractF le baseD rectory = d rectorySuppl er.get();
      L st<AbstractF le> modelD rector es =
          L sts.newArrayL st(baseD rectory.l stF les( S_MODEL_D R));
      for (AbstractF le d rectory : modelD rector es) {
        try {
          // Note that t  modelNa   s t  d rectory na ,  f   ends w h ".sc ma_based", t 
          // model w ll be loaded as a sc ma-based model.
          Str ng modelNa  = d rectory.getNa ();
          AbstractF le modelF le = d rectory.getCh ld(MODEL_F LE_NAME);
          long currentLastMod f ed = modelF le.getLastMod f ed();
          Long lastMod f ed = lastMod f edMsByModel.get(modelNa );
           f (lastMod f ed == null || lastMod f ed < currentLastMod f ed) {
            L ght  ghtL nearModel model =
                L ght  ghtL nearModel.load(modelNa , featureContext, modelF le);
             f (!models.conta nsKey(modelNa )) {
              LOG. nfo("Load ng model {}.", modelNa );
            }
            models.put(modelNa , model);
            lastMod f edMsByModel.put(modelNa , currentLastMod f ed);
            lastModelLoadedAtMs.set(System.currentT  M ll s());
            numLoads. ncre nt();
            LOG.debug("Model: {}", model);
          } else {
            LOG.debug("D rectory for model {} has not changed.", modelNa );
          }
        } catch (Except on e) {
          LOG.error("Error load ng model from d rectory: " + d rectory.getPath(), e);
          t .numErrors. ncre nt();
        }
      }
       f (numModels.get() != models.s ze()) {
        LOG. nfo("F n s d load ng models. Model na s: {}", models.keySet());
      }
      t .numModels.set(models.s ze());
    } catch ( OExcept on e) {
      LOG.error("Error load ng models", e);
      t .numErrors. ncre nt();
    }
  }

  /**
   * Creates an  nstance that loads models from a d rectory (local or from HDFS).
   */
  publ c stat c ModelLoader forD rectory(
      f nal AbstractF le d rectory,
      Compos eFeatureContext featureContext,
      Str ng counterPref x,
      SearchStatsRece ver statsRece ver) {
    Suppl er<AbstractF le> d rectorySuppl er = Suppl ers.of nstance(d rectory);
    return new ModelLoader(featureContext, d rectorySuppl er, counterPref x, statsRece ver);
  }

  /**
   * Creates an  nstance that loads models from HDFS.
   */
  publ c stat c ModelLoader forHdfsD rectory(
      f nal Str ng na Node,
      f nal Str ng d rectory,
      Compos eFeatureContext featureContext,
      Str ng counterPref x,
      SearchStatsRece ver statsRece ver) {
    Suppl er<AbstractF le> d rectorySuppl er =
        () -> F leUt ls.getHdfsF leHandle(d rectory, na Node);
    return new ModelLoader(featureContext, d rectorySuppl er, counterPref x, statsRece ver);
  }

  pr vate stat c f nal AbstractF le.F lter  S_MODEL_D R = f le -> {
    try {
       f (f le. sD rectory()) {
        AbstractF le modelF le = f le.getCh ld(MODEL_F LE_NAME);
        return (modelF le != null) && modelF le.canRead();
      }
    } catch ( OExcept on e) {
      LOG.error("Error read ng f le: " + f le, e);
    }
    return false;
  };
}
