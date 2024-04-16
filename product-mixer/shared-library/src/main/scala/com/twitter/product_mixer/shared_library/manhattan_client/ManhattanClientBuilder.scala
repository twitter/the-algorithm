package com.tw ter.product_m xer.shared_l brary.manhattan_cl ent

 mport com.tw ter.f nagle.mtls.aut nt cat on.EmptyServ ce dent f er
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.ssl.Opportun st cTls
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.manhattan.v1.{thr ftscala => mh}
 mport com.tw ter.storage.cl ent.manhattan.kv.Exper  nts
 mport com.tw ter.storage.cl ent.manhattan.kv.Exper  nts.Exper  nt
 mport com.tw ter.storage.cl ent.manhattan.kv.Guarantee
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl ent
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVEndpo nt
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVEndpo ntBu lder
 mport com.tw ter.storage.cl ent.manhattan.kv.NoMtlsParams
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanCluster
 mport com.tw ter.ut l.Durat on

object ManhattanCl entBu lder {

  /**
   * Bu ld a ManhattanKVCl ent/Endpo nt [[ManhattanKVEndpo nt]] / [[ManhattanKVCl ent]]
   *
   * @param cluster Manhattan cluster
   * @param app d Manhattan app d
   * @param numTr es Max number of t  s to try
   * @param maxT  out Max request t  out
   * @param max emsPerRequest Max  ems per request
   * @param guarantee Cons stency guarantee
   * @param serv ce dent f er Serv ce  D used to S2S Auth
   * @param statsRece ver Stats
   * @param exper  nts MH cl ent exper  nts to  nclude
   * @return ManhattanKVEndpo nt
   */
  def bu ldManhattanEndpo nt(
    cluster: ManhattanCluster,
    app d: Str ng,
    numTr es:  nt,
    maxT  out: Durat on,
    guarantee: Guarantee,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver,
    max emsPerRequest:  nt = 100,
    exper  nts: Seq[Exper  nt] = Seq(Exper  nts.ApertureLoadBalancer)
  ): ManhattanKVEndpo nt = {
    val cl ent = bu ldManhattanCl ent(
      cluster,
      app d,
      serv ce dent f er,
      exper  nts
    )

    ManhattanKVEndpo ntBu lder(cl ent)
      .defaultGuarantee(guarantee)
      .defaultMaxT  out(maxT  out)
      .maxRetryCount(numTr es)
      .max emsPerRequest(max emsPerRequest)
      .statsRece ver(statsRece ver)
      .bu ld()
  }

  /**
   *  Bu ld a ManhattanKVCl ent
   *
   * @param cluster Manhattan cluster
   * @param app d   Manhattan app d
   * @param serv ce dent f er Serv ce  D used to S2S Auth
   * @param exper  nts MH cl ent exper  nts to  nclude
   *
   * @return ManhattanKVCl ent
   */
  def bu ldManhattanCl ent(
    cluster: ManhattanCluster,
    app d: Str ng,
    serv ce dent f er: Serv ce dent f er,
    exper  nts: Seq[Exper  nt] = Seq(Exper  nts.ApertureLoadBalancer)
  ): ManhattanKVCl ent = {
    val mtlsParams = serv ce dent f er match {
      case EmptyServ ce dent f er => NoMtlsParams
      case serv ce dent f er =>
        ManhattanKVCl entMtlsParams(
          serv ce dent f er = serv ce dent f er,
          opportun st cTls = Opportun st cTls.Requ red)
    }

    val label = s"manhattan/${cluster.pref x}"

    new ManhattanKVCl ent(
      app d = app d,
      dest = cluster.w lyNa ,
      mtlsParams = mtlsParams,
      label = label,
      exper  nts = exper  nts
    )
  }

  def bu ldManhattanV1F nagleCl ent(
    cluster: ManhattanCluster,
    serv ce dent f er: Serv ce dent f er,
    exper  nts: Seq[Exper  nt] = Seq(Exper  nts.ApertureLoadBalancer)
  ): mh.ManhattanCoord nator. thodPerEndpo nt = {
    val mtlsParams = serv ce dent f er match {
      case EmptyServ ce dent f er => NoMtlsParams
      case serv ce dent f er =>
        ManhattanKVCl entMtlsParams(
          serv ce dent f er = serv ce dent f er,
          opportun st cTls = Opportun st cTls.Requ red)
    }

    val label = s"manhattan/${cluster.pref x}"

    Exper  nts
      .cl entW hExper  nts(exper  nts, mtlsParams)
      .bu ld[mh.ManhattanCoord nator. thodPerEndpo nt](cluster.w lyNa , label)
  }
}
