package test

import org.scalatest.{ShouldMatchers, FlatSpec}
import contentapiclient.ContentApiQuery
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

class ContentApiQueryTest extends FlatSpec with ShouldMatchers with ScalaFutures {

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(2, Seconds), interval = Span(5, Millis))

  "ContentApiQuery" should "query for an id properly through id" in {
    whenReady(ContentApiQuery("uk/culture").get) { response =>
      response.get.status should be ("ok")
    }
  }

  it should "use a search query" in {
    whenReady(ContentApiQuery().withSearchQuery("london").get) { response =>
      response.get.status should be ("ok")
      response.get.results.get.length should be > 5
    }
  }

}
