#!/usr/bin/env perl

use strict;
use warnings;

use 5.10.0;

my %jobs;

chomp(my @lines = <STDIN>);

my @jobs = map {
  my($id, $class, @prerequisites) = split /, /;

  @prerequisites = map {
    s/\*/8/g;

    my($job, $level) = $_ =~ /(.+) (\d)/;

    [$job, int $level]
  } @prerequisites;

  $jobs{ $class } = { map { @$_ } @prerequisites };

  for (@prerequisites) {
    my($job, $level) = @$_;
    my %more_prerequisites = (%{ $jobs{ $job } }, $job, $level);

    while (my($k, $v) = each %more_prerequisites) {
      ${ $jobs{ $class } }{ $k } = $v unless exists ${ $jobs{ $class } }{ $k } and ${ $jobs{ $class } }{ $k } > $v;
    }
  }

  my $value_name = uc $class =~ s/ /_/r;
  my $object_name = $class =~ s/ //r;

  [$id, $class, $value_name, $object_name, \@prerequisites]
} @lines;

use Data::Dumper; print Dumper \%jobs;

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

for my $job (sort { $a->[0] <=> $b->[0] } @jobs) {
  my $class_name = $job->[1];
  my @prerequisites = keys %{ $jobs{$class_name} };

  my $tuples = join ', ', map { 1 } @prerequisites;

  my $map = @prerequisites ? "Map($tuples)" : "Map[JobClass, Int]()";

  say sprintf "%-${longest_name_length}s -> $map,", $job->[3];
}
