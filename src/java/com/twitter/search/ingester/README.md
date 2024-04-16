##  ngesters
 ngesters are serv ces that consu  raw t ets and user updates, process t m through a ser es of transformat ons and wr e t m to kafka top cs for Earlyb rd to consu  and subsequently  ndex. 

T re are two types of  ngesters:
1. T et  ngesters
2. UserUpdates  ngesters

T et  ngesters consu  raw t ets and extract d fferent f elds and features for Earlyb rd to  ndex. User updates  ngester produces user safety  nformat on such as w t r t  user  s deact vated, suspended or off-boarded. T  user and t et features produced by  ngesters are t n used by Earlyb rd dur ng t et ret eval and rank ng.  

 ngesters are made up of a p pel ne of stages w h each stage perform ng a d fferent f eld/feature extract on. T  p pel ne conf gurat on of t   ngesters can be found at sc ence/search/ ngester/conf g
