package com.tw ter.product_m xer.core.model.common.presentat on

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.UnexpectedCand dateResult
 mport scala.collect on. mmutable.L stSet
 mport scala.reflect.ClassTag

sealed tra  Cand dateW hDeta ls { self =>
  def presentat on: Opt on[Un versalPresentat on]
  def features: FeatureMap

  // last of t  set because  n L stSet, t  last ele nt  s t  f rst  nserted one w h O(1)
  // access
  lazy val s ce: Cand dateP pel ne dent f er = features.get(Cand dateP pel nes).last
  lazy val s cePos  on:  nt = features.get(Cand dateS cePos  on)

  /**
   * @see [[getCand date d]]
   */
  def cand date dLong: Long = getCand date d[Long]

  /**
   * @see [[getCand date d]]
   */
  def cand date dStr ng: Str ng = getCand date d[Str ng]

  /**
   * Conven ence  thod for retr ev ng a cand date  D off of t  base [[Cand dateW hDeta ls]] tra 
   * w hout manually pattern match ng.
   *
   * @throws P pel neFa lure  f Cand date dType does not match t  expected  em Cand date  d type,
   *                         or  f  nvoked on a Module Cand date
   */
  def getCand date d[Cand date dType](
  )(
     mpl c  tag: ClassTag[Cand date dType]
  ): Cand date dType =
    self match {
      case  em:  emCand dateW hDeta ls =>
         em.cand date. d match {
          case  d: Cand date dType =>  d
          case _ =>
            throw P pel neFa lure(
              UnexpectedCand dateResult,
              s" nval d  em Cand date  D type expected $tag for  em Cand date type ${ em.cand date.getClass}")
        }
      case _: ModuleCand dateW hDeta ls =>
        throw P pel neFa lure(
          UnexpectedCand dateResult,
          "Cannot retr eve  em Cand date  D for a Module")
    }

  /**
   * Conven ence  thod for retr ev ng a cand date off of t  base [[Cand dateW hDeta ls]] tra 
   * w hout manually pattern match ng.
   *
   * @throws P pel neFa lure  f Cand dateType does not match t  expected  em Cand date type, or
   *                          f  nvoked on a Module Cand date
   */
  def getCand date[Cand dateType <: Un versalNoun[_]](
  )(
     mpl c  tag: ClassTag[Cand dateType]
  ): Cand dateType =
    self match {
      case  emCand dateW hDeta ls(cand date: Cand dateType, _, _) => cand date
      case  em:  emCand dateW hDeta ls =>
        throw P pel neFa lure(
          UnexpectedCand dateResult,
          s" nval d  em Cand date type expected $tag for  em Cand date type ${ em.cand date.getClass}")
      case _: ModuleCand dateW hDeta ls =>
        throw P pel neFa lure(
          UnexpectedCand dateResult,
          "Cannot retr eve  em Cand date for a Module")
    }

  /**
   * Conven ence  thod for c ck ng  f t  conta ns a certa n cand date type
   *
   * @throws P pel neFa lure  f Cand dateType does not match t  expected  em Cand date type, or
   *                          f  nvoked on a Module Cand date
   */
  def  sCand dateType[Cand dateType <: Un versalNoun[_]](
  )(
     mpl c  tag: ClassTag[Cand dateType]
  ): Boolean = self match {
    case  emCand dateW hDeta ls(_: Cand dateType, _, _) => true
    case _ => false
  }
}

case class  emCand dateW hDeta ls(
  overr de val cand date: Un versalNoun[Any],
  presentat on: Opt on[Un versalPresentat on],
  overr de val features: FeatureMap)
    extends Cand dateW hDeta ls
    w h Cand dateW hFeatures[Un versalNoun[Any]]

case class ModuleCand dateW hDeta ls(
  cand dates: Seq[ emCand dateW hDeta ls],
  presentat on: Opt on[ModulePresentat on],
  overr de val features: FeatureMap)
    extends Cand dateW hDeta ls

object  emCand dateW hDeta ls {
  def apply(
    cand date: Un versalNoun[Any],
    presentat on: Opt on[Un versalPresentat on],
    s ce: Cand dateP pel ne dent f er,
    s cePos  on:  nt,
    features: FeatureMap
  ):  emCand dateW hDeta ls = {
    val newFeatureMap =
      FeatureMapBu lder()
        .add(Cand dateS cePos  on, s cePos  on)
        .add(Cand dateP pel nes, L stSet.empty + s ce).bu ld() ++ features
     emCand dateW hDeta ls(cand date, presentat on, newFeatureMap)
  }
}

object ModuleCand dateW hDeta ls {
  def apply(
    cand dates: Seq[ emCand dateW hDeta ls],
    presentat on: Opt on[ModulePresentat on],
    s ce: Cand dateP pel ne dent f er,
    s cePos  on:  nt,
    features: FeatureMap
  ): ModuleCand dateW hDeta ls = {
    val newFeatureMap =
      FeatureMapBu lder()
        .add(Cand dateS cePos  on, s cePos  on)
        .add(Cand dateP pel nes, L stSet.empty + s ce).bu ld() ++ features

    ModuleCand dateW hDeta ls(cand dates, presentat on, newFeatureMap)
  }
}
