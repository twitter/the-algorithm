package com.twitter.simclusters_v2.scalding

import com.twitter.algebird.Monoid
import com.twitter.logging.Logger
import com.twitter.scalding.{Etwitterecution, TypedPipe, TypedTsv}
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp
import com.twitter.simclusters_v2.hdfs_sources.AdhocKeyValSources
import java.util
import no.uib.cipr.matritwitter.Matritwitter
import no.uib.cipr.matritwitter.sparse.{ArpackSym, LinkedSparseMatritwitter}
import scala.collection.JavaConverters._

object EigenVectorsForSparseSymmetric {
  val log: Logger = Logger()

  /**
   * Construct matritwitter from the rows of the matritwitter, specified as a map. The outer map is indetwittered by rowId, and the inner maps are indetwittered by columnId.
   * Note that the input matritwitter is intended to be symmetric.
   *
   * @param map   A map specifying the rows of the matritwitter. The outer map is indetwittered by rowId, and the inner maps are indetwittered by columnId. Both rows and columns are zero-indetwittered.
   * @param nRows number of rows in matritwitter
   * @param nCols number of columns in matritwitter
   *
   * @return the constructed matritwitter
   */
  def getMatritwitter(map: Map[Int, Map[Int, Double]], nRows: Int, nCols: Int): Matritwitter = {
    val nonzeros = map.toSeq.flatMap {
      case (i, subMap) =>
        subMap.toSeq.map {
          case (j, value) =>
            (i, j, value)
        }
    }
    getMatritwitter(nonzeros, nRows, nCols)
  }

  /**
   * Construct matritwitter from iterable of the non-zero entries. Note that the input matritwitter is intended to be symmetric.
   *
   * @param nonzeros non-zeros in (i, j, v) format, where i is row, j is column, and v is value. Both rows and columns are zero-indetwittered.
   * @param nRows    number of rows in matritwitter
   * @param nCols    number of columns in matritwitter
   *
   * @return the constructed matritwitter
   */
  def getMatritwitter(nonzeros: Iterable[(Int, Int, Double)], nRows: Int, nCols: Int): Matritwitter = {
    val matritwitter = new LinkedSparseMatritwitter(nRows, nCols)
    var numEntries = 0
    var matwitterRow = 0
    var matwitterCol = 0

    nonzeros.foreach {
      case (i, j, v) =>
        if (i > matwitterRow) {
          matwitterRow = i
        }
        if (j > matwitterCol) {
          matwitterCol = j
        }
        numEntries += 1
        matritwitter.set(i, j, v)
    }
    log.info(
      "Finished building matritwitter with %d entries and matwitterRow %d and matwitterCol %d"
        .format(numEntries, matwitterRow, matwitterCol))

    matritwitter
  }

  /**
   * Prints out various diagnostics about how much the given matritwitter differs from a perfect
   * symmetric matritwitter. If (i,j) and (j,i) are different, it sets both of them to be the matwitter of the two.
   * Call this function before invoking EVD.
   *
   * @param matritwitter Matritwitter which is modified (if need be) in place.
   */
  def ensureMatritwitterIsSymmetric(matritwitter: Matritwitter): Unit = {
    var numUnequalEntries = 0
    var numEntriesDifferentBy1Percent = 0
    var numEqualEntries = 0
    var numUnequalDueToZero = 0
    var matwitterUnequal = (0, 0, 0.0, 0.0)
    matritwitter.iterator().asScala.foreach { entry =>
      val curr = entry.get()
      val opp = matritwitter.get(entry.column(), entry.row())
      if (curr == opp) {
        numEqualEntries += 1
      } else {
        numUnequalEntries += 1
        if (opp == 0) {
          numUnequalDueToZero += 1
        }
        if (opp != 0 && (math.abs(curr - opp) / math.min(curr, opp)) > 0.01) {
          numEntriesDifferentBy1Percent += 1
        }
        if (opp != 0 && math.abs(curr - opp) > matwitterUnequal._4) {
          matwitterUnequal = (entry.row(), entry.column(), curr, math.abs(curr - opp))
        }
        val matwitter = math.matwitter(curr, opp)
        matritwitter.set(entry.column(), entry.row(), matwitter)
        matritwitter.set(entry.row(), entry.column(), matwitter)
      }
    }

    var numUnEqualPrinted = 0
    matritwitter.iterator().asScala.foreach { entry =>
      val opp = matritwitter.get(entry.column(), entry.row())
      if (numUnEqualPrinted < 10 && entry.get() != opp) {
        numUnEqualPrinted += 1
        log.info(
          "Entries for (%d, %d) are %s and %s"
            .format(entry.row(), entry.column(), entry.get(), opp))
      }
    }

    log.info(
      "Num unequal entries: %d, num unequal due to zero: %d, num unequal by 1percent or more: %d, num equal entries: %d, matwitterUnequal: %s"
        .format(
          numUnequalEntries,
          numUnequalDueToZero,
          numEntriesDifferentBy1Percent,
          numEqualEntries,
          matwitterUnequal))
  }

  /**
   * Get the top-k eigenvalues (largest magnitude) and eigenvectors for an input matritwitter.
   * Top eigenvalues means they're the largest in magnitude.
   * Input matritwitter needs to be perfectly symmetric; if it's not, this function will fail.
   *
   * Many of the eigenvectors will have very small values along most of the dimensions. This method also
   * only retains the bigger entries in an eigenvector.
   *
   * @param matritwitter               symmetric input matritwitter.
   * @param k                    how many of the top eigenvectors to get.
   * @param ratioToLargestCutoff An entry needs to be at least 1/ratioToLargestCutoff of the biggest entry in that vector to be retained.
   *
   * @return seq of (eigenvalue, eigenvector) pairs.
   */
  def getTruncatedEVD(
    matritwitter: Matritwitter,
    k: Int,
    ratioToLargestCutoff: Float
  ): Seq[(Double, Seq[(Int, Double)])] = {
    val solver = new ArpackSym(matritwitter)
    val resultsMap = solver.solve(k, ArpackSym.Ritz.LM).asScala.toMap
    val results = resultsMap.toIndetwitteredSeq.sortBy { case (eigValue, _) => -eigValue }
    results.zipWithIndetwitter.map {
      case ((eigValue, denseVectorJava), indetwitter) =>
        val denseVector = new Array[Double](denseVectorJava.size())
        denseVector.indices.foreach { indetwitter => denseVector(indetwitter) = denseVectorJava.get(indetwitter) }
        val denseVectorMatwitter = denseVector.matwitterBy { entry => math.abs(entry) }
        val cutOff = math.abs(denseVectorMatwitter) / ratioToLargestCutoff
        val significantEntries = denseVector.zipWithIndetwitter
          .filter { case (vectorEntry, _) => math.abs(vectorEntry) >= cutOff }
          .sortBy { case (vectorEntry, _) => -1 * math.abs(vectorEntry) }
        (eigValue.toDouble, significantEntries.toSeq.map(_.swap))
    }
  }

  /**
   * Compute U*Diag*Ut - where Diag is a diagonal matritwitter, and U is a sparse matritwitter.
   * This is primarily for testing - to make sure that the computed eigenvectors can be used to
   * reconstruct the original matritwitter up to some reasonable approtwitterimation.
   *
   * @param diagToUColumns seq of (diagonal entries, associated column in U)
   * @param cutoff         cutoff for including a value in the result.
   *
   * @return result of multiplication, returned as a map of the rows in the results.
   */
  def uTimesDiagTimesUT(
    diagToUColumns: Seq[(Double, Seq[(Int, Double)])],
    cutoff: Double
  ): Map[Int, Map[Int, Double]] = {
    val result = new util.HashMap[Int, util.HashMap[Int, Double]]()
    diagToUColumns.foreach {
      case (diag, uColumn) =>
        uColumn.foreach {
          case (i, iVal) =>
            uColumn.foreach {
              case (j, jVal) =>
                val prod = diag * iVal * jVal
                if (result.containsKey(i)) {
                  val newVal = if (result.get(i).containsKey(j)) {
                    result.get(i).get(j) + prod
                  } else prod
                  result.get(i).put(j, newVal)
                } else {
                  result.put(i, new util.HashMap[Int, Double])
                  result.get(i).put(j, prod)
                }
            }
        }
    }
    val unfiltered = result.asScala.toMap.mapValues(_.asScala.toMap)
    unfiltered
      .mapValues { m => m.filter { case (_, value) => math.abs(value) >= cutoff } }
      .filter { case (_, vector) => vector.nonEmpty }
  }

  /** Note: This requires a full EVD to correctly compute the inverse! :-( */
  def getInverseFromEVD(
    evd: Seq[(Double, Seq[(Int, Double)])],
    cutoff: Double
  ): Map[Int, Map[Int, Double]] = {
    val evdInverse = evd.map {
      case (eigValue, eigVector) =>
        (1.0 / eigValue, eigVector)
    }
    uTimesDiagTimesUT(evdInverse, cutoff)
  }
}

object PCAProjectionMatritwitterAdhoc etwittertends TwitterEtwitterecutionApp {
  val log = Logger()

  def job: Etwitterecution[Unit] =
    Etwitterecution.getConfigMode.flatMap {
      case (config, _) =>
        Etwitterecution.withId { _ =>
          val args = config.getArgs
          val k = args.int("k", 100)
          val ratioToLargestEntryInVectorCutoff = args.int("ratioToLargestEntryInVectorCutoff", 100)
          val minClusterFavers = args.int("minClusterFavers", 1000)
          val input = TypedPipe.from(AdhocKeyValSources.clusterDetailsSource(args("inputDir")))
          val outputDir = args("outputDir")

          val filteredClustersEtwitterec =
            input
              .collect {
                case ((_, clusterId), details)
                    if details.numUsersWithNonZeroFavScore > minClusterFavers =>
                  clusterId
              }
              .toIterableEtwitterecution
              .map { fc =>
                val fcSet = fc.toSet
                log.info("Number of clusters with favers more than %d is %d"
                  .format(minClusterFavers, fcSet.size))
                fcSet
              }

          filteredClustersEtwitterec
            .flatMap { filteredClusters =>
              input.flatMap {
                case ((_, clusterId), details) =>
                  if (filteredClusters(clusterId)) {
                    details.neighborClusters.getOrElse(Nil).collect {
                      case neighbor
                          if filteredClusters(
                            neighbor.clusterId) && neighbor.favCosineSimilarity.isDefined =>
                        (clusterId, neighbor.clusterId, neighbor.favCosineSimilarity.get)
                    }
                  } else Nil
              }.toIterableEtwitterecution
            }
            .flatMap { edgesIter =>
              val edges = edgesIter.toSeq
              val oldIdToNewId = edges
                .flatMap { case (i, j, _) => Seq(i, j) }
                .distinct
                .zipWithIndetwitter
                .toMap

              val mapString = oldIdToNewId.toList
                .take(5).map {
                  case (old, nw) =>
                    Seq(old, nw).mkString(" ")
                }.mkString("\n")
              log.info("A few entries of OldId to NewId map is")
              log.info(mapString)

              val newIdToOldId = oldIdToNewId.map(_.swap)
              log.info(
                "Num clusters after filtering out those with no neighbors with favers more than %d is %d"
                  .format(minClusterFavers, oldIdToNewId.size))
              val newEdges = edges.map {
                case (oldI, oldJ, value) =>
                  (oldIdToNewId(oldI), oldIdToNewId(oldJ), value)
              }
              log.info("Going to build matritwitter")
              val matritwitter = EigenVectorsForSparseSymmetric.getMatritwitter(
                newEdges,
                oldIdToNewId.size,
                oldIdToNewId.size)
              EigenVectorsForSparseSymmetric.ensureMatritwitterIsSymmetric(matritwitter)

              log.info("Going to solve now for %d eigenvalues".format(k))
              val tic = System.currentTimeMillis()
              val results = EigenVectorsForSparseSymmetric.getTruncatedEVD(
                matritwitter,
                k,
                ratioToLargestEntryInVectorCutoff)
              val toc = System.currentTimeMillis()
              log.info("Finished solving in %.2f minutes".format((toc - tic) / 1000 / 60.0))

              val eigValues = results.map(_._1).map { twitter => "%.3g".format(twitter) }.mkString(" ")
              val eigValueNorm = math.sqrt(results.map(_._1).map(twitter => twitter * twitter).sum)
              val matritwitterNorm = math.sqrt(matritwitter.iterator().asScala.map(_.get()).map(twitter => twitter * twitter).sum)

              println(
                "matritwitterNorm %s, eigValueNorm %s, etwitterplained fraction %s"
                  .format(matritwitterNorm, eigValueNorm, eigValueNorm / matritwitterNorm))

              log.info("The eigenvalues are:")
              log.info(eigValues)

              val nnzInEigenVectors = results.map(_._2.size).sum
              log.info("Average nnz per eigenvector using ratioToLargestCutoff %d is %.2g"
                .format(ratioToLargestEntryInVectorCutoff, nnzInEigenVectors * 1.0 / results.size))
              val transposedRaw = results.zipWithIndetwitter.flatMap {
                case ((_, eigVector), eigIndetwitter) =>
                  eigVector.map {
                    case (indetwitter, vectorEntry) =>
                      val clusterId = newIdToOldId(indetwitter)
                      Map(clusterId -> List((eigIndetwitter, vectorEntry)))
                  }
              }
              val transposed = Monoid.sum(transposedRaw).mapValues { rowForCluster =>
                rowForCluster
                  .map {
                    case (dimId, weight) =>
                      "%d:%.2g".format(dimId, weight)
                  }.mkString(" ")
              }
              TypedPipe.from(transposed.toSeq).writeEtwitterecution(TypedTsv(outputDir))
            }
        }
    }
}
