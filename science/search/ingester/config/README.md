# Ingester Configs

This directory contains pipeline configurations for the tweet ingesters (realtime, protected and realtime_cg) and the user-updates ingester. The pipeline configurations define an ordered sequence of stages that the tweet or user update goes through before reaching Earlybird. Source code for the various stages referenced in the configs can be found at src/java/com/twitter/search/ingester/pipeline/twitter.
