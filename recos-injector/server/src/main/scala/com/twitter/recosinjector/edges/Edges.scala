package com.tw ter.recos njector.edges

 mport com.tw ter.recos. nternal.thr ftscala.RecosHose ssage
 mport com.tw ter.recos.recos_ njector.thr ftscala.{Features, UserT etAuthorGraph ssage}
 mport com.tw ter.recos.ut l.Act on.Act on
 mport com.tw ter.recos njector.ut l.T etDeta ls
 mport scala.collect on.Map

tra  Edge {
  // RecosHose ssage  s t  thr ft struct that t  graphs consu .
  def convertToRecosHose ssage: RecosHose ssage

  // UserT etAuthorGraph ssage  s t  thr ft struct that user_t et_author_graph consu s.
  def convertToUserT etAuthorGraph ssage: UserT etAuthorGraph ssage
}

/**
 * Edge correspond ng to UserT etEnt yEdge.
 *   captures user-t et  nteract ons: Create, L ke, Ret et, Reply etc.
 */
case class UserT etEnt yEdge(
  s ceUser: Long,
  targetT et: Long,
  act on: Act on,
  card nfo: Opt on[Byte],
   tadata: Opt on[Long],
  ent  esMap: Opt on[Map[Byte, Seq[ nt]]],
  t etDeta ls: Opt on[T etDeta ls])
    extends Edge {

  overr de def convertToRecosHose ssage: RecosHose ssage = {
    RecosHose ssage(
      left d = s ceUser,
      r ght d = targetT et,
      act on = act on. d.toByte,
      card = card nfo,
      ent  es = ent  esMap,
      edge tadata =  tadata
    )
  }

  pr vate def getFeatures(t etDeta ls: T etDeta ls): Features = {
    Features(
      hasPhoto = So (t etDeta ls.hasPhoto),
      hasV deo = So (t etDeta ls.hasV deo),
      hasUrl = So (t etDeta ls.hasUrl),
      hasHashtag = So (t etDeta ls.hasHashtag)
    )
  }

  overr de def convertToUserT etAuthorGraph ssage: UserT etAuthorGraph ssage = {
    UserT etAuthorGraph ssage(
      left d = s ceUser,
      r ght d = targetT et,
      act on = act on. d.toByte,
      card = card nfo,
      author d = t etDeta ls.flatMap(_.author d),
      features = t etDeta ls.map(getFeatures)
    )
  }
}

/**
 * Edge correspond ng to UserUserGraph.
 *   captures user-user  nteract ons: Follow,  nt on,  d atag.
 */
case class UserUserEdge(
  s ceUser: Long,
  targetUser: Long,
  act on: Act on,
   tadata: Opt on[Long])
    extends Edge {
  overr de def convertToRecosHose ssage: RecosHose ssage = {
    RecosHose ssage(
      left d = s ceUser,
      r ght d = targetUser,
      act on = act on. d.toByte,
      edge tadata =  tadata
    )
  }

  overr de def convertToUserT etAuthorGraph ssage: UserT etAuthorGraph ssage = {
    throw new Runt  Except on(
      "convertToUserT etAuthorGraph ssage not  mple nted  n UserUserEdge.")
  }

}
