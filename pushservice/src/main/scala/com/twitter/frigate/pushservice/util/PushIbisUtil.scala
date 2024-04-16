package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.ut l.Future

object Push b sUt l {

  def getSoc alContextModelValues(soc alContextUser ds: Seq[Long]): Map[Str ng, Str ng] = {

    val soc alContextS ze = soc alContextUser ds.s ze

    val (d splaySoc alContexts, ot rCount) = {
       f (soc alContextS ze < 3) (soc alContextUser ds, 0)
      else (soc alContextUser ds.take(1), soc alContextS ze - 1)
    }

    val usersValue = d splaySoc alContexts.map(_.toStr ng).mkStr ng(",")

     f (ot rCount > 0) Map("soc al_users" -> s"$usersValue+$ot rCount")
    else Map("soc al_users" -> usersValue)
  }

  def  rgeFutModelValues(
    mvFut1: Future[Map[Str ng, Str ng]],
    mvFut2: Future[Map[Str ng, Str ng]]
  ): Future[Map[Str ng, Str ng]] = {
    Future.jo n(mvFut1, mvFut2).map {
      case (mv1, mv2) => mv1 ++ mv2
    }
  }

  def  rgeModelValues(
    mvFut1: Future[Map[Str ng, Str ng]],
    mv2: Map[Str ng, Str ng]
  ): Future[Map[Str ng, Str ng]] =
    mvFut1.map { mv1 => mv1 ++ mv2 }
}
