ramza-in-the-fast-lane
======================

Exploring optimality in Final Fantasy Tactics

Testing
-------

    $ sbt console
    scala> Party(ZodiacWarrior(), ZodiacWarrior()).gainExperience
    res0: ...

Generating code
---------------

    $ cat levels.txt | ./levels.pl
    $ cat jobs.txt | ./jobs.pl

Colophon
--------

I love the game *Jones in the Fast Lane*.
