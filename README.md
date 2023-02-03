# Atlantbh Code Challenge

## Introduction
The first days were spent researching on similar problems and their respective recursive solutions. After a while I concluded that this problem was a modification of the known "Subset Sum Problem". In the subset sum problem the task is to find all possible subsets of an array that sum up to a given sum K. The array can have numbers that repeat and doesn't need to be of any sequence of numbers. This makes it NP complete, which is detrimental to our solution as any known algorithm has increasingly big O time complexity.

## Solving the problem
Luckily for us there are constraints imposed on the problem:
1. Lego blocks are the same width and height.
2. Each pillar must be higher than previously built pillars
3. The maximum number of lego blocks in test cases will be 300 000.

These constraints can be interpreted as:
1. Can consider every number from 1 .. n-1 as a valid member of a subset
2. There can be no repeating numbers in the subset, therefore the array set (or the pillars we can consider) can be the sequence of natural numbers from 1 .. n-1
3. We only need to optimize till we can reach input 300 000.

Building from these constraints we can finally start working on our solution.

## Mathematical definition
To proceed with the definition we must first understand what is a partition. A partition is a way of writing a number n as a sum of positive integers k. If we want partitions of k=2 that sum up to n=6, we could write {1, 5} or {2, 4}. If we want partitions of k=3 n=6, we can only write {1, 2, 3}, as there are no other possible combinations of 3 different positive integers that can sum up to 6.

We can view the number of possible pillar combinations as the number of partitions of n into at least two distinct parts. We cannot have a pillar that has a height of the number of lego blocks given, as that is not a combination of pillars but just one singular pillar. Building from that to get all the possible pillar combinations we would just sum up all the possible pillar combinations from (k=2) pillars all the way to (k=sqrt(2𝑛)). The upper bound of sqrt(2𝑛) is due to the fact that any set of 𝑘 numbers will have sum at least 𝑘(𝑘+1)/2, therefore there is no need to consider sets of size sqrt(2n) or larger as they will yield 0 possible combinations. The final formula then would be: ∑(𝑘=2, sqrt(2𝑛))=𝑓(𝑛,𝑘).

## The recursive formula 
Let f(n,k) be the number of sets of k natural numbers summing to n. For n = 6; k = 2, it would return 2 ({2,4}, {1, 5}). We can claim that f(n,k)=f(n−k,k)+f(n−k,k−1). To see this, let xₖ > xₖ₋₁ > ... > x₁ be k numbers summing to n. If x₁ > 1 then xₖ - 1 > xₖ₋₁ - 1 > ... > x₂ - 1 > x₁ - 1 is a set of k numbers summing to n−k. If x₁=1, then the above list with x1−1 removed is a list of k−1 numbers summing to n−k. 

This recursion gives us an algorithm to compute 𝑓(𝑛,𝑘) for all 2≤𝑘≤sqrt(2n) via dynamic programming in 𝑂(3/2𝑛) time.

Algorithm courtesy of Mike Earnest: https://math.stackexchange.com/a/3065671

## Problems with C++ 
The first implementation of the recursive algorithm was done in C++. In the beginning I already faced difficulties because C++ didn't have native support for big integer types. The answers for n = 3000 wouldn't fit inside C++ biggest integer type: long long int. As a workaround I started using the Boost C++ library, which has a cpp_int class that could be used as an arbitrary precision integer. The problem was considered solved.

The recursive solution was implemented successfully. Without memoization it could return the result of n = 100 in about  ~0.1 seconds and 150 in ~5 seconds. Testing further would yield little benefit.

Dynamic programming was implemented next using a 2D hashmap. Keys were n and k, values were the result of f(n,k) recursion. This improved the time complexity by magnitudes allowing computations of n=30000 in about ~3 seconds.

The final problem of the solution was memory allocation. For n = 300000 the code was throwing std::bad_alloc, an error indicating that memory couldn't be allocated. I couldn't find a way to specify to the compiler to allocate RAM memory for the program to use, and such the upper limit became 2GB of RAM, which clearly wasn't enough for the max lego blocks.

## Java implementation (Courtesy to my brother for helping me) 
Java was then chosen as the second programming language. It has native BigInt support, custom BigInt libraries that focused more on optimizing memory usage then speed, and most importantly the ability to specify the heap size using -Xmx${HeapSize}. This allowed usage of memory far more than 2GB, and we finally calculated n=300000 in about ~106 seconds using about 28GB of RAM.
