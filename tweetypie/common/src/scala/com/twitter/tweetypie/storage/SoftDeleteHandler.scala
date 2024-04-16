package com.tw ter.t etyp e.storage

 mport com.tw ter.ut l.T  

object SoftDeleteHandler {
  def apply(
     nsert: ManhattanOperat ons. nsert,
    scr be: Scr be
  ): T etStorageCl ent.SoftDelete =
    t et d => {
      val mhT  stamp = T  .now
      val softDeleteRecord = T etStateRecord
        .SoftDeleted(t et d, mhT  stamp. nM ll s)
        .toT etMhRecord

       nsert(softDeleteRecord).onSuccess { _ =>
        scr be.logRemoved(t et d, mhT  stamp,  sSoftDeleted = true)
      }
    }
}
