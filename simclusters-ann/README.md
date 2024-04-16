# S mClusters ANN

S mClusters ANN  s a serv ce that returns t et cand date recom ndat ons g ven a S mClusters embedd ng. T  serv ce  mple nts t et recom ndat ons based on t  Approx mate Cos ne S m lar y algor hm.

T  cos ne s m lar y bet en two T et S mClusters Embedd ng represents t  relevance level of two t ets  n S mCluster space. T  trad  onal algor hm for calculat ng cos ne s m lar y  s expens ve and hard to support by t  ex st ng  nfrastructure. T refore, t  Approx mate Cos ne S m lar y algor hm  s  ntroduced to save response t   by reduc ng  /O operat ons.

## Background
S mClusters V2 runt    nfra  ntroduces t  S mClusters and  s onl ne and offl ne approac s. A  ron job bu lds t  mapp ng bet en S mClusters and T ets. T  job saves top 400 T ets for a S mClusters and top 100 S mClusters for a T et. Favor e score and follow score are two types of t et score.   n t  docu nt, t  top 100 S mClusters based on t  favor e score for a T et stands for t  T et S mClusters Embedd ng. 

T  cos ne s m lar y bet en two T et S mClusters Embedd ng presents t  relevant level of two t ets  n S mCluster space. T  score var es from 0 to 1. T  h gh cos ne s m lar y score(>= 0.7  n Prod)  ans that t  users who l ke two t ets share t  sa  S mClusters. 


S mClusters from t  L near Algebra Perspect ve d scussed t  d fference bet en t  dot-product and cos ne s m lar y  n S mCluster space.   bel eve t  cos ne s m lar y approach  s better because   avo ds t  b as of t et popular y.

 Ho ver, calculat ng t  cos ne s m lar y bet en two T ets  s pretty expens ve  n T et cand date generat on.  n TW STLY,   scan at most 15,000 (6 s ce t ets * 25 clusters * 100 t ets per clusters) t et cand dates for every Ho  T  l ne request. T  trad  onal algor hm needs to make AP  calls to fetch 15,000 t et S mCluster embedd ngs. Cons der that   need to process over 6,000 RPS,  ’s hard to support by t  ex st ng  nfrastructure.  


## S mClusters Approx mate Cos ne S m lar y Core Algor hm

1. Prov de a s ce S mCluster Embedd ng *SV*, *SV = [(SC1, Score), (SC2, Score), (SC3, Score) …]*

2. Fetch top *M* t ets for each Top *N* S mClusters based on SV.  n Prod, *M = 400*, *N = 50*.  T ets may appear  n mult ple S mClusters. 
 
|   |   |   |   |
|---|---|---|---|
| SC1  | T1:Score  | T2: Score  | ...   |
| SC2 |  T3: Score | T4: Score  |  ... |


3. Based on t  prev ous table, generate an *(M x N) x N* Matr x *R*. T  *R* represents t  approx mate S mCluster embedd ngs for *MxN* t ets. T  embedd ng only conta ns top *N* S mClusters from *SV*. Only top *M* t ets from each S mCluster have t  score. Ot rs are 0. 

|   |  SC1 |  SC2 | ...   |
|---|---|---|---|
| T1  | Score  | 0  | ...   |
| T2 |  Score | 0 |  ... |
| T3 |  0 | Score  |  ... |

4. Compute t  dot product bet en s ce vector and t  approx mate vectors for each t et. (Calculate *R • SV^T*). Take top *X* t ets.  n Prod, *X = 200*

5. Fetch *X* t et S mClusters Embedd ng, Calculate Cos ne S m lar y bet en *X* t ets and *SV*, Return top *Y* above a certa n threshold *Z*.

Approx mate Cos ne S m lar y  s an approx mate algor hm.  nstead of fetch ng *M * N* t ets embedd ng,   only fetc s *X* t ets embedd ng.  n prod, *X / M * N * 100% = 6%*. Based on t   tr cs dur ng TW STLY develop nt, most of t  response t    s consu d by  /O operat on. T  Approx mate Cos ne S m lar y  s a good approach to save a large amount of response t  . 

T   dea of t  approx mate algor hm  s based on t  assumpt on that t  h g r dot-product bet en s ce t ets’ S mCluster embedd ng and cand date t et’s l m ed S mCluster Embedd ng, t  poss b l y that t se two t ets are relevant  s h g r. Add  onal Cos ne S m lar y f lter  s to guarantee that t  results are not affected by popular y b as.  

Adjust ng t  M, N, X, Y, Z  s able to balance t  prec s on and recall for d fferent products. T   mple ntat on of approx mate cos ne s m lar y  s used by TW STLY,  nterest-based t et recom ndat on, S m lar T et  n RUX, and Author based recom ndat on. T  algor hm  s also su able for future user or ent y recom ndat on based on S mClusters Embedd ng. 


# -------------------------------
# Bu ld and Test
# -------------------------------
Comp le t  serv ce

    $ ./bazel bu ld s mclusters-ann/server:b n

Un  tests

    $ ./bazel test s mclusters-ann/server:b n

# -------------------------------
# Deploy
# -------------------------------

## Prerequ s e for devel deploy nts
F rst of all,   need to generate Serv ce to Serv ce cert f cates for use wh le develop ng locally. T  only needs to be done ONCE:

To add cert f les to Aurora ( f   want to deploy to DEVEL):
```
$ developer-cert-ut l --env devel --job s mclusters-ann
```

## Deploy ng to devel/stag ng from a local bu ld
Reference -
    
    $ ./s mclusters-ann/b n/deploy.sh -- lp

Use t  scr pt to bu ld t  serv ce  n y  local branch, upload   to packer and deploy  n devel aurora:

    $ ./s mclusters-ann/b n/deploy.sh atla $USER devel s mclusters-ann

  can also deploy to stag ng w h t  scr pt. E.g. to deploy to  nstance 1:

    $ ./s mclusters-ann/b n/deploy.sh atla s mclusters-ann stag ng s mclusters-ann < nstance-number>

## Deploy ng to product on

Product on deploys should be managed by Workflows. 
_Do not_ deploy to product on unless    s an e rgency and   have approval from oncall.

#####    s not recom nded to deploy from Command L nes  nto product on env ron nts, unless 1)   test ng a small change  n Canary shard [0,9]. 2) Tt  s an absolute e rgency. Be sure to make oncalls aware of t  changes   deploy ng.

    $ ./s mclusters-ann/b n/deploy.sh atla s mclusters-ann prod s mclusters-ann < nstance-number>
 n t  case of mult ple  nstances,

    $ ./s mclusters-ann/b n/deploy.sh atla s mclusters-ann prod s mclusters-ann < nstance-number-start>-< nstance-number-end>

## C ck ng Deployed Vers on and Roll ng Back

W rever poss ble, roll back us ng Workflows by f nd ng an earl er good vers on and cl ck ng t  "rollback" button  n t  U . T   s t  safest and least error-prone  thod.
