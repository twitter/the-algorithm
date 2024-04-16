package com.tw ter.product_m xer.core.serv ce.cand date_decorator_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Decorat on
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.st ch.Arrow

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cand dateDecoratorExecutor @ nject() (overr de val statsRece ver: StatsRece ver)
    extends Executor {
  def arrow[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
    decoratorOpt: Opt on[Cand dateDecorator[Query, Cand date]],
    context: Executor.Context
  ): Arrow[(Query, Seq[Cand dateW hFeatures[Cand date]]), Cand dateDecoratorExecutorResult] = {
    val decoratorArrow =
      decoratorOpt match {
        case So (decorator) =>
          val cand dateDecoratorArrow =
            Arrow.flatMap[(Query, Seq[Cand dateW hFeatures[Cand date]]), Seq[Decorat on]] {
              case (query, cand datesW hFeatures) => decorator.apply(query, cand datesW hFeatures)
            }

          wrapComponentW hExecutorBookkeep ng(context, decorator. dent f er)(
            cand dateDecoratorArrow)

        case _ => Arrow.value(Seq.empty[Decorat on])
      }

    decoratorArrow.map(Cand dateDecoratorExecutorResult)
  }
}
