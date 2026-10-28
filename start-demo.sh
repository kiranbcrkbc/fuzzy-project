#!/usr/bin/env sh
set -eu
ROOT=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
cd "$ROOT"
if [ "${FUZZY_PROFILE:-demo}" = "demo" ]; then export FUZZY_DATA_ROOT="$ROOT/data/h2-demo"; else export FUZZY_DATA_ROOT="$ROOT/data"; fi
export FUZZY_DEMO=true
exec java -jar "$ROOT/app/dist/fuzzy-auditing-1.0.0.jar" "--spring.profiles.active=${FUZZY_PROFILE:-demo}" "$@"
