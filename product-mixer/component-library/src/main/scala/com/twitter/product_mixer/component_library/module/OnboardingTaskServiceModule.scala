package com.ExTwitter.product_mixer.component_library.module

import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.onboarding.task.service.thriftscala.TaskService
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.util.Duration
import com.ExTwitter.conversions.DurationOps._

object OnboardingTaskServiceModule
    extends ThriftMethodBuilderClientModule[
      TaskService.ServicePerEndpoint,
      TaskService.MethodPerEndpoint
    ]
    with MtlsClient {
  override val label: String = "onboarding-task-service"
  override val dest: String = "/s/onboarding-task-service/onboarding-task-service"

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder
      .withTimeoutPerRequest(500.millis)
      .withTimeoutTotal(1000.millis)
  }

  override protected def sessionAcquisitionTimeout: Duration = 500.milliseconds
}
