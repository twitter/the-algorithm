package com.tw ter.follow_recom ndat ons.common.transforms.  ghted_sampl ng
 mport com.tw ter.follow_recom ndat ons.common.base.GatedTransform
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasDebugOpt ons
 mport com.tw ter.follow_recom ndat ons.common.models.Score
 mport com.tw ter.follow_recom ndat ons.common.models.Scores
 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d
 mport com.tw ter.follow_recom ndat ons.common.rankers.ut ls.Ut ls
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Sampl ngTransform @ nject() ()
    extends GatedTransform[HasCl entContext w h HasParams w h HasDebugOpt ons, Cand dateUser] {

  val na : Str ng = t .getClass.getS mpleNa 

  /*
  Descr pt on: T  funct on takes  n a set of cand date users and ranks t m for a who-to-follow
  request by sampl ng from t  Placket-Luce d str but on
  (https://cran.rstud o.com/ b/packages/PlackettLuce/v gnettes/Overv ew.html) w h a three
  var at ons. T  f rst var at on  s that t  scores of t  cand dates are mult pl ed by
  mult pl cat veFactor before sampl ng. T  second var at on  s that t  scores are
  exponent ated before sampl ng. T  th rd var at on  s that depend ng on how many who-to-follow
  pos  ons are be ng requested, t  f rst k pos  ons are reserved for t  cand dates w h t 
  h g st scores (and t y are sorted  n decreas ng order of score) and t  rema n ng pos  ons
  are sampled from a Placket-Luce.   use t  eff c ent algor hm proposed  n t  blog
  https:// d um.com/swlh/go ng-old-school-des gn ng-algor hms-for-fast-  ghted-sampl ng- n-product on-c48fc1f40051
  to sample from a Plackett-Luce. Because of nu r cal stab l y reasons, before sampl ng from t 
  d str but on, (1)   subtract off t  max mum score from all t  scores and (2)  f after
  t  subtract on and mult pl cat on by t  mult pl cat ve factor t  result ng score  s <= -10,
    force t  cand date's transfor d score under t  above algor hm to be 0 (so r^(1/w) = 0)
  w re r  s a random number and w  s t  transfor d score.

   nputs:
  - target: HasCl entContext (WTF request)
  - cand dates: sequence of Cand dateUsers (users that need to be ranked from a who-to-follow
                request) each of wh ch has a score

   nputs accessed through feature sw c s,  .e. through target.params (see t  follow ng f le:
  "follow-recom ndat ons-serv ce/common/src/ma n/scala/com/tw ter/follow_recom ndat ons/common/
  transforms/  ghted_sampl ng/Sampl ngTransformParams.scala"):
  - topKF xed: t  f rst k pos  ons of t  who-to-follow rank ng correspond to t  users w h t  k
               h g st scores and are not sampled from t  Placket-Luce d str but on
  - mult pl cat veFactor: mult pl cat veFactor  s used to transform t  scores of each cand date by
                          mult ply ng that user's score by mult pl cat veFactor

  output:
  - Sequence of Cand dateUser whose order represents t  rank ng of users  n a who-to-follow request
    T  rank ng  s sampled from a Placket-Luce d str but on.
   */
  overr de def transform(
    target: HasCl entContext w h HasParams w h HasDebugOpt ons,
    cand dates: Seq[Cand dateUser]
  ): St ch[Seq[Cand dateUser]] = {

    // t  f rst k pos  ons of t  who-to-follow rank ng correspond to t  users w h t  k
    // h g st scores and are not sampled from t  Placket-Luce d str but on
    val topKF xed = target.params(Sampl ngTransformParams.TopKF xed)

    // mult pl cat veFactor  s used to transform t  scores of each cand date by
    // mult ply ng that user's score by mult pl cat veFactor
    val mult pl cat veFactor = target.params(Sampl ngTransformParams.Mult pl cat veFactor)

    // sort cand dates by t  r score
    val cand datesSorted = cand dates.sortBy(-1 * _.score.getOrElse(0.0))

    // p ck t  top K cand dates by score and t  rema n ng cand dates
    val (topKF xedCand dates, cand datesOuts deOfTopK) =
      cand datesSorted.z pW h ndex.part  on { case (value,  ndex) =>  ndex < topKF xed }

    val randomNumGenerator =
      new scala.ut l.Random(target.getRandom zat onSeed.getOrElse(System.currentT  M ll s))

    //   need to subtract t  max mum score off t  scores for nu r cal stab l y reasons
    // subtract ng t  max score off does not effect t  underly ng d str but on   are sampl ng
    // t  cand dates from
    //   need t   f state nt s nce   cannot take t  max of an empty sequence
    val max mum_score =  f (cand datesOuts deOfTopK.nonEmpty) {
      cand datesOuts deOfTopK.map(x => x._1.score.getOrElse(0.0)).max
    } else {
      0.0
    }

    // for cand dates  n cand datesOuts deOfTopK,   transform t  r score by subtract ng off
    // max mum_score and t n mult ply by mult pl cat veFactor
    val cand datesOuts deOfTopKTransfor dScore = cand datesOuts deOfTopK.map(x =>
      (x._1, mult pl cat veFactor * (x._1.score.getOrElse(0.0) - max mum_score)))

    // for each cand date w h score transfor d and cl p score w, sample a random number r,
    // create a new score r^(1/w) and sort t  cand dates to get t  f nal rank ng.
    // for nu r cal stab l y reasons  f t  score  s <=-10,   force r^(1/w) = 0.
    // t  samples t  cand dates from t  mod f ed Plackett-Luce d str but on. See
    // https:// d um.com/swlh/go ng-old-school-des gn ng-algor hms-for-fast-  ghted-sampl ng- n-product on-c48fc1f40051

    val cand datesOuts deOfTopKSampled = cand datesOuts deOfTopKTransfor dScore
      .map(x =>
        (
          x._1,
           f (x._2 <= -10.0)
            0.0
          else
            scala.math.pow(
              randomNumGenerator.nextFloat(),
              1 / (scala.math
                .exp(x._2))))).sortBy(-1 * _._2)

    val topKCand dates: Seq[Cand dateUser] = topKF xedCand dates.map(_._1)

    val scr beRank ng nfo: Boolean =
      target.params(Sampl ngTransformParams.Scr beRank ng nfo nSampl ngTransform)

    val transfor dCand dates: Seq[Cand dateUser] =  f (scr beRank ng nfo) {
      val topKCand datesW hRank ng nfo: Seq[Cand dateUser] =
        Ut ls.addRank ng nfo(topKCand dates, na )
      val cand datesOuts deOfTopKSampledW hRank ng nfo: Seq[Cand dateUser] =
        cand datesOuts deOfTopKSampled.z pW h ndex.map {
          case ((cand date, score), rank) =>
            val newScore = Seq(Score(score, So (Ranker d.PlacketLuceSampl ngTransfor r)))
            val newScores: Opt on[Scores] = cand date.scores
              .map { scores =>
                scores.copy(scores = scores.scores ++ newScore)
              }.orElse(So (Scores(newScore, So (Ranker d.PlacketLuceSampl ngTransfor r))))
            val globalRank = rank + topKF xed + 1
            cand date.add nfoPerRank ngStage(na , newScores, globalRank)
        }

      topKCand datesW hRank ng nfo ++ cand datesOuts deOfTopKSampledW hRank ng nfo
    } else {
      topKCand dates ++ cand datesOuts deOfTopKSampled.map(_._1)
    }

    St ch.value(transfor dCand dates)
  }
}
