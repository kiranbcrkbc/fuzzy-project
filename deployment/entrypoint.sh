#!/bin/sh
set -eu
# /var/data MUST be the provider's persistent volume, never the container layer.
test "${FUZZY_DATA_ROOT:-/var/data}" = /var/data || { echo 'Mount durable storage at /var/data.' >&2; exit 1; }
mkdir -p /var/data/cloud /var/data/trusted
chown fuzzy:fuzzy /var/data /var/data/cloud /var/data/trusted
chmod 700 /var/data /var/data/cloud /var/data/trusted
exec gosu fuzzy java -XX:MaxRAMPercentage=65 -jar /app/fuzzy-auditing.jar
