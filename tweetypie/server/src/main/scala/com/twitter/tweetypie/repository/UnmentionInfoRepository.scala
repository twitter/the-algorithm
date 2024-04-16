package com.tw ter.t etyp e.repos ory

 mport com.tw ter.consu r_pr vacy. nt on_controls.thr ftscala.Un nt on nfo
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._

object Un nt on nfoRepos ory {
  type Type = T et => St ch[Opt on[Un nt on nfo]]

  val column = "consu r-pr vacy/ nt ons-manage nt/un nt on nfoFromT et"
  case class Un nt on nfoV ew(asV e r: Opt on[Long])

  /**
   * Creates a funct on that extracts users f elds from a t et and c cks
   *  f t  extracted users have been un nt oned from t  t et's asssoc ated conversat on.
   * T  funct on enables t  prefetch cach ng of Un nt on nfo used by graphql dur ng createT et
   * events and m rrors t  log c found  n t  un nt on nfo Strato column found
   *  re: http://go/un nt on nfo.strato
   * @param cl ent Strato cl ent
   * @return
   */
  def apply(cl ent: StratoCl ent): Type = {
    val fetc r: Fetc r[T et, Un nt on nfoV ew, Un nt on nfo] =
      cl ent.fetc r[T et, Un nt on nfoV ew, Un nt on nfo](column)

    t et =>
      t et.coreData.flatMap(_.conversat on d) match {
        case So (conversat on d) =>
          val v e rUser d = Tw terContext().flatMap(_.user d)
          fetc r
            .fetch(t et, Un nt on nfoV ew(v e rUser d))
            .map(_.v)
        case _ => St ch.None
      }
  }
}
