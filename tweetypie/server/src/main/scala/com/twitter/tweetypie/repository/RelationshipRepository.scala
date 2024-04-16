package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.soc algraph.thr ftscala._
 mport com.tw ter.st ch.SeqGroup
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.compat.LegacySeqGroup

object Relat onsh pKey {
  def blocks(s ce d: User d, dest nat on d: User d): Relat onsh pKey =
    Relat onsh pKey(s ce d, dest nat on d, Relat onsh pType.Block ng)

  def follows(s ce d: User d, dest nat on d: User d): Relat onsh pKey =
    Relat onsh pKey(s ce d, dest nat on d, Relat onsh pType.Follow ng)

  def mutes(s ce d: User d, dest nat on d: User d): Relat onsh pKey =
    Relat onsh pKey(s ce d, dest nat on d, Relat onsh pType.Mut ng)

  def reported(s ce d: User d, dest nat on d: User d): Relat onsh pKey =
    Relat onsh pKey(s ce d, dest nat on d, Relat onsh pType.ReportedAsSpam)
}

case class Relat onsh pKey(
  s ce d: User d,
  dest nat on d: User d,
  relat onsh p: Relat onsh pType) {
  def asEx stsRequest: Ex stsRequest =
    Ex stsRequest(
      s ce = s ce d,
      target = dest nat on d,
      relat onsh ps = Seq(Relat onsh p(relat onsh p))
    )
}

object Relat onsh pRepos ory {
  type Type = Relat onsh pKey => St ch[Boolean]

  def apply(
    ex sts: FutureArrow[(Seq[Ex stsRequest], Opt on[RequestContext]), Seq[Ex stsResult]],
    maxRequestS ze:  nt
  ): Type = {
    val relat onsh pGroup: SeqGroup[Relat onsh pKey, Boolean] =
      new SeqGroup[Relat onsh pKey, Boolean] {
        overr de def run(keys: Seq[Relat onsh pKey]): Future[Seq[Try[Boolean]]] =
          LegacySeqGroup.l ftToSeqTry(
            ex sts((keys.map(_.asEx stsRequest), None)).map(_.map(_.ex sts)))
        overr de val maxS ze:  nt = maxRequestS ze
      }

    relat onsh pKey => St ch.call(relat onsh pKey, relat onsh pGroup)
  }
}
