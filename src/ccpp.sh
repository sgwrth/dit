#!/bin/bash

# Exit with error if less than four parameters were passed.
if [[ "$#" -lt 4 ]]; then
    printf "Error: missing parameter(s)\n"
    exit 1
fi

lang=$1
case "$lang" in
    "-c")
        ;;
    "-cpp")
        ;;
    *)
        printf "Error: invalid language argument \"${1}\".\n"
        exit 1
        ;;
esac

mode=$2
case $mode in
    "-f")
        ;;
    "-m")
        ;;
    "-r")
        ;;
    *)
        printf "Error: invalid action argument \"${2}\".\n"
        exit 1
        ;;
esac

dir=$3
# If dir param is '-d', replace it with './src/'
if [[ "$dir" == "-d" ]]; then
    dir="./src/"
else
    # Append '/' to directory parameter if necessary.
    last_char_of_dir_param=${dir: -1}
    if [[ last_char_of_dir_param != "/" ]]; then
        dir="${dir}/"
    fi
fi

if [[ "$mode" == "-m" ]]; then
    file=$4
    file_upper=$(printf "$file" | tr '[:lower:]' '[:upper:]')
    header="#ifndef ${file_upper}_H\n#define ${file_upper}_H\n\n#endif\n"
    source="#include \"./${file}.h\"\n\n"

    touch "$dir"/"$file".h
    printf "$header" >> "${dir}${file}".h

    if [[ "$lang" == "-c" ]]; then
        extension=".c"
    fi

    if [[ "$lang" == "-cpp" ]]; then
        extension=".cpp"
    fi

    touch "${dir}${file}${extension}"
    printf "$source" >> "${dir}${file}${extension}"
fi

if [[ "$mode" == "-f" ]]; then
    search_term=$4

    if [[ "$lang" == "-c" ]]; then
            filetypes=".h$|.c$"
    fi

    if [[ "$lang" == "-cpp" ]]; then
        filetypes=".h$|.cpp$"
    fi

    result=$(
        find "$dir" -type f \
        | grep -E "$filetypes" \
        | xargs grep -E "$search_term" \
        | sed 's_//_/_g' # When dir param ends with '/', results will have '//'.
    )

    # Early return if no results were found.
    if [[ "$result" == "" ]]; then
        printf "Found: 0\n"
        exit 0
    fi

    printf "$result\n"
    printf "$result" | awk 'END { print "Found:", NR }'
fi

# Replace
if [[ "$mode" == "-r" ]]; then

    if [[ "$5" == "" ]]; then
        printf "Error: missing parameter\n"
        exit 1
    fi

    old_term=$4
    new_term=$5

    if [[ "$lang" == "-c" ]]; then
            filetypes=".h$|.c$"
    fi

    if [[ "$lang" == "-cpp" ]]; then
        filetypes=".h$|.cpp$"
    fi
 
    result=$(
        find "$dir" -type f \
        | grep -E "$filetypes" \
        | xargs grep -E "$old_term" \
        | sed 's_//_/_g' # When dir param ends with '/', results will have '//'.
    )

    find "$dir" -type f \
        | grep -E "$filetypes" \
        | xargs sed -i "s/$old_term/$new_term/g"

    # Early return if no results were found.
    if [[ "$result" == "" ]]; then
        printf "Replaced: 0\n"
        exit 0
    fi

    printf "$result\n" | sed "s/$old_term/$new_term/g"
    printf "$result" | awk 'END { print "Replaced:", NR }'
fi
