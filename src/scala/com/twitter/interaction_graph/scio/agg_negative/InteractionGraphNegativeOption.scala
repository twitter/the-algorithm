package com.twitter.interaction_graph.scio.agg_negative

import com.twitter.beam.io.dal.DALOptions
import com.twitter.beam.job.DateRangeOptions
import org.apache.beam.sdk.options.Description
import org.apache.beam.sdk.options.Validation.Required

trait InteractionGraphNegativeOption etwittertends DALOptions with DateRangeOptions {
  @Required
  @Description("Output path for storing the final dataset")
  def getOutputPath: String
  def setOutputPath(value: String): Unit

  @Description("BQ dataset prefitwitter")
  def getBqDataset: String
  def setBqDataset(value: String): Unit

}
