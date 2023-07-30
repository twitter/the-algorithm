package com.X.visibility.util

import com.X.abdecider.ABDeciderFactory
import com.X.abdecider.LoggingABDecider
import com.X.decider.Decider
import com.X.decider.DeciderFactory
import com.X.decider.LocalOverrides
import com.X.logging._

object DeciderUtil {
  val DefaultDeciderPath = "/config/com/X/visibility/decider.yml"

  private val zone = Option(System.getProperty("dc")).getOrElse("atla")
  val DefaultDeciderOverlayPath: Some[String] = Some(
    s"/usr/local/config/overlays/visibility-library/visibility-library/prod/$zone/decider_overlay.yml"
  )

  val DefaultABDeciderPath = "/usr/local/config/abdecider/abdecider.yml"

  def mkDecider(
    deciderBasePath: String = DefaultDeciderPath,
    deciderOverlayPath: Option[String] = DefaultDeciderOverlayPath,
    useLocalDeciderOverrides: Boolean = false,
  ): Decider = {
    val fileBased = new DeciderFactory(Some(deciderBasePath), deciderOverlayPath)()
    if (useLocalDeciderOverrides) {
      LocalOverrides.decider("visibility-library").orElse(fileBased)
    } else {
      fileBased
    }
  }

  def mkLocalDecider: Decider = mkDecider(deciderOverlayPath = None)

  def mkABDecider(
    scribeLogger: Option[Logger],
    abDeciderPath: String = DefaultABDeciderPath
  ): LoggingABDecider = {
    ABDeciderFactory(
      abDeciderPath,
      Some("production"),
      scribeLogger = scribeLogger
    ).buildWithLogging()
  }
}
