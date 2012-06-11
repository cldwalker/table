## Description

Display ascii tables for almost any data structure with ease.

## Install

Until this is packaged as a proper clojar:

    $ lein deps
    $ lein repl

## Usage

To use as a library:

    (use '[table.core :only [table]])

Or in a repl:

```clojure
$ lein repl
user=> (table [["1" "2"] ["3" "4"]])
+---+---+
| 1 | 2 |
+---+---+
| 3 | 4 |
+---+---+

user=> (table [{:a 11 :b 22} {:a 3 :b 4}])
+----+----+
| a  | b  |
+----+----+
| 11 | 22 |
| 3  | 4  |
+----+----+
```

## Similar libraries
* Clojure comes with a similar function [print-table](http://clojure.github.com/clojure/clojure.pprint-api.html#clojure.pprint/print-table). But it isn't too smart. This library aims to supports more data structures.
* [doric](https://github.com/joegallo/doric) is more full-featured than print-table, supporting different formats. However it doesn't focus on features I'm interested in or have a thorough-enough test suite.

## TODO
* Handle string keys
* Handle non-string values
* Handle sets, lists, anything seqable
* Port features from [hirb](http://github.com/cldwalker/hirb)
  * Handle all fields from a map
  * Ellipsis when column width exceeds allowed length
  * Custom names for table fields
  * Display unicode, vertical, org and markdown style tables
  * Adjust width of table based on terminal size
* Look into auto-rendering database results in reply repl
