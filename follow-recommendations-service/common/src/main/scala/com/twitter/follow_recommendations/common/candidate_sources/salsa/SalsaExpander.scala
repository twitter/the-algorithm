package com.tw ter.follow_recom ndat ons.common.cand date_s ces.salsa

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.SalsaF rstDegreeOnUserCl entColumn
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.SalsaSecondDegreeOnUserCl entColumn
 mport com.tw ter.follow_recom ndat ons.common.models.AccountProof
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.FollowProof
 mport com.tw ter.follow_recom ndat ons.common.models.Reason
 mport com.tw ter.st ch.St ch
 mport com.tw ter.wtf.cand date.thr ftscala.Cand date
 mport javax. nject. nject
 mport javax. nject.S ngleton

case class SalsaExpandedCand date(
  cand date d: Long,
  numberOfConnect ons:  nt,
  totalScore: Double,
  connect ngUsers: Seq[Long]) {
  def toCand dateUser: Cand dateUser =
    Cand dateUser(
       d = cand date d,
      score = So (totalScore),
      reason = So (Reason(
        So (AccountProof(followProof = So (FollowProof(connect ngUsers, connect ngUsers.s ze))))))
    )
}

case class S m larUserCand date(cand date d: Long, score: Double, s m larToCand date: Long)

/**
 * Salsa expander uses pre-computed l sts of cand dates for each  nput user  d and returns t  h g st scored cand dates  n t  pre-computed l sts as t  expans on for t  correspond ng  nput  d.
 */
@S ngleton
class SalsaExpander @ nject() (
  statsRece ver: StatsRece ver,
  f rstDegreeCl ent: SalsaF rstDegreeOnUserCl entColumn,
  secondDegreeCl ent: SalsaSecondDegreeOnUserCl entColumn,
) {

  val stats = statsRece ver.scope("salsa_expander")

  pr vate def s m larUsers(
     nput: Seq[Long],
    ne ghbors: Seq[Opt on[Seq[Cand date]]]
  ): Seq[SalsaExpandedCand date] = {
     nput
      .z p(ne ghbors).flatMap {
        case (rec d, So (ne ghbors)) =>
          ne ghbors.map(ne ghbor => S m larUserCand date(ne ghbor.user d, ne ghbor.score, rec d))
        case _ => N l
      }.groupBy(_.cand date d).map {
        case (key, ne ghbors) =>
          val scores = ne ghbors.map(_.score)
          val connect ngUsers = ne ghbors
            .sortBy(-_.score)
            .take(SalsaExpander.MaxConnect ngUsersToOutputPerExpandedCand date)
            .map(_.s m larToCand date)

          SalsaExpandedCand date(key, scores.s ze, scores.sum, connect ngUsers)
      }
      .f lter(
        _.numberOfConnect ons >= math
          .m n(SalsaExpander.M nConnect ngUsersThreshold,  nput.s ze)
      )
      .toSeq
  }

  def apply(
    f rstDegree nput: Seq[Long],
    secondDegree nput: Seq[Long],
    maxNumOfCand datesToReturn:  nt
  ): St ch[Seq[Cand dateUser]] = {

    val f rstDegreeNe ghborsSt ch =
      St ch
        .collect(f rstDegree nput.map(f rstDegreeCl ent.fetc r
          .fetch(_).map(_.v.map(_.cand dates.take(SalsaExpander.MaxD rectNe ghbors))))).onSuccess {
          f rstDegreeNe ghbors =>
            stats.stat("f rst_degree_ne ghbors").add(f rstDegreeNe ghbors.flatten.s ze)
        }

    val secondDegreeNe ghborsSt ch =
      St ch
        .collect(
          secondDegree nput.map(
            secondDegreeCl ent.fetc r
              .fetch(_).map(
                _.v.map(_.cand dates.take(SalsaExpander.Max nd rectNe ghbors))))).onSuccess {
          secondDegreeNe ghbors =>
            stats.stat("second_degree_ne ghbors").add(secondDegreeNe ghbors.flatten.s ze)
        }

    val ne ghborSt c s =
      St ch.jo n(f rstDegreeNe ghborsSt ch, secondDegreeNe ghborsSt ch).map {
        case (f rst, second) => f rst ++ second
      }

    val s m larUsersTo nput = ne ghborSt c s.map { ne ghbors =>
      s m larUsers(f rstDegree nput ++ secondDegree nput, ne ghbors)
    }

    s m larUsersTo nput.map {
      // Rank t  cand date cot users by t  comb ned   ghts from t  connect ng users. T   s t  default or g nal  mple ntat on.    s unl kely to have   ght t es and thus a second rank ng funct on  s not necessary.
      _.sortBy(-_.totalScore)
        .take(maxNumOfCand datesToReturn)
        .map(_.toCand dateUser)
    }
  }
}

object SalsaExpander {
  val MaxD rectNe ghbors = 2000
  val Max nd rectNe ghbors = 2000
  val M nConnect ngUsersThreshold = 2
  val MaxConnect ngUsersToOutputPerExpandedCand date = 3
}
