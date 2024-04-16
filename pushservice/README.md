# Pushserv ce

Pushserv ce  s t  ma n push recom ndat on serv ce at Tw ter used to generate recom ndat on-based not f cat ons for users.   currently po rs two funct onal  es:

- RefreshForPushHandler: T  handler determ nes w t r to send a recom ndat on push to a user based on t  r  D.   generates t  best push recom ndat on  em and coord nates w h downstream serv ces to del ver  
- SendHandler: T  handler determ nes and manage w t r send t  push to users based on t  g ven target user deta ls and t  prov ded push recom ndat on  em

## Overv ew

### RefreshForPushHandler

RefreshForPushHandler follows t se steps:

- Bu ld ng Target and c ck ng el g b l y
    - Bu lds a target user object based on t  g ven user  D
    - Performs target-level f lter ngs to determ ne  f t  target  s el g ble for a recom ndat on push
- Fetch Cand dates
    - Retr eves a l st of potent al cand dates for t  push by query ng var ous cand date s ces us ng t  target
- Cand date Hydrat on
    - Hydrates t  cand date deta ls w h batch calls to d fferent downstream serv ces
- Pre-rank F lter ng, also called L ght F lter ng
    - F lters t  hydrated cand dates w h l ght  ght RPC calls
- Rank
    - Perform feature hydrat on for cand dates and target user
    - Performs l ght rank ng on cand dates
    - Performs  avy rank ng on cand dates
- Take Step, also called  avy F lter ng
    - Takes t  top-ranked cand dates one by one and appl es  avy f lter ng unt l one cand date passes all f lter steps
- Send
    - Calls t  appropr ate downstream serv ce to del ver t  el g ble cand date as a push and  n-app not f cat on to t  target user

### SendHandler

SendHandler follows t se steps:

- Bu ld ng Target
    - Bu lds a target user object based on t  g ven user  D
- Cand date Hydrat on
    - Hydrates t  cand date deta ls w h batch calls to d fferent downstream serv ces
- Feature Hydrat on
    - Perform feature hydrat on for cand dates and target user
- Take Step, also called  avy F lter ng
    - Perform f lter ngs and val dat on c ck ng for t  g ven cand date
- Send
    - Calls t  appropr ate downstream serv ce to del ver t  g ven cand date as a push and/or  n-app not f cat on to t  target user