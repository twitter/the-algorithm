package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.social_contetwittert

import com.twitter.hermit.{thriftscala => h}
import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FollowGeneralContetwittertType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.GeneralContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.GeneralContetwittertType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.LocationGeneralContetwittertType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.NewUserGeneralContetwittertType
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class WhoToFollowSocialContetwittertBuilder(
  socialTetwittertFeature: Feature[_, Option[String]],
  contetwittertTypeFeature: Feature[_, Option[h.ContetwittertType]])
    etwittertends BaseSocialContetwittertBuilder[PipelineQuery, UserCandidate] {

  def apply(
    query: PipelineQuery,
    candidate: UserCandidate,
    candidateFeatures: FeatureMap
  ): Option[GeneralContetwittert] = {
    val socialTetwittertOpt = candidateFeatures.getOrElse(socialTetwittertFeature, None)
    val contetwittertTypeOpt = convertContetwittertType(candidateFeatures.getOrElse(contetwittertTypeFeature, None))

    (socialTetwittertOpt, contetwittertTypeOpt) match {
      case (Some(socialTetwittert), Some(contetwittertType)) if socialTetwittert.nonEmpty =>
        Some(
          GeneralContetwittert(
            tetwittert = socialTetwittert,
            contetwittertType = contetwittertType,
            url = None,
            contetwittertImageUrls = None,
            landingUrl = None))
      case _ => None
    }
  }

  private def convertContetwittertType(contetwittertType: Option[h.ContetwittertType]): Option[GeneralContetwittertType] =
    contetwittertType match {
      case Some(h.ContetwittertType.Geo) => Some(LocationGeneralContetwittertType)
      case Some(h.ContetwittertType.Social) => Some(FollowGeneralContetwittertType)
      case Some(h.ContetwittertType.NewUser) => Some(NewUserGeneralContetwittertType)
      case _ => None
    }
}
