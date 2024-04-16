package com.tw ter.product_m xer.component_l brary.decorator.urt

 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.Urt emPresentat on
 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.UrtModulePresentat on
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Decorator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Decorat on
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.st ch.St ch
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.Module dGenerat on
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.Automat cUn queModule d
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseT  l neModuleBu lder

/**
 * G ven a [[Cand dateW hFeatures]] return t  correspond ng group w h wh ch   should be
 * assoc ated. Return ng none w ll result  n t  cand date not be ng ass gned to any module.
 */
tra  GroupByKey[-Query <: P pel neQuery, -Bu lder nput <: Un versalNoun[Any], Key] {
  def apply(query: Query, cand date: Bu lder nput, cand dateFeatures: FeatureMap): Opt on[Key]
}

/**
 * S m lar to [[Urt em nModuleDecorator]] except that t  decorator can ass gn  ems to d fferent
 * modules based on t  prov ded [[GroupByKey]].
 *
 * @param urt emCand dateDecorator decorates  nd v dual  em cand dates
 * @param moduleBu lder bu lds a module from a part cular cand date group
 * @param groupByKey ass gns each cand date a module group. Return ng [[None]] w ll result  n t 
 *                   cand date not be ng ass gned to a module
 */
case class UrtMult pleModulesDecorator[
  -Query <: P pel neQuery,
  -Bu lder nput <: Un versalNoun[Any],
  GroupKey
](
  urt emCand dateDecorator: Cand dateDecorator[Query, Bu lder nput],
  moduleBu lder: BaseT  l neModuleBu lder[Query, Bu lder nput],
  groupByKey: GroupByKey[Query, Bu lder nput, GroupKey],
  overr de val  dent f er: Decorator dent f er = Decorator dent f er("UrtMult pleModules"))
    extends Cand dateDecorator[Query, Bu lder nput] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Bu lder nput]]
  ): St ch[Seq[Decorat on]] = {
     f (cand dates.nonEmpty) {

      /**  nd v dual cand dates w h [[Urt emPresentat on]]s */
      val decoratedCand datesSt ch: St ch[
        Seq[(Cand dateW hFeatures[Bu lder nput], Decorat on)]
      ] = urt emCand dateDecorator(query, cand dates).map(cand dates.z p(_))

      decoratedCand datesSt ch.map { decoratedCand dates =>
        // Group cand dates  nto modules
        val cand datesByModule: Map[Opt on[GroupKey], Seq[
          (Cand dateW hFeatures[Bu lder nput], Decorat on)
        ]] =
          decoratedCand dates.groupBy {
            case (Cand dateW hFeatures(cand date, features), _) =>
              groupByKey(query, cand date, features)
          }

        cand datesByModule. erator.z pW h ndex.flatMap {

          // A None group key  nd cates t se cand dates should not be put  nto a module. Return
          // t  decorated cand dates.
          case ((None, cand dateGroup), _) =>
            cand dateGroup.map {
              case (_, decorat on) => decorat on
            }

          // Bu ld a UrtModulePresentat on and add   to each cand date's decorat on.
          case ((_, cand dateGroup),  ndex) =>
            val (cand datesW hFeatures, decorat ons) = cand dateGroup.unz p

            /**
             * Bu ld t  module and update  s  D  f [[Automat cUn queModule d]]s are be ng used.
             * Forc ng  Ds to be d fferent ensures that modules are never acc dentally grouped
             * toget r, s nce all ot r f elds m ght ot rw se be equal (cand dates aren't added
             * to modules unt l t  doma n marshall ng phase).
             */
            val t  l neModule = {
              val module = moduleBu lder(query, cand datesW hFeatures)

              Module dGenerat on(module. d) match {
                case  d: Automat cUn queModule d => module.copy( d =  d.w hOffset( ndex).module d)
                case _ => module
              }
            }

            val modulePresentat on = UrtModulePresentat on(t  l neModule)

            decorat ons.collect {
              case Decorat on(cand date, urt emPresentat on: Urt emPresentat on) =>
                Decorat on(
                  cand date,
                  urt emPresentat on.copy(modulePresentat on = So (modulePresentat on)))
            }
        }.toSeq
      }
    } else {
      St ch.N l
    }
  }
}
