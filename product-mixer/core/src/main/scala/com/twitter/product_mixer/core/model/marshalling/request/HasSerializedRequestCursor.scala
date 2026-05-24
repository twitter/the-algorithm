package com.twitter.product_mixer.core.model.marshalling.request

/**
 * serializedRequestCursor is any serialized representation of a cursor.
 *
 * The serialized representation is implementation-specific but will often be a base 64
 * representation of a Thrift struct. Cursors should not be deserialized in the unmarshaller.
 */
trait HasSerializedRequestCursor {
  def serializedRequestCursor: Option[String]
}
