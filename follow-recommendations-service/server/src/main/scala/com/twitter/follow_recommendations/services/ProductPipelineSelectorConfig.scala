package com.twitter.follow_recommendations.services

import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.Param
import javax.inject.Singleton

@Singleton
class ProductPipelineSelectorConfig {
  private val paramsMap: Map[DisplayLocation, DarkReadAndExpParams] = Map.empty

  def getDarkReadAndExpParams(
    displayLocation: DisplayLocation
  ): Option[DarkReadAndExpParams] = {
    paramsMap.get(displayLocation)
  }
}

case class DarkReadAndExpParams(darkReadParam: Param[Boolean], expParam: FSParam[Boolean])
