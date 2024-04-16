package com.tw ter.search.earlyb rd_root.cach ng;

 mport com.google.common.base.Opt onal;

 mport com.tw ter.search.common.cach ng.f lter.Cac PostProcessor;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c class Earlyb rdCac PostProcessor
    extends Cac PostProcessor<Earlyb rdRequestContext, Earlyb rdResponse> {

  @Overr de
  publ c f nal vo d recordCac H (Earlyb rdResponse response) {
    response.setCac H (true);
  }

  @Overr de
  publ c Opt onal<Earlyb rdResponse> processCac Response(Earlyb rdRequestContext or g nalRequest,
                                                          Earlyb rdResponse cac Response) {
    return Opt onal.of(cac Response);
  }
}
