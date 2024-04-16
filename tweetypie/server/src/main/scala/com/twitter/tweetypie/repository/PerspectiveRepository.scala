package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t  l neserv ce.T  l neServ ce.GetPerspect ves
 mport com.tw ter.t  l neserv ce.thr ftscala.T  l neEntryPerspect ve

object Perspect veRepos ory {

  /**
   * Sa  type as com.tw ter.st ch.t  l neserv ce.T  l neServ ce.GetPerspect ves but w hout
   * us ng Arrow.
   */
  type Type = GetPerspect ves.Query => St ch[T  l neEntryPerspect ve]
}
