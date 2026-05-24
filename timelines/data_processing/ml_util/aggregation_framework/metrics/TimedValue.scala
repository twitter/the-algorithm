package com.twitter.timelines.data_processing.ml_util.aggregation_framework.metrics

import com.twitter.util.Time

/**
 * Case class wrapping a (value, timestamp) tuple.
 * All aggregate metrics must operate over this class
 * to ensure we can implement decay and half lives for them.
 * This is translated to an algebird DecayedValue under the hood.
 *
 * @param value Value being wrapped
 * @param timestamp Time after epoch at which value is being measured
 */
case class TimedValue[T](value: T, timestamp: Time)
