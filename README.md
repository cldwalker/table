## Description

Display ascii tables that fit in your terminal for almost any data structure.

[![Build Status](https://travis-ci.org/cldwalker/table.png?branch=master)](https://travis-ci.org/cldwalker/rubydoc)

## Install

To have it available on all projects, add to your leiningen2's ~/lein/profiles.clj:

    {:user {:dependencies { table "0.3.2"}}}

To have it on an individual project, add to your project.clj:

    [table "0.3.2"]

## Usage

To use in a library:

    (use '[table.core :only [table]])

table handles rendering combinations of maps, vecs, lists and sets nested in one another.

    $ lein repl
    user=> (use 'table.core)
    nil

    ; These three yields the same table
    user=> (table [["1" "2"] ["3" "4"]])
    user=> (table '((1 2) (3 4)))
    user=> (table #{[1 2] [3 4]})
    +---+---+
    | 1 | 2 |
    +---+---+
    | 3 | 4 |
    +---+---+

    user=> (table [{:a 11} {:a 3 :b 22}])
    +----+----+
    | a  | b  |
    +----+----+
    | 11 |    |
    | 3  | 22 |
    +----+----+

table can render different styles of tables:

    user=> (table [ [1 2] [3 4]] :style :unicode)
    ┌───┬───┐
    │ 1 │ 2 │
    ├───┼───┤
    │ 3 ╎ 4 │
    └───┴───┘

    user=> (table [ [1 2] [3 4]] :style :org)
    |---+---|
    | 1 | 2 |
    |---+---|
    | 3 | 4 |
    |---+---|

    # Yes, these will generate tables for github's markdown
    user=> (table [ [10 20] [3 4]] :style :github-markdown)

    | 10 | 20 |
    |--- | ---|
    | 3  | 4  |

table can handle plain maps and vectors of course:

    user=> (table (meta #'doc))
    +-----------+---------------------------------------------------------------+
    | key       | value                                                         |
    +-----------+---------------------------------------------------------------+
    | :macro    | true                                                          |
    | :ns       | clojure.repl                                                  |
    | :name     | doc                                                           |
    | :arglists | ([name])                                                      |
    | :added    | 1.0                                                           |
    | :doc      | Prints documentation for a var or special form given its name |
    | :line     | 120                                                           |
    | :file     | clojure/repl.clj                                              |
    +-----------+---------------------------------------------------------------+

    user=> (table (seq (.getURLs (java.lang.ClassLoader/getSystemClassLoader))))
    +--------------------------------------------------+
    | value                                            |
    +--------------------------------------------------|
    | file:/Users/me/code/gems/table/test/             |
    | file:/Users/me/code/gems/table/src/              |
    | file:/Users/me/code/gems/table/dev-resources     |
    | file:/Users/me/code/gems/table/resources         |
    | file:/Users/me/code/gems/table/target/classes/   |
    ...

## Configuration

If your terminal width isn't being auto-detected, you can execute this in your shell before using
the repl: `export COLUMNS`. Alternatively you can bind/alter table.width/\*width\* to your desired
width.

## Similar libraries
* Clojure 1.5.0 comes with a similar function clojure.pprint/pprint-table
* [doric](https://github.com/joegallo/doric) is more full-featured than print-table, supporting formats other than text. But it lacks support for handling many data structures and tests are weak.

table improves on these by rendering more data structures and ascii style tables.

## Bugs/Issues
Please report them [on github](http://github.com/cldwalker/table/issues).

## Contributing
[See here](http://tagaholic.me/contributing.html)

## TODO
* Set default style
* Handle no rows
* Handle vecs with different sizes
* Look into auto-rendering database results in reply repl
* Escape tabs
