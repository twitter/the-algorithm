package com.tw ter.t etyp e.storage

 mport com.tw ter.ut l.T  

object BounceDeleteHandler {
  def apply(
     nsert: ManhattanOperat ons. nsert,
    scr be: Scr be
  ): T etStorageCl ent.BounceDelete =
    t et d => {
      val mhT  stamp = T  .now
      val bounceDeleteRecord = T etStateRecord
        .BounceDeleted(t et d, mhT  stamp. nM ll s)
        .toT etMhRecord

       nsert(bounceDeleteRecord).onSuccess { _ =>
        scr be.logRemoved(t et d, mhT  stamp,  sSoftDeleted = true)
      }
    }
}
