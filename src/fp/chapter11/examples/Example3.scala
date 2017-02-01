package fp.chapter11.examples

import fp.chapter8.examples.Gen2

case class Order(item: Item, quantity: Int)
case class Item(name: String, price: Double)

object Example3 {
//  val genOrder: Gen2[Order] = for {
//  name <- Gen2.nextString
//  price <- Gen2.nextDouble
//  quantity <- Gen2.nextInt
//} yield Order(Item(name, price), quantity)
}