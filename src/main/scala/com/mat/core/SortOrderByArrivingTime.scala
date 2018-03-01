package com.mat.core

object SortOrderByArrivingTime extends Ordering[Order] {
  
  def compare(a: Order, b: Order): Int = {    
    b.arrivingT compare a.arrivingT    
  }
}
