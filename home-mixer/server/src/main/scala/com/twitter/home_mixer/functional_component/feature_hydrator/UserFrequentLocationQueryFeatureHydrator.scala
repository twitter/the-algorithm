package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.product_mixer.component_library.feature.location.{Location => ProductLocation}
import com.twitter.product_mixer.component_library.feature.location.LocationSharedFeatures.LocationFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.geoduck.common.{thriftscala => t}
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.geo.service.UserLocationClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserFrequentLocationQueryFeatureHydrator @Inject() (
  userLocationClientColumn: UserLocationClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserFrequentLocationQuery")

  override val features: Set[Feature[_, _]] = Set(LocationFeature)

  private val PlaceQuery = t.PlaceQuery(
    placeTypes = Some(
      Set(
        t.PlaceType.Neighborhood,
        t.PlaceType.City,
        t.PlaceType.Metro,
        t.PlaceType.Admin1,
        t.PlaceType.Country
      )
    )
  )

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(LocationFeature, None)
    .build()

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    val locationStitch = userLocationClientColumn.fetcher
      .fetch(
        key = Unit,
        t.UserLocationRequest(
          userIds = Seq(userId),
          placeQuery = Some(PlaceQuery)
        )
      )
      .map { response =>
        response.v.toList.flatMap(_._1).toMap.get(userId)
      }
    locationStitch
      .map { locationOpt =>
        locationOpt
          .map { location =>
            val placeMap = location.placeMap
            val locationDetails = ProductLocation(
              neighborhood = placeMap
                .flatMap(_.get(t.PlaceType.Neighborhood))
                .flatMap(_.headOption),
              city = placeMap
                .flatMap(_.get(t.PlaceType.City))
                .flatMap(_.headOption),
              metro = placeMap
                .flatMap(_.get(t.PlaceType.Metro))
                .flatMap(_.headOption),
              region = placeMap
                .flatMap(_.get(t.PlaceType.Admin1))
                .flatMap(_.headOption),
              country = placeMap
                .flatMap(_.get(t.PlaceType.Country))
                .flatMap(_.headOption)
            )
            FeatureMapBuilder()
              .add(LocationFeature, Option(locationDetails))
              .build()
          }
          .getOrElse {
            DefaultFeatureMap
          }
      }.handle {
        case e =>
          DefaultFeatureMap
      }
  }
}
