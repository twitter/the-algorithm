package com.tw ter.ho _m xer.ut l

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Favor edByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Has mageFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features. d aUnderstand ngAnnotat on dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Repl edByEngager dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Ret etedByEngager dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScoreFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceUser dFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.CursorCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.UnexpectedCand dateResult
 mport scala.reflect.ClassTag

object Cand datesUt l {
  def get emCand dates(cand dates: Seq[Cand dateW hDeta ls]): Seq[ emCand dateW hDeta ls] = {
    cand dates.collect {
      case  em:  emCand dateW hDeta ls  f ! em. sCand dateType[CursorCand date] => Seq( em)
      case module: ModuleCand dateW hDeta ls => module.cand dates
    }.flatten
  }

  def get emCand datesW hOnlyModuleLast(
    cand dates: Seq[Cand dateW hDeta ls]
  ): Seq[ emCand dateW hDeta ls] = {
    cand dates.collect {
      case  em:  emCand dateW hDeta ls  f ! em. sCand dateType[CursorCand date] =>  em
      case module: ModuleCand dateW hDeta ls => module.cand dates.last
    }
  }

  def conta nsType[Cand dateType <: Un versalNoun[_]](
    cand dates: Seq[Cand dateW hDeta ls]
  )(
     mpl c  tag: ClassTag[Cand dateType]
  ): Boolean = cand dates.ex sts {
    case  emCand dateW hDeta ls(_: Cand dateType, _, _) => true
    case module: ModuleCand dateW hDeta ls =>
      module.cand dates. ad. sCand dateType[Cand dateType]()
    case _ => false
  }

  def getOr g nalT et d(cand date: Cand dateW hFeatures[T etCand date]): Long = {
     f (cand date.features.getOrElse( sRet etFeature, false))
      cand date.features.getOrElse(S ceT et dFeature, None).getOrElse(cand date.cand date. d)
    else
      cand date.cand date. d
  }

  def getOr g nalAuthor d(cand dateFeatures: FeatureMap): Opt on[Long] =
     f (cand dateFeatures.getOrElse( sRet etFeature, false))
      cand dateFeatures.getOrElse(S ceUser dFeature, None)
    else cand dateFeatures.getOrElse(Author dFeature, None)

  def  sOr g nalT et(cand date: Cand dateW hFeatures[T etCand date]): Boolean =
    !cand date.features.getOrElse( sRet etFeature, false) &&
      cand date.features.getOrElse( nReplyToT et dFeature, None). sEmpty

  def getEngagerUser ds(
    cand dateFeatures: FeatureMap
  ): Seq[Long] = {
    cand dateFeatures.getOrElse(Favor edByUser dsFeature, Seq.empty) ++
      cand dateFeatures.getOrElse(Ret etedByEngager dsFeature, Seq.empty) ++
      cand dateFeatures.getOrElse(Repl edByEngager dsFeature, Seq.empty)
  }

  def get d aUnderstand ngAnnotat on ds(
    cand dateFeatures: FeatureMap
  ): Seq[Long] = {
     f (cand dateFeatures.get(Has mageFeature))
      cand dateFeatures.getOrElse( d aUnderstand ngAnnotat on dsFeature, Seq.empty)
    else Seq.empty
  }

  def getT et dAndS ce d(cand date: Cand dateW hFeatures[T etCand date]): Seq[Long] =
    Seq(cand date.cand date. d) ++ cand date.features.getOrElse(S ceT et dFeature, None)

  def  sAuthoredByV e r(query: P pel neQuery, cand dateFeatures: FeatureMap): Boolean =
    cand dateFeatures.getOrElse(Author dFeature, None).conta ns(query.getRequ redUser d) ||
      (cand dateFeatures.getOrElse( sRet etFeature, false) &&
        cand dateFeatures.getOrElse(S ceUser dFeature, None).conta ns(query.getRequ redUser d))

  val reverseChronT etsOrder ng: Order ng[Cand dateW hDeta ls] =
    Order ng.by[Cand dateW hDeta ls, Long] {
      case  emCand dateW hDeta ls(cand date: T etCand date, _, _) => -cand date. d
      case ModuleCand dateW hDeta ls(cand dates, _, _)  f cand dates.nonEmpty =>
        -cand dates.last.cand date dLong
      case _ => throw P pel neFa lure(UnexpectedCand dateResult, " nval d cand date type")
    }

  val scoreOrder ng: Order ng[Cand dateW hDeta ls] = Order ng.by[Cand dateW hDeta ls, Double] {
    case  emCand dateW hDeta ls(_, _, features) =>
      -features.getOrElse(ScoreFeature, None).getOrElse(0.0)
    case ModuleCand dateW hDeta ls(cand dates, _, _) =>
      -cand dates.last.features.getOrElse(ScoreFeature, None).getOrElse(0.0)
    case _ => throw P pel neFa lure(UnexpectedCand dateResult, " nval d cand date type")
  }

  val conversat onModuleT etsOrder ng: Order ng[Cand dateW hDeta ls] =
    Order ng.by[Cand dateW hDeta ls, Long] {
      case  emCand dateW hDeta ls(cand date: T etCand date, _, _) => cand date. d
      case _ => throw P pel neFa lure(UnexpectedCand dateResult, "Only  em cand date expected")
    }
}
