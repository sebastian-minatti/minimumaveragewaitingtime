package com.mat.core

object SortOrderByCookingTime extends Ordering[Order] {
  
  def compare(a: Order, b: Order): Int = {    
    b.cookingT compare a.cookingT    
  }
}
