#!/usr/bin/env perl

use strict;
use warnings;

use 5.10.0;

chomp(my @lines = <STDIN>);

my @jobs = map {
  my($id, $class, @prerequisites) = split /, /;

  my $value_name = uc $class =~ s/ /_/r;
  my $object_name = $class =~ s/ //r;

  [$id, $class, $value_name, $object_name]
} @lines;

my $longest_name_length = do {
  my $champion = (sort { length $b->[2] <=> length $a->[2] } @jobs)[0][2];

  length($champion);
};

for (sort { $a->[0] <=> $b->[0] } @jobs) {
  say sprintf "val %-${longest_name_length}s = %s", $_->[2], $_->[0];
}

for (sort { $a->[0] <=> $b->[0] } @jobs) {
  say sprintf "%-${longest_name_length}s -> 0,", $_->[2];
}

say join ', ', map { $_->[3] } @jobs;

for (sort { $a->[0] <=> $b->[0] } @jobs) {
  say sprintf "%-${longest_name_length}s -> 0,", $_->[3];
}
