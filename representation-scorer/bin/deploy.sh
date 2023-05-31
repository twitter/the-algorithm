#!/usr/bin/env bash

JOB=representation-scorer bazel run --ui_event_filters=-info,-stdout,-stderr --noshow_progress \
	//relevance-platform/src/main/python/deploy -- "$@"
