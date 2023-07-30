package com.X.product_mixer.component_library.module

import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.onboarding.task.service.thriftscala.TaskService
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.util.Duration
import com.X.conversions.DurationOps._

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
