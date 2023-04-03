packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon

/**
 * PipelonlinelonCursor relonprelonselonnts any product-speloncific cursor modelonl. Typically thelon PipelonlinelonCursor will belon
 * a delon-selonrializelond baselon 64 thrift struct from initial relonquelonst.
 */
trait PipelonlinelonCursor

/**
 * HasPipelonlinelonCursor indicatelons that a [[PipelonlinelonQuelonry]] has a cursor
 */
trait HasPipelonlinelonCursor[+Cursor <: PipelonlinelonCursor] {
  delonf pipelonlinelonCursor: Option[Cursor]

  /**
   * If thelon cursor is not prelonselonnt, this typically melonans that welon arelon on thelon first pagelon
   */
  delonf isFirstPagelon: Boolelonan = pipelonlinelonCursor.iselonmpty
}

/**
 * UrtPipelonlinelonCursor relonprelonselonnts a URT product-speloncific cursor modelonl. Typically thelon UrtPipelonlinelonCursor
 * will belon a delon-selonrializelond baselon 64 thrift struct from initial relonquelonst.
 */
trait UrtPipelonlinelonCursor elonxtelonnds PipelonlinelonCursor {

  /** Selonelon [[UrtCursorBuildelonr]] for background on building initialSortIndelonx */
  delonf initialSortIndelonx: Long
}

objelonct UrtPipelonlinelonCursor {
  delonf gelontCursorInitialSortIndelonx(quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[_]): Option[Long] = {
    quelonry.pipelonlinelonCursor match {
      caselon Somelon(cursor: UrtPipelonlinelonCursor) => Somelon(cursor.initialSortIndelonx)
      caselon _ => Nonelon
    }
  }
}
