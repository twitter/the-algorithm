package com.tw ter.search.common.ut l.ml.pred ct on_eng ne;

 mport java. o. OExcept on;
 mport java.ut l.Collect ons;
 mport java.ut l.Map;
 mport java.ut l.funct on.Suppl er;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.ml.ap .FeatureContext;
 mport com.tw ter.mlv2.trees.pred ctor.CartTree;
 mport com.tw ter.mlv2.trees.scorer.Dec s onForestScorer;
 mport com.tw ter.search.common.f le.AbstractF le;
 mport com.tw ter.search.common.ut l.ml.models_manager.BaseModelsManager;

/**
 * Loads Dec s on Forest based models and keep t m  n  mory. Can also be sc duled to reload
 * models per od cally.
 *
 * Note: Each  nstance  s t ed to a s ngle {@l nk FeatureContext}  nstance. So, to load models
 * for d fferent tasks,   should use d fferent  nstances of t  t  class.
 */
publ c class Dec s onForestModelsManager extends BaseModelsManager<Dec s onForestScorer<CartTree>> {
  pr vate stat c f nal Str ng MODEL_F LE_NAME = "model.json";

  pr vate f nal FeatureContext featureContext;

  Dec s onForestModelsManager(
      Suppl er<Map<Str ng, AbstractF le>> act veModelsSuppl er,
      FeatureContext featureContext,
      boolean shouldUnload nact veModels,
      Str ng statsPref x
  ) {
    super(act veModelsSuppl er, shouldUnload nact veModels, statsPref x);
    t .featureContext = featureContext;
  }

  @Overr de
  publ c Dec s onForestScorer<CartTree> readModelFromD rectory(AbstractF le modelBaseD r)
      throws  OExcept on {
    Str ng modelF lePath = modelBaseD r.getCh ld(MODEL_F LE_NAME).getPath();
    return Dec s onForestScorer.createCartTreeScorer(modelF lePath, featureContext);
  }

  /**
   * Creates an  nstance that loads t  models spec f ed  n a conf gurat on f le.
   *
   * Note that  f t  conf gurat on f le changes and   doesn't  nclude a model that was present
   * before, t  model w ll be removed ( .e.   unloads models that are not act ve anymore).
   */
  publ c stat c Dec s onForestModelsManager createUs ngConf gF le(
      AbstractF le conf gF le, FeatureContext featureContext, Str ng statsPref x) {
    Precond  ons.c ckArgu nt(
        conf gF le.canRead(), "Conf g f le  s not readable: %s", conf gF le.getPath());
    return new Dec s onForestModelsManager(
        new Conf gSuppl er(conf gF le), featureContext, true, statsPref x);
  }

  /**
   * Creates a no-op  nstance.   can be used for tests or w n t  models are d sabled.
   */
  publ c stat c Dec s onForestModelsManager createNoOp(Str ng statsPref x) {
    return new Dec s onForestModelsManager(
        Collect ons::emptyMap, new FeatureContext(), false, statsPref x) {
      @Overr de
      publ c vo d run() { }
    };
  }
}
