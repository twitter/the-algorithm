package com.tw ter.t etyp e

 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanValue
 mport java.n o.ByteBuffer

package object storage {
  type T et d = Long
  type F eld d = Short

  type T etManhattanValue = ManhattanValue[ByteBuffer]
}
