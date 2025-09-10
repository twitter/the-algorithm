package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.home_mixer.model.HomeFeatures.BasketballContextFeature
import com.twitter.home_mixer.model.HomeFeatures.GenericPostContextFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseTweetContextBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.icon
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata._
import com.twitter.product_mixer.core.pipeline.PipelineQuery

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeTweetContextBuilder @Inject() ()
    extends BaseTweetContextBuilder[PipelineQuery, TweetCandidate] {

  private val grokCom = "grok.com"

  private def mapBasketballStatus(originalStatus: Option[String]): Option[String] = {
    originalStatus.flatMap {
      case "Inprogress" | "Halftime" => Some("Live")
      case "Closed" | "Completed" => Some("Final")
      case "Created" | "Scheduled" => Some("Upcoming")
      case _ => None
    }
  }

  private def mapPoints(status: String, points: Option[Short]): Option[Short] = {
    if (status == "Live" || status == "Final") {
      points
    } else {
      None
    }
  }

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    features: FeatureMap
  ): Option[TweetContext] = {
    features
      .getOrElse(BasketballContextFeature, None).flatMap { basketballContext =>
        val mappedStatus = mapBasketballStatus(basketballContext.status)

        mappedStatus.map { status =>
          TweetContext(
            contextType = ContextType.Topic,
            text = "",
            landingUrl = None,
            contextImageUrls = None,
            context = Some(TweetContextDetails.Basketball(BasketballContext(
              clock = basketballContext.clock,
              homeTeamScore = mapPoints(status, basketballContext.homeTeamScore),
              awayTeamScore = mapPoints(status, basketballContext.awayTeamScore),
              homeTeamName = basketballContext.homeTeamName,
              awayTeamName = basketballContext.awayTeamName,
              status = Some(status),
              url = Url(
                urlType = DeepLink,
                url = basketballContext.url.url,
                urtEndpointOptions = None
              )
            ))),
            icon = None
          )
        }
      }.orElse {
        features.getOrElse(GenericPostContextFeature, None).map { genericContext =>
          val grokIconOpt = if (genericContext.url.url.contains(grokCom)) Some(icon.Grok) else None

          TweetContext(
            contextType = ContextType.Topic,
            text = genericContext.primaryText,
            landingUrl = None,
            contextImageUrls = None,
            context = Some(TweetContextDetails.Generic(GenericContext(
              primaryText = genericContext.primaryText,
              secondaryText = genericContext.secondaryText,
              url = Url(
                urlType = DeepLink,
                url = genericContext.url.url,
                urtEndpointOptions = None
              )
            ))),
            icon = grokIconOpt
          )
        }
      }
  }
}
