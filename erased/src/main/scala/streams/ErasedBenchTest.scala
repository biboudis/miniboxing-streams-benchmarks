package streams

import scalameter._

trait ErasedBenchTest extends BaseTest {

  def testErased() = {

    var N: Int = testSize
    var v: Array[Long] = Array.tabulate(N)(i => i.toLong % 1000)
    var vHi: Array[Long] = Array.tabulate(1000000)(_.toLong)
    var vLo: Array[Long] = Array.tabulate(10)(_.toLong)

    test(
      "erased",
      "Sum",
      _ => {},
      {
        Stream(v).sum
      },
      () => {})

    test(
      "erased",
      "SumOfSquares",
      _ => {},
      {
        Stream(v).map(d => d * d).sum
      },
      () => {})

    test(
      "erased",
      "SumOfSqEven",
      _ => {},
      {
        Stream(v).filter(x => x % 2 == 0).map(x => x * x).sum
      },
      () => {})

    test(
      "erased",
      "Cart",
      _ => {},
      {
        val sum: Long = Stream(vHi).flatMap(d => Stream(vLo).map(dp => dp * d)).sum
      },
      () => {})
  }
}
