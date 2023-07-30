package com.X.home_mixer.marshaller.request

import com.X.home_mixer.model.request.DeviceContext
import com.X.home_mixer.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceContextUnmarshaller @Inject() () {

  def apply(deviceContext: t.DeviceContext): DeviceContext = {
    DeviceContext(
      isPolling = deviceContext.isPolling,
      requestContext = deviceContext.requestContext,
      latestControlAvailable = deviceContext.latestControlAvailable,
      autoplayEnabled = deviceContext.autoplayEnabled
    )
  }
}
