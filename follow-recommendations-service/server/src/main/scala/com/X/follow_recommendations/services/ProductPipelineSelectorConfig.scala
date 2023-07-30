package com.X.follow_recommendations.services

import com.X.follow_recommendations.common.models.DisplayLocation
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.Param
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
