package com.twitter.home_mixer.product.for_you.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.FollowsSportsAccountFeature
import com.twitter.home_mixer.product.for_you.param.ForYouParam.FollowingSportsGateUsersParam
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.strato.generated.client.socialgraph.service.ExistsClientColumn
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowingSportsAccountsQueryFeatureHydrator @Inject() (
  existsClientColumn: ExistsClientColumn)
  extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("FollowingSportsAccounts")

  override val features: Set[Feature[_, _]] = Set(FollowsSportsAccountFeature)

  val lookupContext = sg.LookupContext(includeInactive = false)

  private val fetcher: Fetcher[
    ExistsClientColumn.Key,
    ExistsClientColumn.View,
    ExistsClientColumn.Value
  ] = existsClientColumn.fetcher

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    query.getOptionalUserId match {
      case Some(userId) =>
        Stitch
          .traverse(query.params(FollowingSportsGateUsersParam).toSeq) { sportsAccountId =>
            val request =
              (userId, Seq(sg.Relationship(sg.RelationshipType.Following)), sportsAccountId)
            fetcher
              .fetch(request, lookupContext).map(_.v.getOrElse(false))
          }.map { followingAccounts: Seq[Boolean] =>
            FeatureMap(FollowsSportsAccountFeature, followingAccounts.contains(true))
          }
      case None => Stitch.value(FeatureMap(FollowsSportsAccountFeature, false))
    }

  }
}
