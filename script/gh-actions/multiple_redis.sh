#!/bin/bash

redis-server --daemonize yes --port 6379 --dbfilename "autocomplete.rdb"
redis-server --daemonize yes --port 6380 --dbfilename "general.rdb"
redis-server --daemonize yes --port 6381 --dbfilename "hits.rdb"
redis-server --daemonize yes --port 6382 --dbfilename "kudos.rdb"
redis-server --daemonize yes --port 6383 --dbfilename "resque.rdb"
redis-server --daemonize yes --port 6384 --dbfilename "rollout.rdb"
