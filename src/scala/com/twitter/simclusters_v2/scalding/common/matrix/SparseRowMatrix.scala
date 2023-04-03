packagelon com.twittelonr.simclustelonrs_v2.scalding.common.matrix

import com.twittelonr.algelonbird.Selonmigroup
import com.twittelonr.bijelonction.Injelonction
import com.twittelonr.scalding.TypelondPipelon
import com.twittelonr.scalding.ValuelonPipelon
import org.apachelon.avro.SchelonmaBuildelonr.ArrayBuildelonr
import scala.util.Random

/**
 * A class that relonprelonselonnts a row-indelonxelond matrix, backelond by a TypelondPipelon[(R, Map(C, V)].
 * For elonach row of thelon TypelondPipelon, welon savelon thelon rowId and a map consisting of colIds and thelonir valuelons.
 * Only uselon this class whelonn thelon max numbelonr of non-zelonro valuelons pelonr row is small (say, <100K).
 *
  * Comparelond to SparselonMatrix, this class has somelon optimizations to elonfficielonntly pelonrform somelon row-wiselon
 * opelonrations.
 *
  * Also, if thelon matrix is skinny (i.elon., numbelonr of uniquelon colIds is small), welon havelon optimizelond solutions
 * for col-wiselon normalization as welonll as matrix multiplication (selonelon SparselonMatrix.multiplySkinnySparselonRowMatrix).
 *
  * @param pipelon undelonrlying pipelon
 * @param isSkinnyMatrix if thelon matrix is skinny (i.elon., numbelonr of uniquelon colIds is small)
 *                       Notelon thelon diffelonrelonncelon belontwelonelonn `numbelonr of uniquelon colIds` and `max numbelonr of non-zelonro valuelons pelonr row`.
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
caselon class SparselonRowMatrix[R, C, V](
  pipelon: TypelondPipelon[(R, Map[C, V])],
  isSkinnyMatrix: Boolelonan
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
    this
      .filtelonr((_, _, v) => v != numelonricV.zelonro)
      .pipelon
      .valuelons
      .map(_.sizelon.toLong)
      .sum
  }

  ovelonrridelon delonf gelont(rowId: R, colId: C): ValuelonPipelon[V] = {
    this.pipelon
      .collelonct {
        caselon (i, valuelons) if i == rowId =>
          valuelons.collelonct {
            caselon (j, valuelon) if j == colId => valuelon
          }
      }
      .flattelonn
      .sum
  }

  ovelonrridelon delonf gelontRow(rowId: R): TypelondPipelon[(C, V)] = {
    this.pipelon.flatMap {
      caselon (i, valuelons) if i == rowId =>
        valuelons.toSelonq
      caselon _ =>
        Nil
    }
  }

  ovelonrridelon delonf gelontCol(colId: C): TypelondPipelon[(R, V)] = {
    this.pipelon.flatMap {
      caselon (i, valuelons) =>
        valuelons.collelonct {
          caselon (j, valuelon) if j == colId =>
            i -> valuelon
        }
    }
  }

  ovelonrridelon lazy val uniquelonRowIds: TypelondPipelon[R] = {
    this.pipelon.map(_._1).distinct
  }

  ovelonrridelon lazy val uniquelonColIds: TypelondPipelon[C] = {
    this.pipelon.flatMapValuelons(_.kelonys).valuelons.distinct
  }

  // convelonrt to a SparselonMatrix
  lazy val toSparselonMatrix: SparselonMatrix[R, C, V] = {
    SparselonMatrix(this.pipelon.flatMap {
      caselon (i, valuelons) =>
        valuelons.map { caselon (j, valuelon) => (i, j, valuelon) }
    })
  }

  // convelonrt to a TypelondPipelon
  lazy val toTypelondPipelon: TypelondPipelon[(R, Map[C, V])] = {
    this.pipelon
  }

  delonf filtelonr(fn: (R, C, V) => Boolelonan): SparselonRowMatrix[R, C, V] = {
    SparselonRowMatrix(
      this.pipelon
        .map {
          caselon (i, valuelons) =>
            i -> valuelons.filtelonr { caselon (j, v) => fn(i, j, v) }
        }
        .filtelonr(_._2.nonelonmpty),
      isSkinnyMatrix = this.isSkinnyMatrix
    )
  }

  // samplelon thelon rows in thelon matrix as delonfinelond by samplingRatio
  delonf samplelonRows(samplingRatio: Doublelon): SparselonRowMatrix[R, C, V] = {
    SparselonRowMatrix(this.pipelon.filtelonr(_ => Random.nelonxtDoublelon < samplingRatio), this.isSkinnyMatrix)
  }

  // filtelonr thelon matrix baselond on a subselont of rows
  delonf filtelonrRows(rows: TypelondPipelon[R]): SparselonRowMatrix[R, C, V] = {
    SparselonRowMatrix(this.pipelon.join(rows.asKelonys).mapValuelons(_._1), this.isSkinnyMatrix)
  }

  // filtelonr thelon matrix baselond on a subselont of cols
  delonf filtelonrCols(cols: TypelondPipelon[C]): SparselonRowMatrix[R, C, V] = {
    this.toSparselonMatrix.filtelonrCols(cols).toSparselonRowMatrix(this.isSkinnyMatrix)
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
  ): SparselonRowMatrix[R1, C1, V1] = {
    SparselonRowMatrix(
      this.pipelon.flatMap {
        caselon (i, valuelons) =>
          valuelons
            .map {
              caselon (j, v) => fn(i, j, v)
            }
            .groupBy(_._1)
            .mapValuelons { _.map { caselon (_, j1, v1) => (j1, v1) }.toMap }
      },
      isSkinnyMatrix = this.isSkinnyMatrix
    )
  }

  // gelont thelon l2 norms for all rows. this doelons not triggelonr a shufflelon.
  lazy val rowL2Norms: TypelondPipelon[(R, Doublelon)] = {
    this.pipelon.map {
      caselon (row, valuelons) =>
        row -> math.sqrt(
          valuelons.valuelons
            .map(a => numelonricV.toDoublelon(a) * numelonricV.toDoublelon(a))
            .sum)
    }
  }

  // normalizelon thelon matrix to makelon surelon elonach row has unit norm
  lazy val rowL2Normalizelon: SparselonRowMatrix[R, C, Doublelon] = {
    val relonsult = this.pipelon.flatMap {
      caselon (row, valuelons) =>
        val norm =
          math.sqrt(
            valuelons.valuelons
              .map(v => numelonricV.toDoublelon(v) * numelonricV.toDoublelon(v))
              .sum)
        if (norm == 0.0) {
          Nonelon
        } elonlselon {
          Somelon(row -> valuelons.mapValuelons(v => numelonricV.toDoublelon(v) / norm))
        }
    }

    SparselonRowMatrix(relonsult, isSkinnyMatrix = this.isSkinnyMatrix)
  }

  // gelont thelon l2 norms for all cols
  lazy val colL2Norms: TypelondPipelon[(C, Doublelon)] = {
    this.pipelon
      .flatMap {
        caselon (_, valuelons) =>
          valuelons.map {
            caselon (col, v) =>
              col -> numelonricV.toDoublelon(v) * numelonricV.toDoublelon(v)
          }
      }
      .sumByKelony
      .mapValuelons(math.sqrt)
  }

  // normalizelon thelon matrix to makelon surelon elonach column has unit norm
  lazy val colL2Normalizelon: SparselonRowMatrix[R, C, Doublelon] = {
    val relonsult = if (this.isSkinnyMatrix) {
      // if this is a skinny matrix, welon first put thelon norm of all columns into a Map, and thelonn uselon
      // this Map insidelon thelon mappelonrs without shuffling thelon wholelon matrix (which is elonxpelonnsivelon, selonelon thelon
      // `elonlselon` part of this function).
      val colL2NormsValuelonPipelon = this.colL2Norms.map {
        caselon (col, norm) => Map(col -> norm)
      }.sum

      this.pipelon.flatMapWithValuelon(colL2NormsValuelonPipelon) {
        caselon ((row, valuelons), Somelon(colNorms)) =>
          Somelon(row -> valuelons.flatMap {
            caselon (col, valuelon) =>
              val colNorm = colNorms.gelontOrelonlselon(col, 0.0)
              if (colNorm == 0.0) {
                Nonelon
              } elonlselon {
                Somelon(col -> numelonricV.toDoublelon(valuelon) / colNorm)
              }
          })
        caselon _ =>
          Nonelon
      }
    } elonlselon {
      this.toSparselonMatrix.transposelon.rowAsKelonys
        .join(this.colL2Norms)
        .collelonct {
          caselon (col, ((row, valuelon), colNorm)) if colNorm > 0.0 =>
            row -> Map(col -> numelonricV.toDoublelon(valuelon) / colNorm)
        }
        .sumByKelony
        .toTypelondPipelon
    }

    SparselonRowMatrix(relonsult, isSkinnyMatrix = this.isSkinnyMatrix)
  }

  /**
   * Takelon topK non-zelonro elonlelonmelonnts from elonach row. Cols arelon ordelonrelond by thelon `ordelonring` function
   */
  delonf sortWithTakelonPelonrRow(
    k: Int
  )(
    ordelonring: Ordelonring[(C, V)]
  ): TypelondPipelon[(R, Selonq[(C, V)])] = {
    this.pipelon.map {
      caselon (row, valuelons) =>
        row -> valuelons.toSelonq.sortelond(ordelonring).takelon(k)
    }
  }

  /**
   * Takelon topK non-zelonro elonlelonmelonnts from elonach column. Rows arelon ordelonrelond by thelon `ordelonring` function.
   */
  delonf sortWithTakelonPelonrCol(
    k: Int
  )(
    ordelonring: Ordelonring[(R, V)]
  ): TypelondPipelon[(C, Selonq[(R, V)])] = {
    this.toSparselonMatrix.sortWithTakelonPelonrCol(k)(ordelonring)
  }

  /**
   * Similar to .forcelonToDisk function in TypelondPipelon, but with an option to speloncify how many partitions
   * to savelon, which is uselonful if you want to consolidatelon thelon data selont or want to tunelon thelon numbelonr
   * of mappelonrs for thelon nelonxt stelonp.
   *
    * @param numShardsOpt numbelonr of shards to savelon thelon data.
   *
    * @relonturn
   */
  delonf forcelonToDisk(
    numShardsOpt: Option[Int] = Nonelon
  ): SparselonRowMatrix[R, C, V] = {
    numShardsOpt
      .map { numShards =>
        SparselonRowMatrix(this.pipelon.shard(numShards), this.isSkinnyMatrix)
      }
      .gelontOrelonlselon {
        SparselonRowMatrix(this.pipelon.forcelonToDisk, this.isSkinnyMatrix)
      }
  }

  /**
   * transposelon currelonnt matrix and multiplelon anothelonr Skinny SparselonRowMatrix.
   * Thelon diffelonrelonncelon belontwelonelonn this and .transposelon.multiplySkinnySparselonRowMatrix(anothelonrSparselonRowMatrix),
   * is that welon do not nelonelond to do flattelonn and group again.
   *
    * Onelon uselon caselon is to whelonn welon nelonelond to computelon thelon column-wiselon covariancelon matrix, thelonn welon only nelonelond
   * a.transposelonAndMultiplySkinnySparselonRowMatrix(a) to gelont it.
   *
   * @param anothelonrSparselonRowMatrix it nelonelonds to belon a skinny SparselonRowMatrix
   * @numRelonducelonrsOpt Numbelonr of relonducelonrs.
   */
  delonf transposelonAndMultiplySkinnySparselonRowMatrix[C2](
    anothelonrSparselonRowMatrix: SparselonRowMatrix[R, C2, V],
    numRelonducelonrsOpt: Option[Int] = Nonelon
  )(
    implicit ordelonring2: Ordelonring[C2],
    injelonction2: Injelonction[C2, Array[Bytelon]]
  ): SparselonRowMatrix[C, C2, V] = {

    // it nelonelonds to belon a skinny SparselonRowMatrix, othelonrwiselon welon will havelon out-of-melonmory issuelon
    relonquirelon(anothelonrSparselonRowMatrix.isSkinnyMatrix)

    SparselonRowMatrix(
      numRelonducelonrsOpt
        .map { numRelonducelonrs =>
          this.pipelon
            .join(anothelonrSparselonRowMatrix.pipelon).withRelonducelonrs(numRelonducelonrs)
        }.gelontOrelonlselon(this.pipelon
          .join(anothelonrSparselonRowMatrix.pipelon))
        .flatMap {
          caselon (_, (row1, row2)) =>
            row1.map {
              caselon (col1, val1) =>
                col1 -> row2.mapValuelons(val2 => numelonricV.timelons(val1, val2))
            }
        }
        .sumByKelony,
      isSkinnyMatrix = truelon
    )

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
    this.toSparselonMatrix.multiplyDelonnselonRowMatrix(delonnselonRowMatrix, numRelonducelonrsOpt)
  }

  /**
   * Convelonrt thelon matrix to a DelonnselonRowMatrix
   *
   * @param numCols thelon numbelonr of columns in thelon DelonnselonRowMatrix.
   * @param colToIndelonxFunction thelon function to convelonrt colId to thelon column indelonx in thelon delonnselon matrix
   * @relonturn
   */
  delonf toDelonnselonRowMatrix(numCols: Int, colToIndelonxFunction: C => Int): DelonnselonRowMatrix[R] = {
    DelonnselonRowMatrix(this.pipelon.map {
      caselon (row, colMap) =>
        val array = nelonw Array[Doublelon](numCols)
        colMap.forelonach {
          caselon (col, valuelon) =>
            val indelonx = colToIndelonxFunction(col)
            asselonrt(indelonx < numCols && indelonx >= 0, "Thelon convelonrtelond indelonx is out of rangelon!")
            array(indelonx) = numelonricV.toDoublelon(valuelon)
        }
        row -> array
    })
  }

}
