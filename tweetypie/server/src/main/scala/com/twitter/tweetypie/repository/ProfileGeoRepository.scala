package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.dataproducts.enr ch nts.thr ftscala._
 mport com.tw ter.g zmoduck.thr ftscala.UserResponseState._
 mport com.tw ter.st ch.SeqGroup
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.compat.LegacySeqGroup
 mport com.tw ter.t etyp e.backends.Gn pEnr c rator
 mport com.tw ter.t etyp e.thr ftscala.GeoCoord nates

case class Prof leGeoKey(t et d: T et d, user d: Opt on[User d], coords: Opt on[GeoCoord nates]) {
  def key: T etData =
    T etData(
      t et d = t et d,
      user d = user d,
      coord nates = coords.map(Prof leGeoRepos ory.convertGeo)
    )
}

object Prof leGeoRepos ory {
  type Type = Prof leGeoKey => St ch[Prof leGeoEnr ch nt]

  case class UnexpectedState(state: Enr ch ntHydrat onState) extends Except on(state.na )

  def convertGeo(coords: GeoCoord nates): T etyP eGeoCoord nates =
    T etyP eGeoCoord nates(
      lat ude = coords.lat ude,
      long ude = coords.long ude,
      geoPrec s on = coords.geoPrec s on,
      d splay = coords.d splay
    )

  def apply(hydrateProf leGeo: Gn pEnr c rator.HydrateProf leGeo): Type = {
     mport Enr ch ntHydrat onState._

    val emptyEnr ch ntSt ch = St ch.value(Prof leGeoEnr ch nt())

    val prof leGeoGroup = SeqGroup[T etData, Prof leGeoResponse] { keys: Seq[T etData] =>
      // Gn p  gnores wr ePath and treats all requests as reads
      LegacySeqGroup.l ftToSeqTry(
        hydrateProf leGeo(Prof leGeoRequest(requests = keys, wr ePath = false))
      )
    }

    (geoKey: Prof leGeoKey) =>
      St ch
        .call(geoKey.key, prof leGeoGroup)
        .flatMap {
          case Prof leGeoResponse(_, Success, So (enr ch nt), _) =>
            St ch.value(enr ch nt)
          case Prof leGeoResponse(_, Success, None, _) =>
            // w n state  s Success enr ch nt should always be So , but default to be safe
            emptyEnr ch ntSt ch
          case Prof leGeoResponse(
                _,
                UserLookupError,
                _,
                So (Deact vatedUser | SuspendedUser | NotFound)
              ) =>
            emptyEnr ch ntSt ch
          case r =>
            St ch.except on(UnexpectedState(r.state))
        }
  }
}
