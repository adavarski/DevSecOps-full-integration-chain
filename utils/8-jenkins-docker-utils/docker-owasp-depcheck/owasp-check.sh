#!/bin/sh

OWASPDC_DIRECTORY="$(pwd)/owasp-dependency-check"
DATA_DIRECTORY="$OWASPDC_DIRECTORY/data"
REPORT_DIRECTORY="$OWASPDC_DIRECTORY/reports"

if [ ! -d "$DATA_DIRECTORY" ]; then
    mkdir -p "$OWASPDC_DIRECTORY"
fi

if [ ! -d "$DATA_DIRECTORY" ]; then
    mkdir -p "$DATA_DIRECTORY"
    chmod -R 777 "$DATA_DIRECTORY"

    mkdir -p "$REPORT_DIRECTORY"
    chmod -R 777 "$REPORT_DIRECTORY"
fi

docker pull owasp/dependency-check

docker run --rm \
    --volume $(pwd):/src \
    --volume "$DATA_DIRECTORY":/usr/share/dependency-check/data \
    --volume "$REPORT_DIRECTORY":/report \
    owasp/dependency-check \
    --scan /src \
    --log /report/dc.log \
    --out /report/dc.html \
    --format "HTML" \
    --project "$PROJECT_NAME"
