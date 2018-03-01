package com.mat.core

/**
 * Take customer orders.
 * 
 * @param arrivingT The time when order arrived.
 * @param cookingT  The time required to cook. 
 */
case class Order (
  arrivingT:  Int,
  cookingT:    Int 
){
  assert(arrivingT >= 0 && arrivingT <= 1000000000, s"Mismatch ordering time $arrivingT should be 0 <= Oi <= 1000000000")
  assert(cookingT >= 1 && cookingT <= 1000000000, s"Mismatch ordering time $arrivingT should be 1 <= Di <= 1000000000")
}
