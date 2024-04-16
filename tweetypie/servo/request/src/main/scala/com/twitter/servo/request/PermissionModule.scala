package com.tw ter.servo.request

 mport com.tw ter.conf g.yaml.YamlMap
 mport com.tw ter.ut l.Try

/**
 * Module for def n ng a set of perm ss ons. T   s s m lar to
 * Enu rat on  n t  scala standard l brary.
 *
 * To use,  nstant ate a subclass:
 *
 * {{{
 * object  Perm ss ons extends Perm ss onModule {
 *   val Eat = create("eat")
 *   val Dr nk = create("dr nk")
 * }
 * }}}
 *
 * Perm ss ons only support one k nd of author zat on, wh ch  s that
 *   can c ck w t r a holder of perm ss ons has all of t 
 * perm ss ons  n a part cular set.
 *
 * {{{
 * val snack =  Perm ss ons.Eat
 * val d nner =  Perm ss ons.Eat un on  Perm ss ons.Dr nk
 * val canEat =  Perm ss ons.Eat
 * d nner sat sf edBy canEat // false
 * snack sat sf edBy canEat // true
 * }}}
 *
 * Each  nstance w ll have  s own d st nct perm ss on type, so    s
 * not poss ble to confuse t  perm ss ons def ned  n d fferent
 * modules.
 *
 * {{{
 * scala> object P1 extends Perm ss onModule { val Read = create("read") }
 * scala> object P2 extends Perm ss onModule { val Read = create("read") }
 * scala> P1.Read sat sf edBy P2.Read
 * error: type m smatch;
 * found   : P2.Perm ss ons
 * requ red: P1.Perm ss ons
 *              P1.Read sat sf edBy P2.Read
 * }}}
 *
 * Once an  nstance has been created,   w ll not be poss ble to
 * create new perm ss ons. T   ntent on  s that all perm ss ons w ll
 * be created at object  n  al zat on t  .
 *
 * Each  nstance also suppl es funct onal y for access ng perm ss ons
 * by na ,  nclud ng pars ng cl ent perm ss on maps from YAML.
 */
tra  Perm ss onModule {
  // T  var  s used dur ng object  n  al zat on to collect all of
  // t  perm ss ons that are created  n t  subclass. T  lazy
  //  n  al zer for `All` w ll set t  to null as a s de-effect, so
  // that furt r perm ss on creat ons are not allo d.
  @volat le pr vate[t ] var allPerms: Set[Str ng] = Set.empty

  /**
   * Create a new Perm ss on w h t  g ven na . Note that "*"  s a
   * reversed str ng for `All` perm ss ons, thus   can not be
   * used as t  na  of an  nd v dual perm ss on.
   *
   * T   thod must be called before `All`  s accessed.
   * T   ntent on  s that   should be called as part of
   * object  n  al zat on.
   *
   * Note that so   thods of Perm ss onModule access `All`, so    s
   * best to create all of y  perm ss ons before do ng anyth ng
   * else.
   *
   * @throws Runt  Except on:  f    s called after `All` has been
   *    n  al zed.
   */
  protected def create(na : Str ng) = {
    synchron zed {
       f (allPerms == null) {
        throw new Runt  Except on("Perm ss on creat on after  n  al zat on")
      }

      allPerms = allPerms un on Set(na )
    }

    new Perm ss ons(Set(na ))
  }

  /**
   * Get a set of perm ss ons w h t  s ngle perm ss on by na .  
   * w ll return None  f t re  s no perm ss on by that na .
   *
   * No perm ss ons may be def ned after t   thod  s called.
   */
  def get(na : Str ng): Opt on[Perm ss ons] = All.get(na )

  /**
   * Get t  set of perm ss ons that conta ns that s ngle perm ss on
   * by na .
   *
   * @throws Runt  Except on  f t re  s no def ned perm ss on w h
   *   t  na .
   *
   * No perm ss ons may be def ned after t   thod  s called.
   */
  def apply(na : Str ng): Perm ss ons =
    get(na ) match {
      case None => throw new Runt  Except on("Unknown perm ss on: " + na )
      case So (p) => p
    }

  /**
   * No perm ss ons (requ red or  ld)
   */
  val Empty: Perm ss ons = new Perm ss ons(Set.empty)

  /**
   * All def ned perm ss ons.
   *
   * No perm ss ons may be def ned after t  value  s  n  al zed.
   */
  lazy val All: Perm ss ons = {
    val p = new Perm ss ons(allPerms)
    allPerms = null
    p
  }

  /**
   * Load perm ss ons from a YAML map.
   *
   * No perm ss ons may be def ned after t   thod  s called.
   *
   * @return a map from cl ent  dent f er to perm ss on set.
   * @throws Runt  Except on w n t  perm ss on from t  Map  s not def ned.
   */
  def fromYaml(m: YamlMap): Try[Map[Str ng, Perm ss ons]] =
    Try {
      m.keys.map { k =>
        k -> fromSeq((m yamlL st k).map { _.toStr ng })
      }.toMap
    }

  /**
   * Load perm ss ons from map.
   *
   * No perm ss ons may be def ned after t   thod  s called.
   *
   * @param m a map from cl ent  dent f er to a set of perm ss on str ngs
   *
   * @return a map from cl ent  dent f er to perm ss on set.
   * @throws Runt  Except on w n t  perm ss on from t  Map  s not def ned.
   */
  def fromMap(m: Map[Str ng, Seq[Str ng]]): Try[Map[Str ng, Perm ss ons]] =
    Try {
      m.map { case (k, v) => k -> fromSeq(v) }
    }

  /**
   * Load perm ss ons from seq.
   *
   * No perm ss ons may be def ned after t   thod  s called.
   *
   * @param sequence a Seq of perm ss on str ngs
   *
   * @return a perm ss on set.
   * @throws Runt  Except on w n t  perm ss on  s not def ned.
   */
  def fromSeq(perm ss onStr ngs: Seq[Str ng]): Perm ss ons =
    perm ss onStr ngs.foldLeft(Empty) { (p, v) =>
      v match {
        case "all"  f get("all"). sEmpty => All
        case ot r => p un on apply(ot r)
      }
    }

  /**
   * Author zer based on a Perm ss ons for RPC  thod na s.
   * @param requ redPerm ss ons
   *   map of RPC  thod na s to Perm ss ons requ red for that RPC
   * @param cl entPerm ss ons
   *   map of Cl ent d to Perm ss ons a cl ent has
   */
  def perm ss onBasedAuthor zer(
    requ redPerm ss ons: Map[Str ng, Perm ss ons],
    cl entPerm ss ons: Map[Str ng, Perm ss ons]
  ): Cl entRequestAuthor zer =
    Cl entRequestAuthor zer.f ltered { ( thodNa , cl ent d) =>
      requ redPerm ss ons.get( thodNa ) ex sts {
        _ sat sf edBy cl entPerm ss ons.getOrElse(cl ent d, Empty)
      }
    }

  /**
   * A set of perm ss ons. T  can represent e  r perm ss ons that
   * are requ red to perform an act on, or perm ss ons that are  ld
   * by a cl ent.
   *
   * T  type cannot be  nstant ated d rectly. Use t   thods of
   * y  subclass of Perm ss onModule to do so.
   */
  class Perm ss ons pr vate[Perm ss onModule] (pr vate[Perm ss onModule] val permSet: Set[Str ng]) {

    /**
     * Does t  suppl ed set of  ld perm ss ons sat sfy t 
     * requ re nts of t  set of perm ss ons?
     *
     * For example,  f t  set of perm ss ons  s Set("read"), and t 
     * ot r set of perm ss ons  s Set("read", "wr e"), t n t 
     * ot r set of perm ss ons sat sf es t  set.
     */
    def sat sf edBy(ot r: Perm ss ons): Boolean = permSet subsetOf ot r.permSet

    overr de def equals(ot r: Any): Boolean =
      ot r match {
        case p: Perm ss ons => p.permSet == permSet
        case _ => false
      }

    overr de lazy val hashCode:  nt = 5 + 37 * permSet.hashCode

    /**
     * Get a s ngle perm ss on
     */
    def get(permNa : Str ng): Opt on[Perm ss ons] =
       f (permSet conta ns permNa ) So (new Perm ss ons(Set(permNa ))) else None

    /**
     * Create a new perm ss on set that holds t  perm ss ons of t 
     * object as  ll as t  perm ss ons of t  ot r object.
     */
    def un on(ot r: Perm ss ons): Perm ss ons = new Perm ss ons(permSet un on ot r.permSet)

    overr de def toStr ng: Str ng = "Perm ss ons(%s)".format(permSet.mkStr ng(", "))
  }
}
