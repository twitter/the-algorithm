package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.RealGraphManhattanEndpo nt
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.storage.cl ent.manhattan.kv._
 mport com.tw ter.t  l nes.conf g.Conf gUt ls
 mport com.tw ter.ut l.Durat on
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object ManhattanCl entsModule extends Tw terModule w h Conf gUt ls {

  pr vate val ApolloDest = "/s/manhattan/apollo.nat ve-thr ft"
  pr vate f nal val T  out = "mh_real_graph.t  out"

  flag[Durat on](T  out, 150.m ll s, "T  out total")

  @Prov des
  @S ngleton
  @Na d(RealGraphManhattanEndpo nt)
  def prov desRealGraphManhattanEndpo nt(
    @Flag(T  out) t  out: Durat on,
    serv ce dent f er: Serv ce dent f er
  ): ManhattanKVEndpo nt = {
    lazy val cl ent = ManhattanKVCl ent(
      app d = "real_graph",
      dest = ApolloDest,
      mtlsParams = ManhattanKVCl entMtlsParams(serv ce dent f er = serv ce dent f er),
      label = "real-graph-data"
    )

    ManhattanKVEndpo ntBu lder(cl ent)
      .maxRetryCount(2)
      .defaultMaxT  out(t  out)
      .bu ld()
  }
}
