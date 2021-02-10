#!/bin/bash



if [ -d "zips" ]; then
    echo "Deleting old zips"
    rm -rf zips
fi

mkdir zips

echo "Generating zips"

for dir in */     # list directories in the form "/tmp/dirname/"
do
    if [ "${dir%*/}" != "zips" ]; then 
        echo "Generating Zip: bbs27_${dir%*/}"
        zip -r zips/bbs27_${dir%*/}.zip ${dir}*
    fi
done

echo "Done generating zips"
