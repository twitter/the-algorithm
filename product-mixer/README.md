Product M xer
=============

## Overv ew

Product M xer  s a common serv ce fra work and set of l brar es that make   easy to bu ld,
 erate on, and own product surface areas.   cons sts of:

- **Core L brar es:** A set of l brar es that enable   to bu ld execut on p pel nes out of
  reusable components.   def ne y  log c  n small,  ll-def ned, reusable components and focus
  on express ng t  bus ness log c   want to have. T n   can def ne easy to understand p pel nes
  that compose y  components. Product M xer handles t  execut on and mon or ng of y  p pel nes
  allow ng   to focus on what really matters, y  bus ness log c.

- **Serv ce Fra work:** A common serv ce skeleton for teams to host t  r Product M xer products.

- **Component L brary:** A shared l brary of components made by t  Product M xer Team, or
  contr buted by users. T  enables   to both eas ly share t  reusable components   make as  ll
  as benef  from t  work ot r teams have done by ut l z ng t  r shared components  n t  l brary.

## Arch ecture

T  bulk of a Product M xer can be broken down  nto P pel nes and Components. Components allow  
to break bus ness log c  nto separate, standard zed, reusable, testable, and eas ly composable
p eces, w re each component has a  ll def ned abstract on. P pel nes are essent ally conf gurat on
f les spec fy ng wh ch Components should be used and w n. T  makes   easy to understand how y 
code w ll execute wh le keep ng   organ zed and structured  n a ma nta nable way.

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
