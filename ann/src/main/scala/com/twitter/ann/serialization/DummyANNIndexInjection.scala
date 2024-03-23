package com.ExTwitter.ann.serialization

import com.ExTwitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.ExTwitter.scalding_internal.multiformat.format.keyval.KeyValInjection.Long2BigEndian

/**
Dummy injection required to writeup dummy dal dataset to ANN folder.
**/
object DummyANNIndexInjection {
  val injection: KeyValInjection[Long, Long] =
    KeyValInjection[Long, Long](Long2BigEndian, Long2BigEndian)
}
