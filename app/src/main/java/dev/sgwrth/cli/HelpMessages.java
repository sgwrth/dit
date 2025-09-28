package dev.sgwrth.cli;

public class HelpMessages {
    public static final String GENERAL_HELP = """
    Show version/help:
    dit -v:Check version.
    dit -h:Show help.

    General usage:
    dit [lang] [action] [dir] [search term] [replacement term]

    [lang]:
    -c    C
    -cpp  C++

    [action]:
    -f    Find text in files.
    -m    Make source (and header) file.
    -r    Search for and replace text.

    [dir]:
    -d    Sets dir to "./src/".
    Otherwise, give the (relative) path to the dir you want to use.
    The path can end in "/" (default behaviour when using tab to
    autocomplete) or not, both are fine.

    [search term], [replacement term]:
    Give the string you want to find and its replacement.  When a term
    consists of multiple words, enclose it in quotes.

    Examples:
    dit -c -f -d 42
        Searches all *.c and *.h files in ./src/ and its subdirs
        for the term "42" and lists the occurances.
    dit -cpp -m ./src/structs Person
        Creates "Person.cpp" and "Person.h" files in ./src/structs dir.
        The files include basic C/C++ boilerplate code ("#ifndef ...").
    dit -cpp -r ./src/classes Foo Bar
        Searches all *.cpp and *.h files in ./src/classes and its subdirs
        for the term "Foo" and replaces them with "Bar".
    """;
}
