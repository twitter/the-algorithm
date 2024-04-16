# Not f cat on L ght Ranker Model

## Model Context
T re are 4 major components of Tw ter not f cat ons recom ndat on system: 1) cand date generat on 2) l ght rank ng 3)  avy rank ng & 4) qual y control. T  not f cat on l ght ranker model br dges cand date generat on and  avy rank ng by pre-select ng h ghly-relevant cand dates from t   n  al huge cand date pool.  ’s a l ght-  ght model to reduce system cost dur ng  avy rank ng w hout hurt ng user exper ence.

## D rectory Structure
- BU LD: t  f le def nes python l brary dependenc es
- model_pools_mlp.py: t  f le def nes tensorflow model arch ecture for t  not f cat on l ght ranker model
- deep_norm.py: t  f le conta ns 1) how to bu ld t  tensorflow graph w h spec f ed model arch ecture, loss funct on and tra n ng conf gurat on. 2) how to set up t  overall model tra n ng & evaluat on p pel ne
- eval_model.py: t  ma n python entry f le to set up t  overall model evaluat on p pel ne




