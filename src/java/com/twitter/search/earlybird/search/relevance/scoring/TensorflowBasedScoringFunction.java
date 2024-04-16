package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport java. o. OExcept on;
 mport java.n o.FloatBuffer;
 mport java.ut l.HashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect. mmutableMap;

 mport org.apac .lucene.search.Explanat on;
 mport org.tensorflow.Tensor;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftQueryS ce;
 mport com.tw ter.search.common.features.Earlyb rdRank ngDer vedFeature;
 mport com.tw ter.search.common.features.FeatureHandler;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchResultFeatures;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.ut l.ml.tensorflow_eng ne.TensorflowModelsManager;
 mport com.tw ter.search.earlyb rd.Earlyb rdSearc r;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.except on.Cl entExcept on;
 mport com.tw ter.search.earlyb rd.search.Ant Gam ngF lter;
 mport com.tw ter.search.earlyb rd.search.relevance.L nearScor ngData;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRelevanceOpt ons;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultType;
 mport com.tw ter.search.model ng.common.T etFeaturesUt ls;
 mport com.tw ter.tfcompute_java.TFModelRunner;

/**
 * TensorflowBasedScor ngFunct on rel es on a TF model for scor ng t ets
 * Only t  `batchScore` part  s  mple nted
 */
publ c class TensorflowBasedScor ngFunct on extends FeatureBasedScor ngFunct on {
  pr vate f nal TFModelRunner tfModelRunner;

  // https://stackoverflow.com/quest ons/37849322/how-to-understand-t -term-tensor- n-tensorflow
  // for more  nformat on on t  notat on -  n short, a TF graph  s made
  // of TF operat ons and doesn't have a f rst order not on of tensors
  // T  notat on <operat on>:< ndex> w ll maps to t  < ndex> output of t 
  // <operat on> conta ned  n t  TF graph.
  pr vate stat c f nal Str ng  NPUT_VALUES = " nput_sparse_tensor_values:0";
  pr vate stat c f nal Str ng  NPUT_ ND CES = " nput_sparse_tensor_ nd ces:0";
  pr vate stat c f nal Str ng  NPUT_SHAPE = " nput_sparse_tensor_shape:0";
  pr vate stat c f nal Str ng OUTPUT_NODE = "output_scores:0";

  pr vate f nal Map< nteger, Long> featureSc ma dToMlAp  d;
  pr vate f nal Map<Long, Float> t et dToScoreMap = new HashMap<>();
  pr vate f nal Earlyb rdRequest request;

  publ c TensorflowBasedScor ngFunct on(
      Earlyb rdRequest request,
       mmutableSc ma nterface sc ma,
      Thr ftSearchQuery searchQuery,
      Ant Gam ngF lter ant Gam ngF lter,
      Thr ftSearchResultType searchResultType,
      UserTable userTable,
      TensorflowModelsManager tensorflowModelsManager
      ) throws  OExcept on, Cl entExcept on {
    super(
      "TensorflowBasedScor ngFunct on",
      sc ma,
      searchQuery,
      ant Gam ngF lter,
      searchResultType,
        userTable
    );
    t .request = request;
    Str ng modelNa  = searchQuery.getRelevanceOpt ons().getRank ngParams().selectedTensorflowModel;
    t .featureSc ma dToMlAp  d = tensorflowModelsManager.getFeatureSc ma dToMlAp  d();

     f (modelNa  == null) {
      throw new Cl entExcept on("Scor ng type  s TENSORFLOW_BASED but no model was selected");
    } else  f (!tensorflowModelsManager.getModel(modelNa ). sPresent()) {
      throw new Cl entExcept on(
        "Scor ng type  s TENSORFLOW_BASED. Model "
        + modelNa 
        + "  s not present."
      );
    }

     f (searchQuery.getRelevanceOpt ons().getRank ngParams(). sEnableH Demot on()) {
      throw new Cl entExcept on(
          "H  attr bute demot on  s not supported w h TENSORFLOW_BASED scor ng type");
    }

    tfModelRunner = tensorflowModelsManager.getModel(modelNa ).get();
  }

  /**
   * S ngle  em scor ng just returns t  lucene score to be used dur ng t  batch ng phase.
   */
  @Overr de
  protected float score(float luceneQueryScore) {
    return luceneQueryScore;
  }

  @Overr de
  publ c Pa r<L nearScor ngData, Thr ftSearchResultFeatures> collectFeatures(
      float luceneQueryScore) throws  OExcept on {
    L nearScor ngData l nearScor ngData = updateL nearScor ngData(luceneQueryScore);
    Thr ftSearchResultFeatures features =
        createFeaturesForDocu nt(l nearScor ngData, true).getFeatures();

    return new Pa r<>(l nearScor ngData, features);
  }

  @Overr de
  protected FeatureHandler createFeaturesForDocu nt(
      L nearScor ngData l nearScor ngData,
      boolean  gnoreDefaultValues) throws  OExcept on {
    return super.createFeaturesForDocu nt(l nearScor ngData,
             gnoreDefaultValues)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_TREND_CL CK,
            request.queryS ce == Thr ftQueryS ce.TREND_CL CK)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_TYPED_QUERY,
            request.queryS ce == Thr ftQueryS ce.TYPED_QUERY)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_TYPEAHEAD_CL CK,
            request.queryS ce == Thr ftQueryS ce.TYPEAHEAD_CL CK)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_HASHTAG_CL CK,
            request.queryS ce == Thr ftQueryS ce.RECENT_SEARCH_CL CK)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_RECENT_SEARCH_CL CK,
            request.queryS ce == Thr ftQueryS ce.RECENT_SEARCH_CL CK)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_PROF LE_CL CK,
            request.queryS ce == Thr ftQueryS ce.PROF LE_CL CK)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_AP _CALL,
            request.queryS ce == Thr ftQueryS ce.AP _CALL)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_PROMOTED_TREND_CL CK,
            request.queryS ce == Thr ftQueryS ce.PROMOTED_TREND_CL CK)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_SAVED_SEARCH_CL CK,
            request.queryS ce == Thr ftQueryS ce.SAVED_SEARCH_CL CK)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_CASHTAG_CL CK,
            request.queryS ce == Thr ftQueryS ce.CASHTAG_CL CK)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_SPELL NG_EXPANS ON_REVERT_CL CK,
            request.queryS ce == Thr ftQueryS ce.SPELL NG_EXPANS ON_REVERT_CL CK)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_SPELL NG_SUGGEST ON_CL CK,
            request.queryS ce == Thr ftQueryS ce.SPELL NG_SUGGEST ON_CL CK)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_LOGGED_OUT_HOME_TREND_CL CK,
            request.queryS ce == Thr ftQueryS ce.LOGGED_OUT_HOME_TREND_CL CK)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_RELATED_QUERY_CL CK,
            request.queryS ce == Thr ftQueryS ce.RELATED_QUERY_CL CK)
        .addBoolean(Earlyb rdRank ngDer vedFeature.QUERY_SOURCE_AUTO_SPELL_CORRECT_REVERT_CL CK,
            request.queryS ce == Thr ftQueryS ce.AUTO_SPELL_CORRECT_REVERT_CL CK);
  }

  /**
   * Return scores computed  n batchScore()  f forExplanat on  s true.
   */
  @Overr de
  protected double computeScore(L nearScor ngData data, boolean forExplanat on) {
    Precond  ons.c ckState(forExplanat on,
        "forExplanat on  s false. computeScore() should only be used for explanat on creat on");
    return t et dToScoreMap.get(t et DMapper.getT et D(getCurrentDoc D()));
  }

  @Overr de
  protected vo d generateExplanat onForScor ng(
      L nearScor ngData scor ngData, boolean  sH , L st<Explanat on> deta ls) {
  }

  @V s bleForTest ng
  SparseTensor create nputTensor(Thr ftSearchResultFeatures[] featuresForDocs) {
    // Mov ng t  across outs de of t  request path
    // would reduce t  allocat on cost and make t  `ByteBuffer`s
    // long l ved - would need one per thread.
    SparseTensor sparseTensor =
        new SparseTensor(featuresForDocs.length, featureSc ma dToMlAp  d.s ze());
    for (Thr ftSearchResultFeatures features : featuresForDocs) {
      updateSparseTensor(sparseTensor, features);
    }
    return sparseTensor;
  }

  pr vate vo d addSc maBooleanFeatures(SparseTensor sparseTensor,
                                        Map< nteger, Boolean> booleanMap) {
     f (booleanMap == null || booleanMap. sEmpty()) {
      return;
    }
    for (Map.Entry< nteger, Boolean> entry : booleanMap.entrySet()) {
      Precond  ons.c ckState(featureSc ma dToMlAp  d.conta nsKey(entry.getKey()));
      sparseTensor.addValue(
          featureSc ma dToMlAp  d.get(entry.getKey()), entry.getValue() ? 1f : 0f);
    }
  }

  pr vate vo d addSc maCont nuousFeatures(SparseTensor sparseTensor,
                                           Map< nteger, ? extends Number> valueMap) {
     f (valueMap == null || valueMap. sEmpty()) {
      return;
    }
    for (Map.Entry< nteger, ? extends Number> entry : valueMap.entrySet()) {
       nteger  d = entry.getKey();
      // SEARCH-26795
       f (!T etFeaturesUt ls. sFeatureD screte( d)) {
        Precond  ons.c ckState(featureSc ma dToMlAp  d.conta nsKey( d));
        sparseTensor.addValue(
            featureSc ma dToMlAp  d.get( d), entry.getValue().floatValue());
      }
    }
  }

  pr vate vo d updateSparseTensor(SparseTensor sparseTensor, Thr ftSearchResultFeatures features) {
    addSc maBooleanFeatures(sparseTensor, features.getBoolValues());
    addSc maCont nuousFeatures(sparseTensor, features.get ntValues());
    addSc maCont nuousFeatures(sparseTensor, features.getLongValues());
    addSc maCont nuousFeatures(sparseTensor, features.getDoubleValues());

    sparseTensor. ncNumRecordsSeen();
  }

  pr vate float[] batchScore nternal(Thr ftSearchResultFeatures[] featuresForDocs) {
     nt nbDocs = featuresForDocs.length;
    float[] back ngArrayResults = new float[nbDocs];
    SparseTensor sparseTensor = create nputTensor(featuresForDocs);
    Tensor<?> sparseValues =
      Tensor.create(
        Float.class,
        sparseTensor.getSparseValuesShape(),
        sparseTensor.getSparseValues());
    Tensor<?> sparse nd ces =
      Tensor.create(
        Long.class,
        sparseTensor.getSparse nd cesShape(),
        sparseTensor.getSparse nd ces());
    Tensor<?> sparseShape =
      Tensor.create(
        Long.class,
        sparseTensor.getSparseShapeShape(),
        sparseTensor.getSparseShape());
    Map<Str ng, Tensor<?>>  nputMap =  mmutableMap.of(
       NPUT_VALUES, sparseValues,
       NPUT_ ND CES, sparse nd ces,
       NPUT_SHAPE, sparseShape
      );
    L st<Str ng> output =  mmutableL st.of(OUTPUT_NODE);

    Map<Str ng, Tensor<?>> outputs = tfModelRunner.run(
       nputMap,
      output,
       mmutableL st.of()
    );
    Tensor<?> outputTensor = outputs.get(OUTPUT_NODE);
    try {
      FloatBuffer f nalResultBuffer =
        FloatBuffer.wrap(back ngArrayResults, 0, nbDocs);

      outputTensor.wr eTo(f nalResultBuffer);
    } f nally {
      // Close tensors to avo d  mory leaks
      sparseValues.close();
      sparse nd ces.close();
      sparseShape.close();
       f (outputTensor != null) {
        outputTensor.close();
      }
    }
    return back ngArrayResults;
  }

  /**
   * Compute t  score for a l st of h s. Not thread safe.
   * @return Array of scores
   */
  @Overr de
  publ c float[] batchScore(L st<BatchH > h s) throws  OExcept on {
    Thr ftSearchResultFeatures[] featuresForDocs = new Thr ftSearchResultFeatures[h s.s ze()];

    for ( nt   = 0;   < h s.s ze();  ++) {
      // T   s a g gant c allocat on, but t  models are tra ned to depend on unset values hav ng
      // a default.
      BatchH  h  = h s.get( );
      Thr ftSearchResultFeatures features = h .getFeatures().deepCopy();

      // Adjust features of a h  based on overr des prov ded by relevance opt ons. Should mostly
      // be used for debugg ng purposes.
      adjustH Scor ngFeatures(h , features);

      setDefaultFeatureValues(features);
      featuresForDocs[ ] = features;
    }

    float[] scores = batchScore nternal(featuresForDocs);
    float[] f nalScores = new float[h s.s ze()];

    for ( nt   = 0;   < h s.s ze();  ++) {
      L nearScor ngData data = h s.get( ).getScor ngData();
       f (data.sk pReason != null && data.sk pReason != L nearScor ngData.Sk pReason.NOT_SK PPED) {
        //  f t  h  should be sk pped, overwr e t  score w h SK P_H T
        scores[ ] = SK P_H T;
      }

      //  f explanat ons enabled, Add scores to map. W ll be used  n computeScore()
       f (Earlyb rdSearc r.explanat onsEnabled(debugMode)) {
        t et dToScoreMap.put(h s.get( ).getT et D(), scores[ ]);
      }

      f nalScores[ ] = postScoreComputat on(
          data,
          scores[ ],
          false,  // cannot get t  h  attr but on  nfo for t  h  at t  po nt  n t  
          null);
    }
    return f nalScores;
  }

  pr vate vo d adjustH Scor ngFeatures(BatchH  h , Thr ftSearchResultFeatures features) {

     f (request. sSetSearchQuery() && request.getSearchQuery(). sSetRelevanceOpt ons()) {
      Thr ftSearchRelevanceOpt ons relevanceOpt ons =
          request.getSearchQuery().getRelevanceOpt ons();

       f (relevanceOpt ons. sSetPerT etFeaturesOverr de()
          && relevanceOpt ons.getPerT etFeaturesOverr de().conta nsKey(h .getT et D())) {
        overr deFeatureValues(
            features,
            relevanceOpt ons.getPerT etFeaturesOverr de().get(h .getT et D()));
      }

       f (relevanceOpt ons. sSetPerUserFeaturesOverr de()
          && relevanceOpt ons.getPerUserFeaturesOverr de().conta nsKey(
              h .getScor ngData().fromUser d)) {
        overr deFeatureValues(
            features,
            relevanceOpt ons.getPerUserFeaturesOverr de().get(h .getScor ngData().fromUser d));
      }

       f (relevanceOpt ons. sSetGlobalFeaturesOverr de()) {
        overr deFeatureValues(
            features, relevanceOpt ons.getGlobalFeaturesOverr de());
      }
    }
  }
}
