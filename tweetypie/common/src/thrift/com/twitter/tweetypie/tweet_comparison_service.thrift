na space java com.tw ter.t etyp ecompar son.thr ftjava
#@na space scala com.tw ter.t etyp ecompar son.thr ftscala
#@na space strato com.tw ter.t etyp ecompar son

 nclude "com/tw ter/t etyp e/t et_serv ce.thr ft"
 nclude "com/tw ter/context/v e r.thr ft"

serv ce T etCompar sonServ ce {
  vo d compare_ret et(
    1: t et_serv ce.Ret etRequest request,
    2: opt onal v e r.V e r v e r
  )

  vo d compare_post_t et(
    1: t et_serv ce.PostT etRequest request,
    2: opt onal v e r.V e r v e r
  )

  vo d compare_unret et(
    1: t et_serv ce.Unret etRequest request,
    2: opt onal v e r.V e r v e r
  )

  vo d compare_delete_t ets(
    1: t et_serv ce.DeleteT etsRequest request,
    2: opt onal v e r.V e r v e r
  )
}
