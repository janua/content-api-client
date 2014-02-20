package test

import org.scalatest.{ShouldMatchers, FlatSpec}
import contentapiclient.ContentApiQuery
import org.scalatest.concurrent.ScalaFutures

class ContentApiQueryTest extends FlatSpec with ShouldMatchers with ScalaFutures {

  "ContentApiQuery" should "query for an id properly through id" in {
    whenReady(ContentApiQuery("uk/culture").get) { response =>
      response.get.status should be ("ok")
    }
  }

  it should "use a search query" in {
    whenReady(ContentApiQuery("ignored").withSearchQuery("london").get) { response =>
      response.get.status should be ("ok")
      response.get.results.get.length should be > 5
    }
  }

}
