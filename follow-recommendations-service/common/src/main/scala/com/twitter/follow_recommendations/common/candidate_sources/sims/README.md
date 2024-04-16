# S ms Cand date S ce
Offers var ous onl ne s ces for f nd ng s m lar accounts based on a g ven user, w t r    s t  target user or an account cand date.

## S ms
T  object ve  s to  dent fy a l st of K users who are s m lar to a g ven user.  n t  scenar o,   pr mar ly focus on f nd ng s m lar users as "producers" rat r than "consu rs." S ms has two steps: cand date generat on and rank ng.

### S ms Cand date Generat on

W h over 700 m ll on users to cons der, t re are mult ple ways to def ne s m lar  es. Currently,   have three cand date s ces for S ms:

**Cos neFollow** (based on user-user follow graph): T  s m lar y bet en two users  s def ned as t  cos ne s m lar y bet en t  r follo rs. Desp e sound ng s mple, comput ng all-pa r s m lar y on t  ent re follow graph  s computat onally challeng ng.   are currently us ng t  WH MP algor hm to f nd t  top 1000 s m lar users for each user  D. T  cand date s ce has t  largest coverage, as   can f nd s m lar user cand dates for more than 700 m ll on users.

**Cos neL st** (based on user-l st  mbersh p graph): T  s m lar y bet en two users  s def ned as t  cos ne s m lar y bet en t  l sts t y are  ncluded as  mbers (e.g., [ re](https://tw ter.com/jack/l sts/ mbersh ps) are t  l sts that @jack  s on). T  sa  algor hm as Cos neFollow  s used.

**Follow2Vec** (essent ally Word2Vec on user-user follow graph):   f rst tra n t  Word2Vec model on follow sequence data to obta n users' embedd ngs and t n f nd t  most s m lar users based on t  s m lar y of t  embedd ngs. Ho ver,   need enough data for each user to learn a  an ngful embedd ng for t m, so   can only obta n embedd ngs for t  top 10 m ll on users (currently  n product on, test ng 30 m ll on users). Furt rmore, Word2Vec model tra n ng  s l m ed by  mory and computat on as    s tra ned on a s ngle mach ne.

##### Cos ne S m lar y
A cruc al component  n S ms  s calculat ng cos ne s m lar  es bet en users based on a user-X (X can be a user, l st, or ot r ent  es) b part e graph. T  problem  s techn cally challeng ng and took several years of effort to solve.

T  current  mple ntat on uses t  algor hm proposed  n [W n has s  t  dges: A d str buted algor hm for f nd ng h gh s m lar y vectors. WWW 2017](https://arx v.org/pdf/1703.01054.pdf)

### S ms Rank ng
After t  cand date generat on step,   can obta n dozens to hundreds of s m lar user cand dates for each user. Ho ver, s nce t se cand dates co  from d fferent algor hms,   need a way to rank t m. To do t ,   collect user feedback.

  use t  "Prof le S debar  mpress ons & Follow" (a module w h follow suggest ons d splayed w n a user v s s a prof le page and scrolls down) to collect tra n ng data. To allev ate any system b as,   use 4% of traff c to show randomly shuffled cand dates to users and collect pos  ve (follo d  mpress on) and negat ve ( mpress on only) data from t  traff c. T  data  s used as an evaluat on set.   use a port on of t  rema n ng 96% of traff c for tra n ng data, f lter ng only for sets of  mpress ons that had at least one follow, ensur ng that t  user tak ng act on was pay ng attent on to t   mpress ons.

T  examples are  n t  format of (prof le_user, cand date_user, label).   add features for prof le_users and cand date_users based on so  h gh-level aggregated stat st cs  n a feature dataset prov ded by t  Custo r J ney team, as  ll as features that represent t  s m lar y bet en t  prof le_user and cand date_user.

  employ a mult -to r MLP model and opt m ze t  log st c loss. T  model  s refres d  ekly us ng an ML workflow.

  recompute t  cand dates and rank t m da ly. T  ranked results are publ s d to t  Manhattan dataset.

