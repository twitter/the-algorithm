package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}

/**
 * Repos ory for fetch ng User ds that have un nt oned t mselves from a conversat on.
 */
object Un nt onedEnt  esRepos ory {
  type Type = (Conversat on d, Seq[User d]) => St ch[Opt on[Seq[User d]]]

  val column = "consu r-pr vacy/ nt ons-manage nt/getUn nt onedUsersFromConversat on"
  case class GetUn nt onV ew(user ds: Opt on[Seq[Long]])

  def apply(cl ent: StratoCl ent): Type = {
    val fetc r: Fetc r[Long, GetUn nt onV ew, Seq[Long]] =
      cl ent.fetc r[Long, GetUn nt onV ew, Seq[Long]](column)

    (conversat on d, user ds) =>
       f (user ds.nonEmpty) {
        fetc r.fetch(conversat on d, GetUn nt onV ew(So (user ds))).map(_.v)
      } else {
        St ch.None
      }
  }
}
