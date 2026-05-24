package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.home_mixer.model.HomeFeatures.DebugStringFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableDebugString
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.social_context.BaseSocialContextBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.CommunityGeneralContextType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.DeepLink
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.GeneralContext
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.util.Try

object DebugSocialContextBuilder extends BaseSocialContextBuilder[PipelineQuery, TweetCandidate] {

  val TweetUrl = ""
  val UserUrl = ""
  val TrendsUrl = ""
  val UserSignals = Set("Follow", "Profile")
  val Trends = "Trends"

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[SocialContext] = {
    if (query.params(EnableDebugString)) {
      candidateFeatures.getOrElse(DebugStringFeature, None).map { debugString =>
        val signalId = Try(debugString.split(" ").head.toLong).toOption
        val baseUrl =
          if (UserSignals.exists(debugString.contains)) UserUrl
          else if (debugString.contains(Trends)) TrendsUrl
          else TweetUrl

        val url = signalId.map { id =>
          Url(
            urlType = DeepLink,
            url = s"$baseUrl$id",
            urtEndpointOptions = None
          )
        }

        GeneralContext(
          contextType = CommunityGeneralContextType,
          text = debugString,
          url = None,
          contextImageUrls = None,
          landingUrl = url
        )
      }
    } else None
  }
}
