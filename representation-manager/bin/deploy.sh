#!/usr/b n/env bash

JOB=representat on-manager bazel run --u _event_f lters=- nfo,-stdout,-stderr --noshow_progress \
	//relevance-platform/src/ma n/python/deploy -- "$@"
