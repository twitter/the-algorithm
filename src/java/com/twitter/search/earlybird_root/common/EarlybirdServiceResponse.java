package com.tw ter.search.earlyb rd_root.common;

 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;

/**
 * A class that wraps an Earlyb rdResponse and a flag that determ nes  f a request was sent to a
 * serv ce.
 */
publ c f nal class Earlyb rdServ ceResponse {
  publ c stat c enum Serv ceState {
    // T  serv ce was called (or w ll be called).
    SERV CE_CALLED(true),

    // T  serv ce  s not ava lable (turned off by a dec der, for example).
    SERV CE_NOT_AVA LABLE(false),

    // T  cl ent d d not request results from t  serv ce.
    SERV CE_NOT_REQUESTED(false),

    // T  serv ce  s ava lable and t  cl ent wants results from t  serv ce, but t  serv ce
    // was not called (because   got enough results from ot r serv ces, for example).
    SERV CE_NOT_CALLED(false);

    pr vate f nal boolean serv ceWasCalled;

    pr vate Serv ceState(boolean serv ceWasCalled) {
      t .serv ceWasCalled = serv ceWasCalled;
    }

    publ c boolean serv ceWasCalled() {
      return serv ceWasCalled;
    }

    publ c boolean serv ceWasRequested() {
      return t  != SERV CE_NOT_REQUESTED;
    }

  }

  pr vate f nal Earlyb rdResponse earlyb rdResponse;
  pr vate f nal Serv ceState serv ceState;

  pr vate Earlyb rdServ ceResponse(@Nullable Earlyb rdResponse earlyb rdResponse,
                                   Serv ceState serv ceState) {
    t .earlyb rdResponse = earlyb rdResponse;
    t .serv ceState = serv ceState;
     f (!serv ceState.serv ceWasCalled()) {
      Precond  ons.c ckArgu nt(earlyb rdResponse == null);
    }
  }

  /**
   * Creates a new Earlyb rdServ ceResponse  nstance,  nd cat ng that t  serv ce was not called.
   *
   * @param serv ceState T  state of t  serv ce.
   * @return a new Earlyb rdServ ceResponse  nstance,  nd cat ng that t  serv ce was not called.
   */
  publ c stat c Earlyb rdServ ceResponse serv ceNotCalled(Serv ceState serv ceState) {
    Precond  ons.c ckArgu nt(!serv ceState.serv ceWasCalled());
    return new Earlyb rdServ ceResponse(null, serv ceState);
  }

  /**
   * Creates a new Earlyb rdServ ceResponse  nstance that wraps t  g ven earlyb rd response.
   *
   * @param earlyb rdResponse T  Earlyb rdResponse  nstance returned by t  serv ce.
   * @return a new Earlyb rdServ ceResponse  nstance that wraps t  g ven earlyb rd response.
   */
  publ c stat c Earlyb rdServ ceResponse serv ceCalled(Earlyb rdResponse earlyb rdResponse) {
    return new Earlyb rdServ ceResponse(earlyb rdResponse, Serv ceState.SERV CE_CALLED);
  }

  /** Returns t  wrapped earlyb rd response. */
  @Nullable
  publ c Earlyb rdResponse getResponse() {
    return earlyb rdResponse;
  }

  /** Returns t  state of t  serv ce. */
  publ c Serv ceState getServ ceState() {
    return serv ceState;
  }
}
