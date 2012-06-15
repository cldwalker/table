## Description

Display ascii tables for almost any data structure with ease.

## Install

Until this is packaged as a proper clojar:

    $ lein deps
    $ lein repl

## Usage

To use as a library:

    (use '[table.core :only [table]])

table handles rendering combinations of maps, vecs, lists and sets nested in one another.

    $ lein repl
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

## Similar libraries
* Clojure comes with a similar function [print-table](http://clojure.github.com/clojure/clojure.pprint-api.html#clojure.pprint/print-table). But it isn't too smart. This library aims to supports more data structures.
* [doric](https://github.com/joegallo/doric) is more full-featured than print-table, supporting different formats. However it doesn't focus on features I'm interested in or have a thorough-enough test suite.

## TODO
* Port features from [hirb](http://github.com/cldwalker/hirb)
  * Ellipsis when column width exceeds allowed length
  * Custom names for table fields
  * Display unicode, vertical, org and markdown style tables
  * Adjust width of table based on terminal size
* Look into auto-rendering database results in reply repl
