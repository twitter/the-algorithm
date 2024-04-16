package com.tw ter.t etyp e.repos ory

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.T et d
 mport com.tw ter.t etyp e.core.T etResult

object T etResultRepos ory {
  type Type = (T et d, T etQuery.Opt ons) => St ch[T etResult]

  /**
   * Short-c rcu s t  request of  nval d t et  ds (`<= 0`) by  m d ately throw ng `NotFound`.
   */
  def shortC rcu  nval d ds(repo: Type): Type = {
    case (t et d, _)  f t et d <= 0 => St ch.NotFound
    case (t et d, opt ons) => repo(t et d, opt ons)
  }
}
