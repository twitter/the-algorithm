package com.tw ter.search.earlyb rd.common;

 mport javax. nject. nject;
 mport javax. nject.S ngleton;

 mport org.apac .thr ft.protocol.TProtocolFactory;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.common.ut l.thr ft.Thr ftToBytesF lter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;

@S ngleton
publ c class Earlyb rdThr ftBackend extends Earlyb rdServ ce.Serv ceToCl ent {

  /**
   * Wrapp ng t  bytes svc back to a Earlyb rdServ ce.Serv ceToCl ent, wh ch
   *  s a Earlyb rdServ ce.Serv ce face aga n.
   */
  @ nject
  publ c Earlyb rdThr ftBackend(
      Thr ftToBytesF lter thr ftToBytesF lter,
      Serv ce<byte[], byte[]> byteServ ce,
      TProtocolFactory protocolFactory) {

    super(thr ftToBytesF lter.andT n(byteServ ce), protocolFactory);
  }

}
