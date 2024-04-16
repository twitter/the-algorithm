package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on

 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.TwoHopExpans onCand dateS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms.Sw ch ngS msS ce
 mport com.tw ter.follow_recom ndat ons.common.models.AccountProof
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasS m larToContext
 mport com.tw ter.follow_recom ndat ons.common.models.Reason
 mport com.tw ter.follow_recom ndat ons.common.models.S m larToProof
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport scala.math._

case class S m larUser(cand date d: Long, s m larTo: Long, score: Double)

abstract class S msExpans onBasedCand dateS ce[-Target <: HasParams](
  sw ch ngS msS ce: Sw ch ngS msS ce)
    extends TwoHopExpans onCand dateS ce[Target, Cand dateUser, S m larUser, Cand dateUser] {

  // max number secondary degree nodes per f rst degree node
  def maxSecondaryDegreeNodes(req: Target):  nt

  // max number output results
  def maxResults(req: Target):  nt

  // scorer to score cand date based on f rst and second degree node scores
  def scoreCand date(s ce: Double, s m larToScore: Double): Double

  def cal brateD v sor(req: Target): Double

  def cal brateScore(cand dateScore: Double, req: Target): Double = {
    cand dateScore / cal brateD v sor(req)
  }

  overr de def secondaryDegreeNodes(req: Target, node: Cand dateUser): St ch[Seq[S m larUser]] = {
    sw ch ngS msS ce(new HasParams w h HasS m larToContext {
      overr de val s m larToUser ds = Seq(node. d)
      overr de val params = (req.params)
    }).map(_.take(maxSecondaryDegreeNodes(req)).map { cand date =>
      S m larUser(
        cand date. d,
        node. d,
        (node.score, cand date.score) match {
          // only cal brated s ms expanded cand dates scores
          case (So (nodeScore), So (cand dateScore)) =>
            cal brateScore(scoreCand date(nodeScore, cand dateScore), req)
          case (So (nodeScore), _) => nodeScore
          // NewFollow ngS m larUser w ll enter t  case
          case _ => cal brateScore(cand date.score.getOrElse(0.0), req)
        }
      )
    })
  }

  overr de def aggregateAndScore(
    request: Target,
    f rstDegreeToSecondDegreeNodesMap: Map[Cand dateUser, Seq[S m larUser]]
  ): St ch[Seq[Cand dateUser]] = {

    val  nputNodes = f rstDegreeToSecondDegreeNodesMap.keys.map(_. d).toSet
    val aggregator = request.params(S msExpans onS ceParams.Aggregator) match {
      case S msExpans onS ceAggregator d.Max =>
        S msExpans onBasedCand dateS ce.ScoreAggregator.Max
      case S msExpans onS ceAggregator d.Sum =>
        S msExpans onBasedCand dateS ce.ScoreAggregator.Sum
      case S msExpans onS ceAggregator d.Mult Decay =>
        S msExpans onBasedCand dateS ce.ScoreAggregator.Mult Decay
    }

    val groupedCand dates = f rstDegreeToSecondDegreeNodesMap.values.flatten
      .f lterNot(c =>  nputNodes.conta ns(c.cand date d))
      .groupBy(_.cand date d)
      .map {
        case ( d, cand dates) =>
          // D fferent aggregators for f nal score
          val f nalScore = aggregator(cand dates.map(_.score).toSeq)
          val proofs = cand dates.map(_.s m larTo).toSet

          Cand dateUser(
             d =  d,
            score = So (f nalScore),
            reason =
              So (Reason(So (AccountProof(s m larToProof = So (S m larToProof(proofs.toSeq))))))
          ).w hCand dateS ce( dent f er)
      }
      .toSeq
      .sortBy(-_.score.getOrElse(0.0d))
      .take(maxResults(request))

    St ch.value(groupedCand dates)
  }
}

object S msExpans onBasedCand dateS ce {
  object ScoreAggregator {
    val Max: Seq[Double] => Double = (cand dateScores: Seq[Double]) => {
       f (cand dateScores.s ze > 0) cand dateScores.max else 0.0
    }
    val Sum: Seq[Double] => Double = (cand dateScores: Seq[Double]) => {
      cand dateScores.sum
    }
    val Mult Decay: Seq[Double] => Double = (cand dateScores: Seq[Double]) => {
      val alpha = 0.1
      val beta = 0.1
      val gamma = 0.8
      val decay_scores: Seq[Double] =
        cand dateScores
          .sorted(Order ng[Double].reverse)
          .z pW h ndex
          .map(x => x._1 * pow(gamma, x._2))
      alpha * cand dateScores.max + decay_scores.sum + beta * cand dateScores.s ze
    }
  }
}
