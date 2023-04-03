packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst

/**
 * selonrializelondRelonquelonstCursor is any selonrializelond relonprelonselonntation of a cursor.
 *
 * Thelon selonrializelond relonprelonselonntation is implelonmelonntation-speloncific but will oftelonn belon a baselon 64
 * relonprelonselonntation of a Thrift struct. Cursors should not belon delonselonrializelond in thelon unmarshallelonr.
 */
trait HasSelonrializelondRelonquelonstCursor {
  delonf selonrializelondRelonquelonstCursor: Option[String]
}
