package com.tw ter.search.earlyb rd_root;

 mport scala.Part alFunct on;
 mport scala.runt  .AbstractPart alFunct on;

 mport com.tw ter.f nagle.serv ce.ReqRep;
 mport com.tw ter.f nagle.serv ce.ResponseClass;
 mport com.tw ter.f nagle.serv ce.ResponseClasses;
 mport com.tw ter.f nagle.serv ce.ResponseClass f er;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.ut l.Try;

publ c class RootResponseClass f er extends AbstractPart alFunct on<ReqRep, ResponseClass> {
  pr vate stat c f nal Part alFunct on<ReqRep, ResponseClass> DEFAULT_CLASS F ER =
      ResponseClass f er.Default();

  pr vate stat c f nal SearchRateCounter NOT_EARLYB RD_REQUEST_COUNTER =
      SearchRateCounter.export("response_class f er_not_earlyb rd_request");
  pr vate stat c f nal SearchRateCounter NOT_EARLYB RD_RESPONSE_COUNTER =
      SearchRateCounter.export("response_class f er_not_earlyb rd_response");
  pr vate stat c f nal SearchRateCounter NON_RETRYABLE_FA LURE_COUNTER =
      SearchRateCounter.export("response_class f er_non_retryable_fa lure");
  pr vate stat c f nal SearchRateCounter RETRYABLE_FA LURE_COUNTER =
      SearchRateCounter.export("response_class f er_retryable_fa lure");
  pr vate stat c f nal SearchRateCounter SUCCESS_COUNTER =
      SearchRateCounter.export("response_class f er_success");

  @Overr de
  publ c boolean  sDef nedAt(ReqRep reqRep) {
     f (!(reqRep.request()  nstanceof Earlyb rdServ ce.search_args)) {
      NOT_EARLYB RD_REQUEST_COUNTER. ncre nt();
      return false;
    }

     f (!reqRep.response(). sThrow() && (!(reqRep.response().get()  nstanceof Earlyb rdResponse))) {
      NOT_EARLYB RD_RESPONSE_COUNTER. ncre nt();
      return false;
    }

    return true;
  }

  @Overr de
  publ c ResponseClass apply(ReqRep reqRep) {
    Try<?> responseTry = reqRep.response();
     f (responseTry. sThrow()) {
      return DEFAULT_CLASS F ER.apply(reqRep);
    }

    //  sDef nedAt() guarantees that t  response  s an Earlyb rdResponse  nstance.
    Earlyb rdResponseCode responseCode = ((Earlyb rdResponse) responseTry.get()).getResponseCode();
    sw ch (responseCode) {
      case PART T ON_NOT_FOUND:
      case PART T ON_D SABLED:
      case PERS STENT_ERROR:
        NON_RETRYABLE_FA LURE_COUNTER. ncre nt();
        return ResponseClasses.NON_RETRYABLE_FA LURE;
      case TRANS ENT_ERROR:
        RETRYABLE_FA LURE_COUNTER. ncre nt();
        return ResponseClasses.RETRYABLE_FA LURE;
      default:
        SUCCESS_COUNTER. ncre nt();
        return ResponseClasses.SUCCESS;
    }
  }
}
