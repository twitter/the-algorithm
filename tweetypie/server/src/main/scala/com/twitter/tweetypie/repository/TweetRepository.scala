package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._

object T etRepos ory {
  type Type = (T et d, T etQuery.Opt ons) => St ch[T et]
  type Opt onal = (T et d, T etQuery.Opt ons) => St ch[Opt on[T et]]

  def t etGetter(repo: Opt onal, opts: T etQuery.Opt ons): FutureArrow[T et d, Opt on[T et]] =
    FutureArrow(t et d => St ch.run(repo(t et d, opts)))

  def t etGetter(repo: Opt onal): FutureArrow[(T et d, T etQuery.Opt ons), Opt on[T et]] =
    FutureArrow { case (t et d, opts) => St ch.run(repo(t et d, opts)) }

  /**
   * Converts a `T etResultRepos ory.Type`-typed repo to an `T etRepos ory.Type`-typed repo.
   */
  def fromT etResult(repo: T etResultRepos ory.Type): Type =
    (t et d, opt ons) => repo(t et d, opt ons).map(_.value.t et)

  /**
   * Converts a `Type`-typed repo to an `Opt onal`-typed
   * repo, w re NotFound or f ltered t ets are returned as `None`.
   */
  def opt onal(repo: Type): Opt onal =
    (t et d, opt ons) =>
      repo(t et d, opt ons).l ftToOpt on { case NotFound | (_: F lteredState) => true }
}
