package com.twitter.product_mitwitterer.component_library.module

import com.twitter.finagle.thriftmutwitter.MethodBuilder
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.onboarding.task.service.thriftscala.TaskService
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.util.Duration
import com.twitter.conversions.DurationOps._

object OnboardingTaskServiceModule
    etwittertends ThriftMethodBuilderClientModule[
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
