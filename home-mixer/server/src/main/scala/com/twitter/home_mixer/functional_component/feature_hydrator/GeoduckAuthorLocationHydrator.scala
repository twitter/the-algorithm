package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.dal.personal_data.{thriftjava => pd}
import com.twitter.geoduck.common.thriftscala.TransactionLocation
import com.twitter.geoduck.common.{thriftscala => t}
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.product_mixer.component_library.feature.location.Location
import com.twitter.product_mixer.component_library.feature.location.LocationSharedFeatures.LocationFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.datarecord.DataRecordOptionalFeature
import com.twitter.product_mixer.core.feature.datarecord.LongDiscreteDataRecordCompatible
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.ExpiringLruInProcessCache
import com.twitter.servo.cache.InProcessCache
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.geo.service.UserLocationClientColumn
import com.twitter.timelines.prediction.features.location.LocationFeatures
import javax.inject.Inject
import javax.inject.Singleton

object AuthorLocationNeighborhoodFeature
    extends DataRecordOptionalFeature[PipelineQuery, Long]
    with LongDiscreteDataRecordCompatible {
  override val featureName: String =
    LocationFeatures.AUTHOR_LOCATION_NEIGHBORDHOOD.getFeatureName
  override val personalDataTypes: Set[pd.PersonalDataType] =
    Set(pd.PersonalDataType.InferredLocation)
}

object AuthorLocationCityFeature
    extends DataRecordOptionalFeature[PipelineQuery, Long]
    with LongDiscreteDataRecordCompatible {
  override val featureName: String =
    LocationFeatures.AUTHOR_LOCATION_CITY.getFeatureName
  override val personalDataTypes: Set[pd.PersonalDataType] =
    Set(pd.PersonalDataType.InferredLocation)
}

object AuthorLocationMetroFeature
    extends DataRecordOptionalFeature[PipelineQuery, Long]
    with LongDiscreteDataRecordCompatible {
  override val featureName: String =
    LocationFeatures.AUTHOR_LOCATION_METRO.getFeatureName
  override val personalDataTypes: Set[pd.PersonalDataType] =
    Set(pd.PersonalDataType.InferredLocation)
}

object AuthorLocationRegionFeature
    extends DataRecordOptionalFeature[PipelineQuery, Long]
    with LongDiscreteDataRecordCompatible {
  override val featureName: String =
    LocationFeatures.AUTHOR_LOCATION_REGION.getFeatureName
  override val personalDataTypes: Set[pd.PersonalDataType] =
    Set(pd.PersonalDataType.InferredLocation)
}

object AuthorLocationCountryFeature
    extends DataRecordOptionalFeature[PipelineQuery, Long]
    with LongDiscreteDataRecordCompatible {
  override val featureName: String =
    LocationFeatures.AUTHOR_LOCATION_COUNTRY.getFeatureName
  override val personalDataTypes: Set[pd.PersonalDataType] =
    Set(pd.PersonalDataType.InferredLocation)
}

object GeoduckAuthorLocationHydrator {
  private val BaseTTLMinutes = 60 * 24
  private val TTL = (BaseTTLMinutes + scala.util.Random.nextInt(60)).minutes

  val cache: InProcessCache[Long, Option[TransactionLocation]] =
    new ExpiringLruInProcessCache[Long, Option[TransactionLocation]](
      ttl = TTL,
      maximumSize = 150 * 1000 // Cache up to 150k users
    )
}

@Singleton
class GeoduckAuthorLocationHydrator @Inject() (
  userLocationClientColumn: UserLocationClientColumn)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "GeoduckAuthorLocationHydrator")

  override val features: Set[Feature[_, _]] = Set(
    LocationFeature,
    AuthorLocationNeighborhoodFeature,
    AuthorLocationCityFeature,
    AuthorLocationMetroFeature,
    AuthorLocationRegionFeature,
    AuthorLocationCountryFeature
  )

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

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val authorIds = candidates.flatMap(_.features.getOrElse(AuthorIdFeature, None)).distinct

    val (hitIds, missIds) = authorIds.partition(id =>
      GeoduckAuthorLocationHydrator.cache
        .get(id)
        .isDefined)

    val cachedOptionMap: Map[Long, Option[TransactionLocation]] =
      hitIds.map(id => id -> GeoduckAuthorLocationHydrator.cache.get(id).get).toMap

    val fetchCacheMisses: Stitch[Map[Long, TransactionLocation]] =
      if (missIds.isEmpty) Stitch.value(Map.empty[Long, TransactionLocation])
      else {
        userLocationClientColumn.fetcher
          .fetch(
            key = Unit,
            t.UserLocationRequest(
              userIds = missIds,
              placeQuery = Some(PlaceQuery)
            )
          )
          .map { response =>
            response.v.toList.flatMap(_._1).toMap
          }
          .handle { case _ => Map.empty[Long, TransactionLocation] }
      }

    val allLocationsStitch: Stitch[Map[Long, TransactionLocation]] =
      fetchCacheMisses.map { fetchedMap =>
        missIds.foreach { id =>
          val locOpt: Option[TransactionLocation] = fetchedMap.get(id)
          GeoduckAuthorLocationHydrator.cache.set(id, locOpt)
        }

        val cachedLocs: Map[Long, TransactionLocation] =
          cachedOptionMap.collect { case (id, Some(loc)) => id -> loc }

        cachedLocs ++ fetchedMap
      }

    allLocationsStitch.map { allLocations =>
      candidates.map { candidate =>
        val locOpt = for {
          authorId <- candidate.features.getOrElse(AuthorIdFeature, None)
          loc <- allLocations.get(authorId)
        } yield loc

        locOpt
          .map { transactionLocation =>
            val placeMap = transactionLocation.placeMap
            val locationDetails = Location(
              neighborhood = placeMap
                .flatMap(_.get(t.PlaceType.Neighborhood))
                .flatMap(_.headOption),
              city = placeMap.flatMap(_.get(t.PlaceType.City)).flatMap(_.headOption),
              metro = placeMap.flatMap(_.get(t.PlaceType.Metro)).flatMap(_.headOption),
              region = placeMap.flatMap(_.get(t.PlaceType.Admin1)).flatMap(_.headOption),
              country = placeMap.flatMap(_.get(t.PlaceType.Country)).flatMap(_.headOption)
            )

            FeatureMapBuilder()
              .add(LocationFeature, Option(locationDetails))
              .add(AuthorLocationNeighborhoodFeature, locationDetails.neighborhood)
              .add(AuthorLocationCityFeature, locationDetails.city)
              .add(AuthorLocationMetroFeature, locationDetails.metro)
              .add(AuthorLocationRegionFeature, locationDetails.region)
              .add(AuthorLocationCountryFeature, locationDetails.country)
              .build()
          }
          .getOrElse {
            FeatureMapBuilder()
              .add(LocationFeature, None)
              .add(AuthorLocationNeighborhoodFeature, None)
              .add(AuthorLocationCityFeature, None)
              .add(AuthorLocationMetroFeature, None)
              .add(AuthorLocationRegionFeature, None)
              .add(AuthorLocationCountryFeature, None)
              .build()
          }
      }
    }
  }
}
