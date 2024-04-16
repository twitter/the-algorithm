package com.tw ter.product_m xer.component_l brary.cand date_s ce.bus ness_prof les

 mport com.tw ter.product_m xer.component_l brary.model.cursor.NextCursorFeature
 mport com.tw ter.product_m xer.component_l brary.model.cursor.Prev ousCursorFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato.StratoKeyV ewFetc rW hS ceFeaturesS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.generated.cl ent.consu r_ dent y.bus ness_prof les.Bus nessProf leTeam mbersOnUserCl entColumn
 mport com.tw ter.strato.generated.cl ent.consu r_ dent y.bus ness_prof les.Bus nessProf leTeam mbersOnUserCl entColumn.{
  Value => Team mbersSl ce
}
 mport com.tw ter.strato.generated.cl ent.consu r_ dent y.bus ness_prof les.Bus nessProf leTeam mbersOnUserCl entColumn.{
  V ew => Team mbersV ew
}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Team mbersCand dateS ce @ nject() (
  column: Bus nessProf leTeam mbersOnUserCl entColumn)
    extends StratoKeyV ewFetc rW hS ceFeaturesS ce[
      Long,
      Team mbersV ew,
      Team mbersSl ce,
      Long
    ] {
  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    "Bus nessProf leTeam mbers")

  overr de val fetc r: Fetc r[Long, Team mbersV ew, Team mbersSl ce] = column.fetc r

  overr de def stratoResultTransfor r(
    stratoKey: Long,
    stratoResult: Team mbersSl ce
  ): Seq[Long] =
    stratoResult. mbers

  overr de protected def extractFeaturesFromStratoResult(
    stratoKey: Long,
    stratoResult: Team mbersSl ce
  ): FeatureMap = {
    val featureMapBu lder = FeatureMapBu lder()
    stratoResult.prev ousCursor.foreach { cursor =>
      featureMapBu lder.add(Prev ousCursorFeature, cursor.toStr ng)
    }
    stratoResult.nextCursor.foreach { cursor =>
      featureMapBu lder.add(NextCursorFeature, cursor.toStr ng)
    }
    featureMapBu lder.bu ld()
  }
}
