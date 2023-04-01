# recos-injector
Recos-Injector is a streaming event processor for building input streams for GraphJet based services.
It is general purpose in that it consumes arbitrary incoming event stream (e.x. Fav, RT, Follow, client_events, etc), applies
filtering, combines and publishes cleaned up events to corresponding GraphJet services. 
Each GraphJet based service subscribes to a dedicated Kafka topic. Recos-Injector enables a GraphJet based service to consume any 
event it wants

## How to run recos-injector-server tests

Tests can be run by using this command from your project's root directory:

    $ bazel build recos-injector/...
    $ bazel test recos-injector/...

## How to run recos-injector-server in development on a local machine

The simplest way to stand up a service is to run it locally. To run
recos-injector-server in development mode, compile the project and then
execute it with `bazel run`:

    $ bazel build recos-injector/server:bin
    $ bazel run recos-injector/server:bin

A tunnel can be set up in order for downstream queries to work properly.
Upon successful server startup, try to `curl` its admin endpoint in another
terminal:

    $ curl -s localhost:9990/admin/ping
    pong

Run `curl -s localhost:9990/admin` to see a list of all of the available admin
endpoints.

## Querying recos-injector-server from a Scala console

Recos Injector does not have a thrift endpoint. It reads Event Bus and Kafka queues and writes to recos_injector kafka.

## Generating a package for deployment

To package your service into a zip for deployment:

    $ bazel bundle recos-injector/server:bin --bundle-jvm-archive=zip

If successful, a file `dist/recos-injector-server.zip` will be created.
