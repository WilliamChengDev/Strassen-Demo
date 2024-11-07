# Strassen's Algoritm Demonstration

This is a demonstration of Strassen's algorithm, an algorithm used for matrix multiplication. It is named after <br>
German mathematician Volker Strassen. This demo compares strassen's algorithm's runtime to the normal gradeschool <br>
method of matrix multiplication.

## How the gradeschool algorithm works:

Recall the conventional method of matrix multiplication: <br>
![gradeschool](/res/gradeschool.png) <br>

The .32 result was obtained by multiplying the respective pair of values in the row and column: <br>
.7*.3 + .2*.4 + .10\*.3 = .32

## How the Regular Algorithm works:

The regular algorithm uses something called "block matrix multiplication".
Assume we are calculating the matrix multiplcation of two n\*n matrices, A and B, where n is a power of 2.<br>

In this case n = 4.
We can split the two matrices into 4 equal sub-matrices:
![split matrix](/res/blocks.png) <br>
Where A is split into A11, A12, A21, A22 <br>
And B is split into B11, B12, B21, B22 <br>

The multiplied matrix C will therefore also be composed of 4 submatrices, C11, C12, C21, C22.

Similiar to gradeschool matrix multiplication, C11 will be equal to (A11 \* B11) + (A12 \* B21).

The resulting A11 \* B11 and A12 \* B21 can then be solved using the same partition-and-merge method, therefore the whole solution <br>
is recursive.

combining all sub-matrices of C will give us the multiplication result.

## Regular Complexity

Since a total of 8 recursive calls are in each level, with the matrices split into 4 at each time, <br>
we get this complexity via the master theorem: <br>
![regular complexity](/res/regularComplexity.png); <br>

## How Strassen's works

Strassen found a way to do the same calculations with only 7 multiplications by calculating 7 matrix multiplications <br>
which means there is one less recursive call needed.

![strassens](/res/strassens.png) <br>

by calculating P1-P7 we can add and subtract those terms to get C11-C22, reducing the number of multiplcations needed by 1.

## Strassen's complexity

Since there is one less recursive call needed, the complexity we get via the master theorem is:
![strassen's complexity](/res/fastComplexity.png) <br>

which although may seem marginal, there is a large difference in practice.

## Testing:

(Running on M1 Pro Macbook Pro)
| Matrix Size | Regular Time | Strassen's Time |
| ----------- | ------------ | --------------- |
| 2x2 |5.0ms|<0.0ms|
|4x4|7.0ms|<0.0ms|
|8x8|6.0ms|1.0ms|

not much difference when the matrices are small, however when they are larger... <br>
(Running on M1 Pro Macbook Pro, average 3 trials)
| Matrix Size | Regular Time | Strassen's Time |Improvement|
| ----------- | ------------ | --------------- |---|
| 64x64 |87.0ms|65.6ms|32.6% faster|
|128x128|520.3ms|377.0ms|38.0% faster|
|256x256|3.8s|2.6s| 46.2% faster|
|512x512|29.9s|18.6s|60.8% faster|
|1024x1024|4.09min|2.17min|88.5%faster|

as you can see, the improvement grows as the matrix multiplied grows.
Looking at the complexity n^3 vs n^2.81:

![desmos](/res/desmos.png) <br>

at the time the regular algorithm calculates a solution for a ~2500x2500 matrix, <br>
strassen's algorithm can calculate the solution for ~4500x4500 matrix. <br>

## Reflection

This was an interesting experiment to run, and the results were surprising how much faster strassen's algorithm can run. <br>
That is not to say that strassen's algorithm is perfect. Some one its cons include:

・Sparsity. <br>
・Caching.<br>
・n not a power of 2.<br>
・Numerical stability.<br>
・Non-square matrices.<br>
・Storage for intermediate submatrices.<br>
・Crossover to classical algorithm when n is “small.”<br>
・Parallelism for multi-core and many-core architectures<br>

### Other Matrix Multiplication Algorithms:

As a matter of fact, Strassen's algorithm is actually one of the slower matrix multiplication algorithms. <br>
However it is interesting to study because it is a lot less large/complex then other algorithms, <br>
and I found the implementation to be very clever.
![others](/res/others.png) <br>

## Credits

Author William Cheng, University of Iowa (WilliamChengDev on github) <br>
Images from slides by Kevin Wayne <br>
Content from _Algorithm Design_ by Jon Kleinberg and Eva Tardos. Copyright © 2005 Pearson-Addison Wesley
