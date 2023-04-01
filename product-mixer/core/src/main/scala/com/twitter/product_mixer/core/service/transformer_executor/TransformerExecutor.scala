package com.twitter.product_mixer.core.service.transformer_executor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.functional_component.transformer.Transformer
import com.twitter.product_mixer.core.service.Executor
import com.twitter.stitch.Arrow

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
