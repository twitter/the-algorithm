package com.tw ter.search.common.ut l.ml.tensorflow_eng ne;

 mport java. o. OExcept on;
 mport java.ut l.Collect ons;
 mport java.ut l.HashMap;
 mport java.ut l.Map;
 mport java.ut l.funct on.Suppl er;

 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;
 mport org.tensorflow.SavedModelBundle;
 mport org.tensorflow.Sess on;

 mport com.tw ter.ml.ap .FeatureUt l;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc ma;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc maEntry;
 mport com.tw ter.search.common.f le.AbstractF le;
 mport com.tw ter.search.common.sc ma.Dynam cSc ma;
 mport com.tw ter.search.common.ut l.ml.models_manager.BaseModelsManager;
 mport com.tw ter.tfcompute_java.TFModelRunner;
 mport com.tw ter.tfcompute_java.TFSess on n ;
 mport com.tw ter.twml.runt  .l b.TwmlLoader;
 mport com.tw ter.twml.runt  .models.ModelLocator;
 mport com.tw ter.twml.runt  .models.ModelLocator$;
 mport com.tw ter.ut l.Awa ;

/**
 * TensorflowModelsManager manages t  l fecyle of TF models.
 */
publ c class TensorflowModelsManager extends BaseModelsManager<TFModelRunner>  {

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(TensorflowModelsManager.class);

  pr vate stat c f nal Str ng[] TF_TAGS = new Str ng[] {"serve"};

  pr vate volat le Map< nteger, Long> featureSc ma dToMlAp  d = new HashMap< nteger, Long>();

  stat c {
    TwmlLoader.load();
  }

  publ c stat c f nal TensorflowModelsManager NO_OP_MANAGER =
    createNoOp("no_op_manager");

  publ c TensorflowModelsManager(
      Suppl er<Map<Str ng, AbstractF le>> act veModelsSuppl er,
      boolean shouldUnload nact veModels,
      Str ng statsPref x
  ) {
    t (
      act veModelsSuppl er,
      shouldUnload nact veModels,
      statsPref x,
      () -> true,
      () -> true,
      null
    );
  }

  publ c TensorflowModelsManager(
      Suppl er<Map<Str ng, AbstractF le>> act veModelsSuppl er,
      boolean shouldUnload nact veModels,
      Str ng statsPref x,
      Suppl er<Boolean> serveModels,
      Suppl er<Boolean> loadModels,
      Dynam cSc ma dynam cSc ma
  ) {
    super(
      act veModelsSuppl er,
      shouldUnload nact veModels,
      statsPref x,
      serveModels,
      loadModels
    );
     f (dynam cSc ma != null) {
      updateFeatureSc ma dToMl dMap(dynam cSc ma.getSearchFeatureSc ma());
    }
  }

  /**
   * T  ML AP  feature  ds for tensorflow scor ng are has s of t  r feature na s. T  hash ng
   * could be expens ve to do for every search request.  nstead, allow t  map from sc ma feature
   *  d to ML AP   d to be updated w never t  sc ma  s reloaded.
   */
  publ c vo d updateFeatureSc ma dToMl dMap(Thr ftSearchFeatureSc ma sc ma) {
    HashMap< nteger, Long> newFeatureSc ma dToMlAp  d = new HashMap< nteger, Long>();
    Map< nteger, Thr ftSearchFeatureSc maEntry> featureEntr es = sc ma.getEntr es();
    for (Map.Entry< nteger, Thr ftSearchFeatureSc maEntry> entry : featureEntr es.entrySet()) {
      long mlAp Feature d = FeatureUt l.feature dForNa (entry.getValue().getFeatureNa ());
      newFeatureSc ma dToMlAp  d.put(entry.getKey(), mlAp Feature d);
    }

    featureSc ma dToMlAp  d = newFeatureSc ma dToMlAp  d;
  }

  publ c Map< nteger, Long> getFeatureSc ma dToMlAp  d() {
    return featureSc ma dToMlAp  d;
  }

  /**
   *  f t  manager  s not enabled,   won't fetch TF models.
   */
  publ c boolean  sEnabled() {
    return true;
  }

  /**
   * Load an  nd v dual model and make   ava lable for  nference.
   */
  publ c TFModelRunner readModelFromD rectory(
    AbstractF le modelD r) throws  OExcept on {

    ModelLocator modelLocator =
      ModelLocator$.MODULE$.apply(
        modelD r.toStr ng(),
        modelD r.toUR ()
      );

    try {
      Awa .result(modelLocator.ensureLocalPresent(true));
    } catch (Except on e) {
      LOG.error("Couldn't f nd model " + modelD r.toStr ng(), e);
      throw new  OExcept on("Couldn't f nd model " + modelD r.toStr ng());
    }

    Sess on sess on = SavedModelBundle.load(modelLocator.localPath(), TF_TAGS).sess on();

    return new TFModelRunner(sess on);
  }


  /**
   *  n  al ze Tensorflow  ntra and  nter op thread pools.
   * See `Conf gProto.[ ntra| nter]_op_parallel sm_threads` docu ntat on for more  nformat on:
   * https://g hub.com/tensorflow/tensorflow/blob/master/tensorflow/core/protobuf/conf g.proto
   *  n  al zat on should happen only once.
   * Default values for Tensorflow are:
   *  ntraOpParallel smThreads = 0 wh ch  ans that TF w ll p ck an appropr ate default.
   *  nterOpParallel smThreads = 0 wh ch  ans that TF w ll p ck an appropr ate default.
   * operat on_t  out_ n_ms = 0 wh ch  ans that no t  out w ll be appl ed.
   */
  publ c stat c vo d  n TensorflowThreadPools(
     nt  ntraOpParallel smThreads,
     nt  nterOpParallel smThreads) {
    new TFSess on n ( ntraOpParallel smThreads,  nterOpParallel smThreads, 0);
  }

  /**
   * Creates a no-op  nstance.   can be used for tests or w n t  models are d sabled.
   */
  publ c stat c TensorflowModelsManager createNoOp(Str ng statsPref x) {
    return new TensorflowModelsManager(Collect ons::emptyMap, false, statsPref x) {
      @Overr de
      publ c vo d run() { }

      @Overr de
      publ c boolean  sEnabled() {
        return false;
      }

      @Overr de
      publ c vo d updateFeatureSc ma dToMl dMap(Thr ftSearchFeatureSc ma sc ma) { }
    };
  }

 /**
   * Creates an  nstance that loads t  models based on a Conf gSuppl er.
   */
  publ c stat c TensorflowModelsManager createUs ngConf gF le(
      AbstractF le conf gF le,
      boolean shouldUnload nact veModels,
      Str ng statsPref x,
      Suppl er<Boolean> serveModels,
      Suppl er<Boolean> loadModels,
      Dynam cSc ma dynam cSc ma) {
    Precond  ons.c ckArgu nt(
        conf gF le.canRead(), "Conf g f le  s not readable: %s", conf gF le.getPath());
    return new TensorflowModelsManager(
      new Conf gSuppl er(conf gF le),
      shouldUnload nact veModels,
      statsPref x,
      serveModels,
      loadModels,
      dynam cSc ma
    );
  }
}
