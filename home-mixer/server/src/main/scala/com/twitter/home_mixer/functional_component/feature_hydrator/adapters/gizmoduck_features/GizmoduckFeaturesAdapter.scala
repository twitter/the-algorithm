package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.gizmoduck_features

import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.features.gizmoduck.GizmoduckFeatures
import java.lang.{Boolean => JBoolean}

object GizmoduckFeaturesAdapter extends TimelinesMutatingAdapterBase[GFeatures] {

  override val getFeatureContext: FeatureContext = new FeatureContext(
    GizmoduckFeatures.AUTHOR_IS_BLUE_VERIFIED,
    GizmoduckFeatures.AUTHOR_IS_VERIFIED_ORGANIZATION,
    GizmoduckFeatures.AUTHOR_IS_VERIFIED_ORGANIZATION_AFFILIATE,
    GizmoduckFeatures.AUTHOR_IS_PROTECTED,
  )

  override val commonFeatures: Set[Feature[_]] = Set.empty

  override def candidateFeatures: Set[Feature[_]] = super.candidateFeatures
  override def setFeatures(
    contentFeatures: GFeatures,
    richDataRecord: RichDataRecord
  ): Unit = {
    richDataRecord.setFeatureValue[JBoolean](
      GizmoduckFeatures.AUTHOR_IS_BLUE_VERIFIED,
      contentFeatures.isBlueVerified
    )
    richDataRecord.setFeatureValue[JBoolean](
      GizmoduckFeatures.AUTHOR_IS_VERIFIED_ORGANIZATION,
      contentFeatures.isVerifiedOrganization
    )
    richDataRecord.setFeatureValue[JBoolean](
      GizmoduckFeatures.AUTHOR_IS_VERIFIED_ORGANIZATION_AFFILIATE,
      contentFeatures.isVerifiedOrganizationAffiliate
    )
    richDataRecord.setFeatureValue[JBoolean](
      GizmoduckFeatures.AUTHOR_IS_PROTECTED,
      contentFeatures.isProtected
    )
  }
}

trait GFeatures {
  def isBlueVerified: Boolean
  def isVerifiedOrganization: Boolean
  def isVerifiedOrganizationAffiliate: Boolean
  def isProtected: Boolean
}
