package com.tw ter.search.earlyb rd_root;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.ut l.Future;

/**
 * F lter that returns a PART T ON_SK PPED response  nstead of send ng t  request to a part  on
 *  f t  part  on Part  onAccessController says  s d sabled for a request.
 */
publ c f nal class Sk pPart  onF lter extends
    S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Sk pPart  onF lter.class);

  pr vate f nal Str ng t erNa ;
  pr vate f nal  nt part  onNum;
  pr vate f nal Part  onAccessController controller;

  pr vate Sk pPart  onF lter(Str ng t erNa ,  nt part  onNum,
                             Part  onAccessController controller) {
    t .t erNa  = t erNa ;
    t .part  onNum = part  onNum;
    t .controller = controller;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {

    Earlyb rdRequest request = requestContext.getRequest();
     f (!controller.canAccessPart  on(t erNa , part  onNum, request.getCl ent d(),
        Earlyb rdRequestType.of(request))) {
      return Future.value(Earlyb rdServ ceScatterGat rSupport.newEmptyResponse());
    }

    return serv ce.apply(requestContext);
  }

  /**
   * Wrap t  serv ces w h a Sk pPart  onF lter
   */
  publ c stat c L st<Serv ce<Earlyb rdRequestContext, Earlyb rdResponse>> wrapServ ces(
      Str ng t erNa ,
      L st<Serv ce<Earlyb rdRequestContext, Earlyb rdResponse>> cl ents,
      Part  onAccessController controller) {

    LOG. nfo("Creat ng Sk pPart  onF lters for cluster: {}, t er: {}, part  ons 0-{}",
        controller.getClusterNa (), t erNa , cl ents.s ze() - 1);

    L st<Serv ce<Earlyb rdRequestContext, Earlyb rdResponse>> wrappedServ ces = new ArrayL st<>();
    for ( nt part  onNum = 0; part  onNum < cl ents.s ze(); part  onNum++) {
      Sk pPart  onF lter f lter = new Sk pPart  onF lter(t erNa , part  onNum, controller);
      wrappedServ ces.add(f lter.andT n(cl ents.get(part  onNum)));
    }

    return wrappedServ ces;
  }
}
