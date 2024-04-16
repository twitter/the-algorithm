package com.tw ter.ann.common

 mport com.google.common. o.ByteStreams
 mport com.tw ter.ann.common.thr ftscala.Ann ndex tadata
 mport com.tw ter. d aserv ces.commons.codec.ArrayByteBufferCodec
 mport com.tw ter. d aserv ces.commons.codec.Thr ftByteBufferCodec
 mport com.tw ter.search.common.f le.AbstractF le
 mport java. o. OExcept on
 mport java. o. nputStream
 mport java. o.OutputStream
 mport java.n o.channels.Channels
 mport org.apac .beam.sdk. o.F leSystems
 mport org.apac .beam.sdk. o.fs.MoveOpt ons
 mport org.apac .beam.sdk. o.fs.ResolveOpt ons
 mport org.apac .beam.sdk. o.fs.ResolveOpt ons.StandardResolveOpt ons
 mport org.apac .beam.sdk. o.fs.Res ce d
 mport org.apac .beam.sdk.ut l.M  Types
 mport org.apac .hadoop. o. OUt ls
 mport scala.collect on.JavaConverters._

/**
 * T  class creates a wrapper around GCS f lesystem and HDFS f lesystem for t   ndex
 * generat on job.    mple nts t  bas c  thods requ red by t   ndex generat on job and h des
 * t  log c around handl ng HDFS vs GCS.
 */
class  ndexOutputF le(val abstractF le: AbstractF le, val res ce d: Res ce d) {

  // Success f le na 
  pr vate val SUCCESS_F LE = "_SUCCESS"
  pr vate val  NDEX_METADATA_F LE = "ANN_ NDEX_METADATA"
  pr vate val  tadataCodec = new Thr ftByteBufferCodec[Ann ndex tadata](Ann ndex tadata)

  /**
   * Constructor for Res ce d. T   s used for GCS f lesystem
   * @param res ce d
   */
  def t (res ce d: Res ce d) = {
    t (null, res ce d)
  }

  /**
   * Constructor for AbstractF le. T   s used for HDFS and local f lesystem
   * @param abstractF le
   */
  def t (abstractF le: AbstractF le) = {
    t (abstractF le, null)
  }

  /**
   * Returns true  f t   nstance  s around an AbstractF le.
   * @return
   */
  def  sAbstractF le(): Boolean = {
    abstractF le != null
  }

  /**
   * Creates a _SUCCESS f le  n t  current d rectory.
   */
  def createSuccessF le(): Un  = {
     f ( sAbstractF le()) {
      abstractF le.createSuccessF le()
    } else {
      val successF le =
        res ce d.resolve(SUCCESS_F LE, ResolveOpt ons.StandardResolveOpt ons.RESOLVE_F LE)
      val successWr erChannel = F leSystems.create(successF le, M  Types.B NARY)
      successWr erChannel.close()
    }
  }

  /**
   * Returns w t r t  current  nstance represents a d rectory
   * @return True  f t  current  nstance  s a d rectory
   */
  def  sD rectory(): Boolean = {
     f ( sAbstractF le()) {
      abstractF le. sD rectory
    } else {
      res ce d. sD rectory
    }
  }

  /**
   * Return t  current path of t  f le represented by t  current  nstance
   * @return T  path str ng of t  f le/d rectory
   */
  def getPath(): Str ng = {
     f ( sAbstractF le()) {
      abstractF le.getPath.toStr ng
    } else {
       f (res ce d. sD rectory) {
        res ce d.getCurrentD rectory.toStr ng
      } else {
        res ce d.getCurrentD rectory.toStr ng + res ce d.getF lena 
      }
    }
  }

  /**
   * Creates a new f le @param f leNa   n t  current d rectory.
   * @param f leNa 
   * @return A new f le  ns de t  current d rectory
   */
  def createF le(f leNa : Str ng):  ndexOutputF le = {
     f ( sAbstractF le()) {
      // AbstractF le treats f les and d rector es t  sa  way.  nce, not c ck ng for d rectory
      //  re.
      new  ndexOutputF le(abstractF le.getCh ld(f leNa ))
    } else {
       f (!res ce d. sD rectory) {
        //  f t   s not a d rectory, throw except on.
        throw new  llegalArgu ntExcept on(getPath() + "  s not a d rectory.")
      }
      new  ndexOutputF le(
        res ce d.resolve(f leNa , ResolveOpt ons.StandardResolveOpt ons.RESOLVE_F LE))
    }
  }

  /**
   * Creates a new d rectory @param d rectoryNa   n t  current d rectory.
   * @param d rectoryNa 
   * @return A new d rectory  ns de t  current d rectory
   */
  def createD rectory(d rectoryNa : Str ng):  ndexOutputF le = {
     f ( sAbstractF le()) {
      // AbstractF le treats f les and d rector es t  sa  way.  nce, not c ck ng for d rectory
      //  re.
      val d r = abstractF le.getCh ld(d rectoryNa )
      d r.mkd rs()
      new  ndexOutputF le(d r)
    } else {
       f (!res ce d. sD rectory) {
        //  f t   s not a d rectory, throw except on.
        throw new  llegalArgu ntExcept on(getPath() + "  s not a d rectory.")
      }
      val newRes ce d =
        res ce d.resolve(d rectoryNa , ResolveOpt ons.StandardResolveOpt ons.RESOLVE_D RECTORY)

      // Create a tmp f le and delete  n order to tr gger d rectory creat on
      val tmpF le =
        newRes ce d.resolve("tmp", ResolveOpt ons.StandardResolveOpt ons.RESOLVE_F LE)
      val tmpWr erChannel = F leSystems.create(tmpF le, M  Types.B NARY)
      tmpWr erChannel.close()
      F leSystems.delete(L st(tmpF le).asJava, MoveOpt ons.StandardMoveOpt ons. GNORE_M SS NG_F LES)

      new  ndexOutputF le(newRes ce d)
    }
  }

  def getCh ld(f leNa : Str ng,  sD rectory: Boolean = false):  ndexOutputF le = {
     f ( sAbstractF le()) {
      new  ndexOutputF le(abstractF le.getCh ld(f leNa ))
    } else {
      val resolveOpt on =  f ( sD rectory) {
        StandardResolveOpt ons.RESOLVE_D RECTORY
      } else {
        StandardResolveOpt ons.RESOLVE_F LE
      }
      new  ndexOutputF le(res ce d.resolve(f leNa , resolveOpt on))
    }
  }

  /**
   * Returns an OutputStream for t  underly ng f le.
   * Note: Close t  OutputStream after wr  ng
   * @return
   */
  def getOutputStream(): OutputStream = {
     f ( sAbstractF le()) {
      abstractF le.getByteS nk.openStream()
    } else {
       f (res ce d. sD rectory) {
        //  f t   s a d rectory, throw except on.
        throw new  llegalArgu ntExcept on(getPath() + "  s a d rectory.")
      }
      val wr erChannel = F leSystems.create(res ce d, M  Types.B NARY)
      Channels.newOutputStream(wr erChannel)
    }
  }

  /**
   * Returns an  nputStream for t  underly ng f le.
   * Note: Close t   nputStream after read ng
   * @return
   */
  def get nputStream():  nputStream = {
     f ( sAbstractF le()) {
      abstractF le.getByteS ce.openStream()
    } else {
       f (res ce d. sD rectory) {
        //  f t   s a d rectory, throw except on.
        throw new  llegalArgu ntExcept on(getPath() + "  s a d rectory.")
      }
      val readChannel = F leSystems.open(res ce d)
      Channels.new nputStream(readChannel)
    }
  }

  /**
   * Cop es content from t  src n  nto t  current f le.
   * @param src n
   */
  def copyFrom(src n:  nputStream): Un  = {
    val out = getOutputStream()
    try {
       OUt ls.copyBytes(src n, out, 4096)
      out.close()
    } catch {
      case ex:  OExcept on =>
         OUt ls.closeStream(out);
        throw ex;
    }
  }

  def wr e ndex tadata(ann ndex tadata: Ann ndex tadata): Un  = {
    val out = createF le( NDEX_METADATA_F LE).getOutputStream()
    val bytes = ArrayByteBufferCodec.decode( tadataCodec.encode(ann ndex tadata))
    out.wr e(bytes)
    out.close()
  }

  def load ndex tadata(): Ann ndex tadata = {
    val  n = ByteStreams.toByteArray(get nputStream())
     tadataCodec.decode(ArrayByteBufferCodec.encode( n))
  }
}
