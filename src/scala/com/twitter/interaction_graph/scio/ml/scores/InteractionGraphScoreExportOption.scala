package com.twitter.interaction_graph.scio.ml.scores

import com.twitter.beam.io.dal.DALOptions
import com.twitter.beam.job.DateRangeOptions
import org.apache.beam.sdk.options.Default
import org.apache.beam.sdk.options.Description
import org.apache.beam.sdk.options.Validation.Required

trait InteractionGraphScoreExportOption extends DALOptions with DateRangeOptions {
  @Required
  @Description("Output path for storing the final dataset")
  def getOutputPath: String
  def setOutputPath(value: String): Unit

  @Description("Indicates DAL write environment. Can be set to dev/stg during local validation")
  @Default.String("PROD")
  def getDALWriteEnvironment: String
  def setDALWriteEnvironment(value: String): Unit

  @Description("Number of shards/partitions for saving the final dataset.")
  @Default.Integer(1000)
  def getNumberOfShards: Integer
  def setNumberOfShards(value: Integer): Unit
}
