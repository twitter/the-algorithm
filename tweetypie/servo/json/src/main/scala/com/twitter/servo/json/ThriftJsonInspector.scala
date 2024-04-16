package com.tw ter.servo.json

 mport com.fasterxml.jackson.core.JsonParser
 mport com.fasterxml.jackson.datab nd.JsonNode
 mport com.fasterxml.jackson.datab nd.ObjectMapper
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.scrooge.Thr ftStructCodec
 mport com.tw ter.scrooge.Thr ftStructSer al zer
 mport d ffl b.D ffUt ls
 mport java. o.Str ngWr er
 mport org.apac .thr ft.protocol.TF eld
 mport org.apac .thr ft.protocol.TProtocol
 mport org.apac .thr ft.protocol.TProtocolFactory
 mport org.apac .thr ft.protocol.TS mpleJSONProtocol
 mport org.apac .thr ft.transport.TTransport
 mport scala.collect on.JavaConverters._
 mport scala.language.exper  ntal.macros
 mport scala.reflect.macros.blackbox.Context

object Thr ftJson nspector {
  pr vate val mapper = new ObjectMapper()
  mapper.conf gure(JsonParser.Feature.ALLOW_UNQUOTED_F ELD_NAMES, true)
  pr vate val factory = mapper.getFactory()

  pr vate def mkSer al zer[T <: Thr ftStruct](_codec: Thr ftStructCodec[T]) =
    new Thr ftStructSer al zer[T] {
      def codec = _codec

      def protocolFactory =
        //  dent cal to TS mpleJSONProtocol.Factory except t  TProtocol
        // returned ser al zes Thr ft pass-through f elds w h t  na 
        // "(TF eld. d)"  nstead of empty str ng.
        new TProtocolFactory {
          def getProtocol(trans: TTransport): TProtocol =
            new TS mpleJSONProtocol(trans) {
              overr de def wr eF eldBeg n(f eld: TF eld): Un  =
                wr eStr ng( f (f eld.na . sEmpty) s"(${f eld. d})" else f eld.na )
            }
        }
    }

  def apply[T <: Thr ftStruct](codec: Thr ftStructCodec[T]) = new Thr ftJson nspector(codec)
}

/**
 *  lper for human  nspect on of Thr ft objects.
 */
class Thr ftJson nspector[T <: Thr ftStruct](codec: Thr ftStructCodec[T]) {
   mport Thr ftJson nspector._

  pr vate[t ] val ser al zer = mkSer al zer(codec)

  /**
   * Convert t  Thr ft object to a JSON representat on based on t 
   * object's codec,  n t  manner of TS mpleJSONProtocol. T  result ng
   * JSON w ll have human-readable f eld na s that match t  f eld
   * na s that  re used  n t  Thr ft def n  on that t  codec was
   * created from, but t  convers on  s lossy, and t  JSON
   * representat on cannot be converted back.
   */
  def toS mpleJson(t: T): JsonNode =
    mapper.readTree(factory.createParser(ser al zer.toBytes(t)))

  /**
   * Selects requested f elds (match ng aga nst t  JSON f elds) from a
   * Thr ft-generated class.
   *
   * Paths are spec f ed as slash-separated str ngs (e.g.,
   * "key1/key2/key3").  f t  path spec f es an array or object,    s
   *  ncluded  n t  output  n JSON format, ot rw se t  s mple value  s
   * converted to a str ng.
   */
  def select( em: T, paths: Seq[Str ng]): Seq[Str ng] = {
    val jsonNode = toS mpleJson( em)
    paths.map {
      _.spl ("/").foldLeft(jsonNode)(_.f ndPath(_)) match {
        case node  f node. sM ss ngNode => "[ nval d-path]"
        case node  f node. sConta nerNode => node.toStr ng
        case node => node.asText
      }
    }
  }

  /**
   * Convert t  g ven Thr ft struct to a human-readable pretty-pr nted
   * JSON representat on. T  JSON cannot be converted back  nto a
   * struct. T  output  s  ntended for debug logg ng or  nteract ve
   *  nspect on of Thr ft objects.
   */
  def prettyPr nt(t: T): Str ng = pr nt(t, true)

  def pr nt(t: T, pretty: Boolean = false): Str ng = {
    val wr er = new Str ngWr er()
    val generator = factory.createGenerator(wr er)
     f (pretty)
      generator.useDefaultPrettyPr nter()
    generator.wr eTree(toS mpleJson(t))
    wr er.toStr ng
  }

  /**
   * Produce a human-readable un f ed d ff of t  json pretty-pr nted
   * representat ons of `a` and `b`.  f t   nputs have t  sa  JSON
   * representat on, t  result w ll be t  empty str ng.
   */
  def d ff(a: T, b: T, contextL nes:  nt = 1): Str ng = {
    val l nesA = prettyPr nt(a).l nes erator.toL st.asJava
    val l nesB = prettyPr nt(b).l nes erator.toL st.asJava
    val patch = D ffUt ls.d ff(l nesA, l nesB)
    D ffUt ls.generateUn f edD ff("a", "b", l nesA, patch, contextL nes).asScala.mkStr ng("\n")
  }
}

object syntax {
  pr vate[t ] object Compan onObjectLoader {
    def load[T](c: Context)( mpl c  t: c.un verse. akTypeTag[T]) = {
      val tSym = t.tpe.typeSymbol
      val compan on = tSym.asClass.compan on
       f (compan on == c.un verse.NoSymbol) {
        c.abort(c.enclos ngPos  on, s"${tSym} has no compan on object")
      } else {
        c.un verse. dent(compan on)
      }
    }
  }

  /**
   * Load t  compan on object of t  na d type para ter and requ re
   *   to be a Thr ftStructCodec. Comp lat on w ll fa l  f t 
   * compan on object  s not a Thr ftStructCodec.
   */
   mpl c  def thr ftStructCodec[T <: Thr ftStruct]: Thr ftStructCodec[T] =
    macro Compan onObjectLoader.load[T]

   mpl c  class Thr ftJsonSyntax[T <: Thr ftStruct](t: T)( mpl c  codec: Thr ftStructCodec[T]) {
    pr vate[t ] def  nspector = Thr ftJson nspector(codec)
    def toS mpleJson: JsonNode =  nspector.toS mpleJson(t)
    def prettyPr nt: Str ng =  nspector.prettyPr nt(t)
    def d ff(ot r: T, contextL nes:  nt = 1): Str ng =
       nspector.d ff(t, ot r, contextL nes)
  }
}
