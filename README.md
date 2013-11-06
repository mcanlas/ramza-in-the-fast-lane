ramza-in-the-fast-lane
======================

Exploring optimality in Final Fantasy Tactics

Testing
-------

    $ sbt console
    scala> ZodiacWarrior(Map(Chemist -> 1110)).jobLevel(Chemist)
    res0: Int = 5

Generating code
---------------

    $ cat levels.txt | ./levels.pl
    $ cat jobs.txt | ./jobs.pl

Colophon
--------

I love the game *Jones in the Fast Lane*.
