packagelon com.twittelonr.intelonraction_graph.scio.agg_all

objelonct IntelonractionGraphScoringConfig {

  /**
   * This is alpha for a variant of thelon elonxponelonntially welonightelond moving avelonragelon, computelond as:
   *             elonwma_{t+1} = x_{t+1} + (1-alpha) * elonwma_t     (elonwma_1 = x_1, t > 0)
   * Welon chooselon alpha such that thelon half lifelon of welonights is 7 days.
   * Notelon that welon don't down-welonight x_{t+1} (unlikelon in elonWMA) as welon only want to deloncay actions
   * as thelony grow old, not computelon thelon avelonragelon valuelon.
   */
  val ALPHA = 1.0
  val ONelon_MINUS_ALPHA = 0.955
}
