packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata

trait HasClielonntelonvelonntInfo {
  delonf clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo]
}

/**
 * Information uselond to build Clielonnt elonvelonnts
 * @selonelon [[http://go/clielonnt-elonvelonnts]]
 */
caselon class ClielonntelonvelonntInfo(
  componelonnt: Option[String],
  elonlelonmelonnt: Option[String],
  delontails: Option[ClielonntelonvelonntDelontails],
  action: Option[String],
  elonntityTokelonn: Option[String])

/**
 * Additional clielonnt elonvelonnts fielonlds
 *
 * @notelon if a fielonld from [[http://go/clielonnt_app.thrift]] is nelonelondelond but is not helonrelon
 *       contact thelon `#product-mixelonr` telonam to havelon it addelond.
 */
caselon class ClielonntelonvelonntDelontails(
  convelonrsationDelontails: Option[ConvelonrsationDelontails],
  timelonlinelonsDelontails: Option[TimelonlinelonsDelontails],
  articlelonDelontails: Option[ArticlelonDelontails],
  livelonelonvelonntDelontails: Option[LivelonelonvelonntDelontails],
  commelonrcelonDelontails: Option[CommelonrcelonDelontails])
