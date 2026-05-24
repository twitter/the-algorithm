package com.twitter.product_mixer.component_library.module

import com.twitter.finagle.thriftmux.MethodBuilder
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.onboarding.task.service.thriftscala.TaskService
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration
import com.twitter.conversions.DurationOps._

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
