## Description

This library displays ascii tables that automatically fit in your terminal and handle most data structures.

[![Build Status](https://travis-ci.org/cldwalker/table.png?branch=master)](https://travis-ci.org/cldwalker/table)

## Install

To have it in a lein project, add to your project.clj:

    [table "0.5.0"]

To have it in a deps.edn project, add to your deps.edn:

    {table/table {:mvn/version "0.5.0"}}

To use with [babashka](https://github.com/babashka/babashka), add to bb.edn.

## Usage

table handles rendering combinations of maps, vecs, lists and sets nested in one another.

    $ lein repl
    user=> (require '[table.core :as t])
    nil

    ; These three yields the same table
    user=> (t/table [["1" "2"] ["3" "4"]])
    user=> (t/table '((1 2) (3 4)))
    user=> (t/table #{[1 2] [3 4]})
    +---+---+
    | 1 | 2 |
    +---+---+
    | 3 | 4 |
    +---+---+

    user=> (t/table [{:a 11} {:a 3 :b 22}])
    +----+----+
    | a  | b  |
    +----+----+
    | 11 |    |
    | 3  | 22 |
    +----+----+

table can render different styles of tables:

    user=> (t/table [ [1 2] [3 4]] :style :unicode)
    ┌───┬───┐
    │ 1 │ 2 │
    ├───┼───┤
    │ 3 ╎ 4 │
    └───┴───┘

    user=> (t/table [ [1 2] [3 4]] :style :org)
    |---+---|
    | 1 | 2 |
    |---+---|
    | 3 | 4 |
    |---+---|

    # Yes, these will generate tables for github's markdown
    user=> (t/table [ [10 20] [3 4]] :style :github-markdown)

    | 10 | 20 |
    |--- | ---|
    | 3  | 4  |

table can also render custom styles:

    user> (t/table [[10 20] [3 4]] :style {:top ["◤ " " ▼ " " ◥"]
                                   :top-dash "✈︎"
                                   :middle ["▶︎ " "   " " ◀︎"]
                                   :dash "✂︎"
                                   :bottom ["◣ " " ▲ " " ◢"]
                                   :bottom-dash "☺︎"
                                   :header-walls ["  " "   " "  "]
                                   :body-walls ["  " "   " "  "] })
    ◤ ✈︎✈︎ ▼ ✈︎✈︎ ◥
      10   20
    ▶︎ ✂︎✂︎   ✂︎✂︎ ◀︎
      3    4
    ◣ ☺︎☺︎ ▲ ☺︎☺︎ ◢

table can handle plain maps and vectors of course:

    user=> (t/table (meta #'doc))
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

    user=> (t/table (seq (.getURLs (java.lang.ClassLoader/getSystemClassLoader))))
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
* Clojure 1.5.0 comes with a similar function clojure.pprint/print-table
* [doric](https://github.com/joegallo/doric) is more full-featured than print-table, supporting
  formats other than text.

table improves on these alternatives by rendering more data structures, supporting
different ascii style tables and resizing to fit your terminal.

## Bugs/Issues
Please report them [on github](http://github.com/cldwalker/table/issues).

## Contributing
[See here](https://tagaholic.me/contributing.html)

## TODO
* Handle no rows
* Handle vecs with different sizes
* Escape tabs
