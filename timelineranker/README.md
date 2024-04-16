# T  l neRanker

**T  l neRanker** (TLR)  s a legacy serv ce that prov des relevance-scored t ets from t  Earlyb rd Search  ndex and User T et Ent y Graph (UTEG) serv ce. Desp e  s na ,   no longer performs  avy rank ng or model-based rank ng  self;   only uses relevance scores from t  Search  ndex for ranked t et endpo nts.

T  follow ng  s a l st of major serv ces that T  l ne Ranker  nteracts w h:

- **Earlyb rd-root-superroot (a.k.a Search):** T  l ne Ranker calls t  Search  ndex's super root to fetch a l st of T ets.
- **User T et Ent y Graph (UTEG):** T  l ne Ranker calls UTEG to fetch a l st of t ets l ked by t  users   follow.
- **Soc algraph:** T  l ne Ranker calls Soc al Graph Serv ce to obta n t  follow graph and user states such as blocked, muted, ret ets muted, etc.
- **T etyP e:** T  l ne Ranker hydrates t ets by call ng T etyP e to post-f lter t ets based on certa n hydrated f elds.
- **Manhattan:** T  l ne Ranker hydrates so  t et features (e.g., user languages) from Manhattan.

**Ho  M xer** calls T  l ne Ranker to fetch t ets from t  Earlyb rd Search  ndex and User T et Ent y Graph (UTEG) serv ce to po r both t  For   and Follow ng Ho  T  l nes. T  l ne Ranker performs l ght rank ng based on Earlyb rd t et cand date scores and truncates to t  number of cand dates requested by Ho  M xer based on t se scores.
