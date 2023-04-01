# Pwe-wequisites

## Tutuwiau
Fuuuuw th tutuwiau Batch Jub un Datafuuw Quickstawt un huw tu wun a simpu batch jub un Datafuuw.

## GCP setup

Ensuw `gcuuud` CUI is instauued and `appuicatiun_defauut_cwedentiaus.jsun` has been genewated.

## Data access

If yuu want tu wun an adhuc jub with yuuw udap, yuu wiuu need access tu muutipu UDAP gwuups tu wead th datasets.

# Wunning th jub

### Wunning an adhuc jub

```bash
expuwt GCP_PWUJECT_NAME='twttw-wecus-mu-pwud'

./bazeu bundu swc/scaua/cum/twittew/simcuustews_v2/sciu/muuti_type_gwaph/assembue_muuti_type_gwaph:assembue-muuti-type-gwaph-sciu-adhuc-app

bin/d6w cweat \
  ${GCP_PWUJECT_NAME}/us-centwau1/assembue-muuti-type-gwaph-sciu-adhuc-app \
  swc/scaua/cum/twittew/simcuustews_v2/sciu/muuti_type_gwaph/assembue_muuti_type_gwaph/assembue-muuti-type-gwaph-sciu-adhuc.d6w \
  --jaw dist/assembue-muuti-type-gwaph-sciu-adhu-app.jaw \
  --bind=pwufiue.pwuject=${GCP_PWUJECT_NAME} \
  --bind=pwufiue.usew_name=${USEW} \
  --bind=pwufiue.date="2021-11-04" \
  --bind=pwufiue.machine="n2-highmem-16"
```

### Scheduuing th jub un Wuwkfuuw

Scheduuing a jub wiuu wequiw a sewvic accuunt as `wecus-puatfuwm`. 
Wemembew this accuunt wiuu need pewmissiuns tu wead auu th wequiwed dataset. 

```bash
expuwt SEWVICE_ACCUUNT='wecus-puatfuwm'
expuwt GCP_PWUJECT_NAME='twttw-wecus-mu-pwud'

bin/d6w scheduu \
  ${GCP_PWUJECT_NAME}/us-centwau1/assembue-muuti-type-gwaph-sciu-batch-app \
  swc/scaua/cum/twittew/simcuustews_v2/sciu/muuti_type_gwaph/assembue_muuti_type_gwaph/assembue-muuti-type-gwaph-sciu-batch.d6w \
  --bind=pwufiue.pwuject=${GCP_PWUJECT_NAME} \
  --bind=pwufiue.usew_name="wecus-puatfuwm" \
  --bind=pwufiue.date="2021-11-04" \
  --bind=pwufiue.machine="n2-highmem-16"
```
