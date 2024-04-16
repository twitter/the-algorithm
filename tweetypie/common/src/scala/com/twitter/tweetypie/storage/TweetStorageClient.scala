package com.tw ter.t etyp e.storage

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.storage.Response.T etResponse
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.ut l.Future

/**
 *  nterface for read ng and wr  ng t et data  n Manhattan
 */
tra  T etStorageCl ent {
   mport T etStorageCl ent._
  def addT et: AddT et
  def deleteAdd  onalF elds: DeleteAdd  onalF elds
  def getT et: GetT et
  def getStoredT et: GetStoredT et
  def getDeletedT ets: GetDeletedT ets
  def undelete: Undelete
  def updateT et: UpdateT et
  def scrub: Scrub
  def softDelete: SoftDelete
  def bounceDelete: BounceDelete
  def hardDeleteT et: HardDeleteT et
  def p ng: P ng
}

object T etStorageCl ent {
  type GetT et = T et d => St ch[GetT et.Response]

  object GetT et {
    sealed tra  Response
    object Response {
      case class Found(t et: T et) extends Response
      object NotFound extends Response
      object Deleted extends Response
      // On BounceDeleted, prov de t  full T et so that  mple ntat ons
      // ( .e. ManhattanT etStorageCl ent) don't not need to be aware of t  spec f c t et
      // f elds requ red by callers for proper process ng of bounced deleted t ets.
      case class BounceDeleted(t et: T et) extends Response
    }
  }

  type GetStoredT et = T et d => St ch[GetStoredT et.Response]

  object GetStoredT et {
    sealed abstract class Error(val  ssage: Str ng) {
      overr de def toStr ng: Str ng =  ssage
    }
    object Error {
      case object T et sCorrupt extends Error("stored t et data  s corrupt and cannot be decoded")

      case object ScrubbedF eldsPresent
          extends Error("stored t et f elds that should be scrubbed are st ll present")

      case object T etF eldsM ss ngOr nval d
          extends Error("expected t et f elds are m ss ng or conta n  nval d values")

      case object T etShouldBeHardDeleted
          extends Error("stored t et that should be hard deleted  s st ll present")
    }

    sealed tra  Response
    object Response {
      sealed tra  StoredT et tadata {
        def state: Opt on[T etStateRecord]
        def allStates: Seq[T etStateRecord]
        def scrubbedF elds: Set[F eld d]
      }

      sealed tra  StoredT etErrors {
        def errs: Seq[Error]
      }

      /**
       * T et data was found, poss bly state records and/or scrubbed f eld records.
       */
      sealed tra  FoundAny extends Response w h StoredT et tadata {
        def t et: T et
      }

      object FoundAny {
        def unapply(
          response: Response
        ): Opt on[
          (T et, Opt on[T etStateRecord], Seq[T etStateRecord], Set[F eld d], Seq[Error])
        ] =
          response match {
            case f: FoundW hErrors =>
              So ((f.t et, f.state, f.allStates, f.scrubbedF elds, f.errs))
            case f: FoundAny => So ((f.t et, f.state, f.allStates, f.scrubbedF elds, Seq.empty))
            case _ => None
          }
      }

      /**
       * No records for t  t et  d  re found  n storage
       */
      case class NotFound( d: T et d) extends Response

      /**
       * Data related to t  T et  d was found but could not be loaded successfully. T 
       * errs array conta ns deta ls of t  problems.
       */
      case class Fa led(
         d: T et d,
        state: Opt on[T etStateRecord],
        allStates: Seq[T etStateRecord],
        scrubbedF elds: Set[F eld d],
        errs: Seq[Error],
      ) extends Response
          w h StoredT et tadata
          w h StoredT etErrors

      /**
       * No T et data was found, and t  most recent state record found  s HardDeleted
       */
      case class HardDeleted(
         d: T et d,
        state: Opt on[T etStateRecord.HardDeleted],
        allStates: Seq[T etStateRecord],
        scrubbedF elds: Set[F eld d],
      ) extends Response
          w h StoredT et tadata

      /**
       * T et data was found, and t  most recent state record found,  f any,  s not
       * any form of delet on record.
       */
      case class Found(
        t et: T et,
        state: Opt on[T etStateRecord],
        allStates: Seq[T etStateRecord],
        scrubbedF elds: Set[F eld d],
      ) extends FoundAny

      /**
       * T et data was found, and t  most recent state record found  nd cates delet on.
       */
      case class FoundDeleted(
        t et: T et,
        state: Opt on[T etStateRecord],
        allStates: Seq[T etStateRecord],
        scrubbedF elds: Set[F eld d],
      ) extends FoundAny

      /**
       * T et data was found, ho ver errors  re detected  n t  stored data. Requ red
       * f elds may be m ss ng from t  T et struct (e.g. CoreData), stored f elds that
       * should be scrubbed rema n present, or T ets that should be hard-deleted rema n
       *  n storage. T  errs array conta ns deta ls of t  problems.
       */
      case class FoundW hErrors(
        t et: T et,
        state: Opt on[T etStateRecord],
        allStates: Seq[T etStateRecord],
        scrubbedF elds: Set[F eld d],
        errs: Seq[Error],
      ) extends FoundAny
          w h StoredT etErrors
    }
  }

  type HardDeleteT et = T et d => St ch[HardDeleteT et.Response]
  type SoftDelete = T et d => St ch[Un ]
  type BounceDelete = T et d => St ch[Un ]

  object HardDeleteT et {
    sealed tra  Response
    object Response {
      case class Deleted(deletedAtM ll s: Opt on[Long], createdAtM ll s: Opt on[Long])
          extends Response
      case class NotDeleted( d: T et d,  nel g bleLKey: Opt on[T etKey.LKey])
          extends Throwable
          w h Response
    }
  }

  type Undelete = T et d => St ch[Undelete.Response]
  object Undelete {
    case class Response(
      code: UndeleteResponseCode,
      t et: Opt on[T et] = None,
      createdAtM ll s: Opt on[Long] = None,
      arch vedAtM ll s: Opt on[Long] = None)

    sealed tra  UndeleteResponseCode

    object UndeleteResponseCode {
      object Success extends UndeleteResponseCode
      object BackupNotFound extends UndeleteResponseCode
      object NotCreated extends UndeleteResponseCode
    }
  }

  type AddT et = T et => St ch[Un ]
  type UpdateT et = (T et, Seq[F eld]) => St ch[T etResponse]
  type GetDeletedT ets = Seq[T et d] => St ch[Seq[DeletedT etResponse]]
  type DeleteAdd  onalF elds = (Seq[T et d], Seq[F eld]) => St ch[Seq[T etResponse]]
  type Scrub = (Seq[T et d], Seq[F eld]) => St ch[Un ]
  type P ng = () => Future[Un ]
}
