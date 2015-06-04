package streams

import scalameter._

trait ErasedBenchTest extends BaseTest {

  def testErased() = {

    var N: Int = testSize
    var v: Array[Long] = Array.tabulate(N)(i => i.toLong % 1000)
    var vHi: Array[Long] = Array.tabulate(1000000)(_.toLong)
    var vLo: Array[Long] = Array.tabulate(10)(_.toLong)

    def sanityCheck() = {
      val r1 = Stream(Array(1,2,3)).sum
      val r2 = Stream(Array(1,2,3)).map(d => d * d).sum
      val r3 = Stream(Array(1,2,3)).filter(x => x % 2 == 0).map(x => x * x).sum
      val r4 = Stream(Array(1,2,3)).flatMap(d => Stream(Array(4,5)).map(dp => dp * d)).sum
      assert(r1 == 6, r1)
      assert(r2 == 14, r2)
      assert(r3 == 4, r3)
      assert(r4 == 54, r4)
    }

    test(
      "erased",
      "Sum",
      _ => sanityCheck(),
      {
        Stream(v).sum
      },
      () => {})

    test(
      "erased",
      "SumOfSquares",
      _ => sanityCheck(),
      {
        Stream(v).map(d => d * d).sum
      },
      () => {})

    test(
      "erased",
      "SumOfSqEven",
      _ => sanityCheck(),
      {
        Stream(v).filter(x => x % 2 == 0).map(x => x * x).sum
      },
      () => {})

    test(
      "erased",
      "Cart",
      _ => sanityCheck(),
      {
        val sum: Long = Stream(vHi).flatMap(d => Stream(vLo).map(dp => dp * d)).sum
      },
      () => {})
  }
}
