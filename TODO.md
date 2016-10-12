Next action
===========

* limit frontier size by always sorting the warriors
* add distance to next level up as a secondary heuristic to cut down on graph explosion during jp grinding
* research the lack of inter party sorting, using head distance vs min any distance (search for one node), and inter character frontier trimming (if you do not contribute to the head improving, it is not a worthwhile investment)

Miscellaneous
=============

* add scalariform
* add microbenchmarking
* allow available jobs to be filterable (see dark knight filter) (this is a basic heuristic)
* add heuristic framework based on distance to hasJob (how many jobs needed to get to dark knight)
* design query language (one character has darknight, one character has mystic, one character has time mage and white mage)
  * query language needs expression class, concat operator, and job to expression implicit converter
* add convenience exp/jp constructors/DSL to aid with testing (needs implicit dictionary)
