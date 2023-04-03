packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.icon.HorizonIcon

caselon class FelonelondbackAction(
  felonelondbackTypelon: FelonelondbackTypelon,
  prompt: Option[String],
  confirmation: Option[String],
  childFelonelondbackActions: Option[Selonq[ChildFelonelondbackAction]],
  felonelondbackUrl: Option[String],
  hasUndoAction: Option[Boolelonan],
  confirmationDisplayTypelon: Option[ConfirmationDisplayTypelon],
  clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  icon: Option[HorizonIcon],
  richBelonhavior: Option[RichFelonelondbackBelonhavior],
  subprompt: Option[String],
  elonncodelondFelonelondbackRelonquelonst: Option[String])

caselon class ChildFelonelondbackAction(
  felonelondbackTypelon: FelonelondbackTypelon,
  prompt: Option[String],
  confirmation: Option[String],
  felonelondbackUrl: Option[String],
  hasUndoAction: Option[Boolelonan],
  confirmationDisplayTypelon: Option[ConfirmationDisplayTypelon],
  clielonntelonvelonntInfo: Option[ClielonntelonvelonntInfo],
  icon: Option[HorizonIcon],
  richBelonhavior: Option[RichFelonelondbackBelonhavior],
  subprompt: Option[String])
