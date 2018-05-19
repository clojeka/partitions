# Data partitions 10-folded

When you test different data mining classifiers, you need to run them
with the same test data in order to be a fair comparison.  I had built
a classifier in Lisp, but was going to compare it against Weka's
classifiers, so I had to test them with the same test data as Weka
would use.  The Weka library has the right code to generate k-fold
test data, so I wrote Java to extract them.  If I knew a little
Clojure back then, I could have written my classifier and test data
generation all in Clojure because Clojure has easy access to any Java
library.

This library offers you is pairs (test, training) of CSV files
10-folded.  (Yes, I fixed it at 10.)  It generates the parts as CSV
files.  See

  partitions.core.-main 

for an example.
