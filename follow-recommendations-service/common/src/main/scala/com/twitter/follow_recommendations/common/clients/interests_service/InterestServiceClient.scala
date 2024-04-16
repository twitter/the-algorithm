package com.tw ter.follow_recom ndat ons.common.cl ents. nterests_serv ce

 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store. nterested n nterestsFetchKey
 mport com.tw ter. nject.Logg ng
 mport com.tw ter. nterests.thr ftscala. nterest d
 mport com.tw ter. nterests.thr ftscala. nterestRelat onsh p
 mport com.tw ter. nterests.thr ftscala. nterested n nterestModel
 mport com.tw ter. nterests.thr ftscala.User nterest
 mport com.tw ter. nterests.thr ftscala.User nterestData
 mport com.tw ter. nterests.thr ftscala.User nterestsResponse
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._

@S ngleton
class  nterestServ ceCl ent @ nject() (
  stratoCl ent: Cl ent,
  statsRece ver: StatsRece ver = NullStatsRece ver)
    extends Logg ng {

  val  nterestsServ ceStratoColumnPath = " nterests/ nterested n nterests"
  val stats = statsRece ver.scope(" nterest_serv ce_cl ent")
  val errorCounter = stats.counter("error")

  pr vate val  nterestsFetc r =
    stratoCl ent.fetc r[ nterested n nterestsFetchKey, User nterestsResponse](
       nterestsServ ceStratoColumnPath,
      c ckTypes = true
    )

  def fetchUtt nterest ds(
    user d: Long
  ): St ch[Seq[Long]] = {
    fetch nterestRelat onsh ps(user d)
      .map(_.toSeq.flatten.flatMap(extractUtt nterest))
  }

  def extractUtt nterest(
     nterestRelat onSh p:  nterestRelat onsh p
  ): Opt on[Long] = {
     nterestRelat onSh p match {
      case  nterestRelat onsh p.V1(relat onsh pV1) =>
        relat onsh pV1. nterest d match {
          case  nterest d.Semant cCore(semant cCore nterest) => So (semant cCore nterest. d)
          case _ => None
        }
      case _ => None
    }
  }

  def fetchCustom nterests(
    user d: Long
  ): St ch[Seq[Str ng]] = {
    fetch nterestRelat onsh ps(user d)
      .map(_.toSeq.flatten.flatMap(extractCustom nterest))
  }

  def extractCustom nterest(
     nterestRelat onSh p:  nterestRelat onsh p
  ): Opt on[Str ng] = {
     nterestRelat onSh p match {
      case  nterestRelat onsh p.V1(relat onsh pV1) =>
        relat onsh pV1. nterest d match {
          case  nterest d.FreeForm(freeForm nterest) => So (freeForm nterest. nterest)
          case _ => None
        }
      case _ => None
    }
  }

  def fetch nterestRelat onsh ps(
    user d: Long
  ): St ch[Opt on[Seq[ nterestRelat onsh p]]] = {
     nterestsFetc r
      .fetch(
         nterested n nterestsFetchKey(
          user d = user d,
          labels = None,
          None
        ))
      .map(_.v)
      .map {
        case So (response) =>
          response. nterests. nterests.map {  nterests =>
             nterests.collect {
              case User nterest(_, So ( nterestData)) =>
                get nterestRelat onsh p( nterestData)
            }.flatten
          }
        case _ => None
      }
      .rescue {
        case e: Throwable => //   are swallow ng all errors
          logger.warn(s" nterests could not be retr eved for user $user d due to ${e.getCause}")
          errorCounter. ncr
          St ch.None
      }
  }

  pr vate def get nterestRelat onsh p(
     nterestData: User nterestData
  ): Seq[ nterestRelat onsh p] = {
     nterestData match {
      case User nterestData. nterested n( nterestModels) =>
         nterestModels.collect {
          case  nterested n nterestModel.Expl c Model(model) => model
        }
      case _ => N l
    }
  }
}
