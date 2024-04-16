package com.tw ter.product_m xer.core.serv ce.group_results_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Platform dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateP pel nes
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateS cePos  on
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateS ces
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emPresentat on
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModulePresentat on
 mport com.tw ter.product_m xer.core.model.common.presentat on.Un versalPresentat on
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on. mmutable.L stSet

// Most executors are  n t  core.serv ce package, but t  one  s p pel ne spec f c
@S ngleton
class GroupResultsExecutor @ nject() (overr de val statsRece ver: StatsRece ver) extends Executor {

  val  dent f er: Component dent f er = Platform dent f er("GroupResults")

  def arrow[Cand date <: Un versalNoun[Any]](
    p pel ne dent f er: Cand dateP pel ne dent f er,
    s ce dent f er: Cand dateS ce dent f er,
    context: Executor.Context
  ): Arrow[GroupResultsExecutor nput[Cand date], GroupResultsExecutorResult] = {

    val groupArrow = Arrow.map {  nput: GroupResultsExecutor nput[Cand date] =>
      val modules: Map[Opt on[ModulePresentat on], Seq[Cand dateW hFeatures[Cand date]]] =
         nput.cand dates
          .map { cand date: Cand dateW hFeatures[Cand date] =>
            val modulePresentat onOpt: Opt on[ModulePresentat on] =
               nput.decorat ons.get(cand date.cand date).collect {
                case  emPresentat on:  emPresentat on
                     f  emPresentat on.modulePresentat on. sDef ned =>
                   emPresentat on.modulePresentat on.get
              }

            (cand date, modulePresentat onOpt)
          }.groupBy {
            case (_, modulePresentat onOpt) => modulePresentat onOpt
          }.mapValues {
            resultModuleOptTuples: Seq[
              (Cand dateW hFeatures[Cand date], Opt on[ModulePresentat on])
            ] =>
              resultModuleOptTuples.map {
                case (result, _) => result
              }
          }

      // Modules should be  n t  r or g nal order, but t  groupBy above  sn't stable.
      // Sort t m by t  s cePos  on, us ng t  s cePos  on of t  r f rst conta ned
      // cand date.
      val sortedModules: Seq[(Opt on[ModulePresentat on], Seq[Cand dateW hFeatures[Cand date]])] =
        modules.toSeq
          .sortBy {
            case (_, results) =>
              results. adOpt on.map(_.features.get(Cand dateS cePos  on))
          }

      val cand datesW hDeta ls: Seq[Cand dateW hDeta ls] = sortedModules.flatMap {
        case (modulePresentat onOpt, resultsSeq) =>
          val  emsW hDeta ls = resultsSeq.map { result =>
            val presentat onOpt =  nput.decorat ons.get(result.cand date) match {
              case  emPresentat on @ So (_:  emPresentat on) =>  emPresentat on
              case _ => None
            }

            val baseFeatureMap = FeatureMapBu lder()
              .add(Cand dateP pel nes, L stSet.empty + p pel ne dent f er)
              .bu ld()

             emCand dateW hDeta ls(
              cand date = result.cand date,
              presentat on = presentat onOpt,
              features = baseFeatureMap ++ result.features
            )
          }

          modulePresentat onOpt
            .map { modulePresentat on =>
              val moduleS cePos  on =
                resultsSeq. ad.features.get(Cand dateS cePos  on)
              val baseFeatureMap = FeatureMapBu lder()
                .add(Cand dateP pel nes, L stSet.empty + p pel ne dent f er)
                .add(Cand dateS cePos  on, moduleS cePos  on)
                .add(Cand dateS ces, L stSet.empty + s ce dent f er)
                .bu ld()

              Seq(
                ModuleCand dateW hDeta ls(
                  cand dates =  emsW hDeta ls,
                  presentat on = So (modulePresentat on),
                  features = baseFeatureMap
                ))
            }.getOrElse( emsW hDeta ls)
      }

      GroupResultsExecutorResult(cand datesW hDeta ls)
    }

    wrapW hErrorHandl ng(context,  dent f er)(groupArrow)
  }
}

case class GroupResultsExecutor nput[Cand date <: Un versalNoun[Any]](
  cand dates: Seq[Cand dateW hFeatures[Cand date]],
  decorat ons: Map[Un versalNoun[Any], Un versalPresentat on])

case class GroupResultsExecutorResult(cand datesW hDeta ls: Seq[Cand dateW hDeta ls])
    extends ExecutorResult
