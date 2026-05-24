package com.twitter.follow_recommendations.common.models

import com.twitter.adserver.{thriftscala => t}

case class AdMetadata(
  insertPosition: Int,
  // use original ad impression info to avoid losing data in domain model translations
  adImpression: t.AdImpression)

trait HasAdMetadata {

  def adMetadata: Option[AdMetadata]

  def adImpression: Option[t.AdImpression] = {
    adMetadata.map(_.adImpression)
  }

  def insertPosition: Option[Int] = {
    adMetadata.map(_.insertPosition)
  }

  def isPromotedAccount: Boolean = adMetadata.isDefined
}
