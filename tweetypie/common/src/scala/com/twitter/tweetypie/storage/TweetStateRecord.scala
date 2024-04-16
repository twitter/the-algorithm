package com.tw ter.t etyp e.storage

 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanValue
 mport com.tw ter.ut l.T  

/**
 * A [[T etStateRecord]] represents an act on taken on a t et and can be used to determ ne a t et's state.
 *
 * T  state  s determ ned by t  record w h t  most recent t  stamp.  n t  absence of any
 * record a t et  s cons dered found, wh ch  s to say t  t et has not been through t 
 * delet on process.
 *
 * T  [[T etStateRecord]] type  s determ ned by t  lkey of a t et manhattan record:
 *     tadata/delete_state      -> HardDeleted
 *     tadata/soft_delete_state -> SoftDeleted
 *     tadata/undelete_state    -> Undeleted
 *     tadata/force_added_state -> ForceAdded
 *
 * See t  README  n t  d rectory for more deta ls about t  state of a t et.
 */
sealed tra  T etStateRecord {
  def t et d: T et d
  def createdAt: Long
  def stateKey: T etKey.LKey.StateKey
  def values: Map[Str ng, Long] = Map("t  stamp" -> createdAt)
  def na : Str ng

  def toT etMhRecord: T etManhattanRecord = {
    val valByteBuffer = ByteArrayCodec.toByteBuffer(Json.encode(values))
    val value = ManhattanValue(valByteBuffer, So (T  .fromM ll seconds(createdAt)))
    T etManhattanRecord(T etKey(t et d, stateKey), value)
  }
}

object T etStateRecord {

  /** W n a soft-deleted or bounce deleted t et  s ult mately hard-deleted by an offl ne job. */
  case class HardDeleted(t et d: T et d, createdAt: Long, deletedAt: Long)
      extends T etStateRecord {
    // t  stamp  n t  mh backend  s t  hard delet on t  stamp
    overr de def values = Map("t  stamp" -> createdAt, "softdelete_t  stamp" -> deletedAt)
    def stateKey = T etKey.LKey.HardDelet onStateKey
    def na  = "hard_deleted"
  }

  /** W n a t et  s deleted by t  user.   can st ll be undeleted wh le  n t  soft deleted state. */
  case class SoftDeleted(t et d: T et d, createdAt: Long) extends T etStateRecord {
    def stateKey = T etKey.LKey.SoftDelet onStateKey
    def na  = "soft_deleted"
  }

  /** W n a t et  s deleted by go/bouncer for v olat ng Tw ter Rules.   MAY NOT be undeleted. */
  case class BounceDeleted(t et d: T et d, createdAt: Long) extends T etStateRecord {
    def stateKey = T etKey.LKey.BounceDelet onStateKey
    def na  = "bounce_deleted"
  }

  /** W n a t et  s undeleted by an  nternal system. */
  case class Undeleted(t et d: T et d, createdAt: Long) extends T etStateRecord {
    def stateKey = T etKey.LKey.UnDelet onStateKey
    def na  = "undeleted"
  }

  /** W n a t et  s created us ng t  forceAdd endpo nt. */
  case class ForceAdded(t et d: T et d, createdAt: Long) extends T etStateRecord {
    def stateKey = T etKey.LKey.ForceAddedStateKey
    def na  = "force_added"
  }

  def fromT etMhRecord(record: T etManhattanRecord): Opt on[T etStateRecord] = {
    def ts = T  stampDecoder.decode(record, T  stampType.Default).getOrElse(0L)
    def sdts = T  stampDecoder.decode(record, T  stampType.SoftDelete).getOrElse(0L)
    def t et d = record.pkey

    record.lkey match {
      case T etKey.LKey.HardDelet onStateKey => So (HardDeleted(t et d, ts, sdts))
      case T etKey.LKey.SoftDelet onStateKey => So (SoftDeleted(t et d, ts))
      case T etKey.LKey.BounceDelet onStateKey => So (BounceDeleted(t et d, ts))
      case T etKey.LKey.UnDelet onStateKey => So (Undeleted(t et d, ts))
      case T etKey.LKey.ForceAddedStateKey => So (ForceAdded(t et d, ts))
      case _ => None
    }
  }

  def fromT etMhRecords(records: Seq[T etManhattanRecord]): Seq[T etStateRecord] =
    records.flatMap(fromT etMhRecord)

  def mostRecent(records: Seq[T etManhattanRecord]): Opt on[T etStateRecord] =
    fromT etMhRecords(records).sortBy(_.createdAt).lastOpt on
}
