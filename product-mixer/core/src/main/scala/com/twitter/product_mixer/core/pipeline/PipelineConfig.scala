package com.tw ter.product_m xer.core.p pel ne

 mport com.tw ter.product_m xer.core.model.common. dent f er.HasComponent dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er

tra  P pel neConf g extends HasComponent dent f er

tra  P pel neConf gCompan on {

  /** used to generate `AsyncFeaturesFor` [[P pel neStep dent f er]]s for t   nternal Async Features Step */
  pr vate[core] def asyncFeaturesStep(
    stepToHydrateFor: P pel neStep dent f er
  ): P pel neStep dent f er =
    P pel neStep dent f er("AsyncFeaturesFor" + stepToHydrateFor.na )

  /** All t  Steps wh ch are executed by a [[P pel ne]]  n t  order  n wh ch t y are run */
  val steps nOrder: Seq[P pel neStep dent f er]

  val stepsAsyncFeatureHydrat onCanBeCompletedBy: Set[P pel neStep dent f er] = Set.empty
}
