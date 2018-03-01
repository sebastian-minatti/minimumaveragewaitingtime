package com.mat.util

import java.io.PrintStream


/**
  * Validate input.
  */
object Validator {

  def apply(lines: Iterator[Int], out: PrintStream) {

    assert(lines.nonEmpty, "The input is empty.")
   if(out == null) throw new IllegalArgumentException // I should create my own exception

    val guests = lines.next
    assert( guests >= 0 && guests <= 100000, s"Guests number mismatch $guests should be  0 <= G  <= 100000")
    assert(0 == lines.size % guests, "Input mismatch must be two input values per guest.")
  }
}
