package com.X.product_mixer.core.service.transformer_executor

import com.X.finagle.stats.StatsReceiver
import com.X.product_mixer.core.functional_component.transformer.Transformer
import com.X.product_mixer.core.service.Executor
import com.X.stitch.Arrow

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransformerExecutor @Inject() (override val statsReceiver: StatsReceiver) extends Executor {
  def arrow[In, Out](
    transformer: Transformer[In, Out],
    context: Executor.Context
  ): Arrow[In, Out] = {
    wrapComponentWithExecutorBookkeeping(
      context,
      transformer.identifier
    )(Arrow.map(transformer.transform))
  }
}
