package com.X.interaction_graph.scio.agg_negative

import com.X.beam.io.dal.DALOptions
import com.X.beam.job.DateRangeOptions
import org.apache.beam.sdk.options.Description
import org.apache.beam.sdk.options.Validation.Required

trait InteractionGraphNegativeOption extends DALOptions with DateRangeOptions {
  @Required
  @Description("Output path for storing the final dataset")
  def getOutputPath: String
  def setOutputPath(value: String): Unit

  @Description("BQ dataset prefix")
  def getBqDataset: String
  def setBqDataset(value: String): Unit

}
