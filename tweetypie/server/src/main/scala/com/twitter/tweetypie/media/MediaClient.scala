package com.tw ter.t etyp e
package  d a

 mport com.tw ter. d a nfo.server.{thr ftscala => m s}
 mport com.tw ter. d aserv ces.commons. d a nformat on.thr ftscala.UserDef nedProduct tadata
 mport com.tw ter. d aserv ces.commons.photurkey.thr ftscala.Pr vacyType
 mport com.tw ter. d aserv ces.commons.servercommon.thr ftscala.{ServerError => CommonServerError}
 mport com.tw ter. d aserv ces.commons.thr ftscala.ProductKey
 mport com.tw ter. d aserv ces.commons.thr ftscala. d aKey
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.thumb ngb rd.{thr ftscala =>  fs}
 mport com.tw ter.t etyp e.backends. d a nfoServ ce
 mport com.tw ter.t etyp e.backends.User mageServ ce
 mport com.tw ter.t etyp e.core.UpstreamFa lure
 mport com.tw ter.user_ mage_serv ce.{thr ftscala => u s}
 mport com.tw ter.user_ mage_serv ce.thr ftscala. d aUpdateAct on
 mport com.tw ter.user_ mage_serv ce.thr ftscala. d aUpdateAct on.Delete
 mport com.tw ter.user_ mage_serv ce.thr ftscala. d aUpdateAct on.Undelete
 mport java.n o.ByteBuffer
 mport scala.ut l.control.NoStackTrace

/**
 * T   d aCl ent tra  encapsulates t  var ous operat ons   make to t  d fferent  d a serv ces
 * backends.
 */
tra   d aCl ent {
   mport  d aCl ent._

  /**
   * On t et creat on,  f t  t et conta ns  d a upload  ds,   call t  operat on to process
   * that  d a and get back  tadata about t   d a.
   */
  def process d a: Process d a

  /**
   * On t  read path, w n hydrat ng a  d aEnt y,   call t  operat on to get  tadata
   * about ex st ng  d a.
   */
  def get d a tadata: Get d a tadata

  def delete d a: Delete d a

  def undelete d a: Undelete d a
}

/**
 * Request type for t   d aCl ent.update d a operat on.
 */
pr vate case class Update d aRequest(
   d aKey:  d aKey,
  act on:  d aUpdateAct on,
  t et d: T et d)

case class Delete d aRequest( d aKey:  d aKey, t et d: T et d) {
  pr vate[ d a] def toUpdate d aRequest = Update d aRequest( d aKey, Delete, t et d)
}

case class Undelete d aRequest( d aKey:  d aKey, t et d: T et d) {
  pr vate[ d a] def toUpdate d aRequest = Update d aRequest( d aKey, Undelete, t et d)
}

/**
 * Request type for t   d aCl ent.process d a operat on.
 */
case class Process d aRequest(
   d a ds: Seq[ d a d],
  user d: User d,
  t et d: T et d,
   sProtected: Boolean,
  product tadata: Opt on[Map[ d a d, UserDef nedProduct tadata]]) {
  pr vate[ d a] def toProcessT et d aRequest =
    u s.ProcessT et d aRequest( d a ds, user d, t et d)

  pr vate[ d a] def toUpdateProduct tadataRequests( d aKeys: Seq[ d aKey]) =
    product tadata match {
      case None => Seq()
      case So (map) =>
         d aKeys.flatMap {  d aKey =>
          map.get( d aKey. d a d).map {  tadata =>
            u s.UpdateProduct tadataRequest(ProductKey(t et d.toStr ng,  d aKey),  tadata)
          }
        }
    }
}

/**
 * Request type for t   d aCl ent.get d a tdata operat on.
 */
case class  d a tadataRequest(
   d aKey:  d aKey,
  t et d: T et d,
   sProtected: Boolean,
  extens onsArgs: Opt on[ByteBuffer]) {
  pr vate[ d a] def pr vacyType =  d aCl ent.toPr vacyType( sProtected)

  /**
   * For debugg ng purposes, make a copy of t  byte buffer at object
   * creat on t  , so that   can  nspect t  or g nal buffer  f t re
   *  s an error.
   *
   * Once   have found t  problem, t   thod should be removed.
   */
  val savedExtens onArgs: Opt on[ByteBuffer] =
    extens onsArgs.map { buf =>
      val b = buf.asReadOnlyBuffer()
      val ary = new Array[Byte](b.rema n ng)
      b.get(ary)
      ByteBuffer.wrap(ary)
    }

  pr vate[ d a] def toGetT et d a nfoRequest =
    m s.GetT et d a nfoRequest(
       d aKey =  d aKey,
      t et d = So (t et d),
      pr vacyType = pr vacyType,
      stratoExtens onsArgs = extens onsArgs
    )
}

object  d aCl ent {
   mport  d aExcept ons._

  /**
   * Operat on type for process ng uploaded  d a dur ng t et creat on.
   */
  type Process d a = FutureArrow[Process d aRequest, Seq[ d aKey]]

  /**
   * Operat on type for delet ng and undelet ng t ets.
   */
  pr vate[ d a] type Update d a = FutureArrow[Update d aRequest, Un ]

  type Undelete d a = FutureArrow[Undelete d aRequest, Un ]

  type Delete d a = FutureArrow[Delete d aRequest, Un ]

  /**
   * Operat on type for gett ng  d a  tadata for ex st ng  d a dur ng t et reads.
   */
  type Get d a tadata = FutureArrow[ d a tadataRequest,  d a tadata]

  /**
   * Bu lds a Update d a FutureArrow us ng User mageServ ce endpo nts.
   */
  pr vate[ d a] object Update d a {
    def apply(updateT et d a: User mageServ ce.UpdateT et d a): Update d a =
      FutureArrow[Update d aRequest, Un ] { r =>
        updateT et d a(u s.UpdateT et d aRequest(r. d aKey, r.act on, So (r.t et d))).un 
      }.translateExcept ons(handle d aExcept ons)
  }

  /**
   * Bu lds a Process d a FutureArrow us ng User mageServ ce endpo nts.
   */
  object Process d a {

    def apply(
      updateProduct tadata: User mageServ ce.UpdateProduct tadata,
      processT et d a: User mageServ ce.ProcessT et d a
    ): Process d a = {

      val updateProduct tadataSeq = updateProduct tadata.l ftSeq

      FutureArrow[Process d aRequest, Seq[ d aKey]] { req =>
        for {
           d aKeys <- processT et d a(req.toProcessT et d aRequest).map(_. d aKeys)
          _ <- updateProduct tadataSeq(req.toUpdateProduct tadataRequests( d aKeys))
        } y eld {
          sortKeysBy ds(req. d a ds,  d aKeys)
        }
      }.translateExcept ons(handle d aExcept ons)
    }

    /**
     * Sort t   d aKeys Seq based on t   d a  d order ng spec f ed by t 
     * caller's request  d a ds Seq.
     */
    pr vate def sortKeysBy ds( d a ds: Seq[ d a d],  d aKeys: Seq[ d aKey]): Seq[ d aKey] = {
      val  dToKeyMap =  d aKeys.map(key => (key. d a d, key)).toMap
       d a ds.flatMap( dToKeyMap.get)
    }
  }

  /**
   * Bu lds a Get d a tadata FutureArrow us ng  d a nfoServ ce endpo nts.
   */
  object Get d a tadata {

    pr vate[t ] val log = Logger(getClass)

    def apply(getT et d a nfo:  d a nfoServ ce.GetT et d a nfo): Get d a tadata =
      FutureArrow[ d a tadataRequest,  d a tadata] { req =>
        getT et d a nfo(req.toGetT et d a nfoRequest).map { res =>
           d a tadata(
            res. d aKey,
            res.assetUrlHttps,
            res.s zes.toSet,
            res. d a nfo,
            res.add  onal tadata.flatMap(_.product tadata),
            res.stratoExtens onsReply,
            res.add  onal tadata
          )
        }
      }.translateExcept ons(handle d aExcept ons)
  }

  pr vate[ d a] def toPr vacyType( sProtected: Boolean): Pr vacyType =
     f ( sProtected) Pr vacyType.Protected else Pr vacyType.Publ c

  /**
   * Constructs an  mple ntat on of t   d aCl ent  nterface us ng backend  nstances.
   */
  def fromBackends(
    user mageServ ce: User mageServ ce,
     d a nfoServ ce:  d a nfoServ ce
  ):  d aCl ent =
    new  d aCl ent {

      val get d a tadata =
        Get d a tadata(
          getT et d a nfo =  d a nfoServ ce.getT et d a nfo
        )

      val process d a =
        Process d a(
          user mageServ ce.updateProduct tadata,
          user mageServ ce.processT et d a
        )

      pr vate val update d a =
        Update d a(
          user mageServ ce.updateT et d a
        )

      val delete d a: FutureArrow[Delete d aRequest, Un ] =
        FutureArrow[Delete d aRequest, Un ](r => update d a(r.toUpdate d aRequest))

      val undelete d a: FutureArrow[Undelete d aRequest, Un ] =
        FutureArrow[Undelete d aRequest, Un ](r => update d a(r.toUpdate d aRequest))
    }
}

/**
 * Except ons from t  var ous  d a serv ces backends that  nd cate bad requests (val dat on
 * fa lures) are converted to a  d aCl entExcept on.  Except ons that  nd cate a server
 * error are converted to a UpstreamFa lure. d aServ ceServerError.
 *
 *  d aNotFound: G ven  d a  d does not ex st.   could have been exp red
 * Bad d a:      G ven  d a  s corrupted and can not be processed.
 *  nval d d a:  G ven  d a has fa led to pass one or more val dat ons (s ze, d  ns ons, type etc.)
 * BadRequest     Request  s bad, but reason not ava lable
 */
object  d aExcept ons {
   mport UpstreamFa lure. d aServ ceServerError

  // Extends NoStackTrace because t  c rcumstances  n wh ch t 
  // except ons are generated don't y eld useful stack traces
  // (e.g.   can't tell from t  stack trace anyth ng about what
  // backend call was be ng made.)
  abstract class  d aCl entExcept on( ssage: Str ng) extends Except on( ssage) w h NoStackTrace

  class  d aNotFound( ssage: Str ng) extends  d aCl entExcept on( ssage)
  class Bad d a( ssage: Str ng) extends  d aCl entExcept on( ssage)
  class  nval d d a( ssage: Str ng) extends  d aCl entExcept on( ssage)
  class BadRequest( ssage: Str ng) extends  d aCl entExcept on( ssage)

  // translat ons from var ous  d a serv ce errors  nto  d aExcept ons
  val handle d aExcept ons: Part alFunct on[Any, Except on] = {
    case u s.BadRequest(msg, reason) =>
      reason match {
        case So (u s.BadRequestReason. d aNotFound) => new  d aNotFound(msg)
        case So (u s.BadRequestReason.Bad d a) => new Bad d a(msg)
        case So (u s.BadRequestReason. nval d d a) => new  nval d d a(msg)
        case _ => new BadRequest(msg)
      }
    case  fs.BadRequest(msg, reason) =>
      reason match {
        case So ( fs.BadRequestReason.NotFound) => new  d aNotFound(msg)
        case _ => new BadRequest(msg)
      }
    case m s.BadRequest(msg, reason) =>
      reason match {
        case So (m s.BadRequestReason. d aNotFound) => new  d aNotFound(msg)
        case _ => new BadRequest(msg)
      }
    case ex: CommonServerError =>  d aServ ceServerError(ex)
  }
}
