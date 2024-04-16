package com.tw ter. nteract on_graph.sc o.common

 mport com.spot fy.sc o.coders.Coder
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.twadoop.user.gen.thr ftscala.Comb nedUser
 mport com.tw ter.users ce.snapshot.flat.thr ftscala.FlatUser

object UserUt l {

  /**
   * placeholder for t  dest d w n represent ng vertex features w h no dest (eg create t et)
   * t  w ll only be aggregated and saved  n t  vertex datasets but not t  edge datasets
   */
  val DUMMY_USER_ D = -1L
  def getVal dUsers(users: SCollect on[Comb nedUser]): SCollect on[Long] = {
    users
      .flatMap { u =>
        for {
          user <- u.user
           f user. d != 0
          safety <- user.safety
           f !(safety.suspended || safety.deact vated || safety.restr cted ||
            safety.nsfwUser || safety.nsfwAdm n || safety.erased)
        } y eld {
          user. d
        }
      }
  }

  def getVal dFlatUsers(users: SCollect on[FlatUser]): SCollect on[Long] = {
    users
      .flatMap { u =>
        for {
           d <- u. d
           f  d != 0 && u.val dUser.conta ns(true)
        } y eld {
           d
        }
      }
  }

  def get nval dUsers(users: SCollect on[FlatUser]): SCollect on[Long] = {
    users
      .flatMap { user =>
        for {
          val d <- user.val dUser
           f !val d
           d <- user. d
        } y eld  d
      }
  }

  def f lterUsersBy dMapp ng[T: Coder](
     nput: SCollect on[T],
    usersToBeF ltered: SCollect on[Long],
    user dMapp ng: T => Long
  ): SCollect on[T] = {
     nput
      .w hNa ("f lter users by  d")
      .keyBy(user dMapp ng(_))
      .leftOuterJo n[Long](usersToBeF ltered.map(x => (x, x)))
      .collect {
        // only return data  f t  key  s not  n t  l st of usersToBeF ltered
        case (_, (data, None)) => data
      }
  }

  def f lterUsersByMult ple dMapp ngs[T: Coder](
     nput: SCollect on[T],
    usersToBeF ltered: SCollect on[Long],
    user dMapp ngs: Seq[T => Long]
  ): SCollect on[T] = {
    user dMapp ngs.foldLeft( nput)((data, mapp ng) =>
      f lterUsersBy dMapp ng(data, usersToBeF ltered, mapp ng))
  }
}
