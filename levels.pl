#!/usr/bin/env perl

use strict;
use warnings;

use 5.10.0;

my @points;

while (<STDIN>) {
  chomp;

  my($level, $points) = split / /;

  push @points, $points;
}

$" = ', ';
say "[@points]";
