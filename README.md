## Description

Display ascii tables for almost any data structure with ease.

[![Build Status](https://secure.travis-ci.org/cldwalker/table.png?branch=master)](http://travis-ci.org/cldwalker/table)

## Install

To have it available on all projects, add to your leiningen2's ~/lein/profiles.clj:

    {:user {:dependencies { table "0.2.0"}}}

To have it on an individual project, add to your project.clj:

    [table "0.2.0"]

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

## Similar libraries
* Clojure comes with a similar function [print-table](http://clojure.github.com/clojure/clojure.pprint-api.html#clojure.pprint/print-table) but it is simple and ugly [until this gets merged](http://dev.clojure.org/jira/browse/CLJ-1009).
* [doric](https://github.com/joegallo/doric) is more full-featured than print-table, supporting formats other than text. But it lacks support for handling many data structures and tests are weak.

table improves on these by rendering more data structures and ascii style tables.

## Contributing
[See here](http://tagaholic.me/contributing.html)

## TODO
* Set default style
* Port features from [hirb](http://github.com/cldwalker/hirb)
  * Ellipsis when column width exceeds allowed length
  * Custom names for table fields
  * Adjust width of table based on terminal size
* Handle no rows
* Handle vecs with different sizes
* Look into auto-rendering database results in reply repl
* Escape tabs
