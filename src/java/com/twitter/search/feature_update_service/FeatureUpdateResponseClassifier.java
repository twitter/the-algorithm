package com.tw ter.search.feature_update_serv ce;

 mport scala.runt  .AbstractPart alFunct on;

 mport com.tw ter.f nagle.serv ce.ReqRep;
 mport com.tw ter.f nagle.serv ce.ResponseClass;
 mport com.tw ter.f nagle.serv ce.ResponseClass f er;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateResponse;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateResponseCode;
 mport com.tw ter.ut l.Try;

publ c class FeatureUpdateResponseClass f er
    extends AbstractPart alFunct on<ReqRep, ResponseClass> {
  @Overr de
  publ c boolean  sDef nedAt(ReqRep tuple) {
    return true;
  }

  @Overr de
  publ c ResponseClass apply(ReqRep reqRep) {
    Try<Object> f nagleResponse = reqRep.response();
     f (f nagleResponse. sThrow()) {
      return ResponseClass f er.Default().apply(reqRep);
    }
    FeatureUpdateResponse response = (FeatureUpdateResponse) f nagleResponse.apply();
    FeatureUpdateResponseCode responseCode = response.getResponseCode();
    sw ch (responseCode) {
      case TRANS ENT_ERROR:
      case SERVER_T MEOUT_ERROR:
        return ResponseClass.RetryableFa lure();
      case PERS STENT_ERROR:
        return ResponseClass.NonRetryableFa lure();
      // Cl ent cancellat ons don't necessar ly  an fa lures on   end. T  cl ent dec ded to
      // cancel t  request (for example   t  d out, so t y sent a dupl cate request etc.),
      // so let's treat t m as successes.
      case CL ENT_CANCEL_ERROR:
      default:
        // T  ot r response codes are cl ent errors, and success, and  n those cases t  server
        // behaved correctly, so   class fy   as a success.
        return ResponseClass.Success();
    }
  }
}
