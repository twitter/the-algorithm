package com.tw ter.t  l neranker.core

 mport com.tw ter.t  l nes.model.User d

/**
 * Follow graph deta ls of a g ven user.  ncludes users follo d, but also follo d users  n var ous
 * states of mute.
 *
 * @param user d  D of a g ven user.
 * @param follo dUser ds  Ds of users who t  g ven user follows.
 * @param mutuallyFollow ngUser ds A subset of follo dUser ds w re follo d users follow back t  g ven user.
 * @param mutedUser ds A subset of follo dUser ds that t  g ven user has muted.
 * @param ret etsMutedUser ds A subset of follo dUser ds whose ret ets are muted by t  g ven user.
 */
case class FollowGraphData(
  user d: User d,
  follo dUser ds: Seq[User d],
  mutuallyFollow ngUser ds: Set[User d],
  mutedUser ds: Set[User d],
  ret etsMutedUser ds: Set[User d]) {
  val f lteredFollo dUser ds: Seq[User d] = follo dUser ds.f lterNot(mutedUser ds)
  val allUser ds: Seq[User d] = f lteredFollo dUser ds :+ user d
  val  nNetworkUser ds: Seq[User d] = follo dUser ds :+ user d
}

object FollowGraphData {
  val Empty: FollowGraphData = FollowGraphData(
    0L,
    Seq.empty[User d],
    Set.empty[User d],
    Set.empty[User d],
    Set.empty[User d]
  )
}
