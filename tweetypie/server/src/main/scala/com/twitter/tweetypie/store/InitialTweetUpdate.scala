package com.tw ter.t etyp e.store

 mport com.tw ter.t etyp e.T et
 mport com.tw ter.t etyp e.serverut l.ExtendedT et tadataBu lder
 mport com.tw ter.t etyp e.thr ftscala.Ed Control
 mport com.tw ter.t etyp e.thr ftscala. n  alT etUpdateRequest
 mport com.tw ter.t etyp e.ut l.Ed ControlUt l

/* Log c to update t   n  al t et w h new  nformat on w n that t et  s ed ed */
object  n  alT etUpdate {

  /* G ven t   n  al t et and update request, copy updated ed 
   * related f elds onto  .
   */
  def updateT et( n  alT et: T et, request:  n  alT etUpdateRequest): T et = {

    // compute a new ed  control  n  al w h updated l st of ed  t et  ds
    val ed Control: Ed Control. n  al =
      Ed ControlUt l.ed ControlFor n  alT et( n  alT et, request.ed T et d).get()

    // compute t  correct extended  tadata for a permal nk
    val extendedT et tadata =
      request.selfPermal nk.map(l nk => ExtendedT et tadataBu lder( n  alT et, l nk))

     n  alT et.copy(
      selfPermal nk =  n  alT et.selfPermal nk.orElse(request.selfPermal nk),
      ed Control = So (ed Control),
      extendedT et tadata =  n  alT et.extendedT et tadata.orElse(extendedT et tadata)
    )
  }
}
