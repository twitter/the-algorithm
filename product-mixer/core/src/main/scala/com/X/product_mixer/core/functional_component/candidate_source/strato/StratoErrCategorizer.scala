package com.X.product_mixer.core.functional_component.candidate_source.strato

import com.X.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.X.product_mixer.core.pipeline.pipeline_failure.Unauthorized
import com.X.stitch.Stitch
import com.X.strato.response.Err

/**
 * Categorize Strato's Err messages to our PipelineFailures.
 *
 * This should be used by all strato-based candidate source, and we can
 * add more cases here as they're useful.
 */
object StratoErrCategorizer {
  val CategorizeStratoException: PartialFunction[Throwable, Stitch[Nothing]] = {
    case err @ Err(Err.Authorization, reason, context) =>
      Stitch.exception(
        PipelineFailure(Unauthorized, s"$reason [${context.toString}]", underlying = Some(err))
      )
  }
}
