package ch.epfl.scala

import scalaprops.{Property, Scalaprops}

object Usage extends Scalaprops {

  val testDoNothing: Property =
// #do-nothing
    Property.forAll { x: Int =>
      Example.doNothing(x) == x
    }
// #do-nothing

}
