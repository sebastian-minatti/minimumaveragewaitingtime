package com.mat.core

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

import org.scalatest.FlatSpec

class OrderProcessorSpec extends FlatSpec {

  def fixture = new {
    val output = new ByteArrayOutputStream()
    val stream = new PrintStream(output)
  }

  it should "produce NumberFormatException when all input are not integers" in {
    val f = fixture
    val testInputErrorA = new ByteArrayInputStream("3\na\r3\r1\ra\r2\r6".getBytes())

    OrderProcessor.process(testInputErrorA, f.stream)

    assert( f.output.toString() == "Syntax error For input string: \"a\"\n" )
  }

  it should "produce AssertionError when the input is empty" in {
    val f = fixture
    val testInputErrorB = new ByteArrayInputStream("".getBytes())

    OrderProcessor.process(testInputErrorB, f.stream)

    assert( f.output.toString() == "Syntax error assertion failed: The input is empty.\n" )
  }

  it should "produce AssertionError when the number of guest is greater than 100000" in {
    val f = fixture
    val testInputErrorB = new ByteArrayInputStream("100001\n".getBytes())

    OrderProcessor.process(testInputErrorB, f.stream)

    assert( f.output.toString() == "Syntax error assertion failed: Guests number mismatch 100001 should be  0 <= G  <= 100000\n" )
  }

  it should "produce AssertionError when the number of guest is less than 0" in {
    val f = fixture
    val testInputErrorC = new ByteArrayInputStream("-1\n".getBytes())

    OrderProcessor.process(testInputErrorC, f.stream)

    assert( f.output.toString() == "Syntax error assertion failed: Guests number mismatch -1 should be  0 <= G  <= 100000\n" )
  }

  it should "produce AssertionError when the number of input does not match with the number of guests" in {
    val f = fixture
    // we need one more input since 3 is the number of guests, and we have 5 inputs.
    val testInputErrorD = new ByteArrayInputStream("3\n0\r3\r1\r9\r2".getBytes())

    OrderProcessor.process(testInputErrorD, f.stream)

    assert( f.output.toString() == "Syntax error assertion failed: Input mismatch must be two input values per guest.\n" )
  }

  // (TODO) we should add testing for orders

  it should "return 8 as minimum average time" in {
    val f = fixture
    val testA = new ByteArrayInputStream("3\n0\r3\r1\r9\r2\r6".getBytes())
    OrderProcessor.process(testA, f.stream)

    assert( f.output.toString() == "9\n" )
  }

  it should "return 9 as minimum average time" in {
    val f = fixture
    val testB = new ByteArrayInputStream("3\n0\r3\r1\r9\r2\r5".getBytes())
    OrderProcessor.process(testB, f.stream)

    assert( f.output.toString() == "8\n" )
  }

  it should "return 6 as minimum average time" in {
    val f = fixture
    // with big arrival times numbers for the last guests.
    val testC = new ByteArrayInputStream("3\n0\r3\r20\r9\r18\r5".getBytes())
    OrderProcessor.process(testC, f.stream)

    assert( f.output.toString() == "6\n" )
  }

  it should "return 5 as minimum average time" in {
    val f = fixture
    // with the first arrival at 1000 the next at 2000 and the last 3000
    val testD = new ByteArrayInputStream("3\n1000\r3\r2000\r9\r3000\r5".getBytes())
    OrderProcessor.process(testD, f.stream)

    assert( f.output.toString() == "5\n" )
  }

}
