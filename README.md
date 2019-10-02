# CalcOfSets
Interpreter of operations on sets 

An interpreter for a calculator with the "command-language" shown further down. All "sentences" in this command language are statements for operations with sets of arbitrary big natural numbers. The calculator should be able to store an arbitrary number of variables (A variable
being a combination of a name and a value).

With the aid of the command-language specified further down, we can manipulate
the sets of natural numbers and the variables that are contained in the calculator.
Only identifiers are allowed as names for variables.

There are four operators that can be used on sets in this language:
+ : A + B = { x | x ∈ A ∨ x ∈ B } union
* : A * B = { x | x ∈ A ∧ x ∈ B } intersection
− : A − B = { x | x ∈ A ∧ x ∉ B } complement
| : A | B = { x | x ∈ A + B ∧ x ∉ A * B } symmetric difference
= { x | x ∈ (A + B) − (A * B) }

The operator '*' has a higher priority than '+', '|' and '-', whom have the same
priority. The operators are left-associative.
Example: A − B * Set1 + D is to be evaluated as (A − (B * Set1)) + D.

There are two commands available. For each of those an example is given:
1. Set1 = A + B − Set1 * (D + Set2)
Calculate the set that is the result of the expression to the right of the '='-sign and associate the variable with the identifier Set1 with this Set.

2. ? Set1 + Set2
Calculate the set that is the result of the expression, and print the elements of this set: separated by spaces on a single line on the
standard output. Do not print any other text or commas because this will interfere with the automated test.
