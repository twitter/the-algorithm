package com.tw ter.search.earlyb rd.ml;

 mport java. o. OExcept on;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Opt onal;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.f le.AbstractF le;
 mport com.tw ter.search.common.f le.F leUt ls;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common.sc ma.Dynam cSc ma;
 mport com.tw ter.search.common.ut l.ml.pred ct on_eng ne.Compos eFeatureContext;
 mport com.tw ter.search.common.ut l.ml.pred ct on_eng ne.L ght  ghtL nearModel;
 mport com.tw ter.search.common.ut l.ml.pred ct on_eng ne.ModelLoader;

 mport stat c com.tw ter.search.model ng.t et_rank ng.T etScor ngFeatures.CONTEXT;
 mport stat c com.tw ter.search.model ng.t et_rank ng.T etScor ngFeatures.FeatureContextVers on.CURRENT_VERS ON;

/**
 * Loads t  scor ng models for t ets and prov des access to t m.
 *
 * T  class rel es on a l st of ModelLoader objects to retr eve t  objects from t m.   w ll
 * return t  f rst model found accord ng to t  order  n t  l st.
 *
 * For product on,   load models from 2 s ces: classpath and HDFS.  f a model  s ava lable
 * from HDFS,   return  , ot rw se   use t  model from t  classpath.
 *
 * T  models used for default requests ( .e. not exper  nts) MUST be present  n t 
 * classpath, t  allows us to avo d errors  f t y can't be loaded from HDFS.
 * Models for exper  nts can l ve only  n HDFS, so   don't need to redeploy Earlyb rd  f  
 * want to test t m.
 */
publ c class Scor ngModelsManager {

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Scor ngModelsManager.class);

  /**
   * Used w n
   * 1. Test ng
   * 2. T  scor ng models are d sabled  n t  conf g
   * 3. Except ons thrown dur ng load ng t  scor ng models
   */
  publ c stat c f nal Scor ngModelsManager NO_OP_MANAGER = new Scor ngModelsManager() {
    @Overr de
    publ c boolean  sEnabled() {
      return false;
    }
  };

  pr vate f nal ModelLoader[] loaders;
  pr vate f nal Dynam cSc ma dynam cSc ma;

  publ c Scor ngModelsManager(ModelLoader... loaders) {
    t .loaders = loaders;
    t .dynam cSc ma = null;
  }

  publ c Scor ngModelsManager(Dynam cSc ma dynam cSc ma, ModelLoader... loaders) {
    t .loaders = loaders;
    t .dynam cSc ma = dynam cSc ma;
  }

  /**
   *  nd cates that t  scor ng models  re enabled  n t  conf g and  re loaded successfully
   */
  publ c boolean  sEnabled() {
    return true;
  }

  publ c vo d reload() {
    for (ModelLoader loader : loaders) {
      loader.run();
    }
  }

  /**
   * Loads and returns t  model w h t  g ven na ,  f one ex sts.
   */
  publ c Opt onal<L ght  ghtL nearModel> getModel(Str ng modelNa ) {
    for (ModelLoader loader : loaders) {
      Opt onal<L ght  ghtL nearModel> model = loader.getModel(modelNa );
       f (model. sPresent()) {
        return model;
      }
    }
    return Opt onal.absent();
  }

  /**
   * Creates an  nstance that loads models f rst from HDFS and t  classpath res ces.
   *
   *  f t  models are not found  n HDFS,   uses t  models from t  classpath as fallback.
   */
  publ c stat c Scor ngModelsManager create(
      SearchStatsRece ver serverStats,
      Str ng hdfsNa Node,
      Str ng hdfsBasedPath,
      Dynam cSc ma dynam cSc ma) throws  OExcept on {
    // Create a compos e feature context so   can load both legacy and sc ma-based models
    Compos eFeatureContext featureContext = new Compos eFeatureContext(
        CONTEXT, dynam cSc ma::getSearchFeatureSc ma);
    ModelLoader hdfsLoader = createHdfsLoader(
        serverStats, hdfsNa Node, hdfsBasedPath, featureContext);
    ModelLoader classpathLoader = createClasspathLoader(
        serverStats, featureContext);

    // Expl c ly load t  models from t  classpath
    classpathLoader.run();

    Scor ngModelsManager manager = new Scor ngModelsManager(hdfsLoader, classpathLoader);
    LOG. nfo(" n  al zed Scor ngModelsManager for load ng models from HDFS and t  classpath");
    return manager;
  }

  protected stat c ModelLoader createHdfsLoader(
      SearchStatsRece ver serverStats,
      Str ng hdfsNa Node,
      Str ng hdfsBasedPath,
      Compos eFeatureContext featureContext) {
    Str ng hdfsVers onedPath = hdfsBasedPath + "/" + CURRENT_VERS ON.getVers onD rectory();
    LOG. nfo("Start ng to load scor ng models from HDFS: {}:{}",
        hdfsNa Node, hdfsVers onedPath);
    return ModelLoader.forHdfsD rectory(
        hdfsNa Node,
        hdfsVers onedPath,
        featureContext,
        "scor ng_models_hdfs_",
        serverStats);
  }

  /**
   * Creates a loader that loads models from a default locat on  n t  classpath.
   */
  @V s bleForTest ng
  publ c stat c ModelLoader createClasspathLoader(
      SearchStatsRece ver serverStats, Compos eFeatureContext featureContext)
      throws  OExcept on {
    AbstractF le defaultModelsBaseD r = F leUt ls.getTmpD rHandle(
        Scor ngModelsManager.class,
        "/com/tw ter/search/earlyb rd/ml/default_models");
    AbstractF le defaultModelsD r = defaultModelsBaseD r.getCh ld(
        CURRENT_VERS ON.getVers onD rectory());

    LOG. nfo("Start ng to load scor ng models from t  classpath: {}",
        defaultModelsD r.getPath());
    return ModelLoader.forD rectory(
        defaultModelsD r,
        featureContext,
        "scor ng_models_classpath_",
        serverStats);
  }
}
