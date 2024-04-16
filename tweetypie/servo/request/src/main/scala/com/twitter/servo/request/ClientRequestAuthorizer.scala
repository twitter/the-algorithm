package com.tw ter.servo.request

 mport com.tw ter.servo.gate.RateL m  ngGate
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.ut l.Future

/**
 * Collects per-request stats by  thod-na  and cl ent.
 */
tra  Cl entRequestAuthor zer extends ((Str ng, Opt on[Str ng]) => Future[Un ]) { self =>

  /**
   * @param  thodNa  t  na  of t  Serv ce  thod be ng called
   * @param cl ent dStrOpt an Opt on of t  str ng value of t  or g nat ng
   *   request's Cl ent d
   */
  def apply( thodNa : Str ng, cl ent dStrOpt: Opt on[Str ng]): Future[Un ]

  /**
   * Compose t  author zer w h anot r so that one  s appl ed after t  ot r.
   *
   * T  resultant author zer requ res both underly ng author zers to succeed  n
   * order to author ze a request.
   */
  def andT n(ot r: Cl entRequestAuthor zer) = new Cl entRequestAuthor zer {
    overr de def apply( thodNa : Str ng, cl ent dStrOpt: Opt on[Str ng]): Future[Un ] = {
      self.apply( thodNa , cl ent dStrOpt) flatMap { _ =>
        ot r( thodNa , cl ent dStrOpt)
      }
    }
  }
}

object Cl entRequestAuthor zer {
  case class Unauthor zedExcept on(msg: Str ng) extends Except on(msg)

  protected[t ] val noCl ent dExcept on =
    Future.except on(new Unauthor zedExcept on("No Cl ent d spec f ed"))
  protected[t ] val unauthor zedExcept on =
    new Unauthor zedExcept on("Y  Cl ent d  s not author zed.")
  protected[t ] val overRateL m Except on =
    new Unauthor zedExcept on("Y  Cl ent d  s over t  allo d rate l m .")

  /**
   *  ncre nt stats counters for t  request.
   *
   * Note that Cl entRequestAuthor zer.observed doesn't compose  n t  sa  fash on
   * as ot r author zers v a `andT n`.  n order to observe author zat on results,
   * pass  n an underly ng author zer as an argu nt to observed.
   */
  def observed(
    underly ngAuthor zer: Cl entRequestAuthor zer,
    observer: Cl entRequestObserver
  ) = new Cl entRequestAuthor zer {
    overr de def apply( thodNa : Str ng, cl ent dStrOpt: Opt on[Str ng]): Future[Un ] = {
      val cl ent dStr = cl ent dStrOpt.getOrElse("no_cl ent_ d")

      observer( thodNa , cl ent dStrOpt map { Seq(_) })

      underly ngAuthor zer( thodNa , cl ent dStrOpt) onFa lure { _ =>
        observer.unauthor zed( thodNa , cl ent dStr)
      } onSuccess { _ =>
        observer.author zed( thodNa , cl ent dStr)
      }
    }
  }

  def observed(observer: Cl entRequestObserver): Cl entRequestAuthor zer =
    observed(Cl entRequestAuthor zer.perm ss ve, observer)

  /**
   * Lets all requests through.
   */
  def perm ss ve = new Cl entRequestAuthor zer {
    overr de def apply( thodNa : Str ng, cl ent dStrOpt: Opt on[Str ng]) = Future.Done
  }

  /**
   * A Gener c Author zer that allows   to pass  n y  own author zer funct on (f lter).
   * T  f lter should take  n  thodNa  and cl ent d and return a Boolean dec s on
   *
   * Note: Requ res requests to have Cl ent ds.
   * @param except on return t  except on  f t  request does not pass t  f lter
   */
  def f ltered(
    f lter: (Str ng, Str ng) => Boolean,
    except on: Except on = unauthor zedExcept on
  ): Cl entRequestAuthor zer =
    new Cl entRequestAuthor zer {
      val futureExcept on = Future.except on(except on)

      overr de def apply( thodNa : Str ng, cl ent dStrOpt: Opt on[Str ng]): Future[Un ] = {
        cl ent dStrOpt match {
          case So (cl ent dStr) =>
             f (f lter( thodNa , cl ent dStr))
              Future.Done
            else
              futureExcept on
          case None =>
            noCl ent dExcept on
        }
      }
    }

  /**
   * Author zes cl ent requests based on a allowl st of Cl ent d str ngs.
   */
  def allowl sted(allowl st: Set[Str ng]): Cl entRequestAuthor zer =
    f ltered { (_, cl ent dStr) =>
      allowl st.conta ns(cl ent dStr)
    }

  /**
   * Author zes requests  f and only  f t y have an assoc ated Cl ent d.
   */
  def w hCl ent d: Cl entRequestAuthor zer = f ltered { (_, _) =>
    true
  }

  /**
   * Consult a (presumably) Dec der-backed pred cate to author ze requests by Cl ent d.
   * @param except on return t  except on  f t  request does not pass t  f lter
   */
  def dec derable(
     sAva lable: Str ng => Boolean,
    except on: Except on = unauthor zedExcept on
  ): Cl entRequestAuthor zer =
    f ltered(
      { (_, cl ent dStr) =>
         sAva lable(cl ent dStr)
      },
      except on
    )

  /**
   * S mple rate l m er for unknown cl ent  ds. Useful for lett ng new cl ents
   * send so  traff c w hout t  r sk of be ng overrun by requests.
   *
   * @param l m PerSecond Number of calls per second   can tolerate
   */
  def rateL m ed(l m PerSecond: Double): Cl entRequestAuthor zer = {
    gated(RateL m  ngGate.un form(l m PerSecond), overRateL m Except on)
  }

  /**
   * S mple Gate based author zer, w ll author ze accord ng to t  result of t  gate regardless
   * of t  cl ent/ thod na 
   */
  def gated(
    gate: Gate[Un ],
    except on: Except on = unauthor zedExcept on
  ): Cl entRequestAuthor zer = {
    dec derable(_ => gate(), except on)
  }

  /**
   * @return A Cl entRequestAuthor zer that sw c s bet en two prov ded
   * Cl entRequestAuthor zers depend ng on a dec der.
   */
  def select(
    dec der: Gate[Un ],
     fTrue: Cl entRequestAuthor zer,
     fFalse: Cl entRequestAuthor zer
  ): Cl entRequestAuthor zer =
    new Cl entRequestAuthor zer {
      overr de def apply( thodNa : Str ng, cl ent dStrOpt: Opt on[Str ng]): Future[Un ] =
        dec der.p ck(
           fTrue( thodNa , cl ent dStrOpt),
           fFalse( thodNa , cl ent dStrOpt)
        )
    }
}
