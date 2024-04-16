package com.tw ter.search.feature_update_serv ce.ut l;


 mport javax.annotat on.Nullable;

 mport com.tw ter.search.common.sc ma.base.Thr ftDocu ntUt l;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateRequest;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateResponse;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateResponseCode;

publ c f nal class FeatureUpdateVal dator {

  pr vate FeatureUpdateVal dator() { }

  /**
   * Val dates FeatureUpdateRequest
   * @param featureUpdate  nstance of FeatureUpdateRequest w h Thr ft ndex ngEvent
   * @return null  f val d,  nstance of FeatureUpdateResponse  f not.
   * Response w ll have appropr ate error code and  ssage set.
   */
  @Nullable
  publ c stat c FeatureUpdateResponse val date(FeatureUpdateRequest featureUpdate) {

     f (Thr ftDocu ntUt l.hasDupl cateF elds(featureUpdate.getEvent().getDocu nt())) {
      return createResponse(
          Str ng.format("dupl cate docu nt f elds: %s", featureUpdate.toStr ng()));
    }
     f (!featureUpdate.getEvent(). sSetU d()) {
      return createResponse(Str ng.format("unset u d: %s", featureUpdate.toStr ng()));
    }

    return null;
  }

  pr vate stat c FeatureUpdateResponse createResponse(Str ng errorMsg) {
    FeatureUpdateResponseCode responseCode = FeatureUpdateResponseCode.CL ENT_ERROR;
    FeatureUpdateResponse response = new FeatureUpdateResponse(responseCode);
    response.setDeta l ssage(errorMsg);
    return response;
  }
}
