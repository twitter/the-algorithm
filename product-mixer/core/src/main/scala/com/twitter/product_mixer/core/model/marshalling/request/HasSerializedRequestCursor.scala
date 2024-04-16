package com.tw ter.product_m xer.core.model.marshall ng.request

/**
 * ser al zedRequestCursor  s any ser al zed representat on of a cursor.
 *
 * T  ser al zed representat on  s  mple ntat on-spec f c but w ll often be a base 64
 * representat on of a Thr ft struct. Cursors should not be deser al zed  n t  unmarshaller.
 */
tra  HasSer al zedRequestCursor {
  def ser al zedRequestCursor: Opt on[Str ng]
}
