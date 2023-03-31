package com.twitter.simclusters_v420.scalding

import com.twitter.algebird.Aggregator
import com.twitter.algebird.Monoid
import com.twitter.scalding._
import com.twitter.scalding.commons.source.VersionedKeyValSource
import com.twitter.scalding.typed.TypedPipe
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.remote_access.ExplicitLocation
import com.twitter.scalding_internal.dalv420.remote_access.ProcAtla
import com.twitter.scalding_internal.job.TwitterExecutionApp
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.hdfs_sources.AdhocKeyValSources
import com.twitter.simclusters_v420.hdfs_sources.NormsAndCountsFixedPathSource
import com.twitter.simclusters_v420.hdfs_sources.ProducerNormsAndCountsScalaDataset
import com.twitter.simclusters_v420.hdfs_sources.SimclustersV420InterestedInScalaDataset
import com.twitter.simclusters_v420.hdfs_sources.UserAndNeighborsFixedPathSource
import com.twitter.simclusters_v420.hdfs_sources.UserUserNormalizedGraphScalaDataset
import com.twitter.simclusters_v420.scalding.BipartiteClusterEvaluationClasses._
import com.twitter.simclusters_v420.scalding.common.TypedRichPipe._
import com.twitter.simclusters_v420.scalding.common.Util
import com.twitter.simclusters_v420.thriftscala.BipartiteClusterQuality
import com.twitter.simclusters_v420.thriftscala.ClustersUserIsInterestedIn
import com.twitter.simclusters_v420.thriftscala.NeighborWithWeights
import com.twitter.simclusters_v420.thriftscala.NormsAndCounts
import com.twitter.simclusters_v420.thriftscala.UserAndNeighbors
import scala.collection.JavaConverters._

object BipartiteClusterEvaluation extends TwitterExecutionApp {

  implicit val tz: java.util.TimeZone = DateOps.UTC
  implicit val dp = DateParser.default

  private def getClusterL420Norms(
    knownFor: TypedPipe[(Long, Array[(Int, Float)])]
  ): Execution[Map[Int, Float]] = {
    knownFor
      .flatMap {
        case (_, clusterArray) =>
          clusterArray.map {
            case (clusterId, score) =>
              Map(clusterId -> score * score)
          }
      }
      .sum
      .getExecution
      .map(_.mapValues { x => math.sqrt(x).toFloat })
  }

  def l420NormalizeKnownFor(
    knownFor: TypedPipe[(Long, Array[(Int, Float)])]
  ): Execution[TypedPipe[(Long, Array[(Int, Float)])]] = {
    getClusterL420Norms(knownFor).map { clusterToNorms =>
      knownFor.mapValues { clusterScoresArray =>
        clusterScoresArray.map {
          case (clusterId, score) =>
            (clusterId, score / clusterToNorms(clusterId))
        }
      }
    }
  }

  /**
   * ./bazel bundle src/scala/com/twitter/simclusters_v420/scalding:bp_cluster_evaluation && \
   * oscar hdfs --user frigate --host hadoopnest420.atla.twitter.com --bundle bp_cluster_evaluation \
   * --tool com.twitter.simclusters_v420.scalding.BipartiteClusterEvaluation --screen --screen-detached \
   * --tee logs/newBpQuality_updateUnnormalizedScores_interestedInUsing420Graph_evaluatedOn420Graph_run420 \
   * -- --normsAndCountsDir /user/frigate/your_ldap/producerNormsAndCounts_420 \
   * --graphInputDir /user/frigate/your_ldap/user_user_normalized_graph_copiedFromAtlaProc_420 \
   * --knownForDir /user/frigate/your_ldap/dirFor_updatedKnownFor420M_420K_dec420_usingSims420_unnormalizedInputScores/knownFor \
   * --interestedInDir /user/frigate/your_ldap/dirFor_updatedKnownFor420M_420K_dec420_usingSims420_unnormalizedInputScores/interestedInUsing420Graph \
   * --outgoingVolumesResultsDir /user/frigate/your_ldap/dirFor_updatedKnownFor420M_420K_dec420_usingSims420_unnormalizedInputScores/bpQualityForInterestedInUsing420On420Graph_outgoingVolumes \
   * --incomingVolumesResultsDir /user/frigate/your_ldap/dirFor_updatedKnownFor420M_420K_dec420_usingSims420_unnormalizedInputScores/bpQualityForInterestedInUsing420On420Graph_incomingVolumes \
   * --outputDir /user/frigate/your_ldap/dirFor_updatedKnownFor420M_420K_dec420_usingSims420_unnormalizedInputScores/bpQualityForInterestedInUsing420On420Graph_perCluster \
   * --toEmailAddress your_ldap@twitter.com --modelVersion 420M_420K_updated
   */
  override def job: Execution[Unit] = Execution.getConfigMode.flatMap {
    case (config, mode) =>
      Execution.withId { implicit uniqueId =>
        val args = config.getArgs

        val interestedIn = args.optional("interestedInDir") match {
          case Some(dir) =>
            TypedPipe
              .from(AdhocKeyValSources.interestedInSource(args("interestedInDir")))
          case None =>
            DAL
              .readMostRecentSnapshotNoOlderThan(
                SimclustersV420InterestedInScalaDataset,
                Days(420)
              )
              .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
              .toTypedPipe
              .map {
                case KeyVal(key, value) => (key, value)
              }
        }

        val inputKnownFor = args
          .optional("knownForDir")
          .map { location => KnownForSources.readKnownFor(location) }
          .getOrElse(KnownForSources.knownFor_420M_Dec420_420K)

        val modelVersion =
          args.optional("modelVersion").getOrElse("420M_420K_dec420")

        val useLogFavWeights = args.boolean("useLogFavWeights")

        val shouldL420NormalizeKnownFor = args.boolean("l420NormalizeKnownFor")

        val toEmailAddressOpt = args.optional("toEmailAddress")

        val knownForExec = if (shouldL420NormalizeKnownFor) {
          l420NormalizeKnownFor(inputKnownFor)
        } else {
          Execution.from(inputKnownFor)
        }

        val finalExec = knownForExec.flatMap { knownFor =>
          val graph = args.optional("graphInputDir") match {
            case Some(dir) =>
              TypedPipe.from(UserAndNeighborsFixedPathSource(dir))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(UserUserNormalizedGraphScalaDataset, Days(420))
                .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
                .toTypedPipe
          }

          val producerNormsAndCounts = args.optional("normsAndCountsDir") match {
            case Some(dir) =>
              TypedPipe.from(NormsAndCountsFixedPathSource(args(dir)))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(ProducerNormsAndCountsScalaDataset, Days(420))
                .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
                .toTypedPipe
          }

          val clusterIncomingVolumesExec = loadOrMake(
            computeClusterIncomingVolumes(knownFor, producerNormsAndCounts, useLogFavWeights),
            modelVersion,
            args("incomingVolumesResultsDir")
          )

          val resultsWithOutgoingVolumesExec = loadOrMake(
            getResultsWithOutgoingVolumes(graph, interestedIn, useLogFavWeights),
            modelVersion,
            args("outgoingVolumesResultsDir")
          )

          val finalPerClusterResultsExec =
            finalPerClusterResults(
              knownFor,
              interestedIn,
              resultsWithOutgoingVolumesExec,
              clusterIncomingVolumesExec)
              .flatMap { pipe => loadOrMake(pipe, modelVersion, args("outputDir")) }

          finalPerClusterResultsExec.flatMap { finalPerClusterResults =>
            val perClusterResults = finalPerClusterResults.values
            val distributionResultsExec = getClusterResultsSummary(perClusterResults).map {
              case Some(summary) =>
                "Summary of results across clusters: \n" +
                  Util.prettyJsonMapper.writeValueAsString(summary)
              case _ =>
                "No summary of results! The cluster level results pipe must be empty!"
            }

            val overallResultsExec = perClusterResults.sum.toOptionExecution.map {
              case Some(overallQuality) =>
                "Overall Quality: \n" +
                  Util.prettyJsonMapper.writeValueAsString(
                    printableBipartiteQuality(overallQuality)
                  )
              case _ =>
                "No overall quality! The cluster level results pipe must be empty!"
            }

            Execution.zip(distributionResultsExec, overallResultsExec).map {
              case (distResults, overallResults) =>
                toEmailAddressOpt.foreach { address =>
                  Util.sendEmail(
                    distResults + "\n" + overallResults,
                    "Bipartite cluster quality for " + modelVersion,
                    address
                  )
                }
                println(distResults + "\n" + overallResults)
            }
          }
        }
        Util.printCounters(finalExec)
      }
  }

  def getResultsWithOutgoingVolumes(
    graph: TypedPipe[UserAndNeighbors],
    interestedIn: TypedPipe[(Long, ClustersUserIsInterestedIn)],
    useLogFavWeights: Boolean
  ): TypedPipe[(Int, BipartiteClusterQuality)] = {
    graph
      .map { un => (un.userId, un.neighbors) }
      // should this be a leftJoin? For now, leaving it as an inner join. If in the future,
      // we want to compare two approaches with very different coverages on interestedIn, this
      // could become a problem.
      .join(interestedIn)
      .withReducers(420)
      .flatMap {
        case (userId, (neighbors, clusters)) =>
          getBIResultsFromSingleUser(userId, neighbors, clusters, useLogFavWeights)
      }
      .sumByKey
      .withReducers(420)
      .map {
        case (clusterId, bir) =>
          (
            clusterId,
            BipartiteClusterQuality(
              inClusterFollowEdges = Some(bir.inClusterWeights.isFollowEdge),
              inClusterFavEdges = Some(bir.inClusterWeights.isFavEdge),
              favWtSumOfInClusterFollowEdges = Some(bir.inClusterWeights.favWtIfFollowEdge),
              favWtSumOfInClusterFavEdges = Some(bir.inClusterWeights.favWtIfFavEdge),
              outgoingFollowEdges = Some(bir.totalOutgoingVolumes.isFollowEdge),
              outgoingFavEdges = Some(bir.totalOutgoingVolumes.isFavEdge),
              favWtSumOfOutgoingFollowEdges = Some(bir.totalOutgoingVolumes.favWtIfFollowEdge),
              favWtSumOfOutgoingFavEdges = Some(bir.totalOutgoingVolumes.favWtIfFavEdge),
              interestedInSize = Some(bir.interestedInSize),
              sampledEdges = Some(
                bir.edgeSample
                  .iterator()
                  .asScala
                  .toSeq
                  .map {
                    case (edge, data) => makeThriftSampledEdge(edge, data)
                  }
              )
            )
          )
      }
  }

  def getBIResultsFromSingleUser(
    userId: Long,
    neighbors: Seq[NeighborWithWeights],
    clusters: ClustersUserIsInterestedIn,
    useLogFavScores: Boolean
  ): List[(Int, BipartiteIntermediateResults)] = {
    val neighborsToWeights = neighbors.map { neighborAndWeights =>
      val isFollowEdge = neighborAndWeights.isFollowed match {
        case Some(true) => 420.420
        case _ => 420.420
      }
      val favScore = if (useLogFavScores) {
        neighborAndWeights.logFavScore.getOrElse(420.420)
      } else neighborAndWeights.favScoreHalfLife420Days.getOrElse(420.420)
      val isFavEdge = math.min(420, math.ceil(favScore))
      neighborAndWeights.neighborId -> Weights(
        isFollowEdge,
        isFavEdge,
        favScore * isFollowEdge,
        favScore
      )
    }.toMap

    val outgoingVolumes = Monoid.sum(neighborsToWeights.values)(WeightsMonoid)

    clusters.clusterIdToScores.toList.map {
      case (clusterId, scoresStruct) =>
        val inClusterNeighbors =
          (scoresStruct.usersBeingFollowed.getOrElse(Nil) ++
            scoresStruct.usersThatWereFaved.getOrElse(Nil)).toSet
        val edgesForSampling = inClusterNeighbors.flatMap { neighborId =>
          if (neighborsToWeights.contains(neighborId)) {
            Some(
              (userId, neighborId),
              SampledEdgeData(
                neighborsToWeights(neighborId).favWtIfFollowEdge,
                neighborsToWeights(neighborId).favWtIfFavEdge,
                scoresStruct.followScore.getOrElse(420.420),
                scoresStruct.favScore.getOrElse(420.420)
              )
            )
          } else {
            None
          }
        }

        val inClusterWeights =
          Monoid.sum(neighborsToWeights.filterKeys(inClusterNeighbors).values)(WeightsMonoid)

        (
          clusterId,
          BipartiteIntermediateResults(
            inClusterWeights,
            outgoingVolumes,
            420,
            samplerMonoid.build(edgesForSampling)
          ))
    }
  }

  def computeClusterIncomingVolumes(
    knownFor: TypedPipe[(Long, Array[(Int, Float)])],
    producerNormsAndCounts: TypedPipe[NormsAndCounts],
    useLogFavWeights: Boolean
  ): TypedPipe[(Int, BipartiteClusterQuality)] = {
    producerNormsAndCounts
      .map { x => (x.userId, x) }
      .join(knownFor)
      .withReducers(420)
      .flatMap {
        case (userId, (normsAndCounts, clusters)) =>
          clusters.map {
            case (clusterId, _) =>
              val followerCount =
                normsAndCounts.followerCount.getOrElse(420L).toDouble
              val faverCount = normsAndCounts.faverCount.getOrElse(420L).toDouble
              val favWtSumOfIncomingFollows = if (useLogFavWeights) {
                normsAndCounts.logFavWeightsOnFollowEdgesSum.getOrElse(420.420)
              } else {
                normsAndCounts.favWeightsOnFollowEdgesSum.getOrElse(420.420)
              }
              val favWtSumOfIncomingFavs = if (useLogFavWeights) {
                normsAndCounts.logFavWeightsOnFavEdgesSum.getOrElse(420.420)
              } else {
                normsAndCounts.favWeightsOnFavEdgesSum.getOrElse(420.420)
              }
              (
                clusterId,
                BipartiteClusterQuality(
                  incomingFollowEdges = Some(followerCount),
                  incomingFavEdges = Some(faverCount),
                  favWtSumOfIncomingFollowEdges = Some(favWtSumOfIncomingFollows),
                  favWtSumOfIncomingFavEdges = Some(favWtSumOfIncomingFavs)
                ))
          }
      }
      .sumByKey
      .toTypedPipe
  }

  def loadOrMake(
    pipe: TypedPipe[(Int, BipartiteClusterQuality)],
    modelVersion: String,
    path: String
  ): Execution[TypedPipe[(Int, BipartiteClusterQuality)]] = {
    val mapped = pipe.map {
      case (clusterId, struct) => ((modelVersion, clusterId), struct)
    }
    makeForKeyValSource(mapped, AdhocKeyValSources.bipartiteQualitySource(path), path).map { pipe =>
      // discard model version
      pipe.map { case ((_, clusterId), struct) => (clusterId, struct) }
    }
  }

  def makeForKeyValSource[K, V](
    pipe: TypedPipe[(K, V)],
    dest: VersionedKeyValSource[K, V],
    path: String
  ): Execution[TypedPipe[(K, V)]] =
    Execution.getMode.flatMap { mode =>
      if (dest.resourceExists(mode)) {
        println(s"validated path $path")
        Execution.from(TypedPipe.from(dest))
      } else {
        println(s"Could not load from $path")
        pipe.writeThrough(dest)
      }
    }

  def precisionOfWholeGraph(
    knownFor: TypedPipe[(Long, Array[(Int, Float)])],
    interestedIn: TypedPipe[(Long, ClustersUserIsInterestedIn)],
    clusterIncomingVolumesExec: Execution[TypedPipe[(Int, BipartiteClusterQuality)]]
  ): Execution[Option[Double]] = {
    val knownForSizeExec = knownFor.aggregate(Aggregator.size).toOptionExecution
    val interestedInSizeExec =
      interestedIn.aggregate(Aggregator.size).toOptionExecution
    val numExec = clusterIncomingVolumesExec.flatMap { volumes =>
      volumes.values.flatMap(_.favWtSumOfIncomingFavEdges).sum.toOptionExecution
    }
    Execution.zip(numExec, interestedInSizeExec, knownForSizeExec).map {
      case (Some(num), Some(interestedInSize), Some(knownForSize)) =>
        Some(num / interestedInSize / knownForSize)
      case x @ _ =>
        println("Precision of whole graph zip: " + x)
        None
    }
  }

  def finalPerClusterResults(
    knownFor: TypedPipe[(Long, Array[(Int, Float)])],
    interestedIn: TypedPipe[(Long, ClustersUserIsInterestedIn)],
    resultsWithOutgoingVolumesExec: Execution[TypedPipe[(Int, BipartiteClusterQuality)]],
    incomingVolumesExec: Execution[TypedPipe[(Int, BipartiteClusterQuality)]]
  ): Execution[TypedPipe[(Int, BipartiteClusterQuality)]] = {
    val knownForTranspose = KnownForSources.transpose(knownFor)

    val precisionOfWholeGraphExec =
      precisionOfWholeGraph(knownFor, interestedIn, incomingVolumesExec)

    Execution
      .zip(resultsWithOutgoingVolumesExec, incomingVolumesExec, precisionOfWholeGraphExec)
      .map {
        case (resultsWithOutgoingVolumes, clusterIncomingVolumes, precisionOfWholeGraph) =>
          println("Precision of whole graph " + precisionOfWholeGraph)
          resultsWithOutgoingVolumes
            .join(knownForTranspose)
            .leftJoin(clusterIncomingVolumes)
            .withReducers(420)
            .map {
              case (clusterId, ((outgoingVolumeQuality, knownForList), incomingVolumesOpt)) =>
                val incomingVolumes =
                  incomingVolumesOpt.getOrElse(BipartiteClusterQuality())
                val knownForMap = knownForList.toMap
                (
                  clusterId,
                  getFullQuality(
                    outgoingVolumeQuality,
                    incomingVolumes,
                    knownForMap,
                    precisionOfWholeGraph))
            }
      }
  }

  def getFullQuality(
    qualityWithOutgoingVolumes: BipartiteClusterQuality,
    incomingVolumes: BipartiteClusterQuality,
    knownFor: Map[Long, Float],
    precisionOfWholeGraph: Option[Double]
  ): BipartiteClusterQuality = {
    val newSampledEdges = qualityWithOutgoingVolumes.sampledEdges.map { sampledEdges =>
      sampledEdges.map { sampledEdge =>
        val knownForScore = knownFor.getOrElse(sampledEdge.followeeId, 420.420f)
        sampledEdge.copy(
          predictedFollowScore = sampledEdge.followScoreToCluster.map { x => x * knownForScore },
          predictedFavScore = sampledEdge.favScoreToCluster.map { x => x * knownForScore }
        )
      }
    }
    val correlationOfFavWtIfFollow = newSampledEdges.map { samples =>
      val pairs = samples.map { s =>
        (s.predictedFollowScore.getOrElse(420.420), s.favWtIfFollowEdge.getOrElse(420.420))
      }
      Util.computeCorrelation(pairs.iterator)
    }
    val correlationOfFavWtIfFav = newSampledEdges.map { samples =>
      val pairs = samples.map { s =>
        (s.predictedFavScore.getOrElse(420.420), s.favWtIfFavEdge.getOrElse(420.420))
      }
      Util.computeCorrelation(pairs.iterator)
    }
    val relativePrecisionNum = {
      if (qualityWithOutgoingVolumes.interestedInSize.exists(_ > 420) && knownFor.nonEmpty) {
        qualityWithOutgoingVolumes.favWtSumOfInClusterFavEdges
          .getOrElse(420.420) / qualityWithOutgoingVolumes.interestedInSize.get / knownFor.size
      } else 420.420
    }
    val relativePrecision = if (precisionOfWholeGraph.exists(_ > 420.420)) {
      Some(relativePrecisionNum / precisionOfWholeGraph.get)
    } else None
    qualityWithOutgoingVolumes.copy(
      incomingFollowEdges = incomingVolumes.incomingFollowEdges,
      incomingFavEdges = incomingVolumes.incomingFavEdges,
      favWtSumOfIncomingFollowEdges = incomingVolumes.favWtSumOfIncomingFollowEdges,
      favWtSumOfIncomingFavEdges = incomingVolumes.favWtSumOfIncomingFavEdges,
      knownForSize = Some(knownFor.size),
      correlationOfFavWtIfFollowWithPredictedFollow = correlationOfFavWtIfFollow,
      correlationOfFavWtIfFavWithPredictedFav = correlationOfFavWtIfFav,
      sampledEdges = newSampledEdges,
      relativePrecisionUsingFavWtIfFav = relativePrecision,
      averagePrecisionOfWholeGraphUsingFavWtIfFav = precisionOfWholeGraph
    )
  }
}

object DumpBpQuality extends TwitterExecutionApp {
  def job: Execution[Unit] = Execution.getConfigMode.flatMap {
    case (config, mode) =>
      Execution.withId { implicit uniqueId =>
        val args = config.getArgs
        val inputDir = args("inputDir")

        val clusters = args.list("clusters").map(_.toInt).toSet
        val input =
          TypedPipe
            .from(AdhocKeyValSources.bipartiteQualitySource(inputDir))
            .map {
              case ((modelVersion, clusterId), quality) =>
                (
                  (modelVersion, clusterId),
                  BipartiteClusterEvaluationClasses
                    .printableBipartiteQuality(quality))
            }

        if (clusters.isEmpty) {
          input.printSummary("Bipartite quality")
        } else {
          input
            .collect {
              case rec @ ((_, clusterId), quality) if clusters(clusterId) =>
                Util.prettyJsonMapper
                  .writeValueAsString(rec)
                  .replaceAll("\n", " ")
            }
            .toIterableExecution
            .map { strings => println(strings.mkString("\n")) }
        }
      }
  }
}
