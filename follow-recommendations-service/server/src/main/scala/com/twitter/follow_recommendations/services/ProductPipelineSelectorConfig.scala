package com.twitter.follow_recommendations.services

import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param
import javatwitter.inject.Singleton

@Singleton
class ProductPipelineSelectorConfig {
  private val paramsMap: Map[DisplayLocation, DarkReadAndEtwitterpParams] = Map.empty

  def getDarkReadAndEtwitterpParams(
    displayLocation: DisplayLocation
  ): Option[DarkReadAndEtwitterpParams] = {
    paramsMap.get(displayLocation)
  }
}

case class DarkReadAndEtwitterpParams(darkReadParam: Param[Boolean], etwitterpParam: FSParam[Boolean])
