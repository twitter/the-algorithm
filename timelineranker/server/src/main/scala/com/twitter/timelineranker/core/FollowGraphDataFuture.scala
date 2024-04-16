package com.tw ter.t  l neranker.core

 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.ut l.Future

/**
 * S m lar to FollowGraphData but makes ava lable  s f elds as separate futures.
 */
case class FollowGraphDataFuture(
  user d: User d,
  follo dUser dsFuture: Future[Seq[User d]],
  mutuallyFollow ngUser dsFuture: Future[Set[User d]],
  mutedUser dsFuture: Future[Set[User d]],
  ret etsMutedUser dsFuture: Future[Set[User d]]) {

  def  nNetworkUser dsFuture: Future[Seq[User d]] = follo dUser dsFuture.map(_ :+ user d)

  def get(): Future[FollowGraphData] = {
    Future
      .jo n(
        follo dUser dsFuture,
        mutuallyFollow ngUser dsFuture,
        mutedUser dsFuture,
        ret etsMutedUser dsFuture
      )
      .map {
        case (follo dUser ds, mutuallyFollow ngUser ds, mutedUser ds, ret etsMutedUser ds) =>
          FollowGraphData(
            user d,
            follo dUser ds,
            mutuallyFollow ngUser ds,
            mutedUser ds,
            ret etsMutedUser ds
          )
      }
  }
}

object FollowGraphDataFuture {
  pr vate def mkEmptyFuture(f eld: Str ng) = {
    Future.except on(
      new  llegalStateExcept on(s"FollowGraphDataFuture f eld $f eld accessed w hout be ng set")
    )
  }

  val EmptyFollowGraphDataFuture: FollowGraphDataFuture = FollowGraphDataFuture(
    user d = 0L,
    follo dUser dsFuture = mkEmptyFuture("follo dUser dsFuture"),
    mutuallyFollow ngUser dsFuture = mkEmptyFuture("mutuallyFollow ngUser dsFuture"),
    mutedUser dsFuture = mkEmptyFuture("mutedUser dsFuture"),
    ret etsMutedUser dsFuture = mkEmptyFuture("ret etsMutedUser dsFuture")
  )
}
