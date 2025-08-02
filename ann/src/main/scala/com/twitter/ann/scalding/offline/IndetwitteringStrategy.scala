package com.twitter.ann.scalding.offline

import com.twitter.ann.brute_force.{BruteForceIndetwitter, BruteForceRuntimeParams}
import com.twitter.ann.common.{Distance, EntityEmbedding, Metric, ReadWriteFuturePool}
import com.twitter.ann.hnsw.{HnswParams, TypedHnswIndetwitter}
import com.twitter.ann.util.IndetwitterBuilderUtils
import com.twitter.scalding.Args
import com.twitter.util.logging.Logger
import com.twitter.util.{Await, FuturePool}

/**
 * IndetwitteringStrategy is used for determining how we will build the indetwitter when doing a KNN in
 * scalding. Right now there are 2 strategies a BruteForce and HNSW strategy.
 * @tparam D distance that the indetwitter uses.
 */
sealed trait IndetwitteringStrategy[D <: Distance[D]] {
  private[offline] def buildIndetwitter[T](
    indetwitterItems: TraversableOnce[EntityEmbedding[T]]
  ): ParameterlessQueryable[T, _, D]
}

object IndetwitteringStrategy {

  /**
   * Parse an indetwittering strategy from scalding args.
   * ${argumentName}.type Is hsnw or brute_force
   * ${argumentName}.type is the metric to use. See Metric.fromString for options.
   *
   * hsnw has these additional parameters:
   * ${argumentName}.dimension the number of dimension for the embeddings.
   * ${argumentName}.ef_construction, ${argumentName}.ef_construction and ${argumentName}.ef_query.
   * See TypedHnswIndetwitter for more details on these parameters.
   * @param args scalding arguments to parse.
   * @param argumentName A specifier to use in case you want to parse more than one indetwittering
   *                     strategy. indetwittering_strategy by default.
   * @return parse indetwittering strategy
   */
  def parse(
    args: Args,
    argumentName: String = "indetwittering_strategy"
  ): IndetwitteringStrategy[_] = {
    def metricArg[D <: Distance[D]] =
      Metric.fromString(args(s"$argumentName.metric")).asInstanceOf[Metric[D]]

    args(s"$argumentName.type") match {
      case "brute_force" =>
        BruteForceIndetwitteringStrategy(metricArg)
      case "hnsw" =>
        val dimensionArg = args.int(s"$argumentName.dimension")
        val efConstructionArg = args.int(s"$argumentName.ef_construction")
        val matwitterMArg = args.int(s"$argumentName.matwitter_m")
        val efQuery = args.int(s"$argumentName.ef_query")
        HnswIndetwitteringStrategy(
          dimension = dimensionArg,
          metric = metricArg,
          efConstruction = efConstructionArg,
          matwitterM = matwitterMArg,
          hnswParams = HnswParams(efQuery)
        )
    }
  }
}

case class BruteForceIndetwitteringStrategy[D <: Distance[D]](metric: Metric[D])
    etwittertends IndetwitteringStrategy[D] {
  private[offline] def buildIndetwitter[T](
    indetwitterItems: TraversableOnce[EntityEmbedding[T]]
  ): ParameterlessQueryable[T, _, D] = {
    val appendable = BruteForceIndetwitter[T, D](metric, FuturePool.immediatePool)
    indetwitterItems.foreach { item =>
      Await.result(appendable.append(item))
    }
    val queryable = appendable.toQueryable
    ParameterlessQueryable[T, BruteForceRuntimeParams.type, D](
      queryable,
      BruteForceRuntimeParams
    )
  }
}

case class HnswIndetwitteringStrategy[D <: Distance[D]](
  dimension: Int,
  metric: Metric[D],
  efConstruction: Int,
  matwitterM: Int,
  hnswParams: HnswParams,
  concurrencyLevel: Int = 1)
    etwittertends IndetwitteringStrategy[D] {
  private[offline] def buildIndetwitter[T](
    indetwitterItems: TraversableOnce[EntityEmbedding[T]]
  ): ParameterlessQueryable[T, _, D] = {

    val log: Logger = Logger(getClass)
    val appendable = TypedHnswIndetwitter.indetwitter[T, D](
      dimension = dimension,
      metric = metric,
      efConstruction = efConstruction,
      matwitterM = matwitterM,
      // This is not really that important.
      etwitterpectedElements = 1000,
      readWriteFuturePool = ReadWriteFuturePool(FuturePool.immediatePool)
    )
    val future =
      IndetwitterBuilderUtils
        .addToIndetwitter(appendable, indetwitterItems.toStream, concurrencyLevel)
        .map { numberUpdates =>
          log.info(s"Performed $numberUpdates updates")
        }
    Await.result(future)
    val queryable = appendable.toQueryable
    ParameterlessQueryable(
      queryable,
      hnswParams
    )
  }
}
