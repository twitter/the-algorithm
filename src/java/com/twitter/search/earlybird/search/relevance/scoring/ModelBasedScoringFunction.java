package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport java. o. OExcept on;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.base.Opt onal;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport org.apac .lucene.search.Explanat on;

 mport com.tw ter.search.common.features.thr ft.Thr ftSearchResultFeatures;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftRank ngParams;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.ut l.ml.pred ct on_eng ne.L ght  ghtL nearModel;
 mport com.tw ter.search.common.ut l.ml.pred ct on_eng ne.Sc maBasedScoreAccumulator;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.except on.Cl entExcept on;
 mport com.tw ter.search.earlyb rd.ml.Scor ngModelsManager;
 mport com.tw ter.search.earlyb rd.search.Ant Gam ngF lter;
 mport com.tw ter.search.earlyb rd.search.relevance.L nearScor ngData;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultType;

/**
 * Scor ng funct on that uses t  scor ng models spec f ed from t  request.
 */
publ c class ModelBasedScor ngFunct on extends FeatureBasedScor ngFunct on {
  pr vate f nal SelectedModel[] selectedModels;
  pr vate f nal boolean useLog Score;
  pr vate f nal boolean  sSc maBased;

  pr vate stat c f nal SearchCounter NUM_LEGACY_MODELS =
      SearchCounter.export("scor ng_funct on_num_legacy_models");
  pr vate stat c f nal SearchCounter NUM_SCHEMA_BASED_MODELS =
      SearchCounter.export("scor ng_funct on_num_sc ma_based_models");
  pr vate stat c f nal SearchCounter M XED_MODEL_TYPES =
      SearchCounter.export("scor ng_funct on_m xed_model_types");

  publ c ModelBasedScor ngFunct on(
       mmutableSc ma nterface sc ma,
      Thr ftSearchQuery searchQuery,
      Ant Gam ngF lter ant Gam ngF lter,
      Thr ftSearchResultType searchResultType,
      UserTable userTable,
      Scor ngModelsManager scor ngModelsManager
  ) throws  OExcept on, Cl entExcept on {

    super("ModelBasedScor ngFunct on", sc ma, searchQuery, ant Gam ngF lter, searchResultType,
        userTable);

    Thr ftRank ngParams rank ngParams = searchQuery.getRelevanceOpt ons().getRank ngParams();
    Precond  ons.c ckNotNull(rank ngParams);

     f (rank ngParams.getSelectedModelsS ze() <= 0) {
      throw new Cl entExcept on("Scor ng type  s MODEL_BASED but no models  re selected");
    }

    Map<Str ng, Double> models = rank ngParams.getSelectedModels();

    selectedModels = new SelectedModel[models.s ze()];
     nt numSc maBased = 0;
     nt   = 0;
    for (Map.Entry<Str ng, Double> na And  ght : models.entrySet()) {
      Opt onal<L ght  ghtL nearModel> model =
          scor ngModelsManager.getModel(na And  ght.getKey());
       f (!model. sPresent()) {
          throw new Cl entExcept on(Str ng.format(
              "Scor ng funct on  s MODEL_BASED. Selected model '%s' not found",
              na And  ght.getKey()));
      }
      selectedModels[ ] =
          new SelectedModel(na And  ght.getKey(), na And  ght.getValue(), model.get());

       f (selectedModels[ ].model. sSc maBased()) {
        ++numSc maBased;
        NUM_SCHEMA_BASED_MODELS. ncre nt();
      } else {
        NUM_LEGACY_MODELS. ncre nt();
      }
      ++ ;
    }

    //   should e  r see all models sc ma-based, or none of t m so,  f t   s not t  case,
    //   log an error  ssage and fall back to use just t  f rst model, whatever    s.
     f (numSc maBased > 0 && numSc maBased != selectedModels.length) {
      M XED_MODEL_TYPES. ncre nt();
      throw new Cl entExcept on(
          "  cannot m x sc ma-based and non-sc ma-based models  n t  sa  request, "
          + "models are: " + models.keySet());
    }

     sSc maBased = selectedModels[0].model. sSc maBased();
    useLog Score = rank ngParams. sUseLog Score();
  }

  @Overr de
  protected double computeScore(L nearScor ngData data, boolean forExplanat on) throws  OExcept on {
    Thr ftSearchResultFeatures features =
         sSc maBased ? createFeaturesForDocu nt(data, false).getFeatures() : null;

    double score = 0;
    for (SelectedModel selectedModel : selectedModels) {
      double modelScore =  sSc maBased
          ? new Sc maBasedScoreAccumulator(selectedModel.model).scoreW h(features, useLog Score)
          : new LegacyScoreAccumulator(selectedModel.model).scoreW h(data, useLog Score);
      score += selectedModel.  ght * modelScore;
    }

    return score;
  }

  @Overr de
  protected vo d generateExplanat onForScor ng(
      L nearScor ngData scor ngData, boolean  sH , L st<Explanat on> deta ls) throws  OExcept on {
    boolean sc maBased = selectedModels[0].model. sSc maBased();
    Thr ftSearchResultFeatures features =
        sc maBased ? createFeaturesForDocu nt(scor ngData, false).getFeatures() : null;

    // 1. Model-based score
    f nal L st<Explanat on> modelExplanat ons = L sts.newArrayL st();
    float f nalScore = 0;
    for (SelectedModel selectedModel : selectedModels) {
      double modelScore = sc maBased
          ? new Sc maBasedScoreAccumulator(selectedModel.model).scoreW h(features, useLog Score)
          : new LegacyScoreAccumulator(selectedModel.model).scoreW h(scor ngData, useLog Score);
      float   ghtedScore = (float) (selectedModel.  ght * modelScore);
      deta ls.add(Explanat on.match(
            ghtedScore, Str ng.format("model=%s score=%.6f   ght=%.3f useLog Score=%s",
          selectedModel.na , modelScore, selectedModel.  ght, useLog Score)));
      f nalScore +=   ghtedScore;
    }

    deta ls.add(Explanat on.match(
        f nalScore, Str ng.format("Total model-based score (h =%s)",  sH ), modelExplanat ons));
  }

  pr vate stat c f nal class SelectedModel {
    publ c f nal Str ng na ;
    publ c f nal double   ght;
    publ c f nal L ght  ghtL nearModel model;

    pr vate SelectedModel(Str ng na , double   ght, L ght  ghtL nearModel model) {
      t .na  = na ;
      t .  ght =   ght;
      t .model = model;
    }
  }
}
