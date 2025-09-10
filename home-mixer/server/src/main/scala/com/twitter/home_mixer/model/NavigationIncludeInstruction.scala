package com.twitter.home_mixer.model

import com.twitter.home_mixer.model.request.DeviceContext.RequestContext
import com.twitter.home_mixer.model.request.HasDeviceContext
import com.twitter.product_mixer.component_library.premarshaller.urt.builder.IncludeInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.FSParam

/**
 * Include a navigation timeline instruction when we satisfy criteria
 */
case class NavigationIncludeInstruction(
  ptrEnableParam: FSParam[Boolean],
  coldStartEnableParam: FSParam[Boolean],
  warmStartEnableParam: FSParam[Boolean],
  navigateEnableParam: FSParam[Boolean],
  manualRefreshEnableParam: FSParam[Boolean])
    extends IncludeInstruction[PipelineQuery with HasDeviceContext] {

  override def apply(
    query: PipelineQuery with HasDeviceContext,
    entries: Seq[TimelineEntry]
  ): Boolean = {
    val requestContext = query.deviceContext.flatMap(_.requestContextValue)

    val ptrEnabled =
      query.params(ptrEnableParam) && requestContext.contains(RequestContext.PullToRefresh)
    val coldStartEnabled =
      query.params(coldStartEnableParam) && requestContext.contains(RequestContext.Launch)
    val warmStartEnabled =
      query.params(warmStartEnableParam) && requestContext.contains(RequestContext.Foreground)
    val manualRefreshEnabled =
      query.params(manualRefreshEnableParam) && requestContext.contains(
        RequestContext.ManualRefresh)
    val navigateEnabled =
      query.params(navigateEnableParam) && requestContext.contains(RequestContext.Navigate)

    ptrEnabled || coldStartEnabled || warmStartEnabled || manualRefreshEnabled || navigateEnabled
  }
}
