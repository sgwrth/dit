#!/bin/bash

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
    "-m")
        ;;
    *)
        printf "Error: invalid action argument \"${2}\".\n"
        exit 1
        ;;
esac

dir=$3
# Append '/' to directory parameter if necessary.
last_char_of_dir_param=${dir: -1}
if [[ last_char_of_dir_param != "/" ]]; then
    dir="${dir}/"
fi

file=$4

if [[ "$mode" == "-m" ]]; then

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
