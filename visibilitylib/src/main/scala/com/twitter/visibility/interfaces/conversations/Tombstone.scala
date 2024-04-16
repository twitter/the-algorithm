package com.tw ter.v s b l y. nterfaces.conversat ons

 mport com.tw ter.t  l nes.render.thr ftscala.TombstoneD splayType
 mport com.tw ter.t  l nes.render.thr ftscala.Tombstone nfo
 mport com.tw ter.v s b l y.rules._

case class VfTombstone(
  tombstone d: Long,
   ncludeT et: Boolean,
  act on: Act on,
  tombstone nfo: Opt on[Tombstone nfo] = None,
  tombstoneD splayType: TombstoneD splayType = TombstoneD splayType. nl ne,
  truncateDescendantsW nFocal: Boolean = false) {

  val  sTruncatable: Boolean = act on match {
    case  nterst  al(Reason.V e rBlocksAuthor, _, _) => true
    case  nterst  al(Reason.V e rHardMutedAuthor, _, _) => true
    case  nterst  al(Reason.MutedKeyword, _, _) => true
    case Tombstone(Ep aph.NotFound, _) => true
    case Tombstone(Ep aph.Unava lable, _) => true
    case Tombstone(Ep aph.Suspended, _) => true
    case Tombstone(Ep aph.Protected, _) => true
    case Tombstone(Ep aph.Deact vated, _) => true
    case Tombstone(Ep aph.BlockedBy, _) => true
    case Tombstone(Ep aph.Moderated, _) => true
    case Tombstone(Ep aph.Deleted, _) => true
    case Tombstone(Ep aph.Underage, _) => true
    case Tombstone(Ep aph.NoStatedAge, _) => true
    case Tombstone(Ep aph.LoggedOutAge, _) => true
    case Tombstone(Ep aph.SuperFollowsContent, _) => true
    case Tombstone(Ep aph.Commun yT etH dden, _) => true
    case _: Local zedTombstone => true
    case _ => false
  }
}
