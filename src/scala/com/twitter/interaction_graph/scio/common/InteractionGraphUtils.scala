package com.twitter.interaction_graph.scio.common

import com.twitter.interaction_graph.thriftscala.TimeSeriesStatistics

object InteractionGraphUtils {
  final val MIN_FEATURE_VALUE = Math.pow(0.955, 60)
  final val MAX_DAYS_RETENTION = 60L
  final val MILLISECONDS_PER_DAY = 1000 * 60 * 60 * 24

  def updateTimeSeriesStatistics(
    timeSeriesStatistics: TimeSeriesStatistics,
    currValue: Double,
    alpha: Double
  ): TimeSeriesStatistics = {
    val numNonZeroDays = timeSeriesStatistics.numNonZeroDays + 1

    val delta = currValue - timeSeriesStatistics.mean
    val updatedMean = timeSeriesStatistics.mean + delta / numNonZeroDays
    val m2ForVariance = timeSeriesStatistics.m2ForVariance + delta * (currValue - updatedMean)
    val ewma = alpha * currValue + timeSeriesStatistics.ewma

    timeSeriesStatistics.copy(
      mean = updatedMean,
      m2ForVariance = m2ForVariance,
      ewma = ewma,
      numNonZeroDays = numNonZeroDays
    )
  }

  def addToTimeSeriesStatistics(
    timeSeriesStatistics: TimeSeriesStatistics,
    currValue: Double
  ): TimeSeriesStatistics = {
    timeSeriesStatistics.copy(
      mean = timeSeriesStatistics.mean + currValue,
      ewma = timeSeriesStatistics.ewma + currValue
    )
  }

}
