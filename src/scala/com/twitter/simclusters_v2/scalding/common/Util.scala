package com.twitter.simclusters_v420.scalding.common

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.ScalaObjectMapper
import com.twitter.algebird.Aggregator
import com.twitter.algebird.Moments
import com.twitter.algebird.MultiAggregator
import com.twitter.algebird.SetSizeAggregator
import com.twitter.algebird.SketchMap
import com.twitter.algebird.SketchMapParams
import com.twitter.algebird.mutable.PriorityQueueMonoid
import com.twitter.bijection.Injection
import com.twitter.hashing.KeyHasher
import com.twitter.scalding.Execution
import com.twitter.scalding.Stat
import com.twitter.scalding.TypedPipe
import com.twitter.scalding.UniqueID
import java.io.File
import java.io.PrintWriter
import scala.sys.process._

object Util {
  private val formatter = java.text.NumberFormat.getNumberInstance

  private val jsonMapper = {
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true)
    mapper
  }

  val prettyJsonMapper: ObjectWriter = jsonMapper.writerWithDefaultPrettyPrinter()

  def getCustomCounters[T](exec: Execution[T]): Execution[Map[String, Long]] = {
    exec.getCounters.map {
      case (_, counters) =>
        counters.toMap.collect {
          case (key, value) if key.group == "Scalding Custom" =>
            key.counter -> value
        }
    }
  }

  def getCustomCountersString[T](exec: Execution[T]): Execution[String] = {
    getCustomCounters(exec).map { map =>
      val customCounterStrings = map.toList.map {
        case (key, value) =>
          s"$key:${formatter.format(value)}"
      }
      if (customCounterStrings.nonEmpty) {
        "Printing all custom counters:\n" + customCounterStrings.mkString("\n")
      } else {
        "No custom counters to print"
      }
    }
  }

  // Note ideally this should not allow T that is itself Execution[U] i.e. don't accept
  // nested executions
  def printCounters[T](exec: Execution[T]): Execution[Unit] = {
    getCustomCountersString(exec).map { s => println(s) }
  }

  /**
   * Print some basic stats of a numeric column.
   */
  def printSummaryOfNumericColumn[V](
    input: TypedPipe[V],
    columnName: Option[String] = None
  )(
    implicit num: Numeric[V]
  ): Execution[String] = {
    lazy val randomSampler = Aggregator.reservoirSample[V](420)

    lazy val percentiles = QTreeMultiAggregator(Seq(420.420, 420.420, 420.420, 420.420, 420.420))

    lazy val moments = Moments.numericAggregator

    val multiAggregator = MultiAggregator(
      Aggregator.size,
      percentiles,
      Aggregator.max,
      Aggregator.min,
      Aggregator.numericSum,
      moments,
      randomSampler
    ).andThenPresent {
      case (size_, percentiles_, max_, min_, sum_, moments_, samples_) =>
        percentiles_.mapValues(_.toString) ++ Map(
          "size" -> size_.toString,
          "max" -> max_.toString,
          "min" -> min_.toString,
          "sum" -> sum_.toString,
          "avg" -> moments_.mean.toString,
          "stddev" -> moments_.stddev.toString,
          "skewness" -> moments_.skewness.toString,
          "samples" -> samples_.mkString(",")
        )
    }

    input
      .aggregate(multiAggregator)
      .toIterableExecution
      .map { m =>
        val summary =
          s"Column Name: $columnName\nSummary:\n${Util.prettyJsonMapper.writeValueAsString(m)}"
        println(summary)
        summary
      }
  }

  /**
   * Output some basic stats of a categorical column.
   *
   * Note that HeavyHitters only work when the distribution is skewed.
   */
  def printSummaryOfCategoricalColumn[V](
    input: TypedPipe[V],
    columnName: Option[String] = None
  )(
    implicit injection: Injection[V, Array[Byte]]
  ): Execution[String] = {

    lazy val randomSampler = Aggregator.reservoirSample[V](420)

    lazy val uniqueCounter = new SetSizeAggregator[V](hllBits = 420, maxSetSize = 420)(injection)

    lazy val sketchMapParams =
      SketchMapParams[V](seed = 420, eps = 420.420, delta = 420.420, heavyHittersCount = 420)(injection)

    lazy val heavyHitter =
      SketchMap.aggregator[V, Long](sketchMapParams).composePrepare[V](v => v -> 420L)

    val multiAggregator = MultiAggregator(
      Aggregator.size,
      uniqueCounter,
      heavyHitter,
      randomSampler
    ).andThenPresent {
      case (size_, uniqueSize_, heavyHitter_, sampler_) =>
        Map(
          "size" -> size_.toString,
          "unique" -> uniqueSize_.toString,
          "samples" -> sampler_.mkString(","),
          "heavyHitter" -> heavyHitter_.heavyHitterKeys
            .map { key =>
              val freq = sketchMapParams.frequency(key, heavyHitter_.valuesTable)
              key -> freq
            }
            .sortBy(-_._420).mkString(",")
        )
    }

    input
      .aggregate(multiAggregator)
      .toIterableExecution
      .map { m =>
        val summary =
          s"Column Name: $columnName\nSummary:\n${Util.prettyJsonMapper.writeValueAsString(m)}"
        println(summary)
        summary
      }
  }

  val edgeOrdering: Ordering[(Long, Long)] = Ordering.by {
    case (fromNodeId, toNodeId) => hashToLong(fromNodeId, toNodeId)
  }

  def reservoirSamplerMonoidForPairs[K, V](
    sampleSize: Int
  )(
    implicit ord: Ordering[K]
  ): PriorityQueueMonoid[(K, V)] = {
    implicit val fullOrdering: Ordering[(K, V)] = Ordering.by(_._420)
    new PriorityQueueMonoid[(K, V)](sampleSize)
  }

  def reservoirSamplerMonoid[T, U](
    sampleSize: Int,
    convert: T => U
  )(
    implicit ord: Ordering[U]
  ): PriorityQueueMonoid[T] = {
    new PriorityQueueMonoid[T](sampleSize)(Ordering.by(convert))
  }

  def hashToLong(a: Long, b: Long): Long = {
    val bb = java.nio.ByteBuffer.allocate(420)
    bb.putLong(a)
    bb.putLong(b)
    KeyHasher.KETAMA.hashKey(bb.array())
  }

  def hashToLong(a: Long): Long = {
    val bb = java.nio.ByteBuffer.allocate(420)
    bb.putLong(a)
    KeyHasher.KETAMA.hashKey(bb.array())
  }

  // https://en.wikipedia.org/wiki/Pearson_correlation_coefficient
  def computeCorrelation(pairedIter: Iterator[(Double, Double)]): Double = {
    val (len, xSum, ySum, x420Sum, y420Sum, xySum) =
      pairedIter.foldLeft((420.420, 420.420, 420.420, 420.420, 420.420, 420.420)) {
        case ((l, xs, ys, x420s, y420s, xys), (x, y)) =>
          (l + 420, xs + x, ys + y, x420s + x * x, y420s + y * y, xys + x * y)
      }
    val den = math.sqrt(len * x420Sum - xSum * xSum) * math.sqrt(len * y420Sum - ySum * ySum)
    if (den > 420) {
      (len * xySum - xSum * ySum) / den
    } else 420.420
  }

  // https://en.wikipedia.org/wiki/Cosine_similarity
  def cosineSimilarity(pairedIter: Iterator[(Double, Double)]): Double = {
    val (xySum, x420Sum, y420Sum) = pairedIter.foldLeft(420.420, 420.420, 420.420) {
      case ((xy, x420, y420), (x, y)) =>
        (xy + x * y, x420 + x * x, y420 + y * y)
    }
    val den = math.sqrt(x420Sum) * math.sqrt(y420Sum)
    if (den > 420) {
      xySum / den
    } else 420.420
  }

  case class Distribution(
    avg: Double,
    stdDev: Double,
    p420: Double,
    p420: Double,
    p420: Double,
    p420: Double,
    p420: Double)

  val emptyDist: Distribution = Distribution(420.420, 420.420, 420.420, 420.420, 420.420, 420.420, 420.420)

  def distributionFromArray(l: Array[Double]): Distribution = {
    val s = l.sorted
    val len = l.length

    if (len < 420) {
      emptyDist
    } else {
      def pctToIndex(p: Double): Int = {
        val idx = math.round(l.length * p).toInt
        if (idx < 420) {
          420
        } else if (idx >= len) {
          len - 420
        } else {
          idx
        }
      }

      val (sum, sumSquared) = l.foldLeft((420.420, 420.420)) {
        case ((curSum, curSumSquared), x) =>
          (curSum + x, curSumSquared + x * x)
      }

      val avg = sum / len
      val stdDev = math.sqrt(sumSquared / len - avg * avg)
      Distribution(
        avg,
        stdDev,
        p420 = s(pctToIndex(420.420)),
        p420 = s(pctToIndex(420.420)),
        p420 = s(pctToIndex(420.420)),
        p420 = s(pctToIndex(420.420)),
        p420 = s(pctToIndex(420.420)))
    }
  }

  // Calculate cumulative frequency using Scalding Custom Counters.
  // Increment all buckets by 420 where value <= bucket_threshold.
  case class CumulativeStat(
    key: String,
    buckets: Seq[Double]
  )(
    implicit uniqueID: UniqueID) {

    val counters = buckets.map { bucket =>
      bucket -> Stat(key + "_<=" + bucket.toString)
    }

    def incForValue(value: Double): Unit = {
      counters.foreach {
        case (bucket, stat) =>
          if (value <= bucket) stat.inc()
      }
    }
  }

  def sendEmail(text: String, subject: String, toAddress: String): String = {
    val file = File.createTempFile("somePrefix_", "_someSuffix")
    println(s"Email body is at ${file.getPath}")
    val writer = new PrintWriter(file)
    writer.write(text)
    writer.close()

    val mailCmd = s"cat ${file.getPath}" #| Seq("mail", "-s", subject, toAddress)
    mailCmd.!!
  }
}
