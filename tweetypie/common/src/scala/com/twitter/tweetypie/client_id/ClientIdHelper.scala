package com.tw ter.t etyp e.cl ent_ d

 mport com.tw ter.f nagle.mtls.aut nt cat on.EmptyServ ce dent f er
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.transport.S2STransport
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.strato.access.Access
 mport com.tw ter.strato.access.Access.ForwardedServ ce dent f er

object Cl ent d lper {

  val UnknownCl ent d = "unknown"

  def default: Cl ent d lper = new Cl ent d lper(UseTransportServ ce dent f er)

  /**
   * Tr ms off t  last .ele nt, wh ch  s usually .prod or .stag ng
   */
  def getCl ent dRoot(cl ent d: Str ng): Str ng =
    cl ent d.last ndexOf('.') match {
      case -1 => cl ent d
      case  dx => cl ent d.substr ng(0,  dx)
    }

  /**
   * Returns t  last .ele nt w hout t  '.'
   */
  def getCl ent dEnv(cl ent d: Str ng): Str ng =
    cl ent d.last ndexOf('.') match {
      case -1 => cl ent d
      case  dx => cl ent d.substr ng( dx + 1)
    }

  pr vate[cl ent_ d] def asCl ent d(s: Serv ce dent f er): Str ng = s"${s.serv ce}.${s.env ron nt}"
}

class Cl ent d lper(serv ce dent f erStrategy: Serv ce dent f erStrategy) {

  pr vate[cl ent_ d] val ProcessPathPref x = "/p/"

  /**
   * T  effect ve cl ent  d  s used for request author zat on and  tr cs
   * attr but on. For calls to T etyp e's thr ft AP , t  thr ft Cl ent d
   *  s used and  s expected  n t  form of "serv ce-na .env". Federated
   * Strato cl ents don't support conf gured Cl ent ds and  nstead prov de
   * a "process path" conta n ng  nstance-spec f c  nformat on. So for
   * calls to t  federated AP ,   compute an effect ve cl ent  d from
   * t  Serv ce dent f er,  f present,  n Strato's Access pr nc ples. T 
   *  mple ntat on avo ds comput ng t   dent f er unless necessary,
   * s nce t   thod  s  nvoked on every request.
   */
  def effect veCl ent d: Opt on[Str ng] = {
    val cl ent d: Opt on[Str ng] = Cl ent d.current.map(_.na )
    cl ent d
    // Exclude process paths because t y are  nstance-spec f c and aren't
    // supported by t etyp e for author zat on or  tr cs purposes.
      .f lterNot(_.startsW h(ProcessPathPref x))
      // Try comput ng a value from t  Serv ce d  f t  thr ft
      // Cl ent d  s undef ned or unsupported.
      .orElse(serv ce dent f erStrategy.serv ce dent f er.map(Cl ent d lper.asCl ent d))
      // Ult mately fall back to t  Cl ent d value, even w n g ven an
      // unsupported format, so that error text and debug logs  nclude
      // t  value passed by t  caller.
      .orElse(cl ent d)
  }

  def effect veCl ent dRoot: Opt on[Str ng] = effect veCl ent d.map(Cl ent d lper.getCl ent dRoot)

  def effect veServ ce dent f er: Opt on[Serv ce dent f er] =
    serv ce dent f erStrategy.serv ce dent f er
}

/** Log c how to f nd a [[Serv ce dent f er]] for t  purpose of craft ng a cl ent  D. */
tra  Serv ce dent f erStrategy {
  def serv ce dent f er: Opt on[Serv ce dent f er]

  /**
   * Returns t  only ele nt of g ven [[Set]] or [[None]].
   *
   * T  ut l y  s used defens vely aga nst a set of pr nc pals collected
   * from [[Access.getPr nc pals]]. Wh le t  contract  s that t re should be at most one
   *  nstance of each pr nc pal k nd present  n that set,  n pract ce that has not been t  case
   * always. T  safest strategy to  n that case  s to abandon a set completely  f more than
   * one pr nc pals are compet ng.
   */
  f nal protected def onlyEle nt[T](set: Set[T]): Opt on[T] =
     f (set.s ze <= 1) {
      set. adOpt on
    } else {
      None
    }
}

/**
 * P cks [[Serv ce dent f er]] from F nagle SSL Transport,  f one ex sts.
 *
 * T  works for both Thr ft AP  calls as  ll as StratoFed AP  calls. Strato's
 * [[Access#getPr nc pals]] collect on, wh ch would typ cally be consulted by StratoFed
 * column log c, conta ns t  sa  [[Serv ce dent f er]] der ved from t  F nagle SSL
 * transport, so t re's no need to have separate strateg es for Thr ft vs StratoFed
 * calls.
 *
 * T   s t  default behav or of us ng [[Serv ce dent f er]] for comput ng cl ent  D.
 */
pr vate[cl ent_ d] class UseTransportServ ce dent f er(
  // overr dable for test ng
  getPeerServ ce dent f er: => Serv ce dent f er,
) extends Serv ce dent f erStrategy {
  overr de def serv ce dent f er: Opt on[Serv ce dent f er] =
    getPeerServ ce dent f er match {
      case EmptyServ ce dent f er => None
      case s  => So (s )
    }
}

object UseTransportServ ce dent f er
    extends UseTransportServ ce dent f er(S2STransport.peerServ ce dent f er)

/**
 * P cks [[ForwardedServ ce dent f er]] from Strato pr nc pals for cl ent  D
 *  f [[Serv ce dent f er]] po nts at call com ng from Strato.
 *  f not present, falls back to [[UseTransportServ ce dent f er]] behav or.
 *
 * T etyp e ut l zes t  strategy to p ck [[Serv ce dent f er]] for t  purpose
 * of generat ng a cl ent  D w n t  cl ent  D  s absent or unknown.
 * [[PreferForwardedServ ce dent f erForStrato]] looks for t  [[ForwardedServ ce dent f er]]
 * values set by stratoserver request.
 * T  reason  s, stratoserver  s effect vely a condu , forward ng t  [[Serv ce dent f er]]
 * of t  _actual cl ent_ that  s call ng stratoserver.
 * Any d rect callers not go ng through stratoserver w ll default to [[Serv ce dentf er]].
 */
pr vate[cl ent_ d] class PreferForwardedServ ce dent f erForStrato(
  // overr dable for test ng
  getPeerServ ce dent f er: => Serv ce dent f er,
) extends Serv ce dent f erStrategy {
  val useTransportServ ce dent f er =
    new UseTransportServ ce dent f er(getPeerServ ce dent f er)

  overr de def serv ce dent f er: Opt on[Serv ce dent f er] =
    useTransportServ ce dent f er.serv ce dent f er match {
      case So (serv ce dent f er)  f  sStrato(serv ce dent f er) =>
        onlyEle nt(
          Access.getPr nc pals
            .collect {
              case forwarded: ForwardedServ ce dent f er =>
                forwarded.serv ce dent f er.serv ce dent f er
            }
        ).orElse(useTransportServ ce dent f er.serv ce dent f er)
      case ot r => ot r
    }

  /**
   * Strato uses var ous serv ce na s l ke "stratoserver" and "stratoserver-pat ent".
   * T y all do start w h "stratoserver" though, so at t  po nt of  mple nt ng,
   * t  safest bet to recogn ze strato  s to look for t  pref x.
   *
   * T  also works for staged strato  nstances (wh ch   should), desp e allow ng
   * for techn cally any caller to force t  strategy, by creat ng serv ce cert f cate
   * w h t  serv ce na .
   */
  pr vate def  sStrato(serv ce dent f er: Serv ce dent f er): Boolean =
    serv ce dent f er.serv ce.startsW h("stratoserver")
}

object PreferForwardedServ ce dent f erForStrato
    extends PreferForwardedServ ce dent f erForStrato(S2STransport.peerServ ce dent f er)

/**
 * [[Serv ce dent f erStrategy]] wh ch d spatc s bet en two delegates based on t  value
 * of a un ary dec der every t   [[serv ce dent f er]]  s called.
 */
class Cond  onalServ ce dent f erStrategy(
  pr vate val cond  on: Gate[Un ],
  pr vate val  fTrue: Serv ce dent f erStrategy,
  pr vate val  fFalse: Serv ce dent f erStrategy)
    extends Serv ce dent f erStrategy {

  overr de def serv ce dent f er: Opt on[Serv ce dent f er] =
     f (cond  on()) {
       fTrue.serv ce dent f er
    } else {
       fFalse.serv ce dent f er
    }
}
