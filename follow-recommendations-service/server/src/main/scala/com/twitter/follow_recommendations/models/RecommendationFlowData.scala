package com.tw ter.follow_recom ndat ons.models

 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.Cl entContextConverter
 mport com.tw ter.follow_recom ndat ons.common.models.HasUserState
 mport com.tw ter.follow_recom ndat ons.common.ut ls.UserS gnupUt l
 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Recom ndat onP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.ut l.T  

case class Recom ndat onFlowData[Target <: HasCl entContext](
  request: Target,
  recom ndat onFlow dent f er: Recom ndat onP pel ne dent f er,
  cand dateS ces: Seq[Cand dateS ce[Target, Cand dateUser]],
  cand datesFromCand dateS ces: Seq[Cand dateUser],
   rgedCand dates: Seq[Cand dateUser],
  f lteredCand dates: Seq[Cand dateUser],
  rankedCand dates: Seq[Cand dateUser],
  transfor dCand dates: Seq[Cand dateUser],
  truncatedCand dates: Seq[Cand dateUser],
  results: Seq[Cand dateUser])
    extends HasMarshall ng {

   mport Recom ndat onFlowData._

  lazy val toRecom ndat onFlowLogOffl neThr ft: offl ne.Recom ndat onFlowLog = {
    val user tadata = userToOffl neRecom ndat onFlowUser tadata(request)
    val s gnals = userToOffl neRecom ndat onFlowS gnals(request)
    val f lteredCand dateS ceCand dates =
      cand datesToOffl neRecom ndat onFlowCand dateS ceCand dates(
        cand dateS ces,
        f lteredCand dates
      )
    val rankedCand dateS ceCand dates =
      cand datesToOffl neRecom ndat onFlowCand dateS ceCand dates(
        cand dateS ces,
        rankedCand dates
      )
    val truncatedCand dateS ceCand dates =
      cand datesToOffl neRecom ndat onFlowCand dateS ceCand dates(
        cand dateS ces,
        truncatedCand dates
      )

    offl ne.Recom ndat onFlowLog(
      Cl entContextConverter.toFRSOffl neCl entContextThr ft(request.cl entContext),
      user tadata,
      s gnals,
      T  .now. nM ll s,
      recom ndat onFlow dent f er.na ,
      So (f lteredCand dateS ceCand dates),
      So (rankedCand dateS ceCand dates),
      So (truncatedCand dateS ceCand dates)
    )
  }
}

object Recom ndat onFlowData {
  def userToOffl neRecom ndat onFlowUser tadata[Target <: HasCl entContext](
    request: Target
  ): Opt on[offl ne.Offl neRecom ndat onFlowUser tadata] = {
    val userS gnupAge = UserS gnupUt l.userS gnupAge(request).map(_. nDays)
    val userState = request match {
      case req: HasUserState => req.userState.map(_.na )
      case _ => None
    }
    So (offl ne.Offl neRecom ndat onFlowUser tadata(userS gnupAge, userState))
  }

  def userToOffl neRecom ndat onFlowS gnals[Target <: HasCl entContext](
    request: Target
  ): Opt on[offl ne.Offl neRecom ndat onFlowS gnals] = {
    val countryCode = request.getCountryCode
    So (offl ne.Offl neRecom ndat onFlowS gnals(countryCode))
  }

  def cand datesToOffl neRecom ndat onFlowCand dateS ceCand dates[Target <: HasCl entContext](
    cand dateS ces: Seq[Cand dateS ce[Target, Cand dateUser]],
    cand dates: Seq[Cand dateUser],
  ): Seq[offl ne.Offl neRecom ndat onFlowCand dateS ceCand dates] = {
    val cand datesGroupedByCand dateS ces =
      cand dates.groupBy(
        _.getPr maryCand dateS ce.getOrElse(Cand dateS ce dent f er("NoCand dateS ce")))

    cand dateS ces.map(cand dateS ce => {
      val cand dates =
        cand datesGroupedByCand dateS ces.get(cand dateS ce. dent f er).toSeq.flatten
      val cand dateUser ds = cand dates.map(_. d)
      val cand dateUserScores = cand dates.map(_.score).ex sts(_.nonEmpty) match {
        case true => So (cand dates.map(_.score.getOrElse(-1.0)))
        case false => None
      }
      offl ne.Offl neRecom ndat onFlowCand dateS ceCand dates(
        cand dateS ce. dent f er.na ,
        cand dateUser ds,
        cand dateUserScores
      )
    })
  }
}
