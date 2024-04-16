package com.tw ter.product_m xer.core.controllers

 mport com.tw ter.f nagle.http.Request
 mport com.tw ter.scrooge.B naryThr ftStructSer al zer
 mport com.tw ter.scrooge.Thr ft thod
 mport com.tw ter.scrooge.sc ma.Thr ftDef n  ons
 mport com.tw ter.scrooge.sc ma.scrooge.scala.Comp ledScroogeDefBu lder
 mport com.tw ter.scrooge.sc ma.ser al zat on.thr ft.ReferenceResolver
 mport com.tw ter.scrooge.sc ma.ser al zat on.thr ft.Thr ftDef n  onsSer al zer
 mport com.tw ter.scrooge.sc ma.{thr ftscala => THR FT}

/**
 * Endpo nt to expose a M xer's expected query conf gurat on,  nclud ng t  request sc ma.
 *
 * @param debugEndpo nt t  debug Thr ft endpo nt. Pass ng [[None]] d sables t  query debugg ng
 *                      feature.
 * @tparam Serv ce face a thr ft serv ce conta n ng t  [[debugEndpo nt]]
 */
case class GetDebugConf gurat onHandler[Serv ce face](
  thr ft thod: Thr ft thod
)(
   mpl c  val serv ce Face: Man fest[Serv ce face]) {

  //   need to b nary encode t  serv ce def because t  underly ng Thr ft  sn't suff c ently
  // annotated to be ser al zed/deser al zed by Jackson
  pr vate val serv ceDef = {
    val fullServ ceDef n  on: Thr ftDef n  ons.Serv ceDef = Comp ledScroogeDefBu lder
      .bu ld(serv ce Face).as nstanceOf[Thr ftDef n  ons.Serv ceDef]

    val endpo ntDef n  on: Thr ftDef n  ons.Serv ceEndpo ntDef =
      fullServ ceDef n  on.endpo ntsByNa (thr ft thod.na )

    // Create a serv ce def n  on wh ch just conta ns t  debug endpo nt. At a bare m n mum,   need
    // to g ve callers a way to  dent fy t  debug endpo nt. Send ng back all t  endpo nts  s
    // redundant.
    val serv ceDef n  on: Thr ftDef n  ons.Serv ceDef =
      fullServ ceDef n  on.copy(endpo nts = Seq(endpo ntDef n  on))

    val thr ftDef n  onsSer al zer = {
      //   don't make use of references but a reference resolver  s requ red by t  Scrooge AP 
      val noopReferenceResolver: ReferenceResolver =
        (_: THR FT.ReferenceDef) => throw new Except on("no references")

      new Thr ftDef n  onsSer al zer(noopReferenceResolver, enableReferences = false)
    }

    val thr ftB narySer al zer = B naryThr ftStructSer al zer.apply(THR FT.Def n  on)

    val ser al zedServ ceDef = thr ftDef n  onsSer al zer(serv ceDef n  on)

    thr ftB narySer al zer.toBytes(ser al zedServ ceDef)
  }

  def apply(request: Request): DebugConf gurat onResponse =
    DebugConf gurat onResponse(thr ft thod.na , serv ceDef)
}

case class DebugConf gurat onResponse(
  debugEndpo ntNa : Str ng,
  serv ceDef n  on: Array[Byte])
