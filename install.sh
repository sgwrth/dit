#!/bin/bash

printf "This will install dit on your system (locally).\n"
printf "Steps:\n"
printf "\t1. Rename 'dit.sh' to 'dit'\n"
printf "\t2. Copy 'dit' to /usr/local/bin\n"
printf "\t3. Run dit from anywhere!\n\n"

while true; do
    printf "Do you want to install dit? Type y or n:\n"
    read input

    if [[ "$input" == "n" ]]; then
        printf "Okay, no installation.  Bye.\n"
        exit 0
    fi

    if [[ "$input" == y ]]; then
        printf "Cool!  Let's go ...\n"
        sudo cp ./src/dit.sh /usr/local/bin/dit
        printf "... done!  Try 'dit -v' or 'dit -h'\n"
        exit 0
    fi
done
