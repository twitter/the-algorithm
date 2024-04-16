package com.tw ter.search.common.relevance.features;

publ c class T etFeatures {
  pr vate f nal T etTextQual y t etTextQual y = new T etTextQual y();
  pr vate f nal T etTextFeatures t etTextFeatures = new T etTextFeatures();
  pr vate f nal T etUserFeatures t etUserFeatures = new T etUserFeatures();

  publ c T etTextFeatures getT etTextFeatures() {
    return t etTextFeatures;
  }

  publ c T etTextQual y getT etTextQual y() {
    return t etTextQual y;
  }

  publ c T etUserFeatures getT etUserFeatures() {
    return t etUserFeatures;
  }
}
