package com.tw ter.s mclusters_v2.scald ng.common.matr x

 mport com.tw ter.algeb rd.Sem group
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.scald ng.TypedP pe
 mport com.tw ter.scald ng.ValueP pe
 mport org.apac .avro.Sc maBu lder.ArrayBu lder
 mport scala.ut l.Random

/**
 * A class that represents a row- ndexed matr x, backed by a TypedP pe[(R, Map(C, V)].
 * For each row of t  TypedP pe,   save t  row d and a map cons st ng of col ds and t  r values.
 * Only use t  class w n t  max number of non-zero values per row  s small (say, <100K).
 *
  * Compared to SparseMatr x, t  class has so  opt m zat ons to eff c ently perform so  row-w se
 * operat ons.
 *
  * Also,  f t  matr x  s sk nny ( .e., number of un que col ds  s small),   have opt m zed solut ons
 * for col-w se normal zat on as  ll as matr x mult pl cat on (see SparseMatr x.mult plySk nnySparseRowMatr x).
 *
  * @param p pe underly ng p pe
 * @param  sSk nnyMatr x  f t  matr x  s sk nny ( .e., number of un que col ds  s small)
 *                       Note t  d fference bet en `number of un que col ds` and `max number of non-zero values per row`.
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
case class SparseRowMatr x[R, C, V](
  p pe: TypedP pe[(R, Map[C, V])],
   sSk nnyMatr x: Boolean
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
    t 
      .f lter((_, _, v) => v != nu r cV.zero)
      .p pe
      .values
      .map(_.s ze.toLong)
      .sum
  }

  overr de def get(row d: R, col d: C): ValueP pe[V] = {
    t .p pe
      .collect {
        case ( , values)  f   == row d =>
          values.collect {
            case (j, value)  f j == col d => value
          }
      }
      .flatten
      .sum
  }

  overr de def getRow(row d: R): TypedP pe[(C, V)] = {
    t .p pe.flatMap {
      case ( , values)  f   == row d =>
        values.toSeq
      case _ =>
        N l
    }
  }

  overr de def getCol(col d: C): TypedP pe[(R, V)] = {
    t .p pe.flatMap {
      case ( , values) =>
        values.collect {
          case (j, value)  f j == col d =>
              -> value
        }
    }
  }

  overr de lazy val un queRow ds: TypedP pe[R] = {
    t .p pe.map(_._1).d st nct
  }

  overr de lazy val un queCol ds: TypedP pe[C] = {
    t .p pe.flatMapValues(_.keys).values.d st nct
  }

  // convert to a SparseMatr x
  lazy val toSparseMatr x: SparseMatr x[R, C, V] = {
    SparseMatr x(t .p pe.flatMap {
      case ( , values) =>
        values.map { case (j, value) => ( , j, value) }
    })
  }

  // convert to a TypedP pe
  lazy val toTypedP pe: TypedP pe[(R, Map[C, V])] = {
    t .p pe
  }

  def f lter(fn: (R, C, V) => Boolean): SparseRowMatr x[R, C, V] = {
    SparseRowMatr x(
      t .p pe
        .map {
          case ( , values) =>
              -> values.f lter { case (j, v) => fn( , j, v) }
        }
        .f lter(_._2.nonEmpty),
       sSk nnyMatr x = t . sSk nnyMatr x
    )
  }

  // sample t  rows  n t  matr x as def ned by sampl ngRat o
  def sampleRows(sampl ngRat o: Double): SparseRowMatr x[R, C, V] = {
    SparseRowMatr x(t .p pe.f lter(_ => Random.nextDouble < sampl ngRat o), t . sSk nnyMatr x)
  }

  // f lter t  matr x based on a subset of rows
  def f lterRows(rows: TypedP pe[R]): SparseRowMatr x[R, C, V] = {
    SparseRowMatr x(t .p pe.jo n(rows.asKeys).mapValues(_._1), t . sSk nnyMatr x)
  }

  // f lter t  matr x based on a subset of cols
  def f lterCols(cols: TypedP pe[C]): SparseRowMatr x[R, C, V] = {
    t .toSparseMatr x.f lterCols(cols).toSparseRowMatr x(t . sSk nnyMatr x)
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
  ): SparseRowMatr x[R1, C1, V1] = {
    SparseRowMatr x(
      t .p pe.flatMap {
        case ( , values) =>
          values
            .map {
              case (j, v) => fn( , j, v)
            }
            .groupBy(_._1)
            .mapValues { _.map { case (_, j1, v1) => (j1, v1) }.toMap }
      },
       sSk nnyMatr x = t . sSk nnyMatr x
    )
  }

  // get t  l2 norms for all rows. t  does not tr gger a shuffle.
  lazy val rowL2Norms: TypedP pe[(R, Double)] = {
    t .p pe.map {
      case (row, values) =>
        row -> math.sqrt(
          values.values
            .map(a => nu r cV.toDouble(a) * nu r cV.toDouble(a))
            .sum)
    }
  }

  // normal ze t  matr x to make sure each row has un  norm
  lazy val rowL2Normal ze: SparseRowMatr x[R, C, Double] = {
    val result = t .p pe.flatMap {
      case (row, values) =>
        val norm =
          math.sqrt(
            values.values
              .map(v => nu r cV.toDouble(v) * nu r cV.toDouble(v))
              .sum)
         f (norm == 0.0) {
          None
        } else {
          So (row -> values.mapValues(v => nu r cV.toDouble(v) / norm))
        }
    }

    SparseRowMatr x(result,  sSk nnyMatr x = t . sSk nnyMatr x)
  }

  // get t  l2 norms for all cols
  lazy val colL2Norms: TypedP pe[(C, Double)] = {
    t .p pe
      .flatMap {
        case (_, values) =>
          values.map {
            case (col, v) =>
              col -> nu r cV.toDouble(v) * nu r cV.toDouble(v)
          }
      }
      .sumByKey
      .mapValues(math.sqrt)
  }

  // normal ze t  matr x to make sure each column has un  norm
  lazy val colL2Normal ze: SparseRowMatr x[R, C, Double] = {
    val result =  f (t . sSk nnyMatr x) {
      //  f t   s a sk nny matr x,   f rst put t  norm of all columns  nto a Map, and t n use
      // t  Map  ns de t  mappers w hout shuffl ng t  whole matr x (wh ch  s expens ve, see t 
      // `else` part of t  funct on).
      val colL2NormsValueP pe = t .colL2Norms.map {
        case (col, norm) => Map(col -> norm)
      }.sum

      t .p pe.flatMapW hValue(colL2NormsValueP pe) {
        case ((row, values), So (colNorms)) =>
          So (row -> values.flatMap {
            case (col, value) =>
              val colNorm = colNorms.getOrElse(col, 0.0)
               f (colNorm == 0.0) {
                None
              } else {
                So (col -> nu r cV.toDouble(value) / colNorm)
              }
          })
        case _ =>
          None
      }
    } else {
      t .toSparseMatr x.transpose.rowAsKeys
        .jo n(t .colL2Norms)
        .collect {
          case (col, ((row, value), colNorm))  f colNorm > 0.0 =>
            row -> Map(col -> nu r cV.toDouble(value) / colNorm)
        }
        .sumByKey
        .toTypedP pe
    }

    SparseRowMatr x(result,  sSk nnyMatr x = t . sSk nnyMatr x)
  }

  /**
   * Take topK non-zero ele nts from each row. Cols are ordered by t  `order ng` funct on
   */
  def sortW hTakePerRow(
    k:  nt
  )(
    order ng: Order ng[(C, V)]
  ): TypedP pe[(R, Seq[(C, V)])] = {
    t .p pe.map {
      case (row, values) =>
        row -> values.toSeq.sorted(order ng).take(k)
    }
  }

  /**
   * Take topK non-zero ele nts from each column. Rows are ordered by t  `order ng` funct on.
   */
  def sortW hTakePerCol(
    k:  nt
  )(
    order ng: Order ng[(R, V)]
  ): TypedP pe[(C, Seq[(R, V)])] = {
    t .toSparseMatr x.sortW hTakePerCol(k)(order ng)
  }

  /**
   * S m lar to .forceToD sk funct on  n TypedP pe, but w h an opt on to spec fy how many part  ons
   * to save, wh ch  s useful  f   want to consol date t  data set or want to tune t  number
   * of mappers for t  next step.
   *
    * @param numShardsOpt number of shards to save t  data.
   *
    * @return
   */
  def forceToD sk(
    numShardsOpt: Opt on[ nt] = None
  ): SparseRowMatr x[R, C, V] = {
    numShardsOpt
      .map { numShards =>
        SparseRowMatr x(t .p pe.shard(numShards), t . sSk nnyMatr x)
      }
      .getOrElse {
        SparseRowMatr x(t .p pe.forceToD sk, t . sSk nnyMatr x)
      }
  }

  /**
   * transpose current matr x and mult ple anot r Sk nny SparseRowMatr x.
   * T  d fference bet en t  and .transpose.mult plySk nnySparseRowMatr x(anot rSparseRowMatr x),
   *  s that   do not need to do flatten and group aga n.
   *
    * One use case  s to w n   need to compute t  column-w se covar ance matr x, t n   only need
   * a.transposeAndMult plySk nnySparseRowMatr x(a) to get  .
   *
   * @param anot rSparseRowMatr x   needs to be a sk nny SparseRowMatr x
   * @numReducersOpt Number of reducers.
   */
  def transposeAndMult plySk nnySparseRowMatr x[C2](
    anot rSparseRowMatr x: SparseRowMatr x[R, C2, V],
    numReducersOpt: Opt on[ nt] = None
  )(
     mpl c  order ng2: Order ng[C2],
     nject on2:  nject on[C2, Array[Byte]]
  ): SparseRowMatr x[C, C2, V] = {

    //   needs to be a sk nny SparseRowMatr x, ot rw se   w ll have out-of- mory  ssue
    requ re(anot rSparseRowMatr x. sSk nnyMatr x)

    SparseRowMatr x(
      numReducersOpt
        .map { numReducers =>
          t .p pe
            .jo n(anot rSparseRowMatr x.p pe).w hReducers(numReducers)
        }.getOrElse(t .p pe
          .jo n(anot rSparseRowMatr x.p pe))
        .flatMap {
          case (_, (row1, row2)) =>
            row1.map {
              case (col1, val1) =>
                col1 -> row2.mapValues(val2 => nu r cV.t  s(val1, val2))
            }
        }
        .sumByKey,
       sSk nnyMatr x = true
    )

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
    t .toSparseMatr x.mult plyDenseRowMatr x(denseRowMatr x, numReducersOpt)
  }

  /**
   * Convert t  matr x to a DenseRowMatr x
   *
   * @param numCols t  number of columns  n t  DenseRowMatr x.
   * @param colTo ndexFunct on t  funct on to convert col d to t  column  ndex  n t  dense matr x
   * @return
   */
  def toDenseRowMatr x(numCols:  nt, colTo ndexFunct on: C =>  nt): DenseRowMatr x[R] = {
    DenseRowMatr x(t .p pe.map {
      case (row, colMap) =>
        val array = new Array[Double](numCols)
        colMap.foreach {
          case (col, value) =>
            val  ndex = colTo ndexFunct on(col)
            assert( ndex < numCols &&  ndex >= 0, "T  converted  ndex  s out of range!")
            array( ndex) = nu r cV.toDouble(value)
        }
        row -> array
    })
  }

}
