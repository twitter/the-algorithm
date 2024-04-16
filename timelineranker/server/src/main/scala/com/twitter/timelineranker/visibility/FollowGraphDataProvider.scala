package com.tw ter.t  l neranker.v s b l y

 mport com.tw ter.t  l neranker.core.FollowGraphData
 mport com.tw ter.t  l neranker.core.FollowGraphDataFuture
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.ut l.Future

tra  FollowGraphDataProv der {

  /**
   * Gets follow graph data for t  g ven user.
   *
   * @param user d user whose follow graph deta ls are to be obta ned.
   * @param maxFollow ngCount Max mum number of follo d user  Ds to fetch.
   *           f t  g ven user follows more than t se many users,
   *          t n t  most recent maxFollow ngCount users are returned.
   */
  def get(user d: User d, maxFollow ngCount:  nt): Future[FollowGraphData]

  def getAsync(user d: User d, maxFollow ngCount:  nt): FollowGraphDataFuture

  def getFollow ng(user d: User d, maxFollow ngCount:  nt): Future[Seq[User d]]

  def getMutuallyFollow ngUser ds(user d: User d, follow ng ds: Seq[User d]): Future[Set[User d]]
}
