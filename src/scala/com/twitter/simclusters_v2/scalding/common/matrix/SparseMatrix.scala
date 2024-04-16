package com.tw ter.s mclusters_v2.scald ng.common.matr x

 mport com.tw ter.algeb rd.Sem group
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.scald ng.{TypedP pe, ValueP pe}

/**
 * A case class that represents a sparse matr x backed by a TypedP pe[(R, C, V)].
 *
 *   assu  t   nput does not have more than one value per (row, col), and all t   nput values
 * are non-zero.
 *
 *   do not except t   nput p pe are  ndexed from 0 to numRows or numCols.
 * T   nput can be any type (for example, user d/T et d/Hashtag).
 *   do not convert t m to  nd ces, but just use t   nput as a key to represent t  row d/col d.
 *
 * Example:
 *
 *  val a = SparseMatr x(TypedP pe.from(Seq((1,1,1.0), (2,2,2.0), (3,3,3.0))))
 *
 *  val b = a.rowL2Normal ze // get a new matr x that has un -norm each row.
 *
 *  val c = a.mult plySparseMatr x(b) // mult ply anot r matr x
 *
 *  val d = a.transpose // transpose t  matr x
 *
 * @param p pe underly ng p pe.   assu  t   nput does not have more than one value per (row, col),
 *             and all t  values are non-zero.
 * @param rowOrd order ng funct on for row type
 * @param colOrd order ng funct on for col type
 * @param nu r cV nu r c operat ons for value type
 * @param sem groupV sem group for t  value type
 * @param row nj  nject on funct on for t  row type
 * @param col nj  nject on funct on for t  col type
 * @tparam R Type for rows
 * @tparam C Type for columns
 * @tparam V Type for ele nts of t  matr x
 */
case class SparseMatr x[R, C, V](
  p pe: TypedP pe[(R, C, V)]
)(
   mpl c  overr de val rowOrd: Order ng[R],
  overr de val colOrd: Order ng[C],
  overr de val nu r cV: Nu r c[V],
  overr de val sem groupV: Sem group[V],
  overr de val row nj:  nject on[R, Array[Byte]],
  overr de val col nj:  nject on[C, Array[Byte]])
    extends TypedP peMatr x[R, C, V] {

  // number of non-zero values  n t  matr x
  overr de lazy val nnz: ValueP pe[Long] = {
    t .f lter((_, _, v) => v != nu r cV.zero).p pe.map(_ => 1L).sum
  }

  // number of non-zero values  n each row
  lazy val rowNnz: TypedP pe[(R, Long)] = {
    t .p pe.collect {
      case (row, _, v)  f v != nu r cV.zero =>
        row -> 1L
    }.sumByKey
  }

  // get t  num of non-zero values for each col.
  lazy val colNnz: TypedP pe[(C, Long)] = {
    t .transpose.rowNnz
  }

  overr de lazy val un queRow ds: TypedP pe[R] = {
    t .p pe.map(t => t._1).d st nct
  }

  overr de lazy val un queCol ds: TypedP pe[C] = {
    t .p pe.map(t => t._2).d st nct
  }

  overr de def getRow(row d: R): TypedP pe[(C, V)] = {
    t .p pe.collect {
      case ( , j, value)  f   == row d =>
        j -> value
    }
  }

  overr de def getCol(col d: C): TypedP pe[(R, V)] = {
    t .p pe.collect {
      case ( , j, value)  f j == col d =>
          -> value
    }
  }

  overr de def get(row d: R, col d: C): ValueP pe[V] = {
    t .p pe.collect {
      case ( , j, value)  f   == row d && j == col d =>
        value
    }.sum // t  assu s t  matr x does not have any dupl cates
  }

  // f lter t  matr x based on (row, col, value)
  def f lter(fn: (R, C, V) => Boolean): SparseMatr x[R, C, V] = {
    SparseMatr x(t .p pe.f lter {
      case (row, col, value) => fn(row, col, value)
    })
  }

  // f lter t  matr x based on a subset of rows
  def f lterRows(rows: TypedP pe[R]): SparseMatr x[R, C, V] = {
    SparseMatr x(t .rowAsKeys.jo n(rows.asKeys).map {
      case (row, ((col, value), _)) => (row, col, value)
    })
  }

  // f lter t  matr x based on a subset of cols
  def f lterCols(cols: TypedP pe[C]): SparseMatr x[R, C, V] = {
    t .transpose.f lterRows(cols).transpose
  }

  // convert t  tr plet (row, col, value) to a new (row1, col1, value1)
  def tr pleApply[R1, C1, V1](
    fn: (R, C, V) => (R1, C1, V1)
  )(
     mpl c  rowOrd1: Order ng[R1],
    colOrd1: Order ng[C1],
    nu r cV1: Nu r c[V1],
    sem groupV1: Sem group[V1],
    row nj:  nject on[R1, Array[Byte]],
    col nj:  nject on[C1, Array[Byte]]
  ): SparseMatr x[R1, C1, V1] = {
    SparseMatr x(t .p pe.map {
      case (row, col, value) => fn(row, col, value)
    })
  }

  // get t  l1 norms for all rows
  lazy val rowL1Norms: TypedP pe[(R, Double)] = {
    t .p pe.map {
      case (row, _, value) =>
        row -> nu r cV.toDouble(value).abs
    }.sumByKey
  }

  // get t  l2 norms for all rows
  lazy val rowL2Norms: TypedP pe[(R, Double)] = {
    t .p pe
      .map {
        case (row, _, value) =>
          row -> nu r cV.toDouble(value) * nu r cV.toDouble(value)
      }
      .sumByKey
      .mapValues(math.sqrt)
  }

  // normal ze t  matr x to make sure each row has un  norm
  lazy val rowL2Normal ze: SparseMatr x[R, C, Double] = {
    val result = t .rowAsKeys
      .jo n(t .rowL2Norms)
      .collect {
        case (row, ((col, value), l2norm))  f l2norm > 0.0 =>
          (row, col, nu r cV.toDouble(value) / l2norm)
      }

    SparseMatr x(result)
  }

  // get t  l2 norms for all cols
  lazy val colL2Norms: TypedP pe[(C, Double)] = {
    t .transpose.rowL2Norms
  }

  // normal ze t  matr x to make sure each column has un  norm
  lazy val colL2Normal ze: SparseMatr x[R, C, Double] = {
    t .transpose.rowL2Normal ze.transpose
  }

  /**
   * Take topK non-zero ele nts from each row. Cols are ordered by t  `order ng` funct on
   */
  def sortW hTakePerRow(k:  nt)(order ng: Order ng[(C, V)]): TypedP pe[(R, Seq[(C, V)])] = {
    t .rowAsKeys.group.sortedTake(k)(order ng)
  }

  /**
   * Take topK non-zero ele nts from each column. Rows are ordered by t  `order ng` funct on.
   *
   */
  def sortW hTakePerCol(k:  nt)(order ng: Order ng[(R, V)]): TypedP pe[(C, Seq[(R, V)])] = {
    t .transpose.sortW hTakePerRow(k)(order ng)
  }

  /**
   * Mult ply anot r SparseMatr x. T  only requ re nt  s that t  col type of current matr x should
   * be sa  w h t  row type of t  ot r matr x.
   *
   * @param sparseMatr x   anot r matr x to mult ply
   * @param numReducersOpt opt onal para ter to set number of reducers.   uses 1000 by default.
   *                         can change   based on y  appl cat ons.
   * @param order ng2      order ng funct on for t  column type of anot r matr x
   * @param  nject on2      nject on funct on for t  column type of anot r matr x
   * @tparam C2 col type of anot r matr x
   *
   * @return
   */
  def mult plySparseMatr x[C2](
    sparseMatr x: SparseMatr x[C, C2, V],
    numReducersOpt: Opt on[ nt] = None
  )(
     mpl c  order ng2: Order ng[C2],
     nject on2:  nject on[C2, Array[Byte]]
  ): SparseMatr x[R, C2, V] = {
     mpl c  val col nject onFunct on: C => Array[Byte] = col nj.toFunct on

    val result =
      // 1000  s t  reducer number used for sketchJo n; 1000  s a number that works  ll emp r cally.
      // feel free to change t  or make t  as a param  f   f nd t  does not work for y  case.
      t .transpose.rowAsKeys
        .sketch(numReducersOpt.getOrElse(1000))
        .jo n(sparseMatr x.rowAsKeys)
        .map {
          case (_, ((row1, value1), (col2, value2))) =>
            (row1, col2) -> nu r cV.t  s(value1, value2)
        }
        .sumByKey
        .map {
          case ((row, col), value) =>
            (row, col, value)
        }

    SparseMatr x(result)
  }

  /**
   * Mult ply a SparseRowMatr x. T   mple ntat on of t  funct on assu  t   nput SparseRowMatr x
   *  s a sk nny matr x,  .e., w h a small number of un que columns. Based on   exper ence,   can
   * th nk 100K  s a small number  re.
   *
   * @param sk nnyMatr x    anot r matr x to mult ply
   * @param numReducersOpt  opt onal para ter to set number of reducers.   uses 1000 by default.
   *                          can change   based on y  appl cat ons.
   * @param order ng2 order ng funct on for t  column type of anot r matr x
   * @param  nject on2  nject on funct on for t  column type of anot r matr x
   * @tparam C2 col type of anot r matr x
   *
   * @return
   */
  def mult plySk nnySparseRowMatr x[C2](
    sk nnyMatr x: SparseRowMatr x[C, C2, V],
    numReducersOpt: Opt on[ nt] = None
  )(
     mpl c  order ng2: Order ng[C2],
     nject on2:  nject on[C2, Array[Byte]]
  ): SparseRowMatr x[R, C2, V] = {

    assert(
      sk nnyMatr x. sSk nnyMatr x,
      "t  funct on only works for sk nny sparse row matr x, ot rw se   w ll get out-of- mory problem")

     mpl c  val col nject onFunct on: C => Array[Byte] = col nj.toFunct on

    val result =
      // 1000  s t  reducer number used for sketchJo n; 1000  s a number that works  ll emp r cally.
      // feel free to change t  or make t  as a param  f   f nd t  does not work for y  case.
      t .transpose.rowAsKeys
        .sketch(numReducersOpt.getOrElse(1000))
        .jo n(sk nnyMatr x.p pe)
        .map {
          case (_, ((row1, value1), colMap)) =>
            row1 -> colMap.mapValues(v => nu r cV.t  s(value1, v))
        }
        .sumByKey

    SparseRowMatr x(result, sk nnyMatr x. sSk nnyMatr x)
  }

  /***
   * Mult ply a DenseRowMatr x. T  result w ll be also a DenseRowMatr x.
   *
   * @param denseRowMatr x matr x to mult ply
   * @param numReducersOpt opt onal para ter to set number of reducers.   uses 1000 by default.
   *                         can change   based on y  appl cat ons
   * @return
   */
  def mult plyDenseRowMatr x(
    denseRowMatr x: DenseRowMatr x[C],
    numReducersOpt: Opt on[ nt] = None
  ): DenseRowMatr x[R] = {

     mpl c  val col nject onFunct on: C => Array[Byte] = col nj.toFunct on
     mpl c  val arrayVSem Group: Sem group[Array[Double]] = denseRowMatr x.sem groupArrayV

    val result =
      // 1000  s t  reducer number used for sketchJo n; 1000  s a number that works  ll emp r cally.
      // feel free to change t  or make t  as a param  f   f nd t  does not work for y  case.
      t .transpose.rowAsKeys
        .sketch(numReducersOpt.getOrElse(1000))
        .jo n(denseRowMatr x.p pe)
        .map {
          case (_, ((row1, value1), array)) =>
            row1 -> array.map(v => nu r cV.toDouble(value1) * v)
        }
        .sumByKey

    DenseRowMatr x(result)
  }

  // Transpose t  matr x.
  lazy val transpose: SparseMatr x[C, R, V] = {
    SparseMatr x(
      t .p pe
        .map {
          case (row, col, value) =>
            (col, row, value)
        })
  }

  // Create a Key-Val TypedP pe for .jo n() and ot r use cases.
  lazy val rowAsKeys: TypedP pe[(R, (C, V))] = {
    t .p pe
      .map {
        case (row, col, value) =>
          (row, (col, value))
      }
  }

  // convert to a TypedP pe
  lazy val toTypedP pe: TypedP pe[(R, C, V)] = {
    t .p pe
  }

  lazy val forceToD sk: SparseMatr x[R, C, V] = {
    SparseMatr x(t .p pe.forceToD sk)
  }

  /**
   * Convert t  matr x to a SparseRowMatr x. Do t  only w n t  max number of non-zero values per row  s
   * small (say, not more than 200K).
   *
   * @ sSk nnyMatr x  s t  resulted matr x sk nny,  .e., number of un que col ds  s small (<200K).
   *                Note t  d fference bet en `number of un que col ds` and `max number of non-zero values per row`.
   * @return
   */
  def toSparseRowMatr x( sSk nnyMatr x: Boolean = false): SparseRowMatr x[R, C, V] = {
    SparseRowMatr x(
      t .p pe.map {
        case ( , j, v) =>
            -> Map(j -> v)
      }.sumByKey,
       sSk nnyMatr x)
  }

  /**
   * Convert t  matr x to a DenseRowMatr x
   *
   * @param numCols t  number of columns  n t  DenseRowMatr x.
   * @param colTo ndexFunct on t  funct on to convert col d to t  column  ndex  n t  dense matr x
   * @return
   */
  def toDenseRowMatr x(numCols:  nt, colTo ndexFunct on: C =>  nt): DenseRowMatr x[R] = {
    t .toSparseRowMatr x( sSk nnyMatr x = true).toDenseRowMatr x(numCols, colTo ndexFunct on)
  }

  /**
   * Determ nes w t r   should return a g ven  erator g ven a threshold for t  sum of values
   * across a row and w t r   are look ng to stay under or above that value.
   * Note that  erators are mutable/destruct ve, and even call ng .s ze on   w ll 'use   up'
   *  .e.   no longer hasNext and   no longer have any reference to t   ad of t  collect on.
   *
   * @param columnValue erator     erator over column-value pa rs.
   * @param threshold T  threshold for t  sum of values
   * @param  fM n     True  f   want to stay at least above that g ven value
   * @return          A new SparseMatr x after   have f ltered t   nel g ble rows
   */
  pr vate[t ] def f lter er(
    columnValue erator:  erator[(C, V)],
    threshold: V,
     fM n: Boolean
  ):  erator[(C, V)] = {
    var sum: V = nu r cV.zero
    var  :  erator[(C, V)] =  erator.empty
    var exceeded = false
    wh le (columnValue erator.hasNext && !exceeded) {
      val (c, v) = columnValue erator.next
      val nextSum = sem groupV.plus(sum, v)
      val cmp = nu r cV.compare(nextSum, threshold)
       f (( fM n && cmp < 0) || (! fM n && cmp <= 0)) {
          =   ++  erator((c, v))
        sum = nextSum
      } else {
          =   ++  erator((c, v))
        exceeded = true
      }
    }
    ( fM n, exceeded) match {
      case (true, true) =>   ++ columnValue erator
      case (true, false) =>  erator.empty
      case (false, true) =>  erator.empty
      case (false, false) =>   ++ columnValue erator
    }
  }

  /**
   * removes entr es whose sum over rows do not  et t  m n mum sum (m nSum)
   * @param m nSum  m n mum sum for wh ch   want to enforce across all rows
   */
  def f lterRowsByM nSum(m nSum: V): SparseMatr x[R, C, V] = {
    val f lteredP pe = t .rowAsKeys.group
      .mapValueStream(f lter er(_, threshold = m nSum,  fM n = true)).map {
        case (r, (c, v)) =>
          (r, c, v)
      }
    SparseMatr x(f lteredP pe)
  }

  /**
   * removes entr es whose sum over rows exceed t  max mum sum (maxSum)
   * @param maxSum  max mum sum for wh ch   want to enforce across all rows
   */
  def f lterRowsByMaxSum(maxSum: V): SparseMatr x[R, C, V] = {
    val f lteredP pe = t .rowAsKeys.group
      .mapValueStream(f lter er(_, threshold = maxSum,  fM n = false)).map {
        case (r, (c, v)) =>
          (r, c, v)
      }
    SparseMatr x(f lteredP pe)
  }
}
