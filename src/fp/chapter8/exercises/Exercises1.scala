package fp.chapter8.exercises

object SumProperty {
  /*
   * 1. The sum of the empty list is 0.
   * 2. The sum of a list whose elements are all equal to `x` is just the list's 
   * 		length multiplied by `x`. We might express this as `sum(List.fill(n)(x)) == n*x`
   * 3. For any list, `l`, `sum(l) == sum(l.reverse)`, since addition is commutative.
   * 4. The sum of 1,2,3...n is `n*(n+1)/2`.
   * 5. Given a list, `List(x,y,z,p,q)`, `sum(List(x,y,z,p,q)) == sum(List(x,y)) + sum(List(z,p,q))`, 
   * 		since addition is associative. More generally, we can partition a list into two subsequences whose 
   * 		sum is equal to the sum of the overall list.
   * */
}