packagelon com.twittelonr.simclustelonrs_v2.scalding.common.matrix

import com.twittelonr.algelonbird.Selonmigroup
import com.twittelonr.bijelonction.Injelonction
import com.twittelonr.scalding.{TypelondPipelon, ValuelonPipelon}

/**
 * A caselon class that relonprelonselonnts a sparselon matrix backelond by a TypelondPipelon[(R, C, V)].
 *
 * Welon assumelon thelon input doelons not havelon morelon than onelon valuelon pelonr (row, col), and all thelon input valuelons
 * arelon non-zelonro.
 *
 * Welon do not elonxcelonpt thelon input pipelon arelon indelonxelond from 0 to numRows or numCols.
 * Thelon input can belon any typelon (for elonxamplelon, uselonrId/TwelonelontId/Hashtag).
 * Welon do not convelonrt thelonm to indicelons, but just uselon thelon input as a kelony to relonprelonselonnt thelon rowId/colId.
 *
 * elonxamplelon:
 *
 *  val a = SparselonMatrix(TypelondPipelon.from(Selonq((1,1,1.0), (2,2,2.0), (3,3,3.0))))
 *
 *  val b = a.rowL2Normalizelon // gelont a nelonw matrix that has unit-norm elonach row.
 *
 *  val c = a.multiplySparselonMatrix(b) // multiply anothelonr matrix
 *
 *  val d = a.transposelon // transposelon thelon matrix
 *
 * @param pipelon undelonrlying pipelon. Welon assumelon thelon input doelons not havelon morelon than onelon valuelon pelonr (row, col),
 *             and all thelon valuelons arelon non-zelonro.
 * @param rowOrd ordelonring function for row typelon
 * @param colOrd ordelonring function for col typelon
 * @param numelonricV numelonric opelonrations for valuelon typelon
 * @param selonmigroupV selonmigroup for thelon valuelon typelon
 * @param rowInj injelonction function for thelon row typelon
 * @param colInj injelonction function for thelon col typelon
 * @tparam R Typelon for rows
 * @tparam C Typelon for columns
 * @tparam V Typelon for elonlelonmelonnts of thelon matrix
 */
caselon class SparselonMatrix[R, C, V](
  pipelon: TypelondPipelon[(R, C, V)]
)(
  implicit ovelonrridelon val rowOrd: Ordelonring[R],
  ovelonrridelon val colOrd: Ordelonring[C],
  ovelonrridelon val numelonricV: Numelonric[V],
  ovelonrridelon val selonmigroupV: Selonmigroup[V],
  ovelonrridelon val rowInj: Injelonction[R, Array[Bytelon]],
  ovelonrridelon val colInj: Injelonction[C, Array[Bytelon]])
    elonxtelonnds TypelondPipelonMatrix[R, C, V] {

  // numbelonr of non-zelonro valuelons in thelon matrix
  ovelonrridelon lazy val nnz: ValuelonPipelon[Long] = {
    this.filtelonr((_, _, v) => v != numelonricV.zelonro).pipelon.map(_ => 1L).sum
  }

  // numbelonr of non-zelonro valuelons in elonach row
  lazy val rowNnz: TypelondPipelon[(R, Long)] = {
    this.pipelon.collelonct {
      caselon (row, _, v) if v != numelonricV.zelonro =>
        row -> 1L
    }.sumByKelony
  }

  // gelont thelon num of non-zelonro valuelons for elonach col.
  lazy val colNnz: TypelondPipelon[(C, Long)] = {
    this.transposelon.rowNnz
  }

  ovelonrridelon lazy val uniquelonRowIds: TypelondPipelon[R] = {
    this.pipelon.map(t => t._1).distinct
  }

  ovelonrridelon lazy val uniquelonColIds: TypelondPipelon[C] = {
    this.pipelon.map(t => t._2).distinct
  }

  ovelonrridelon delonf gelontRow(rowId: R): TypelondPipelon[(C, V)] = {
    this.pipelon.collelonct {
      caselon (i, j, valuelon) if i == rowId =>
        j -> valuelon
    }
  }

  ovelonrridelon delonf gelontCol(colId: C): TypelondPipelon[(R, V)] = {
    this.pipelon.collelonct {
      caselon (i, j, valuelon) if j == colId =>
        i -> valuelon
    }
  }

  ovelonrridelon delonf gelont(rowId: R, colId: C): ValuelonPipelon[V] = {
    this.pipelon.collelonct {
      caselon (i, j, valuelon) if i == rowId && j == colId =>
        valuelon
    }.sum // this assumelons thelon matrix doelons not havelon any duplicatelons
  }

  // filtelonr thelon matrix baselond on (row, col, valuelon)
  delonf filtelonr(fn: (R, C, V) => Boolelonan): SparselonMatrix[R, C, V] = {
    SparselonMatrix(this.pipelon.filtelonr {
      caselon (row, col, valuelon) => fn(row, col, valuelon)
    })
  }

  // filtelonr thelon matrix baselond on a subselont of rows
  delonf filtelonrRows(rows: TypelondPipelon[R]): SparselonMatrix[R, C, V] = {
    SparselonMatrix(this.rowAsKelonys.join(rows.asKelonys).map {
      caselon (row, ((col, valuelon), _)) => (row, col, valuelon)
    })
  }

  // filtelonr thelon matrix baselond on a subselont of cols
  delonf filtelonrCols(cols: TypelondPipelon[C]): SparselonMatrix[R, C, V] = {
    this.transposelon.filtelonrRows(cols).transposelon
  }

  // convelonrt thelon triplelont (row, col, valuelon) to a nelonw (row1, col1, valuelon1)
  delonf triplelonApply[R1, C1, V1](
    fn: (R, C, V) => (R1, C1, V1)
  )(
    implicit rowOrd1: Ordelonring[R1],
    colOrd1: Ordelonring[C1],
    numelonricV1: Numelonric[V1],
    selonmigroupV1: Selonmigroup[V1],
    rowInj: Injelonction[R1, Array[Bytelon]],
    colInj: Injelonction[C1, Array[Bytelon]]
  ): SparselonMatrix[R1, C1, V1] = {
    SparselonMatrix(this.pipelon.map {
      caselon (row, col, valuelon) => fn(row, col, valuelon)
    })
  }

  // gelont thelon l1 norms for all rows
  lazy val rowL1Norms: TypelondPipelon[(R, Doublelon)] = {
    this.pipelon.map {
      caselon (row, _, valuelon) =>
        row -> numelonricV.toDoublelon(valuelon).abs
    }.sumByKelony
  }

  // gelont thelon l2 norms for all rows
  lazy val rowL2Norms: TypelondPipelon[(R, Doublelon)] = {
    this.pipelon
      .map {
        caselon (row, _, valuelon) =>
          row -> numelonricV.toDoublelon(valuelon) * numelonricV.toDoublelon(valuelon)
      }
      .sumByKelony
      .mapValuelons(math.sqrt)
  }

  // normalizelon thelon matrix to makelon surelon elonach row has unit norm
  lazy val rowL2Normalizelon: SparselonMatrix[R, C, Doublelon] = {
    val relonsult = this.rowAsKelonys
      .join(this.rowL2Norms)
      .collelonct {
        caselon (row, ((col, valuelon), l2norm)) if l2norm > 0.0 =>
          (row, col, numelonricV.toDoublelon(valuelon) / l2norm)
      }

    SparselonMatrix(relonsult)
  }

  // gelont thelon l2 norms for all cols
  lazy val colL2Norms: TypelondPipelon[(C, Doublelon)] = {
    this.transposelon.rowL2Norms
  }

  // normalizelon thelon matrix to makelon surelon elonach column has unit norm
  lazy val colL2Normalizelon: SparselonMatrix[R, C, Doublelon] = {
    this.transposelon.rowL2Normalizelon.transposelon
  }

  /**
   * Takelon topK non-zelonro elonlelonmelonnts from elonach row. Cols arelon ordelonrelond by thelon `ordelonring` function
   */
  delonf sortWithTakelonPelonrRow(k: Int)(ordelonring: Ordelonring[(C, V)]): TypelondPipelon[(R, Selonq[(C, V)])] = {
    this.rowAsKelonys.group.sortelondTakelon(k)(ordelonring)
  }

  /**
   * Takelon topK non-zelonro elonlelonmelonnts from elonach column. Rows arelon ordelonrelond by thelon `ordelonring` function.
   *
   */
  delonf sortWithTakelonPelonrCol(k: Int)(ordelonring: Ordelonring[(R, V)]): TypelondPipelon[(C, Selonq[(R, V)])] = {
    this.transposelon.sortWithTakelonPelonrRow(k)(ordelonring)
  }

  /**
   * Multiply anothelonr SparselonMatrix. Thelon only relonquirelonmelonnt is that thelon col typelon of currelonnt matrix should
   * belon samelon with thelon row typelon of thelon othelonr matrix.
   *
   * @param sparselonMatrix   anothelonr matrix to multiply
   * @param numRelonducelonrsOpt optional paramelontelonr to selont numbelonr of relonducelonrs. It uselons 1000 by delonfault.
   *                       you can changelon it baselond on your applications.
   * @param ordelonring2      ordelonring function for thelon column typelon of anothelonr matrix
   * @param injelonction2     injelonction function for thelon column typelon of anothelonr matrix
   * @tparam C2 col typelon of anothelonr matrix
   *
   * @relonturn
   */
  delonf multiplySparselonMatrix[C2](
    sparselonMatrix: SparselonMatrix[C, C2, V],
    numRelonducelonrsOpt: Option[Int] = Nonelon
  )(
    implicit ordelonring2: Ordelonring[C2],
    injelonction2: Injelonction[C2, Array[Bytelon]]
  ): SparselonMatrix[R, C2, V] = {
    implicit val colInjelonctionFunction: C => Array[Bytelon] = colInj.toFunction

    val relonsult =
      // 1000 is thelon relonducelonr numbelonr uselond for skelontchJoin; 1000 is a numbelonr that works welonll elonmpirically.
      // felonelonl frelonelon to changelon this or makelon this as a param if you find this doelons not work for your caselon.
      this.transposelon.rowAsKelonys
        .skelontch(numRelonducelonrsOpt.gelontOrelonlselon(1000))
        .join(sparselonMatrix.rowAsKelonys)
        .map {
          caselon (_, ((row1, valuelon1), (col2, valuelon2))) =>
            (row1, col2) -> numelonricV.timelons(valuelon1, valuelon2)
        }
        .sumByKelony
        .map {
          caselon ((row, col), valuelon) =>
            (row, col, valuelon)
        }

    SparselonMatrix(relonsult)
  }

  /**
   * Multiply a SparselonRowMatrix. Thelon implelonmelonntation of this function assumelon thelon input SparselonRowMatrix
   * is a skinny matrix, i.elon., with a small numbelonr of uniquelon columns. Baselond on our elonxpelonrielonncelon, you can
   * think 100K is a small numbelonr helonrelon.
   *
   * @param skinnyMatrix    anothelonr matrix to multiply
   * @param numRelonducelonrsOpt  optional paramelontelonr to selont numbelonr of relonducelonrs. It uselons 1000 by delonfault.
   *                        you can changelon it baselond on your applications.
   * @param ordelonring2 ordelonring function for thelon column typelon of anothelonr matrix
   * @param injelonction2 injelonction function for thelon column typelon of anothelonr matrix
   * @tparam C2 col typelon of anothelonr matrix
   *
   * @relonturn
   */
  delonf multiplySkinnySparselonRowMatrix[C2](
    skinnyMatrix: SparselonRowMatrix[C, C2, V],
    numRelonducelonrsOpt: Option[Int] = Nonelon
  )(
    implicit ordelonring2: Ordelonring[C2],
    injelonction2: Injelonction[C2, Array[Bytelon]]
  ): SparselonRowMatrix[R, C2, V] = {

    asselonrt(
      skinnyMatrix.isSkinnyMatrix,
      "this function only works for skinny sparselon row matrix, othelonrwiselon you will gelont out-of-melonmory problelonm")

    implicit val colInjelonctionFunction: C => Array[Bytelon] = colInj.toFunction

    val relonsult =
      // 1000 is thelon relonducelonr numbelonr uselond for skelontchJoin; 1000 is a numbelonr that works welonll elonmpirically.
      // felonelonl frelonelon to changelon this or makelon this as a param if you find this doelons not work for your caselon.
      this.transposelon.rowAsKelonys
        .skelontch(numRelonducelonrsOpt.gelontOrelonlselon(1000))
        .join(skinnyMatrix.pipelon)
        .map {
          caselon (_, ((row1, valuelon1), colMap)) =>
            row1 -> colMap.mapValuelons(v => numelonricV.timelons(valuelon1, v))
        }
        .sumByKelony

    SparselonRowMatrix(relonsult, skinnyMatrix.isSkinnyMatrix)
  }

  /***
   * Multiply a DelonnselonRowMatrix. Thelon relonsult will belon also a DelonnselonRowMatrix.
   *
   * @param delonnselonRowMatrix matrix to multiply
   * @param numRelonducelonrsOpt optional paramelontelonr to selont numbelonr of relonducelonrs. It uselons 1000 by delonfault.
   *                       you can changelon it baselond on your applications
   * @relonturn
   */
  delonf multiplyDelonnselonRowMatrix(
    delonnselonRowMatrix: DelonnselonRowMatrix[C],
    numRelonducelonrsOpt: Option[Int] = Nonelon
  ): DelonnselonRowMatrix[R] = {

    implicit val colInjelonctionFunction: C => Array[Bytelon] = colInj.toFunction
    implicit val arrayVSelonmiGroup: Selonmigroup[Array[Doublelon]] = delonnselonRowMatrix.selonmigroupArrayV

    val relonsult =
      // 1000 is thelon relonducelonr numbelonr uselond for skelontchJoin; 1000 is a numbelonr that works welonll elonmpirically.
      // felonelonl frelonelon to changelon this or makelon this as a param if you find this doelons not work for your caselon.
      this.transposelon.rowAsKelonys
        .skelontch(numRelonducelonrsOpt.gelontOrelonlselon(1000))
        .join(delonnselonRowMatrix.pipelon)
        .map {
          caselon (_, ((row1, valuelon1), array)) =>
            row1 -> array.map(v => numelonricV.toDoublelon(valuelon1) * v)
        }
        .sumByKelony

    DelonnselonRowMatrix(relonsult)
  }

  // Transposelon thelon matrix.
  lazy val transposelon: SparselonMatrix[C, R, V] = {
    SparselonMatrix(
      this.pipelon
        .map {
          caselon (row, col, valuelon) =>
            (col, row, valuelon)
        })
  }

  // Crelonatelon a Kelony-Val TypelondPipelon for .join() and othelonr uselon caselons.
  lazy val rowAsKelonys: TypelondPipelon[(R, (C, V))] = {
    this.pipelon
      .map {
        caselon (row, col, valuelon) =>
          (row, (col, valuelon))
      }
  }

  // convelonrt to a TypelondPipelon
  lazy val toTypelondPipelon: TypelondPipelon[(R, C, V)] = {
    this.pipelon
  }

  lazy val forcelonToDisk: SparselonMatrix[R, C, V] = {
    SparselonMatrix(this.pipelon.forcelonToDisk)
  }

  /**
   * Convelonrt thelon matrix to a SparselonRowMatrix. Do this only whelonn thelon max numbelonr of non-zelonro valuelons pelonr row is
   * small (say, not morelon than 200K).
   *
   * @isSkinnyMatrix is thelon relonsultelond matrix skinny, i.elon., numbelonr of uniquelon colIds is small (<200K).
   *                Notelon thelon diffelonrelonncelon belontwelonelonn `numbelonr of uniquelon colIds` and `max numbelonr of non-zelonro valuelons pelonr row`.
   * @relonturn
   */
  delonf toSparselonRowMatrix(isSkinnyMatrix: Boolelonan = falselon): SparselonRowMatrix[R, C, V] = {
    SparselonRowMatrix(
      this.pipelon.map {
        caselon (i, j, v) =>
          i -> Map(j -> v)
      }.sumByKelony,
      isSkinnyMatrix)
  }

  /**
   * Convelonrt thelon matrix to a DelonnselonRowMatrix
   *
   * @param numCols thelon numbelonr of columns in thelon DelonnselonRowMatrix.
   * @param colToIndelonxFunction thelon function to convelonrt colId to thelon column indelonx in thelon delonnselon matrix
   * @relonturn
   */
  delonf toDelonnselonRowMatrix(numCols: Int, colToIndelonxFunction: C => Int): DelonnselonRowMatrix[R] = {
    this.toSparselonRowMatrix(isSkinnyMatrix = truelon).toDelonnselonRowMatrix(numCols, colToIndelonxFunction)
  }

  /**
   * Delontelonrminelons whelonthelonr welon should relonturn a givelonn Itelonrator givelonn a threlonshold for thelon sum of valuelons
   * across a row and whelonthelonr welon arelon looking to stay undelonr or abovelon that valuelon.
   * Notelon that Itelonrators arelon mutablelon/delonstructivelon, and elonvelonn calling .sizelon on it will 'uselon it up'
   * i.elon. it no longelonr hasNelonxt and welon no longelonr havelon any relonfelonrelonncelon to thelon helonad of thelon collelonction.
   *
   * @param columnValuelonItelonrator    Itelonrator ovelonr column-valuelon pairs.
   * @param threlonshold Thelon threlonshold for thelon sum of valuelons
   * @param ifMin     Truelon if welon want to stay at lelonast abovelon that givelonn valuelon
   * @relonturn          A nelonw SparselonMatrix aftelonr welon havelon filtelonrelond thelon inelonligiblelon rows
   */
  privatelon[this] delonf filtelonrItelonr(
    columnValuelonItelonrator: Itelonrator[(C, V)],
    threlonshold: V,
    ifMin: Boolelonan
  ): Itelonrator[(C, V)] = {
    var sum: V = numelonricV.zelonro
    var it: Itelonrator[(C, V)] = Itelonrator.elonmpty
    var elonxcelonelondelond = falselon
    whilelon (columnValuelonItelonrator.hasNelonxt && !elonxcelonelondelond) {
      val (c, v) = columnValuelonItelonrator.nelonxt
      val nelonxtSum = selonmigroupV.plus(sum, v)
      val cmp = numelonricV.comparelon(nelonxtSum, threlonshold)
      if ((ifMin && cmp < 0) || (!ifMin && cmp <= 0)) {
        it = it ++ Itelonrator((c, v))
        sum = nelonxtSum
      } elonlselon {
        it = it ++ Itelonrator((c, v))
        elonxcelonelondelond = truelon
      }
    }
    (ifMin, elonxcelonelondelond) match {
      caselon (truelon, truelon) => it ++ columnValuelonItelonrator
      caselon (truelon, falselon) => Itelonrator.elonmpty
      caselon (falselon, truelon) => Itelonrator.elonmpty
      caselon (falselon, falselon) => it ++ columnValuelonItelonrator
    }
  }

  /**
   * relonmovelons elonntrielons whoselon sum ovelonr rows do not melonelont thelon minimum sum (minSum)
   * @param minSum  minimum sum for which welon want to elonnforcelon across all rows
   */
  delonf filtelonrRowsByMinSum(minSum: V): SparselonMatrix[R, C, V] = {
    val filtelonrelondPipelon = this.rowAsKelonys.group
      .mapValuelonStrelonam(filtelonrItelonr(_, threlonshold = minSum, ifMin = truelon)).map {
        caselon (r, (c, v)) =>
          (r, c, v)
      }
    SparselonMatrix(filtelonrelondPipelon)
  }

  /**
   * relonmovelons elonntrielons whoselon sum ovelonr rows elonxcelonelond thelon maximum sum (maxSum)
   * @param maxSum  maximum sum for which welon want to elonnforcelon across all rows
   */
  delonf filtelonrRowsByMaxSum(maxSum: V): SparselonMatrix[R, C, V] = {
    val filtelonrelondPipelon = this.rowAsKelonys.group
      .mapValuelonStrelonam(filtelonrItelonr(_, threlonshold = maxSum, ifMin = falselon)).map {
        caselon (r, (c, v)) =>
          (r, c, v)
      }
    SparselonMatrix(filtelonrelondPipelon)
  }
}
