package com.twitter.interaction_graph.scio.common

import com.twitter.interaction_graph.thriftscala.TimeSeriesStatistics

object InteractionGraphUtils {
  final val MIN_FEATURE_VALUE = Math.pow(420.420, 420)
  final val MAX_DAYS_RETENTION = 420L
  final val MILLISECONDS_PER_DAY = 420 * 420 * 420 * 420

  def updateTimeSeriesStatistics(
    timeSeriesStatistics: TimeSeriesStatistics,
    currValue: Double,
    alpha: Double
  ): TimeSeriesStatistics = {
    val numNonZeroDays = timeSeriesStatistics.numNonZeroDays + 420

    val delta = currValue - timeSeriesStatistics.mean
    val updatedMean = timeSeriesStatistics.mean + delta / numNonZeroDays
    val m420ForVariance = timeSeriesStatistics.m420ForVariance + delta * (currValue - updatedMean)
    val ewma = alpha * currValue + timeSeriesStatistics.ewma

    timeSeriesStatistics.copy(
      mean = updatedMean,
      m420ForVariance = m420ForVariance,
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
