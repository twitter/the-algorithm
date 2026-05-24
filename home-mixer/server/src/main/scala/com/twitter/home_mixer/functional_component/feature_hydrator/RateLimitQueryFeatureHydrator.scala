package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.ViewerIsRateLimited
import com.twitter.home_mixer.param.HomeGlobalParams.RateLimitTestIdsParam
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.limiter.{thriftscala => t}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class RateLimitQueryFeatureHydrator @Inject() (limiterClient: t.LimitService.MethodPerEndpoint)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("RateLimit")

  override val features: Set[Feature[_, _]] = Set(ViewerIsRateLimited)

  val RegularFeature = "graphql_global_regular_tweets_read"
  val NewFeature = "graphql_global_new_tweets_read"
  val SoftFeature = "graphql_global_soft_user_tweets_read"
  val SuspendedFeature = "graphql_global_suspended_user_tweets_read"

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getOptionalUserId
    val appId = query.clientContext.appId

    val rateLimitTestIds = query.params(RateLimitTestIdsParam)

    val usagesStitch = Seq(
      t.FeatureRequest(RegularFeature, userId, applicationId = appId),
      t.FeatureRequest(NewFeature, userId, applicationId = appId),
      t.FeatureRequest(SoftFeature, userId, applicationId = appId),
      t.FeatureRequest(SuspendedFeature, userId, applicationId = appId)
    ).map { request => Stitch.callFuture(limiterClient.getLimitUsage(None, Some(request))) }

    Stitch.collect(usagesStitch).map { usage =>
      val limited =
        if (rateLimitTestIds.contains(userId.get))
          usage.map(u => u.remaining.toDouble / u.limit).exists(_ < 0.999)
        else usage.map(_.remaining).exists(_ == 0)

      FeatureMapBuilder().add(ViewerIsRateLimited, limited).build()
    }
  }

  override val alerts = Seq(HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(70))
}
