#!/bin/sh

for i in {1..10}
do
    echo "Migrating Database..."
    python manage.py migrate

    if [ $? == "0" ]; then
        echo "Migration Complete"
        break
    fi

    sleep 3
done

python manage.py runserver 0.0.0.0:8000
