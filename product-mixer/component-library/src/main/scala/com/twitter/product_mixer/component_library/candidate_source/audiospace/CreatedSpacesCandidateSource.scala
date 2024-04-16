package com.tw ter.product_m xer.component_l brary.cand date_s ce.aud ospace

 mport com.tw ter.per scope.aud o_space.thr ftscala.CreatedSpacesV ew
 mport com.tw ter.per scope.aud o_space.thr ftscala.SpaceSl ce
 mport com.tw ter.product_m xer.component_l brary.model.cursor.NextCursorFeature
 mport com.tw ter.product_m xer.component_l brary.model.cursor.Prev ousCursorFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato.StratoKeyV ewFetc rW hS ceFeaturesS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.generated.cl ent.per scope.CreatedSpacesSl ceOnUserCl entColumn
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CreatedSpacesCand dateS ce @ nject() (
  column: CreatedSpacesSl ceOnUserCl entColumn)
    extends StratoKeyV ewFetc rW hS ceFeaturesS ce[
      Long,
      CreatedSpacesV ew,
      SpaceSl ce,
      Str ng
    ] {

  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er("CreatedSpaces")

  overr de val fetc r: Fetc r[Long, CreatedSpacesV ew, SpaceSl ce] = column.fetc r

  overr de def stratoResultTransfor r(
    stratoKey: Long,
    stratoResult: SpaceSl ce
  ): Seq[Str ng] =
    stratoResult. ems

  overr de protected def extractFeaturesFromStratoResult(
    stratoKey: Long,
    stratoResult: SpaceSl ce
  ): FeatureMap = {
    val featureMapBu lder = FeatureMapBu lder()
    stratoResult.sl ce nfo.prev ousCursor.foreach { cursor =>
      featureMapBu lder.add(Prev ousCursorFeature, cursor)
    }
    stratoResult.sl ce nfo.nextCursor.foreach { cursor =>
      featureMapBu lder.add(NextCursorFeature, cursor)
    }
    featureMapBu lder.bu ld()
  }
}
