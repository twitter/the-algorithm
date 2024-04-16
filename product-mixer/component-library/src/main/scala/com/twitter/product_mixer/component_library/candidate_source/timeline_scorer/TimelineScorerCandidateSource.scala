package com.tw ter.product_m xer.component_l brary.cand date_s ce.t  l ne_scorer

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ceW hExtractedFeatures
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand datesW hS ceFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nescorer.common.scoredt etcand date.thr ftscala.v1
 mport com.tw ter.t  l nescorer.common.scoredt etcand date.thr ftscala.v1.Ancestor
 mport com.tw ter.t  l nescorer.common.scoredt etcand date.{thr ftscala => ct}
 mport com.tw ter.t  l nescorer.{thr ftscala => t}
 mport com.tw ter.t  l neserv ce.suggests.logg ng.cand date_t et_s ce_ d.thr ftscala.Cand dateT etS ce d
 mport javax. nject. nject
 mport javax. nject.S ngleton

case class ScoredT etCand dateW hFocalT et(
  cand date: v1.ScoredT etCand date,
  focalT et dOpt: Opt on[Long])

case object T  l neScorerCand dateS ceSucceededFeature extends Feature[P pel neQuery, Boolean]

@S ngleton
class T  l neScorerCand dateS ce @ nject() (
  t  l neScorerCl ent: t.T  l neScorer. thodPerEndpo nt)
    extends Cand dateS ceW hExtractedFeatures[
      t.ScoredT etsRequest,
      ScoredT etCand dateW hFocalT et
    ] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er("T  l neScorer")

  pr vate val MaxConversat onAncestors = 2

  overr de def apply(
    request: t.ScoredT etsRequest
  ): St ch[Cand datesW hS ceFeatures[ScoredT etCand dateW hFocalT et]] = {
    St ch
      .callFuture(t  l neScorerCl ent.getScoredT ets(request))
      .map { response =>
        val scoredT etsOpt = response match {
          case t.ScoredT etsResponse.V1(v1) => v1.scoredT ets
          case t.ScoredT etsResponse.UnknownUn onF eld(f eld) =>
            throw new UnsupportedOperat onExcept on(s"Unknown response type: ${f eld.f eld.na }")
        }
        val scoredT ets = scoredT etsOpt.getOrElse(Seq.empty)

        val allAncestors = scoredT ets.flatMap {
          case ct.ScoredT etCand date.V1(v1)  f  sEl g bleReply(v1) =>
            v1.ancestors.get.map(_.t et d)
          case _ => Seq.empty
        }.toSet

        // Remove t ets w h n ancestor l st of ot r t ets to avo d serv ng dupl cates
        val keptT ets = scoredT ets.collect {
          case ct.ScoredT etCand date.V1(v1)  f !allAncestors.conta ns(or g nalT et d(v1)) => v1
        }

        // Add parent and root t et for el g ble reply focal t ets
        val cand dates = keptT ets
          .flatMap {
            case v1  f  sEl g bleReply(v1) =>
              val ancestors = v1.ancestors.get
              val focalT et d = v1.t et d

              //  nclude root t et  f t  conversat on has atleast 2 ancestors
              val opt onally ncludedRootT et =  f (ancestors.s ze >= MaxConversat onAncestors) {
                val rootT et = toScoredT etCand dateFromAncestor(
                  ancestor = ancestors.last,
                   nReplyToT et d = None,
                  conversat on d = v1.conversat on d,
                  ancestors = None,
                  cand dateT etS ce d = v1.cand dateT etS ce d
                )
                Seq((rootT et, So (v1)))
              } else Seq.empty

              /**
               * Sett ng t   n-reply-to t et  d on t   m d ate parent,  f one ex sts,
               *  lps ensure t et type  tr cs correctly d st ngu sh roots from non-roots.
               */
              val  nReplyToT et d = ancestors.ta l. adOpt on.map(_.t et d)
              val parentAncestor = toScoredT etCand dateFromAncestor(
                ancestor = ancestors. ad,
                 nReplyToT et d =  nReplyToT et d,
                conversat on d = v1.conversat on d,
                ancestors = So (ancestors.ta l),
                cand dateT etS ce d = v1.cand dateT etS ce d
              )

              opt onally ncludedRootT et ++
                Seq((parentAncestor, So (v1)), (v1, So (v1)))

            case any => Seq((any, None)) // Set focalT et d to None  f not el g ble for convo
          }

        /**
         * Dedup each t et keep ng t  one w h h g st scored Focal T et
         * Focal T et  D != t  Conversat on  D, wh ch  s set to t  root of t  conversat on
         * Focal T et  D w ll be def ned for t ets w h ancestors that should be
         *  n conversat on modules and None for standalone t ets.
         */
        val sortedDedupedCand dates = cand dates
          .groupBy { case (v1, _) => v1.t et d }
          .mapValues { group =>
            val (cand date, focalT etOpt) = group.maxBy {
              case (_, So (focal)) => focal.score
              case (_, None) => 0
            }
            ScoredT etCand dateW hFocalT et(cand date, focalT etOpt.map(focal => focal.t et d))
          }.values.toSeq.sortBy(_.cand date.t et d)

        Cand datesW hS ceFeatures(
          cand dates = sortedDedupedCand dates,
          features = FeatureMapBu lder()
            .add(T  l neScorerCand dateS ceSucceededFeature, true)
            .bu ld()
        )
      }
  }

  pr vate def  sEl g bleReply(cand date: ct.ScoredT etCand dateAl ases.V1Al as): Boolean = {
    cand date. nReplyToT et d.nonEmpty &&
    !cand date. sRet et.getOrElse(false) &&
    cand date.ancestors.ex sts(_.nonEmpty)
  }

  /**
   *  f   have a ret et, get t  s ce t et  d.
   *  f    s not a ret et, get t  regular t et  d.
   */
  pr vate def or g nalT et d(cand date: ct.ScoredT etCand dateAl ases.V1Al as): Long = {
    cand date.s ceT et d.getOrElse(cand date.t et d)
  }

  pr vate def toScoredT etCand dateFromAncestor(
    ancestor: Ancestor,
     nReplyToT et d: Opt on[Long],
    conversat on d: Opt on[Long],
    ancestors: Opt on[Seq[Ancestor]],
    cand dateT etS ce d: Opt on[Cand dateT etS ce d]
  ): ct.ScoredT etCand dateAl ases.V1Al as = {
    ct.v1.ScoredT etCand date(
      t et d = ancestor.t et d,
      author d = ancestor.user d.getOrElse(0L),
      score = 0.0,
       sAncestorCand date = So (true),
       nReplyToT et d =  nReplyToT et d,
      conversat on d = conversat on d,
      ancestors = ancestors,
      cand dateT etS ce d = cand dateT etS ce d
    )
  }
}
