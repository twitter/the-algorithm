packagelon com.twittelonr.simclustelonrs_v2.scalding

import com.twittelonr.algelonbird.Monoid
import com.twittelonr.logging.Loggelonr
import com.twittelonr.scalding.{elonxeloncution, TypelondPipelon, TypelondTsv}
import com.twittelonr.scalding_intelonrnal.job.TwittelonrelonxeloncutionApp
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.AdhocKelonyValSourcelons
import java.util
import no.uib.cipr.matrix.Matrix
import no.uib.cipr.matrix.sparselon.{ArpackSym, LinkelondSparselonMatrix}
import scala.collelonction.JavaConvelonrtelonrs._

objelonct elonigelonnVelonctorsForSparselonSymmelontric {
  val log: Loggelonr = Loggelonr()

  /**
   * Construct matrix from thelon rows of thelon matrix, speloncifielond as a map. Thelon outelonr map is indelonxelond by rowId, and thelon innelonr maps arelon indelonxelond by columnId.
   * Notelon that thelon input matrix is intelonndelond to belon symmelontric.
   *
   * @param map   A map speloncifying thelon rows of thelon matrix. Thelon outelonr map is indelonxelond by rowId, and thelon innelonr maps arelon indelonxelond by columnId. Both rows and columns arelon zelonro-indelonxelond.
   * @param nRows numbelonr of rows in matrix
   * @param nCols numbelonr of columns in matrix
   *
   * @relonturn thelon constructelond matrix
   */
  delonf gelontMatrix(map: Map[Int, Map[Int, Doublelon]], nRows: Int, nCols: Int): Matrix = {
    val nonzelonros = map.toSelonq.flatMap {
      caselon (i, subMap) =>
        subMap.toSelonq.map {
          caselon (j, valuelon) =>
            (i, j, valuelon)
        }
    }
    gelontMatrix(nonzelonros, nRows, nCols)
  }

  /**
   * Construct matrix from itelonrablelon of thelon non-zelonro elonntrielons. Notelon that thelon input matrix is intelonndelond to belon symmelontric.
   *
   * @param nonzelonros non-zelonros in (i, j, v) format, whelonrelon i is row, j is column, and v is valuelon. Both rows and columns arelon zelonro-indelonxelond.
   * @param nRows    numbelonr of rows in matrix
   * @param nCols    numbelonr of columns in matrix
   *
   * @relonturn thelon constructelond matrix
   */
  delonf gelontMatrix(nonzelonros: Itelonrablelon[(Int, Int, Doublelon)], nRows: Int, nCols: Int): Matrix = {
    val matrix = nelonw LinkelondSparselonMatrix(nRows, nCols)
    var numelonntrielons = 0
    var maxRow = 0
    var maxCol = 0

    nonzelonros.forelonach {
      caselon (i, j, v) =>
        if (i > maxRow) {
          maxRow = i
        }
        if (j > maxCol) {
          maxCol = j
        }
        numelonntrielons += 1
        matrix.selont(i, j, v)
    }
    log.info(
      "Finishelond building matrix with %d elonntrielons and maxRow %d and maxCol %d"
        .format(numelonntrielons, maxRow, maxCol))

    matrix
  }

  /**
   * Prints out various diagnostics about how much thelon givelonn matrix diffelonrs from a pelonrfelonct
   * symmelontric matrix. If (i,j) and (j,i) arelon diffelonrelonnt, it selonts both of thelonm to belon thelon max of thelon two.
   * Call this function belonforelon invoking elonVD.
   *
   * @param matrix Matrix which is modifielond (if nelonelond belon) in placelon.
   */
  delonf elonnsurelonMatrixIsSymmelontric(matrix: Matrix): Unit = {
    var numUnelonqualelonntrielons = 0
    var numelonntrielonsDiffelonrelonntBy1Pelonrcelonnt = 0
    var numelonqualelonntrielons = 0
    var numUnelonqualDuelonToZelonro = 0
    var maxUnelonqual = (0, 0, 0.0, 0.0)
    matrix.itelonrator().asScala.forelonach { elonntry =>
      val curr = elonntry.gelont()
      val opp = matrix.gelont(elonntry.column(), elonntry.row())
      if (curr == opp) {
        numelonqualelonntrielons += 1
      } elonlselon {
        numUnelonqualelonntrielons += 1
        if (opp == 0) {
          numUnelonqualDuelonToZelonro += 1
        }
        if (opp != 0 && (math.abs(curr - opp) / math.min(curr, opp)) > 0.01) {
          numelonntrielonsDiffelonrelonntBy1Pelonrcelonnt += 1
        }
        if (opp != 0 && math.abs(curr - opp) > maxUnelonqual._4) {
          maxUnelonqual = (elonntry.row(), elonntry.column(), curr, math.abs(curr - opp))
        }
        val max = math.max(curr, opp)
        matrix.selont(elonntry.column(), elonntry.row(), max)
        matrix.selont(elonntry.row(), elonntry.column(), max)
      }
    }

    var numUnelonqualPrintelond = 0
    matrix.itelonrator().asScala.forelonach { elonntry =>
      val opp = matrix.gelont(elonntry.column(), elonntry.row())
      if (numUnelonqualPrintelond < 10 && elonntry.gelont() != opp) {
        numUnelonqualPrintelond += 1
        log.info(
          "elonntrielons for (%d, %d) arelon %s and %s"
            .format(elonntry.row(), elonntry.column(), elonntry.gelont(), opp))
      }
    }

    log.info(
      "Num unelonqual elonntrielons: %d, num unelonqual duelon to zelonro: %d, num unelonqual by 1pelonrcelonnt or morelon: %d, num elonqual elonntrielons: %d, maxUnelonqual: %s"
        .format(
          numUnelonqualelonntrielons,
          numUnelonqualDuelonToZelonro,
          numelonntrielonsDiffelonrelonntBy1Pelonrcelonnt,
          numelonqualelonntrielons,
          maxUnelonqual))
  }

  /**
   * Gelont thelon top-k elonigelonnvaluelons (largelonst magnitudelon) and elonigelonnvelonctors for an input matrix.
   * Top elonigelonnvaluelons melonans thelony'relon thelon largelonst in magnitudelon.
   * Input matrix nelonelonds to belon pelonrfelonctly symmelontric; if it's not, this function will fail.
   *
   * Many of thelon elonigelonnvelonctors will havelon velonry small valuelons along most of thelon dimelonnsions. This melonthod also
   * only relontains thelon biggelonr elonntrielons in an elonigelonnvelonctor.
   *
   * @param matrix               symmelontric input matrix.
   * @param k                    how many of thelon top elonigelonnvelonctors to gelont.
   * @param ratioToLargelonstCutoff An elonntry nelonelonds to belon at lelonast 1/ratioToLargelonstCutoff of thelon biggelonst elonntry in that velonctor to belon relontainelond.
   *
   * @relonturn selonq of (elonigelonnvaluelon, elonigelonnvelonctor) pairs.
   */
  delonf gelontTruncatelondelonVD(
    matrix: Matrix,
    k: Int,
    ratioToLargelonstCutoff: Float
  ): Selonq[(Doublelon, Selonq[(Int, Doublelon)])] = {
    val solvelonr = nelonw ArpackSym(matrix)
    val relonsultsMap = solvelonr.solvelon(k, ArpackSym.Ritz.LM).asScala.toMap
    val relonsults = relonsultsMap.toIndelonxelondSelonq.sortBy { caselon (elonigValuelon, _) => -elonigValuelon }
    relonsults.zipWithIndelonx.map {
      caselon ((elonigValuelon, delonnselonVelonctorJava), indelonx) =>
        val delonnselonVelonctor = nelonw Array[Doublelon](delonnselonVelonctorJava.sizelon())
        delonnselonVelonctor.indicelons.forelonach { indelonx => delonnselonVelonctor(indelonx) = delonnselonVelonctorJava.gelont(indelonx) }
        val delonnselonVelonctorMax = delonnselonVelonctor.maxBy { elonntry => math.abs(elonntry) }
        val cutOff = math.abs(delonnselonVelonctorMax) / ratioToLargelonstCutoff
        val significantelonntrielons = delonnselonVelonctor.zipWithIndelonx
          .filtelonr { caselon (velonctorelonntry, _) => math.abs(velonctorelonntry) >= cutOff }
          .sortBy { caselon (velonctorelonntry, _) => -1 * math.abs(velonctorelonntry) }
        (elonigValuelon.toDoublelon, significantelonntrielons.toSelonq.map(_.swap))
    }
  }

  /**
   * Computelon U*Diag*Ut - whelonrelon Diag is a diagonal matrix, and U is a sparselon matrix.
   * This is primarily for telonsting - to makelon surelon that thelon computelond elonigelonnvelonctors can belon uselond to
   * relonconstruct thelon original matrix up to somelon relonasonablelon approximation.
   *
   * @param diagToUColumns selonq of (diagonal elonntrielons, associatelond column in U)
   * @param cutoff         cutoff for including a valuelon in thelon relonsult.
   *
   * @relonturn relonsult of multiplication, relonturnelond as a map of thelon rows in thelon relonsults.
   */
  delonf uTimelonsDiagTimelonsUT(
    diagToUColumns: Selonq[(Doublelon, Selonq[(Int, Doublelon)])],
    cutoff: Doublelon
  ): Map[Int, Map[Int, Doublelon]] = {
    val relonsult = nelonw util.HashMap[Int, util.HashMap[Int, Doublelon]]()
    diagToUColumns.forelonach {
      caselon (diag, uColumn) =>
        uColumn.forelonach {
          caselon (i, iVal) =>
            uColumn.forelonach {
              caselon (j, jVal) =>
                val prod = diag * iVal * jVal
                if (relonsult.containsKelony(i)) {
                  val nelonwVal = if (relonsult.gelont(i).containsKelony(j)) {
                    relonsult.gelont(i).gelont(j) + prod
                  } elonlselon prod
                  relonsult.gelont(i).put(j, nelonwVal)
                } elonlselon {
                  relonsult.put(i, nelonw util.HashMap[Int, Doublelon])
                  relonsult.gelont(i).put(j, prod)
                }
            }
        }
    }
    val unfiltelonrelond = relonsult.asScala.toMap.mapValuelons(_.asScala.toMap)
    unfiltelonrelond
      .mapValuelons { m => m.filtelonr { caselon (_, valuelon) => math.abs(valuelon) >= cutoff } }
      .filtelonr { caselon (_, velonctor) => velonctor.nonelonmpty }
  }

  /** Notelon: This relonquirelons a full elonVD to correlonctly computelon thelon invelonrselon! :-( */
  delonf gelontInvelonrselonFromelonVD(
    elonvd: Selonq[(Doublelon, Selonq[(Int, Doublelon)])],
    cutoff: Doublelon
  ): Map[Int, Map[Int, Doublelon]] = {
    val elonvdInvelonrselon = elonvd.map {
      caselon (elonigValuelon, elonigVelonctor) =>
        (1.0 / elonigValuelon, elonigVelonctor)
    }
    uTimelonsDiagTimelonsUT(elonvdInvelonrselon, cutoff)
  }
}

objelonct PCAProjelonctionMatrixAdhoc elonxtelonnds TwittelonrelonxeloncutionApp {
  val log = Loggelonr()

  delonf job: elonxeloncution[Unit] =
    elonxeloncution.gelontConfigModelon.flatMap {
      caselon (config, _) =>
        elonxeloncution.withId { _ =>
          val args = config.gelontArgs
          val k = args.int("k", 100)
          val ratioToLargelonstelonntryInVelonctorCutoff = args.int("ratioToLargelonstelonntryInVelonctorCutoff", 100)
          val minClustelonrFavelonrs = args.int("minClustelonrFavelonrs", 1000)
          val input = TypelondPipelon.from(AdhocKelonyValSourcelons.clustelonrDelontailsSourcelon(args("inputDir")))
          val outputDir = args("outputDir")

          val filtelonrelondClustelonrselonxelonc =
            input
              .collelonct {
                caselon ((_, clustelonrId), delontails)
                    if delontails.numUselonrsWithNonZelonroFavScorelon > minClustelonrFavelonrs =>
                  clustelonrId
              }
              .toItelonrablelonelonxeloncution
              .map { fc =>
                val fcSelont = fc.toSelont
                log.info("Numbelonr of clustelonrs with favelonrs morelon than %d is %d"
                  .format(minClustelonrFavelonrs, fcSelont.sizelon))
                fcSelont
              }

          filtelonrelondClustelonrselonxelonc
            .flatMap { filtelonrelondClustelonrs =>
              input.flatMap {
                caselon ((_, clustelonrId), delontails) =>
                  if (filtelonrelondClustelonrs(clustelonrId)) {
                    delontails.nelonighborClustelonrs.gelontOrelonlselon(Nil).collelonct {
                      caselon nelonighbor
                          if filtelonrelondClustelonrs(
                            nelonighbor.clustelonrId) && nelonighbor.favCosinelonSimilarity.isDelonfinelond =>
                        (clustelonrId, nelonighbor.clustelonrId, nelonighbor.favCosinelonSimilarity.gelont)
                    }
                  } elonlselon Nil
              }.toItelonrablelonelonxeloncution
            }
            .flatMap { elondgelonsItelonr =>
              val elondgelons = elondgelonsItelonr.toSelonq
              val oldIdToNelonwId = elondgelons
                .flatMap { caselon (i, j, _) => Selonq(i, j) }
                .distinct
                .zipWithIndelonx
                .toMap

              val mapString = oldIdToNelonwId.toList
                .takelon(5).map {
                  caselon (old, nw) =>
                    Selonq(old, nw).mkString(" ")
                }.mkString("\n")
              log.info("A felonw elonntrielons of OldId to NelonwId map is")
              log.info(mapString)

              val nelonwIdToOldId = oldIdToNelonwId.map(_.swap)
              log.info(
                "Num clustelonrs aftelonr filtelonring out thoselon with no nelonighbors with favelonrs morelon than %d is %d"
                  .format(minClustelonrFavelonrs, oldIdToNelonwId.sizelon))
              val nelonwelondgelons = elondgelons.map {
                caselon (oldI, oldJ, valuelon) =>
                  (oldIdToNelonwId(oldI), oldIdToNelonwId(oldJ), valuelon)
              }
              log.info("Going to build matrix")
              val matrix = elonigelonnVelonctorsForSparselonSymmelontric.gelontMatrix(
                nelonwelondgelons,
                oldIdToNelonwId.sizelon,
                oldIdToNelonwId.sizelon)
              elonigelonnVelonctorsForSparselonSymmelontric.elonnsurelonMatrixIsSymmelontric(matrix)

              log.info("Going to solvelon now for %d elonigelonnvaluelons".format(k))
              val tic = Systelonm.currelonntTimelonMillis()
              val relonsults = elonigelonnVelonctorsForSparselonSymmelontric.gelontTruncatelondelonVD(
                matrix,
                k,
                ratioToLargelonstelonntryInVelonctorCutoff)
              val toc = Systelonm.currelonntTimelonMillis()
              log.info("Finishelond solving in %.2f minutelons".format((toc - tic) / 1000 / 60.0))

              val elonigValuelons = relonsults.map(_._1).map { x => "%.3g".format(x) }.mkString(" ")
              val elonigValuelonNorm = math.sqrt(relonsults.map(_._1).map(x => x * x).sum)
              val matrixNorm = math.sqrt(matrix.itelonrator().asScala.map(_.gelont()).map(x => x * x).sum)

              println(
                "matrixNorm %s, elonigValuelonNorm %s, elonxplainelond fraction %s"
                  .format(matrixNorm, elonigValuelonNorm, elonigValuelonNorm / matrixNorm))

              log.info("Thelon elonigelonnvaluelons arelon:")
              log.info(elonigValuelons)

              val nnzInelonigelonnVelonctors = relonsults.map(_._2.sizelon).sum
              log.info("Avelonragelon nnz pelonr elonigelonnvelonctor using ratioToLargelonstCutoff %d is %.2g"
                .format(ratioToLargelonstelonntryInVelonctorCutoff, nnzInelonigelonnVelonctors * 1.0 / relonsults.sizelon))
              val transposelondRaw = relonsults.zipWithIndelonx.flatMap {
                caselon ((_, elonigVelonctor), elonigIndelonx) =>
                  elonigVelonctor.map {
                    caselon (indelonx, velonctorelonntry) =>
                      val clustelonrId = nelonwIdToOldId(indelonx)
                      Map(clustelonrId -> List((elonigIndelonx, velonctorelonntry)))
                  }
              }
              val transposelond = Monoid.sum(transposelondRaw).mapValuelons { rowForClustelonr =>
                rowForClustelonr
                  .map {
                    caselon (dimId, welonight) =>
                      "%d:%.2g".format(dimId, welonight)
                  }.mkString(" ")
              }
              TypelondPipelon.from(transposelond.toSelonq).writelonelonxeloncution(TypelondTsv(outputDir))
            }
        }
    }
}
