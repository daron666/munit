package munit

import munit.internal.console.Printers

class AssertionsSuite extends BaseSuite {
  def check(
      name: TestOptions,
      cond: => Boolean,
      expected: String
  )(implicit loc: Location): Unit =
    test(name.tag(NoDotty)) {
      val (_, clues) = munitCaptureClues(cond)
      assertNoDiff(Printers.print(clues), expected)
    }

  val a = 42
  val b = 43L
  val c: List[Int] = List(41)
  check(
    "basic",
    clue(a) > clue(b),
    """|Clues {
       |  a: Int = 42
       |  b: Long = 43
       |}
       |""".stripMargin
  )

  check(
    "expr",
    clue(a) > clue(c.head),
    """|Clues {
       |  a: Int = 42
       |  c.head: Int = 41
       |}
       |""".stripMargin
  )

  check(
    "subexpr",
    clue(a) > clue(c).head,
    """|Clues {
       |  a: Int = 42
       |  c: List[Int] = List(
       |    41
       |  )
       |}
       |""".stripMargin
  )
}
