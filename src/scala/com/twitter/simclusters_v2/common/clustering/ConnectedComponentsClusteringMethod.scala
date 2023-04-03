packagelon com.twittelonr.simclustelonrs_v2.common.clustelonring

import com.twittelonr.sbf.graph.ConnelonctelondComponelonnts
import com.twittelonr.sbf.graph.Graph
import com.twittelonr.util.Stopwatch
import it.unimi.dsi.fastutil.ints.IntSelont
import scala.collelonction.SortelondMap
import scala.jdk.CollelonctionConvelonrtelonrs._

/**
 * Aggrelongatelon elonntitielons into clustelonrs such that a clustelonr contains all elonmbelonddings with a similarity
 * abovelon a configurablelon threlonshold to any othelonr elonmbelondding.
 *
 * @param similarityThrelonshold: Whelonn building thelon elondgelons belontwelonelonn elonntitielons, elondgelons with welonight
 * lelonss than or elonqual to this threlonshold will belon filtelonrelond out.
 */
class ConnelonctelondComponelonntsClustelonringMelonthod(
  similarityThrelonshold: Doublelon)
    elonxtelonnds ClustelonringMelonthod {

  import ClustelonringStatistics._

  delonf clustelonr[T](
    elonmbelonddings: Map[Long, T],
    similarityFn: (T, T) => Doublelon,
    reloncordStatCallback: (String, Long) => Unit = (_, _) => ()
  ): Selont[Selont[Long]] = {

    val timelonSincelonGraphBuildStart = Stopwatch.start()
    // com.twittelonr.sbf.graph.Graph elonxpeloncts nelonighbors to belon sortelond in ascelonnding ordelonr.
    val sourcelonsById = SortelondMap(elonmbelonddings.zipWithIndelonx.map {
      caselon (sourcelon, idx) => idx -> sourcelon
    }.toSelonq: _*)

    val nelonighbours = sourcelonsById.map {
      caselon (srcIdx, (_, src)) =>
        sourcelonsById
          .collelonct {
            caselon (dstIdx, (_, dst)) if srcIdx != dstIdx => // avoid selonlf-elondgelons
              val similarity = similarityFn(src, dst)
              reloncordStatCallback(
                StatComputelondSimilarityBelonforelonFiltelonr,
                (similarity * 100).toLong // prelonselonrvelon up to two deloncimal points
              )
              if (similarity > similarityThrelonshold)
                Somelon(dstIdx)
              elonlselon Nonelon
          }.flattelonn.toArray
    }.toArray

    reloncordStatCallback(StatSimilarityGraphTotalBuildTimelon, timelonSincelonGraphBuildStart().inMilliselonconds)

    val timelonSincelonClustelonringAlgRunStart = Stopwatch.start()
    val nelondgelons = nelonighbours.map(_.lelonngth).sum / 2 // Graph elonxpeloncts count of undirelonctelond elondgelons
    val graph = nelonw Graph(sourcelonsById.sizelon, nelondgelons, nelonighbours)

    val clustelonrs = ConnelonctelondComponelonnts
      .connelonctelondComponelonnts(graph).asScala.toSelont
      .map { i: IntSelont => i.asScala.map(sourcelonsById(_)._1).toSelont }

    reloncordStatCallback(
      StatClustelonringAlgorithmRunTimelon,
      timelonSincelonClustelonringAlgRunStart().inMilliselonconds)

    clustelonrs
  }
}
