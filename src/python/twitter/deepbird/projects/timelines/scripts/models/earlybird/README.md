# Earlyb rd L ght Ranker

*Note: t  l ght ranker  s an old part of t  stack wh ch   are currently  n t  process of replac ng.
T  current model was last tra ned several years ago, and uses so  very strange features.
  are work ng on tra n ng a new model, and eventually rebu ld ng t  part of t  stack ent rely.*

T  Earlyb rd l ght ranker  s a log st c regress on model wh ch pred cts t  l kel hood that t  user w ll engage w h a
t et.
   s  ntended to be a s mpl f ed vers on of t   avy ranker wh ch can run on a greater amount of t ets.

T re are currently 2 ma n l ght ranker models  n use: one for rank ng  n network t ets (`recap_earlyb rd`), and
anot r for
out of network (UTEG) t ets (`rect et_earlyb rd`). Both models are tra ned us ng t  `tra n.py` scr pt wh ch  s
 ncluded  n t  d rectory. T y d ffer ma nly  n t  set of features
used by t  model.
T   n network model uses
t  `src/python/tw ter/deepb rd/projects/t  l nes/conf gs/recap/feature_conf g.py` f le to def ne t 
feature conf gurat on, wh le t 
out of network model uses `src/python/tw ter/deepb rd/projects/t  l nes/conf gs/rect et_earlyb rd/feature_conf g.py`.

T  `tra n.py` scr pt  s essent ally a ser es of hooks prov ded to for Tw ter's `twml` fra work to execute,
wh ch  s  ncluded under `twml/`.

### Features

T  l ght ranker features p pel ne  s as follows:
![earlyb rd_features.png](earlyb rd_features.png)

So  of t se components are expla ned below:

-  ndex  ngester: an  ndex ng p pel ne that handles t  t ets as t y are generated. T   s t  ma n  nput of
  Earlyb rd,   produces T et Data (t  bas c  nformat on about t  t et, t  text, t  urls,  d a ent  es, facets,
  etc) and Stat c Features (t  features   can compute d rectly from a t et r ght now, l ke w t r   has URL, has
  Cards, has quotes, etc); All  nformat on computed  re are stored  n  ndex and flus d as each realt    ndex seg nts
  beco  full. T y are loaded back later from d sk w n Earlyb rd restarts. Note that t  features may be computed  n a
  non-tr v al way (l ke dec d ng t  value of hasUrl), t y could be computed and comb ned from so  more "raw"
   nformat on  n t  t et and from ot r serv ces.
  S gnal  ngester: t   ngester for Realt   Features, per-t et features that can change after t  t et has been
   ndexed, mostly soc al engage nts l ke ret etCount, favCount, replyCount, etc, along w h so  (future) spam s gnals
  that's computed w h later act v  es. T se  re collected and computed  n a  ron topology by process ng mult ple
  event streams and can be extended to support more features.
- User Table Features  s anot r set of features per user. T y are from User Table Updater, a d fferent  nput that
  processes a stream wr ten by   user serv ce.  's used to store sparse realt   user
   nformat on. T se per-user features are propagated to t  t et be ng scored by
  look ng up t  author of t  t et.
- Search Context Features are bas cally t   nformat on of current searc r, l ke t  r U  language, t  r own
  produced/consu d language, and t  current t   ( mpl ed). T y are comb ned w h T et Data to compute so  of t 
  features used  n scor ng.

T  scor ng funct on  n Earlyb rd uses both stat c and realt   features. Examples of stat c features used are:

- W t r t  t et  s a ret et
- W t r t  t et conta ns a l nk
- W t r t  t et has any trend words at  ngest on t  
- W t r t  t et  s a reply
- A score for t  stat c qual y of t  text, computed  n T etTextScorer.java  n t   ngester. Based on t  factors
  such as offens veness, content entropy, "shout" score, length, and readab l y.
- t epcred, see top-level README.md

Examples of realt   features used are:

- Number of t et l kes/repl es/ret ets
- pTox c y and pBlock scores prov ded by  alth models
