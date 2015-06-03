package streams

import scalameter._

trait MiniboxedBenchTest extends BaseTest {

  def testMiniboxed() = {

    var N: Int = testSize
    var v: Array[Long] = Array.tabulate(N)(i => i.toLong % 1000)
    var vHi: Array[Long] = Array.tabulate(1000000)(_.toLong)
    var vLo: Array[Long] = Array.tabulate(10)(_.toLong)

    test(
      "miniboxed",
      "Sum",
      _ => {},
      {
        Stream(v).sum
      },
      () => {})

    test(
      "miniboxed",
      "SumOfSquares",
      _ => {},
      {
        Stream(v).map(d => d * d).sum
      },
      () => {})

    test(
      "miniboxed",
      "SumOfSqEven",
      _ => {},
      {
        Stream(v).filter(x => x % 2 == 0).map(x => x * x).sum
      },
      () => {})

    test(
      "miniboxed",
      "Cart",
      _ => {},
      {
        Stream(vHi).flatMap(d => Stream(vLo).map(dp => dp * d)).sum
      },
      () => {})
  }
}
