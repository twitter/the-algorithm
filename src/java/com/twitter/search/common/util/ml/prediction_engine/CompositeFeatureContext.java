package com.tw ter.search.common.ut l.ml.pred ct on_eng ne;

 mport java.ut l.funct on.Suppl er;
 mport javax.annotat on.Nullable;

 mport com.tw ter.ml.ap .FeatureContext;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc ma;

/**
 * An object to store feature context  nformat on to bu ld models w h.
 */
publ c class Compos eFeatureContext {
  // legacy stat c feature context
  pr vate f nal FeatureContext legacyContext;
  // a suppl er for t  context ( ll t  sc ma  self) of t  sc ma-based features
  pr vate f nal Suppl er<Thr ftSearchFeatureSc ma> sc maSuppl er;

  publ c Compos eFeatureContext(
      FeatureContext legacyContext,
      @Nullable Suppl er<Thr ftSearchFeatureSc ma> sc maSuppl er) {
    t .legacyContext = legacyContext;
    t .sc maSuppl er = sc maSuppl er;
  }

  FeatureContext getLegacyContext() {
    return legacyContext;
  }

  Thr ftSearchFeatureSc ma getFeatureSc ma() {
     f (sc maSuppl er == null) {
      throw new UnsupportedOperat onExcept on("Feature sc ma was not  n  al zed");
    }
    return sc maSuppl er.get();
  }
}
