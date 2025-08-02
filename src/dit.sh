#!/bin/bash

# Show version.
if [[ "$1" == "-v" || "$1" = "" ]]; then
    printf "dit version 0.1 [02-Aug-2025]\n"
    printf "Run 'dit -h' for help.\n"
    exit 0
fi

# Show help.
if [[ "$1" == "-h" ]]; then
    printf "Show version/help:\n"
    printf "dit -v:\tCheck version.\n"
    printf "dit -h:\tShow help.\n"
    printf "\nGeneral usage:\n"
    printf "dit [lang] [action] [dir] [search term] [replacement term]\n"
    printf "\n[lang]:\n"
    printf "%s\t%s\n" "-c" "C"
    printf "%s\t%s\n" "-cpp" "C++"
    printf "\n[action]:\n"
    printf "%s\t%s\n" "-f" "Find text in files."
    printf "%s\t%s\n" "-m" "Make source (and header) file."
    printf "%s\t%s\n" "-r" "Search for and replace text."
    printf "\n[dir]:\n"
    printf "%s\t%s\n" "-d" "Sets dir to './src/'."
    printf "Otherwise, give the (relative) path to the dir you want to use.\n"
    printf "The path can end in '/' (default behaviour when using tab to\n"
    printf "autocomplete) or not, both are fine.\n"
    printf "\n[search term], [replacement term]:\n"
    printf "Give the string you want to find and its replacement.  When a term\n"
    printf "consists of multiple words, enclose it in quotes.\n"
    printf "\nExamples:\n"
    printf "dit -c -f -d 42\n"
    printf "\tSearches all *.c and *.h files in ./src/ and its subdirs\n"
    printf "\tfor the term '42' and lists the occurances.\n"
    printf "dit -cpp -m ./src/structs Person\n"
    printf "\tCreates 'Person.cpp' and 'Person.h' files in ./src/structs dir.\n"
    printf "\tThe files include basic C/C++ boilerplate code ('#ifndef ...').\n"
    printf "dit -cpp -r ./src/classes Foo Bar\n"
    printf "\tSearches all *.cpp and *.h files in ./src/classes and its subdirs\n"
    printf "\tfor the term 'Foo' and replaces them with 'Bar'.\n"
    printf "\n"
    exit 0
fi

# Check if necessary binaries are installed on the system.
required_bins=(
    "awk"
    "find"
    "printf"
    "sed"
    "touch"
    "tr"
    "xargs"
)
bin_missing=false
for required_bin in "${required_bins[@]}"; do
    if command -v "$required_bin" >/dev/null 2>&1; then
        : # Do nothing.
    else
        bin_missing=true
        printf "Error: required program '${required_bin}' missing\n"
    fi
done
if [[ "$bin_missing" == true ]]; then
    exit 1
fi

# Determine language.
lang=$1
case "$lang" in
    "-c")
        ;;
    "-cpp")
        ;;
    *)
        printf "Error: invalid parameter '${1}'.\n"
        exit 1
        ;;
esac

# Exit with error if less than four params (minimum for '-f' action) were passed.
if [[ "$#" -lt 4 ]]; then
    printf "Error: missing parameter(s)\n"
    exit 1
fi

# Determine action.
mode=$2
case $mode in
    "-f") # Find.
        ;;
    "-m") # Make source (and header) file.
        ;;
    "-r") # Replace.
        ;;
    *)
        printf "Error: invalid action argument '${2}'.\n"
        exit 1
        ;;
esac

# If directory param is '-d', replace it with './src/'
dir=$3
if [[ "$dir" == "-d" ]]; then
    dir="./src/"
else
    # Append '/' to directory parameter if necessary.
    last_char_of_dir_param=${dir: -1}
    if [[ last_char_of_dir_param != "/" ]]; then
        dir="${dir}/"
    fi
fi

# Make source (and header) file.
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

# Find.
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

# Replace.
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
