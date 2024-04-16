# Not f cat on  avy Ranker Model

## Model Context
T re are 4 major components of Tw ter not f cat ons recom ndat on system: 1) cand date generat on 2) l ght rank ng 3)  avy rank ng & 4) qual y control. T  not f cat on  avy ranker model  s t  core rank ng model for t  personal sed not f cat ons recom ndat on.  's a mult -task learn ng model to pred ct t  probab l  es that t  target users w ll open and engage w h t  sent not f cat ons. 


## D rectory Structure
- BU LD: t  f le def nes python l brary dependenc es
- deep_norm.py: t  f le conta ns how to set up cont nuous tra n ng, model evaluat on and model export ng for t  not f cat on  avy ranker model
- eval.py: t  ma n python entry f le to set up t  overall model evaluat on p pel ne
- features.py: t  f le conta ns  mport ng feature l st and support funct ons for feature eng neer ng
- graph.py: t  f le def nes how to bu ld t  tensorflow graph w h spec f ed model arch ecture, loss funct on and tra n ng conf gurat on
- model_pools.py: t  f le def nes t  ava lable model types for t   avy ranker
- params.py: t  f le def nes hyper-para ters used  n t  not f cat on  avy ranker 
- run_args.py: t  f le def nes command l ne para ters to run model tra n ng & evaluat on
- update_warm_start_c ckpo nt.py: t  f le conta ns t  support to mod fy c ckpo nts of t  g ven saved  avy ranker model
- l b/BU LD: t  f le def nes python l brary dependenc es for tensorflow model arch ecture
- l b/layers.py: t  f le def nes d fferent type of convolut on layers to be used  n t   avy ranker model
- l b/model.py: t  f le def nes t  module conta n ng ClemNet, t   avy ranker model type
- l b/params.py: t  f le def nes para ters used  n t   avy ranker model 
