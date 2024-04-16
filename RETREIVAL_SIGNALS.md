# S gnals for Cand date S ces

## Overv ew

T  cand date s c ng stage w h n t  Tw ter Recom ndat on algor hm serves to s gn f cantly narrow down t   em s ze from approx mately 1 b ll on to just a few thousand. T  process ut l zes Tw ter user behav or as t  pr mary  nput for t  algor hm. T  docu nt compre ns vely enu rates all t  s gnals dur ng t  cand date s c ng phase.

| S gnals               |  Descr pt on                                                          |
| :-------------------- | :-------------------------------------------------------------------- |
| Author Follow         | T  accounts wh ch user expl c  follows.                             |
| Author Unfollow       | T  accounts wh ch user recently unfollows.                           |
| Author Mute           | T  accounts wh ch user have muted.                                   |
| Author Block          | T  accounts wh ch user have blocked                                  |
| T et Favor e        | T  t ets wh ch user cl cked t  l ke botton.                        | 
| T et Unfavor e      | T  t ets wh ch user cl cked t  unl ke botton.                      |       
| Ret et               | T  t ets wh ch user ret eted                                       |
| Quote T et           | T  t ets wh ch user ret eted w h com nts.                        |
| T et Reply           | T  t ets wh ch user repl ed.                                        |
| T et Share           | T  t ets wh ch user cl cked t  share botton.                       |
| T et Bookmark        | T  t ets wh ch user cl cked t  bookmark botton.                    |
| T et Cl ck           | T  t ets wh ch user cl cked and v e d t  t et deta l page.       |
| T et V deo Watch     | T  v deo t ets wh ch user watc d certa n seconds or percentage.    |
| T et Don't l ke      | T  t ets wh ch user cl cked "Not  nterested  n t  t et" botton.  |
| T et Report          | T  t ets wh ch user cl cked "Report T et" botton.                  |
| Not f cat on Open     | T  push not f cat on t ets wh ch user opened.                       |
| Ntab cl ck            | T  t ets wh ch user cl ck on t  Not f cat ons page.                |               
| User AddressBook      | T  author accounts  dent f ers of t  user's addressbook.            | 

## Usage Deta ls

Tw ter uses t se user s gnals as tra n ng labels and/or ML features  n t  each cand date s c ng algor hms. T  follow ng tables shows how t y are used  n t  each components.

| S gnals               | USS                | S mClusters        |  TwH n             |   UTEG             | FRS                |  L ght Rank ng     |
| :-------------------- | :----------------- | :----------------- | :----------------- | :----------------- | :----------------- | :----------------- | 
| Author Follow         | Features           | Features / Labels  | Features / Labels  | Features           | Features / Labels  | N/A                |
| Author Unfollow       | Features           | N/A                | N/A                | N/A                | N/A                | N/A                |
| Author Mute           | Features           | N/A                | N/A                | N/A                | Features           | N/A                |
| Author Block          | Features           | N/A                | N/A                | N/A                | Features           | N/A                |
| T et Favor e        | Features           | Features           | Features / Labels  | Features           | Features / Labels  | Features / Labels  |
| T et Unfavor e      | Features           | Features           | N/A                | N/A                | N/A                | N/A                |       
| Ret et               | Features           | N/A                | Features / Labels  | Features           | Features / Labels  | Features / Labels  |
| Quote T et           | Features           | N/A                | Features / Labels  | Features           | Features / Labels  | Features / Labels  |
| T et Reply           | Features           | N/A                | Features           | Features           | Features / Labels  | Features           |
| T et Share           | Features           | N/A                | N/A                | N/A                | Features           | N/A                |
| T et Bookmark        | Features           | N/A                | N/A                | N/A                | N/A                | N/A                |
| T et Cl ck           | Features           | N/A                | N/A                | N/A                | Features           | Labels             |
| T et V deo Watch     | Features           | Features           | N/A                | N/A                | N/A                | Labels             |
| T et Don't l ke      | Features           | N/A                | N/A                | N/A                | N/A                | N/A                |
| T et Report          | Features           | N/A                | N/A                | N/A                | N/A                | N/A                |
| Not f cat on Open     | Features           | Features           | Features           | N/A                | Features           | N/A                |                       
| Ntab cl ck            | Features           | Features           | Features           | N/A                | Features           | N/A                |
| User AddressBook      | N/A                | N/A                | N/A                | N/A                | Features           | N/A                |