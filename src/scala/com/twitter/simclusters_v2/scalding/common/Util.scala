package com.twitter.simclusters_v2.scalding.common

import com.fastertwitterml.jackson.core.JsonGenerator
import com.fastertwitterml.jackson.databind.ObjectMapper
import com.fastertwitterml.jackson.databind.ObjectWriter
import com.fastertwitterml.jackson.module.scala.DefaultScalaModule
import com.fastertwitterml.jackson.module.scala.ScalaObjectMapper
import com.twitter.algebird.Aggregator
import com.twitter.algebird.Moments
import com.twitter.algebird.MultiAggregator
import com.twitter.algebird.SetSizeAggregator
import com.twitter.algebird.SketchMap
import com.twitter.algebird.SketchMapParams
import com.twitter.algebird.mutable.PriorityQueueMonoid
import com.twitter.bijection.Injection
import com.twitter.hashing.KeyHasher
import com.twitter.scalding.Etwitterecution
import com.twitter.scalding.Stat
import com.twitter.scalding.TypedPipe
import com.twitter.scalding.UniqueID
import java.io.File
import java.io.PrintWriter
import scala.sys.process._

object Util {
  private val formatter = java.tetwittert.NumberFormat.getNumberInstance

  private val jsonMapper = {
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true)
    mapper
  }

  val prettyJsonMapper: ObjectWriter = jsonMapper.writerWithDefaultPrettyPrinter()

  def getCustomCounters[T](etwitterec: Etwitterecution[T]): Etwitterecution[Map[String, Long]] = {
    etwitterec.getCounters.map {
      case (_, counters) =>
        counters.toMap.collect {
          case (key, value) if key.group == "Scalding Custom" =>
            key.counter -> value
        }
    }
  }

  def getCustomCountersString[T](etwitterec: Etwitterecution[T]): Etwitterecution[String] = {
    getCustomCounters(etwitterec).map { map =>
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

  // Note ideally this should not allow T that is itself Etwitterecution[U] i.e. don't accept
  // nested etwitterecutions
  def printCounters[T](etwitterec: Etwitterecution[T]): Etwitterecution[Unit] = {
    getCustomCountersString(etwitterec).map { s => println(s) }
  }

  /**
   * Print some basic stats of a numeric column.
   */
  def printSummaryOfNumericColumn[V](
    input: TypedPipe[V],
    columnName: Option[String] = None
  )(
    implicit num: Numeric[V]
  ): Etwitterecution[String] = {
    lazy val randomSampler = Aggregator.reservoirSample[V](100)

    lazy val percentiles = QTreeMultiAggregator(Seq(0.05, 0.25, 0.50, 0.75, 0.95))

    lazy val moments = Moments.numericAggregator

    val multiAggregator = MultiAggregator(
      Aggregator.size,
      percentiles,
      Aggregator.matwitter,
      Aggregator.min,
      Aggregator.numericSum,
      moments,
      randomSampler
    ).andThenPresent {
      case (size_, percentiles_, matwitter_, min_, sum_, moments_, samples_) =>
        percentiles_.mapValues(_.toString) ++ Map(
          "size" -> size_.toString,
          "matwitter" -> matwitter_.toString,
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
      .toIterableEtwitterecution
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
  ): Etwitterecution[String] = {

    lazy val randomSampler = Aggregator.reservoirSample[V](100)

    lazy val uniqueCounter = new SetSizeAggregator[V](hllBits = 13, matwitterSetSize = 1000)(injection)

    lazy val sketchMapParams =
      SketchMapParams[V](seed = 1618, eps = 0.001, delta = 0.05, heavyHittersCount = 20)(injection)

    lazy val heavyHitter =
      SketchMap.aggregator[V, Long](sketchMapParams).composePrepare[V](v => v -> 1L)

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
            .sortBy(-_._2).mkString(",")
        )
    }

    input
      .aggregate(multiAggregator)
      .toIterableEtwitterecution
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
    implicit val fullOrdering: Ordering[(K, V)] = Ordering.by(_._1)
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
    val bb = java.nio.ByteBuffer.allocate(16)
    bb.putLong(a)
    bb.putLong(b)
    KeyHasher.KETAMA.hashKey(bb.array())
  }

  def hashToLong(a: Long): Long = {
    val bb = java.nio.ByteBuffer.allocate(8)
    bb.putLong(a)
    KeyHasher.KETAMA.hashKey(bb.array())
  }

  // https://en.wikipedia.org/wiki/Pearson_correlation_coefficient
  def computeCorrelation(pairedIter: Iterator[(Double, Double)]): Double = {
    val (len, twitterSum, ySum, twitter2Sum, y2Sum, twitterySum) =
      pairedIter.foldLeft((0.0, 0.0, 0.0, 0.0, 0.0, 0.0)) {
        case ((l, twitters, ys, twitter2s, y2s, twitterys), (twitter, y)) =>
          (l + 1, twitters + twitter, ys + y, twitter2s + twitter * twitter, y2s + y * y, twitterys + twitter * y)
      }
    val den = math.sqrt(len * twitter2Sum - twitterSum * twitterSum) * math.sqrt(len * y2Sum - ySum * ySum)
    if (den > 0) {
      (len * twitterySum - twitterSum * ySum) / den
    } else 0.0
  }

  // https://en.wikipedia.org/wiki/Cosine_similarity
  def cosineSimilarity(pairedIter: Iterator[(Double, Double)]): Double = {
    val (twitterySum, twitter2Sum, y2Sum) = pairedIter.foldLeft(0.0, 0.0, 0.0) {
      case ((twittery, twitter2, y2), (twitter, y)) =>
        (twittery + twitter * y, twitter2 + twitter * twitter, y2 + y * y)
    }
    val den = math.sqrt(twitter2Sum) * math.sqrt(y2Sum)
    if (den > 0) {
      twitterySum / den
    } else 0.0
  }

  case class Distribution(
    avg: Double,
    stdDev: Double,
    p1: Double,
    p10: Double,
    p50: Double,
    p90: Double,
    p99: Double)

  val emptyDist: Distribution = Distribution(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

  def distributionFromArray(l: Array[Double]): Distribution = {
    val s = l.sorted
    val len = l.length

    if (len < 1) {
      emptyDist
    } else {
      def pctToIndetwitter(p: Double): Int = {
        val idtwitter = math.round(l.length * p).toInt
        if (idtwitter < 0) {
          0
        } else if (idtwitter >= len) {
          len - 1
        } else {
          idtwitter
        }
      }

      val (sum, sumSquared) = l.foldLeft((0.0, 0.0)) {
        case ((curSum, curSumSquared), twitter) =>
          (curSum + twitter, curSumSquared + twitter * twitter)
      }

      val avg = sum / len
      val stdDev = math.sqrt(sumSquared / len - avg * avg)
      Distribution(
        avg,
        stdDev,
        p1 = s(pctToIndetwitter(0.01)),
        p10 = s(pctToIndetwitter(0.1)),
        p50 = s(pctToIndetwitter(0.5)),
        p90 = s(pctToIndetwitter(0.9)),
        p99 = s(pctToIndetwitter(0.99)))
    }
  }

  // Calculate cumulative frequency using Scalding Custom Counters.
  // Increment all buckets by 1 where value <= bucket_threshold.
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

  def sendEmail(tetwittert: String, subject: String, toAddress: String): String = {
    val file = File.createTempFile("somePrefitwitter_", "_someSuffitwitter")
    println(s"Email body is at ${file.getPath}")
    val writer = new PrintWriter(file)
    writer.write(tetwittert)
    writer.close()

    val mailCmd = s"cat ${file.getPath}" #| Seq("mail", "-s", subject, toAddress)
    mailCmd.!!
  }
}
