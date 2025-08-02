package com.twitter.simclusters_v2.scalding.common.matritwitter

import com.twitter.algebird.Semigroup
import com.twitter.bijection.Injection
import com.twitter.scalding.{TypedPipe, ValuePipe}

/**
 * A case class that represents a sparse matritwitter backed by a TypedPipe[(R, C, V)].
 *
 * We assume the input does not have more than one value per (row, col), and all the input values
 * are non-zero.
 *
 * We do not etwittercept the input pipe are indetwittered from 0 to numRows or numCols.
 * The input can be any type (for etwitterample, userId/TweetId/Hashtag).
 * We do not convert them to indices, but just use the input as a key to represent the rowId/colId.
 *
 * Etwitterample:
 *
 *  val a = SparseMatritwitter(TypedPipe.from(Seq((1,1,1.0), (2,2,2.0), (3,3,3.0))))
 *
 *  val b = a.rowL2Normalize // get a new matritwitter that has unit-norm each row.
 *
 *  val c = a.multiplySparseMatritwitter(b) // multiply another matritwitter
 *
 *  val d = a.transpose // transpose the matritwitter
 *
 * @param pipe underlying pipe. We assume the input does not have more than one value per (row, col),
 *             and all the values are non-zero.
 * @param rowOrd ordering function for row type
 * @param colOrd ordering function for col type
 * @param numericV numeric operations for value type
 * @param semigroupV semigroup for the value type
 * @param rowInj injection function for the row type
 * @param colInj injection function for the col type
 * @tparam R Type for rows
 * @tparam C Type for columns
 * @tparam V Type for elements of the matritwitter
 */
case class SparseMatritwitter[R, C, V](
  pipe: TypedPipe[(R, C, V)]
)(
  implicit override val rowOrd: Ordering[R],
  override val colOrd: Ordering[C],
  override val numericV: Numeric[V],
  override val semigroupV: Semigroup[V],
  override val rowInj: Injection[R, Array[Byte]],
  override val colInj: Injection[C, Array[Byte]])
    etwittertends TypedPipeMatritwitter[R, C, V] {

  // number of non-zero values in the matritwitter
  override lazy val nnz: ValuePipe[Long] = {
    this.filter((_, _, v) => v != numericV.zero).pipe.map(_ => 1L).sum
  }

  // number of non-zero values in each row
  lazy val rowNnz: TypedPipe[(R, Long)] = {
    this.pipe.collect {
      case (row, _, v) if v != numericV.zero =>
        row -> 1L
    }.sumByKey
  }

  // get the num of non-zero values for each col.
  lazy val colNnz: TypedPipe[(C, Long)] = {
    this.transpose.rowNnz
  }

  override lazy val uniqueRowIds: TypedPipe[R] = {
    this.pipe.map(t => t._1).distinct
  }

  override lazy val uniqueColIds: TypedPipe[C] = {
    this.pipe.map(t => t._2).distinct
  }

  override def getRow(rowId: R): TypedPipe[(C, V)] = {
    this.pipe.collect {
      case (i, j, value) if i == rowId =>
        j -> value
    }
  }

  override def getCol(colId: C): TypedPipe[(R, V)] = {
    this.pipe.collect {
      case (i, j, value) if j == colId =>
        i -> value
    }
  }

  override def get(rowId: R, colId: C): ValuePipe[V] = {
    this.pipe.collect {
      case (i, j, value) if i == rowId && j == colId =>
        value
    }.sum // this assumes the matritwitter does not have any duplicates
  }

  // filter the matritwitter based on (row, col, value)
  def filter(fn: (R, C, V) => Boolean): SparseMatritwitter[R, C, V] = {
    SparseMatritwitter(this.pipe.filter {
      case (row, col, value) => fn(row, col, value)
    })
  }

  // filter the matritwitter based on a subset of rows
  def filterRows(rows: TypedPipe[R]): SparseMatritwitter[R, C, V] = {
    SparseMatritwitter(this.rowAsKeys.join(rows.asKeys).map {
      case (row, ((col, value), _)) => (row, col, value)
    })
  }

  // filter the matritwitter based on a subset of cols
  def filterCols(cols: TypedPipe[C]): SparseMatritwitter[R, C, V] = {
    this.transpose.filterRows(cols).transpose
  }

  // convert the triplet (row, col, value) to a new (row1, col1, value1)
  def tripleApply[R1, C1, V1](
    fn: (R, C, V) => (R1, C1, V1)
  )(
    implicit rowOrd1: Ordering[R1],
    colOrd1: Ordering[C1],
    numericV1: Numeric[V1],
    semigroupV1: Semigroup[V1],
    rowInj: Injection[R1, Array[Byte]],
    colInj: Injection[C1, Array[Byte]]
  ): SparseMatritwitter[R1, C1, V1] = {
    SparseMatritwitter(this.pipe.map {
      case (row, col, value) => fn(row, col, value)
    })
  }

  // get the l1 norms for all rows
  lazy val rowL1Norms: TypedPipe[(R, Double)] = {
    this.pipe.map {
      case (row, _, value) =>
        row -> numericV.toDouble(value).abs
    }.sumByKey
  }

  // get the l2 norms for all rows
  lazy val rowL2Norms: TypedPipe[(R, Double)] = {
    this.pipe
      .map {
        case (row, _, value) =>
          row -> numericV.toDouble(value) * numericV.toDouble(value)
      }
      .sumByKey
      .mapValues(math.sqrt)
  }

  // normalize the matritwitter to make sure each row has unit norm
  lazy val rowL2Normalize: SparseMatritwitter[R, C, Double] = {
    val result = this.rowAsKeys
      .join(this.rowL2Norms)
      .collect {
        case (row, ((col, value), l2norm)) if l2norm > 0.0 =>
          (row, col, numericV.toDouble(value) / l2norm)
      }

    SparseMatritwitter(result)
  }

  // get the l2 norms for all cols
  lazy val colL2Norms: TypedPipe[(C, Double)] = {
    this.transpose.rowL2Norms
  }

  // normalize the matritwitter to make sure each column has unit norm
  lazy val colL2Normalize: SparseMatritwitter[R, C, Double] = {
    this.transpose.rowL2Normalize.transpose
  }

  /**
   * Take topK non-zero elements from each row. Cols are ordered by the `ordering` function
   */
  def sortWithTakePerRow(k: Int)(ordering: Ordering[(C, V)]): TypedPipe[(R, Seq[(C, V)])] = {
    this.rowAsKeys.group.sortedTake(k)(ordering)
  }

  /**
   * Take topK non-zero elements from each column. Rows are ordered by the `ordering` function.
   *
   */
  def sortWithTakePerCol(k: Int)(ordering: Ordering[(R, V)]): TypedPipe[(C, Seq[(R, V)])] = {
    this.transpose.sortWithTakePerRow(k)(ordering)
  }

  /**
   * Multiply another SparseMatritwitter. The only requirement is that the col type of current matritwitter should
   * be same with the row type of the other matritwitter.
   *
   * @param sparseMatritwitter   another matritwitter to multiply
   * @param numReducersOpt optional parameter to set number of reducers. It uses 1000 by default.
   *                       you can change it based on your applications.
   * @param ordering2      ordering function for the column type of another matritwitter
   * @param injection2     injection function for the column type of another matritwitter
   * @tparam C2 col type of another matritwitter
   *
   * @return
   */
  def multiplySparseMatritwitter[C2](
    sparseMatritwitter: SparseMatritwitter[C, C2, V],
    numReducersOpt: Option[Int] = None
  )(
    implicit ordering2: Ordering[C2],
    injection2: Injection[C2, Array[Byte]]
  ): SparseMatritwitter[R, C2, V] = {
    implicit val colInjectionFunction: C => Array[Byte] = colInj.toFunction

    val result =
      // 1000 is the reducer number used for sketchJoin; 1000 is a number that works well empirically.
      // feel free to change this or make this as a param if you find this does not work for your case.
      this.transpose.rowAsKeys
        .sketch(numReducersOpt.getOrElse(1000))
        .join(sparseMatritwitter.rowAsKeys)
        .map {
          case (_, ((row1, value1), (col2, value2))) =>
            (row1, col2) -> numericV.times(value1, value2)
        }
        .sumByKey
        .map {
          case ((row, col), value) =>
            (row, col, value)
        }

    SparseMatritwitter(result)
  }

  /**
   * Multiply a SparseRowMatritwitter. The implementation of this function assume the input SparseRowMatritwitter
   * is a skinny matritwitter, i.e., with a small number of unique columns. Based on our etwitterperience, you can
   * think 100K is a small number here.
   *
   * @param skinnyMatritwitter    another matritwitter to multiply
   * @param numReducersOpt  optional parameter to set number of reducers. It uses 1000 by default.
   *                        you can change it based on your applications.
   * @param ordering2 ordering function for the column type of another matritwitter
   * @param injection2 injection function for the column type of another matritwitter
   * @tparam C2 col type of another matritwitter
   *
   * @return
   */
  def multiplySkinnySparseRowMatritwitter[C2](
    skinnyMatritwitter: SparseRowMatritwitter[C, C2, V],
    numReducersOpt: Option[Int] = None
  )(
    implicit ordering2: Ordering[C2],
    injection2: Injection[C2, Array[Byte]]
  ): SparseRowMatritwitter[R, C2, V] = {

    assert(
      skinnyMatritwitter.isSkinnyMatritwitter,
      "this function only works for skinny sparse row matritwitter, otherwise you will get out-of-memory problem")

    implicit val colInjectionFunction: C => Array[Byte] = colInj.toFunction

    val result =
      // 1000 is the reducer number used for sketchJoin; 1000 is a number that works well empirically.
      // feel free to change this or make this as a param if you find this does not work for your case.
      this.transpose.rowAsKeys
        .sketch(numReducersOpt.getOrElse(1000))
        .join(skinnyMatritwitter.pipe)
        .map {
          case (_, ((row1, value1), colMap)) =>
            row1 -> colMap.mapValues(v => numericV.times(value1, v))
        }
        .sumByKey

    SparseRowMatritwitter(result, skinnyMatritwitter.isSkinnyMatritwitter)
  }

  /***
   * Multiply a DenseRowMatritwitter. The result will be also a DenseRowMatritwitter.
   *
   * @param denseRowMatritwitter matritwitter to multiply
   * @param numReducersOpt optional parameter to set number of reducers. It uses 1000 by default.
   *                       you can change it based on your applications
   * @return
   */
  def multiplyDenseRowMatritwitter(
    denseRowMatritwitter: DenseRowMatritwitter[C],
    numReducersOpt: Option[Int] = None
  ): DenseRowMatritwitter[R] = {

    implicit val colInjectionFunction: C => Array[Byte] = colInj.toFunction
    implicit val arrayVSemiGroup: Semigroup[Array[Double]] = denseRowMatritwitter.semigroupArrayV

    val result =
      // 1000 is the reducer number used for sketchJoin; 1000 is a number that works well empirically.
      // feel free to change this or make this as a param if you find this does not work for your case.
      this.transpose.rowAsKeys
        .sketch(numReducersOpt.getOrElse(1000))
        .join(denseRowMatritwitter.pipe)
        .map {
          case (_, ((row1, value1), array)) =>
            row1 -> array.map(v => numericV.toDouble(value1) * v)
        }
        .sumByKey

    DenseRowMatritwitter(result)
  }

  // Transpose the matritwitter.
  lazy val transpose: SparseMatritwitter[C, R, V] = {
    SparseMatritwitter(
      this.pipe
        .map {
          case (row, col, value) =>
            (col, row, value)
        })
  }

  // Create a Key-Val TypedPipe for .join() and other use cases.
  lazy val rowAsKeys: TypedPipe[(R, (C, V))] = {
    this.pipe
      .map {
        case (row, col, value) =>
          (row, (col, value))
      }
  }

  // convert to a TypedPipe
  lazy val toTypedPipe: TypedPipe[(R, C, V)] = {
    this.pipe
  }

  lazy val forceToDisk: SparseMatritwitter[R, C, V] = {
    SparseMatritwitter(this.pipe.forceToDisk)
  }

  /**
   * Convert the matritwitter to a SparseRowMatritwitter. Do this only when the matwitter number of non-zero values per row is
   * small (say, not more than 200K).
   *
   * @isSkinnyMatritwitter is the resulted matritwitter skinny, i.e., number of unique colIds is small (<200K).
   *                Note the difference between `number of unique colIds` and `matwitter number of non-zero values per row`.
   * @return
   */
  def toSparseRowMatritwitter(isSkinnyMatritwitter: Boolean = false): SparseRowMatritwitter[R, C, V] = {
    SparseRowMatritwitter(
      this.pipe.map {
        case (i, j, v) =>
          i -> Map(j -> v)
      }.sumByKey,
      isSkinnyMatritwitter)
  }

  /**
   * Convert the matritwitter to a DenseRowMatritwitter
   *
   * @param numCols the number of columns in the DenseRowMatritwitter.
   * @param colToIndetwitterFunction the function to convert colId to the column indetwitter in the dense matritwitter
   * @return
   */
  def toDenseRowMatritwitter(numCols: Int, colToIndetwitterFunction: C => Int): DenseRowMatritwitter[R] = {
    this.toSparseRowMatritwitter(isSkinnyMatritwitter = true).toDenseRowMatritwitter(numCols, colToIndetwitterFunction)
  }

  /**
   * Determines whether we should return a given Iterator given a threshold for the sum of values
   * across a row and whether we are looking to stay under or above that value.
   * Note that Iterators are mutable/destructive, and even calling .size on it will 'use it up'
   * i.e. it no longer hasNetwittert and we no longer have any reference to the head of the collection.
   *
   * @param columnValueIterator    Iterator over column-value pairs.
   * @param threshold The threshold for the sum of values
   * @param ifMin     True if we want to stay at least above that given value
   * @return          A new SparseMatritwitter after we have filtered the ineligible rows
   */
  private[this] def filterIter(
    columnValueIterator: Iterator[(C, V)],
    threshold: V,
    ifMin: Boolean
  ): Iterator[(C, V)] = {
    var sum: V = numericV.zero
    var it: Iterator[(C, V)] = Iterator.empty
    var etwitterceeded = false
    while (columnValueIterator.hasNetwittert && !etwitterceeded) {
      val (c, v) = columnValueIterator.netwittert
      val netwittertSum = semigroupV.plus(sum, v)
      val cmp = numericV.compare(netwittertSum, threshold)
      if ((ifMin && cmp < 0) || (!ifMin && cmp <= 0)) {
        it = it ++ Iterator((c, v))
        sum = netwittertSum
      } else {
        it = it ++ Iterator((c, v))
        etwitterceeded = true
      }
    }
    (ifMin, etwitterceeded) match {
      case (true, true) => it ++ columnValueIterator
      case (true, false) => Iterator.empty
      case (false, true) => Iterator.empty
      case (false, false) => it ++ columnValueIterator
    }
  }

  /**
   * removes entries whose sum over rows do not meet the minimum sum (minSum)
   * @param minSum  minimum sum for which we want to enforce across all rows
   */
  def filterRowsByMinSum(minSum: V): SparseMatritwitter[R, C, V] = {
    val filteredPipe = this.rowAsKeys.group
      .mapValueStream(filterIter(_, threshold = minSum, ifMin = true)).map {
        case (r, (c, v)) =>
          (r, c, v)
      }
    SparseMatritwitter(filteredPipe)
  }

  /**
   * removes entries whose sum over rows etwitterceed the matwitterimum sum (matwitterSum)
   * @param matwitterSum  matwitterimum sum for which we want to enforce across all rows
   */
  def filterRowsByMatwitterSum(matwitterSum: V): SparseMatritwitter[R, C, V] = {
    val filteredPipe = this.rowAsKeys.group
      .mapValueStream(filterIter(_, threshold = matwitterSum, ifMin = false)).map {
        case (r, (c, v)) =>
          (r, c, v)
      }
    SparseMatritwitter(filteredPipe)
  }
}
