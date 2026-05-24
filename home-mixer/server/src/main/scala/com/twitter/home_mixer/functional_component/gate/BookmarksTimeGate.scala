package com.twitter.home_mixer.functional_component.gate

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.home_mixer.model.HomeFeatures.HasDarkRequestFeature
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableBookmarksModuleWeekendGate
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import java.time.DayOfWeek
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookmarksTimeGate @Inject() (serviceIdentifier: ServiceIdentifier)
    extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("BookmarksTime")

  // Serve on the weekends, with 48 hours injection time since persistence store is only 2 days. This way we have 2
  // chances to show the module both on Saturday and Sunday
  private def isWeekend(zonedDateTime: ZonedDateTime) = {
    zonedDateTime.getDayOfWeek match {
      case DayOfWeek.SATURDAY => true
      case DayOfWeek.SUNDAY => true
      case _ => false
    }

  }
  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val gateDisabled = !query.params(EnableBookmarksModuleWeekendGate)
    val isDevel = serviceIdentifier.environment.toLowerCase != "prod"
    val isDarkRequest = query.features.flatMap { _.get(HasDarkRequestFeature) }.getOrElse(false)
    Stitch.value(
      gateDisabled || isDarkRequest || isDevel || isWeekend(query.queryTime.toZonedDateTime))
  }
}
