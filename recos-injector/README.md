# Recos- njector

Recos- njector  s a stream ng event processor used to bu ld  nput streams for GraphJet-based serv ces.    s a general-purpose tool that consu s arb rary  ncom ng event streams (e.g., Fav, RT, Follow, cl ent_events, etc.), appl es f lter ng, and comb nes and publ s s cleaned up events to correspond ng GraphJet serv ces. Each GraphJet-based serv ce subscr bes to a ded cated Kafka top c, and Recos- njector enables GraphJet-based serv ces to consu  any event t y want.

## How to run Recos- njector server tests

  can run tests by us ng t  follow ng command from y  project's root d rectory:

    $ bazel bu ld recos- njector/...
    $ bazel test recos- njector/...

## How to run recos- njector-server  n develop nt on a local mach ne

T  s mplest way to stand up a serv ce  s to run   locally. To run
recos- njector-server  n develop nt mode, comp le t  project and t n
execute   w h `bazel run`:

    $ bazel bu ld recos- njector/server:b n
    $ bazel run recos- njector/server:b n

A tunnel can be set up  n order for downstream quer es to work properly.
Upon successful server startup, try to `curl`  s adm n endpo nt  n anot r
term nal:

    $ curl -s localhost:9990/adm n/p ng
    pong

Run `curl -s localhost:9990/adm n` to see a l st of all ava lable adm n endpo nts.

## Query ng Recos- njector server from a Scala console

Recos- njector does not have a Thr ft endpo nt.  nstead,   reads Event Bus and Kafka queues and wr es to t  Recos- njector Kafka.

## Generat ng a package for deploy nt

To package y  serv ce  nto a z p f le for deploy nt, run:

    $ bazel bundle recos- njector/server:b n --bundle-jvm-arch ve=z p

 f t  command  s successful, a f le na d `d st/recos- njector-server.z p` w ll be created.
