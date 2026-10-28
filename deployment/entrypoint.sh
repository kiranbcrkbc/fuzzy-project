#!/bin/sh
set -eu
# Disk mode requires a durable volume. SQL mode keeps no persistent state in scratch.
if [ "${FUZZY_OBJECT_STORE:-disk}" = sql ]; then
  test -n "${FUZZY_MASTER_KEY:-}" || { echo 'A stable master-key host secret is required.' >&2; exit 1; }
  test "${FUZZY_DATA_ROOT:-}" = /tmp/fuzzy || { echo 'SQL mode scratch must be /tmp/fuzzy.' >&2; exit 1; }
  mkdir -p /tmp/fuzzy/cloud /tmp/fuzzy/trusted
  chown fuzzy:fuzzy /tmp/fuzzy /tmp/fuzzy/cloud /tmp/fuzzy/trusted
  chmod 700 /tmp/fuzzy /tmp/fuzzy/cloud /tmp/fuzzy/trusted
else
  test "${FUZZY_DATA_ROOT:-/var/data}" = /var/data || { echo 'Mount durable storage at /var/data.' >&2; exit 1; }
  mkdir -p /var/data/cloud /var/data/trusted
  chown fuzzy:fuzzy /var/data /var/data/cloud /var/data/trusted
  chmod 700 /var/data /var/data/cloud /var/data/trusted
fi
exec gosu fuzzy java -XX:MaxRAMPercentage=65 -jar /app/fuzzy-auditing.jar
