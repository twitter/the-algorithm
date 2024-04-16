package com.tw ter.ho _m xer.product.for_ .feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.AuthorEnabledPrev ewsFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.aud encerewards.aud enceRewardsServ ce.GetCreatorPreferencesOnUserCl entColumn

 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Hydrates t  `AuthorEnabledPrev ews` feature for t ets authored by creators by query ng t 
 * `GetCreatorPreferences` Strato column. T  feature corresponds to t  `prev ews_enabled` f eld of that column.
 * G ven a t et from a creator, t  feature  nd cates w t r that creator has enabled prev ews
 * on t  r prof le.
 */
@S ngleton
class AuthorEnabledPrev ewsFeatureHydrator @ nject() (
  getCreatorPreferencesOnUserCl entColumn: GetCreatorPreferencesOnUserCl entColumn)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("AuthorEnabledPrev ews")

  overr de val features: Set[Feature[_, _]] = Set(AuthorEnabledPrev ewsFeature)

  pr vate val fetc r = getCreatorPreferencesOnUserCl entColumn.fetc r

  pr vate val DefaultAuthorEnabledPrev ewsValue = true

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    val cand dateAuthors = cand dates
      .map(_.features.getOrElse(Author dFeature, None))
      .toSet
      .flatten

    // Bu ld a map of creator -> authorEnabledPrev ews, t n use   to populate cand date features
    val author dToFeatureSt ch = St ch.collect {
      cand dateAuthors
        .map { author =>
          val  sAuthorEnabledPrev ews = fetc r.fetch(author).map {
              _.v.map(_.prev ewsEnabled).getOrElse(DefaultAuthorEnabledPrev ewsValue)
          }
          (author,  sAuthorEnabledPrev ews)
        }.toMap
    }

    author dToFeatureSt ch.map { author dToFeatureMap =>
      cand dates.map {
        _.features.getOrElse(Author dFeature, None) match {
          case So (author d) => FeatureMapBu lder()
            .add(AuthorEnabledPrev ewsFeature, author dToFeatureMap(author d))
            .bu ld()
          case _ => FeatureMapBu lder()
            .add(AuthorEnabledPrev ewsFeature, DefaultAuthorEnabledPrev ewsValue)
            .bu ld()
        }
      }
    }
  }
}
