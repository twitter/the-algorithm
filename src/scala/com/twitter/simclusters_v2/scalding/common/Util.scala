package com.tw ter.s mclusters_v2.scald ng.common

 mport com.fasterxml.jackson.core.JsonGenerator
 mport com.fasterxml.jackson.datab nd.ObjectMapper
 mport com.fasterxml.jackson.datab nd.ObjectWr er
 mport com.fasterxml.jackson.module.scala.DefaultScalaModule
 mport com.fasterxml.jackson.module.scala.ScalaObjectMapper
 mport com.tw ter.algeb rd.Aggregator
 mport com.tw ter.algeb rd.Mo nts
 mport com.tw ter.algeb rd.Mult Aggregator
 mport com.tw ter.algeb rd.SetS zeAggregator
 mport com.tw ter.algeb rd.SketchMap
 mport com.tw ter.algeb rd.SketchMapParams
 mport com.tw ter.algeb rd.mutable.Pr or yQueueMono d
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.hash ng.KeyHas r
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.Stat
 mport com.tw ter.scald ng.TypedP pe
 mport com.tw ter.scald ng.Un que D
 mport java. o.F le
 mport java. o.Pr ntWr er
 mport scala.sys.process._

object Ut l {
  pr vate val formatter = java.text.NumberFormat.getNumber nstance

  pr vate val jsonMapper = {
    val mapper = new ObjectMapper() w h ScalaObjectMapper
    mapper.reg sterModule(DefaultScalaModule)
    mapper.conf gure(JsonGenerator.Feature.WR TE_NUMBERS_AS_STR NGS, true)
    mapper
  }

  val prettyJsonMapper: ObjectWr er = jsonMapper.wr erW hDefaultPrettyPr nter()

  def getCustomCounters[T](exec: Execut on[T]): Execut on[Map[Str ng, Long]] = {
    exec.getCounters.map {
      case (_, counters) =>
        counters.toMap.collect {
          case (key, value)  f key.group == "Scald ng Custom" =>
            key.counter -> value
        }
    }
  }

  def getCustomCountersStr ng[T](exec: Execut on[T]): Execut on[Str ng] = {
    getCustomCounters(exec).map { map =>
      val customCounterStr ngs = map.toL st.map {
        case (key, value) =>
          s"$key:${formatter.format(value)}"
      }
       f (customCounterStr ngs.nonEmpty) {
        "Pr nt ng all custom counters:\n" + customCounterStr ngs.mkStr ng("\n")
      } else {
        "No custom counters to pr nt"
      }
    }
  }

  // Note  deally t  should not allow T that  s  self Execut on[U]  .e. don't accept
  // nested execut ons
  def pr ntCounters[T](exec: Execut on[T]): Execut on[Un ] = {
    getCustomCountersStr ng(exec).map { s => pr ntln(s) }
  }

  /**
   * Pr nt so  bas c stats of a nu r c column.
   */
  def pr ntSummaryOfNu r cColumn[V](
     nput: TypedP pe[V],
    columnNa : Opt on[Str ng] = None
  )(
     mpl c  num: Nu r c[V]
  ): Execut on[Str ng] = {
    lazy val randomSampler = Aggregator.reservo rSample[V](100)

    lazy val percent les = QTreeMult Aggregator(Seq(0.05, 0.25, 0.50, 0.75, 0.95))

    lazy val mo nts = Mo nts.nu r cAggregator

    val mult Aggregator = Mult Aggregator(
      Aggregator.s ze,
      percent les,
      Aggregator.max,
      Aggregator.m n,
      Aggregator.nu r cSum,
      mo nts,
      randomSampler
    ).andT nPresent {
      case (s ze_, percent les_, max_, m n_, sum_, mo nts_, samples_) =>
        percent les_.mapValues(_.toStr ng) ++ Map(
          "s ze" -> s ze_.toStr ng,
          "max" -> max_.toStr ng,
          "m n" -> m n_.toStr ng,
          "sum" -> sum_.toStr ng,
          "avg" -> mo nts_. an.toStr ng,
          "stddev" -> mo nts_.stddev.toStr ng,
          "skewness" -> mo nts_.skewness.toStr ng,
          "samples" -> samples_.mkStr ng(",")
        )
    }

     nput
      .aggregate(mult Aggregator)
      .to erableExecut on
      .map { m =>
        val summary =
          s"Column Na : $columnNa \nSummary:\n${Ut l.prettyJsonMapper.wr eValueAsStr ng(m)}"
        pr ntln(summary)
        summary
      }
  }

  /**
   * Output so  bas c stats of a categor cal column.
   *
   * Note that  avyH ters only work w n t  d str but on  s ske d.
   */
  def pr ntSummaryOfCategor calColumn[V](
     nput: TypedP pe[V],
    columnNa : Opt on[Str ng] = None
  )(
     mpl c   nject on:  nject on[V, Array[Byte]]
  ): Execut on[Str ng] = {

    lazy val randomSampler = Aggregator.reservo rSample[V](100)

    lazy val un queCounter = new SetS zeAggregator[V](hllB s = 13, maxSetS ze = 1000)( nject on)

    lazy val sketchMapParams =
      SketchMapParams[V](seed = 1618, eps = 0.001, delta = 0.05,  avyH tersCount = 20)( nject on)

    lazy val  avyH ter =
      SketchMap.aggregator[V, Long](sketchMapParams).composePrepare[V](v => v -> 1L)

    val mult Aggregator = Mult Aggregator(
      Aggregator.s ze,
      un queCounter,
       avyH ter,
      randomSampler
    ).andT nPresent {
      case (s ze_, un queS ze_,  avyH ter_, sampler_) =>
        Map(
          "s ze" -> s ze_.toStr ng,
          "un que" -> un queS ze_.toStr ng,
          "samples" -> sampler_.mkStr ng(","),
          " avyH ter" ->  avyH ter_. avyH terKeys
            .map { key =>
              val freq = sketchMapParams.frequency(key,  avyH ter_.valuesTable)
              key -> freq
            }
            .sortBy(-_._2).mkStr ng(",")
        )
    }

     nput
      .aggregate(mult Aggregator)
      .to erableExecut on
      .map { m =>
        val summary =
          s"Column Na : $columnNa \nSummary:\n${Ut l.prettyJsonMapper.wr eValueAsStr ng(m)}"
        pr ntln(summary)
        summary
      }
  }

  val edgeOrder ng: Order ng[(Long, Long)] = Order ng.by {
    case (fromNode d, toNode d) => hashToLong(fromNode d, toNode d)
  }

  def reservo rSamplerMono dForPa rs[K, V](
    sampleS ze:  nt
  )(
     mpl c  ord: Order ng[K]
  ): Pr or yQueueMono d[(K, V)] = {
     mpl c  val fullOrder ng: Order ng[(K, V)] = Order ng.by(_._1)
    new Pr or yQueueMono d[(K, V)](sampleS ze)
  }

  def reservo rSamplerMono d[T, U](
    sampleS ze:  nt,
    convert: T => U
  )(
     mpl c  ord: Order ng[U]
  ): Pr or yQueueMono d[T] = {
    new Pr or yQueueMono d[T](sampleS ze)(Order ng.by(convert))
  }

  def hashToLong(a: Long, b: Long): Long = {
    val bb = java.n o.ByteBuffer.allocate(16)
    bb.putLong(a)
    bb.putLong(b)
    KeyHas r.KETAMA.hashKey(bb.array())
  }

  def hashToLong(a: Long): Long = {
    val bb = java.n o.ByteBuffer.allocate(8)
    bb.putLong(a)
    KeyHas r.KETAMA.hashKey(bb.array())
  }

  // https://en.w k ped a.org/w k /Pearson_correlat on_coeff c ent
  def computeCorrelat on(pa red er:  erator[(Double, Double)]): Double = {
    val (len, xSum, ySum, x2Sum, y2Sum, xySum) =
      pa red er.foldLeft((0.0, 0.0, 0.0, 0.0, 0.0, 0.0)) {
        case ((l, xs, ys, x2s, y2s, xys), (x, y)) =>
          (l + 1, xs + x, ys + y, x2s + x * x, y2s + y * y, xys + x * y)
      }
    val den = math.sqrt(len * x2Sum - xSum * xSum) * math.sqrt(len * y2Sum - ySum * ySum)
     f (den > 0) {
      (len * xySum - xSum * ySum) / den
    } else 0.0
  }

  // https://en.w k ped a.org/w k /Cos ne_s m lar y
  def cos neS m lar y(pa red er:  erator[(Double, Double)]): Double = {
    val (xySum, x2Sum, y2Sum) = pa red er.foldLeft(0.0, 0.0, 0.0) {
      case ((xy, x2, y2), (x, y)) =>
        (xy + x * y, x2 + x * x, y2 + y * y)
    }
    val den = math.sqrt(x2Sum) * math.sqrt(y2Sum)
     f (den > 0) {
      xySum / den
    } else 0.0
  }

  case class D str but on(
    avg: Double,
    stdDev: Double,
    p1: Double,
    p10: Double,
    p50: Double,
    p90: Double,
    p99: Double)

  val emptyD st: D str but on = D str but on(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

  def d str but onFromArray(l: Array[Double]): D str but on = {
    val s = l.sorted
    val len = l.length

     f (len < 1) {
      emptyD st
    } else {
      def pctTo ndex(p: Double):  nt = {
        val  dx = math.round(l.length * p).to nt
         f ( dx < 0) {
          0
        } else  f ( dx >= len) {
          len - 1
        } else {
           dx
        }
      }

      val (sum, sumSquared) = l.foldLeft((0.0, 0.0)) {
        case ((curSum, curSumSquared), x) =>
          (curSum + x, curSumSquared + x * x)
      }

      val avg = sum / len
      val stdDev = math.sqrt(sumSquared / len - avg * avg)
      D str but on(
        avg,
        stdDev,
        p1 = s(pctTo ndex(0.01)),
        p10 = s(pctTo ndex(0.1)),
        p50 = s(pctTo ndex(0.5)),
        p90 = s(pctTo ndex(0.9)),
        p99 = s(pctTo ndex(0.99)))
    }
  }

  // Calculate cumulat ve frequency us ng Scald ng Custom Counters.
  //  ncre nt all buckets by 1 w re value <= bucket_threshold.
  case class Cumulat veStat(
    key: Str ng,
    buckets: Seq[Double]
  )(
     mpl c  un que D: Un que D) {

    val counters = buckets.map { bucket =>
      bucket -> Stat(key + "_<=" + bucket.toStr ng)
    }

    def  ncForValue(value: Double): Un  = {
      counters.foreach {
        case (bucket, stat) =>
           f (value <= bucket) stat. nc()
      }
    }
  }

  def sendEma l(text: Str ng, subject: Str ng, toAddress: Str ng): Str ng = {
    val f le = F le.createTempF le("so Pref x_", "_so Suff x")
    pr ntln(s"Ema l body  s at ${f le.getPath}")
    val wr er = new Pr ntWr er(f le)
    wr er.wr e(text)
    wr er.close()

    val ma lCmd = s"cat ${f le.getPath}" #| Seq("ma l", "-s", subject, toAddress)
    ma lCmd.!!
  }
}
