packagelon com.twittelonr.simclustelonrs_v2.scalding.common.matrix

import com.twittelonr.algelonbird.{Aggrelongator, Selonmigroup}
import com.twittelonr.bijelonction.Injelonction
import com.twittelonr.scalding.{TypelondPipelon, ValuelonPipelon}

/**
 * A matrix trait for relonprelonselonnting a matrix backelond by TypelondPipelon
 *
 * @tparam R Typelon for rows
 * @tparam C Typelon for columns
 * @tparam V Typelon for elonlelonmelonnts of thelon matrix
 */
abstract class TypelondPipelonMatrix[R, C, @speloncializelond(Doublelon, Int, Float, Long, Short) V] {
  implicit val selonmigroupV: Selonmigroup[V]
  implicit val numelonricV: Numelonric[V]
  implicit val rowOrd: Ordelonring[R]
  implicit val colOrd: Ordelonring[C]
  implicit val rowInj: Injelonction[R, Array[Bytelon]]
  implicit val colInj: Injelonction[C, Array[Bytelon]]

  // num of non-zelonro elonlelonmelonnts in thelon matrix
  val nnz: ValuelonPipelon[Long]

  // list of uniquelon rowIds in thelon matrix
  val uniquelonRowIds: TypelondPipelon[R]

  // list of uniquelon uniquelon in thelon matrix
  val uniquelonColIds: TypelondPipelon[C]

  // gelont a speloncific row of thelon matrix
  delonf gelontRow(rowId: R): TypelondPipelon[(C, V)]

  // gelont a speloncific column of thelon matrix
  delonf gelontCol(colId: C): TypelondPipelon[(R, V)]

  // gelont thelon valuelon of an elonlelonmelonnt
  delonf gelont(rowId: R, colId: C): ValuelonPipelon[V]

  // numbelonr of uniquelon rowIds
  lazy val numUniquelonRows: ValuelonPipelon[Long] = {
    this.uniquelonRowIds.aggrelongatelon(Aggrelongator.sizelon)
  }

  // numbelonr of uniquelon uniquelon
  lazy val numUniquelonCols: ValuelonPipelon[Long] = {
    this.uniquelonColIds.aggrelongatelon(Aggrelongator.sizelon)
  }
}
