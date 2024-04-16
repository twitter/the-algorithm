package com.tw ter.t etyp e
package serv ce

/**
 * An author zer for determ n ng  f a request to a
 *  thod should be rejected.
 *
 * T  class  s  n t  sp r  of servo.request.Cl entRequestAuthor zer.
 * T  d fference  s Cl entRequestAuthor zer only operates
 * on two p eces of  nformat on, cl ent d and a  thod na .
 *
 * T  class can be used to create a more complex author zer that
 * operates on t  spec f cs of a request. e.g, an
 * author zer that d sallows certa n cl ents from pass ng
 * certa n opt onal flags.
 *
 * Note: W h so  work, Cl entRequestAuthor zer could be
 * general zed to support cases l ke t .  f   end up mak ng
 * more  thod author zers   m ght be worth   to
 * go that route.
 */
abstract class  thodAuthor zer[T]() {
  def apply(request: T, cl ent d: Str ng): Future[Un ]

  /**
   * Created dec dered  thodAuthor zer
   *  f t  dec der  s off   w ll execute
   *  thodAuthor zer.un , wh ch always succeeds.
   */
  def enabledBy(dec der: Gate[Un ]):  thodAuthor zer[T] =
     thodAuthor zer.select(dec der, t ,  thodAuthor zer.un )

  /**
   * Transform t   thodAuthor zer[T]  nto a  thodAuthor zer[A]
   * by prov d ng a funct on from A => T
   */
  def contramap[A](f: A => T):  thodAuthor zer[A] =
     thodAuthor zer[A] { (request, cl ent d) => t (f(request), cl ent d) }
}

object  thodAuthor zer {

  /**
   * @param f an author zat on funct on that returns
   * Future.Un   f t  request  s author zed, and Future.except on()
   *  f t  request  s not author zed.
   *
   * @return An  nstance of  thodAuthor zer w h an apply  thod
   * that returns f
   */
  def apply[T](f: (T, Str ng) => Future[Un ]):  thodAuthor zer[T] =
    new  thodAuthor zer[T]() {
      def apply(request: T, cl ent d: Str ng): Future[Un ] = f(request, cl ent d)
    }

  /**
   * @param author zers A seq of  thodAuthor zers to be
   * composed  nto one.
   * @return A  thodAuthor zer that sequent ally executes
   * all of t  author zers
   */
  def all[T](author zers: Seq[ thodAuthor zer[T]]):  thodAuthor zer[T] =
     thodAuthor zer { (request, cl ent d) =>
      author zers.foldLeft(Future.Un ) {
        case (f, author ze) => f.before(author ze(request, cl ent d))
      }
    }

  /**
   * @return A  thodAuthor zer that always returns Future.Un 
   * Useful  f   need to dec der off y   thodAuthor zer
   * and replace   w h one that always passes.
   */
  def un [T]:  thodAuthor zer[T] =  thodAuthor zer { (request, cl ent) => Future.Un  }

  /**
   * @return A  thodAuthor zer that sw c s bet en two prov ded
   *  thodAuthor zers depend ng on a dec der.
   */
  def select[T](
    dec der: Gate[Un ],
     fTrue:  thodAuthor zer[T],
     fFalse:  thodAuthor zer[T]
  ):  thodAuthor zer[T] =
     thodAuthor zer { (request, cl ent) =>
      dec der.p ck(
         fTrue(request, cl ent),
         fFalse(request, cl ent)
      )
    }
}
