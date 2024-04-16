package com.tw ter.follow_recom ndat ons.common.cand date_s ces.base

 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.S m larUserExpanderParams.DefaultEnable mpl c EngagedExpans on
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.S m larUserExpanderParams.DefaultExpans on nputCount
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.S m larUserExpanderParams.DefaultF nalCand datesReturnedCount
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.S m larUserExpanderParams.EnableNonD rectFollowExpans on
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.S m larUserExpanderParams.EnableS msExpandSeedAccountsSort
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.S m larUserExpanderRepos ory.DefaultCand dateBu lder
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.S m larUserExpanderRepos ory.DefaultScore
 mport com.tw ter.follow_recom ndat ons.common.models.AccountProof
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.Engage ntType
 mport com.tw ter.follow_recom ndat ons.common.models.FollowProof
 mport com.tw ter.follow_recom ndat ons.common.models.Reason
 mport com.tw ter.follow_recom ndat ons.common.models.S m larToProof
 mport com.tw ter.follow_recom ndat ons.common.models.UserCand dateS ceDeta ls
 mport com.tw ter. rm .cand date.thr ftscala.Cand dates
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Params

case class SecondDegreeCand date(user d: Long, score: Double, soc alProof: Opt on[Seq[Long]])

abstract class S m larUserExpanderRepos ory[-Request <: HasParams](
  overr de val  dent f er: Cand dateS ce dent f er,
  s m larToCand datesFetc r: Fetc r[
    Long,
    Un ,
    Cand dates
  ],
  expans on nputS zeParam: FSBoundedParam[ nt] = DefaultExpans on nputCount,
  cand datesReturnedS zeParam: FSBoundedParam[ nt] = DefaultF nalCand datesReturnedCount,
  enable mpl c EngagedExpans on: FSParam[Boolean] = DefaultEnable mpl c EngagedExpans on,
  thresholdToAvo dExpans on:  nt = 30,
  maxExpans onPerCand date: Opt on[ nt] = None,
   nclud ngOr g nalCand dates: Boolean = false,
  scorer: (Double, Double) => Double = S m larUserExpanderRepos ory.DefaultScorer,
  aggregator: (Seq[Double]) => Double = ScoreAggregator.Max,
  cand dateBu lder: (Long, Cand dateS ce dent f er, Double, Cand dateUser) => Cand dateUser =
    DefaultCand dateBu lder)
    extends TwoHopExpans onCand dateS ce[
      Request,
      Cand dateUser,
      SecondDegreeCand date,
      Cand dateUser
    ] {

  val or g nalCand dateS ce: Cand dateS ce[Request, Cand dateUser]
  val backupOr g nalCand dateS ce: Opt on[Cand dateS ce[Request, Cand dateUser]] = None

  overr de def f rstDegreeNodes(request: Request): St ch[Seq[Cand dateUser]] = {

    val or g nalCand datesSt ch: St ch[Seq[Cand dateUser]] =
      or g nalCand dateS ce(request)

    val backupCand datesSt ch: St ch[Seq[Cand dateUser]] =
       f (request.params(EnableNonD rectFollowExpans on)) {
        backupOr g nalCand dateS ce.map(_.apply(request)).getOrElse(St ch.N l)
      } else {
        St ch.N l
      }

    val f rstDegreeCand datesComb nedSt ch: St ch[Seq[Cand dateUser]] =
      St ch
        .jo n(or g nalCand datesSt ch, backupCand datesSt ch).map {
          case (f rstDegreeOr gCand dates, backupF rstDegreeCand dates) =>
             f (request.params(EnableS msExpandSeedAccountsSort)) {
              f rstDegreeOr gCand dates ++ backupF rstDegreeCand dates sortBy {
                -_.score.getOrElse(DefaultScore)
              }
            } else {
              f rstDegreeOr gCand dates ++ backupF rstDegreeCand dates
            }
        }

    val cand datesAfter mpl c Engage ntsRemovalSt ch: St ch[Seq[Cand dateUser]] =
      getCand datesAfter mpl c Engage ntF lter ng(
        request.params,
        f rstDegreeCand datesComb nedSt ch)

    val f rstDegreeCand datesComb nedTr m d = cand datesAfter mpl c Engage ntsRemovalSt ch.map {
      cand dates: Seq[Cand dateUser] =>
        cand dates.take(request.params(expans on nputS zeParam))
    }

    f rstDegreeCand datesComb nedTr m d.map { f rstDegreeResults: Seq[Cand dateUser] =>
       f (f rstDegreeResults.nonEmpty && f rstDegreeResults.s ze < thresholdToAvo dExpans on) {
        f rstDegreeResults
          .groupBy(_. d).mapValues(
            _.maxBy(_.score)
          ).values.toSeq
      } else {
        N l
      }
    }

  }

  overr de def secondaryDegreeNodes(
    request: Request,
    f rstDegreeCand date: Cand dateUser
  ): St ch[Seq[SecondDegreeCand date]] = {
    s m larToCand datesFetc r.fetch(f rstDegreeCand date. d).map(_.v).map { cand dateL stOpt on =>
      cand dateL stOpt on
        .map { cand datesL st =>
          cand datesL st.cand dates.map(cand date =>
            SecondDegreeCand date(cand date.user d, cand date.score, cand date.soc alProof))
        }.getOrElse(N l)
    }

  }

  overr de def aggregateAndScore(
    req: Request,
    f rstDegreeToSecondDegreeNodesMap: Map[Cand dateUser, Seq[SecondDegreeCand date]]
  ): St ch[Seq[Cand dateUser]] = {

    val s m larExpanderResults = f rstDegreeToSecondDegreeNodesMap.flatMap {
      case (f rstDegreeCand date, seqOfSecondDegreeCand dates) =>
        val s ceScore = f rstDegreeCand date.score.getOrElse(DefaultScore)
        val results: Seq[Cand dateUser] = seqOfSecondDegreeCand dates.map { secondDegreeCand date =>
          val score = scorer(s ceScore, secondDegreeCand date.score)
          cand dateBu lder(secondDegreeCand date.user d,  dent f er, score, f rstDegreeCand date)
        }
        maxExpans onPerCand date match {
          case None => results
          case So (l m ) => results.sortBy(-_.score.getOrElse(DefaultScore)).take(l m )
        }
    }.toSeq

    val allCand dates = {
       f ( nclud ngOr g nalCand dates)
        f rstDegreeToSecondDegreeNodesMap.keySet.toSeq
      else
        N l
    } ++ s m larExpanderResults

    val groupedCand dates: Seq[Cand dateUser] = allCand dates
      .groupBy(_. d)
      .flatMap {
        case (_, cand dates) =>
          val f nalScore = aggregator(cand dates.map(_.score.getOrElse(DefaultScore)))
          val cand dateS ceDeta lsComb ned = aggregateCand dateS ceDeta ls(cand dates)
          val accountSoc alProofcomb ned = aggregateAccountSoc alProof(cand dates)

          cand dates. adOpt on.map(
            _.copy(
              score = So (f nalScore),
              reason = accountSoc alProofcomb ned,
              userCand dateS ceDeta ls = cand dateS ceDeta lsComb ned)
              .w hCand dateS ce( dent f er))
      }
      .toSeq

    St ch.value(
      groupedCand dates
        .sortBy { -_.score.getOrElse(DefaultScore) }.take(req.params(cand datesReturnedS zeParam))
    )
  }

  def aggregateCand dateS ceDeta ls(
    cand dates: Seq[Cand dateUser]
  ): Opt on[UserCand dateS ceDeta ls] = {
    cand dates
      .map { cand date =>
        cand date.userCand dateS ceDeta ls.map(_.cand dateS ceScores).getOrElse(Map.empty)
      }.reduceLeftOpt on { (scoreMap1, scoreMap2) =>
        scoreMap1 ++ scoreMap2
      }.map {
        UserCand dateS ceDeta ls(pr maryCand dateS ce = None, _)
      }

  }

  def aggregateAccountSoc alProof(cand dates: Seq[Cand dateUser]): Opt on[Reason] = {
    cand dates
      .map { cand date =>
        (
          cand date.reason
            .flatMap(_.accountProof.flatMap(_.s m larToProof.map(_.s m larTo))).getOrElse(N l),
          cand date.reason
            .flatMap(_.accountProof.flatMap(_.followProof.map(_.follo dBy))).getOrElse(N l),
          cand date.reason
            .flatMap(_.accountProof.flatMap(_.followProof.map(_.num ds))).getOrElse(0)
        )
      }.reduceLeftOpt on { (accountProofOne, accountProofTwo) =>
        (
          //  rge s m larTo ds
          accountProofOne._1 ++ accountProofTwo._1,
          //  rge follo dBy ds
          accountProofOne._2 ++ accountProofTwo._2,
          // add num ds
          accountProofOne._3 + accountProofTwo._3)
      }.map { proofs =>
        Reason(accountProof = So (
          AccountProof(
            s m larToProof = So (S m larToProof(proofs._1)),
            followProof =  f (proofs._2.nonEmpty) So (FollowProof(proofs._2, proofs._3)) else None
          )))
      }
  }

  def getCand datesAfter mpl c Engage ntF lter ng(
    params: Params,
    f rstDegreeCand datesSt ch: St ch[Seq[Cand dateUser]]
  ): St ch[Seq[Cand dateUser]] = {

     f (!params(enable mpl c EngagedExpans on)) {

      /**
       * Remove cand dates whose engage nt types only conta n  mpl c  engage nts
       * (e.g. Prof le V ew, T et Cl ck) and only expand those cand dates who conta n expl c 
       * engage nts.
       */
      f rstDegreeCand datesSt ch.map { cand dates =>
        cand dates.f lter { cand =>
          cand.engage nts.ex sts(engage =>
            engage == Engage ntType.L ke || engage == Engage ntType.Ret et || engage == Engage ntType. nt on)
        }
      }
    } else {
      f rstDegreeCand datesSt ch
    }
  }

}

object S m larUserExpanderRepos ory {
  val DefaultScorer: (Double, Double) => Double = (s ceScore: Double, s m larScore: Double) =>
    s m larScore
  val Mult plyScorer: (Double, Double) => Double = (s ceScore: Double, s m larScore: Double) =>
    s ceScore * s m larScore
  val S ceScorer: (Double, Double) => Double = (s ceScore: Double, s m larScore: Double) =>
    s ceScore

  val DefaultScore = 0.0d

  val DefaultCand dateBu lder: (
    Long,
    Cand dateS ce dent f er,
    Double,
    Cand dateUser
  ) => Cand dateUser =
    (
      user d: Long,
      _: Cand dateS ce dent f er,
      score: Double,
      cand date: Cand dateUser
    ) => {
      val or g nalCand dateS ceDeta ls =
        cand date.userCand dateS ceDeta ls.flatMap { candS ceDeta ls =>
          candS ceDeta ls.pr maryCand dateS ce.map { pr maryCand dateS ce =>
            UserCand dateS ceDeta ls(
              pr maryCand dateS ce = None,
              cand dateS ceScores = Map(pr maryCand dateS ce -> cand date.score))
          }
        }
      Cand dateUser(
         d = user d,
        score = So (score),
        userCand dateS ceDeta ls = or g nalCand dateS ceDeta ls,
        reason =
          So (Reason(So (AccountProof(s m larToProof = So (S m larToProof(Seq(cand date. d)))))))
      )
    }

  val FollowClusterCand dateBu lder: (
    Long,
    Cand dateS ce dent f er,
    Double,
    Cand dateUser
  ) => Cand dateUser =
    (user d: Long, _: Cand dateS ce dent f er, score: Double, cand date: Cand dateUser) => {
      val or g nalCand dateS ceDeta ls =
        cand date.userCand dateS ceDeta ls.flatMap { candS ceDeta ls =>
          candS ceDeta ls.pr maryCand dateS ce.map { pr maryCand dateS ce =>
            UserCand dateS ceDeta ls(
              pr maryCand dateS ce = None,
              cand dateS ceScores = Map(pr maryCand dateS ce -> cand date.score))
          }
        }

      val or g nalFollowCluster = cand date.reason
        .flatMap(_.accountProof.flatMap(_.followProof.map(_.follo dBy)))

      Cand dateUser(
         d = user d,
        score = So (score),
        userCand dateS ceDeta ls = or g nalCand dateS ceDeta ls,
        reason = So (
          Reason(
            So (
              AccountProof(
                s m larToProof = So (S m larToProof(Seq(cand date. d))),
                followProof = or g nalFollowCluster.map(follows =>
                  FollowProof(follows, follows.s ze)))))
        )
      )
    }
}

object ScoreAggregator {
  // aggregate t  sa  cand dates w h sa   d by tak ng t  one w h largest score
  val Max: Seq[Double] => Double = (cand dateScores: Seq[Double]) => { cand dateScores.max }

  // aggregate t  sa  cand dates w h sa   d by tak ng t  sum of t  scores
  val Sum: Seq[Double] => Double = (cand dateScores: Seq[Double]) => { cand dateScores.sum }
}
