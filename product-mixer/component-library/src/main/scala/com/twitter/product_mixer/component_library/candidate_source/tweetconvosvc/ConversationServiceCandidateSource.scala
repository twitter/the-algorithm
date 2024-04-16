package com.tw ter.product_m xer.component_l brary.cand date_s ce.t etconvosvc

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ceW hExtractedFeatures
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand datesW hS ceFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etconvosvc.t et_ancestor.{thr ftscala => ta}
 mport com.tw ter.t etconvosvc.{thr ftscala => tcs}
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport javax. nject. nject
 mport javax. nject.S ngleton

case class Conversat onServ ceCand dateS ceRequest(
  t etsW hConversat on tadata: Seq[T etW hConversat on tadata])

case class T etW hConversat on tadata(
  t et d: Long,
  user d: Opt on[Long],
  s ceT et d: Opt on[Long],
  s ceUser d: Opt on[Long],
   nReplyToT et d: Opt on[Long],
  conversat on d: Opt on[Long],
  ancestors: Seq[ta.T etAncestor])

/**
 * Cand date s ce that fetc s ancestors of  nput cand dates from T etconvosvc and
 * returns a flattened l st of  nput and ancestor cand dates.
 */
@S ngleton
class Conversat onServ ceCand dateS ce @ nject() (
  conversat onServ ceCl ent: tcs.Conversat onServ ce. thodPerEndpo nt)
    extends Cand dateS ceW hExtractedFeatures[
      Conversat onServ ceCand dateS ceRequest,
      T etW hConversat on tadata
    ] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er("Conversat onServ ce")

  pr vate val maxModuleS ze = 3
  pr vate val maxAncestors nConversat on = 2
  pr vate val numberOfRootT ets = 1
  pr vate val maxT ets nConversat onW hSa  d = 1

  overr de def apply(
    request: Conversat onServ ceCand dateS ceRequest
  ): St ch[Cand datesW hS ceFeatures[T etW hConversat on tadata]] = {
    val  nputT etsW hConversat on tadata: Seq[T etW hConversat on tadata] =
      request.t etsW hConversat on tadata
    val ancestorsRequest =
      tcs.GetAncestorsRequest( nputT etsW hConversat on tadata.map(_.t et d))

    // bu ld t  t ets w h conversat on  tadata by call ng t  conversat on serv ce w h reduced
    // ancestors to l m  to maxModuleS ze
    val t etsW hConversat on tadataFromAncestors: St ch[Seq[T etW hConversat on tadata]] =
      St ch
        .callFuture(conversat onServ ceCl ent.getAncestors(ancestorsRequest))
        .map { getAncestorsResponse: tcs.GetAncestorsResponse =>
           nputT etsW hConversat on tadata
            .z p(getAncestorsResponse.ancestors).collect {
              case (focalT et, tcs.T etAncestorsResult.T etAncestors(ancestorsResult))
                   f ancestorsResult.nonEmpty =>
                getT ets nThread(focalT et, ancestorsResult. ad)
            }.flatten
        }

    // dedupe t  t ets  n t  l st and transform t  call ng error to
    // return t  requested t ets w h conversat on  tadata
    val transfor dT etsW hConversat on tadata: St ch[Seq[T etW hConversat on tadata]] =
      t etsW hConversat on tadataFromAncestors.transform {
        case Return(ancestors) =>
          St ch.value(dedupeCand dates( nputT etsW hConversat on tadata, ancestors))
        case Throw(_) =>
          St ch.value( nputT etsW hConversat on tadata)
      }

    // return t  cand dates w h empty s ce features from transfor d t etsW hConversat on tadata
    transfor dT etsW hConversat on tadata.map {
      responseT etsW hConversat on tadata: Seq[T etW hConversat on tadata] =>
        Cand datesW hS ceFeatures(
          responseT etsW hConversat on tadata,
          FeatureMap.empty
        )
    }
  }

  pr vate def getT ets nThread(
    focalT et: T etW hConversat on tadata,
    ancestors: ta.T etAncestors
  ): Seq[T etW hConversat on tadata] = {
    // Re-add t  focal t et so   can eas ly bu ld modules and dedupe later.
    // Note, T etConvoSVC returns t  bottom of t  thread f rst, so  
    // reverse t m for easy render ng.
    val focalT etW hConversat on tadata = T etW hConversat on tadata(
      t et d = focalT et.t et d,
      user d = focalT et.user d,
      s ceT et d = focalT et.s ceT et d,
      s ceUser d = focalT et.s ceUser d,
       nReplyToT et d = focalT et. nReplyToT et d,
      conversat on d = So (focalT et.t et d),
      ancestors = ancestors.ancestors
    )

    val parentT ets = ancestors.ancestors.map { ancestor =>
      T etW hConversat on tadata(
        t et d = ancestor.t et d,
        user d = So (ancestor.user d),
        s ceT et d = None,
        s ceUser d = None,
         nReplyToT et d = None,
        conversat on d = So (focalT et.t et d),
        ancestors = Seq.empty
      )
    } ++ getTruncatedRootT et(ancestors, focalT et.t et d)

    val ( nter d ates, root) = parentT ets.spl At(parentT ets.s ze - numberOfRootT ets)
    val truncated nter d ates =
       nter d ates.take(maxModuleS ze - maxAncestors nConversat on).reverse
    root ++ truncated nter d ates :+ focalT etW hConversat on tadata
  }

  /**
   * Ancestor store truncates at 256 ancestors. For very large reply threads,   try best effort
   * to append t  root t et to t  ancestor l st based on t  conversat on d and
   * conversat onRootAuthor d. W n render ng conversat on modules,   can d splay t  root t et
   *  nstead of t  256th h g st ancestor.
   */
  pr vate def getTruncatedRootT et(
    ancestors: ta.T etAncestors,
    focalT et d: Long
  ): Opt on[T etW hConversat on tadata] = {
    ancestors.conversat onRootAuthor d.collect {
      case rootAuthor d
           f ancestors.state == ta.ReplyState.Part al &&
            ancestors.ancestors.last.t et d != ancestors.conversat on d =>
        T etW hConversat on tadata(
          t et d = ancestors.conversat on d,
          user d = So (rootAuthor d),
          s ceT et d = None,
          s ceUser d = None,
           nReplyToT et d = None,
          conversat on d = So (focalT et d),
          ancestors = Seq.empty
        )
    }
  }

  pr vate def dedupeCand dates(
     nputT etsW hConversat on tadata: Seq[T etW hConversat on tadata],
    ancestors: Seq[T etW hConversat on tadata]
  ): Seq[T etW hConversat on tadata] = {
    val dedupedAncestors:  erable[T etW hConversat on tadata] = ancestors
      .groupBy(_.t et d).map {
        case (_, dupl cateAncestors)
             f dupl cateAncestors.s ze > maxT ets nConversat onW hSa  d =>
          dupl cateAncestors.maxBy(_.conversat on d.getOrElse(0L))
        case (_, nonDupl cateAncestors) => nonDupl cateAncestors. ad
      }
    // Sort by t et  d to prevent  ssues w h future assumpt ons of t  root be ng t  f rst
    // t et and t  focal be ng t  last t et  n a module. T  t ets as a whole do not need
    // to be sorted overall, only t  relat ve order w h n modules must be kept.
    val sortedDedupedAncestors: Seq[T etW hConversat on tadata] =
      dedupedAncestors.toSeq.sortBy(_.t et d)

    val ancestor ds = sortedDedupedAncestors.map(_.t et d).toSet
    val updatedCand dates =  nputT etsW hConversat on tadata.f lterNot { cand date =>
      ancestor ds.conta ns(cand date.t et d)
    }
    sortedDedupedAncestors ++ updatedCand dates
  }
}
