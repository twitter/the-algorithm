Ho  M xer
==========

Ho  M xer  s t  ma n serv ce used to construct and serve Tw ter's Ho  T  l nes.   currently
po rs:
- For   - best T ets from people   follow + recom nded out-of-network content
- Follow ng - reverse chronolog cal T ets from people   follow
- L sts - reverse chronolog cal T ets from L st  mbers

Ho  M xer  s bu lt on Product M xer,   custom Scala fra work that fac l ates bu ld ng
feeds of content.

## Overv ew

T  For   recom ndat on algor hm  n Ho  M xer  nvolves t  follow ng stages:

- Cand date Generat on - fetch T ets from var ous Cand date S ces. For example:
    - Earlyb rd Search  ndex
    - User T et Ent y Graph
    - Cr M xer
    - Follow Recom ndat ons Serv ce
- Feature Hydrat on
    - Fetch t  ~6000 features needed for rank ng
- Scor ng and Rank ng us ng ML model
- F lters and  ur st cs. For example:
    - Author D vers y
    - Content Balance ( n network vs Out of Network)
    - Feedback fat gue
    - Dedupl cat on / prev ously seen T ets removal
    - V s b l y F lter ng (blocked, muted authors/t ets, NSFW sett ngs)
- M x ng -  ntegrate T ets w h non-T et content
    - Ads
    - Who-to-follow modules
    - Prompts
- Product Features and Serv ng
    - Conversat on Modules for repl es
    - Soc al Context
    - T  l ne Nav gat on
    - Ed ed T ets
    - Feedback opt ons
    - Pag nat on and cursor ng
    - Observab l y and logg ng
    - Cl ent  nstruct ons and content marshall ng

## P pel ne Structure

### General

Product M xer serv ces l ke Ho  M xer are structured around P pel nes that spl  t  execut on
 nto transparent and structured steps.

Requests f rst go to Product P pel nes, wh ch are used to select wh ch M xer P pel ne or
Recom ndat on P pel ne to run for a g ven request. Each M xer or Recom ndat on
P pel ne may run mult ple Cand date P pel nes to fetch cand dates to  nclude  n t  response.

M xer P pel nes comb ne t  results of mult ple  terogeneous Cand date P pel nes toget r
(e.g. ads, t ets, users) wh le Recom ndat on P pel nes are used to score (v a Scor ng P pel nes)
and rank t  results of homogenous Cand date P pel nes so that t  top ranked ones can be returned.
T se p pel nes also marshall cand dates  nto a doma n object and t n  nto a transport object
to return to t  caller.

Cand date P pel nes fetch cand dates from underly ng Cand date S ces and perform so  bas c
operat ons on t  Cand dates, such as f lter ng out unwanted cand dates, apply ng decorat ons,
and hydrat ng features.

T  sect ons below descr be t  h gh level p pel ne structure (non-exhaust ve) for t  ma n Ho 
T  l ne tabs po red by Ho  M xer.

### For  

- For ProductP pel neConf g
    - For ScoredT etsM xerP pel neConf g (ma n orc strat on layer - m xes T ets w h ads and users)
        - For ScoredT etsCand dateP pel neConf g (fetch T ets)
            - ScoredT etsRecom ndat onP pel neConf g (ma n T et recom ndat on layer)
                - Fetch T et Cand dates
                    - ScoredT ets nNetworkCand dateP pel neConf g
                    - ScoredT etsT etM xerCand dateP pel neConf g
                    - ScoredT etsUtegCand dateP pel neConf g
                    - ScoredT etsFrsCand dateP pel neConf g
                - Feature Hydrat on and Scor ng
                    - ScoredT etsScor ngP pel neConf g
        - For Conversat onServ ceCand dateP pel neConf g (backup reverse chron p pel ne  n case Scored T ets fa ls)
        - For AdsCand dateP pel neConf g (fetch ads)
        - For WhoToFollowCand dateP pel neConf g (fetch users to recom nd)

### Follow ng

- Follow ngProductP pel neConf g
    - Follow ngM xerP pel neConf g
        - Follow ngEarlyb rdCand dateP pel neConf g (fetch t ets from Search  ndex)
        - Conversat onServ ceCand dateP pel neConf g (fetch ancestors for conversat on modules)
        - Follow ngAdsCand dateP pel neConf g (fetch ads)
        - Follow ngWhoToFollowCand dateP pel neConf g (fetch users to recom nd)

### L sts

- L stT etsProductP pel neConf g
    - L stT etsM xerP pel neConf g
        - L stT etsT  l neServ ceCand dateP pel neConf g (fetch t ets from t  l ne serv ce)
        - Conversat onServ ceCand dateP pel neConf g (fetch ancestors for conversat on modules)
        - L stT etsAdsCand dateP pel neConf g (fetch ads)
