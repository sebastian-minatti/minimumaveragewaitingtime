package com.mat.core

import java.io.InputStream
import java.io.PrintStream
import java.util.logging.Logger

import scala.collection.mutable
import scala.io.Source

import com.mat.util.Validator

object OrderProcessor {

  val logger = Logger.getLogger(getClass.getName)

  /**
   * @param in: An InputStream, which contains the following
   * input:
   * A line containing a single number: The number of guests G,
   * Followed by G lines containing two numbers Oi and Di
   * separated by space.
   * There may be a trailing newline.
   * Oi ist the ordering time for Gi, Di is the time it takes
   * to bake Gi's pizza.
   *
   *  0 <= G  <= 100000
   *  0 <= Oi <= 1000000000
   *  1 <= Di <= 1000000000
   *
   * @param out: A PrintStream, which process writes the
   *
   * following output to:
   * A single line containing the integer part of the average
   * waiting time if the input is valid.
   * A single line starting with the words "Syntax error" and
   * an optional description otherwise.
   * There may be a trailing newline.
   */
  def process(in: InputStream, out: PrintStream): Unit = {

    try {

      val (lines, validateLines) = Source.fromInputStream(in).getLines().map( _.toInt ).duplicate

      // validate input
      Validator(validateLines, out)

      val guests: Int = lines.next()

      val arrivingQ: mutable.PriorityQueue[Order] = mutable.PriorityQueue[Order]()(SortOrderByArrivingTime)

      // load guests.
      for (_ <- 1 to guests) {
        val arrivalT = lines.next()
        val pizzaT = lines.next()
        arrivingQ.enqueue(Order(arrivalT, pizzaT))
      }

      val response = minimumWaitingTime(arrivingQ) / guests

      out.append( s"$response\n")
    } catch {
      case nfe: NumberFormatException    => out.append(s"Syntax error ${nfe.getMessage}\n")
      case ase: AssertionError           => out.append(s"Syntax error ${ase.getMessage}\n")
      case iae: IllegalArgumentException => logger.info(s"${iae.getCause}")
      case err: Exception                => out.append(s"Syntax error ${err.getMessage}\n")
    }
  }

  private
  def minimumWaitingTime(arrivalQ: mutable.PriorityQueue[Order]): Int = {
    val cookingQ: mutable.PriorityQueue[Order] = mutable.PriorityQueue[Order]()(SortOrderByCookingTime)

    var timer: Int = 0
    var waitingTime: Int = 0

    // iterate over each guest
    for( i <- 1 to arrivalQ.size ) {
      // add orders
      if( i == 1 || arrivalQ.nonEmpty && arrivalQ.head.arrivingT > timer ) {
        timer = Math.max(arrivalQ.head.arrivingT, timer)
        cookingQ.enqueue(arrivalQ.dequeue())
      } else {
        while (arrivalQ.nonEmpty && arrivalQ.head.arrivingT <= timer) {
          cookingQ.enqueue(arrivalQ.dequeue())
        }
      }

      //serve orders
      if (cookingQ.nonEmpty) {
        val current = cookingQ.dequeue()
        timer += current.cookingT
        waitingTime +=  timer - current.arrivingT
      }
    }
    waitingTime
  }
}



