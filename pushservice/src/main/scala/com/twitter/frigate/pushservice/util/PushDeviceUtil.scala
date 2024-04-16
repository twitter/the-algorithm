package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.fr gate.common.store.dev ce nfo.Dev ce nfo
 mport com.tw ter.fr gate.common.store.dev ce nfo.Mob leCl entType
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.ut l.Future
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver

object PushDev ceUt l {

  def  sPr maryDev ceAndro d(dev ce nfoOpt: Opt on[Dev ce nfo]): Boolean = {
    dev ce nfoOpt.ex sts {
      _.guessedPr maryCl ent.ex sts { cl entType =>
        (cl entType == Mob leCl entType.Andro d) || (cl entType == Mob leCl entType.Andro dL e)
      }
    }
  }

  def  sPr maryDev ce OS(dev ce nfoOpt: Opt on[Dev ce nfo]): Boolean = {
    dev ce nfoOpt.ex sts {
      _.guessedPr maryCl ent.ex sts { cl entType =>
        (cl entType == Mob leCl entType. phone) || (cl entType == Mob leCl entType. pad)
      }
    }
  }

  def  sPushRecom ndat onsEl g ble(target: Target): Future[Boolean] =
    target.dev ce nfo.map(_.ex sts(_. sRecom ndat onsEl g ble))

  def  sTop csEl g ble(
    target: Target,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Future[Boolean] = {
    val  sTop csSk pFat gue = Future.True

    Future.jo n( sTop csSk pFat gue, target.dev ce nfo.map(_.ex sts(_. sTop csEl g ble))).map {
      case ( sTop csNotFat gue,  sTop csEl g bleSett ng) =>
         sTop csNotFat gue &&  sTop csEl g bleSett ng
    }
  }

  def  sSpacesEl g ble(target: Target): Future[Boolean] =
    target.dev ce nfo.map(_.ex sts(_. sSpacesEl g ble))

  def  sNtabOnlyEl g ble: Future[Boolean] = {
    Future.False
  }

  def  sRecom ndat onsEl g ble(target: Target): Future[Boolean] = {
    Future.jo n( sPushRecom ndat onsEl g ble(target),  sNtabOnlyEl g ble).map {
      case ( sPushRecom ndat on,  sNtabOnly) =>  sPushRecom ndat on ||  sNtabOnly
      case _ => false
    }
  }

}
