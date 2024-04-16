na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer

 nclude "ads.thr ft"
 nclude "cand date_generat on_key.thr ft"
 nclude "product.thr ft"
 nclude "product_context.thr ft"
 nclude "val dat on.thr ft"
 nclude " tr c_tags.thr ft"
 nclude "related_t et.thr ft"
 nclude "uteg.thr ft"
 nclude "frs_based_t et.thr ft"
 nclude "related_v deo_t et.thr ft"
 nclude "top c_t et.thr ft"

 nclude "com/tw ter/product_m xer/core/cl ent_context.thr ft"
 nclude "com/tw ter/t  l nes/render/response.thr ft"
 nclude "f natra-thr ft/f natra_thr ft_except ons.thr ft"
 nclude "com/tw ter/strato/graphql/sl ce.thr ft"

struct CrM xerT etRequest {
	1: requ red cl ent_context.Cl entContext cl entContext
	2: requ red product.Product product
	# Product-spec f c para ters should be placed  n t  Product Context
	3: opt onal product_context.ProductContext productContext
	4: opt onal l st< 64> excludedT et ds (personalDataType = 'T et d')
} (pers sted='true', hasPersonalData='true')

struct T etRecom ndat on {
  1: requ red  64 t et d (personalDataType = 'T et d')
  2: requ red double score
  3: opt onal l st< tr c_tags. tr cTag>  tr cTags
  # 4: t  author of t  t et cand date. To be used by Content-M xer to unblock t  Hydra exper  nt.
  4: opt onal  64 author d (personalDataType = 'User d')
  # 5: extra  nfo about cand date generat on. To be used by Content-M xer to unblock t  Hydra exper  nt.
  5: opt onal cand date_generat on_key.Cand dateGenerat onKey cand dateGenerat onKey
  # 1001: t  latest t  stamp of fav s gnals.  f null, t  cand date  s not generated from fav s gnals
  1001: opt onal  64 latestS ceS gnalT  stamp nM ll s(personalDataType = 'Publ cT  stamp')
} (pers sted='true', hasPersonalData = 'true')

struct CrM xerT etResponse {
 1: requ red l st<T etRecom ndat on> t ets
} (pers sted='true')

serv ce CrM xer {
  CrM xerT etResponse getT etRecom ndat ons(1: CrM xerT etRequest request) throws (
    # Val dat on errors - t  deta ls of wh ch w ll be reported to cl ents on fa lure
    1: val dat on.Val dat onExcept onL st val dat onErrors;
    # Server errors - t  deta ls of wh ch w ll not be reported to cl ents
    2: f natra_thr ft_except ons.ServerError serverError
  )

  # getRelatedT etsForQueryT et and getRelatedT etsForQueryAuthor do very s m lar th ngs
  #   can  rge t se two endpo nts  nto one un f ed endpo nt
  related_t et.RelatedT etResponse getRelatedT etsForQueryT et(1: related_t et.RelatedT etRequest request) throws (
    # Val dat on errors - t  deta ls of wh ch w ll be reported to cl ents on fa lure
    1: val dat on.Val dat onExcept onL st val dat onErrors;
    # Server errors - t  deta ls of wh ch w ll not be reported to cl ents
    2: f natra_thr ft_except ons.ServerError serverError
  )

  related_t et.RelatedT etResponse getRelatedT etsForQueryAuthor(1: related_t et.RelatedT etRequest request) throws (
    # Val dat on errors - t  deta ls of wh ch w ll be reported to cl ents on fa lure
    1: val dat on.Val dat onExcept onL st val dat onErrors;
    # Server errors - t  deta ls of wh ch w ll not be reported to cl ents
    2: f natra_thr ft_except ons.ServerError serverError
  )

  uteg.UtegT etResponse getUtegT etRecom ndat ons(1: uteg.UtegT etRequest request) throws (
    # Val dat on errors - t  deta ls of wh ch w ll be reported to cl ents on fa lure
    1: val dat on.Val dat onExcept onL st val dat onErrors;
    # Server errors - t  deta ls of wh ch w ll not be reported to cl ents
    2: f natra_thr ft_except ons.ServerError serverError
  )

  frs_based_t et.FrsT etResponse getFrsBasedT etRecom ndat ons(1: frs_based_t et.FrsT etRequest request) throws (
     # Val dat on errors - t  deta ls of wh ch w ll be reported to cl ents on fa lure
     1: val dat on.Val dat onExcept onL st val dat onErrors;
     # Server errors - t  deta ls of wh ch w ll not be reported to cl ents
     2: f natra_thr ft_except ons.ServerError serverError
  )

  related_v deo_t et.RelatedV deoT etResponse getRelatedV deoT etsForQueryT et(1: related_v deo_t et.RelatedV deoT etRequest request) throws (
      # Val dat on errors - t  deta ls of wh ch w ll be reported to cl ents on fa lure
      1: val dat on.Val dat onExcept onL st val dat onErrors;
      # Server errors - t  deta ls of wh ch w ll not be reported to cl ents
      2: f natra_thr ft_except ons.ServerError serverError
  )

  ads.AdsResponse getAdsRecom ndat ons(1: ads.AdsRequest request) throws (
    # Val dat on errors - t  deta ls of wh ch w ll be reported to cl ents on fa lure
    1: val dat on.Val dat onExcept onL st val dat onErrors;
    # Server errors - t  deta ls of wh ch w ll not be reported to cl ents
    2: f natra_thr ft_except ons.ServerError serverError
  )

  top c_t et.Top cT etResponse getTop cT etRecom ndat ons(1: top c_t et.Top cT etRequest request) throws (
    # Val dat on errors - t  deta ls of wh ch w ll be reported to cl ents on fa lure
    1: val dat on.Val dat onExcept onL st val dat onErrors;
    # Server errors - t  deta ls of wh ch w ll not be reported to cl ents
    2: f natra_thr ft_except ons.ServerError serverError
  )
}
