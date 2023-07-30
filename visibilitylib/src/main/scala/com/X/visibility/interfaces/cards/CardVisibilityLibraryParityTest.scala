package com.X.visibility.interfaces.cards

import com.X.finagle.stats.StatsReceiver
import com.X.stitch.Stitch
import com.X.visibility.builder.VisibilityResult

class CardVisibilityLibraryParityTest(statsReceiver: StatsReceiver) {
  private val parityTestScope = statsReceiver.scope("card_visibility_library_parity")
  private val requests = parityTestScope.counter("requests")
  private val equal = parityTestScope.counter("equal")
  private val incorrect = parityTestScope.counter("incorrect")
  private val failures = parityTestScope.counter("failures")

  def runParityTest(
    preHydratedFeatureVisibilityResult: Stitch[VisibilityResult],
    resp: VisibilityResult
  ): Stitch[Unit] = {
    requests.incr()

    preHydratedFeatureVisibilityResult
      .flatMap { parityResponse =>
        if (parityResponse.verdict == resp.verdict) {
          equal.incr()
        } else {
          incorrect.incr()
        }

        Stitch.Done
      }.rescue {
        case t: Throwable =>
          failures.incr()
          Stitch.Done
      }.unit
  }
}
