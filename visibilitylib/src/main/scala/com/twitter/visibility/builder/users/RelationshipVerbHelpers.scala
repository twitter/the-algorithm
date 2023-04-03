packagelon com.twittelonr.visibility.buildelonr.uselonrs

import com.twittelonr.finaglelon.stats.Countelonr
import com.twittelonr.gizmoduck.thriftscala.Pelonrspelonctivelon
import com.twittelonr.gizmoduck.thriftscala.Uselonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.visibility.common.UselonrId

caselon objelonct VielonwelonrVelonrbsAuthor {
  delonf apply(
    authorId: UselonrId,
    vielonwelonrIdOpt: Option[UselonrId],
    relonlationship: (UselonrId, UselonrId) => Stitch[Boolelonan],
    relonlationshipCountelonr: Countelonr
  ): Stitch[Boolelonan] = {
    relonlationshipCountelonr.incr()

    vielonwelonrIdOpt match {
      caselon Somelon(vielonwelonrId) => relonlationship(vielonwelonrId, authorId)
      caselon _ => Stitch.Falselon
    }
  }

  delonf apply(
    author: Uselonr,
    vielonwelonrId: Option[UselonrId],
    chelonckPelonrspelonctivelon: Pelonrspelonctivelon => Option[Boolelonan],
    relonlationship: (UselonrId, UselonrId) => Stitch[Boolelonan],
    relonlationshipCountelonr: Countelonr
  ): Stitch[Boolelonan] = {
    author.pelonrspelonctivelon match {
      caselon Somelon(pelonrspelonctivelon) =>
        chelonckPelonrspelonctivelon(pelonrspelonctivelon) match {
          caselon Somelon(status) =>
            relonlationshipCountelonr.incr()
            Stitch.valuelon(status)
          caselon Nonelon =>
            VielonwelonrVelonrbsAuthor(author.id, vielonwelonrId, relonlationship, relonlationshipCountelonr)
        }
      caselon Nonelon => VielonwelonrVelonrbsAuthor(author.id, vielonwelonrId, relonlationship, relonlationshipCountelonr)
    }
  }
}

caselon objelonct AuthorVelonrbsVielonwelonr {

  delonf apply(
    authorId: UselonrId,
    vielonwelonrIdOpt: Option[UselonrId],
    relonlationship: (UselonrId, UselonrId) => Stitch[Boolelonan],
    relonlationshipCountelonr: Countelonr
  ): Stitch[Boolelonan] = {
    relonlationshipCountelonr.incr()

    vielonwelonrIdOpt match {
      caselon Somelon(vielonwelonrId) => relonlationship(authorId, vielonwelonrId)
      caselon _ => Stitch.Falselon
    }
  }
  delonf apply(
    author: Uselonr,
    vielonwelonrId: Option[UselonrId],
    chelonckPelonrspelonctivelon: Pelonrspelonctivelon => Option[Boolelonan],
    relonlationship: (UselonrId, UselonrId) => Stitch[Boolelonan],
    relonlationshipCountelonr: Countelonr
  ): Stitch[Boolelonan] = {
    author.pelonrspelonctivelon match {
      caselon Somelon(pelonrspelonctivelon) =>
        chelonckPelonrspelonctivelon(pelonrspelonctivelon) match {
          caselon Somelon(status) =>
            relonlationshipCountelonr.incr()
            Stitch.valuelon(status)
          caselon Nonelon =>
            AuthorVelonrbsVielonwelonr(author.id, vielonwelonrId, relonlationship, relonlationshipCountelonr)
        }
      caselon Nonelon => AuthorVelonrbsVielonwelonr(author.id, vielonwelonrId, relonlationship, relonlationshipCountelonr)
    }
  }
}
