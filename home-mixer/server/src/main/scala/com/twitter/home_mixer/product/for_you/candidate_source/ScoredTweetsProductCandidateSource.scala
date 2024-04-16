package com.tw ter.ho _m xer.product.for_ .cand date_s ce

 mport com.google. nject.Prov der
 mport com.tw ter.ho _m xer.model.Ho Features.ServedT et dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.T  l neServ ceT etsFeature
 mport com.tw ter.ho _m xer.model.request.Ho M xerRequest
 mport com.tw ter.ho _m xer.model.request.ScoredT etsProduct
 mport com.tw ter.ho _m xer.model.request.ScoredT etsProductContext
 mport com.tw ter.ho _m xer.product.for_ .model.For Query
 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.UrtCursorSer al zer
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.product_p pel ne.ProductP pel neCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .ParamsBu lder
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.product.reg stry.ProductP pel neReg stry
 mport com.tw ter.t  l nes.render.{thr ftscala => tl}
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => st}
 mport com.tw ter.t etconvosvc.t et_ancestor.{thr ftscala => ta}
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * [[ScoredT etW hConversat on tadata]]
 **/
case class ScoredT etW hConversat on tadata(
  t et d: Long,
  author d: Long,
  score: Opt on[Double] = None,
  suggestType: Opt on[st.SuggestType] = None,
  s ceT et d: Opt on[Long] = None,
  s ceUser d: Opt on[Long] = None,
  quotedT et d: Opt on[Long] = None,
  quotedUser d: Opt on[Long] = None,
   nReplyToT et d: Opt on[Long] = None,
   nReplyToUser d: Opt on[Long] = None,
  d rectedAtUser d: Opt on[Long] = None,
   nNetwork: Opt on[Boolean] = None,
  sgsVal dL kedByUser ds: Opt on[Seq[Long]] = None,
  sgsVal dFollo dByUser ds: Opt on[Seq[Long]] = None,
  ancestors: Opt on[Seq[ta.T etAncestor]] = None,
  top c d: Opt on[Long] = None,
  top cFunct onal yType: Opt on[tl.Top cContextFunct onal yType] = None,
  conversat on d: Opt on[Long] = None,
  conversat onFocalT et d: Opt on[Long] = None,
   sReadFromCac : Opt on[Boolean] = None,
  streamToKafka: Opt on[Boolean] = None,
  exclus veConversat onAuthor d: Opt on[Long] = None,
  author sBlueVer f ed: Opt on[Boolean] = None,
  author sGoldVer f ed: Opt on[Boolean] = None,
  author sGrayVer f ed: Opt on[Boolean] = None,
  author sLegacyVer f ed: Opt on[Boolean] = None,
  author sCreator: Opt on[Boolean] = None,
  perspect veF lteredL kedByUser ds: Opt on[Seq[Long]] = None)

@S ngleton
class ScoredT etsProductCand dateS ce @ nject() (
  overr de val productP pel neReg stry: Prov der[ProductP pel neReg stry],
  overr de val paramsBu lder: Prov der[ParamsBu lder])
    extends ProductP pel neCand dateS ce[
      For Query,
      Ho M xerRequest,
      t.ScoredT etsResponse,
      ScoredT etW hConversat on tadata
    ] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er("ScoredT etsProduct")

  pr vate val MaxModuleS ze = 3
  pr vate val MaxAncestors nConversat on = 2

  overr de def p pel neRequestTransfor r(productP pel neQuery: For Query): Ho M xerRequest = {
    Ho M xerRequest(
      cl entContext = productP pel neQuery.cl entContext,
      product = ScoredT etsProduct,
      productContext = So (
        ScoredT etsProductContext(
          productP pel neQuery.dev ceContext,
          productP pel neQuery.seenT et ds,
          productP pel neQuery.features.map(_.getOrElse(ServedT et dsFeature, Seq.empty)),
          productP pel neQuery.features.map(_.getOrElse(T  l neServ ceT etsFeature, Seq.empty))
        )),
      ser al zedRequestCursor =
        productP pel neQuery.p pel neCursor.map(UrtCursorSer al zer.ser al zeCursor),
      maxResults = productP pel neQuery.requestedMaxResults,
      debugParams = None,
      ho RequestParam = false
    )
  }

  overr de def productP pel neResultTransfor r(
    productP pel neResult: t.ScoredT etsResponse
  ): Seq[ScoredT etW hConversat on tadata] = {
    val scoredT ets = productP pel neResult.scoredT ets.flatMap { focalT et =>
      val parentT ets = focalT et.ancestors.getOrElse(Seq.empty).sortBy(-_.t et d)
      val ( nter d ates, root) = parentT ets.spl At(parentT ets.s ze - 1)
      val truncated nter d ates =
         nter d ates.take(MaxModuleS ze - MaxAncestors nConversat on).reverse
      val rootScoredT et: Seq[ScoredT etW hConversat on tadata] = root.map { ancestor =>
        ScoredT etW hConversat on tadata(
          t et d = ancestor.t et d,
          author d = ancestor.user d,
          suggestType = focalT et.suggestType,
          conversat on d = So (ancestor.t et d),
          conversat onFocalT et d = So (focalT et.t et d),
          exclus veConversat onAuthor d = focalT et.exclus veConversat onAuthor d
        )
      }
      val conversat on d = rootScoredT et. adOpt on.map(_.t et d)

      val t etsToParents =
         f (parentT ets.nonEmpty) parentT ets.z p(parentT ets.ta l).toMap
        else Map.empty[ta.T etAncestor, ta.T etAncestor]

      val  nter d ateScoredT ets = truncated nter d ates.map { ancestor =>
        ScoredT etW hConversat on tadata(
          t et d = ancestor.t et d,
          author d = ancestor.user d,
          suggestType = focalT et.suggestType,
           nReplyToT et d = t etsToParents.get(ancestor).map(_.t et d),
          conversat on d = conversat on d,
          conversat onFocalT et d = So (focalT et.t et d),
          exclus veConversat onAuthor d = focalT et.exclus veConversat onAuthor d
        )
      }
      val parentScoredT ets = rootScoredT et ++  nter d ateScoredT ets

      val conversat onFocalT et d =
         f (parentScoredT ets.nonEmpty) So (focalT et.t et d) else None

      val focalScoredT et = ScoredT etW hConversat on tadata(
        t et d = focalT et.t et d,
        author d = focalT et.author d,
        score = focalT et.score,
        suggestType = focalT et.suggestType,
        s ceT et d = focalT et.s ceT et d,
        s ceUser d = focalT et.s ceUser d,
        quotedT et d = focalT et.quotedT et d,
        quotedUser d = focalT et.quotedUser d,
         nReplyToT et d = parentScoredT ets.lastOpt on.map(_.t et d),
         nReplyToUser d = focalT et. nReplyToUser d,
        d rectedAtUser d = focalT et.d rectedAtUser d,
         nNetwork = focalT et. nNetwork,
        sgsVal dL kedByUser ds = focalT et.sgsVal dL kedByUser ds,
        sgsVal dFollo dByUser ds = focalT et.sgsVal dFollo dByUser ds,
        top c d = focalT et.top c d,
        top cFunct onal yType = focalT et.top cFunct onal yType,
        ancestors = focalT et.ancestors,
        conversat on d = conversat on d,
        conversat onFocalT et d = conversat onFocalT et d,
         sReadFromCac  = focalT et. sReadFromCac ,
        streamToKafka = focalT et.streamToKafka,
        exclus veConversat onAuthor d = focalT et.exclus veConversat onAuthor d,
        author sBlueVer f ed = focalT et.author tadata.map(_.blueVer f ed),
        author sGoldVer f ed = focalT et.author tadata.map(_.goldVer f ed),
        author sGrayVer f ed = focalT et.author tadata.map(_.grayVer f ed),
        author sLegacyVer f ed = focalT et.author tadata.map(_.legacyVer f ed),
        author sCreator = focalT et.author tadata.map(_.creator),
        perspect veF lteredL kedByUser ds = focalT et.perspect veF lteredL kedByUser ds
      )

      parentScoredT ets :+ focalScoredT et
    }

    val dedupedT ets = scoredT ets.groupBy(_.t et d).map {
      case (_, dupl cateAncestors) => dupl cateAncestors.maxBy(_.score.getOrElse(0.0))
    }

    // Sort by t et  d to prevent  ssues w h future assumpt ons of t  root be ng t  f rst
    // t et and t  focal be ng t  last t et  n a module. T  t ets as a whole do not need
    // to be sorted overall, only t  relat ve order w h n modules must be kept.
    dedupedT ets.toSeq.sortBy(_.t et d)
  }
}
