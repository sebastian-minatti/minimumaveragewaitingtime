package com.mat.core

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

object Executor {
  
  def main(args: Array[String]): Unit = {
    val output = new ByteArrayOutputStream()
    val stream = new PrintStream(output)

    val testA = new ByteArrayInputStream("3\n0\r3\r1\r9\r2\r6".getBytes())
    OrderProcessor.process(testA, stream)

    val testB = new ByteArrayInputStream("3\n0\r3\r1\r9\r2\r5".getBytes())
    OrderProcessor.process(testB, stream)

    val testC = new ByteArrayInputStream("3\n0\r3\r20\r9\r18\r5".getBytes())
    OrderProcessor.process(testC, stream)

    println(output.toString())
  }
}
