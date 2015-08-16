Documentation for AI Homework 1
=====================================

+---------------+
| BUILD AND RUN |
+---------------+

1. Unzip PRABHAKAR_SINGH_HW1.zip in some location.
2. To build, run "make agent"
3. To run the program, issue "make run"

+------+
| BUGS |
+------+

None

+-------+
| OTHER |
+-------+

All search algorithms have been implemented using "A Clean Robust Algorithm".
So it is possible that a node may appear one or more times in LOG.

For BFS and DFS, all edge values are considered to 1 while traversing. Though shortest path distance outputted by these algorithms include actual edge cost.

In case algorithm is not able to find any solution, it prints LOG in first line  and "NoPathAvailable" in second line.
