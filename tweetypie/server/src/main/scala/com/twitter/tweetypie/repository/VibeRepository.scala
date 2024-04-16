package com.tw ter.t etyp e.repos ory

 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._
 mport com.tw ter.v bes.thr ftscala.V beV2

object V beRepos ory {
  type Type = T et => St ch[Opt on[V beV2]]

  val column = "v bes/v be.T et"
  case class V beV ew(v e r d: Opt on[Long])

  /**
   * Creates a funct on that appl es t  v bes/v be.T et strato column fetch on t  g ven
   * T et. Strato column s ce: go/v be.strato
   * @param cl ent Strato cl ent
   * @return
   */
  def apply(cl ent: StratoCl ent): Type = {
    val fetc r: Fetc r[Long, V beV ew, V beV2] =
      cl ent.fetc r[Long, V beV ew, V beV2](column)
    t et =>
      fetc r
        .fetch(t et. d, V beV ew(None))
        .map(_.v)
  }
}
